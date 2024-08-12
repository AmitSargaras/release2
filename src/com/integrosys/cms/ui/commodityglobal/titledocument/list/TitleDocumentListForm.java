/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/titledocument/list/TitleDocumentListForm.java,v 1.2 2004/06/04 05:11:52 hltan Exp $
 */
package com.integrosys.cms.ui.commodityglobal.titledocument.list;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:11:52 $ Tag: $Name: $
 */

public class TitleDocumentListForm extends TrxContextForm implements Serializable {

	private String[] chkDeletesNonNeg;

	private String[] chkDeletesNeg;

	private String type;

	public String[] getChkDeletesNonNeg() {
		return this.chkDeletesNonNeg;
	}

	public void setChkDeletesNonNeg(String[] chkDeletesNonNeg) {
		this.chkDeletesNonNeg = chkDeletesNonNeg;
	}

	public String[] getChkDeletesNeg() {
		return this.chkDeletesNeg;
	}

	public void setChkDeletesNeg(String[] chkDeletesNeg) {
		this.chkDeletesNeg = chkDeletesNeg;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[][] getMapper() {
		String[][] input = {
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "titleDocListObj", "com.integrosys.cms.ui.commodityglobal.titledocument.list.TitleDocumentListMapper" }, };
		return input;
	}
}
