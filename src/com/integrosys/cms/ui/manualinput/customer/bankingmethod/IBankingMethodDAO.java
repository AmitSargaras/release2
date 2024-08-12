package com.integrosys.cms.ui.manualinput.customer.bankingmethod;

import java.util.ArrayList;

import com.integrosys.cms.app.customer.bus.IBankingMethod;
import com.integrosys.cms.app.customer.bus.OBBankingMethod;

/** 
 * Defines methods for operation on Relationship Manager
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 * Tag : $Name$
 */

public interface IBankingMethodDAO {

	public final static String ACTUAL_BANKING_METHOD_ENTITY_NAME = "actualBankingMethodCust";
	
	public final static String STAGING_BANKING_METHOD_ENTITY_NAME = "stagingBankingMethodCust";
	
	public void insertBankingMethodCustStage(OBBankingMethod obj);

	public void insertBankingMethodCustActual(OBBankingMethod obj);

	public String getBankingMethodByCustId(String customerPartyId);

	public void disableActualBankingMethod(String referenceId);
	
	/*public final static String ACTUAL_ENTITY_NAME = "actualRelationshipMgr";
	
	public final static String STAGING_ENTITY_NAME = "stagingRelationshipMgr";
	
	static final String FILE_MAPPER = "fileMapper";
	static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
	static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";
	
	public SearchResult getRelationshipMgrList(String rmCode,String rmName) throws BankingMethodException;
	
	public SearchResult getRelationshipMgrList(String regionId) throws BankingMethodException;
	
	public boolean isRMCodeUnique(String rmCode);

	public SearchResult getRelationshipMgr() throws BankingMethodException;
	
	public IRelationshipMgr getRelationshipMgrById(long id) throws BankingMethodException ;
	 
	public IRelationshipMgr updateRelationshipMgr(IRelationshipMgr relationshipMgr) throws BankingMethodException;
	
	public IRelationshipMgr deleteRelationshipMgr(IRelationshipMgr relationshipMgr) throws BankingMethodException;
	
	public IRelationshipMgr createRelationshipMgr(IRelationshipMgr relationshipMgr) throws BankingMethodException;
	
	IRelationshipMgr getRelationshipMgr(String entityName, Serializable key)
		throws BankingMethodException;

	IRelationshipMgr updateRelationshipMgr(String entityName, IRelationshipMgr item)
		throws BankingMethodException;

	IRelationshipMgr createRelationshipMgr(String entityName, IRelationshipMgr systemBank)
		throws BankingMethodException;
	
	public List getRegionList(String countryCode);	
	
	IFileMapperId insertRelationshipMgr(String entityName, IFileMapperId fileId, IRelationshipMgrTrxValue trxValue)
	throws BankingMethodException;
	int insertRelationshipMgr(IFileMapperMaster fileMapperMaster, String userName, ArrayList result);
	IFileMapperId createFileId(String entityName, IFileMapperId fileId) throws BankingMethodException;
	IFileMapperId getInsertFileList(String entityName, Serializable key)throws BankingMethodException;
	IRelationshipMgr insertActualRelationshipMgr(String sysId)
	throws BankingMethodException;
	IRelationshipMgr insertRelationshipMgr(String entityName, IRelationshipMgr holiday)
	throws BankingMethodException;
	List getAllStageRelationshipMgr (String searchBy, String login)throws BankingMethodException;
	List getFileMasterList(String searchBy)throws BankingMethodException;
	boolean isPrevFileUploadPending() throws BankingMethodException;

	public boolean isValidRegionCode(String regionCode);

	public boolean isRelationshipMgrNameUnique(String relationshipMgrName);
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	*//**
	 * Returns relationship mgr present for the  input relationship code
	 *//*
	public IRelationshipMgr getRelationshipMgrByCode(String id);


	public	IRelationshipMgr getRelationshipMgrByEmployeeCode(String id)
			throws BankingMethodException;

	
	public boolean isEmployeeIdUnique(String employeeId);*/

	/*public IHRMSData getHRMSEmpDetails(String rmEmpID);

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
*/
//	public  void updatePartyRMDetails(String[] data);
}
