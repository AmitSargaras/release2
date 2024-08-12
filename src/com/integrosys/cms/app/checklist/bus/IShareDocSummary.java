package com.integrosys.cms.app.checklist.bus;

import com.integrosys.cms.app.chktemplate.bus.IDocumentHeldItem;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2006/07/31 02:07:41 $ Tag: $Name: $
 */
public interface IShareDocSummary extends IDocumentHeldItem {

	public boolean getIsMandatory();

	public void setIsMandatory(boolean mandatory);

	public String getChecklistCategory();

	public void setChecklistCategory(String checklistCategory);

	// public long getLeID();
	// public void setLeID(long leID);
	//
	// public String getLeName();
	// public void setLeName(String leName);
	//
	// public long getChecklistID();
	// public void setChecklistID(long checklistID);

}
