package com.integrosys.cms.app.partygroup.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.partygroup.bus.PartyGroupException;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Title: CLIMS Description: Copyright: Integro Technologies Sdn Bhd Author:
 * Andy Wong Date: Jan 18, 2008
 */
public class MakerSavePartyGroupOperation extends
		AbstractPartyGroupTrxOperation {

	/**
	 * Defaulc Constructor
	 */
	public MakerSavePartyGroupOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SAVE_PARTY_GROUP;
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
		IPartyGroupTrxValue trxValue = super.getPartyGroupTrxValue(anITrxValue);
		DefaultLogger.debug(this, "trxValue is null ? " + (trxValue == null));
		DefaultLogger.debug(this,
				" ---- trxValue.getStagingPrIdx() is null ? ----- "
						+ (trxValue.getStagingPartyGroup() == null));

		trxValue = createStagingPartyGroup(trxValue);
		trxValue = createPartyGroupTransaction(trxValue);
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
	private IPartyGroupTrxValue createPartyGroupTransaction(
			IPartyGroupTrxValue anICCPartyGroupTrxValue)
			throws TrxOperationException, PartyGroupException {
		try {
			anICCPartyGroupTrxValue = prepareTrxValue(anICCPartyGroupTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCPartyGroupTrxValue);
			OBPartyGroupTrxValue partyGroupTrxValue = new OBPartyGroupTrxValue(
					trxValue);
			partyGroupTrxValue.setStagingPartyGroup(anICCPartyGroupTrxValue
					.getStagingPartyGroup());
			partyGroupTrxValue.setPartyGroup(anICCPartyGroupTrxValue
					.getPartyGroup());
			return partyGroupTrxValue;
		} catch (PartyGroupException se) {
			throw new PartyGroupException(
					"Error in Create Party Group Operation ");
		} catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		} catch (Exception ex) {
			throw new TrxOperationException("General Exception: "
					+ ex.toString());
		}
	}

}
