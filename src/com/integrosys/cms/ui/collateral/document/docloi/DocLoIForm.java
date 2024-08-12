package com.integrosys.cms.ui.collateral.document.docloi;

import java.io.Serializable;

import com.integrosys.cms.ui.collateral.document.DocumentForm;

public class DocLoIForm extends DocumentForm implements Serializable {

	public static final String DOCDOAMAPPER = "com.integrosys.cms.ui.collateral.document.docloi.DocLoIMapper";

	public void reset() {

		super.reset();

	}

	public String[][] getMapper() {

		String[][] input = {

		{ "form.collateralObject", DOCDOAMAPPER },

		{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
		{ "pledgorRemove", "com.integrosys.cms.ui.collateral.pledgor.PledgorRemoveMapper" },
		{ "pledgeRemove", "com.integrosys.cms.ui.collateral.pledge.PledgeRemoveMapper" },

		};

		return input;

	}

}
