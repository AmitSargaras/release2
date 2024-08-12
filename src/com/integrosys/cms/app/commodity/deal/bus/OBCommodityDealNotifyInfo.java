/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/OBCommodityDealNotifyInfo.java,v 1.3 2004/11/30 03:50:28 wltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/11/30 03:50:28 $ Tag: $Name: $
 */
public class OBCommodityDealNotifyInfo implements ICommodityDealNotifyInfo {

	private String transactionID;

	private ICommodityDeal deal;

	private ICommodityCollateral commodity;

	private String originatingCountry;

	private long customerID = ICMSConstant.LONG_INVALID_VALUE;

	private String customerName;

	private String operationName;

	private long trxUserID = ICMSConstant.LONG_INVALID_VALUE;

	private long trxUserTeamID = ICMSConstant.LONG_INVALID_VALUE;

	public OBCommodityDealNotifyInfo() {
	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public ICommodityDeal getDeal() {
		return deal;
	}

	public void setDeal(ICommodityDeal deal) {
		this.deal = deal;
	}

	public ICommodityCollateral getCommodity() {
		return commodity;
	}

	public void setCommodity(ICommodityCollateral commodity) {
		this.commodity = commodity;
	}

	public String getOriginatingCountry() {
		return originatingCountry;
	}

	public void setOriginatingCountry(String originatingCountry) {
		this.originatingCountry = originatingCountry;
	}

	public long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public long getTrxUserID() {
		return trxUserID;
	}

	public void setTrxUserID(long trxUserID) {
		this.trxUserID = trxUserID;
	}

	public long getTrxUserTeamID() {
		return trxUserTeamID;
	}

	public void setTrxUserTeamID(long trxUserTeamID) {
		this.trxUserTeamID = trxUserTeamID;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof OBCommodityDealNotifyInfo)) {
			return false;
		}

		final OBCommodityDealNotifyInfo obCommodityDealNotifyInfo = (OBCommodityDealNotifyInfo) o;

		if (customerID != obCommodityDealNotifyInfo.customerID) {
			return false;
		}
		if (trxUserID != obCommodityDealNotifyInfo.trxUserID) {
			return false;
		}
		if (trxUserTeamID != obCommodityDealNotifyInfo.trxUserTeamID) {
			return false;
		}
		if (commodity != null ? !commodity.equals(obCommodityDealNotifyInfo.commodity)
				: obCommodityDealNotifyInfo.commodity != null) {
			return false;
		}
		if (customerName != null ? !customerName.equals(obCommodityDealNotifyInfo.customerName)
				: obCommodityDealNotifyInfo.customerName != null) {
			return false;
		}
		if (deal != null ? !deal.equals(obCommodityDealNotifyInfo.deal) : obCommodityDealNotifyInfo.deal != null) {
			return false;
		}
		if (operationName != null ? !operationName.equals(obCommodityDealNotifyInfo.operationName)
				: obCommodityDealNotifyInfo.operationName != null) {
			return false;
		}
		if (originatingCountry != null ? !originatingCountry.equals(obCommodityDealNotifyInfo.originatingCountry)
				: obCommodityDealNotifyInfo.originatingCountry != null) {
			return false;
		}
		if (transactionID != null ? !transactionID.equals(obCommodityDealNotifyInfo.transactionID)
				: obCommodityDealNotifyInfo.transactionID != null) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result;
		result = (transactionID != null ? transactionID.hashCode() : 0);
		result = 29 * result + (deal != null ? deal.hashCode() : 0);
		result = 29 * result + (commodity != null ? commodity.hashCode() : 0);
		result = 29 * result + (originatingCountry != null ? originatingCountry.hashCode() : 0);
		result = 29 * result + (int) (customerID ^ (customerID >>> 32));
		result = 29 * result + (customerName != null ? customerName.hashCode() : 0);
		result = 29 * result + (operationName != null ? operationName.hashCode() : 0);
		result = 29 * result + (int) (trxUserID ^ (trxUserID >>> 32));
		result = 29 * result + (int) (trxUserTeamID ^ (trxUserTeamID >>> 32));
		return result;
	}
}
