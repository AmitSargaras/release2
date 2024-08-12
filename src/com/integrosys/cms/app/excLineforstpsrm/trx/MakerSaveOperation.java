package com.integrosys.cms.app.excLineforstpsrm.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.excLineforstpsrm.bus.ExcLineForSTPSRMException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.valuationAmountAndRating.bus.ValuationAmountAndRatingException;

public class MakerSaveOperation extends AbstractTrxOperation {

	public MakerSaveOperation() {
		super();
	}
	
	public String getOperationName()
	{
		return ICMSConstant.ACTION_MAKER_SAVE_EXC_LINE_FR_STP_SRM;
	}
	
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
	{
	    IExcLineForSTPSRMTrxValue trxValue = super.getTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStaging()==null));

	    trxValue = createStaging(trxValue);
		trxValue = createActualTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	private IExcLineForSTPSRMTrxValue createActualTransaction(IExcLineForSTPSRMTrxValue trx) throws TrxOperationException,ExcLineForSTPSRMException
	{
		try
		{
            trx = prepareTrxValue(trx);
			ICMSTrxValue trxValue = createTransaction(trx);
            OBExcLineForSTPSRMTrxValue objTrx = new OBExcLineForSTPSRMTrxValue (trxValue);
            objTrx.setStaging(trx.getStaging());
            objTrx.setActual(trx.getActual());
	        return objTrx;
		}
		catch(ExcLineForSTPSRMException se)
		{
			throw new ValuationAmountAndRatingException("Error in Create Exclusion Line for STP for SRM");
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