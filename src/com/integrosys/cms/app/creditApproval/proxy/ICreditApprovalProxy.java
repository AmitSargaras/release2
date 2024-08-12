
package com.integrosys.cms.app.creditApproval.proxy;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $govind.sahu $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2011/03/07 10:02:41 $ Tag: $Name: $
 */
public interface ICreditApprovalProxy extends java.io.Serializable {
	
	
	public ICreditApprovalTrxValue getCreditApprovalTrxValue(long aCreditApprovalId) throws CreditApprovalException,TrxParameterException,TransactionException;
	/**
	 * Get price for CreditApproval.
	 * 
	 * @return List
	 * @throws CreditApprovalException on errors retrieving the CreditApproval
	 */
	public List getCreditApprovalList() throws CreditApprovalException;
	
	
	/**
	 * Get CreditApproval for CreditApproval.
	 * 
	 * @return List
	 * @throws CreditApprovalException on errors retrieving the CreditApproval 
	 */
	public boolean getCheckCreditApprovalUniquecode(String appCode) throws CreditApprovalException;
	
	/**
	 * Maker Update CreditApproval
	 * @param anITrxContext access context required for routing, approval
	 * @param anICreditApprovalTrxValue transaction wrapper for the CreditApproval object
     * @param aaICreditApproval CreditApproval object
	 */
	public ICreditApprovalTrxValue makerUpdateCreditApproval(ITrxContext anITrxContext, ICreditApprovalTrxValue anICreditApprovalTrxValue, ICreditApproval aaICreditApproval)throws CreditApprovalException,TrxParameterException,TransactionException;
	
	/**
	 * Maker Update Save Create Credit Approval
	 * @param anITrxContext access context required for routing, approval
	 * @param anICreditApprovalTrxValue transaction wrapper for the CreditApproval object
     * @param aaICreditApproval CreditApproval object
	 */
	public ICreditApprovalTrxValue makerUpdateSaveCreateCreditApproval(ITrxContext anITrxContext, ICreditApprovalTrxValue anICreditApprovalTrxValue, ICreditApproval aaICreditApproval)
	throws CreditApprovalException,TrxParameterException,TransactionException;
	
	/**
	 * Maker Soft Delete CreditApproval
	 * @param anITrxContext access context required for routing, approval
	 * @param anICreditApprovalTrxValue transaction wrapper for the CreditApproval object
     * @param aaICreditApproval CreditApproval object
	 */
	public ICreditApprovalTrxValue makerDeleteCreditApproval(ITrxContext anITrxContext, ICreditApprovalTrxValue anICreditApprovalTrxValue, ICreditApproval aaICreditApproval) throws CreditApprovalException,TrxParameterException,TransactionException;
	
	/**
	 * Maker Edit Rejected CreditApproval
	 * @param anITrxContext access context required for routing, approval
	 * @param anICreditApprovalTrxValue transaction wrapper for the CreditApproval object
     * @param aaICreditApproval CreditApproval object
	 */
	public ICreditApprovalTrxValue makerEditRejectedCreditApproval(ITrxContext anITrxContext, ICreditApprovalTrxValue anICreditApprovalTrxValue, ICreditApproval aaICreditApproval) throws CreditApprovalException,TrxParameterException,TransactionException;


	/**
	 * Get the transaction value containing CreditApproval by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing ICreditApprovalTrxValue
	 */
	public ICreditApprovalTrxValue getCreditApprovalByTrxID(long trxID)throws CreditApprovalException,TrxParameterException,TransactionException;

	/**
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the CreditApproval object
     * @param aCreditApproval for the CreditApproval
     * @return ICreditApprovalTrxValue type ob
     */
	public ICreditApprovalTrxValue makerSubmitCreditApproval(ITrxContext anITrxContext, ICreditApprovalTrxValue aTrxValue,
			ICreditApproval creditApproval) throws CreditApprovalException,TrxParameterException,TransactionException;
	
	/**
	 * This is essentially the same as makerUpdateCreditApproval except that it
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the CreditApproval object
	 */
	public ICreditApprovalTrxValue makerUpdateRejectedCreditApproval(ITrxContext anITrxContext, ICreditApprovalTrxValue aTrxValue)
			throws CreditApprovalException;

	/**
     * Maker Close Rejected Credit Approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the CreditApproval object
	 */
	public ICreditApprovalTrxValue makerCloseRejectedCreditApproval(ITrxContext anITrxContext, ICreditApprovalTrxValue aTrxValue)
			throws CreditApprovalException;
	
