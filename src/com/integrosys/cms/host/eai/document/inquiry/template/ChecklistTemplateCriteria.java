package com.integrosys.cms.host.eai.document.inquiry.template;

import com.integrosys.cms.host.eai.document.bus.CCTemplate;
import com.integrosys.cms.host.eai.document.bus.SCTemplate;

/**
 * CheckList
 * 
 * @author $Author: shphoon $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class ChecklistTemplateCriteria implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	private String checklistType;

	private String country;

	private CCTemplate cCTemplate;

	private SCTemplate sCTemplate;

	public CCTemplate getCCTemplate() {
		return cCTemplate;
	}

	public void setCCTemplate(CCTemplate template) {
		cCTemplate = template;
	}

	public SCTemplate getSCTemplate() {
		return sCTemplate;
	}

	public void setSCTemplate(SCTemplate template) {
		sCTemplate = template;
	}

	public String getChecklistType() {
		return checklistType;
	}

	public void setChecklistType(String checklistType) {
		this.checklistType = checklistType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
