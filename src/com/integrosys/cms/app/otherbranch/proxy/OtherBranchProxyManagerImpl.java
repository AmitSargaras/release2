package com.integrosys.cms.app.otherbranch.proxy;

/**
 * This OtherBranchProxyManagerImpl implements the methods that will be available to the
 * operating on a other bank branch
 * 
 * @author $Author: Dattatray Thorat
 * @version $Revision: 1.0 $
 * @since $Date: 2011/02/18 10:03:55 
 */


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.insurancecoverage.bus.InsuranceCoverageException;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.app.otherbank.trx.OBOtherBankTrxValue;
import com.integrosys.cms.app.otherbranch.bus.IOtherBranchBusManager;
import com.integrosys.cms.app.otherbranch.bus.OtherBranchException;
import com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue;
import com.integrosys.cms.app.otherbranch.trx.OBOtherBankBranchTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

public class OtherBranchProxyManagerImpl implements IOtherBranchProxyManager {

	private IOtherBranchBusManager otherBranchBusManager;
	
	private IOtherBranchBusManager stagingOtherBranchBusManager;
	
    private ITrxControllerFactory trxControllerFactory;


	/**
	 * @return the stagingOtherBranchBusManager
	 */
	public IOtherBranchBusManager getStagingOtherBranchBusManager() {
		return stagingOtherBranchBusManager;
	}


	/**
	 * @param stagingOtherBranchBusManager the stagingOtherBranchBusManager to set
	 */
	public void setStagingOtherBranchBusManager(
			IOtherBranchBusManager stagingOtherBranchBusManager) {
		this.stagingOtherBranchBusManager = stagingOtherBranchBusManager;
	}


	/**
	 * @return the otherBranchBusManager
	 */
	public IOtherBranchBusManager getOtherBranchBusManager() {
		return otherBranchBusManager;
	}


	/**
	 * @param otherBranchBusManager the otherBranchBusManager to set
	 */
	public void setOtherBranchBusManager(
			IOtherBranchBusManager otherBranchBusManager) {
		this.otherBranchBusManager = otherBranchBusManager;
	}


