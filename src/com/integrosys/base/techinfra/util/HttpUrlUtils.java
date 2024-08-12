package com.integrosys.base.techinfra.util;

import java.math.BigDecimal;

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

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;

public class HttpUrlUtils {

	public String processURL(String URL) throws Exception {		
		DefaultLogger.debug(this, "   ##### [Process HTTP URL]");
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

	public URLConnection getURLConnection(String urlStr) throws Exception {

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

	public URL getHttpsURL(String urlStr) throws Exception {
		trustAllHttpsCertificates();
		return new URL(urlStr);
	}

	public void trustAllHttpsCertificates() throws Exception {
		String protocol = PropertyUtil.getInstance("/ofa_env.properties").getProperty("sslortls.protocol");
		System.out.println("protocol:"+protocol);
		SSLContext sc = SSLContext.getInstance(protocol);
		//SSLContext sc = SSLContext.getInstance("SSL);
		sc.init(null, getTrustAllCerts(), null);
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier(getHostNameVerifier());
	}

	public TrustManager[] getTrustAllCerts() {
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

	public HostnameVerifier getHostNameVerifier() {
		HostnameVerifier verifier = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
				DefaultLogger.debug(this, "   #####      [getHostNameVerifier] URL Host: " + urlHostName + " vs. " + session.getPeerHost());
				return true;
			}
		};
		return verifier;
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

}
