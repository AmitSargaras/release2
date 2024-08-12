package com.integrosys.cms.host.eai.document.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.StandardCode;

/**
 * CheckList
 * 
 * @author $Author: shphoon $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class SCChecklist implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	private long cmsCollateralID = ICMSConstant.LONG_INVALID_VALUE;
	
	private String lOSSecurityId;

	public String getLOSSecurityId() {
		return lOSSecurityId;
	}

	public void setLOSSecurityId(String securityId) {
		lOSSecurityId = securityId;
	}

	public long getCmsCollateralID() {
		return cmsCollateralID;
	}

	public void setCmsCollateralID(long cmsCollateralID) {
		this.cmsCollateralID = cmsCollateralID;
	}

}
