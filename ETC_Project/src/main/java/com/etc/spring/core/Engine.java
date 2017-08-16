package com.etc.spring.core;

import com.etc.spring.util.GetDirUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by none.none on 2017/3/7.
 */
public class Engine {
    public static Engine engine;
    public static WebDriver driver;
    public String caseName;
    public String caseFolder;
    public String dir;
    public static Engine getEngine()
    {
        if(engine!=null)
        {
            return engine;
        }else
        {
            engine=new Engine();
            return engine;
        }
    }
    public void setDriver(WebDriver driver)
    {
        this.driver=driver;
    }
    public static WebDriver getDriver()
    {
        if(driver!=null)
        {
            return driver;
        }else
        {
            return null;
        }
    }
    public static void takeSnapShot(String name)
    {
        Engine.getEngine().snapShot(Engine.getDriver(),name);
    }
    public  void snapShot(WebDriver driver,String filename)
    {
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Date date = new Date();
            DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            String time = format.format(date);
            FileUtils.copyFile(scrFile, new File(caseFolder + time + "-" + filename + ".png"));
        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    public void initByTestCase(String caseName) throws IOException
    {
        Date date=new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = format.format(date);
        String dir=GetDirUtil.dir;
        caseFolder = dir+"OutputResults/"+caseName+"/"+time;
        FileUtils.forceMkdir(new File(caseFolder));
        this.caseName=caseName;

    }
}
