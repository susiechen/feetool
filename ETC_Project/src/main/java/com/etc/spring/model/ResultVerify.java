package com.etc.spring.model;

import java.io.Serializable;

/**
 * Created by none.none on 2017/2/27.
 */
public class ResultVerify implements Serializable {
    private long tc_verify_id;
    private long result_verify;
    private String command;
    private String target;
    private String elementvalue;
    private String property;
    private String expectedvalue;
    private String paramater;

    public long getResult_verufy() {
        return result_verify;
    }

    public void setResult_verufy(long result_verufy) {
        this.result_verify = result_verufy;
    }

    public String getParamater() {
        return paramater;
    }

    public void setParamater(String paramater) {
        this.paramater = paramater;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getExpectedvalue() {
        return expectedvalue;
    }

    public void setExpectedvalue(String expectedvalue) {
        this.expectedvalue = expectedvalue;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getElementvalue() {
        return elementvalue;
    }

    public void setElementvalue(String value) {
        this.elementvalue = value;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }


    public long getTc_verify_id() {
        return tc_verify_id;
    }

    public void setTc_verify_id(long tc_verify_id) {
        this.tc_verify_id = tc_verify_id;
    }

    @Override
    public String toString() {
        return "ResultVerify{" +
                "tc_verify_id=" + tc_verify_id +

                ", command='" + command + '\'' +
                ", target='" + target + '\'' +
                ", elementvalue='" + elementvalue + '\'' +
                ", property='" + property + '\'' +
                ", expectedvalue='" + expectedvalue + '\'' +
                ", paramater='" + paramater + '\'' +
                '}';
    }
}
