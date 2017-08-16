package com.etc.spring.core;

import com.etc.spring.testcase.TestCase_ETC;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;

import java.io.File;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by none.none on 2017/3/7.
 */
public class BasePageAction {
    public static Logger logger = Logger.getLogger(BasePageAction.class);
    public static void getPageAction(String command,String locateWay,String locateValue,String meno,String tcname)
    {
        if(locateValue != null && !locateValue.equals("")) {
            WebElement element = BaseLocationMethod.findElement(locateWay, locateValue);
        }
        switch (command)
        {
            case "open":
                openUrl(meno,tcname);
                break;
            case "单击":
                click(locateWay,locateValue);
                break;
            case "滑动":
                break;
            case "等待":
                pause(Integer.parseInt(locateValue));
                break;
            case "清空内容":
                clear(locateWay,locateValue);
                break;
            case "输入文本":
                typeContent(locateWay,locateValue);
                break;
            case "指针移动后再点击":
                break;
            case "切换到iFrame":
                switchToFrame(locateWay,locateValue);
                break;
            case "从iframe切回":
                switchOutFrame();
                break;
            case "close":
                quit();
                break;
            case "切换窗口":
                switchToWindow(locateValue);
                break;
            case "ExecJS":
                executeJS(locateValue);
                break;
            default:
                return;
        }
    }

