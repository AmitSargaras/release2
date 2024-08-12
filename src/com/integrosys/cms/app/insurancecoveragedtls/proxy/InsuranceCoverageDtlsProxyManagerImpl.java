package com.integrosys.cms.app.insurancecoveragedtls.proxy;

/**
 * This InsuranceCoverageProxyManagerImpl implements the methods that will be available to the
 * operating on a Insurance Coverage Details Master
 * 
 * @author $Author: Dattatray Thorat
 * @version $Revision: 1.0 $
 */

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
import com.integrosys.cms.app.insurancecoverage.bus.InsuranceCoverageException;
import com.integrosys.cms.app.insurancecoveragedtls.bus.IInsuranceCoverageDtlsBusManager;
import com.integrosys.cms.app.insurancecoveragedtls.bus.InsuranceCoverageDtlsException;
import com.integrosys.cms.app.insurancecoveragedtls.trx.IInsuranceCoverageDtlsTrxValue;
import com.integrosys.cms.app.insurancecoveragedtls.trx.OBInsuranceCoverageDtlsTrxValue;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.ui.insurancecoveragedtls.IInsuranceCoverageDtls;

public class InsuranceCoverageDtlsProxyManagerImpl implements IInsuranceCoverageDtlsProxyManager {
	
	private IInsuranceCoverageDtlsBusManager insuranceCoverageDtlsBusManager;
	
	private IInsuranceCoverageDtlsBusManager stagingInsuranceCoverageDtlsBusManager;
	
    private ITrxControllerFactory trxControllerFactory;

	/**
	 * @return the insuranceCoverageBusManager
	 */
	public IInsuranceCoverageDtlsBusManager getInsuranceCoverageDtlsBusManager() {
		return insuranceCoverageDtlsBusManager;
	}


	/**
	 * @param insuranceCoverageBusManager the insuranceCoverageBusManager to set
	 */
	public void setInsuranceCoverageDtlsBusManager(
			IInsuranceCoverageDtlsBusManager insuranceCoverageDtlsBusManager) {
		this.insuranceCoverageDtlsBusManager = insuranceCoverageDtlsBusManager;
	}


	/**
	 * @return the stagingInsuranceCoverageDtlsBusManager
	 */
	public IInsuranceCoverageDtlsBusManager getStagingInsuranceCoverageDtlsBusManager() {
		return stagingInsuranceCoverageDtlsBusManager;
	}


