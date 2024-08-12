package com.integrosys.cms.host.eai.document;

import com.integrosys.cms.host.eai.EAIBody;
import com.integrosys.cms.host.eai.document.bus.CheckList;

/**
 * DocumentMessageBody.
 * 
 * @author $Author: shphoon $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class DocumentInputMessageBody extends EAIBody implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	/**
	 * Field CheckList
	 */
	private CheckList checkList;

	// ----------------/
	// - Constructors -/
	// ----------------/

	public DocumentInputMessageBody() {
		super();
	} // -- Message()

	public CheckList getCheckList() {
		return checkList;
	}

	public void setCheckList(CheckList checkList) {
		this.checkList = checkList;
	}

	// -----------/
	// - Methods -/
	// -----------/

}
