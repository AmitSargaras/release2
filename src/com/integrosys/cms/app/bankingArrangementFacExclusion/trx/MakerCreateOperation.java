package com.integrosys.cms.app.bankingArrangementFacExclusion.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.BankingArrangementFacExclusionException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public class MakerCreateOperation extends AbstractTrxOperation{

	public MakerCreateOperation() {
		super();
	}
	
	public String getOperationName()
	{
		return ICMSConstant.ACTION_MAKER_CREATE_BANKING_ARRANGEMENT_FAC_EXCLUSION;
	}
	
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
	{
		IBankingArrangementFacExclusionTrxValue trxValue = super.getTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStaging()==null));

	    trxValue = createStaging(trxValue);
		trxValue = createActualTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
	
	private IBankingArrangementFacExclusionTrxValue createActualTransaction(IBankingArrangementFacExclusionTrxValue trx) throws TrxOperationException,BankingArrangementFacExclusionException
	{
		try
		{
			trx = prepareTrxValue(trx);
			ICMSTrxValue trxValue = createTransaction(trx);
			OBBankingArrangementFacExclusionTrxValue newTrx = new OBBankingArrangementFacExclusionTrxValue(trxValue);
			newTrx.setStaging(trx.getStaging());
			newTrx.setActual(trx.getActual());
	        return newTrx;
		}
		catch(BankingArrangementFacExclusionException se)
		{
			throw new BankingArrangementFacExclusionException("Error in Create BankingArrangementFacExclusion Operation ");
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