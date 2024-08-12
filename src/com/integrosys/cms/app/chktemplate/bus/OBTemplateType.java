/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/OBTemplateType.java,v 1.1 2003/06/23 10:08:13 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class implements the ITemplateType
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/23 10:08:13 $ Tag: $Name: $
 */
public class OBTemplateType implements ITemplateType {
	private String templateType = null;

	private String legalConstitution = null;

	private String law = null;

	private String country = null;

	/**
	 * Get the template type
	 * @return String - the template type
	 */
	public String getTemplateType() {
		return this.templateType;
	}

	/**
	 * Get the legal constitution
	 * @return String - the legal constitution
	 */
	public String getLegalConstitution() {
		return this.legalConstitution;
	}

	/**
	 * Get the law
	 * @return String - the law
	 */
	public String getLaw() {
		return this.law;
	}

	/**
	 * Get the country
	 * @return String - the country
	 */
	public String getCountry() {
		return this.country;
	}

	/**
	 * Set the template type
	 * @param aTemplateType - String
	 */
	public void setTemplateType(String aTemplateType) {
		this.templateType = aTemplateType;
	}

	/**
	 * Set the legal constitution
	 * @param aLegalConstitution - String
	 */
	public void setLegalConstitution(String aLegalConstitution) {
		this.legalConstitution = aLegalConstitution;
	}

	/**
	 * Set the law
	 * @param aLaw - String
	 */
	public void setLaw(String aLaw) {
		this.law = aLaw;
	}

	/**
	 * Set the country
	 * @param aCountry - String
	 */
	public void setCountry(String aCountry) {
		this.country = aCountry;
	}

	/**
	 * Prints a String representation of this object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