	/**
	 * Maker Close Draft Credit Approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the CreditApproval object
	 */
	public ICreditApprovalTrxValue makerCloseDraftCreditApproval(ITrxContext anITrxContext, ICreditApprovalTrxValue aTrxValue) throws CreditApprovalException,TrxParameterException,TransactionException;

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the CreditApproval object
	 */
	public ICreditApprovalTrxValue checkerApproveCreditApproval(ITrxContext anITrxContext, ICreditApprovalTrxValue aTrxValue)
			throws CreditApprovalException;


	/**
	 * Approve Pending Create 
	 * */
	public ICreditApprovalTrxValue checkerApproveCreateCreditApproval(ITrxContext anITrxContext, ICreditApprovalTrxValue anIImageUploadTrxValue) throws CreditApprovalException, TrxParameterException,TransactionException;

	/**
	 *  Approve Pending Update 
	 *  
	 */
	public ICreditApprovalTrxValue checkerApproveUpdateCreditApproval(ITrxContext anITrxContext, ICreditApprovalTrxValue anIImageUploadTrxValue)throws CreditApprovalException, TrxParameterException,TransactionException;

	
	
	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the CreditApproval object
	 */

	public ICreditApprovalTrxValue checkerRejectCreditApproval(ITrxContext anITrxContext, ICreditApprovalTrxValue aTrxValue)throws CreditApprovalException, TrxParameterException,TransactionException;
	
	/**
	 * Maker Update Save Update Credit Approval
	 * @param anITrxContext access context required for routing, approval
	 * @param anICreditApprovalTrxValue transaction wrapper for the CreditApproval object
     * @param aaICreditApproval CreditApproval object
	 */

	public ICreditApprovalTrxValue makerUpdateSaveUpdateCreditApproval(ITrxContext anITrxContext,ICreditApprovalTrxValue anICCCreditApprovalTrxValue,ICreditApproval anICCCreditApproval)throws CreditApprovalException, TrxParameterException,TransactionException;

	/**
	 * Maker Save CreditApproval
	 * @param anITrxContext access context required for routing, approval
	 * @param anICreditApprovalTrxValue transaction wrapper for the CreditApproval object
     * @param aaICreditApproval CreditApproval object
	 */

	public ICreditApprovalTrxValue makerSaveCreditApproval(ITrxContext anITrxContext, ICreditApproval anICCCreditApproval)throws CreditApprovalException,TrxParameterException,TransactionException;

	
	/**
	 * @return List of all Credit Approval according to criteria .
	 */
	 public List getAllActual(String searchTxtApprovalCode,String searchTxtApprovalName)throws CreditApprovalException,TrxParameterException,TransactionException ;

 	public boolean isPrevFileUploadPending() throws CreditApprovalException,TrxParameterException,TransactionException;
 	public ICreditApprovalTrxValue makerInsertMapperCreditApproval(ITrxContext anITrxContext, OBFileMapperID obFileMapperID)throws CreditApprovalException,TrxParameterException,TransactionException;
	public int insertCreditApproval(IFileMapperMaster fileMapperMaster, String userName, ArrayList resultList, long countryId) throws CreditApprovalException,TrxParameterException,TransactionException;
	public ICreditApprovalTrxValue getInsertFileByTrxID(String aTrxID) throws CreditApprovalException,TransactionException,CommandProcessingException;
	public List getAllStage(String searchBy, String login) throws CreditApprovalException,TrxParameterException,TransactionException;
	public ICreditApprovalTrxValue checkerApproveInsertCreditApproval(ITrxContext anITrxContext, ICreditApprovalTrxValue anICreditApprovalTrxValue) throws CreditApprovalException,TrxParameterException,TransactionException;
	public List getFileMasterList(String searchBy) throws CreditApprovalException,TrxParameterException,TransactionException;
	public ICreditApproval insertActualCreditApproval(String sysId) throws CreditApprovalException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	public ICreditApprovalTrxValue checkerCreateCreditApproval(ITrxContext anITrxContext,ICreditApproval anICCCreditApproval, String refStage)throws CreditApprovalException,TrxParameterException,TransactionException;
	public ICreditApprovalTrxValue checkerRejectInsertCreditApproval(ITrxContext anITrxContext, ICreditApprovalTrxValue anICreditApprovalTrxValue) throws CreditApprovalException,TrxParameterException,TransactionException;
	public ICreditApprovalTrxValue makerInsertCloseRejectedCreditApproval(ITrxContext anITrxContext, ICreditApprovalTrxValue anICreditApprovalTrxValue) throws CreditApprovalException,TrxParameterException,TransactionException;

	public long getCountryIdForCountry(String countryName);
	
	public boolean isRegionCodeVaild(String regionCode,long countryId);
	
	public boolean isCreditApprovalNameUnique(String creditApprovalName);
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException,TrxParameterException,TransactionException;
	
	public List getRegionList(String countryCode);	
	
	public boolean isCreditEmployeeIdUnique(String employeeId);
}
