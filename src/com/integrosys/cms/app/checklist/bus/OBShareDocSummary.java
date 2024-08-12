package com.integrosys.cms.app.checklist.bus;

import com.integrosys.cms.app.chktemplate.bus.OBDocumentHeldItem;

/**
 * Represent the details of "View Share Document"
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2006/07/31 02:07:41 $ Tag: $Name: $
 */
public class OBShareDocSummary extends OBDocumentHeldItem implements IShareDocSummary {

	private boolean isMandatory;

	private String checklistCategory;

	public boolean getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(boolean mandatory) {
		isMandatory = mandatory;
	}

	public String getChecklistCategory() {
		return checklistCategory;
	}

	public void setChecklistCategory(String checklistCategory) {
		this.checklistCategory = checklistCategory;
	}

}
