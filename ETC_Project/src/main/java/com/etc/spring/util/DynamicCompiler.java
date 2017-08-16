package com.etc.spring.util;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by none.none on 2017/4/14.
 */
public class DynamicCompiler {
    public static boolean dynamicCompiler(String jarPath,String sourcePath,String destPath) throws InterruptedException
    {
        String command = "javac -encoding utf-8 -d " + destPath + " @" +sourcePath+" -classpath "+jarPath;
        Process process;
        try {
            process = Runtime.getRuntime().exec(command);
            InputStream errorStream = process.getErrorStream();
            InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line=bufferedReader.readLine()) != null){
                System.out.println(line);
                return false;
            }
            int exitVal = process.waitFor();
            System.out.println("Process exitValue: " + exitVal);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return true;

    }
}
