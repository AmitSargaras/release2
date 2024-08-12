package com.integrosys.cms.app.limitsOfAuthorityMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.LimitsOfAuthorityMasterException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public class MakerCreateOperation extends AbstractTrxOperation{

	public MakerCreateOperation() {
		super();
	}
	
	public String getOperationName()
	{
		return ICMSConstant.LimitsOfAuthorityMaster.ACTION_MAKER_CREATE_LIMITS_OF_AUTHORITY;
	}
	
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
	{
		ILimitsOfAuthorityMasterTrxValue trxValue = super.getTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStaging()==null));

	    trxValue = createStaging(trxValue);
		trxValue = createActualTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
	
	private ILimitsOfAuthorityMasterTrxValue createActualTransaction(ILimitsOfAuthorityMasterTrxValue trx) throws TrxOperationException,LimitsOfAuthorityMasterException
	{
		try
		{
			trx = prepareTrxValue(trx);
			ICMSTrxValue trxValue = createTransaction(trx);
			OBLimitsOfAuthorityMasterTrxValue newTrx = new OBLimitsOfAuthorityMasterTrxValue(trxValue);
			newTrx.setStaging(trx.getStaging());
			newTrx.setActual(trx.getActual());
	        return newTrx;
		}
		catch(LimitsOfAuthorityMasterException se)
		{
			throw new LimitsOfAuthorityMasterException("Error in Create LimitsOfAuthorityMaster Operation ");
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