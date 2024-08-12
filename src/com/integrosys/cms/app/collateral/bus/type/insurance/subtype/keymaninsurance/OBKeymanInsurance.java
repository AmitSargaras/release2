/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/insurance/subtype/keymaninsurance/OBKeymanInsurance.java,v 1.5 2003/07/24 06:13:39 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.insurance.subtype.keymaninsurance;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.insurance.OBInsuranceCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Insurance of type Keyman Insurance.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/07/24 06:13:39 $ Tag: $Name: $
 */
public class OBKeymanInsurance extends OBInsuranceCollateral implements IKeymanInsurance {
	private String insurerName;

	private String insuranceType;

	private Amount insuredAmount;

	private String insuredCcyCode;

	private Date insEffectiveDate;

	private Date insExpiryDate;

	private String policyNo;

	private boolean isBankInterestDulyNoted;

	private boolean bankRiskConfirmation;

	private String arrInsurer;

	private Date issuanceDate;

	private Amount insurancePremium;

	/**
	 * Default Constructor.
	 */
	public OBKeymanInsurance() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_INS_KEYMAN_INS));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IKeymanInsurance
	 */
	public OBKeymanInsurance(IKeymanInsurance obj) {
		this();
		AccessorUtil.copyValue(obj, this);
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
		else if (!(obj instanceof OBKeymanInsurance)) {
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

	public String getArrInsurer() {
		return arrInsurer;
	}

	public boolean getBankRiskConfirmation() {
		return bankRiskConfirmation;
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
	 * Get insurance expiry date.
	 * 
	 * @return Date
	 */
	public Date getInsExpiryDate() {
		return insExpiryDate;
	}

	public Amount getInsurancePremium() {
		return this.insurancePremium;
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
	 * Get insured amount.
	 * 
	 * @return Amount
	 */
	public Amount getInsuredAmount() {
		return insuredAmount;
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
	 * Get insurer's name.
	 * 
	 * @return String
	 */
	public String getInsurerName() {
		return insurerName;
	}

	/**
	 * Get if the bank's interest is duly noted.
	 * 
	 * @return boolean
	 */
	public boolean getIsBankInterestDulyNoted() {
		return isBankInterestDulyNoted;
	}

	public Date getIssuanceDate() {
		return this.issuanceDate;
	}

	/**
	 * Get policy no.
	 * 
	 * @return String
	 */
	public String getPolicyNo() {
		return policyNo;
	}

	public void setArrInsurer(String arrInsurer) {
		this.arrInsurer = arrInsurer;
	}

	public void setBankRiskConfirmation(boolean bankRiskConfirmation) {
		this.bankRiskConfirmation = bankRiskConfirmation;
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
	 * Set insurance expiry date.
	 * 
	 * @param insExpiryDate of type Date
	 */
	public void setInsExpiryDate(Date insExpiryDate) {
		this.insExpiryDate = insExpiryDate;
	}

	public void setInsurancePremium(Amount insurancePremium) {
		this.insurancePremium = insurancePremium;
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
	 * Set insured amount.
	 * 
	 * @param insuredAmount of type Amount
	 */
	public void setInsuredAmount(Amount insuredAmount) {
		this.insuredAmount = insuredAmount;
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
	 * Set insurer's name.
	 * 
	 * @param insurerName of type String
	 */
	public void setInsurerName(String insurerName) {
		this.insurerName = insurerName;
	}

	/**
	 * Set if the bank's interest is duly noted.
	 * 
	 * @param isBankInterestDulyNoted
	 */
	public void setIsBankInterestDulyNoted(boolean isBankInterestDulyNoted) {
		this.isBankInterestDulyNoted = isBankInterestDulyNoted;
	}

	public void setIssuanceDate(Date issuanceDate) {
		this.issuanceDate = issuanceDate;
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
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
	
	//Added by Pramod Katkar for New Filed CR on 19-08-2013
	private int physicalInspectionFreq;
	private String physicalInspectionFreqUnit;
	private boolean isPhysicalInspection;
	private Date lastPhysicalInspectDate;
	private Date nextPhysicalInspectDate;
	public int getPhysicalInspectionFreq() {
		return physicalInspectionFreq;
	}
	public void setPhysicalInspectionFreq(int physicalInspectionFreq) {
		this.physicalInspectionFreq = physicalInspectionFreq;
	}
	public String getPhysicalInspectionFreqUnit() {
		return physicalInspectionFreqUnit;
	}
	public void setPhysicalInspectionFreqUnit(String physicalInspectionFreqUnit) {
		this.physicalInspectionFreqUnit = physicalInspectionFreqUnit;
	}
	public boolean getIsPhysicalInspection() {
		return isPhysicalInspection;
	}
	public void setIsPhysicalInspection(boolean isPhysicalInspection) {
		this.isPhysicalInspection = isPhysicalInspection;
	}
	public Date getLastPhysicalInspectDate() {
		return lastPhysicalInspectDate;
	}
	public void setLastPhysicalInspectDate(Date lastPhysicalInspectDate) {
		this.lastPhysicalInspectDate = lastPhysicalInspectDate;
	}
	public Date getNextPhysicalInspectDate() {
		return nextPhysicalInspectDate;
	}
	public void setNextPhysicalInspectDate(Date nextPhysicalInspectDate) {
		this.nextPhysicalInspectDate = nextPhysicalInspectDate;
	}
	//End by Pramod Katkar
}