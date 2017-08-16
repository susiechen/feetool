package com.etc.spring.model;

import java.io.Serializable;

/**
 * Created by none.none on 2017/2/24.
 */
public class OperateStep implements Serializable {

    private long tc_step_id;
    private long tc_step;
    private String command;
    private String target;
    private String elementvalue;
    private String meno;
    private boolean screamshot;


    public long getTc_step() {
        return tc_step;
    }

    public void setTc_step(long tc_step) {
        this.tc_step = tc_step;
    }

    public boolean isScreamshot() {
        return screamshot;
    }

    public void setScreamshot(boolean screamshot) {
        this.screamshot = screamshot;
    }

    public String getTarget() {

        return target;
    }


    public long getTc_step_id() {
        return tc_step_id;
    }

    public void setTc_step_id(long tc_verify_id) {
        this.tc_step_id = tc_verify_id;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getElementvalue() {
        return elementvalue;
    }

    public void setElementvalue(String elementvalue) {
        this.elementvalue = elementvalue;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getMeno() {
        return meno;
    }


    @Override
    public String toString() {
        return "OperateStep{" +
                "tc_verify_id=" + tc_step_id +
                ", command='" + command + '\'' +
                ", target='" + target + '\'' +
                ", elementvalue='" + elementvalue + '\'' +
                ", meno='" + meno + '\'' +
                ", screamshot=" + screamshot +
                '}';
    }
}
