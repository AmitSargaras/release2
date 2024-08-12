package com.integrosys.cms.app.contentManager.ibmDb2Cm.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.ibm.mm.sdk.common.DKConstant;
import com.ibm.mm.sdk.common.DKException;
import com.ibm.mm.sdk.common.DKImageICM;
import com.ibm.mm.sdk.common.DKRetrieveOptionsICM;
import com.ibm.mm.sdk.server.DKDatastoreICM;
import com.integrosys.base.techinfra.crypto.CryptoManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.contentManager.exception.ContentManagerInitializationException;
import com.integrosys.cms.app.contentManager.ibmDb2Cm.connectionPool.DB2ConnectionPool;
import com.integrosys.cms.app.contentManager.service.ContentManagerService;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.batch.contentManager.schedular.HTPUtils;


public class DB2ContentManagerService implements ContentManagerService {

	public static DKDatastoreICM dsICM;
	public Object deleteDocuments(Object[] retrieveParams) throws Exception { 
		boolean isDeleted  = false;
		DKDatastoreICM connection = (DKDatastoreICM) this.getDataStore(new Object[1]);
		if (retrieveParams == null || retrieveParams.length != 2) {
			throw new Exception("More Data Needed for Retrieving Image.");
		}
		String mimeType = (String) retrieveParams[0];
		String imagePID = (String) retrieveParams[1];
		try{
			DKImageICM image = (DKImageICM) connection.createDDO("S_image", DKConstant.DK_CM_ITEM);     // in the Item Type Definition.
			image.setMimeType(mimeType); // First we will need to know what items to retrieve.  The PID information is needed.
			image = (DKImageICM) connection.createDDO(imagePID); 
			image.del();
			connection.disconnect();
			connection.destroy();
			isDeleted  = true;
		}
		catch (Exception e) {
			isDeleted  = false;
		}
		return new Boolean(isDeleted);
	}

	public Object insertDocuments(Object[] retrieveParams) throws ContentManagerInitializationException, Exception {
		DefaultLogger.debug(this,"insertDocuments() Retrieve Params ----- " + retrieveParams +"  input size "+retrieveParams.length);
		String imagePid;
		if (retrieveParams == null || retrieveParams.length != 2) {
			throw new Exception("More Data Needed for inserting Image.");
		}
		DKDatastoreICM connection = null;
		try {
			connection = (DKDatastoreICM) retrieveParams[0];
			//connection = (DKDatastoreICM) DB2ConnectionPool.getConnectionPoolInstance().borrowObject();
			DefaultLogger.debug(this,"insertDocuments() connection ------ "+connection);
			OBImageUploadAdd aOBImageUploadAdd = (OBImageUploadAdd) retrieveParams[1];
			DefaultLogger.debug(this,"insertDocuments() aOBImageUploadAdd ------ "+aOBImageUploadAdd);
			DKImageICM image = (DKImageICM) connection.createDDO("S_image",DKConstant.DK_CM_ITEM);
			String filePath = aOBImageUploadAdd.getImageFilePath();
			String mime = getMimeType(aOBImageUploadAdd.getImageFilePath());
			DefaultLogger.debug(this,"insertDocuments() Inserting ++++++ " +  filePath + "   " + mime);
			image.setMimeType(mime);
			image.setContentFromClientFile(filePath);
			DefaultLogger.debug(this,"Inserting Image @ --- "+ aOBImageUploadAdd.getImageFilePath()+ " \nUsing Connection " + connection);
			image.add();
			imagePid = image.getPidObject().pidString();
			DefaultLogger.debug(this,"PID Generated --- " + imagePid);
			aOBImageUploadAdd.setImageFilePath(imagePid);
			aOBImageUploadAdd.setStatus(3);
		}
        catch (DKException e) {
        	e.printStackTrace();
        	throw new Exception("Error inserting image into content manager.");
		} 
        catch (Exception e) {
        	e.printStackTrace();
        	throw new Exception("Error inserting image into content manager.");
        }
		return imagePid;
	}

