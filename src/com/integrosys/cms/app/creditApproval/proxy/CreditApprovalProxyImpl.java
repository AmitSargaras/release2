
package com.integrosys.cms.app.creditApproval.proxy;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.collateralNewMaster.bus.CollateralNewMasterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.creditApproval.bus.ICreditApprovalBusManager;
import com.integrosys.cms.app.creditApproval.bus.OBCreditApproval;
import com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue;
import com.integrosys.cms.app.creditApproval.trx.OBCreditApprovalTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

public class CreditApprovalProxyImpl implements ICreditApprovalProxy {
	/**
	 * Get feed price for CreditApproval.
	 * 
	 * @throws CreditApprovalException on errors retrieving the CreditApproval 
	 */

	private ICreditApprovalBusManager creditApprovalBusManager;

	private ICreditApprovalBusManager stagingCreditApprovalBusManager;

	private ITrxControllerFactory trxControllerFactory;
	
    private ICreditApprovalBusManager stagingCreditApprovalFileMapperIdBusManager;
	
	private ICreditApprovalBusManager creditApprovalFileMapperIdBusManager;
	
	



	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	/**
	 * @return the creditApprovalBusManager
	 */
	public ICreditApprovalBusManager getCreditApprovalBusManager() {
		return creditApprovalBusManager;
	}

	/**
	 * @param creditApprovalBusManager the creditApprovalBusManager to set
	 */
	public void setCreditApprovalBusManager(
			ICreditApprovalBusManager creditApprovalBusManager) {
		this.creditApprovalBusManager = creditApprovalBusManager;
	}

	/**
	 * @return the stagingCreditApprovalBusManager
	 */
	public ICreditApprovalBusManager getStagingCreditApprovalBusManager() {
		return stagingCreditApprovalBusManager;
	}

	/**
	 * @param stagingCreditApprovalBusManager the stagingCreditApprovalBusManager to set
	 */
	public void setStagingCreditApprovalBusManager(
			ICreditApprovalBusManager stagingCreditApprovalBusManager) {
		this.stagingCreditApprovalBusManager = stagingCreditApprovalBusManager;
	}
	
	/**
	 * Get Credit Approval List
	 * @return Credit Approval List
	 * @throws CreditApprovalException
	 */
	public List getCreditApprovalList() throws CreditApprovalException {
		return getCreditApprovalBusManager().getCreditApprovalList();
	}	
	
	
	
	/**
	 * Gets the CreditApproval boolean

	 * @return The  CreditApproval List
	 * @throws CreditApprovalException
	 */
	public boolean getCheckCreditApprovalUniquecode(String appCode) throws CreditApprovalException {
		return getStagingCreditApprovalBusManager().getCheckCreditApprovalUniquecode(appCode);
	}	
	

	
	
	 /**
	  * Update By MAKER
	  * @return updated Credit Approval Trx value Object
	  * @param Trx object, Credit Approval Trx object,Credit Approval object to be updated
	  * 
	  * The updated Credit Approval object in stored in Staging Table of Credit Approval
	  */
	 
