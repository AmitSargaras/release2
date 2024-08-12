/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/trx/OfficerApproveCommodityDealOperation.java,v 1.12 2006/08/11 03:04:43 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.deal.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealBusManagerFactory;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealException;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.OBCommodityDealNotifyInfo;
import com.integrosys.cms.app.transaction.CMSTrxApproveOperation;
import com.integrosys.cms.app.transaction.CMSTrxOperation;

/**
 * This abstract operation class is extended by any operation for officer
 * approval for deal.
 * 
 * @author $Author: hmbao $
 * @version $Revision: 1.12 $
 * @since $Date: 2006/08/11 03:04:43 $ Tag: $Name: $
 */
public abstract class OfficerApproveCommodityDealOperation extends CMSTrxApproveOperation {

	protected abstract CMSTrxOperation getOperation();

	protected abstract ICommodityDeal getDeal(ICommodityDealTrxValue trxValue);

	/**
	 * Behaves in the same manner as CheckApproveCreateCommodityDealOperation.
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ITrxValue updatedValue = super.performProcess(value).getTrxValue();
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
	 * Method to process to process notification.
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
