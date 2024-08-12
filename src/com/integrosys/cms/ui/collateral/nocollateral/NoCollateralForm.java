package com.integrosys.cms.ui.collateral.nocollateral;

import com.integrosys.cms.ui.collateral.CollateralForm;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Feb 26, 2007 Time: 3:55:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class NoCollateralForm extends CollateralForm {

	private static final long serialVersionUID = 4927822859837083961L;

	public static final String NOCOLLATERALMAPPER = "com.integrosys.cms.ui.collateral.nocollateral.NoCollateralMapper";

	private String sciRefNote = "";

	public String getSciRefNote() {
		return sciRefNote;
	}

	public void setSciRefNote(String sciRefNote) {
		this.sciRefNote = sciRefNote;
	}

	public void reset() {
	}

	public String[][] getMapper() {
		String[][] input = { { "form.collateralObject", NOCOLLATERALMAPPER },
				{ "pledgeRemove", "com.integrosys.cms.ui.collateral.pledge.PledgeRemoveMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" }, };
		return input;

	}

}
