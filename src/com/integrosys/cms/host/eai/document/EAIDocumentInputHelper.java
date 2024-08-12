package com.integrosys.cms.host.eai.document;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.document.bus.CheckList;
import com.integrosys.cms.host.eai.document.inquiry.template.ChecklistTemplateCriteriaValidator;

/**
 * EAIDocumentInputHelper.
 * 
 * @author $Author: Iwan Satria $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class EAIDocumentInputHelper {
	private static EAIDocumentInputHelper instance = null;

	private EAIDocumentInputHelper() {
	}

	public static EAIDocumentInputHelper getInstance() {
		if (instance == null) {
			synchronized (ChecklistTemplateCriteriaValidator.class) {
				if (instance == null) {
					instance = new EAIDocumentInputHelper();
				}
			}
		}
		return instance;
	}

	// Check List
	public CheckList retrieveCheckList(EAIMessage msg) {
		return ((DocumentInputMessageBody) msg.getMsgBody()).getCheckList();
	}

}
