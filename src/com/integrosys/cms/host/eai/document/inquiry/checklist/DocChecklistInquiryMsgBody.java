package com.integrosys.cms.host.eai.document.inquiry.checklist;

import com.integrosys.cms.host.eai.EAIBody;

/**
 * DocumentInquiryMessageBody.
 * 
 * @author $Author: Iwan Satria $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class DocChecklistInquiryMsgBody extends EAIBody implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// -------------------------- /

	private ChecklistCriteria checklistCriteria;

	public ChecklistCriteria getChecklistCriteria() {
		return checklistCriteria;
	}

	public void setChecklistCriteria(ChecklistCriteria checklistCriteria) {
		this.checklistCriteria = checklistCriteria;
	}

	/*
	 * public ChecklistCriteria getChecklistCriteria() { return
	 * checklistCriteria; }
	 * 
	 * public void setChecklistTemplateCriteria( ChecklistCriteria
	 * checklistCriteria) { this.checklistCriteria = checklistCriteria; }
	 */
}
