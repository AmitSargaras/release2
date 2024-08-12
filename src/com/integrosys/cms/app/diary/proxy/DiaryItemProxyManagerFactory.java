/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/proxy/DiaryItemProxyManagerFactory.java,v 1.1 2004/05/12 13:10:03 jtan Exp $
 */
package com.integrosys.cms.app.diary.proxy;

/**
 * Factory class that instantiates the IDiaryItemProxyManager.
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/05/12 13:10:03 $ Tag: $Name: $
 */
public class DiaryItemProxyManagerFactory {
	/**
	 * Default Constructor
	 */
	public DiaryItemProxyManagerFactory() {
	}

	/**
	 * creates an instance of the diary item proxy manager
	 * @return
	 */
	public static IDiaryItemProxyManager getProxyManager() {
		return new DiaryItemProxyManagerImpl();
	}
}