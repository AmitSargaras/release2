/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/reports/ReportRequestManagerFactory.java,v 1.2 2006/03/08 07:04:29 hshii Exp $
 */
package com.integrosys.cms.batch.reports;

/**
 * Factory class that instantiates the IDiaryItemProxyManager.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2006/03/08 07:04:29 $ Tag: $Name: $
 * @deprecated consider to get the implementation of IReportRequestManager from
 *             context which has everything wired
 */
public class ReportRequestManagerFactory {
	/**
	 * Default Constructor
	 */
	public ReportRequestManagerFactory() {
	}

	/**
	 * creates an instance of the diary item proxy manager
	 * @return
	 */
	public static IReportRequestManager getProxyManager() {
		return new ReportRequestManagerImpl();
	}
}