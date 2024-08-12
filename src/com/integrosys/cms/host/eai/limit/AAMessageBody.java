package com.integrosys.cms.host.eai.limit;

import java.util.Vector;

import com.integrosys.cms.host.eai.EAIBody;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;

/**
 * Message body to hold all the information related to a Loan Application, such
 * as AA, Joint Borrower, Limit, Limit Security Maps, Charge Details
 * @author marvin
 * @author Chong Jun Yong
 */
public class AAMessageBody extends EAIBody implements java.io.Serializable {

	private static final long serialVersionUID = 7680475541960356576L;

	private LimitProfile limitProfile;

	private Vector limits;

	private Vector creditGrade;

	private Vector limitsSystemXReferenceMap;

	private Vector limitsApprovedSecurityMap;

	private Vector limitsXReferenceMap;

	private Vector chargeDetail;

	private Vector jointBorrower;

	public AAMessageBody() {
		super();
	}

	public Vector getChargeDetail() {
		return chargeDetail;
	}

	public Vector getCreditGrade() {
		return creditGrade;
	}

	public Vector getJointBorrower() {
		return jointBorrower;
	}

	public LimitProfile getLimitProfile() {
		return limitProfile;
	}

	public Vector getLimits() {
		return limits;
	}

	public Vector getLimitsApprovedSecurityMap() {
		return limitsApprovedSecurityMap;
	}

	public Vector getLimitsSystemXReferenceMap() {
		return limitsSystemXReferenceMap;
	}

	public Vector getLimitsXReferenceMap() {
		return limitsXReferenceMap;
	}

	public void setChargeDetail(Vector chargeDetail) {
		this.chargeDetail = chargeDetail;
	}

	public void setCreditGrade(Vector creditGrade) {
		this.creditGrade = creditGrade;
	}

	public void setJointBorrower(Vector jointBorrower) {
		this.jointBorrower = jointBorrower;
	}

	public void setLimitProfile(LimitProfile limitProfile) {
		this.limitProfile = limitProfile;
	}

	public void setLimits(Vector limits) {
		this.limits = limits;
	}

	public void setLimitsApprovedSecurityMap(Vector limitsApprovedSecurityMap) {
		this.limitsApprovedSecurityMap = limitsApprovedSecurityMap;
	}

	public void setLimitsSystemXReferenceMap(Vector limitsSystemXReferenceMap) {
		this.limitsSystemXReferenceMap = limitsSystemXReferenceMap;
	}

	public void setLimitsXReferenceMap(Vector limitsXReferenceMap) {
		this.limitsXReferenceMap = limitsXReferenceMap;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("AAMessageBody [");
		buf.append("limitProfile=");
		buf.append(limitProfile);
		buf.append(", jointBorrower=");
		buf.append(jointBorrower);
		buf.append(", limits=");
		buf.append(limits);
		buf.append(", limitsApprovedSecurityMap=");
		buf.append(limitsApprovedSecurityMap);
		buf.append(", chargeDetail=");
		buf.append(chargeDetail);
		buf.append("]");
		return buf.toString();
	}

}
