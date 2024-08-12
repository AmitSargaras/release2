/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/OBTemplate.java,v 1.13 2003/08/22 11:13:25 sathish Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.chktemplate.bus.ITemplateItem;
import com.integrosys.cms.app.chktemplate.bus.IItem;

/**
 * This class implements the ITemplate
 * 
 * @author $Author: sathish $<br>
 * @version $Revision: 1.13 $
 * @since $Date: 2003/08/22 11:13:25 $ Tag: $Name: $
 */
public class OBTemplate implements ITemplate {
	private long templateID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String templateType = null;

	private String legalConstitution = null;

	private String law = null;

	private String country = null;

	private String collateralType = null;

	private String collateralSubType = null;
	
	private String collateralId = null;

	private long parentTemplateID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private ITemplateItem[] templateItemList = null;

	// private ITemplate template = null;
	private long versionTime = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	public OBTemplate() {
	}

	public OBTemplate(String aLaw, String aLegalConstitution, String aCountry) {
		setLaw(aLaw);
		setLegalConstitution(aLegalConstitution);
		setCountry(aCountry);
	}

	public OBTemplate(String aCollateralType, String aCollateralSubType) {
		setCollateralType(aCollateralType);
		setCollateralSubType(aCollateralSubType);
	}

	/**
	 * Get the template ID
	 * @return long - the template ID
	 */
	public long getTemplateID() {
		return this.templateID;
	}

	/**
	 * Get the template type
	 * @return String - templateType
	 */
	public String getTemplateType() {
		return this.templateType;
	}

	/**
	 * Get the legal constitution
	 * @return String - the legal constitution
	 */
	public String getLegalConstitution() {
		/*
		 * if (getTemplate() != null) { return
		 * getTemplate().getLegalConstitution(); }
		 */
		return this.legalConstitution;
	}

