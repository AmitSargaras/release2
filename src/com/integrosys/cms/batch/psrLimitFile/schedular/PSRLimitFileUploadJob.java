package com.integrosys.cms.batch.psrLimitFile.schedular;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.ftp.CMSFtpClient;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.ws.dto.PSRDataLogException;
import com.integrosys.cms.app.ws.dto.PSRDataLogHelper;

import au.com.bytecode.opencsv.CSVWriter;

public class PSRLimitFileUploadJob implements PSRFileConstants{

	ResourceBundle bundle1 = ResourceBundle.getBundle("ofa");
	
	public PSRLimitFileUploadJob() {
		
	}
	
	public static void main(String[] args) {
		new PSRLimitFileUploadJob().execute();
	}
	
	/**
	 * This job is run and executed by quartz schedular.
	 * For more details refer to schedular configuration in 
	 * config\spring\batch\psrLimitFile\AppContext_Master.xml
	 * Schedular has been designed to carry out the following activities
	 * 1. Fetch the pending released line details
	 * 2. Generate the file and add all the records as per the format shared
	 * 3. Upload the file to remote location
	 * 4. Store the records in the log table.
	 * 5.Move the file in backup folder
	 * 6.Delete file after 7 days.
	*/
	
	public void execute() {	
		
		try {
			System.out.println("Starting PSRLimitFileUploadJob......"+Calendar.getInstance().getTime());
			String psrServerName = bundle1.getString("psr.server.name");
			
			if(null!= psrServerName && psrServerName.equalsIgnoreCase("app1")){
				// Fetch record from limit table
				ILimitDAO dao = LimitDAOFactory.getDAO();
				OBCustomerSysXRef[] customerSysXRefList = dao.getLimitProfileforPSRFile();
				System.out.println("PSRLimitFileUploadJob fetched Line Details record......"+customerSysXRefList.length);
				
				//Mark SCHEDULER_STATUS=IN_PROGRESS
				for(OBCustomerSysXRef obCustomerSysXRef : customerSysXRefList)
					dao.updateStatusSchedulerProgress(obCustomerSysXRef.getXRefID(),obCustomerSysXRef.getSourceRefNo());
				
				//Generate file
				if(null!= customerSysXRefList && customerSysXRefList.length>0) {
					File psrFile = null;
					CSVWriter out = null;
					FileWriter fw = null;
					ResourceBundle bundle = ResourceBundle.getBundle("ofa");
					String serverFilePath = bundle.getString(FTP_PSR_UPLOAD_LOCAL_DIR);
					String fileName = PSR_FILENAME; 
					Date date=new Date();
					String fileDate = toDateFormat(date,PSR_FILEDATEFORMAT);
					String seqNo = dao.getSeqNoForPSRFile();
					fileName = fileName+fileDate+seqNo+PSR_FILEEXTENSION;
					psrFile = new File(serverFilePath+fileName);
					System.out.println("PSRLimitFileUploadJob FileName......"+serverFilePath+fileName);
					
					File dirFile = new File(serverFilePath);
					if(!dirFile.exists())
						dirFile.mkdirs();
					psrFile.createNewFile();
					fw = new FileWriter(psrFile.getAbsoluteFile(), true);
					out = new CSVWriter(fw,'|',CSVWriter.NO_QUOTE_CHARACTER,CSVWriter.DEFAULT_ESCAPE_CHARACTER,CSVWriter.DEFAULT_LINE_END);
					
					String header [] = new String[25];
					header[0]="CLIMS Party ID";
					header[1]="Source Ref No";
					header[2]="System ID";
					header[3]="Serial No";
					header[4]="Line No";
					header[5]="Currency";
					header[6]="Tenure";
					header[7]="Sell Down Period";
					header[8]="Released Amount";
					header[9]="Date of Request";
					header[10]="RM Name";
					header[11]="RM region";
					header[12]="CAM Start Date";
					header[13]="CAM Expiry date";
					header[14]="Approval received from CAM";
					header[15]="Liability ID";
					header[16]="Segment";
					header[17]="RAM Rating";
					header[18]="RBI Industry Code";
					header[19]="PAN No";
					header[20]="Limit Remarks";
					header[21]="Industry Description";
					header[22]="Action";
					header[23]="Maker";
					header[24]="Checker";
					
					out.writeNext(header);
					
					for(OBCustomerSysXRef obCustomerSysXRef : customerSysXRefList){
						System.out.println("PSRLimitFileUploadJob XREF ID is "+obCustomerSysXRef.getXRefID());
						out = generatePSRLimitFile(obCustomerSysXRef,out);
					}
					out.close();
					fw.close();
					
					//upload file to sftp
					uploadFileToSFTP(fileName,fileDate);
					
					//log data into table 
					Date requestDate = new Date();
					PSRDataLogHelper  psrDataLogHelper = new PSRDataLogHelper();
					for(OBCustomerSysXRef obCustomerSysXRef : customerSysXRefList){
						psrDataLogHelper.psrDataLoggingActivity(obCustomerSysXRef,fileName,requestDate);
						dao.updateStatus(obCustomerSysXRef.getXRefID(),obCustomerSysXRef.getSourceRefNo());
						dao.updateStatusSchedulerCompleted(obCustomerSysXRef.getXRefID(),obCustomerSysXRef.getSourceRefNo());
					}
					
					moveFile(psrFile,fileName);
					deleteOldFiles();
				}	
			}
			System.out.println("Ending PSRLimitFileUploadJob......");
			
		} catch (LimitException e) {
			DefaultLogger.debug(this,"PSRLimitFileUploadJob in catch LimitException......"+e.getMessage());
			e.printStackTrace();
		} catch (PSRDataLogException e) {
			DefaultLogger.debug(this,"PSRLimitFileUploadJob in catch PSRDataLogException......"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			DefaultLogger.debug(this,"PSRLimitFileUploadJob in catch IOException......"+e.getMessage());
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("PSRLimitFileUploadJob got exception....");
			e.printStackTrace();
		}
	}
	
	public static String toDateFormat(Date date, String formatter) throws Exception {
		try {
			if (date == null)
				return "";
			SimpleDateFormat dateFormat = new SimpleDateFormat(formatter);
			return dateFormat.format(date);
		} catch (Exception e) {
			System.out.println("PSRLimitFileUploadJob toDateFormat" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
	
	public void uploadFileToSFTP(String psrFile, String fileDate) throws Exception {
		System.out.println("PSRLimitFileUploadJob uploadFileToSFTP() ");
		ResourceBundle resbundle = ResourceBundle.getBundle("ofa");
		String remoteDataDir = resbundle.getString(FTP_PSR_UPLOAD_REMOTE_DIR) + fileDate + "/";
		String localDataDir = resbundle.getString(FTP_PSR_UPLOAD_LOCAL_DIR);
		CMSFtpClient sftpClient = CMSFtpClient.getInstance("ofa", ICMSConstant.PSRLIMIT_FILE_UPLOAD);
		
		sftpClient.openConnection();
		System.out.println("PSRLimitFileUploadJob uploadFileToSFTP():SFTP connection opened() ");
		sftpClient.uploadFCUBSFile(localDataDir + psrFile, remoteDataDir, psrFile);
		sftpClient.closeConnection();
		System.out.println("PSRLimitFileUploadJob uploadFileToSFTP():FTP connection closed() ");
	}
	
	public void moveFile(File sourceFile, String fileName) {
		System.out.println("PSRLimitFileUploadJob moveFile() ");
		FileInputStream inputStream = null;
		FileOutputStream outstream = null;
		try {
			inputStream = new FileInputStream(sourceFile);
			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			String destPath = bundle.getString(FTP_PSR_BACKUP_LOCAL_DIR);
			File destFolderPath = new File(destPath);
			if (!destFolderPath.exists())
				destFolderPath.mkdirs();

			outstream = new FileOutputStream(destPath+fileName);
			byte[] buffer = new byte[1024];
			int length;
			// copy the file content in bytes
			while ((length = inputStream.read(buffer)) > 0) 
				outstream.write(buffer, 0, length);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (inputStream != null)
					inputStream.close();

				if (outstream != null)
					outstream.close();

				sourceFile.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void deleteOldFiles() {
		System.out.println("PSRLimitFileUploadJob deleteOldFiles() ");
		try{
			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			String serverFilePath = bundle.getString(PSRFileConstants.FTP_PSR_BACKUP_LOCAL_DIR);
			String days = bundle.getString(PSRFileConstants.FTP_PSR_UPLOAD_NOOFDAYS);
			long noofDays = 1;
			File folder = new File(serverFilePath);
			
			if(null!= days && !"".equalsIgnoreCase(days))
				noofDays = Long.parseLong(days);

			if (folder.exists()) {
				File[] listFiles = folder.listFiles();
				long eligibleForDeletion = System.currentTimeMillis() -
						(noofDays * 24 * 60 * 60 * 1000);

				for (File listFile: listFiles) 
					if (listFile.lastModified() < eligibleForDeletion) 
						listFile.delete();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private CSVWriter generatePSRLimitFile(OBCustomerSysXRef obCustomerSysXRef,CSVWriter out) throws Exception {

		System.out.println("generatePSRLimitFile generate File ID is "+obCustomerSysXRef.getXRefID());
		DefaultLogger.debug(this,"generatePSRLimitFile generate File ID is "+obCustomerSysXRef.getXRefID());
		ILimitDAO dao = LimitDAOFactory.getDAO();
		
		Map<String,String > resultMap = dao.getDataforPSRFile(obCustomerSysXRef.getXRefID());
		
		String limitStartDate = toDateFormat(obCustomerSysXRef.getReleaseDate(),PSR_DATEFORMAT);
		String limitExpiryDate = toDateFormat(obCustomerSysXRef.getDateOfReset(),PSR_DATEFORMAT);
		String limitRemarks = "";
		
		if(null == obCustomerSysXRef.getLimitRemarks()){
			limitRemarks  = "";
		}else{
			limitRemarks = obCustomerSysXRef.getLimitRemarks().replaceAll("[\\t\\n\\r]+"," ");
		}
		
		String data [] = new String[25];

		data[0]=resultMap.get("CLIMS_PARTY_ID");
		data[1]=obCustomerSysXRef.getSourceRefNo();
		data[2]=obCustomerSysXRef.getFacilitySystemID();
		data[3]=obCustomerSysXRef.getSerialNo();
		data[4]=obCustomerSysXRef.getLineNo();
		data[5]=obCustomerSysXRef.getCurrency();
		data[6]=obCustomerSysXRef.getTenure();
		data[7]=obCustomerSysXRef.getSellDownPeriod();
		data[8]=obCustomerSysXRef.getReleasedAmount();
		data[9]=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
//		data[10]=resultMap.get("RM_NAME");
		data[10]=resultMap.get("RELATION_MGR_EMP_CODE");
		data[11]=resultMap.get("RM_REGION");
		data[12]=limitStartDate;
		data[13]=limitExpiryDate;
		data[14]="Y";
		data[15]= obCustomerSysXRef.getLiabilityId();
		data[16]=resultMap.get("PARTY_SEGMENT");
		data[17]=resultMap.get("RAM_RATING");
		data[18]=resultMap.get("RBI_IND_CODE");
		data[19]=resultMap.get("PAN_NO");
		data[20]= limitRemarks;
		data[21]=resultMap.get("INDUST_DESC"); 
		data[22]=obCustomerSysXRef.getAction();
		data[23]=obCustomerSysXRef.getCreatedBy();
		data[24]=obCustomerSysXRef.getUpdatedBy();
		
		out.writeNext(data);
		return out;
	}

	
	
	
}
