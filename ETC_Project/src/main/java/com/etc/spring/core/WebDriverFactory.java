package com.etc.spring.core;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by none.none on 2017/3/3.
 */

public class WebDriverFactory {
    public static String platForm;
    public  static String deviceName;
    public  static String pVersion;
    public  static String browserName;

    public static  AndroidDriver driver=null;
    public static AndroidDriver getDriver() {
        if (driver != null) {
            if(driver.getSessionId()!=null) {
                return driver;
            }else {
                return initDriver(platForm,deviceName, pVersion,browserName);
            }
        } else {
            driver=initDriver(platForm,deviceName, pVersion,browserName);
            return driver;
        }
    }
    public static AndroidDriver initDriver(String platForm,String deviceName,String pVersion,String browserName)
    {
        DesiredCapabilities desiredCapabilities=new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME,"Chrome");
        desiredCapabilities.setCapability("platformName",platForm);
        desiredCapabilities.setCapability("deviceName",deviceName);
        desiredCapabilities.setCapability("sessionOverride", true);
        desiredCapabilities.setCapability("platformVersion",pVersion);
        desiredCapabilities.setCapability("browserName",browserName);
        try {
            driver=new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),desiredCapabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }
    public static void  driverQuit()
    {
        driver.quit();
    }
}
