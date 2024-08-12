package com.integrosys.cms.app.otherbank.proxy;

/**
 * This OtherBankProxyManagerImpl implements the methods that will be available to the
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
import com.integrosys.cms.app.otherbank.bus.IOtherBankBusManager;
import com.integrosys.cms.app.otherbank.bus.OBOtherBank;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.app.otherbank.trx.OBOtherBankTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;

public class OtherBankProxyManagerImpl implements IOtherBankProxyManager {

	
	
	private IOtherBankBusManager otherBankBusManager;
	
	private IOtherBankBusManager stagingOtherBankBusManager;
	
    private ITrxControllerFactory trxControllerFactory;

    /**
	 * @return the otherBankBusManager
	 */
	public IOtherBankBusManager getOtherBankBusManager() {
		return otherBankBusManager;
	}


	/**
	 * @param otherBankBusManager the otherBankBusManager to set
	 */
	public void setOtherBankBusManager(IOtherBankBusManager otherBankBusManager) {
		this.otherBankBusManager = otherBankBusManager;
	}


	/**
	 * @return the stagingOtherBankBusManager
	 */
	public IOtherBankBusManager getStagingOtherBankBusManager() {
		return stagingOtherBankBusManager;
	}


	/**
	 * @param stagingOtherBankBusManager the stagingOtherBankBusManager to set
	 */
	public void setStagingOtherBankBusManager(
			IOtherBankBusManager stagingOtherBankBusManager) {
		this.stagingOtherBankBusManager = stagingOtherBankBusManager;
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
	/*public SearchResult getOtherBank() throws OtherBankException{
		return (SearchResult)getOtherBankBusManager().getOtherBank();
	}*/
	
	/**
	 * @return the boolean
	 */
	public boolean checkOtherBranchById(long id) throws OtherBankException  {
		return (boolean)getOtherBankBusManager().checkOtherBranchById(id);
	}
	
	/**
	 * @return the OtherBank details
	 */
	/*public IOtherBank getOtherBankById(long id) throws OtherBankException {
		return (IOtherBank)getOtherBankBusManager().getOtherBankById(id);
	}*/
	
	public IOtherBank getOtherBankById(long id) throws OtherBankException,TrxParameterException,TransactionException 
    {
	 if(id!=0){
		 return getOtherBankBusManager().getOtherBankById(id);
	 }else{
		 throw new OtherBankException("ERROR-- Key for Other Bank is null.");
	 }
	 
    }
	

	/**
	 * @return the SearchResult
	 */
	public SearchResult getOtherBankList(String bankCode,String bankName) throws OtherBankException{
		return (SearchResult)getOtherBankBusManager().getOtherBankList(bankCode,bankName);
	}

	
	public List<OBOtherBank> getOtherBankList(String bankCode,String bankName,String branchName,String branchCode)throws OtherBankException{
		return (List<OBOtherBank>)getOtherBankBusManager().getOtherBankList(bankCode,bankName,branchName,branchCode);
	}
	
	
	public SearchResult getInsurerList() throws OtherBankException{
		return (SearchResult)getOtherBankBusManager().getInsurerList();
	}
	/**
	 * @return the OtherBank details
	 * @throws ConcurrentUpdateException 
	 * @throws TransactionException 
	 * @throws TrxParameterException 
	 */
	public IOtherBank updateOtherBank(IOtherBank OtherBank) throws OtherBankException, TrxParameterException, TransactionException,ConcurrentUpdateException {
		if(OtherBank!=null){
			return (IOtherBank)getOtherBankBusManager().updateOtherBank(OtherBank);
		}else{
			 throw new OtherBankException("Other Bank Object is null.");
		 }	
	}
    
	/**
	 * @return the OtherBank details
	 */
	public IOtherBank deleteOtherBank(IOtherBank OtherBank) throws OtherBankException , TrxParameterException, TransactionException{
		if(OtherBank!=null){
			return (IOtherBank)getOtherBankBusManager().deleteOtherBank(OtherBank);
		}else{
			 throw new OtherBankException("Other Bank Object is null.");
		}		
	}
	
	
	/**
	 * @return the OtherBank details
	 */
	public IOtherBank createOtherBank(IOtherBank OtherBank) throws OtherBankException {
		return (IOtherBank)getOtherBankBusManager().createOtherBank(OtherBank);
	}
	
	/**
	  * @return OtherBankTrx Value
	  * @param OtherBank Object 
	  * This method fetches the Proper trx value
	  * according to the Object passed as parameter.
	  * 
	  */
	public IOtherBankTrxValue getOtherBankTrxValue(long aOtherBank) throws OtherBankException,TrxParameterException,TransactionException {
       if (aOtherBank == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
           throw new OtherBankException("Invalid OtherBankId");
       }
       IOtherBankTrxValue trxValue = new OBOtherBankTrxValue();
       trxValue.setReferenceID(String.valueOf(aOtherBank));
       trxValue.setTransactionType(ICMSConstant.INSTANCE_OTHER_BANK);
       OBCMSTrxParameter param = new OBCMSTrxParameter();
       param.setAction(ICMSConstant.ACTION_READ_OTHER_BANK);
       return operate(trxValue, param);
   }
	
	/**
	  * 
	  * @param anIOtherBankTrxValue
	  * @param anOBCMSTrxParameter
	  * @return IOtherBankTrxValue
	  * @throws OtherBankException
	  * @throws TrxParameterException
	  * @throws TransactionException
	  */
	 
	 private IOtherBankTrxValue operate(IOtherBankTrxValue anIOtherBankTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws OtherBankException,TrxParameterException,TransactionException {
	        ICMSTrxResult result = operateForResult(anIOtherBankTrxValue, anOBCMSTrxParameter);
	        return (IOtherBankTrxValue) result.getTrxValue();
	    }
	 
	 /**
	  * 
	  * @param anICMSTrxValue
	  * @param anOBCMSTrxParameter
	  * @return ICMSTrxResult
	  * @throws OtherBankException
	  * @throws TrxParameterException
	  * @throws TransactionException
	  * 
	  * 
	  * This method selects the Controller for 
	  * the Operation to be performed.
	  */
	 
	 protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
	 throws OtherBankException,TrxParameterException,TransactionException {
		 try {
			 ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			 Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			 ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			 return (ICMSTrxResult) result;
		 }

		 catch (OtherBankException ex) {
			 throw new OtherBankException(ex.toString());
		 }
	 }
	 
	 /**
	  * @return updated Other Bank Trx value Object
	  * @param Trx object, Other Bank Trx object,Other Bank object to be updated
	  * 
	  * The updated Other Bank object in stored in Staging Table of Other Bank
	  */
	 
	 public IOtherBankTrxValue makerUpdateOtherBank(ITrxContext anITrxContext, IOtherBankTrxValue anICCOtherBankTrxValue, IOtherBank anICCOtherBank) throws OtherBankException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new OtherBankException("The ITrxContext is null!!!");
	        }
	        if (anICCOtherBank == null) {
	            throw new OtherBankException("The ICCOtherBank to be updated is null !!!");
	        }
	        IOtherBankTrxValue trxValue = formulateTrxValue(anITrxContext, anICCOtherBankTrxValue, anICCOtherBank);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_OTHER_BANK);
	        return operate(trxValue, param);
	    }
	 
	 
	 /**
	  * @return updated Other Bank Trx value Object
	  * @param Trx object, Other Bank Trx object,Other Bank object to be updated
	  * 
	  * The updated Other Bank object in stored in Staging Table of Other Bank
	  */
	 
	 public IOtherBankTrxValue makerDeleteOtherBank(ITrxContext anITrxContext, IOtherBankTrxValue anICCOtherBankTrxValue, IOtherBank anICCOtherBank) 
	 	throws OtherBankException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new OtherBankException("The ITrxContext is null!!!");
	        }
	        if (anICCOtherBank == null) {
	            throw new OtherBankException("The ICCOtherBank to be updated is null !!!");
	        }
	        IOtherBankTrxValue trxValue = formulateTrxValue(anITrxContext, anICCOtherBankTrxValue, anICCOtherBank);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_OTHER_BANK);
	        return operate(trxValue, param);
	    }
	 
	 /**
	  * @return updated Other Bank Trx value Object
	  * @param Trx object, Other Bank Trx object,Other Bank object to be updated
	  * After once rejected by Checker, if maker attempts to update the same record 
	  * its done by this method. 
	  * The updated Other Bank object in stored in Staging Table of Other Bank
	  */
	 public IOtherBankTrxValue makerEditRejectedOtherBank(ITrxContext anITrxContext, IOtherBankTrxValue anIOtherBankTrxValue, IOtherBank anIOtherBank) throws OtherBankException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new OtherBankException("The ITrxContext is null!!!");
	        }
	        if (anIOtherBankTrxValue == null) {
	            throw new OtherBankException("The IOtherBankTrxValue to be updated is null!!!");
	        }
	        if (anIOtherBank == null) {
	            throw new OtherBankException("The IOtherBank to be updated is null !!!");
	        }
	        anIOtherBankTrxValue = formulateTrxValue(anITrxContext, anIOtherBankTrxValue, anIOtherBank);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_OTHER_BANK);
	        return operate(anIOtherBankTrxValue, param);
	    }
	 /**
	  * 
	  * @param anITrxContext
	  * @param anICMSTrxValue
	  * @param anIOtherBank
	  * @return IOtherBankTrxValue 
	  * 
	  * 
	  */
	 
	 private IOtherBankTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IOtherBank anIOtherBank) {
	        IOtherBankTrxValue ccOtherBankTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccOtherBankTrxValue = new OBOtherBankTrxValue(anICMSTrxValue);
	        } else {
	            ccOtherBankTrxValue = new OBOtherBankTrxValue();
	        }
	        ccOtherBankTrxValue = formulateTrxValue(anITrxContext, (IOtherBankTrxValue) ccOtherBankTrxValue);
	        ccOtherBankTrxValue.setStagingOtherBank(anIOtherBank);
	        return ccOtherBankTrxValue;
	    }
	 /**
	  * 
	  * @param anITrxContext
	  * @param anIOtherBankTrxValue
	  * @return IOtherBankTrxValue
	  */
	 private IOtherBankTrxValue formulateTrxValue(ITrxContext anITrxContext, IOtherBankTrxValue anIOtherBankTrxValue) {
	        anIOtherBankTrxValue.setTrxContext(anITrxContext);
	        anIOtherBankTrxValue.setTransactionType(ICMSConstant.INSTANCE_OTHER_BANK);
	        return anIOtherBankTrxValue;
	    }

	 /**
		  * @return OtherBankTrx Value
		  * @param OtherBank Object 
		  * This method fetches the Proper trx value
		  * according to the Transaction Id passed as parameter.
		  * 
		  */
	  public IOtherBankTrxValue getOtherBankByTrxID(String trxID) throws OtherBankException,TransactionException,CommandProcessingException{
		  IOtherBankTrxValue trxValue = new OBOtherBankTrxValue();
	        trxValue.setTransactionID(String.valueOf(trxID));
	        trxValue.setTransactionType(ICMSConstant.INSTANCE_OTHER_BANK);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_READ_OTHER_BANK_ID);
	        return operate(trxValue, param);
	    }
	  /**
	   * @return Other Bank Trx Value
	   * @param Trx object, Other Bank Trx object,Other Bank object
	   * 
	   * This method Approves the Object passed by Maker
	   */
	  
	  public IOtherBankTrxValue checkerApproveOtherBank(ITrxContext anITrxContext, IOtherBankTrxValue anIOtherBankTrxValue) throws OtherBankException,TransactionException {
	        if (anITrxContext == null) {
	            throw new OtherBankException("The ITrxContext is null!!!");
	        }
	        if (anIOtherBankTrxValue == null) {
	            throw new OtherBankException
	                    ("The IOtherBankTrxValue to be updated is null!!!");
	        }
	        anIOtherBankTrxValue = formulateTrxValue(anITrxContext, anIOtherBankTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_OTHER_BANK);
	        return operate(anIOtherBankTrxValue, param);
	    }
	  /**
	   * @return Other Bank Trx Value
	   * @param Trx object, Other Bank Trx object,Other Bank object
	   * 
	   * This method Rejects the Object passed by Maker
	   */
	  public IOtherBankTrxValue checkerRejectOtherBank(ITrxContext anITrxContext, IOtherBankTrxValue anIOtherBankTrxValue) throws OtherBankException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new OtherBankException("The ITrxContext is null!!!");
	        }
	        if (anIOtherBankTrxValue == null) {
	            throw new OtherBankException("The IOtherBankTrxValue to be updated is null!!!");
	        }
	        anIOtherBankTrxValue = formulateTrxValue(anITrxContext, anIOtherBankTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_OTHER_BANK);
	        return operate(anIOtherBankTrxValue, param);
	    }
	  /**
	   * @return Other Bank Trx Value
	   * @param Trx object, Other Bank Trx object,Other Bank object
	   * 
	   * This method Close the Object rejected by Checker
	   */
	  public IOtherBankTrxValue makerCloseRejectedOtherBank(ITrxContext anITrxContext, IOtherBankTrxValue anIOtherBankTrxValue) throws OtherBankException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new OtherBankException("The ITrxContext is null!!!");
	        }
	        if (anIOtherBankTrxValue == null) {
	            throw new OtherBankException("The IOtherBankTrxValue to be updated is null!!!");
	        }
	        anIOtherBankTrxValue = formulateTrxValue(anITrxContext, anIOtherBankTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_OTHER_BANK);
	        return operate(anIOtherBankTrxValue, param);
	    }
	  
	/**
	 * @return updated Other Bank Trx value Object
	 * @param Trx
	 *            object, Other Bank Trx object,Other Bank object to be updated
	 * 
	 *            The updated Other Bank object in stored in Staging Table of
	 *            Other Bank
	 */

	public IOtherBankTrxValue makerCreateOtherBank(ITrxContext anITrxContext,
			IOtherBankTrxValue anICCOtherBankTrxValue, IOtherBank anICCOtherBank)
			throws OtherBankException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new OtherBankException("The ITrxContext is null!!!");
		}
		if (anICCOtherBank == null) {
			throw new OtherBankException(
					"The ICCOtherBank to be created is null !!!");
		}
		IOtherBankTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCOtherBankTrxValue, anICCOtherBank);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_OTHER_BANK);
		return operate(trxValue, param);
	}

	/**
	 * @return the SearchResult
	 */
	public SearchResult getOtherBranchList(String branchCode,
			String branchName, String state, String city, long id) {
		return (SearchResult) getOtherBankBusManager().getOtherBranchList(
				branchCode, branchName, state, city, id);
	}

	/**
	 * @return Relationship Manager Trx Value
	 * @param Trx
	 *            object, Relationship Manager Trx object,Relationship Manager
	 *            object
	 * 
	 *            This method Close the Object rejected by Checker
	 */
	public IOtherBankTrxValue makerCloseDraftOtherBank(
			ITrxContext anITrxContext, IOtherBankTrxValue anIOtherBankTrxValue)
			throws InsuranceCoverageException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new InsuranceCoverageException("The ITrxContext is null!!!");
		}
		if (anIOtherBankTrxValue == null) {
			throw new InsuranceCoverageException(
					"The IOtherBankTrxValue to be updated is null!!!");
		}
		anIOtherBankTrxValue = formulateTrxValue(anITrxContext,
				anIOtherBankTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_OTHER_BANK);
		return operate(anIOtherBankTrxValue, param);
	}

	/**
	 * @return updated Relationship Manager Trx value Object
	 * @param Trx
	 *            object, Relationship Manager Trx object,Relationship Manager
	 *            object to be updated
	 * 
	 *            The updated Relationship Manager object in stored in Staging
	 *            Table of Relationship Manager
	 */

	public IOtherBankTrxValue makerSaveOtherBank(ITrxContext anITrxContext,
			IOtherBankTrxValue anICCInsuranceCoverageTrxValue,
			IOtherBank anICCOtherBank) throws InsuranceCoverageException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new InsuranceCoverageException("The ITrxContext is null!!!");
		}
		if (anICCOtherBank == null) {
			throw new InsuranceCoverageException(
					"The IInsuranceCoverage to be created is null !!!");
		}
		IOtherBankTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCInsuranceCoverageTrxValue, anICCOtherBank);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		trxValue.setFromState(ICMSConstant.STATE_PENDING_PERFECTION);
		trxValue.setStatus(ICMSConstant.STATE_PENDING_PERFECTION);
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_CREATE_OTHER_BANK);
		return operate(trxValue, param);
	}

	/**
	 * @return updated Relationship Manager Trx value Object
	 * @param Trx
	 *            object, Relationship Manager Trx object,Relationship Manager
	 *            object to be updated
	 * 
	 *            The updated Relationship Manager object in stored in Staging
	 *            Table of Relationship Manager
	 */

	public IOtherBankTrxValue makerUpdateCreateOtherBank(
			ITrxContext anITrxContext,
			IOtherBankTrxValue anICCInsuranceCoverageTrxValue,
			IOtherBank anICCOtherBank) throws InsuranceCoverageException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new InsuranceCoverageException("The ITrxContext is null!!!");
		}
		if (anICCOtherBank == null) {
			throw new InsuranceCoverageException(
					"The ICCInsuranceCoverage to be updated is null !!!");
		}
		IOtherBankTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCInsuranceCoverageTrxValue, anICCOtherBank);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_OTHER_BANK);
		return operate(trxValue, param);
	}

	/**
	 * @return updated Relationship Manager Trx value Object
	 * @param Trx
	 *            object, Relationship Manager Trx object,Relationship Manager
	 *            object to be updated
	 * 
	 *            The updated Relationship Manager object in stored in Staging
	 *            Table of Relationship Manager
	 */

	public IOtherBankTrxValue makerUpdateSaveOtherBank(
			ITrxContext anITrxContext,
			IOtherBankTrxValue anICCInsuranceCoverageTrxValue,
			IOtherBank anICCOtherBank) throws InsuranceCoverageException,
			TrxParameterException, TransactionException {
		if (anITrxContext == null) {
			throw new InsuranceCoverageException("The ITrxContext is null!!!");
		}
		if (anICCOtherBank == null) {
			throw new InsuranceCoverageException(
					"The ICCInsuranceCoverage to be updated is null !!!");
		}
		IOtherBankTrxValue trxValue = formulateTrxValue(anITrxContext,
				anICCInsuranceCoverageTrxValue, anICCOtherBank);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_SAVE_OTHER_BANK);
		return operate(trxValue, param);
	}

	/**
	 * @return the ArrayList
	 */
	public List getCountryList() throws OtherBankException {
		return (List) getOtherBankBusManager().getCountryList();
	}

	/**
	 * @return the ArrayList
	 */
	public List getRegionList(String countryId) throws OtherBankException {
		return (List) getOtherBankBusManager().getRegionList(countryId);
	}
	public SearchResult getInsurerNameFromCode(String insurerName)throws OtherBankException {
		return (SearchResult) getOtherBankBusManager().getInsurerNameFromCode( insurerName);
	}
	/**
	 * @return the ArrayList
	 */
	public List getStateList(String regionId) throws OtherBankException {
		return (List) getOtherBankBusManager().getStateList(regionId);
	}

	/**
	 * @return the ArrayList
	 */
	public List getCityList(String stateId) throws OtherBankException {
		return (List) getOtherBankBusManager().getCityList(stateId);
	}

	// *******************************UPLOADE***************************************************
	public boolean isPrevFileUploadPending() throws OtherBankException,
			TrxParameterException, TransactionException {

		try {
			return getOtherBankBusManager().isPrevFileUploadPending();
		} catch (Exception e) {

			e.printStackTrace();
			throw new OtherBankException(
					"ERROR-- Due to null  object cannot update.");
		}
	}

	public IOtherBankTrxValue makerInsertMapperOtherBank(
			ITrxContext anITrxContext, OBFileMapperID obFileMapperID)
			throws OtherBankException, TrxParameterException,
			TransactionException {
		// TODO Auto-generated method stub
		if (anITrxContext == null) {
			throw new OtherBankException("The ITrxContext is null!!!");
		}
		if (obFileMapperID == null) {
			throw new OtherBankException(
					"The OBFileMapperID to be updated is null !!!");
		}

		IOtherBankTrxValue trxValue = formulateTrxValue(anITrxContext, null,
				obFileMapperID);
		trxValue.setFromState("PENDING_INSERT");
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_FILE_INSERT);
		return operate(trxValue, param);
	}

	private IOtherBankTrxValue formulateTrxValue(ITrxContext anITrxContext,
			ICMSTrxValue anICMSTrxValue, OBFileMapperID obFileMapperID) {
		IOtherBankTrxValue ccOtherBankTrxValue = null;
		if (anICMSTrxValue != null) {
			ccOtherBankTrxValue = new OBOtherBankTrxValue(anICMSTrxValue);
		} else {
			ccOtherBankTrxValue = new OBOtherBankTrxValue();
		}
		ccOtherBankTrxValue = formulateTrxValueID(anITrxContext,
				(IOtherBankTrxValue) ccOtherBankTrxValue);
		ccOtherBankTrxValue.setStagingFileMapperID(obFileMapperID);
		return ccOtherBankTrxValue;
	}

	private IOtherBankTrxValue formulateTrxValueID(ITrxContext anITrxContext,
			IOtherBankTrxValue anIOtherBankTrxValue) {
		anIOtherBankTrxValue.setTrxContext(anITrxContext);
		anIOtherBankTrxValue
				.setTransactionType(ICMSConstant.INSTANCE_INSERT_OTHER_BANK);
		return anIOtherBankTrxValue;
	}

	public int insertOtherBank(IFileMapperMaster fileMapperMaster,
			String userName, ArrayList resultlList) throws OtherBankException,
			TrxParameterException, TransactionException {

		try {
			return getOtherBankBusManager().insertOtherBank(fileMapperMaster,
					userName, resultlList);
		} catch (Exception e) {

			e.printStackTrace();
			throw new OtherBankException(
					"ERROR-- Due to null OtherBank object cannot update.");
		}
	}

	public IOtherBankTrxValue getInsertFileByTrxID(String trxID)
			throws OtherBankException, TransactionException,
			CommandProcessingException {
		IOtherBankTrxValue trxValue = new OBOtherBankTrxValue();
		trxValue.setTransactionID(String.valueOf(trxID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_OTHER_BANK);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_FILE_INSERT);
		return operate(trxValue, param);
	}

	public List getAllStage(String searchBy, String login)
			throws OtherBankException, TrxParameterException,
			TransactionException {
		if (searchBy != null) {

			return getOtherBankBusManager().getAllStageOtherBank(searchBy,
					login);
		} else {
			throw new OtherBankException("ERROR- Search criteria is null.");
		}
	}

	public IOtherBankTrxValue checkerApproveInsertOtherBank(
			ITrxContext anITrxContext, IOtherBankTrxValue anIOtherBankTrxValue)
			throws OtherBankException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new OtherBankException("The ITrxContext is null!!!");
		}
		if (anIOtherBankTrxValue == null) {
			throw new OtherBankException(
					"The IOtherBankTrxValue to be updated is null!!!");
		}
		anIOtherBankTrxValue = formulateTrxValueID(anITrxContext,
				anIOtherBankTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER);
		return operate(anIOtherBankTrxValue, param);
	}

	/**
	 * @return list of files uploaded in staging table of OtherBank.
	 */
	public List getFileMasterList(String searchBy) throws OtherBankException,
			TrxParameterException, TransactionException {
		if (searchBy != null) {

			return getOtherBankBusManager().getFileMasterList(searchBy);
		} else {
			throw new OtherBankException("ERROR- Search criteria is null.");
		}
	}

	/**
	 * @return Maker insert upload files.
	 */
	public IOtherBank insertActualOtherBank(String sysId)
			throws OtherBankException, TrxParameterException,
			TransactionException

	{
		if (sysId != null) {
			try {
				return getOtherBankBusManager().insertActualOtherBank(sysId);
			} catch (Exception e) {
				e.printStackTrace();
				throw new OtherBankException(
						"ERROR- Transaction for the Id is invalid.");
			}
		} else {
			throw new OtherBankException("ERROR- Id for retrival is null.");
		}
	}

	/**
	 * @return Checker create file master in OtherBank.
	 */

	public IOtherBankTrxValue checkerCreateOtherBank(ITrxContext anITrxContext,
			IOtherBank anICCOtherBank, String refStage)
			throws OtherBankException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new OtherBankException("The ITrxContext is null!!!");
		}
		if (anICCOtherBank == null) {
			throw new OtherBankException(
					"The ICCOtherBank to be updated is null !!!");
		}

		IOtherBankTrxValue trxValue = formulateTrxValue(anITrxContext, null,
				anICCOtherBank);
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

	public IOtherBankTrxValue checkerRejectInsertOtherBank(
			ITrxContext anITrxContext, IOtherBankTrxValue anIOtherBankTrxValue)
			throws OtherBankException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new OtherBankException("The ITrxContext is null!!!");
		}
		if (anIOtherBankTrxValue == null) {
			throw new OtherBankException(
					"The IOtherBankTrxValue to be updated is null!!!");
		}
		anIOtherBankTrxValue = formulateTrxValueID(anITrxContext,
				anIOtherBankTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER);
		return operate(anIOtherBankTrxValue, param);
	}

	public IOtherBankTrxValue makerInsertCloseRejectedOtherBank(
			ITrxContext anITrxContext, IOtherBankTrxValue anIOtherBankTrxValue)
			throws OtherBankException, TrxParameterException,
			TransactionException {
		if (anITrxContext == null) {
			throw new OtherBankException("The ITrxContext is null!!!");
		}
		if (anIOtherBankTrxValue == null) {
			throw new OtherBankException(
					"The IOtherBankTrxValue to be updated is null!!!");
		}
		anIOtherBankTrxValue = formulateTrxValue(anITrxContext,
				anIOtherBankTrxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param
				.setAction(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER_OTHER_BANK);
		return operate(anIOtherBankTrxValue, param);
	}

	public boolean isUniqueCode(String branchCode) throws OtherBankException,
			TrxParameterException, TransactionException {
		return getOtherBankBusManager().isUniqueCode(branchCode);
	}

	public boolean isUniqueName(String branchName) throws OtherBankException,
			TrxParameterException, TransactionException {
		return getOtherBankBusManager().isUniqueName(branchName);
	}
						
				
	//*******************************UPLOAD END************************************************
						 
    //*******************************Guarantee Security methods starts*********************************************
    
	public String getCityName(String cityId) throws OtherBankException{
		return (String)getOtherBankBusManager().getCityName(cityId);
	}
	
	public String getStateName(String cityId) throws OtherBankException{
		return (String)getOtherBankBusManager().getStateName(cityId);
	}
	
	public String getRegionName(String cityId) throws OtherBankException{
		return (String)getOtherBankBusManager().getRegionName(cityId);
	}
	
	public String getCountryName(String cityId) throws OtherBankException{
		return (String)getOtherBankBusManager().getCountryName(cityId);
	}
						 
    //*******************************Guarantee Security methods end************************************************						 

	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws OtherBankException, TrxParameterException,TransactionException {
		getOtherBankBusManager().deleteTransaction(obFileMapperMaster);					
	}


	public String isCodeExisting(String countryCode, String regionCode,String stateCode, String cityCode) throws OtherBankException,TrxParameterException, TransactionException {
		return getOtherBankBusManager().isCodeExisting(countryCode, regionCode, stateCode, cityCode);
	}
}
