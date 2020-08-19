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
 * HSSFWorkbook�ǲ���03��Excel
 * XSSFWorkbook�ǲ���07��Excel
 * SXSSFWorkbook�ǲ�����������Excelʹ��
 * 
 * SXSSFWorkbook��streaming�汾��XSSFWorkbook,��ֻ�ᱣ�����µ�excel rows���ڴ��﹩�鿴���ڴ�֮ǰ��excel rows���ᱻд�뵽Ӳ����
 * ���ڶ����б������ڴ������д��Ӳ�̣�����DEFAULT_WINDOW_SIZE�����ġ�����Ҳ�����ڴ���SXSSFWorkbookʵ��ʱ������һ��int���������趨��
 * ��ע����ǣ�int rowAccessWindowSize�������100��������ָ100�б������ڴ������˵100����Ļ�ߴ��¿ɼ�������
 * @author Jay
 * @date 2018��9��19��
 */
public class BigExcelWriterUtil {
	
public static void main(String[] args) throws Exception {
		String sfilePath = "C:/Users/Administrator/Desktop/�ܼҾ�/����/�ֶ�ӳ���ĵ�-����POS�յ�ϵͳ.xlsx";
		String filePath = "C:/Users/Administrator/Desktop/�ܼҾ�/����/temp2.xlsx";
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
        //�����п� 0�� 20���
        sheet.setColumnWidth(0, 20 * 256);

        for(int rowIdx = 0; rowIdx < wLists.size(); rowIdx++){
            Row row = sheet.createRow(rowIdx);
            //�����и�0�� 20�߶�
            row.setHeightInPoints(20);
            
            List<String> cellList = wLists.get(rowIdx);
            for(int cellIdx = 0; cellIdx < cellList.size(); cellIdx++){
                Cell cell = row.createCell(cellIdx);
                
                CellStyle cellStyle = wb.createCellStyle();;

                //�����Զ�����
                cellStyle.setWrapText(true);
                
                //���ô�ֱ���У������VerticalAlignment
                cellStyle.setVerticalAlignment((short) 1);
                //����ˮƽ����룬�����HorizontalAlignment
                cellStyle.setAlignment((short) 1);
                
                //���ñ߿�
                cellStyle.setBorderBottom((short) 1);
                cellStyle.setBorderLeft((short) 1);
                cellStyle.setBorderTop((short) 1);
                cellStyle.setBorderRight((short) 1);
                
                //��������
                Font font = wb.createFont();
                font.setFontName("����");
                font.setFontHeightInPoints((short) 9);
                cellStyle.setFont(font);
                
                //���õ�Ԫ�񱳾���ɫ
                cellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
                //��������ʽ,�����FillPatternType
                cellStyle.setFillPattern((short)1);
                
                //���ó�����,���ӵ�ǰ�ĵ�ҳǩ��Ϊ����ʶ�𲢶�λ��A1
				CreationHelper createHelper = wb.getCreationHelper();
				XSSFHyperlink link = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_DOCUMENT);
				link.setLocation("#����ʶ��!A1");
				cell.setHyperlink(link);
                
                
                cell.setCellStyle(cellStyle);
                
                
                String value = cellList.get(cellIdx);
                cell.setCellValue(value);
                
                
                /*
                 * ��ԭ����ʽ�����޸ģ��ȴ������ٸ��ƣ��ٸ��Ի����������
                CellStyle cellStyle = wb.createCellStyle();
				cellStyle.cloneStyleFrom(cell.getCellStyle());
				// ���õ�Ԫ�񱳾���ɫ
				cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
				// ��������ʽ,�����FillPatternType
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
