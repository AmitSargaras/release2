package com.integrosys.cms.batch.contentManager.schedular;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.ibm.mm.sdk.common.DKException;
import com.ibm.mm.sdk.server.DKDatastoreICM;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.contentManager.exception.ContentManagerInitializationException;
import com.integrosys.cms.app.contentManager.factory.ContentManagerFactory;
import com.integrosys.cms.app.contentManager.ibmDb2Cm.utilities.ContentManagerHelper;
import com.integrosys.cms.app.contentManager.service.ContentManagerService;
import com.integrosys.cms.app.image.bus.IImageUploadDao;
import com.integrosys.cms.app.image.bus.ImageUploadDaoImpl;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.image.proxy.IImageUploadProxyManager;

public class ContentManagerThreadForHCP  implements Runnable {

	private String userId;
	private String password;
	List imageList = new ArrayList();
	//DKDatastoreICM connection;
	IImageUploadProxyManager proxyManager;
	
	private String url;
	private  String localFilePath;
	private String hCPAuthHeaderKey;
	private String downloadFilePath;
	private String auth;
	private JdbcTemplate jdbcTemplate;
	
	


	public ContentManagerThreadForHCP(String url, String localFilePath, String hCPAuthHeaderKey,
			String downloadFilePath, String auth,IImageUploadProxyManager proxyManager) throws Exception {
		this.url = url;
		this.localFilePath = localFilePath;
		this.hCPAuthHeaderKey = hCPAuthHeaderKey;
		this.downloadFilePath = downloadFilePath;
		this.auth = auth;
		this.proxyManager = proxyManager;
		DefaultLogger.debug(this,"ContentManagerThread ------- "+ this.url);
		DefaultLogger.debug(this,"ContentManagerThread ------- "+ this.localFilePath);
		DefaultLogger.debug(this,"ContentManagerThread ------- "+ this.hCPAuthHeaderKey);
		DefaultLogger.debug(this,"ContentManagerThread ------- "+ this.downloadFilePath);
		if (url == null ) {
			throw new ContentManagerInitializationException("Error connecting to content manager datasource : No Credentials Configured.");
		}
	}

	

	public void run() {
		IImageUploadDao dao = new ImageUploadDaoImpl();
		//new ContentManagerJob().getSequenceNo();
		DefaultLogger.debug(this,"Starting Thread with userId ------- "+ userId +"\nNo of Images to Process: ------- "+imageList.size());
		try {
			//this.connection = ContentManagerHelper.initializeDatastore(userId, password);
			DefaultLogger.debug(this,"Initializing connection --" + userId+"@"+password+"------");
			ContentManagerService contentManagerService = ContentManagerFactory.getContentManagerService();
			//Session session = new ImageUploadDaoImpl().getHibernateTemplate().getSessionFactory().openSession();
		//	Transaction transaction = session.beginTransaction();
			OBImageUploadAdd aOBImageUploadAdd;
			OBImageUploadAdd aOBImageUploadAddNS;
			Object[] params;
			//SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			java.util.Date date=new java.util.Date();
			java.sql.Timestamp sqlTime=new java.sql.Timestamp(date.getTime());
			Random random = new Random();
			if (imageList != null) {
				Iterator iter = imageList.iterator();
				while (iter.hasNext()) {
					aOBImageUploadAddNS = (OBImageUploadAdd) iter.next();
					String getMiliSecond=format2.format(new Date());
					aOBImageUploadAdd = null;
					//aOBImageUploadAdd = (OBImageUploadAdd)session.load(aOBImageUploadAddNS.getClass(), aOBImageUploadAddNS.getImgId());
					try {
						DefaultLogger.debug(this,"Proxy ------" + proxyManager);
						aOBImageUploadAdd = (OBImageUploadAdd) proxyManager.getTempImageUploadById(aOBImageUploadAddNS.getImgId());
						DefaultLogger.debug(this,"run() == Inserting ---- "+aOBImageUploadAdd.getImageFilePath());
						/*params = new Object[2];
						params[0] = connection;
						params[1] = aOBImageUploadAdd;*/
						
						HttpClient client = HttpClientBuilder.create().build();
						String HCPFileName = aOBImageUploadAdd.getCustId()+format2.format(new Date())+String.valueOf(random.nextInt(9999))+"."+getMimeTypeForHTC(aOBImageUploadAdd.getImageFilePath());
						String imgstatus = contentManagerService.put(hCPAuthHeaderKey ,client, auth,  url.concat(HCPFileName),  aOBImageUploadAdd.getImageFilePath());
						//String pid = (String) contentManagerService.insertDocuments(params);

						
						
						aOBImageUploadAdd.setImageFilePath(HCPFileName);
						aOBImageUploadAdd.setStatus(3);
						aOBImageUploadAdd.setHCPFileName(aOBImageUploadAdd.getImgFileName());
						aOBImageUploadAdd.setHCPFileName(HCPFileName);
						System.out.println("getHCPFileName.............."+aOBImageUploadAdd.getHCPFileName());
						aOBImageUploadAdd.setHCPStatus("Y");
						System.out.println("getHCPStatus.............."+aOBImageUploadAdd.getHCPStatus());
						aOBImageUploadAdd.setHCPStatusCode(imgstatus);
						System.out.println("getHCPStatusCode.............."+aOBImageUploadAdd.getHCPStatusCode());
						aOBImageUploadAdd.setHCPMoveDate(sqlTime);
						System.out.println("getHCPMoveDate.............."+aOBImageUploadAdd.getHCPMoveDate());
						proxyManager.updateTempImageUpload(aOBImageUploadAdd);
						System.out.println("proxyManager.updateTempImageUpload...");
						//dao.updateTempImageUpload(aOBImageUploadAdd);
				//		session.update(aOBImageUploadAdd);
					} 
					catch (Exception e) {
						e.printStackTrace();
						if (aOBImageUploadAdd != null) {
							aOBImageUploadAdd.setStatus(4);
							aOBImageUploadAdd.setHCPStatus("N");
							aOBImageUploadAdd.setError(e.getMessage());
							proxyManager.updateTempImageUpload(aOBImageUploadAdd);
						}
					//	dao.updateTempImageUpload(aOBImageUploadAdd);
				//		session.update(aOBImageUploadAdd);
					}
				}
			}
			//transaction.commit();
			//session.close();
		} 
		catch (ContentManagerInitializationException e) {
			e.printStackTrace();
			if (imageList != null) {
				Iterator iter = imageList.iterator();
				OBImageUploadAdd aOBImageUploadAdd;
				while (iter.hasNext()) {
					aOBImageUploadAdd = (OBImageUploadAdd ) iter.next();
					aOBImageUploadAdd.setStatus(1);
					dao.updateTempImageUpload(aOBImageUploadAdd);
				}
			}
		} 
		
	}
	
