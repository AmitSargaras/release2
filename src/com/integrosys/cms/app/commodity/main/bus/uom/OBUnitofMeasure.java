/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/uom/OBUnitofMeasure.java,v 1.4 2004/08/19 04:45:13 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.uom;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.main.bus.OBCommodityMainInfo;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents user defined unit of measure.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/19 04:45:13 $ Tag: $Name: $
 */
public class OBUnitofMeasure extends OBCommodityMainInfo implements IUnitofMeasure {

	private long unitofMeasureID = ICMSConstant.LONG_INVALID_VALUE;

	private long profileID = ICMSConstant.LONG_INVALID_VALUE;

	private long groupID = ICMSConstant.LONG_INVALID_VALUE;

	private long commonRefID = ICMSConstant.LONG_INVALID_VALUE;

	private String unitofMeasureName;

	private Quantity marketQuantity;

	private Quantity metricQuantity;

	private IProfile commodityProfile;

	/**
	 * Default Constructor.
	 */
	public OBUnitofMeasure() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IUnitofMeasure
	 */
	public OBUnitofMeasure(IUnitofMeasure obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Construct the object with specified parameters.
	 * 
	 * @param unitofMeasureID
	 * @param profileID
	 * @param unitofMeasureName
	 * @param marketQuantity
	 * @param metricQuantity
	 */
	public OBUnitofMeasure(long unitofMeasureID, long profileID, long groupID, long commonRefID,
			String unitofMeasureName, Quantity marketQuantity, Quantity metricQuantity) {
		this.unitofMeasureID = unitofMeasureID;
		this.profileID = profileID;
		this.groupID = groupID;
		this.commonRefID = commonRefID;
		this.unitofMeasureName = unitofMeasureName;
		this.marketQuantity = marketQuantity;
		this.metricQuantity = metricQuantity;
	}

	/**
	 * Get the user-defined unit of measure ID.
	 * 
	 * @return long - ID for the unit of measure
	 */
	public long getUnitofMeasureID() {
		return unitofMeasureID;
	}

	/**
	 * Set the user-defined unit of measure ID
	 * 
	 * @param unitofMeasureID
	 */
	public void setUnitofMeasureID(long unitofMeasureID) {
		this.unitofMeasureID = unitofMeasureID;
	}

	/**
	 * Get the profile ID for which the unit of measure is being defined for.
	 * 
	 * @return long - ID for the commodity profile
	 */
	public long getProfileID() {
		return profileID;
	}

	/**
	 * Set the profile ID for which the unit of meausre is being defined for.
	 * 
	 * @param profileID
	 */
	public void setProfileID(long profileID) {
		this.profileID = profileID;
	}

	/**
	 * Get commodity profile information.
	 * 
	 * @return IProfile
	 */
	public IProfile getCommodityProfile() {
		return commodityProfile;
	}

	/**
	 * Set commodity profile information.
	 * 
	 * @param commodityProfile of type IProfile
	 */
	public void setCommodityProfile(IProfile commodityProfile) {
		this.commodityProfile = commodityProfile;
	}

	/**
	 * Get the group ID to which the unit of measure belongs. Since a set of
	 * unit of measure can be submitted in a singal transaction, this group ID
	 * identify the set of unit of measure that were submitted together.
	 * 
	 * @return long - ID for the group
	 */
	public long getGroupID() {
		return groupID;
	}

	/**
	 * Set the group ID to which the unit of meausre belongs to. Since a set of
	 * unit of measure can be submitted in a singal transaction, this group ID
	 * identify the set of unit of measure that were submitted together.
	 * 
	 * @param groupID
	 */
	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	/**
	 * Get the common reference ID for the unit of measure. Since a set of unit
	 * of measure can be submitted in a singal transaction, this common
	 * reference ID identifies the staging record from which an actual biz
	 * record is created. In other words, the related staging and actual record
	 * will have the same common reference ID.
	 * 
	 * @return long - ID for the group
	 */
	public long getCommonReferenceID() {
		return commonRefID;
	}

	/**
	 * Set the common reference ID to which the unit of meausre belongs to.
	 * Since a set of unit of measure can be submitted in a singal transaction,
	 * this common reference ID identifies the staging record from which an
	 * actual biz record is created. In other words, the related staging and
	 * actual record will have the same common reference ID.
	 * 
	 * @param commonRefID
	 */
	public void setCommonReferenceID(long commonRefID) {
		this.commonRefID = commonRefID;
	}

	/**
	 * Get the description for the user-defined unit of measure.
	 * 
	 * @return
	 */
	public String getName() {
		return unitofMeasureName;
	}

	/**
	 * Set the description for the user-defined unit of measure.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.unitofMeasureName = name;
	}

	/**
	 * Get the equivalent quantity in the unit of measure commonly used in the
	 * market for the commodity.
	 * 
	 * @return Quantity - quantity
	 */
	public Quantity getMarketQuantity() {
		return marketQuantity;
	}

	/**
	 * Set the equivalent quantity in the unit of measure commonly used in the
	 * market for the commodity.
	 * 
	 * @param quantity - the equivalent quantity
	 */
	public void setMarketQuantity(Quantity quantity) {
		this.marketQuantity = quantity;
	}

	/**
	 * Get the equivalent quanity in the metric unit of measure for the
	 * user-defined unit of measure.
	 * 
	 * @return Quantity - quantiy
	 */
	public Quantity getMetricQuantity() {
		return metricQuantity;
	}

	/**
	 * Set the equivalent quantity in the metric unit of measure for the
	 * user-defined unit of measure.
	 * 
	 * @param quantity - metric quantity
	 */
	public void setMetricQuantity(Quantity quantity) {
		this.metricQuantity = quantity;
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
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof OBUnitofMeasure)) {
			return false;
		}
		if (!super.equals(obj)) {
			return false;
		}

		final OBUnitofMeasure obUnitofMeasure = (OBUnitofMeasure) obj;

		if (unitofMeasureID != obUnitofMeasure.unitofMeasureID) {
			return false;
		}

		return true;
	}

	/**
	 * Return the hash code
	 * 
	 * @return int
	 */
	public int hashCode() {
		return (int) (unitofMeasureID ^ (unitofMeasureID >>> 32));
	}

}
