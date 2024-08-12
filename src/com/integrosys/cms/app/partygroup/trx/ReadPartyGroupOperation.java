package com.integrosys.cms.app.partygroup.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * @author bharat.waghela Read Party group Operation
 */

public class ReadPartyGroupOperation extends AbstractPartyGroupTrxOperation
		implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadPartyGroupOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_PARTY_GROUP;
	}

	/**
	 * This method is used to read a transaction object
	 * 
	 * @param anITrxValue
	 *            the ITrxValue object containing the parameters required for
	 *            retrieving a record, such as the transaction ID.
	 * @return ITrxValue - containing the requested data.
	 * @throws com.integrosys.base.businfra.transaction.TransactionException
	 *             if any other errors occur.
	 */
	public ITrxValue getTransaction(ITrxValue anITrxValue)
			throws TransactionException {
		try {
			ICMSTrxValue trxValue = super.getCMSTrxValue(anITrxValue);
			trxValue = (ICMSTrxValue) getTrxManager().getTrxByRefIDAndTrxType(
					trxValue.getReferenceID(), trxValue.getTransactionType());
			OBPartyGroupTrxValue newValue = new OBPartyGroupTrxValue(trxValue);

			String stagingRef = trxValue.getStagingReferenceID();
			String actualRef = trxValue.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference: " + actualRef
					+ " , Staging Reference: " + stagingRef);

			if (null != stagingRef) {
				long stagingPK = Long.parseLong(stagingRef);
				newValue.setStagingPartyGroup(getStagingPartyGroupBusManager()
						.getPartyGroupById(stagingPK));
			}

			if (null != actualRef) {
				long actualPK = Long.parseLong(actualRef);

				newValue.setPartyGroup(getPartyGroupBusManager()
						.getPartyGroupById(actualPK));
			}

			return newValue;
		} catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
	}
}
