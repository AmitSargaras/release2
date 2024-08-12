package com.integrosys.cms.batch.common.filereader;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.struts.upload.FormFile;
import org.exolab.castor.xml.Unmarshaller;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.batch.common.BatchResourceFactory;
import com.integrosys.cms.batch.common.datafileparser.DataFile;

public class ProcessDataFileLeiDetails {

	
	private static final String EXCEL_FILE_FORMAT = "excel";

	private static final String FL_FILE_FORMAT = "fix_length";

	// ************** Constant attributes in the property file
	// ******************************/

	private static final String DATA_FILE_PATH = "DATA_FILE_PATH";

	private static final String DATA_FILE_FORMAT = "DATA_FILE_FORMAT";

	private static final String ROW_HEADER_INDEX = "ROW_HEADER_INDEX";

	private static final String ROW_START_INDEX = "ROW_START_INDEX";

	private static final String ROW_END_INDEX = "ROW_END_INDEX";

	private static final String COLUMN_START_INDEX = "COLUMN_START_INDEX";

	private static final String TOTAL_COLUMN = "TOTAL_COLUMN";

	private static final String COLUMNS_INDEX = "COLUMNS_INDEX";

	private static final String COLUMN_LENGTH = "COLUMN_LENGTH";

	private static final String DELIMITER = "DELIMITER";

	private static final String MAX_RECORD_PROCESS = "MAX_RECORD_PROCESS";
	
	/*Added by archana*/
	private static final String DATATYPE_ROW_INDEX ="DATATYPE_ROW_INDEX";
	
	private boolean isValid = true;
	/*Added by archana for user upload- Start*/
	private boolean userMasterValidationStatus;
	
	private String secondaryDelimiter;
	/*Added by archana for user upload- End*/
	
	private HashMap errorList = new HashMap();
	
	private int maxCount = 0; 
	
	private boolean isCheckSumFooter = false;

	// ************** Attributes hold values read from the property file
	// ******************************/
	String dataFilePath = null;

	String dataFileFormat = null;

	int rowHeaderIndex = 0;

	int rowStartIndex = 0;

	int rowEndIndex = 0;

	int columnStartIndex = 0;

	int totalColumn = 0;

	String columnsIndex = null;

	int columnLength = 0;

	String delimiter = null;

	Integer maxRecProcess = null;
	
	int dataTypeRowIndex = 1;

	Properties properties = new Properties();

	// ************** Holds records processed ******************************/
	ArrayList rowArray = new ArrayList();
	
	Map<Integer,String> columnNameValuesMap = new HashMap<Integer, String>();


	public ProcessDataFileLeiDetails() {
	}
	public ProcessDataFileLeiDetails(String propertiesFileName) {

		try {
			String propertiesFilePath = (new BatchResourceFactory()).getPropertyFileName(propertiesFileName);
			
			DefaultLogger.debug(this, "propertiesFilePath=" + propertiesFilePath);

			properties.load(new FileInputStream(propertiesFilePath));

			setDataParameter();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new ProcessFileException(e.getMessage());
			
		}
	}

	public ProcessDataFileLeiDetails(String propertiesFileName,String batchJobName) {

		try {
			String propertiesFilePath = (new BatchResourceFactory()).getPropertyFileName(propertiesFileName);
//			System.out.println("propertiesFilePath=" + propertiesFilePath);
			DefaultLogger.debug(this, "propertiesFilePath=" + propertiesFilePath);

			properties.load(new FileInputStream(propertiesFilePath));

			setDataParameter(batchJobName);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new ProcessFileException(e.getMessage());
			
		}
	}
	
	public ArrayList doProcessData() {

		if (getFileFormat().equals(EXCEL_FILE_FORMAT)) {
			LeiDetailsExcel pe = new LeiDetailsExcel();
			pe.readExcel(this);
		}
		return rowArray;
	}
	
