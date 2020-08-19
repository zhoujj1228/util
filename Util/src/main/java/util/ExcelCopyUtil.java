package util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * ���Ʋ�����ʽ
 * 
 * @author Jay
 * @date 2019��8��26��
 */
public final class ExcelCopyUtil {

	public static void main(String[] args) {
		String path = "D:\\Test\\excel\\�ֶ�ӳ���ĵ�-���м�ҵ��-57����Ԥ���ʽ���.xlsx";
		String path1 = "D:\\Test\\excel\\test20190823.xlsx";
		Workbook sWorkbook = ExcelUtil.getExcelWorkbook(new File(path));
		Sheet ssheet = sWorkbook.getSheet("���˻��б��ѯ");
		Workbook tWorkbook = ExcelUtil.getExcelWorkbook(new File(path1));
		Sheet tSheet = tWorkbook.createSheet("���˻��б��ѯ");
		copySheets(tSheet, ssheet);
		ExcelUtil.writeExcel(new File(path1), tWorkbook);
	}

	public ExcelCopyUtil() {
	}

	public static void copySheets(Sheet newSheet, Sheet sheet) {
		copySheets(newSheet, sheet, true);
	}

	public static void copySheets(Sheet newSheet, Sheet sheet, boolean copyStyle) {
		int maxColumnNum = 0;
		Map<Integer, CellStyle> styleMap = (copyStyle) ? new HashMap<Integer, CellStyle>() : null;
		for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
			Row srcRow = sheet.getRow(i);
			Row destRow = newSheet.createRow(i);
			if (srcRow != null) {
				ExcelCopyUtil.copyRow(sheet, newSheet, srcRow, destRow, styleMap);
				if (srcRow.getLastCellNum() > maxColumnNum) {
					maxColumnNum = srcRow.getLastCellNum();
				}
			}
		}
		for (int i = 0; i <= maxColumnNum; i++) { // �����п�
			newSheet.setColumnWidth(i, sheet.getColumnWidth(i));
		}
	}

	/**
	 * ���Ʋ��ϲ���Ԫ��
	 * 
	 * @param newSheet
	 * @param sheet
	 * @param copyStyle
	 */
	public static void copyRow(Sheet srcSheet, Sheet destSheet, Row srcRow, Row destRow,
			Map<Integer, CellStyle> styleMap) {
		Set<CellRangeAddressWrapper> mergedRegions = new TreeSet<CellRangeAddressWrapper>();
		destRow.setHeight(srcRow.getHeight());
		int deltaRows = destRow.getRowNum() - srcRow.getRowNum(); // ���copy����һ��sheet����ʼ������ͬ
		for (int j = srcRow.getFirstCellNum(); j <= srcRow.getLastCellNum(); j++) {
			Cell oldCell = srcRow.getCell(j); // old cell
			Cell newCell = destRow.getCell(j); // new cell
			if (oldCell != null) {
				if (newCell == null) {
					newCell = destRow.createCell(j);
				}
				copyCell(oldCell, newCell, styleMap);
				CellRangeAddress mergedRegion = getMergedRegion(srcSheet, srcRow.getRowNum(),
						(short) oldCell.getColumnIndex());
				if (mergedRegion != null) {
					CellRangeAddress newMergedRegion = new CellRangeAddress(mergedRegion.getFirstRow() + deltaRows,
							mergedRegion.getLastRow() + deltaRows, mergedRegion.getFirstColumn(),
							mergedRegion.getLastColumn());
					CellRangeAddressWrapper wrapper = new CellRangeAddressWrapper(newMergedRegion);
					if (isNewMergedRegion(wrapper, mergedRegions)) {
						mergedRegions.add(wrapper);
						destSheet.addMergedRegion(wrapper.range);
					}
				}
			}
		}
	}

	/**
	 * ��ԭ����Sheet��cell���У�����ʽ���������͸��Ƶ��µ�sheet��cell���У���
	 * 
	 * @param oldCell
	 * @param newCell
	 * @param styleMap
	 */
	public static void copyCell(Cell oldCell, Cell newCell, Map<Integer, CellStyle> styleMap) {
		if (styleMap != null) {
			if (oldCell.getSheet().getWorkbook() == newCell.getSheet().getWorkbook()) {
				newCell.setCellStyle(oldCell.getCellStyle());
			} else {
				int stHashCode = oldCell.getCellStyle().hashCode();
				CellStyle newCellStyle = styleMap.get(stHashCode);
				if (newCellStyle == null) {
					newCellStyle = newCell.getSheet().getWorkbook().createCellStyle();
					newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
					styleMap.put(stHashCode, newCellStyle);
				}
				newCell.setCellStyle(newCellStyle);
			}
		}
		try {
			newCell.setCellValue(ExcelUtil.getCellString(oldCell));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ��ȡmerge����
	public static CellRangeAddress getMergedRegion(Sheet sheet, int rowNum, short cellNum) {
		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
			CellRangeAddress merged = sheet.getMergedRegion(i);
			if (merged.isInRange(rowNum, cellNum)) {
				return merged;
			}
		}
		return null;
	}

	private static boolean isNewMergedRegion(CellRangeAddressWrapper newMergedRegion,
			Set<CellRangeAddressWrapper> mergedRegions) {
		boolean bool = mergedRegions.contains(newMergedRegion);
		return !bool;
	}

}

class CellRangeAddressWrapper implements Comparable<CellRangeAddressWrapper> {

	public CellRangeAddress range;

	public CellRangeAddressWrapper(CellRangeAddress theRange) {
		this.range = theRange;
	}

	public int compareTo(CellRangeAddressWrapper craw) {
		if (range.getFirstColumn() < craw.range.getFirstColumn() && range.getFirstRow() < craw.range.getFirstRow()) {
			return -1;
		} else if (range.getFirstColumn() == craw.range.getFirstColumn()
				&& range.getFirstRow() == craw.range.getFirstRow()) {
			return 0;
		} else {
			return 1;
		}
	}
}
