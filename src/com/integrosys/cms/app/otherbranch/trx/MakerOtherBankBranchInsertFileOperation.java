package com.integrosys.cms.app.otherbranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.otherbank.trx.AbstractOtherBankTrxOperation;
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.app.otherbank.trx.OBOtherBankTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public class MakerOtherBankBranchInsertFileOperation extends AbstractOtherBankBranchTrxOperation {

	   /**
	    * Defaulc Constructor
	    */
	    public MakerOtherBankBranchInsertFileOperation()
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
		    IOtherBankBranchTrxValue trxValue = super.getOtherBankBranchTrxValue(anITrxValue);
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
		private IOtherBankBranchTrxValue createFileIdTransaction(IOtherBankBranchTrxValue anICCIOtherBankTrxValue) throws TrxOperationException,OtherBankException
		{
			try
			{
				anICCIOtherBankTrxValue = prepareInsertTrxValue(anICCIOtherBankTrxValue);
				ICMSTrxValue trxValue = createTransaction(anICCIOtherBankTrxValue);
	            OBOtherBankBranchTrxValue otherBankBranch = new OBOtherBankBranchTrxValue (trxValue);
	            otherBankBranch.setStagingFileMapperID(anICCIOtherBankTrxValue.getStagingFileMapperID());
	            otherBankBranch.setFileMapperID(anICCIOtherBankTrxValue.getFileMapperID());
		        return otherBankBranch;
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
