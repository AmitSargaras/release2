package com.integrosys.cms.app.contractfinancing.bus;

import java.util.Date;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class OBTNC implements ITNC {

	private long tncID = ICMSConstant.LONG_INVALID_VALUE;

	// private long contractID = ICMSConstant.LONG_INVALID_VALUE;
	private String terms;

	private String termsOthers;

	private Date tncDate;

	private String conditions;

	private String status;

	private String remarks;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	private boolean isDeleted;

	/**
	 * Default Constructor
	 */
	public OBTNC() {
	}

	public long getTncID() {
		return tncID;
	}

	public void setTncID(long tncID) {
		this.tncID = tncID;
	}

	// public long getContractID() {
	// return contractID;
	// }
	//
	// public void setContractID(long contractID) {
	// this.contractID = contractID;
	// }

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getTermsOthers() {
		return termsOthers;
	}

	public void setTermsOthers(String termsOthers) {
		this.termsOthers = termsOthers;
	}

	public Date getTncDate() {
		return tncDate;
	}

	public void setTncDate(Date tncDate) {
		this.tncDate = tncDate;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	// ========================
	// Common Methods
	// ========================
	public long getCommonRef() {
		return commonRef;
	}

	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
