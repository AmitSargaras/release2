/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/fixedassetsoth/faoitem/FAOItemForm.java,v 1.2 2005/03/18 03:36:48 lini Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.fixedassetsoth.faoitem;

import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.GeneralChargeSubTypeForm;

/**
 * Description
 * 
 * @author $Author: lini $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/03/18 03:36:48 $ Tag: $Name: $
 */

public class FAOItemForm extends GeneralChargeSubTypeForm {
	private String faoID = "";

	private String description = "";

	private String evalDateFSV = "";

	private String creditor = "";

	public String getFaoID() {
		return this.faoID;
	}

	public void setFaoID(String faoID) {
		this.faoID = faoID;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEvalDateFSV() {
		return this.evalDateFSV;
	}

	public void setEvalDateFSV(String evalDateFSV) {
		this.evalDateFSV = evalDateFSV;
	}

	public String getCreditor() {
		return this.creditor;
	}

	public void setCreditor(String creditor) {
		this.creditor = creditor;
	}

	public String[][] getMapper() {
		String[][] input = { { "faoItemObj",
				"com.integrosys.cms.ui.collateral.assetbased.assetgencharge.fixedassetsoth.faoitem.FAOItemMapper" }, };
		return input;
	}
}
