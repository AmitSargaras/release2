/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/dataprotection/bus/IDataAccessProfile.java,v 1.1 2005/10/27 06:33:40 lyng Exp $
 */
package com.integrosys.cms.app.dataprotection.bus;

import java.io.Serializable;
import java.util.List;

/**
 * This interface represents a Collateral entity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/10/27 06:33:40 $ Tag: $Name: $
 */
public interface IDataAccessProfile extends Serializable {
	/**
	 * Get data access profile primary id.
	 * 
	 * @return long
	 */
	public long getDapID();

	/**
	 * Set data access profile primary id.
	 * 
	 * @param dapID of type long
	 */
	public void setDapID(long dapID);

	/**
	 * Get data access module type.
	 * 
	 * @return String
	 */
	public String getModuleType();

	/**
	 * Set data access module type.
	 * 
	 * @param moduleType of type String
	 */
	public void setModuleType(String moduleType);

	/**
	 * Get data access module subtype.
	 * 
	 * @return String
	 */
	public String getModuleSubType();

	/**
	 * Set data acess module subtype.
	 * 
	 * @param moduleSubType of type String
	 */
	public void setModuleSubType(String moduleSubType);

	/**
	 * Get data access fieldname.
	 * 
	 * @return String
	 */
	public String getFieldName();

	/**
	 * Set data access fieldname.
	 * 
	 * @param fieldName of type String
	 */
	public void setFieldName(String fieldName);

	/**
	 * Get team type membership id.
	 * 
	 * @return long
	 */
	public long getTeamTypeMshipID();

	/**
	 * Set team type membership id.
	 * 
	 * @param teamTypeMshipID of type long
	 */
	public void setTeamTypeMshipID(long teamTypeMshipID);

	/**
	 * Get granted booking location list.
	 * 
	 * @return a List of IBookingLocation objects
	 */
	public List getGrantedBkgLoc();

	/**
	 * Set granted booking location list.
	 * 
	 * @param grantedBkgLoc a List of IBookingLocation objects
	 */
	public void setGrantedBkgLoc(List grantedBkgLoc);

	/**
	 * Get a list of booking location that is denied to access the data.
	 * 
	 * @return a List of IBookingLocation objects
	 */
	public List getDeniedBkgLoc();

	/**
	 * Set a list of booking location denied to access data.
	 * 
	 * @param deniedBkgLoc a List of IBookingLocation objects
	 */
	public void setDeniedBkgLoc(List deniedBkgLoc);
}