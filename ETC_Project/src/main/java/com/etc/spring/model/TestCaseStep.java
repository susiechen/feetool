package com.etc.spring.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by none.none on 2017/2/24.
 */
public class TestCaseStep implements Serializable{
    private String tcname;
    private String pid;
    private int  priority;
    private List<OperateStep> data;
    private List<ResultVerify> result;
    private String casedescription;

    public String getCasedescription() {
        return casedescription;
    }
    public void setCasedescription(String casedescription) {
        this.casedescription = casedescription;
    }
    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public List<ResultVerify> getResult() {
        return result;
    }

    public void setResult(List<ResultVerify> result) {
        this.result = result;
    }

    public String getTcname() {
        return tcname;
    }

    public void setTcname(String tcname) {
        this.tcname = tcname;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<OperateStep> getData() {
        return data;
    }

    public void setOperateStep(List<OperateStep> data) {
        this.data =data;
    }

    @Override
    public String toString() {
        return "TestCaseStep{" +
                "tcname='" + tcname + '\'' +
                ", pid='" + pid + '\'' +
                ", priority=" + priority +
                ", data=" + data +
                ", result=" + result +
                ", casedescription='" + casedescription + '\'' +
                '}';
    }
}
