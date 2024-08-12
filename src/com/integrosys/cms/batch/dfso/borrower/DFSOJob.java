package com.integrosys.cms.batch.dfso.borrower;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.ftp.CMSFtpClient;
import com.integrosys.cms.app.json.dto.OBJsInterfaceLog;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;

import au.com.bytecode.opencsv.CSVWriter;

public class DFSOJob implements IBorrower {

	private final static Logger logger = LoggerFactory.getLogger(DiscrepencyDeferralGeneralJob.class);

	public static void main(String[] args) {

		new DiscrepencyDeferralGeneralJob().execute();
	}

	public DFSOJob() {
	}

	public void execute() {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String dfsoServerName = bundle.getString("dfso.server.name");
		String dfsoChanges = bundle.getString("dfso.changes");

		System.out.println("<<<<In execute() DFSOJob Strating....>>>>" + dfsoServerName + " " + dfsoChanges);

		if (null != dfsoChanges && "Y".equalsIgnoreCase(dfsoChanges) && null != dfsoServerName
				&& "app1".equalsIgnoreCase(dfsoServerName)) {
			try {
				System.out.println("Starting DFSOJob");
				ILimitDAO dao = LimitDAOFactory.getDAO();

				File dfsoFile = null;
				CSVWriter out = null;
				FileWriter fw = null;

				String serverFilePath = bundle.getString(FTP_DFSO_JOB_UPLOAD_LOCAL_DIR);
				String fileName = DFSO_JOB_FILENAME;
				Date date = new Date();
				Date dateBefore = new Date(date.getTime() - 1 * 24 * 3600 * 1000); // Subtract n days
				System.out.println("dateBefore:: " + dateBefore);
				String fileDate = toDateFormat(dateBefore, DFSO_JOB_FILEDATEFORMAT);

				fileName = fileName + fileDate + DFSO_JOB_FILEEXTENSION;
				System.out.println("fileName:: " + fileName);

				dfsoFile = new File(serverFilePath + fileName);
				System.out.println("dfso Job" + serverFilePath + fileName);

				if (dfsoFile.exists()) {
					dfsoFile.delete();
				}
				File dirFile = new File(serverFilePath);
				if (!dirFile.exists())
					dirFile.mkdirs();

				boolean createNewFile = dfsoFile.createNewFile();
				if (createNewFile == false)
					System.out.println("Error while creating new file:" + dfsoFile.getPath());

				fw = new FileWriter(dfsoFile.getAbsoluteFile(), true);
				out = new CSVWriter(fw, '|', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER,
						CSVWriter.DEFAULT_LINE_END);

				String header[] = new String[75];
				header[0] = "Party ID (CLIMS No.)";
				header[1] = "Branch Code";
				header[2] = "Party Name";
				header[3] = "DUNS Number";
				header[4] = "Borrower Legal Constitution";
				header[5] = "Borrower - Address Line 1";
				header[6] = "Borrower - Address Line 2";
				header[7] = "Borrower - Address Line 3";
				header[8] = "Borrower - City";
				header[9] = "Borrower - State";
				header[10] = "Borrower - Pincode";
				header[11] = "Borower - Telephone Number";
				header[12] = "Borrower - PAN";
				header[13] = "Class Of Activity 1";
				header[14] = "Class Of Activity 2";
				header[15] = "Region";
				header[16] = "RM Name";
				header[17] = "Segment";
				header[18] = "Sanction Amount";
				header[19] = "Sanction Date";
				header[20] = "Guarantor Name";
				header[21] = "Guarantor Type";
				header[22] = "Guarantor Nature";
				header[23] = "Guarantor - Address";
				header[24] = "Guarantor - City";
				header[25] = "Guarantor - State";
				header[26] = "Guarantor - PinCode";
				header[27] = "Guarantor - Country";
				header[28] = "Guarantor PAN";
				header[29] = "Guarantors DUNS Number";
				header[30] = "Guarantor Networth";
				header[31] = "Guarantee - Security Sub Type";
				header[32] = "Relationship(number) ";
				header[33] = "Full Name - Relationship";
				header[34] = "Relationship - Address 1";
				header[35] = "Relationship - Address 2";
				header[36] = "Relationship - Address 3";
				header[37] = "Relationship - City";
				header[38] = "Relationship - State";
				header[39] = "Relationship - Pincode";
				header[40] = "Relationship - Country";
				header[41] = "Relationship - Telephone Number";
				header[42] = "Relationship - DIN No / PAN No.";
				header[43] = "FACILITYNAME";
				header[44] = "COLLATERAL_NAME";
				header[45] = "SECURITYAMOUNT";
				header[46] = "VALUATIONAMT";
				header[47] = "DATEOFVAL";
				header[48] = "TYPEOFCHARGE";
				header[49] = "Security ID";
				header[50] = "Property Address";
				header[51] = "Property - Country";
				header[52] = "Property - State";
				header[53] = "Property - City";
				header[54] = "Property - Pin Code";
				header[55] = "Property - Vault Location";
				header[56] = "Property - Security Priority";
				header[57] = "Type Of Mortgage";
				header[58] = "Date Of Mortgage";
				header[59] = "Mortgage Registered Reference";
				header[60] = "Property Owner Name";
				header[61] = "Property Type";
				header[62] = "Property - Valuation Date";
				header[63] = "Total Property Amount";
				header[64] = "Property - Valuation Company";
				header[65] = "Property - Built-up Area";
				header[66] = "Property - Land Area";
				header[67] = "Advocate/Lawyer Name";
				header[68] = "Property - Cersai ID";
				header[69] = "Property - Type Of Charge";
				header[70] = "Property - Date of TSR";
				header[71] = "Property - Date of CERSAI Registration";
				header[72] = "Property - Land Value";
				header[73] = "Property - Building Value";
				header[74] = "Property - Reconstruction Value";

				out.writeNext(header);

				List dfsoList = dao.getDFSOJobDetails();
				if (dfsoList != null) {
					for (int i = 0; i < dfsoList.size(); i++) {
						String[] arr = (String[]) (dfsoList.get(i));
						// System.out.println( " PARTI ID ::"+ arr[0]+ " PARTY NAME :: "+arr[1]);
						out.writeNext(arr);
					}

					out.close();
					fw.close();
					System.out.println("DFSO File Created successfully..............!!!!");

					OBDFSOLog log = new OBDFSOLog();
					log.setFileName(fileName);
					// upload file to sftp
					try {
						uploadFileToSFTP(fileName, fileDate, serverFilePath);
						log.setStatus("File updated successfully");
						log.setUploadTime(new Date());
						System.out.println("DFSO SFTP File Uploaded successfully....!!!!");
					} catch (Exception e) {
						System.out.println("Exception in uploadFileToSFTP DFSOJob.java  e=>" + e);
						e.printStackTrace();
						log.setStatus("File Failed to Upload.");
					}
					System.out.println("Before DFSO Data log activity");
					log.setUploadTime(new Date());
					dao.insertLogForDFSO(log);
					System.out.println("After DFSO Data log activity");
					moveFile(dfsoFile, fileName);
					deleteOldFiles();
				}

			} catch (IOException e) {
				DefaultLogger.debug(this, "DFSO Job in catch IOException......" + e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				DefaultLogger.debug(this, "DFSO Job in catch Exception......" + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public void uploadFileToSFTP(String dfsoFileName, String fileDate, String serverFilePath) throws Exception {

		System.out.println("Uploading generated DFSO file to SFTP location");
		logger.info("Uploading generated DFSO file to SFTP location");
		String connectionFor = "";
		connectionFor = ICMSConstant.DFSO_JOB_UPLOAD;
		ResourceBundle resbundle = ResourceBundle.getBundle("ofa");
		String remoteDataDir = resbundle.getString(FTP_DFSO_JOB_UPLOAD_REMOTE_DIR);

		System.out.println("remoteDataDir:: " + remoteDataDir);
		CMSFtpClient sftpClient = CMSFtpClient.getInstance("ofa", connectionFor);
		System.out.println("After CMSFtpClient.getInstance...");

		sftpClient.openConnection();
		System.out.println("Uploading generated DFSO file to SFTP location and paths :localFilePath=>" + serverFilePath
				+ dfsoFileName + "  remoteDataDir=>" + remoteDataDir);
		logger.info("SFTP connection was opened for file transfer");

		sftpClient.uploadFileNew(serverFilePath + dfsoFileName, remoteDataDir);

		sftpClient.closeConnection();

		System.out.println("Uploading generated file to SFTP location");
		logger.info("SFTP connection successfully closed after file transfer");
	}

	public static String toDateFormat(Date date, String formatter) throws Exception {
		try {
			if (date == null)
				return "";
			SimpleDateFormat dateFormat = new SimpleDateFormat(formatter);
			return dateFormat.format(date);
		} catch (Exception e) {
			DefaultLogger.debug("", "DiscrepencyDeferralGeneral toDateFormat" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	private void deleteOldFiles() {
		System.out.println("<<<<In execute() of DFSO Job deleteOldFiles()");
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			String serverFilePath = bundle.getString("ftp.dfso.backup.local.dir");
			String days = bundle.getString("ftp.dfso.noofDays");
			long noofDays = 1;
			if (null != days && !"".equalsIgnoreCase(days)) {

				noofDays = Long.parseLong(days);
				System.out.println("noofDays:: " + noofDays);
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
		System.out.println("<<<<In execute() of DFSO Job moveFile()");
		FileInputStream inputStream = null;
		FileOutputStream outstream = null;
		try {
			inputStream = new FileInputStream(sourceFile);
			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			String destPath = bundle.getString("ftp.dfso.backup.local.dir");
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
					System.out.println("DFSO file  deletion failed for file:" + sourceFile.getPath());
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}

}
