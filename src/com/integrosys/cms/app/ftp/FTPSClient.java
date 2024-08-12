package com.integrosys.cms.app.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPHTTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamListener;
import org.apache.commons.net.util.TrustManagerUtils;

import com.integrosys.base.techinfra.crypto.CryptoManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
public class FTPSClient extends CMSFtpClient {

	protected boolean binaryTransfer = false, error = false, hidden = false ,secureftp=false;

	protected boolean localActive = false, useEpsvWithIPv4 = false, printHash = false;
	// protected boolean mlst = false, mlsd = false, storeFile = false,
	// listFiles = false, listNames = false, feat = false,;
	protected boolean lenient = false;
	protected long keepAliveTimeout = -1;
	protected int controlKeepAliveReplyTimeout = -1;

	protected String protocol = null; // SSL protocol

	protected String trustmgr = null;
	protected String proxyHost = null;
	protected int proxyPort = 80;
	protected String proxyUser = null;
	protected String proxyPassword = null;

	protected FTPClient ftp;
	protected Properties ftpPasswordProperties = new Properties();
	public FTPSClient(ResourceBundle ftpProperty,String connectionFor) {
		super(ftpProperty,connectionFor);

		DefaultLogger.debug(this, "FTPSClient countructor called()::::"+connectionFor);
		if(ICMSConstant.SYSTEM_FILE_UPLOAD.equals(connectionFor)){
			localActive = getFieldValueBoolean("ftp.upload.localActive");
	
			binaryTransfer = getFieldValueBoolean("ftp.upload.binaryTransfer");
	
			useEpsvWithIPv4 = getFieldValueBoolean("ftp.upload.useEpsvWithIPv4");
			secureftp = getFieldValueBoolean("ftp.protocal.secureftp");
	
			hidden = getFieldValueBoolean("ftp.upload.hidden");
	
			if (getFieldValue("ftp.upload.keepAliveTimeout") != null) {
				keepAliveTimeout = Long.parseLong(getFieldValue("ftp.upload.keepAliveTimeout"));
			}
	
			lenient = getFieldValueBoolean("ftp.upload.lenient");
	
			protocol = getFieldValue("ftp.upload.protocol");
	
			if (getFieldValue("ftp.upload.controlKeepAliveReplyTimeout") != null) {
				controlKeepAliveReplyTimeout = Integer.parseInt(getFieldValue("ftp.upload.controlKeepAliveReplyTimeout"));
			}
	
			trustmgr = getFieldValue("ftp.upload.trustmgr");
	
			proxyHost = getFieldValue("ftp.upload.proxyHost");
	
			if (getFieldValue("ftp.upload.proxyPort") != null) {
				proxyPort = Integer.parseInt(getFieldValue("ftp.upload.proxyPort"));
			}
	
			proxyUser = getFieldValue("ftp.upload.proxyUser");
			try {
				this.ftpPasswordProperties.load(new FileInputStream(PropertyManager.getValue("ftp.upload.password.filepath")));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			proxyPassword = this.ftpPasswordProperties.getProperty("ftp.upload.password");
		//	proxyPassword = getFieldValue("ftp.upload.proxyPassword");
			CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
			proxyPassword = sec.decrypt(proxyPassword);
			
			printHash = getFieldValueBoolean("ftp.upload.printHash");
		
			
		}else if(ICMSConstant.MASTER_SYNC_UP.equals(connectionFor)){
			DefaultLogger.debug(this, "Inside else if condition for master sync up:::"+connectionFor);
			
			localActive = getFieldValueBoolean("ftp.localActive");
	
			binaryTransfer = getFieldValueBoolean("ftp.binaryTransfer");
	
			useEpsvWithIPv4 = getFieldValueBoolean("ftp.useEpsvWithIPv4");
	
			hidden = getFieldValueBoolean("ftp.hidden");
	
			if (getFieldValue("ftp.keepAliveTimeout") != null) {
				keepAliveTimeout = Long.parseLong(getFieldValue("ftp.keepAliveTimeout"));
			}
	
			lenient = getFieldValueBoolean("ftp.lenient");
	
			protocol = getFieldValue("ftp.protocol");
	
			if (getFieldValue("ftp.controlKeepAliveReplyTimeout") != null) {
				controlKeepAliveReplyTimeout = Integer.parseInt(getFieldValue("ftp.controlKeepAliveReplyTimeout"));
			}
	
			trustmgr = getFieldValue("ftp.trustmgr");
	
			proxyHost = getFieldValue("ftp.proxyHost");
	
			if (getFieldValue("ftp.proxyPort") != null) {
				proxyPort = Integer.parseInt(getFieldValue("ftp.proxyPort"));
			}
	
			proxyUser = getFieldValue("ftp.proxyUser");
	
			proxyPassword = getFieldValue("ftp.proxyPassword");
			CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
			proxyPassword = sec.decrypt(proxyPassword);
			
			printHash = getFieldValueBoolean("ftp.printHash");
			DefaultLogger.debug(this, "values set from property file:::");
		}
	}

	@Override
	public boolean openConnection() throws IOException {
		DefaultLogger.debug(this, "Inside OpenConnection() method - for FTP connection :::");
		if (protocol == null) {
			if (proxyHost != null) {
				DefaultLogger.debug(this,"Using HTTP proxy server: " + proxyHost);
				System.out.println("Using HTTP proxy server: " + proxyHost);
				ftp = new FTPHTTPClient(proxyHost, proxyPort, proxyUser, proxyPassword);
			} else {
				DefaultLogger.debug(this, "Creating FTPClient()");
				ftp = new FTPClient();
				DefaultLogger.debug(this, "Created FTPClient()");
			}
		} else {
			org.apache.commons.net.ftp.FTPSClient ftps;
			if (protocol.equals("true")) {
				ftps = new org.apache.commons.net.ftp.FTPSClient(true);
			} else if (protocol.equals("false")) {
				ftps = new org.apache.commons.net.ftp.FTPSClient(false);
			} else {
				String prot[] = protocol.split(",");
				if (prot.length == 1) { // Just protocol
					ftps = new org.apache.commons.net.ftp.FTPSClient(protocol);
				} else { // protocol,true|false
					ftps = new org.apache.commons.net.ftp.FTPSClient(prot[0], Boolean.parseBoolean(prot[1]));
				}
			}
			ftp = ftps;
			if ("all".equals(trustmgr)) {
				ftps.setTrustManager(TrustManagerUtils.getAcceptAllTrustManager());
			} else if ("valid".equals(trustmgr)) {
				ftps.setTrustManager(TrustManagerUtils.getValidateServerCertificateTrustManager());
			} else if ("none".equals(trustmgr)) {
				ftps.setTrustManager(null);
			}
		}

		if (printHash) {
			ftp.setCopyStreamListener(createListener());
		}
		if (keepAliveTimeout >= 0) {
			ftp.setControlKeepAliveTimeout(keepAliveTimeout);
		}
		if (controlKeepAliveReplyTimeout >= 0) {
			ftp.setControlKeepAliveReplyTimeout(controlKeepAliveReplyTimeout);
		}
		ftp.setListHiddenFiles(hidden);

		// suppress login details
		ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));