	/**
	 * Get the law
	 * @return String - the law
	 */
	public String getLaw() {
		/*
		 * if (getTemplate() != null) { return getTemplate().getLaw(); }
		 */
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
	 * Get the collateral type
	 * @return String - the collateral type
	 */
	public String getCollateralType() {
		/*
		 * if (getTemplate() != null) { return
		 * getTemplate().getCollateralType(); }
		 */
		return this.collateralType;
	}

	/**
	 * Get the collateral subtype
	 * @return String - the collateral subtype
	 */
	public String getCollateralSubType() {
		/*
		 * if (getTemplate() != null) { return
		 * getTemplate().getCollateralSubType(); }
		 */
		return this.collateralSubType;
	}

	/**
	 * Get the parent template ID
	 * @return long - the parent template ID
	 */
	public long getParentTemplateID() {
		return this.parentTemplateID;
	}

	/**
	 * Get the template item list
	 * @return ITemplateItem[] - the list of template items
	 */
	public ITemplateItem[] getTemplateItemList() {
		// for the abstract level template
		/*
		 * if (getTemplate() == null) { System.out.println("Template: " +
		 * getTemplate()); System.out.println("IN return current item list");
		 * return this.templateItemList; } //for the country level template
		 * ITemplateItem[] parentItemList = getTemplate().getTemplateItemList();
		 * if ((parentItemList == null) || (parentItemList.length == 0)) {
		 * return this.templateItemList; } if ((templateItemList == null) ||
		 * (templateItemList.length == 0)) {
		 * System.out.println("Template Items: " + templateItemList);
		 * System.out.println("In return parent items as child"); return
		 * getParentItemsAsChild(parentItemList); }
		 * System.out.println("In merge parent child"); return
		 * mergeParentChildItems(parentItemList, templateItemList);
		 */
		return this.templateItemList;
	}

	/*
	 * private ITemplateItem[] getParentItemsAsChild(ITemplateItem[]
	 * aParentTemplateItemList) { for (int ii=0;
	 * ii<aParentTemplateItemList.length; ii++) {
	 * aParentTemplateItemList[ii].setIsInheritedInd(true); } return
	 * aParentTemplateItemList; }
	 * 
	 * //Parent found in Child //Parent not found in child //Child not in parent
	 * private ITemplateItem[] mergeParentChildItems(ITemplateItem[]
	 * aParentTemplateItemList, ITemplateItem[] aChildTemplateItemList) {
	 * //Phase 1: Parent found in child => merge for (int ii=0;
	 * ii<aParentTemplateItemList.length; ii++) { for (int jj=0;
	 * jj<aChildTemplateItemList.length; jj++) { if
	 * (aParentTemplateItemList[ii].
	 * getItemCode().equals(aChildTemplateItemList[jj].getItemCode())) {
	 * aParentTemplateItemList
	 * [ii].setIsInVaultInd(aChildTemplateItemList[jj].getIsInVaultInd());
	 * aParentTemplateItemList
	 * [ii].setIsExtCustInd(aChildTemplateItemList[jj].getIsExtCustInd());
	 * aParentTemplateItemList
	 * [ii].setIsAuditInd(aChildTemplateItemList[jj].getIsAuditInd());
	 * aParentTemplateItemList[ii].setIsInheritedInd(true);
	 * aChildTemplateItemList[jj].setIsInheritedInd(true); } } }
	 * 
	 * ArrayList itemList = new ArrayList(
	 * Arrays.asList(aParentTemplateItemList)); for (int ii=0;
	 * ii<aChildTemplateItemList.length; ii++) { if
	 * (!aChildTemplateItemList[ii].getIsInheritedInd()) {
	 * itemList.add(aChildTemplateItemList[ii]); } } return
	 * (ITemplateItem[])itemList.toArray(new ITemplateItem[itemList.size()]); }
	 * 
	 * / Get the template
	 * 
	 * @return ITemplate - the template that the current template inherited
	 */
	/*
	 * public ITemplate getTemplate() { return this.template; }
	 */

	/**
	 * Get the version time
	 * @return long - the version time
	 */
	public long getVersionTime() {
		return this.versionTime;
	}

	/**
	 * Set the template ID
	 * @param aTemplateID - long
	 */
	public void setTemplateID(long aTemplateID) {
		this.templateID = aTemplateID;
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
	 * @param aLegalConstition - String
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
	 * Set the collateral type
	 * @param aCollateralType - String
	 */
	public void setCollateralType(String aCollateralType) {
		this.collateralType = aCollateralType;
	}

	/**
	 * Set the collateral subtype
	 * @param aCollateralSubType - String
	 */
	public void setCollateralSubType(String aCollateralSubType) {
		this.collateralSubType = aCollateralSubType;
	}

	public String getCollateralId() {
		return collateralId;
	}

	public void setCollateralId(String collateralId) {
		this.collateralId = collateralId;
	}

	/**
	 * Set the parent template ID.
	 * @param aParentTemplateID - long
	 */
	public void setParentTemplateID(long aParentTemplateID) {
		this.parentTemplateID = aParentTemplateID;
	}

	/**
	 * Set the template item list
	 * @param anITemplateItemList - ITemplateItem[]
	 */
	public void setTemplateItemList(ITemplateItem[] anITemplateItemList) {
		this.templateItemList = anITemplateItemList;
	}

	/**
	 * Set the template
	 * @param anITemplate - ITemplate
	 */
	/*
	 * public void setTemplate(ITemplate anITemplate) { this.template =
	 * anITemplate; }
	 * 
	 * / Set the version time
	 * 
	 * @param aVersionTime - long
	 */
	public void setVersionTime(long aVersionTime) {
		this.versionTime = aVersionTime;
	}

	/**
	 * Add a item into the template
	 * @param anItem - IItem
	 */
	public void addItem(IItem anItem) {
		int numOfItems = 0;
		ITemplateItem[] itemList = getTemplateItemList();
		if (itemList != null) {
			numOfItems = itemList.length;
		}

		ITemplateItem[] newList = new OBTemplateItem[numOfItems + 1];
		if (itemList != null) {
			for (int ii = 0; ii < itemList.length; ii++) {
				newList[ii] = itemList[ii];
			}
		}
		ITemplateItem item = new OBTemplateItem();
		item.setItem(anItem);
		newList[numOfItems] = item;
		setTemplateItemList(newList);
	}

	/**
	 * Add an list of items into the template
	 * @param anItemList - IItem[]
	 */
	public void addItems(IItem[] anItemList) {
		int numOfItems = 0;
		ITemplateItem[] itemList = getTemplateItemList();
		if (itemList != null) {
			numOfItems = itemList.length;
		}

		ITemplateItem[] newList = new OBTemplateItem[numOfItems + anItemList.length];
		ITemplateItem item = null;
		if (itemList != null) {
			for (int ii = 0; ii < itemList.length; ii++) {
				newList[ii] = itemList[ii];
			}
		}
		for (int ii = 0; ii < anItemList.length; ii++) {
			item = new OBTemplateItem();
			item.setItem(anItemList[ii]);
			newList[ii + numOfItems] = item;
		}
		setTemplateItemList(newList);
	}

	/**
	 * Remove a list of items into the template
	 * @param int[] - the list of item index to be removed
	 */
	public void removeItems(int[] anItemIndexList) {
		ITemplateItem[] itemList = getTemplateItemList();
		ITemplateItem[] newList = new OBTemplateItem[itemList.length - anItemIndexList.length];
		int ctr = 0;
		boolean removeFlag = false;
		for (int ii = 0; ii < itemList.length; ii++) {
			for (int jj = 0; jj < anItemIndexList.length; jj++) {
				if (ii == anItemIndexList[jj]) {
					removeFlag = true;
					break;
				}
			}
			if (!removeFlag) {
				newList[ctr] = itemList[ii];
				ctr++;
			}
			removeFlag = false;
		}
		setTemplateItemList(newList);
	}

	/**
	 * Update an item in the template
	 * @param anItemIndex - int
	 * @param anItem - IItem
	 */
	public void updateItem(int anItemIndex, IItem anItem) {
		ITemplateItem[] itemList = getTemplateItemList();
		if (itemList != null) {
			if (anItemIndex < itemList.length) {
				String itemCode = itemList[anItemIndex].getItemCode();
				anItem.setItemCode(itemCode);
				itemList[anItemIndex].setItem(anItem);
				setTemplateItemList(itemList);
			}
		}
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
