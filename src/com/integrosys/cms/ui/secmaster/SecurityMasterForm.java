/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.secmaster;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * @author $Author: hltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/10/28 08:58:58 $ Tag: $Name: $
 */

public class SecurityMasterForm extends TrxContextForm implements Serializable {

	private String secType = "";

	private String secTypeDesc = "";
	
	private String collateralId = "";

	

	private String subType = "";

	private String subTypeDesc = "";

	private String docCode = "";

	private String docDesc = "";

	private String expDate = "";

	private String monitorType = "";
	
private String tenureCount="";
	
	private String tenureType="";

	// index used for add remove items in template
	private String index = "";

	

	public String getTenureCount() {
		return tenureCount;
	}

	public void setTenureCount(String tenureCount) {
		this.tenureCount = tenureCount;
	}

	public String getTenureType() {
		return tenureType;
	}

	public void setTenureType(String tenureType) {
		this.tenureType = tenureType;
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


	public String getCollateralId() {
		return collateralId;
	}

	public void setCollateralId(String collateralId) {
		this.collateralId = collateralId;
	}
	/**
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it. it has a
	 * syntax (keyMapperclassname)
	 * 
	 * @return two-dimesnional String Array
	 */
	public String[][] getMapper() {
		String[][] input = { { "templateItem", "com.integrosys.cms.ui.secmaster.SecurityMasterMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" }, };
		return input;
	}


}
