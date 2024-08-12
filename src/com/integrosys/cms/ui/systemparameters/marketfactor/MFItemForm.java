/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.ui.systemparameters.marketfactor;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MFItemForm extends CommonForm implements Serializable {

	private String fromEvent;

	private String indexID;

	private String isCreate;

	private String factorDescription;

	private String weightPercentage;

	/**
	 * @return Returns the fromEvent.
	 */
	public String getFromEvent() {
		return fromEvent;
	}

	/**
	 * @param fromEvent The fromEvent to set.
	 */
	public void setFromEvent(String fromEvent) {
		this.fromEvent = fromEvent;
	}

	/**
	 * @return Returns the indexID.
	 */
	public String getIndexID() {
		return indexID;
	}

	/**
	 * @param indexID The indexID to set.
	 */
	public void setIndexID(String indexID) {
		this.indexID = indexID;
	}

	/**
	 * @return Returns the isCreate.
	 */
	public String getIsCreate() {
		return isCreate;
	}

	/**
	 * @param isCreate The isCreate to set.
	 */
	public void setIsCreate(String isCreate) {
		this.isCreate = isCreate;
	}

	/**
	 * @return Returns the factorDescription.
	 */
	public String getFactorDescription() {
		return factorDescription;
	}

	/**
	 * @param factorDescription The factorDescription to set.
	 */
	public void setFactorDescription(String factorDescription) {
		this.factorDescription = factorDescription;
	}

	/**
	 * @return Returns the weightPercentage.
	 */
	public String getWeightPercentage() {
		return weightPercentage;
	}

	/**
	 * @param weightPercentage The weightPercentage to set.
	 */
	public void setWeightPercentage(String weightPercentage) {
		this.weightPercentage = weightPercentage;
	}

	public String[][] getMapper() {
		// TODO Auto-generated method stub
		String[][] input = { { "MFItemForm", "com.integrosys.cms.ui.systemparameters.marketfactor.MFItemMapper" }, };
		return input;
	}
}