		try {
			int reply;
			if (secureftp) {
				org.apache.commons.net.ftp.FTPSClient ftps=(org.apache.commons.net.ftp.FTPSClient)ftp;
				ftp=ftps;
				ftp.connect(server);
				ftps.execPROT("P");
			}
			else if (port > 0) {
				ftp.connect(server,port);
			}else {
				ftp.connect(server);
			}
			System.out.println("Connected to " + server + " on " + (port > 0 ? port : ftp.getDefaultPort()));
			DefaultLogger.debug(this, "Connected to " + server + " on " + (port > 0 ? port : ftp.getDefaultPort()));
			
			/*if (secureftp) {
				org.apache.commons.net.ftp.FTPSClient ftps=(org.apache.commons.net.ftp.FTPSClient)ftp;
				ftps.execPROT("P");
				ftp=ftps;
			}*/

			// After connection attempt, you should check the reply code to
			// verify
			// success.
			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.err.println("FTP server refused connection.");
				throw new IOException("FTP server refused connection.");
			}
		} catch (IOException e) {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					// do nothing
				}
			}
			DefaultLogger.error(this, "Could not connect to server through FTP.");
			System.err.println("Could not connect to server.");
			e.printStackTrace();
			throw e;
		}
		if (!ftp.login(username, password)) {
			ftp.logout();
			throw new IOException("FTP server refused connection.");

		}
		DefaultLogger.debug(this,"Remote system is " + ftp.getSystemType());
		System.out.println("Remote system is " + ftp.getSystemType());

		if (binaryTransfer) {
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
		} else {
			// in theory this should not be necessary as servers should
			// default to ASCII
			// but they don't all do so - see NET-500
			ftp.setFileType(FTP.ASCII_FILE_TYPE);
		}

		// Use passive mode as default because most of us are
		// behind firewalls these days.
		if (localActive) {
			ftp.enterLocalActiveMode();
		} else {
			ftp.enterLocalPassiveMode();
		}
		ftp.setUseEPSVwithIPv4(useEpsvWithIPv4);
		DefaultLogger.debug(this," Open connection method call completed.. ");
		return true;
	}

	private static CopyStreamListener createListener() {
		return new CopyStreamListener() {
			private long megsTotal = 0;

			// @Override
			public void bytesTransferred(CopyStreamEvent event) {
				bytesTransferred(event.getTotalBytesTransferred(), event.getBytesTransferred(), event.getStreamSize());
			}

			// @Override
			public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {
				long megs = totalBytesTransferred / 1000000;
				for (long l = megsTotal; l < megs; l++) {
					System.err.print("#");
				}
				megsTotal = megs;
			}
		};
	}

	@Override
	public void closeConnection() {
		try {
			ftp.noop(); // check that control connection is working OK

			ftp.logout();
		} catch (FTPConnectionClosedException e) {
			error = true;
			DefaultLogger.error(this,"Server closed connection.");
			System.err.println("Server closed connection.");
			e.printStackTrace();
		} catch (IOException e) {
			error = true;
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					// do nothing
				}
			}
		}
	}

	public void doOperation(String operation, String local, String remote) throws IOException {

		if (operation.equalsIgnoreCase("listFiles")) {
			if (lenient) {
				FTPClientConfig config = new FTPClientConfig();
				config.setLenientFutureDates(true);
				ftp.configure(config);
			}

			for (FTPFile f : ftp.listFiles(remote)) {
				System.out.println(f.getRawListing());
				System.out.println(f.toFormattedString());
			}
		} else if (operation.equalsIgnoreCase("mlsd")) {
			for (FTPFile f : ftp.mlistDir(remote)) {
				System.out.println(f.getRawListing());
				System.out.println(f.toFormattedString());
			}
		} else if (operation.equalsIgnoreCase("mlst")) {
			FTPFile f = ftp.mlistFile(remote);
			if (f != null) {
				System.out.println(f.toFormattedString());
			}
		} else if (operation.equalsIgnoreCase("listNames")) {
			for (String s : ftp.listNames(remote)) {
				System.out.println(s);
			}
		} else if (operation.equalsIgnoreCase("feat")) {
			// boolean feature check
			if (remote != null) { // See if the command is present
				if (ftp.hasFeature(remote)) {
					System.out.println("Has feature: " + remote);
				} else {
					if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
						System.out.println("FEAT " + remote + " was not detected");
					} else {
						System.out.println("Command failed: " + ftp.getReplyString());
					}
				}

				// Strings feature check
				String[] features = ftp.featureValues(remote);
				if (features != null) {
					for (String f : features) {
						System.out.println("FEAT " + remote + "=" + f + ".");
					}
				} else {
					if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
						System.out.println("FEAT " + remote + " is not present");
					} else {
						System.out.println("Command failed: " + ftp.getReplyString());
					}
				}
			} else {
				if (ftp.features()) {
					// Command listener has already printed the output
				} else {
					System.out.println("Failed: " + ftp.getReplyString());
				}
			}
		} else {
			if (ftp.doCommand(operation, remote)) {
				// Command listener has already printed the output
				// for(String s : ftp.getReplyStrings()) {
				// System.out.println(s);
				// }
			} else {
				System.out.println(operation + " Failed: " + ftp.getReplyString());
			}
		}

	}

	@Override
	public void uploadFile(String localPath, String remotePath) throws IOException {
		DefaultLogger.debug(this, "uploadFile() method called..");
		InputStream input = null;
		try {
			input = new FileInputStream(localPath);

			ftp.storeFile(remotePath, input);

			input.close();
		} catch (IOException e) {
			DefaultLogger.error(this, "uploadFile() method called.."+e);
			e.printStackTrace();
			throw e;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e1) {
					// do nothing
				}
			}
		}
	}

	@Override
	public void downloadFile(String localPath, String remotePath) throws IOException {
		DefaultLogger.debug(this, "downloadFile method called()>>localPath::"+localPath+":remotePath::"+remotePath);
		OutputStream output = null;
		try {
			output = new FileOutputStream(localPath);

			ftp.retrieveFile(remotePath, output);
			output.close();
		} catch (IOException e) {
			DefaultLogger.error(this,e);
			e.printStackTrace();
			throw e;
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (Exception e1) {
					// do nothing
				}
			}
		}
	}
	
	@Override
	public boolean deleteFile(String remoteFile){
		try {
			ftp.deleteFile(remoteFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean changeDir(String remotePath) throws Exception {
		return ftp.changeWorkingDirectory(remotePath);
	}

	public void disconnect() throws Exception {
		System.out.println("FTP request disconnect");
		ftp.disconnect();
	}

	@Override
	protected boolean downloadFileAfterCheck(String remotePath, String localFile) throws IOException {

		boolean rst;
		FileOutputStream out = null;
		try {
			File file = new File(localFile);
			if (!file.exists()) {
				out = new FileOutputStream(localFile);
				rst = ftp.retrieveFile(remotePath, out);
			} else {
				rst = true;
			}
		} finally {
			if (out != null) {
				out.close();
			}
		}
		if (out != null) {
			out.close();
		}
		return rst;
	}

	@Override
	public Vector<String> listFileInDir(String remoteDir) throws Exception {
		if (changeDir(remoteDir)) {
			FTPFile[] files = ftp.listFiles();
			Vector<String> v = new Vector<String>();
			for (FTPFile file : files) {
				if (!file.isDirectory()) {
					v.addElement(file.getName());
				}
			}
			return v;
		} else {
			return null;
		}
	}

	@Override
	public Vector<String> listSubDirInDir(String remoteDir) throws Exception {
		if (changeDir(remoteDir)) {
			FTPFile[] files = ftp.listFiles();
			Vector<String> v = new Vector<String>();
			for (FTPFile file : files) {
				if (file.isDirectory()) {
					v.addElement(file.getName());
				}
			}
			return v;
		} else {
			return null;
		}
	}

	@Override
	protected boolean createDirectory(String dirName) {
		try {
			return ftp.makeDirectory(dirName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isARemoteDirectory(String path) {
		String cache = "/";
		try {
			cache = ftp.printWorkingDirectory();
		} catch (NullPointerException e) {
			// nop
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			boolean isDir = changeDir(path);
			changeDir(cache);
			return isDir;
		} catch (IOException e) {
			// e.printStackTrace();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return false;
	}

	public String getWorkingDirectory() {
		try {
			return ftp.printWorkingDirectory();
		} catch (IOException e) {
		}
		return null;
	}

	public static void main(String[] args) throws IOException {
		FTPSClient cmsFtpClient = new FTPSClient(ResourceBundle.getBundle("ofa"),ICMSConstant.MASTER_SYNC_UP);
		cmsFtpClient.openConnection();
		System.out.println(cmsFtpClient);
		cmsFtpClient.closeConnection();
	}

	@Override
	public void uploadFCUBSFile(String localPath, String remotePath,String fileName)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean downloadFCUBSFile(String remotePath, String localFile,
			ArrayList<String[]> fileList) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean downloadPSRFile(String remotePath, String localFile, ArrayList<String[]> fileList) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean downloadFccColFile(String remotePath, String localFile, ArrayList<String> fileList) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void uploadFileNew(String localPath, String remotePath) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
	

}
