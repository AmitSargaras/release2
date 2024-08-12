package com.integrosys.cms.ui.eventmonitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import com.integrosys.base.techinfra.util.PropertyUtil;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;

public class EventMonitorClient {
	private static String batchType = "";
	private static String url = "";
	public static void main(String[] args) {
		try {
			EventMonitorClient client = new EventMonitorClient();
			// Added this line for testing - Archana
			//args = new String[]{"bhavcopyBatchJob"};
		//	args = new String[]{"generateEmailNotificationBatchJob"};
		//	client.doWork(args);
		//	args = new String[]{"eodUserManagerJob"};
		//	client.doWork(args);
		//	args = new String[]{"systemHandOffJob"};
		//	client.doWork(args);*/
		//	args = new String[]{"endOfDayBatchJob"};
			batchType = (args.length > 0) ? args[0] : "";
			client.doWork(args);
		}
		catch (Exception e) {
			e.printStackTrace();
			if (e instanceof ConnectException || e instanceof IOException) {
				logError();
			}
			else {
				e.printStackTrace();
			}
		}
	}

	private static void logError() {
		Properties properties = new Properties(); 
		String fileName = null;
		try {  
			System.out.println("<<<<<<<<Inside logError() before load property>>>>>>");
			properties.load(new FileInputStream("/clims/apps/config/eod/eventmon.properties")); 
			System.out.println("<<<<<<<<Inside logError() after load property>>>>>>");
			System.out.println("Inside logError() "+batchType);
			if ("endOfDayBatchJob".equalsIgnoreCase(batchType.trim())) {
				fileName = properties.getProperty("eod.logPath") + properties.getProperty("eod.failFileName");
			} else if ("systemHandOffJob".equalsIgnoreCase(batchType.trim())) {
				fileName = properties.getProperty("systemHO.logPath") + properties.getProperty("systemHO.failFileName");
			} else if ("rBIReportEndOfDayBatchJob".equalsIgnoreCase(batchType.trim())){
				fileName = properties.getProperty("eod.logPath") + "RBIReporteodFail.log"; //properties.getProperty("rBIReporteod.failFileName");
			}
			// Added By Dayananda Laishram for Basel Report Generation on 12-May-2015 BASEL_REPORT_ON_EOD
			else if ("endOfDayBaselUpdateReportJob".equalsIgnoreCase(batchType.trim())){
				fileName = properties.getProperty("eod.logPath") + "baselUpdateReportFail.log"; //properties.getProperty("rBIReporteod.failFileName");
			}else if ("posidexFileGenerationJob".equalsIgnoreCase(batchType.trim())){
				fileName = properties.getProperty("eod.logPath") + "posidexReportGenFail.log"; //properties.getProperty("rBIReporteod.failFileName");
			}else if ("fcubsFileDownload".equalsIgnoreCase(batchType.trim())){
				fileName = properties.getProperty("eod.logPath") + "fcubsFileDownloadFail.log";
			}
			if  (fileName == null || fileName.trim().equalsIgnoreCase("")) {
				fileName = properties.getProperty("eod.logPath") + "Fail.log";
			}
			System.out.println("<<<<<<<<Inside logError() print file name: >>>>>>"+fileName);
					
			System.out.println("<<<<<<<<Inside logError() print eod.failFileName: >>>>>>"+properties.getProperty("eod.failFileName"));
			System.out.println("<<<<<<<<Inside logError() print eod.logPath: >>>>>>"+properties.getProperty("eod.logPath"));
			System.out.println("<<<<<<<<Inside logError() print db.url: >>>>>>"+properties.getProperty("db.url"));
			System.out.println("<<<<<<<<Inside logError() print systemHO.failFileName: >>>>>>"+properties.getProperty("systemHO.failFileName"));
			System.out.println("<<<<<<<<Inside logError() print db.port: >>>>>>"+properties.getProperty("db.port"));
			System.out.println("<<<<<<<<Inside logError() print db.user: >>>>>>"+properties.getProperty("db.user"));
			System.out.println("<<<<<<<<Inside logError() print systemHO.logPath: >>>>>>"+properties.getProperty("systemHO.logPath"));
			System.out.println("<<<<<<<<Inside logError() print db.schema: >>>>>>"+properties.getProperty("db.schema"));
			
			File file = new File(fileName);
			boolean createNewFile = file.createNewFile();
			 if(createNewFile==false) {
					System.out.println("Error while creating new file:"+file.getPath());	
				      }
			FileOutputStream fos  = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(fos);
			pw.write("Connection Exception : Clims Application Not Running ("+url+"), Please check status of WAS and/or Oracle DB services.");
			pw.close();
		} 
		catch (IOException ioe) {   
			ioe.printStackTrace();
		} 
	}
	
