/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/sublimit/ISubLimit.java,v 1.1 2005/10/06 05:49:52 hmbao Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity.sublimit;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-27
 * @Tag 
 *      com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit.java
 */
public interface ISubLimit extends java.io.Serializable {
	public long getSubLimitID();

	public void setSubLimitID(long subLimitID);

	// public long getChargeID();
	//
	// public void setChargeID(long chargeID);

	public String getActiveAmount();

	public void setActiveAmount(String activeAmount);

	public String getSubLimitAmount();

	public void setSubLimitAmount(String subLimitAmount);

	public String getSubLimitCCY();

	public void setSubLimitCCY(String subLimitCCY);

	public String getSubLimitType();

	public void setSubLimitType(String subLimitType);

	public long getGroupID();

	public void setGroupID(long groupID);

	public long getCommonRef();

	public void setCommonRef(long commonRef);

	public long getVersionTime();

	public void setVersionTime(long versionTime);

	public String getStatus();

	public void setStatus(String status);

	public boolean isInnerLimit();

	public void setInnerLimit(boolean innerFlag);
}
