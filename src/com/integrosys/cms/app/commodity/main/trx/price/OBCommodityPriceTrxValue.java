/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/price/OBCommodityPriceTrxValue.java,v 1.3 2006/03/03 05:21:41 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.price;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.main.bus.price.ICommodityPrice;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * This class defines transaction data for use with CMS.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2006/03/03 05:21:41 $ Tag: $Name: $
 */
public class OBCommodityPriceTrxValue extends OBCMSTrxValue implements ICommodityPriceTrxValue {
	private ICommodityPrice[] price;

	private ICommodityPrice[] stagingPrice;

	private String commodityCategoryCode;

	private String commodityProdTypeCode;

	private String commodityRICType;

	/**
	 * Default Constructor
	 */
	public OBCommodityPriceTrxValue() {
		super();
		setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_PRICE);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type ICommodityPriceTrxValue
	 */
	public OBCommodityPriceTrxValue(ICMSTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get actual commodity prices contained in this transaction.
	 * 
	 * @return object of type ICommodityPrice[]
	 */
	public ICommodityPrice[] getCommodityPrice() {
		return price;
	}

	/**
	 * Set actual commodity prices to this transaction.
	 * 
	 * @param price of type ICommodityPrice[]
	 */
	public void setCommodityPrice(ICommodityPrice[] price) {
		this.price = price;
	}

	/**
	 * Get staging commodity prices list contained in this transaction.
	 * 
	 * @return staging commodity price objects
	 */
	public ICommodityPrice[] getStagingCommodityPrice() {
		return stagingPrice;
	}

	/**
	 * Set staging commodity price list to this transaction.
	 * 
	 * @param stagingPrice of type ICommodityPrice[]
	 */
	public void setStagingCommodityPrice(ICommodityPrice[] stagingPrice) {
		this.stagingPrice = stagingPrice;
	}

	/**
	 * Get commodity category code.
	 * 
	 * @return String
	 */
	public String getCommodityCategoryCode() {
		return commodityCategoryCode;
	}

	/**
	 * Set commodity category code.
	 * 
	 * @param commodityCategoryCode of type String
	 */
	public void setCommodityCategoryCode(String commodityCategoryCode) {
		this.commodityCategoryCode = commodityCategoryCode;
	}

	/**
	 * Get commodity product type code.
	 * 
	 * @return String
	 */
	public String getCommodityProdTypeCode() {
		return commodityProdTypeCode;
	}

	/**
	 * Set commodity product type code.
	 * 
	 * @param commodityProdTypeCode of type String
	 */
	public void setCommodityProdTypeCode(String commodityProdTypeCode) {
		this.commodityProdTypeCode = commodityProdTypeCode;
	}

	public String getCommodityRICType() {
		return commodityRICType;
	}

	public void setCommodityRICType(String commodityRICType) {
		this.commodityRICType = commodityRICType;
	}
}