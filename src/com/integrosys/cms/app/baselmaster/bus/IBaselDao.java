package com.integrosys.cms.app.baselmaster.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.baselmaster.trx.IBaselMasterTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;

public interface IBaselDao {
	
	static final String ACTUAL_BASEL_NAME = "actualBasel";
	static final String STAGE_BASEL_NAME="stageBasel";
	static final String FILE_MAPPER = "fileMapper";
	static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
	static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";
	
	
	public IBaselMaster createBasel(String entityName,IBaselMaster basel)throws BaselMasterException;
	
	public IBaselMaster getBasel(String entityName, Serializable key)throws BaselMasterException;
	
	public IBaselMaster updateBasel(String entityName, IBaselMaster item)throws BaselMasterException;
	
	public IBaselMaster deleteBasel(String entityName, IBaselMaster item)throws BaselMasterException;
	
	public IFileMapperId insertBasel(String entityName,	IFileMapperId fileId, IBaselMasterTrxValue trxValue)
	throws BaselMasterException;
	
	public int insertBasel(IFileMapperMaster fileMapperMaster, String userName, ArrayList result);
	
	public IFileMapperId createFileId(String entityName, IFileMapperId fileId)
	throws BaselMasterException ;
	
	public IFileMapperId getInsertFileList(String entityName, Serializable key)
	throws BaselMasterException;
	
	public List getFileMasterList(String searchBy) ;
	
	public IBaselMaster insertActualBasel(String sysId)	throws BaselMasterException;
	
	public IBaselMaster insertBasel(String entityName,IBaselMaster basel)
	throws BaselMasterException ;
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) ;
	
	public boolean isUniqueCode(String systemName);
	
	public SearchResult getSearchBaselList(String baselName)throws BaselMasterException;
	
	

}
