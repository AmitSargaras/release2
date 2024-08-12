/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/sublimittype/ISubLimitType.java,v 1.1 2005/10/06 03:39:36 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.sublimittype;

import java.io.Serializable;

import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfo;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-15
 * @Tag 
 *      com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType.java
 */
public interface ISubLimitType extends ICommodityMainInfo, Serializable {
	/**
	 * @return long - subLimitTypeID ID
	 */
	public long getSubLimitTypeID();

	/**
	 * @param subLimitTypeID - long
	 */
	public void setSubLimitTypeID(long subLimitTypeID);

	/**
	 * @return Returns the limitType.
	 */
	public String getLimitType();

	/**
	 * @param limitType
	 */
	public void setLimitType(String limitType);

	/**
	 * @return Returns the subLimitType.
	 */
	public String getSubLimitType();

	/**
	 * @param subLimitType
	 */
	public void setSubLimitType(String subLimitType);

	/**
	 * @return Returns the groupID.
	 */
	public long getGroupID();

	/**
	 * @param groupID
	 */
	public void setGroupID(long groupID);

	/**
	 * Get common reference for actual and staging sublimittype.
	 * 
	 * @return long
	 */
	public long getCommonRef();

	/**
	 * Set common reference for actual and staging sublimittype.
	 * 
	 * @param commonRef of type long
	 */
	public void setCommonRef(long commonRef);
}
