package com.integrosys.cms.host.eai.document.inquiry.checklist;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.document.bus.CCChecklist;
import com.integrosys.cms.host.eai.document.bus.SCChecklist;

/**
 * CheckList
 * 
 * @author $Author: Iwan Satria $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class ChecklistCriteria implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	private String lOSAANumber;
	
	private String checklistType;
	
	private long cMSChecklistID;
	
	private CCChecklist cCChecklist;
	
	private SCChecklist sCChecklist;

	public String getLOSAANumber() {
		return lOSAANumber;
	}

	public void setLOSAANumber(String number) {
		lOSAANumber = number;
	}

	public String getChecklistType() {
		return checklistType;
	}

	public void setChecklistType(String checklistType) {
		this.checklistType = checklistType;
	}

	public long getCMSChecklistID() {
		return cMSChecklistID;
	}

	public void setCMSChecklistID(long checklistID) {
		cMSChecklistID = checklistID;
	}

	public CCChecklist getCCChecklist() {
		return cCChecklist;
	}

	public void setCCChecklist(CCChecklist checklist) {
		cCChecklist = checklist;
	}

	public SCChecklist getSCChecklist() {
		return sCChecklist;
	}

	public void setSCChecklist(SCChecklist checklist) {
		sCChecklist = checklist;
	}
	
}
