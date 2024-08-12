/**

 * Copyright Integro Technologies Pte Ltd

 * $Header:

 */

package com.integrosys.cms.ui.caseCreation;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 *@author $Author: Abhijit R$
 *Form Bean for CaseCreation Master
 */

public class CaseCreationForm extends TrxContextForm implements Serializable {

	
 
		private String versionTime;
		private String status;
		private String id;
		private String checklistitemid="";
		private String casecreationid ="";
		private String itemtype="";
		private String coordinator1Name;
		private String coordinator2Name;
		
	
	public String[][] getMapper() {
		String[][] input = {  { "caseCreationObj", CASE_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER }
		};
		return input;

	}
	
	
	public static final String CASE_MAPPER = "com.integrosys.cms.ui.caseCreation.CaseCreationMapper";

	public static final String CASE_LIST_MAPPER = "com.integrosys.cms.ui.caseCreation.CaseCreationListMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
	
	
	
	
	
	public String getCoordinator1Name() {
		return coordinator1Name;
	}
	public void setCoordinator1Name(String coordinator1Name) {
		this.coordinator1Name = coordinator1Name;
	}
	public String getCoordinator2Name() {
		return coordinator2Name;
	}
	public void setCoordinator2Name(String coordinator2Name) {
		this.coordinator2Name = coordinator2Name;
	}
	public String getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(String versionTime) {
		this.versionTime = versionTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	
	

}
