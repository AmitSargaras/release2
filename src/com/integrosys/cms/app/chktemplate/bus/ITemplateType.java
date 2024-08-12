/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/ITemplateType.java,v 1.1 2003/06/23 10:08:13 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//java
import java.io.Serializable;

/**
 * This interface defines the list of attributes that will be available to a
 * template type
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/23 10:08:13 $ Tag: $Name: $
 */
public interface ITemplateType extends Serializable {
	/**
	 * Get the template type
	 * @return String - the template type
	 */
	public String getTemplateType();

	/**
	 * Get the legal constitution
	 * @return String - the legal constitution
	 */
	public String getLegalConstitution();

	/**
	 * Get the law
	 * @return String - the law
	 */
	public String getLaw();

	/**
	 * Get the country
	 * @return String - the country
	 */
	public String getCountry();

	/**
	 * Set the template type
	 * @param aTemplateType - String
	 */
	public void setTemplateType(String aTemplateType);

	/**
	 * Set the legal constitution
	 * @param aLegalConstitution - String
	 */
	public void setLegalConstitution(String aLegalConstitution);

	/**
	 * Set the law
	 * @param aLaw - String
	 */
	public void setLaw(String aLaw);

	/**
	 * Set the country
	 * @param aCountry - String
	 */
	public void setCountry(String aCountry);
}
