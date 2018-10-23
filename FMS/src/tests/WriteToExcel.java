package tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteToExcel {
	XSSFWorkbook wb;
	XSSFSheet sheet;
	
	public void writeExcelData(String sheetName, int row, int cell, String result) {
		sheet = wb.getSheet(sheetName);
		Row r = sheet.getRow(row);
		System.out.println(r);
		Cell c = r.getCell(cell);
		c.setCellValue(result);
	}
	
	public void openExcel(String filePath) {
		try {
			FileInputStream fis = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeExcel(String filePath) {
		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			wb.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
