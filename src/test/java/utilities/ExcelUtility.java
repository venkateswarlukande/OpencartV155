package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	public static FileInputStream fi;
	public static FileOutputStream fo;
	public static XSSFWorkbook wb;
	public static XSSFSheet ws;
	public static XSSFRow row;
	public static XSSFCell cell;
	public static CellStyle style;

	String path;

	public ExcelUtility(String path) {
		this.path = path;
	}

	public int getRowCount(String sheet) throws IOException {

		fi = new FileInputStream(path);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(sheet);
		int rownum = ws.getLastRowNum();
		wb.close();
		fi.close();
		return rownum;
	}

	public int getCellCount(String sheet, int rownum) throws IOException {

		fi = new FileInputStream(path);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(sheet);
		row = ws.getRow(rownum);
		int cellnum = row.getLastCellNum();
		wb.close();
		fi.close();
		return cellnum;
	}

	public String getCellData(String sheet, int rownum, int celnum) throws IOException {

		fi = new FileInputStream(path);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(sheet);
		row = ws.getRow(rownum);
		cell = row.getCell(celnum);

		String data;
		DataFormatter formatter = new DataFormatter();
		try {
			// data = cell.toString();
			data = formatter.formatCellValue(cell);
		} catch (Exception e) {
			data = "";
		}

		return data;

	}

	public void setCellData(String sheet, int rownum, int celnum, String data) throws IOException {

		File xlfile = new File(path);
		if (!xlfile.exists()) { // if file not exist then create new file
			wb = new XSSFWorkbook();
			fo = new FileOutputStream(path);
			wb.write(fo);
		}

		fi = new FileInputStream(path);
		wb = new XSSFWorkbook(fi);

		if (wb.getSheetIndex(sheet) == -1) // if sheet not exist then create new sheet
			wb.createSheet(sheet);
		ws = wb.getSheet(sheet);

		if (ws.getRow(rownum) == null)
			ws.createRow(rownum);
		row = ws.getRow(rownum);

		cell = row.createCell(celnum);
		cell.setCellValue(data);
		fo = new FileOutputStream(path);
		wb.write(fo);

		wb.close();
		fi.close();
		fo.close();
	}

	public void fillGreenColor( String sheet, int rownum, int celnum) throws IOException {

		fi = new FileInputStream(path);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(sheet);
		row = ws.getRow(rownum);
		cell = row.getCell(celnum);

		style = wb.createCellStyle();

		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(style);
		fo = new FileOutputStream(path);
		wb.write(fo);

		wb.close();
		fi.close();
		fo.close();
	}

	public void fillRedColor( String sheet, int rownum, int celnum) throws IOException {

		fi = new FileInputStream(path);
		wb = new XSSFWorkbook(fi);
		ws = wb.getSheet(sheet);
		row = ws.getRow(rownum);
		cell = row.getCell(celnum);

		style = wb.createCellStyle();

		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(style);
		fo = new FileOutputStream(path);
		wb.write(fo);

		wb.close();
		fi.close();
		fo.close();
	}

}
