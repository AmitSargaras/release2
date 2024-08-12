package com.integrosys.cms.app.caseCreationUpdate.bus;

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
import com.integrosys.cms.app.caseCreationUpdate.trx.ICaseCreationTrxValue;

/**
 * @author  Abhijit R. 
 */
public interface ICaseCreationBusManager {
	

		List searchCaseCreation(String login) throws CaseCreationException,TrxParameterException,TransactionException;
		ICaseCreation getCaseCreationById(long id) throws CaseCreationException,TrxParameterException,TransactionException;
		SearchResult getAllCaseCreation()throws CaseCreationException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		SearchResult getAllCaseCreation(long id)throws CaseCreationException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		SearchResult getAllCaseCreationBranchMenu(String branchCode)throws CaseCreationException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		SearchResult getAllCaseCreation(String searchBy,String searchText)throws CaseCreationException,TrxParameterException,TransactionException;
		ICaseCreation updateCaseCreation(ICaseCreation item) throws CaseCreationException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		ICaseCreation deleteCaseCreation(ICaseCreation item) throws CaseCreationException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		ICaseCreation updateToWorkingCopy(ICaseCreation workingCopy, ICaseCreation imageCopy) throws CaseCreationException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		ICaseCreation createCaseCreation(ICaseCreation caseCreationUpdate)throws CaseCreationException;
		
		boolean isPrevFileUploadPending() throws CaseCreationException;
		int insertCaseCreation(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList)throws CaseCreationException;
		IFileMapperId insertCaseCreation(IFileMapperId fileId, ICaseCreationTrxValue idxTrxValue)throws CaseCreationException;
		IFileMapperId createFileId(IFileMapperId obFileMapperID)throws CaseCreationException;
		IFileMapperId getInsertFileById(long id) throws CaseCreationException,TrxParameterException,TransactionException;
		List getAllStageCaseCreation(String searchBy, String login)throws CaseCreationException,TrxParameterException,TransactionException;
		List getFileMasterList(String searchBy)throws CaseCreationException,TrxParameterException,TransactionException;
		ICaseCreation insertActualCaseCreation(String sysId)throws CaseCreationException;
		ICaseCreation insertCaseCreation(ICaseCreation caseCreationUpdate)throws CaseCreationException;
		public List getCaseCreationByBranchCode(String branchCode) ;
		public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
}
