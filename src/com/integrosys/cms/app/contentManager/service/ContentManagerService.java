package com.integrosys.cms.app.contentManager.service;
 
import com.integrosys.cms.app.contentManager.exception.ContentManagerInitializationException;

import java.io.IOException;

import org.apache.http.client.HttpClient;

import com.ibm.mm.sdk.server.DKDatastoreICM;

public interface ContentManagerService {
	public Object retrieveDocument(Object[] retrieveParams) throws ContentManagerInitializationException, Exception; 
	public Object retrieveDocumentOnly(Object[] retrieveParams,DKDatastoreICM connection) throws ContentManagerInitializationException, Exception; 
	public Object deleteDocuments (Object[] retrieveParams) throws ContentManagerInitializationException, Exception; 
	public Object insertDocuments (Object[] retrieveParams) throws ContentManagerInitializationException, Exception;
	public String put(String hCPAuthHeaderKey, HttpClient client, String auth, String url, String localFilePath) throws IOException; 
	 public String get(String hcpFileName, String status );
}
