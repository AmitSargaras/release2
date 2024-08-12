/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityuom/item/CommodityUOMItemForm.java,v 1.2 2004/06/04 05:11:44 hltan Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityuom.item;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:11:44 $ Tag: $Name: $
 */

public class CommodityUOMItemForm extends CommonForm implements Serializable {
	private String commoditySubType = "";

	private String unitOfMeasure = "";

	private String marketUOMVal = "";

	private String marketUOMUnit = "";

	private String metricUOMVal = "";

	private String metricUOMUnit = "";

	public String getCommoditySubType() {
		return this.commoditySubType;
	}

	public void setCommoditySubType(String commoditySubType) {
		this.commoditySubType = commoditySubType;
	}

	public String getUnitOfMeasure() {
		return this.unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public String getMarketUOMVal() {
		return marketUOMVal;
	}

	public void setMarketUOMVal(String marketUOMVal) {
		this.marketUOMVal = marketUOMVal;
	}

	public String getMarketUOMUnit() {
		return marketUOMUnit;
	}

	public void setMarketUOMUnit(String marketUOMUnit) {
		this.marketUOMUnit = marketUOMUnit;
	}

	public String getMetricUOMVal() {
		return metricUOMVal;
	}

	public void setMetricUOMVal(String metricUOMVal) {
		this.metricUOMVal = metricUOMVal;
	}

	public String getMetricUOMUnit() {
		return metricUOMUnit;
	}

	public void setMetricUOMUnit(String metricUOMUnit) {
		this.metricUOMUnit = metricUOMUnit;
	}

	public String[][] getMapper() {
		String[][] input = { { "commodityUOMItemObj",
				"com.integrosys.cms.ui.commodityglobal.commodityuom.item.CommodityUOMItemMapper" }, };
		return input;
	}

}
