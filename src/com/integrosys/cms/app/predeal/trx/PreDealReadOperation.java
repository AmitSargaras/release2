/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealReadOperation
 *
 * Created on 11:51:10 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.predeal.bus.IPreDeal;
import com.integrosys.cms.app.predeal.bus.SBPreDealBusManager;
import com.integrosys.cms.app.predeal.bus.SBPreDealBusManagerHome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 26, 2007 Time: 11:51:10 AM
 */
public class PreDealReadOperation extends CMSTrxOperation implements ITrxReadOperation {

	public String getOperationName() {
		return ICMSConstant.ACTION_READ_PRE_DEAL;
	}

	public ITrxValue getTransaction(ITrxValue value) throws TransactionException {
		try {
			ICMSTrxValue trxValue = getTrxManager().getTrxByRefIDAndTrxType(value.getTransactionID(),
					ICMSConstant.INSTANCE_PRE_DEAL);

			OBPreDealTrxValue newValue = new OBPreDealTrxValue(trxValue);

			String stagingRef = trxValue.getStagingReferenceID();
			String actualRef = trxValue.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference : " + actualRef + " , Staging Reference : " + stagingRef);

			if (stagingRef != null) {
				IPreDeal stagingData = getSBStagingPreDealBusManager().getByEarMarkId(stagingRef);

				newValue.setStagingPreDeal(stagingData);
			}

			if (actualRef != null) {
				IPreDeal actualData = getSBPreDealBusManager().getByEarMarkId(actualRef);

				newValue.setPreDeal(actualData);
			}

			return newValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
	}

	private SBPreDealBusManager getSBPreDealBusManager() {
		SBPreDealBusManager busMgr = (SBPreDealBusManager) BeanController.getEJB(ICMSJNDIConstant.SB_PRE_DEAL_BUS_JNDI,
				SBPreDealBusManagerHome.class.getName());

		if (busMgr == null) {
			DefaultLogger.debug(this, "Business Bean is null");
		}

		return busMgr;
	}

	private SBPreDealBusManager getSBStagingPreDealBusManager() {
		SBPreDealBusManager busMgr = (SBPreDealBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_PRE_DEAL_BUS_JNDI, SBPreDealBusManagerHome.class.getName());

		if (busMgr == null) {
			DefaultLogger.debug(this, "Staging Business Bean is null");
		}

		return busMgr;
	}

}