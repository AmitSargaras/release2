/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/titledoc/TitleDocForm.java,v 1.4 2004/11/18 07:24:44 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.titledoc;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/11/18 07:24:44 $ Tag: $Name: $
 */

public class TitleDocForm extends CommonForm implements Serializable {

	private String processStage = "";

	private String docType = "";

	private String docDesc = "";

	private String eligibilityAdv = "";

	private String secured = "";

	private String[] deleteWarehouse = new String[0];

	private String transactionDate = "";

	// bill lading
	private String shippingCompany = "";

	private String billLadingNo = "";

	private String billLadingDate = "";

	private String billLadingRemarks = "";

	// trust receipt
	private String trNo = "";

	private String billLadingRefNo = "";

	private String trAmtCcy = "";

	private String trAmtVal = "";

	private String trDate = "";

	private String trMaturityDate = "";

	private String trRemarks = "";

	// other document details
	private String documentNo = "";

	private String documentDesc = "";

	private String documentDate = "";

	private String documentDueDate = "";

	private String documentRemarks = "";

	public String getProcessStage() {
		return this.processStage;
	}

	public void setProcessStage(String processStage) {
		this.processStage = processStage;
	}

	public String getDocType() {
		return this.docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDocDesc() {
		return this.docDesc;
	}

	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}

	public String getEligibilityAdv() {
		return this.eligibilityAdv;
	}

	public void setEligibilityAdv(String eligibilityAdv) {
		this.eligibilityAdv = eligibilityAdv;
	}

	public String getSecured() {
		return secured;
	}

	public void setSecured(String secured) {
		this.secured = secured;
	}

	public String[] getDeleteWarehouse() {
		return deleteWarehouse;
	}

	public void setDeleteWarehouse(String[] deleteWarehouse) {
		this.deleteWarehouse = deleteWarehouse;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getShippingCompany() {
		return this.shippingCompany;
	}

	public void setShippingCompany(String shippingCompany) {
		this.shippingCompany = shippingCompany;
	}

	public String getBillLadingNo() {
		return this.billLadingNo;
	}

	public void setBillLadingNo(String billLadingNo) {
		this.billLadingNo = billLadingNo;
	}

	public String getBillLadingDate() {
		return this.billLadingDate;
	}

	public void setBillLadingDate(String billLadingDate) {
		this.billLadingDate = billLadingDate;
	}

	public String getBillLadingRemarks() {
		return this.billLadingRemarks;
	}

	public void setBillLadingRemarks(String billLadingRemarks) {
		this.billLadingRemarks = billLadingRemarks;
	}

	public String getTrNo() {
		return this.trNo;
	}

	public void setTrNo(String trNo) {
		this.trNo = trNo;
	}

	public String getBillLadingRefNo() {
		return this.billLadingRefNo;
	}

	public void setBillLadingRefNo(String billLadingRefNo) {
		this.billLadingRefNo = billLadingRefNo;
	}

	public String getTrAmtCcy() {
		return this.trAmtCcy;
	}

	public void setTrAmtCcy(String trAmtCcy) {
		this.trAmtCcy = trAmtCcy;
	}

	public String getTrAmtVal() {
		return this.trAmtVal;
	}

	public void setTrAmtVal(String trAmtVal) {
		this.trAmtVal = trAmtVal;
	}

	public String getTrDate() {
		return this.trDate;
	}

	public void setTrDate(String trDate) {
		this.trDate = trDate;
	}

	public String getTrMaturityDate() {
		return this.trMaturityDate;
	}

	public void setTrMaturityDate(String trMaturityDate) {
		this.trMaturityDate = trMaturityDate;
	}

	public String getTrRemarks() {
		return this.trRemarks;
	}

	public void setTrRemarks(String trRemarks) {
		this.trRemarks = trRemarks;
	}

	public String getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getDocumentDesc() {
		return this.documentDesc;
	}

	public void setDocumentDesc(String documentDesc) {
		this.documentDesc = documentDesc;
	}

	public String getDocumentDate() {
		return this.documentDate;
	}

	public void setDocumentDate(String documentDate) {
		this.documentDate = documentDate;
	}

	public String getDocumentDueDate() {
		return this.documentDueDate;
	}

	public void setDocumentDueDate(String documentDueDate) {
		this.documentDueDate = documentDueDate;
	}

	public String getDocumentRemarks() {
		return this.documentRemarks;
	}

	public void setDocumentRemarks(String documentRemarks) {
		this.documentRemarks = documentRemarks;
	}

	public String[][] getMapper() {
		String[][] input = { { "titleDocObj",
				"com.integrosys.cms.ui.commoditydeal.commoditydoc.titledoc.TitleDocMapper" }, };
		return input;
	}
}
