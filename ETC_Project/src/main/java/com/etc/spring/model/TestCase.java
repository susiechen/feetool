package com.etc.spring.model;

import java.io.Serializable;

/**
 * Created by none.none on 2017/2/13.
 */
public class TestCase implements Serializable {
    private  int tc_id;
    private  int project_id;
    private String tc_name;
    private int priority;
    private long tc_step;
    private long result_verify;
    private String remark;
    @Override
    public String toString() {
        return "TestCase{" +
                "tc_id=" + tc_id +
                ", project_id=" + project_id +
                ", tc_name='" + tc_name + '\'' +
                ", priority=" + priority +
                ", tc_step='" + tc_step + '\'' +
                ", result_verify='" + result_verify + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
    public TestCase()
    {
    }
    public TestCase(String tc_name,int priority,int tc_id)
    {
        this.tc_name=tc_name;
        this.priority=priority;
        this.tc_id=tc_id;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getResult_verify() {
        return result_verify;
    }

    public void setResult_verify(long result_verify) {
        this.result_verify = result_verify;
    }

    public int getTc_id() {
        return tc_id;
    }

    public void setTc_id(int tc_id) {
        this.tc_id = tc_id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getTc_name() {
        return tc_name;
    }

    public void setTc_name(String tc_name) {
        this.tc_name = tc_name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getTc_step() {
        return tc_step;
    }

    public void setTc_step(long tc_step) {
        this.tc_step = tc_step;
    }
}
