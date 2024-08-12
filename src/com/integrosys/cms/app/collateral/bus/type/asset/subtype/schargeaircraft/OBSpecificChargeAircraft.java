/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/schargeaircraft/OBSpecificChargeAircraft.java,v 1.3 2006/01/18 05:32:00 priya Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeaircraft;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.OBChargeCommon;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Asset of type Specific Charge - Aircraft.
 * 
 * @author $Author: priya $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2006/01/18 05:32:00 $ Tag: $Name: $
 */
public class OBSpecificChargeAircraft extends OBChargeCommon implements ISpecificChargeAircraft {
	private boolean isInsBrokerUndertake;

	private String processAgent;

	private String processAgentCountry;

	private String legalAdvice;

	private String exportCrAgency;

	private String guarantors;

	private String aircraftSerialNo;

	private String manufacturer;

	private String manufacturerWarranties;

	private String assignors;

	private boolean isInsAssign;

	private Amount insAssignAmount;

	private Date insAssignEffectiveDate;

	private Date insAssignExpiryDate;

	private boolean isReinsAssign;

	private Date reinsAssignEffectiveDate;

	private Date reinsAssignExpiryDate;
	
	private Amount tradeinValue;
	
	private Amount tradeinDeposit;
	
	private Date startDate;

	private Date maturityDate;
	
	private String ramId;
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getRamId() {
		return ramId;
	}

	public void setRamId(String ramId) {
		this.ramId = ramId;
	}

	public Amount getTradeinValue() {
		return tradeinValue;
	}

	public void setTradeinValue(Amount tradeinValue) {
		this.tradeinValue = tradeinValue;
	}

	public Amount getTradeinDeposit() {
		return tradeinDeposit;
	}

	public void setTradeinDeposit(Amount tradeinDeposit) {
		this.tradeinDeposit = tradeinDeposit;
	}

