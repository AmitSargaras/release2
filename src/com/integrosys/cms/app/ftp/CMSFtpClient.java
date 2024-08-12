package com.integrosys.cms.app.ftp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;

import com.integrosys.base.techinfra.crypto.CryptoManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.batch.fcc.col.liquidation.fd.upload.FccColFdFileUploadConstant;
import com.integrosys.cms.batch.mtmvalprocess.fd.upload.FixedDepositFileUploadConstant;
import com.integrosys.cms.batch.mtmvalprocess.mfequity.upload.MfEquityFileUploadConstant;
import com.integrosys.cms.batch.mtmvalprocess.sbbgsblc.upload.SBBGSBLCFileUploadConstant;





public abstract class CMSFtpClient {

	protected String username = null;
	protected String password = null;

	protected String server = null;
	protected int port = 0;

	protected ResourceBundle resourceBundle;
	protected Properties ftpPasswordProperties = new Properties();
	protected Properties ftpPosidexPasswordProperties = new Properties();
	protected Properties ftpFCUBSPasswordProperties = new Properties();
	
	protected boolean getFieldValueBoolean(String name) {
		if (resourceBundle.containsKey(name)) {
			return "true".equalsIgnoreCase(resourceBundle.getString(name).trim());
		} else {
			return false;
		}

	}

	protected String getFieldValue(String name) {
		if (resourceBundle.containsKey(name)) {
			return resourceBundle.getString(name).trim();
		} else {
			return null;
		}

	}

