package com.integrosys.cms.ui.creditriskparam.internallimit.list;

import com.integrosys.cms.ui.common.TrxContextForm;

public class InternalLimitListForm extends TrxContextForm implements
		java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String description[];

	private String internalLimitPercentage[];

	private String currency[];

	private String capitalFundAmount[];

	private String status[];

	public String[] getCapitalFundAmount() {
		return capitalFundAmount;
	}

	public void setCapitalFundAmount(String[] capitalFundAmount) {
		this.capitalFundAmount = capitalFundAmount;
	}

	public String[] getCurrency() {
		return currency;
	}

	public void setCurrency(String[] currency) {
		this.currency = currency;
	}

	public String[] getDescription() {
		return description;
	}

	public void setDescription(String[] description) {
		this.description = description;
	}

	public String[] getInternalLimitPercentage() {
		return internalLimitPercentage;
	}

	public void setInternalLimitPercentage(String[] internalLimitPercentage) {
		this.internalLimitPercentage = internalLimitPercentage;
	}

	public String[] getStatus() {
		return status;
	}

	public void setStatus(String[] status) {
		this.status = status;
	}

	public String[][] getMapper() {
		return new String[][] {
				{ "theOBTrxContext",
						"com.integrosys.cms.ui.common.TrxContextMapper" },
				{
						"theILParamList",
						"com.integrosys.cms.ui.creditriskparam.internallimit.list.InternalLimitListMapper" } };
	}
}