package com.integrosys.cms.host.eai.limit.bus;

import java.io.Serializable;

import com.integrosys.cms.host.eai.AbstractCreditGrade;

public class LimitCreditGrade extends AbstractCreditGrade implements Serializable{

	public String getLOSAANumber() {
		return LOSAANumber;
	}

	public void setLOSAANumber(String number) {
		LOSAANumber = number;
	}

	private String LOSAANumber;
	
}
