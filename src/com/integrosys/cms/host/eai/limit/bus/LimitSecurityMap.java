/*
 * LimitSecurityMap.java
 *
 * Created on May 4, 2007, 11:20 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.integrosys.cms.host.eai.limit.bus;

import java.io.Serializable;

/**
 * 
 * @author OEM
 */
public class LimitSecurityMap implements Serializable {
	private long chargeId; // internal key

	private long limitId; // internal key

	private long cmsCollateralId; // internal key

	private long limitProfileId; // internal key

	/** Creates a new instance of LimitSecurityMap */
	public LimitSecurityMap() {
	}

	public long getLimitId() {
		return limitId;
	}

	public void setLimitId(long limitId) {
		this.limitId = limitId;
	}

	public long getCmsCollateralId() {
		return cmsCollateralId;
	}

	public void setCmsCollateralId(long cmsCollateralId) {
		this.cmsCollateralId = cmsCollateralId;
	}

	public long getLimitProfileId() {
		return limitProfileId;
	}

	public void setLimitProfileId(long limitProfileId) {
		this.limitProfileId = limitProfileId;
	}

	public long getChargeId() {
		return chargeId;
	}

	public void setChargeId(long chargeId) {
		this.chargeId = chargeId;
	}

}
