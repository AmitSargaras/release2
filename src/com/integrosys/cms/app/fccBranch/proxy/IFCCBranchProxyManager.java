package com.integrosys.cms.app.fccBranch.proxy;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.fccBranch.bus.FCCBranchException;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;
import com.integrosys.cms.app.fccBranch.trx.IFCCBranchTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This interface defines the list of attributes that will be available to the
 * generation of a diary item
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/06/29 10:03:55 $ Tag: $Name: $
 */
public interface IFCCBranchProxyManager {

	
	public SearchResult getAllActualFCCBranch() throws FCCBranchException,TrxParameterException,TransactionException;
	public SearchResult getAllFilteredActualFCCBranch(String code,String name) throws FCCBranchException,TrxParameterException,TransactionException;
	public SearchResult getAllActual(String searchBy,String searchText) throws FCCBranchException,TrxParameterException,TransactionException;
	public IFCCBranch deleteFCCBranch(IFCCBranch fccBranch) throws FCCBranchException,TrxParameterException,TransactionException;
	public IFCCBranchTrxValue makerCloseRejectedFCCBranch(ITrxContext anITrxContext, IFCCBranchTrxValue anIFCCBranchTrxValue) throws FCCBranchException,TrxParameterException,TransactionException;
	public IFCCBranchTrxValue makerCloseDraftFCCBranch(ITrxContext anITrxContext, IFCCBranchTrxValue anIFCCBranchTrxValue) throws FCCBranchException,TrxParameterException,TransactionException;
	public IFCCBranch getFCCBranchById(long id) throws FCCBranchException,TrxParameterException,TransactionException ;
	public IFCCBranch updateFCCBranch(IFCCBranch fccBranch) throws FCCBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public IFCCBranchTrxValue makerDeleteFCCBranch(ITrxContext anITrxContext, IFCCBranchTrxValue anICCFCCBranchTrxValue, IFCCBranch anICCFCCBranch)throws FCCBranchException,TrxParameterException,TransactionException;
	public IFCCBranchTrxValue makerUpdateFCCBranch(ITrxContext anITrxContext, IFCCBranchTrxValue anICCFCCBranchTrxValue, IFCCBranch anICCFCCBranch)
	throws FCCBranchException,TrxParameterException,TransactionException;
	public IFCCBranchTrxValue makerUpdateSaveUpdateFCCBranch(ITrxContext anITrxContext, IFCCBranchTrxValue anICCFCCBranchTrxValue, IFCCBranch anICCFCCBranch)
	throws FCCBranchException,TrxParameterException,TransactionException;
	public IFCCBranchTrxValue makerUpdateSaveCreateFCCBranch(ITrxContext anITrxContext, IFCCBranchTrxValue anICCFCCBranchTrxValue, IFCCBranch anICCFCCBranch)
	throws FCCBranchException,TrxParameterException,TransactionException;
	
	public IFCCBranchTrxValue makerEditRejectedFCCBranch(ITrxContext anITrxContext, IFCCBranchTrxValue anIFCCBranchTrxValue, IFCCBranch anFCCBranch) throws FCCBranchException,TrxParameterException,TransactionException;
	public IFCCBranchTrxValue getFCCBranchTrxValue(long aFCCBranchId) throws FCCBranchException,TrxParameterException,TransactionException;
	public IFCCBranchTrxValue getFCCBranchByTrxID(String aTrxID) throws FCCBranchException,TransactionException,CommandProcessingException;
	public IFCCBranchTrxValue checkerApproveFCCBranch(ITrxContext anITrxContext, IFCCBranchTrxValue anIFCCBranchTrxValue) throws FCCBranchException,TrxParameterException,TransactionException;
	public IFCCBranchTrxValue checkerRejectFCCBranch(ITrxContext anITrxContext, IFCCBranchTrxValue anIFCCBranchTrxValue) throws FCCBranchException,TrxParameterException,TransactionException;
	public IFCCBranchTrxValue makerCreateFCCBranch(ITrxContext anITrxContext, IFCCBranch anICCFCCBranch)throws FCCBranchException,TrxParameterException,TransactionException;
	public IFCCBranchTrxValue makerSaveFCCBranch(ITrxContext anITrxContext, IFCCBranch anICCFCCBranch)throws FCCBranchException,TrxParameterException,TransactionException;
	
	public boolean isPrevFileUploadPending() throws FCCBranchException,TrxParameterException,TransactionException;
	public IFCCBranchTrxValue makerInsertMapperFCCBranch(ITrxContext anITrxContext, OBFileMapperID obFileMapperID)throws FCCBranchException,TrxParameterException,TransactionException;
	public int insertFCCBranch(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList) throws FCCBranchException,TrxParameterException,TransactionException;
	public IFCCBranchTrxValue getInsertFileByTrxID(String aTrxID) throws FCCBranchException,TransactionException,CommandProcessingException;
	public List getAllStage(String searchBy, String login) throws FCCBranchException,TrxParameterException,TransactionException;
	public IFCCBranchTrxValue checkerApproveInsertFCCBranch(ITrxContext anITrxContext, IFCCBranchTrxValue anIFCCBranchTrxValue) throws FCCBranchException,TrxParameterException,TransactionException;
	public List getFileMasterList(String searchBy) throws FCCBranchException,TrxParameterException,TransactionException;
	public IFCCBranch insertActualFCCBranch(String sysId) throws FCCBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public IFCCBranchTrxValue checkerCreateFCCBranch(ITrxContext anITrxContext,IFCCBranch anICCFCCBranch, String refStage)throws FCCBranchException,TrxParameterException,TransactionException;
	public IFCCBranchTrxValue checkerRejectInsertFCCBranch(ITrxContext anITrxContext, IFCCBranchTrxValue anIFCCBranchTrxValue) throws FCCBranchException,TrxParameterException,TransactionException;
	public IFCCBranchTrxValue makerInsertCloseRejectedFCCBranch(ITrxContext anITrxContext, IFCCBranchTrxValue anIFCCBranchTrxValue) throws FCCBranchException,TrxParameterException,TransactionException;
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public String fccBranchUniqueCombination(String branchCode,String aliasBranchCode,long id) throws FCCBranchException;
}
