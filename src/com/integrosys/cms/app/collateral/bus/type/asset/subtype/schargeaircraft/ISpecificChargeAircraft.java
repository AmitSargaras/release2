/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/schargeaircraft/ISpecificChargeAircraft.java,v 1.1 2003/07/28 08:28:15 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeaircraft;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.IChargeCommon;

/**
 * This interface represents Asset of type Specific Charge - Aircraft.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/28 08:28:15 $ Tag: $Name: $
 */
public interface ISpecificChargeAircraft extends IChargeCommon {
	/**
	 * Get if it is insurance broker undertaking.
	 * 
	 * @return boolean
	 */
	public boolean getIsInsBrokerUndertake();

	/**
	 * Set if it is insurance broker undertaking.
	 * 
	 * @param isInsBrokerUndertake of type boolean
	 */
	public void setIsInsBrokerUndertake(boolean isInsBrokerUndertake);

	/**
	 * Get process agent of operators.
	 * 
	 * @return String
	 */
	public String getProcessAgent();

	/**
	 * Set process agent of operators.
	 * 
	 * @param processAgent of type String
	 */
	public void setProcessAgent(String processAgent);

	/**
	 * Get country of process agent of operators.
	 * 
	 * @return String
	 */
	public String getProcessAgentCountry();

	/**
	 * Set country of process agent of operators.
	 * 
	 * @param processAgentCountry of type String
	 */
	public void setProcessAgentCountry(String processAgentCountry);

	/**
	 * Get specialist legal advice and opinion.
	 * 
	 * @return String
	 */
	public String getLegalAdvice();

	/**
	 * Set specialist legal advice and opinion.
	 * 
	 * @param legalAdvice of type String
	 */
	public void setLegalAdvice(String legalAdvice);

	/**
	 * Get export credit agency.
	 * 
	 * @return String
	 */
	public String getExportCrAgency();

	/**
	 * Set export credit agency.
	 * 
	 * @param exportCrAgency of type String
	 */
	public void setExportCrAgency(String exportCrAgency);

	/**
	 * Get name of guarantors.
	 * 
	 * @return String
	 */
	public String getGuarantors();

	/**
	 * Set name of guarantors.
	 * 
	 * @param guarantors of type String
	 */
	public void setGuarantors(String guarantors);

	/**
	 * Get aircraft serial no.
	 * 
	 * @return String
	 */
	public String getAircraftSerialNo();

	/**
	 * Set aircraft serial no.
	 * 
	 * @param aircraftSerialNo of type String
	 */
	public void setAircraftSerialNo(String aircraftSerialNo);

	/**
	 * Get name of manufacturer.
	 * 
	 * @return String
	 */
	public String getManufacturer();

	/**
	 * Set name of manufacturer.
	 * 
	 * @param manufacturer of type String
	 */
	public void setManufacturer(String manufacturer);

	/**
	 * Get manufacturer warranties.
	 * 
	 * @return String
	 */
	public String getManufacturerWarranties();

	/**
	 * Set manufacturer warranties.
	 * 
	 * @param manufacturerWarranties of type String
	 */
	public void setManufacturerWarranties(String manufacturerWarranties);

	/**
	 * Get assignors.
	 * 
	 * @return String
	 */
	public String getAssignors();

	/**
	 * Set assignors.
	 * 
	 * @param assignors of type String
	 */
	public void setAssignors(String assignors);

	/**
	 * Get amount assignment of insurances.
	 * 
	 * @return Amount
	 */
	public Amount getInsAssignAmount();

	/**
	 * Set amount assignment of insurances.
	 * 
	 * @param insAssignAmount of type Amount
	 */
	public void setInsAssignAmount(Amount insAssignAmount);

	/**
	 * Get effective date assignment of insurances.
	 * 
	 * @return Date
	 */
	public Date getInsAssignEffectiveDate();

	/**
	 * Set effective date assignment of insurances.
	 * 
	 * @param insAssignEffectiveDate of type Date
	 */
	public void setInsAssignEffectiveDate(Date insAssignEffectiveDate);

	/**
	 * Get expiry date assignment of insurances.
	 * 
	 * @return Date
	 */
	public Date getInsAssignExpiryDate();

	/**
	 * Set expiry date assignment of insurances.
	 * 
	 * @param insAssignExpiryDate of type Date
	 */
	public void setInsAssignExpiryDate(Date insAssignExpiryDate);

	/**
	 * Get effective date of assignment of reinsurances.
	 * 
	 * @return Date
	 */
	public Date getReinsAssignEffectiveDate();

	/**
	 * Set effective date of assignment of reinsurances.
	 * 
	 * @param reinsAssignEffectiveDate of type Date
	 */
	public void setReinsAssignEffectiveDate(Date reinsAssignEffectiveDate);

	/**
	 * Get expiry date of assignment of reinsurances.
	 * 
	 * @return Date
	 */
	public Date getReinsAssignExpiryDate();

	/**
	 * Set expiry date of assignment of reinsurances.
	 * 
	 * @param reinsAssignExpiryDate of type Date
	 */
	public void setReinsAssignExpiryDate(Date reinsAssignExpiryDate);

	/**
	 * Get if it is assignment of insurances.
	 * 
	 * @return boolean
	 */
	public boolean getIsInsAssign();

	/**
	 * Set if it is assignment of insurances.
	 * 
	 * @param isInsAssign of type boolean
	 */
	public void setIsInsAssign(boolean isInsAssign);

	/**
	 * Get if it is assignment of reinsurances.
	 * 
	 * @return boolean
	 */
	public boolean getIsReinsAssign();

	/**
	 * Set if it is assignment of reinsurances.
	 * 
	 * @param isReinsAssign of type boolean
	 */
	public void setIsReinsAssign(boolean isReinsAssign);
	
	/**
	 * Get amount trade in value.
	 * 
	 * @return Amount
	 */
	public Amount getTradeinValue();
	
	/**
	 * Set amount trade in value.
	 * 
	 * @param tradeinValue of type Amount
	 */
	public void setTradeinValue(Amount tradeinValue);
		
	/**
	 * Get amount trade in deposit.
	 * 
	 * @return Amount
	 */
	public Amount getTradeinDeposit();

	/**
	 * Set amount trade in deposit.
	 * 
	 * @param tradeinDeposit of type Amount
	 */
	public void setTradeinDeposit(Amount tradeinDeposit);

	
	public Date getStartDate();

	public void setStartDate(Date startDate);

	public Date getMaturityDate();

	public void setMaturityDate(Date maturityDate);

	public String getRamId();

	public void setRamId(String ramId);
}
