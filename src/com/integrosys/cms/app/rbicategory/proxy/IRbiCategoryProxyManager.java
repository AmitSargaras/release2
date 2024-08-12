package com.integrosys.cms.app.rbicategory.proxy;

import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.rbicategory.bus.IRbiCategory;
import com.integrosys.cms.app.rbicategory.bus.OBRbiCategory;
import com.integrosys.cms.app.rbicategory.bus.RbiCategoryException;
import com.integrosys.cms.app.rbicategory.trx.IRbiCategoryTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This interface defines the list of attributes that will be available to the
 * generation of a diary item
 * 
 * @author $Govind:Sahu $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2011/05/29 10:03:55 $ Tag: $Name: $
 */
public interface IRbiCategoryProxyManager {
	
	public List searchRbiCategory(String srAlph)throws RbiCategoryException,TrxParameterException,TransactionException ;
	public List getAllRbiCategoryList() throws RbiCategoryException,TrxParameterException,TransactionException;
	public List getRbiIndCodeByNameList(String indName) throws RbiCategoryException,TrxParameterException,TransactionException;
	public IRbiCategory deleteRbiCategory(IRbiCategory rbiCategory) throws RbiCategoryException,TrxParameterException,TransactionException;
	public boolean getCheckIndustryName(IRbiCategory rbiCategory) throws RbiCategoryException,TrxParameterException,TransactionException;
	
	
	public IRbiCategoryTrxValue makerCloseRejectedRbiCategory(ITrxContext anITrxContext, IRbiCategoryTrxValue anIRbiCategoryTrxValue) throws RbiCategoryException,TrxParameterException,TransactionException;
	public IRbiCategoryTrxValue makerCloseDraftRbiCategory(ITrxContext anITrxContext, IRbiCategoryTrxValue anIRbiCategoryTrxValue) throws RbiCategoryException,TrxParameterException,TransactionException;
	public IRbiCategory getRbiCategoryById(long id) throws RbiCategoryException,TrxParameterException,TransactionException ;
	public IRbiCategory updateRbiCategory(IRbiCategory rbiCategory) throws RbiCategoryException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public IRbiCategoryTrxValue makerDeleteRbiCategory(ITrxContext anITrxContext, IRbiCategoryTrxValue anICCRbiCategoryTrxValue, IRbiCategory anICCRbiCategory)throws RbiCategoryException,TrxParameterException,TransactionException;
	public IRbiCategoryTrxValue makerUpdateRbiCategory(ITrxContext anITrxContext, IRbiCategoryTrxValue anICCRbiCategoryTrxValue, IRbiCategory anICCRbiCategory)
	throws RbiCategoryException,TrxParameterException,TransactionException;
	public IRbiCategoryTrxValue makerUpdateSaveUpdateRbiCategory(ITrxContext anITrxContext, IRbiCategoryTrxValue anICCRbiCategoryTrxValue, IRbiCategory anICCRbiCategory)
	throws RbiCategoryException,TrxParameterException,TransactionException;
	public IRbiCategoryTrxValue makerUpdateSaveCreateRbiCategory(ITrxContext anITrxContext, IRbiCategoryTrxValue anICCRbiCategoryTrxValue, IRbiCategory anICCRbiCategory)
	throws RbiCategoryException,TrxParameterException,TransactionException;
	
	public IRbiCategoryTrxValue makerEditRejectedRbiCategory(ITrxContext anITrxContext, IRbiCategoryTrxValue anIRbiCategoryTrxValue, IRbiCategory anRbiCategory) throws RbiCategoryException,TrxParameterException,TransactionException;
	public IRbiCategoryTrxValue getRbiCategoryTrxValue(long aRbiCategoryId) throws RbiCategoryException,TrxParameterException,TransactionException;
	public IRbiCategoryTrxValue getRbiCategoryByTrxID(String aTrxID) throws RbiCategoryException,TransactionException,CommandProcessingException;
	public IRbiCategoryTrxValue checkerApproveRbiCategory(ITrxContext anITrxContext, IRbiCategoryTrxValue anIRbiCategoryTrxValue) throws RbiCategoryException,TrxParameterException,TransactionException;
	public IRbiCategoryTrxValue checkerRejectRbiCategory(ITrxContext anITrxContext, IRbiCategoryTrxValue anIRbiCategoryTrxValue) throws RbiCategoryException,TrxParameterException,TransactionException;
	public IRbiCategoryTrxValue makerCreateRbiCategory(ITrxContext anITrxContext, IRbiCategory anICCRbiCategory)throws RbiCategoryException,TrxParameterException,TransactionException;
	public IRbiCategoryTrxValue makerSaveRbiCategory(ITrxContext anITrxContext, IRbiCategory anICCRbiCategory)throws RbiCategoryException,TrxParameterException,TransactionException;
	public boolean isIndustryNameApprove(String industryNameId)throws RbiCategoryException,TrxParameterException,TransactionException;
	public List isRbiCodeCategoryApprove(OBRbiCategory stgObRbiCategory, boolean isEdit, OBRbiCategory actObRbiCategory)throws RbiCategoryException;
}