	 public ICreditApprovalTrxValue makerDeleteCreditApproval(ITrxContext anITrxContext, ICreditApprovalTrxValue anICreditApprovalTrxValue, ICreditApproval anICreditApproval) throws CreditApprovalException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new CreditApprovalException("The ITrxContext is null!!!");
	        }
	        if (anICreditApprovalTrxValue == null) {
	            throw new CreditApprovalException("The anICreditApprovalTrxValue to be updated is null !!!");
	        }
	        ICreditApprovalTrxValue trxValue = formulateTrxValue(anITrxContext, anICreditApprovalTrxValue, anICreditApproval);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        
	       param.setAction(ICMSConstant.ACTION_MAKER_DELETE_CREDIT_APPROVAL);
	        return operate(trxValue, param);
	    }
	 

	 /**
	  * Update By MAKER
	  * @return updated Credit Approval Trx value Object
	  * @param Trx object, Credit Approval Trx object,Credit Approval object to be updated
	  * 
	  * The updated Credit Approval object in stored in Staging Table of Credit Approval
	  */
	 
	 public ICreditApprovalTrxValue makerUpdateCreditApproval(ITrxContext anITrxContext, ICreditApprovalTrxValue anICreditApprovalTrxValue, ICreditApproval anICreditApproval) throws CreditApprovalException,TrxParameterException,TransactionException{
	        if (anITrxContext == null) {
	            throw new CreditApprovalException("The ITrxContext is null!!!");
	        }
	        if (anICreditApprovalTrxValue == null) {
	            throw new CreditApprovalException("The anICreditApprovalTrxValue to be updated is null !!!");
	        }
	        ICreditApprovalTrxValue trxValue = formulateTrxValue(anITrxContext, anICreditApprovalTrxValue, anICreditApproval);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        
	       param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_CREDIT_APPROVAL);
	        return operate(trxValue, param);
	    }

		/**
		 * @return Maker Update Draft for create Credit Approval  .
		 */

		public ICreditApprovalTrxValue makerUpdateSaveCreateCreditApproval(
				ITrxContext anITrxContext,
				ICreditApprovalTrxValue anICreditApprovalTrxValue,
				ICreditApproval anICreditApproval)
				throws CreditApprovalException, TrxParameterException,
				TransactionException {
			 if (anITrxContext == null) {
		            throw new CreditApprovalException("The ITrxContext is null!!!");
		        }
		        if (anICreditApprovalTrxValue == null) {
		            throw new CreditApprovalException("The anICreditApprovalTrxValue to be updated is null !!!");
		        }
		        ICreditApprovalTrxValue trxValue = formulateTrxValue(anITrxContext, anICreditApprovalTrxValue, anICreditApproval);
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_CREDIT_APPROVAL);
		        return operate(trxValue, param);
		}
	
	 /**
	  * @return updated Credit Approval Trx value Object
	  * @param Trx object, Credit Approval Trx object,Credit Approval object to be updated
	  * After once rejected by Checker, if maker attempts to update the same record 
	  * its done by this method. 
	  * The updated Credit Approval object in stored in Staging Table of Credit Approval
	  */
	 public ICreditApprovalTrxValue makerEditRejectedCreditApproval(ITrxContext anITrxContext, ICreditApprovalTrxValue anICreditApprovalTrxValue, ICreditApproval anICreditApproval)throws CreditApprovalException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new CreditApprovalException("The ITrxContext is null!!!");
	        }
	        if (anICreditApprovalTrxValue == null) {
	            throw new CreditApprovalException("The anICreditApprovalTrxValue to be updated is null!!!");
	        }
	        if (anICreditApproval == null) {
	            throw new CreditApprovalException("The anICreditApproval to be updated is null !!!");
	        }
	        if(anICreditApprovalTrxValue.getReferenceID()!=null && !anICreditApprovalTrxValue.getReferenceID().equals(""))
			{
			OBCreditApproval oBCreditApproval = new OBCreditApproval();
			oBCreditApproval.setId(Long.parseLong(anICreditApprovalTrxValue.getReferenceID()));
			anICreditApprovalTrxValue.setCreditApproval(oBCreditApproval);
			}
			else
			{
				DefaultLogger.debug(this, "ReferenceID is :" +anICreditApprovalTrxValue.getReferenceID());
			}
	        anICreditApprovalTrxValue = formulateTrxValue(anITrxContext, anICreditApprovalTrxValue, anICreditApproval);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREDIT_APPROVAL);
	        return operate(anICreditApprovalTrxValue, param);
	    }
	
	
	protected ICreditApprovalTrxValue formulateTrxValue(ITrxContext anITrxContext, ICreditApprovalTrxValue aTrxValue) {
		aTrxValue.setTrxContext(anITrxContext);
		aTrxValue.setTransactionType(ICMSConstant.INSTANCE_CREDIT_APPROVAL);

		return aTrxValue;
	}

	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws CreditApprovalException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			if (controller == null) {
				throw new CreditApprovalException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch(TrxParameterException te){
			 te.printStackTrace();
			 throw new CreditApprovalException("ERROR--Cannot update already deleted record");
		 }
		catch (TransactionException e) {
			e.printStackTrace();
			throw new CreditApprovalException("Error in transaction "+e.getMessage());
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new CreditApprovalException("Error in Operate For resut "+ex.getMessage());
		}
	}

	protected ICreditApprovalTrxValue operate(ICreditApprovalTrxValue aTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws CreditApprovalException {
		ICMSTrxResult result = operateForResult(aTrxValue, anOBCMSTrxParameter);
		return (ICreditApprovalTrxValue) result.getTrxValue();
	}
	

	protected ICreditApprovalTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			ICreditApproval aCreditApproval) {

		ICreditApprovalTrxValue trxValue = null;
		if (anICMSTrxValue != null) {
			trxValue = new OBCreditApprovalTrxValue(anICMSTrxValue);
		}
		else {
			trxValue = new OBCreditApprovalTrxValue();
		}
		trxValue = formulateTrxValue(anITrxContext, (ICreditApprovalTrxValue) trxValue);

		trxValue.setStagingCreditApproval(aCreditApproval);

		return trxValue;
	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the CreditApproval object
	 */
	public ICreditApprovalTrxValue checkerApproveCreditApproval(ITrxContext anITrxContext,
			ICreditApprovalTrxValue aTrxValue) throws CreditApprovalException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");
		
		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_CREDIT_APPROVAL);
		return operate(aTrxValue, param);

	}

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the CreditApproval object
	 */

	public ICreditApprovalTrxValue checkerRejectCreditApproval(ITrxContext anITrxContext, ICreditApprovalTrxValue aTrxValue)
			 throws CreditApprovalException, TrxParameterException,TransactionException{
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");
		
		if(aTrxValue.getReferenceID()!=null && !aTrxValue.getReferenceID().equals(""))
		{
		OBCreditApproval oBCreditApproval = new OBCreditApproval();
		oBCreditApproval.setId(Long.parseLong(aTrxValue.getReferenceID()));
		aTrxValue.setCreditApproval(oBCreditApproval);
		}
		else
		{
			DefaultLogger.debug(this, "ReferenceID is :" +aTrxValue.getReferenceID());
		}
		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_CREDIT_APPROVAL);
		return operate(aTrxValue, param);
	}

	/**
	 * Get the transaction value containing CreditApproval by trxID
	 * @param trxID the transaction ID
	 * @return the trx value containing ICreditApprovalTrxValue
	 */
	public ICreditApprovalTrxValue getCreditApprovalByTrxID(long trxID) throws CreditApprovalException,TrxParameterException,TransactionException {
		ICreditApprovalTrxValue trxValue = new OBCreditApprovalTrxValue();
		trxValue.setTransactionID(String.valueOf(trxID));

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_CREDIT_APPROVAL);

		return operate(trxValue, param);
	}

	
	 /**
	  * @return ICreditApprovalTrxValue Value
	  * @param 
	  * This method fetches the Proper trx value
	  * according to the Object passed as parameter.
	  * 
	  */
		public ICreditApprovalTrxValue getCreditApprovalTrxValue(long aCreditApproval) throws CreditApprovalException,TrxParameterException,TransactionException{
       if (aCreditApproval == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
           throw new CreditApprovalException("Invalid Credit approval Id");
       }
		ICreditApprovalTrxValue trxValue = new OBCreditApprovalTrxValue();
       trxValue.setReferenceID(String.valueOf(aCreditApproval));
       trxValue.setTransactionType(ICMSConstant.INSTANCE_CREDIT_APPROVAL);
       trxValue.setFromState(ICMSConstant.ACTION_PENDING_CREATE);
       trxValue.setStatus(ICMSConstant.ACTION_STATUS);
       OBCMSTrxParameter param = new OBCMSTrxParameter();
       param.setAction(ICMSConstant.ACTION_READ_CREDIT_APPROVAL);
       return operate(trxValue, param);
   }
	
	/**
	 * Submit for approval
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the CreditApproval object
     * @param aCreditApproval for the CreditApproval
	 * object
	 * trx value, but the intention is that the caller should not have modified
	 * the trxValue, as the caller does not need to know about staging settings
	 * et al.
	 */
	public ICreditApprovalTrxValue makerSubmitCreditApproval(ITrxContext anITrxContext,
			ICreditApprovalTrxValue aTrxValue, ICreditApproval aCreditApproval) throws CreditApprovalException,TrxParameterException,TransactionException {

		Validate.notNull(aCreditApproval, "'aCreditApproval' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue, aCreditApproval);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_CREDIT_APPROVAL);
		return operate(aTrxValue, param);
	}
	

	 /**
	   * @return Image Upload Trx Value
	   * @param Trx object, Image Upload object,Image Upload  object
	   * 
	   * This method Approves the Object passed by Maker
	   */
	  
	  public ICreditApprovalTrxValue checkerApproveCreateCreditApproval(ITrxContext anITrxContext, ICreditApprovalTrxValue anICreditApprovalTrxValue) throws CreditApprovalException, TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new CreditApprovalException("The ITrxContext is null!!!");
	        }
	        if (anICreditApprovalTrxValue == null) {
	            throw new CreditApprovalException
	                    ("The ICreditApprovalTrxValue to be updated is null!!!");
	        }
	        anICreditApprovalTrxValue = formulateTrxValue(anITrxContext, anICreditApprovalTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_CREDIT_APPROVAL);
	        return operate(anICreditApprovalTrxValue, param);
	    }
	  
		 /**
		   * @return Image Upload Trx Value
		   * @param Trx object, Image Upload object,Image Upload  object
		   * 
		   * This method Approves the Object passed by Maker
		   */
		  
		  public ICreditApprovalTrxValue checkerApproveUpdateCreditApproval(ITrxContext anITrxContext, ICreditApprovalTrxValue anICreditApprovalTrxValue)throws CreditApprovalException, TrxParameterException,TransactionException {
		        if (anITrxContext == null) {
		            throw new CreditApprovalException("The ITrxContext is null!!!");
		        }
		        if (anICreditApprovalTrxValue == null) {
		            throw new CreditApprovalException
		                    ("The ICreditApprovalTrxValue to be updated is null!!!");
		        }
		        anICreditApprovalTrxValue = formulateTrxValue(anITrxContext, anICreditApprovalTrxValue);
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_CREDIT_APPROVAL);
		        return operate(anICreditApprovalTrxValue, param);
		    }

	/**
	 * This is essentially the same as makerUpdateCreditApproval except that it
	 * triggers a different state transition from REJECTED to DRAFT.
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the CreditApprovalFeedGroup object
	 */
	public ICreditApprovalTrxValue makerUpdateRejectedCreditApproval(ITrxContext anITrxContext,
			ICreditApprovalTrxValue aTrxValue) throws CreditApprovalException {

		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_CREDIT_APPROVAL);
		return operate(aTrxValue, param);
	}
	
	 /**
	 * @return Maker Close draft Credit Approval  .
	 */
	
	public ICreditApprovalTrxValue makerCloseDraftCreditApproval(
			ITrxContext anITrxContext,
			ICreditApprovalTrxValue aTrxValue)
			throws CreditApprovalException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
            throw new CreditApprovalException("The ITrxContext is null!!!");
        }
        if (aTrxValue == null) {
            throw new CreditApprovalException("The ICreditApprovalTrxValue to be updated is null!!!");
        }
        if(aTrxValue.getReferenceID()!=null && !aTrxValue.getReferenceID().equals(""))
		{
		OBCreditApproval oBCreditApproval = new OBCreditApproval();
		oBCreditApproval.setId(Long.parseLong(aTrxValue.getReferenceID()));
		aTrxValue.setCreditApproval(oBCreditApproval);
		}
		else
		{
			DefaultLogger.debug(this, "ReferenceID is :" +aTrxValue.getReferenceID());
		}
        aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_CREDIT_APPROVAL);
        return operate(aTrxValue, param);
	}
	
	

	/**
	 * @param anITrxContext access context required for routing, approval
	 * @param aTrxValue transaction wrapper for the CreditApproval object
	 */
	public ICreditApprovalTrxValue makerCloseRejectedCreditApproval(ITrxContext anITrxContext,
			ICreditApprovalTrxValue aTrxValue) throws CreditApprovalException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");
		 if(aTrxValue.getReferenceID()!=null && !aTrxValue.getReferenceID().equals(""))
			{
			OBCreditApproval oBCreditApproval = new OBCreditApproval();
			oBCreditApproval.setId(Long.parseLong(aTrxValue.getReferenceID()));
			aTrxValue.setCreditApproval(oBCreditApproval);
			}
			else
			{
				DefaultLogger.debug(this, "ReferenceID is :" +aTrxValue.getReferenceID());
			}
		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_CREDIT_APPROVAL);
		return operate(aTrxValue, param);
	}
	
	 /**
	 * @return Maker Update Credit Approval  .
	 */

	public ICreditApprovalTrxValue makerUpdateSaveUpdateCreditApproval(
			ITrxContext anITrxContext,
			ICreditApprovalTrxValue anICCCreditApprovalTrxValue,
			ICreditApproval anICCCreditApproval)
			throws CreditApprovalException, TrxParameterException,
			TransactionException {
		 if (anITrxContext == null) {
	            throw new CreditApprovalException("The ITrxContext is null!!!");
	        }
	        if (anICCCreditApproval == null) {
	            throw new CreditApprovalException("The anICCCreditApproval to be updated is null !!!");
	        }
	        ICreditApprovalTrxValue trxValue = formulateTrxValue(anITrxContext, anICCCreditApprovalTrxValue, anICCCreditApproval);
	        //trxValue.setFromState("DRAFT");
	        //trxValue.setStatus("ACTIVE");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_CREDIT_APPROVAL);
	        return operate(trxValue, param);
	}
	
	
	 /**
	 * @return Maker Save Credit Approval  .
	 */
	 public ICreditApprovalTrxValue makerSaveCreditApproval(ITrxContext anITrxContext, ICreditApproval anICCCreditApproval) throws CreditApprovalException,TrxParameterException,
		TransactionException {
	        if (anITrxContext == null) {
	            throw new CreditApprovalException("The ITrxContext is null!!!");
	        }
	        if (anICCCreditApproval == null) {
	            throw new CreditApprovalException("The ICreditApproval to be updated is null !!!");
	        }

	        ICreditApprovalTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCCreditApproval);
	        trxValue.setFromState("DRAFT");
	        trxValue.setStatus("PENDING_PERFECTION");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_CREDIT_APPROVAL);
	        return operate(trxValue, param);
	    }
	 
		/**
		 * @return List of all Credit Approval according to criteria .
		 */
	 public List getAllActual(String searchTxtApprovalCode,String searchTxtApprovalName)throws CreditApprovalException,TrxParameterException,TransactionException 
			{
		      if(searchTxtApprovalCode!=null&& searchTxtApprovalName!=null){
		
			 return getCreditApprovalBusManager().getAllCreditApproval( searchTxtApprovalCode, searchTxtApprovalName);
			}else{
				throw new CreditApprovalException("ERROR- Search criteria is null.");
			}
		    }


	//------------------------------------File Upload-----------------------------------------------------
	
	 private ICreditApprovalTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, OBFileMapperID obFileMapperID) {
		 ICreditApprovalTrxValue ccCreditApprovalTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccCreditApprovalTrxValue = new OBCreditApprovalTrxValue(anICMSTrxValue);
	        } else {
	            ccCreditApprovalTrxValue = new OBCreditApprovalTrxValue();
	        }
	        ccCreditApprovalTrxValue = formulateTrxValueID(anITrxContext, (ICreditApprovalTrxValue) ccCreditApprovalTrxValue);
	        ccCreditApprovalTrxValue.setStagingFileMapperID(obFileMapperID);
	        return ccCreditApprovalTrxValue;
	    }
	 
	 private ICreditApprovalTrxValue formulateTrxValueID(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IFileMapperId fileId) {
		 ICreditApprovalTrxValue ccCreditApprovalTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccCreditApprovalTrxValue = new OBCreditApprovalTrxValue(anICMSTrxValue);
	        } else {
	            ccCreditApprovalTrxValue = new OBCreditApprovalTrxValue();
	        }
	        ccCreditApprovalTrxValue = formulateTrxValueID(anITrxContext, (ICreditApprovalTrxValue) ccCreditApprovalTrxValue);
	        ccCreditApprovalTrxValue.setStagingFileMapperID(fileId);
	        return ccCreditApprovalTrxValue;
	    }
	 private ICreditApprovalTrxValue formulateTrxValueID(ITrxContext anITrxContext, ICreditApprovalTrxValue anICreditApprovalTrxValue) {
	        anICreditApprovalTrxValue.setTrxContext(anITrxContext);
	        anICreditApprovalTrxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_CREDIT_APPROVAL);
	        return anICreditApprovalTrxValue;
	    }
   
	 
	 /**
		 * @return Maker insert a fileID to generate a transation.
		 */
	 public ICreditApprovalTrxValue makerInsertMapperCreditApproval(
				ITrxContext anITrxContext, OBFileMapperID obFileMapperID)
				throws CreditApprovalException, TrxParameterException,
				TransactionException {
			// TODO Auto-generated method stub
			if (anITrxContext == null) {
	            throw new CreditApprovalException("The ITrxContext is null!!!");
	        }
	        if (obFileMapperID == null) {
	            throw new CreditApprovalException("The OBFileMapperID to be updated is null !!!");
	        }

	        ICreditApprovalTrxValue trxValue = formulateTrxValue(anITrxContext, null, obFileMapperID);
	        trxValue.setFromState("PENDING_INSERT");
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_FILE_INSERT);
	        return operate(trxValue, param);
		} 
	 
	 /**
		 * @return Maker check if previous upload is pending.
		 */
	 public boolean isPrevFileUploadPending() throws CreditApprovalException, TrxParameterException,
		TransactionException  {
		 
			try {
				return getCreditApprovalBusManager().isPrevFileUploadPending();
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new CreditApprovalException("ERROR-- Due to null CreditApproval object cannot update.");
			}
		}
	 
	 /**
		 * @return Maker insert uploaded files in Staging table.
		 */
	 
	 public int insertCreditApproval(IFileMapperMaster fileMapperMaster,String userName, ArrayList resultlList, long countryId) throws CreditApprovalException, TrxParameterException,
		TransactionException  {
		 
			try {
				return getCreditApprovalBusManager().insertCreditApproval(fileMapperMaster, userName, resultlList, countryId);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new CreditApprovalException("ERROR-- Due to null CreditApproval object cannot update.");
			}
		}
	 
	 /**
		 * @return create record with TransID.
		 */
	 
	 public ICreditApprovalTrxValue getInsertFileByTrxID(String trxID)
		throws CreditApprovalException, TransactionException,
		CommandProcessingException {
		 	ICreditApprovalTrxValue trxValue = new OBCreditApprovalTrxValue();
		 	trxValue.setTransactionID(String.valueOf(trxID));
		 	trxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_SYS_BRANCH);
		 	OBCMSTrxParameter param = new OBCMSTrxParameter();
		 	param.setAction(ICMSConstant.ACTION_READ_FILE_INSERT);
		 return operate(trxValue, param);
}
	 
	 /**
		 * @return Pagination for uploaded files in CreditApproval.
		 */
	 public List getAllStage(String searchBy, String login)throws CreditApprovalException,TrxParameterException,TransactionException {
		if(searchBy!=null){
	
		 return getCreditApprovalBusManager().getAllStageCreditApproval( searchBy, login);
		}else{
			throw new CreditApprovalException("ERROR- Search criteria is null.");
		}
	  }
	
	 /**
		 * @return Checker approval for uploaded files.
		 */
	 
	 public ICreditApprovalTrxValue checkerApproveInsertCreditApproval(
	 		ITrxContext anITrxContext,
	 		ICreditApprovalTrxValue anICreditApprovalTrxValue)
	 		throws CreditApprovalException, TrxParameterException,
	 		TransactionException {
	 	if (anITrxContext == null) {
	        throw new CreditApprovalException("The ITrxContext is null!!!");
	    }
	    if (anICreditApprovalTrxValue == null) {
	        throw new CreditApprovalException
	                ("The ICreditApprovalTrxValue to be updated is null!!!");
	    }
	    anICreditApprovalTrxValue = formulateTrxValueID(anITrxContext, anICreditApprovalTrxValue);
	    OBCMSTrxParameter param = new OBCMSTrxParameter();
	    param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER);
	    return operate(anICreditApprovalTrxValue, param);
	 }
	 
	 /**
		 * @return list of files uploaded in staging table of CreditApproval.
		 */
	 public List getFileMasterList(String searchBy)throws CreditApprovalException,TrxParameterException,TransactionException {
		if(searchBy!=null){
	
		 return getCreditApprovalBusManager().getFileMasterList( searchBy);
		}else{
			throw new CreditApprovalException("ERROR- Search criteria is null.");
		}
	  }
	 
	 /**
		 * @return Maker insert upload files.
		 */
	 public ICreditApproval insertActualCreditApproval(String sysId) throws CreditApprovalException,TrxParameterException,TransactionException

	 {
	  if(sysId != null){
	 	 try {
	 		 return getCreditApprovalBusManager().insertActualCreditApproval(sysId);
	 	 } catch (Exception e) {		 		
	 		 e.printStackTrace();
	 		 throw new CreditApprovalException("ERROR- Transaction for the Id is invalid.");
	 	 }
	  }else{
	 	 throw new CreditApprovalException("ERROR- Id for retrival is null.");
	  }
	 }
	 
	 /**
		 * @return Checker create file master in CreditApproval.
		 */
	 
	 public ICreditApprovalTrxValue checkerCreateCreditApproval(ITrxContext anITrxContext, ICreditApproval anICCCreditApproval, String refStage) throws CreditApprovalException,TrxParameterException,
	 TransactionException {
	     if (anITrxContext == null) {
	         throw new CreditApprovalException("The ITrxContext is null!!!");
	     }
	     if (anICCCreditApproval == null) {
	         throw new CreditApprovalException("The ICCCreditApproval to be updated is null !!!");
	     }

	     ICreditApprovalTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCCreditApproval);
	     trxValue.setFromState("PENDING_CREATE");
	     trxValue.setReferenceID(String.valueOf(anICCCreditApproval.getId()));
	     trxValue.setStagingReferenceID(refStage);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_CHECKER_FILE_MASTER);
	     return operate(trxValue, param);
	 }
	 
	 /**
		 * @return Checker Reject for upload files CreditApproval.
		 */

	 public ICreditApprovalTrxValue checkerRejectInsertCreditApproval(
	 	ITrxContext anITrxContext,
	 	ICreditApprovalTrxValue anICreditApprovalTrxValue)
	 	throws CreditApprovalException, TrxParameterException,
	 	TransactionException {
	 	if (anITrxContext == null) {
	 	  throw new CreditApprovalException("The ITrxContext is null!!!");
	 	}
	 	if (anICreditApprovalTrxValue == null) {
	 	  throw new CreditApprovalException
	 	          ("The ICreditApprovalTrxValue to be updated is null!!!");
	 	}
	 		anICreditApprovalTrxValue = formulateTrxValueID(anITrxContext, anICreditApprovalTrxValue);
	 		OBCMSTrxParameter param = new OBCMSTrxParameter();
	 		param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER);
	 	return operate(anICreditApprovalTrxValue, param);
	 }
	 
	 /**
		 * @return Maker Close rejected files CreditApproval.
		 */

	 public ICreditApprovalTrxValue makerInsertCloseRejectedCreditApproval(
	 		ITrxContext anITrxContext,
	 		ICreditApprovalTrxValue anICreditApprovalTrxValue)
	 		throws CreditApprovalException, TrxParameterException,
	 		TransactionException {
	 	if (anITrxContext == null) {
	         throw new CreditApprovalException("The ITrxContext is null!!!");
	     }
	     if (anICreditApprovalTrxValue == null) {
	         throw new CreditApprovalException("The ICreditApprovalTrxValue to be updated is null!!!");
	     }
	     anICreditApprovalTrxValue = formulateTrxValue(anITrxContext, anICreditApprovalTrxValue);
	     OBCMSTrxParameter param = new OBCMSTrxParameter();
	     param.setAction(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER);
	     return operate(anICreditApprovalTrxValue, param);
	 }

	/**
	 * @return the stagingCreditApprovalFileMapperIdBusManager
	 */
	public ICreditApprovalBusManager getStagingCreditApprovalFileMapperIdBusManager() {
		return stagingCreditApprovalFileMapperIdBusManager;
	}

	/**
	 * @param stagingCreditApprovalFileMapperIdBusManager the stagingCreditApprovalFileMapperIdBusManager to set
	 */
	public void setStagingCreditApprovalFileMapperIdBusManager(
			ICreditApprovalBusManager stagingCreditApprovalFileMapperIdBusManager) {
		this.stagingCreditApprovalFileMapperIdBusManager = stagingCreditApprovalFileMapperIdBusManager;
	}

	/**
	 * @return the creditApprovalFileMapperIdBusManager
	 */
	public ICreditApprovalBusManager getCreditApprovalFileMapperIdBusManager() {
		return creditApprovalFileMapperIdBusManager;
	}

	/**
	 * @param creditApprovalFileMapperIdBusManager the creditApprovalFileMapperIdBusManager to set
	 */
	public void setCreditApprovalFileMapperIdBusManager(
			ICreditApprovalBusManager creditApprovalFileMapperIdBusManager) {
		this.creditApprovalFileMapperIdBusManager = creditApprovalFileMapperIdBusManager;
	}

	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException, TransactionException {
		getCreditApprovalBusManager().deleteTransaction(obFileMapperMaster);
	}

	public long getCountryIdForCountry(String countryName) {
		return getCreditApprovalBusManager().getCountryIdForCountry(countryName);
	}

	public boolean isCreditApprovalNameUnique(String creditApprovalName) {
		return getCreditApprovalBusManager().isCreditApprovalNameUnique(creditApprovalName);
	}

	public boolean isRegionCodeVaild(String regionCode, long countryId) {
		return getCreditApprovalBusManager().isRegionCodeVaild(regionCode, countryId);
	}

	public List getRegionList(String countryCode) {
		return getCreditApprovalBusManager().getRegionList(countryCode);
	}
	
	
	public boolean isCreditEmployeeIdUnique(String employeeId) {
		return getCreditApprovalBusManager().isCreditEmployeeIdUnique(employeeId);
	}
	
}
