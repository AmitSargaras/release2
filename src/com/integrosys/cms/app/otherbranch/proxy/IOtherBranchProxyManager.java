package com.integrosys.cms.app.otherbranch.proxy;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.otherbranch.bus.OtherBranchException;
import com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

/**
 * This interface defines the methods that will be available to the
 * operating on a other bank branch
 * 
 * @author $Author: Dattatray Thorat
 * @version $Revision: 1.0 $
 * @since $Date: 2011/02/18 10:03:55 
 */


public interface IOtherBranchProxyManager{

	
	public SearchResult getOtherBranchList(String searchType,String searchVal) throws OtherBranchException ;

	public SearchResult getOtherBranch() throws OtherBranchException ;
	
	public boolean isOBCodeUnique(String rmCode);
	
	public IOtherBranch getOtherBranchById(long id) throws OtherBranchException,TrxParameterException,TransactionException ;
	 
	public IOtherBranch updateOtherBranch(IOtherBranch OtherBranch) throws OtherBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public IOtherBranch deleteOtherBranch(IOtherBranch OtherBranch) throws OtherBranchException, TrxParameterException, TransactionException;
	
	public IOtherBranch createOtherBranch(IOtherBranch OtherBranch) throws OtherBranchException;
	
	public IOtherBankBranchTrxValue makerUpdateOtherBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anICCOtherBranchTrxValue, IOtherBranch anICCOtherBranch)
	throws OtherBranchException,TrxParameterException,TransactionException;

	public IOtherBankBranchTrxValue makerEditRejectedOtherBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anIOtherBankBranchTrxValue, IOtherBranch anOtherBranch) 
		throws OtherBranchException,TrxParameterException,TransactionException;
	
	public IOtherBankBranchTrxValue getOtherBranchTrxValue(long aOtherBranchId) throws OtherBranchException,TrxParameterException,TransactionException;
	
	public IOtherBankBranchTrxValue makerDeleteOtherBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anICCOtherBranchTrxValue, IOtherBranch anICCOtherBranch)
		throws OtherBranchException,TrxParameterException,TransactionException;
	
	public IOtherBankBranchTrxValue getOtherBranchByTrxID(String aTrxID) throws OtherBranchException,TransactionException,CommandProcessingException;
	
	public IOtherBankBranchTrxValue checkerApproveOtherBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anIOtherBankBranchTrxValue) throws OtherBranchException,TrxParameterException,TransactionException;
	
	public IOtherBankBranchTrxValue checkerRejectOtherBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anIOtherBankBranchTrxValue) throws OtherBranchException,TrxParameterException,TransactionException;
	
	public IOtherBankBranchTrxValue makerCreateOtherBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anICCOtherBranchTrxValue, IOtherBranch anICCOtherBranch)
		throws OtherBranchException,TrxParameterException,TransactionException;
	
	public IOtherBankBranchTrxValue makerCloseRejectedOtherBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anISystemBankTrxValue) throws OtherBranchException,TrxParameterException,TransactionException;
	
	public IOtherBankBranchTrxValue makerCloseDraftOtherBankBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anIOtherBankBranchTrxValue) throws OtherBranchException,TrxParameterException,TransactionException;
	
	public IOtherBankBranchTrxValue makerUpdateCreateOtherBankBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anICCInsuranceCoverageTrxValue, IOtherBranch anICCIOtherBranch)
		throws OtherBranchException,TrxParameterException,TransactionException;

	public IOtherBankBranchTrxValue makerUpdateSaveOtherBankBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anICCIOtherBranchTrxValue, IOtherBranch anICCIOtherBranch)
		throws OtherBranchException,TrxParameterException,TransactionException;
	
	public IOtherBankBranchTrxValue makerSaveOtherBankBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anIOtherBankBranchTrxValue, IOtherBranch anICCIOtherBranch)
		throws OtherBranchException,TrxParameterException,TransactionException;
	
	
//**********************FOR UPLOAD********************************

	
	public boolean isPrevFileUploadPendingBankBranch() throws OtherBankException,TrxParameterException,TransactionException;
    
	public IOtherBankBranchTrxValue makerInsertMapperOtherBankBranch(ITrxContext anITrxContext, OBFileMapperID obFileMapperID)throws OtherBankException,TrxParameterException,TransactionException;
	
	public int insertOtherBankBranch(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList) throws OtherBankException,TrxParameterException,TransactionException;
   
	public IOtherBankBranchTrxValue getInsertFileByTrxIDBankBranch(String aTrxID) throws OtherBankException,TransactionException,CommandProcessingException;

	public List getAllStageBankBranch(String searchBy, String login) throws OtherBankException,TrxParameterException,TransactionException;

	public IOtherBankBranchTrxValue checkerApproveInsertOtherBankBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anIOtherBankTrxValue) throws OtherBankException,TrxParameterException,TransactionException;
	public List getFileMasterListBankBranch(String searchBy) throws OtherBankException,TrxParameterException,TransactionException;
	public IOtherBranch insertActualOtherBankBranch(String sysId) throws OtherBankException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public IOtherBankBranchTrxValue checkerCreateOtherBankBranch(ITrxContext anITrxContext,IOtherBranch anICCOtherBank, String refStage)throws OtherBankException,TrxParameterException,TransactionException;
	public IOtherBankBranchTrxValue checkerRejectInsertOtherBankBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anIOtherBankTrxValue) throws OtherBankException,TrxParameterException,TransactionException;

	public IOtherBankBranchTrxValue makerInsertCloseRejectedOtherBankBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anIOtherBankTrxValue) throws OtherBankException,TrxParameterException,TransactionException;
	
	public boolean isUniqueCodeBankBranch(String branchCode) throws OtherBankException,TrxParameterException,TransactionException;
	
	public boolean isUniqueBranchName(String branchName,String bankCode) throws OtherBankException,TrxParameterException,TransactionException;
	
	public boolean isUniqueRbiCode(String rbiCode) throws OtherBankException,TrxParameterException,TransactionException;

	public String isCodeExisting(String countryCode,String regionCode,String stateCode,String cityCode) throws OtherBranchException,TrxParameterException,TransactionException;
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws OtherBranchException,TrxParameterException,TransactionException;
}
