package com.integrosys.cms.batch.common.filereader;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.exolab.castor.xml.Unmarshaller;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.eod.bus.OBCpsToClimsMaster;
import com.integrosys.cms.app.eod.sync.bus.OBEODSyncStatus;
import com.integrosys.cms.batch.common.BatchResourceFactory;
import com.integrosys.cms.batch.common.syncmaster.datafileparser.SyncMasterTemplateIn;
import com.integrosys.cms.batch.eod.IEndOfDaySyncMastersService;
import com.integrosys.cms.batch.eod.IEodSyncConstants;

/**
 * This class process the dat file.
 * 
 * @author anil.pandey
 */
public class ProcessDataFileEOD {
	
	private boolean isValid = true;
	
	private String secondaryDelimiter;
	
	private HashMap errorList = new HashMap();
	
	private int maxCount = 0; 
	
	private String ackFileName;

	// ************** Attributes hold values read from the property file
	// ******************************/
	String dataFilePath = null;

	String dataFileFormat = null;
	String requestClassName=null;
	int rowHeaderIndex = 0;

	int rowStartIndex = 0;

	int rowEndIndex = 0;

	int columnStartIndex = 0;

	int totalColumn = 0;

	String columnsIndex = null;

	int columnLength = 0;

	String delimiter = null;
	
	String secondDelimiter = null;

	Integer maxRecProcess = null;
	
	int dataTypeRowIndex = 1;
	
	int totalRecordCount = 0;
	Long successfullRecordCount = 0l;
	Long failureRecordCount = 0l;
	Long fileRecordCount = 0l;

	Properties properties = new Properties();

	/**
	 * @return the totalRecordCount
	 */
	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	/**
	 * @param totalRecordCount the totalRecordCount to set
	 */
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	
	/**
	 * @return the successfullRecordCount
	 */
	public Long getSuccessfullRecordCount() {
		return successfullRecordCount;
	}

	/**
	 * @param successfullRecordCount the successfullRecordCount to set
	 */
	public void setSuccessfullRecordCount(Long successfullRecordCount) {
		this.successfullRecordCount = successfullRecordCount;
	}

	/**
	 * @return the failureRecordCount
	 */
	public Long getFailureRecordCount() {
		return failureRecordCount;
	}

	/**
	 * @param failureRecordCount the failureRecordCount to set
	 */
	public void setFailureRecordCount(Long failureRecordCount) {
		this.failureRecordCount = failureRecordCount;
	}
	

	/**
	 * @return the fileRecordCount
	 */
	public Long getFileRecordCount() {
		return fileRecordCount;
	}

	/**
	 * @param fileRecordCount the fileRecordCount to set
	 */
	public void setFileRecordCount(Long fileRecordCount) {
		this.fileRecordCount = fileRecordCount;
	}

