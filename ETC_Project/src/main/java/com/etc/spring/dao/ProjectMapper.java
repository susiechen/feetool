package com.etc.spring.dao;

import com.etc.spring.model.Project;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by none.none on 2017/2/14.
 */
@Repository
public interface ProjectMapper {
    public  List<Project>  getProjectList();
    public  List<Project> pageList();
    public Long pageCounts();
    public  int delProject(int projectId);
    public  int updateProject(Project p);
    // update devices
    public int updateDevices(Project p);
    public List<Project> searchProject(Map<String, Object> params);
    public Long searchProjectCounts(String projectName);
    public int addProject(String projectName);
    public int searchProjectIsExist(long project_id);
    public int verifyProjectNameIsExist(String project_name);
    public Project searchDevices(int project_id);
}
