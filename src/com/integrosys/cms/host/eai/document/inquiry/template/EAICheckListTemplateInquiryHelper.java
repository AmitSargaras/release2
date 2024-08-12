package com.integrosys.cms.host.eai.document.inquiry.template;

import com.integrosys.cms.host.eai.EAIMessage;

/**
 * EAIDocumentInquiryHelper.
 * 
 * @author $Author: Iwan Satria $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class EAICheckListTemplateInquiryHelper {
	private static EAICheckListTemplateInquiryHelper instance = null;

	private EAICheckListTemplateInquiryHelper() {
	}

	public static EAICheckListTemplateInquiryHelper getInstance() {
		if (instance == null) {
			synchronized (ChecklistTemplateCriteriaValidator.class) {
				if (instance == null) {
					instance = new EAICheckListTemplateInquiryHelper();
				}
			}
		}
		return instance;
	}

	// Check List
	public ChecklistTemplateCriteria retrieveCheckListTemplateCriteria(EAIMessage msg) {
		return ((CheckListTemplateInquiryMsgBody) msg.getMsgBody()).getChecklistTemplateCriteria();
	}

}
