/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/dataprotection/bus/OBCollateralMetaData.java,v 1.4 2005/10/27 06:34:17 lyng Exp $
 */
package com.integrosys.cms.app.dataprotection.bus;

import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Purpose: A value object that contains meta data on t on the collateral fields
 * @author $jtan$<br>
 * @version $revision$
 * @since $date$ Tag: $Name: $
 * 
 */
public class OBCollateralMetaData extends OBDataAccessProfile implements ICollateralMetaData {
	ICollateralSubType subtype;

	boolean isUpdatable;

	String applicableCountry;

	public ICollateralSubType getSubtype() {
		return subtype;
	}

	public void setSubtype(ICollateralSubType subtype) {
		this.subtype = subtype;
	}

	/*
	 * states whether the SCC collateral fields are updatable
	 * 
	 * @return is field updateable?
	 */
	public boolean getSCCUpdatable() {
		if (getTeamTypeMshipID() == ICMSConstant.TEAM_TYPE_SSC_MAKER
				||getTeamTypeMshipID() == ICMSConstant.TEAM_TYPE_SSC_MAKER_WFH) {
			return true;
		}
		else {
			return false;
		}
	}

	public void setSCCUpdatable(boolean b) {
		this.isUpdatable = b;
	}

	public ICollateralSubType getCollateralSubType() {
		return null;
	}

	public String getApplicableCountry() {
		if (this.getGrantedBkgLoc().size() == 1) { // temp wrapper. Remote it
													// after collateral change
													// to user
													// IDataAccessProfile
			IBookingLocation bkg = (IBookingLocation) this.getGrantedBkgLoc().get(0);
			return bkg.getCountryCode();
		}
		return applicableCountry;
	}

	public void setApplicableCountry(String applicableCountry) {
		this.applicableCountry = applicableCountry;
	}
}
