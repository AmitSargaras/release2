package com.integrosys.cms.ui.cam;

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

public class CAMGlobalForm extends DocumentationGlobalForm {

	public CAMGlobalForm() {
		super();
	}
	
	private String camDocCode="";
	private String camDocDesc="";
	private String camDocTenureCount="";
	private String camDocTenureType="";

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

		{ "documentItem", "com.integrosys.cms.ui.cam.CAMGlobalCheckListMapper" },

		{ "stgDocumentItem", "com.integrosys.cms.ui.cam.StagingCAMGlobalCheckListMapper" },

		{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },

		};

		return input;

	}

	public String getCamDocCode() {
		return camDocCode;
	}

	public void setCamDocCode(String camDocCode) {
		this.camDocCode = camDocCode;
	}

	public String getCamDocDesc() {
		return camDocDesc;
	}

	public void setCamDocDesc(String camDocDesc) {
		this.camDocDesc = camDocDesc;
	}

	public String getCamDocTenureCount() {
		return camDocTenureCount;
	}

	public void setCamDocTenureCount(String camDocTenureCount) {
		this.camDocTenureCount = camDocTenureCount;
	}

	public String getCamDocTenureType() {
		return camDocTenureType;
	}

	public void setCamDocTenureType(String camDocTenureType) {
		this.camDocTenureType = camDocTenureType;
	}


}
