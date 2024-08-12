package com.integrosys.cms.app.bridgingloan.bus;

import java.io.Serializable;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface IPropertyType extends Serializable {

	public long getProjectID();

	public void setProjectID(long projectID);

	public long getPropertyTypeID();

	public void setPropertyTypeID(long propertyTypeID);

	public String getPropertyType();

	public void setPropertyType(String propertyType);

	public String getPropertyTypeOthers();

	public void setPropertyTypeOthers(String propertyTypeOthers);

	public int getNoOfUnits();

	public void setNoOfUnits(int noOfUnits);

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