/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.exemptedinst;

import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.SBExemptedInstBusManager;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.ExemptedInstBusManagerFactory;

/**
 * Operation to read Exempted Institution.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class ReadExemptedInstOperation extends CMSTrxOperation implements ITrxReadOperation
{
	/**
  	 * Default Constructor
 	 */
	public ReadExemptedInstOperation() {
		super();
	}

    /**
	     * Get the operation name of the current operation.
	     *
	     * @return the operation name of the current operation
	     */
    public String getOperationName() {
        return ICMSConstant.ACTION_READ_EXEMPT_INST;
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

			IExemptedInst[] stageExemptedInst = null;
			IExemptedInst[] actualExemptedInst = null;

			// get actual Exempted Institution
			SBExemptedInstBusManager mgr = ExemptedInstBusManagerFactory.getActualExemptedInstBusManager();			
			actualExemptedInst = mgr.getAllExemptedInst ();

			String actualRefID = null;
			String stagingRefID = null;
			if (actualExemptedInst != null && actualExemptedInst.length > 0)
			{
				actualRefID = String.valueOf ( (actualExemptedInst[0]).getGroupID() );
				
				if (actualRefID != null) {
						DefaultLogger.debug (this,"************ group id/ actualRefID"+actualRefID);

					try {
						cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType (actualRefID, ICMSConstant.INSTANCE_EXEMPT_INST);
					
						stagingRefID = cmsTrxValue.getStagingReferenceID();						
						
					}
					catch (Exception e) {
						// do nothing here coz the the first col ExemptedInsts created without trx
					}
				}	
			}
			else
			{				
				//chk working transaction exists, transaction <> closed will be return if exists
				cmsTrxValue = getTrxManager().getWorkingTrxByTrxType (ICMSConstant.INSTANCE_EXEMPT_INST);
				
				if( cmsTrxValue == null ) {
					return null;
				}
				stagingRefID = cmsTrxValue.getStagingReferenceID();										

			}
			
			IExemptedInstTrxValue trxVal = new OBExemptedInstTrxValue (cmsTrxValue);		
		
			if (actualRefID != null) {
				// get actual Exempted Institution
                actualExemptedInst = mgr.getExemptedInstByGroupID (Long.parseLong (actualRefID));
                trxVal.setExemptedInst (actualExemptedInst);
                
            }
			if(stagingRefID!=null)
			{
				// get staging Exempted Institution
				SBExemptedInstBusManager stageMgr = ExemptedInstBusManagerFactory.getStagingExemptedInstBusManager();			
				stageExemptedInst = stageMgr.getExemptedInstByGroupID (Long.parseLong (stagingRefID));
				trxVal.setStagingExemptedInst (stageExemptedInst);				
			}	

            return trxVal;
        }
        catch (Exception e) {
            throw new TrxOperationException(e);
        }
    }
	
	 
}
