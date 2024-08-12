package com.integrosys.cms.app.insurancecoverage.proxy;

/**
 * This InsuranceCoverageProxyManagerImpl implements the methods that will be available to the
 * operating on a Insurance Coverage Master
 * 
 * @author $Author: Dattatray Thorat
 * @version $Revision: 1.0 $
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
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.insurancecoverage.bus.IInsuranceCoverageBusManager;
import com.integrosys.cms.app.insurancecoverage.bus.InsuranceCoverageException;
import com.integrosys.cms.app.insurancecoverage.trx.IInsuranceCoverageTrxValue;
import com.integrosys.cms.app.insurancecoverage.trx.OBInsuranceCoverageTrxValue;
import com.integrosys.cms.app.insurancecoveragedtls.bus.InsuranceCoverageDtlsException;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue;
import com.integrosys.cms.app.relationshipmgr.trx.OBRelationshipMgrTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

public class InsuranceCoverageProxyManagerImpl implements IInsuranceCoverageProxyManager {

	
	private IInsuranceCoverageBusManager insuranceCoverageBusManager;
	
	private IInsuranceCoverageBusManager stagingInsuranceCoverageBusManager;
	
    private ITrxControllerFactory trxControllerFactory;

	/**
	 * @return the insuranceCoverageBusManager
	 */
	public IInsuranceCoverageBusManager getInsuranceCoverageBusManager() {
		return insuranceCoverageBusManager;
	}


	/**
	 * @param insuranceCoverageBusManager the insuranceCoverageBusManager to set
	 */
	public void setInsuranceCoverageBusManager(
			IInsuranceCoverageBusManager insuranceCoverageBusManager) {
		this.insuranceCoverageBusManager = insuranceCoverageBusManager;
	}


	/**
	 * @return the stagingInsuranceCoverageBusManager
	 */
	public IInsuranceCoverageBusManager getStagingInsuranceCoverageBusManager() {
		return stagingInsuranceCoverageBusManager;
	}


	/**
	 * @param stagingInsuranceCoverageBusManager the stagingInsuranceCoverageBusManager to set
	 */
	public void setStagingInsuranceCoverageBusManager(
			IInsuranceCoverageBusManager stagingInsuranceCoverageBusManager) {
		this.stagingInsuranceCoverageBusManager = stagingInsuranceCoverageBusManager;
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
	public SearchResult getInsuranceCoverageList(String icCode,String companyName) throws InsuranceCoverageException{
		return (SearchResult)getInsuranceCoverageBusManager().getInsuranceCoverageList(icCode,companyName);
	}
	
	/**
	 * @return the SearchResult
	 */
	public SearchResult getInsuranceCoverageDtlsList(long id) throws InsuranceCoverageDtlsException {
		return (SearchResult)getInsuranceCoverageBusManager().getInsuranceCoverageDtlsList(id);
	}
	
	public IInsuranceCoverage getInsuranceCoverageById(long id) throws InsuranceCoverageException,TrxParameterException,TransactionException 
    {
	 if(id!=0){
		 return getInsuranceCoverageBusManager().getInsuranceCoverageById(id);
	 }else{
		 throw new OtherBankException("ERROR-- Key for InsuranceCoverage is null.");
	 }
	 
    }
	
	/**
	 * @return the Insurance Coverage details
	 * @throws ConcurrentUpdateException 
	 * @throws TransactionException 
	 * @throws TrxParameterException 
	 */
	 public IInsuranceCoverage updateInsuranceCoverage(IInsuranceCoverage insuranceCoverage) throws InsuranceCoverageException, TrxParameterException, TransactionException,ConcurrentUpdateException {
		if(insuranceCoverage!=null){
			return (IInsuranceCoverage)getInsuranceCoverageBusManager().updateInsuranceCoverage(insuranceCoverage);
		}else{
			 throw new InsuranceCoverageException("Insurance Coverage Object is null.");
		 }	
	}
    
	/**
	 * @return the InsuranceCoverage details
	 */
	public IInsuranceCoverage deleteInsuranceCoverage(IInsuranceCoverage insuranceCoverage) throws InsuranceCoverageException , TrxParameterException, TransactionException{
		if(insuranceCoverage!=null){
			return (IInsuranceCoverage)getInsuranceCoverageBusManager().deleteInsuranceCoverage(insuranceCoverage);
		}else{
			 throw new InsuranceCoverageException("Insurance Coverage Object is null.");
		}		
	}
	
	
	/**
	 * @return the InsuranceCoverage details
	 */
	public IInsuranceCoverage createInsuranceCoverage(IInsuranceCoverage insuranceCoverage) throws InsuranceCoverageException {
		return (IInsuranceCoverage)getInsuranceCoverageBusManager().createInsuranceCoverage(insuranceCoverage);
	}
	
	/**
	  * @return InsuranceCoverageTrx Value
	  * @param InsuranceCoverage Object 
	  * This method fetches the Proper trx value
	  * according to the Object passed as parameter.
	  * 
	  */
	public IInsuranceCoverageTrxValue getInsuranceCoverageTrxValue(long aInsuranceCoverage) throws InsuranceCoverageException,TrxParameterException,TransactionException {
       if (aInsuranceCoverage == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
           throw new InsuranceCoverageException("Invalid InsuranceCoverageId");
       }
       IInsuranceCoverageTrxValue trxValue = new OBInsuranceCoverageTrxValue();
       trxValue.setReferenceID(String.valueOf(aInsuranceCoverage));
       trxValue.setTransactionType(ICMSConstant.INSTANCE_INSURANCE_COVERAGE);
       OBCMSTrxParameter param = new OBCMSTrxParameter();
       param.setAction(ICMSConstant.ACTION_READ_INSURANCE_COVERAGE);
       return operate(trxValue, param);
   }
	
	/**
	  * 
	  * @param anIInsuranceCoverageTrxValue
	  * @param anOBCMSTrxParameter
	  * @return IInsuranceCoverageTrxValue
	  * @throws InsuranceCoverageException
	  * @throws TrxParameterException
	  * @throws TransactionException
	  */
	 
	 private IInsuranceCoverageTrxValue operate(IInsuranceCoverageTrxValue anIInsuranceCoverageTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws InsuranceCoverageException,TrxParameterException,TransactionException {
	        ICMSTrxResult result = operateForResult(anIInsuranceCoverageTrxValue, anOBCMSTrxParameter);
	        return (IInsuranceCoverageTrxValue) result.getTrxValue();
	    }
	 
	 /**
	  * 
	  * @param anICMSTrxValue
	  * @param anOBCMSTrxParameter
	  * @return ICMSTrxResult
	  * @throws InsuranceCoverageException
	  * @throws TrxParameterException
	  * @throws TransactionException
	  * 
	  * 
	  * This method selects the Controller for 
	  * the Operation to be performed.
	  */
	 
	 protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
	 throws InsuranceCoverageException,TrxParameterException,TransactionException {
		 try {
			 ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			 Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			 ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			 return (ICMSTrxResult) result;
		 }

		 catch (InsuranceCoverageException ex) {
			 throw new InsuranceCoverageException(ex.toString());
		 }
	 }
	 
	 /**
	  * @return updated Insurance Coverage Trx value Object
	  * @param Trx object, Insurance Coverage Trx object,Insurance Coverage object to be updated
	  * 
	  * The updated Insurance Coverage object in stored in Staging Table of Insurance Coverage
	  */
	 
	 public IInsuranceCoverageTrxValue makerUpdateInsuranceCoverage(ITrxContext anITrxContext, IInsuranceCoverageTrxValue anICCInsuranceCoverageTrxValue, IInsuranceCoverage anICCInsuranceCoverage) throws InsuranceCoverageException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new InsuranceCoverageException("The ITrxContext is null!!!");
	        }
	        if (anICCInsuranceCoverage == null) {
	            throw new InsuranceCoverageException("The ICCInsuranceCoverage to be updated is null !!!");
	        }
	        IInsuranceCoverageTrxValue trxValue = formulateTrxValue(anITrxContext, anICCInsuranceCoverageTrxValue, anICCInsuranceCoverage);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_INSURANCE_COVERAGE);
	        return operate(trxValue, param);
	    }
	 
	 
	 /**
	  * @return updated Insurance Coverage Trx value Object
	  * @param Trx object, Insurance Coverage Trx object,Insurance Coverage object to be updated
	  * 
	  * The updated Insurance Coverage object in stored in Staging Table of Insurance Coverage
	  */
	 
	 public IInsuranceCoverageTrxValue makerDeleteInsuranceCoverage(ITrxContext anITrxContext, IInsuranceCoverageTrxValue anICCInsuranceCoverageTrxValue, IInsuranceCoverage anICCInsuranceCoverage) 
	 	throws InsuranceCoverageException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new InsuranceCoverageException("The ITrxContext is null!!!");
	        }
	        if (anICCInsuranceCoverage == null) {
	            throw new InsuranceCoverageException("The ICCInsuranceCoverage to be updated is null !!!");
	        }
	        IInsuranceCoverageTrxValue trxValue = formulateTrxValue(anITrxContext, anICCInsuranceCoverageTrxValue, anICCInsuranceCoverage);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_INSURANCE_COVERAGE);
	        return operate(trxValue, param);
	    }
	 
	 /**
	  * @return updated Insurance Coverage Trx value Object
	  * @param Trx object, Insurance Coverage Trx object,Insurance Coverage object to be updated
	  * After once rejected by Checker, if maker attempts to update the same record 
	  * its done by this method. 
	  * The updated Insurance Coverage object in stored in Staging Table of Insurance Coverage
	  */
	 public IInsuranceCoverageTrxValue makerEditRejectedInsuranceCoverage(ITrxContext anITrxContext, IInsuranceCoverageTrxValue anIInsuranceCoverageTrxValue, IInsuranceCoverage anIInsuranceCoverage) throws InsuranceCoverageException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new InsuranceCoverageException("The ITrxContext is null!!!");
	        }
	        if (anIInsuranceCoverageTrxValue == null) {
	            throw new InsuranceCoverageException("The IInsuranceCoverageTrxValue to be updated is null!!!");
	        }
	        if (anIInsuranceCoverage == null) {
	            throw new InsuranceCoverageException("The IInsuranceCoverage to be updated is null !!!");
	        }
	        anIInsuranceCoverageTrxValue = formulateTrxValue(anITrxContext, anIInsuranceCoverageTrxValue, anIInsuranceCoverage);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_INSURANCE_COVERAGE);
	        return operate(anIInsuranceCoverageTrxValue, param);
	    }
	 /**
	  * 
	  * @param anITrxContext
	  * @param anICMSTrxValue
	  * @param anIInsuranceCoverage
	  * @return IInsuranceCoverageTrxValue 
	  * 
	  * 
	  */
	 
	 private IInsuranceCoverageTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IInsuranceCoverage anIInsuranceCoverage) {
	        IInsuranceCoverageTrxValue ccInsuranceCoverageTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccInsuranceCoverageTrxValue = new OBInsuranceCoverageTrxValue(anICMSTrxValue);
	        } else {
	            ccInsuranceCoverageTrxValue = new OBInsuranceCoverageTrxValue();
	        }
	        ccInsuranceCoverageTrxValue = formulateTrxValue(anITrxContext, (IInsuranceCoverageTrxValue) ccInsuranceCoverageTrxValue);
	        ccInsuranceCoverageTrxValue.setStagingInsuranceCoverage(anIInsuranceCoverage);
	        return ccInsuranceCoverageTrxValue;
	    }
	 /**
	  * 
	  * @param anITrxContext
	  * @param anIInsuranceCoverageTrxValue
	  * @return IInsuranceCoverageTrxValue
	  */
	 private IInsuranceCoverageTrxValue formulateTrxValue(ITrxContext anITrxContext, IInsuranceCoverageTrxValue anIInsuranceCoverageTrxValue) {
	        anIInsuranceCoverageTrxValue.setTrxContext(anITrxContext);
	        anIInsuranceCoverageTrxValue.setTransactionType(ICMSConstant.INSTANCE_INSURANCE_COVERAGE);
	        return anIInsuranceCoverageTrxValue;
	    }

	 /**
		  * @return InsuranceCoverageTrx Value
		  * @param InsuranceCoverage Object 
		  * This method fetches the Proper trx value
		  * according to the Transaction Id passed as parameter.
		  * 
		  */
	  public IInsuranceCoverageTrxValue getInsuranceCoverageByTrxID(String trxID) throws InsuranceCoverageException,TransactionException,CommandProcessingException{
		  IInsuranceCoverageTrxValue trxValue = new OBInsuranceCoverageTrxValue();
	        trxValue.setTransactionID(String.valueOf(trxID));
	        trxValue.setTransactionType(ICMSConstant.INSTANCE_INSURANCE_COVERAGE);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_READ_INSURANCE_COVERAGE_ID);
	        return operate(trxValue, param);
	    }
	  /**
	   * @return Insurance Coverage Trx Value
	   * @param Trx object, Insurance Coverage Trx object,Insurance Coverage object
	   * 
	   * This method Approves the Object passed by Maker
	   */
	  
	  public IInsuranceCoverageTrxValue checkerApproveInsuranceCoverage(ITrxContext anITrxContext, IInsuranceCoverageTrxValue anIInsuranceCoverageTrxValue) throws InsuranceCoverageException,TransactionException {
	        if (anITrxContext == null) {
	            throw new InsuranceCoverageException("The ITrxContext is null!!!");
	        }
	        if (anIInsuranceCoverageTrxValue == null) {
	            throw new InsuranceCoverageException
	                    ("The IInsuranceCoverageTrxValue to be updated is null!!!");
	        }
	        anIInsuranceCoverageTrxValue = formulateTrxValue(anITrxContext, anIInsuranceCoverageTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_INSURANCE_COVERAGE);
	        return operate(anIInsuranceCoverageTrxValue, param);
	    }
	  /**
	   * @return Insurance Coverage Trx Value
	   * @param Trx object, Insurance Coverage Trx object,Insurance Coverage object
	   * 
	   * This method Rejects the Object passed by Maker
	   */
	  public IInsuranceCoverageTrxValue checkerRejectInsuranceCoverage(ITrxContext anITrxContext, IInsuranceCoverageTrxValue anIInsuranceCoverageTrxValue) throws InsuranceCoverageException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new InsuranceCoverageException("The ITrxContext is null!!!");
	        }
	        if (anIInsuranceCoverageTrxValue == null) {
	            throw new InsuranceCoverageException("The IInsuranceCoverageTrxValue to be updated is null!!!");
	        }
	        anIInsuranceCoverageTrxValue = formulateTrxValue(anITrxContext, anIInsuranceCoverageTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_INSURANCE_COVERAGE);
	        return operate(anIInsuranceCoverageTrxValue, param);
	    }
	  /**
	   * @return Insurance Coverage Trx Value
	   * @param Trx object, Insurance Coverage Trx object,Insurance Coverage object
	   * 
	   * This method Close the Object rejected by Checker
	   */
	  public IInsuranceCoverageTrxValue makerCloseRejectedInsuranceCoverage(ITrxContext anITrxContext, IInsuranceCoverageTrxValue anIInsuranceCoverageTrxValue) throws InsuranceCoverageException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new InsuranceCoverageException("The ITrxContext is null!!!");
	        }
	        if (anIInsuranceCoverageTrxValue == null) {
	            throw new InsuranceCoverageException("The IInsuranceCoverageTrxValue to be updated is null!!!");
	        }
	        anIInsuranceCoverageTrxValue = formulateTrxValue(anITrxContext, anIInsuranceCoverageTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_INSURANCE_COVERAGE);
	        return operate(anIInsuranceCoverageTrxValue, param);
	    }
	  
	  /**
		  * @return updated Insurance Coverage Trx value Object
		  * @param Trx object, Insurance Coverage Trx object,Insurance Coverage object to be updated
		  * 
		  * The updated Insurance Coverage object in stored in Staging Table of Insurance Coverage
		  */
		 
		 public IInsuranceCoverageTrxValue makerCreateInsuranceCoverage(ITrxContext anITrxContext, IInsuranceCoverageTrxValue anICCInsuranceCoverageTrxValue, IInsuranceCoverage anICCInsuranceCoverage) throws InsuranceCoverageException,TrxParameterException,TransactionException {
		        if (anITrxContext == null) {
		            throw new InsuranceCoverageException("The ITrxContext is null!!!");
		        }
		        if (anICCInsuranceCoverage == null) {
		            throw new InsuranceCoverageException("The IInsuranceCoverage to be created is null !!!");
		        }
		        IInsuranceCoverageTrxValue trxValue = formulateTrxValue(anITrxContext, anICCInsuranceCoverageTrxValue, anICCInsuranceCoverage);
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_INSURANCE_COVERAGE);
		        return operate(trxValue, param);
		    }
		 
		 public boolean isICCodeUnique(String rmCode){
			 return getInsuranceCoverageBusManager().isICCodeUnique(rmCode);
		 }
		 
		 /**
		   * @return Relationship Manager Trx Value
		   * @param Trx object, Relationship Manager Trx object,Relationship Manager object
		   * 
		   * This method Close the Object rejected by Checker
		   */
		  public IInsuranceCoverageTrxValue makerCloseDraftInsuranceCoverage(ITrxContext anITrxContext, IInsuranceCoverageTrxValue anIInsuranceCoverageTrxValue) throws InsuranceCoverageException,TrxParameterException,TransactionException {
		        if (anITrxContext == null) {
		            throw new InsuranceCoverageException("The ITrxContext is null!!!");
		        }
		        if (anIInsuranceCoverageTrxValue == null) {
		            throw new InsuranceCoverageException("The IInsuranceCoverageTrxValue to be updated is null!!!");
		        }
		        anIInsuranceCoverageTrxValue = formulateTrxValue(anITrxContext, anIInsuranceCoverageTrxValue);
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_INSURANCE_COVERAGE);
		        return operate(anIInsuranceCoverageTrxValue, param);
		    }
		  
			 
			 /**
			  * @return updated Relationship Manager Trx value Object
			  * @param Trx object, Relationship Manager Trx object,Relationship Manager object to be updated
			  * 
			  * The updated Relationship Manager object in stored in Staging Table of Relationship Manager
			  */
			 
			 public IInsuranceCoverageTrxValue makerSaveInsuranceCoverage(ITrxContext anITrxContext, IInsuranceCoverageTrxValue anICCInsuranceCoverageTrxValue, IInsuranceCoverage anICCInsuranceCoverage) throws InsuranceCoverageException,TrxParameterException,TransactionException {
			        if (anITrxContext == null) {
			            throw new InsuranceCoverageException("The ITrxContext is null!!!");
			        }
			        if (anICCInsuranceCoverage == null) {
			            throw new InsuranceCoverageException("The IInsuranceCoverage to be created is null !!!");
			        }
			        IInsuranceCoverageTrxValue trxValue = formulateTrxValue(anITrxContext, anICCInsuranceCoverageTrxValue, anICCInsuranceCoverage);
			        OBCMSTrxParameter param = new OBCMSTrxParameter();
			        trxValue.setFromState(ICMSConstant.STATE_PENDING_PERFECTION);
			        trxValue.setStatus(ICMSConstant.STATE_PENDING_PERFECTION);
			        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_CREATE_INSURANCE_COVERAGE);
			        return operate(trxValue, param);
			    }
			 
			 /**
			  * @return updated Relationship Manager Trx value Object
			  * @param Trx object, Relationship Manager Trx object,Relationship Manager object to be updated
			  * 
			  * The updated Relationship Manager object in stored in Staging Table of Relationship Manager
			  */
			 
			 public IInsuranceCoverageTrxValue makerUpdateCreateInsuranceCoverage(ITrxContext anITrxContext, IInsuranceCoverageTrxValue anICCInsuranceCoverageTrxValue, IInsuranceCoverage anICCInsuranceCoverage) throws InsuranceCoverageException,TrxParameterException,TransactionException {
			        if (anITrxContext == null) {
			            throw new InsuranceCoverageException("The ITrxContext is null!!!");
			        }
			        if (anICCInsuranceCoverage == null) {
			            throw new InsuranceCoverageException("The ICCInsuranceCoverage to be updated is null !!!");
			        }
			        IInsuranceCoverageTrxValue trxValue = formulateTrxValue(anITrxContext, anICCInsuranceCoverageTrxValue, anICCInsuranceCoverage);
			        OBCMSTrxParameter param = new OBCMSTrxParameter();
			        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_INSURANCE_COVERAGE);
			        return operate(trxValue, param);
			    }
			 
			 /**
			  * @return updated Relationship Manager Trx value Object
			  * @param Trx object, Relationship Manager Trx object,Relationship Manager object to be updated
			  * 
			  * The updated Relationship Manager object in stored in Staging Table of Relationship Manager
			  */
			 
			 public IInsuranceCoverageTrxValue makerUpdateSaveInsuranceCoverage(ITrxContext anITrxContext, IInsuranceCoverageTrxValue anICCInsuranceCoverageTrxValue, IInsuranceCoverage anICCInsuranceCoverage) throws InsuranceCoverageException,TrxParameterException,TransactionException {
			        if (anITrxContext == null) {
			            throw new InsuranceCoverageException("The ITrxContext is null!!!");
			        }
			        if (anICCInsuranceCoverage == null) {
			            throw new InsuranceCoverageException("The ICCInsuranceCoverage to be updated is null !!!");
			        }
			        IInsuranceCoverageTrxValue trxValue = formulateTrxValue(anITrxContext, anICCInsuranceCoverageTrxValue, anICCInsuranceCoverage);
			        OBCMSTrxParameter param = new OBCMSTrxParameter();
			        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_SAVE_INSURANCE_COVERAGE);
			        return operate(trxValue, param);
			    }
			 
			 
			 //***************** Method for File Upload *********************
			 
			 private IInsuranceCoverageTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, OBFileMapperID obFileMapperID) {
				 IInsuranceCoverageTrxValue ccInsuranceCoverageTrxValue = null;
			        if (anICMSTrxValue != null) {
			            ccInsuranceCoverageTrxValue = new OBInsuranceCoverageTrxValue(anICMSTrxValue);
			        } else {
			            ccInsuranceCoverageTrxValue = new OBInsuranceCoverageTrxValue();
			        }
			        ccInsuranceCoverageTrxValue = formulateTrxValueID(anITrxContext, (IInsuranceCoverageTrxValue) ccInsuranceCoverageTrxValue);
			        ccInsuranceCoverageTrxValue.setStagingFileMapperID(obFileMapperID);
			        return ccInsuranceCoverageTrxValue;
			    }
			 
			 private IInsuranceCoverageTrxValue formulateTrxValueID(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IFileMapperId fileId) {
				 IInsuranceCoverageTrxValue ccInsuranceCoverageTrxValue = null;
			        if (anICMSTrxValue != null) {
			            ccInsuranceCoverageTrxValue = new OBInsuranceCoverageTrxValue(anICMSTrxValue);
			        } else {
			            ccInsuranceCoverageTrxValue = new OBInsuranceCoverageTrxValue();
			        }
			        ccInsuranceCoverageTrxValue = formulateTrxValueID(anITrxContext, (IInsuranceCoverageTrxValue) ccInsuranceCoverageTrxValue);
			        ccInsuranceCoverageTrxValue.setStagingFileMapperID(fileId);
			        return ccInsuranceCoverageTrxValue;
			    }
			 private IInsuranceCoverageTrxValue formulateTrxValueID(ITrxContext anITrxContext, IInsuranceCoverageTrxValue anIInsuranceCoverageTrxValue) {
			        anIInsuranceCoverageTrxValue.setTrxContext(anITrxContext);
			        anIInsuranceCoverageTrxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_INSURANCE_COVERAGE);
			        return anIInsuranceCoverageTrxValue;
			 }
			 /**
				 * @return Maker insert a fileID to generate a transation.
				 */
			 public IInsuranceCoverageTrxValue makerInsertMapperInsuranceCoverage(
						ITrxContext anITrxContext, OBFileMapperID obFileMapperID)
						throws InsuranceCoverageException, TrxParameterException,
						TransactionException {
					if (anITrxContext == null) {
			            throw new InsuranceCoverageException("The ITrxContext is null!!!");
			        }
			        if (obFileMapperID == null) {
			            throw new InsuranceCoverageException("The OBFileMapperID to be updated is null !!!");
			        }

			        IInsuranceCoverageTrxValue trxValue = formulateTrxValue(anITrxContext, null, obFileMapperID) ;
			        trxValue.setFromState("PENDING_INSERT");
			        OBCMSTrxParameter param = new OBCMSTrxParameter();
			        param.setAction(ICMSConstant.ACTION_MAKER_FILE_INSERT);
			        return operate(trxValue, param);
				} 
			 
			 /**
				 * @return Maker check if previous upload is pending.
				 */
			 public boolean isPrevFileUploadPending() throws InsuranceCoverageException, TrxParameterException,
				TransactionException  {
				 
					try {
						return getInsuranceCoverageBusManager().isPrevFileUploadPending();
					} catch (Exception e) {
						e.printStackTrace();
						throw new InsuranceCoverageException("ERROR-- Due to null InsuranceCoverage object cannot update.");
					}
				}
			 
			 /**
				 * @return Maker insert uploaded files in Staging table.
				 */
			 
			 public int insertInsuranceCoverage(IFileMapperMaster fileMapperMaster,String userName, ArrayList resultlList) throws InsuranceCoverageException, TrxParameterException,
				TransactionException  {
				 
					try {
						return getInsuranceCoverageBusManager().insertInsuranceCoverage(fileMapperMaster, userName, resultlList);
					} catch (Exception e) {
						
						e.printStackTrace();
						throw new InsuranceCoverageException("ERROR-- Due to null InsuranceCoverage object cannot update.");
					}
				}
			 
			 /**
				 * @return create record with TransID.
				 */
			 
			 public IInsuranceCoverageTrxValue getInsertFileByTrxID(String trxID)
				throws InsuranceCoverageException, TransactionException,
				CommandProcessingException {
				 	IInsuranceCoverageTrxValue trxValue = new OBInsuranceCoverageTrxValue();
				 	trxValue.setTransactionID(String.valueOf(trxID));
				 	trxValue.setTransactionType(ICMSConstant.INSTANCE_INSERT_INSURANCE_COVERAGE);
				 	OBCMSTrxParameter param = new OBCMSTrxParameter();
				 	param.setAction(ICMSConstant.ACTION_READ_FILE_INSERT);
				 return operate(trxValue, param);
		}
			 
			 /**
				 * @return Pagination for uploaded files in InsuranceCoverage.
				 */
			 public List getAllStage(String searchBy, String login)throws InsuranceCoverageException,TrxParameterException,TransactionException {
				if(searchBy!=null){
			
				 return getInsuranceCoverageBusManager().getAllStageInsuranceCoverage( searchBy, login);
				}else{
					throw new InsuranceCoverageException("ERROR- Search criteria is null.");
				}
			  }
			
			 /**
				 * @return Checker approval for uploaded files.
				 */
			 
			 public IInsuranceCoverageTrxValue checkerApproveInsertInsuranceCoverage(
			 		ITrxContext anITrxContext,
			 		IInsuranceCoverageTrxValue anIInsuranceCoverageTrxValue)
			 		throws InsuranceCoverageException, TrxParameterException,
			 		TransactionException {
			 	if (anITrxContext == null) {
			        throw new InsuranceCoverageException("The ITrxContext is null!!!");
			    }
			    if (anIInsuranceCoverageTrxValue == null) {
			        throw new InsuranceCoverageException
			                ("The IInsuranceCoverageTrxValue to be updated is null!!!");
			    }
			    anIInsuranceCoverageTrxValue = formulateTrxValueID(anITrxContext, anIInsuranceCoverageTrxValue);
			    OBCMSTrxParameter param = new OBCMSTrxParameter();
			    param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER);
			    return operate(anIInsuranceCoverageTrxValue, param);
			 }
			 
			 /**
				 * @return list of files uploaded in staging table of InsuranceCoverage.
				 */
			 public List getFileMasterList(String searchBy)throws InsuranceCoverageException,TrxParameterException,TransactionException {
				if(searchBy!=null){
			
				 return getInsuranceCoverageBusManager().getFileMasterList( searchBy);
				}else{
					throw new InsuranceCoverageException("ERROR- Search criteria is null.");
				}
			  }
			 
			 /**
				 * @return Maker insert upload files.
				 */
			 public IInsuranceCoverage insertActualInsuranceCoverage(String sysId) throws InsuranceCoverageException,TrxParameterException,TransactionException

			 {
			  if(sysId != null){
			 	 try {
			 		 return getInsuranceCoverageBusManager().insertActualInsuranceCoverage(sysId);
			 	 } catch (Exception e) {		 		
			 		 e.printStackTrace();
			 		 throw new InsuranceCoverageException("ERROR- Transaction for the Id is invalid.");
			 	 }
			  }else{
			 	 throw new InsuranceCoverageException("ERROR- Id for retrival is null.");
			  }
			 }
			 
			 /**
				 * @return Checker create file master in InsuranceCoverage.
				 */
			 
			 public IInsuranceCoverageTrxValue checkerCreateInsuranceCoverage(ITrxContext anITrxContext, IInsuranceCoverage anICCInsuranceCoverage, String refStage) throws InsuranceCoverageException,TrxParameterException,
			 TransactionException {
			     if (anITrxContext == null) {
			         throw new InsuranceCoverageException("The ITrxContext is null!!!");
			     }
			     if (anICCInsuranceCoverage == null) {
			         throw new InsuranceCoverageException("The ICCInsuranceCoverage to be updated is null !!!");
			     }

			     IInsuranceCoverageTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCInsuranceCoverage);
			     trxValue.setFromState("PENDING_CREATE");
			     trxValue.setReferenceID(String.valueOf(anICCInsuranceCoverage.getId()));
			     trxValue.setStagingReferenceID(refStage);
			     OBCMSTrxParameter param = new OBCMSTrxParameter();
			     param.setAction(ICMSConstant.ACTION_CHECKER_FILE_MASTER);
			     return operate(trxValue, param);
			 }
			 
			 /**
				 * @return Checker Reject for upload files InsuranceCoverage.
				 */

			 public IInsuranceCoverageTrxValue checkerRejectInsertInsuranceCoverage(
			 	ITrxContext anITrxContext,
			 	IInsuranceCoverageTrxValue anIInsuranceCoverageTrxValue)
			 	throws InsuranceCoverageException, TrxParameterException,
			 	TransactionException {
			 	if (anITrxContext == null) {
			 	  throw new InsuranceCoverageException("The ITrxContext is null!!!");
			 	}
			 	if (anIInsuranceCoverageTrxValue == null) {
			 	  throw new InsuranceCoverageException
			 	          ("The IInsuranceCoverageTrxValue to be updated is null!!!");
			 	}
			 		anIInsuranceCoverageTrxValue = formulateTrxValueID(anITrxContext, anIInsuranceCoverageTrxValue);
			 		OBCMSTrxParameter param = new OBCMSTrxParameter();
			 		param.setAction(ICMSConstant.ACTION_CHECKER_INSERT_REJECT_MASTER);
			 	return operate(anIInsuranceCoverageTrxValue, param);
			 }
			 
			 /**
				 * @return Maker Close rejected files InsuranceCoverage.
				 */

			 public IInsuranceCoverageTrxValue makerInsertCloseRejectedInsuranceCoverage(
			 		ITrxContext anITrxContext,
			 		IInsuranceCoverageTrxValue anIInsuranceCoverageTrxValue)
			 		throws InsuranceCoverageException, TrxParameterException,
			 		TransactionException {
			 	if (anITrxContext == null) {
			         throw new InsuranceCoverageException("The ITrxContext is null!!!");
			     }
			     if (anIInsuranceCoverageTrxValue == null) {
			         throw new InsuranceCoverageException("The IInsuranceCoverageTrxValue to be updated is null!!!");
			     }
			     anIInsuranceCoverageTrxValue = formulateTrxValue(anITrxContext, anIInsuranceCoverageTrxValue);
			     OBCMSTrxParameter param = new OBCMSTrxParameter();
			     param.setAction(ICMSConstant.ACTION_MAKER_INSERT_CLOSE_REJECTED_MASTER);
			     return operate(anIInsuranceCoverageTrxValue, param);
			 }


			public boolean isCompanyNameUnique(String companyName) {
				return getInsuranceCoverageBusManager().isCompanyNameUnique(companyName);
			}

			public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,TransactionException {
				getInsuranceCoverageBusManager().deleteTransaction(obFileMapperMaster);					
			}
		 
}
