/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/proxy/CheckListProxyManagerFactory.java,v 1.1 2003/06/30 09:02:23 hltan Exp $
 */
package com.integrosys.cms.app.checklist.proxy;

import com.integrosys.base.techinfra.context.BeanHouse;

/**
 * Factory class that instantiate the ICheckListProxyManager.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/30 09:02:23 $ Tag: $Name: $
 */
public class CheckListProxyManagerFactory {
	/**
	 * Default Constructor
	 */
	public CheckListProxyManagerFactory() {
	}

	/**
	 * Get the checklist proxy manager.
	 * @return ICheckListProxyManager - the checklist proxy manager
	 */
	public static ICheckListProxyManager getCheckListProxyManager() {
		return (ICheckListProxyManager) BeanHouse.get("checklistProxy");
	}
}