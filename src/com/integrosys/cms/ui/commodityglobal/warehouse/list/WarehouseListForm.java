/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/warehouse/list/WarehouseListForm.java,v 1.2 2004/06/04 05:12:02 hltan Exp $
 */
package com.integrosys.cms.ui.commodityglobal.warehouse.list;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:12:02 $ Tag: $Name: $
 */

public class WarehouseListForm extends TrxContextForm implements Serializable {

	private String[] chkDeletes;

	private String country = "";

	public String[] getChkDeletes() {
		return this.chkDeletes;
	}

	public void setChkDeletes(String[] chkDeletes) {
		this.chkDeletes = chkDeletes;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String[][] getMapper() {
		String[][] input = { { "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "warehouseListObj", "com.integrosys.cms.ui.commodityglobal.warehouse.list.WarehouseListMapper" }, };
		return input;
	}
}
