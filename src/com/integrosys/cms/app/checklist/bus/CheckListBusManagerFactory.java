/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/CheckListBusManagerFactory.java,v 1.1 2003/06/24 11:35:59 hltan Exp $
 */
package com.integrosys.cms.app.checklist.bus;

/**
 * Factory class that instantiate the ICheckListBusManager.
 * 
 * @author $Author: Abhijit Rudrakshawar $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/24 11:35:59 $ Tag: $Name: $
 */
public class CheckListBusManagerFactory {
	/**
	 * Default Constructor
	 */
	public CheckListBusManagerFactory() {
	}

    /**
	 * Get the checklist bus manager.
	 * @return ICheckListBusManager - the checkList bus manager
	 */
	public static ICheckListBusManager getCheckListBusManager() {
		return new CheckListBusManagerImpl();
	}
}