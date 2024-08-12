package com.integrosys.cms.ui.recurrentMaster;

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

public class RecurrentGlobalForm extends DocumentationGlobalForm {

	public RecurrentGlobalForm() {
		super();
	}

	private String statDesc="";
	
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


	public String getStatDesc() {
		return statDesc;
	}

	public void setStatDesc(String statDesc) {
		this.statDesc = statDesc;
	}

	public String[][] getMapper() {

		String[][] input = {

		{ "documentItem", "com.integrosys.cms.ui.recurrentMaster.RecurrentGlobalCheckListMapper" },

		{ "stgDocumentItem", "com.integrosys.cms.ui.recurrentMaster.StagingRecurrentGlobalCheckListMapper" },

		{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },

		};

		return input;

	}


}
