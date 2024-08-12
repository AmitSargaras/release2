package com.integrosys.cms.app.bridgingloan.bus;

import java.io.Serializable;
import java.util.Date;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface IProjectSchedule extends Serializable {

	public long getScheduleID();

	public void setScheduleID(long scheduleID);

	public long getProjectID();

	public void setProjectID(long projectID);

	public String getProgressStage();

	public void setProgressStage(String progressStage);

	public Date getStartDate();

	public void setStartDate(Date startDate);

	public Date getEndDate();

	public void setEndDate(Date endDate);

	public Date getActualStartDate();

	public void setActualStartDate(Date actualStartDate);

	public Date getActualEndDate();

	public void setActualEndDate(Date actualEndDate);

	public String getRemarks();

	public void setRemarks(String remarks);

	public IDevelopmentDoc[] getDevelopmentDocList();

	public void setDevelopmentDocList(IDevelopmentDoc[] developmentDocList);

	// ========================
	// Common Methods
	// ========================
	public long getCommonRef();

	public void setCommonRef(long commonRef);

	public boolean getIsDeletedInd();

	public void setIsDeletedInd(boolean isDeleted);

}
