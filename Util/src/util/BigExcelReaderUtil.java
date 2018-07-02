package util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;


import util.comparator.StringNumComparator;

public class BigExcelReaderUtil {

	public static HashMap<String, HashMap<String, HashMap<String, String>>> indexRowColMap;
	public static HashMap<String, HashMap<String, HashMap<String, String>>> indexColRowMap;
	public static HashMap<String, HashMap<String, String>> rowColMap;
	public static HashMap<String, HashMap<String, String>> colRowMap;

	public static void main(String[] args) throws Exception {
		
		//String filePath = "C:/Users/Administrator/Desktop/周家炬/保存/temp.xlsx";
		String filePath = "C:/Users/Administrator/Desktop/周家炬/保存/ESB_ZJCBSD_服务分析全量文档.xlsx";
//		BigExcelReaderUtil.processOnSheetToMap(filePath);
		List<String> indexList = new ArrayList<String>();
		indexList.add("1");
		indexList.add("2");
		indexList.add("3");
		indexList.add("4");
		BigExcelReaderUtil.processAllSheetsToMapByIdxList(filePath,indexList);
		//HashMap<String, HashMap<String, String>> colRowMap = BigExcelReaderUtil.colRowMap;
		System.out.println();
		System.out.println("--------------------");
		for(String index : indexList){
			HashMap<String, HashMap<String, String>> rowColMap = indexRowColMap.get(index);
			Set<String> rowSet = rowColMap.keySet();
			List<String> rowList = new ArrayList<String>(rowSet);
			Collections.sort(rowList, new StringNumComparator(true));
			for(String row : rowList){
				System.out.print(row);
				HashMap<String, String> colMap = rowColMap.get(row);
				Set<String> colSet = colMap.keySet();
				List<String> colList = new ArrayList<String>(colSet);
				Collections.sort(colList);
				for(String col : colList){
					System.out.print(col + "\t|" + colMap.get(col) + "|\t");
				}
				System.out.println();
			}
			System.out.println("--------------------");
		}
		
		//BigExcelReaderUtil.processAllSheets(filePath);
	}
	
	public static List<String> getRowList(Set<String> rowSet){
		List<String> rowList = new ArrayList<String>(rowSet);
		Collections.sort(rowList, new StringNumComparator(true));
		return rowList;
	}
	
	public static List<String> getColList(Set<String> colSet){
		List<String> colList = new ArrayList<String>(colSet);
		Collections.sort(colList);
		return colList;
	}
	
	public static void processOnSheet(String filePath) throws Exception{
		OPCPackage pkg = OPCPackage.open(filePath);
		XSSFReader reader = new XSSFReader(pkg);
		SharedStringsTable sst = reader.getSharedStringsTable();
		XMLReader parser = fetchSheetParser(sst);
		InputStream sheet2 = reader.getSheet("rId1");
		InputSource sheetSource = new InputSource(sheet2);
		parser.parse(sheetSource);
		sheet2.close();
	}

	public static void processAllSheets(String fileName) throws Exception{
		OPCPackage pkg = OPCPackage.open(fileName);
		XSSFReader reader = new XSSFReader(pkg);
		SharedStringsTable sst = reader.getSharedStringsTable();
		XMLReader parser = fetchSheetParser(sst);
		Iterator<InputStream> sheets = reader.getSheetsData();
		while(sheets.hasNext()){
			System.out.println("processing new sheet:\n");
			InputStream sheet = sheets.next();
			InputSource sheetSource = new InputSource(sheet);
			parser.parse(sheetSource);
			sheet.close();
			System.out.println("");
		}
	}
	
	
	public static void processOnSheetToMap(String filePath, String index) throws Exception{
		rowColMap = new HashMap<String, HashMap<String, String>>();
		colRowMap = new HashMap<String, HashMap<String, String>>();
		
		OPCPackage pkg = OPCPackage.open(filePath);
		XSSFReader reader = new XSSFReader(pkg);
		SharedStringsTable sst = reader.getSharedStringsTable();
		XMLReader parser = fetchSheetParserToMap(sst);
		InputStream sheet = reader.getSheet("rId" + index);
		InputSource sheetSource = new InputSource(sheet);
		parser.parse(sheetSource);
		sheet.close();
	}
	
	public static void processAllSheetsToMap(String fileName) throws Exception{
		indexRowColMap = new HashMap<String, HashMap<String, HashMap<String, String>>>();
		indexColRowMap = new HashMap<String, HashMap<String, HashMap<String, String>>>();
		OPCPackage pkg = OPCPackage.open(fileName);
		XSSFReader reader = new XSSFReader(pkg);
		SharedStringsTable sst = reader.getSharedStringsTable();
		XMLReader parser = fetchSheetParserToMap(sst);
		Iterator<InputStream> sheets = reader.getSheetsData();
		int index = 0;
		while(sheets.hasNext()){
			rowColMap = new HashMap<String, HashMap<String, String>>();
			colRowMap = new HashMap<String, HashMap<String, String>>();
			
			System.out.println("processing new sheet:\n");
			InputStream sheet = sheets.next();
			InputSource sheetSource = new InputSource(sheet);
			parser.parse(sheetSource);
			sheet.close();
			System.out.println("");
			
			indexColRowMap.put(index+"", colRowMap);
			indexRowColMap.put(index+"", rowColMap);
			index++;
		}
	}
	
