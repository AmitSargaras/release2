package com.integrosys.cms.app.fccBranch.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.fccBranch.trx.IFCCBranchTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
/***
 * 
 * @author komal.agicha
 *
 */
public interface IFCCBranchDao {

	static final String ACTUAL_FCCBRANCH_NAME = "actualFCCBranch";
	static final String STAGE_FCCBRANCH_NAME = "stageFCCBranch";
	static final String FILE_MAPPER = "fileMapper";
	static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
	static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";

	IFCCBranch getFCCBranch(String entityName, Serializable key)throws FCCBranchException;
	IFCCBranch updateFCCBranch(String entityName, IFCCBranch item)throws FCCBranchException;
	IFCCBranch deleteFCCBranch(String entityName, IFCCBranch item);
	IFCCBranch load(String entityName,long id)throws FCCBranchException;
	
	IFCCBranch createFCCBranch(String entityName, IFCCBranch fccBranch)
	throws FCCBranchException;
	IFileMapperId insertFCCBranch(String entityName, IFileMapperId fileId, IFCCBranchTrxValue trxValue)
	throws FCCBranchException;
	int insertFCCBranch(IFileMapperMaster fileMapperMaster, String userName, ArrayList result);
	IFileMapperId createFileId(String entityName, IFileMapperId fileId) throws FCCBranchException;
	IFileMapperId getInsertFileList(String entityName, Serializable key)throws FCCBranchException;
	IFCCBranch insertActualFCCBranch(String sysId)
	throws FCCBranchException;
	IFCCBranch insertFCCBranch(String entityName, IFCCBranch fccBranch)
	throws FCCBranchException;
	boolean isPrevFileUploadPending() throws FCCBranchException;
	List getFileMasterList(String searchBy)throws FCCBranchException;
	List getAllStageFCCBranch (String searchBy, String login)throws RelationshipMgrException;
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	public String fccBranchUniqueCombination(String branchCode, String aliasBranchCode,long id) throws FCCBranchException; 
		
	public List getFccBranchList();
}
