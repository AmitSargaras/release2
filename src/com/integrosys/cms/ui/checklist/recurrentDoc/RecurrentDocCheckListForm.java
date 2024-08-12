/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recurrentDoc;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/10/09 05:39:20 $ Tag: $Name: $
 */

public class RecurrentDocCheckListForm extends TrxContextForm implements Serializable {

	private static final long serialVersionUID = -2645216329133622076L;

	private String checkListID = "";

	private String limitProfileID = "";

	private String limitBkgLoc = "";

	private String docCode = "";

	private String docDesc = "";

	private String secType = "";

	private String secSubType = "";

	private String secName = "";

	private String collateralID = "";

	private String replaceCheckListItemRef = "";

	// R1.5 CR35 -----
	private String legalID = "";

	private String custCategory = "";

	// R1.5 CR162a
	private String orgCode = "";

	private String applicationType = "";
	
private String statementType = "";
	
	private String allStatementType = "";

	private String appendStatementType = "";
	
	

	public String getStatementType() {
		return statementType;
	}

	public void setStatementType(String statementType) {
		this.statementType = statementType;
	}

	public String getAllStatementType() {
		return allStatementType;
	}

	public void setAllStatementType(String allStatementType) {
		this.allStatementType = allStatementType;
	}

	public String getAppendStatementType() {
		return appendStatementType;
	}

	public void setAppendStatementType(String appendStatementType) {
		this.appendStatementType = appendStatementType;
	}

	public void reset() {

	}

	/**
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it. it has a
	 * syntax (keyMapperclassname)
	 * 
	 * @return two-dimesnional String Array
	 */
	public String[][] getMapper() {
		String[][] input = { { "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "ownerObj", "com.integrosys.cms.ui.checklist.recurrentDoc.OwnerMapper" },
				{ "checkListItem", "com.integrosys.cms.ui.checklist.recurrentDoc.CheckListItemMapper" },
				{ "limitProfileID", "com.integrosys.cms.ui.checklist.recurrentDoc.LimitProfileMapper" } };
		return input;
	}

	public String getCheckListID() {
		return checkListID;
	}

	public void setCheckListID(String checkListID) {
		this.checkListID = checkListID;
	}

	public String getLimitProfileID() {
		return limitProfileID;
	}

	public void setLimitProfileID(String limitProfileID) {
		this.limitProfileID = limitProfileID;
	}

	public String getLimitBkgLoc() {
		return limitBkgLoc;
	}

	public void setLimitBkgLoc(String limitBkgLoc) {
		this.limitBkgLoc = limitBkgLoc;
	}

	public String getDocCode() {
		return docCode;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public String getDocDesc() {
		return docDesc;
	}

	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}

	public String getSecType() {
		return secType;
	}

	public void setSecType(String secType) {
		this.secType = secType;
	}

	public String getSecSubType() {
		return secSubType;
	}

	public void setSecSubType(String secSubType) {
		this.secSubType = secSubType;
	}

	public String getSecName() {
		return secName;
	}

	public void setSecName(String secName) {
		this.secName = secName;
	}

	public String getCollateralID() {
		return collateralID;
	}

	public void setCollateralID(String collateralID) {
		this.collateralID = collateralID;
	}

	public String getReplaceCheckListItemRef() {
		return replaceCheckListItemRef;
	}

	public void setReplaceCheckListItemRef(String replaceCheckListItemRef) {
		this.replaceCheckListItemRef = replaceCheckListItemRef;
	}

	public String getLegalID() {
		return legalID;
	}

	public void setLegalID(String legalID) {
		this.legalID = legalID;
	}

	public String getCustCategory() {
		return custCategory;
	}

	public void setCustCategory(String custCategory) {
		this.custCategory = custCategory;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
}
