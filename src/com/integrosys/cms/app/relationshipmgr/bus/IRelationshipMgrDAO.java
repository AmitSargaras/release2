package com.integrosys.cms.app.relationshipmgr.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.holiday.bus.IHoliday;
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;
import com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue;
import com.integrosys.cms.ui.relationshipmgr.ILocalCAD;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

/** 
 * Defines methods for operation on Relationship Manager
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 * Tag : $Name$
 */

public interface IRelationshipMgrDAO {
	
	public final static String ACTUAL_ENTITY_NAME = "actualRelationshipMgr";
	
	public final static String STAGING_ENTITY_NAME = "stagingRelationshipMgr";
	
	static final String FILE_MAPPER = "fileMapper";
	static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
	static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";
	
	public SearchResult getRelationshipMgrList(String rmCode,String rmName) throws RelationshipMgrException;
	
	public SearchResult getRelationshipMgrList(String regionId) throws RelationshipMgrException;
	
	public boolean isRMCodeUnique(String rmCode);

	public SearchResult getRelationshipMgr() throws RelationshipMgrException;
	
	public IRelationshipMgr getRelationshipMgrById(long id) throws RelationshipMgrException ;
	 
	public IRelationshipMgr updateRelationshipMgr(IRelationshipMgr relationshipMgr) throws RelationshipMgrException;
	
	public IRelationshipMgr deleteRelationshipMgr(IRelationshipMgr relationshipMgr) throws RelationshipMgrException;
	
	public IRelationshipMgr createRelationshipMgr(IRelationshipMgr relationshipMgr) throws RelationshipMgrException;
	
	IRelationshipMgr getRelationshipMgr(String entityName, Serializable key)
		throws RelationshipMgrException;

	IRelationshipMgr updateRelationshipMgr(String entityName, IRelationshipMgr item)
		throws RelationshipMgrException;

	IRelationshipMgr createRelationshipMgr(String entityName, IRelationshipMgr systemBank)
		throws RelationshipMgrException;
	
	public List getRegionList(String countryCode);	
	
	IFileMapperId insertRelationshipMgr(String entityName, IFileMapperId fileId, IRelationshipMgrTrxValue trxValue)
	throws RelationshipMgrException;
	int insertRelationshipMgr(IFileMapperMaster fileMapperMaster, String userName, ArrayList result);
	IFileMapperId createFileId(String entityName, IFileMapperId fileId) throws RelationshipMgrException;
	IFileMapperId getInsertFileList(String entityName, Serializable key)throws RelationshipMgrException;
	IRelationshipMgr insertActualRelationshipMgr(String sysId)
	throws RelationshipMgrException;
	IRelationshipMgr insertRelationshipMgr(String entityName, IRelationshipMgr holiday)
	throws RelationshipMgrException;
	List getAllStageRelationshipMgr (String searchBy, String login)throws RelationshipMgrException;
	List getFileMasterList(String searchBy)throws RelationshipMgrException;
	boolean isPrevFileUploadPending() throws RelationshipMgrException;

	public boolean isValidRegionCode(String regionCode);

	public boolean isRelationshipMgrNameUnique(String relationshipMgrName);
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	/**
	 * Returns relationship mgr present for the  input relationship code
	 */
	public IRelationshipMgr getRelationshipMgrByCode(String id);


	public	IRelationshipMgr getRelationshipMgrByEmployeeCode(String id)
			throws RelationshipMgrException;

	
	public boolean isEmployeeIdUnique(String employeeId);

	public IHRMSData getHRMSEmpDetails(String rmEmpID);

	public IRelationshipMgr getRMDetails(String rmID);

	public IHRMSData getLocalCAD(String cadEmployeeCode, String cadBranchCode);
	
	public void insertHRMSData(String[] data);
	
	public void updateHRMSData(IHRMSData  ihrmsData,String[] data);
	
	public void updateRMData(IRelationshipMgr  iRelationshipMgr,String[] data);	
	
	public ILocalCAD createLocalCAD(String entityName,
			ILocalCAD localCAD)
			throws Exception;
	
	public List getLocalCADs(String rmCode);
	
	public IRegion getRegionByRegionCode(String regionCode);
	
	public IRegion getRegionByRegionName(String regionName);

	public IRelationshipMgr getRelationshipMgrByName(String relationshipMgrName, String rmMgrCode);
	
	public void insertData(IFileUpload fileUpload);

	public List getStagingLocalCADs(String relationshipMgrCode);

	public ILocalCAD updateStagingLocalCAD(String string, ILocalCAD localCAD) throws Exception;

	public List getStagingDeletedLocalCADs(String relationshipMgrCode);

	public List getStagingCreatedAndDeletedLocalCADs(String relationshipMgrCode);

	public ILocalCAD updateLocalCAD(String string, ILocalCAD localCAD)  throws Exception;

	public IRelationshipMgr getRelationshipMgrByNameAndRMCode(String relationshipManager, String rmMgrCode);

//	public  void updatePartyRMDetails(String[] data);
}
