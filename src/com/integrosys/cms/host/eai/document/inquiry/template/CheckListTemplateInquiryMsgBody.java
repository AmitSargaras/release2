package com.integrosys.cms.host.eai.document.inquiry.template;

import com.integrosys.cms.host.eai.EAIBody;

/**
 * DocumentInquiryMessageBody.
 * 
 * @author $Author: Iwan Satria $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class CheckListTemplateInquiryMsgBody extends EAIBody implements java.io.Serializable {

	private static final long serialVersionUID = -1501087451432600755L;

	private ChecklistTemplateCriteria checklistTemplateCriteria;

	public ChecklistTemplateCriteria getChecklistTemplateCriteria() {
		return checklistTemplateCriteria;
	}

	public void setChecklistTemplateCriteria(ChecklistTemplateCriteria checklistTemplateCriteria) {
		this.checklistTemplateCriteria = checklistTemplateCriteria;
	}
}
