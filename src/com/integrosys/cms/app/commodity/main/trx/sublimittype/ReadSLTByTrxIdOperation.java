/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/sublimittype/ReadSLTByTrxIdOperation.java,v 1.1 2005/10/06 05:08:56 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.sublimittype;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoManagerFactory;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfo;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-22
 * @Tag com.integrosys.cms.app.commodity.main.trx.sublimittype.
 *      ReadSLTByTrxIdOperation.java
 */
public class ReadSLTByTrxIdOperation extends CMSTrxOperation implements ITrxReadOperation {
	public ReadSLTByTrxIdOperation() {
		super();
	}

	/*
	 * @seecom.integrosys.base.businfra.transaction.AbstractTrxOperation#
	 * getOperationName()
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_COMMODITY_MAIN_TRXID;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.transaction.ITrxReadOperation#getTransaction(com
	 * .integrosys.base.businfra.transaction.ITrxValue)
	 */
	public ITrxValue getTransaction(ITrxValue value) throws TransactionException {
		DefaultLogger.debug(this, " - getTransaction() - Begin.");
		try {
			ICMSTrxValue trxValue = super.getCMSTrxValue(value);
			DefaultLogger.debug(this, " trxId :" + trxValue.getTransactionID());
			trxValue = (ICMSTrxValue) getTrxManager().getTransaction(trxValue.getTransactionID());

			ISubLimitType[] actualSLT = getActualSLT(trxValue.getReferenceID());
			ISubLimitType[] stagingSLT = getStagingSLT(trxValue.getStagingReferenceID());

			OBSubLimitTypeTrxValue newValue = new OBSubLimitTypeTrxValue(trxValue);
			newValue.setSubLimitTypes(actualSLT);
			newValue.setStagingSubLimitTypes(stagingSLT);
			return newValue;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
		finally {
			DefaultLogger.debug(this, " - getTransaction() - End.");
		}
	}

	private ISubLimitType[] getActualSLT(String actualRef) throws CommodityException {
		if (actualRef == null) {
			return null;
		}
		DefaultLogger.debug(this, "Actual Reference: " + actualRef);
		ISubLimitType[] actualSLT = (ISubLimitType[]) CommodityMainInfoManagerFactory.getManager()
				.getCommodityMainInfosByGroupID((actualRef), ICommodityMainInfo.INFO_TYPE_SUBLIMITTYPE);
		DefaultLogger.debug(this, "Length of actual : " + (actualSLT == null ? 0 : actualSLT.length));
		return actualSLT;
	}

	private ISubLimitType[] getStagingSLT(String stagingRef) throws CommodityException {
		if (stagingRef == null) {
			return null;
		}
		DefaultLogger.debug(this, "Staging Reference: " + stagingRef);
		ISubLimitType[] stagingSLT = (ISubLimitType[]) CommodityMainInfoManagerFactory.getStagingManager()
				.getCommodityMainInfosByGroupID((stagingRef), ICommodityMainInfo.INFO_TYPE_SUBLIMITTYPE);
		DefaultLogger.debug(this, "Length of staging : " + (stagingSLT == null ? 0 : stagingSLT.length));
		return stagingSLT;
	}
}