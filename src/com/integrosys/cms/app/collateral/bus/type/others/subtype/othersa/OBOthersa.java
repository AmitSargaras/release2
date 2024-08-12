/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/
 */
package com.integrosys.cms.app.collateral.bus.type.others.subtype.othersa;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.others.OBOthersCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents others of type Othersa.
 * 
 * @author $Author: lyng $<br>
 * @since $Date: 2005/08/15 09:20:24 $ Tag: $Name: $
 */
public class OBOthersa extends OBOthersCollateral implements IOthersa {
	/**
	 * Default Constructor.
	 */
	private String assetValue;
	private String scrapValue;
	

	public OBOthersa() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_OTHERS_OTHERSA));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IIndustrial
	 */
	public OBOthersa(IOthersa obj) {
		this();
		AccessorUtil.copyValue(obj, this);
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
		else if (!(obj instanceof OBOthersa)) {
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

	public String getAssetValue() {
		return assetValue;
	}

	public void setAssetValue(String assetValue) {
		this.assetValue = assetValue;
	}
	public String getScrapValue() {
		return scrapValue;
	}

	public void setScrapValue(String scrapValue) {
		this.scrapValue = scrapValue;
	}


	


}