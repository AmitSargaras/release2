/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.countrylimit;

import com.integrosys.cms.app.creditriskparam.bus.countrylimit.*;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Operation to read Country Limit.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class ReadCountryLimitOperation extends CMSTrxOperation implements ITrxReadOperation
{

    private ICountryLimitBusManager countryLimitBusManager;

    private ICountryLimitBusManager stagingCountryLimitBusManager;

    public ICountryLimitBusManager getCountryLimitBusManager() {
        return countryLimitBusManager;
    }

    public void setCountryLimitBusManager(ICountryLimitBusManager countryLimitBusManager) {
        this.countryLimitBusManager = countryLimitBusManager;
    }

    public ICountryLimitBusManager getStagingCountryLimitBusManager() {
        return stagingCountryLimitBusManager;
    }

    public void setStagingCountryLimitBusManager(ICountryLimitBusManager stagingCountryLimitBusManager) {
        this.stagingCountryLimitBusManager = stagingCountryLimitBusManager;
    }

    /**
  	 * Default Constructor
 	 */
	public ReadCountryLimitOperation() {
		super();
	}

    /**
	     * Get the operation name of the current operation.
	     *
	     * @return the operation name of the current operation
	     */
    public String getOperationName() {
        return ICMSConstant.ACTION_READ_COUNTRY_LIMIT;
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

			ICountryLimitParam stageCountryLimit = null;
			ICountryLimitParam actualCountryLimit = null;
			
			ICountryRating[] actualCountryRating = null;

			// get actual Country Rating
//			SBCountryLimitBusManager mgr = CountryLimitBusManagerFactory.getActualCountryLimitBusManager();
            ICountryLimitBusManager mgr = getCountryLimitBusManager();
			actualCountryRating = mgr.getCountryRating ();

			String actualRefID = null;
			String stagingRefID = null;
			if (actualCountryRating != null && actualCountryRating.length > 0)
			{
				actualRefID = String.valueOf ( (actualCountryRating[0]).getGroupID() );
				
				if (actualRefID != null) {
					DefaultLogger.debug (this,"************ group id/ actualRefID"+actualRefID);

					try {
						cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType (actualRefID, ICMSConstant.INSTANCE_COUNTRY_LIMIT);
					
						stagingRefID = cmsTrxValue.getStagingReferenceID();									
					}
					catch (Exception e) {
						// do nothing here coz the the first col CountryLimits created without trx
					}
				}	
			}
			else
			{				
				//should not empty because country rating data will be setup first					
				if( cmsTrxValue == null ) {
					return null;
				}
				stagingRefID = cmsTrxValue.getStagingReferenceID();		
			}
			
			ICountryLimitTrxValue trxVal = new OBCountryLimitTrxValue (cmsTrxValue);		
		
			if (actualRefID != null) {
				// get actual Country Limit
                actualCountryLimit = mgr.getCountryLimitParamByGroupID (Long.parseLong (actualRefID));
                trxVal.setCountryLimitParam (actualCountryLimit);
                
            }
			if(stagingRefID!=null)	{
				// get staging Country Limit
//				SBCountryLimitBusManager stageMgr = CountryLimitBusManagerFactory.getStagingCountryLimitBusManager();
                ICountryLimitBusManager stageMgr = getStagingCountryLimitBusManager();
				stageCountryLimit = stageMgr.getCountryLimitParamByGroupID (Long.parseLong (stagingRefID));
				trxVal.setStagingCountryLimitParam (stageCountryLimit);				
			}	
			
            return trxVal;
        }
        catch (Exception e) {
            throw new TrxOperationException(e);
        }
    }
	
	 
}
