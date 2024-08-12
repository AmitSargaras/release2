/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/generalinfo/cashdeposit/CashDepositForm.java,v 1.2 2004/06/04 05:07:38 hltan Exp $
 */
package com.integrosys.cms.ui.commoditydeal.generalinfo.cashdeposit;

import java.io.Serializable;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:07:38 $ Tag: $Name: $
 */

public class CashDepositForm extends CommonForm implements Serializable {
	private String typeCashHolding = "";

	private String depositNumber = "";

	private String depositCcy = "";

	private String depositAmt = "";

	private String cashLocation = "";

	private String depositMaturityDate = "";
		
	public String getTypeCashHolding() {
		return typeCashHolding;
	}

	public void setTypeCashHolding(String typeCashHolding) {
		this.typeCashHolding = typeCashHolding;
	}

	public String getDepositNumber() {
		return this.depositNumber;
	}

	public void setDepositNumber(String depositNumber) {
		this.depositNumber = depositNumber;
	}

	public String getDepositCcy() {
		return this.depositCcy;
	}

	public void setDepositCcy(String depositCcy) {
		this.depositCcy = depositCcy;
	}

	public String getDepositAmt() {
		return this.depositAmt;
	}

	public void setDepositAmt(String depositAmt) {
		this.depositAmt = depositAmt;
	}

	public String getCashLocation() {
		return this.cashLocation;
	}

	public void setCashLocation(String cashLocation) {
		this.cashLocation = cashLocation;
	}

	public String getDepositMaturityDate() {
		return this.depositMaturityDate;
	}

	public void setDepositMaturityDate(String depositMaturityDate) {
		this.depositMaturityDate = depositMaturityDate;
	}

	public String[][] getMapper() {
		String[][] input = { { "cashDepositObj",
				"com.integrosys.cms.ui.commoditydeal.generalinfo.cashdeposit.CashDepositMapper" }, };
		return input;
	}
}
