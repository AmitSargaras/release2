/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityprice/CommodityPriceForm.java,v 1.3 2004/06/15 04:12:48 hshii Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityprice;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/06/15 04:12:48 $ Tag: $Name: $
 */

public class CommodityPriceForm extends TrxContextForm implements Serializable {

	private String category = "";

	private String commodityType = "";

	private String[] commodityProduct;

	private String[] priceType;

	private String[] ric;

	private String[] priceUOM;

	private String[] closePriceCcy;

	private String[] closePriceAmt;

	private String[] closeUpdateDate;

	private String[] currentPriceCcy;

	private String[] currentPriceAmt;

	private String[] currentUpdateDate;

	private String[] updateCheck;

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCommodityType() {
		return this.commodityType;
	}

	public void setCommodityType(String commodityType) {
		this.commodityType = commodityType;
	}

	public String[] getCommodityProduct() {
		return commodityProduct;
	}

	public void setCommodityProduct(String[] commodityProduct) {
		this.commodityProduct = commodityProduct;
	}

	public String[] getPriceType() {
		return priceType;
	}

	public void setPriceType(String[] priceType) {
		this.priceType = priceType;
	}

	public String[] getRic() {
		return ric;
	}

	public void setRic(String[] ric) {
		this.ric = ric;
	}

	public String[] getPriceUOM() {
		return priceUOM;
	}

	public void setPriceUOM(String[] priceUOM) {
		this.priceUOM = priceUOM;
	}

	public String[] getClosePriceCcy() {
		return closePriceCcy;
	}

	public void setClosePriceCcy(String[] closePriceCcy) {
		this.closePriceCcy = closePriceCcy;
	}

	public String[] getClosePriceAmt() {
		return closePriceAmt;
	}

	public void setClosePriceAmt(String[] closePriceAmt) {
		this.closePriceAmt = closePriceAmt;
	}

	public String[] getCloseUpdateDate() {
		return closeUpdateDate;
	}

	public void setCloseUpdateDate(String[] closeUpdateDate) {
		this.closeUpdateDate = closeUpdateDate;
	}

	public String[] getCurrentPriceCcy() {
		return currentPriceCcy;
	}

	public void setCurrentPriceCcy(String[] currentPriceCcy) {
		this.currentPriceCcy = currentPriceCcy;
	}

	public String[] getCurrentPriceAmt() {
		return currentPriceAmt;
	}

	public void setCurrentPriceAmt(String[] currentPriceAmt) {
		this.currentPriceAmt = currentPriceAmt;
	}

	public String[] getCurrentUpdateDate() {
		return currentUpdateDate;
	}

	public void setCurrentUpdateDate(String[] currentUpdateDate) {
		this.currentUpdateDate = currentUpdateDate;
	}

	public String[] getUpdateCheck() {
		return this.updateCheck;
	}

	public void setUpdateCheck(String[] updateCheck) {
		this.updateCheck = updateCheck;
	}

	public String[][] getMapper() {
		String[][] input = { { "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "commodityPriceObj", "com.integrosys.cms.ui.commodityglobal.commodityprice.CommodityPriceMapper" }, };
		return input;
	}
}
