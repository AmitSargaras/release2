//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION

package com.integrosys.cms.ui.collateral.document.docpledge;

import java.io.Serializable;

import com.integrosys.cms.ui.collateral.document.DocumentForm;

/**
 * Created by IntelliJ IDEA. User: jerlin Date: 2007/03/16 To change this
 * template use Options | File Templates.
 */

public class DocPledgeForm extends DocumentForm implements Serializable {

	public static final String DOCPledgeMAPPER = "com.integrosys.cms.ui.collateral.document.docpledge.DocPledgeMapper";

	private String projectName = "";

	private String awardedDate = "";

	private String letterInstructFlag = "";

	private String letterUndertakeFlag = "";

	private String blanketAssignment = "";

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
		return this.letterInstructFlag;
	}

	public void setIsLetterInstruct(String letterInstructFlag) {
		this.letterInstructFlag = letterInstructFlag;
	}

	public String getIsLetterUndertake() {
		return this.letterUndertakeFlag;
	}

	public void setIsLetterUndertake(String letterUndertakeFlag) {
		this.letterUndertakeFlag = letterUndertakeFlag;
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
		String[][] input = { { "form.collateralObject", DOCPledgeMAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "pledgorRemove", "com.integrosys.cms.ui.collateral.pledgor.PledgorRemoveMapper" },
				{ "pledgeRemove", "com.integrosys.cms.ui.collateral.pledge.PledgeRemoveMapper" }, };
		return input;
	}
}
