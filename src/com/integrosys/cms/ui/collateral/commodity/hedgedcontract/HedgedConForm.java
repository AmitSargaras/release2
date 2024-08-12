/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/hedgedcontract/HedgedConForm.java,v 1.2 2004/06/04 05:22:28 hltan Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.hedgedcontract;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:22:28 $ Tag: $Name: $
 */

public class HedgedConForm extends CommonForm implements Serializable {

	private String securityID = "";

	private String globalTreasuryRef = "";

	private String dealDate = "";

	private String counterParty = "";

	private String dealAmtCcy = "";

	private String dealAmt = "";

	private String hedgedAgreeRef = "";

	private String hedgedAgreeDate = "";

	private String margin = "";

	public String getSecurityID() {
		return securityID;
	}

	public void setSecurityID(String securityID) {
		this.securityID = securityID;
	}

	public String getGlobalTreasuryRef() {
		return globalTreasuryRef;
	}

	public void setGlobalTreasuryRef(String globalTreasuryRef) {
		this.globalTreasuryRef = globalTreasuryRef;
	}

	public String getDealDate() {
		return dealDate;
	}

	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	public String getCounterParty() {
		return counterParty;
	}

	public void setCounterParty(String counterParty) {
		this.counterParty = counterParty;
	}

	public String getDealAmtCcy() {
		return dealAmtCcy;
	}

	public void setDealAmtCcy(String dealAmtCcy) {
		this.dealAmtCcy = dealAmtCcy;
	}

	public String getDealAmt() {
		return dealAmt;
	}

	public void setDealAmt(String dealAmt) {
		this.dealAmt = dealAmt;
	}

	public String getHedgedAgreeRef() {
		return hedgedAgreeRef;
	}

	public void setHedgedAgreeRef(String hedgedAgreeRef) {
		this.hedgedAgreeRef = hedgedAgreeRef;
	}

	public String getHedgedAgreeDate() {
		return hedgedAgreeDate;
	}

	public void setHedgedAgreeDate(String hedgedAgreeDate) {
		this.hedgedAgreeDate = hedgedAgreeDate;
	}

	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	public String[][] getMapper() {
		String[][] input = { { "hedgedContractObj",
				"com.integrosys.cms.ui.collateral.commodity.hedgedcontract.HedgedConMapper" }, };
		return input;
	}
}
