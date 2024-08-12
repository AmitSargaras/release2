/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.trx.shareholder;

import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import com.integrosys.cms.app.custrelationship.bus.ICustShareholder;
import com.integrosys.cms.app.custrelationship.bus.SBCustRelationshipBusManager;
import com.integrosys.cms.app.custrelationship.bus.CustRelationshipBusManagerFactory;

/**
 * Operation to read Customer Shareholder.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class ReadCustShareholderOperation extends CMSTrxOperation implements ITrxReadOperation
{
	/**
  	 * Default Constructor
 	 */
	public ReadCustShareholderOperation() {
		super();
	}

    /**
	     * Get the operation name of the current operation.
	     *
	     * @return the operation name of the current operation
	     */
    public String getOperationName() {
        return ICMSConstant.ACTION_READ_SHAREHOLDER;
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
            ICMSTrxValue cmsTrxValue = super.getCMSTrxValue (val);

			ICustShareholder[] stageShareholder = null;
			ICustShareholder[] actualShareholder = null;

			ICMSTrxValue[] cmsTrxValueArray = getTrxManager().getTrxByParentTrxID (
                			cmsTrxValue.getTrxReferenceID(), ICMSConstant.INSTANCE_SHAREHOLDER );

			if( cmsTrxValueArray != null && cmsTrxValueArray.length > 0 ) {
				boolean found = false;
				for(int i=0; i<cmsTrxValueArray.length; i++) {
					cmsTrxValue = cmsTrxValueArray[i];
					
					if ( cmsTrxValue.getStatus() != null && 
						 !cmsTrxValue.getStatus().equals( ICMSConstant.STATE_CLOSED ) ) {
						found = true;
						break;
					}
				}
				if( !found ) {
					return null;
				}
			}
			else
			{
				return null;
			}
			
			ICustShareholderTrxValue trxVal = new OBCustShareholderTrxValue (cmsTrxValue);

			String stagingRef = trxVal.getStagingReferenceID();
			String actualRef = trxVal.getReferenceID();
			long parentSubProfileID = ICMSConstant.LONG_INVALID_VALUE;

			DefaultLogger.debug (this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if(stagingRef!=null)
			{
				// get staging Customer Shareholder
				SBCustRelationshipBusManager mgr = CustRelationshipBusManagerFactory.getStagingCustRelationshipBusManager();			
				stageShareholder = mgr.getCustShareholderByGroupID (Long.parseLong (stagingRef));
				trxVal.setStagingCustShareholder (stageShareholder);
				if( stageShareholder != null && stageShareholder.length > 0 )
                {
                	parentSubProfileID = stageShareholder[0].getParentSubProfileID();
				}
			}
			if(actualRef!=null)
			{
				// get actual Customer Shareholder
				SBCustRelationshipBusManager mgr = CustRelationshipBusManagerFactory.getActualCustRelationshipBusManager();			
				actualShareholder = mgr.getCustShareholderByGroupID ( Long.parseLong (actualRef) );
				trxVal.setCustShareholder (actualShareholder);
				if( actualShareholder != null && actualShareholder.length > 0 )
                {
                	parentSubProfileID = actualShareholder[0].getParentSubProfileID();
				}
			}
            trxVal.setParentSubProfileID( parentSubProfileID );

            return trxVal;
        }
        catch (Exception e) {
            throw new TrxOperationException(e);
        }
    }
	
	 
}