	/**
	 * Retrieve Image from IBM DB2 Content Manager.
	 */
	public Object retrieveDocument(Object[] retrieveParams) throws ContentManagerInitializationException, Exception {
		DefaultLogger.debug(this,"Input Retrieve Params ----- " + retrieveParams);
		DKDatastoreICM connection = (DKDatastoreICM) DB2ConnectionPool.getConnectionPoolInstance().borrowObject();
		if (retrieveParams == null || retrieveParams.length != 3) {
			throw new Exception("More Data Needed for Retrieving Image.");
		}
		String imagePath="";
		String relativePath = PropertyManager.getValue("presetFolder")+"sorry.jpg";
	//	String mimeType = (String) retrieveParams[0];
		String filePID  = (String) retrieveParams[0];
		String fileName = (String) retrieveParams[1];
		String status = (String) retrieveParams[2];
		if ("2".equals(status) || "0".equals(status) || "1".equals(status)) {
			relativePath = PropertyManager.getValue("presetFolder")+"inProgress.jpg";
			DB2ConnectionPool.getConnectionPoolInstance().returnObject(connection);
			return relativePath;
		}
		else if ("4".equals(status)) {
			DB2ConnectionPool.getConnectionPoolInstance().returnObject(connection);
			return relativePath;
		}
		DefaultLogger.debug(this,"File Id  : --------" + filePID);
		DefaultLogger.debug(this,"File Name: --------" + fileName);
		//.substring(fileName.lastIndexOf(".") + 1)
		String mimeType = getMimeType(fileName);
		try {
		DKImageICM image = (DKImageICM) connection.createDDO("S_image", DKConstant.DK_CM_ITEM);     // in the Item Type Definition.
        image.setMimeType(mimeType); // First we will need to know what items to retrieve.  The PID information is needed.
        image = (DKImageICM) connection.createDDO(filePID);        
        DKRetrieveOptionsICM dkRetrieveOptions = DKRetrieveOptionsICM.createInstance(connection);
        dkRetrieveOptions.resourceContent(true);
        relativePath = PropertyManager.getValue("dmsTempFolder")+filePID.replace(' ', '_')+"."+mimeType.split("/")[1];
     //   String imagePath = PropertyManager.getValue("contextPath")+relativePath+fileName;//+"."+mimeType.split("/")[1];
        imagePath = PropertyManager.getValue("contextPath")+relativePath;//+fileName;//+"."+mimeType.split("/")[1];
        image.retrieve(imagePath,dkRetrieveOptions.dkNVPair()); // saved in the specified file.          
		//connection.disconnect();
        //connection.destroy();
		}
		catch (Exception e) {
		}
		finally {
		DB2ConnectionPool.getConnectionPoolInstance().returnObject(connection);
		}
		DefaultLogger.debug(this,"Retrieving Image to ------------ > " + imagePath);
		DefaultLogger.debug(this,"Returning path ------------ > " + relativePath);
		return relativePath;
	}

