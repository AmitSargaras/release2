package com.integrosys.cms.app.baselmaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.baselmaster.bus.BaselMasterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;
import com.integrosys.cms.app.component.trx.OBComponentTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public class MakerCreateBaselOperation extends AbstractBaselTrxOperation{
	

	
	  /**
	    * Defaulc Constructor
	    */
	    public MakerCreateBaselOperation()
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
			return ICMSConstant.ACTION_MAKER_CREATE_BASEL;
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
			IBaselMasterTrxValue trxValue = super.getBaselTrxValue(anITrxValue);
	        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
	        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingBaselMaster()==null));

		    trxValue = createStagingBasel(trxValue);
			trxValue = createComponentTransaction(trxValue);
			return super.prepareResult(trxValue);
		}

		/**
		* Create a property index transaction
		* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
		* @return ICCPropertyIdxTrxValue
		* @throws TrxOperationException if there is any processing errors
		*/
		private IBaselMasterTrxValue createComponentTransaction(IBaselMasterTrxValue anICCBaselTrxValue) throws TrxOperationException,BaselMasterException
		{
			try
			{
				anICCBaselTrxValue = prepareTrxValue(anICCBaselTrxValue);
				ICMSTrxValue trxValue = createTransaction(anICCBaselTrxValue);
				OBBaselMasterTrxValue baselTrxValue = new OBBaselMasterTrxValue (trxValue);
				baselTrxValue.setStagingBaselMaster (anICCBaselTrxValue.getStagingBaselMaster());
				baselTrxValue.setBaselMaster(anICCBaselTrxValue.getBaselMaster());
		        return baselTrxValue;
			}
			catch(BaselMasterException se)
			{
				throw new BaselMasterException("Error in Create Basel Operation ");
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
