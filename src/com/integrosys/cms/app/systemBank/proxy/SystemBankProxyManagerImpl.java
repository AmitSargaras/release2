/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/proxy/DiaryItemProxyManagerImpl.java,v 1.6 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.systemBank.proxy;

// java

import java.util.List;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.bus.ISystemBankBusManager;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.systemBank.trx.ISystemBankTrxValue;
import com.integrosys.cms.app.systemBank.trx.OBSystemBankTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * @author $Author: Abhijit R $<br>
 * 
 * Proxy manager interface declares the methods used by commands
 */
public class SystemBankProxyManagerImpl implements ISystemBankProxyManager {

	
	
	private ISystemBankBusManager systemBankBusManager;
	
	private ISystemBankBusManager stagingSystemBankBusManager;

    private ITrxControllerFactory trxControllerFactory;

	public ISystemBankBusManager getSystemBankBusManager() {
		return systemBankBusManager;
	}

	public void setSystemBankBusManager(ISystemBankBusManager systemBankBusManager) {
		this.systemBankBusManager = systemBankBusManager;
	}

	public ISystemBankBusManager getStagingSystemBankBusManager() {
		return stagingSystemBankBusManager;
	}

	public void setStagingSystemBankBusManager(
			ISystemBankBusManager stagingSystemBankBusManager) {
		this.stagingSystemBankBusManager = stagingSystemBankBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}
	

	
	
	/**
	 * @return List of System Bank 
	 * 
	 * This method access the Database through jdbc and fetch data. 
	 */
	
	 public List getAllActual() {
		 return getSystemBankBusManager().getAllSystemBank();
	    }
	 
	 
	 /**
	  * @return Particular System Bank  according 
	  * to the id passed as parameter.  
	  * @param Bank Code 
	  */
	 
	 public ISystemBank getSystemBankById(long id) throws SystemBankException,TrxParameterException,TransactionException 
	    {
		 if(id!=0){
		 return getSystemBankBusManager().getSystemBankById(id);
		 }else{
			 throw new SystemBankException("ERROR-- Key for System Bank is null.");
		 }
		 
	    }
	 
	 
	 /**
	  * @return updated System Bank Object
	  * @param System Bank object to be updated
	  */
	 
