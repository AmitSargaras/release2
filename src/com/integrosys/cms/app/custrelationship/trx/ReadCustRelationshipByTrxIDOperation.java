/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.trx;

import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import com.integrosys.cms.app.custrelationship.bus.ICustRelationship;
import com.integrosys.cms.app.custrelationship.bus.SBCustRelationshipBusManager;
import com.integrosys.cms.app.custrelationship.bus.CustRelationshipBusManagerFactory;


/**
 * The operation is to read Customer Relationship by transaction ID.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class ReadCustRelationshipByTrxIDOperation extends CMSTrxOperation implements ITrxReadOperation
{
	/**
  	 * Default Constructor
 	 */
	public ReadCustRelationshipByTrxIDOperation() {
		super();
	}

	/**
 	 * Get the operation name of the current operation.
     *
 	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_CUST_RELNSHIP_BY_TRXID;
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

			OBCustRelationshipTrxValue trxVal = new OBCustRelationshipTrxValue (cmsTrxValue);

			String stagingRef = cmsTrxValue.getStagingReferenceID();
			String actualRef = cmsTrxValue.getReferenceID();
			long parentSubProfileID = ICMSConstant.LONG_INVALID_VALUE;
			
			DefaultLogger.debug (this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
				SBCustRelationshipBusManager mgr = CustRelationshipBusManagerFactory.getStagingCustRelationshipBusManager();			
                ICustRelationship[] custRelnList = mgr.getCustRelationshipByGroupID (Long.parseLong (stagingRef));
                trxVal.setStagingCustRelationship (custRelnList);
				if( custRelnList != null && custRelnList.length > 0 )
                {
                	parentSubProfileID = custRelnList[0].getParentSubProfileID();
				}
            }

			if (actualRef != null) {
				SBCustRelationshipBusManager mgr = CustRelationshipBusManagerFactory.getActualCustRelationshipBusManager();			
                ICustRelationship[] custRelnList = mgr.getCustRelationshipByGroupID (Long.parseLong (actualRef));
                trxVal.setCustRelationship (custRelnList);
                if( custRelnList != null && custRelnList.length > 0 )
                {
                	parentSubProfileID = custRelnList[0].getParentSubProfileID();
				}
            }
            trxVal.setParentSubProfileID( parentSubProfileID );
            return trxVal;

        }
        catch (Exception e) {
            throw new TrxOperationException (e);
        }
    } 

}