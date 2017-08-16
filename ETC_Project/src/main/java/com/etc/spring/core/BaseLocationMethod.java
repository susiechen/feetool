package com.etc.spring.core;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

/**
 * Created by none.none on 2017/3/3.
 * 定位方法封装
 */
public class BaseLocationMethod {
    public static Logger logger = Logger.getLogger(BaseLocationMethod.class);
    static WebElement element = null;
    static List <?> elements = null;
    public static WebElement findElement(String locateWay,String locateValue){
        switch(locateWay){
            case "id":
                element = WebDriverFactory.getDriver().findElement(By.id(locateValue));
                break;
            case "className":
                element =  WebDriverFactory.getDriver().findElement(By.className(locateValue));
                break;
            case "cssSelector":
                element =  WebDriverFactory.getDriver().findElement(By.cssSelector(locateValue));
                break;
            case "linkText":
                element =  WebDriverFactory.getDriver().findElement(By.linkText(locateValue));
                break;
            case "name":
                element =  WebDriverFactory.getDriver().findElement(By.name(locateValue));
                break;
            case "partialLinkText":
                element =  WebDriverFactory.getDriver().findElement(By.partialLinkText(locateValue));
                break;
            case "tagName":
                element =  WebDriverFactory.getDriver().findElement(By.tagName(locateValue));
                break;
            case "xpath":
                element =  WebDriverFactory.getDriver().findElement(By.xpath(locateValue));
                break;
            default:
                logger.error("定位方式："+locateWay+"不被支持");
                Assert.fail("定位方式：" + locateWay + "不被支持");
        }
        return element;
    }
    /**查找一组元素 - appium新增的查找元素方法*/
    public static List<?> findElements(String locateWay,String locateValue){

        switch(locateWay){
            case "id":
                elements = WebDriverFactory.getDriver().findElements(By.id(locateValue));
                break;
            case "className":
                elements =  WebDriverFactory.getDriver().findElements(By.className(locateValue));
                break;
            case "cssSelector":
                elements =  WebDriverFactory.getDriver().findElements(By.cssSelector(locateValue));
                break;
            case "linkText":
                elements =  WebDriverFactory.getDriver().findElements(By.linkText(locateValue));
                break;
            case "name":
                elements =  WebDriverFactory.getDriver().findElements(By.name(locateValue));
                break;
            case "partialLinkText":
                elements =  WebDriverFactory.getDriver().findElements(By.partialLinkText(locateValue));
                break;
            case "tagName":
                elements =  WebDriverFactory.getDriver().findElements(By.tagName(locateValue));
                break;
            case "xpath":
                elements =  WebDriverFactory.getDriver().findElements(By.xpath(locateValue));
                break;
            default:
                logger.error("定位方式："+locateWay+"不被支持");
                Assert.fail("定位方式：" + locateWay + "不被支持");
        }
        return elements;
    }

}
