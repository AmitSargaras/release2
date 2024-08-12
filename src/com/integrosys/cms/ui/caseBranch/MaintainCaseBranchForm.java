/**

 * Copyright Integro Technologies Pte Ltd

 * $Header:

 */

package com.integrosys.cms.ui.caseBranch;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 *@author $Author: Abhijit R$
 *Form Bean for CaseBranch Master
 */

public class MaintainCaseBranchForm extends TrxContextForm implements Serializable {

	
 
		private String versionTime;
		private String id;
		private String status;
		private String deprecated;
		private String branchCode;
		private String branchCodeSearch;       
		private String branchNameSearch;       
		private String coordinator1;  
		private String coordinator1Email;
		private String coordinator2;   
		private String coordinator2Email;
		private String go="";
	
	public String[][] getMapper() {
		String[][] input = {  { "caseBranchObj", HOLIDAY_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER }
		};
		return input;

	}
	
	
	public static final String HOLIDAY_MAPPER = "com.integrosys.cms.ui.caseBranch.CaseBranchMapper";

	public static final String HOLIDAY_LIST_MAPPER = "com.integrosys.cms.ui.caseBranch.CaseBranchListMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
	
	
	
	
	
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
	public String getDeprecated() {
		return deprecated;
	}
	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getCoordinator1() {
		return coordinator1;
	}
	public void setCoordinator1(String coordinator1) {
		this.coordinator1 = coordinator1;
	}
	public String getCoordinator1Email() {
		return coordinator1Email;
	}
	public void setCoordinator1Email(String coordinator1Email) {
		this.coordinator1Email = coordinator1Email;
	}
	public String getCoordinator2() {
		return coordinator2;
	}
	public void setCoordinator2(String coordinator2) {
		this.coordinator2 = coordinator2;
	}
	public String getCoordinator2Email() {
		return coordinator2Email;
	}
	public void setCoordinator2Email(String coordinator2Email) {
		this.coordinator2Email = coordinator2Email;
	}
	public String getBranchCodeSearch() {
		return branchCodeSearch;
	}
	public void setBranchCodeSearch(String branchCodeSearch) {
		this.branchCodeSearch = branchCodeSearch;
	}
	public String getBranchNameSearch() {
		return branchNameSearch;
	}
	public void setBranchNameSearch(String branchNameSearch) {
		this.branchNameSearch = branchNameSearch;
	}
	public String getGo() {
		return go;
	}
	public void setGo(String go) {
		this.go = go;
	}
	
	
	
	

}
