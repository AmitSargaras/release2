package com.integrosys.cms.ui.facglobal;

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

public class FacilityGlobalForm extends DocumentationGlobalForm {

	public FacilityGlobalForm() {
		super();
	}

	private String facDocCode="";
	private String facDocDesc="";
	private String facDocTenureCount="";
	private String facDocTenureType="";
	

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

	

	public String getFacDocCode() {
		return facDocCode;
	}

	public void setFacDocCode(String facDocCode) {
		this.facDocCode = facDocCode;
	}

	public String getFacDocDesc() {
		return facDocDesc;
	}

	public void setFacDocDesc(String facDocDesc) {
		this.facDocDesc = facDocDesc;
	}

	public String getFacDocTenureCount() {
		return facDocTenureCount;
	}

	public void setFacDocTenureCount(String facDocTenureCount) {
		this.facDocTenureCount = facDocTenureCount;
	}

	public String getFacDocTenureType() {
		return facDocTenureType;
	}

	public void setFacDocTenureType(String facDocTenureType) {
		this.facDocTenureType = facDocTenureType;
	}

	public String[][] getMapper() {

		String[][] input = {

		{ "documentItem", "com.integrosys.cms.ui.facglobal.FacilityGlobalCheckListMapper" },

		{ "stgDocumentItem", "com.integrosys.cms.ui.facglobal.StagingFacilityGlobalCheckListMapper" },

		{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },

		};

		return input;

	}


}