	/**
	 * Default Constructor.
	 */
	public OBSpecificChargeAircraft() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_AIRCRAFT));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ISpecificChargeAircraft
	 */
	public OBSpecificChargeAircraft(ISpecificChargeAircraft obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get if it is insurance broker undertaking.
	 * 
	 * @return boolean
	 */
	public boolean getIsInsBrokerUndertake() {
		return isInsBrokerUndertake;
	}

	/**
	 * Set if it is insurance broker undertaking.
	 * 
	 * @param isInsBrokerUndertake of type boolean
	 */
	public void setIsInsBrokerUndertake(boolean isInsBrokerUndertake) {
		this.isInsBrokerUndertake = isInsBrokerUndertake;
	}

	/**
	 * Get process agent of operators.
	 * 
	 * @return String
	 */
	public String getProcessAgent() {
		return processAgent;
	}

	/**
	 * Set process agent of operators.
	 * 
	 * @param processAgent of type String
	 */
	public void setProcessAgent(String processAgent) {
		this.processAgent = processAgent;
	}

	/**
	 * Get country of process agent of operators.
	 * 
	 * @return String
	 */
	public String getProcessAgentCountry() {
		return processAgentCountry;
	}

	/**
	 * Set country of process agent of operators.
	 * 
	 * @param processAgentCountry of type String
	 */
	public void setProcessAgentCountry(String processAgentCountry) {
		this.processAgentCountry = processAgentCountry;
	}

	/**
	 * Get specialist legal advice and opinion.
	 * 
	 * @return String
	 */
	public String getLegalAdvice() {
		return legalAdvice;
	}

	/**
	 * Set specialist legal advice and opinion.
	 * 
	 * @param legalAdvice of type String
	 */
	public void setLegalAdvice(String legalAdvice) {
		this.legalAdvice = legalAdvice;
	}

	/**
	 * Get export credit agency.
	 * 
	 * @return String
	 */
	public String getExportCrAgency() {
		return exportCrAgency;
	}

	/**
	 * Set export credit agency.
	 * 
	 * @param exportCrAgency of type String
	 */
	public void setExportCrAgency(String exportCrAgency) {
		this.exportCrAgency = exportCrAgency;
	}

	/**
	 * Get name of guarantors.
	 * 
	 * @return String
	 */
	public String getGuarantors() {
		return guarantors;
	}

	/**
	 * Set name of guarantors.
	 * 
	 * @param guarantors of type String
	 */
	public void setGuarantors(String guarantors) {
		this.guarantors = guarantors;
	}

	/**
	 * Get aircraft serial no.
	 * 
	 * @return String
	 */
	public String getAircraftSerialNo() {
		return aircraftSerialNo;
	}

	/**
	 * Set aircraft serial no.
	 * 
	 * @param aircraftSerialNo of type String
	 */
	public void setAircraftSerialNo(String aircraftSerialNo) {
		this.aircraftSerialNo = aircraftSerialNo;
	}

	/**
	 * Get name of manufacturer.
	 * 
	 * @return String
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * Set name of manufacturer.
	 * 
	 * @param manufacturer of type String
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * Get manufacturer warranties.
	 * 
	 * @return String
	 */
	public String getManufacturerWarranties() {
		return manufacturerWarranties;
	}

	/**
	 * Set manufacturer warranties.
	 * 
	 * @param manufacturerWarranties of type String
	 */
	public void setManufacturerWarranties(String manufacturerWarranties) {
		this.manufacturerWarranties = manufacturerWarranties;
	}

	/**
	 * Get assignors.
	 * 
	 * @return String
	 */
	public String getAssignors() {
		return assignors;
	}

	/**
	 * Set assignors.
	 * 
	 * @param assignors of type String
	 */
	public void setAssignors(String assignors) {
		this.assignors = assignors;
	}

	/**
	 * Get amount assignment of insurances.
	 * 
	 * @return Amount
	 */
	public Amount getInsAssignAmount() {
		return insAssignAmount;
	}

	/**
	 * Set amount assignment of insurances.
	 * 
	 * @param insAssignAmount of type Amount
	 */
	public void setInsAssignAmount(Amount insAssignAmount) {
		this.insAssignAmount = insAssignAmount;
	}

	/**
	 * Get effective date assignment of insurances.
	 * 
	 * @return Date
	 */
	public Date getInsAssignEffectiveDate() {
		return insAssignEffectiveDate;
	}

	/**
	 * Set effective date assignment of insurances.
	 * 
	 * @param insAssignEffectiveDate of type Date
	 */
	public void setInsAssignEffectiveDate(Date insAssignEffectiveDate) {
		this.insAssignEffectiveDate = insAssignEffectiveDate;
	}

	/**
	 * Get expiry date assignment of insurances.
	 * 
	 * @return Date
	 */
	public Date getInsAssignExpiryDate() {
		return insAssignExpiryDate;
	}

	/**
	 * Set expiry date assignment of insurances.
	 * 
	 * @param insAssignExpiryDate of type Date
	 */
	public void setInsAssignExpiryDate(Date insAssignExpiryDate) {
		this.insAssignExpiryDate = insAssignExpiryDate;
	}

	/**
	 * Get effective date of assignment of reinsurances.
	 * 
	 * @return Date
	 */
	public Date getReinsAssignEffectiveDate() {
		return reinsAssignEffectiveDate;
	}

	/**
	 * Set effective date of assignment of reinsurances.
	 * 
	 * @param reinsAssignEffectiveDate of type Date
	 */
	public void setReinsAssignEffectiveDate(Date reinsAssignEffectiveDate) {
		this.reinsAssignEffectiveDate = reinsAssignEffectiveDate;
	}

	/**
	 * Get expiry date of assignment of reinsurances.
	 * 
	 * @return Date
	 */
	public Date getReinsAssignExpiryDate() {
		return reinsAssignExpiryDate;
	}

	/**
	 * Set expiry date of assignment of reinsurances.
	 * 
	 * @param reinsAssignExpiryDate of type Date
	 */
	public void setReinsAssignExpiryDate(Date reinsAssignExpiryDate) {
		this.reinsAssignExpiryDate = reinsAssignExpiryDate;
	}

	/**
	 * Get if it is assignment of insurances.
	 * 
	 * @return boolean
	 */
	public boolean getIsInsAssign() {
		return isInsAssign;
	}

	/**
	 * Set if it is assignment of insurances.
	 * 
	 * @param isInsAssign of type boolean
	 */
	public void setIsInsAssign(boolean isInsAssign) {
		this.isInsAssign = isInsAssign;
	}

	/**
	 * Get if it is assignment of reinsurances.
	 * 
	 * @return boolean
	 */
	public boolean getIsReinsAssign() {
		return isReinsAssign;
	}

	/**
	 * Set if it is assignment of reinsurances.
	 * 
	 * @param isReinsAssign of type boolean
	 */
	public void setIsReinsAssign(boolean isReinsAssign) {
		this.isReinsAssign = isReinsAssign;
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
		else if (!(obj instanceof OBSpecificChargeAircraft)) {
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

}