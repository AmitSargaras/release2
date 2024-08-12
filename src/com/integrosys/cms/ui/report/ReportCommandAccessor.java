package com.integrosys.cms.ui.report;

import com.integrosys.cms.batch.reports.IReportRequestManager;

/**
 * Provide the service bean to report ui commands
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class ReportCommandAccessor {
	private IReportRequestManager reportRequestManager;

	/**
	 * @param reportRequestManager the reportRequestManager to set
	 */
	public void setReportRequestManager(IReportRequestManager reportRequestManager) {
		this.reportRequestManager = reportRequestManager;
	}

	/**
	 * @return the reportRequestManager
	 */
	public IReportRequestManager getReportRequestManager() {
		return reportRequestManager;
	}

}
