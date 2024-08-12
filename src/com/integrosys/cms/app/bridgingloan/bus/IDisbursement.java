package com.integrosys.cms.app.bridgingloan.bus;

import java.io.Serializable;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface IDisbursement extends Serializable {

	public long getDisbursementID();

	public void setDisbursementID(long disbursementID);

	public long getProjectID();

	public void setProjectID(long projectID);

	public String getPurpose();

	public void setPurpose(String purpose);

	public String getDisRemarks();

	public void setDisRemarks(String disRemarks);

	public IDisbursementDetail[] getDisbursementDetailList();

	public void setDisbursementDetailList(IDisbursementDetail[] disbursementDetailList);

	// ========================
	// Common Methods
	// ========================
	public long getCommonRef();

	public void setCommonRef(long commonRef);

	public boolean getIsDeletedInd();

	public void setIsDeletedInd(boolean isDeleted);

}
