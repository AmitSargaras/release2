package com.integrosys.cms.app.systemBankBranch.bus;

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
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListSubItem;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue;

/**
 * @author Abhijit R.
 */
public interface ISystemBankBranchDao {

	static final String ACTUAL_SYSTEM_BANK_BRANCH_NAME = "actualSystemBankBranch";
	static final String STAGE_SYSTEM_BANK_BRANCH_NAME = "stageSystemBankBranch";
	static final String FILE_MAPPER = "fileMapper";
	static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
	static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";

	ISystemBankBranch getSystemBankBranch(String entityName, Serializable key)
			throws SystemBankBranchException;
	
	IRecurrentCheckList  getRecurrentCheckList(long key)
	throws Exception;
	
	IRecurrentCheckListItem[]  getRecurrentCheckListItem(long key)
	throws Exception;
	
	IRecurrentCheckListSubItem[] getRecurrentCheckListSubItem(long key)
	throws Exception;

	ISystemBankBranch updateSystemBankBranch(String entityName,
			ISystemBankBranch item) throws SystemBankBranchException;

	ISystemBankBranch deleteSystemBankBranch(String entityName,
			ISystemBankBranch item);

	ISystemBankBranch load(String entityName, long id)
			throws SystemBankBranchException;
	
	ISystemBankBranch getSystemBankBranch(String branchCode)throws SystemBankBranchException;

	SearchResult getAllSystemBankBranch() throws SystemBankBranchException;
	
	SearchResult getAllSystemBankBranchForHUB() throws SystemBankBranchException;
	
	public SearchResult getSystemBranchList(String branchCode,String branchName,String state,String city) throws SystemBankException ;

	ISystemBankBranch createSystemBankBranch(String entityName,
			ISystemBankBranch systemBankBranch)
			throws SystemBankBranchException;

	boolean isUniqueCode(String coloumn ,String branchCode) throws SystemBankBranchException;

	IFileMapperId insertSystemBankBranch(String entityName,
			IFileMapperId fileId, ISystemBankBranchTrxValue trxValue)
			throws SystemBankBranchException;

	IFileMapperId createFileId(String entityName, IFileMapperId fileId)
			throws SystemBankBranchException;

	int insertSystemBankBranch(IFileMapperMaster fileMapperMaster,
			String userName, ArrayList result);

	ISystemBankBranch insertActualSystemBankBranch(String sysId)
			throws SystemBankBranchException;

	IFileMapperId getInsertFileList(String entityName, Serializable key)
			throws SystemBankBranchException;

	ISystemBankBranch insertSystemBankBranch(String entityName,
			ISystemBankBranch systemBankBranch)
			throws SystemBankBranchException;
	List getAllStageSystemBankBranch (String searchBy, String login)throws SystemBankBranchException;
	List getFileMasterList(String searchBy)throws SystemBankBranchException;
	boolean isPrevFileUploadPending() throws SystemBankBranchException;

	public String isCodeExisting(String countryCode,String regionCode,String stateCode,String cityCode) throws SystemBankBranchException,TrxParameterException,TransactionException;

	public boolean isHubValid(String linkedHub);
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	SearchResult getSystemBankBranchList(String branchcode) throws SystemBankBranchException;
}