	public CMSFtpClient(ResourceBundle ftpProperty,String connectionFor) {
		this.resourceBundle = ftpProperty;
		DefaultLogger.debug(this,"Indide CMSFtpClient constructor connectionFor :"+connectionFor);
		System.out.println("Indide CMSFtpClient constructor connectionFor :"+connectionFor);
		if(ICMSConstant.SYSTEM_FILE_UPLOAD.equals(connectionFor)){
			System.out.println("Indide SYSTEM_FILE_UPLOAD if condition.");
			
			try {
				this.ftpPasswordProperties.load(new FileInputStream(PropertyManager.getValue("ftp.upload.password.filepath")));
			} catch (FileNotFoundException e1) {
				DefaultLogger.debug(this, "Unable to load ftp password file :"+getFieldValue("ftp.upload.password.filepath"));
				e1.printStackTrace();
			} catch (IOException e1) {
				DefaultLogger.debug(this, "Unable to load ftp password file :"+getFieldValue("ftp.upload.password.filepath"));
				e1.printStackTrace();
			}
			if (getFieldValueBoolean("ftp.upload.anonymous")) {
				username = "anonymous";
				try {
					password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					System.err.println("Error while getting localhost - hostname!");
					e.printStackTrace();
				}
			} else {
				username = getFieldValue("ftp.upload.username");
				password = this.ftpPasswordProperties.getProperty("ftp.upload.password");
				//System.out.println("Before decrypt password :"+password);
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				password = sec.decrypt(password);
				//System.out.println("After decrypt password :"+password);
				
			}

			server = getFieldValue("ftp.upload.server");
			if (getFieldValue("ftp.upload.port") != null) {
				port = Integer.parseInt(getFieldValue("ftp.upload.port"));
			}

		}else if(ICMSConstant.MASTER_SYNC_UP.equals(connectionFor)){
			DefaultLogger.debug(this,"Indide MASTER_SYNC_UP if condition.");
			System.out.println("Indide MASTER_SYNC_UP if condition.");
//			try {
//				this.ftpPasswordProperties.load(new FileInputStream(PropertyManager.getValue("ftp.password.filepath")));
//			} catch (FileNotFoundException e1) {
//				DefaultLogger.error(this, "Unable to load ftp password file :"+getFieldValue("ftp.password.filepath"), e1);
//			} catch (IOException e1) {
//				DefaultLogger.error(this, "Unable to load ftp password file :"+getFieldValue("ftp.password.filepath"), e1);
//			}
			if (getFieldValueBoolean("ftp.anonymous")) {
				username = "anonymous";
				try {
					password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					System.err.println("Error while getting localhost - hostname!");
					e.printStackTrace();
				}
			} else {
				username = getFieldValue("ftp.username");
				//password = this.ftpPasswordProperties.getProperty("ftp.password");
				password = getFieldValue("ftp.password");
			//	DefaultLogger.debug(this, "encryption: " + PropertyManager.getValue("encryption.provider")
			//	+ ", username: " + username + ", password: " + password);
		
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				password = sec.decrypt(password);
			}

			server = getFieldValue("ftp.server");
			if (getFieldValue("ftp.port") != null) {
				port = Integer.parseInt(getFieldValue("ftp.port"));
			}
		}else if(ICMSConstant.POSIDEX_FILE_UPLOAD.equals(connectionFor)){
			DefaultLogger.debug(this,"Indide POSIDEX_FILE_UPLOAD if condition.");
			System.out.println("Indide POSIDEX_FILE_UPLOAD if condition.");
			try {
				this.ftpPosidexPasswordProperties.load(new FileInputStream(PropertyManager.getValue("ftp.posidex.password.filepath")));
			} catch (FileNotFoundException e1) {
				DefaultLogger.debug(this, "Unable to load ftp password file :"+getFieldValue("ftp.posidex.password.filepath"));
				e1.printStackTrace();
			} catch (IOException e1) {
				DefaultLogger.debug(this, "Unable to load ftp password file :"+getFieldValue("ftp.posidex.password.filepath"));
				e1.printStackTrace();
			}
			if (getFieldValueBoolean("ftp.posidex.anonymous")) {
				username = "anonymous";
				try {
					password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					System.err.println("Error while getting localhost - hostname!");
					e.printStackTrace();
				}
			} else {
				username = getFieldValue("ftp.posidex.username");
				password = this.ftpPosidexPasswordProperties.getProperty("ftp.posidex.password");
				//System.out.println("Before decrypt password :"+password);
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				password = sec.decrypt(password);
				//System.out.println("After decrypt password :"+password);
				
			}

			server = getFieldValue("ftp.posidex.server");
			if (getFieldValue("ftp.posidex.port") != null) {
				port = Integer.parseInt(getFieldValue("ftp.posidex.port"));
			}
		}
		
		else if(ICMSConstant.FCUBSLIMIT_FILE_UPLOAD.equals(connectionFor)){
			DefaultLogger.debug(this,"Inside FCUBS_FILE_UPLOAD if condition.");
			System.out.println("Inside FCUBS_FILE_UPLOAD if condition.");
			/*try {
				this.ftpFCUBSPasswordProperties.load(new FileInputStream(PropertyManager.getValue("ftp.fcubslimit.password.filepath")));
			} catch (FileNotFoundException e1) {
				DefaultLogger.debug(this, "Unable to load ftp password file :"+getFieldValue("ftp.fcubslimit.password.filepath"));
				e1.printStackTrace();
			} catch (IOException e1) {
				DefaultLogger.debug(this, "Unable to load ftp password file :"+getFieldValue("ftp.fcubslimit.password.filepath"));
				e1.printStackTrace();
			}*/
			if (getFieldValueBoolean("ftp.fcubslimit.anonymous")) {
				username = "anonymous";
				try {
					password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					System.err.println("Error while getting localhost - hostname!");
					e.printStackTrace();
				}
			} else {
				username = getFieldValue("ftp.fcubslimit.username");
				//password = this.ftpFCUBSPasswordProperties.getProperty("ftp.fcubs.password");
				password = getFieldValue("ftp.fcubslimit.password");
				//System.out.println("Before decrypt password :"+password);
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				password = sec.decrypt(password);
				//System.out.println("After decrypt password :"+password);
				
			}

			server = getFieldValue("ftp.fcubslimit.server");
			if (getFieldValue("ftp.fcubslimit.port") != null) {
				port = Integer.parseInt(getFieldValue("ftp.fcubslimit.port"));
			}
		}
		else if(ICMSConstant.DFSO_UPLOAD.equals(connectionFor)){
			DefaultLogger.debug(this,"Inside DFSO_FILE_UPLOAD if condition.");
			System.out.println("Inside DFSO_FILE_UPLOAD if condition.");
			if (getFieldValueBoolean("ftp.dfso.borrower.anonymous")) {
				username = "anonymous";
				try {
					password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					System.err.println("Error while getting localhost - hostname!");
					e.printStackTrace();
				}
			} else {
				username = getFieldValue("ftp.dfso.stockDeferral.username");
				//password = this.ftpFCUBSPasswordProperties.getProperty("ftp.fcubs.password");
				password = getFieldValue("ftp.dfso.stockDeferral.password");
				//System.out.println("Before decrypt password :"+password);
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				password = sec.decrypt(password);
				//System.out.println("After decrypt password :"+password);
			}
			server = getFieldValue("ftp.dfso.borrower.server");
			if (getFieldValue("ftp.dfso.borrower.port") != null) {
				port = Integer.parseInt(getFieldValue("ftp.dfso.borrower.port"));
			}
		}
		else if(ICMSConstant.DFSO_JOB_UPLOAD.equals(connectionFor)){
			DefaultLogger.debug(this,"Inside DFSO_JOB_FILE_UPLOAD if condition.");
			System.out.println("Inside DFSO_JOB_FILE_UPLOAD if condition.");
			if (getFieldValueBoolean("ftp.dfso.anonymous")) {
				username = "anonymous";
				try {
					password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					System.err.println("Error while getting localhost - hostname!");
					e.printStackTrace();
				}
			} else {
				username = getFieldValue("ftp.dfso.username");
				//password = this.ftpFCUBSPasswordProperties.getProperty("ftp.fcubs.password");
				password = getFieldValue("ftp.dfso.password");
				//System.out.println("Before decrypt password :"+password);
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				password = sec.decrypt(password);
				//System.out.println("After decrypt password :"+password);
			}
			server = getFieldValue("ftp.dfso.server");
			if (getFieldValue("ftp.dfso.port") != null) {
				port = Integer.parseInt(getFieldValue("ftp.dfso.port"));
			}
		}
		else if(ICMSConstant.NPA_FILE_UPLOAD.equals(connectionFor)){
			DefaultLogger.debug(this,"Inside NPA_FILE_UPLOAD if condition.");
			System.out.println("Inside NPA_FILE_UPLOAD if condition.");
			
			if (getFieldValueBoolean("ftp.npa.anonymous")) {
				username = "anonymous";
				try {
					password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					System.err.println("Error while getting localhost - hostname!");
					e.printStackTrace();
				}
			} else {
				username = getFieldValue("ftp.npa.username");
				//password = this.ftpFCUBSPasswordProperties.getProperty("ftp.fcubs.password");
				password = getFieldValue("ftp.npa.password");
				System.out.println("Before decrypt password :"+password);
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				password = sec.decrypt(password);
				System.out.println("After decrypt password :"+password);
				
			}

			server = getFieldValue("ftp.npa.server");
			if (getFieldValue("ftp.npa.port") != null) {
				port = Integer.parseInt(getFieldValue("ftp.npa.port"));
			}
		}
		else if(ICMSConstant.NPA_DAILY_STAMPING_FILE_UPLOAD.equals(connectionFor)){
			DefaultLogger.debug(this,"Inside NPA_DAILY_STAMPING_FILE_UPLOAD if condition.");
			System.out.println("Inside NPA_DAILY_STAMPING_FILE_UPLOAD if condition.");
			
			if (getFieldValueBoolean("ftp.npa.dailyStamping.anonymous")) {
				username = "anonymous";
				try {
					password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					System.err.println("Error while getting localhost - hostname!");
					e.printStackTrace();
				}
			} else {
				username = getFieldValue("ftp.npa.dailyStamping.username");
				password = getFieldValue("ftp.npa.dailyStamping.password");
				System.out.println("username :: "+username  + "  Before decrypt password :: "+password);
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				System.out.println("After CryptoManager class");
				password = sec.decrypt(password);
				System.out.println("After decrypt password :"+password);
				
			}

			server = getFieldValue("ftp.npa.dailyStamping.server");
			if (getFieldValue("ftp.npa.dailyStamping.port") != null) {
				port = Integer.parseInt(getFieldValue("ftp.npa.dailyStamping.port"));
			}
		}

		else if(ICMSConstant.PSRLIMIT_FILE_UPLOAD.equals(connectionFor)){
			DefaultLogger.debug(this,"Inside PSRLIMIT_FILE_UPLOAD if condition.");
			System.out.println("Inside PSRLIMIT_FILE_UPLOAD if condition.");
			/*try {
				this.ftpFCUBSPasswordProperties.load(new FileInputStream(PropertyManager.getValue("ftp.fcubslimit.password.filepath")));
			} catch (FileNotFoundException e1) {
				DefaultLogger.debug(this, "Unable to load ftp password file :"+getFieldValue("ftp.fcubslimit.password.filepath"));
				e1.printStackTrace();
			} catch (IOException e1) {
				DefaultLogger.debug(this, "Unable to load ftp password file :"+getFieldValue("ftp.fcubslimit.password.filepath"));
				e1.printStackTrace();
			}*/
			if (getFieldValueBoolean("ftp.psrlimit.anonymous")) {
				username = "anonymous";
				try {
					password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					System.err.println("Error while getting localhost - hostname!");
					e.printStackTrace();
				}
			} else {
				username = getFieldValue("ftp.psrlimit.username");
				//password = this.ftpFCUBSPasswordProperties.getProperty("ftp.fcubs.password");
				password = getFieldValue("ftp.psrlimit.password");
				System.out.println("Before decrypt password :"+password);
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				password = sec.decrypt(password);
				System.out.println("After decrypt password :"+password);
				
			}

			server = getFieldValue("ftp.psrlimit.server");
			if (getFieldValue("ftp.psrlimit.port") != null) {
				port = Integer.parseInt(getFieldValue("ftp.psrlimit.port"));
			}
		}
		
		else if(ICMSConstant.SBBG_SBLC_FILE_UPLOAD.equals(connectionFor)){
			if (getFieldValueBoolean(SBBGSBLCFileUploadConstant.FTP_ANONYMOUS)) {
				username = "anonymous";
				try {
					password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					System.err.println("Error while getting localhost - hostname!");
					e.printStackTrace();
				}
			} else {
				username = getFieldValue(SBBGSBLCFileUploadConstant.FTP_USERNAME);
				password = getFieldValue(SBBGSBLCFileUploadConstant.FTP_PASSWORD);
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				password = sec.decrypt(password);
			}

			server = getFieldValue(SBBGSBLCFileUploadConstant.FTP_SERVER_URL);
			if (getFieldValue(SBBGSBLCFileUploadConstant.FTP_SERVER_PORT) != null) {
				port = Integer.parseInt(getFieldValue(SBBGSBLCFileUploadConstant.FTP_SERVER_PORT));
			}
		}
		else if(ICMSConstant.MF_EQUITY_FILE_UPLOAD.equals(connectionFor)){
			if (getFieldValueBoolean(MfEquityFileUploadConstant.FTP_ANONYMOUS)) {
				username = "anonymous";
				try {
					password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					System.err.println("Error while getting localhost - hostname!");
					e.printStackTrace();
				}
			} else {
				username = getFieldValue(MfEquityFileUploadConstant.FTP_USERNAME);
				password = getFieldValue(MfEquityFileUploadConstant.FTP_PASSWORD);
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				password = sec.decrypt(password);
			}

			server = getFieldValue(MfEquityFileUploadConstant.FTP_SERVER_URL);
			if (getFieldValue(MfEquityFileUploadConstant.FTP_SERVER_PORT) != null) {
				port = Integer.parseInt(getFieldValue(MfEquityFileUploadConstant.FTP_SERVER_PORT));
			}
		}
		else if(ICMSConstant.FD_STP_FILE_UPLOAD.equals(connectionFor)){
			if (getFieldValueBoolean(FixedDepositFileUploadConstant.FTP_ANONYMOUS)) {
				username = "anonymous";
				try {
					password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					System.err.println("Error while getting localhost - hostname!");
					e.printStackTrace();
				}
			} else {
				username = getFieldValue(FixedDepositFileUploadConstant.FTP_USERNAME);
				password = getFieldValue(FixedDepositFileUploadConstant.FTP_PASSWORD);
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				password = sec.decrypt(password);
			}

			server = getFieldValue(FixedDepositFileUploadConstant.FTP_SERVER_URL);
			if (getFieldValue(FixedDepositFileUploadConstant.FTP_SERVER_PORT) != null) {
				port = Integer.parseInt(getFieldValue(FixedDepositFileUploadConstant.FTP_SERVER_PORT));
			}
		}
		else if(ICMSConstant.DEFERRAL_EXTENSION.equals(connectionFor)){
			DefaultLogger.debug(this,"Inside DEFERRAL_EXTENSION if condition.");
			System.out.println("Inside DEFERRAL_EXTENSION if condition.");
		
			if (getFieldValueBoolean("ftp.deferral.anonymous")) {
				username = "anonymous";
				try {
					password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					System.err.println("Error while getting localhost - hostname!");
					e.printStackTrace();
				}
			} else {
				username = getFieldValue("ftp.deferral.username");
				password = getFieldValue("ftp.deferral.password");
				//System.out.println("Before decrypt password :"+password);
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				password = sec.decrypt(password);
				//System.out.println("After decrypt password :"+password);
			}

			server = getFieldValue("ftp.deferral.server");
			if (getFieldValue("ftp.deferral.port") != null) {
				port = Integer.parseInt(getFieldValue("ftp.deferral.port"));
			}
		}
		//Start:Added by Uma Khot:for FD Flexcube CR
		if(ICMSConstant.FD_FILE_UPLOAD.equals(connectionFor)){
			System.out.println("Inside FD_FILE_UPLOAD if condition.");
			
			try {
				this.ftpPasswordProperties.load(new FileInputStream(PropertyManager.getValue("ftp.fd.upload.password.filepath")));
			} catch (FileNotFoundException e1) {
				DefaultLogger.debug(this, "Unable to load fd ftp password file :"+getFieldValue("ftp.fd.upload.password.filepath"));
				e1.printStackTrace();
			} catch (IOException e1) {
				DefaultLogger.debug(this, "Unable to load fd ftp password file :"+getFieldValue("ftp.fd.upload.password.filepath"));
				e1.printStackTrace();
			}
			if (getFieldValueBoolean("ftp.fd.upload.anonymous")) {
				username = "anonymous";
				try {
					password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					System.err.println("Error while getting localhost - hostname!");
					e.printStackTrace();
				}
			} else {
				username = getFieldValue("ftp.fd.upload.username");
				password = this.ftpPasswordProperties.getProperty("ftp.fd.upload.password");
				//System.out.println("Before decrypt password :"+password);
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				password = sec.decrypt(password);
				//System.out.println("After decrypt password :"+password);
				
			}


			server = getFieldValue("ftp.fd.upload.server");
			if (getFieldValue("ftp.fd.upload.port") != null) {
				port = Integer.parseInt(getFieldValue("ftp.fd.upload.port"));
			}
		}
		
		//FCUBS handoff CR
		if(ICMSConstant.FCUBS_MONTHLY_FILE_DOWNLOAD.equals(connectionFor)){
			System.out.println("Inside FCUBS_MONTHLY_FILE_DOWNLOAD if condition.");
			
			try {
				this.ftpPasswordProperties.load(new FileInputStream(PropertyManager.getValue("ftp.fcubs.password.filepath")));
			} catch (FileNotFoundException e1) {
				DefaultLogger.debug(this, "Unable to load fd ftp password file :"+getFieldValue("ftp.fcubs.password.filepath"));
				e1.printStackTrace();
			} catch (IOException e1) {
				DefaultLogger.debug(this, "Unable to load fd ftp password file :"+getFieldValue("ftp.fcubs.password.filepath"));
				e1.printStackTrace();
			}
			if (getFieldValueBoolean("ftp.fcubs.anonymous")) {
				username = "anonymous";
				try {
					password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					System.err.println("Error while getting localhost - hostname!");
					e.printStackTrace();
				}
			} else {
				username = getFieldValue("ftp.fcubs.username");
				password = this.ftpPasswordProperties.getProperty("ftp.fcubs.password");
				//System.out.println("Before decrypt password :"+password);
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				password = sec.decrypt(password);
				//System.out.println("After decrypt password :"+password);
				
			}


			server = getFieldValue("ftp.fcubs.server");
			if (getFieldValue("ftp.fcubs.port") != null) {
				port = Integer.parseInt(getFieldValue("ftp.fcubs.port"));
			}
		}else if(ICMSConstant.FD_STP_FCC_COL_FILE_UPLOAD_BH.equals(connectionFor)){
			String suffixs = ".bh";
			if (getFieldValueBoolean(FccColFdFileUploadConstant.FTP_ANONYMOUS + suffixs)) {
				username = "anonymous";
				try {
					password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					System.err.println("Error while getting localhost - hostname!");
					e.printStackTrace();
				}
			} else {
				username = getFieldValue(FccColFdFileUploadConstant.FTP_USERNAME + suffixs);
				password = getFieldValue(FccColFdFileUploadConstant.FTP_PASSWORD + suffixs);
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				password = sec.decrypt(password);
			}

			server = getFieldValue(FccColFdFileUploadConstant.FTP_SERVER_URL + suffixs);
			if (getFieldValue(FccColFdFileUploadConstant.FTP_SERVER_PORT + suffixs) != null) {
				port = Integer.parseInt(getFieldValue(FccColFdFileUploadConstant.FTP_SERVER_PORT + suffixs));
			}
		}
		else if(ICMSConstant.FD_STP_FCC_COL_FILE_UPLOAD_GC.equals(connectionFor)){
			String suffixs = ".gc";
			if (getFieldValueBoolean(FccColFdFileUploadConstant.FTP_ANONYMOUS + suffixs)) {
				username = "anonymous";
				try {
					password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					System.err.println("Error while getting localhost - hostname!");
					e.printStackTrace();
				}
			} else {
				username = getFieldValue(FccColFdFileUploadConstant.FTP_USERNAME + suffixs);
				password = getFieldValue(FccColFdFileUploadConstant.FTP_PASSWORD + suffixs);
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				password = sec.decrypt(password);
			}

			server = getFieldValue(FccColFdFileUploadConstant.FTP_SERVER_URL + suffixs);
			if (getFieldValue(FccColFdFileUploadConstant.FTP_SERVER_PORT + suffixs) != null) {
				port = Integer.parseInt(getFieldValue(FccColFdFileUploadConstant.FTP_SERVER_PORT + suffixs));
			}
		}
		else if(ICMSConstant.FD_STP_FCC_COL_FILE_UPLOAD_HK.equals(connectionFor)){
			String suffixs = ".hk";
			if (getFieldValueBoolean(FccColFdFileUploadConstant.FTP_ANONYMOUS + suffixs)) {
				username = "anonymous";
				try {
					password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					System.err.println("Error while getting localhost - hostname!");
					e.printStackTrace();
				}
			} else {
				username = getFieldValue(FccColFdFileUploadConstant.FTP_USERNAME + suffixs);
				password = getFieldValue(FccColFdFileUploadConstant.FTP_PASSWORD + suffixs);
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				password = sec.decrypt(password);
			}

			server = getFieldValue(FccColFdFileUploadConstant.FTP_SERVER_URL + suffixs);
			if (getFieldValue(FccColFdFileUploadConstant.FTP_SERVER_PORT + suffixs) != null) {
				port = Integer.parseInt(getFieldValue(FccColFdFileUploadConstant.FTP_SERVER_PORT + suffixs));
			}
		}
		
		else if(ICMSConstant.EWS_STOCK_DEFERRAL_FILE_UPLOAD.equals(connectionFor)){
			DefaultLogger.debug(this,"Inside EWS_STOCK_DEFERRAL_FILE_UPLOAD if condition.");
			System.out.println("Inside EWS_STOCK_DEFERRAL_FILE_UPLOAD if condition.");
			
			if (getFieldValueBoolean("ftp.ews.stockDeferral.anonymous")) {
				username = "anonymous";
				try {
					password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					System.err.println("Error while getting localhost - hostname!");
					e.printStackTrace();
				}
			} else {
				username = getFieldValue("ftp.ews.stockDeferral.username");
				password = getFieldValue("ftp.ews.stockDeferral.password");
				System.out.println("username :: "+username  + "  Before decrypt password :: "+password);
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				password = sec.decrypt(password);
				System.out.println("EWS After decrypt password :"+password);
				
			}

			server = getFieldValue("ftp.ews.stockDeferral.server");
			if (getFieldValue("ftp.ews.stockDeferral.port") != null) {
				port = Integer.parseInt(getFieldValue("ftp.ews.stockDeferral.port"));
			}
		}
		
		else if(ICMSConstant.REPORT_LN_FILE_UPLOAD.equals(connectionFor)){
			DefaultLogger.debug(this,"Inside REPORT_LN_FILE_UPLOAD if condition.");
			System.out.println("Inside REPORT_LN_FILE_UPLOAD if condition.");
			
			if (getFieldValueBoolean("report.leadnodal.anonymous")) {
				username = "anonymous";
				try {
					password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					System.err.println("Error while getting localhost - hostname!");
					e.printStackTrace();
				}
			} else {
				username = getFieldValue("report.leadnodal.username");
				password = getFieldValue("report.leadnodal.password");
				System.out.println("username :: "+username  + "  Before decrypt password :: "+password);
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				password = sec.decrypt(password);
				System.out.println("EWS After decrypt password :"+password);
				
			}


			server = getFieldValue("report.leadnodal.server");
			if (getFieldValue("report.leadnodal.port") != null) {
				port = Integer.parseInt(getFieldValue("report.leadnodal.port"));
			}
		}

		else if(ICMSConstant.ECBF_DEFERRAL_REPORT_UPLOAD.equals(connectionFor)){
            DefaultLogger.debug(this,"Inside ECBF_DEFERRAL_REPORT_UPLOAD if condition.");
            System.out.println("Inside ECBF_DEFERRAL_REPORT_UPLOAD if condition.");
            
            if (getFieldValueBoolean("ecbf.defferal.report.anonymous")) {
                username = "anonymous";
                try {
                    password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
                } catch (UnknownHostException e) {
                    System.err.println("Error while getting localhost - hostname!");
                    e.printStackTrace();
                }
            } else {
                username = getFieldValue("ecbf.defferal.report.username");
                password = getFieldValue("ecbf.defferal.report.password");
                System.out.println("ECBF_DEFERRAL_REPORT_UPLOAD=>username :: "+username  + "  Before decrypt password :: "+password);
                CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
                password = sec.decrypt(password);
                System.out.println("EWS After decrypt password :"+password);
                
            }


            server = getFieldValue("ecbf.defferal.report.server");
            if (getFieldValue("ecbf.defferal.report.port") != null) {
                port = Integer.parseInt(getFieldValue("ecbf.defferal.report.port"));
            }
        }
		else if(ICMSConstant.DIGI_LIB_DOC_FILE_UPLOAD.equals(connectionFor)){
            DefaultLogger.debug(this,"Inside DIGI_LIB_DOC_FILE_UPLOAD if condition.");
            System.out.println("Inside DIGI_LIB_DOC_FILE_UPLOAD if condition.");
            
            if (getFieldValueBoolean("digital.library.document.syncup.anonymous")) {
                username = "anonymous";
                try {
                    password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
                } catch (UnknownHostException e) {
                    System.err.println("Error while getting localhost - hostname!");
                    e.printStackTrace();
                }
            } else {
                username = getFieldValue("digital.library.document.syncup.username");
                password = getFieldValue("digital.library.document.syncup.password");
                System.out.println("DIGI_LIB_DOC_FILE_UPLOAD=>username :: "+username  + "  Before decrypt password :: "+password);
                CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
                password = sec.decrypt(password);
                System.out.println("DIGI_LIB_DOC_FILE_UPLOAD After decrypt password :"+password);
                
            }


            server = getFieldValue("digital.library.document.syncup.server");
            if (getFieldValue("digital.library.document.syncup.port") != null) {
                port = Integer.parseInt(getFieldValue("digital.library.document.syncup.port"));
            }
        }

		if(IFileUploadConstants.FILEDOWNLOAD_HRMS.equals(connectionFor)){
			System.out.println("Inside CmsFtpClient.java => FILEDOWNLOAD_HRMS. ");
			if (getFieldValueBoolean("ftp.hrms.upload.anonymous")) {
				username = "anonymous";
				try {
					System.out.println("anonymous username ="+username);
					password = System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName();
					System.out.println("password=>"+password);
				} catch (UnknownHostException e) {
					System.err.println("Error while getting localhost - hostname!");
					e.printStackTrace();
				}
			} else {
				username = getFieldValue("ftp.hrms.upload.username");
				System.out.println("Inside CmsFtpClient.java => FILEDOWNLOAD_HRMS.username=>"+username);
//				password = this.ftpPasswordProperties.getProperty("ftp.hrms.upload.password");
				password = getFieldValue("ftp.hrms.upload.password");
				System.out.println("Before decrypt password FILEDOWNLOAD_HRMS:"+password);
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				System.out.println("CryptoManager sec=>"+sec);
				password = sec.decrypt(password);
				System.out.println("After decrypt password FILEDOWNLOAD_HRMS:"+password);
			}
			server = getFieldValue("ftp.hrms.upload.server");
			System.out.println("FILEDOWNLOAD_HRMS server=>"+server);
			if (getFieldValue("ftp.hrms.upload.port") != null) {
				port = Integer.parseInt(getFieldValue("ftp.hrms.upload.port"));
				System.out.println("FILEDOWNLOAD_HRMS port=>"+port);
			}
		}

		DefaultLogger.debug(this, "CMSFtpClient Constructor loading completed.! ");
		System.out.println("CMSFtpClient Constructor loading completed.! ");
	}
	
