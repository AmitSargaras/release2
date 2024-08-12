package com.integrosys.cms.app.fccBranch.bus;

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
import com.integrosys.cms.app.fccBranch.trx.IFCCBranchTrxValue;

/****
 * 
 * @author komal.agicha
 *
 */
public interface IFCCBranchBusManager {
	

		
		IFCCBranch getFCCBranchById(long id) throws FCCBranchException,TrxParameterException,TransactionException;
	
		SearchResult getAllFCCBranch()throws FCCBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		SearchResult getAllFilteredFCCBranch(String code,String name)throws FCCBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		SearchResult getAllFCCBranch(String searchBy,String searchText)throws FCCBranchException,TrxParameterException,TransactionException;
		IFCCBranch updateFCCBranch(IFCCBranch item) throws FCCBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		IFCCBranch deleteFCCBranch(IFCCBranch item) throws FCCBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		IFCCBranch updateToWorkingCopy(IFCCBranch workingCopy, IFCCBranch imageCopy) throws FCCBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
		IFCCBranch createFCCBranch(IFCCBranch fccBranch)throws FCCBranchException;
		
		boolean isPrevFileUploadPending() throws FCCBranchException;
		int insertFCCBranch(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList)throws FCCBranchException;
		IFileMapperId insertFCCBranch(IFileMapperId fileId, IFCCBranchTrxValue idxTrxValue)throws FCCBranchException;
		IFileMapperId createFileId(IFileMapperId obFileMapperID)throws FCCBranchException;
		IFileMapperId getInsertFileById(long id) throws FCCBranchException,TrxParameterException,TransactionException;
		List getAllStageFCCBranch(String searchBy, String login)throws FCCBranchException,TrxParameterException,TransactionException;
		List getFileMasterList(String searchBy)throws FCCBranchException,TrxParameterException,TransactionException;
		IFCCBranch insertActualFCCBranch(String sysId)throws FCCBranchException;
		IFCCBranch insertFCCBranch(IFCCBranch fccBranch)throws FCCBranchException;
		
		public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
		
		
		public String fccBranchUniqueCombination(String branchCode, String aliasBranchCode,long id) throws FCCBranchException;
}
