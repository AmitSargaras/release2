/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityuom/list/CommodityUOMListForm.java,v 1.2 2004/06/04 05:11:44 hltan Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityuom.list;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:11:44 $ Tag: $Name: $
 */

public class CommodityUOMListForm extends TrxContextForm implements Serializable {

	private String[] chkDeletes;

	private String category = "";

	private String commodityType = "";

	public String[] getChkDeletes() {
		return this.chkDeletes;
	}

	public void setChkDeletes(String[] chkDeletes) {
		this.chkDeletes = chkDeletes;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCommodityType() {
		return this.commodityType;
	}

	public void setCommodityType(String commodityType) {
		this.commodityType = commodityType;
	}

	public String[][] getMapper() {
		String[][] input = {
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "commodityUOMListObj",
						"com.integrosys.cms.ui.commodityglobal.commodityuom.list.CommodityUOMListMapper" }, };
		return input;
	}
}
