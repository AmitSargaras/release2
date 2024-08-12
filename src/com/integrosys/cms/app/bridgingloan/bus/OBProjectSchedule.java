package com.integrosys.cms.app.bridgingloan.bus;

import java.util.Date;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class OBProjectSchedule implements IProjectSchedule {
	private long scheduleID = ICMSConstant.LONG_INVALID_VALUE;

	private long projectID = ICMSConstant.LONG_INVALID_VALUE;

	private String progressStage;

	private Date startDate;

	private Date endDate;

	private Date actualStartDate;

	private Date actualEndDate;

	private String remarks;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	private boolean isDeletedInd;

	private IDevelopmentDoc[] developmentDocList;

	/**
	 * Default Constructor
	 */
	public OBProjectSchedule() {
	}

	public long getScheduleID() {
		return scheduleID;
	}

	public void setScheduleID(long scheduleID) {
		this.scheduleID = scheduleID;
	}

	public long getProjectID() {
		return projectID;
	}

	public void setProjectID(long projectID) {
		this.projectID = projectID;
	}

	public String getProgressStage() {
		return progressStage;
	}

	public void setProgressStage(String progressStage) {
		this.progressStage = progressStage;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(Date actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public Date getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public IDevelopmentDoc[] getDevelopmentDocList() {
		return developmentDocList;
	}

	public void setDevelopmentDocList(IDevelopmentDoc[] developmentDocList) {
		this.developmentDocList = developmentDocList;
	}

	// ========================
	// Common Methods
	// ========================
	public long getCommonRef() {
		return commonRef;
	}

	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}

	public boolean getIsDeletedInd() {
		return isDeletedInd;
	}

	public void setIsDeletedInd(boolean isDeletedInd) {
		this.isDeletedInd = isDeletedInd;
	}
}