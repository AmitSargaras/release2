/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/insurance/subtype/keymaninsurance/IKeymanInsurance.java,v 1.5 2003/07/24 06:13:39 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.insurance.subtype.keymaninsurance;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.type.insurance.IInsuranceCollateral;

/**
 * This interface represents Insurance of type Keyman Insurance.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/07/24 06:13:39 $ Tag: $Name: $
 */
public interface IKeymanInsurance extends IInsuranceCollateral {
	public String getArrInsurer();

	public boolean getBankRiskConfirmation();

	/**
	 * Get insurance effective date.
	 * 
	 * @return Date
	 */
	public Date getInsEffectiveDate();

	/**
	 * Get insurance expiry date.
	 * 
	 * @return Date
	 */
	public Date getInsExpiryDate();

	public Amount getInsurancePremium();

	/**
	 * Get insurance type.
	 * 
	 * @return String
	 */
	public String getInsuranceType();

	/**
	 * Get insured amount.
	 * 
	 * @return Amount
	 */
	public Amount getInsuredAmount();

	/**
	 * Get insured amount currency code.
	 * 
	 * @return String
	 */
	public String getInsuredCcyCode();

	/**
	 * Get insurer's name.
	 * 
	 * @return String
	 */
	public String getInsurerName();

	/**
	 * Get if the bank's interest is duly noted.
	 * 
	 * @return boolean
	 */
	public boolean getIsBankInterestDulyNoted();

	public Date getIssuanceDate();

	/**
	 * Get policy no.
	 * 
	 * @return String
	 */
	public String getPolicyNo();

	public void setArrInsurer(String arrInsurer);

	public void setBankRiskConfirmation(boolean bankConfirmation);

	/**
	 * Set insurance effective date.
	 * 
	 * @param insEffectiveDate of type Date
	 */
	public void setInsEffectiveDate(Date insEffectiveDate);

	/**
	 * Set insurance expiry date.
	 * 
	 * @param insExpiryDate of type Date
	 */
	public void setInsExpiryDate(Date insExpiryDate);

	public void setInsurancePremium(Amount insurancePremium);

	/**
	 * Set insurance type.
	 * 
	 * @param insuranceType of type String
	 */
	public void setInsuranceType(String insuranceType);

	/**
	 * Set insured amount.
	 * 
	 * @param insuredAmount of type Amount
	 */
	public void setInsuredAmount(Amount insuredAmount);

	/**
	 * Set insured amount currency code.
	 * 
	 * @param insuredCcyCode of type String
	 */
	public void setInsuredCcyCode(String insuredCcyCode);

	/**
	 * Set insurer's name.
	 * 
	 * @param insurerName of type String
	 */
	public void setInsurerName(String insurerName);

	/**
	 * Set if the bank's interest is duly noted.
	 * 
	 * @param isBankInterestDulyNoted
	 */
	public void setIsBankInterestDulyNoted(boolean isBankInterestDulyNoted);

	public void setIssuanceDate(Date issuanceDate);

	/**
	 * Set policy no.
	 * 
	 * @param policyNo of type String
	 */
	
	
	public void setPolicyNo(String policyNo);
	//Added by Pramod Katkar for New Filed CR on 20-08-2013
	public int getPhysicalInspectionFreq();
	public void setPhysicalInspectionFreq(int physicalInspectionFreq);
	public String getPhysicalInspectionFreqUnit();
	public void setPhysicalInspectionFreqUnit(String physicalInspectionFreqUnit);
	public boolean getIsPhysicalInspection();
	public void setIsPhysicalInspection(boolean isPhysicalInspection);
	public Date getLastPhysicalInspectDate();
	public void setLastPhysicalInspectDate(Date lastPhysicalInspectDate);
	public Date getNextPhysicalInspectDate();
	public void setNextPhysicalInspectDate(Date nextPhysicalInspectDate);
	
	//End by Pramod Katkar
}