	public static void processAllSheetsToMapByIdxList(String filePath, List<String> idxList) throws Exception{
		OPCPackage pkg = OPCPackage.open(filePath);
		XSSFReader reader = new XSSFReader(pkg);
		SharedStringsTable sst = reader.getSharedStringsTable();
		XMLReader parser = fetchSheetParserToMap(sst);
		indexRowColMap = new HashMap<String, HashMap<String, HashMap<String, String>>>();
		indexColRowMap = new HashMap<String, HashMap<String, HashMap<String, String>>>();
		for(String index : idxList){
			rowColMap = new HashMap<String, HashMap<String, String>>();
			colRowMap = new HashMap<String, HashMap<String, String>>();
			
			InputStream sheet = reader.getSheet("rId" + index);
			InputSource sheetSource = new InputSource(sheet);
			parser.parse(sheetSource);
			sheet.close();
			
			indexColRowMap.put(index, colRowMap);
			indexRowColMap.put(index, rowColMap);
		}
	}
	
	private static XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
		XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
		ContentHandler handler = new SheetHandler(sst);
		parser.setContentHandler(handler);
		
		return parser;
	}

	private static class SheetHandler extends DefaultHandler{
		private SharedStringsTable sst;
		private String lastContents;
		private boolean nextIsString;
		private SheetHandler(SharedStringsTable sst){
			this.sst = sst;
		}
		/**
		 * attrs.getValue("r"):当前单元格的位置
		 * attrs.getValue("t"):当前单元格数据类型,有b(boolean), e(error), inlineStr(inLineStr), s(sstIndex), str(formula) 
		 * 
		 */
		public void startElement(String uri, String localName, String name, Attributes attrs){
			if(name.equals("c")){
				System.out.print(attrs.getValue("r") + " - ");
				String cellType = attrs.getValue("t");
				if(cellType != null && cellType.equals("s")){
					nextIsString = true;
				}else{
					nextIsString = false;
				}
			}
			lastContents = "";
		}
		
		public void endElement(String uri, String localName, String name){
			if(nextIsString){
				int idx = Integer.parseInt(lastContents);
				lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
				nextIsString = false;
			}
			
			if(name.equals("v")){
				System.out.println(lastContents);
			}
		}
		
		public void characters(char[] ch, int start, int length){
			lastContents += new String(ch, start, length);
		}
		
	}

	
	private static XMLReader fetchSheetParserToMap(SharedStringsTable sst) throws SAXException {
		XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
		ContentHandler handler = new MapSheetHandler(sst);
		parser.setContentHandler(handler);
		
		return parser;
	}
	
	private static class MapSheetHandler extends DefaultHandler{
		private SharedStringsTable sst;
		private String lastContents;
		private boolean nextIsString;
		private String position;
		public MapSheetHandler(SharedStringsTable sst){
			this.sst = sst;
		}
		
		/**
		 * attrs.getValue("r"):当前单元格的位置
		 * attrs.getValue("t"):当前单元格数据类型,有b(boolean), e(error), inlineStr(inLineStr), s(sstIndex), str(formula) 
		 * 
		 */
		public void startElement(String uri, String localName, String name, Attributes attrs){
			
			if(name.equals("c")){
				position = attrs.getValue("r");
				System.out.print(attrs.getValue("r") + " - ");
				String cellType = attrs.getValue("t");
				if(cellType != null && cellType.equals("s")){
					nextIsString = true;
				}else{
					nextIsString = false;
				}
			}
			lastContents = "";
		}
		
		public void endElement(String uri, String localName, String name){
			if(nextIsString){
				int idx = Integer.parseInt(lastContents);
				lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
				nextIsString = false;
			}
			
			if(name.equals("v")){
				System.out.println(lastContents);
			}
			
			if(position != null){
				char[] chars = position.toCharArray();
				StringBuilder colStr = new StringBuilder();
				StringBuilder rowStr = new StringBuilder();
				for(char c : chars){
					int charIndex = (int)c;
					if(charIndex > 64){
						colStr.append(c);
					}else{
						rowStr.append(c);
					}
				}
				/*int colIndex = Integer.parseInt(colStr.toString());
				int rowIndex = Integer.parseInt(rowStr.toString());*/
				HashMap<String, String> colMap = rowColMap.get(rowStr.toString());
				if(colMap == null){
					colMap = new HashMap<String, String>();
				}
				colMap.put(colStr.toString(), lastContents);
				rowColMap.put(rowStr.toString(), colMap);
				
				HashMap<String, String> rowMap = colRowMap.get(colStr.toString());
				if(rowMap == null){
					rowMap = new HashMap<String, String>();
				}
				rowMap.put(rowStr.toString(), lastContents);
				colRowMap.put(colStr.toString(), rowMap);
				position = null;
			}
		}
		
		public void characters(char[] ch, int start, int length){
			lastContents += new String(ch, start, length);
		}
		
	}
	
	
}

