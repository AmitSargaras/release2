/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/OBCollateralCheckListOwner.java,v 1.5 2006/08/30 11:46:35 czhou Exp $
 */
package com.integrosys.cms.app.checklist.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class provides the implementation for the ICollateralCheckListOwner
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/08/30 11:46:35 $ Tag: $Name: $
 */
public class OBCollateralCheckListOwner extends OBCheckListOwner implements ICollateralCheckListOwner {
	private long collateralID = ICMSConstant.LONG_INVALID_VALUE;

	private String collateralRef = null;

    private String applicationType = null;

    /**
	 * Default Constructor
	 */
	OBCollateralCheckListOwner() {
	}

	/**
	 * Constructor
	 * @param aCollateralID the long representation of the collateral ID
	 */
	public OBCollateralCheckListOwner(long aLimitProfileID, long aCollateralID) {
		setLimitProfileID(aLimitProfileID);
		setCollateralID(aCollateralID);
	}

	public OBCollateralCheckListOwner(long aLimitProfileID, long aCollateralID, long aSubOwnerID, String aSubOwnerType) {
		// R1.5 CR35: set subOwnerID to invalid value 'cos there can be multiple
		// coborrower for the security,
		// so this field is no longer valid
		super(aLimitProfileID, ICMSConstant.LONG_INVALID_VALUE, aSubOwnerType);
		setCollateralID(aCollateralID);
	}

	public OBCollateralCheckListOwner(long aLimitProfileID, long aCollateralID, String aSubOwnerType) {
		setLimitProfileID(aLimitProfileID);
		setSubOwnerType(aSubOwnerType);
		setCollateralID(aCollateralID);
	}

    public OBCollateralCheckListOwner(long aLimitProfileID, long aCollateralID, String aSubOwnerType, String applicationType) {
        setLimitProfileID(aLimitProfileID);
        setSubOwnerType(aSubOwnerType);
        setCollateralID(aCollateralID);
        setApplicationType(applicationType);
    }

    /**
	 * Get the collateral ID.
	 * @return long - the collateral ID
	 */
	public long getCollateralID() {
		return this.collateralID;
	}

	/**
	 * Set the collateral ID.
	 * @param aCollateralID the long representation
	 */
	public void setCollateralID(long aCollateralID) {
		this.collateralID = aCollateralID;
	}

	public String getCollateralRef() {
		return this.collateralRef;
	}

	public void setCollateralRef(String aCollateralRef) {
		this.collateralRef = aCollateralRef;
	}


    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }
}
