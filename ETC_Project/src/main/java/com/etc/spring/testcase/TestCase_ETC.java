package com.etc.spring.testcase;
import com.etc.spring.core.BasePageAction;
import com.etc.spring.core.BaseResultAssertMethod;
import com.etc.spring.core.WebDriverFactory;
import com.etc.spring.model.OperateStep;
import com.etc.spring.model.Project;
import com.etc.spring.model.ResultVerify;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import java.util.*;

/**
 * Created by none.none on 2017/3/15.
 */
public class TestCase_ETC {
    //public static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TestCase_ETC.class);
    public static List<OperateStep> operateSteps=new ArrayList<>();
    public static List<ResultVerify> resultVerifies=new ArrayList<>();
    public static String tc_name="";

    public static List<ResultVerify> getResultVerifies() {
        return resultVerifies;
    }

    public static void setResultVerifies(List<ResultVerify> resultVerifies) {
        TestCase_ETC.resultVerifies = resultVerifies;
    }

    public static List<OperateStep> getOperateSteps() {
        return operateSteps;
    }

    public static void setOperateSteps(List<OperateStep> operateSteps) {
        TestCase_ETC.operateSteps = operateSteps;
    }

    public static String getTc_name() {
        return tc_name;
    }

    public static void setTc_name(String tc_name) {
        TestCase_ETC.tc_name = tc_name;
    }
    public TestCase_ETC()
    {}
    public TestCase_ETC(List<OperateStep> operateSteps, List<ResultVerify> resultVerifies) {
        this.operateSteps = operateSteps;
        this.resultVerifies = resultVerifies;
    }

    @Test
    public void test() {
        for (OperateStep operateStep : operateSteps) {
            BasePageAction.getPageAction(operateStep.getCommand(), operateStep.getTarget(), operateStep.getElementvalue(), operateStep.getMeno(), tc_name);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (ResultVerify resultVerify : resultVerifies) {
//          BaseResultAssertMethod.getAssertCommand("AssertTrue", "id", "weather", "isDisplayed", null, "true");
            BaseResultAssertMethod.getAssertCommand(resultVerify.getCommand(), resultVerify.getTarget(), resultVerify.getElementvalue(), resultVerify.getProperty(), resultVerify.getParamater(), resultVerify.getExpectedvalue());
        }

    }

    @AfterTest
    public void tearDown() throws Exception {
        WebDriverFactory.driverQuit();
        //logger.info("执行完毕!");
    }
}