	/**
	 * Retrieve Image from IBM DB2 Content Manager.
	 */
	public Object retrieveDocumentOnly(Object[] retrieveParams,DKDatastoreICM connection) throws ContentManagerInitializationException, Exception {
		DefaultLogger.debug(this,"Input Retrieve Params ----- " + retrieveParams);
		//DKDatastoreICM connection = (DKDatastoreICM) DB2ConnectionPool.getConnectionPoolInstance().borrowObject();
		if (retrieveParams == null || retrieveParams.length != 3) {
			throw new Exception("More Data Needed for Retrieving Image.");
		}
		String imagePath="";
		String relativePath = PropertyManager.getValue("presetFolder")+"sorry.jpg";
	//	String mimeType = (String) retrieveParams[0];
		String filePID  = (String) retrieveParams[0];
		String fileName = (String) retrieveParams[1];
		String status = (String) retrieveParams[2];
		if ("2".equals(status) || "0".equals(status) || "1".equals(status)) {
			relativePath = PropertyManager.getValue("presetFolder")+"inProgress.jpg";
			return relativePath;
		}
		else if ("4".equals(status)) {
			return relativePath;
		}
		DefaultLogger.debug(this,"File Id  : --------" + filePID);
		DefaultLogger.debug(this,"File Name: --------" + fileName);
		//.substring(fileName.lastIndexOf(".") + 1)
		String mimeType = getMimeType(fileName);
		try {
		DKImageICM image = (DKImageICM) connection.createDDO("S_image", DKConstant.DK_CM_ITEM);     // in the Item Type Definition.
        image.setMimeType(mimeType); // First we will need to know what items to retrieve.  The PID information is needed.
        image = (DKImageICM) connection.createDDO(filePID);        
        DKRetrieveOptionsICM dkRetrieveOptions = DKRetrieveOptionsICM.createInstance(connection);
        dkRetrieveOptions.resourceContent(true);
        relativePath = PropertyManager.getValue("dmsTempFolder")+filePID.replace(' ', '_')+"."+mimeType.split("/")[1];
     //   String imagePath = PropertyManager.getValue("contextPath")+relativePath+fileName;//+"."+mimeType.split("/")[1];
        imagePath = PropertyManager.getValue("contextPath")+relativePath;//+fileName;//+"."+mimeType.split("/")[1];
        image.retrieve(imagePath,dkRetrieveOptions.dkNVPair()); // saved in the specified file.          
		//connection.disconnect();
        //connection.destroy();
		}
		catch (Exception e) {
		}
		finally {
		//DB2ConnectionPool.getConnectionPoolInstance().returnObject(connection);
		}
		DefaultLogger.debug(this,"Retrieving Image to ------------ > " + imagePath);
		DefaultLogger.debug(this,"Returning path ------------ > " + relativePath);
		return relativePath;
	}
	
	
	public Object getDataStore(Object[] retrieveParams) throws ContentManagerInitializationException {
		//if (dsICM == null) {
			//initializeDatastore();
	//	}
			return dsICM;
	}
	
	// ----------- Utility Methods ----------------

	
	private String getFileName(Object[] retrieveParams) throws Exception {
		if (retrieveParams == null || retrieveParams.length == 0) {
			throw new Exception("Invalid File Name");
		}
		return (String)retrieveParams[0];
	}
	
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
    
    public static void main(String[] args) {
    	String mimeType = "ok.isl.jpg".substring("ok.isl.jpg".lastIndexOf(".")+1);
    	System.out.println(mimeType);
	}
    
    
    

