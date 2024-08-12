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
public class OBPariPassuCheckListOwner extends OBCheckListOwner implements IPariPassuCheckListOwner {
	private long pariPassuID = ICMSConstant.LONG_INVALID_VALUE;

	private String pariPassuRef = null;

    private String applicationType = null;

    /**
	 * Default Constructor
	 */
    OBPariPassuCheckListOwner() {
	}

	/**
	 * Constructor
	 * @param aCollateralID the long representation of the collateral ID
	 */
	public OBPariPassuCheckListOwner(long aLimitProfileID, long aPariPassu) {
		setLimitProfileID(aLimitProfileID);
		setPariPassuID(aPariPassu);
	}

	public OBPariPassuCheckListOwner(long aLimitProfileID, long aPariPassu, long aSubOwnerID, String aSubOwnerType) {
		// R1.5 CR35: set subOwnerID to invalid value 'cos there can be multiple
		// coborrower for the security,
		// so this field is no longer valid
		super(aLimitProfileID, ICMSConstant.LONG_INVALID_VALUE, aSubOwnerType);
		setPariPassuID(aPariPassu);
	}

	public OBPariPassuCheckListOwner(long aLimitProfileID, long aPariPassu, String aSubOwnerType) {
		setLimitProfileID(aLimitProfileID);
		setSubOwnerType(aSubOwnerType);
		setPariPassuID(aPariPassu);
	}

    public OBPariPassuCheckListOwner(long aLimitProfileID, long aPariPassu, String aSubOwnerType, String applicationType) {
        setLimitProfileID(aLimitProfileID);
        setSubOwnerType(aSubOwnerType);
        setPariPassuID(aPariPassu);
        setApplicationType(applicationType);
    }

    /**
	 * Get the collateral ID.
	 * @return long - the collateral ID
	 */



    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

	public long getPariPassuID() {
		return pariPassuID;
	}

	public void setPariPassuID(long pariPassuID) {
		this.pariPassuID = pariPassuID;
	}

	public String getPariPassuRef() {
		return pariPassuRef;
	}

	public void setPariPassuRef(String pariPassuRef) {
		this.pariPassuRef = pariPassuRef;
	}
}
