/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/IApprovedCommodityType.java,v 1.4 2004/08/18 02:35:05 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;

/**
 * An interface represents approved commodity type.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/18 02:35:05 $ Tag: $Name: $
 */
public interface IApprovedCommodityType extends java.io.Serializable {
	/**
	 * Get approved commodity type id.
	 * 
	 * @return long
	 */
	public long getApprovedCommodityTypeID();

	/**
	 * Set approved commodity type id.
	 * 
	 * @param approvedCommodityTypeID of type long
	 */
	public void setApprovedCommodityTypeID(long approvedCommodityTypeID);

	/**
	 * Get approved commodity type profile.
	 * 
	 * @return IProfile
	 */
	public IProfile getProfile();

	/**
	 * Set approved commodity type profile.
	 * 
	 * @param profile of type IProfile
	 */
	public void setProfile(IProfile profile);

	/**
	 * Get common reference of actual and staging approved commodity type.
	 * 
	 * @return long
	 */
	public long getCommonRef();

	/**
	 * Set common reference of actual and staging approved commodity type.
	 * 
	 * @param commonRef of type long
	 */
	public void setCommonRef(long commonRef);

	/**
	 * Get approved commodity type status.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set approved commodity type status.
	 * 
	 * @param status of type Status
	 */
	public void setStatus(String status);
}
