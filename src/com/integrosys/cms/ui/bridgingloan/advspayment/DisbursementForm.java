/*
Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan.advspayment;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * ActionForm for DisbursementAction.
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $String: $ Tag: $Name: $
 */
public class DisbursementForm extends TrxContextForm implements Serializable {

	private String purpose = "";

	private String disRemarks = "";

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getDisRemarks() {
		return disRemarks;
	}

	public void setDisRemarks(String disRemarks) {
		this.disRemarks = disRemarks;
	}

	public String[][] getMapper() {
		String[][] input = {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.ui.bridgingloan.advspayment.DisbursementMapper" },
				{ "objBridgingLoan", "com.integrosys.cms.ui.bridgingloan.advspayment.DisbursementMapper" }, };
		return input;
	}
}