	public ArrayList processFile(FormFile fileUpload,String master) {
			try {

				String templatePath = (new BatchResourceFactory()).getTemplateXmlFileName(master);
				
				/*InputStream is = getClass().getResourceAsStream(templatePath);
				InputStreamReader read = new InputStreamReader(is);*/
				FileInputStream fis = new FileInputStream(templatePath);
				InputStreamReader read = new InputStreamReader(fis);
				DataFile dataFile = (DataFile) Unmarshaller.unmarshal(DataFile.class,read);
				
//				dataFileFormat = "excel";
				dataFileFormat = dataFile.getFileFormat();

				// rowHeaderIndex is mandatory
				rowHeaderIndex = 0;

//				rowStartIndex = rowHeaderIndex + 1;
				rowStartIndex = dataFile.getDataStartIndex().intValue();

				
				//DELIMITER
//				delimiter="|";
				delimiter = dataFile.getDelimiter();	
				if(dataFile.getHeader().getColumn()!=null && dataFile.getHeader().getColumn().length>0){
					int columnArrayLength = dataFile.getHeader().getColumn().length;
					Map<Integer,String> colNameValsMap = new HashMap<Integer,String>();
					for(int i=0;i<columnArrayLength;i++){
							String columnName = (String)dataFile.getHeader().getColumn()[i].getName();
							colNameValsMap.put(i, columnName);
					}
					columnNameValuesMap = colNameValsMap;
				}	
					if("LeiDetailsUpload".equalsIgnoreCase(master))
					{
						LeiDetailsExcel excel = new LeiDetailsExcel();
						if(fileUpload.getFileName().endsWith(".xls") || fileUpload.getFileName().endsWith(".XLS")){
							rowArray = excel.readExcel(fileUpload,this);
						}else if(fileUpload.getFileName().endsWith(".xlsx") || fileUpload.getFileName().endsWith(".XLSX")){
							rowArray = excel.readXLSXFile(fileUpload,this);
						}
					}
			}
			catch (Exception e) {
				DefaultLogger.error(this, "", e);
				e.printStackTrace();
				throw new ProcessFileException(e.getMessage());

			}
			return rowArray;
	}

	public void setDataParameter(String batchJobName) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");	
			String date = sdf.format(new Date());
			dataFilePath = properties.getProperty(DATA_FILE_PATH+"_"+batchJobName)+"."+date;
			DefaultLogger.debug(this,"dataFilePath == " +dataFilePath);
			dataFileFormat = properties.getProperty(DATA_FILE_FORMAT+"_"+batchJobName);

			// rowHeaderIndex is mandatory
			rowHeaderIndex = Integer.parseInt(properties.getProperty(ROW_HEADER_INDEX+"_"+batchJobName));

			if ((properties.getProperty(ROW_START_INDEX+"_"+batchJobName) == null) || properties.getProperty(ROW_START_INDEX+"_"+batchJobName).equals("")) {
				// rowStartIndex default will one row next after header
				rowStartIndex = rowHeaderIndex + 1;
			}
			else {
				rowStartIndex = Integer.parseInt(properties.getProperty(ROW_START_INDEX+"_"+batchJobName));
			}

			if ((properties.getProperty(ROW_END_INDEX) != null) && !properties.getProperty(ROW_END_INDEX).equals("")) {
				// rowEndIndex - optional, if not define here, system will read
				// untill end of the data.
				rowEndIndex = Integer.parseInt(properties.getProperty("ROW_END_INDEX"));
			}

			// define where column start
			if ((properties.getProperty(COLUMN_START_INDEX) != null)
					&& !properties.getProperty(COLUMN_START_INDEX).equals("")) {
				columnStartIndex = Integer.parseInt(properties.getProperty(COLUMN_START_INDEX));
			}

			// total of column need to read
			if ((properties.getProperty(TOTAL_COLUMN) != null) && !properties.getProperty(TOTAL_COLUMN).equals("")) {
				totalColumn = Integer.parseInt(properties.getProperty(TOTAL_COLUMN));
			}

			// columnsIndex - a list of column index
			if ((properties.getProperty(COLUMNS_INDEX) != null) && !properties.getProperty(COLUMNS_INDEX).equals("")) {
				columnsIndex = properties.getProperty(COLUMNS_INDEX);
			}

			// number of length in each column for fix length file
			if ((properties.getProperty(COLUMN_LENGTH) != null) && !properties.getProperty(COLUMN_LENGTH).equals("")) {
				columnLength = Integer.parseInt(properties.getProperty(COLUMN_LENGTH));
			}

			if ((properties.getProperty(DELIMITER+"_"+batchJobName) != null) && !properties.getProperty(DELIMITER+"_"+batchJobName).equals("")) {
				delimiter = properties.getProperty(DELIMITER+"_"+batchJobName);
			}