	private void doWork(String[] args) throws Exception {
		URLConnection con = getURLConnection();
		ObjectOutputStream objStream = new ObjectOutputStream(con.getOutputStream());
		objStream.writeObject(args);
		objStream.flush();
		objStream.close();
		System.out.println("Request send !");
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		System.out.println("Waiting for response...");
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
		}
		reader.close();
	}

	private URLConnection getURLConnection() throws Exception {
		String urlStr = getAppURL();
		url = urlStr;
		System.out.println("<<<<<<Inside getURLConnection URL : >>>>>>>" + urlStr);
		if (urlStr == null) {
			throw new Exception("Invalid app url configuration!");
		}
		URL urlObj = null;
		if (urlStr.startsWith("https:")) {
			urlObj = getHttpsURL(urlStr + "/EventMonitorServlet");
		}
		else {
			urlObj = new URL(urlStr + "/EventMonitorServlet");
		}
	//	System.out.println("create a new URL obj");
		URLConnection con = urlObj.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setRequestProperty("Content-type", "application/octest-stream");
		return con;
	}

	private URL getHttpsURL(String urlStr) throws Exception {
		trustAllHttpsCertificates();
		return new URL(urlStr);
	}

	private String getAppURL() throws Exception {
		System.out.println("<<<<<< Inside getAppURL() >>>>>>>:"+PropertyUtil.getInstance("/ofa_env.properties").getProperty(ICMSUIConstant.CMS_APP_URL));
		return PropertyUtil.getInstance("/ofa_env.properties").getProperty(ICMSUIConstant.CMS_APP_URL);
	}

	private void trustAllHttpsCertificates() throws Exception {
		String protocol = PropertyUtil.getInstance("/ofa_env.properties").getProperty("sslortls.protocol");
		System.out.println("protocol:"+protocol);
		//SSLContext sc = SSLContext.getInstance(protocol);
		SSLContext sc = SSLContext.getInstance("TLSv1.1");
		sc.init(null, getTrustAllCerts(), null);
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier(getHostNameVreifier());
	}

	private TrustManager[] getTrustAllCerts() {
		final X509TrustManager finalTm = getX509TrustManager();
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) throws CertificateException {
				finalTm.checkClientTrusted(certs, authType);
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) throws CertificateException {
				finalTm.checkServerTrusted(certs, authType);
			}
		} };
		return trustAllCerts;
	}

	private X509TrustManager getX509TrustManager() {
		TrustManagerFactory tmf = null;
		try {
			tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		
		// Using null here initialises the TMF with the default trust store.
		tmf.init((KeyStore) null);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Get hold of the default trust manager
		X509TrustManager x509Tm = null;
		for (TrustManager tm : tmf.getTrustManagers()) {
		    if (tm instanceof X509TrustManager) {
		        x509Tm = (X509TrustManager) tm;
		        break;
		    }
		}

		return x509Tm;
		
	}

	private HostnameVerifier getHostNameVreifier() {
		HostnameVerifier verifier = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
			//	System.out.println("URL Host: " + urlHostName + " vs. " + session.getPeerHost());
				 return urlHostName.equalsIgnoreCase(session.getPeerHost());
				//return true;
			}
		};
		return verifier;
	}
}
