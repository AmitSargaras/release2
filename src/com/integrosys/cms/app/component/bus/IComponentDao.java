package com.integrosys.cms.app.component.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;

public interface IComponentDao {

	static final String ACTUAL_COMPONENT_NAME = "actualComponent";
	static final String STAGE_COMPONENT_NAME="stageComponent";
	static final String FILE_MAPPER = "fileMapper";
	static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
	static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";

	IComponent getComponent(String entityName, Serializable key)throws ComponentException;
	IComponent updateComponent(String entityName, IComponent item)throws ComponentException;
	IComponent deleteComponent(String entityName, IComponent item);
	IComponent load(String entityName,long id)throws ComponentException;
	void insertComponentinCommonCode(OBComponent obj);
	void updateComponentinCommonCode(OBComponent actualName,OBComponent stagingName,boolean isNameChanged, boolean isDeleted, boolean isTypeChanged);
	
	
	IComponent createComponent(String entityName, IComponent holiday)
	throws ComponentException;
	IFileMapperId insertComponent(String entityName, IFileMapperId fileId, IComponentTrxValue trxValue)
	throws ComponentException;
	int insertComponent(IFileMapperMaster fileMapperMaster, String userName, ArrayList result);
	IFileMapperId createFileId(String entityName, IFileMapperId fileId) throws ComponentException;
	IFileMapperId getInsertFileList(String entityName, Serializable key)throws ComponentException;
	IComponent insertActualComponent(String sysId)
	throws ComponentException;
	IComponent insertComponent(String entityName, IComponent holiday)
	throws ComponentException;
	boolean isPrevFileUploadPending() throws ComponentException;
	List getFileMasterList(String searchBy)throws ComponentException;
	List getAllStageComponent (String searchBy, String login)throws RelationshipMgrException;
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public boolean isUniqueCode(String componentName);
	public SearchResult getSearchComponentList(String componentName) throws ComponentException;
	public List getActualComponentList()throws ComponentException;
	public List getAgeDetailList()throws ComponentException;
	//start santosh
	public List getComponentCategoryDetailList()throws ComponentException;
	public List getApplicableForDpList()throws ComponentException;
	//end santosh
}
