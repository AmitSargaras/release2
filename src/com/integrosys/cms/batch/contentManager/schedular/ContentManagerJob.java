package com.integrosys.cms.batch.contentManager.schedular;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
//import java.util.concurrent.ThreadPoolExecutor;

import com.integrosys.base.techinfra.crypto.CryptoManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
/*import com.integrosys.cms.batch.contentManager.ibmDb2Cm.utilities.PropertyUtility;
import com.integrosys.cms.batch.contentManager.threadPool.ContentManagerThread;
import com.integrosys.cms.batch.contentManager.threadPool.ContentManagerThreadPool;*/
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.image.proxy.IImageUploadProxyManager;
import com.integrosys.cms.app.image.proxy.ImageUploadProxyManagerImpl;


public class ContentManagerJob {
	
	/**
	 * This job is run and executed by quartz schedular.
	 * For more details refer to schedular configuration in 
	 * config\spring\batch\contentManger\AppContext_Master.xml
	 * 
	 * Schedular has been designed to carry out the following activities
	 * 1. Pick a thread from the thread pool
	 * 2. Each thread carries out the following tasks
	 *     i. Query oracle database (system database) for newly uploaded images and there path in the filesystem.
	 *    ii. Read the images from the filesystem.
	 *   iii. Push the images to Content manager
	 *    iv. Get the pId returned from Content manager.
	 *     v. Update the oracle database (system database) with the pId.
	 */
	private int noOfLicenses = 1;//Integer.valueOf(PropertyManager.getValue("dms.admin.license"));
	private static final String ADMINUSER = "dms.admin.userName";
	private static final String ADMINPASSWORD = "dms.admin.password";
	private IImageUploadProxyManager imageUploadProxy;
	private List threadList = new ArrayList();
	
	private static final int STATUS_ON_FILE_SYSTEM = 1;
	private static final int STATUS_PROCESSING = 2;
	private static final int STATUS_IN_DMS = 3;
	private static final int STATUS_ERROR = 4;
//	private List<ContentManagerThread> threadList = new ArrayList<ContentManagerThread>();
	
	public void setImageUploadProxy(IImageUploadProxyManager anImageUploadProxyManager) {
		this.imageUploadProxy = anImageUploadProxyManager;
	}
	
	public IImageUploadProxyManager getImageUploadProxy() {
		return this.imageUploadProxy;
	}
	
