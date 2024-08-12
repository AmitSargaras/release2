package com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeaircraft;

import java.math.BigDecimal;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.EBAssetChargeDetailBean;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public abstract class EBSpecificChargeAircraftBean extends EBAssetChargeDetailBean {

	/**
	 * Get if it is insurance broker undertaking.
	 * 
	 * @return boolean
	 */
	public boolean getIsInsBrokerUndertake() {
		String isIns = getEBIsInsBrokerUndertake();
		if ((isIns != null) && isIns.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Set if it is insurance broker undertaking.
	 * 
	 * @param isInsBrokerUndertake of type boolean
	 */
	public void setIsInsBrokerUndertake(boolean isInsBrokerUndertake) {
		if (isInsBrokerUndertake) {
			setEBIsInsBrokerUndertake(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBIsInsBrokerUndertake(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Get amount assignment of insurances.
	 * 
	 * @return Amount
	 */
	public Amount getInsAssignAmount() {
		if (getEBInsAssignAmount() != null) {
			return new Amount(getEBInsAssignAmount(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	/**
	 * Set amount assignment of insurances.
	 * 
	 * @param insAssignAmount of type Amount
	 */
	public void setInsAssignAmount(Amount insAssignAmount) {
		setEBInsAssignAmount((insAssignAmount == null) ? null : insAssignAmount.getAmountAsBigDecimal());
	}

	/**
	 * Get amount trade in value.
	 * 
	 * @return Amount
	 */
	public Amount getTradeinValue() {
		if (getEBtradeinValue() != null) {
			return new Amount(getEBtradeinValue(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	/**
	 * Set amount trade in value.
	 * 
	 * @param tradeinValue of type Amount
	 */
	public void setTradeinValue(Amount tradeinValue) {
		setEBtradeinValue((tradeinValue == null) ? null : tradeinValue.getAmountAsBigDecimal());
	}

	/**
	 * Get amount trade in deposit.
	 * 
	 * @return Amount
	 */
	public Amount getTradeinDeposit() {
		if (getEBtradeinDeposit() != null) {
			return new Amount(getEBtradeinDeposit(), new CurrencyCode(currencyCode));
		}
		return null;
	}

	/**
	 * Set amount trade in deposit.
	 * 
	 * @param tradeinDeposit of type Amount
	 */
	public void setTradeinDeposit(Amount tradeinDeposit) {
		setEBtradeinDeposit((tradeinDeposit == null) ? null : tradeinDeposit.getAmountAsBigDecimal());
	}

	/**
	 * Get if it is assignment of insurances.
	 * 
	 * @return boolean
	 */
	public boolean getIsInsAssign() {
		String isIns = getEBIsInsAssign();
		if ((isIns != null) && isIns.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Set if it is assignment of insurances.
	 * 
	 * @param isInsAssign of type boolean
	 */
	public void setIsInsAssign(boolean isInsAssign) {
		if (isInsAssign) {
			setEBIsInsAssign(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBIsInsAssign(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Get if it is assignment of reinsurances.
	 * 
	 * @return boolean
	 */
	public boolean getIsReinsAssign() {
		String isReins = getEBIsReinsAssign();
		if ((isReins != null) && isReins.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Set if it is assignment of reinsurances.
	 * 
	 * @param isReinsAssign of type boolean
	 */
	public void setIsReinsAssign(boolean isReinsAssign) {
		if (isReinsAssign) {
			setEBIsReinsAssign(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBIsReinsAssign(ICMSConstant.FALSE_VALUE);
		}
	}

	public abstract String getEBIsInsBrokerUndertake();

	public abstract void setEBIsInsBrokerUndertake(String eBIsInsBrokerUndertake);

	public abstract BigDecimal getEBInsAssignAmount();

	public abstract void setEBInsAssignAmount(BigDecimal eBInsAssignAmount);

	public abstract String getEBIsInsAssign();

	public abstract void setEBIsInsAssign(String eBIsInsAssign);

	public abstract String getEBIsReinsAssign();

	public abstract void setEBIsReinsAssign(String eBIsReinsAssign);

	/**
	 * Get process agent of operators.
	 * 
	 * @return String
	 */
	public abstract String getProcessAgent();

	/**
	 * Set process agent of operators.
	 * 
	 * @param processAgent of type String
	 */
	public abstract void setProcessAgent(String processAgent);

	/**
	 * Get country of process agent of operators.
	 * 
	 * @return String
	 */
	public abstract String getProcessAgentCountry();

	/**
	 * Set country of process agent of operators.
	 * 
	 * @param processAgentCountry of type String
	 */
	public abstract void setProcessAgentCountry(String processAgentCountry);

	/**
	 * Get specialist legal advice and opinion.
	 * 
	 * @return String
	 */
	public abstract String getLegalAdvice();

	/**
	 * Set specialist legal advice and opinion.
	 * 
	 * @param legalAdvice of type String
	 */
	public abstract void setLegalAdvice(String legalAdvice);

	/**
	 * Get export credit agency.
	 * 
	 * @return String
	 */
	public abstract String getExportCrAgency();

	/**
	 * Set export credit agency.
	 * 
	 * @param exportCrAgency of type String
	 */
	public abstract void setExportCrAgency(String exportCrAgency);

	/**
	 * Get name of guarantors.
	 * 
	 * @return String
	 */
	public abstract String getGuarantors();

	/**
	 * Set name of guarantors.
	 * 
	 * @param guarantors of type String
	 */
	public abstract void setGuarantors(String guarantors);

	/**
	 * Get aircraft serial no.
	 * 
	 * @return String
	 */
	public abstract String getAircraftSerialNo();

	/**
	 * Set aircraft serial no.
	 * 
	 * @param aircraftSerialNo of type String
	 */
	public abstract void setAircraftSerialNo(String aircraftSerialNo);

	/**
	 * Get name of manufacturer.
	 * 
	 * @return String
	 */
	public abstract String getManufacturer();

	/**
	 * Set name of manufacturer.
	 * 
	 * @param manufacturer of type String
	 */
	public abstract void setManufacturer(String manufacturer);

	/**
	 * Get manufacturer warranties.
	 * 
	 * @return String
	 */
	public abstract String getManufacturerWarranties();

	/**
	 * Set manufacturer warranties.
	 * 
	 * @param manufacturerWarranties of type String
	 */
	public abstract void setManufacturerWarranties(String manufacturerWarranties);

	/**
	 * Get assignors.
	 * 
	 * @return String
	 */
	public abstract String getAssignors();

	/**
	 * Set assignors.
	 * 
	 * @param assignors of type String
	 */
	public abstract void setAssignors(String assignors);

	/**
	 * Get effective date assignment of insurances.
	 * 
	 * @return Date
	 */
	public abstract Date getInsAssignEffectiveDate();

	/**
	 * Set effective date assignment of insurances.
	 * 
	 * @param insAssignEffectiveDate of type Date
	 */
	public abstract void setInsAssignEffectiveDate(Date insAssignEffectiveDate);

	/**
	 * Get expiry date assignment of insurances.
	 * 
	 * @return Date
	 */
	public abstract Date getInsAssignExpiryDate();

	/**
	 * Set expiry date assignment of insurances.
	 * 
	 * @param insAssignExpiryDate of type Date
	 */
	public abstract void setInsAssignExpiryDate(Date insAssignExpiryDate);

	/**
	 * Get effective date of assignment of reinsurances.
	 * 
	 * @return Date
	 */
	public abstract Date getReinsAssignEffectiveDate();

	/**
	 * Set effective date of assignment of reinsurances.
	 * 
	 * @param reinsAssignEffectiveDate of type Date
	 */
	public abstract void setReinsAssignEffectiveDate(Date reinsAssignEffectiveDate);

	/**
	 * Get expiry date of assignment of reinsurances.
	 * 
	 * @return Date
	 */
	public abstract Date getReinsAssignExpiryDate();

	/**
	 * Set expiry date of assignment of reinsurances.
	 * 
	 * @param reinsAssignExpiryDate of type Date
	 */
	public abstract void setReinsAssignExpiryDate(Date reinsAssignExpiryDate);

	public abstract BigDecimal getEBtradeinValue();

	public abstract void setEBtradeinValue(BigDecimal eBtradeinValue);

	public abstract BigDecimal getEBtradeinDeposit();

	public abstract void setEBtradeinDeposit(BigDecimal eBtradeinDeposit);
	
	
	public abstract Date getStartDate();

	public abstract void setStartDate(Date startDate);

	public abstract Date getMaturityDate();

	public abstract void setMaturityDate(Date maturityDate);

	public abstract String getRamId();

	public abstract void setRamId(String ramId);

}
