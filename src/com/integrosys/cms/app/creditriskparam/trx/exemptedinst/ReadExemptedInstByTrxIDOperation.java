/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.exemptedinst;

import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.SBExemptedInstBusManager;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.ExemptedInstBusManagerFactory;


/**
 * The operation is to read Exempted Institution by transaction ID.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class ReadExemptedInstByTrxIDOperation extends CMSTrxOperation implements ITrxReadOperation
{
	/**
  	 * Default Constructor
 	 */
	public ReadExemptedInstByTrxIDOperation() {
		super();
	}

	/**
 	 * Get the operation name of the current operation.
     *
 	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_EXEMPT_INST_BY_TRXID;
	}

    /**
     * This method is used to read a transaction object.
     *
     * @param val transaction value required for retrieving transaction record
     * @return transaction value
     * @throws TransactionException on errors retrieving the transaction value
     */
    public ITrxValue getTransaction (ITrxValue val) throws TransactionException
    {
        try
        {
			ICMSTrxValue cmsTrxValue = getTrxManager().getTransaction(val.getTransactionID());

			OBExemptedInstTrxValue trxVal = new OBExemptedInstTrxValue (cmsTrxValue);

			String stagingRef = cmsTrxValue.getStagingReferenceID();
			String actualRef = cmsTrxValue.getReferenceID();
			
			DefaultLogger.debug (this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
				SBExemptedInstBusManager mgr = ExemptedInstBusManagerFactory.getStagingExemptedInstBusManager();			
                IExemptedInst[] exemptedInstList = mgr.getExemptedInstByGroupID (Long.parseLong (stagingRef));
                trxVal.setStagingExemptedInst (exemptedInstList);
				
            }

			if (actualRef != null) {
				SBExemptedInstBusManager mgr = ExemptedInstBusManagerFactory.getActualExemptedInstBusManager();			
                IExemptedInst[] exemptedInstList = mgr.getExemptedInstByGroupID (Long.parseLong (actualRef));
                trxVal.setExemptedInst (exemptedInstList);
                
            }
            return trxVal;

        }
        catch (Exception e) {
            throw new TrxOperationException (e);
        }
    } 

}