package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

/**
 * This class represents the summary for the general charge asset fixed
 * asset/others subtype.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/06/07 07:21:38 $ Tag: $Name: $
 */
public class OBFixedAssetOthersSummary extends OBGeneralChargeSubTypeSummary {
	String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
