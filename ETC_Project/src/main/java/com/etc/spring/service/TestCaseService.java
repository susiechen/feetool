package com.etc.spring.service;

import com.etc.spring.core.WebDriverFactory;
import com.etc.spring.dao.TestCaseMapper;
import com.etc.spring.model.*;
import com.etc.spring.testcase.RetryTestListener;
import com.etc.spring.testcase.TestCase_ETC;
import com.etc.spring.util.*;
import com.fasterxml.jackson.databind.deser.Deserializers;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.testng.TestNG;
import org.testng.reporters.JUnitXMLReporter;
import org.uncommons.reportng.HTMLReporter;

import javax.servlet.http.HttpServletRequest;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by none.none on 2017/2/15.
 */
@Service("testCaseService")
public class TestCaseService {
    public static Logger logger = Logger.getLogger(TestCaseService.class);

    @Autowired
    private TestCaseMapper testCaseMapper;
    public synchronized List<TestCase> testCaseList(Map<String,Object> params)
    {
        return this.testCaseMapper.testCaseList(params);
    }
    public synchronized  Long testCaseCounts(int project_id )
    {
        return  this.testCaseMapper.testCaseCounts(project_id);
    }
    public synchronized int delTestCase(int tc_id)throws Exception
    {
        return this.testCaseMapper.delTestCase(tc_id);
    }
    public synchronized int updateTestCase(TestCase tc)throws Exception
    {
        return this.testCaseMapper.updateTestCase(tc);
    }
    public synchronized List<TestCase> getCaseByProjectName(Map<String,Object> params)
    {
        return this.testCaseMapper.getCaseByProjectName(params);
    }
    public synchronized Long getCaseCountByProjectName(String project_name)
    {
        return this.testCaseMapper.getCaseCountByProjectName(project_name);
    }
    public synchronized TestCase  getTestCaseOperate(int id)
    {
        return this.testCaseMapper.getTestCaseOperate(id);
    }
    public synchronized int newTestCase(Map<String,Object> params)throws Exception
    {
        return  this.testCaseMapper.newTestCase(params);
    }
    public synchronized int newTestStep(Map<String,Object> params)throws Exception
    {
        return this.testCaseMapper.newTestStep(params);
    }
    public synchronized int newVerifyStep(Map<String,Object> params)throws Exception
    {
        return this.testCaseMapper.newVerifyStep(params);
    }

    public synchronized List<OperateStep> getTestStep(Map<String,Object> params)
    {
        return this.testCaseMapper.getTestStep(params);
    }
    public synchronized List<ResultVerify> getVerifyStep(Map<String,Object> params)
    {
        return this.testCaseMapper.getVerifyStep(params);
    }
    public synchronized int delOperateStep(Map<String,Object> params)throws Exception
    {return this.testCaseMapper.delOperateStep(params);}
    public synchronized int delResultVerify(Map<String,Object> params)throws Exception
    {return this.testCaseMapper.delResultVerify(params);}
    public synchronized int updateEditTestCase(Map<String,Object> params) throws Exception{return this.testCaseMapper.updateEditTestCase(params);}
    public synchronized long getTcStepNumber(Map<String,Object> params){return this.testCaseMapper.getTcStepNumber(params);}
    public synchronized Project getProjectInfo(int project_id)
    {
        return this.testCaseMapper.getProjectInfo(project_id);
    }
    public synchronized List<String> getTestCaseByProjectId(int project_id){return this.testCaseMapper.getTestCaseByProjectId(project_id);}

