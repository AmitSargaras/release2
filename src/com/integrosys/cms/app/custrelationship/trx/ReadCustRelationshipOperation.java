/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.trx;

import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import com.integrosys.cms.app.custrelationship.bus.ICustRelationship;
import com.integrosys.cms.app.custrelationship.bus.SBCustRelationshipBusManager;
import com.integrosys.cms.app.custrelationship.bus.CustRelationshipBusManagerFactory;

/**
 * Operation to read Customer Relationship.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class ReadCustRelationshipOperation extends CMSTrxOperation implements ITrxReadOperation
{
	/**
  	 * Default Constructor
 	 */
	public ReadCustRelationshipOperation() {
		super();
	}

    /**
	     * Get the operation name of the current operation.
	     *
	     * @return the operation name of the current operation
	     */
    public String getOperationName() {
        return ICMSConstant.ACTION_READ_CUST_RELNSHIP;
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

			ICustRelationship[] stageCustRelationship = null;
			ICustRelationship[] actualCustRelationship = null;

			ICMSTrxValue[] cmsTrxValueArray = getTrxManager().getTrxByParentTrxID (
                			cmsTrxValue.getTrxReferenceID(), ICMSConstant.INSTANCE_CUST_RELNSHIP);

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
			
			ICustRelationshipTrxValue trxVal = new OBCustRelationshipTrxValue (cmsTrxValue);

			String stagingRef = trxVal.getStagingReferenceID();
			String actualRef = trxVal.getReferenceID();
			long parentSubProfileID = ICMSConstant.LONG_INVALID_VALUE;

			DefaultLogger.debug (this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if(stagingRef!=null)
			{
				// get staging Customer Relationship
				SBCustRelationshipBusManager mgr = CustRelationshipBusManagerFactory.getStagingCustRelationshipBusManager();			
				stageCustRelationship = mgr.getCustRelationshipByGroupID (Long.parseLong (stagingRef));
				trxVal.setStagingCustRelationship (stageCustRelationship);
				if( stageCustRelationship != null && stageCustRelationship.length > 0 )
                {
                	parentSubProfileID = stageCustRelationship[0].getParentSubProfileID();
				}
			}
			if(actualRef!=null)
			{
				// get actual Customer Relationship
				SBCustRelationshipBusManager mgr = CustRelationshipBusManagerFactory.getActualCustRelationshipBusManager();			
				actualCustRelationship = mgr.getCustRelationshipByGroupID ( Long.parseLong (actualRef) );
				trxVal.setCustRelationship (actualCustRelationship);
				if( actualCustRelationship != null && actualCustRelationship.length > 0 )
                {
                	parentSubProfileID = actualCustRelationship[0].getParentSubProfileID();
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
