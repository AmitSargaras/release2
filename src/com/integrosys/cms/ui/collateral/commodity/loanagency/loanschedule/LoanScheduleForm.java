/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/loanagency/loanschedule/LoanScheduleForm.java,v 1.2 2004/06/22 12:42:34 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.loanagency.loanschedule;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/22 12:42:34 $ Tag: $Name: $
 */
public class LoanScheduleForm extends CommonForm implements Serializable {

	private String[] paymentDate = new String[0];

	private String[] principalAmtDue = new String[0];

	private String[] interestAmtDue = new String[0];

	private String[] totalPaymentDue = new String[0];

	public String[] getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String[] paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String[] getPrincipalAmtDue() {
		return principalAmtDue;
	}

	public void setPrincipalAmtDue(String[] principalAmtDue) {
		this.principalAmtDue = principalAmtDue;
	}

	public String[] getInterestAmtDue() {
		return interestAmtDue;
	}

	public void setInterestAmtDue(String[] interestAmtDue) {
		this.interestAmtDue = interestAmtDue;
	}

	public String[] getTotalPaymentDue() {
		return totalPaymentDue;
	}

	public void setTotalPaymentDue(String[] totalPaymentDue) {
		this.totalPaymentDue = totalPaymentDue;
	}

	public String[][] getMapper() {
		String[][] input = { { "loanScheduleObj",
				"com.integrosys.cms.ui.collateral.commodity.loanagency.loanschedule.LoanScheduleMapper" }, };
		return input;
	}
}
