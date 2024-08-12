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
public interface IDevelopmentDoc extends Serializable {

	public long getDevDocID();

	public void setDevDocID(long devDocID);

	public long getScheduleID();

	public void setScheduleID(long scheduleID);

	public String getDocName();

	public void setDocName(String docName);

	public String getDocRef();

	public void setDocRef(String docRef);

	public Date getReceiveDate();

	public void setReceiveDate(Date receiveDate);

	public Date getDocDate();

	public void setDocDate(Date docDate);

	public String getRemarks();

	public void setRemarks(String remarks);

	// ========================
	// Common Methods
	// ========================
	public long getCommonRef();

	public void setCommonRef(long commonRef);

	public boolean getIsDeletedInd();

	public void setIsDeletedInd(boolean isDeleted);

}
