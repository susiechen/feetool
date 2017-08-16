package com.etc.spring.core;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by none.none on 2017/3/3.
 * 元素属性封装
 */
public abstract class BaseElementPropertyMethod {

    public static Logger logger = Logger.getLogger(BaseElementPropertyMethod.class);
    public static  String getElementProperty(String locateWay,String locateValue,String property,String parameter)
    {
        WebElement element = BaseLocationMethod.findElement(locateWay,locateValue);
        switch (property)
        {
            case "getAttribute":
                return element.getAttribute(parameter);

            case "isEnabled":
                return String.valueOf(element.isEnabled());

            case "isDisplayed":
                return String.valueOf(element.isDisplayed());

            case "isSelected":
                return String.valueOf(element.isSelected());

            case "getSize":
                return String.valueOf(element.getSize());

            case "getTagName":
                return element.getTagName();

            case "getCssValue":
                return element.getCssValue(parameter);
            case "getText":
                return element.getText();

            case "getHashCode":
                return String.valueOf(element.hashCode());

            case "toString":
                return element.toString();

            case "getRect":
                return String.valueOf(element.getRect());

            default:
                return "choose null";


        }
    }
}