	/**
	 * @return the ackFileName
	 */
	public String getAckFileName() {
		if(this.ackFileName==null){
			String dateTimeStamp = new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime())+"_"+Calendar.getInstance().getTimeInMillis();
			this.ackFileName = dateTimeStamp+"Master_Ack.dat";
		}
		return ackFileName;
	}

	/**
	 * @param ackFileName the ackFileName to set
	 */
	public void setAckFileName(String ackFileName) {
		if(ackFileName!=null){
			ackFileName=ackFileName.substring(0,ackFileName.lastIndexOf('.'));
			this.ackFileName = ackFileName+"_Ack.dat";
		}
	}

	

	// ************** Holds records processed ******************************/
	ArrayList rowArray = new ArrayList<Object>();
	
	public ArrayList processFile(File fileUpload,String master) {
		try {

			String templatePath = (new BatchResourceFactory()).getSyncMasterTemplateXmlFileName(master);
			
			FileInputStream fis = new FileInputStream(templatePath);
			InputStreamReader read = new InputStreamReader(fis);
			SyncMasterTemplateIn syncMasterTemplate = (SyncMasterTemplateIn) Unmarshaller.unmarshal(SyncMasterTemplateIn.class,read);
			
			requestClassName = syncMasterTemplate.getRequestClassName();
			dataFileFormat = syncMasterTemplate.getFileFormat();
			rowHeaderIndex = 0;
			rowStartIndex = syncMasterTemplate.getDataStartIndex().intValue();
			//DELIMITER
			delimiter = syncMasterTemplate.getDelimiter();
			//Second DELIMITER
			secondDelimiter=syncMasterTemplate.getSecondaryDelimiter();
			
			//MAX_RECORD_PROCESS
			//maxRecProcess=50;
			DATReader dat = new DATReader();
			dat.readFileDAT(fileUpload,this,syncMasterTemplate,master);
			isValid = dat.isValidationStatus();
			errorList = dat.getErrorHm();
			maxCount = dat.getMaxRowCount();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			e.printStackTrace();
			throw new ProcessFileException(e.getMessage());

		}
		return rowArray;
	}
	/**
	 * Generates the Control File Acknowledgement based on the Masters processed.
	 * 
	 * @param taskMap
	 * @param string 
	 */
	public void generateControlFileAck(HashMap<String, OBCpsToClimsMaster> taskMap, String controlFileName) {
		
		StringBuffer strAck= new StringBuffer("");
		if(taskMap!=null && !taskMap.isEmpty()){
			List<OBCpsToClimsMaster> obCpsToClimsMasterList= new ArrayList<OBCpsToClimsMaster>(taskMap.values());
			//Sorting as per row Index
			Collections.sort(obCpsToClimsMasterList,new Comparator<OBCpsToClimsMaster>() {
															public int compare(OBCpsToClimsMaster o1,
																	OBCpsToClimsMaster o2) {
																return (o1.getRowIndex()).compareTo(o2.getRowIndex());
															}
														});
			String delimeter="|||";
			String secDelimeter="~~~";
			strAck.append("Master Name|||File Name|||Record Count|||Failure~~~");
			for (OBCpsToClimsMaster cpsToClimsMaster : obCpsToClimsMasterList) {
				strAck.append("\n");
				strAck.append(cpsToClimsMaster.getMasterName());
				strAck.append(delimeter);
				if(cpsToClimsMaster.getFileName()!=null){
					strAck.append(cpsToClimsMaster.getFileName().substring(0,cpsToClimsMaster.getFileName().lastIndexOf('.'))+"_Ack.dat");
				}else{
					strAck.append(cpsToClimsMaster.getFileName());
				}
				strAck.append(delimeter);
				strAck.append(cpsToClimsMaster.getRecordCount());
				strAck.append(delimeter);
				strAck.append(cpsToClimsMaster.getFailureRecordCount());
				strAck.append(secDelimeter);
			}
		}else{
			strAck.append("No Masters found to process.");
		}
		controlFileName=controlFileName.substring(0,controlFileName.indexOf("."));
		DATReader.generateFile(IEodSyncConstants.SYNC_DIRECTION_CPSTOCLIMS,controlFileName+"_Ack.dat", strAck.toString());
		
	}

	public void generateClimsControlFile(HashMap<String, OBEODSyncStatus> climsToCPSTaskMap) {
		
		StringBuffer controlFileBuffer= new StringBuffer("");
		if(climsToCPSTaskMap!=null && !climsToCPSTaskMap.isEmpty()){
			List<OBEODSyncStatus> obClimsToCpsMasterList= new ArrayList<OBEODSyncStatus>(climsToCPSTaskMap.values());
			String delimeter="|||";
			String secDelimeter="~~~";
			controlFileBuffer.append("Master Name|||File Name|||Record Count~~~");
			for (OBEODSyncStatus cpsToClimsMaster : obClimsToCpsMasterList) {
				if(cpsToClimsMaster.getTotalCount()>0){
					controlFileBuffer.append("\n");
					controlFileBuffer.append(cpsToClimsMaster.getProcessDesc());
					controlFileBuffer.append(delimeter);
					controlFileBuffer.append(cpsToClimsMaster.getFileName());
					controlFileBuffer.append(delimeter);
					controlFileBuffer.append(cpsToClimsMaster.getTotalCount());
					controlFileBuffer.append(secDelimeter);
				}
			}
		}else{
			controlFileBuffer.append("No Masters found to process.");
		}
		String controlFileName = IEndOfDaySyncMastersService.CLIMS_CPS_CONTROL_FILE+"_"+getDateTimeStamp()+".dat";
		DATReader.generateFile(IEodSyncConstants.SYNC_DIRECTION_CLIMSTOCPS, controlFileName, controlFileBuffer.toString());
		
	}
	private String getDateTimeStamp() {
		String dateTimeStamp = new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime())+"_"+Calendar.getInstance().getTimeInMillis();
		return dateTimeStamp;
	}

	public ArrayList processAckFile(File fileUpload, String master) {
		try {

			String templatePath = PropertyManager.getValue(IEodSyncConstants.EOD_SYNC_CLIMSTOCPS_ACK_TEMPLATE_BASE_PATH)+master+"_ACK_Template.xml";;
			
			FileInputStream fis = new FileInputStream(templatePath);
			InputStreamReader read = new InputStreamReader(fis);
			SyncMasterTemplateIn syncMasterTemplate = (SyncMasterTemplateIn) Unmarshaller.unmarshal(SyncMasterTemplateIn.class,read);
			
			requestClassName = syncMasterTemplate.getRequestClassName();
			dataFileFormat = syncMasterTemplate.getFileFormat();
			rowHeaderIndex = 0;
			rowStartIndex = syncMasterTemplate.getDataStartIndex().intValue();
			//DELIMITER
			delimiter = syncMasterTemplate.getDelimiter();
			//Second DELIMITER
			secondDelimiter=syncMasterTemplate.getSecondaryDelimiter();
			
			//MAX_RECORD_PROCESS
			//maxRecProcess=50;
			DATReader dat = new DATReader();
			dat.readAckFile(fileUpload,this,syncMasterTemplate,master);
			isValid = dat.isValidationStatus();
			errorList = dat.getErrorHm();
			maxCount = dat.getMaxRowCount();
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			e.printStackTrace();
			throw new ProcessFileException(e.getMessage());

		}
		return rowArray;
	}

}
