package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel07Util {
    public static void main(String[] args) {
        File file = new File("D:/Test/excel/test2.xlsx");
        HashMap<String, List<List<String>>> map= FileUtil.getSheetNameMapBy03OR07Excel("D:/Test/excel/t.xls", 0, 0);
        writeNewExcelByMap(file, map);
    }
    public static boolean writeNewExcelByMapAndFlag(File file, HashMap<String, List<String>> map, String flag){
        OutputStream os;
        Workbook writewb;
        try {
            writewb = new XSSFWorkbook();
            if(!file.exists()){
                file.createNewFile();
            }
            
            for(String key : map.keySet()){
                List<String> lists = map.get(key);
                Sheet sheet = writewb.createSheet(key);
                for(int rowIdx = 0; rowIdx < lists.size(); rowIdx++){
                    Row row = sheet.createRow(rowIdx);
                    String list = lists.get(rowIdx);
                    String[] cellList = list.split(flag);
                    for(int cellIdx = 0; cellIdx < cellList.length; cellIdx++){
                        Cell cell = row.createCell(cellIdx);
                        String value = cellList[cellIdx];
                        cell.setCellValue(value);
                    }
                }
            }
            
            os = new FileOutputStream(file);
            writewb.write(os);
            os.close();
            writewb.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean copy07Excel(File newFile, File oldFile) {
        InputStream is = null;
        OutputStream os;
        Workbook writewb = null;
        try {
            if(!newFile.exists()){
                newFile.createNewFile();
            }
            is = new FileInputStream(oldFile);
            writewb = WorkbookFactory.create(is);
            is.close();
            os = new FileOutputStream(newFile);
            writewb.write(os);
            writewb.close();
            os.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 在原来excel上追加lists数据
     * @param file
     * @param lists
     * @return
     */
    public static boolean addSheetToExcelByList(File file, List<List<String>> lists){
        InputStream is = null;
        OutputStream os;
        Workbook writewb = null;
        try {
            is = new FileInputStream(file);
            writewb = WorkbookFactory.create(is);
            is.close();
            Sheet sheet = writewb.createSheet();
            for(int rowIdx = 0; rowIdx < lists.size(); rowIdx++){
                Row row = sheet.createRow(rowIdx);
                List<String> cellList = lists.get(rowIdx);
                for(int cellIdx = 0; cellIdx < cellList.size(); cellIdx++){
                    Cell cell = row.createCell(cellIdx);
                    String value = cellList.get(cellIdx);
                    cell.setCellValue(value);
                }
            }
            os = new FileOutputStream(file);
            writewb.write(os);
            writewb.close();
            os.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 创建一个新的excel并写入lists数据
     * @param file
     * @param lists
     * @return
     */
    public static boolean writeNewExcelByList(File file, List<List<String>> lists){
        OutputStream os;
        Workbook writewb;
        try {
            writewb = new XSSFWorkbook();
            if(!file.exists()){
                file.createNewFile();
            }
            Sheet sheet = writewb.createSheet();
            for(int rowIdx = 0; rowIdx < lists.size(); rowIdx++){
                Row row = sheet.createRow(rowIdx);
                List<String> cellList = lists.get(rowIdx);
                for(int cellIdx = 0; cellIdx < cellList.size(); cellIdx++){
                    Cell cell = row.createCell(cellIdx);
                    String value = cellList.get(cellIdx);
                    cell.setCellValue(value);
                }
            }
            os = new FileOutputStream(file);
            writewb.write(os);
            os.close();
            writewb.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean writeNewExcelByListAndFlag(File file, List<String> lists, String flag){
        OutputStream os;
        Workbook writewb;
        try {
            writewb = new XSSFWorkbook();
            if(!file.exists()){
                file.createNewFile();
            }
            Sheet sheet = writewb.createSheet();
            for(int rowIdx = 0; rowIdx < lists.size(); rowIdx++){
                Row row = sheet.createRow(rowIdx);
                String list = lists.get(rowIdx);
                String[] cellList = list.split(flag);
                for(int cellIdx = 0; cellIdx < cellList.length; cellIdx++){
                    Cell cell = row.createCell(cellIdx);
                    String value = cellList[cellIdx];
                    cell.setCellValue(value);
                }
            }
            os = new FileOutputStream(file);
            writewb.write(os);
            os.close();
            writewb.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 创建新excel并写入map数据
     * @param file
     * @param map
     * @return
     */
    public static boolean writeNewExcelByMap(File file, HashMap<String, List<List<String>>> map){
        OutputStream os;
        Workbook writewb;
        try {
            writewb = new XSSFWorkbook();
            if(!file.exists()){
                file.createNewFile();
            }
            for(String key : map.keySet()){
                List<List<String>> wLists = map.get(key);
                Sheet sheet = writewb.createSheet(key);
                for(int rowIdx = 0; rowIdx < wLists.size(); rowIdx++){
                    Row row = sheet.createRow(rowIdx);
                    List<String> cellList = wLists.get(rowIdx);
                    for(int cellIdx = 0; cellIdx < cellList.size(); cellIdx++){
                        Cell cell = row.createCell(cellIdx);
                        String value = cellList.get(cellIdx);
                        cell.setCellValue(value);
                    }
                }

            }
            os = new FileOutputStream(file);
            writewb.write(os);
            os.close();
            writewb.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 创建一个新07版excel
     * @param file
     */
    public static void create07Excel(File file){
        XSSFWorkbook wb = new XSSFWorkbook();
        wb.createSheet();
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            wb.write(fos);
            fos.close();
            wb.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public static void writeExcelWithFormat(File file, List<List<String>> wLists){
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        //设置列宽 0列 20宽度
        sheet.setColumnWidth(0, 20 * 256);

        for(int rowIdx = 0; rowIdx < wLists.size(); rowIdx++){
            Row row = sheet.createRow(rowIdx);
            //设置行高0行 20高度
            row.setHeightInPoints(20);
            
            List<String> cellList = wLists.get(rowIdx);
            for(int cellIdx = 0; cellIdx < cellList.size(); cellIdx++){
                Cell cell = row.createCell(cellIdx);
                
                CellStyle cellStyle = cell.getCellStyle();

                //设置自动换行
                cellStyle.setWrapText(true);
                
                //设置垂直居中，具体见VerticalAlignment
                cellStyle.setVerticalAlignment((short) 1);
                //设置水平左对齐，具体见HorizontalAlignment
                cellStyle.setAlignment((short) 1);
                
                cell.setCellStyle(cellStyle);
                
                
                String value = cellList.get(cellIdx);
                cell.setCellValue(value);
            }
        }
        
        try {
            FileOutputStream fos = new FileOutputStream(file);
            wb.write(fos);
            fos.close();
            wb.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * 在原来excel追加map数据
     * @param file
     * @param map
     */
    public static void addSheetToExcelByMap(File file, HashMap<String, List<List<String>>> map){
        InputStream is = null;
        Workbook wb = null;
        try {
            is = new FileInputStream(file);
            wb = WorkbookFactory.create(is);
            FileOutputStream fos = null;
            for(String key : map.keySet()){
                List<List<String>> wLists = map.get(key);
                Sheet sheet = wb.createSheet(key);
                for(int rowIdx = 0; rowIdx < wLists.size(); rowIdx++){
                    Row row = sheet.createRow(rowIdx);
                    List<String> cellList = wLists.get(rowIdx);
                    for(int cellIdx = 0; cellIdx < cellList.size(); cellIdx++){
                        Cell cell = row.createCell(cellIdx);
                        String value = cellList.get(cellIdx);
                        cell.setCellValue(value);
                    }
                }

            }
            fos = new FileOutputStream(file);
            wb.write(fos);
            fos.close();
            wb.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
