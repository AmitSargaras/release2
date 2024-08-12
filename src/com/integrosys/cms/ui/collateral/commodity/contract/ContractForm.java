/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/contract/ContractForm.java,v 1.3 2004/08/30 12:28:16 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.contract;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/30 12:28:16 $ Tag: $Name: $
 */

public class ContractForm extends CommonForm implements Serializable {
	private String securityID = "";

	private String securitySubType = "";

	private String productType = "";

	private String productSubType = "";

	private String supplier = "";

	private String contractMaturityDate = "";

	private String minShippingFreq = "";

	private String minShippingFreqUOM = "";

	private String lastShipmentDate = "";

	private String mainContractNo = "";

	private String mainContractPrice = "";

	private String mainContractPriceCcy = "";

	private String mainContractAmt = "";

	private String mainContractAmtCcy = "";

	private String contractQty = "";

	private String contractUOM = "";

	private String contractRemarks = "";

	private String diffQuantitysign = "";

	private String quantityDiff = "";

	public String getSecurityID() {
		return this.securityID;
	}

	public void setSecurityID(String securityID) {
		this.securityID = securityID;
	}

	public String getSecuritySubType() {
		return this.securitySubType;
	}

	public void setSecuritySubType(String securitySubType) {
		this.securitySubType = securitySubType;
	}

	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductSubType() {
		return this.productSubType;
	}

	public void setProductSubType(String productSubType) {
		this.productSubType = productSubType;
	}

	public String getContractMaturityDate() {
		return this.contractMaturityDate;
	}

	public void setContractMaturityDate(String contractMaturityDate) {
		this.contractMaturityDate = contractMaturityDate;
	}

	public String getSupplier() {
		return this.supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getMinShippingFreq() {
		return minShippingFreq;
	}

	public void setMinShippingFreq(String minShippingFreq) {
		this.minShippingFreq = minShippingFreq;
	}

	public String getMinShippingFreqUOM() {
		return minShippingFreqUOM;
	}

	public void setMinShippingFreqUOM(String minShippingFreqUOM) {
		this.minShippingFreqUOM = minShippingFreqUOM;
	}

	public String getQuantityDiff() {
		return this.quantityDiff;
	}

	public void setQuantityDiff(String quantityDiff) {
		this.quantityDiff = quantityDiff;
	}

	public String getMainContractNo() {
		return this.mainContractNo;
	}

	public void setMainContractNo(String mainContractNo) {
		this.mainContractNo = mainContractNo;
	}

	public String getMainContractPrice() {
		return mainContractPrice;
	}

	public void setMainContractPrice(String mainContractPrice) {
		this.mainContractPrice = mainContractPrice;
	}

	public String getMainContractPriceCcy() {
		return mainContractPriceCcy;
	}

	public void setMainContractPriceCcy(String mainContractPriceCcy) {
		this.mainContractPriceCcy = mainContractPriceCcy;
	}

	public String getMainContractAmt() {
		return this.mainContractAmt;
	}

	public void setMainContractAmt(String mainContractAmt) {
		this.mainContractAmt = mainContractAmt;
	}

	public String getMainContractAmtCcy() {
		return mainContractAmtCcy;
	}

	public void setMainContractAmtCcy(String mainContractAmtCcy) {
		this.mainContractAmtCcy = mainContractAmtCcy;
	}

	public String getLastShipmentDate() {
		return this.lastShipmentDate;
	}

	public void setLastShipmentDate(String lastShipmentDate) {
		this.lastShipmentDate = lastShipmentDate;
	}

	public String getContractQty() {
		return contractQty;
	}

	public void setContractQty(String contractQty) {
		this.contractQty = contractQty;
	}

	public String getContractUOM() {
		return contractUOM;
	}

	public void setContractUOM(String contractUOM) {
		this.contractUOM = contractUOM;
	}

	public String getContractRemarks() {
		return this.contractRemarks;
	}

	public void setContractRemarks(String contractRemarks) {
		this.contractRemarks = contractRemarks;
	}

	public String getDiffQuantitysign() {
		return this.diffQuantitysign;
	}

	public void setDiffQuantitysign(String diffQuantitysign) {
		this.diffQuantitysign = diffQuantitysign;
	}

	public String[][] getMapper() {
		String[][] input = { { "contractObj", "com.integrosys.cms.ui.collateral.commodity.contract.ContractMapper" }, };
		return input;
	}
}
