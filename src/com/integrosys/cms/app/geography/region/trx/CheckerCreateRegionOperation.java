package com.integrosys.cms.app.geography.region.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class CheckerCreateRegionOperation extends AbstractRegionTrxOperation {

   /**
    * Defaulc Constructor
    */
    public CheckerCreateRegionOperation()
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
	    IRegionTrxValue trxValue = super.getRegionTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));

        
	    trxValue = insertActualRegion(trxValue);
		trxValue = createRegion(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private IRegionTrxValue createRegion(IRegionTrxValue anICCRegionTrxValue) throws TrxOperationException, NoSuchGeographyException
	{	
		try
		{
			String refTemp= anICCRegionTrxValue.getStagingReferenceID();
			IRegionTrxValue inRegionTrxValue = prepareTrxValue(anICCRegionTrxValue);
			
			inRegionTrxValue.setFromState("PENDING_CREATE");
			inRegionTrxValue.setTransactionType("REGION");
			inRegionTrxValue.setToState("ACTIVE");
			inRegionTrxValue.setStatus("ACTIVE");
			inRegionTrxValue.setStagingReferenceID(refTemp);
			
			ICMSTrxValue trxValue = createTransaction(inRegionTrxValue);
            OBRegionTrxValue relationshipMgrTrxValue = new OBRegionTrxValue (trxValue);
            relationshipMgrTrxValue.setActualRegion (anICCRegionTrxValue.getActualRegion());
	        return relationshipMgrTrxValue;
		}
		catch(NoSuchGeographyException se)
		{
			throw new NoSuchGeographyException("Error in Create Region Operation ");
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
