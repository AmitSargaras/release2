/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/trx/FAMConfirmRejectCommodityDealOperation.java,v 1.13 2006/08/11 03:04:43 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.deal.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealBusManagerFactory;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealException;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.OBCommodityDealNotifyInfo;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.CMSTrxRejectOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This abstract operation class is extended by any operation for a FAM officer
 * confirm rejection of deal.
 * 
 * @author $Author: hmbao $
 * @version $Revision: 1.13 $
 * @since $Date: 2006/08/11 03:04:43 $ Tag: $Name: $
 */
public abstract class FAMConfirmRejectCommodityDealOperation extends CMSTrxRejectOperation {

	public String getOperationName() {
		return ICMSConstant.ACTION_FAM_CONFIRM_REJECT_CREATE_DEAL;
	}

	protected abstract CMSTrxOperation getOperation();

	protected abstract ICommodityDeal getDeal(ICommodityDealTrxValue trxValue);

	/**
	 * Behaves in the same manner as CheckerRejectCreateCommodityDealOperation.
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			DefaultLogger.debug(this, "########## FAMConfirmRejectCommodityDealOperation.performProcess ##########");
			ICMSTrxValue updatedValue = (ICMSTrxValue) value;

			// Upon rejection, deal is sent to CMT maker todo's list
			// CMSTrxValue is updated before updateTransaction is called
			// so that the correct value will be displayed in to-track list
			updatedValue.setToAuthGroupTypeId(ICMSConstant.TEAM_TYPE_CMT_MAKER);
			updatedValue.setToAuthGId(ICMSConstant.LONG_INVALID_VALUE);
			updatedValue.setToUserId(ICMSConstant.LONG_INVALID_VALUE);

			ITrxResult result = getOperation().performProcess(updatedValue);
			ICommodityDealTrxValue dealTrxValue = (ICommodityDealTrxValue) value;
			processNotification(getDeal(dealTrxValue), dealTrxValue);

			return result;
		}
		catch (ClassCastException cce) {
			cce.printStackTrace();
			throw new TrxOperationException("Exception caught", cce);
		}
	}

	/**
	 * Method to process to process notification when deal gets rejected.
	 * 
	 * @param deal
	 * @param value
	 */
	protected void processNotification(ICommodityDeal deal, ICommodityDealTrxValue value) throws TrxOperationException {
		try {
			OBCommodityDealNotifyInfo notifyInfo = new OBCommodityDealNotifyInfo();
			notifyInfo.setTransactionID(value.getTransactionID());
			notifyInfo.setCommodity(value.getCommodityCollateral());
			notifyInfo.setDeal(deal);
			// notifyInfo.setCustomerID(value.getCustomerID());
			// notifyInfo.setCustomerName(value.getLegalName());
			notifyInfo.setOriginatingCountry(value.getOriginatingCountry());
			notifyInfo.setOperationName(this.getOperationName());
			notifyInfo.setTrxUserID(value.getUID());
			notifyInfo.setTrxUserTeamID(value.getTeamID());

			CommodityDealBusManagerFactory.getActualCommodityDealBusManager().processNotification(notifyInfo);
		}
		catch (CommodityDealException e) {
			throw new TrxOperationException("CommodityDealException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}
}
