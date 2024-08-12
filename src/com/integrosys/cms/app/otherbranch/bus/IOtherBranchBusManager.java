package com.integrosys.cms.app.otherbranch.bus;

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
import com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

/** 
 * Defines methods for operation on other bank
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-02-18 17:27:01 +0800 (Fri, 18 Feb 2011) $
 * Tag : $Name$
 */

public interface IOtherBranchBusManager {
	
	public SearchResult getOtherBranchList(String searchType,String searchVal) throws OtherBranchException ;

	public SearchResult getOtherBranch() throws OtherBranchException ;
	
	public boolean isOBCodeUnique(String rmCode);
	
	public IOtherBranch deleteOtherBranch(IOtherBranch OtherBranch) throws OtherBranchException, TrxParameterException, TransactionException;
	
	IOtherBranch getOtherBranchById(long id) throws OtherBranchException,TrxParameterException,TransactionException;
	
	List getAllOtherBranch();
	
	IOtherBranch updateOtherBranch(IOtherBranch item) throws OtherBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	IOtherBranch updateToWorkingCopy(IOtherBranch workingCopy, IOtherBranch imageCopy) throws OtherBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	IOtherBranch createOtherBranch(IOtherBranch systemBank)throws OtherBranchException;
	
	
	//**********************FOR UPLOAD********************************
	boolean isPrevFileUploadPendingBankBranch() throws OtherBranchException;
	
	int insertOtherBankBranch(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList)throws OtherBranchException;
	
	IFileMapperId createFileIdBankBranch(IFileMapperId obFileMapperID)throws OtherBranchException;
	
	IOtherBranch insertOtherBankBranch(IOtherBranch otherBank)throws OtherBranchException;
	
	IFileMapperId insertOtherBankBranch(IFileMapperId fileId, IOtherBankBranchTrxValue idxTrxValue)throws OtherBranchException;
	
	
	IFileMapperId getInsertFileByIdBankBranch(long id) throws OtherBranchException,TrxParameterException,TransactionException;
	
	List getAllStageOtherBankBranch(String searchBy, String login)throws OtherBranchException,TrxParameterException,TransactionException;
	
	List getFileMasterListBankBranch(String searchBy)throws OtherBranchException,TrxParameterException,TransactionException;
	IOtherBranch insertActualOtherBankBranch(String sysId)throws OtherBranchException;
	boolean isUniqueCodeBankBranch(String branchCode) throws OtherBranchException,TrxParameterException,TransactionException;
	
	boolean isUniqueBranchName(String branchName,String bankCode) throws OtherBranchException,TrxParameterException,TransactionException;
	
	boolean isUniqueRbiCode(String rbiCode) throws OtherBranchException,TrxParameterException,TransactionException;
	
	public String isCodeExisting(String countryCode,String regionCode,String stateCode,String cityCode) throws OtherBranchException,TrxParameterException,TransactionException;

	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws OtherBranchException,TrxParameterException,TransactionException;

}
