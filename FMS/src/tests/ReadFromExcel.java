package tests;

import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadFromExcel {
	private Object[][] testData;
	
	public Object[][] readExcelData(String filePath, String sheetName, int params) {
		try {
			FileInputStream fis = new FileInputStream(filePath);
			
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheet(sheetName);
			
			testData = new Object[sheet.getPhysicalNumberOfRows()-1][params + 1];
			
			Iterator<Row> ri = sheet.iterator();
			int rownum = 0;
			
			ri.next();
			while (ri.hasNext()) {
				Row r = ri.next();
				
				testData[rownum][0] = rownum + 1;
				
				for (int dataCnt = 1; dataCnt < params; dataCnt++) {
					testData[rownum][dataCnt] = r.getCell(dataCnt - 1).getStringCellValue();
				}
				
//				testData[rownum][1] = r.getCell(3).getStringCellValue();
//				testData[rownum][2] = r.getCell(4).getStringCellValue();
//				testData[rownum][3] = r.getCell(5).getBooleanCellValue();

				rownum++;
			}
			wb.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return testData;
	}
}
