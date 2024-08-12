package com.integrosys.cms.batch.npaProvision.schedular;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.ftp.CMSFtpClient;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.NpaDataLogHelper;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.OBNpaProvisionJob;

public class NpaProvisionFileWriteBatch implements INpaFileConstants {

	private final static Logger logger = LoggerFactory.getLogger(NpaProvisionFileWriteBatch.class);

	public static void main(String[] args) {

		new NpaProvisionFileWriteBatch().execute();
	}

	public NpaProvisionFileWriteBatch() {
	}

	public void execute() {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String npaProvisionServerName = bundle.getString("npa.provision.server.name");
		String npaDayOfMonth = bundle.getString("npa.provision.file.generate.dayOfMonth");
		
		System.out.println("<<<<In execute() NpaProvisionFileWriteBatch Strating....>>>>" + npaProvisionServerName);

		if (null != npaProvisionServerName && npaProvisionServerName.equalsIgnoreCase("app1")) {
			System.out.println("<<<<In execute() NpaProvisionJob npaDayOfMonth = " + npaDayOfMonth);
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			String systemDayOfMonth=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
			System.out.println("<<<<In execute() NpaProvisionFileWriteBatch systemDayOfMonth = " + systemDayOfMonth);
		//	if (null != npaDayOfMonth && npaDayOfMonth.equalsIgnoreCase(systemDayOfMonth)) {
			try {
				System.out.println("Starting NpaProvisionFileWriteBatch");
				// Fetch record for processing
				ILimitDAO dao = LimitDAOFactory.getDAO();
				/*IFileUploadJdbc jdbc=(IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
				
				System.out.println("spBaselMonthlyNpaTrackNew started:");
				jdbc.spBaselMonthlyNpaTrackNew();
				System.out.println("spBaselMonthlyNpaTrackNew finished:");*/
				
				OBNpaProvisionJob[] objList = dao.getNpaProvisionFileDetails();
				System.out.println("NpaProvisionFileWriteBatch fetched Line Details record......" + objList.length);

				if (null != objList && objList.length > 0) {
					int recordNo = 0;
					File npaFile = null;
					BufferedWriter out = null;
					FileWriter fw = null;
					String serverFilePath = bundle.getString(FTP_NPA_UPLOAD_LOCAL_DIR);
					String fileName = NPA_FILENAME;
					
					Date today = new Date();  

			       /* calendar.setTime(today);  
			        calendar.add(Calendar.MONTH, 1);  
			        calendar.set(Calendar.DAY_OF_MONTH, 1);  
			        calendar.add(Calendar.DATE, -1);  */
			        
			        Calendar aCalendar = Calendar.getInstance();
			        
			        String isOnOff = bundle.getString(NPA_PROVISION_DAILY_FILE_GENERATION_ON_OFF);
			        String specificDate = bundle.getString(NPA_PROVISION_FILE_GENERATION_DATE);
			        if("on".equalsIgnoreCase(isOnOff)) {
			        	if(specificDate!=null && !specificDate.isEmpty()) {
			        		aCalendar.setTime(new Date(specificDate));
			        	}else {
			        		aCalendar.setTime(new Date(System.currentTimeMillis()-24*60*60*1000));
			        	}
			        }else {
			        	 aCalendar.set(Calendar.DATE, 1);
			        	aCalendar.add(Calendar.DAY_OF_MONTH, -1);
			        }
			        
			        Date lastDateOfPreviousMonth = aCalendar.getTime();

			      //  Date lastDayOfMonth = calendar.getTime(); 
					String fileDate = toDateFormat(lastDateOfPreviousMonth, NPA_FILEDATEFORMAT);
					fileName = fileName + fileDate + NPA_FILEEXTENSION;
					npaFile = new File(serverFilePath + fileName);
					System.out.println("NpaProvisionFileWriteBatch FileName......" + serverFilePath + fileName);

					File dirFile = new File(serverFilePath);
					if (!dirFile.exists())
						dirFile.mkdirs();

					if(npaFile.exists())
						npaFile.delete();
					
					boolean createNewFile = npaFile.createNewFile();
					if (createNewFile == false)
						System.out.println("Error while creating new file:" + npaFile.getPath());

					fw = new FileWriter(npaFile.getAbsoluteFile(), true);
					out = new BufferedWriter(fw);

					out.write("HDR~" + fileDate);
					out.newLine();

					for (OBNpaProvisionJob ob : objList) {
						//System.out.println("NpaProvisionJob Record No. : " + recordNo);
						out = generateNpaFile(ob, recordNo, out,lastDateOfPreviousMonth);
						out.newLine();
						recordNo++;
					}

					out.write("TRL~" + recordNo);
					out.close();
					fw.close();

					// upload file to sftp
					uploadFileToSFTP(fileName, fileDate);

					// log data into table
					Date requestDate = new Date();
					NpaDataLogHelper npaDataLogHelper = new NpaDataLogHelper();
					for (OBNpaProvisionJob ob : objList) {
						npaDataLogHelper.npaDataLoggingActivity(ob, fileName, requestDate);
					}

					// moveFile(npaFile,fileName);
					// deleteOldFiles();

				}

			} catch (IOException e) {
				DefaultLogger.debug(this, "NpaProvisionJob in catch IOException......" + e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				DefaultLogger.debug(this, "NpaProvisionJob in catch Exception......" + e.getMessage());
				e.printStackTrace();
			}
		}
	 //}
		
	}

	public void uploadFileToSFTP(String npaFileName, String fileDate) throws Exception {
		DefaultLogger.debug(this, "NpaProvisionJob uploadFileToSFTP() ");
		ResourceBundle resbundle = ResourceBundle.getBundle("ofa");
		CMSFtpClient sftpClient = CMSFtpClient.getInstance("ofa", ICMSConstant.NPA_FILE_UPLOAD);
		String remoteDataDir = resbundle.getString(FTP_NPA_UPLOAD_REMOTE_DIR) + fileDate + "/";

		String localDataDir = resbundle.getString(FTP_NPA_UPLOAD_LOCAL_DIR);
		sftpClient.openConnection();
		DefaultLogger.debug(this, "NpaProvisionJob uploadFileToSFTP():SFTP connection opened() ");
		sftpClient.uploadFCUBSFile(localDataDir + npaFileName, remoteDataDir, npaFileName);
		sftpClient.closeConnection();
		DefaultLogger.debug(this, "NpaProvisionJob uploadFileToSFTP():FTP connection closed() ");

	}

	public static String toDateFormat(Date date, String formatter) throws Exception {
		try {
			if (date == null)
				return "";
			SimpleDateFormat dateFormat = new SimpleDateFormat(formatter);
			return dateFormat.format(date);
		} catch (Exception e) {
			DefaultLogger.debug("", "NpaProvisionJob toDateFormat" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	private void deleteOldFiles() {
		System.out.println("<<<<In execute() of NpaProvisionJob deleteOldFiles()");
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			String serverFilePath = bundle.getString("ftp.master.deferral.extension.backup.local.dir");
			String days = bundle.getString("ftp.master.deferral.extension.noofDays");
			long noofDays = 1;
			if (null != days && !"".equalsIgnoreCase(days)) {

				noofDays = Long.parseLong(days);
			}
			File folder = new File(serverFilePath);

			if (folder.exists()) {

				File[] listFiles = folder.listFiles();

				long eligibleForDeletion = System.currentTimeMillis() - (noofDays * 24 * 60 * 60 * 1000);

				for (File listFile : listFiles) {

					if (listFile.lastModified() < eligibleForDeletion) {

						boolean delete = listFile.delete();
						if (delete == false) {
							System.out.println("file  deletion failed for file:" + listFile.getPath());
						}
					}
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void moveFile(File sourceFile, String fileName) {
		System.out.println("<<<<In execute() of NpaProvisionJob moveFile()");
		FileInputStream inputStream = null;
		FileOutputStream outstream = null;
		try {
			inputStream = new FileInputStream(sourceFile);
			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			String destPath = bundle.getString("ftp.master.npa.provision.backup.local.dir");
			File destFolderPath = new File(destPath);
			if (!destFolderPath.exists()) {
				destFolderPath.mkdirs();
			}

			outstream = new FileOutputStream(destPath + fileName);
			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = inputStream.read(buffer)) > 0) {

				outstream.write(buffer, 0, length);

			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (inputStream != null)
					inputStream.close();

				if (outstream != null)
					outstream.close();

				boolean delete = sourceFile.delete();
				if (delete == false) {
					System.out.println("file  deletion failed for file:" + sourceFile.getPath());
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}

	private BufferedWriter generateNpaFile(OBNpaProvisionJob obj, int recordNo, BufferedWriter out,Date lastDayOfMonth) throws Exception {

		//System.out.println(".....generateNpaFile().......");
		//	String reportingDate = toDateFormat(obj.getReportingDate(), NPA_DATEFORMAT);
		String reportingDate = toDateFormat(lastDayOfMonth, NPA_DATEFORMAT);
	//	System.out.println("@@@@@@@@@@@@@@@@file reportingDate==============================================================================:"+reportingDate);
		String valuationDate = toDateFormat(obj.getValuationDate(), NPA_DATEFORMAT);

		out.write(reportingDate + "~");
		out.write(obj.getSystem() + "~");
		out.write(obj.getPartyID() + "~");
		out.write(obj.getCollateralType() + "~");
		out.write(valuationDate + "~");
		out.write(obj.getValuationAmount() + "~");
		out.write("" + "~");
		out.write("" + "~");
		out.write("" + "~");
		if(null!=obj.getErosion())
			out.write(obj.getErosion()+"");
		else
			out.write("");

		//DefaultLogger.debug(this, "NpaProvisionJob  Record" + recordNo);

		return out;
	}

}
