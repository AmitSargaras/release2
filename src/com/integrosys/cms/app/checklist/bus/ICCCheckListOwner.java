/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/ICCCheckListOwner.java,v 1.3 2006/08/07 03:40:52 czhou Exp $
 */
package com.integrosys.cms.app.checklist.bus;

/**
 * This interface defines the list of attributes that is required for a
 * constractual/constitutional checklist owner
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2006/08/07 03:40:52 $ Tag: $Name: $
 */
public interface ICCCheckListOwner extends ICheckListOwner {
	// R1.5 CR35: moved all methods up to parent 'cos ICollateralCheckListOwner
	// is using as well
}
