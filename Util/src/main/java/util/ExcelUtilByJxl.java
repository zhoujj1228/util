package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelUtilByJxl {
	
	public static WritableWorkbook createNewExcel03(String excelPath) {
		WritableWorkbook writewb = null;
		try {
			writewb = jxl.Workbook.createWorkbook(new File(excelPath));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return writewb;
	}
	/**
	 * ����excel�ļ�·���õ�һ����д��Workbook
	 * @param excelPath �ļ�·��
	 * @return һ����д��Workbook
	 */
	public static WritableWorkbook getWriteExcel03(String excelPath){
		InputStream is = null;
		jxl.Workbook readwb;
		WritableWorkbook writewb = null;
		try {
			is = new FileInputStream(excelPath);
			readwb = jxl.Workbook.getWorkbook(is);
			writewb = jxl.Workbook.createWorkbook(new File(excelPath), readwb);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		/*�����÷�����
		writewb = Workbook.createWorkbook(new File(excelPath));
		writewb.createSheet("�ӿ��嵥", 0);
		WritableSheet sheet = writewb.getSheet(0);
		Label label = new Label(lie, hang, data);
		sheet.addCell(label);
		wwb.write();
		wwb.close();*/
		return writewb;
	}
	
	/**
	 * ����excel�ļ�·���õ�һ����д��Workbook
	 * @param excelPath �ļ�·��
	 * @return һ����д��Workbook
	 */
	public static boolean write03ExcelByList(String excelPath, List<List<String>> lists){
		InputStream is = null;
		jxl.Workbook readwb;
		WritableWorkbook writewb = null;
		try {
			is = new FileInputStream(excelPath);
			readwb = jxl.Workbook.getWorkbook(is);
			writewb = jxl.Workbook.createWorkbook(new File(excelPath), readwb);
			writewb = jxl.Workbook.createWorkbook(new File(excelPath));
			writewb.createSheet("sheet1", 0);
			WritableSheet sheet = writewb.getSheet(0);
			for(int i = 0; i < lists.size(); i++){
				List<String> list = lists.get(i);
				for(int j = 0; j < list.size(); j++){
					Label label = new Label(j, i, list.get(j));
					sheet.addCell(label);
				}
			}
			writewb.write();
			writewb.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
     * ����excel�ļ�·���õ�һ����д��Workbook
     * @param excelPath �ļ�·��
     * @return һ����д��Workbook
     */
    public static boolean write03ExcelByMap(String excelPath, HashMap<String, List<List<String>>> map){
        InputStream is = null;
        jxl.Workbook readwb;
        WritableWorkbook writewb = null;
        try {
            is = new FileInputStream(excelPath);
            readwb = jxl.Workbook.getWorkbook(is);
            writewb = jxl.Workbook.createWorkbook(new File(excelPath), readwb);
            writewb = jxl.Workbook.createWorkbook(new File(excelPath));
            int sheetNum = 0;
            for(String key : map.keySet()){
                List<List<String>> lists = map.get(key);
                WritableSheet sheet = writewb.createSheet(key, sheetNum++);
                //WritableSheet sheet = writewb.getSheet(0);
                for(int i = 0; i < lists.size(); i++){
                    List<String> list = lists.get(i);
                    for(int j = 0; j < list.size(); j++){
                        Label label = new Label(j, i, list.get(j));
                        sheet.addCell(label);
                    }
                }
            }

            writewb.write();
            writewb.close();
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
