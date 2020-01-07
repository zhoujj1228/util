package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.sl.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;





public class Excel07Util {
    public static void main(String[] args) {
        File file = new File("D:/Test/excel/test2.xlsx");
        HashMap<String, List<List<String>>> map= ExcelUtil.getSheetNameMapBy03OR07Excel("D:/Test/excel/t.xls", 0, 0);
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
                
                CellStyle cellStyle = wb.createCellStyle();

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
                font.setColor(HSSFColor.BLACK.index);
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
    
    public static boolean udpateExcelExample(String filePath) {
		InputStream is = null;
		OutputStream os = null;
		Workbook writewb = null;
		try {
			is = new FileInputStream(filePath);
			writewb = WorkbookFactory.create(is);
			is.close();
			os = new FileOutputStream(filePath);
			
			
			writewb.write(os);
			writewb.close();
			os.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

	}
    
    public static boolean writeExcelToFile(String outfilePath, Workbook writewb) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(outfilePath);
			writewb.write(os);
			writewb.close();
			os.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

	}
    
    public static Workbook getWriteExcel(String filePath) {
		InputStream is = null;
		Workbook writewb = null;
		try {
			is = new FileInputStream(filePath);
			writewb = WorkbookFactory.create(is);
			is.close();
			return writewb;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}
    
    public static void compareTwoExcel(File oldexcel, File newexcel){
    	HashMap<String, List<List<String>>> oldSheetNameMap = ExcelUtil.getSheetNameMapBy03OR07Excel(oldexcel.getAbsolutePath(), 0, 0);
    	HashMap<String, List<List<String>>> newSheetNameMap = ExcelUtil.getSheetNameMapBy03OR07Excel(newexcel.getAbsolutePath(), 0, 0);
    	
    	//对比表格数
    	Set<String> oldkeySet = oldSheetNameMap.keySet();
    	Set<String> newkeySet = newSheetNameMap.keySet();
    	List<String> oldmoreList = new LinkedList<>();
    	List<String> newmoreList = new LinkedList<>();
    	for(String oldkey : oldkeySet){
    		if(!newkeySet.contains(oldkey)){
    			oldmoreList.add(oldkey);
    		}
    	}
    	
    	for(String newkey : newkeySet){
    		if(!oldkeySet.contains(newkey)){
    			newmoreList.add(newkey);
    		}
    	}
    	
    	for(String sheetName : oldmoreList){
    		System.out.println(newexcel.getName() + " 少了表格：" + sheetName);
    	}
    	
    	for(String sheetName : newmoreList){
    		System.out.println(newexcel.getName() + " 多出表格：" + sheetName);
    	}
    	
    	
    	//对比表格内容
    	for(String sheetName : newkeySet){
    		if(!oldSheetNameMap.containsKey(sheetName)){
    			continue;
    		}
    		
    		List<List<String>> newlists = newSheetNameMap.get(sheetName);
    		List<List<String>> oldlists = oldSheetNameMap.get(sheetName);
    		
    		if(newlists.size() != oldlists.size()){
    			int diff = newlists.size() - oldlists.size();
    			System.out.println(newexcel.getName() + " 表格有改动-表前后行数量不一致：" + sheetName + "\t相差行数：" + diff);
    			continue;
    		}
    		for (int i = 0; i < newlists.size(); i++) {
    			List<String> newlist = newlists.get(i);
    			List<String> oldlist = oldlists.get(i);
    			
    			if(newlist.size() != oldlist.size()){
    				System.out.println(newexcel.getName() + " 表格有改动-行前后字段数量不一致：" + sheetName + "\t" + (i+1));
    				continue;
    			}
    			
    			for (int j = 0; j < newlist.size(); j++) {
    				String newValue = newlist.get(j);
    				String oldValue = oldlist.get(j);
    				if(newValue == null && oldValue == null){
    					continue;
    				}
    				if(!newValue.equals(oldValue)){
    					System.out.println(newexcel.getName() + " 表格有改动-字段有改动：" + sheetName + "\t" + (i+1) + "\t" + (j+1));
    				}
				}
			}
    		
    	}
    	
    }
    
    
    
    
    
    
    
	
	
	
	
	/**
	 * 合并单元格处理,获取合并行
	 * 
	 * @param sheet
	 * @return List<CellRangeAddress>
	 */
	public List<CellRangeAddress> getCombineCell(Sheet sheet) {
		List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
		// 获得一个 sheet 中合并单元格的数量
		int sheetmergerCount = sheet.getNumMergedRegions();
		// 遍历所有的合并单元格
		for (int i = 0; i < sheetmergerCount; i++) {
			// 获得合并单元格保存进list中
			CellRangeAddress ca = sheet.getMergedRegion(i);
			list.add(ca);
		}
		return list;
	}


	/**
	 * 判断单元格是否为合并单元格，是的话则将单元格的值返回
	 * 
	 * @param listCombineCell 存放合并单元格的list
	 * @param cell 需要判断的单元格
	 * @param sheet sheet
	 * @return
	 */
	public static String isCombineCell(List<CellRangeAddress> listCombineCell, Cell cell, Sheet sheet) throws Exception {
		int firstC = 0;
		int lastC = 0;
		int firstR = 0;
		int lastR = 0;
		String cellValue = null;
		for (CellRangeAddress ca : listCombineCell) {
			// 获得合并单元格的起始行, 结束行, 起始列, 结束列
			firstC = ca.getFirstColumn();
			lastC = ca.getLastColumn();
			firstR = ca.getFirstRow();
			lastR = ca.getLastRow();
			if (cell.getRowIndex() >= firstR && cell.getRowIndex() <= lastR) {
				if (cell.getColumnIndex() >= firstC && cell.getColumnIndex() <= lastC) {
					Row fRow = sheet.getRow(firstR);
					Cell fCell = fRow.getCell(firstC);
					cellValue = ExcelUtil.getCellString(fCell);
					break;
				}
			} else {
				cellValue = "";
			}
		}
		return cellValue;
	}

	/**
	 * 获取合并单元格的值
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static String getMergedRegionValue(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();

		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();

			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					try {
						return ExcelUtil.getCellString(fCell);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		return null;
	}

	/**
	 * 判断指定的单元格是否是合并单元格
	 * 
	 * @param sheet
	 * @param row 行下标
	 * @param column 列下标
	 * @return
	 */
	public static boolean isMergedRegion(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 获取sheetNameMap，其中解决了单元格合并问题
	 * @param excelPath
	 * @param beginRow
	 * @param beginLie
	 * @return
	 */
	
	public static HashMap<String, List<List<String>>> getSheetNameMapByExcel(String excelPath, int beginRow, int beginLie) {
		org.apache.poi.ss.usermodel.Workbook wb = null;
		HashMap<String, List<List<String>>> sheetNameListMap = new HashMap<String, List<List<String>>>();
		
		try {
			File file = new File(excelPath);
			wb =  WorkbookFactory.create(new FileInputStream(file));
			int sheetCount = wb.getNumberOfSheets();
			
			for(int sheetNum = 0; sheetNum < sheetCount; sheetNum++){

				Sheet sheet = wb.getSheetAt(sheetNum);
				String sheetName = sheet.getSheetName();

				//System.out.println("------------" + sheetName);
				HashMap<String, String> mergedRegionValueMap = getMergedRegionValueMap(sheet);
				List<List<String>> allList = getListsBySheet(sheet, beginRow, beginLie, mergedRegionValueMap);
				sheetNameListMap.put(sheetName, allList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(wb != null){
					wb.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				
			}
		}
		/*for(ArrayList<String> list : allList){
			System.out.println();
			for(String s : list){
				System.out.print(s+" ");
			}
		}*/
		
		
		return sheetNameListMap;
	}

	/**
	 * 返回合并单元格值的Map，Map的key为“表名-行数-列数”
	 * @param sheet
	 * @return
	 * @throws Exception 
	 */
	private static HashMap<String, String> getMergedRegionValueMap(Sheet sheet) throws Exception {
		HashMap<String, String> result = new HashMap<>();
		int sheetMergeCount = sheet.getNumMergedRegions();
		String sheetName = sheet.getSheetName();
		
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();
			Cell cell = sheet.getRow(firstRow).getCell(firstColumn);
			String value = ExcelUtil.getCellString(cell);
			for (int j = firstColumn; j < lastColumn + 1; j++) {
				for (int k = firstRow; k < lastRow + 1; k++) {
					String key = sheetName + "-" + k + "-" +j;
					result.put(key, value);
				}
			}
		}

		return result;
	}

	/**
	 * 将sheet转换为List模型，其中解决了单元格合并问题
	 * @param sheet
	 * @param beginRow
	 * @param beginLie
	 * @param mergedRegionValueMap 
	 * @return
	 * @throws Exception
	 */
	private static List<List<String>> getListsBySheet(Sheet sheet, int beginRow, int beginLie, HashMap<String, String> mergedRegionValueMap) throws Exception {
		List<List<String>> allList = new ArrayList<List<String>>();
		List<String> rowList;
		for(int i = beginRow; i < sheet.getPhysicalNumberOfRows(); i++){
			Row row = sheet.getRow(i);
			if(row == null){
				//System.out.println("null页"+sheetNum+"行"+i);
				continue;
			}
			rowList = new ArrayList<String>();
			for(int j = beginLie ; j < row.getLastCellNum() + 1; j++){
				Cell cell = row.getCell(j);
				if(cell==null){
					//System.out.println("null页"+sheetNum+"行"+i+"列"+j);
					rowList.add("");
					continue;
				}
				String s;
				String cellIndex = sheet.getSheetName() + "-" + i + "-" +j;
				if(mergedRegionValueMap.containsKey(cellIndex)){
					s = mergedRegionValueMap.get(cellIndex);
				}else{
					s = ExcelUtil.getCellString(cell);
				}
				rowList.add(s);
				
			}
			allList.add(rowList);
		}
		return allList;
	}
	
	
	public void copySheetToExcel(File source, String sheetName, File target) {
		
	}
	
	public static boolean writeNewExcelByListAndFlag(File file, List<String> lists, String flag, String sheetName) {
		OutputStream os;
		Workbook writewb = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
				writewb = new XSSFWorkbook();
			}else {
				writewb = getWriteExcel(file.getAbsolutePath());
			}
			Sheet sheet = writewb.createSheet(sheetName);
			for (int rowIdx = 0; rowIdx < lists.size(); rowIdx++) {
				Row row = sheet.createRow(rowIdx);
				String list = lists.get(rowIdx);
				String[] cellList = list.split(flag);
				for (int cellIdx = 0; cellIdx < cellList.length; cellIdx++) {
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
	
	public static void writeExcelBySheetNameAndListToLastRow(File file, List<String> list,
			String sheetName, boolean copyLastRowStyle) {
		Workbook writeExcel = getWriteExcel(file.getPath());
		Sheet sheet = writeExcel.getSheet(sheetName);
		int lastRowIndex = sheet.getPhysicalNumberOfRows();
		Row createRow = createRow(sheet, lastRowIndex);
		for (int i = 0; i < list.size(); i++) {
			String cellValue = list.get(i);
			Cell createCell = createRow.createCell(i);
			createCell.setCellValue(cellValue);
			if(copyLastRowStyle) {
				Cell lastCell = sheet.getRow(lastRowIndex-1).getCell(i);
				cloneStyle(createCell, lastCell, writeExcel);
			}
		}
		
		writeExcelToFile(file.getPath(), writeExcel);
	}
	
	public static void cloneStyle(Cell cell, Cell cloneCell, Workbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.cloneStyleFrom(cloneCell.getCellStyle());
		cell.setCellStyle(cellStyle);
		
	}
	
	/**
	 * 如果行已存在则在该行前插入一行，否则新建一行
	 * @param sheet
	 * @param rowIndex
	 * @return
	 */
	public static Row createRow(Sheet sheet, int rowIndex) {
		Row row = null;
		if (sheet.getRow(rowIndex) != null) {
			int lastRowNo = sheet.getLastRowNum();
			sheet.shiftRows(rowIndex, lastRowNo, 1);
		}
		row = sheet.createRow(rowIndex);
		return row;
	}
}
