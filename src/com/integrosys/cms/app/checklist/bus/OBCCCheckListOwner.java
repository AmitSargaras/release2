/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/OBCCCheckListOwner.java,v 1.4 2006/08/07 03:40:52 czhou Exp $
 */
package com.integrosys.cms.app.checklist.bus;

/**
 * This class provides the implementation for ICCCheckListOwner
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/08/07 03:40:52 $ Tag: $Name: $
 */
public class OBCCCheckListOwner extends OBCheckListOwner implements ICCCheckListOwner {
	private static final long serialVersionUID = 274733714177842896L;

	/**
	 * Constructor
	 * @param aLimitProfileID the long representation of the limit profile ID
	 * @param aSubOwnerID long representation of the sub owner ID
	 * @param aSubOwnerType String representation of the sub owner type
	 */
	public OBCCCheckListOwner(long aLimitProfileID, long aSubOwnerID, String aSubOwnerType) {
		super(aLimitProfileID, aSubOwnerID, aSubOwnerType);
	}

	public OBCCCheckListOwner(long aLimitProfileID, long aSubOwnerID, String aSubOwnerType, String applicationType) {
		super(aLimitProfileID, aSubOwnerID, aSubOwnerType, applicationType);
	}

}
