package com.etc.spring.service;

import com.etc.spring.dao.ProjectMapper;
import com.etc.spring.model.Page;
import com.etc.spring.model.Project;
import com.etc.spring.util.BaseController;
import com.etc.spring.util.CopyFileUtil;
import com.etc.spring.util.GetDirUtil;
import com.etc.spring.util.MakeDirUtil;
import com.fasterxml.jackson.databind.deser.Deserializers;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by none.none on 2017/2/13.
 */
@Service("projectService")
@Transactional(rollbackFor = Exception.class)
public class ProjectService {
    @Autowired
    private ProjectMapper projectMapper;

    public synchronized List<Project> getProjectList() {
        return (List<Project>) this.projectMapper.getProjectList();
    }

    public synchronized Long pageCounts() {
        return this.projectMapper.pageCounts();
    }

    public synchronized List<Project> pageList() {
        return this.projectMapper.pageList();
    }

    public synchronized  int delProject(int projectId)throws Exception
    {
       return  this.projectMapper.delProject(projectId);
    }
    public synchronized int  updateProject(Project p )throws Exception
    {
        return this.projectMapper.updateProject(p);
    }

    public synchronized int  updateDevices(Project p)throws Exception
    {
        return this.projectMapper.updateDevices(p);
    }

    public synchronized Project searchDevices(int ProjectId)
    {
        return  this.projectMapper.searchDevices(ProjectId);
    }