    public static void moveToElement(String locateWay,String locateValue)
    {
        WebElement element= BaseLocationMethod.findElement(locateWay, locateValue);
        Actions action =new Actions(WebDriverFactory.getDriver());
        action.moveToElement(element);
        action.click().perform();

    }
    /**
     * 切换窗口
     * @param locateValue
     */
    public static void switchToWindow( String locateValue)
    {
        try
        {
            String currenthandle =WebDriverFactory.getDriver().getWindowHandle();
            Set<String> handles =WebDriverFactory.getDriver().getWindowHandles();
            for(String s:handles)
            {
                if(s.equals(currenthandle))
                {
                    continue;
                }else
                {
                    WebDriverFactory.getDriver().switchTo().window(s);
                    if(WebDriverFactory.getDriver().getTitle().contains(locateValue))
                    {
                        logger.info("Windows switch successful");
                        break;
                    }else
                    {
                        continue;
                    }
                }
            }
        }catch (NoSuchWindowException e)
        {
            e.printStackTrace();

            logger.info("Windows switch false");
        }
    }
    public static void openUrl(String url,String tcname ){
        Engine.driver= WebDriverFactory.getDriver();
        try {
            Engine.getEngine().initByTestCase(WebDriverFactory.deviceName + File.separator + tcname + File.separator);
            WebDriverFactory.getDriver().get(url);
            logger.info(WebDriverFactory.getDriver());
            logger.info(url + "已经打开...");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    /*
     * 关闭窗口
     */
    public static void close(){
        WebDriverFactory.getDriver().close();
        logger.info("窗口已经关闭...");
    }

    /**退出移动浏览器*/
    public static void quit(){
        WebDriverFactory.getDriver().quit();
        logger.info("driver已被清理");
    }
    /**清空元素内容*/
    public static void clear(String locateWay,String locateValue){
        WebElement element= BaseLocationMethod.findElement(locateWay, locateValue);
        element.clear();
        logger.info("清空元素："+getLocatorByElement(element, ">")+"上的内容");
    }
    /**输入内容*/
    public static void typeContent(String locateWay,String locateValue){
        BaseLocationMethod.findElement(locateWay, locateValue).sendKeys(locateValue);
        logger.info("在元素："+getLocatorByElement(BaseLocationMethod.findElement(locateWay, locateValue), ">")+"输入内容："+locateValue);
    }
    /**点击*/
    public static void click(String locateWay,String locateValue){
         WebElement element= BaseLocationMethod.findElement(locateWay, locateValue);
        try{
            BaseLocationMethod.findElement(locateWay, locateValue).click();
            logger.info("点击元素："+getLocatorByElement(element, ">"));
        }catch(Exception e){
            logger.error("点击元素:"+getLocatorByElement(element, ">")+"失败", e);
            Assert.fail("点击元素:" + getLocatorByElement(element, ">") + "失败", e);
        }
    }
//    /**提交*/
//    public void submit(By by){
//        WebElement element=findElement(by);
//        try{
//            element.submit();
//        }catch(Exception e){
//            logger.error("在元素："+getLocatorByElement(element, ">")+"做的提交操作失败",e);
//            Assert.fail("在元素："+getLocatorByElement(element, ">")+"做的提交操作失败",e);
//        }
//        logger.info("在元素："+getLocatorByElement(element, ">")+"做了提交操作");
//    }


    /**
     * 在给定的时间内去查找元素，如果没找到则超时，抛出异常
     * */
    public void waitForElementToLoad(int elementTimeOut, final By By) {
        logger.info("开始查找元素[" + By + "]");
        try {
            (new WebDriverWait(WebDriverFactory.getDriver(), elementTimeOut)).until(new ExpectedCondition<Boolean>() {

                public Boolean apply(WebDriver driver) {
                    WebElement element = driver.findElement(By);
                    return element.isDisplayed();
                }
            });
        } catch (TimeoutException e) {
            logger.error("超时!! " + elementTimeOut + " 秒之后还没找到元素 [" + By + "]");
            Assert.fail("超时!! " + elementTimeOut + " 秒之后还没找到元素 [" + By + "]");

        }
        logger.info("找到了元素 [" + By + "]");
    }


    /**
     * 暂停当前用例的执行，暂停的时间为：sleepTime
     * */
    public static  void pause(int sleepTime) {
        if (sleepTime <= 0) {
            return;
        }
        try {
            TimeUnit.SECONDS.sleep(sleepTime);
            logger.info("暂停:"+sleepTime+"秒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** 根据元素来获取此元素的定位值 */
    public static String getLocatorByElement(WebElement element, String expectText) {
        String text = element.toString();
        String expect = null;
        try {
            expect = text.substring(text.indexOf(expectText) + 1, text.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("failed to find the string [" + expectText + "]");
        }
        return expect;
    }

//    /**跳转到webview页面*/
//    public void switchWebview(int index){
//        Set<String> contexts = driver.getContextHandles();
//        for (String context : contexts) {
//            System.out.println(context);
//            //打印出来看看有哪些context
//        }
//        driver.context((String) contexts.toArray()[index]);
//
//    }
//
//
//    /**跳转到webview页面*/
//    public void switchWebview(String contextName){
//        try{
//            Set<String> contexts = driver.getContextHandles();
//            for (String context : contexts) {
//                System.out.println(context);
//                //打印出来看看有哪些context
//            }
//            driver.context(contextName);
//        }catch(NoSuchContextException nce){
//            logger.error("没有这个context:"+contextName, nce);
//            Assert.fail("没有这个context:"+contextName, nce);
//        }
//
//    }
    /**
     * 执行JavaScript 方法
     * */
    public static void executeJS(String js) {
        ((JavascriptExecutor) WebDriverFactory.getDriver()).executeScript(js);
        logger.info("执行JavaScript语句：[" + js + "]");
    }
    public static void switchToFrame(String locateWay,String locateValue) {
        WebElement element= BaseLocationMethod.findElement(locateWay, locateValue);
        WebDriverFactory.getDriver().switchTo().frame(element);
    }
    public static void switchOutFrame() {
        WebDriverFactory.getDriver().switchTo().defaultContent();
    }

    /**
     * 执行JavaScript 方法和对象
     * 用法：seleniumUtil.executeJS("arguments[0].click();", seleniumUtil.findElementBy(MyOrdersPage.MOP_TAB_ORDERCLOSE));
     * */
    public static void executeJS(String js, Object... args) {
        ((JavascriptExecutor)  WebDriverFactory.getDriver()).executeScript(js, args);
        logger.info("执行JavaScript语句：[" + js + "]");
    }

//    /**检查元素是不是存在*/
//    public  boolean doesElementsExist(By byElement){
//        try{
//            findElement(byElement);
//            return true;
//        }catch(NoSuchElementException nee){
//
//            return false;
//        }



}