	public static CMSFtpClient getInstance(String propertyFile, String connectionFor){
		ResourceBundle bundle = ResourceBundle.getBundle( "ofa");
		CMSFtpClient client = null;
		DefaultLogger.debug(CMSFtpClient.class.getName(), "getInstace() method called:"+connectionFor);
		System.out.println("Inside CMSFtpClient =>connectionFor=>"+connectionFor);
		String connectonType = null;
		if(ICMSConstant.MASTER_SYNC_UP.equals(connectionFor))
			connectonType = bundle.getString("ftp.connection.type");
		else if(ICMSConstant.SYSTEM_FILE_UPLOAD.equals(connectionFor)){
			connectonType = bundle.getString("ftp.upload.connection.type");
		}


		//Start:Added by Uma Khot:for FD Flexcube CR
		else if(ICMSConstant.FD_FILE_UPLOAD.equals(connectionFor)){
			connectonType = bundle.getString("ftp.fd.upload.connection.type");
		}else if(ICMSConstant.POSIDEX_FILE_UPLOAD.equals(connectionFor)){
			connectonType = bundle.getString("ftp.posidex.connection.type");
		}else if(ICMSConstant.FCUBS_MONTHLY_FILE_DOWNLOAD.equals(connectionFor)){
			connectonType = bundle.getString("ftp.fcubs.connection.type");
		}
		else if(ICMSConstant.FCUBSLIMIT_FILE_UPLOAD.equals(connectionFor)){
			connectonType = bundle.getString("ftp.fcubslimit.connection.type");
		}
		
		else if(ICMSConstant.DFSO_UPLOAD.equals(connectionFor)){
			connectonType = bundle.getString("ftp.dfso.borrower.connection.type");
		}
		else if(ICMSConstant.DFSO_JOB_UPLOAD.equals(connectionFor)){
			connectonType = bundle.getString("ftp.dfso.connection.type");
		}
		
		else if(ICMSConstant.FCUBSLIMIT_FILE_UPLOAD.equals(connectionFor)){
			connectonType = bundle.getString("ftp.fcubslimit.connection.type");
		}
		else if(ICMSConstant.DEFERRAL_EXTENSION.equals(connectionFor)){
			connectonType = bundle.getString("ftp.deferral.connection.type");
		}else if(ICMSConstant.SBBG_SBLC_FILE_UPLOAD.equals(connectionFor)) {
			connectonType = bundle.getString(SBBGSBLCFileUploadConstant.CONNECTION_TYPE);
		}else if(ICMSConstant.MF_EQUITY_FILE_UPLOAD.equals(connectionFor)) {
			connectonType = bundle.getString(MfEquityFileUploadConstant.CONNECTION_TYPE);
		}
		else if(ICMSConstant.FD_STP_FILE_UPLOAD.equals(connectionFor)) {
			connectonType = bundle.getString(FixedDepositFileUploadConstant.CONNECTION_TYPE);
		}
		else if(ICMSConstant.PSRLIMIT_FILE_UPLOAD.equals(connectionFor)){
			connectonType = bundle.getString("ftp.psrlimit.connection.type");
		}
		else if(ICMSConstant.NPA_FILE_UPLOAD.equals(connectionFor)){
			connectonType = bundle.getString("ftp.npa.connection.type");
		}
		else if(ICMSConstant.FD_STP_FCC_COL_FILE_UPLOAD_BH.equals(connectionFor)){
			connectonType = bundle.getString(FccColFdFileUploadConstant.CONNECTION_TYPE + ".bh");
		}
		else if(ICMSConstant.FD_STP_FCC_COL_FILE_UPLOAD_GC.equals(connectionFor)){
			connectonType = bundle.getString(FccColFdFileUploadConstant.CONNECTION_TYPE + ".gc");
		}
		else if(ICMSConstant.FD_STP_FCC_COL_FILE_UPLOAD_HK.equals(connectionFor)){
			connectonType = bundle.getString(FccColFdFileUploadConstant.CONNECTION_TYPE + ".hk");
		}
		else if(ICMSConstant.NPA_DAILY_STAMPING_FILE_UPLOAD.equals(connectionFor)){
			connectonType = bundle.getString("ftp.npa.dailyStamping.connection.type");
		}
		else if(ICMSConstant.EWS_STOCK_DEFERRAL_FILE_UPLOAD.equals(connectionFor)){
			connectonType = bundle.getString("ftp.ews.stockDeferral.connection.type");
		}
		else if(ICMSConstant.REPORT_LN_FILE_UPLOAD.equals(connectionFor)) {
			connectonType = bundle.getString("report.leadnodal.connection.type");
		}
		else if(IFileUploadConstants.FILEDOWNLOAD_HRMS.equals(connectionFor)){
			System.out.println("Inside if for CMSFtpClient connectonType");
			connectonType = bundle.getString("ftp.hrms.upload.connection.type");
			System.out.println("Inside if for CMSFtpClient connectonType=>"+connectonType);
		}
		else if(ICMSConstant.ECBF_DEFERRAL_REPORT_UPLOAD.equals(connectionFor)){
			System.out.println("Inside if for CMSFtpClient connectonType line 657 ECBF_DEFERRAL_REPORT_UPLOAD");
			connectonType = bundle.getString("ecbf.defferal.report.connection.type");
			System.out.println("Inside if for CMSFtpClient connectonType ECBF_DEFERRAL_REPORT_UPLOAD=>"+connectonType);
		}
		else if(ICMSConstant.DIGI_LIB_DOC_FILE_UPLOAD.equals(connectionFor)){
			System.out.println("Inside if for CMSFtpClient connectonType line 720 DIGI_LIB_DOC_FILE_UPLOAD");
			connectonType = bundle.getString("digital.library.document.syncup.connection.type");
			System.out.println("Inside if for CMSFtpClient connectonType DIGI_LIB_DOC_FILE_UPLOAD=>"+connectonType);
		}
		DefaultLogger.debug(CMSFtpClient.class.getName(), "connectonType:"+connectonType);
		//End:Added by Uma Khot:for FD Flexcube CR
		
		if("sftp".equalsIgnoreCase( connectonType)){
			System.out.println("Going for connectonType sftp and connectonType=>"+connectonType+" connectionFor=>"+connectionFor);
			client = new SFTPClient(bundle,connectionFor);
		} else{
			System.out.println("Going for connectonType another and connectonType=>"+connectonType+" connectionFor=>"+connectionFor);
			client = new FTPSClient(bundle,connectionFor);
		}
		return client;
	}

