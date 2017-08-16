package com.etc.spring.model;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by none.none on 2017/2/13.
 */
@Component
public class Project implements Serializable {
    private int project_id;
    private String project_name;
    private String platformName;
    private String deviceName;
    private String pVersion;
    private String browserName;

    public Project(){}
    public Project(int project_id,String project_name,String platformName,String deviceName,String pVersion,String browserName)
    {
        this.project_id=project_id;
        this.project_name=project_name;
        this.platformName=platformName;
        this.deviceName=deviceName;
        this.pVersion=pVersion;
        this.browserName=browserName;
    }

    public Project(int project_id,String project_name)
    {
        this.project_id=project_id;
        this.project_name=project_name;

    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }
    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getpVersion() {
        return pVersion;
    }

    public void setpVersion(String pVersion) {
        this.pVersion = pVersion;
    }

    public String getBrowserName() {
        return browserName;
    }
}
