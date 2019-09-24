package util;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Workbook;
import util.domain.ExcelSheetDomain;

public class ExcelUtil {
	public static void main(String[] args) {
		String path = "D:\\Test\\excel\\字段映射文档-新中间业务-57重庆预售资金监管.xlsx";
		String path1 = "D:\\Test\\excel\\test" + new Date().getTime() + ".xlsx";
		Excel07Util.create07Excel(new File(path1));
		copyExcelSheet(new File(path), new File(path1), "子账户列表查询");
	}
	
	/**
	 * 结果中数字以整形返回
	 * 
	 * @param excelPath
	 * @param beginRow
	 * @param beginLie
	 * @return
	 */
	public static HashMap<String, List<List<String>>> getSheetNameMapNumAsInt(String excelPath, int beginRow,
			int beginLie) {
		Workbook wb = null;
		HashMap<String, List<List<String>>> sheetNameListMap = new HashMap<String, List<List<String>>>();
		ArrayList<String> rowList = null;
		try {
			File file = new File(excelPath);
			wb = WorkbookFactory.create(new FileInputStream(file));
			int sheetCount = wb.getNumberOfSheets();
			// System.out.println(sheetCount);
			for (int sheetNum = 0; sheetNum < sheetCount; sheetNum++) {

				Sheet sheet = wb.getSheetAt(sheetNum);
				String sheetName = sheet.getSheetName();
				List<List<String>> allList = new ArrayList<List<String>>();

				for (int i = beginRow; i < sheet.getPhysicalNumberOfRows(); i++) {
					Row row = sheet.getRow(i);
					if (row == null) {
						// System.out.println("null页"+sheetNum+"行"+i);
						continue;
					}
					rowList = new ArrayList<String>();
					for (int j = beginLie; j < row.getLastCellNum() + 1; j++) {
						Cell cell = row.getCell(j);
						if (cell == null) {
							// System.out.println("null页"+sheetNum+"行"+i+"列"+j);
							rowList.add("");
							continue;
						}
						String s = getCellStringNumAsInt(cell);
						rowList.add(s);

					}
					allList.add(rowList);
				}

				sheetNameListMap.put(sheetName, allList);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (wb != null) {
					wb.close();
				}

			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		/*
		 * for(ArrayList<String> list : allList){ System.out.println(); for(String s :
		 * list){ System.out.print(s+" "); } }
		 */

		return sheetNameListMap;
	}

	private static String getCellStringNumAsInt(Cell cell) throws Exception {

		String result = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				result = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				Boolean temp = cell.getBooleanCellValue();
				result = temp.toString();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					Date cellDate = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					result = sdf.format(cellDate);
				} else {
					result = DecimalUtil.getNumberStr(cell.getNumericCellValue(), "#");
				}
				break;
			case Cell.CELL_TYPE_BLANK:
				result = "";
				break;
			case Cell.CELL_TYPE_FORMULA:
				// System.out.println("FORMULA类型默认设置为String:rowindex=" + cell.getRowIndex() + "
				// colindex=" + cell.getColumnIndex());
				result = cell.getStringCellValue();
				break;
			default:
				throw new Exception("数据类型不正确:rowindex=" + cell.getRowIndex() + " colindex=" + cell.getColumnIndex());
			}
		}
		return result;
	}

	/**
	 * 返回包括空值的行列，更新版
	 * 
	 * @param excelPath
	 * @param beginRow
	 * @param beginLie
	 * @return
	 */
	public static List<List<String>> getListBy03OR07Excel(String excelPath, int beginRow, int beginLie) {
		Workbook wb = null;
		ArrayList<String> rowList = null;
		List<List<String>> allList = new ArrayList<List<String>>();
		try {
			File file = new File(excelPath);
			wb = WorkbookFactory.create(new FileInputStream(file));
			int sheetCount = wb.getNumberOfSheets();
			System.out.println(sheetCount);
			for (int sheetNum = 0; sheetNum < sheetCount; sheetNum++) {

				Sheet sheet = wb.getSheetAt(sheetNum);

				for (int i = beginRow; i < sheet.getPhysicalNumberOfRows(); i++) {
					Row row = sheet.getRow(i);
					if (row == null) {
						System.out.println("null页" + sheetNum + "行" + i);
						continue;
					}
					rowList = new ArrayList<String>();
					for (int j = beginLie; j < row.getLastCellNum() + 1; j++) {
						Cell cell = row.getCell(j);
						if (cell == null) {
							System.out.println("null页" + sheetNum + "行" + i + "列" + j);
							rowList.add("");
							continue;
						}
						String s = getCellString(cell);
						rowList.add(s);

					}
					allList.add(rowList);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (wb != null) {
					wb.close();
				}

			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		/*
		 * for(ArrayList<String> list : allList){ System.out.println(); for(String s :
		 * list){ System.out.print(s+" "); } }
		 */

		return allList;
	}

	public static ArrayList<ArrayList<String>> getListBy07Excel(String excelPath, int beginRow, int beginLie,
			int sheetCount) {
		HSSFWorkbook wb = null;
		ArrayList<String> rowList = null;
		ArrayList<ArrayList<String>> allList = new ArrayList<ArrayList<String>>();
		try {
			wb = new HSSFWorkbook(new FileInputStream(excelPath));
			for (int sheetNum = 0; sheetNum < sheetCount; sheetNum++) {
				HSSFSheet sheet = wb.getSheetAt(sheetNum);
				rowList = new ArrayList<String>();

				for (int i = beginRow; i < sheet.getLastRowNum() + 1; i++) {
					HSSFRow row = sheet.getRow(i);
					for (int j = beginLie; j < row.getLastCellNum() + 1; j++) {
						HSSFCell cell = row.getCell(j);
						String s = getCellString(cell);
						rowList.add(s);
					}

				}
				allList.add(rowList);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (wb != null) {
					wb.close();
				}

			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		for (ArrayList<String> list : allList) {
			System.out.println();
			for (String s : list) {
				System.out.print(s + " ");
			}
		}

		return allList;
	}

	public static List<List<String>> getListBy03OR07ExcelAndSheetNames(String excelPath, List<String> list,
			int beginRow, int beginLie) {

		Workbook wb = null;
		ArrayList<String> rowList = null;
		List<List<String>> allList = new ArrayList<List<String>>();
		try {
			File file = new File(excelPath);
			wb = WorkbookFactory.create(new FileInputStream(file));

			for (String sheetName : list) {
				Sheet sheet = wb.getSheet(sheetName);
				for (int i = beginRow; i < sheet.getPhysicalNumberOfRows(); i++) {
					Row row = sheet.getRow(i);
					if (row == null) {
						System.out.println("null页" + sheetName + "行" + i);
						continue;
					}
					rowList = new ArrayList<String>();
					for (int j = beginLie; j < row.getLastCellNum() + 1; j++) {
						Cell cell = row.getCell(j);
						if (cell == null) {
							System.out.println("null页" + sheetName + "行" + i + "列" + j);
							rowList.add("");
							continue;
						}
						String s = getCellString(cell);
						rowList.add(s);

					}
					allList.add(rowList);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (wb != null) {
					wb.close();
				}

			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		/*
		 * for(ArrayList<String> list : allList){ System.out.println(); for(String s :
		 * list){ System.out.print(s+" "); } }
		 */

		return allList;
	}

	public static String getCellString(Cell cell) throws Exception {
		String result = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				result = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				Boolean temp = cell.getBooleanCellValue();
				result = temp.toString();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					Date cellDate = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					result = sdf.format(cellDate);
				} else {
					//result = cell.getNumericCellValue() + "";
					result = getNumberStr(cell.getNumericCellValue(), "#.##########");
				}
				break;
			case Cell.CELL_TYPE_BLANK:
				result = "";
				break;
			case Cell.CELL_TYPE_FORMULA:
				System.out.println(
						"FORMULA类型默认设置为String:rowindex=" + cell.getRowIndex() + " colindex=" + cell.getColumnIndex());
				result = cell.getStringCellValue();
				break;
			default:
				throw new Exception("数据类型不正确:rowindex=" + cell.getRowIndex() + " colindex=" + cell.getColumnIndex());
			}
		}
		return result;
	}

	public static ArrayList<ArrayList<String>> readExcelAllData(File file) {
		ArrayList<ArrayList<String>> rowList = null;
		FileInputStream input = null;
		HSSFWorkbook wb = null;
		try {
			input = new FileInputStream(file);
			wb = new HSSFWorkbook(new BufferedInputStream(input));
			HSSFSheet sheet = wb.getSheetAt(0);
			rowList = new ArrayList<ArrayList<String>>();
			ArrayList<String> cellList = null;
			for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
				HSSFRow row = sheet.getRow(i);
				cellList = new ArrayList<String>();
				for (int j = 0; j < row.getLastCellNum() + 1; j++) {
					if (row != null) {
						HSSFCell cell = row.getCell(j);
						if (cell == null) {
							cellList.add("");
							continue;
						}
						String s = getCellString(cell);
						cellList.add(s);
					}

				}
				rowList.add(cellList);
			}
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			try {
				wb.close();
				input.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		/*
		 * for(ArrayList<String> list : rowList){ System.out.println(list.size());
		 * for(String s : list){ System.out.print("   "+s); } }
		 */
		return rowList;
	}

	/**
	 * 只返回有效的行列值（空值不返回），原版
	 * 
	 * @param excelPath
	 * @param beginRow
	 * @param beginLie
	 * @return
	 */
	public static List<List<String>> getListBy03OR07ExcelPhysical(String excelPath, int beginRow, int beginLie) {
		Workbook wb = null;
		ArrayList<String> rowList = null;
		List<List<String>> allList = new ArrayList<List<String>>();
		try {
			File file = new File(excelPath);
			wb = WorkbookFactory.create(new FileInputStream(file));
			int sheetCount = wb.getNumberOfSheets();
			System.out.println(sheetCount);
			for (int sheetNum = 0; sheetNum < sheetCount; sheetNum++) {

				Sheet sheet = wb.getSheetAt(sheetNum);

				for (int i = beginRow; i < sheet.getPhysicalNumberOfRows(); i++) {
					Row row = sheet.getRow(i);
					if (row == null) {
						System.out.println("null页" + sheetNum + "行" + i);
						continue;
					}
					rowList = new ArrayList<String>();
					for (int j = beginLie; j < row.getPhysicalNumberOfCells(); j++) {
						Cell cell = row.getCell(j);
						if (cell == null) {
							System.out.println("null页" + sheetNum + "行" + i + "列" + j);
							continue;
						}
						String s = getCellString(cell);
						rowList.add(s);

					}
					allList.add(rowList);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (wb != null) {
					wb.close();
				}

			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		/*
		 * for(ArrayList<String> list : allList){ System.out.println(); for(String s :
		 * list){ System.out.print(s+" "); } }
		 */

		return allList;
	}

	public static List<ExcelSheetDomain> getSheetDomainBy03OR07Excel(String excelPath, int beginRow, int beginLie) {
		List<ExcelSheetDomain> result = new ArrayList<ExcelSheetDomain>();
		Workbook wb = null;
		ArrayList<String> rowList = null;
		List<List<String>> allList = null;
		try {
			File file = new File(excelPath);
			wb = WorkbookFactory.create(new FileInputStream(file));
			int sheetCount = wb.getNumberOfSheets();
			// System.out.println(sheetCount);
			ExcelSheetDomain esd;
			for (int sheetNum = 0; sheetNum < sheetCount; sheetNum++) {

				Sheet sheet = wb.getSheetAt(sheetNum);
				esd = new ExcelSheetDomain();
				esd.setSheetName(sheet.getSheetName());
				allList = new ArrayList<List<String>>();
				for (int i = beginRow; i < sheet.getPhysicalNumberOfRows(); i++) {
					Row row = sheet.getRow(i);
					if (row == null) {
						System.out.println("null页" + sheetNum + "行" + i);
						continue;
					}
					rowList = new ArrayList<String>();
					for (int j = beginLie; j < row.getLastCellNum() + 1; j++) {
						Cell cell = row.getCell(j);
						if (cell == null) {
							System.out.println("null页" + sheetNum + "行" + i + "列" + j);
							rowList.add("");
							continue;
						}
						String s = getCellString(cell);
						rowList.add(s);

					}
					allList.add(rowList);
				}
				esd.setDataList(allList);
				result.add(esd);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (wb != null) {
					wb.close();
				}

			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		/*
		 * for(ArrayList<String> list : allList){ System.out.println(); for(String s :
		 * list){ System.out.print(s+" "); } }
		 */

		return result;
	}

	public static HashMap<String, List<List<String>>> getSheetNameMapBy03OR07Excel(String excelPath, int beginRow,
			int beginLie) {
		Workbook wb = null;
		HashMap<String, List<List<String>>> sheetNameListMap = new HashMap<String, List<List<String>>>();
		ArrayList<String> rowList = null;
		try {
			File file = new File(excelPath);
			wb = WorkbookFactory.create(new FileInputStream(file));
			int sheetCount = wb.getNumberOfSheets();
			// System.out.println(sheetCount);
			for (int sheetNum = 0; sheetNum < sheetCount; sheetNum++) {

				Sheet sheet = wb.getSheetAt(sheetNum);
				String sheetName = sheet.getSheetName();
				List<List<String>> allList = new ArrayList<List<String>>();

				for (int i = beginRow; i < sheet.getPhysicalNumberOfRows(); i++) {
					Row row = sheet.getRow(i);
					if (row == null) {
						// System.out.println("null页"+sheetNum+"行"+i);
						continue;
					}
					rowList = new ArrayList<String>();
					for (int j = beginLie; j < row.getLastCellNum() + 1; j++) {
						Cell cell = row.getCell(j);
						if (cell == null) {
							// System.out.println("null页"+sheetNum+"行"+i+"列"+j);
							rowList.add("");
							continue;
						}
						String s = getCellString(cell);
						rowList.add(s);

					}
					allList.add(rowList);
				}

				sheetNameListMap.put(sheetName, allList);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (wb != null) {
					wb.close();
				}

			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		/*
		 * for(ArrayList<String> list : allList){ System.out.println(); for(String s :
		 * list){ System.out.print(s+" "); } }
		 */

		return sheetNameListMap;
	}

	/**
	 * 复制不了样式
	 * @param sourceFile
	 * @param targetFile
	 * @param sheetName
	 */
	public static void copyExcelSheet(File sourceFile, File targetFile, String sheetName) {
		Workbook sourceWorkbook = getExcelWorkbook(sourceFile);
		Sheet sourceSheet = getExcelSheet(sourceWorkbook, sheetName);
		List<List<CellStyle>> cellStyleLists = getCellStyleLists(sourceSheet);
		List<List<String>> cellValueLists = getCellValueLists(sourceSheet);
		Workbook targetWorkbook = getExcelWorkbook(targetFile);
		createSheet(targetWorkbook, sheetName, cellStyleLists, cellValueLists);
		writeExcel(targetFile, targetWorkbook);
		
	}
	
	private static void createSheet(Workbook targetWorkbook, String sheetName, List<List<CellStyle>> cellStyleLists,
			List<List<String>> cellValueLists) {
		Sheet targetSheet = targetWorkbook.createSheet(sheetName);
		for (int i = 0; i < cellValueLists.size(); i++) {
			Row createRow = targetSheet.createRow(i);
			List<String> cellValuelist = cellValueLists.get(i);
			List<CellStyle> cellStyleList = cellStyleLists.get(i);
			for (int j = 0; j < cellValuelist.size(); j++) {
				Cell createCell = createRow.createCell(j);
				createCell.setCellValue(cellValuelist.get(j));
				CellStyle createCellStyle = targetWorkbook.createCellStyle();
				createCellStyle.cloneStyleFrom(cellStyleList.get(j));
				createCell.setCellStyle(createCellStyle);
			}
		}
	}

	public static List<List<CellStyle>> getCellStyleLists(Sheet sheet) {
		List<List<CellStyle>> result = new ArrayList<>();
		for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
			List<CellStyle> rowCellStyleList = new ArrayList<>();
			Row row = sheet.getRow(i);
			if(row == null) {
				continue;
			}
			for (int j = 0; j < row.getLastCellNum() + 1; j++) {
				if(row.getCell(j) == null) {
					continue;
				}
				CellStyle cellStyle = row.getCell(j).getCellStyle();
				rowCellStyleList.add(cellStyle);
			}
			result.add(rowCellStyleList);
		}
		return result;
	}
	
	public static List<List<String>> getCellValueLists(Sheet sheet) {
		List<List<String>> result = new ArrayList<>();
		for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
			List<String> rowCellValueList = new ArrayList<>();
			Row row = sheet.getRow(i);
			if(row == null) {
				continue;
			}
			for (int j = 0; j < row.getLastCellNum() + 1; j++) {
				if(row.getCell(j) == null) {
					continue;
				}
				Cell cell = row.getCell(j);
				String cellString = "";
				try {
					cellString = getCellString(cell);
				} catch (Exception e) {
					e.printStackTrace();
				}
				rowCellValueList.add(cellString);
			}
			result.add(rowCellValueList);
		}
		return result;
	}
	

	public static Sheet getExcelSheet(Workbook excelWorkbook, String sheetName) {
		Sheet sheet = excelWorkbook.getSheet(sheetName);
		return sheet;
	}

	/**
	 * 获取workbook(可读可写)
	 * 
	 * @param file
	 * @return
	 */
	public static Workbook getExcelWorkbook(File file) {
		try {
			Workbook wb = WorkbookFactory.create(new FileInputStream(file));
			return wb;
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static String getNumberStr(double number, String pattern){
		DecimalFormat df = new DecimalFormat(pattern);
		String format = df.format(number);
		return format;
	}
	
	public static boolean writeExcel(File file, Workbook writewb) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
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
}
