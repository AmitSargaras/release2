package com.integrosys.cms.app.valuationAgency.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.holiday.trx.AbstractHolidayTrxOperation;
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;
import com.integrosys.cms.app.holiday.trx.OBHolidayTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyException;

public class CheckerCreateValuationAgencyOperation extends AbstractValuationAgencyTrxOperation {

	   /**
	    * Defaulc Constructor
	    */
	    public CheckerCreateValuationAgencyOperation()
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
		    IValuationAgencyTrxValue trxValue = super.getValuationAgencyTrxValue(anITrxValue);
	        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));

	        
		    trxValue = insertActualValuationAgency(trxValue); 
			trxValue = createValuationAgency(trxValue);
			return super.prepareResult(trxValue);
		}

		/**
		* Create a property index transaction
		* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
		* @return ICCPropertyIdxTrxValue
		* @throws TrxOperationException if there is any processing errors
		*/
		private IValuationAgencyTrxValue createValuationAgency(IValuationAgencyTrxValue anICCValuationAgencyTrxValue) throws TrxOperationException, ValuationAgencyException
		{	
			try
			{
				String refTemp= anICCValuationAgencyTrxValue.getStagingReferenceID();
				IValuationAgencyTrxValue inValuationAgencyTrxValue = prepareTrxValue(anICCValuationAgencyTrxValue);
				
				inValuationAgencyTrxValue.setFromState("PENDING_CREATE");
				inValuationAgencyTrxValue.setTransactionType("VALUATION_AGENCY");
				inValuationAgencyTrxValue.setToState("ACTIVE");
				inValuationAgencyTrxValue.setStatus("ACTIVE");
				inValuationAgencyTrxValue.setStagingReferenceID(refTemp);
				
				ICMSTrxValue trxValue = createTransaction(inValuationAgencyTrxValue);
	            OBValuationAgencyTrxValue holidayTrxValue = new OBValuationAgencyTrxValue (trxValue);
	            holidayTrxValue.setValuationAgency (anICCValuationAgencyTrxValue.getValuationAgency());
	            holidayTrxValue.setValuationAgency(anICCValuationAgencyTrxValue.getValuationAgency());
		        return holidayTrxValue;
			}
			catch(ValuationAgencyException se)
			{
				throw new ValuationAgencyException("Error in Create ValuationAgency Operation ");
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
