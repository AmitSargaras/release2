/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/OBFixedAssetOthers.java,v 1.10 2005/04/06 06:46:53 wltan Exp $
 */

package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.math.BigDecimal;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This interface represents Fixed Asset/Others of the Asset of type General
 * Charge.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2005/04/06 06:46:53 $ Tag: $Name: $
 */
public class OBFixedAssetOthers extends OBGeneralChargeSubType implements IFixedAssetOthers {

	private static final String ID_PREFIX = "FAO";

	private static final char ID_FILLER = '0';

	private static int ID_MAX_LENGTH = 10;

	private long assetGCFixedAssetOthersID = ICMSConstant.LONG_MIN_VALUE;

	private String fxasstOthrID;

	private String description;

	/**
	 * Default Constructor.
	 */
	public OBFixedAssetOthers() {
	}

	/**
	 * Construct the object from its interface.
	 * @param obj is of type IFixedAssetOthers
	 */
	public OBFixedAssetOthers(IFixedAssetOthers obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * To generate New ID used for very-first time linking with insurance in UI
	 * layer
	 * @return String New ID
	 */
	public static String generateNewID(int index) {
		return GeneralChargeUtil.generateNewID(ID_PREFIX, ID_FILLER, ID_MAX_LENGTH, index);
	}

	/**
	 * Get ref ID for the FAO.
	 * 
	 * @return String
	 */
	public String getID() {
		return getFAOID();
	}

	/**
	 * To get assetGCFixedAssetOthersID
	 * @return long
	 */
	public long getAssetGCFixedAssetOthersID() {
		return assetGCFixedAssetOthersID;
	}

	/**
	 * To set assetGCFixedAssetOthersID
	 * @param assetGCFixedAssetOthersID of type long
	 */
	public void setAssetGCFixedAssetOthersID(long assetGCFixedAssetOthersID) {
		this.assetGCFixedAssetOthersID = assetGCFixedAssetOthersID;
	}

	/**
	 * To get fxasstOthrID
	 * @return String fxasstOthrID
	 */
	public String getFAOID() {
		return fxasstOthrID;
	}

	/**
	 * To set fxasstOthrID
	 * @param fxasstOthrID fxasstOthrID of type String
	 */
	public void setFAOID(String fxasstOthrID) {
		this.fxasstOthrID = fxasstOthrID;
	}

	/**
	 * To get description
	 * @return String description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * To set description
	 * @param description description of type String
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	////////////////////////////////////////////////////////////////////////////
	// //////
	// /////// Methods to calculate derived values
	// ////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	// //////

	// no need to hv method to calculate gross value because it is an absolute
	// value. no calculation required.

	/**
	 * Get calculated net value for this fao.
	 * 
	 * @return Amount denoting net value of the fao
	 */
	public Amount getCalculatedNetValue() {
		if ((getGrossValue() == null) || (getMargin() < 0)) {
			return null;
		}
		if (getMargin() == 0) {
			return new Amount(0, getValuationCurrency());
		}
		return (getMargin() == 100) ? getGrossValue() : getGrossValue().multiply((new BigDecimal(getMargin())));
	}

	/**
	 * Return a String representation of this object.
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Return the hash code
	 * @return int
	 */
	public int hashCode() {
		String hash = String.valueOf(assetGCFixedAssetOthersID);
		return hash.hashCode();
	}

	/**
	 * Test for equality.
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBFixedAssetOthers)) {
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
