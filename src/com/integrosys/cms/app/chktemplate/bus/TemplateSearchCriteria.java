/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/TemplateSearchCriteria.java,v 1.4 2003/07/09 07:13:27 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//ofa
import com.integrosys.base.businfra.search.SearchCriteria;

/**
 * This is a helper class that will contains all the possible search criteria
 * required by the template
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/07/09 07:13:27 $ Tag: $Name: $
 */
public class TemplateSearchCriteria extends SearchCriteria {
	private String templateType = null;

	private String country = null;

	private String collateralType = null;

	private String collateralSubType = null;

	private String law = null;

	private String legalConstitution = null;

	private String[] trxStatusList = null;

	/**
	 * Default Constructor
	 */
	public TemplateSearchCriteria() {
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

	public String[] getTrxStatusList() {
		return this.trxStatusList;
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

	public void setTrxStatusList(String[] aTrxStatusList) {
		this.trxStatusList = aTrxStatusList;
	}
}
