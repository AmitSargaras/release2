package com.integrosys.cms.app.caseBranch.bus;

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
import com.integrosys.cms.app.caseBranch.trx.ICaseBranchTrxValue;

/**
 * @author  Abhijit R. 
 */
public interface ICaseBranchBusManager {
	

		List searchCaseBranch(String login) throws CaseBranchException,TrxParameterException,TransactionException;
		ICaseBranch getCaseBranchById(long id) throws CaseBranchException,TrxParameterException,TransactionException;
	
		SearchResult getAllCaseBranch()throws CaseBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		SearchResult getAllFilteredCaseBranch(String code,String name)throws CaseBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		SearchResult getAllCaseBranch(String searchBy,String searchText)throws CaseBranchException,TrxParameterException,TransactionException;
		ICaseBranch updateCaseBranch(ICaseBranch item) throws CaseBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		ICaseBranch deleteCaseBranch(ICaseBranch item) throws CaseBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		ICaseBranch updateToWorkingCopy(ICaseBranch workingCopy, ICaseBranch imageCopy) throws CaseBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		ICaseBranch createCaseBranch(ICaseBranch caseBranch)throws CaseBranchException;
		
		boolean isPrevFileUploadPending() throws CaseBranchException;
		int insertCaseBranch(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList)throws CaseBranchException;
		IFileMapperId insertCaseBranch(IFileMapperId fileId, ICaseBranchTrxValue idxTrxValue)throws CaseBranchException;
		IFileMapperId createFileId(IFileMapperId obFileMapperID)throws CaseBranchException;
		IFileMapperId getInsertFileById(long id) throws CaseBranchException,TrxParameterException,TransactionException;
		List getAllStageCaseBranch(String searchBy, String login)throws CaseBranchException,TrxParameterException,TransactionException;
		List getFileMasterList(String searchBy)throws CaseBranchException,TrxParameterException,TransactionException;
		ICaseBranch insertActualCaseBranch(String sysId)throws CaseBranchException;
		ICaseBranch insertCaseBranch(ICaseBranch caseBranch)throws CaseBranchException;
		
		public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
}
