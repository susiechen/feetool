package com.etc.spring.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by none.none on 2017/3/16.
 */
public class GetDirUtil {

    public static int isFirst=0;
    public  static  String dir;
    public static String getRootDir(HttpServletRequest request)
    {
        return request.getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/");
    }
    public static String getSelectRootDir(HttpServletRequest request,int a)
    {
        String[] path = request.getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/").split("/");
        String dir ="";
        for (int i = 0; i < path.length - a; i++) {
            dir = dir + (path[i] + "/");
        }
        return dir;
    }
}
