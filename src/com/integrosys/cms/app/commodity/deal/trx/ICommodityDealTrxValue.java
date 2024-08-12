/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/trx/ICommodityDealTrxValue.java,v 1.5 2004/07/10 07:04:51 wltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.trx;

import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface defines transaction data for use with CMS. It also contains
 * commodity deal entity.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.5 $
 * @since $Date: 2004/07/10 07:04:51 $ Tag: $Name: $
 */
public interface ICommodityDealTrxValue extends ICMSTrxValue {
	/**
	 * Get actual deal contained in this transaction.
	 * 
	 * @return object of commodity deal type
	 */
	public ICommodityDeal getCommodityDeal();

	/**
	 * Set actual deal to this transaction.
	 * 
	 * @param deal of type ICommodityDeal
	 */
	public void setCommodityDeal(ICommodityDeal deal);

	/**
	 * Get staging deal contained in this transaction.
	 * 
	 * @return staging commodity deal object
	 */
	public ICommodityDeal getStagingCommodityDeal();

	/**
	 * Set staging deal to this transaction.
	 * 
	 * @param stagingDeal of type ICommodityDeal
	 */
	public void setStagingCommodityDeal(ICommodityDeal stagingDeal);

	/**
	 * Check if the transaction value includes actual and staging deal values.
	 * 
	 * @return boolean
	 */
	public boolean isIncludeDealInfo();

	/**
	 * set if the transaction value to include actual and staging deal values.
	 * 
	 * @param includeDealInfo of type boolean
	 */
	public void setIncludeDealInfo(boolean includeDealInfo);

	/**
	 * Check if the transaction value includes a list of transaction history.
	 * 
	 * @return boolean
	 */
	public boolean isIncludeHistory();

	/**
	 * Check if the transaction value includes a list of transaction history.
	 * 
	 * @param includeHistory of type boolean
	 */
	public void setIncludeHistory(boolean includeHistory);

	/**
	 * Gets the collateral the deal is associated to.
	 * 
	 * @return ICommodityCollateral
	 */
	public ICommodityCollateral getCommodityCollateral();

	/**
	 * SEts the collateral the deal is associated to.
	 * 
	 * @param collateral
	 */
	public void setCommodityCollateral(ICommodityCollateral collateral);
}
