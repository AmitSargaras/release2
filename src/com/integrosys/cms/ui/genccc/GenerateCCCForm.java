/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genccc;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/07/22 06:24:48 $ Tag: $Name: $
 */

public class GenerateCCCForm extends TrxContextForm implements Serializable {

	private String limitProfileId;

	private String creditOfficerName;

	private String seniorCreditOfficerName;

	private String creditOfficerSgnNo;

	private String seniorCreditOfficerSgnNo;

	private String creditOfficerLocationCountry;

	private String seniorCreditOfficerLocationCountry;

	private String creditOfficerLocationOrgCode;

	private String seniorCreditOfficerLocationOrgCode;

	private String remarksCCC;

	private String[] actLimit;

	private String[] actAmtCurrCode;

	private String[] appLimit;

	private String[] maturityDate;

	public String getCreditOfficerName() {
		return creditOfficerName;
	}

	public void setCreditOfficerName(String creditOfficerName) {
		this.creditOfficerName = creditOfficerName;
	}

	public String getCreditOfficerSgnNo() {
		return creditOfficerSgnNo;
	}

	public void setCreditOfficerSgnNo(String creditOfficerSgnNo) {
		this.creditOfficerSgnNo = creditOfficerSgnNo;
	}

	public String getRemarksCCC() {
		return remarksCCC;
	}

	public void setRemarksCCC(String remarksCCC) {
		this.remarksCCC = remarksCCC;
	}

	public String[] getActLimit() {
		return actLimit;
	}

	public void setActLimit(String[] actLimit) {
		this.actLimit = actLimit;
	}

	public String[] getActAmtCurrCode() {
		return actAmtCurrCode;
	}

	public void setActAmtCurrCode(String[] actAmtCurrCode) {
		this.actAmtCurrCode = actAmtCurrCode;
	}

	public String[] getAppLimit() {
		return appLimit;
	}

	public void setAppLimit(String[] appLimit) {
		this.appLimit = appLimit;
	}

	public String getSeniorCreditOfficerName() {
		return seniorCreditOfficerName;
	}

	public void setSeniorCreditOfficerName(String seniorCreditOfficerName) {
		this.seniorCreditOfficerName = seniorCreditOfficerName;
	}

	public String getSeniorCreditOfficerSgnNo() {
		return seniorCreditOfficerSgnNo;
	}

	public void setSeniorCreditOfficerSgnNo(String seniorCreditOfficerSgnNo) {
		this.seniorCreditOfficerSgnNo = seniorCreditOfficerSgnNo;
	}

	public String getCreditOfficerLocationCountry() {
		return creditOfficerLocationCountry;
	}

	public void setCreditOfficerLocationCountry(String creditOfficerLocationCountry) {
		this.creditOfficerLocationCountry = creditOfficerLocationCountry;
	}

	public String getSeniorCreditOfficerLocationCountry() {
		return seniorCreditOfficerLocationCountry;
	}

	public void setSeniorCreditOfficerLocationCountry(String seniorCreditOfficerLocationCountry) {
		this.seniorCreditOfficerLocationCountry = seniorCreditOfficerLocationCountry;
	}

	public String getCreditOfficerLocationOrgCode() {
		return creditOfficerLocationOrgCode;
	}

	public void setCreditOfficerLocationOrgCode(String creditOfficerLocationOrgCode) {
		this.creditOfficerLocationOrgCode = creditOfficerLocationOrgCode;
	}

	public String getSeniorCreditOfficerLocationOrgCode() {
		return seniorCreditOfficerLocationOrgCode;
	}

	public void setSeniorCreditOfficerLocationOrgCode(String seniorCreditOfficerLocationOrgCode) {
		this.seniorCreditOfficerLocationOrgCode = seniorCreditOfficerLocationOrgCode;
	}

	public String getLimitProfileId() {
		return limitProfileId;
	}

	public void setLimitProfileId(String limitProfileId) {
		this.limitProfileId = limitProfileId;
	}

	public String[] getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(String[] maturityDate) {
		this.maturityDate = maturityDate;
	}

	/**
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it. it has a
	 * syntax (keyMapperclassname)
	 * 
	 * @return two-dimesnional String Array
	 */
	public String[][] getMapper() {
		String[][] input = { { "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "limitProfile", "com.integrosys.cms.ui.genccc.LimitProfileMapper" },
				{ "cert", "com.integrosys.cms.ui.genccc.CertMapper" } };
		return input;
	}

}