	abstract public boolean openConnection() throws Exception;

	abstract public void closeConnection();

	abstract public void uploadFile(String localPath, String remotePath) throws Exception;
	abstract public void uploadFileNew(String localPath, String remotePath) throws Exception; 

	abstract public void downloadFile(String localPath, String remotePath) throws Exception;

	

	abstract public Vector<String> listSubDirInDir(String remoteDir) throws Exception;

	abstract protected boolean createDirectory(String dirName) ;

	abstract public Vector<String> listFileInDir(String remoteDir) throws Exception ;

	abstract protected boolean downloadFileAfterCheck(String remotePath, String localFile) throws Exception ;

	abstract public boolean changeDir(String remotePath) throws Exception;
	
	abstract public void uploadFCUBSFile(String localPath, String remotePath,String fileName) throws Exception;

	abstract public boolean downloadFCUBSFile(String remotePath, String localFile, ArrayList<String[]> fileList) throws Exception ;
	
	public boolean downloadFileAndMove(String localPath, String remotePath, String remoteMovePath) {
		try {
			downloadFile(localPath, remotePath);
			uploadFile(localPath, remoteMovePath);
			deleteFile(remotePath);
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean downloadFileToLocalAndDeleteRemoteFile(String localPath, String remotePath) {
		try {
			downloadFile(localPath, remotePath);
			deleteFile(remotePath);
		} catch (Exception e) {			
			e.printStackTrace();
			return false;
		}
		return true;
	}
	

	abstract public boolean deleteFile(String remoteFile);
	abstract public boolean isARemoteDirectory(String remotePath);
	
	//For PSR LIMIT CR
	abstract public boolean downloadPSRFile(String remotePath, String localFile, ArrayList<String[]> fileList) throws Exception ;
	
	abstract public boolean downloadFccColFile(String remotePath, String localFile, ArrayList<String> fileList) throws Exception ;


}