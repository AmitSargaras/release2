//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.guarantees.gtegovt;

import java.io.Serializable;

import com.integrosys.cms.ui.collateral.guarantees.GuaranteesForm;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 2:56:38 PM
 * To change this template use Options | File Templates.
 */
public class GteGovtForm extends GuaranteesForm implements Serializable {

	public static final String GTEGOVTMAPPER = "com.integrosys.cms.ui.collateral.guarantees.gtegovt.GteGovtMapper";

	private String minimalFSV;

	public String getMinimalFSV() {
		return this.minimalFSV;
	}

	public void setMinimalFSV(String minimalFSV) {
		this.minimalFSV = minimalFSV;
	}

	public void reset() {
		super.reset();

	}

	public String[][] getMapper() {
		String[][] input = { { "form.collateralObject", GTEGOVTMAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "pledgorRemove", "com.integrosys.cms.ui.collateral.pledgor.PledgorRemoveMapper" },
				{ "pledgeRemove", "com.integrosys.cms.ui.collateral.pledge.PledgeRemoveMapper" }, };
		return input;
	}

}
