/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.countrylimit;

import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimitBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimitParam;
//import com.integrosys.cms.app.creditriskparam.bus.countrylimit.SBCountryLimitBusManager;
//import com.integrosys.cms.app.creditriskparam.bus.countrylimit.CountryLimitBusManagerFactory;


/**
 * The operation is to read Country Limit by transaction ID.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class ReadCountryLimitByTrxIDOperation extends CMSTrxOperation implements ITrxReadOperation
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
	public ReadCountryLimitByTrxIDOperation() {
		super();
	}

	/**
 	 * Get the operation name of the current operation.
     *
 	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_COUNTRY_LIMIT_BY_TRXID;
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

			OBCountryLimitTrxValue trxVal = new OBCountryLimitTrxValue (cmsTrxValue);

			String stagingRef = cmsTrxValue.getStagingReferenceID();
			String actualRef = cmsTrxValue.getReferenceID();
			
			DefaultLogger.debug (this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
//				SBCountryLimitBusManager mgr = CountryLimitBusManagerFactory.getStagingCountryLimitBusManager();
                ICountryLimitBusManager mgr = getStagingCountryLimitBusManager();
                ICountryLimitParam countryLimitList = mgr.getCountryLimitParamByGroupID (Long.parseLong (stagingRef));
                trxVal.setStagingCountryLimitParam (countryLimitList);
				
            }

			if (actualRef != null) {
//				SBCountryLimitBusManager mgr = CountryLimitBusManagerFactory.getActualCountryLimitBusManager();
                ICountryLimitBusManager mgr = getCountryLimitBusManager();
                ICountryLimitParam countryLimitList = mgr.getCountryLimitParamByGroupID (Long.parseLong (actualRef));
                trxVal.setCountryLimitParam (countryLimitList);
                
            }
            return trxVal;

        }
        catch (Exception e) {
            throw new TrxOperationException (e);
        }
    } 

}