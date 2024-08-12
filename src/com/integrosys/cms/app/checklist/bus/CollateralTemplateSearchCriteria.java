/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/CollateralTemplateSearchCriteria.java,v 1.1 2003/07/02 01:20:20 hltan Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//ofa
import com.integrosys.base.businfra.search.SearchCriteria;

/**
 * This is a helper class that will contains all the possible search criteria
 * required by the Collateral template
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/02 01:20:20 $ Tag: $Name: $
 */
public class CollateralTemplateSearchCriteria extends SearchCriteria {
	private String templateType = null;

	private String country = null;

	private String collateralType = null;

	/**
	 * Default Constructor
	 */
	public CollateralTemplateSearchCriteria() {
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
}
