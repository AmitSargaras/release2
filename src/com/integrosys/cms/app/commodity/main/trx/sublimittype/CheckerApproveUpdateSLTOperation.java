/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/sublimittype/CheckerApproveUpdateSLTOperation.java,v 1.1 2005/10/06 05:08:56 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.sublimittype;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-22
 * @Tag com.integrosys.cms.app.commodity.main.trx.sublimittype.
 *      CheckerApproveUpdateSLTOperation.java
 */
public class CheckerApproveUpdateSLTOperation extends AbstractSLTTrxOperation {

	/*
	 * @seecom.integrosys.base.businfra.transaction.AbstractTrxOperation#
	 * getOperationName()
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_UPDATE_APPROVE_COMMODITY_MAIN;
	}

	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ISubLimitTypeTrxValue trxValue = super.getSLTTrxValue(anITrxValue);
		trxValue = updateActualSLT(trxValue);
		trxValue = super.updateTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}
