package com.integrosys.cms.app.newtatmaster.bus;

import java.io.Serializable;

import com.integrosys.cms.app.component.bus.ComponentException;

public interface ITatMasterDao {
	
	static final String ACTUAL_TAT_MASTER_NAME = "actualTatMaster";
	static final String STAGE_TAT_MASTER_NAME="stageTatMaster";
	static final String FILE_MAPPER = "fileMapper";
	static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
	static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";
	
	
	public INewTatMaster getTatMaster(String entityName, Serializable key)throws TatMasterException ;
	
	public INewTatMaster createTatMaster(String entityName,INewTatMaster tatMaster)	throws TatMasterException;
	
	public INewTatMaster updateTatMaster(String entityName, INewTatMaster item)throws ComponentException;

}
