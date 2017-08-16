package com.etc.spring.core;

import org.apache.log4j.Logger;
import org.testng.Assert;

import java.util.Map;
import java.util.Set;

/**
 * Created by none.none on 2017/3/3.
 * 结果验证方式
 */
public abstract class BaseResultAssertMethod {
    public static Logger logger = Logger.getLogger(BaseResultAssertMethod .class);
    public static void  getAssertCommand(String command,String locateWay,String locadeValue,String property,String paramater,String excepValue)
    {

        String actual=BaseElementPropertyMethod.getElementProperty(locateWay,locadeValue,property,paramater);

        switch (command)
        {
            case "AssertFalse":
                assertFalse(Boolean.parseBoolean(actual));
                break;
            case "AssertTrue":
                assertTrue(Boolean.parseBoolean(actual));
                break;
            case "AssertEqualsBoolean":
                assertEqualsBoolean(Boolean.parseBoolean(actual),Boolean.parseBoolean(excepValue));
                break;
            case "AssertEqualsByte":
                try {
                    //assertEqualsByte(actual.getBytes(), excepValue.getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "AssertEqualsDouble":
                assertEqualsDouble(Double.parseDouble(actual),Double.parseDouble(excepValue));
                break;
            case "AssertEqualsString":
                assertEqualsString(actual,excepValue);
                break;
            case "AssertEqualsLong":
                assertEqualsLong(Long.parseLong(actual), Long.parseLong(excepValue));
                break;
            case "AssertEqualsSet":
                //assertEqualsSet();
                break;
            case "AssertEqualsArrayObject":
                break;
            default:
                return;
        }
    }
    public  static void assertTrue(boolean b)
    {

        Assert.assertTrue(b);
    }
    public  static void assertFalse(boolean b)
    {
      Assert.assertFalse(b);
    }
    public  static void assertEqualsBoolean(boolean b,boolean b1)
    {
        Assert.assertEquals(b,b1);
    }
    public  static void assertEqualsByte(byte b,byte b1)
    {
        Assert.assertEquals(b,b1);
    }
    public  static void assertEqualsDouble(double b,double b1)
    {
        Assert.assertEquals(b,b1);
    }
    public  static void assertEqualsString(String b,String b1)
    {
        Assert.assertEquals(b,b1);
    }
    public  static void assertEqualsLong(long b,long b1)
    {
        Assert.assertEquals(b,b1);
    }
    public  static void assertEqualsSet(Set<?> b, Set<?> b1)
    {
        Assert.assertEquals(b,b1);
    }
    public  static void assertEqualsArrayObject(Map<?,?> b,Map<?,?> b1)
    {
        Assert.assertEquals(b,b1);
    }

}
