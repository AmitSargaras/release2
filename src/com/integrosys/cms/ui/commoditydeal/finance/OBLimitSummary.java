/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/OBLimitSummary.java,v 1.1 2004/07/15 09:31:28 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/07/15 09:31:28 $ Tag: $Name: $
 */
public class OBLimitSummary {

	public OBLimitSummary() {
	}

	private String sn = "";

	private String limitID = "";

	private String productDesc = "";

	private String limitBookingLoc = "";

	private String approvedLimit = "";

	private String tpOutstandingLimit = "";

	private String operationalLimit = "";

	private Collection securityID = new ArrayList();

	private Collection securityDesc = new ArrayList();

	private Collection securityLoc = new ArrayList();

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getLimitID() {
		return limitID;
	}

	public void setLimitID(String limitID) {
		this.limitID = limitID;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getLimitBookingLoc() {
		return limitBookingLoc;
	}

	public void setLimitBookingLoc(String limitBookingLoc) {
		this.limitBookingLoc = limitBookingLoc;
	}

	public String getApprovedLimit() {
		return approvedLimit;
	}

	public void setApprovedLimit(String approvedLimit) {
		this.approvedLimit = approvedLimit;
	}

	public String getTpOutstandingLimit() {
		return tpOutstandingLimit;
	}

	public void setTpOutstandingLimit(String tpOutstandingLimit) {
		this.tpOutstandingLimit = tpOutstandingLimit;
	}

	public String getOperationalLimit() {
		return operationalLimit;
	}

	public void setOperationalLimit(String operationalLimit) {
		this.operationalLimit = operationalLimit;
	}

	public Collection getSecurityID() {
		return securityID;
	}

	public void setSecurityID(Collection securityID) {
		this.securityID = securityID;
	}

	public Collection getSecurityDesc() {
		return securityDesc;
	}

	public void setSecurityDesc(Collection securityDesc) {
		this.securityDesc = securityDesc;
	}

	public Collection getSecurityLoc() {
		return securityLoc;
	}

	public void setSecurityLoc(Collection securityLoc) {
		this.securityLoc = securityLoc;
	}
}
