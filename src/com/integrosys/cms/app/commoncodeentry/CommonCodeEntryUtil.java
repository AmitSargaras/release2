/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CommonCodeEntryUtil.java
 *
 * Created on February 2, 2007, 10:50 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Collection;
import java.util.Iterator;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.PropertyUtil;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry;
import com.integrosys.cms.ui.common.CommonCodeList;

/**
 * 
 * @author Eric
 */
public class CommonCodeEntryUtil {

	// public static Collection recontruct(MaintainCommonCodeEntriesForm form) {
	// ArrayList list = new ArrayList();
	//
	// DefaultLogger.debug(CommonCodeEntryUtil.class.getName(),
	// "Recontructing OB ...");
	//
	// String entryName[] = form.getEntryName();
	// String entryCode[] = form.getEntryCode();
	// String country[] = form.getCountry();
	// String activeStatus[] = form.getActiveStatus();
	// String entrySource[] = form.getEntrySource();
	// String entryId[] = form.getEntryId();
	// String categoryCode[] = form.getCategoryCode();
	// String categoryId = form.getCategoryCodeId();
	// String groupId[] = form.getGroupId();
	//
	// if (entryName == null) {
	// return list;
	// }
	//
	// for (int loop = 0; loop < entryName.length; loop++) {
	// OBCommonCodeEntry entry = new OBCommonCodeEntry();
	//
	// String entryId2 = entryId[loop];
	// String groupId2 = groupId[loop];
	//
	// entry.setEntryName(entryName[loop]);
	// entry.setEntryCode(entryCode[loop]);
	// entry.setActiveStatus("A".equals(activeStatus[loop]));
	// entry.setEntrySource(entrySource[loop]);
	// entry.setEntryId(entryId2.length() > 0 ? Long.parseLong(entryId2)
	// : ICMSConstant.LONG_INVALID_VALUE);
	// entry.setCountry("".equals(country[loop]) ? null : country[loop]);
	// entry.setCategoryCode(categoryCode[loop]);
	// entry.setCategoryCodeId(categoryId.length() > 0 ? Integer
	// .parseInt(categoryId) : 0);
	// entry.setGroupId(groupId2.length() > 0 ? Integer.parseInt(groupId2)
	// : 0);
	//
	// DefaultLogger.debug(CommonCodeEntryUtil.class.getName(),
	// "activeStatus[ " + loop + " ] : " + activeStatus[loop]);
	//
	// list.add(entry);
	// }
	//
	// DefaultLogger.debug(CommonCodeEntryUtil.class.getName(),
	// "Recontruction done");
	//
	// return list;
	// }

	public static boolean compareName(Collection actualDataList, String stagingName, long entryId) {
		Iterator iter = actualDataList.iterator();

		while (iter.hasNext()) {
			OBCommonCodeEntry obEntry = (OBCommonCodeEntry) iter.next();

			if (entryId == obEntry.getEntryId()) {
				if (stagingName == null) {
					return obEntry.getEntryName() == null;
				}
				else {
					return stagingName.equals(obEntry.getEntryName());
				}
			}
		}

		return false;
	}

	public static boolean compareCode(Collection actualDataList, String stagingCode, long entryId) {
		Iterator iter = actualDataList.iterator();

		while (iter.hasNext()) {
			OBCommonCodeEntry obEntry = (OBCommonCodeEntry) iter.next();

			if (entryId == obEntry.getEntryId()) {
				if (stagingCode == null) {
					return obEntry.getEntryCode() == null;
				}
				else {
					return stagingCode.equals(obEntry.getEntryCode());
				}
			}
		}

		return false;
	}

	public static boolean compareCountry(Collection actualDataList, String stagingCountry, long entryId) {
		Iterator iter = actualDataList.iterator();

		while (iter.hasNext()) {
			OBCommonCodeEntry obEntry = (OBCommonCodeEntry) iter.next();

			if (entryId == obEntry.getEntryId()) {
				if (stagingCountry == null) {
					return obEntry.getCountry() == null;
				}
				else {
					return stagingCountry.equals(obEntry.getCountry());
				}
			}
		}

		return false;
	}

