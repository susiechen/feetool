package com.etc.spring.web;

import com.etc.spring.model.*;
import com.etc.spring.service.TestCaseService;
import com.etc.spring.util.*;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by none.none on 2017/2/13.
 */
@Controller
@SessionAttributes("testCaseList")
public class TestCaseController extends BaseController {
    public static Logger logger = Logger.getLogger(TestCaseController.class);
    private TestCaseService testCaseService;

    public TestCaseService getTestCaseService() {
        return testCaseService;
    }

    @Resource
    public void setTestCaseService(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @RequestMapping(value = "/testCaseList/project_id/{project_id}/project_name/{project_name}")
    @Async
    public ModelAndView testCaseList(@PathVariable("project_id") int project_id, @PathVariable("project_name") String project_name, HttpServletRequest request, ModelMap model) {

        return this.getTestCaseService().testCaseList(project_id,project_name,request,model);

    }

    @RequestMapping(value = "/delTestCase/{tc_id}/project_name/{project_name}/tcName/{tc_name}")
    @ResponseBody
    @Async
    public String delTestCase(@PathVariable("tc_id") int tc_id,@PathVariable("project_name") String project_name,@PathVariable("tc_name") String tc_name ,HttpServletRequest request, HttpServletResponse response) throws Exception {
        return this.getTestCaseService().deleteTestCase(tc_id,project_name,tc_name,request);


    }

    @RequestMapping(value = "/updateTestCase/tc_name/{tc_name}/priority/{priority}/id/{tc_id}")
    @ResponseBody
    @Async
    public String updateTestCase(@PathVariable("tc_name") String tc_name, @PathVariable("priority") int priority, @PathVariable("tc_id") int tc_id) throws Exception {

        int a = this.getTestCaseService().updateTestCase(new TestCase(tc_name, priority, tc_id));
        if (a > 0) {
            return "update success!";
        } else {
            return "update failed!";
        }
    }

    @RequestMapping(value = "/getCaseByProjectName/project_name/{project_name}")
    public ModelAndView getCaseByprojectName(@PathVariable("project_name") String project_name, HttpServletRequest request, ModelMap model) {
        return this.getTestCaseService().getCaseByProjectName(project_name,request,model);

    }

    @RequestMapping(value = "/getCaseByProjectName.do")
    @Async
    public ModelAndView getCaseByProjectName(HttpServletRequest request, ModelMap model) {
        return  this.getTestCaseService().getCasesByProjectName(request,model);
    }

    @RequestMapping(value = "/newTestCase/project_id/{project_id}")
    @Async
    public ModelAndView newTestCase(@PathVariable("project_id") String project_id) {

        ModelAndView mv = new ModelAndView();
        mv.addObject("project_id", project_id);
        mv.setViewName("new_test_case");
        return mv;
    }

    @RequestMapping(value = "/saveTestCase.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @Async
    public Map<String, Object> saveTestCase(@RequestBody TestCaseStep caseInfo, HttpServletRequest request) throws Exception {
        return this.getTestCaseService().saveTestCase(caseInfo,request);

    }

    @RequestMapping(value = "/suiteRunTestCase/project_id/{project_id}/project_name/{project_name}")
    @ResponseBody
    @Async
    public String runTestCase(@PathVariable("project_id") String project_id, @PathVariable("project_name") String project_name, ModelMap model, HttpServletRequest request) throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchFieldException {
        return this.getTestCaseService().suiteRunTestCase(project_id,project_name,request);


    }

    @RequestMapping(value = "/runTestCase/tc_name/{tc_name}/project_id/{project_id}/project_name/{project_name}")
    @Async
    public String runTestCase(@PathVariable("tc_name") String tc_name, @PathVariable("project_id") String project_id, @PathVariable("project_name") String project_name, ModelMap model, HttpServletRequest request) {
       return this.getTestCaseService().runTestCase(tc_name,project_id,project_name,request);


    }

    @RequestMapping(value = "/editTestCase/tc_id/{tc_id}", produces = "application/json;charset=UTF-8")
    @Async
    public ModelAndView editTestCase(@PathVariable("tc_id") String tc_id) {
        return this.getTestCaseService().editTestCase(tc_id);


    }

    @RequestMapping(value = "/saveEditTestCase.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @Async
    public Map<String, Object> saveEditTestCase(@RequestBody TestCaseStep caseInfo) throws Exception {
        return this.getTestCaseService().saveEditTestCase(caseInfo);

    }
}
