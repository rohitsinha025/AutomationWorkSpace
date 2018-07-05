package IMAP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelToProperty {

	HashMap<String, String> properties = new HashMap<String, String>();

	public static void main(String[] args) throws IOException {

		ExcelSheetToPropertyFileConvert();
	}

	public static void ExcelSheetToPropertyFileConvert() throws IOException {
		ExcelToProperty readWriteExcelProperties = new ExcelToProperty();
		readWriteExcelProperties.readExcelFile(
				"C:\\Users\\sinhar7\\Desktop\\Rohit Materials\\Raghu Work\\IMAP_POC\\datasheets\\ObjectRepository.xls");
		readWriteExcelProperties.writeToPropertiesFile(".//src/main/java/config/objectmap.properties");
	}

	public void readExcelFile(String fileName) throws IOException {

		HSSFCell cell1 = null;
		HSSFCell cell2 = null;
		try {
			FileInputStream input = new FileInputStream(fileName);
			POIFSFileSystem fileSystem = new POIFSFileSystem(input);
			HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
			Integer count = workBook.getNumberOfSheets();
			ArrayList<String> name = new ArrayList<String>();
			ArrayList<String> value = new ArrayList<String>();
			for (int i = 0; i < count; i++) {
				HSSFSheet sheet = workBook.getSheetAt(i);
				Iterator<?> rowIterator = sheet.rowIterator();
				while (rowIterator.hasNext()) {
					HSSFRow row = (HSSFRow) rowIterator.next();
					Iterator<?> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						cell1 = (HSSFCell) cellIterator.next();
						name.add(cell1.getRichStringCellValue().toString());
						if (!cellIterator.hasNext()) {
							value.add("");
						} else {
							cell2 = (HSSFCell) cellIterator.next();
							value.add(cell2.getRichStringCellValue().toString());
						}
					}
				}
			}
			for (int i = 0; i < name.size(); i++) {
				properties.put(name.get(i), value.get(i));
			}
		} catch (Exception e) {
			System.out.println("No Such Element Exception Occured ..... ");
			e.printStackTrace();
		}
	}

	public void writeToPropertiesFile(String propertiesPath) {
		Properties props = new Properties();
		File propertiesFile = new File(propertiesPath);
		try {
			FileOutputStream xlsFos = new FileOutputStream(propertiesFile);
			Iterator<String> mapIterator = properties.keySet().iterator();
			while (mapIterator.hasNext()) {
				String key = mapIterator.next().toString();
				String value = properties.get(key);
				props.setProperty(key, value);
			}
			props.store(xlsFos, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}