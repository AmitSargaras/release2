/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/insurance/subtype/creditinsurance/OBCreditInsurance.java,v 1.7 2003/07/24 06:13:16 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditinsurance;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.insurance.OBInsuranceCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Insurance of type Credit Insurance.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2003/07/24 06:13:16 $ Tag: $Name: $
 */
public class OBCreditInsurance extends OBInsuranceCollateral implements ICreditInsurance {
	private String insurerName;

	private String insuranceType;

	private Amount insuredAmount;

	private String insuredCcyCode;

	private Date insEffectiveDate;

	private Date insExpiryDate;

	private String policyNo;

	private String externalLegalCounsel;

	private boolean isAccelerationClause;

	private String localCCyInCoreMarket;

	private String coreMarket;

	private String description;
	
	private Date iSDADate;
	
	private Date treasuryDocDate;
	
	private boolean bankRiskConfirmation;
	
	private String arrInsurer;

	public String getArrInsurer() {
		return arrInsurer;
	}

	public void setArrInsurer(String arrInsurer) {
		this.arrInsurer = arrInsurer;
	}

	public boolean getBankRiskConfirmation() {
		return bankRiskConfirmation;
	}

	public void setBankRiskConfirmation(boolean bankRiskConfirmation) {
		this.bankRiskConfirmation = bankRiskConfirmation;
	}

	public Date getTreasuryDocDate() {
		return treasuryDocDate;
	}

	public void setTreasuryDocDate(Date treasuryDocDate) {
		this.treasuryDocDate = treasuryDocDate;
	}

	/**
	 * Default Constructor.
	 */
	public OBCreditInsurance() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_INS_CR_INS));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICreditInsurance
	 */
	public OBCreditInsurance(ICreditInsurance obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get insurer's name.
	 * 
	 * @return String
	 */
	public String getInsurerName() {
		return insurerName;
	}

	/**
	 * Set insurer's name.
	 * 
	 * @param insurerName of type String
	 */
	public void setInsurerName(String insurerName) {
		this.insurerName = insurerName;
	}

	/**
	 * Get insurance type.
	 * 
	 * @return String
	 */
	public String getInsuranceType() {
		return insuranceType;
	}

	/**
	 * Set insurance type.
	 * 
	 * @param insuranceType of type String
	 */
	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}

	/**
	 * Get insured amount.
	 * 
	 * @return Amount
	 */
	public Amount getInsuredAmount() {
		return insuredAmount;
	}

	/**
	 * Set insured amount.
	 * 
	 * @param insuredAmount of type Amount
	 */
	public void setInsuredAmount(Amount insuredAmount) {
		this.insuredAmount = insuredAmount;
	}

	/**
	 * Get insured amount currency code.
	 * 
	 * @return String
	 */
	public String getInsuredCcyCode() {
		return insuredCcyCode;
	}

	/**
	 * Set insured amount currency code.
	 * 
	 * @param insuredCcyCode of type String
	 */
	public void setInsuredCcyCode(String insuredCcyCode) {
		this.insuredCcyCode = insuredCcyCode;
	}

	/**
	 * Get insurance effective date.
	 * 
	 * @return Date
	 */
	public Date getInsEffectiveDate() {
		return insEffectiveDate;
	}

	/**
	 * Set insurance effective date.
	 * 
	 * @param insEffectiveDate of type Date
	 */
	public void setInsEffectiveDate(Date insEffectiveDate) {
		this.insEffectiveDate = insEffectiveDate;
	}

	/**
	 * Get insurance expiry date.
	 * 
	 * @return Date
	 */
	public Date getInsExpiryDate() {
		return insExpiryDate;
	}

	/**
	 * Set insurance expiry date.
	 * 
	 * @param insExpiryDate of type Date
	 */
	public void setInsExpiryDate(Date insExpiryDate) {
		this.insExpiryDate = insExpiryDate;
	}

	/**
	 * Get policy no.
	 * 
	 * @return String
	 */
	public String getPolicyNo() {
		return policyNo;
	}

	/**
	 * Set policy no.
	 * 
	 * @param policyNo of type String
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * Get external legal counsel.
	 * 
	 * @return String
	 */
	public String getExternalLegalCounsel() {
		return externalLegalCounsel;
	}

	/**
	 * Set external legal counsel.
	 * 
	 * @param externalLegalCounsel of type String
	 */
	public void setExternalLegalCounsel(String externalLegalCounsel) {
		this.externalLegalCounsel = externalLegalCounsel;
	}

	/**
	 * Get if it is acceleration clause.
	 * 
	 * @return boolean
	 */
	public boolean getIsAccelerationClause() {
		return isAccelerationClause;
	}

	/**
	 * Set if it is acceleration clause.
	 * 
	 * @param isAccelerationClause of type boolean
	 */
	public void setIsAccelerationClause(boolean isAccelerationClause) {
		this.isAccelerationClause = isAccelerationClause;
	}

	/**
	 * Get local currencies in core markets.
	 * 
	 * @return String
	 */
	public String getLocalCCyInCoreMarket() {
		return localCCyInCoreMarket;
	}

	/**
	 * Set local currencies in core markets.
	 * 
	 * @param localCCyInCoreMarket of type String
	 */
	public void setLocalCCyInCoreMarket(String localCCyInCoreMarket) {
		this.localCCyInCoreMarket = localCCyInCoreMarket;
	}

	/**
	 * Get core markets.
	 * 
	 * @return String
	 */
	public String getCoreMarket() {
		return coreMarket;
	}

	/**
	 * Set core markets.
	 * 
	 * @param coreMarket of type String
	 */
	public void setCoreMarket(String coreMarket) {
		this.coreMarket = coreMarket;
	}

	/**
	 * Get specific cover description.
	 * 
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set specific cover description.
	 * 
	 * @param description of type String
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBCreditInsurance)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public Date getISDADate() {
		return iSDADate;
	}

	public void setISDADate(Date date) {
		iSDADate = date;
	}
}