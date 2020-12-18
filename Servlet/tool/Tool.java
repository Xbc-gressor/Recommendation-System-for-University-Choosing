package com.ursys.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

public class Tool {
    // 用于获取文件每一行的内容，返回一个列表
    public static Vector<String> readTxtFile(String filePath) {
        Vector<String> fileContent = new Vector<String>();
        try {
            String encoding = "UTF-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    fileContent.add(lineTxt);
                }
                read.close();
            } else {
                System.out.println("文件打开失败");
            }
        } catch (Exception e) {
            System.out.println("文件读取失败");
            e.printStackTrace();
        }
        return fileContent;
    }
}
