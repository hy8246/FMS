package tests;

import org.testng.annotations.Test;

import com.syntelinc.fms.logic.queries.SequenceQuery;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

public class SequenceQueryTest extends QueryTest {
	
	private SequenceQuery sq;
	private String excelPath = "C:/Users/syntel/Documents/Java/EclipseProjects/FMS/src/tests/SequenceQuery.xlsx";
	
	@BeforeClass
	public void startTests() {
		sq = new SequenceQuery();
	}
	
	@BeforeMethod
	public void openBook() {
		excelBook.openExcel(excelPath);
	}
	
	@AfterMethod
	public void closeBook() {
		excelBook.closeExcel(excelPath);
	}
	
	@Test(dataProvider="getNextFeatureData")
	public void getNextFeatureTest(int row) {
		int result = sq.getNextFeature();
		excelBook.writeExcelData("getNextFeature()", row, 1, String.valueOf(result));
	}
	
	@DataProvider
	public Object[][] getNextFeatureData() {
		ReadFromExcel reader = new ReadFromExcel();
		return reader.readExcelData(excelPath, "getNextFeature()", 0);
	}
}