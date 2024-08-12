/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.exemptFacility;

import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.SBExemptFacilityBusManager;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.ExemptFacilityBusManagerFactory;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.IExemptFacilityGroup;


/**
 * The operation is to read Exempted Institution by transaction ID.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class ReadExemptFacilityByTrxIDOperation extends CMSTrxOperation implements ITrxReadOperation
{
	/**
  	 * Default Constructor
 	 */
	public ReadExemptFacilityByTrxIDOperation() {
		super();
	}

	/**
 	 * Get the operation name of the current operation.
     *
 	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_EXEMPT_FACILITY_GROUP_BY_TRXID;
	}

    /**
     * This method is used to read a transaction object.
     *
     * @param val transaction value required for retrieving transaction record
     * @return transaction value
     * @throws com.integrosys.base.businfra.transaction.TransactionException on errors retrieving the transaction value
     */
    public ITrxValue getTransaction (ITrxValue val) throws TransactionException
    {
        try
        {
			ICMSTrxValue cmsTrxValue = getTrxManager().getTransaction(val.getTransactionID());

			OBExemptFacilityGroupTrxValue trxVal = new OBExemptFacilityGroupTrxValue (cmsTrxValue);

			String stagingRef = cmsTrxValue.getStagingReferenceID();
			String actualRef = cmsTrxValue.getReferenceID();
			
			DefaultLogger.debug (this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
				SBExemptFacilityBusManager mgr = ExemptFacilityBusManagerFactory.getStagingFeedBusManager();
                IExemptFacilityGroup exemptFacilityGroup = mgr.getExemptFacilityGroupByGroupID (Long.parseLong (stagingRef));
                trxVal.setStagingExemptFacilityGroup(exemptFacilityGroup);
				
            }

			if (actualRef != null) {
				SBExemptFacilityBusManager mgr = ExemptFacilityBusManagerFactory.getActualFeedBusManager();
                IExemptFacilityGroup exemptFacilityGroup = mgr.getExemptFacilityGroupByGroupID (Long.parseLong (actualRef));
                trxVal.setExemptFacilityGroup (exemptFacilityGroup);
                
            }
            return trxVal;

        }
        catch (Exception e) {
            throw new TrxOperationException (e);
        }
    } 

}