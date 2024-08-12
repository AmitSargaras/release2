/*
Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * ActionForm for PropertyTypeAction.
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $String: $ Tag: $Name: $
 */
public class ProjectScheduleForm extends TrxContextForm implements Serializable {
	private String scheduleID = "";

	private String projectID = "";

	private String progressStage = "";

	private String startDate = "";

	private String endDate = "";

	private String actualStartDate = "";

	private String actualEndDate = "";

	private String remarks = "";

	public String getScheduleID() {
		return scheduleID;
	}

	public void setScheduleID(String scheduleID) {
		this.scheduleID = scheduleID;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getProgressStage() {
		return progressStage;
	}

	public void setProgressStage(String progressStage) {
		this.progressStage = progressStage;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(String actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public String getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(String actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String[][] getMapper() {
		String[][] input = {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue" },
				{ "objBridgingLoan", "com.integrosys.cms.ui.bridgingloan.ProjectScheduleMapper" }, };
		return input;
	}
}