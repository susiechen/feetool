package com.etc.spring.util;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by none.none on 2017/3/29.
 */
public class MakeDirUtil {
    static String MESSAGE;

    public static boolean makeDirs(String filePath) {
        File fp = new File(filePath);
        try {
            if (!fp.exists()) {
                fp.mkdirs();// 目录不存在的情况下，创建目录。
                return true;
            }
        } catch (Exception e) {
            MESSAGE = "创建项目文件夹失败" + filePath;
            //JOptionPane.showMessageDialog(null, MESSAGE);
            return false;
        }
        return true;
    }
    public static boolean makeFile(String filepath,String fileName)
    {
        File file = new File(filepath,fileName);
        File path =new File(filepath);
        if (!path.exists()) {
            path.mkdirs();
        }
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
    public static void listMyFiles(File file,String destfile)
    {
        if (file.isDirectory())
        {
            File[] fs = file.listFiles();
            for (File file2 : fs) {
                if(file2.isFile())
                {
                    try {
                        // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
                        FileWriter writer = new FileWriter(destfile, true);
                        writer.write(file2.toString()+System.getProperty("line.separator"));
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(file2);
            }
            for (File file2 : fs)
            {
                listMyFiles(file2,destfile);

            }
        }
    }
}
