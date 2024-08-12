package com.integrosys.cms.app.caseBranch.proxy;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.caseBranch.bus.CaseBranchException;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.caseBranch.bus.ICaseBranch;
import com.integrosys.cms.app.caseBranch.trx.ICaseBranchTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This interface defines the list of attributes that will be available to the
 * generation of a diary item
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/06/29 10:03:55 $ Tag: $Name: $
 */
public interface ICaseBranchProxyManager {

	public List searchCaseBranch(String login) throws CaseBranchException,TrxParameterException,TransactionException;
	public SearchResult getAllActualCaseBranch() throws CaseBranchException,TrxParameterException,TransactionException;
	public SearchResult getAllFilteredActualCaseBranch(String code,String name) throws CaseBranchException,TrxParameterException,TransactionException;
	public SearchResult getAllActual(String searchBy,String searchText) throws CaseBranchException,TrxParameterException,TransactionException;
	public ICaseBranch deleteCaseBranch(ICaseBranch caseBranch) throws CaseBranchException,TrxParameterException,TransactionException;
	public ICaseBranchTrxValue makerCloseRejectedCaseBranch(ITrxContext anITrxContext, ICaseBranchTrxValue anICaseBranchTrxValue) throws CaseBranchException,TrxParameterException,TransactionException;
	public ICaseBranchTrxValue makerCloseDraftCaseBranch(ITrxContext anITrxContext, ICaseBranchTrxValue anICaseBranchTrxValue) throws CaseBranchException,TrxParameterException,TransactionException;
	public ICaseBranch getCaseBranchById(long id) throws CaseBranchException,TrxParameterException,TransactionException ;
	public ICaseBranch updateCaseBranch(ICaseBranch caseBranch) throws CaseBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public ICaseBranchTrxValue makerDeleteCaseBranch(ITrxContext anITrxContext, ICaseBranchTrxValue anICCCaseBranchTrxValue, ICaseBranch anICCCaseBranch)throws CaseBranchException,TrxParameterException,TransactionException;
	public ICaseBranchTrxValue makerUpdateCaseBranch(ITrxContext anITrxContext, ICaseBranchTrxValue anICCCaseBranchTrxValue, ICaseBranch anICCCaseBranch)
	throws CaseBranchException,TrxParameterException,TransactionException;
	public ICaseBranchTrxValue makerUpdateSaveUpdateCaseBranch(ITrxContext anITrxContext, ICaseBranchTrxValue anICCCaseBranchTrxValue, ICaseBranch anICCCaseBranch)
	throws CaseBranchException,TrxParameterException,TransactionException;
	public ICaseBranchTrxValue makerUpdateSaveCreateCaseBranch(ITrxContext anITrxContext, ICaseBranchTrxValue anICCCaseBranchTrxValue, ICaseBranch anICCCaseBranch)
	throws CaseBranchException,TrxParameterException,TransactionException;
	
	public ICaseBranchTrxValue makerEditRejectedCaseBranch(ITrxContext anITrxContext, ICaseBranchTrxValue anICaseBranchTrxValue, ICaseBranch anCaseBranch) throws CaseBranchException,TrxParameterException,TransactionException;
	public ICaseBranchTrxValue getCaseBranchTrxValue(long aCaseBranchId) throws CaseBranchException,TrxParameterException,TransactionException;
	public ICaseBranchTrxValue getCaseBranchByTrxID(String aTrxID) throws CaseBranchException,TransactionException,CommandProcessingException;
	public ICaseBranchTrxValue checkerApproveCaseBranch(ITrxContext anITrxContext, ICaseBranchTrxValue anICaseBranchTrxValue) throws CaseBranchException,TrxParameterException,TransactionException;
	public ICaseBranchTrxValue checkerRejectCaseBranch(ITrxContext anITrxContext, ICaseBranchTrxValue anICaseBranchTrxValue) throws CaseBranchException,TrxParameterException,TransactionException;
	public ICaseBranchTrxValue makerCreateCaseBranch(ITrxContext anITrxContext, ICaseBranch anICCCaseBranch)throws CaseBranchException,TrxParameterException,TransactionException;
	public ICaseBranchTrxValue makerSaveCaseBranch(ITrxContext anITrxContext, ICaseBranch anICCCaseBranch)throws CaseBranchException,TrxParameterException,TransactionException;
	
	public boolean isPrevFileUploadPending() throws CaseBranchException,TrxParameterException,TransactionException;
	public ICaseBranchTrxValue makerInsertMapperCaseBranch(ITrxContext anITrxContext, OBFileMapperID obFileMapperID)throws CaseBranchException,TrxParameterException,TransactionException;
	public int insertCaseBranch(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList) throws CaseBranchException,TrxParameterException,TransactionException;
	public ICaseBranchTrxValue getInsertFileByTrxID(String aTrxID) throws CaseBranchException,TransactionException,CommandProcessingException;
	public List getAllStage(String searchBy, String login) throws CaseBranchException,TrxParameterException,TransactionException;
	public ICaseBranchTrxValue checkerApproveInsertCaseBranch(ITrxContext anITrxContext, ICaseBranchTrxValue anICaseBranchTrxValue) throws CaseBranchException,TrxParameterException,TransactionException;
	public List getFileMasterList(String searchBy) throws CaseBranchException,TrxParameterException,TransactionException;
	public ICaseBranch insertActualCaseBranch(String sysId) throws CaseBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public ICaseBranchTrxValue checkerCreateCaseBranch(ITrxContext anITrxContext,ICaseBranch anICCCaseBranch, String refStage)throws CaseBranchException,TrxParameterException,TransactionException;
	public ICaseBranchTrxValue checkerRejectInsertCaseBranch(ITrxContext anITrxContext, ICaseBranchTrxValue anICaseBranchTrxValue) throws CaseBranchException,TrxParameterException,TransactionException;
	public ICaseBranchTrxValue makerInsertCloseRejectedCaseBranch(ITrxContext anITrxContext, ICaseBranchTrxValue anICaseBranchTrxValue) throws CaseBranchException,TrxParameterException,TransactionException;
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
}