	public static boolean compareStatus(Collection actualDataList, boolean stagingStatus, long entryId) {
		Iterator iter = actualDataList.iterator();

		while (iter.hasNext()) {
			OBCommonCodeEntry obEntry = (OBCommonCodeEntry) iter.next();

			if (entryId == obEntry.getEntryId()) {
				return stagingStatus == obEntry.getActiveStatus();
			}
		}

		return false;
	}

	/*
	 * private final static HashMap commonCodeUpdateQueue=new HashMap();
	 * 
	 * public final static void batchUpdateCommonCode(OBCommonCodeEntry entry){
	 * // Use Category Code , Entry Code and Source ID as the Key identifier .
	 * String
	 * key=entry.getCategoryCode()+"_"+entry.getEntryCode()+"_"+entry.getEntrySource
	 * ();
	 * 
	 * commonCodeUpdateQueue.put(key, entry); }
	 */

	/**
	 * This method refresh current server common code list and clustered server
	 * common code list
	 * @param categoryCode
	 */
	public final static synchronized void synchronizeCommonCode(String categoryCode) {
	
		PropertyManager pm = PropertyManager.getInstance();
		boolean syncEnabled = pm.getBoolean("commoncode.sync.enabled", false);

		DefaultLogger.debug(CommonCodeEntryUtil.class, "###############################################################################");
		DefaultLogger.debug(CommonCodeEntryUtil.class, "##### [Synchronize CommonCode] Sync flag set to: " + syncEnabled);

		// If Sync Not enable , refresh local common code only
		// a "*" will denote full refresh
		if (!syncEnabled) {
			if ("*".equals(categoryCode))
				CommonCodeList.refresh();
			else
				CommonCodeList.refresh(categoryCode);

			return;
		}
		
		// to refresh all servers configured
		try {
			CommonCodeSynchronizer sync = new CommonCodeSynchronizer(categoryCode);
			sync.start();
		}
		catch (Exception e) {
			DefaultLogger.error(CommonCodeEntryUtil.class, e.getMessage(), e);
		}
	}

	/**
	 * 
	 * To allow Synchronize of Standard Code cache for all clustered servers
	 * 
	 * 
	 */
	private final static class CommonCodeSynchronizer extends Thread {

		private String categoryCode;

