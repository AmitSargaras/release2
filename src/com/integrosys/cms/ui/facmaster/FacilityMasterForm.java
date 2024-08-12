/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.facmaster;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * @author $Author: hltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/10/28 08:58:58 $ Tag: $Name: $
 */

public class FacilityMasterForm extends TrxContextForm implements Serializable {

	private String secType;

	private String secTypeDesc;

	private String subType;

	private String subTypeDesc;

	private String docCode;

	private String docDesc;

	private String expDate;

	private String monitorType;
	
	private String tenureType;
	
	private String tenureCount;
	
	private String go="";

	// index used for add remove items in template
	private String index;
	
	private String facilityCategory;
	
	private String facilityType;
	
	private String system;

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
	

	public String getTenureType() {
		return tenureType;
	}

	public void setTenureType(String tenureType) {
		this.tenureType = tenureType;
	}
	
	public String getTenureCount() {
		return tenureCount;
	}

	public void setTenureCount(String tenureCount) {
		this.tenureCount = tenureCount;
	}

	public String getGo() {
		return go;
	}

	public void setGo(String go) {
		this.go = go;
	}

	public void reset() {

	}

	public String getFacilityCategory() {
		return facilityCategory;
	}

	public void setFacilityCategory(String facilityCategory) {
		this.facilityCategory = facilityCategory;
	}
	
	

	public String getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	/**
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it. it has a
	 * syntax (keyMapperclassname)
	 * 
	 * @return two-dimesnional String Array
	 */
	public String[][] getMapper() {
		String[][] input = { { "templateItem", "com.integrosys.cms.ui.facmaster.FacilityMasterMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" }, };
		return input;
	}


}
