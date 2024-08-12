/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/uom/MakerCloseUpdateUOMOperation.java,v 1.2 2005/01/13 08:32:53 whuang Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.uom;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: whuang $
 * @version $Revision: 1.2 $
 * @since $Date: 2005/01/13 08:32:53 $ Tag: $Name: $
 */
public class MakerCloseUpdateUOMOperation extends AbstractUOMTrxOperation {
	/**
	 * Default constructor.
	 */
	public MakerCloseUpdateUOMOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_CLOSE_COMMODITY_MAIN;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. create staging unit of measure record 2. update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			IUnitofMeasureTrxValue trxValue = super.getUnitofMeasureTrxValue(value);
			DefaultLogger.debug(this, " -------------------------------- " + trxValue.getReferenceID()
					+ " in close update operation1 ----------------------------------------");
			// trxValue.setStagingUnitofMeasure(trxValue.getUnitofMeasure());
			// trxValue = super.createStaging(trxValue);
			DefaultLogger.debug(this, " -------------------------------- " + trxValue.getReferenceID()
					+ " in close update operation2 ----------------------------------------");
			trxValue = super.updateTransaction(trxValue);

			return super.prepareResult(trxValue);
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxOperationException("Caught Exception!", e);
		}
	}
}
