package com.integrosys.cms.host.eai.document.bus;

import com.integrosys.cms.host.eai.StandardCode;

/**
 * CheckList
 * 
 * @author $Author: shphoon $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class SCTemplate implements java.io.Serializable {

	// --------------------------/
	// - Class/Member Variables -/
	// --------------------------/

	private StandardCode securityType;
	
	private StandardCode securitySubType;
	
	private StandardCode applicationType;
	
	private StandardCode goodsStatus;
	
	private StandardCode pBTPBRInd;

	public StandardCode getPBTPBRInd() {
		return pBTPBRInd;
	}

	public void setPBTPBRInd(StandardCode ind) {
		pBTPBRInd = ind;
	}

	public StandardCode getSecurityType() {
		return securityType;
	}

	public void setSecurityType(StandardCode securityType) {
		this.securityType = securityType;
	}

	public StandardCode getSecuritySubType() {
		return securitySubType;
	}

	public void setSecuritySubType(StandardCode securitySubType) {
		this.securitySubType = securitySubType;
	}

	public StandardCode getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(StandardCode applicationType) {
		this.applicationType = applicationType;
	}

	public StandardCode getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(StandardCode goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

}
