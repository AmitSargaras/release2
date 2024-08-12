/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/TemplateItemSearchCriteria.java,v 1.1 2003/09/26 11:56:23 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//ofa
import com.integrosys.base.businfra.search.SearchCriteria;

/**
 * This is a helper class that will contains all the possible search criteria
 * required by the template
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/26 11:56:23 $ Tag: $Name: $
 */
public class TemplateItemSearchCriteria extends SearchCriteria {
	private String templateType = null;

	private String country = null;

	private String collateralType = null;

	private String collateralSubType = null;

	private String law = null;

	private String legalConstitution = null;

	private String itemDesc = null;

	/**
	 * Default Constructor
	 */
	public TemplateItemSearchCriteria() {
	}

	/**
	 * Getter Methods
	 */
	public String getTemplateType() {
		return this.templateType;
	}

	public String getCountry() {
		return this.country;
	}

	public String getCollateralType() {
		return this.collateralType;
	}

	public String getCollateralSubType() {
		return this.collateralSubType;
	}

	public String getLaw() {
		return this.law;
	}

	public String getLegalConstitution() {
		return this.legalConstitution;
	}

	public String getItemDesc() {
		return this.itemDesc;
	}

	/**
	 * Setter Methods
	 */
	public void setTemplateType(String aTemplateType) {
		this.templateType = aTemplateType;
	}

	public void setCountry(String aCountry) {
		this.country = aCountry;
	}

	public void setCollateralType(String aCollateralType) {
		this.collateralType = aCollateralType;
	}

	public void setCollateralSubType(String aCollateralSubType) {
		this.collateralSubType = aCollateralSubType;
	}

	public void setLaw(String aLaw) {
		this.law = aLaw;
	}

	public void setLegalConstitution(String aLegalConstitution) {
		this.legalConstitution = aLegalConstitution;
	}

	public void setItemDesc(String anItemDesc) {
		this.itemDesc = anItemDesc;
	}
}
