package com.integrosys.cms.app.caseCreationUpdate.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.caseCreationUpdate.trx.ICaseCreationTrxValue;
import com.integrosys.cms.app.caseCreationUpdate.trx.ICaseCreationTrxValue;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
/**
 * @author  Abhijit R. 
 */
public interface ICaseCreationDao {

	static final String ACTUAL_CASECREATION_NAME = "actualCaseCreationUpdate";
	static final String STAGE_CASECREATION_NAME = "stageCaseCreationUpdate";
	static final String FILE_MAPPER = "fileMapper";
	static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
	static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";

	ICaseCreation getCaseCreation(String entityName, Serializable key)throws CaseCreationException;
	ICaseCreation updateCaseCreation(String entityName, ICaseCreation item)throws CaseCreationException;
	ICaseCreation deleteCaseCreation(String entityName, ICaseCreation item);
	ICaseCreation load(String entityName,long id)throws CaseCreationException;
	
	ICaseCreation createCaseCreation(String entityName, ICaseCreation caseCreationUpdate)	throws CaseCreationException;
	IFileMapperId insertCaseCreation(String entityName, IFileMapperId fileId, ICaseCreationTrxValue trxValue)	throws CaseCreationException;
	int insertCaseCreation(IFileMapperMaster fileMapperMaster, String userName, ArrayList result);
	IFileMapperId createFileId(String entityName, IFileMapperId fileId) throws CaseCreationException;
	IFileMapperId getInsertFileList(String entityName, Serializable key)throws CaseCreationException;
	ICaseCreation insertActualCaseCreation(String sysId)	throws CaseCreationException;
	ICaseCreation insertCaseCreation(String entityName, ICaseCreation caseCreationUpdate)	throws CaseCreationException;
	boolean isPrevFileUploadPending() throws CaseCreationException;
	List getFileMasterList(String searchBy)throws CaseCreationException;
	List getAllStageCaseCreation (String searchBy, String login)throws RelationshipMgrException;
	public List getCaseCreationByBranchCode(String branchCode) ;
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
}
