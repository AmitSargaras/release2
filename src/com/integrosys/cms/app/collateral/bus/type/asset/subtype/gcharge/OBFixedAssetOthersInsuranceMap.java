/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/OBFixedAssetOthersInsuranceMap.java,v 1.1 2005/03/15 03:41:05 htli Exp $
 */

package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/*
 * This is OBFixedAssetOthersInsuranceMap class
 * @author $Author: htli $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/03/15 03:41:05 $
 * Tag: $Name:  $
 */

public class OBFixedAssetOthersInsuranceMap implements IFixedAssetOthersInsuranceMap {

	private long cmsAsstGcInsrFaoMapID = ICMSConstant.LONG_MIN_VALUE;

	private long cmsAsstGcFaoID = ICMSConstant.LONG_MIN_VALUE;

	private long cmsAsstGcInsrID = ICMSConstant.LONG_MIN_VALUE;

	private long cmsRefID = ICMSConstant.LONG_MIN_VALUE;

	private String status;

	// private IFixedAssetOthersInsuranceMap obj;

	/**
	 * Default Constructor.
	 */
	public OBFixedAssetOthersInsuranceMap() {
	}

	/**
	 * Construct the object from its interface.
	 * @param obj is of type IFixedAssetOthersInsuranceMap
	 */
	public OBFixedAssetOthersInsuranceMap(IFixedAssetOthersInsuranceMap obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * To get cmsAsstGcFaoID
	 * @return long cmsAsstGcFaoID
	 */

	public long getCmsAsstGcFaoID() {
		return cmsAsstGcFaoID;
	}

	/**
	 * To set cmsAsstGcFaoID
	 * @param cmsAsstGcFaoID cmsAsstGcFaoID of type long
	 */
	public void setCmsAsstGcFaoID(long cmsAsstGcFaoID) {
		this.cmsAsstGcFaoID = cmsAsstGcFaoID;
	}

	/**
	 * To get cmsAsstGcInsrFaoMapID
	 * @return long cmsAsstGcInsrFaoMapID
	 */

	public long getCmsAsstGcInsrFaoMapID() {
		return cmsAsstGcInsrFaoMapID;
	}

	/**
	 * To set cmsAsstGcInsrFaoMapID
	 * @param cmsAsstGcInsrFaoMapID cmsAsstGcInsrFaoMapID of type long
	 */
	public void setCmsAsstGcInsrFaoMapID(long cmsAsstGcInsrFaoMapID) {
		this.cmsAsstGcInsrFaoMapID = cmsAsstGcInsrFaoMapID;
	}

	/**
	 * To get cmsAsstGcInsrID
	 * @return long cmsAsstGcInsrID
	 */

	public long getCmsAsstGcInsrID() {
		return cmsAsstGcInsrID;
	}

	/**
	 * To set cmsAsstGcInsrID
	 * @param cmsAsstGcInsrID cmsAsstGcInsrID of type long
	 */
	public void setCmsAsstGcInsrID(long cmsAsstGcInsrID) {
		this.cmsAsstGcInsrID = cmsAsstGcInsrID;
	}

	/**
	 * To get cmsRefID
	 * @return long cmsRefID
	 */

	public long getCmsRefID() {
		return cmsRefID;
	}

	/**
	 * To set cmsRefID
	 * @param cmsRefID cmsRefID of type long
	 */
	public void setCmsRefID(long cmsRefID) {
		this.cmsRefID = cmsRefID;
	}

	/**
	 * To get status
	 * @return String status
	 */

	public String getStatus() {
		return status;
	}

	/**
	 * To set status
	 * @param status status of type String
	 */
	public void setStatus(String status) {
		this.status = status;
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
		String hash = String.valueOf(cmsAsstGcInsrFaoMapID);
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
		else if (!(obj instanceof OBFixedAssetOthersInsuranceMap)) {
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
