/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus.type.property.marketfactor;

import com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFItem;

/**
 * This interface represents a MF Checklist Item.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IMFChecklistItem extends ICommonMFItem {
	/**
	 * Get Valuer’s Assigned Factor
	 * 
	 * @return double
	 */
	public double getValuerAssignFactor();

	/**
	 * Set Valuer’s Assigned Factor
	 * 
	 * @param valuerAssignFactor of type double
	 */
	public void setValuerAssignFactor(double valuerAssignFactor);

	/**
	 * Get Weighted Score
	 * 
	 * @return double
	 */
	public double getWeightScore();

	/**
	 * Set Weighted Score
	 * 
	 * @param weightScore of type double
	 */
	public void setWeightScore(double weightScore);
}
