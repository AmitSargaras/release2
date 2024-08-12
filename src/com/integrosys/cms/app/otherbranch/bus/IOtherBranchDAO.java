package com.integrosys.cms.app.otherbranch.bus;

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
import com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

/** 
 * Defines methods for operation on other bank
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-02-18 17:27:01 +0800 (Fri, 18 Feb 2011) $
 * Tag : $Name$
 */

public interface IOtherBranchDAO {
	
	public final static String ACTUAL_ENTITY_NAME = "actualOtherBranch";
	
	public final static String STAGING_ENTITY_NAME = "stagingOtherBranch";
	
	  static final String STAGE_FILE_MAPPER_ID = "stageFileMapperId";
		
		static final String ACTUAL_STAGE_FILE_MAPPER_ID = "actualFileMapperId";
		
		static final String CMS_FILE_MAPPER_ID ="fileMapper";
	
	public SearchResult getOtherBranch() throws OtherBranchException;
	
	public boolean isOBCodeUnique(String rmCode);
	
	public SearchResult getOtherBranchList(String searchType,String searchVal) throws OtherBranchException;
	
	public SearchResult getOtherBranchFromCode(String searchVal)throws OtherBranchException;
	
	public IOtherBranch getOtherBranchById(long id) throws OtherBranchException ;
	 
	public IOtherBranch updateOtherBranch(IOtherBranch OtherBranch) throws OtherBranchException;
	
	public IOtherBranch deleteOtherBranch(IOtherBranch OtherBranch) throws OtherBranchException;
	
	public IOtherBranch createOtherBranch(IOtherBranch OtherBranch) throws OtherBranchException;
	
	IOtherBranch getOtherBranch(String entityName, Serializable key)
		throws OtherBranchException;

	IOtherBranch updateOtherBranch(String entityName, IOtherBranch item)
		throws OtherBranchException;

	IOtherBranch createOtherBranch(String entityName, IOtherBranch systemBank)
		throws OtherBranchException;
	
	
//**********************UPLOAD********************************
	
	int insertOtherBankBranch(IFileMapperMaster fileMapperMaster, String userName, ArrayList result);

	IFileMapperId createFileIdBankBranch(String entityName, IFileMapperId fileId) throws OtherBranchException;
	
	IOtherBranch insertOtherBankBranch(String entityName, IOtherBranch otherBank)	throws OtherBranchException;
	
	IFileMapperId insertOtherBankBranch(String entityName, IFileMapperId fileId, IOtherBankBranchTrxValue trxValue)	throws OtherBranchException;

	IFileMapperId getInsertFileListBankBranch(String entityName, Serializable key)throws OtherBranchException;
	
	public List getAllStageOtherBankBranch(String searchBy, String login)throws OtherBranchException,TrxParameterException,TransactionException;
	public List getFileMasterListBankBranch(String searchBy);
	
	IOtherBranch insertActualOtherBankBranch(String sysId)	throws OtherBranchException;
	
	boolean isUniqueCodeBankBranch(String branchCode) throws OtherBranchException;
	
	boolean isUniqueBranchName(String branchName,String bankCode) throws OtherBranchException;
	
	boolean isUniqueRbiCode(String rbiCode) throws OtherBranchException;
	
	public boolean isPrevFileUploadPendingBankBranch();
	
	public IOtherBranch getOtherBranchListForPDC(String bankCode);
	
	public String isCodeExisting(String countryCode,String regionCode,String stateCode,String cityCode) throws OtherBranchException,TrxParameterException,TransactionException;
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws OtherBranchException,TrxParameterException,TransactionException;	
}
