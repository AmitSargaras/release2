/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/purchasesale/PurchaseSaleForm.java,v 1.3 2004/08/06 07:36:36 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance.purchasesale;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/06 07:36:36 $ Tag: $Name: $
 */

public class PurchaseSaleForm extends CommonForm implements Serializable {
	// fields for purchase column
	private String purSupplier = "";

	private String purSupplierOth = "";

	private String purDescGood = "";

	private String purQty = "";

	private String purUnitPriceCcy = "";

	private String purUnitPriceAmt = "";

	private String purTotalVal = "";

	private String purShipmentDate = "";

	private String purExpiryDate = "";

	private String purShipmentFrom = "";

	private String purShipmentTo = "";

	private String purTransportDoc = "";

	private String purPayment = "";

	private String purCorrBank = "";

	private String purIsTTClaim = "";

	private String purTTClaimDay = "";

	private String purRefNo = "";

	private String purRemarks = "";

	// fiels for sales column
	private String salesBuyer = "";

	private String salesBuyerOth = "";

	private String salesDescGood = "";

	private String salesQty = "";

	private String salesQtyUOM = "";

	private String salesUnitPriceCcy = "";

	private String salesUnitPriceAmt = "";

	private String salesTotalVal = "";

	private String salesShipmentDate = "";

	private String salesExpiryDate = "";

	private String salesShipmentFrom = "";

	private String salesShipmentTo = "";

	private String salesTransportDoc = "";

	private String salesPayment = "";

	private String salesCorrBank = "";

	private String salesIsTTClaim = "";

	private String salesTTClaimDay = "";

	private String salesRefNo = "";

	private String salesRemarks = "";

	public String getPurSupplier() {
		return purSupplier;
	}

	public void setPurSupplier(String purSupplier) {
		this.purSupplier = purSupplier;
	}

	public String getPurSupplierOth() {
		return purSupplierOth;
	}

	public void setPurSupplierOth(String purSupplierOth) {
		this.purSupplierOth = purSupplierOth;
	}

	public String getPurDescGood() {
		return purDescGood;
	}

	public void setPurDescGood(String purDescGood) {
		this.purDescGood = purDescGood;
	}

	public String getPurQty() {
		return purQty;
	}

	public void setPurQty(String purQty) {
		this.purQty = purQty;
	}

	public String getPurUnitPriceCcy() {
		return purUnitPriceCcy;
	}

	public void setPurUnitPriceCcy(String purUnitPriceCcy) {
		this.purUnitPriceCcy = purUnitPriceCcy;
	}

	public String getPurUnitPriceAmt() {
		return purUnitPriceAmt;
	}

	public void setPurUnitPriceAmt(String purUnitPriceAmt) {
		this.purUnitPriceAmt = purUnitPriceAmt;
	}

	public String getPurTotalVal() {
		return purTotalVal;
	}

	public void setPurTotalVal(String purTotalVal) {
		this.purTotalVal = purTotalVal;
	}

	public String getPurShipmentDate() {
		return purShipmentDate;
	}

	public void setPurShipmentDate(String purShipmentDate) {
		this.purShipmentDate = purShipmentDate;
	}

	public String getPurExpiryDate() {
		return purExpiryDate;
	}

	public void setPurExpiryDate(String purExpiryDate) {
		this.purExpiryDate = purExpiryDate;
	}

	public String getPurShipmentFrom() {
		return purShipmentFrom;
	}

	public void setPurShipmentFrom(String purShipmentFrom) {
		this.purShipmentFrom = purShipmentFrom;
	}

	public String getPurShipmentTo() {
		return purShipmentTo;
	}

	public void setPurShipmentTo(String purShipmentTo) {
		this.purShipmentTo = purShipmentTo;
	}

	public String getPurTransportDoc() {
		return purTransportDoc;
	}

	public void setPurTransportDoc(String purTransportDoc) {
		this.purTransportDoc = purTransportDoc;
	}

	public String getPurPayment() {
		return purPayment;
	}

	public void setPurPayment(String purPayment) {
		this.purPayment = purPayment;
	}

	public String getPurCorrBank() {
		return purCorrBank;
	}

	public void setPurCorrBank(String purCorrBank) {
		this.purCorrBank = purCorrBank;
	}

	public String getPurIsTTClaim() {
		return purIsTTClaim;
	}

	public void setPurIsTTClaim(String purIsTTClaim) {
		this.purIsTTClaim = purIsTTClaim;
	}

	public String getPurTTClaimDay() {
		return purTTClaimDay;
	}