    public synchronized List<Project> searchProject(Map<String,Object> params)
    {
        return  this.projectMapper.searchProject(params);
    }
    public synchronized  Long searchProjectCounts(String projectName)
    {
        return  this.projectMapper.searchProjectCounts(projectName);
    }
    public synchronized int addProject(String projectName)throws Exception{
       return this.projectMapper.addProject(projectName);

    }
    public synchronized int searchProjectIsExist(long project_id)
    {
        return this.projectMapper.searchProjectIsExist(project_id);
    }
    public synchronized int verifyProjectNameIsExist(String project_name){
        return this.projectMapper.verifyProjectNameIsExist(project_name);
    }
    @Transactional(rollbackFor = Exception.class)
    public String addProject(String project_name,HttpServletRequest request) throws Exception {
        int a=verifyProjectNameIsExist(project_name);
        String dir = GetDirUtil.getRootDir(request);
        String logPath=dir+"/ETC/"+project_name+"/Log";
        String sdir=GetDirUtil.getSelectRootDir(request,1);
        String testCasePath=GetDirUtil.getSelectRootDir(request,2)+"src/main/java/ETC/"+project_name+"/TestCase/";
        String reportPath=dir+"ETC/"+project_name+"/Reports";
        String suitReportPath=dir+"ETC/"+project_name+"/Reports/SuiteReports";
        String casePath=dir+"src/main/java/com/etc/spring/testcase";
        String reuserpath="src/main/java/";
        if(a==0) {
            int b= addProject(project_name);
            boolean c;
            do {
                c= MakeDirUtil.makeDirs(logPath);
            }while(c==false);
            do {
                c=MakeDirUtil.makeDirs(testCasePath);
            }while (c==false);
            do{
                c=MakeDirUtil.makeDirs(reportPath);
            }while(c==false);
            do{
                c=MakeDirUtil.makeDirs(suitReportPath);
            }while(c==false);

            //CopyFileUtil.copyDirectory(GetDirUtil.getSelectRootDir(request,2)+"/src", GetDirUtil.getRootDir(request)+"/src", true);
            //CopyFileUtil.copyFile(GetDirUtil.getSelectRootDir(request,2)+reuserpath+"com/etc/spring/testcase/TestSuit_ETC.xml",testCasePath+project_name+".xml",true);
            //ModifyFile.modifyString(testCasePath + project_name + ".xml", "ETC_TEST", project_name);
            //ModifyFile.deleteLine(testCasePath+project_name+".xml","com.etc.spring.testcase.TestCase_ETC");
            CopyFileUtil.copyFile(GetDirUtil.getSelectRootDir(request, 2) + "src/main/resources/config/source.txt", testCasePath + "source.txt", true);
            if (b > 0) {
                return "insert success!";
            } else {
                return "insert failed!";
            }
        }else
        {
            return "the project name is exist!";
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public ModelAndView selectProjectByName(String project_name,HttpServletRequest request,ModelMap model)
    {
        ModelAndView mv = new ModelAndView();
        Long totalCount = searchProjectCounts(project_name);
        //设置分页对象
        if (totalCount != 0) {
            Page page = BaseController.executePage(request, totalCount);
            model.put("startIndex", page.getBeginIndex());
            model.put("endIndex", page.getEndIndex());
            model.put("project_name", project_name);
            List<Project> projects = searchProject(model);
            mv.addObject("projectList", projects);
        } else {
            mv.addObject("projectList", "");
        }
        mv.setViewName("search_project_list");
        return mv;
    }
    @Transactional(rollbackFor = Exception.class)
    public ModelAndView selectProject(HttpServletRequest request,ModelMap model)
    {
        ModelAndView mv = new ModelAndView();
        String project_name = request.getParameter("project_name");
        Long totalCount = searchProjectCounts(project_name);
        //设置分页对象
        if (totalCount != 0) {
            Page page = BaseController.executePage(request, totalCount);
            model.put("startIndex", page.getBeginIndex());
            model.put("endIndex", page.getEndIndex());
            model.put("project_name", project_name);
            List<Project> projects = searchProject(model);
            mv.addObject("projectList", projects);
        } else {
            mv.addObject("projectList", "");
        }
        mv.setViewName("search_project_list");
        return mv;

    }
    @Transactional(rollbackFor = Exception.class)
    public ModelAndView getProjectList(HttpServletRequest request,ModelMap model)
    {
 /*       ModelAndView mv = new ModelAndView();
        //获取总条数
        Long totalCount = pageCounts();
        //设置分页对象
        if (totalCount != 0) {
            Page page = BaseController.executePage(request, totalCount);
            model.put("startIndex", page.getBeginIndex());
            model.put("endIndex", page.getEndIndex());
            List<Project> projects = pageList(model);
            mv.addObject("projectList", projects);
        } else {
            mv.addObject("projectList", "");
        }
        GetDirUtil.isFirst++;
        if(GetDirUtil.isFirst==1) {
            String path = GetDirUtil.getSelectRootDir(request, 2) + "/src/main/resources/config";
            MakeDirUtil.makeFile(path, "source.txt");
            File f = new File(GetDirUtil.getSelectRootDir(request, 2) + "src/main/java/com/etc/spring/");
            MakeDirUtil.listMyFiles(f, path + "/source.txt");
        }
        mv.setViewName("project_list");
        return mv;*/
        ModelAndView modelAndView = new ModelAndView();
        //获取总条数
        Long totalCount = pageCounts();
        //设置分页对象
        if (totalCount != 0) {
            List<Project> projects = pageList();
            JSONArray jsonProjects = JSONArray.fromObject(projects.toArray());
            System.out.print(jsonProjects.toString());
            modelAndView.addObject("projects", jsonProjects);
        }else {
            modelAndView.addObject("projects", "");
        }
        GetDirUtil.isFirst++;
        if(GetDirUtil.isFirst==1) {
            String path = GetDirUtil.getSelectRootDir(request, 2) + "/src/main/resources/config";
            MakeDirUtil.makeFile(path, "source.txt");
            File f = new File(GetDirUtil.getSelectRootDir(request, 2) + "src/main/java/com/etc/spring/");
            MakeDirUtil.listMyFiles(f, path + "/source.txt");
        }
        modelAndView.setViewName("project_list");
        return modelAndView;
    }
}
