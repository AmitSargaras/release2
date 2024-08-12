package com.integrosys.cms.ui.secglobal;

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

public class SecurityGlobalForm extends DocumentationGlobalForm {
	
	private String secDocCode="";
	private String secDocDesc="";
	private String secDocTenureCount="";
	private String secDocTenureType="";

	private String isApplicableForCersaiInd;
	
	public SecurityGlobalForm() {
		super();
	}

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

	

	public String getSecDocCode() {
		return secDocCode;
	}

	public void setSecDocCode(String secDocCode) {
		this.secDocCode = secDocCode;
	}

	public String getSecDocDesc() {
		return secDocDesc;
	}

	public void setSecDocDesc(String secDocDesc) {
		this.secDocDesc = secDocDesc;
	}

	public String getSecDocTenureCount() {
		return secDocTenureCount;
	}

	public void setSecDocTenureCount(String secDocTenureCount) {
		this.secDocTenureCount = secDocTenureCount;
	}

	public String getSecDocTenureType() {
		return secDocTenureType;
	}

	public void setSecDocTenureType(String secDocTenureType) {
		this.secDocTenureType = secDocTenureType;
	}

	public String getIsApplicableForCersaiInd() {
		return isApplicableForCersaiInd;
	}

	public void setIsApplicableForCersaiInd(String isApplicableForCersaiInd) {
		this.isApplicableForCersaiInd = isApplicableForCersaiInd;
	}
	
	public String[][] getMapper() {

		String[][] input = {

		{ "documentItem", "com.integrosys.cms.ui.secglobal.SecurityGlobalCheckListMapper" },

		{ "stgDocumentItem", "com.integrosys.cms.ui.secglobal.StagingSecurityGlobalCheckListMapper" },

		{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },

		};

		return input;

	}


}
