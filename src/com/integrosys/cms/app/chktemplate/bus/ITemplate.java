/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/ITemplate.java,v 1.7 2003/07/11 06:22:47 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//java
import java.io.Serializable;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.chktemplate.bus.IItem;

/**
 * This interface defines the list of attributes that will be available to a
 * template
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2003/07/11 06:22:47 $ Tag: $Name: $
 */
public interface ITemplate extends IValueObject, Serializable {
	/**
	 * Get the template ID
	 * @return long - the template ID
	 */
	public long getTemplateID();

	/**
	 * Get the template type
	 * @return String - templateType
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
	 * @return String - the country code
	 */
	public String getCountry();

	/**
	 * Get the collateral type
	 * @return String - the collateral type
	 */
	public String getCollateralType();

	/**
	 * Get the collateral subtype
	 * @return String - the collateral subtype
	 */
	public String getCollateralSubType();
	

	/**
	 * Get the collateral subtype
	 * @return String - the collateral subtype
	 */
	public String getCollateralId();

	/**
	 * Get the template item list
	 * @return ITemplateItem[] - the list of template items
	 */
	public ITemplateItem[] getTemplateItemList();

	/**
	 * Get the template
	 * @return ITemplate - the template that the current template inherited
	 */
	// public ITemplate getTemplate();
	/**
	 * Get the parent template ID
	 * @return long - the parent template ID
	 */
	public long getParentTemplateID();

	/**
	 * Set the template ID
	 * @param aTemplateID - long
	 */
	public void setTemplateID(long aTemplateID);

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

	/**
	 * Set the collateral type
	 * @param aCollateralType - String
	 */
	public void setCollateralType(String aCollateralType);

	/**
	 * Set the collateral subtype
	 * @param aCollateralSubType - String
	 */
	public void setCollateralSubType(String aCollateralSubType);
	
	/**
	 * Set the collateral subtype
	 * @param aCollateralSubType - String
	 */
	public void setCollateralId(String aCollateralId);

	/**
	 * Set the template item list
	 * @param anITemplateItemList - ITemplateItem[]
	 */
	public void setTemplateItemList(ITemplateItem[] anITemplateItemList);

	/**
	 * Set the template
	 * @param anITemplate - ITemplate
	 */
	// public void setTemplate(ITemplate anITemplate);
	/**
	 * Set the parent template ID
	 * @param aParentTemplateID - long
	 */
	public void setParentTemplateID(long aParentTemplateID);

	/**
	 * Add a item into the template
	 * @param anItem - IItem
	 */
	public void addItem(IItem anItem);

	/**
	 * Add a list of items into the template
	 * @param anItemList - IItem[]
	 */
	public void addItems(IItem[] anItemList);

	/**
	 * Remove a list of items into the template
	 * @param int[] - the list of item index to be removed
	 */
	public void removeItems(int[] anItemIndexList);

	/**
	 * Update an item in the template
	 * @param anItemIndex - int
	 * @param anItem - IItem
	 */
	public void updateItem(int anItemIndex, IItem anItem);
}
