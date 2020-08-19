package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.sl.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;

/**
 * HSSFWorkbook是操作03版Excel
 * XSSFWorkbook是操作07版Excel
 * SXSSFWorkbook是操作大数据量Excel使用
 * 
 * SXSSFWorkbook是streaming版本的XSSFWorkbook,它只会保存最新的excel rows在内存里供查看，在此之前的excel rows都会被写入到硬盘里
 * 至于多少行保存在内存里，其他写入硬盘，是由DEFAULT_WINDOW_SIZE决定的。代码也可以在创建SXSSFWorkbook实例时，传入一个int参数，来设定。
 * 需注意的是，int rowAccessWindowSize如果传入100，并不是指100行保存在内存里，而是说100的屏幕尺寸下可见的行数
 * @author Jay
 * @date 2018年9月19日
 */
public class BigExcelWriterUtil {
	
public static void main(String[] args) throws Exception {
		String sfilePath = "C:/Users/Administrator/Desktop/周家炬/保存/字段映射文档-南粤POS收单系统.xlsx";
		String filePath = "C:/Users/Administrator/Desktop/周家炬/保存/temp2.xlsx";
		List<List<String>> lists = ExcelUtil.getListBy03OR07ExcelPhysical(sfilePath, 0, 0);
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
	
	public static void writeBigExcelByMap(File file, HashMap<String, List<List<String>>> map){
        SXSSFWorkbook wb = new SXSSFWorkbook(100);
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

        try {
            fos = new FileOutputStream(file);
            wb.write(fos);
            fos.close();
            wb.dispose();
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static void writeBigExcelWithFormat(File file, List<List<String>> wLists){
        SXSSFWorkbook wb = new SXSSFWorkbook(100);
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
                
                CellStyle cellStyle = wb.createCellStyle();;

                //设置自动换行
                cellStyle.setWrapText(true);
                
                //设置垂直居中，具体见VerticalAlignment
                cellStyle.setVerticalAlignment((short) 1);
                //设置水平左对齐，具体见HorizontalAlignment
                cellStyle.setAlignment((short) 1);
                
                //设置边框
                cellStyle.setBorderBottom((short) 1);
                cellStyle.setBorderLeft((short) 1);
                cellStyle.setBorderTop((short) 1);
                cellStyle.setBorderRight((short) 1);
                
                //设置字体
                Font font = wb.createFont();
                font.setFontName("宋体");
                font.setFontHeightInPoints((short) 9);
                cellStyle.setFont(font);
                
                //设置单元格背景颜色
                cellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
                //设置填充格式,具体见FillPatternType
                cellStyle.setFillPattern((short)1);
                
                //设置超链接,连接当前文档页签名为服务识别并定位到A1
				CreationHelper createHelper = wb.getCreationHelper();
				XSSFHyperlink link = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_DOCUMENT);
				link.setLocation("#服务识别!A1");
				cell.setHyperlink(link);
                
                
                cell.setCellStyle(cellStyle);
                
                
                String value = cellList.get(cellIdx);
                cell.setCellValue(value);
                
                
                /*
                 * 对原有样式进行修改，先创建，再复制，再个性化，最后设置
                CellStyle cellStyle = wb.createCellStyle();
				cellStyle.cloneStyleFrom(cell.getCellStyle());
				// 设置单元格背景颜色
				cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
				// 设置填充格式,具体见FillPatternType
				cellStyle.setFillPattern((short) 1);
				cell.setCellStyle(cellStyle);*/
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
