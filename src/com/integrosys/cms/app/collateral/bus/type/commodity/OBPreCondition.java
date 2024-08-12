/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/OBPreCondition.java,v 1.1 2005/07/14 03:44:37 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents a Pre-Condition entity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/07/14 03:44:37 $ Tag: $Name: $
 */
public class OBPreCondition implements IPreCondition {
	private long preConditionID = ICMSConstant.LONG_MIN_VALUE;

	private String preCondition;

	private String userInfo;

	private long userID;

	private Date updateDate;

	private long limitProfileID = ICMSConstant.LONG_MIN_VALUE;

	/**
	 * Default Constructor.
	 */
	public OBPreCondition() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IPreCondition
	 */
	public OBPreCondition(IPreCondition obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get precondition primary key/id.
	 * 
	 * @return long
	 */
	public long getPreConditionID() {
		return preConditionID;
	}

	/**
	 * Set precondition primary key/id.
	 * 
	 * @param preConditionID of type long
	 */
	public void setPreConditionID(long preConditionID) {
		this.preConditionID = preConditionID;
	}

	/**
	 * Get pre-condition.
	 * 
	 * @return String
	 */
	public String getPreCondition() {
		return preCondition;
	}

	/**
	 * Set pre-condition.
	 * 
	 * @param preCondition of type String
	 */
	public void setPreCondition(String preCondition) {
		this.preCondition = preCondition;
	}

	/**
	 * Get user info to display.
	 * 
	 * @return String
	 */
	public String getUserInfo() {
		return userInfo;
	}

	/**
	 * Set user info to display.
	 * 
	 * @param userInfo of type String
	 */
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * Get user id.
	 * 
	 * @return long
	 */
	public long getUserID() {
		return userID;
	}

	/**
	 * Set user id.
	 * 
	 * @param userID of type long
	 */
	public void setUserID(long userID) {
		this.userID = userID;
	}

	/**
	 * Get last update date.
	 * 
	 * @return Date
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * Set last update date.
	 * 
	 * @param updateDate of type Date
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * Get limit profile id.
	 * 
	 * @return long
	 */
	public long getLimitProfileID() {
		return limitProfileID;
	}

	/**
	 * Set limit profile id.
	 * 
	 * @param limitProfileID of type long
	 */
	public void setLimitProfileID(long limitProfileID) {
		this.limitProfileID = limitProfileID;
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
	 * Return the hash code
	 * 
	 * @return int
	 */
	public int hashCode() {
		String hash = String.valueOf(preConditionID);
		return hash.hashCode();
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
		else if (!(obj instanceof OBPreCondition)) {
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