/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/OBRecurrentCheckList.java,v 1.7 2004/07/09 15:43:59 mcchua Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

//java
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This interface defines the list of attributes that will be available to a
 * recurrent checklist
 * 
 * @author $Author: mcchua $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2004/07/09 15:43:59 $ Tag: $Name: $
 */
public class OBRecurrentCheckList implements IRecurrentCheckList {
	private long checkListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long limitProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long subProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private IConvenant[] convenantList = null;

	private IRecurrentCheckListItem[] itemList = null;

	private String checkListStatus = null;

	private long versionTime = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	
	private IAnnexure[] annexureList = null;

	/**
	 * @return the annexureList
	 */
	public IAnnexure[] getAnnexureList() {
		return annexureList;
	}

	/**
	 * @param annexureList the annexureList to set
	 */
	public void setAnnexureList(IAnnexure[] annexureList) {
		this.annexureList = annexureList;
	}

	/**
	 * Default Constructor
	 */
	OBRecurrentCheckList() {
	}

	/**
	 * Constructor
	 */
	public OBRecurrentCheckList(long aLimitProfileID, long aSubProfileID) {
		setLimitProfileID(aLimitProfileID);
		setSubProfileID(aSubProfileID);
	}

	/**
	 * Get the recurrent checklist ID
	 * @return long - the recurrent checklist ID
	 */
	public long getCheckListID() {
		return this.checkListID;
	}

	/**
	 * Get the limit profile ID
	 * @return long - the limit profile ID
	 */
	public long getLimitProfileID() {
		return this.limitProfileID;
	}

	/**
	 * Get the sub profile ID
	 * @return long - the subprofile ID
	 */
	public long getSubProfileID() {
		return this.subProfileID;
	}

	/**
	 * Get the list convenants
	 * @return IConvenant[] - the list of convenants
	 */
	public IConvenant[] getConvenantList() {
		return this.convenantList;
	}

	/**
	 * Get the list of recurrent checklist items
	 * @return IRecurrentCheckListItem[] - the list of recurrent checklist items
	 */
	public IRecurrentCheckListItem[] getCheckListItemList() {
		return this.itemList;
	}

	/**
	 * Get the checklist status
	 * @return String - the checklist status
	 */
	public String getCheckListStatus() {
		return this.checkListStatus;
	}

	/**
	 * Get the version time
	 * @return long - the version time
	 */
	public long getVersionTime() {
		return this.versionTime;
	}

	/**
	 * Set the recurrent checklist ID
	 * @param aRecurrentCheckListID of long type
	 */
	public void setCheckListID(long aCheckListID) {
		this.checkListID = aCheckListID;
	}

	/**
	 * Set the limit profile ID
	 * @param aLimitProfileID of long type
	 */
	public void setLimitProfileID(long aLimitProfileID) {
		this.limitProfileID = aLimitProfileID;
	}

	/**
	 * Set the sub profile ID
	 * @param aSubProfileID of long type
	 */
	public void setSubProfileID(long aSubProfileID) {
		this.subProfileID = aSubProfileID;
	}

	/**
	 * Set the convenant list
	 * @param anIConvenantList of IConvenant[] type
	 */
	public void setConvenantList(IConvenant[] anIConvenantList) {

		this.convenantList = anIConvenantList;
	}

	/**
	 * Set the recurrent checklist item list
	 * @param anICheckListItemList of IRecurrentCheckListItem[] type
	 */
	public void setCheckListItemList(IRecurrentCheckListItem[] anICheckListItemList) {
		this.itemList = anICheckListItemList;
	}

	/**
	 * Set the checklist status
	 * @param aCheckListStatus of String type
	 */
	public void setCheckListStatus(String aCheckListStatus) {
		this.checkListStatus = aCheckListStatus;
	}

	/**
	 * Set the version time
	 * @param aVersionTime of long type
	 */
	public void setVersionTime(long aVersionTime) {
		this.versionTime = aVersionTime;
	}

	/**
	 * Add a convenant into the checklist
	 * @param anIConvenant of IConvenant type
	 */
	public void addConvenant(IConvenant anIConvenant) {
		int numOfConv = 0;
		IConvenant[] convList = getConvenantList();
		if (convList != null) {
			numOfConv = convList.length;
		}
		IConvenant[] newList = new OBConvenant[numOfConv + 1];
		if (convList != null) {
			for (int ii = 0; ii < convList.length; ii++) {
				newList[ii] = convList[ii];
			}
		}
		newList[numOfConv] = anIConvenant;
		setConvenantList(newList);
	}

	/**
	 * Add an list of convenant into the checklist
	 * @param anIConvenantList of IConvenant[] type
	 */
	public void addConvenants(IConvenant[] anIConvenantList) {
		int numOfConv = 0;
		IConvenant[] convList = getConvenantList();
		if (convList != null) {
			numOfConv = convList.length;
		}
		IConvenant[] newList = new OBConvenant[numOfConv + anIConvenantList.length];
		if (convList != null) {
			for (int ii = 0; ii < convList.length; ii++) {
				newList[ii] = convList[ii];
			}
		}
		for (int ii = 0; ii < anIConvenantList.length; ii++) {
			newList[ii + numOfConv] = anIConvenantList[ii];
		}
		setConvenantList(newList);
	}

	/**
	 * Add an item into the checklist
	 * @param anItem - IRecurrentCheckListItem
	 */
	public void addItem(IRecurrentCheckListItem anItem) {
		int numOfItems = 0;
		IRecurrentCheckListItem[] itemList = getCheckListItemList();
		if (itemList != null) {
			numOfItems = itemList.length;
		}
		IRecurrentCheckListItem[] newList = new OBRecurrentCheckListItem[numOfItems + 1];
		if (itemList != null) {
			for (int ii = 0; ii < itemList.length; ii++) {
				newList[ii] = itemList[ii];
			}
		}
		newList[numOfItems] = anItem;
		setCheckListItemList(newList);
	}

