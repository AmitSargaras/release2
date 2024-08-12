package com.integrosys.cms.ui.component;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

public class MaintainComponentForm extends TrxContextForm implements
		Serializable {

	private String id;
	private String versionTime;
	private String status;
	private String deprecated;
	private String componentType;
	private String componentName;
	private String componentCode;
	private String hasInsurance;
	//Start:-------->Abhishek Naik
	private String debtors;
	private String age;
	// End:-------->Abhishek Naik 
	//start Santosh
	private String componentCategory;
	private String applicableForDp;
	//end santosh
	public String[][] getMapper() {
		String[][] input = { { "componentObj", COMPONENT_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER } };
		return input;

	}

	public static final String COMPONENT_MAPPER = "com.integrosys.cms.ui.component.ComponentMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getDeprecated() {
		return deprecated;
	}

	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}

	public String getComponentType() {
		return componentType;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getComponentCode() {
		return componentCode;
	}

	public void setComponentCode(String componentCode) {
		this.componentCode = componentCode;
	}

	public String getHasInsurance() {
		return hasInsurance;
	}

	public void setHasInsurance(String hasInsurance) {

		this.hasInsurance = hasInsurance;
	}

	//Start:-------->Abhishek Naik
	public String getDebtors() {
		return debtors;
	}

	public void setDebtors(String debtors) {
		this.debtors = debtors;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	// End:-------->Abhishek Naik 
	
	//start santosh
	public String getComponentCategory() {
		return componentCategory;
	}

	public void setComponentCategory(String componentCategory) {
		this.componentCategory = componentCategory;
	}

	public String getApplicableForDp() {
		return applicableForDp;
	}

	public void setApplicableForDp(String applicableForDp) {
		this.applicableForDp = applicableForDp;
	}
	//end santosh
}
