package com.integrosys.cms.batch.common.filereader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.upload.FormFile;
import org.exolab.castor.xml.Unmarshaller;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.batch.common.BatchResourceFactory;
import com.integrosys.cms.batch.common.datafileparser.DataFile;

public class ProcessDataFileStockDetails {
	String EXCEL_FILE_FORMAT = "excel";
	String FL_FILE_FORMAT = "fix_length";
	String DATA_FILE_PATH = "DATA_FILE_PATH";
	String DATA_FILE_FORMAT = "DATA_FILE_FORMAT";
	String ROW_HEADER_INDEX = "ROW_HEADER_INDEX";
	String ROW_START_INDEX = "ROW_START_INDEX";
	String ROW_END_INDEX = "ROW_END_INDEX";
	String COLUMN_START_INDEX = "COLUMN_START_INDEX";
	String TOTAL_COLUMN = "TOTAL_COLUMN";
	String COLUMNS_INDEX = "COLUMNS_INDEX";
	String COLUMN_LENGTH = "COLUMN_LENGTH";
	String DELIMITER = "DELIMITER";
	String MAX_RECORD_PROCESS = "MAX_RECORD_PROCESS";
	String DATATYPE_ROW_INDEX ="DATATYPE_ROW_INDEX";
	
	private boolean isValid = true;
	private boolean userMasterValidationStatus;
	private String secondaryDelimiter;
	private HashMap errorList = new HashMap();
	private int maxCount = 0; 
	private boolean isCheckSumFooter = false;
	private String dataFilePath = null;
	private String dataFileFormat = null;
	private int rowHeaderIndex = 0;
	private int rowStartIndex = 0;
	private int rowEndIndex = 0;
	private int columnStartIndex = 0;
	private int totalColumn = 0;
	private String columnsIndex = null;
	private int columnLength = 0;
	private String delimiter = null;
	private Integer maxRecProcess = null;
	private int dataTypeRowIndex = 1;
	private Properties properties = new Properties();
	private List<List<Cell>> rowArray = new ArrayList<List<Cell>>();
	private Map<Integer,String> columnNameValuesMap = new HashMap<Integer, String>();
	
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public boolean isUserMasterValidationStatus() {
		return userMasterValidationStatus;
	}
	public void setUserMasterValidationStatus(boolean userMasterValidationStatus) {
		this.userMasterValidationStatus = userMasterValidationStatus;
	}

	public String getSecondaryDelimiter() {
		return secondaryDelimiter;
	}
	public void setSecondaryDelimiter(String secondaryDelimiter) {
		this.secondaryDelimiter = secondaryDelimiter;
	}

	public HashMap getErrorList() {
		return errorList;
	}
	public void setErrorList(HashMap errorList) {
		this.errorList = errorList;
	}

	public int getMaxCount() {
		return maxCount;
	}
	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount; 
	}

	public boolean isCheckSumFooter() {
		return isCheckSumFooter;
	}
	public void setCheckSumFooter(boolean isCheckSumFooter) {
		this.isCheckSumFooter = isCheckSumFooter;
	}

	public String getFileName() {
		return dataFilePath;
	}
	public void setFileName(String fileName) {
		this.dataFilePath = fileName;
	}

	public String getDataFileFormat() {
		return dataFileFormat;
	}
	public void setDataFileFormat(String dataFileFormat) {
		this.dataFileFormat = dataFileFormat;
	}

	public int getRowHeaderIndex() {
		return rowHeaderIndex;
	}
	public void setRowHeaderIndex(int rowHeaderIndex) {
		this.rowHeaderIndex = rowHeaderIndex;
	}

	public int getRowStartIndex() {
		return rowStartIndex;
	}
	public void setRowStartIndex(int rowStartIndex) {
		this.rowStartIndex = rowStartIndex;
	}

	public int getRowEndIndex() {
		return rowEndIndex;
	}
	public void setRowEndIndex(int rowEndIndex) {
		this.rowEndIndex = rowEndIndex;
	}

	public int getColumnStartIndex() {
		return columnStartIndex;
	}
	public void setColumnStartIndex(int columnStartIndex) {
		this.columnStartIndex = columnStartIndex;
	}

	public int getTotalColumn() {
		return totalColumn;
	}
	public void setTotalColumn(int totalColumn) {
		this.totalColumn = totalColumn;
	}

	public String getColumnsIndex() {
		return columnsIndex;
	}
	public void setColumnsIndex(String columnsIndex) {
		this.columnsIndex = columnsIndex;
	}

	public int getColumnLength() {
		return columnLength;
	}
	public void setColumnLength(int columnLength) {
		this.columnLength = columnLength;
	}

	public String getDelimiter() {
		return delimiter;
	}
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public Integer getMaxRecProcess() {
		return maxRecProcess;
	}
	public void setMaxRecProcess(Integer maxRecProcess) {
		this.maxRecProcess = maxRecProcess;
	}

	public int getDataTypeRowIndex() {
		return dataTypeRowIndex;
	}
	public void setDataTypeRowIndex(int dataTypeRowIndex) {
		this.dataTypeRowIndex = dataTypeRowIndex;
	}

	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public List<List<Cell>> getRowArray() {
		return rowArray;
	}
	public void setRowArray(List<List<Cell>> rowArray) {
		this.rowArray = rowArray;

	}

	public Map<Integer, String> getColumnNameValuesMap() {
		return columnNameValuesMap;
	}

	public void setColumnNameValuesMap(Map<Integer, String> columnNameValuesMap) {
		this.columnNameValuesMap = columnNameValuesMap;
	}

	public List<List<Cell>> processFile(FormFile fileUpload, String master) {
		try {
			String templatePath = (new BatchResourceFactory()).getTemplateXmlFileName(master);
			FileInputStream fis = new FileInputStream(templatePath);
			InputStreamReader read = new InputStreamReader(fis);
			DataFile dataFile = (DataFile) Unmarshaller.unmarshal(DataFile.class,read);
			
			setDataFileFormat(dataFile.getFileFormat());
			setRowHeaderIndex(0);
			setRowStartIndex(dataFile.getDataStartIndex());
			setDelimiter(dataFile.getDelimiter());
			
			if(dataFile.getHeader().getColumn()!=null && dataFile.getHeader().getColumn().length>0){
				int columnArrayLength = dataFile.getHeader().getColumn().length;
				Map<Integer,String> colNameValsMap = new HashMap<Integer,String>();
				for(int i=0;i<columnArrayLength;i++){
						String columnName = (String)dataFile.getHeader().getColumn()[i].getName();
						colNameValsMap.put(i, columnName);
				}
				setColumnNameValuesMap(colNameValsMap);
			}
			
			if(fileUpload.getFileName().endsWith(".xls") || fileUpload.getFileName().endsWith(".XLS")){
				setRowArray(ProcessDataFileUploadHelper.readExcel(fileUpload));
			}else if(fileUpload.getFileName().endsWith(".xlsx") || fileUpload.getFileName().endsWith(".XLSX")){
				setRowArray(ProcessDataFileUploadHelper.readXLSXFile(fileUpload));
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			e.printStackTrace();
			throw new ProcessFileException(e.getMessage());

		}
		return getRowArray();

	}

}
