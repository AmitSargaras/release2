/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.entitylimit;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.IEntityLimit;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.IEntityLimitBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;


/**
 * The operation is to read Entity Limit by transaction ID.
 *
 * @author   $Author: skchai $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class ReadEntityLimitByTrxIDOperation extends CMSTrxOperation implements ITrxReadOperation
{

    private IEntityLimitBusManager entityLimitBusManager;

    private IEntityLimitBusManager stagingEntityLimitBusManager;

    public IEntityLimitBusManager getEntityLimitBusManager() {
        return entityLimitBusManager;
    }

    public void setEntityLimitBusManager(IEntityLimitBusManager entityLimitBusManager) {
        this.entityLimitBusManager = entityLimitBusManager;
    }

    public IEntityLimitBusManager getStagingEntityLimitBusManager() {
        return stagingEntityLimitBusManager;
    }

    public void setStagingEntityLimitBusManager(IEntityLimitBusManager stagingEntityLimitBusManager) {
        this.stagingEntityLimitBusManager = stagingEntityLimitBusManager;
    }

	private static final long serialVersionUID = 1L;

	/**
  	 * Default Constructor
 	 */
	public ReadEntityLimitByTrxIDOperation() {
		super();
	}

	/**
 	 * Get the operation name of the current operation.
     *
 	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_ENTITY_LIMIT_BY_TRXID;
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

			OBEntityLimitTrxValue trxVal = new OBEntityLimitTrxValue (cmsTrxValue);

			String stagingRef = cmsTrxValue.getStagingReferenceID();
			String actualRef = cmsTrxValue.getReferenceID();
			
			DefaultLogger.debug (this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
//				SBEntityLimitBusManager mgr = EntityLimitBusManagerFactory.getStagingEntityLimitBusManager();
                IEntityLimitBusManager mgr = getStagingEntityLimitBusManager();
                IEntityLimit[] EntityLimitList = mgr.getEntityLimitByGroupID (Long.parseLong (stagingRef));
                trxVal.setStagingEntityLimit (EntityLimitList);
				
            }

			if (actualRef != null) {
//				SBEntityLimitBusManager mgr = EntityLimitBusManagerFactory.getActualEntityLimitBusManager();
                IEntityLimitBusManager mgr = getEntityLimitBusManager();
                IEntityLimit[] EntityLimitList = mgr.getEntityLimitByGroupID (Long.parseLong (actualRef));
                trxVal.setEntityLimit (EntityLimitList);
                
            }
            return trxVal;

        }
        catch (Exception e) {
            throw new TrxOperationException (e);
        }
    } 

}