package com.integrosys.cms.ui.limit.facility.securitydeposit;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainForm;

public class FacilityIslamicSecDepositForm extends FacilityMainForm {
	private static final long serialVersionUID = 1L;
	
	private String numberOfMonth;
	private String securityDeposit;
	private String fixedSecDepositAmt;
	private String originalSecDepositAmt;
	private String recallSDUponRenewalInd;
	private String mthB4PrintRenewalRpt;
	private String remark;
	
	public static final String MAPPER = "com.integrosys.cms.ui.limit.facility.securitydeposit.FacilityIslamicSecDepositMapper";
	
	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}		
	
	public String getNumberOfMonth() {
		return numberOfMonth;
	}

	public void setNumberOfMonth(String numberOfMonth) {
		this.numberOfMonth = numberOfMonth;
	}

	public String getSecurityDeposit() {
		return securityDeposit;
	}

	public void setSecurityDeposit(String securityDeposit) {
		this.securityDeposit = securityDeposit;
	}

	public String getFixedSecDepositAmt() {
		return fixedSecDepositAmt;
	}

	public void setFixedSecDepositAmt(String fixedSecDepositAmt) {
		this.fixedSecDepositAmt = fixedSecDepositAmt;
	}

	public String getOriginalSecDepositAmt() {
		return originalSecDepositAmt;
	}

	public void setOriginalSecDepositAmt(String originalSecDepositAmt) {
		this.originalSecDepositAmt = originalSecDepositAmt;
	}

	public String getRecallSDUponRenewalInd() {
		return recallSDUponRenewalInd;
	}

	public void setRecallSDUponRenewalInd(String recallSDUponRenewalInd) {
		this.recallSDUponRenewalInd = recallSDUponRenewalInd;
	}

	public String getMthB4PrintRenewalRpt() {
		return mthB4PrintRenewalRpt;
	}

	public void setMthB4PrintRenewalRpt(String mthB4PrintRenewalRpt) {
		this.mthB4PrintRenewalRpt = mthB4PrintRenewalRpt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}		
}
