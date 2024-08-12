package com.integrosys.cms.host.eai.document.inquiry.checklist;

import com.integrosys.cms.host.eai.EAIMessage;

/**
 * EAIDocChecklistInquiryHelper.
 * 
 * @author $Author: Iwan Satria $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class EAIDocChecklistInquiryHelper {
	private static EAIDocChecklistInquiryHelper instance = null;

	private EAIDocChecklistInquiryHelper() {
	}

	public static EAIDocChecklistInquiryHelper getInstance() {
		if (instance == null) {
			synchronized (EAIDocChecklistInquiryHelper.class) {
				if (instance == null) {
					instance = new EAIDocChecklistInquiryHelper();
				}
			}
		}
		return instance;
	}

	// Check List
	public ChecklistCriteria retrieveCheckListCriteria(EAIMessage msg) {
		return ((DocChecklistInquiryMsgBody) msg.getMsgBody()).getChecklistCriteria();
	}

}