	public void setPurTTClaimDay(String purTTClaimDay) {
		this.purTTClaimDay = purTTClaimDay;
	}

	public String getPurRefNo() {
		return purRefNo;
	}

	public void setPurRefNo(String purRefNo) {
		this.purRefNo = purRefNo;
	}

	public String getPurRemarks() {
		return purRemarks;
	}

	public void setPurRemarks(String purRemarks) {
		this.purRemarks = purRemarks;
	}

	public String getSalesBuyer() {
		return salesBuyer;
	}

	public void setSalesBuyer(String salesBuyer) {
		this.salesBuyer = salesBuyer;
	}

	public String getSalesBuyerOth() {
		return salesBuyerOth;
	}

	public void setSalesBuyerOth(String salesBuyerOth) {
		this.salesBuyerOth = salesBuyerOth;
	}

	public String getSalesDescGood() {
		return salesDescGood;
	}

	public void setSalesDescGood(String salesDescGood) {
		this.salesDescGood = salesDescGood;
	}

	public String getSalesQty() {
		return salesQty;
	}

	public void setSalesQty(String salesQty) {
		this.salesQty = salesQty;
	}

	public String getSalesQtyUOM() {
		return salesQtyUOM;
	}

	public void setSalesQtyUOM(String salesQtyUOM) {
		this.salesQtyUOM = salesQtyUOM;
	}

	public String getSalesUnitPriceCcy() {
		return salesUnitPriceCcy;
	}

	public void setSalesUnitPriceCcy(String salesUnitPriceCcy) {
		this.salesUnitPriceCcy = salesUnitPriceCcy;
	}

	public String getSalesUnitPriceAmt() {
		return salesUnitPriceAmt;
	}

	public void setSalesUnitPriceAmt(String salesUnitPriceAmt) {
		this.salesUnitPriceAmt = salesUnitPriceAmt;
	}

	public String getSalesTotalVal() {
		return salesTotalVal;
	}

	public void setSalesTotalVal(String salesTotalVal) {
		this.salesTotalVal = salesTotalVal;
	}

	public String getSalesShipmentDate() {
		return salesShipmentDate;
	}

	public void setSalesShipmentDate(String salesShipmentDate) {
		this.salesShipmentDate = salesShipmentDate;
	}

	public String getSalesExpiryDate() {
		return salesExpiryDate;
	}

	public void setSalesExpiryDate(String salesExpiryDate) {
		this.salesExpiryDate = salesExpiryDate;
	}

	public String getSalesShipmentFrom() {
		return salesShipmentFrom;
	}

	public void setSalesShipmentFrom(String salesShipmentFrom) {
		this.salesShipmentFrom = salesShipmentFrom;
	}

	public String getSalesShipmentTo() {
		return salesShipmentTo;
	}

	public void setSalesShipmentTo(String salesShipmentTo) {
		this.salesShipmentTo = salesShipmentTo;
	}

	public String getSalesTransportDoc() {
		return salesTransportDoc;
	}

	public void setSalesTransportDoc(String salesTransportDoc) {
		this.salesTransportDoc = salesTransportDoc;
	}

	public String getSalesPayment() {
		return salesPayment;
	}

	public void setSalesPayment(String salesPayment) {
		this.salesPayment = salesPayment;
	}

	public String getSalesCorrBank() {
		return salesCorrBank;
	}

	public void setSalesCorrBank(String salesCorrBank) {
		this.salesCorrBank = salesCorrBank;
	}

	public String getSalesIsTTClaim() {
		return salesIsTTClaim;
	}

	public void setSalesIsTTClaim(String salesIsTTClaim) {
		this.salesIsTTClaim = salesIsTTClaim;
	}

	public String getSalesTTClaimDay() {
		return salesTTClaimDay;
	}

	public void setSalesTTClaimDay(String salesTTClaimDay) {
		this.salesTTClaimDay = salesTTClaimDay;
	}

	public String getSalesRefNo() {
		return salesRefNo;
	}

	public void setSalesRefNo(String salesRefNo) {
		this.salesRefNo = salesRefNo;
	}

	public String getSalesRemarks() {
		return salesRemarks;
	}

	public void setSalesRemarks(String salesRemarks) {
		this.salesRemarks = salesRemarks;
	}

	public String[][] getMapper() {
		String[][] input = { { "purchaseSaleObj",
				"com.integrosys.cms.ui.commoditydeal.finance.purchasesale.PurchaseSaleMapper" }, };
		return input;
	}
}
