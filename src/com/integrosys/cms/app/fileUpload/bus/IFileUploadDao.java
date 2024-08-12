package com.integrosys.cms.app.fileUpload.bus;

import java.io.Serializable;

import com.integrosys.cms.app.FileUploadLog.OBFileUploadLog;

public interface IFileUploadDao {
	
	static final String ACTUAL_FILEUPLOAD_NAME = "actualfileUpload";
	static final String STAGE_FILEUPLOAD_NAME="stagefileUpload";
	static final String FILE_MAPPER = "fileMapper";
	static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
	static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";
	
	
	public IFileUpload makerCreateFile(String entityName,IFileUpload fileUpload)throws FileUploadException; 
	public IFileUpload getFileUpload(String entityName, Serializable key)throws FileUploadException;
	
	public IFileUpload updateFileUpload(String entityName, IFileUpload item)throws FileUploadException;
	public IFileUpload createFileUpload(String entityName,IFileUpload anFile)throws FileUploadException;
	
	public void createUbsFile(String entityName,OBUbsFile fileUpload)throws FileUploadException;
	public void createHongKongFile(String entityName,OBHongKongFile fileUpload)throws FileUploadException;
	public void createFinwareFile(String entityName,OBFinwareFile fileUpload)throws FileUploadException;
	public void createBahrainFile(String entityName,OBBahrainFile fileUpload)throws FileUploadException;
	public void insertFileUploadMessage(OBFileUploadLog obfileuploadlog);
	String getfileuploadidFromSeq();
}
