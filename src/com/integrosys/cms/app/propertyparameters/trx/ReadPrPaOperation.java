package com.integrosys.cms.app.propertyparameters.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyparameters.bus.PropertyParametersBusDelegate;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Feb 1, 2007 Time: 2:02:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReadPrPaOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadPrPaOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_PRPA;
	}

	/**
	 * This method is used to read a transaction object
	 * @param anITrxValue the ITrxValue object containing the parameters
	 *        required for retrieving a record, such as the transaction ID.
	 * @return ITrxValue - containing the requested data.
	 * @throws com.integrosys.base.businfra.transaction.TransactionException if
	 *         any other errors occur.
	 */
	public ITrxValue getTransaction(ITrxValue anITrxValue) throws TransactionException {
		try {
			ICMSTrxValue trxValue = (ICMSTrxValue) getTrxManager().getTransaction(anITrxValue.getTransactionID());

			OBPrPaTrxValue newValue = new OBPrPaTrxValue(trxValue);

			String stagingRef = trxValue.getStagingReferenceID();
			String actualRef = trxValue.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
				long stagingPK = (new Long(stagingRef)).longValue();

				newValue.setStagingPrPa(getBusDelegrate().getStgPropertyParameters(stagingPK));
			}

			if (actualRef != null) {
				long actualPK = (new Long(actualRef)).longValue();
				newValue.setPrPa(getBusDelegrate().getPropertyParameters(actualPK));
			}
			return newValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex.toString());
		}
	}

	public PropertyParametersBusDelegate getBusDelegrate() {
		return new PropertyParametersBusDelegate();
	}
}
