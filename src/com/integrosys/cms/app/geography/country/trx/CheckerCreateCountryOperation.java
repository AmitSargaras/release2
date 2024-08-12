package com.integrosys.cms.app.geography.country.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.country.bus.CountryException;
import com.integrosys.cms.app.insurancecoverage.bus.InsuranceCoverageException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class CheckerCreateCountryOperation extends AbstractCountryTrxOperation {

   /**
    * Defaulc Constructor
    */
    public CheckerCreateCountryOperation()
    {
        super();
    }

	/**
	* Get the operation name of the current operation
	*
	* @return String - the operation name of the current operation
	*/
	public String getOperationName()
	{
		return ICMSConstant.ACTION_CHECKER_FILE_MASTER;
	}


	/**
	* Process the transaction
	* 1.	Create the staging data
	* 2.	Create the transaction record
	* @param anITrxValue of ITrxValue type
	* @return ITrxResult - the transaction result
	* @throws TrxOperationException if encounters any error during the processing of the transaction
	*/
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
	{
	    ICountryTrxValue trxValue = super.getCountryTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));

        
	    trxValue = insertActualCountry(trxValue);
		trxValue = createCountry(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private ICountryTrxValue createCountry(ICountryTrxValue anICCCountryTrxValue) throws TrxOperationException, CountryException
	{	
		try
		{
			String refTemp= anICCCountryTrxValue.getStagingReferenceID();
			ICountryTrxValue inCountryTrxValue = prepareTrxValue(anICCCountryTrxValue);
			
			inCountryTrxValue.setFromState("PENDING_CREATE");
			inCountryTrxValue.setTransactionType("COUNTRY");
			inCountryTrxValue.setToState("ACTIVE");
			inCountryTrxValue.setStatus("ACTIVE");
			inCountryTrxValue.setStagingReferenceID(refTemp);
			
			ICMSTrxValue trxValue = createTransaction(inCountryTrxValue);
            OBCountryTrxValue relationshipMgrTrxValue = new OBCountryTrxValue (trxValue);
            relationshipMgrTrxValue.setActualCountry (anICCCountryTrxValue.getActualCountry());
	        return relationshipMgrTrxValue;
		}
		catch(CountryException se)
		{
			throw new CountryException("Error in Create Country Operation ");
		}
		catch(TransactionException ex)
		{
			throw new TrxOperationException(ex);
		}
		catch(Exception ex)
		{
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

}
