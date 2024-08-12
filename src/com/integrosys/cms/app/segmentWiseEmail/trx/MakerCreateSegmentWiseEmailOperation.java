package com.integrosys.cms.app.segmentWiseEmail.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collaborationtask.trx.ICCTaskTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.segmentWiseEmail.bus.SegmentWiseEmailException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public class MakerCreateSegmentWiseEmailOperation extends AbstractSegmentWiseEmailTrxOperation{

	/**
	 * Defaulc Constructor
	 */
	public MakerCreateSegmentWiseEmailOperation() {
		super();
	}
	
	/**
	* Get the operation name of the current operation
	*
	* @return String - the operation name of the current operation
	*/
	public String getOperationName(){
		return ICMSConstant.ACTION_MAKER_CREATE_SEGMENT_WISE_EMAIL;
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
		ISegmentWiseEmailTrxValue trxValue = super.getSegmentWiseEmailTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingSegmentWiseEmail()==null));

	    trxValue = createStagingSegmentWiseEmail(trxValue);
		trxValue = createSegmentWiseEmailTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
	
	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private ISegmentWiseEmailTrxValue createSegmentWiseEmailTransaction(ISegmentWiseEmailTrxValue anICCSegmentWiseEmailTrxValue) throws TrxOperationException,SegmentWiseEmailException
	{
		try
		{
			anICCSegmentWiseEmailTrxValue = prepareTrxValue(anICCSegmentWiseEmailTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCSegmentWiseEmailTrxValue);
			OBSegmentWiseEmailTrxValue segmentWiseEmailTrxValue = new OBSegmentWiseEmailTrxValue (trxValue);
			segmentWiseEmailTrxValue.setStagingSegmentWiseEmail(segmentWiseEmailTrxValue.getStagingSegmentWiseEmail());
			segmentWiseEmailTrxValue.setSegmentWiseEmail(anICCSegmentWiseEmailTrxValue.getSegmentWiseEmail());
	        return segmentWiseEmailTrxValue;
		}
		catch(SegmentWiseEmailException se)
		{
			throw new SegmentWiseEmailException("Error in Create SegmentWiseEmail Operation ");
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