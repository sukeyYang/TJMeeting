package com.utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class JxlUtil {

    public void writeExcel(String filepath, List list) {
        if (list == null) {
            throw new IllegalArgumentException("要写入excel的数据不能为空！");
        }
        File file = new File(filepath);
        try {
            WritableWorkbook workbook = Workbook.createWorkbook(file);
            WritableSheet ws = workbook.createSheet("sheet 1", 0);// 创建sheet
            int rowNum = 0; // 要写的行，jxl操作excel时，第一行是从0开始，以此类推
            for (int i = 0; i < list.size(); i++) {// 写sheet
                Object[] cells = (Object[]) list.get(i);
                putRow(ws, rowNum, cells); // 压一行到sheet
                rowNum++;
            }
            workbook.write();
            workbook.close(); // 一定要关闭, 否则没有保存Excel
        } catch (RowsExceededException e) {
            System.out.println("jxl write RowsExceededException: "
                + e.getMessage());
        } catch (WriteException e) {
            System.out.println("jxl write WriteException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("jxl write file i/o exception!, cause by: "
                + e.getMessage());
        }
    }

    public static void writeExcel(String filepath, Map<String, String> question, List<Map<String, String>> answers) {
        if (answers == null) {
            throw new IllegalArgumentException("要写入excel的数据不能为空！");
        }
        File file = new File(filepath);
        try {
            WritableWorkbook workbook = Workbook.createWorkbook(file);
            WritableSheet ws = workbook.createSheet("sheet 1", 0);// 创建sheet
//            int rowNum = 0; // 要写的行，jxl操作excel时，第一行是从0开始，以此类推
            Map<String, Integer> header = new HashMap<String, Integer>();
            if (!question.isEmpty()) {
                int column = 0;
                for (String key : question.keySet()) {
                    Label cell = new Label(column, 0, key);//表头第0行
                    Label cell2 = new Label(column, 1, question.get(key));//表头第一行
                    ws.addCell(cell);
                    ws.addCell(cell2);
                    header.put(key, column);
                    column++;

                }
            }
            int rowNum = 2; // 要写的行，jxl操作excel时，第一行是从0开始，以此类推
            for (int i = 0; i < answers.size(); i++) {// 写sheet
                Map<String, String> answer = answers.get(i);
                putRow(ws, rowNum, answer, header); // 压一行到sheet
                rowNum++;
            }
            workbook.write();
            workbook.close(); // 一定要关闭, 否则没有保存Excel
        } catch (RowsExceededException e) {
            System.out.println("jxl write RowsExceededException: "
                + e.getMessage());
        } catch (WriteException e) {
            System.out.println("jxl write WriteException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("jxl write file i/o exception!, cause by: "
                + e.getMessage());
        }
    }

    public static void writeExcel(String filepath, String filepath2,
                                  List list1, int sheet, int row) {

        File file = new File(filepath);
        File file2 = new File(filepath2);
        Workbook book = null;

        try {
            book = Workbook.getWorkbook(file);
            WritableWorkbook workbook = Workbook.createWorkbook(file2, book);
            WritableSheet ws = workbook.getSheet(sheet);// 创建sheet
            int rowNum = row; // 要写的行，jxl操作excel时，第一行是从0开始，以此类推
            for (int i = 0; i < list1.size(); i++, rowNum++) {// 写sheet
                Object[] cells = (Object[]) list1.get(i);
                putRow(ws, rowNum, cells); // 压一行到sheet
            }
            // workbook.write();
            // workbook.close();
            // ws = workbook.getSheet(1);// 创建sheet
            // rowNum = 3; // 要写的行，jxl操作excel时，第一行是从0开始，以此类推
            // for (int i = 0; i < list2.size(); i++, rowNum++) {// 写sheet
            // Object[] cells = (Object[]) list2.get(i);
            // putRow(ws, rowNum, cells); // 压一行到sheet
            // }
            // workbook.write();
            // workbook.close();
            // ws = workbook.getSheet(2);// 创建sheet
            // rowNum = 3; // 要写的行，jxl操作excel时，第一行是从0开始，以此类推
            // for (int i = 0; i < list3.size(); i++, rowNum++) {// 写sheet
            // Object[] cells = (Object[]) list3.get(i);
            // putRow(ws, rowNum, cells); // 压一行到sheet
            // }
            workbook.write();

            workbook.close(); // 一定要关闭, 否则没有保存Excel
        } catch (BiffException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RowsExceededException e) {
            System.out.println("jxl write RowsExceededException: "
                + e.getMessage());
        } catch (WriteException e) {
            System.out.println("jxl write WriteException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("jxl write file i/o exception!, cause by: "
                + e.getMessage());
        }
    }

    private static void putRow(WritableSheet ws, int rowNum, Object[] cells)
        throws RowsExceededException, WriteException {

        for (int j = 0; j < cells.length; j++) {// 写一行
//			if (isNumeric(cells[j].toString())) {
            if (false) {
                jxl.write.Number numbercell = new jxl.write.Number(j, rowNum,
                    Integer.parseInt(cells[j].toString()));
                ws.addCell(numbercell);
            } else {
                Label cell = new Label(j, rowNum, "" + cells[j]);
                ws.addCell(cell);
            }
        }
    }

    private static void putRow(WritableSheet ws, int rowNum, Map<String, String> answer, Map<String, Integer> header)
        throws RowsExceededException, WriteException {

        for (String key : answer.keySet()) {// 写一行
            if (header.get(key) == null)
                continue;
            int column = header.get(key);
            Label cell = new Label(column, rowNum, answer.get(key));
            ws.addCell(cell);
        }
    }

    public static void main(String[] args) {
        JxlUtil importData = new JxlUtil();
        List<String[]> list = new ArrayList<String[]>();
        String[] a = {"1", "2", "3"};
        list.add(a);

        importData.writeExcel("test.xls", list);
        String aaString = "413023198011234516";
        System.out.println(isNumeric(aaString));
    }

    public static boolean isNumeric(String str) {
        if (str == null || str.equals("") || str.length() > 10) {
            return false;
        }
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
