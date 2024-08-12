package com.integrosys.cms.app.chktemplate.proxy;

import com.integrosys.base.techinfra.context.BeanHouse;

/**
 * Factory class that instantiate the ICheckListProxyManager.
 */

public class CheckListTemplateProxyManagerFactory {

	/**
	 * Default Constructor
	 */
	public CheckListTemplateProxyManagerFactory() {
	}

	/**
	 * Get the checklist proxy manager.
	 * @return ICheckListProxyManager - the checklist proxy manager
	 */
	public static ICheckListTemplateProxyManager getCheckListTemplateProxyManager() {
		return (ICheckListTemplateProxyManager) BeanHouse.get("templateProxy");
	}
}