	/**
	 * Add an list of items into the checklist
	 * @param anItemList of IItem[] type
	 */
	public void addItems(IRecurrentCheckListItem[] anItemList) {
		int numOfItems = 0;
		IRecurrentCheckListItem[] itemList = getCheckListItemList();
		if (itemList != null) {
			numOfItems = itemList.length;
		}
		IRecurrentCheckListItem[] newList = new OBRecurrentCheckListItem[numOfItems + anItemList.length];
		if (itemList != null) {
			for (int ii = 0; ii < itemList.length; ii++) {
				newList[ii] = itemList[ii];
			}
		}
		for (int ii = 0; ii < anItemList.length; ii++) {
			newList[ii + numOfItems] = anItemList[ii];
		}
		setCheckListItemList(newList);
	}

	/**
	 * Remove a list of convenant into the checklist
	 * @param anIndexList of int[] type
	 */
	public void removeConvenants(int[] anIndexList) {
		IConvenant[] convList = getConvenantList();
		IConvenant[] newList = new OBConvenant[convList.length - anIndexList.length];
		int ctr = 0;
		boolean removeFlag = false;
		for (int ii = 0; ii < convList.length; ii++) {
			for (int jj = 0; jj < anIndexList.length; jj++) {
				if (ii == anIndexList[jj]) {
					removeFlag = true;
					break;
				}
			}
			if (!removeFlag) {
				newList[ctr] = convList[ii];
				ctr++;
			}
			removeFlag = false;
		}
		setConvenantList(newList);
	}

	/**
	 * Remove a list of items into the checklist
	 * @param anItemIndexList of int[] type
	 */
	public void removeItems(int[] anItemIndexList) {
		IRecurrentCheckListItem[] itemList = getCheckListItemList();
		IRecurrentCheckListItem[] newList = new OBRecurrentCheckListItem[itemList.length - anItemIndexList.length];
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
		setCheckListItemList(newList);
	}

	/**
	 * Update an item in the template
	 * @param anIndex of int type
	 * @param anIConvenant of IConvenant type
	 */
	public void updateConvenant(int anIndex, IConvenant anIConvenant) {
		IConvenant[] convList = getConvenantList();
		if (convList != null) {
			if (anIndex < convList.length) {
				convList[anIndex] = anIConvenant;
				setConvenantList(convList);
			}
		}
	}

	/**
	 * Update an item in the template
	 * @param anItemIndex of int type
	 * @param anItem of IRecurrentCheckListItem type
	 */
	public void updateItem(int anItemIndex, IRecurrentCheckListItem anItem) {
		IRecurrentCheckListItem[] itemList = getCheckListItemList();
		if (itemList != null) {
			if (anItemIndex < itemList.length) {
				itemList[anItemIndex] = anItem;
				setCheckListItemList(itemList);
			}
		}
	}
	
	//*************** Lines added by Dattatray Thorat for RBI Annexure ********************
	
	/**
	 * Add a annexure into the checklist
	 * @param anIAnnexure of IAnnexure type
	 */
	public void addAnnexure(IAnnexure anIAnnexure) {
		int numOfConv = 0;
		IAnnexure[] convList = getAnnexureList();
		if (convList != null) {
			numOfConv = convList.length;
		}
		IAnnexure[] newList = new OBAnnexure[numOfConv + 1];
		if (convList != null) {
			for (int ii = 0; ii < convList.length; ii++) {
				newList[ii] = convList[ii];
			}
		}
		newList[numOfConv] = anIAnnexure;
		setAnnexureList(newList);
	}

	/**
	 * Add an list of convenant into the checklist
	 * @param anIAnnexureList of IAnnexure[] type
	 */
	public void addAnnexures(IAnnexure[] anIAnnexureList) {
		int numOfConv = 0;
		IAnnexure[] convList = getAnnexureList();
		if (convList != null) {
			numOfConv = convList.length;
		}
		IAnnexure[] newList = new OBAnnexure[numOfConv + anIAnnexureList.length];
		if (convList != null) {
			for (int ii = 0; ii < convList.length; ii++) {
				newList[ii] = convList[ii];
			}
		}
		for (int ii = 0; ii < anIAnnexureList.length; ii++) {
			newList[ii + numOfConv] = anIAnnexureList[ii];
		}
		setAnnexureList(newList);
	}
	
	
	/**
	 * Remove a list of convenant into the checklist
	 * @param anIndexList of int[] type
	 */
	public void removeAnnexures(int[] anIndexList) {
		IAnnexure[] convList = getAnnexureList();
		IAnnexure[] newList = new OBAnnexure[convList.length - anIndexList.length];
		int ctr = 0;
		boolean removeFlag = false;
		for (int ii = 0; ii < convList.length; ii++) {
			for (int jj = 0; jj < anIndexList.length; jj++) {
				if (ii == anIndexList[jj]) {
					removeFlag = true;
					break;
				}
			}
			if (!removeFlag) {
				newList[ctr] = convList[ii];
				ctr++;
			}
			removeFlag = false;
		}
		setAnnexureList(newList);
	}


	/**
	 * Update an item in the template
	 * @param anIndex of int type
	 * @param anIAnnexure of IAnnexure type
	 */
	public void updateAnnexure(int anIndex, IAnnexure anIAnnexure) {
		IAnnexure[] convList = getAnnexureList();
		if (convList != null) {
			if (anIndex < convList.length) {
				convList[anIndex] = anIAnnexure;
				setAnnexureList(convList);
			}
		}
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

}
