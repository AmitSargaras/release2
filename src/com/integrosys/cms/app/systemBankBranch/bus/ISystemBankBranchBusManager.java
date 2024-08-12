package com.integrosys.cms.app.systemBankBranch.bus;

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
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue;
/**
 * @author  Abhijit R. 
 */
public interface ISystemBankBranchBusManager {
	

		SearchResult searchBranch(String login) throws SystemBankBranchException,TrxParameterException,TransactionException;
		boolean isUniqueCode(String coloumn ,String branchCode) throws SystemBankBranchException,TrxParameterException,TransactionException;
		ISystemBankBranch getSystemBankBranchById(long id) throws SystemBankBranchException,TrxParameterException,TransactionException;
		SearchResult getAllSystemBankBranchForHUB()throws SystemBankBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		SearchResult getAllSystemBankBranch()throws SystemBankBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		
		public SearchResult getSystemBranchList(String branchCode,String branchName,String state,String city) throws SystemBankException;
		
		List getAllHUBBranchId()throws SystemBankBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		List getAllHUBBranchValue()throws SystemBankBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		SearchResult getAllSystemBankBranch(String searchBy,String searchText)throws SystemBankBranchException,TrxParameterException,TransactionException;
		List getAllStageSystemBankBranch(String searchBy, String login)throws SystemBankBranchException,TrxParameterException,TransactionException;
		ISystemBankBranch updateSystemBankBranch(ISystemBankBranch item) throws SystemBankBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		ISystemBankBranch deleteSystemBankBranch(ISystemBankBranch item) throws SystemBankBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		ISystemBankBranch updateToWorkingCopy(ISystemBankBranch workingCopy, ISystemBankBranch imageCopy) throws SystemBankBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		ISystemBankBranch createSystemBankBranch(ISystemBankBranch systemBankBranch)throws SystemBankBranchException;
		IFileMapperId insertSystemBankBranch(IFileMapperId fileId, ISystemBankBranchTrxValue idxTrxValue)throws SystemBankBranchException;
		IFileMapperId createFileId(IFileMapperId obFileMapperID)throws SystemBankBranchException;
		int insertSystemBankBranch(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList)throws SystemBankBranchException;
		List getFileMasterList(String searchBy)throws SystemBankBranchException,TrxParameterException,TransactionException;
		IFileMapperId getInsertFileById(long id) throws SystemBankBranchException,TrxParameterException,TransactionException;
		ISystemBankBranch insertActualSystemBankBranch(String sysId)throws SystemBankBranchException;
		ISystemBankBranch insertSystemBankBranch(ISystemBankBranch systemBankBranch)throws SystemBankBranchException;
		boolean isPrevFileUploadPending() throws SystemBankBranchException;

		public boolean isHubValid(String linkedHub);
		
		public String isCodeExisting(String countryCode,String regionCode,String stateCode,String cityCode) throws SystemBankBranchException,TrxParameterException,TransactionException;
		
		public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
}