	public void execute() {	
		try {
			DefaultLogger.debug(this,"Starting Content Manager Thread......");
			List listOfImages = getImages();
			noOfLicenses = Integer.valueOf(PropertyManager.getValue("dms.admin.license")).intValue();
			DefaultLogger.debug(this,"No of images retrieved ......  "+listOfImages.size());
			DefaultLogger.debug(this,"No of Connection Threads before initialization ......" + threadList.size());
			String HtcOrDb2cm = PropertyManager.getValue("cms.image.htcOrDB2CM");
			if(HtcOrDb2cm.equals("DB2CM")) {
				initializeThreadConnections();
				//initializeThreadConnectionsForHTC();
				DefaultLogger.debug(this,"No of Connection Threads after initialization ......" + threadList.size());
				DefaultLogger.debug(this,"No of licenses ......  "+noOfLicenses);
				
				// Update Status to processing.
				updateStatus(listOfImages);
				DefaultLogger.debug(this,"Status Updated ......");
				// Add Images
				Iterator iter = listOfImages.iterator();
				
				int i = 0;
				while (iter.hasNext()) {
					DefaultLogger.debug(this,"Adding Images ......");
					OBImageUploadAdd anOBImageUploadAdd = (OBImageUploadAdd)iter.next();
					((ContentManagerThread) threadList.get(i)).addImageToList(anOBImageUploadAdd);
					i = (++i % noOfLicenses);
				}
				
				startInsert();
				executeTempToActualImageMigration();
			}else {
				initializeThreadConnectionsForHTC();
				DefaultLogger.debug(this,"No of Connection Threads after initializeThreadConnectionsForHTC ......" + threadList.size());
				DefaultLogger.debug(this,"No of licenses ......  "+noOfLicenses);
				
				// Update Status to processing.
				updateStatus(listOfImages);
				DefaultLogger.debug(this,"Status Updated ......");
				// Add Images
				Iterator iter = listOfImages.iterator();
				
				int i = 0;
				while (iter.hasNext()) {
					DefaultLogger.debug(this,"Adding Images ......");
					OBImageUploadAdd anOBImageUploadAdd = (OBImageUploadAdd)iter.next();
					((ContentManagerThreadForHCP) threadList.get(i)).addImageToList(anOBImageUploadAdd);
					i = (++i % noOfLicenses);
				}
				
				startInsertInHTC();
				executeTempToActualImageMigration();
			
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private List getImages() {
		DefaultLogger.debug(this,"Initiating Proxy ------- "+ imageUploadProxy);
		return imageUploadProxy.listTempImagesUpload();///*<Object>*/();
	}
	
	private void initializeThreadConnections() throws Exception {
		threadList = new ArrayList();
		DefaultLogger.debug(this,"initializeThreadConnections ---- "+noOfLicenses);
 		for (int i=1; i<=noOfLicenses; i++) {
			String user = PropertyManager.getValue(ADMINUSER+i);
			String password = PropertyManager.getValue(ADMINPASSWORD+i);
			DefaultLogger.debug(this,"initializeThreadConnections Users---- "+user+"  " +password);
			ContentManagerThread thread = new ContentManagerThread(user, password, this.imageUploadProxy);
			threadList.add(thread);
		}
	}
	
	
	private void initializeThreadConnectionsForHTC() throws Exception {
		threadList = new ArrayList();
		DefaultLogger.debug(this,"initializeThreadConnectionsForHTC ---- "+noOfLicenses);
		String url=PropertyManager.getValue("hcp.rest.url");
    	String localFilePath=PropertyManager.getValue("hcp.localFile.path");
    	String downloadFilePath=PropertyManager.getValue("hcp.downloadFile.path");
    	String HCPAuthHeaderKey=PropertyManager.getValue("hcp.authHeader.key");
    	CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
	 	String result = sec.decrypt(PropertyManager.getValue("hcp.password"));
 		for (int i=1; i<=noOfLicenses; i++) {
		
 			String auth = "HCP " + HTPUtils.getBase64Value(PropertyManager.getValue("hcp.user.name")) + ":" + HTPUtils.getMD5Value(result);
        	System.out.println(PropertyManager.getValue("hcp.user.name") +":--"+HTPUtils.getBase64Value(PropertyManager.getValue("hcp.user.name")));
        	
			DefaultLogger.debug(this,PropertyManager.getValue("hcp.user.name") +":--"+HTPUtils.getBase64Value(PropertyManager.getValue("hcp.user.name")));
			ContentManagerThreadForHCP thread = new ContentManagerThreadForHCP(url, localFilePath, HCPAuthHeaderKey,downloadFilePath,auth,this.imageUploadProxy);
			threadList.add(thread);
		}
	}
	
	private void updateStatus(List listOfImages) {
		if (listOfImages != null) {
			Iterator iter = listOfImages.iterator();
			OBImageUploadAdd anOBImageUploadAdd;
			while (iter.hasNext()) {
				anOBImageUploadAdd = (OBImageUploadAdd)iter.next();
				anOBImageUploadAdd.setStatus(STATUS_PROCESSING);
				imageUploadProxy.updateTempImageUpload(anOBImageUploadAdd);
			}
		}
		
	}
	private void startInsert() throws Exception {
		DefaultLogger.debug(this,"startInsert()......" + threadList.size());
		if (threadList != null) {
			Iterator iter = threadList.iterator();
			while (iter.hasNext()) {
				Thread t = new Thread((ContentManagerThread)iter.next());
				t.start();
			}
		}
		DefaultLogger.debug(this,"end startInsert()......");
	}
	
	private void startInsertInHTC() throws Exception {
		DefaultLogger.debug(this,"startInsert() ContentManagerThreadForHTC......" + threadList.size());
		if (threadList != null) {
			Iterator iter = threadList.iterator();
			while (iter.hasNext()) {
				Thread t = new Thread((ContentManagerThreadForHCP)iter.next());
				t.start();
			}
		}
		DefaultLogger.debug(this,"end startInsert()......");
	}
	
	public ContentManagerJob() {
	}
	
	public static void main(String[] args) {
		new ContentManagerJob().execute();
	}
	//For temp to actual procedure call
	private JdbcTemplate jdbcTemplate;
    private String runProcedureName;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public String getRunProcedureName() {
		return runProcedureName;
	}

	public void setRunProcedureName(String runProcedureName) {
		this.runProcedureName = runProcedureName;
	}
	
	public void executeTempToActualImageMigration() throws Exception {
        try {
        	String fromServer = PropertyManager.getValue("integrosys.server.identification","app1");
        	System.out.println("befor executeTempToActualImageMigration procedure call");
            getJdbcTemplate().execute("{call " + getRunProcedureName() + "('"+fromServer+"')}",  new CallableStatementCallback() {
                public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException { 
                	cs.executeUpdate();
                    return null;
                }
            });
            System.out.println("after executeTempToActualImageMigration procedure call");
        }
        catch (Exception ex) {
        	ex.printStackTrace();
            throw new Exception("Error executeTempToActualImageMigration.................................");
        }
    }
    
	
}
