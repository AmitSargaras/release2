/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/settlementdetails/SettlementForm.java,v 1.7 2004/12/01 13:47:33 wltan Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance.settlementdetails;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2004/12/01 13:47:33 $ Tag: $Name: $
 */

public class SettlementForm extends CommonForm implements Serializable {
	private String originalOutstanding = "";

	private String origianlFaceValueCcy = "";

	private String paymentDate = "";

	private String paymentAmount = "";

	// settlement balance outstanding
	private String balanceOutstanding = "";

	// original payment amount
	private String originalPaymentAmount = "";

	public String getOriginalOutstanding() {
		return originalOutstanding;
	}

	public void setOriginalOutstanding(String originalOutstanding) {
		this.originalOutstanding = originalOutstanding;
	}

	public String getOrigianlFaceValueCcy() {
		return origianlFaceValueCcy;
	}

	public void setOrigianlFaceValueCcy(String origianlFaceValueCcy) {
		this.origianlFaceValueCcy = origianlFaceValueCcy;
	}

	public String getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentAmount() {
		return this.paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getBalanceOutstanding() {
		return balanceOutstanding;
	}

	public void setBalanceOutstanding(String balanceOutstanding) {
		this.balanceOutstanding = balanceOutstanding;
	}

	public String getOriginalPaymentAmount() {
		return this.originalPaymentAmount;
	}

	public void setOriginalPaymentAmount(String paymentAmount) {
		this.originalPaymentAmount = paymentAmount;
	}

	public String[][] getMapper() {
		String[][] input = { { "settlementObj",
				"com.integrosys.cms.ui.commoditydeal.finance.settlementdetails.SettlementMapper" }, };
		return input;
	}
}
