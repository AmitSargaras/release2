/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus.type.property.marketfactor;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFItem;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFTemplate;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.OBCommonMFTemplate;

/**
 * Data model holds MF Checklist. This class is a subclass of
 * OBCommonMFTemplate.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBMFChecklist extends OBCommonMFTemplate implements IMFChecklist {
	private long mFChecklistID = ICMSConstant.LONG_INVALID_VALUE;

	private long collateralID = ICMSConstant.LONG_INVALID_VALUE;

	private ICollateralSubType collSubType;

	private IMFChecklistItem[] mFChecklistItemList;

	/**
	 * Default Constructor.
	 */
	public OBMFChecklist() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IMFChecklist
	 */
	public OBMFChecklist(IMFChecklist obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IMFTemplate
	 */
	public OBMFChecklist(IMFTemplate obj) {
		ICommonMFTemplate template = (ICommonMFTemplate) obj;

		AccessorUtil.copyValue(template, this);
		ICommonMFItem[] itemList = obj.getMFItemList();

		if ((itemList != null) && (itemList.length != 0)) {
			this.mFChecklistItemList = new OBMFChecklistItem[itemList.length];
			for (int i = 0; i < itemList.length; i++) {
				ICommonMFItem commonItem = itemList[i];
				IMFChecklistItem item = new OBMFChecklistItem(commonItem);
				this.mFChecklistItemList[i] = item;
			}
		}
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist#getMFChecklistID
	 */
	public long getMFChecklistID() {
		return mFChecklistID;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist#setMFChecklistID
	 */
	public void setMFChecklistID(long mFChecklistID) {
		this.mFChecklistID = mFChecklistID;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist#getCollateralID
	 */
	public long getCollateralID() {
		return collateralID;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist#setCollateralID
	 */
	public void setCollateralID(long collateralID) {
		this.collateralID = collateralID;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist#getCollateralSubType
	 */
	public ICollateralSubType getCollateralSubType() {
		return collSubType;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist#setCollateralSubType
	 */
	public void setCollateralSubType(ICollateralSubType value) {
		this.collSubType = value;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist#getMFChecklistItemList
	 */
	public IMFChecklistItem[] getMFChecklistItemList() {
		return this.mFChecklistItemList;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist#setMFChecklistItemList
	 */
	public void setMFChecklistItemList(IMFChecklistItem[] mFChecklistItemList) {
		this.mFChecklistItemList = mFChecklistItemList;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBMFChecklist)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}

}