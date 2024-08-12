package com.integrosys.cms.app.caseBranch.bus;

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
import com.integrosys.cms.app.caseBranch.trx.ICaseBranchTrxValue;
import com.integrosys.cms.app.caseBranch.trx.ICaseBranchTrxValue;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
/**
 * @author  Abhijit R. 
 */
public interface ICaseBranchDao {

	static final String ACTUAL_CASEBRANCH_NAME = "actualCaseBranch";
	static final String STAGE_CASEBRANCH_NAME = "stageCaseBranch";
	static final String FILE_MAPPER = "fileMapper";
	static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
	static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";

	ICaseBranch getCaseBranch(String entityName, Serializable key)throws CaseBranchException;
	ICaseBranch updateCaseBranch(String entityName, ICaseBranch item)throws CaseBranchException;
	ICaseBranch deleteCaseBranch(String entityName, ICaseBranch item);
	ICaseBranch load(String entityName,long id)throws CaseBranchException;
	
	ICaseBranch createCaseBranch(String entityName, ICaseBranch caseBranch)
	throws CaseBranchException;
	IFileMapperId insertCaseBranch(String entityName, IFileMapperId fileId, ICaseBranchTrxValue trxValue)
	throws CaseBranchException;
	int insertCaseBranch(IFileMapperMaster fileMapperMaster, String userName, ArrayList result);
	IFileMapperId createFileId(String entityName, IFileMapperId fileId) throws CaseBranchException;
	IFileMapperId getInsertFileList(String entityName, Serializable key)throws CaseBranchException;
	ICaseBranch insertActualCaseBranch(String sysId)
	throws CaseBranchException;
	ICaseBranch insertCaseBranch(String entityName, ICaseBranch caseBranch)
	throws CaseBranchException;
	boolean isPrevFileUploadPending() throws CaseBranchException;
	List getFileMasterList(String searchBy)throws CaseBranchException;
	List getAllStageCaseBranch (String searchBy, String login)throws RelationshipMgrException;
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	public boolean isUniqueCode(String coloumn ,String value)throws Exception;
}
