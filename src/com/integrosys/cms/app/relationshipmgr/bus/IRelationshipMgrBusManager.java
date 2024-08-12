package com.integrosys.cms.app.relationshipmgr.bus;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

public interface IRelationshipMgrBusManager {
	
	public SearchResult getRelationshipMgrList(String rmCode,String rmName) throws RelationshipMgrException;
	
	public SearchResult getRelationshipMgrList(String regionId) throws RelationshipMgrException;
	
	public boolean isRMCodeUnique(String rmCode);
	
	public IRelationshipMgr deleteRelationshipMgr(IRelationshipMgr relationshipMgr) throws RelationshipMgrException,TrxParameterException,TransactionException;
	
	IRelationshipMgr getRelationshipMgrById(long id) throws RelationshipMgrException,TrxParameterException,TransactionException;
	
	IRelationshipMgr updateRelationshipMgr(IRelationshipMgr item) throws RelationshipMgrException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	IRelationshipMgr updateToWorkingCopy(IRelationshipMgr workingCopy, IRelationshipMgr imageCopy) throws RelationshipMgrException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	IRelationshipMgr createRelationshipMgr(IRelationshipMgr systemBank)throws RelationshipMgrException;
	
	public List getRegionList(String countryCode);

	public boolean isValidRegionCode(String regionCode);
	
	boolean isPrevFileUploadPending() throws RelationshipMgrException;
	int insertRelationshipMgr(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList)throws RelationshipMgrException;
	IFileMapperId insertRelationshipMgr(IFileMapperId fileId, IRelationshipMgrTrxValue idxTrxValue)throws RelationshipMgrException;
	IFileMapperId createFileId(IFileMapperId obFileMapperID)throws RelationshipMgrException;
	IFileMapperId getInsertFileById(long id) throws RelationshipMgrException,TrxParameterException,TransactionException;
	List getAllStageRelationshipMgr(String searchBy, String login)throws RelationshipMgrException,TrxParameterException,TransactionException;
	List getFileMasterList(String searchBy)throws RelationshipMgrException,TrxParameterException,TransactionException;
	IRelationshipMgr insertActualRelationshipMgr(String sysId)throws RelationshipMgrException;
	IRelationshipMgr insertRelationshipMgr(IRelationshipMgr holiday)throws RelationshipMgrException;

	public boolean isRelationshipMgrNameUnique(String relationshipMgrName);
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	
	public boolean isEmployeeIdUnique(String employeeId);
	
	public IHRMSData getHRMSEmpDetails(String rmEmpID);
	
	public IRelationshipMgr getRMDetails(String rmID);
	
	public IHRMSData getLocalCAD(String cadEmployeeCode, String cadBranchCode);
	
	public void insertHRMSData(String[] data);
	
	public void updateHRMSData(IHRMSData  ihrmsData,String[] data);
	
}