    @Transactional(rollbackFor = Exception.class)
    public Map saveEditTestCase(TestCaseStep caseInfo) throws Exception {
        String tc_name = caseInfo.getTcname();
        String project_id = caseInfo.getPid();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tc_name", tc_name);
        map.put("project_id", project_id);
        int priority = caseInfo.getPriority();
        String remark = caseInfo.getCasedescription();
        try {
            delOperateStep(map);
            delResultVerify(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean flag = true;
        Map<String, Object> map_tc = new HashMap<String, Object>();
        Map<String, Object> map_operate_step = new HashMap<String, Object>();
        Map<String, Object> map_verify_step = new HashMap<String, Object>();
        List<OperateStep> operateSteps = caseInfo.getData();
        List<ResultVerify> resultVerifies = caseInfo.getResult();
        map_tc.put("tc_name", tc_name);
        map_tc.put("project_id", project_id);
        map_tc.put("priority", priority);
        map_tc.put("remark", remark);
        int save_edit_tc = updateEditTestCase(map_tc);
        if (save_edit_tc < 0) {
            flag = false;
        }
        long tc_step_number = getTcStepNumber(map_tc);
        for (OperateStep step : operateSteps) {
            map_operate_step.put("tc_step", tc_step_number);
            map_operate_step.put("command", step.getCommand());
            map_operate_step.put("target", step.getTarget());
            map_operate_step.put("elementvalue", step.getElementvalue());
            map_operate_step.put("meno", step.getMeno());
            map_operate_step.put("screamshot", step.isScreamshot());
            int save_step_result = newTestStep(map_operate_step);

            if (save_step_result < 0) {
                flag = false;
            }
        }
        for (ResultVerify verify : resultVerifies) {
            map_verify_step.put("result_verify", tc_step_number);
            map_verify_step.put("command", verify.getCommand());
            map_verify_step.put("target", verify.getTarget());
            map_verify_step.put("elementvalue", verify.getElementvalue());
            map_verify_step.put("property", verify.getProperty());
            map_verify_step.put("paramater", verify.getParamater());
            map_verify_step.put("expectedvalue", verify.getExpectedvalue());
            int save_verify_result = newVerifyStep(map_verify_step);
            if (save_verify_result < 0) {
                flag = false;
            }
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (flag) {
            resultMap.put("success", "true");
            return resultMap;
        } else {
            resultMap.put("success", "false");
            return resultMap;
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public ModelAndView editTestCase(String tc_id)
    {
        TestCaseStep testCaseStep = new TestCaseStep();
        TestCase testCase = getTestCaseOperate(Integer.parseInt(tc_id));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tc_name", testCase.getTc_name());
        map.put("project_id", testCase.getProject_id());
        testCaseStep.setTcname(testCase.getTc_name());
        testCaseStep.setPid(Integer.toString(testCase.getProject_id()));
        testCaseStep.setOperateStep(getTestStep(map));
        testCaseStep.setResult(getVerifyStep(map));
        testCaseStep.setCasedescription(testCase.getRemark());
        testCaseStep.setPriority(testCase.getPriority());
        JSONObject jsonTestCaseStep = JSONObject.fromObject(testCaseStep);
        System.out.print(jsonTestCaseStep.toString());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("caseInfo", jsonTestCaseStep);
        modelAndView.setViewName("edit_test_case");
        return modelAndView;
    }
    @Transactional(rollbackFor = Exception.class)
    public String runTestCase(String tc_name,String project_id,String project_name,HttpServletRequest request)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tc_name", tc_name);
        map.put("project_id", Integer.parseInt(project_id));
        Project project = getProjectInfo(Integer.parseInt(project_id));
        if (project.getDeviceName() == null || project.getDeviceName().equals("")) {
            return "please go home page to set device information!!!";
        } else {
            WebDriverFactory.browserName = project.getBrowserName();
            WebDriverFactory.platForm = project.getDeviceName();
            WebDriverFactory.pVersion = project.getpVersion();
            WebDriverFactory.deviceName = project.getDeviceName();
        }
        TestCase_ETC.operateSteps = getTestStep(map);
        TestCase_ETC.resultVerifies = getVerifyStep(map);
        TestCase_ETC.tc_name = tc_name;
        String[] path = GetDirUtil.getRootDir(request).split("/");
        String dir = GetDirUtil.getSelectRootDir(request, 2);
        GetDirUtil.dir = GetDirUtil.getRootDir(request) + "/ETC/" + project_name + "/Log/";
        TestNG testNG = new TestNG();
        List<String> suites = new ArrayList<String>();
        HTMLReporter htmlReporter=new HTMLReporter();
        JUnitXMLReporter jUnitXMLReporter=new JUnitXMLReporter();
        RetryTestListener retryTestListener=new RetryTestListener();
        testNG.addListener(htmlReporter);
        testNG.addListener(jUnitXMLReporter);
        testNG.addListener(retryTestListener);
        testNG.setDefaultTestName(tc_name);
        testNG.setTestClasses(new Class[]{TestCase_ETC.class});
        String outputPath = dir + "target/ETC_Project/ETC/" + project_name + "/Reports";
        boolean c;
        do {
            c = MakeDirUtil.makeDirs(outputPath);
        } while (c == false);
        testNG.setOutputDirectory(outputPath);
        testNG.run();
        return "TestCase run finished!";
    }
    @Transactional(rollbackFor = Exception.class)
    public String suiteRunTestCase(String project_id,String project_name,HttpServletRequest request) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Project project =getProjectInfo(Integer.parseInt(project_id));
        if (project.getDeviceName() == null || project.getDeviceName().equals("")) {
            return "please go home page to set device information!!!";
        } else {
            WebDriverFactory.browserName = project.getBrowserName();
            WebDriverFactory.platForm = project.getDeviceName();
            WebDriverFactory.pVersion = project.getpVersion();
            WebDriverFactory.deviceName = project.getDeviceName();
        }
        String libDir = ".;" + GetDirUtil.getRootDir(request) + "WEB-INF/lib/*";
        String destDir = GetDirUtil.getRootDir(request) + "WEB-INF/classes";
        String sourceDir = GetDirUtil.getSelectRootDir(request,2) + "src/main/java/ETC/" + project_name + "/TestCase/source.txt";
        try {
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
            boolean compilerResult = false;
            compilerResult = DynamicCompiler.dynamicCompiler(libDir, sourceDir, destDir);
            if (compilerResult) {
                logger.info("compile success!");
            } else {
                logger.error("compile failed!");
                for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {

                    System.out.println(diagnostic.getMessage(null));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String classDir = destDir + "/ETC/" + project_name + "/TestCase/";
        List<String> classListNames = new ArrayList<String>();
        classListNames =getTestCaseByProjectId(project.getProject_id());
        Method setTc_name;
        int classtest =0;
        Class[] testclass = new Class[classListNames.size()];
        ParentClassLoad fileClsLoader = null;
        for (String fileName : classListNames) {
            String className = "ETC." + project_name + ".TestCase." + fileName;
            fileClsLoader= new ParentClassLoad(destDir+"/ETC/" + project_name + "/TestCase/"+fileName+".class");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("tc_name", fileName);
            map.put("project_id", Integer.parseInt(project_id));
            Class<?> clazz = fileClsLoader.loadClass("ETC." + project_name + ".TestCase." + fileName);
            System.out.println(System.getProperty("java.class.path")+"\n");
            setTc_name = clazz.getMethod("setTc_name", String.class);
            Method getTc_name = clazz.getMethod("getTc_name");
            Method getOperateSteps = clazz.getMethod("getOperateSteps");
            Method getResultVerifies = clazz.getMethod("getResultVerifies");
            Method setResultVerifies = clazz.getMethod("setResultVerifies", List.class);
            Method setOperateSteps = clazz.getMethod("setOperateSteps", List.class);
            setTc_name.invoke(clazz, fileName);
            setOperateSteps.invoke(clazz,getTestStep(map));
            setResultVerifies.invoke(clazz,getVerifyStep(map));
            logger.info("class load successful" + getTc_name.invoke(clazz));
            testclass[classtest]=clazz;
            classtest++;
        }
        TestNG testNG = new TestNG();
        HTMLReporter htmlReporter=new HTMLReporter();
        JUnitXMLReporter jUnitXMLReporter=new JUnitXMLReporter();
        RetryTestListener retryTestListener=new RetryTestListener();
        testNG.addListener(htmlReporter);
        testNG.addListener(jUnitXMLReporter);
        testNG.addListener(retryTestListener);
        testNG.setTestClasses(testclass);
        String outputPath = GetDirUtil.getRootDir(request) + "ETC/" + project_name + "/Reports/SuiteReports";
        boolean c;
        do {
            c = MakeDirUtil.makeDirs(outputPath);
        } while (c == false);
        testNG.setOutputDirectory(outputPath);
        testNG.run();
        return "suite run successful!";
    }
    @Transactional(rollbackFor = Exception.class)
    public Map saveTestCase(TestCaseStep caseInfo,HttpServletRequest request) throws Exception {
        String tc_name = caseInfo.getTcname();
        String project_id = caseInfo.getPid();
        int priority = caseInfo.getPriority();
        String remark = caseInfo.getCasedescription();
        long nowTime = System.currentTimeMillis() / 1000;
        Map<String, Object> map_tc = new HashMap<String, Object>();
        Map<String, Object> map_operate_step = new HashMap<String, Object>();
        Map<String, Object> map_verify_step = new HashMap<String, Object>();
        List<OperateStep> operateSteps = caseInfo.getData();
        List<ResultVerify> resultVerifies = caseInfo.getResult();
        map_tc.put("tc_name", tc_name);
        map_tc.put("project_id", project_id);
        map_tc.put("priority", priority);
        map_tc.put("tc_step", nowTime);
        map_tc.put("result_verify", nowTime);
        map_tc.put("remark", remark);
        boolean flag = true;
        int save_tc_result = newTestCase(map_tc);
        for (OperateStep step : operateSteps) {
            map_operate_step.put("tc_step", nowTime);
            map_operate_step.put("command", step.getCommand());
            map_operate_step.put("target", step.getTarget());
            map_operate_step.put("elementvalue", step.getElementvalue());
            map_operate_step.put("meno", step.getMeno());
            map_operate_step.put("screamshot", step.isScreamshot());
            int save_step_result = newTestStep(map_operate_step);
            if (save_step_result < 0) {
                flag = false;
            }
        }
        for (ResultVerify verify : resultVerifies) {
            map_verify_step.put("result_verify", nowTime);
            map_verify_step.put("command", verify.getCommand());
            map_verify_step.put("target", verify.getTarget());
            map_verify_step.put("elementvalue", verify.getElementvalue());
            map_verify_step.put("property", verify.getProperty());
            map_verify_step.put("paramater", verify.getParamater());
            map_verify_step.put("expectedvalue", verify.getExpectedvalue());
            int save_verify_result = newVerifyStep(map_verify_step);
            if (save_verify_result < 0) {
                flag = false;
            }
        }
        Project project = getProjectInfo(Integer.parseInt(project_id));
        String sdir = GetDirUtil.getSelectRootDir(request, 1);
        String testCasePath = GetDirUtil.getSelectRootDir(request,2) + "src/main/java/ETC/" + project.getProject_name() + "/TestCase/";
        String reuserpath = "src/main/java/";
        CopyFileUtil.copyFile(GetDirUtil.getSelectRootDir(request, 2) + reuserpath + "com/etc/spring/testcase/TestCase_ETC.java", testCasePath + tc_name + ".java", true);
        ModifyFile.modifyString(testCasePath + tc_name + ".java", "TestCase_ETC", tc_name);
        ModifyFile.modifyString(testCasePath + tc_name + ".java", "com.etc.spring.testcase", "ETC." + project.getProject_name() + ".TestCase");
        //ModifyFile.addLine(testCasePath + project.getProject_name() + ".xml", "<classes>", "<class name=\"ETC." + project.getProject_name() + ".TestCase." + tc_name + "\"/>");
        ModifyFile.addLine(testCasePath + "source.txt", "BaseElementPropertyMethod", testCasePath + tc_name + ".java");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (save_tc_result > 0 && flag) {
            resultMap.put("success", "true");
            return resultMap;
        } else {
            resultMap.put("success", "false");
            return resultMap;
        }

    }
    @Transactional(rollbackFor = Exception.class)
    public ModelAndView getCasesByProjectName(HttpServletRequest request,ModelMap model) {
        String project_name = request.getParameter("project_name");
        //获取总条数
        Long totalCount = getCaseCountByProjectName(project_name);
        System.out.print(totalCount + "");
        ModelAndView mv = new ModelAndView();
        //设置分页对象
        if (totalCount != 0) {
            Page page = BaseController.executePage(request, totalCount);
            model.put("startIndex", page.getBeginIndex());
            model.put("endIndex", page.getEndIndex());
            model.put("project_name", project_name);
            System.out.print(page.getBeginIndex() + page.getEndIndex());
            //   List<Project> projects = this.getProjectService().getProjectList();
            List<TestCase> testCases = getCaseByProjectName(model);
            System.out.print(testCases.size());
            mv.addObject("testCaseList", testCases);
            mv.addObject("project_name", project_name);
        } else {
            mv.addObject("testCaseList", "");
        }
        mv.setViewName("search_case_by_pname");
        return mv;

    }
    @Transactional(rollbackFor = Exception.class)
    public ModelAndView getCaseByProjectName(String project_name,HttpServletRequest request,ModelMap model)
    {
        //获取总条数
        Long totalCount = getCaseCountByProjectName(project_name);
        ModelAndView mv = new ModelAndView();

        //设置分页对象
        if (totalCount != 0) {
            Page page = BaseController.executePage(request, totalCount);
            System.out.print(page.getBeginIndex() + page.getEndIndex());
            model.put("startIndex", page.getBeginIndex());
            model.put("endIndex", page.getEndIndex());
            model.put("project_name", project_name);
            //   List<Project> projects = this.getProjectService().getProjectList();
            List<TestCase> testCases = getCaseByProjectName(model);
            mv.addObject("testCaseList", testCases);
            mv.addObject("project_name", project_name);
        } else {
            mv.addObject("testCaseList", "");
        }
        mv.setViewName("search_case_by_pname");
        return mv;
    }
    @Transactional(rollbackFor = Exception.class)
    public ModelAndView testCaseList(int project_id,String project_name,HttpServletRequest request,ModelMap model)
    {
        //获取总条数
        long totalCount = testCaseCounts(project_id);
        ModelAndView mv = new ModelAndView();

        //设置分页对象
        if (totalCount != 0) {
            Page page = BaseController.executePage(request, totalCount);
            System.out.print(page.getBeginIndex() + page.getEndIndex());
            model.put("startIndex", page.getBeginIndex());
            model.put("endIndex", page.getEndIndex());
            model.put("project_id", project_id);
            //   List<Project> projects = this.getProjectService().getProjectList();
            List<TestCase> testCases = testCaseList(model);
            mv.addObject("testCaseList", testCases);
            mv.addObject("project_id", project_id);
        } else {
            mv.addObject("testCaseList", "");
        }
        mv.getModel().put("project_id", project_id);
        mv.getModel().put("project_name", project_name);
        mv.setViewName("test_case_list");
        return mv;
    }
    @Transactional(rollbackFor = Exception.class)
    public String deleteTestCase(int tc_id,String project_name,String tc_name,HttpServletRequest request) throws Exception {
        int a = delTestCase(tc_id);
        String source=GetDirUtil.getSelectRootDir(request,2) + "src/main/java/ETC/" + project_name + "/TestCase/source.txt";
        ModifyFile.deleteLine(source,project_name+"/TestCase/"+tc_name);
//        ModelAndView mv = new ModelAndView();
//        mv.setViewName();
        if (a > 0) {
            return "delete success!";
        } else {
            return "delete failed!";
        }
    }
}
