/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/CCCheckListSummary.java,v 1.21 2006/08/14 10:48:17 jzhai Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//ofa
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This class contains the attribute required for the c/c checklist summary
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.21 $
 * @since $Date: 2006/08/14 10:48:17 $ Tag: $Name: $
 */
public class CCCheckListSummary implements ICheckListSummary, Comparable {

	private static final long serialVersionUID = 1728697269042644021L;

	private String custCategory = null;

	private long subProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String legalID = null;

	private String legalName = null;

	private String domicileCtry = null;

	private String orgCode = null;

	private String governLaw = null;

	private String limitBkgLoc = null;

	private String customerSegmentCode = null;

	private long checkListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String checkListStatus = ICMSConstant.STATE_CHECKLIST_NEW;

	private String trxID = null;

	private String trxStatus = null;

	private String trxFromState = null;

	private boolean sameCountryInd = false;

	private String pledgorReference = null;

	private boolean allowDeleteInd = false;

	private ICheckListTrxValue trxValue;

	private String applicationType = null;

	/*
	 * Checklist reference ID that will displayed to end-users. Will always be
	 * the actual checklist ID
	 */
	private long checklistReferenceID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String disableTaskInd = null;

	private ICMSTrxValue taskTrx;

	public boolean getAllowDeleteInd() {
		return this.allowDeleteInd;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public long getCheckListID() {
		return this.checkListID;
	}

	/**
	 * Get checklist reference ID.
	 * 
	 * @return long
	 */
	public long getCheckListReferenceID() {
		return checklistReferenceID;
	}

	public String getCheckListStatus() {
		return this.checkListStatus;
	}

	public String getCustCategory() {
		return this.custCategory;
	}

	public String getCustomerSegmentCode() {
		return this.customerSegmentCode;
	}

	public String getDisableTaskInd() {
		return this.disableTaskInd;
	}

	public String getDomicileCtry() {
		return this.domicileCtry;
	}

	public String getGovernLaw() {
		return this.governLaw;
	}

	public String getLegalConstitution() {
		return getCustomerSegmentCode();
	}

	public String getLegalID() {
		return this.legalID;
	}

	public String getLegalName() {
		return this.legalName;
	}

	public String getLimitBkgLoc() {
		// return this.limitBkgLoc;
		return getDomicileCtry();
	}

	public String getOrgCode() {
		return this.orgCode;
	}

	public String getPledgorReference() {
		return this.pledgorReference;
	}

	public boolean getSameCountryInd() {
		return this.sameCountryInd;
	}

	public long getSubProfileID() {
		return this.subProfileID;
	}

	public ICMSTrxValue getTaskTrx() {
		return this.taskTrx;
	}

	public String getTrxFromState() {
		return this.trxFromState;
	}

	public String getTrxID() {
		return this.trxID;
	}

	public String getTrxStatus() {
		return this.trxStatus;
	}

	/**
	 * Get checklist transaction value.
	 * 
	 * @return ICheckListTrxValue
	 */
	public ICheckListTrxValue getTrxValue() {
		return trxValue;
	}

	public boolean isEditable() {
		if (!getSameCountryInd()) {
			return false;
		}
		if ((ICMSConstant.STATE_CHECKLIST_IN_PROGRESS.equals(getCheckListStatus()))
				|| (ICMSConstant.STATE_CHECKLIST_NEW.equals(getCheckListStatus()))) {
			return true;
		}
		return false;
	}

	public void setAllowDeleteInd(boolean anAllowDeleteInd) {
		this.allowDeleteInd = anAllowDeleteInd;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public void setCheckListID(long aCheckListID) {
		this.checkListID = aCheckListID;
	}

	/**
	 * Set checklist reference ID.
	 * 
	 * @param refID of type long
	 */
	public void setCheckListReferenceID(long refID) {
		this.checklistReferenceID = refID;
	}

	public void setCheckListStatus(String aCheckListStatus) {
		this.checkListStatus = aCheckListStatus;
	}

	public void setCustCategory(String aCustCategory) {
		this.custCategory = aCustCategory;
	}

	public void setCustomerSegmentCode(String aCustomerSegmentCode) {
		this.customerSegmentCode = aCustomerSegmentCode;
	}

	public void setDisableTaskInd(String aDisableTaskInd) {
		this.disableTaskInd = aDisableTaskInd;
	}

	public void setDomicileCtry(String aDomicileCtry) {
		this.domicileCtry = aDomicileCtry;
	}

	public void setGovernLaw(String aGovernLaw) {
		this.governLaw = aGovernLaw;
	}

	public void setLegalID(String aLegalID) {
		this.legalID = aLegalID;
	}

	public void setLegalName(String aLegalName) {
		this.legalName = aLegalName;
	}

	public void setLimitBkgLoc(String aLimitBkgLoc) {
		this.limitBkgLoc = aLimitBkgLoc;
	}

	public void setOrgCode(String anOrgCode) {
		this.orgCode = anOrgCode;
	}

	public void setPledgorReference(String aPledgorReference) {
		this.pledgorReference = aPledgorReference;
	}

	public void setSameCountryInd(boolean aSameCountryInd) {
		this.sameCountryInd = aSameCountryInd;
	}

	public void setSubProfileID(long aSubProfileID) {
		this.subProfileID = aSubProfileID;
	}

	public void setTaskTrx(ICMSTrxValue taskStageTrx) {
		this.taskTrx = taskStageTrx;
	}

	public void setTrxFromState(String fromState) {
		this.trxFromState = fromState;
	}

	public void setTrxID(String aTrxID) {
		this.trxID = aTrxID;
	}

	public void setTrxStatus(String aTrxStatus) {
		this.trxStatus = aTrxStatus;
	}

	/**
	 * Set checklist transaction value.
	 * 
	 * @param trxValue of type ICheckListTrxValue
	 */
	public void setTrxValue(ICheckListTrxValue trxValue) {
		this.trxValue = trxValue;
	}

	private int getCustCategoryNo() {
		if (ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(getCustCategory())) {
			return 1;
		}
		if (ICMSConstant.CHECKLIST_CO_BORROWER.equals(getCustCategory())) {
			return 2;
		}
		if (ICMSConstant.CHECKLIST_JOINT_BORROWER.equals(getCustCategory())) {
			return 3;
		}
		if (ICMSConstant.CHECKLIST_PLEDGER.equals(getCustCategory())) {
			return 4;
		}
		return 0;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("CCCheckListSummary [");
		buf.append("checkListID=");
		buf.append(checkListID);
		buf.append(", legalID=");
		buf.append(legalID);
		buf.append(", legalName=");
		buf.append(legalName);
		buf.append(", applicationType=");
		buf.append(applicationType);
		buf.append(", custCategory=");
		buf.append(custCategory);
		buf.append(", checkListStatus=");
		buf.append(checkListStatus);
		buf.append(", allowDeleteInd=");
		buf.append(allowDeleteInd);
		buf.append(", governLaw=");
		buf.append(governLaw);
		buf.append(", domicileCtry=");
		buf.append(domicileCtry);
		buf.append(", limitBkgLoc=");
		buf.append(limitBkgLoc);
		buf.append(", orgCode=");
		buf.append(orgCode);
		buf.append(", pledgorReference=");
		buf.append(pledgorReference);
		buf.append(", sameCountryInd=");
		buf.append(sameCountryInd);
		buf.append(", subProfileID=");
		buf.append(subProfileID);
		buf.append("]");
		return buf.toString();
	}

	public int compareTo(Object that) {
		CCCheckListSummary other = (CCCheckListSummary) that;
		int otherCustCategoryNo = (other == null) ? Integer.MAX_VALUE : other.getCustCategoryNo();
		String otherLegalID = (other == null) ? null : other.getLegalID();

		int result = (getCustCategoryNo() == otherCustCategoryNo) ? 0
				: ((getCustCategoryNo() > otherCustCategoryNo) ? 1 : -1);
		if (result == 0) {
			if (this.legalID == null) {
				return (otherLegalID == null) ? 0 : -1;
			}

			return (otherLegalID == null) ? 1 : this.legalID.compareTo(otherLegalID);
		}

		return result;
	}
}
