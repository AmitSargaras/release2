/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/dataprotection/bus/OBDataAccessProfile.java,v 1.1 2005/10/27 06:33:40 lyng Exp $
 */
package com.integrosys.cms.app.dataprotection.bus;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class represents Data Access Profile.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/10/27 06:33:40 $ Tag: $Name: $
 */
public class OBDataAccessProfile implements IDataAccessProfile {
	private long _dapID;

	private String _moduleType;

	private String _moduleSubType;

	private String _fieldName;

	private long _teamTypeMshipID;

	private List _grantedBkgLoc = new ArrayList();

	private List _deniedBkgLoc = new ArrayList();

	/**
	 * Default Constructor.
	 */
	public OBDataAccessProfile() {
	}

	/**
	 * Get data access profile primary id.
	 * 
	 * @return long
	 */
	public long getDapID() {
		return _dapID;
	}

	/**
	 * Set data access profile primary id.
	 * 
	 * @param dapID of type long
	 */
	public void setDapID(long dapID) {
		_dapID = dapID;
	}

	/**
	 * Get data access module type.
	 * 
	 * @return String
	 */
	public String getModuleType() {
		return _moduleType;
	}

	/**
	 * Set data access module type.
	 * 
	 * @param moduleType of type String
	 */
	public void setModuleType(String moduleType) {
		_moduleType = moduleType;
	}

	/**
	 * Get data access module subtype.
	 * 
	 * @return String
	 */
	public String getModuleSubType() {
		return _moduleSubType;
	}

	/**
	 * Set data acess module subtype.
	 * 
	 * @param moduleSubType of type String
	 */
	public void setModuleSubType(String moduleSubType) {
		_moduleSubType = moduleSubType;
	}

	/**
	 * Get data access fieldname.
	 * 
	 * @return String
	 */
	public String getFieldName() {
		return _fieldName;
	}

	/**
	 * Set data access fieldname.
	 * 
	 * @param fieldName of type String
	 */
	public void setFieldName(String fieldName) {
		_fieldName = fieldName;
	}

	/**
	 * Get team type membership id.
	 * 
	 * @return long
	 */
	public long getTeamTypeMshipID() {
		return _teamTypeMshipID;
	}

	/**
	 * Set team type membership id.
	 * 
	 * @param teamTypeMshipID of type long
	 */
	public void setTeamTypeMshipID(long teamTypeMshipID) {
		_teamTypeMshipID = teamTypeMshipID;
	}

	/**
	 * Get granted booking location list.
	 * 
	 * @return a List of IBookingLocation objects
	 */
	public List getGrantedBkgLoc() {
		return _grantedBkgLoc;
	}

	/**
	 * Set granted booking location list.
	 * 
	 * @param grantedBkgLoc a List of IBookingLocation objects
	 */
	public void setGrantedBkgLoc(List grantedBkgLoc) {
		_grantedBkgLoc = grantedBkgLoc;
	}

	/**
	 * Get a list of booking location that is denied to access the data.
	 * 
	 * @return a List of IBookingLocation objects
	 */
	public List getDeniedBkgLoc() {
		return _deniedBkgLoc;
	}

	/**
	 * Set a list of booking location denied to access data.
	 * 
	 * @param deniedBkgLoc a List of IBookingLocation objects
	 */
	public void setDeniedBkgLoc(List deniedBkgLoc) {
		_deniedBkgLoc = deniedBkgLoc;
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
		String hash = String.valueOf(_dapID);
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
		else if (!(obj instanceof OBDataAccessProfile)) {
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