			if ((properties.getProperty(MAX_RECORD_PROCESS) != null)
					&& !properties.getProperty(MAX_RECORD_PROCESS).equals("")) {
				maxRecProcess = new Integer(properties.getProperty(MAX_RECORD_PROCESS));
			}
			/*Added by archana*/
			if(properties.getProperty(DATATYPE_ROW_INDEX+"_"+batchJobName) != null && !properties.getProperty(DATATYPE_ROW_INDEX+"_"+batchJobName).equals("")){
				
				dataTypeRowIndex = Integer.parseInt(properties.getProperty(DATATYPE_ROW_INDEX+"_"+batchJobName));
			}

		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			e.printStackTrace();
			throw new ProcessFileException(e.getMessage());

		}
	}
	
	public void setDataParameter() {

		try {
			dataFilePath = properties.getProperty(DATA_FILE_PATH);

			dataFileFormat = properties.getProperty(DATA_FILE_FORMAT);

			// rowHeaderIndex is mandatory
			rowHeaderIndex = Integer.parseInt(properties.getProperty(ROW_HEADER_INDEX));

			if ((properties.getProperty(ROW_START_INDEX) == null) || properties.getProperty(ROW_START_INDEX).equals("")) {
				// rowStartIndex default will one row next after header
				rowStartIndex = rowHeaderIndex + 1;
			}
			else {
				rowStartIndex = Integer.parseInt(properties.getProperty(ROW_START_INDEX));
			}

			if ((properties.getProperty(ROW_END_INDEX) != null) && !properties.getProperty(ROW_END_INDEX).equals("")) {
				// rowEndIndex - optional, if not define here, system will read
				// untill end of the data.
				rowEndIndex = Integer.parseInt(properties.getProperty("ROW_END_INDEX"));
			}

			// define where column start
			if ((properties.getProperty(COLUMN_START_INDEX) != null)
					&& !properties.getProperty(COLUMN_START_INDEX).equals("")) {
				columnStartIndex = Integer.parseInt(properties.getProperty(COLUMN_START_INDEX));
			}

			// total of column need to read
			if ((properties.getProperty(TOTAL_COLUMN) != null) && !properties.getProperty(TOTAL_COLUMN).equals("")) {
				totalColumn = Integer.parseInt(properties.getProperty(TOTAL_COLUMN));
			}

			// columnsIndex - a list of column index
			if ((properties.getProperty(COLUMNS_INDEX) != null) && !properties.getProperty(COLUMNS_INDEX).equals("")) {
				columnsIndex = properties.getProperty(COLUMNS_INDEX);
			}

			// number of length in each column for fix length file
			if ((properties.getProperty(COLUMN_LENGTH) != null) && !properties.getProperty(COLUMN_LENGTH).equals("")) {
				columnLength = Integer.parseInt(properties.getProperty(COLUMN_LENGTH));
			}

			if ((properties.getProperty(DELIMITER) != null) && !properties.getProperty(DELIMITER).equals("")) {
				delimiter = properties.getProperty(DELIMITER);
			}

			if ((properties.getProperty(MAX_RECORD_PROCESS) != null)
					&& !properties.getProperty(MAX_RECORD_PROCESS).equals("")) {
				maxRecProcess = new Integer(properties.getProperty(MAX_RECORD_PROCESS));
			}
			/*Added by archana*/
			if(properties.getProperty(DATATYPE_ROW_INDEX) != null && !properties.getProperty(DATATYPE_ROW_INDEX).equals("")){
				
				dataTypeRowIndex = Integer.parseInt(properties.getProperty(DATATYPE_ROW_INDEX));
			}

		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			e.printStackTrace();
			throw new ProcessFileException(e.getMessage());

		}
	}

	public String getFileFormat() {
		return dataFileFormat;
	}

	public String getFileName() {
		return dataFilePath;
	}

	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
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
	/*Added by archana for user upload- start*/
	/**
	 * @return the userMasterValidationStatus
	 */
	public boolean isUserMasterValidationStatus() {
		return userMasterValidationStatus;
	}
	/**
	 * @param userMasterValidationStatus the userMasterValidationStatus to set
	 */
	public void setUserMasterValidationStatus(boolean userMasterValidationStatus) {
		this.userMasterValidationStatus = userMasterValidationStatus;
	}
	
	/**
	 * @return the secondaryDelimiter
	 */
	public String getSecondaryDelimiter() {
		return secondaryDelimiter;
	}
	/**
	 * @param secondaryDelimiter the secondaryDelimiter to set
	 */
	public void setSecondaryDelimiter(String secondaryDelimiter) {
		this.secondaryDelimiter = secondaryDelimiter;
	}
	
}
