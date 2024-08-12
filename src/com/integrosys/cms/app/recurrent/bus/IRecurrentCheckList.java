/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/IRecurrentCheckList.java,v 1.5 2003/08/25 12:25:14 hltan Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

//java

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

import java.io.Serializable;

/**
 * This interface defines the list of attributes that will be available to a
 * recurrent checklist
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/08/25 12:25:14 $ Tag: $Name: $
 */
public interface IRecurrentCheckList extends IValueObject, Serializable {
	/**
	 * Get the recurrent checklist ID
	 * @return long - the recurrent checklist ID
	 */
	public long getCheckListID();	

	/**
	 * Get the limit profile ID
	 * @return long - the limit profile ID
	 */
	public long getLimitProfileID();

	/**
	 * Get the subprofile ID
	 * @return long - the subprofile ID
	 */
	public long getSubProfileID();

	/**
	 * Get the list convenants
	 * @return IConvenant[] - the list of convenants
	 */
	public IConvenant[] getConvenantList();

	/**
	 * Get the list of recurrent checklist items
	 * @return IRecurrentCheckListItem[] - the list of recurrent checklist items
	 */
	public IRecurrentCheckListItem[] getCheckListItemList();

	/**
	 * Get the checklist status
	 * @return String - the checklist status
	 */
	public String getCheckListStatus();

	/**
	 * Set the recurrent checklist ID
	 * @param aCheckListID of long type
	 */
	public void setCheckListID(long aCheckListID);

	/**
	 * Set the limit profile ID
	 * @param aLimitProfileID of long type
	 */
	public void setLimitProfileID(long aLimitProfileID);

	/**
	 * Set the sub profile ID
	 * @param aSubProfileID of long type
	 */
	public void setSubProfileID(long aSubProfileID);

	/**
	 * Set the convenant list
	 * @param anIConvenantList of IConvenant[] type
	 */
	public void setConvenantList(IConvenant[] anIConvenantList);

	/**
	 * Set the recurrent checklist item list
	 * @param anICheckListItemList of IRecurrentCheckListItem[] type
	 */
	public void setCheckListItemList(IRecurrentCheckListItem[] anICheckListItemList);

	/**
	 * Set the checklist status
	 * @param aCheckListStatus of String type
	 */
	public void setCheckListStatus(String aCheckListStatus);

	/**
	 * Add a convenant into the checklist
	 * @param anIConvenant of IConvenant type
	 */
	public void addConvenant(IConvenant anIConvenant);

	/**
	 * Add an list of convenant into the checklist
	 * @param anIConvenantList of IConvenant[] type
	 */
	public void addConvenants(IConvenant[] anIConvenantList);

	/**
	 * Add an item into the checklist
	 * @param anItem - IRecurrentCheckListItem
	 */
	public void addItem(IRecurrentCheckListItem anItem);

	/**
	 * Add an list of items into the checklist
	 * @param anItemList - IItem[]
	 */
	public void addItems(IRecurrentCheckListItem[] anItemList);

	/**
	 * Remove a list of convenant into the checklist
	 * @param anIndexList of int[] type
	 */
	public void removeConvenants(int[] anIndexList);

	/**
	 * Remove a list of items into the checklist
	 * @param int[] - the list of item index to be removed
	 */
	public void removeItems(int[] anItemIndexList);

	/**
	 * Update an item in the template
	 * @param anIndex of int type
	 * @param anIConvenant of IConvenant type
	 */
	public void updateConvenant(int anIndex, IConvenant anIConvenant);

	/**
	 * Update an item in the template
	 * @param anItemIndex - int
	 * @param anItem - IItem
	 */
	public void updateItem(int anItemIndex, IRecurrentCheckListItem anItem);
	
	public IAnnexure[] getAnnexureList();
	
	public void setAnnexureList(IAnnexure[] anIAnnexureList);
}