	 public ISystemBank updateSystemBank(ISystemBank systemBank) throws SystemBankException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		 if(!(systemBank==null)){
		 return getSystemBankBusManager().updateSystemBank((ISystemBank) systemBank);
		 }else{
			 throw new SystemBankException("System Bank Object is null.");
		 }
		}
	 
	 /**
	  * @return updated System Bank Trx value Object
	  * @param Trx object, System Bank Trx object,System Bank object to be updated
	  * 
	  * The updated System Bank object in stored in Staging Table of System Bank
	  */
	 
	 public ISystemBankTrxValue makerUpdateSystemBank(ITrxContext anITrxContext, ISystemBankTrxValue anICCSystemBankTrxValue, ISystemBank anICCSystemBank) throws SystemBankException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new SystemBankException("The ITrxContext is null!!!");
	        }
	        if (anICCSystemBank == null) {
	            throw new SystemBankException("The ICCSystemBank to be updated is null !!!");
	        }
	        ISystemBankTrxValue trxValue = formulateTrxValue(anITrxContext, anICCSystemBankTrxValue, anICCSystemBank);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_SYSTEM_BANK);
	        return operate(trxValue, param);
	    }
	 
	 /**
	  * @return updated System Bank Trx value Object
	  * @param Trx object, System Bank Trx object,System Bank object to be updated
	  * After once rejected by Checker, if maker attempts to update the same record 
	  * its done by this method. 
	  * The updated System Bank object in stored in Staging Table of System Bank
	  */
	 public ISystemBankTrxValue makerEditRejectedSystemBank(ITrxContext anITrxContext, ISystemBankTrxValue anISystemBankTrxValue, ISystemBank anISystemBank) throws SystemBankException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new SystemBankException("The ITrxContext is null!!!");
	        }
	        if (anISystemBankTrxValue == null) {
	            throw new SystemBankException("The ISystemBankTrxValue to be updated is null!!!");
	        }
	        if (anISystemBank == null) {
	            throw new SystemBankException("The ISystemBank to be updated is null !!!");
	        }
	        anISystemBankTrxValue = formulateTrxValue(anITrxContext, anISystemBankTrxValue, anISystemBank);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_SYSTEM_BANK);
	        return operate(anISystemBankTrxValue, param);
	    }
	 /**
	  * 
	  * @param anITrxContext
	  * @param anICMSTrxValue
	  * @param anISystemBank
	  * @return ISystemBankTrxValue 
	  * 
	  * 
	  */
	 
	 private ISystemBankTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, ISystemBank anISystemBank) {
	        ISystemBankTrxValue ccSystemBankTrxValue = null;
	        if (anICMSTrxValue != null) {
	            ccSystemBankTrxValue = new OBSystemBankTrxValue(anICMSTrxValue);
	        } else {
	            ccSystemBankTrxValue = new OBSystemBankTrxValue();
	        }
	        ccSystemBankTrxValue = formulateTrxValue(anITrxContext, (ISystemBankTrxValue) ccSystemBankTrxValue);
	        ccSystemBankTrxValue.setStagingSystemBank(anISystemBank);
	        return ccSystemBankTrxValue;
	    }
	 /**
	  * 
	  * @param anITrxContext
	  * @param anISystemBankTrxValue
	  * @return ISystemBankTrxValue
	  */
	 private ISystemBankTrxValue formulateTrxValue(ITrxContext anITrxContext, ISystemBankTrxValue anISystemBankTrxValue) {
	        anISystemBankTrxValue.setTrxContext(anITrxContext);
	        anISystemBankTrxValue.setTransactionType(ICMSConstant.INSTANCE_SYSTEM_BANK);
	        return anISystemBankTrxValue;
	    }
	 /**
	  * 
	  * @param anISystemBankTrxValue
	  * @param anOBCMSTrxParameter
	  * @return ISystemBankTrxValue
	  * @throws SystemBankException
	  * @throws TrxParameterException
	  * @throws TransactionException
	  */
	 
	 private ISystemBankTrxValue operate(ISystemBankTrxValue anISystemBankTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws SystemBankException,TrxParameterException,TransactionException {
	        ICMSTrxResult result = operateForResult(anISystemBankTrxValue, anOBCMSTrxParameter);
	        return (ISystemBankTrxValue) result.getTrxValue();
	    }
	 /**
	  * 
	  * @param anICMSTrxValue
	  * @param anOBCMSTrxParameter
	  * @return ICMSTrxResult
	  * @throws SystemBankException
	  * @throws TrxParameterException
	  * @throws TransactionException
	  * 
	  * 
	  * This method selects the Controller for 
	  * the Operation to be performed.
	  */
	 
	 protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
	 throws SystemBankException,TrxParameterException,TransactionException {
		 try {
			 ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			 Validate.notNull(controller, "'controller' must not be null, check the controller factory");

			 ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			 return (ICMSTrxResult) result;
		 }

		 catch (SystemBankException ex) {
			 throw new SystemBankException(ex.toString());
		 }
	 }
	 /**
	  * @return SystemBankTrx Value
	  * @param SystemBank Object 
	  * This method fetches the Proper trx value
	  * according to the Object passed as parameter.
	  * 
	  */
		public ISystemBankTrxValue getSystemBankTrxValue(long aSystemBank) throws SystemBankException,TrxParameterException,TransactionException {
        if (aSystemBank == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
            throw new SystemBankException("Invalid SystemBankId");
        }
        ISystemBankTrxValue trxValue = new OBSystemBankTrxValue();
        trxValue.setReferenceID(String.valueOf(aSystemBank));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_SYSTEM_BANK);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_SYSTEM_BANK);
        return operate(trxValue, param);
    }
		 /**
		  * @return SystemBankTrx Value
		  * @param SystemBank Object 
		  * This method fetches the Proper trx value
		  * according to the Transaction Id passed as parameter.
		  * 
		  */
	  public ISystemBankTrxValue getSystemBankByTrxID(String trxID) throws SystemBankException,TransactionException{
		  ISystemBankTrxValue trxValue = new OBSystemBankTrxValue();
	        trxValue.setTransactionID(String.valueOf(trxID));
	        trxValue.setTransactionType(ICMSConstant.INSTANCE_SYSTEM_BANK);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_READ_SYSTEM_BANK_ID);
	        return operate(trxValue, param);
	    }
	  /**
	   * @return System Bank Trx Value
	   * @param Trx object, System Bank Trx object,System Bank object
	   * 
	   * This method Approves the Object passed by Maker
	   */
	  
	  public ISystemBankTrxValue checkerApproveSystemBank(ITrxContext anITrxContext, ISystemBankTrxValue anISystemBankTrxValue) throws SystemBankException,TransactionException {
	        if (anITrxContext == null) {
	            throw new SystemBankException("The ITrxContext is null!!!");
	        }
	        if (anISystemBankTrxValue == null) {
	            throw new SystemBankException
	                    ("The ISystemBankTrxValue to be updated is null!!!");
	        }
	        anISystemBankTrxValue = formulateTrxValue(anITrxContext, anISystemBankTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_SYSTEM_BANK);
	        return operate(anISystemBankTrxValue, param);
	    }
	  /**
	   * @return System Bank Trx Value
	   * @param Trx object, System Bank Trx object,System Bank object
	   * 
	   * This method Rejects the Object passed by Maker
	   */
	  public ISystemBankTrxValue checkerRejectSystemBank(ITrxContext anITrxContext, ISystemBankTrxValue anISystemBankTrxValue) throws SystemBankException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new SystemBankException("The ITrxContext is null!!!");
	        }
	        if (anISystemBankTrxValue == null) {
	            throw new SystemBankException("The ISystemBankTrxValue to be updated is null!!!");
	        }
	        anISystemBankTrxValue = formulateTrxValue(anITrxContext, anISystemBankTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_SYSTEM_BANK);
	        return operate(anISystemBankTrxValue, param);
	    }
	  /**
	   * @return System Bank Trx Value
	   * @param Trx object, System Bank Trx object,System Bank object
	   * 
	   * This method Close the Object rejected by Checker
	   */
	  public ISystemBankTrxValue makerCloseRejectedSystemBank(ITrxContext anITrxContext, ISystemBankTrxValue anISystemBankTrxValue) throws SystemBankException,TrxParameterException,TransactionException {
	        if (anITrxContext == null) {
	            throw new SystemBankException("The ITrxContext is null!!!");
	        }
	        if (anISystemBankTrxValue == null) {
	            throw new SystemBankException("The ISystemBankTrxValue to be updated is null!!!");
	        }
	        anISystemBankTrxValue = formulateTrxValue(anITrxContext, anISystemBankTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_SYSTEM_BANK);
	        return operate(anISystemBankTrxValue, param);
	    }
	  /**
		 * @return Maker Update System Bank 
		 */

		public ISystemBankTrxValue makerUpdateSaveUpdateSystemBank(
				ITrxContext anITrxContext,
				ISystemBankTrxValue anICCSystemBankTrxValue,
				ISystemBank anICCSystemBank)
				throws SystemBankException, TrxParameterException,
				TransactionException {
			 if (anITrxContext == null) {
		            throw new SystemBankException("The ITrxContext is null!!!");
		        }
		        if (anICCSystemBank == null) {
		            throw new SystemBankException("The ICCSystemBank to be updated is null !!!");
		        }
		        ISystemBankTrxValue trxValue = formulateTrxValue(anITrxContext, anICCSystemBankTrxValue, anICCSystemBank);
		        OBCMSTrxParameter param = new OBCMSTrxParameter();
		        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_UPDATE_SYSTEM_BANK);
		        return operate(trxValue, param);
		}
		
		/**
		 * @return Maker Close draft System Bank 
		 */
		
		public ISystemBankTrxValue makerCloseDraftSystemBank(
				ITrxContext anITrxContext,
				ISystemBankTrxValue anISystemBankTrxValue)
				throws SystemBankException, TrxParameterException,
				TransactionException {
			if (anITrxContext == null) {
	            throw new SystemBankException("The ITrxContext is null!!!");
	        }
	        if (anISystemBankTrxValue == null) {
	            throw new SystemBankException("The ISystemBankTrxValue to be updated is null!!!");
	        }
	        anISystemBankTrxValue = formulateTrxValue(anITrxContext, anISystemBankTrxValue);
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
	        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_SYSTEM_BANK);
	        return operate(anISystemBankTrxValue, param);
		}
	  

	
}
