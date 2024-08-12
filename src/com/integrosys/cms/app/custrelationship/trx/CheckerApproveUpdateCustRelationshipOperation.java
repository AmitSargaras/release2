/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.trx;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.custrelationship.bus.ICustRelationship;

/**
 * This operation class is invoked by a checker to approve Customer Relationship updated by a maker.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class CheckerApproveUpdateCustRelationshipOperation extends AbstractCustRelationshipTrxOperation
{
    /**
     * Default constructor.
     */
    public CheckerApproveUpdateCustRelationshipOperation()
    {}

    /**
     * Returns the Operation Name
     *
     * @return String
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CUST_RELNSHIP;
    }

    /**
     * The following tasks are performed:
     *
     * 1. update actual Customer Relationship record
     * 2. update Transaction record
     *
     * @param value is of type ITrxValue
     * @return transaction result
     * @throws TrxOperationException on error updating the transaction
     */
    public ITrxResult performProcess (ITrxValue value) throws TrxOperationException
    {
        try {
	        ICustRelationshipTrxValue trxValue = super.getCustRelationshipTrxValue (value);

            ICustRelationship[] actual = trxValue.getCustRelationship();
			if( actual == null || actual.length == 0)
			{
	        	trxValue = super.createActualCustRelationship (trxValue);
	        }
	        else
	        {
	        	trxValue = super.updateActualCustRelationship (trxValue);
			}
			trxValue = super.updateTransaction (trxValue);

	        return super.prepareResult(trxValue);
	    }
	    catch (TrxOperationException e) {
	        throw e;
	    }
	    catch (Exception e) {
	        throw new TrxOperationException ("Exception caught!", e);
	    }
    }
}