	public void addImageToList(OBImageUploadAdd thread) {
		imageList.add(thread);
	}
	
	
	/*public static long getSequenceNo(){
		Long seqNo=null;
		//Connection conn = null;
		//String regionName = null;
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL("SELECT HCP_IMAGE_SEQ.NEXTVAL FROM dual");
			rs=dbUtil.executeQuery();
			while (rs.next()){	
				seqNo = rs.getLong(1);
			}
			
			dbUtil.close();
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return seqNo;
	
	}*/
	
	
	
	 private String getMimeType(String filePath)
	    {   
	    	DefaultLogger.debug(this,filePath);
	    	filePath=filePath.toLowerCase();
	    	DefaultLogger.debug(this,filePath);
	    	String mimeType=null;
	    	if (filePath.endsWith(".tif") ||filePath.endsWith(".tiff") ) {
	    		mimeType=PropertyManager.getValue(".tif");
	    	}
	    	else if(filePath.endsWith(".jpeg")) {
	    		mimeType=PropertyManager.getValue(".jpeg");
	    	}
	    	else if(filePath.endsWith(".jpg")) {
	    		mimeType=PropertyManager.getValue(".jpg");
	    	}
	    	else if(filePath.endsWith(".gif")) {
	    		mimeType=PropertyManager.getValue(".gif");
	    	}
	    	else if(filePath.endsWith(".pdf")) {
	    		mimeType=PropertyManager.getValue(".pdf");
	    	}
	    	DefaultLogger.debug(this,"Returning mime type is "+mimeType);
			return mimeType;   	
	    }  
	    
	 
	 private String getMimeTypeForHTC(String filePath)
	    {   
	    	 
	    	filePath=filePath.toLowerCase();
	    	 
	    	String mimeType=null;
	    	if (filePath.endsWith(".tiff") ) {
	    		mimeType="tiff";
	    	}
	    	else if(filePath.endsWith(".jpeg")) {
	    		mimeType="jpeg";
	    	}
	    	else if(filePath.endsWith(".jpg")) {
	    		mimeType="jpeg";
	    	}
	    	else if(filePath.endsWith(".gif")) {
	    		mimeType="gif";
	    	}
	    	else if(filePath.endsWith(".pdf")) {
	    		mimeType="pdf";
	    	}else if(filePath.endsWith(".tif")) {
	    		mimeType="tiff";
	    	}
	    	 
			return mimeType;   	
	    }
	    
	
	
}

