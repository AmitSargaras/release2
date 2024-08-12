/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/uom/ReadUOMByTrxIDOperation.java,v 1.2 2004/06/04 04:54:22 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.uom;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoManagerFactory;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfo;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfoManager;
import com.integrosys.cms.app.commodity.main.bus.uom.IUnitofMeasure;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 04:54:22 $ Tag: $Name: $
 */
public class ReadUOMByTrxIDOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadUOMByTrxIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_COMMODITY_MAIN_TRXID;
	}

	/**
	 * This method is used to read a transaction object
	 * 
	 * @param val is the ITrxValue object containing the parameters required for
	 *        retrieving a record, such as the transaction ID.
	 * @return ITrxValue containing the requested data.
	 * @throws com.integrosys.base.businfra.transaction.TransactionException if
	 *         any other errors occur.
	 */
	public ITrxValue getTransaction(ITrxValue val) throws TransactionException {
		try {
			ICMSTrxValue cmsTrxValue = getTrxManager().getTransaction(val.getTransactionID());
			OBUnitofMeasureTrxValue paramTrxValue = new OBUnitofMeasureTrxValue(cmsTrxValue);

			String stagingRef = cmsTrxValue.getStagingReferenceID();
			String actualRef = cmsTrxValue.getReferenceID();
			String transactionID = cmsTrxValue.getTransactionID();

			DefaultLogger.debug(this, "Transaction id : " + transactionID + ", Actual Reference: " + actualRef
					+ " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
				ICommodityMainInfoManager mgr = CommodityMainInfoManagerFactory.getStagingManager();
				IUnitofMeasure[] uoms = (IUnitofMeasure[]) mgr.getCommodityMainInfosByGroupID(stagingRef,
						ICommodityMainInfo.INFO_TYPE_UOM);
				paramTrxValue.setStagingUnitofMeasure(uoms);
			}
			if (actualRef != null) {
				ICommodityMainInfoManager mgr = CommodityMainInfoManagerFactory.getManager();
				IUnitofMeasure[] uoms = (IUnitofMeasure[]) mgr.getCommodityMainInfosByGroupID(actualRef,
						ICommodityMainInfo.INFO_TYPE_UOM);
				paramTrxValue.setUnitofMeasure(uoms);
			}
			return paramTrxValue;
		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
	}
}