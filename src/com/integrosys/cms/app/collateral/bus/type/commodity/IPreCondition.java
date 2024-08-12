/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/IPreCondition.java,v 1.1 2005/07/14 03:45:01 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.io.Serializable;
import java.util.Date;

/**
 * This interface represents a Pre-Condition entity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/07/14 03:45:01 $ Tag: $Name: $
 */
public interface IPreCondition extends Serializable {
	/**
	 * Get precondition primary key/id.
	 * 
	 * @return long
	 */
	public long getPreConditionID();

	/**
	 * Set precondition primary key/id.
	 * 
	 * @param preConditionID of type long
	 */
	public void setPreConditionID(long preConditionID);

	/**
	 * Get pre-condition.
	 * 
	 * @return String
	 */
	public String getPreCondition();

	/**
	 * Set pre-condition.
	 * 
	 * @param preCondition of type String
	 */
	public void setPreCondition(String preCondition);

	/**
	 * Get user info to display.
	 * 
	 * @return String
	 */
	public String getUserInfo();

	/**
	 * Set user info to display.
	 * 
	 * @param userInfo of type String
	 */
	public void setUserInfo(String userInfo);

	/**
	 * Get user id.
	 * 
	 * @return long
	 */
	public long getUserID();

	/**
	 * Set user id.
	 * 
	 * @param userID of type long
	 */
	public void setUserID(long userID);

	/**
	 * Get last update date.
	 * 
	 * @return Date
	 */
	public Date getUpdateDate();

	/**
	 * Set last update date.
	 * 
	 * @param updateDate of type Date
	 */
	public void setUpdateDate(Date updateDate);

	/**
	 * Get limit profile id.
	 * 
	 * @return long
	 */
	public long getLimitProfileID();

	/**
	 * Set limit profile id.
	 * 
	 * @param limitProfileID of type long
	 */
	public void setLimitProfileID(long limitProfileID);
}