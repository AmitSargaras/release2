/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/price/ICommodityPriceTrxValue.java,v 1.3 2006/03/03 05:21:41 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.price;

import com.integrosys.cms.app.commodity.main.bus.price.ICommodityPrice;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface defines transaction data for use with Commodity Price List
 * maintenance.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2006/03/03 05:21:41 $ Tag: $Name: $
 */
public interface ICommodityPriceTrxValue extends ICMSTrxValue {
	/**
	 * Get actual commodity prices contained in this transaction.
	 * 
	 * @return object of type ICommodityPrice[]
	 */
	public ICommodityPrice[] getCommodityPrice();

	/**
	 * Set actual commodity prices to this transaction.
	 * 
	 * @param price of type ICommodityPrice[]
	 */
	public void setCommodityPrice(ICommodityPrice[] price);

	/**
	 * Get staging commodity prices list contained in this transaction.
	 * 
	 * @return staging commodity price objects
	 */
	public ICommodityPrice[] getStagingCommodityPrice();

	/**
	 * Set staging commodity prices list to this transaction.
	 * 
	 * @param stagingPrice of type ICommodityPrice[]
	 */
	public void setStagingCommodityPrice(ICommodityPrice[] stagingPrice);

	/**
	 * Get commodity category code.
	 * 
	 * @return String
	 */
	public String getCommodityCategoryCode();

	/**
	 * Set commodity category code.
	 * 
	 * @param commodityCategoryCode of type String
	 */
	public void setCommodityCategoryCode(String commodityCategoryCode);

	/**
	 * Get commodity product type code.
	 * 
	 * @return String
	 */
	public String getCommodityProdTypeCode();

	/**
	 * Set commodity product type code.
	 * 
	 * @param commodityProdTypeCode of type String
	 */
	public void setCommodityProdTypeCode(String commodityProdTypeCode);

	public String getCommodityRICType();

	public void setCommodityRICType(String commodityRICType);
}
