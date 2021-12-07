package ApiUtilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.LinkedCaseInsensitiveMap;

import Pages.Constant;
import Utilities.LoggerUtility;
import Utilities.Utilities;

public class ExcelHelper {
	
	public static Map<String,String> getData() throws IOException {
		Workbook workbook = null;
		
		String excelName="TestData_API_"+Utilities.ReadFromPropertyFile("Environment").trim()+".xlsx";
      Map<String,String>  hm=new  LinkedCaseInsensitiveMap<>();
     String path= new File(".").getCanonicalPath();
      String actualFilePath=path+"\\src\\test\\resources\\TestDataSheet\\"+excelName;
      File inputworksheet=new File(actualFilePath);
      int iDataColumn=0;
      try {
    	  FileInputStream	  input = new FileInputStream(inputworksheet);
		workbook=new XSSFWorkbook(input);	
	} catch (Exception e) {
		LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
				+ new Throwable().getStackTrace()[0].getMethodName(), "Exception :" + e);
	
	}
      
      String[] splitedScenarioname = Constant.Scenario.split("-");
		Constant.SplitedFeature=splitedScenarioname[0].trim();
		Sheet sheet = workbook.getSheet(Constant.SplitedFeature);
		Row row=sheet.getRow(0);
		for (int i = 1; i < row.getPhysicalNumberOfCells(); i++) {
			if (row.getCell(i).getStringCellValue().equals(Constant.Scenario)) {
				iDataColumn=i;
				break;
			}
		}
		if (iDataColumn!=0) {
			for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {
				Cell cell=sheet.getRow(j).getCell(iDataColumn);
				if (cell!=null) {
					if (cell.getCellType() == CellType.NUMERIC) {
						DataFormatter data = new DataFormatter();
						String key=sheet.getRow(j).getCell(0).getStringCellValue();
						String value = data.formatCellValue(cell);
						hm.put(key, value);
					} else if (!cell.equals(null)&&cell.getCellType() == CellType.STRING) {
						String key=sheet.getRow(j).getCell(0).getStringCellValue();
						String value = cell.getStringCellValue();
						hm.put(key, value);
					} 
				}
				}
		}
		workbook.close();
		return hm;
	
	}

}
