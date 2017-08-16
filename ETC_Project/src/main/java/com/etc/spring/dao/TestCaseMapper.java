package com.etc.spring.dao;

import com.etc.spring.model.OperateStep;
import com.etc.spring.model.Project;
import com.etc.spring.model.ResultVerify;
import com.etc.spring.model.TestCase;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by none.none on 2017/2/15.
 */
@Repository
public interface TestCaseMapper {
    public List<TestCase> testCaseList(Map<String, Object> params);
    public Long testCaseCounts(int project_id);
    public  int delTestCase(int tc_id);
    public int updateTestCase(TestCase tc);
    public List<TestCase> getCaseByProjectName(Map<String, Object> params);
    public Long getCaseCountByProjectName(String project_name);
    public TestCase getTestCaseOperate(int id);
    public int newTestCase(Map<String, Object> params);
    public int newTestStep(Map<String, Object> params);
    public int newVerifyStep(Map<String, Object> params);
    public List<OperateStep> getTestStep(Map<String, Object> params);
    public List<ResultVerify> getVerifyStep(Map<String, Object> params);
    public int delOperateStep(Map<String, Object> params);
    public int delResultVerify(Map<String, Object> params);
    public int updateEditTestCase(Map<String, Object> params);
    public long getTcStepNumber(Map<String, Object> params);
    public Project getProjectInfo(int project_id);
    public List<String> getTestCaseByProjectId(int project_id);
}

