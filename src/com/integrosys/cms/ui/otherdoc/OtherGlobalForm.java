package com.integrosys.cms.ui.otherdoc;

import com.integrosys.cms.ui.docglobal.DocumentationGlobalForm;

/**
 * 
 * @author $Author: sathish $<br>
 * 
 * @version $Revision: 1.3 $
 * 
 * @since $Date: 2003/08/21 08:37:44 $
 * 
 * Tag: $Name: $
 */

public class OtherGlobalForm extends DocumentationGlobalForm {

	public OtherGlobalForm() {
		super();
	}
	
	private String otherDocCode="";
	private String otherDocDesc="";
	private String otherDocTenureCount="";
	private String otherDocTenureType="";

	private String loanApplicationType = "0";
	
	private String[] loanApplicationList;

	public String getLoanApplicationType() {
		return loanApplicationType;
	}

	public void setLoanApplicationType(String loanApplicationType) {
		this.loanApplicationType = loanApplicationType;
	}
	
	public String[] getLoanApplicationList() {
		return loanApplicationList;
	}

	public void setLoanApplicationList(String[] loanApplicationList) {
		this.loanApplicationList = loanApplicationList;
	}


	public String[][] getMapper() {

		String[][] input = {

		{ "documentItem", "com.integrosys.cms.ui.otherdoc.OtherGlobalCheckListMapper" },

		{ "stgDocumentItem", "com.integrosys.cms.ui.otherdoc.StagingOtherGlobalCheckListMapper" },

		{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },

		};

		return input;

	}

	public String getOtherDocCode() {
		return otherDocCode;
	}

	public void setOtherDocCode(String otherDocCode) {
		this.otherDocCode = otherDocCode;
	}

	public String getOtherDocDesc() {
		return otherDocDesc;
	}

	public void setOtherDocDesc(String otherDocDesc) {
		this.otherDocDesc = otherDocDesc;
	}

	public String getOtherDocTenureCount() {
		return otherDocTenureCount;
	}

	public void setOtherDocTenureCount(String otherDocTenureCount) {
		this.otherDocTenureCount = otherDocTenureCount;
	}

	public String getOtherDocTenureType() {
		return otherDocTenureType;
	}

	public void setOtherDocTenureType(String otherDocTenureType) {
		this.otherDocTenureType = otherDocTenureType;
	}


}
