package com.integrosys.cms.app.otherbank.proxy;

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
import com.integrosys.cms.app.otherbank.bus.OBOtherBank;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyException;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;

/**
 * This interface defines the methods that will be available to the
 * operating on a other bank
 * 
 * @author $Author: Dattatray Thorat
 * @version $Revision: 1.0 $
 * @since $Date: 2011/02/18 10:03:55 
 */

public interface IOtherBankProxyManager{

	
	public SearchResult getOtherBankList(String bankCode,String bankName);
	public List<OBOtherBank> getOtherBankList(String bankCode,String bankName,String branchName,String branchCode) throws OtherBankException;
	
	public List getCountryList();
	
	public List getRegionList(String countryId);
	
	public List getStateList(String regionId);
	
	public List getCityList(String stateId);
	
	public SearchResult getOtherBranchList(String branchCode,String branchName,String state,String city,long id);

	public IOtherBank getOtherBankById(long id) throws OtherBankException,TrxParameterException,TransactionException;
	
	public boolean checkOtherBranchById(long id) throws OtherBankException ;
	
	public IOtherBank createOtherBank(IOtherBank OtherBank) throws OtherBankException,TrxParameterException,TransactionException;
	 
	public IOtherBank updateOtherBank(IOtherBank OtherBank) throws OtherBankException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public IOtherBank deleteOtherBank(IOtherBank OtherBank) throws OtherBankException,TrxParameterException,TransactionException;
	
	public IOtherBankTrxValue makerUpdateOtherBank(ITrxContext anITrxContext, IOtherBankTrxValue anICCOtherBankTrxValue, IOtherBank anICCOtherBank)
		throws OtherBankException,TrxParameterException,TransactionException;
	
	public IOtherBankTrxValue makerEditRejectedOtherBank(ITrxContext anITrxContext, IOtherBankTrxValue anIOtherBankTrxValue, IOtherBank anOtherBank) 
		throws OtherBankException,TrxParameterException,TransactionException;
	
	public IOtherBankTrxValue getOtherBankTrxValue(long aOtherBankId) throws OtherBankException,TrxParameterException,TransactionException;

	public IOtherBankTrxValue makerDeleteOtherBank(ITrxContext anITrxContext, IOtherBankTrxValue anICCOtherBankTrxValue, IOtherBank anICCOtherBank)
		throws OtherBankException,TrxParameterException,TransactionException;
	
	public IOtherBankTrxValue getOtherBankByTrxID(String aTrxID) throws OtherBankException,TransactionException,CommandProcessingException;
	
	public IOtherBankTrxValue checkerApproveOtherBank(ITrxContext anITrxContext, IOtherBankTrxValue anIOtherBankTrxValue) throws OtherBankException,TrxParameterException,TransactionException;
	
	public IOtherBankTrxValue checkerRejectOtherBank(ITrxContext anITrxContext, IOtherBankTrxValue anIOtherBankTrxValue) throws OtherBankException,TrxParameterException,TransactionException;

	public IOtherBankTrxValue makerCreateOtherBank(ITrxContext anITrxContext, IOtherBankTrxValue anICCOtherBankTrxValue, IOtherBank anICCOtherBank)
		throws OtherBankException,TrxParameterException,TransactionException;
	
	public IOtherBankTrxValue makerCloseRejectedOtherBank(ITrxContext anITrxContext, IOtherBankTrxValue anIOtherBankTrxValue) throws OtherBankException,TrxParameterException,TransactionException;
	
	public IOtherBankTrxValue makerCloseDraftOtherBank(ITrxContext anITrxContext, IOtherBankTrxValue anIOtherBankTrxValue) throws OtherBankException,TrxParameterException,TransactionException;
	
	public IOtherBankTrxValue makerUpdateCreateOtherBank(ITrxContext anITrxContext, IOtherBankTrxValue anICCInsuranceCoverageTrxValue, IOtherBank anICCInsuranceCoverage)
		throws OtherBankException,TrxParameterException,TransactionException;

	public IOtherBankTrxValue makerUpdateSaveOtherBank(ITrxContext anITrxContext, IOtherBankTrxValue anICCInsuranceCoverageTrxValue, IOtherBank anICCInsuranceCoverage)
		throws OtherBankException,TrxParameterException,TransactionException;
	
	public IOtherBankTrxValue makerSaveOtherBank(ITrxContext anITrxContext, IOtherBankTrxValue anIOtherBankTrxValue, IOtherBank anICCInsuranceCoverage)
		throws OtherBankException,TrxParameterException,TransactionException;
	
	
//**********************FOR UPLOAD********************************

	
	public boolean isPrevFileUploadPending() throws OtherBankException,TrxParameterException,TransactionException;
    
	public IOtherBankTrxValue makerInsertMapperOtherBank(ITrxContext anITrxContext, OBFileMapperID obFileMapperID)throws OtherBankException,TrxParameterException,TransactionException;
	
	public int insertOtherBank(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList) throws OtherBankException,TrxParameterException,TransactionException;
   
	public IOtherBankTrxValue getInsertFileByTrxID(String aTrxID) throws OtherBankException,TransactionException,CommandProcessingException;

	public List getAllStage(String searchBy, String login) throws OtherBankException,TrxParameterException,TransactionException;

	public IOtherBankTrxValue checkerApproveInsertOtherBank(ITrxContext anITrxContext, IOtherBankTrxValue anIOtherBankTrxValue) throws OtherBankException,TrxParameterException,TransactionException;
	public List getFileMasterList(String searchBy) throws OtherBankException,TrxParameterException,TransactionException;
	public IOtherBank insertActualOtherBank(String sysId) throws OtherBankException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public IOtherBankTrxValue checkerCreateOtherBank(ITrxContext anITrxContext,IOtherBank anICCOtherBank, String refStage)throws OtherBankException,TrxParameterException,TransactionException;
	public IOtherBankTrxValue checkerRejectInsertOtherBank(ITrxContext anITrxContext, IOtherBankTrxValue anIOtherBankTrxValue) throws OtherBankException,TrxParameterException,TransactionException;

	public IOtherBankTrxValue makerInsertCloseRejectedOtherBank(ITrxContext anITrxContext, IOtherBankTrxValue anIOtherBankTrxValue) throws OtherBankException,TrxParameterException,TransactionException;
	
	public boolean isUniqueCode(String branchCode) throws OtherBankException,TrxParameterException,TransactionException;
	
	public boolean isUniqueName(String bankName) throws OtherBankException,TrxParameterException,TransactionException;
	
	
	//******************* Methods defined for Guarantee Security ******************
	
	public String getCityName(String cityId);
	public String getStateName(String stateId);
	public String getRegionName(String regionId);
	public String getCountryName(String countryId);
	
	public String isCodeExisting(String countryCode,String regionCode,String stateCode,String cityCode) throws OtherBankException,TrxParameterException,TransactionException;
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws OtherBankException,TrxParameterException,TransactionException;

	public SearchResult getInsurerNameFromCode(String insurerName)throws OtherBankException,TrxParameterException,TransactionException;
	
	
	public SearchResult getInsurerList()throws OtherBankException,TrxParameterException,TransactionException;
}
