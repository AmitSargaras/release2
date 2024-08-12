/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/uom/IUnitofMeasure.java,v 1.3 2004/08/19 04:45:13 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.uom;

import java.io.Serializable;

import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfo;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;

/**
 * This interface represents user-defined unit of measure.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/19 04:45:13 $ Tag: $Name: $
 */
public interface IUnitofMeasure extends ICommodityMainInfo, Serializable {

	/**
	 * Get the user-defined unit of measure ID.
	 * 
	 * @return long - ID for the unit of measure
	 */
	public long getUnitofMeasureID();

	/**
	 * Set the user-defined unit of measure ID
	 * 
	 * @param unitofMeasureID
	 */
	public void setUnitofMeasureID(long unitofMeasureID);

	/**
	 * Get the profile ID for which the unit of measure is being defined for.
	 * 
	 * @return long - ID for the commodity profile
	 */
	public long getProfileID();

	/**
	 * Set the profile ID for which the unit of meausre is being defined for.
	 * 
	 * @param profileID
	 */
	public void setProfileID(long profileID);

	/**
	 * Get commodity profile information.
	 * 
	 * @return IProfile
	 */
	public IProfile getCommodityProfile();

	/**
	 * Set commodity profile information.
	 * 
	 * @param commodityProfile of type IProfile
	 */
	public void setCommodityProfile(IProfile commodityProfile);

	/**
	 * Get the group ID to which the unit of measure belongs. Since a set of
	 * unit of measure can be submitted in a singal transaction, this group ID
	 * identify the set of unit of measure that were submitted together.
	 * 
	 * @return long - ID for the group
	 */
	public long getGroupID();

	/**
	 * Set the group ID to which the unit of meausre belongs to. Since a set of
	 * unit of measure can be submitted in a singal transaction, this group ID
	 * identify the set of unit of measure that were submitted together.
	 * 
	 * @param groupID
	 */
	public void setGroupID(long groupID);

	/**
	 * Get the common reference ID for the unit of measure. Since a set of unit
	 * of measure can be submitted in a singal transaction, this common
	 * reference ID identifies the staging record from which an actual biz
	 * record is created. In other words, the related staging and actual record
	 * will have the same common reference ID.
	 * 
	 * @return long - ID for the group
	 */
	public long getCommonReferenceID();

	/**
	 * Set the common reference ID to which the unit of meausre belongs to.
	 * Since a set of unit of measure can be submitted in a singal transaction,
	 * this common reference ID identifies the staging record from which an
	 * actual biz record is created. In other words, the related staging and
	 * actual record will have the same common reference ID.
	 * 
	 * @param commonRefID
	 */
	public void setCommonReferenceID(long commonRefID);

	/**
	 * Get the description for the user-defined unit of measure.
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * Set the description for the user-defined unit of measure.
	 * 
	 * @param name
	 */
	public void setName(String name);

	/**
	 * Get the equivalent quantity in the unit of measure commonly used in the
	 * market for the commodity.
	 * 
	 * @return Quantity - quantity
	 */
	public Quantity getMarketQuantity();

	/**
	 * Set the equivalent quantity in the unit of measure commonly used in the
	 * market for the commodity.
	 * 
	 * @param quantity - the equivalent quantity
	 */
	public void setMarketQuantity(Quantity quantity);

	/**
	 * Get the equivalent quanity in the metric unit of measure for the
	 * user-defined unit of measure.
	 * 
	 * @return Quantity - quantiy
	 */
	public Quantity getMetricQuantity();

	/**
	 * Set the equivalent quantity in the metric unit of measure for the
	 * user-defined unit of measure.
	 * 
	 * @param quantity - metric quantity
	 */
	public void setMetricQuantity(Quantity quantity);

}
