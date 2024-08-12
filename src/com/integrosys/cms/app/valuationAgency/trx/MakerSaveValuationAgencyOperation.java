package com.integrosys.cms.app.valuationAgency.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyException;

/**
 * @author rajib.aich Checker approve Operation to approve update made by maker
 */
public class MakerSaveValuationAgencyOperation extends
		AbstractValuationAgencyTrxOperation {

	/**
	 * Defaulc Constructor
	 */
	public MakerSaveValuationAgencyOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SAVE_VALUATION_AGENCY;
	}

	/**
	 * Process the transaction 1. Create the staging data 2. Create the
	 * transaction record
	 * 
	 * @param anITrxValue
	 *            of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException
	 *             if encounters any error during the processing of the
	 *             transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue)
			throws TrxOperationException {
		IValuationAgencyTrxValue trxValue = super
				.getValuationAgencyTrxValue(anITrxValue);
		DefaultLogger.debug(this, "trxValue is null ? " + (trxValue == null));
		DefaultLogger.debug(this,
				" ---- trxValue.getStagingPrIdx() is null ? ----- "
						+ (trxValue.getStagingValuationAgency() == null));

		trxValue = createStagingValuationAgency(trxValue);
		trxValue = createValuationAgencyTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create a property index transaction
	 * 
	 * @param anICCPropertyIdxTrxValue
	 *            of ICCPropertyIdxTrxValue type
	 * @return ICCPropertyIdxTrxValue
	 * @throws TrxOperationException
	 *             if there is any processing errors
	 */
	private IValuationAgencyTrxValue createValuationAgencyTransaction(
			IValuationAgencyTrxValue anICCValuationAgencyTrxValue)
			throws TrxOperationException, ValuationAgencyException {
		try {
			anICCValuationAgencyTrxValue = prepareTrxValue(anICCValuationAgencyTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCValuationAgencyTrxValue);
			OBValuationAgencyTrxValue valuationAgencyTrxValue = new OBValuationAgencyTrxValue(
					trxValue);
			valuationAgencyTrxValue
					.setStagingValuationAgency(anICCValuationAgencyTrxValue
							.getStagingValuationAgency());
			valuationAgencyTrxValue
					.setValuationAgency(anICCValuationAgencyTrxValue
							.getValuationAgency());
			return valuationAgencyTrxValue;
		} catch (ValuationAgencyException se) {
			throw new ValuationAgencyException(
					"Error in Create Valuation Agency Operation ");
		} catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		} catch (Exception ex) {
			throw new TrxOperationException("General Exception: "
					+ ex.toString());
		}
	}

}
