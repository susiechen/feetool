package com.etc.spring.web;

import com.etc.spring.model.Page;
import com.etc.spring.model.Project;
import com.etc.spring.service.ProjectService;
import com.etc.spring.util.*;
import com.sun.javafx.sg.prism.NGShape;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * Created by none.none on 2017/2/13.
 */
@Controller
@SessionAttributes("projectList")
public class ProjectController extends BaseController {
    private ProjectService projectService;

    public ProjectService getProjectService() {
        return projectService;
    }

    @Resource
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping(value = "/index.html")
    public String loginPage()
    {
        return "redirect:/projectlist.do";
    }

    @RequestMapping(value="/projectlist.do" ,produces = "application/json;charset=UTF-8")
    @Async
    @ResponseBody
    public ModelAndView projectList(HttpServletRequest request, ModelMap model) {
        return this.getProjectService().getProjectList(request,model);

    }

    @RequestMapping(value = "/delProject/{project_id}")
    @ResponseBody
    @Async
    public String delProject(@PathVariable("project_id") int project_id) throws Exception {
        int a = this.getProjectService().delProject(project_id);
        if (a > 0) {
            return "delete success!";
        } else {
            return "delete failed!";
        }
    }

    @RequestMapping(value = "/updateProject/project_name/{project_name}/pid/{project_id}")
    @ResponseBody
    @Async
    public String updateProject(@PathVariable("project_name") String project_name,
                                @PathVariable("project_id") int project_id) throws Exception {
        int a = this.getProjectService().updateProject(new Project(project_id, project_name));
        if (a > 0) {
            return "update success!";
        } else {
            return "update failed!";
        }
    }

    @RequestMapping(value = "/updateDevices/project_name/{project_name}/pid/{project_id}/platformName/{platformName}/deviceName/{deviceName}/pVersion/{pVersion}/browserName/{browserName}")
    @ResponseBody
    @Async
    public String updateDevices(@PathVariable("project_name") String project_name,
                                @PathVariable("project_id") int project_id,
                                @PathVariable("platformName") String platformName,
                                @PathVariable("deviceName") String deviceName,
                                @PathVariable("pVersion") String pVersion,
                                @PathVariable("browserName") String browserName
    ) throws Exception {

        int a = this.getProjectService().updateDevices(new Project(project_id, project_name, platformName, deviceName, pVersion, browserName));
//        int a = this.getProjectService().updateDevices(project_id,platformName,deviceName,pVersion,browserName);

        if (a > 0) {
            System.out.println("" + a);
            return "update success!";
        } else {
            System.out.println("" + a);
            return "update failed!";
        }
    }

    @RequestMapping("/searchDevices/project_id/{project_id}")
    @ResponseBody
    @Async
    public Project searchDevices(@PathVariable("project_id") String project_id,ModelMap model)
    {
          int pid=Integer.parseInt(project_id);
          Project project=this.getProjectService().searchDevices(pid);
      //    System.out.println(project.getbrowserName() + "  " + project.getdeviceName() + " " + project.getplatformName() + " " + project.getpVersion());
        //  mv.addObject("devicesObj", project);
          return project;
    }

    @RequestMapping("/searchProject.do")
    @Async
    public ModelAndView selectProject(HttpServletRequest request, ModelMap model) {
        return this.getProjectService().selectProject(request,model);

    }

    @RequestMapping("/searchProject/{project_name}")
    @Async
    public ModelAndView selectProjectByName(@PathVariable("project_name") String project_name, HttpServletRequest request, ModelMap model) {
        return this.getProjectService().selectProjectByName(project_name,request,model);


    }

    @RequestMapping(value = "/addProject/project_name/{project_name}")
    @ResponseBody
    @Async
    public String addProject(@PathVariable("project_name") String project_name,HttpServletRequest request) throws Exception {
        return this.getProjectService().addProject(project_name,request);

    }

    @RequestMapping("/verifyProject/{project_id}")
    @ResponseBody
    @Async
    public boolean searchProjectIsExist(@PathVariable("project_id") String project_id, HttpServletRequest request, ModelMap model) {
        int count = this.getProjectService().searchProjectIsExist(Long.parseLong(project_id));
        if (count == 0) {
            return false;
        } else {
            return true;
        }
    }
}
