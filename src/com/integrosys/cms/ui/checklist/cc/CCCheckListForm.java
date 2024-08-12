/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.checklist.cc;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * @author $Author: wltan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/03/29 10:33:18 $ Tag: $Name: $
 */

public class CCCheckListForm extends TrxContextForm implements Serializable {

	private static final long serialVersionUID = 58572168733381685L;

	private String limitProfileID = "";

	private String legalID = "";

	private String custCategory = "";

	private String limitBkgLoc = "";

	private String orgCode = "";

	private String checkListID = "";

	private String docCode = "";

	private String docDesc = "";

	private String replaceCheckListItemRef = "";

	private String applicationType = "";

	public String getApplicationType() {
		return applicationType;
	}

	public String getCheckListID() {
		return checkListID;
	}

	public String getCustCategory() {
		return custCategory;
	}

	public String getDocCode() {
		return docCode;
	}

	public String getDocDesc() {
		return docDesc;
	}

	public String getLegalID() {
		return legalID;
	}

	public String getLimitBkgLoc() {
		return limitBkgLoc;
	}

	public String getLimitProfileID() {
		return limitProfileID;
	}

	public String getOrgCode() {
		return this.orgCode;
	}

	public String getReplaceCheckListItemRef() {
		return replaceCheckListItemRef;
	}

	public void reset() {
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public void setCheckListID(String checkListID) {
		this.checkListID = checkListID;
	}

	public void setCustCategory(String custCategory) {
		this.custCategory = custCategory;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}

	public void setLegalID(String legalID) {
		this.legalID = legalID;
	}

	public void setLimitBkgLoc(String limitBkgLoc) {
		this.limitBkgLoc = limitBkgLoc;
	}

	public void setLimitProfileID(String limitProfileID) {
		this.limitProfileID = limitProfileID;
	}

	public void setOrgCode(String anOrgCode) {
		this.orgCode = anOrgCode;
	}

	public void setReplaceCheckListItemRef(String replaceCheckListItemRef) {
		this.replaceCheckListItemRef = replaceCheckListItemRef;
	}

	public String[][] getMapper() {
		String[][] input = { { "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "ownerObj", "com.integrosys.cms.ui.checklist.cc.OwnerMapper" },
				{ "checkListItem", "com.integrosys.cms.ui.checklist.cc.OwnerMapper" },
				{ "limitProfileID", "com.integrosys.cms.ui.checklist.cc.LimitProfileMapper" } };
		return input;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("CCCheckListForm [");
		buf.append("custCategory=");
		buf.append(custCategory);
		buf.append(", limitProfileID=");
		buf.append(limitProfileID);
		buf.append(", legalID=");
		buf.append(legalID);
		buf.append(", checkListID=");
		buf.append(checkListID);
		buf.append(", limitBkgLoc=");
		buf.append(limitBkgLoc);
		buf.append(", orgCode=");
		buf.append(orgCode);
		buf.append(", applicationType=");
		buf.append(applicationType);
		buf.append(", docCode=");
		buf.append(docCode);
		buf.append(", docDesc=");
		buf.append(docDesc);
		buf.append(", replaceCheckListItemRef=");
		buf.append(replaceCheckListItemRef);
		buf.append("]");
		return buf.toString();
	}

}
