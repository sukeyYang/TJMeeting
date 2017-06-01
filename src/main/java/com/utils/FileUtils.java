package com.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class FileUtils {

    public static void writeFile(String lexeme, String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            output.write(lexeme + "\r\n");
            output.close();
        } catch (Exception e) {
            System.out.println("filePath：error");
            e.printStackTrace();
        }
    }

    public static List<String> getLexemeList(String filePath) {
        List<String> lexemeList = new ArrayList<String>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "utf-8"));
            String lexeme = br.readLine();
            while (lexeme != null) {
                lexemeList.add(lexeme);
                lexeme = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        return lexemeList;
    }

    public static String getLexemeString(String filePath) {
        StringBuffer lexemeString = new StringBuffer();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "utf-8"));
            String lexeme = br.readLine();
            while (lexeme != null) {
                lexeme=lexeme.replaceAll(" ","");
                lexemeString.append(lexeme);
                lexeme = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        return lexemeString.toString();
    }


    public static void main(String[] args) {
        String str = getLexemeString("D:\\workspace\\survey\\school.txt");
        JSONArray array = JSONArray.parseArray(str);
        for(int i=0 ; i<array.size();i++ ){
            JSONObject  schoolObj = array.getJSONObject(i);
            String pname = schoolObj.getString("name");
            JSONArray schoolList = schoolObj.getJSONArray("school");
            for(int j=0 ; j<schoolList.size();j++ ){
                JSONObject obj = schoolList.getJSONObject(j);
                String school =obj.getString("name");
                String data = pname +"|" + school;
                FileUtils.writeFile(data,"province-school.txt");
            }
            System.out.println(pname);
        }
    }

}
