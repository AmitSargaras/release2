/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/insurance/subtype/creditinsurance/ICreditInsurance.java,v 1.7 2003/07/24 06:13:16 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditinsurance;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.type.insurance.IInsuranceCollateral;

/**
 * This interface represents Insurance of type Credit Insurance.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2003/07/24 06:13:16 $ Tag: $Name: $
 */
public interface ICreditInsurance extends IInsuranceCollateral {
	/**
	 * Get insurer's name.
	 * 
	 * @return String
	 */
	public String getInsurerName();

	/**
	 * Set insurer's name.
	 * 
	 * @param insurerName of type String
	 */
	public void setInsurerName(String insurerName);

	/**
	 * Get insurance type.
	 * 
	 * @return String
	 */
	public String getInsuranceType();

	/**
	 * Set insurance type.
	 * 
	 * @param insuranceType of type String
	 */
	public void setInsuranceType(String insuranceType);

	/**
	 * Get insured amount.
	 * 
	 * @return Amount
	 */
	public Amount getInsuredAmount();

	/**
	 * Set insured amount.
	 * 
	 * @param insuredAmount of type Amount
	 */
	public void setInsuredAmount(Amount insuredAmount);

	/**
	 * Get insured amount currency code.
	 * 
	 * @return String
	 */
	public String getInsuredCcyCode();

	/**
	 * Set insured amount currency code.
	 * 
	 * @param insuredCcyCode of type String
	 */
	public void setInsuredCcyCode(String insuredCcyCode);

	/**
	 * Get insurance effective date.
	 * 
	 * @return Date
	 */
	public Date getInsEffectiveDate();

	/**
	 * Set insurance effective date.
	 * 
	 * @param insEffectiveDate of type Date
	 */
	public void setInsEffectiveDate(Date insEffectiveDate);

	/**
	 * Get insurance expiry date.
	 * 
	 * @return Date
	 */
	public Date getInsExpiryDate();

	/**
	 * Set insurance expiry date.
	 * 
	 * @param insExpiryDate of type Date
	 */
	public void setInsExpiryDate(Date insExpiryDate);

	/**
	 * Get policy no.
	 * 
	 * @return String
	 */
	public String getPolicyNo();

	/**
	 * Set policy no.
	 * 
	 * @param policyNo of type String
	 */
	public void setPolicyNo(String policyNo);

	/**
	 * Get external legal counsel.
	 * 
	 * @return String
	 */
	public String getExternalLegalCounsel();

	/**
	 * Set external legal counsel.
	 * 
	 * @param externalLegalCounsel of type String
	 */
	public void setExternalLegalCounsel(String externalLegalCounsel);

	/**
	 * Get if it is acceleration clause.
	 * 
	 * @return boolean
	 */
	public boolean getIsAccelerationClause();

	/**
	 * Set if it is acceleration clause.
	 * 
	 * @param isAccelerationClause of type boolean
	 */
	public void setIsAccelerationClause(boolean isAccelerationClause);

	/**
	 * Get local currencies in core markets.
	 * 
	 * @return String
	 */
	public String getLocalCCyInCoreMarket();

	/**
	 * Set local currencies in core markets.
	 * 
	 * @param localCCyInCoreMarket of type String
	 */
	public void setLocalCCyInCoreMarket(String localCCyInCoreMarket);

	/**
	 * Get core markets.
	 * 
	 * @return String
	 */
	public String getCoreMarket();

	/**
	 * Set core markets.
	 * 
	 * @param coreMarket of type String
	 */
	public void setCoreMarket(String coreMarket);

	/**
	 * Get specific cover description.
	 * 
	 * @return String
	 */
	public String getDescription();

	/**
	 * Set specific cover description.
	 * 
	 * @param specificCoverDesc of type String
	 */
	public void setDescription(String specificCoverDesc);
	
	public Date getISDADate();
	
	public void setISDADate(Date iSDADate);
	
	public Date getTreasuryDocDate();
	
	public void setTreasuryDocDate(Date treasuryDocDate);
	
	public boolean getBankRiskConfirmation();
	
	public void setBankRiskConfirmation(boolean bankRiskConfirmation);
	
	public String getArrInsurer();
	
	public void setArrInsurer(String arrInsurer);
	
}
