package com.integrosys.cms.ui.limit.facility.main;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.ui.common.TrxContextForm;

public class FacilityMainForm extends TrxContextForm {

	// list.jsp page
	private String limitId;

	private String limitFacCode;

	private String limitFacSeq;

	private String limitAmount;

	public String getLimitId() {
		return limitId;
	}

	public String[][] getMapper() {
		return new String[][] { { "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}

	public void setLimitId(String limitId) {
		this.limitId = limitId;
	}

	public String getLimitFacCode() {
		return limitFacCode;
	}

	public void setLimitFacCode(String limitFacCode) {
		this.limitFacCode = limitFacCode;
	}

	public String getLimitFacSeq() {
		return limitFacSeq;
	}

	public void setLimitFacSeq(String limitFacSeq) {
		this.limitFacSeq = limitFacSeq;
	}

	public String getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(String limitAmount) {
		this.limitAmount = limitAmount;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
