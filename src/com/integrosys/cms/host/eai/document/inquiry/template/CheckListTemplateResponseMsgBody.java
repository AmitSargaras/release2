package com.integrosys.cms.host.eai.document.inquiry.template;

import java.util.Vector;

import com.integrosys.cms.host.eai.EAIBody;

/**
 * DocumentInquiryMessageBody.
 * 
 * @author $Author: shphoon $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class CheckListTemplateResponseMsgBody extends EAIBody implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	private CheckListTemplate checkListTemplate;

	private Vector checklistTemplateItem;

	public Vector getChecklistTemplateItem() {
		return checklistTemplateItem;
	}

	public void setChecklistTemplateItem(Vector checklistTemplateItem) {
		this.checklistTemplateItem = checklistTemplateItem;
	}

	public CheckListTemplate getCheckListTemplate() {
		return checkListTemplate;
	}

	public void setCheckListTemplate(CheckListTemplate checklistTemplate) {
		this.checkListTemplate = checklistTemplate;
	}

}