	/**
	 * @param stagingInsuranceCoverageDtlsBusManager the stagingInsuranceCoverageDtlsBusManager to set
	 */
	public void setStagingInsuranceCoverageDtlsBusManager(
			IInsuranceCoverageDtlsBusManager stagingInsuranceCoverageDtlsBusManager) {
		this.stagingInsuranceCoverageDtlsBusManager = stagingInsuranceCoverageDtlsBusManager;
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
	public SearchResult getInsuranceCoverageDtlsList() throws InsuranceCoverageDtlsException{
		return (SearchResult)getInsuranceCoverageDtlsBusManager().getInsuranceCoverageDtlsList();
	}
	
	public IInsuranceCoverageDtls getInsuranceCoverageDtlsById(long id) throws InsuranceCoverageDtlsException,TrxParameterException,TransactionException 
    {
	 if(id!=0){
		 return getInsuranceCoverageDtlsBusManager().getInsuranceCoverageDtlsById(id);
	 }else{
		 throw new OtherBankException("ERROR-- Key for InsuranceCoverageDtls is null.");
	 }
	 
    }
	
	/**
	 * @return the Insurance Coverage details
	 * @throws ConcurrentUpdateException 
	 * @throws TransactionException 
	 * @throws TrxParameterException 
	 */
	 public IInsuranceCoverageDtls updateInsuranceCoverageDtls(IInsuranceCoverageDtls insuranceCoverage) throws InsuranceCoverageDtlsException, TrxParameterException, TransactionException,ConcurrentUpdateException {
		if(insuranceCoverage!=null){
			return (IInsuranceCoverageDtls)getInsuranceCoverageDtlsBusManager().updateInsuranceCoverageDtls(insuranceCoverage);
		}else{
			 throw new InsuranceCoverageDtlsException("Insurance Coverage Object is null.");
		 }	
	}
    
	/**
	 * @return the InsuranceCoverageDtls details
	 */
	public IInsuranceCoverageDtls deleteInsuranceCoverageDtls(IInsuranceCoverageDtls insuranceCoverage) throws InsuranceCoverageDtlsException , TrxParameterException, TransactionException{
		if(insuranceCoverage!=null){
			return (IInsuranceCoverageDtls)getInsuranceCoverageDtlsBusManager().deleteInsuranceCoverageDtls(insuranceCoverage);
		}else{
			 throw new InsuranceCoverageDtlsException("Insurance Coverage Object is null.");
		}		
	}
	
	
	/**
	 * @return the InsuranceCoverageDtls details
	 */
	public IInsuranceCoverageDtls createInsuranceCoverageDtls(IInsuranceCoverageDtls insuranceCoverage) throws InsuranceCoverageDtlsException {
		return (IInsuranceCoverageDtls)getInsuranceCoverageDtlsBusManager().createInsuranceCoverageDtls(insuranceCoverage);
	}
	
	/**
	  * @return InsuranceCoverageDtlsTrx Value
	  * @param InsuranceCoverageDtls Object 
	  * This method fetches the Proper trx value
	  * according to the Object passed as parameter.
	  * 
	  */
	public IInsuranceCoverageDtlsTrxValue getInsuranceCoverageDtlsTrxValue(long aInsuranceCoverageDtls) throws InsuranceCoverageDtlsException,TrxParameterException,TransactionException {
       if (aInsuranceCoverageDtls == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
           throw new InsuranceCoverageDtlsException("Invalid InsuranceCoverageDtlsId");
       }
       IInsuranceCoverageDtlsTrxValue trxValue = new OBInsuranceCoverageDtlsTrxValue();
       trxValue.setReferenceID(String.valueOf(aInsuranceCoverageDtls));
       trxValue.setTransactionType(ICMSConstant.INSTANCE_INSURANCE_COVERAGE_DTLS);
       OBCMSTrxParameter param = new OBCMSTrxParameter();
       param.setAction(ICMSConstant.ACTION_READ_INSURANCE_COVERAGE_DTLS);
       return operate(trxValue, param);
   }
	
	/**
	  * 
	  * @param anIInsuranceCoverageDtlsTrxValue
	  * @param anOBCMSTrxParameter
	  * @return IInsuranceCoverageDtlsTrxValue
	  * @throws InsuranceCoverageDtlsException
	  * @throws TrxParameterException
	  * @throws TransactionException
	  */
	 
	 private IInsuranceCoverageDtlsTrxValue operate(IInsuranceCoverageDtlsTrxValue anIInsuranceCoverageDtlsTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws InsuranceCoverageDtlsException,TrxParameterException,TransactionException {
	        ICMSTrxResult result = operateForResult(anIInsuranceCoverageDtlsTrxValue, anOBCMSTrxParameter);
	        return (IInsuranceCoverageDtlsTrxValue) result.getTrxValue();
	    }
	 
	 /**
	  * 
	  * @param anICMSTrxValue
	  * @param anOBCMSTrxParameter
	  * @return ICMSTrxResult
	  * @throws InsuranceCoverageDtlsException
	  * @throws TrxParameterException
	  * @throws TransactionException
	  * 
	  * 
	  * This method selects the Controller for 
	  * the Operation to be performed.
	  */
	 
	 protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
	 throws InsuranceCoverageDtlsException,TrxParameterException,TransactionException {
		 try {
			 ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			 Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			 ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			 return (ICMSTrxResult) result;
		 }

		 catch (InsuranceCoverageDtlsException ex) {
			 throw new InsuranceCoverageDtlsException(ex.toString());
		 }
	 }
	 
	 /**
	  * @return updated Insurance Coverage Trx value Object
	  * @param Trx object, Insurance Coverage Trx object,Insurance Coverage object to be updated
	  * 
	  * The updated Insurance Coverage object in stored in Staging Table of Insurance Coverage
	  */
	 
	 public IInsuranceCoverageDtlsTrxValue makerUpdateInsuranceCoverageDtls(ITrxContext anITrxContext, IInsuranceCoverageDtlsTrxValue anICCInsuranceCoverageDtlsTrxValue, IInsuranceCoverageDtls anICCInsuranceCoverageDtls) throws InsuranceCoverageDtlsException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new InsuranceCoverageDtlsException("The ITrxContext is null!!!");
	        }
	        if (anICCInsuranceCoverageDtls == null) {
	            throw new InsuranceCoverageDtlsException("The ICCInsuranceCoverageDtls to be updated is null !!!");
	        }
	        IInsuranceCoverageDtlsTrxValue trxValue = formulateTrxValue(anITrxContext, anICCInsuranceCoverageDtlsTrxValue, anICCInsuranceCoverageDtls);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_INSURANCE_COVERAGE_DTLS);
	        return operate(trxValue, param);
	    }
	 
	 
	 /**
	  * @return updated Insurance Coverage Trx value Object
	  * @param Trx object, Insurance Coverage Trx object,Insurance Coverage object to be updated
	  * 
	  * The updated Insurance Coverage object in stored in Staging Table of Insurance Coverage
	  */
	 
	 public IInsuranceCoverageDtlsTrxValue makerDeleteInsuranceCoverageDtls(ITrxContext anITrxContext, IInsuranceCoverageDtlsTrxValue anICCInsuranceCoverageDtlsTrxValue, IInsuranceCoverageDtls anICCInsuranceCoverageDtls) 
	 	throws InsuranceCoverageDtlsException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new InsuranceCoverageDtlsException("The ITrxContext is null!!!");
	        }
	        if (anICCInsuranceCoverageDtls == null) {
	            throw new InsuranceCoverageDtlsException("The ICCInsuranceCoverageDtls to be updated is null !!!");
	        }
	        IInsuranceCoverageDtlsTrxValue trxValue = formulateTrxValue(anITrxContext, anICCInsuranceCoverageDtlsTrxValue, anICCInsuranceCoverageDtls);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_INSURANCE_COVERAGE_DTLS);
	        return operate(trxValue, param);
	    }
	 
	 /**
	  * @return updated Insurance Coverage Trx value Object
	  * @param Trx object, Insurance Coverage Trx object,Insurance Coverage object to be updated
	  * After once rejected by Checker, if maker attempts to update the same record 
	  * its done by this method. 
	  * The updated Insurance Coverage object in stored in Staging Table of Insurance Coverage
	  */
	 public IInsuranceCoverageDtlsTrxValue makerEditRejectedInsuranceCoverageDtls(ITrxContext anITrxContext, IInsuranceCoverageDtlsTrxValue anIInsuranceCoverageDtlsTrxValue, IInsuranceCoverageDtls anIInsuranceCoverageDtls) throws InsuranceCoverageDtlsException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new InsuranceCoverageDtlsException("The ITrxContext is null!!!");
	        }
	        if (anIInsuranceCoverageDtlsTrxValue == null) {
	            throw new InsuranceCoverageDtlsException("The IInsuranceCoverageDtlsTrxValue to be updated is null!!!");
	        }
	        if (anIInsuranceCoverageDtls == null) {
	            throw new InsuranceCoverageDtlsException("The IInsuranceCoverageDtls to be updated is null !!!");
	        }
	        anIInsuranceCoverageDtlsTrxValue = formulateTrxValue(anITrxContext, anIInsuranceCoverageDtlsTrxValue, anIInsuranceCoverageDtls);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_INSURANCE_COVERAGE_DTLS);
	        return operate(anIInsuranceCoverageDtlsTrxValue, param);
	    }
	 /**
	  * 
	  * @param anITrxContext
	  * @param anICMSTrxValue
	  * @param anIInsuranceCoverageDtls
	  * @return IInsuranceCoverageDtlsTrxValue 
	  * 
	  * 
	  */
	 
	 private IInsuranceCoverageDtlsTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IInsuranceCoverageDtls anIInsuranceCoverageDtls) {
	        IInsuranceCoverageDtlsTrxValue ccInsuranceCoverageDtlsTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccInsuranceCoverageDtlsTrxValue = new OBInsuranceCoverageDtlsTrxValue(anICMSTrxValue);
	        } else {
	            ccInsuranceCoverageDtlsTrxValue = new OBInsuranceCoverageDtlsTrxValue();
	        }
	        ccInsuranceCoverageDtlsTrxValue = formulateTrxValue(anITrxContext, (IInsuranceCoverageDtlsTrxValue) ccInsuranceCoverageDtlsTrxValue);
	        ccInsuranceCoverageDtlsTrxValue.setStagingInsuranceCoverageDtls(anIInsuranceCoverageDtls);
	        return ccInsuranceCoverageDtlsTrxValue;
	    }
	 /**
	  * 
	  * @param anITrxContext
	  * @param anIInsuranceCoverageDtlsTrxValue
	  * @return IInsuranceCoverageDtlsTrxValue
	  */
	 private IInsuranceCoverageDtlsTrxValue formulateTrxValue(ITrxContext anITrxContext, IInsuranceCoverageDtlsTrxValue anIInsuranceCoverageDtlsTrxValue) {
	        anIInsuranceCoverageDtlsTrxValue.setTrxContext(anITrxContext);
	        anIInsuranceCoverageDtlsTrxValue.setTransactionType(ICMSConstant.INSTANCE_INSURANCE_COVERAGE_DTLS);
	        return anIInsuranceCoverageDtlsTrxValue;
	    }

	 /**
		  * @return InsuranceCoverageDtlsTrx Value
		  * @param InsuranceCoverageDtls Object 
		  * This method fetches the Proper trx value
		  * according to the Transaction Id passed as parameter.
		  * 
		  */
	  public IInsuranceCoverageDtlsTrxValue getInsuranceCoverageDtlsByTrxID(String trxID) throws InsuranceCoverageDtlsException,TransactionException,CommandProcessingException{
		  IInsuranceCoverageDtlsTrxValue trxValue = new OBInsuranceCoverageDtlsTrxValue();
	        trxValue.setTransactionID(String.valueOf(trxID));
	        trxValue.setTransactionType(ICMSConstant.INSTANCE_INSURANCE_COVERAGE_DTLS);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_READ_INSURANCE_COVERAGE_DTLS_ID);
	        return operate(trxValue, param);
	    }
	  /**
	   * @return Insurance Coverage Trx Value
	   * @param Trx object, Insurance Coverage Trx object,Insurance Coverage object
	   * 
	   * This method Approves the Object passed by Maker
	   */
	  
	  public IInsuranceCoverageDtlsTrxValue checkerApproveInsuranceCoverageDtls(ITrxContext anITrxContext, IInsuranceCoverageDtlsTrxValue anIInsuranceCoverageDtlsTrxValue) throws InsuranceCoverageDtlsException,TransactionException {
	        if (anITrxContext == null) {
	            throw new InsuranceCoverageDtlsException("The ITrxContext is null!!!");
	        }
	        if (anIInsuranceCoverageDtlsTrxValue == null) {
	            throw new InsuranceCoverageDtlsException
	                    ("The IInsuranceCoverageDtlsTrxValue to be updated is null!!!");
	        }
	        anIInsuranceCoverageDtlsTrxValue = formulateTrxValue(anITrxContext, anIInsuranceCoverageDtlsTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_INSURANCE_COVERAGE_DTLS);
	        return operate(anIInsuranceCoverageDtlsTrxValue, param);
	    }
	  /**
	   * @return Insurance Coverage Trx Value
	   * @param Trx object, Insurance Coverage Trx object,Insurance Coverage object
	   * 
	   * This method Rejects the Object passed by Maker
	   */
	  public IInsuranceCoverageDtlsTrxValue checkerRejectInsuranceCoverageDtls(ITrxContext anITrxContext, IInsuranceCoverageDtlsTrxValue anIInsuranceCoverageDtlsTrxValue) throws InsuranceCoverageDtlsException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new InsuranceCoverageDtlsException("The ITrxContext is null!!!");
	        }
	        if (anIInsuranceCoverageDtlsTrxValue == null) {
	            throw new InsuranceCoverageDtlsException("The IInsuranceCoverageDtlsTrxValue to be updated is null!!!");
	        }
	        anIInsuranceCoverageDtlsTrxValue = formulateTrxValue(anITrxContext, anIInsuranceCoverageDtlsTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_INSURANCE_COVERAGE_DTLS);
	        return operate(anIInsuranceCoverageDtlsTrxValue, param);
	    }
	  /**
	   * @return Insurance Coverage Trx Value
	   * @param Trx object, Insurance Coverage Trx object,Insurance Coverage object
	   * 
	   * This method Close the Object rejected by Checker
	   */
	  public IInsuranceCoverageDtlsTrxValue makerCloseRejectedInsuranceCoverageDtls(ITrxContext anITrxContext, IInsuranceCoverageDtlsTrxValue anIInsuranceCoverageDtlsTrxValue) throws InsuranceCoverageDtlsException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new InsuranceCoverageDtlsException("The ITrxContext is null!!!");
	        }
	        if (anIInsuranceCoverageDtlsTrxValue == null) {
	            throw new InsuranceCoverageDtlsException("The IInsuranceCoverageDtlsTrxValue to be updated is null!!!");
	        }
	        anIInsuranceCoverageDtlsTrxValue = formulateTrxValue(anITrxContext, anIInsuranceCoverageDtlsTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_INSURANCE_COVERAGE_DTLS);
	        return operate(anIInsuranceCoverageDtlsTrxValue, param);
	    }
	  
	  /**
		  * @return updated Insurance Coverage Trx value Object
		  * @param Trx object, Insurance Coverage Trx object,Insurance Coverage object to be updated
		  * 
		  * The updated Insurance Coverage object in stored in Staging Table of Insurance Coverage
		  */
		 
		 public IInsuranceCoverageDtlsTrxValue makerCreateInsuranceCoverageDtls(ITrxContext anITrxContext, IInsuranceCoverageDtlsTrxValue anICCInsuranceCoverageDtlsTrxValue, IInsuranceCoverageDtls anICCInsuranceCoverageDtls) throws InsuranceCoverageDtlsException,TrxParameterException,TransactionException {
		        if (anITrxContext == null) {
		            throw new InsuranceCoverageDtlsException("The ITrxContext is null!!!");
		        }
		        if (anICCInsuranceCoverageDtls == null) {
		            throw new InsuranceCoverageDtlsException("The IInsuranceCoverageDtls to be created is null !!!");
		        }
		        IInsuranceCoverageDtlsTrxValue trxValue = formulateTrxValue(anITrxContext, anICCInsuranceCoverageDtlsTrxValue, anICCInsuranceCoverageDtls);
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_INSURANCE_COVERAGE_DTLS);
		        return operate(trxValue, param);
		    }
		 
		 public boolean isICCodeUnique(String rmCode){
			 return getInsuranceCoverageDtlsBusManager().isICCodeUnique(rmCode);
		 }
		 
		 /**
		   * @return Relationship Manager Trx Value
		   * @param Trx object, Relationship Manager Trx object,Relationship Manager object
		   * 
		   * This method Close the Object rejected by Checker
		   */
		  public IInsuranceCoverageDtlsTrxValue makerCloseDraftInsuranceCoverageDtls(ITrxContext anITrxContext, IInsuranceCoverageDtlsTrxValue anIInsuranceCoverageDtlsTrxValue) throws InsuranceCoverageException,TrxParameterException,TransactionException {
		        if (anITrxContext == null) {
		            throw new InsuranceCoverageException("The ITrxContext is null!!!");
		        }
		        if (anIInsuranceCoverageDtlsTrxValue == null) {
		            throw new InsuranceCoverageException("The IInsuranceCoverageDtlsTrxValue to be updated is null!!!");
		        }
		        anIInsuranceCoverageDtlsTrxValue = formulateTrxValue(anITrxContext, anIInsuranceCoverageDtlsTrxValue);
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_INSURANCE_COVERAGE_DTLS);
		        return operate(anIInsuranceCoverageDtlsTrxValue, param);
		    }
		  
			 
			 /**
			  * @return updated Relationship Manager Trx value Object
			  * @param Trx object, Relationship Manager Trx object,Relationship Manager object to be updated
			  * 
			  * The updated Relationship Manager object in stored in Staging Table of Relationship Manager
			  */
			 
			 public IInsuranceCoverageDtlsTrxValue makerSaveInsuranceCoverageDtls(ITrxContext anITrxContext, IInsuranceCoverageDtlsTrxValue anICCInsuranceCoverageTrxValue, IInsuranceCoverageDtls anICCInsuranceCoverageDtls) throws InsuranceCoverageException,TrxParameterException,TransactionException {
			        if (anITrxContext == null) {
			            throw new InsuranceCoverageException("The ITrxContext is null!!!");
			        }
			        if (anICCInsuranceCoverageDtls == null) {
			            throw new InsuranceCoverageException("The IInsuranceCoverage to be created is null !!!");
			        }
			        IInsuranceCoverageDtlsTrxValue trxValue = formulateTrxValue(anITrxContext, anICCInsuranceCoverageTrxValue, anICCInsuranceCoverageDtls);
			        OBCMSTrxParameter param = new OBCMSTrxParameter();
			        trxValue.setFromState(ICMSConstant.STATE_PENDING_PERFECTION);
			        trxValue.setStatus(ICMSConstant.STATE_PENDING_PERFECTION);
			        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_CREATE_INSURANCE_COVERAGE_DTLS);
			        return operate(trxValue, param);
			    }
			 
			 /**
			  * @return updated Relationship Manager Trx value Object
			  * @param Trx object, Relationship Manager Trx object,Relationship Manager object to be updated
			  * 
			  * The updated Relationship Manager object in stored in Staging Table of Relationship Manager
			  */
			 
			 public IInsuranceCoverageDtlsTrxValue makerUpdateCreateInsuranceCoverageDtls(ITrxContext anITrxContext, IInsuranceCoverageDtlsTrxValue anICCInsuranceCoverageTrxValue, IInsuranceCoverageDtls anICCInsuranceCoverageDtls) throws InsuranceCoverageException,TrxParameterException,TransactionException {
			        if (anITrxContext == null) {
			            throw new InsuranceCoverageException("The ITrxContext is null!!!");
			        }
			        if (anICCInsuranceCoverageDtls == null) {
			            throw new InsuranceCoverageException("The ICCInsuranceCoverage to be updated is null !!!");
			        }
			        IInsuranceCoverageDtlsTrxValue trxValue = formulateTrxValue(anITrxContext, anICCInsuranceCoverageTrxValue, anICCInsuranceCoverageDtls);
			        OBCMSTrxParameter param = new OBCMSTrxParameter();
			        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_INSURANCE_COVERAGE_DTLS);
			        return operate(trxValue, param);
			    }
			 
			 /**
			  * @return updated Relationship Manager Trx value Object
			  * @param Trx object, Relationship Manager Trx object,Relationship Manager object to be updated
			  * 
			  * The updated Relationship Manager object in stored in Staging Table of Relationship Manager
			  */
			 
			 public IInsuranceCoverageDtlsTrxValue makerUpdateSaveInsuranceCoverageDtls(ITrxContext anITrxContext, IInsuranceCoverageDtlsTrxValue anICCInsuranceCoverageTrxValue, IInsuranceCoverageDtls anICCInsuranceCoverageDtls) throws InsuranceCoverageException,TrxParameterException,TransactionException {
			        if (anITrxContext == null) {
			            throw new InsuranceCoverageException("The ITrxContext is null!!!");
			        }
			        if (anICCInsuranceCoverageDtls == null) {
			            throw new InsuranceCoverageException("The ICCInsuranceCoverage to be updated is null !!!");
			        }
			        IInsuranceCoverageDtlsTrxValue trxValue = formulateTrxValue(anITrxContext, anICCInsuranceCoverageTrxValue, anICCInsuranceCoverageDtls);
			        OBCMSTrxParameter param = new OBCMSTrxParameter();
			        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_SAVE_INSURANCE_COVERAGE_DTLS);
			        return operate(trxValue, param);
			    }
}
