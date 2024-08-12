/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/trx/OBCommodityDealTrxValue.java,v 1.6 2004/07/10 07:04:51 wltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * This class defines transaction data for use with CMS.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/07/10 07:04:51 $ Tag: $Name: $
 */
public class OBCommodityDealTrxValue extends OBCMSTrxValue implements ICommodityDealTrxValue {
	private ICommodityDeal deal;

	private ICommodityDeal stagingDeal;

	private ICommodityCollateral commodity;

	private boolean includeDealInfo = true;

	private boolean includeHistory = false;

	/**
	 * Default Constructor
	 */
	public OBCommodityDealTrxValue() {
		super();
		super.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_DEAL);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type ICommodityDealTrxValue
	 */
	public OBCommodityDealTrxValue(ICMSTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get actual deal contained in this transaction.
	 * 
	 * @return object of commodity deal type
	 */
	public ICommodityDeal getCommodityDeal() {
		return deal;
	}

	/**
	 * Set actual deal to this transaction.
	 * 
	 * @param deal of type ICommodityDeal
	 */
	public void setCommodityDeal(ICommodityDeal deal) {
		this.deal = deal;
	}

	/**
	 * Get staging deal contained in this transaction.
	 * 
	 * @return staging commodity deal object
	 */
	public ICommodityDeal getStagingCommodityDeal() {
		return stagingDeal;
	}

	/**
	 * Set staging deal to this transaction.
	 * 
	 * @param stagingDeal of type ICommodityDeal
	 */
	public void setStagingCommodityDeal(ICommodityDeal stagingDeal) {
		this.stagingDeal = stagingDeal;
	}

	/**
	 * Check if the transaction value includes actual and staging deal values.
	 * 
	 * @return boolean
	 */
	public boolean isIncludeDealInfo() {
		return includeDealInfo;
	}

	/**
	 * set if the transaction value to include actual and staging deal values.
	 * 
	 * @param includeDealInfo of type boolean
	 */
	public void setIncludeDealInfo(boolean includeDealInfo) {
		this.includeDealInfo = includeDealInfo;
	}

	/**
	 * Check if the transaction value includes a list of transaction history.
	 * 
	 * @return boolean
	 */
	public boolean isIncludeHistory() {
		return includeHistory;
	}

	/**
	 * Check if the transaction value includes a list of transaction history.
	 * 
	 * @param includeHistory of type boolean
	 */
	public void setIncludeHistory(boolean includeHistory) {
		this.includeHistory = includeHistory;
	}

	/**
	 * Gets the collateral the deal is associated to.
	 * 
	 * @return ICommodityCollateral
	 */
	public ICommodityCollateral getCommodityCollateral() {
		return commodity;
	}

	/**
	 * SEts the collateral the deal is associated to.
	 * 
	 * @param commodity
	 */
	public void setCommodityCollateral(ICommodityCollateral commodity) {
		this.commodity = commodity;
	}
}