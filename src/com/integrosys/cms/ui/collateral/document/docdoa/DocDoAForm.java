package com.integrosys.cms.ui.collateral.document.docdoa;

import java.io.Serializable;

import com.integrosys.cms.ui.collateral.document.DocumentForm;

public class DocDoAForm extends DocumentForm implements Serializable {

	public static final String DOCDOAMAPPER = "com.integrosys.cms.ui.collateral.document.docdoa.DocDoAMapper";

	private String deedAssignmtTypeCode = "";

	private String projectName = "";

	private String awardedDate = "";

	private String isLetterInstruct = "";

	private String isLetterUndertake = "";

	private String blanketAssignment = "";
	
	private String contractNumber = "";
	
	private String contractAmt = "";
	
	private String contractName = "";
	
	private String contractDate = "";

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getContractAmt() {
		return contractAmt;
	}

	public void setContractAmt(String contractAmt) {
		this.contractAmt = contractAmt;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getContractDate() {
		return contractDate;
	}

	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}

	public String getDeedAssignmtTypeCode() {
		return this.deedAssignmtTypeCode;
	}

	public void setDeedAssignmtTypeCode(String deedAssignmtTypeCode) {
		this.deedAssignmtTypeCode = deedAssignmtTypeCode;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getAwardedDate() {
		return this.awardedDate;
	}

	public void setAwardedDate(String awardedDate) {
		this.awardedDate = awardedDate;
	}

	public String getIsLetterInstruct() {
		return this.isLetterInstruct;
	}

	public void setIsLetterInstruct(String isLetterInstruct) {
		this.isLetterInstruct = isLetterInstruct;
	}

	public String getIsLetterUndertake() {
		return this.isLetterUndertake;
	}

	public void setIsLetterUndertake(String isLetterUndertake) {
		this.isLetterUndertake = isLetterUndertake;
	}

	public String getBlanketAssignment() {
		return this.blanketAssignment;
	}

	public void setBlanketAssignment(String blanketAssignment) {
		this.blanketAssignment = blanketAssignment;
	}

	public void reset() {

		super.reset();

	}

	public String[][] getMapper() {

		String[][] input = {

		{ "form.collateralObject", DOCDOAMAPPER },

		{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
		{ "pledgorRemove", "com.integrosys.cms.ui.collateral.pledgor.PledgorRemoveMapper" },
		{ "pledgeRemove", "com.integrosys.cms.ui.collateral.pledge.PledgeRemoveMapper" },

		};

		return input;

	}

}
