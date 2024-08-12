/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityprofile/list/CommProfileListForm.java,v 1.2 2004/06/04 05:11:33 hltan Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityprofile.list;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:11:33 $ Tag: $Name: $
 */

public class CommProfileListForm extends TrxContextForm implements Serializable {

	private String[] chkDeletes;

	public String[] getChkDeletes() {
		return this.chkDeletes;
	}

	public void setChkDeletes(String[] chkDeletes) {
		this.chkDeletes = chkDeletes;
	}

	public String[][] getMapper() {
		String[][] input = {
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "commProfileListObj",
						"com.integrosys.cms.ui.commodityglobal.commodityprofile.list.CommProfileListMapper" }, };
		return input;
	}
}
