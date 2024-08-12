package com.integrosys.cms.ui.checklist.paripassu;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

public class MaintainPariPassuCheckListForm extends TrxContextForm implements Serializable{
	public long id;
	public String bankName;
	public String Action;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAction() {
		return Action;
	}
	public void setAction(String action) {
		Action = action;
	}
}
