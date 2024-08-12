/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.trx.shareholder;

import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import com.integrosys.cms.app.custrelationship.bus.ICustShareholder;
import com.integrosys.cms.app.custrelationship.bus.SBCustRelationshipBusManager;
import com.integrosys.cms.app.custrelationship.bus.CustRelationshipBusManagerFactory;


/**
 * The operation is to read Customer Shareholder by transaction ID.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class ReadCustShareholderByTrxIDOperation extends CMSTrxOperation implements ITrxReadOperation
{
	/**
  	 * Default Constructor
 	 */
	public ReadCustShareholderByTrxIDOperation() {
		super();
	}

	/**
 	 * Get the operation name of the current operation.
     *
 	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_SHAREHOLDER_BY_TRXID;
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

			OBCustShareholderTrxValue trxVal = new OBCustShareholderTrxValue (cmsTrxValue);

			String stagingRef = cmsTrxValue.getStagingReferenceID();
			String actualRef = cmsTrxValue.getReferenceID();
			long parentSubProfileID = ICMSConstant.LONG_INVALID_VALUE;
			
			DefaultLogger.debug (this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
				SBCustRelationshipBusManager mgr = CustRelationshipBusManagerFactory.getStagingCustRelationshipBusManager();			
                ICustShareholder[] shareholderList = mgr.getCustShareholderByGroupID (Long.parseLong (stagingRef));
                trxVal.setStagingCustShareholder (shareholderList);
				if( shareholderList != null && shareholderList.length > 0 )
                {
                	parentSubProfileID = shareholderList[0].getParentSubProfileID();
				}
            }

			if (actualRef != null) {
				SBCustRelationshipBusManager mgr = CustRelationshipBusManagerFactory.getActualCustRelationshipBusManager();			
                ICustShareholder[] shareholderList = mgr.getCustShareholderByGroupID (Long.parseLong (actualRef));
                trxVal.setCustShareholder (shareholderList);
                if( shareholderList != null && shareholderList.length > 0 )
                {
                	parentSubProfileID = shareholderList[0].getParentSubProfileID();
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