/*
Copyright Integro Technologies Pte Ltd
$Header: $
*/
package com.integrosys.cms.app.creditriskparam.trx.entitylimit;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;

/**
 * This operation class is invoked by maker to create Entity Limit.
 *
 * @author   $Author: skchai $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class MakerCreateEntityLimitOperation extends AbstractEntityLimitTrxOperation {

	private static final long serialVersionUID = 1L;

	/**
     * Default constructor.
     */
    public MakerCreateEntityLimitOperation()
    {}

    /**
     * Returns the Operation Name
     *
     * @return String
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CREATE_ENTITY_LIMIT;
    }

    /**
     * The following tasks are performed:
     *
     * 1. create staging Entity Limit record
     * 2. create staging transaction record if the status is ND, otherwise
     *    update transaction record.
     *
     * @param value is of type ITrxValue
     * @return ITrxResult
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException on error
     */
    public ITrxResult performProcess(ITrxValue value)
            throws TrxOperationException {
        try {
            IEntityLimitTrxValue trxValue =
                    (IEntityLimitTrxValue)(value);

            trxValue = createStagingEntityLimit(trxValue);

			if (trxValue.getStatus().equals (ICMSConstant.STATE_ND))
			    trxValue = super.createTransaction (trxValue);
		    else
				trxValue = super.updateTransaction(trxValue);

            return prepareResult(trxValue);

        } catch (Exception e) {
            throw new TrxOperationException("Caught Exception!", e);
        }
    }
}
