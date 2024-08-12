/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/sublimittype/CheckerApproveCreateSLTOperation.java,v 1.1 2005/10/06 05:08:56 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.sublimittype;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-22
 * @Tag com.integrosys.cms.app.commodity.main.trx.sublimittype.
 *      CheckerApproveCreateSLTOperation.java
 */
public class CheckerApproveCreateSLTOperation extends AbstractSLTTrxOperation {

	/*
	 * @seecom.integrosys.base.businfra.transaction.AbstractTrxOperation#
	 * getOperationName()
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_CREATE_APPROVE_COMMODITY_MAIN;
	}

	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ISubLimitTypeTrxValue trxValue = super.getSLTTrxValue(anITrxValue);
		trxValue = createActualSLT(trxValue);
		trxValue = super.updateTransaction(trxValue);
		DefaultLogger.debug(this, "Status : " + trxValue.getStatus());
		return super.prepareResult(trxValue);
	}

	private ISubLimitTypeTrxValue createActualSLT(ISubLimitTypeTrxValue anSLTTrxValue) throws TrxOperationException {
		DefaultLogger.debug(this, "createActualProfile - Begin.");
		try {
			ISubLimitType[] staging = anSLTTrxValue.getStagingSubLimitTypes();
			ISubLimitType[] actual = (ISubLimitType[]) getBusManager().createInfo(staging);
			String refId = String.valueOf(actual[0].getSubLimitTypeID());
			anSLTTrxValue.setSubLimitTypes(actual);
			anSLTTrxValue.setReferenceID(refId);
			anSLTTrxValue.setStagingReferenceID(refId);
			DefaultLogger.debug(this, "RefId : " + refId);
			return anSLTTrxValue;
		}
		catch (CommodityException e) {
			throw new TrxOperationException(e);
		}
		finally {
			DefaultLogger.debug(this, "createActualProfile - End.");
		}
	}
}
