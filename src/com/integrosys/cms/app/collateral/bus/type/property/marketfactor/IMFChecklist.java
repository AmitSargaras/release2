/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus.type.property.marketfactor;

import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFTemplate;

/**
 * This interface represents a MF Checklist.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IMFChecklist extends ICommonMFTemplate {
	/**
	 * Get MF Checklist ID.
	 * 
	 * @return long
	 */
	public long getMFChecklistID();

	/**
	 * Set MF Checklist ID.
	 * 
	 * @param mFChecklistID of type long
	 */
	public void setMFChecklistID(long mFChecklistID);

	/**
	 * Get collateral ID.
	 * 
	 * @return long
	 */
	public long getCollateralID();

	/**
	 * Set collateral ID.
	 * 
	 * @param collateralID of type long
	 */
	public void setCollateralID(long collateralID);

	/**
	 * Get collateral Sub Type Code and collateral Type Code
	 * 
	 * @return ICollateralSubType
	 */
	public ICollateralSubType getCollateralSubType();

	/**
	 * Set collateral Sub Type Code and collateral Type Code
	 * 
	 * @param value of type ICollateralSubType
	 */
	public void setCollateralSubType(ICollateralSubType value);

	/**
	 * Get list of MF Checklist Item.
	 * 
	 * @return array of IMFChecklistItem
	 */
	public IMFChecklistItem[] getMFChecklistItemList();

	/**
	 * Set list of MF Checklist Item.
	 * 
	 * @param mFChecklistItemList of type array of IMFChecklistItem
	 */
	public void setMFChecklistItemList(IMFChecklistItem[] mFChecklistItemList);

}
