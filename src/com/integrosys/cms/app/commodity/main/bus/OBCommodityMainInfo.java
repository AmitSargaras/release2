/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/OBCommodityMainInfo.java,v 1.4 2004/08/19 05:08:14 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents base class for commodity maintainence business objects.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/19 05:08:14 $ Tag: $Name: $
 */
public abstract class OBCommodityMainInfo implements ICommodityMainInfo {

	protected long versionTime;

	protected String status = ICMSConstant.STATE_ACTIVE;

	/**
	 * Get version time for the object.
	 * 
	 * @return long
	 */
	public long getVersionTime() {
		return versionTime;
	}

	/**
	 * Set version time for object.
	 * 
	 * @param versionTime - long
	 */
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	/**
	 * Get status for object.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set status for object.
	 * 
	 * @param status - String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Test equality for object.
	 * 
	 * @param o - Object to be tested.
	 * @return boolean
	 */
	public boolean equals(Object o) {
		return this.toString().equals(o.toString());
	}

	/**
	 * Returns string representation for object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

}
