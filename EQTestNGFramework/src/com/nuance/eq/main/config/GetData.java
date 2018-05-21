package com.nuance.eq.main.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import com.nuance.eq.tests.TesEQ;

/**
 * @author Swapnil Sonawane
 * @Description This class contains all the methods used to read and write data
 *              in to files
 *
 */
public class GetData {

	public final static Logger logger = Logger.getLogger(TesEQ.class.getName());

	/**
	 * @param fileName
	 * @return locator map in key value pair
	 * @Description This method reads properties file and returns data in key
	 *              value pairs
	 */
	public static Map<String, String> getProperty(String fileName) {
		logger.info("Reading Properties file " + fileName);
		Properties prop = new Properties();
		Map<String, String> map = new HashMap<String, String>();
		try {
			FileInputStream inputStream = new FileInputStream(fileName);
			prop.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Some issue finding or loading file....!!! " + fileName + " " + e.getMessage());
			Assert.fail("Some issue finding or loading file....!!! " + e.getMessage());

		}
		for (final Entry<Object, Object> entry : prop.entrySet()) {
			map.put((String) entry.getKey(), (String) entry.getValue());
		}
		return map;
	}

	/**
	 * @param file
	 * @param sheetName
	 * @return data from sheet
	 * @Description This file reads data from xlsx file and returns in array
	 */
	@SuppressWarnings("deprecation")
	public String[][] getDataFromExcel(String file, String sheetName) {
		logger.info("Reading Excel file " + file + " With Sheet Name " + sheetName);
		String tabarray[][] = null;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(file));
		} catch (FileNotFoundException e) {
			logger.info("Some issue finding or loading file....!!! " + file + " " + e.getMessage());
			Assert.fail("Some issue finding or loading file....!!! " + e.getMessage());
		}

		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(inputStream);
		} catch (IOException e) {
			logger.info("Some issue while loading file....!!! " + file + " " + e.getMessage());
			Assert.fail("Some issue while loading file....!!! " + e.getMessage());
		}
		Sheet sheet = workbook.getSheet(sheetName);

		int noOfRows = sheet.getLastRowNum();
		int noOfColumns = sheet.getRow(0).getLastCellNum();
		tabarray = new String[noOfRows][noOfColumns];
		Cell cell;
		int cellCounter = 0;
		for (int i = 1; i <= noOfRows; i++) {
			int colums = sheet.getRow(i).getLastCellNum();
			for (int ci = 0; ci < colums; ci++) {
				cell = sheet.getRow(i).getCell(ci, Row.RETURN_BLANK_AS_NULL);
				tabarray[i - 1][cellCounter] = getCellValueAsString(cell);
				cellCounter++;
			}
			cellCounter = 0;
		}
		try {
			workbook.close();
		} catch (IOException e) {
			logger.info("Some issue while closing file....!!! " + file + " " + e.getMessage());
			Assert.fail("Some issue while closding file....!!! " + e.getMessage());
		}
		try {
			inputStream.close();
		} catch (IOException e) {
			logger.info("Some issue while closing input stream....!!! " + file + " " + e.getMessage());
			Assert.fail("Some issue while closing input stream....!!! " + e.getMessage());
		}

		return tabarray;
	}

	/**
	 * @param cell
	 * @return cell value as string
	 * @Description This method takes cell and returns cell value as a string
	 */
	@SuppressWarnings("deprecation")
	public String getCellValueAsString(Cell cell) {
		String cellAsString = null;
		try {

			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				cellAsString = cell.getStringCellValue().trim();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cellAsString = new String(new Boolean(cell.getBooleanCellValue()).toString());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YY");
					cellAsString = sdf.format(cell.getDateCellValue());
				} else {
					Double value = cell.getNumericCellValue();
					Long hhh = value.longValue();
					cellAsString = new String(hhh.toString());
				}
				break;
			case Cell.CELL_TYPE_BLANK:
				cellAsString = " ";
				break;
			}
		} catch (Exception e) {
			logger.info("Some issue while converting excel data to string");
			Assert.fail("Some issue while converting excel data to string");
		}
		return cellAsString;
	}

	/**
	 * @param data
	 * @return rows with run mode yes
	 * @Description this method takes excel data and returns data with run mode
	 *              yes
	 */
	public String[][] getDataWithYesRunMode(String[][] data) {
		String[][] dataWithYesOption;
		int yesOptionCounter = 0;
		int columnCounter = 0;
		for (int i = 0; i < data.length; i++) {
			if (data[i][Constants.runModeIndex].equalsIgnoreCase("yes")) {
				yesOptionCounter++;
				columnCounter = data[i].length;
			}
		}

		dataWithYesOption = new String[yesOptionCounter][columnCounter];
		for (int i = 0; i < data.length; i++) {
			if (data[i][Constants.runModeIndex].equalsIgnoreCase("yes")) {

				for (int k = 0; k < yesOptionCounter; k++) {
					if (dataWithYesOption[k][1] == null) {
						dataWithYesOption[k] = data[i];
						break;
					}
				}
			}
		}

		logger.info("No of rows with 'Yes' Option=" + yesOptionCounter);
		if (columnCounter == 0) {
			Assert.fail("No rows configured with Run Mode Yes");
		}
		return dataWithYesOption;
	}
	
	
	/**
	 * @param file
	 * @param sheetName
	 * @param rowNumber
	 * @return data from sheet
	 * @Description This file reads data from xlsx file and returns in array
	 */
	@SuppressWarnings("deprecation")
	public String[][] getDataFromExcelByRow(String file, String sheetName,int rowNumber) {
		logger.info("Reading Excel file " + file + " With Sheet Name " + sheetName);
		String tabarray[][] = null;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(file));
		} catch (FileNotFoundException e) {
			logger.info("Some issue finding or loading file....!!! " + file + " " + e.getMessage());
			Assert.fail("Some issue finding or loading file....!!! " + e.getMessage());
		}

		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(inputStream);
		} catch (IOException e) {
			logger.info("Some issue while loading file....!!! " + file + " " + e.getMessage());
			Assert.fail("Some issue while loading file....!!! " + e.getMessage());
		}
		Sheet sheet = workbook.getSheet(sheetName);

		int noOfRows = sheet.getLastRowNum();
		int noOfColumns = sheet.getRow(0).getLastCellNum();
		tabarray = new String[1][noOfColumns];
		Cell cell;
		int cellCounter = 0;
		
		for (int i = 1; i <2; i++) {
			int colums = sheet.getRow(i).getLastCellNum();
			for (int ci = 0; ci < colums; ci++) {
				cell = sheet.getRow(rowNumber).getCell(ci, Row.RETURN_BLANK_AS_NULL);
				tabarray[i - 1][cellCounter] = getCellValueAsString(cell);
				cellCounter++;
			}
			cellCounter = 0;
		}
		try {
			workbook.close();
		} catch (IOException e) {
			logger.info("Some issue while closing file....!!! " + file + " " + e.getMessage());
			Assert.fail("Some issue while closding file....!!! " + e.getMessage());
		}
		try {
			inputStream.close();
		} catch (IOException e) {
			logger.info("Some issue while closing input stream....!!! " + file + " " + e.getMessage());
			Assert.fail("Some issue while closing input stream....!!! " + e.getMessage());
		}

		return tabarray;
	}

}
