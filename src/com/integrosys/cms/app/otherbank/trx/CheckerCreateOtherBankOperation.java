package com.integrosys.cms.app.otherbank.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public class CheckerCreateOtherBankOperation extends AbstractOtherBankTrxOperation {

	   /**
	    * Defaulc Constructor
	    */
	    public CheckerCreateOtherBankOperation()
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
		    IOtherBankTrxValue trxValue = super.getOtherBankTrxValue(anITrxValue);
	        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));

	        
		    trxValue = insertActualOtherBank(trxValue);   
			trxValue = createOtherBank(trxValue);
			return super.prepareResult(trxValue);
		}

		/**
		* Create a property index transaction
		* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
		* @return ICCPropertyIdxTrxValue
		* @throws TrxOperationException if there is any processing errors
		*/
		private IOtherBankTrxValue createOtherBank(IOtherBankTrxValue anICCOtherBankTrxValue) throws TrxOperationException, OtherBankException
		{	
			try
			{
				String refTemp= anICCOtherBankTrxValue.getStagingReferenceID();
				IOtherBankTrxValue inOtherBankTrxValue = prepareTrxValue(anICCOtherBankTrxValue);
				
				inOtherBankTrxValue.setFromState("PENDING_CREATE");
				inOtherBankTrxValue.setTransactionType("OTHER_BANK");
				inOtherBankTrxValue.setToState("ACTIVE");
				inOtherBankTrxValue.setStatus("ACTIVE");
				inOtherBankTrxValue.setStagingReferenceID(refTemp);
				
				ICMSTrxValue trxValue = createTransaction(inOtherBankTrxValue);
	            OBOtherBankTrxValue holidayTrxValue = new OBOtherBankTrxValue (trxValue);
	            holidayTrxValue.setOtherBank(anICCOtherBankTrxValue.getOtherBank());
	            holidayTrxValue.setOtherBank(anICCOtherBankTrxValue.getOtherBank());
		        return holidayTrxValue;
			}
			catch(OtherBankException se)
			{
				throw new OtherBankException("Error in Create OtherBank Operation ");
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
