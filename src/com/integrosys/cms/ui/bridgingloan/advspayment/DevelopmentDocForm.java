/*
Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan.advspayment;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * ActionForm for DevelopmentDocAction.
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $String: $ Tag: $Name: $
 */
public class DevelopmentDocForm extends TrxContextForm implements Serializable {
	private String progressStage = "";

	private String docName = "";

	private String docRef = "";

	private String receiveDate = "";

	private String docDate = "";

	private String remarks = "";

	public String getProgressStage() {
		return progressStage;
	}

	public void setProgressStage(String progressStage) {
		this.progressStage = progressStage;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocRef() {
		return docRef;
	}

	public void setDocRef(String docRef) {
		this.docRef = docRef;
	}

	public String getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getDocDate() {
		return docDate;
	}

	public void setDocDate(String docDate) {
		this.docDate = docDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String[][] getMapper() {
		String[][] input = {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue" },
				{ "objBridgingLoan", "com.integrosys.cms.ui.bridgingloan.advspayment.DevelopmentDocMapper" }, };
		return input;
	}
}
