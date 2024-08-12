//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION

package com.integrosys.cms.ui.collateral.property.propcommserviceapt;

import java.io.Serializable;

import com.integrosys.cms.ui.collateral.property.PropertyForm;

/**
 * Created by IntelliJ IDEA. <p/> User: ssathish <p/> Date: Jun 20, 2003 <p/>
 * Time: 2:56:38 PM <p/> To change this template use Options | File Templates.
 */

public class PropCommServiceAptForm extends PropertyForm implements Serializable {

	public static final String PROPCOMMSHOPMAPPER = PropCommServiceAptMapper.class.getName();

	private String builtUpArea = "";

	private String builtUpAreaUnit = "";

	public String getBuiltUpArea() {
		return this.builtUpArea;
	}

	public void setBuiltUpArea(String builtUpArea) {
		this.builtUpArea = builtUpArea;
	}

	public String getBuiltUpAreaUnit() {
		return this.builtUpAreaUnit;
	}

	public void setBuiltUpAreaUnit(String builtUpAreaUnit) {
		this.builtUpAreaUnit = builtUpAreaUnit;
	}

	public void reset() {
		super.reset();
	}

	public String[][] getMapper() {
		String[][] input = { { "form.collateralObject", PROPCOMMSHOPMAPPER },
				{ "pledgorRemove", "com.integrosys.cms.ui.collateral.pledgor.PledgorRemoveMapper" },
				{ "pledgeRemove", "com.integrosys.cms.ui.collateral.pledge.PledgeRemoveMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };

		return input;
	}

}
