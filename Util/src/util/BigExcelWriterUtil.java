package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class BigExcelWriterUtil {
	
public static void main(String[] args) throws Exception {
		String sfilePath = "C:/Users/Administrator/Desktop/周家炬/保存/temp.xlsx";
		String filePath = "C:/Users/Administrator/Desktop/周家炬/保存/temp2.xlsx";
		List<List<String>> lists = FileUtil.getListBy03OR07ExcelPhysical(sfilePath, 0, 0);
		writeBigExcel(new File(filePath), lists);
	}
	
	public static void testWriteBigExcel(File file){
		SXSSFWorkbook wb = new SXSSFWorkbook(100);
		Sheet sheet = wb.createSheet();
		for(int rowIdx = 0; rowIdx < 10000; rowIdx++){
			Row row = sheet.createRow(rowIdx);
			for(int cellIdx = 0; cellIdx < 10; cellIdx++){
				Cell cell = row.createCell(cellIdx);
				String address = new CellReference(cell).formatAsString();
				cell.setCellValue(address);
			}
		}
		
		try {
			FileOutputStream fos = new FileOutputStream(file);
			wb.write(fos);
			fos.close();
			wb.dispose();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void writeBigExcel(File file, List<List<String>> wLists){
		SXSSFWorkbook wb = new SXSSFWorkbook(100);
		Sheet sheet = wb.createSheet();
		for(int rowIdx = 0; rowIdx < wLists.size(); rowIdx++){
			Row row = sheet.createRow(rowIdx);
			List<String> cellList = wLists.get(rowIdx);
			for(int cellIdx = 0; cellIdx < cellList.size(); cellIdx++){
				Cell cell = row.createCell(cellIdx);
				String value = cellList.get(cellIdx);
				cell.setCellValue(value);
			}
		}
		
		try {
			FileOutputStream fos = new FileOutputStream(file);
			wb.write(fos);
			fos.close();
			wb.dispose();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