		public void run() {

			DefaultLogger.debug(this, "   ##### [Sync thread] started");
			
			// refresh local server common code list
			if ("*".equals(categoryCode))
				CommonCodeList.refresh();
			else
				CommonCodeList.refresh(categoryCode);

			// Refresh Others Clustered Servers Common Code List
			PropertyManager pm = PropertyManager.getInstance();
			// pm.reload();

			// If Sync Disabled , terminate
			boolean syncEnabled = pm.getBoolean("commoncode.sync.enabled", false);
			if (syncEnabled == false) {
				return;
			}

			// Get Local IP Address
			String realServerName = null;
			try {
				InetAddress thisIp = InetAddress.getLocalHost();
				realServerName = thisIp.getHostAddress().toLowerCase();
				DefaultLogger.debug(this, "   ##### [Sync thread] Local IP: " + thisIp.getHostAddress());
			}
			catch (Exception e) {
				e.printStackTrace();
			}

//-------------------------------------------------------------------- start of main loop ------------------------------------------------------------------------------------------------
			String applicationList = pm.getValue("commoncode.applicationlist", "").trim();
			if ("".equals(applicationList))
				DefaultLogger.debug(this, "   ##### [Sync thread] No Application List provided, using default");
			else
				DefaultLogger.debug(this, "   ##### [Sync thread] Application List: " + applicationList);

			String[] applist = StringUtils.split(applicationList, ",");			
			if (applist.length == 0)
			{
				//no app is set up, so need to use the default : for backward compatible purpose
				applist = new String [1];
				applist[0] = "";
			}

			DefaultLogger.debug(this, "   ##########################################################################");
			for (int app = 0; app < applist.length; app++)
			{
				DefaultLogger.debug(this, "   ##### [Sync thread] appid: " + (app+1) + "/" + applist.length + "  name: [" + applist[app] + "]");
				String currentApp = ("".equals(applist[app])) ? "" : ("." + applist[app]);
				String urlContext = pm.getValue("commoncode.urlcontextpath" + currentApp, "/cms/console/troubleshoot/reload_common_code.jsp?event=refresh").trim();
				DefaultLogger.debug(this, "   ##### [Sync thread] " + "commoncode.urlcontextpath" + currentApp + ": " + urlContext);
				String serverList = pm.getValue("commoncode.serverlist" + currentApp, "localhost:7001").trim();
				DefaultLogger.debug(this, "   ##### [Sync thread] " + "commoncode.serverlist" + currentApp + ": " + serverList);
				boolean useHttps = pm.getBoolean("commoncode.useHttps" + currentApp, false);
				DefaultLogger.debug(this, "   ##### [Sync thread] " + "commoncode.useHttps" + currentApp + ": " + useHttps);
				String protocal = useHttps ? "https://" : "http://";

				String[] svrlist = StringUtils.split(serverList, ",");
				for (int i = 0; i < svrlist.length; i++)
				{
					String firstParameter = (urlContext.indexOf("?") != -1) ? "&" : "?";
					final String URL = protocal + svrlist[i] + urlContext + firstParameter + "code=" + categoryCode + "&trigger=" + realServerName;
//					if (svrlist[i].toLowerCase().indexOf(realServerName) > -1) {
//						// Skip for local server
//						DefaultLogger.debug(this, "   ##### [Sync thread] Skipping local server: " + svrlist[i]);
//						continue;
//					}
//					else {
						// start processing refresh
						DefaultLogger.debug(this, "   ##### [Sync thread] svrid: " + (i+1) + "/" + svrlist.length + "  name: [" + svrlist[i] + "]");

						try {
							processURL (URL);
						}
						catch (Exception e) {
							DefaultLogger.error(this, "   #####      "  + e.getMessage(), e);
						}
//					}
				} //for i loop
			} // for app loop
//-------------------------------------------------------------------- end of main loop ------------------------------------------------------------------------------------------------
		}

		public CommonCodeSynchronizer(String categoryCode) {
			this.categoryCode = categoryCode;
		}

		private String processURL(String URL) throws Exception {		
			DefaultLogger.debug(this, "   ##### [Process URL]");
			URLConnection con = getURLConnection(URL);
			ObjectOutputStream objStream = new ObjectOutputStream(con.getOutputStream());
			// objStream.writeObject(args);
			objStream.flush();
			objStream.close();
			DefaultLogger.debug(this, "   #####      Request send...");
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			DefaultLogger.debug(this, "   #####      Waiting for response...");
			String line;

			StringBuffer sb = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				DefaultLogger.debug(this, "   #####      -------- Response : " + line);
			}

			reader.close();
			return sb.toString();
		}

		private URLConnection getURLConnection(String urlStr) throws Exception {

			DefaultLogger.debug(this, "   #####      [getURLConnection] url: " + urlStr);
			
			if (urlStr == null) {
				throw new Exception("Invalid app url configuration!");
			}
			URL urlObj = null;

			if (urlStr.startsWith("https:")) {
				urlObj = getHttpsURL(urlStr);
			}
			else {
				urlObj = new URL(urlStr);
			}

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

		private void trustAllHttpsCertificates() throws Exception {
			String protocol = PropertyUtil.getInstance("/ofa_env.properties").getProperty("sslortls.protocol");
			System.out.println("protocol:"+protocol);
			SSLContext sc = SSLContext.getInstance(protocol);
			//SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, getTrustAllCerts(), null);
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(getHostNameVerifier());
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

		private HostnameVerifier getHostNameVerifier() {
			HostnameVerifier verifier = new HostnameVerifier() {
				public boolean verify(String urlHostName, SSLSession session) {
					DefaultLogger.debug(this, "   #####      [getHostNameVerifier] URL Host: " + urlHostName + " vs. " + session.getPeerHost());
					return true;
				}
			};
			return verifier;
		}

	}
	
	private static X509TrustManager getX509TrustManager() {
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

}
