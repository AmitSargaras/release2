/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.seccountry;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * @author $Author: hltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/10/28 09:02:07 $ Tag: $Name: $
 */

public class SecurityCountryForm extends TrxContextForm implements Serializable {

	private String secType = "";

	private String secTypeDesc = "";

	private String subType = "";

	private String subTypeDesc = "";

	private String docCode = "";

	private String docDesc = "";

	private String expDate = "";

	private String monitorType = "";
	
	private String isPreApprove = "N";
	// index used for add remove items in template
	private String index = "";

	private String countryCode = "";

	private String countryName = "";
	
	private String docVersion = "";
	
	private String loanApplicationType = "0";
	
	private String appendLoanList = "0";
	
	private String[] loanApplicationList;
	
	

	public String getLoanApplicationType() {
		return loanApplicationType;
	}

	public void setLoanApplicationType(String loanApplicationType) {
		this.loanApplicationType = loanApplicationType;
	}

	public String getDocVersion() {
		return docVersion;
	}

	public void setDocVersion(String docVersion) {
		this.docVersion = docVersion;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
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

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getMonitorType() {
		return monitorType;
	}

	public void setMonitorType(String monitorType) {
		this.monitorType = monitorType;
	}

	public String getSecTypeDesc() {
		return secTypeDesc;
	}

	public void setSecTypeDesc(String secTypeDesc) {
		this.secTypeDesc = secTypeDesc;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getSubTypeDesc() {
		return subTypeDesc;
	}

	public void setSubTypeDesc(String subTypeDesc) {
		this.subTypeDesc = subTypeDesc;
	}

	public String getSecType() {
		return secType;
	}

	public void setSecType(String secType) {
		this.secType = secType;
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
				{ "templateItem", "com.integrosys.cms.ui.seccountry.SecurityCountryMapper" }, };
		return input;
	}

	public String getIsPreApprove() {
		return isPreApprove;
	}

	public void setIsPreApprove(String isPreApprove) {
		this.isPreApprove = isPreApprove;
	}
	
	public String[] getLoanApplicationList() {
		return loanApplicationList;
	}

	public void setLoanApplicationList(String[] loanApplicationList) {
		this.loanApplicationList = loanApplicationList;
	}
	
	public String getAppendLoanList() {
		return appendLoanList;
	}

	public void setAppendLoanList(String appendLoanList) {
		this.appendLoanList = appendLoanList;
	}

}