	/**
	 * @return the trxControllerFactory
	 */
	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}


	/**
	 * @param trxControllerFactory the trxControllerFactory to set
	 */
	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	/**
	 * @return the SearchResult
	 */
	public SearchResult getOtherBranch() throws OtherBranchException{
		return (SearchResult) getOtherBranchBusManager().getOtherBranch();
	}

	/**
	 * @return the OtherBranch Details
	 */
	public IOtherBranch getOtherBranchById(long id)  throws OtherBranchException,TrxParameterException,TransactionException {
		return (IOtherBranch)getOtherBranchBusManager().getOtherBranchById(id);
	}

	/**
	 * @return the SearchResult
	 */
	public SearchResult getOtherBranchList(String searchType,String searchVal) throws OtherBranchException {
		return (SearchResult)getOtherBranchBusManager().getOtherBranchList(searchType,searchVal);
	}

	/**
	 * @return the OtherBranch Details
	 */
	public IOtherBranch updateOtherBranch(IOtherBranch OtherBranch) throws OtherBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		return (IOtherBranch)getOtherBranchBusManager().updateOtherBranch(OtherBranch);
	}
    
	/**
	 * @return the OtherBranch Details
	 */
	public IOtherBranch deleteOtherBranch(IOtherBranch OtherBranch) throws OtherBranchException , TrxParameterException, TransactionException{
		return (IOtherBranch)getOtherBranchBusManager().deleteOtherBranch(OtherBranch);
	}
	
	/**
	 * @return the OtherBranch Details
	 */
	public IOtherBranch createOtherBranch(IOtherBranch OtherBranch) throws OtherBranchException {
		return (IOtherBranch)getOtherBranchBusManager().createOtherBranch(OtherBranch);
	}
	
	/**
	  * @return OtherBankTrx Value
	  * @param OtherBank Object 
	  * This method fetches the Proper trx value
	  * according to the Object passed as parameter.
	  * 
	  */
	public IOtherBankBranchTrxValue getOtherBranchTrxValue(long aOtherBank) throws OtherBranchException,TrxParameterException,TransactionException {
      if (aOtherBank == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
          throw new OtherBranchException("Invalid OtherBankId");
      }
      IOtherBankBranchTrxValue trxValue = new OBOtherBankBranchTrxValue();
      trxValue.setReferenceID(String.valueOf(aOtherBank));
      trxValue.setTransactionType(ICMSConstant.INSTANCE_OTHER_BANK_BRANCH);
      OBCMSTrxParameter param = new OBCMSTrxParameter();
      param.setAction(ICMSConstant.ACTION_READ_OTHER_BANK_BRANCH);
      return operate(trxValue, param);
  }
	
	/**
	  * 
	  * @param anIOtherBankBranchTrxValue
	  * @param anOBCMSTrxParameter
	  * @return IOtherBankBranchTrxValue
	  * @throws OtherBranchException
	  * @throws TrxParameterException
	  * @throws TransactionException
	  */
	 
	 private IOtherBankBranchTrxValue operate(IOtherBankBranchTrxValue anIOtherBankBranchTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws OtherBranchException,TrxParameterException,TransactionException {
	        ICMSTrxResult result = operateForResult(anIOtherBankBranchTrxValue, anOBCMSTrxParameter);
	        return (IOtherBankBranchTrxValue) result.getTrxValue();
	    }
	 
	 /**
	  * 
	  * @param anICMSTrxValue
	  * @param anOBCMSTrxParameter
	  * @return ICMSTrxResult
	  * @throws OtherBranchException
	  * @throws TrxParameterException
	  * @throws TransactionException
	  * 
	  * 
	  * This method selects the Controller for 
	  * the Operation to be performed.
	  */
	 
	 protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
	 throws OtherBranchException,TrxParameterException,TransactionException {
		 try {
			 ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			 Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			 ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			 return (ICMSTrxResult) result;
		 }

		 catch (OtherBranchException ex) {
			 throw new OtherBranchException(ex.toString());
		 }
	 }
	 
	 /**
	  * @return updated Other Bank Trx value Object
	  * @param Trx object, Other Bank Trx object,Other Bank object to be updated
	  * 
	  * The updated Other Bank object in stored in Staging Table of Other Bank
	  */
	 
	 public IOtherBankBranchTrxValue makerUpdateOtherBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anICCOtherBankTrxValue, IOtherBranch anICCOtherBank) throws OtherBranchException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new OtherBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCOtherBank == null) {
	            throw new OtherBranchException("The ICCOtherBank to be updated is null !!!");
	        }
	        IOtherBankBranchTrxValue trxValue = formulateTrxValue(anITrxContext, anICCOtherBankTrxValue, anICCOtherBank);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_OTHER_BANK_BRANCH);
	        return operate(trxValue, param);
	    }
	 
	 
	 /**
	  * @return updated Other Bank Trx value Object
	  * @param Trx object, Other Bank Trx object,Other Bank object to be updated
	  * 
	  * The updated Other Bank object in stored in Staging Table of Other Bank
	  */
	 
	 public IOtherBankBranchTrxValue makerDeleteOtherBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anICCOtherBankTrxValue, IOtherBranch anICCOtherBank) 
	 	throws OtherBranchException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new OtherBranchException("The ITrxContext is null!!!");
	        }
	        if (anICCOtherBank == null) {
	            throw new OtherBranchException("The ICCOtherBank to be updated is null !!!");
	        }
	        IOtherBankBranchTrxValue trxValue = formulateTrxValue(anITrxContext, anICCOtherBankTrxValue, anICCOtherBank);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_OTHER_BANK_BRANCH);
	        return operate(trxValue, param);
	    }
	 
	 /**
	  * @return updated Other Bank Trx value Object
	  * @param Trx object, Other Bank Trx object,Other Bank object to be updated
	  * After once rejected by Checker, if maker attempts to update the same record 
	  * its done by this method. 
	  * The updated Other Bank object in stored in Staging Table of Other Bank
	  */
	 public IOtherBankBranchTrxValue makerEditRejectedOtherBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anIOtherBankBranchTrxValue, IOtherBranch anIOtherBank) throws OtherBranchException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new OtherBranchException("The ITrxContext is null!!!");
	        }
	        if (anIOtherBankBranchTrxValue == null) {
	            throw new OtherBranchException("The IOtherBankBranchTrxValue to be updated is null!!!");
	        }
	        if (anIOtherBank == null) {
	            throw new OtherBranchException("The IOtherBranch to be updated is null !!!");
	        }
	        anIOtherBankBranchTrxValue = formulateTrxValue(anITrxContext, anIOtherBankBranchTrxValue, anIOtherBank);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_OTHER_BANK_BRANCH);
	        return operate(anIOtherBankBranchTrxValue, param);
	    }
	 /**
	  * 
	  * @param anITrxContext
	  * @param anICMSTrxValue
	  * @param anIOtherBank
	  * @return IOtherBankBranchTrxValue 
	  * 
	  * 
	  */
	 
	 private IOtherBankBranchTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IOtherBranch anIOtherBranch) {
	        IOtherBankBranchTrxValue ccOtherBankTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccOtherBankTrxValue = new OBOtherBankBranchTrxValue(anICMSTrxValue);
	        } else {
	            ccOtherBankTrxValue = new OBOtherBankBranchTrxValue();
	        }
	        ccOtherBankTrxValue = formulateTrxValue(anITrxContext, (IOtherBankBranchTrxValue) ccOtherBankTrxValue);
	        ccOtherBankTrxValue.setStagingOtherBranch(anIOtherBranch);
	        return ccOtherBankTrxValue;
	    }
	 /**
	  * 
	  * @param anITrxContext
	  * @param anIOtherBankBranchTrxValue
	  * @return IOtherBankBranchTrxValue
	  */
	 private IOtherBankBranchTrxValue formulateTrxValue(ITrxContext anITrxContext, IOtherBankBranchTrxValue anIOtherBankBranchTrxValue) {
	        anIOtherBankBranchTrxValue.setTrxContext(anITrxContext);
	        anIOtherBankBranchTrxValue.setTransactionType(ICMSConstant.INSTANCE_OTHER_BANK_BRANCH);
	        return anIOtherBankBranchTrxValue;
	    }

	 /**
		  * @return OtherBankTrx Value
		  * @param OtherBank Object 
		  * This method fetches the Proper trx value
		  * according to the Transaction Id passed as parameter.
		  * 
		  */
	  public IOtherBankBranchTrxValue getOtherBranchByTrxID(String trxID) throws OtherBranchException,TransactionException,CommandProcessingException{
		  IOtherBankBranchTrxValue trxValue = new OBOtherBankBranchTrxValue();
	        trxValue.setTransactionID(String.valueOf(trxID));
	        trxValue.setTransactionType(ICMSConstant.INSTANCE_OTHER_BANK_BRANCH);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_READ_OTHER_BANK_BRANCH_ID);
	        return operate(trxValue, param);
	    }
	  /**
	   * @return Other Bank Trx Value
	   * @param Trx object, Other Bank Trx object,Other Bank object
	   * 
	   * This method Approves the Object passed by Maker
	   */
	  
	  public IOtherBankBranchTrxValue checkerApproveOtherBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anIOtherBankBranchTrxValue) throws OtherBranchException,TransactionException {
	        if (anITrxContext == null) {
	            throw new OtherBranchException("The ITrxContext is null!!!");
	        }
	        if (anIOtherBankBranchTrxValue == null) {
	            throw new OtherBranchException
	                    ("The IOtherBankBranchTrxValue to be updated is null!!!");
	        }
	        anIOtherBankBranchTrxValue = formulateTrxValue(anITrxContext, anIOtherBankBranchTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_OTHER_BANK_BRANCH);
	        return operate(anIOtherBankBranchTrxValue, param);
	    }
	  /**
	   * @return Other Bank Trx Value
	   * @param Trx object, Other Bank Trx object,Other Bank object
	   * 
	   * This method Rejects the Object passed by Maker
	   */
	  public IOtherBankBranchTrxValue checkerRejectOtherBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anIOtherBankBranchTrxValue) throws OtherBranchException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new OtherBranchException("The ITrxContext is null!!!");
	        }
	        if (anIOtherBankBranchTrxValue == null) {
	            throw new OtherBranchException("The IOtherBankBranchTrxValue to be updated is null!!!");
	        }
	        anIOtherBankBranchTrxValue = formulateTrxValue(anITrxContext, anIOtherBankBranchTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_OTHER_BANK_BRANCH);
	        return operate(anIOtherBankBranchTrxValue, param);
	    }
	  /**
	   * @return Other Bank Trx Value
	   * @param Trx object, Other Bank Trx object,Other Bank object
	   * 
	   * This method Close the Object rejected by Checker
	   */
	  public IOtherBankBranchTrxValue makerCloseRejectedOtherBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anIOtherBankBranchTrxValue) throws OtherBranchException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new OtherBranchException("The ITrxContext is null!!!");
	        }
	        if (anIOtherBankBranchTrxValue == null) {
	            throw new OtherBranchException("The IOtherBankBranchTrxValue to be updated is null!!!");
	        }
	        anIOtherBankBranchTrxValue = formulateTrxValue(anITrxContext, anIOtherBankBranchTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_OTHER_BANK_BRANCH);
	        return operate(anIOtherBankBranchTrxValue, param);
	    }
	  
	  /**
		  * @return updated Other Bank Trx value Object
		  * @param Trx object, Other Bank Trx object,Other Bank object to be updated
		  * 
		  * The updated Other Bank object in stored in Staging Table of Other Bank
		  */
		 
		 public IOtherBankBranchTrxValue makerCreateOtherBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anICCOtherBankTrxValue, IOtherBranch anICCOtherBank) throws OtherBranchException,TrxParameterException,TransactionException {
		        if (anITrxContext == null) {
		            throw new OtherBranchException("The ITrxContext is null!!!");
		        }
		        if (anICCOtherBank == null) {
		            throw new OtherBranchException("The ICCOtherBank to be created is null !!!");
		        }
		        IOtherBankBranchTrxValue trxValue = formulateTrxValue(anITrxContext, anICCOtherBankTrxValue, anICCOtherBank);
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_OTHER_BANK_BRANCH);
		        return operate(trxValue, param);
		}
		 
		 /**
		   * @return Relationship Manager Trx Value
		   * @param Trx object, Relationship Manager Trx object,Relationship Manager object
		   * 
		   * This method Close the Object rejected by Checker
		   */
		  public IOtherBankBranchTrxValue makerCloseDraftOtherBankBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anIOtherBankBranchTrxValue) throws InsuranceCoverageException,TrxParameterException,TransactionException {
		        if (anITrxContext == null) {
		            throw new InsuranceCoverageException("The ITrxContext is null!!!");
		        }
		        if (anIOtherBankBranchTrxValue == null) {
		            throw new InsuranceCoverageException("The IOtherBankBranchTrxValue to be updated is null!!!");
		        }
		        anIOtherBankBranchTrxValue = formulateTrxValue(anITrxContext, anIOtherBankBranchTrxValue);
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_OTHER_BANK_BRANCH);
		        return operate(anIOtherBankBranchTrxValue, param);
		    }
		  
			 
			 /**
			  * @return updated Relationship Manager Trx value Object
			  * @param Trx object, Relationship Manager Trx object,Relationship Manager object to be updated
			  * 
			  * The updated Relationship Manager object in stored in Staging Table of Relationship Manager
			  */
			 
			 public IOtherBankBranchTrxValue makerSaveOtherBankBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anICCInsuranceCoverageTrxValue, IOtherBranch anICCOtherBank) throws InsuranceCoverageException,TrxParameterException,TransactionException {
			        if (anITrxContext == null) {
			            throw new InsuranceCoverageException("The ITrxContext is null!!!");
			        }
			        if (anICCOtherBank == null) {
			            throw new InsuranceCoverageException("The IInsuranceCoverage to be created is null !!!");
			        }
			        IOtherBankBranchTrxValue trxValue = formulateTrxValue(anITrxContext, anICCInsuranceCoverageTrxValue, anICCOtherBank);
			        OBCMSTrxParameter param = new OBCMSTrxParameter();
			        trxValue.setFromState(ICMSConstant.STATE_PENDING_PERFECTION);
			        trxValue.setStatus(ICMSConstant.STATE_PENDING_PERFECTION);
			        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_CREATE_OTHER_BANK_BRANCH);
			        return operate(trxValue, param);
			    }
			 
			 /**
			  * @return updated Relationship Manager Trx value Object
			  * @param Trx object, Relationship Manager Trx object,Relationship Manager object to be updated
			  * 
			  * The updated Relationship Manager object in stored in Staging Table of Relationship Manager
			  */
			 
			 public IOtherBankBranchTrxValue makerUpdateCreateOtherBankBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anICCInsuranceCoverageTrxValue, IOtherBranch anICCOtherBank) throws InsuranceCoverageException,TrxParameterException,TransactionException {
			        if (anITrxContext == null) {
			            throw new InsuranceCoverageException("The ITrxContext is null!!!");
			        }
			        if (anICCOtherBank == null) {
			            throw new InsuranceCoverageException("The ICCInsuranceCoverage to be updated is null !!!");
			        }
			        IOtherBankBranchTrxValue trxValue = formulateTrxValue(anITrxContext, anICCInsuranceCoverageTrxValue, anICCOtherBank);
			        OBCMSTrxParameter param = new OBCMSTrxParameter();
			        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_OTHER_BANK_BRANCH);
			        return operate(trxValue, param);
			    }
			 
			 /**
			  * @return updated Relationship Manager Trx value Object
			  * @param Trx object, Relationship Manager Trx object,Relationship Manager object to be updated
			  * 
			  * The updated Relationship Manager object in stored in Staging Table of Relationship Manager
			  */
			 
			 public IOtherBankBranchTrxValue makerUpdateSaveOtherBankBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anICCInsuranceCoverageTrxValue, IOtherBranch anICCOtherBank) throws InsuranceCoverageException,TrxParameterException,TransactionException {
			        if (anITrxContext == null) {
			            throw new InsuranceCoverageException("The ITrxContext is null!!!");
			        }
			        if (anICCOtherBank == null) {
			            throw new InsuranceCoverageException("The ICCInsuranceCoverage to be updated is null !!!");
			        }
			        IOtherBankBranchTrxValue trxValue = formulateTrxValue(anITrxContext, anICCInsuranceCoverageTrxValue, anICCOtherBank);
			        OBCMSTrxParameter param = new OBCMSTrxParameter();
			        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_SAVE_OTHER_BANK_BRANCH);
			        return operate(trxValue, param);
			    }	 
			 
			 public boolean isOBCodeUnique(String obCode){
				 return getOtherBranchBusManager().isOBCodeUnique(obCode);
			 }
			 
			//*******************************UPLOADE***************************************************
			 public boolean isPrevFileUploadPendingBankBranch() throws OtherBankException, TrxParameterException,
				TransactionException  {
				 
					try {
						return getOtherBranchBusManager().isPrevFileUploadPendingBankBranch();
					} catch (Exception e) {
						
						e.printStackTrace();
						throw new OtherBankException("ERROR-- Due to null  object cannot update.");
					}
				}
			 
			 
			 public IOtherBankBranchTrxValue makerInsertMapperOtherBankBranch(ITrxContext anITrxContext, OBFileMapperID obFileMapperID)throws OtherBankException, TrxParameterException,
						TransactionException {
					// TODO Auto-generated method stub
					if (anITrxContext == null) {
			            throw new OtherBankException("The ITrxContext is null!!!");
			        }
			        if (obFileMapperID == null) {
			            throw new OtherBankException("The OBFileMapperID to be updated is null !!!");
			        }

			        IOtherBankBranchTrxValue trxValue = formulateTrxValueBankBranch(anITrxContext, null, obFileMapperID);
			        trxValue.setFromState("PENDING_INSERT");
			        OBCMSTrxParameter param = new OBCMSTrxParameter();
			        param.setAction(ICMSConstant.ACTION_MAKER_FILE_INSERT);
			        return operate(trxValue, param);
				} 
			 
			 private IOtherBankBranchTrxValue formulateTrxValueBankBranch(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, OBFileMapperID obFileMapperID) {
				 IOtherBankBranchTrxValue ccOtherBankTrxValue = null;
			        if (anICMSTrxValue != null) {
			            ccOtherBankTrxValue = new OBOtherBankBranchTrxValue(anICMSTrxValue);
			        } else {
			            ccOtherBankTrxValue = new OBOtherBankBranchTrxValue();
			        }
			        ccOtherBankTrxValue = formulateTrxValueIDBankBranch(anITrxContext, (IOtherBankBranchTrxValue) ccOtherBankTrxValue);
			        ccOtherBankTrxValue.setStagingFileMapperID(obFileMapperID);
			        return ccOtherBankTrxValue;
			    }
			 
			 private IOtherBankBranchTrxValue formulateTrxValueIDBankBranch(ITrxContext anITrxContext, IOtherBankBranchTrxValue anIOtherBankTrxValue) {
			        anIOtherBankTrxValue.setTrxContext(anITrxContext);
			        anIOtherBankTrxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_OTHER_BANK_BRANCH);
			        return anIOtherBankTrxValue;
			    }
			 
			 public int insertOtherBankBranch(IFileMapperMaster fileMapperMaster,String userName, ArrayList resultlList) throws OtherBankException, TrxParameterException,
				TransactionException  {
				 
					try {
						return getOtherBranchBusManager().insertOtherBankBranch(fileMapperMaster, userName, resultlList);
					} catch (Exception e) {
						
						e.printStackTrace();
						throw new OtherBankException("ERROR-- Due to null OtherBank object cannot update.");
					}
				}
			 
			 
			 public IOtherBankBranchTrxValue getInsertFileByTrxIDBankBranch(String trxID)
				throws OtherBankException, TransactionException,
				CommandProcessingException {
				 IOtherBankBranchTrxValue trxValue = new OBOtherBankBranchTrxValue();
				 	trxValue.setTransactionID(String.valueOf(trxID));
				 	trxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_OTHER_BANK_BRANCH);
				 	OBCMSTrxParameter param = new OBCMSTrxParameter();
				 	param.setAction(ICMSConstant.ACTION_READ_FILE_INSERT);
				 return operate(trxValue, param);
		}
			 
			 public List getAllStageBankBranch(String searchBy, String login)throws OtherBankException,TrxParameterException,TransactionException {
					if(searchBy!=null){
				
					 return getOtherBranchBusManager().getAllStageOtherBankBranch( searchBy, login); 
					}else{
						throw new OtherBankException("ERROR- Search criteria is null.");
					}
				  }
				
			 public IOtherBankBranchTrxValue checkerApproveInsertOtherBankBranch(
				 		ITrxContext anITrxContext,
				 		IOtherBankBranchTrxValue anIOtherBankTrxValue)
				 		throws OtherBankException, TrxParameterException,
				 		TransactionException {
				 	if (anITrxContext == null) {
				        throw new OtherBankException("The ITrxContext is null!!!");
				    }
				    if (anIOtherBankTrxValue == null) {
				        throw new OtherBankException
				                ("The IOtherBankTrxValue to be updated is null!!!");
				    }
				    anIOtherBankTrxValue = formulateTrxValueIDBankBranch(anITrxContext, anIOtherBankTrxValue);
				    OBCMSTrxParameter param = new OBCMSTrxParameter();
				    param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER);
				    return operate(anIOtherBankTrxValue, param);
				 }
				 
				 /**
					 * @return list of files uploaded in staging table of OtherBank.
					 */
				 public List getFileMasterListBankBranch(String searchBy)throws OtherBankException,TrxParameterException,TransactionException {
					if(searchBy!=null){
				
					 return getOtherBranchBusManager().getFileMasterListBankBranch( searchBy);   
					}else{
						throw new OtherBankException("ERROR- Search criteria is null.");
					}
				  }
				 
				 /**
					 * @return Maker insert upload files.
					 */
				 public IOtherBranch insertActualOtherBankBranch(String sysId) throws OtherBankException,TrxParameterException,TransactionException

				 {
				  if(sysId != null){
				 	 try {
				 		 return getOtherBranchBusManager().insertActualOtherBankBranch(sysId);
				 	 } catch (Exception e) {		 		
				 		 e.printStackTrace();
				 		 throw new OtherBankException("ERROR- Transaction for the Id is invalid.");
				 	 }
				  }else{
				 	 throw new OtherBankException("ERROR- Id for retrival is null.");
				  }
				 }
				 
				 /**
					 * @return Checker create file master in OtherBank.
					 */
				 
				 public IOtherBankBranchTrxValue checkerCreateOtherBankBranch(ITrxContext anITrxContext, IOtherBranch anICCOtherBank, String refStage) throws OtherBankException,TrxParameterException,
				 TransactionException {
				     if (anITrxContext == null) {
				         throw new OtherBankException("The ITrxContext is null!!!");
				     }
				     if (anICCOtherBank == null) {
				         throw new OtherBankException("The ICCOtherBank to be updated is null !!!");
				     }

				     IOtherBankBranchTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCOtherBank);
				     trxValue.setFromState("PENDING_CREATE");
				     trxValue.setReferenceID(String.valueOf(anICCOtherBank.getId()));
				     trxValue.setStagingReferenceID(refStage);
				     OBCMSTrxParameter param = new OBCMSTrxParameter();
				     param.setAction(ICMSConstant.ACTION_CHECKER_FILE_MASTER);
				     return operate(trxValue, param);
				 }
				 
				 /**
					 * @return Checker Reject for upload files OtherBank.
					 */

				 public IOtherBankBranchTrxValue checkerRejectInsertOtherBankBranch(
				 	ITrxContext anITrxContext,
				 	IOtherBankBranchTrxValue anIOtherBankTrxValue)
				 	throws OtherBankException, TrxParameterException,
				 	TransactionException {
				 	if (anITrxContext == null) {
				 	  throw new OtherBankException("The ITrxContext is null!!!");
				 	}
				 	if (anIOtherBankTrxValue == null) {
				 	  throw new OtherBankException
				 	          ("The IOtherBankTrxValue to be updated is null!!!");
				 	}
				 		anIOtherBankTrxValue = formulateTrxValueIDBankBranch(anITrxContext, anIOtherBankTrxValue);
				 		OBCMSTrxParameter param = new OBCMSTrxParameter();
				 		param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER);
				 	return operate(anIOtherBankTrxValue, param);
				 }
				 
				 public IOtherBankBranchTrxValue makerInsertCloseRejectedOtherBankBranch(
					 		ITrxContext anITrxContext,
					 		IOtherBankBranchTrxValue anIOtherBankTrxValue)
					 		throws OtherBankException, TrxParameterException,
					 		TransactionException {
					 	if (anITrxContext == null) {
					         throw new OtherBankException("The ITrxContext is null!!!");
					     }
					     if (anIOtherBankTrxValue == null) {
					         throw new OtherBankException("The IOtherBankTrxValue to be updated is null!!!");
					     }
					     anIOtherBankTrxValue = formulateTrxValue(anITrxContext, anIOtherBankTrxValue);
					     OBCMSTrxParameter param = new OBCMSTrxParameter();
					     param.setAction(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER_OTHER_BANK_BRANCH);
					     return operate(anIOtherBankTrxValue, param);
					 }
				 
				 public boolean isUniqueCodeBankBranch(String branchCode) throws OtherBankException,TrxParameterException,
					TransactionException {
				     return  getOtherBranchBusManager().isUniqueCodeBankBranch(branchCode);  
				    }
				 
				 public boolean isUniqueBranchName(String branchName,String bankCode) throws OtherBankException,TrxParameterException,
					TransactionException {
				     return  getOtherBranchBusManager().isUniqueBranchName(branchName,bankCode);  
				    }
				 
				 public boolean isUniqueRbiCode(String rbiCode) throws OtherBankException,TrxParameterException,
					TransactionException {
				     return  getOtherBranchBusManager().isUniqueRbiCode(rbiCode);  
				    }


				public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws OtherBranchException, TrxParameterException,TransactionException {
					getOtherBranchBusManager().deleteTransaction(obFileMapperMaster);					
				}


				public String isCodeExisting(String countryCode,String regionCode, String stateCode, String cityCode)throws OtherBranchException, TrxParameterException,TransactionException {
					return getOtherBranchBusManager().isCodeExisting(countryCode, regionCode, stateCode, cityCode);
				}
				
		
//*******************************UPLOAD END************************************************
}
