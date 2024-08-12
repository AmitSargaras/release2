package com.integrosys.cms.app.otherbank.bus;

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
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.app.otherbranch.bus.OtherBranchException;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;

/**
 * This Interface defines methods for operation on Other Banks  
 * $Author: Dattatray Thorat
 * jtan $
 * @version $Revision: 1.2 $
 * @since $Date: 2011/02/18 11:32:23 $ Tag: $Name: $
 */

public interface IOtherBankBusManager {
	
	public SearchResult getOtherBankList(String bankCode,String bankName) throws OtherBankException;
	public List<OBOtherBank> getOtherBankList(String bankCode,String bankName,String branchName,String branchCode) throws OtherBankException;
	
	public List getCountryList();
	
	public List getRegionList(String countryId);
	
	public List getStateList(String regionId);
	
	public List getCityList(String stateId);

	public SearchResult getOtherBranchList(String branchCode,String branchName,String state,String city,long id) throws OtherBranchException ;
	
	public boolean checkOtherBranchById(long id) throws OtherBankException;
	 
	public IOtherBank deleteOtherBank(IOtherBank OtherBank) throws OtherBankException,TrxParameterException,TransactionException;
	
	IOtherBank getOtherBankById(long id) throws OtherBankException,TrxParameterException,TransactionException;
	
	List getAllOtherBank();
	
	IOtherBank updateOtherBank(IOtherBank item) throws OtherBankException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	IOtherBank updateToWorkingCopy(IOtherBank workingCopy, IOtherBank imageCopy) throws OtherBankException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	IOtherBank createOtherBank(IOtherBank systemBank)throws OtherBankException;
	
	//**********************FOR UPLOAD********************************
	boolean isPrevFileUploadPending() throws OtherBankException;
	
	int insertOtherBank(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList)throws OtherBankException;
	
	IFileMapperId createFileId(IFileMapperId obFileMapperID)throws OtherBankException;
	
	IOtherBank insertOtherBank(IOtherBank otherBank)throws OtherBankException;
	
	IFileMapperId insertOtherBank(IFileMapperId fileId, IOtherBankTrxValue idxTrxValue)throws OtherBankException;
	
	
	IFileMapperId getInsertFileById(long id) throws OtherBankException,TrxParameterException,TransactionException;
	
	List getAllStageOtherBank(String searchBy, String login)throws OtherBankException,TrxParameterException,TransactionException;
	
	List getFileMasterList(String searchBy)throws OtherBankException,TrxParameterException,TransactionException;
	IOtherBank insertActualOtherBank(String sysId)throws OtherBankException;
	boolean isUniqueCode(String branchCode) throws OtherBankException,TrxParameterException,TransactionException;
	boolean isUniqueName(String branchName) throws OtherBankException,TrxParameterException,TransactionException;	

	
//******************* Methods defined for Guarantee Security ******************
	
	public String getCityName(String cityId);
	public String getStateName(String stateId);
	public String getRegionName(String regionId);
	public String getCountryName(String countryId);

	public String isCodeExisting(String countryCode,String regionCode,String stateCode,String cityCode) throws OtherBankException,TrxParameterException,TransactionException;
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;

	public SearchResult getInsurerList() throws OtherBankException;

	public SearchResult getInsurerNameFromCode(String insurerName) throws OtherBankException;
	
}
