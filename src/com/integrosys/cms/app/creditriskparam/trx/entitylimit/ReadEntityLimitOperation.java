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
 * Operation to read Entity Limit.
 *
 * @author   $Author: skchai $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class ReadEntityLimitOperation extends CMSTrxOperation implements ITrxReadOperation
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

	/**
  	 * Default Constructor
 	 */
	public ReadEntityLimitOperation() {
		super();
	}

    /**
	     * Get the operation name of the current operation.
	     *
	     * @return the operation name of the current operation
	     */
    public String getOperationName() {
        return ICMSConstant.ACTION_READ_ENTITY_LIMIT;
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

			IEntityLimit[] stageEntityLimit = null;
			IEntityLimit[] actualEntityLimit = null;

			// get actual Entity Limit
//			SBEntityLimitBusManager mgr = EntityLimitBusManagerFactory.getActualEntityLimitBusManager();
            IEntityLimitBusManager mgr = getEntityLimitBusManager();
			actualEntityLimit = mgr.getAllEntityLimit ();

			String actualRefID = null;
			String stagingRefID = null;
			if (actualEntityLimit != null && actualEntityLimit.length > 0)
			{
				actualRefID = String.valueOf ( (actualEntityLimit[0]).getGroupID() );
				
				if (actualRefID != null) {
						DefaultLogger.debug (this,"************ group id/ actualRefID"+actualRefID);

					try {
						cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType (actualRefID, ICMSConstant.INSTANCE_ENTITY_LIMIT);
					
						stagingRefID = cmsTrxValue.getStagingReferenceID();						
						
					}
					catch (Exception e) {
						// do nothing here coz the the first col EntityLimits created without trx
					}
				}	
			}
			else
			{				
				//chk working transaction exists, transaction <> closed will be return if exists
				cmsTrxValue = getTrxManager().getWorkingTrxByTrxType (ICMSConstant.INSTANCE_ENTITY_LIMIT);
				
				if( cmsTrxValue == null ) {
					return null;
				}
				stagingRefID = cmsTrxValue.getStagingReferenceID();										

			}
			
			IEntityLimitTrxValue trxVal = new OBEntityLimitTrxValue (cmsTrxValue);		
		
			if (actualRefID != null) {
				// get actual Entity Limit
                actualEntityLimit = mgr.getEntityLimitByGroupID (Long.parseLong (actualRefID));
                trxVal.setEntityLimit (actualEntityLimit);
                
            }
			if(stagingRefID!=null)
			{
				// get staging Exempted Institution
//				SBEntityLimitBusManager stageMgr = EntityLimitBusManagerFactory.getStagingEntityLimitBusManager();
                IEntityLimitBusManager stageMgr = getStagingEntityLimitBusManager();
				stageEntityLimit = stageMgr.getEntityLimitByGroupID (Long.parseLong (stagingRefID));
				trxVal.setStagingEntityLimit (stageEntityLimit);				
			}	

            return trxVal;
        }
        catch (Exception e) {
            throw new TrxOperationException(e);
        }
    }
	
	 
}
