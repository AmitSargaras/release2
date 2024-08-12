//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.guarantees.gteslcdiff;

import java.io.Serializable;

import com.integrosys.cms.ui.collateral.guarantees.GuaranteesForm;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 2:56:38 PM
 * To change this template use Options | File Templates.
 */
public class GteSLCDiffForm extends GuaranteesForm implements Serializable {

	public static final String GTESLCDIFFMAPPER = "com.integrosys.cms.ui.collateral.guarantees.gteslcdiff.GteSLCDiffMapper";

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
		String[][] input = { { "form.collateralObject", GTESLCDIFFMAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" }, };
		return input;
	}

}
