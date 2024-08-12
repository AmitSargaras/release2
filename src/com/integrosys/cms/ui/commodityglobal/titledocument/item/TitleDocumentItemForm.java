/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/titledocument/item/TitleDocumentItemForm.java,v 1.2 2004/06/04 05:11:52 hltan Exp $
 */
package com.integrosys.cms.ui.commodityglobal.titledocument.item;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:11:52 $ Tag: $Name: $
 */

public class TitleDocumentItemForm extends CommonForm implements Serializable {
	private String documentDesc = "";

	private String type = "";

	public String getDocumentDesc() {
		return this.documentDesc;
	}

	public void setDocumentDesc(String documentDesc) {
		this.documentDesc = documentDesc;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[][] getMapper() {
		String[][] input = { { "titleDocItemObj",
				"com.integrosys.cms.ui.commodityglobal.titledocument.item.TitleDocumentItemMapper" }, };
		return input;
	}

}
