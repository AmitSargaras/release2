/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/financingdoc/FinancingDocForm.java,v 1.3 2005/09/15 06:00:48 czhou Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.financingdoc;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/09/15 06:00:48 $ Tag: $Name: $
 */

public class FinancingDocForm extends CommonForm implements Serializable {
	private String salesDocDesc = "";

	private String salesDocDescOth = "";

	private String descGoods = "";

	private String salesOrderCcy = "";

	private String salesOrderAmt = "";

	private String quantityVal = "";

	private String quantityUOM = "";

	private String salesDocNo = "";

	private String expiryDate = "";

	private String countryExportIssBank = "";

	private String exportLCIssBank = "";

	private String lcReference = "";

	private String lcExpiryDate = "";

	private String locDueDate = ""; // added for CR119

	private String counterParty = "";

	private String termMatched = "";

	private String financingDocRemarks = "";

	public String getSalesDocDesc() {
		return this.salesDocDesc;
	}

	public void setSalesDocDesc(String salesDocDesc) {
		this.salesDocDesc = salesDocDesc;
	}

	public String getSalesDocDescOth() {
		return this.salesDocDescOth;
	}

	public void setSalesDocDescOth(String salesDocDescOth) {
		this.salesDocDescOth = salesDocDescOth;
	}

	public String getDescGoods() {
		return this.descGoods;
	}

	public void setDescGoods(String descGoods) {
		this.descGoods = descGoods;
	}

	public String getSalesOrderCcy() {
		return this.salesOrderCcy;
	}

	public void setSalesOrderCcy(String salesOrderCcy) {
		this.salesOrderCcy = salesOrderCcy;
	}

	public String getSalesOrderAmt() {
		return this.salesOrderAmt;
	}

	public void setSalesOrderAmt(String salesOrderAmt) {
		this.salesOrderAmt = salesOrderAmt;
	}

	public String getQuantityVal() {
		return this.quantityVal;
	}

	public void setQuantityVal(String quantityVal) {
		this.quantityVal = quantityVal;
	}

	public String getQuantityUOM() {
		return this.quantityUOM;
	}

	public void setQuantityUOM(String quantityUOM) {
		this.quantityUOM = quantityUOM;
	}

	public String getSalesDocNo() {
		return this.salesDocNo;
	}

	public void setSalesDocNo(String salesDocNo) {
		this.salesDocNo = salesDocNo;
	}

	public String getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getCountryExportIssBank() {
		return this.countryExportIssBank;
	}

	public void setCountryExportIssBank(String countryExportIssBank) {
		this.countryExportIssBank = countryExportIssBank;
	}

	public String getExportLCIssBank() {
		return this.exportLCIssBank;
	}

	public void setExportLCIssBank(String exportLCIssBank) {
		this.exportLCIssBank = exportLCIssBank;
	}

	public String getLcReference() {
		return this.lcReference;
	}

	public void setLcReference(String lcReference) {
		this.lcReference = lcReference;
	}

	public String getLcExpiryDate() {
		return this.lcExpiryDate;
	}

	public void setLcExpiryDate(String lcExpiryDate) {
		this.lcExpiryDate = lcExpiryDate;
	}

	public String getLocDueDate() {
		return this.locDueDate;
	}

	public void setLocDueDate(String locDueDate) {
		this.locDueDate = locDueDate;
	}

	public String getCounterParty() {
		return this.counterParty;
	}

	public void setCounterParty(String counterParty) {
		this.counterParty = counterParty;
	}

	public String getTermMatched() {
		return termMatched;
	}

	public void setTermMatched(String termMatched) {
		this.termMatched = termMatched;
	}

	public String getFinancingDocRemarks() {
		return this.financingDocRemarks;
	}

	public void setFinancingDocRemarks(String financingDocRemarks) {
		this.financingDocRemarks = financingDocRemarks;
	}

	public String[][] getMapper() {
		String[][] input = { { "financingDocObj",
				"com.integrosys.cms.ui.commoditydeal.commoditydoc.financingdoc.FinancingDocMapper" }, };
		return input;
	}
}
