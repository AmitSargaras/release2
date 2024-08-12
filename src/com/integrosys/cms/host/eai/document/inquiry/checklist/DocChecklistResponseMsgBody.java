package com.integrosys.cms.host.eai.document.inquiry.checklist;

import java.util.Vector;

import com.integrosys.cms.host.eai.EAIBody;
import com.integrosys.cms.host.eai.document.bus.CheckList;

/**
 * DocumentInquiryMessageBody.
 * 
 * @author $Author: Iwan Satria $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class DocChecklistResponseMsgBody extends EAIBody implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	private CheckList checkList;

	private Vector checkListItem;

	public CheckList getCheckList() {
		return checkList;
	}

	public void setCheckList(CheckList checkList) {
		this.checkList = checkList;
	}

	public Vector getCheckListItem() {
		return checkListItem;
	}

	public void setCheckListItem(Vector checkListItem) {
		this.checkListItem = checkListItem;
	}

}