	 public String put(String HCPAuthHeaderKey,HttpClient client, String auth, String url,String localFilePath ) throws IOException{
	        //specify namespace URL - eg. namespace.tenant.HCP.DOMAIN.com/rest/path
	        //   String url = "http://example-namespace.example-tenant.hcp1.hcpdemo.com/rest/examples/world.txt";
	        //specify path to file you want to upload(PUT)
	       // String localFilePath = "C:/Users/Administrator/git/HCPJavaRestExamples/world.txt";
		
	        System.out.println("Curl Statement Output: curl -iT " + localFilePath + " -H \"Authorization: " + auth + "\" " + url);

	        HttpPut request = new HttpPut(url);
	        File input = new File(localFilePath);
	        try {
	        //add authorization header for user(base64) "exampleuser" with password(md5) "passw0rd"
	        request.addHeader(HCPAuthHeaderKey, auth);

	        //UNCOMMENT TO USE CONDITIONAL PUT
	        //request.addHeader("If-None-Match", "86d434bcb3a1af9b0b764fc7dd4dd31a");
	        //request.addHeader("If-Match", "f363f9556891bd306aedd590b4d23a1e");
	        //request.addHeader("Expect", "100-CONTINUE");
	        

	        //setup byte array for file to upload(PUT)
	        
	        byte[] fileAsByteArr = HTPUtils.fileToByteArray(input);

	        ByteArrayEntity requestEntity = new ByteArrayEntity(fileAsByteArr);

	        //set the request to use the byte array
	        request.setEntity(requestEntity);
	        //execute PUT request
	        }
	        catch (Exception e) {
				e.printStackTrace();
			}
	        DefaultLogger.debug(this,"Request : "+request);
	        HttpResponse response = client.execute(request);
	        System.out.println("Response Code : "
	                + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
	       
	        return response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase();

	        //print response status to console
	        
		 
		 
	    }
	 
	 
	 public String get(String hcpFileName, String status ) {
	        //specify namespace URL - eg. namespace.tenant.HCP.DOMAIN.com/rest/path
	      //  String url = "http://example-namespace.example-tenant.hcp1.hcpdemo.com/rest/examples/world.txt";
		 	String url=PropertyManager.getValue("hcp.rest.url");
		 	System.out.println("url............"+url);
		 	String localFilePath=PropertyManager.getValue("hcp.localFile.path");
		 	System.out.println("localFilePath............"+localFilePath);
		 	String downloadFilePath=PropertyManager.getValue("hcp.downloadFile.path");
		 	System.out.println("downloadFilePath............"+downloadFilePath);
		 	String HCPAuthHeaderKey=PropertyManager.getValue("hcp.authHeader.key");
		 	System.out.println("HCPAuthHeaderKey............"+HCPAuthHeaderKey);
		 	HttpClient client = HttpClientBuilder.create().build();
		 	String relativePath = PropertyManager.getValue("presetFolder")+"sorry.jpg";
		 	CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
		 	String result = sec.decrypt(PropertyManager.getValue("hcp.password"));
		 	try{
		 	String auth = "HCP " + HTPUtils.getBase64Value(PropertyManager.getValue("hcp.user.name")) + ":" + HTPUtils.getMD5Value(result);
		 	System.out.println("auth............"+auth);
		 	//String auth = "HCP " + Utils.getBase64Value(PropertyManager.getValue(("user")) + ":" + Utils.getMD5Value(PropertyManager.getValue(("password"));
		 	
	        HttpGet request = new HttpGet(url.concat(hcpFileName));
	        
	        //   String imagePath = PropertyManager.getValue("contextPath")+relativePath+fileName;//+"."+mimeType.split("/")[1];
	       
	        //add authorization header for user(base64) "exampleuser" with password(md5) "passw0rd"
	        
	        
	        
	        if ("2".equals(status) || "0".equals(status) || "1".equals(status)) {
				relativePath = PropertyManager.getValue("presetFolder")+"inProgress.jpg";
				return relativePath;
			}
			else if ("4".equals(status)) {
				return relativePath;
			}
	        System.out.println("hcpFileName............"+hcpFileName);
	        
	        if (hcpFileName.endsWith(".ods")||hcpFileName.endsWith(".DAT")||hcpFileName.endsWith(".doc")||hcpFileName.endsWith(".db")||hcpFileName.endsWith(".sxw")||
	        		hcpFileName.endsWith(".BMP")||hcpFileName.endsWith(".zip")||hcpFileName.endsWith(".bmp")||hcpFileName.endsWith(".JPG")||hcpFileName.endsWith(".gif")||
	        		hcpFileName.endsWith(".GIF")||hcpFileName.endsWith(".ini")||hcpFileName.endsWith(".rtf")) {
	        	return  PropertyManager.getValue("presetFolder")+"sorry.jpg";
	        }
	        
	        relativePath = PropertyManager.getValue("dmsTempFolder")+hcpFileName;
	        //String hcpFileNameWithRelativePath = relativePath;
	        //System.out.println("hcpFileNameWithRelativePath............"+hcpFileNameWithRelativePath);
	        String  imagePath = PropertyManager.getValue("contextPath")+relativePath;
	        System.out.println("imagePath............"+imagePath);
	        System.out.println("relativePath............"+relativePath);
	          
	        
	        
	        request.addHeader(HCPAuthHeaderKey, auth);
	        DefaultLogger.debug(this,"Request : "+request);	        
	        HttpResponse response = client.execute(request);

	        //print response status to console
	        System.out.println("Response Code : "
	                + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());

	        
	        
	        BufferedInputStream bis = new BufferedInputStream(response.getEntity().getContent());
	     // String filePath = "/tmp/outfile.txt";
	      BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(imagePath)));
	      int inByte;
	      while((inByte = bis.read()) != -1) bos.write(inByte);
	      bis.close();
	      bos.close();
	     
		 	}
		 	catch (Exception e) {
				 e.printStackTrace();
			 }
	      
		 	 return relativePath;
	    }

	 
	 
	 
}
