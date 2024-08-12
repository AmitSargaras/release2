/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/message/castor/sci/security/CashSecurity.java,v 1.3 2003/12/08 04:25:06 lyng Exp $
 */
package com.integrosys.cms.host.eai.security.bus.cash;

import java.util.Date;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * This class represents approved security of type Cash.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/12/08 04:25:06 $ Tag: $Name: $
 */
public class CashSecurity extends ApprovedSecurity {
	/**
	 * Default constructor.
	 */
	public CashSecurity() {
		super();
	}

	private String interestCapital;

	private Double holdingPeriod;

	private StandardCode holdingPeriodUnit;
	
	private String securityIssuer;

	private String cCRefNo;

	private String privateCaveatGteeExpDate;

	private String fDDesc;
	
	public String getSecurityIssuer() {
		return securityIssuer;
	}

	public void setSecurityIssuer(String securityIssuer) {
		this.securityIssuer = securityIssuer;
	}

	public String getCCRefNo() {
		return cCRefNo;
	}

	public void setCCRefNo(String refNo) {
		cCRefNo = refNo;
	}

	public String getPrivateCaveatGteeExpDate() {
		return privateCaveatGteeExpDate;
	}

	public void setPrivateCaveatGteeExpDate(String privateCaveatGteeExpDate) {
		this.privateCaveatGteeExpDate = privateCaveatGteeExpDate;
	}

	public String getFDDesc() {
		return fDDesc;
	}

	public void setFDDesc(String desc) {
		fDDesc = desc;
	}

	public void setJDOPrivateCaveatGteeExpDate(Date privateCaveatGteeExpDate) {
		this.privateCaveatGteeExpDate = MessageDate.getInstance().getString(privateCaveatGteeExpDate) ;
	}

	public Date getJDOPrivateCaveatGteeExpDate() {
		return MessageDate.getInstance().getDate(this.privateCaveatGteeExpDate);
	}

	public String getInterestCapital() {
		return interestCapital;
	}

	public void setInterestCapital(String interestCapital) {
		this.interestCapital = interestCapital;
	}

	public Double getHoldingPeriod() {
		return holdingPeriod;
	}

	public void setHoldingPeriod(Double holdingPeriod) {
		this.holdingPeriod = holdingPeriod;
	}

	public StandardCode getHoldingPeriodUnit() {
		return holdingPeriodUnit;
	}

	public void setHoldingPeriodUnit(StandardCode holdingPeriodUnit) {
		this.holdingPeriodUnit = holdingPeriodUnit;
	}

}
