package com.integrosys.cms.app.component.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.component.bus.ComponentException;

public class MakerCreateComponentOperation extends AbstractComponentTrxOperation{
	
	  /**
	    * Defaulc Constructor
	    */
	    public MakerCreateComponentOperation()
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
			return ICMSConstant.ACTION_MAKER_CREATE_COMPONENT;
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
		    IComponentTrxValue trxValue = super.getComponentTrxValue(anITrxValue);
	        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
	        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingComponent()==null));

		    trxValue = createStagingComponent(trxValue);
			trxValue = createComponentTransaction(trxValue);
			return super.prepareResult(trxValue);
		}

		/**
		* Create a property index transaction
		* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
		* @return ICCPropertyIdxTrxValue
		* @throws TrxOperationException if there is any processing errors
		*/
		private IComponentTrxValue createComponentTransaction(IComponentTrxValue anICCComponentTrxValue) throws TrxOperationException,ComponentException
		{
			try
			{
	            anICCComponentTrxValue = prepareTrxValue(anICCComponentTrxValue);
				ICMSTrxValue trxValue = createTransaction(anICCComponentTrxValue);
	            OBComponentTrxValue componentTrxValue = new OBComponentTrxValue (trxValue);
	            componentTrxValue.setStagingComponent (anICCComponentTrxValue.getStagingComponent());
	            componentTrxValue.setComponent(anICCComponentTrxValue.getComponent());
		        return componentTrxValue;
			}
			catch(ComponentException se)
			{
				throw new ComponentException("Error in Create Component Operation ");
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
