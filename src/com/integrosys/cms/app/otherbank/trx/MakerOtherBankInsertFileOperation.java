package com.integrosys.cms.app.otherbank.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public class MakerOtherBankInsertFileOperation extends AbstractOtherBankTrxOperation {

	   /**
	    * Defaulc Constructor
	    */
	    public MakerOtherBankInsertFileOperation()
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
			return ICMSConstant.ACTION_MAKER_FILE_INSERT;
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
	  //      DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingFileMapperID()==null));

		    trxValue = createStagingFileId(trxValue); 
			trxValue = createFileIdTransaction(trxValue);
			return super.prepareResult(trxValue);
		}

		/**
		* Create a property index transaction
		* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
		* @return ICCPropertyIdxTrxValue
		* @throws TrxOperationException if there is any processing errors
		*/
		private IOtherBankTrxValue createFileIdTransaction(IOtherBankTrxValue anICCIOtherBankTrxValue) throws TrxOperationException,OtherBankException
		{
			try
			{
				anICCIOtherBankTrxValue = prepareInsertTrxValue(anICCIOtherBankTrxValue);
				ICMSTrxValue trxValue = createTransaction(anICCIOtherBankTrxValue);
	            OBOtherBankTrxValue otherBank = new OBOtherBankTrxValue (trxValue);
	            otherBank.setStagingFileMapperID(anICCIOtherBankTrxValue.getStagingFileMapperID());
	            otherBank.setFileMapperID(anICCIOtherBankTrxValue.getFileMapperID());
		        return otherBank;
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
