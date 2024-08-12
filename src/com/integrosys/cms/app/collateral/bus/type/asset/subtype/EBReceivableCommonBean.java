/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/EBReceivableCommonBean.java,v 1.20 2005/08/12 04:39:17 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.EBCollateralDetailBean;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for asset of type charge.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.20 $
 * @since $Date: 2005/08/12 04:39:17 $ Tag: $Name: $
 */
public abstract class EBReceivableCommonBean extends EBCollateralDetailBean implements IReceivableCommon {
	/**
	 * Get collateral ID.
	 * 
	 * @return long
	 */
	public long getCollateralID() {
		return getEBCollateralID().longValue();
	}

	/**
	 * set collateral ID.
	 * 
	 * @param collateralID is of type long
	 */
	public void setCollateralID(long collateralID) {
		setEBCollateralID(new Long(collateralID));
	}

	/**
	 * Get proceeds of receivables controlled by own bank.
	 * 
	 * @return boolean
	 */
	public boolean getIsOwnProceedsOfReceivables() {
		String isOwn = getEBIsOwnProceedsOfReceivables();
		if ((isOwn != null) && isOwn.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Set proceeds of receivables controlled by own bank.
	 * 
	 * @param isOwnProceedsOfReceivables is of type boolean
	 */
	public void setIsOwnProceedsOfReceivables(boolean isOwnProceedsOfReceivables) {
		if (isOwnProceedsOfReceivables) {
			setEBIsOwnProceedsOfReceivables(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBIsOwnProceedsOfReceivables(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Get nominal Value.
	 * 
	 * @return Amount
	 */
	public Amount getNominalValue() {
		if (getEBNominalValue() != null) {
			return new Amount(getEBNominalValue().doubleValue(), currencyCode);
		}
		else {
			return null;
		}
	}

	/**
	 * Set nominal value.
	 * 
	 * @param nominalValue of type Amount
	 */
	public void setNominalValue(Amount nominalValue) {
		if (nominalValue != null) {
			setEBNominalValue(new Double(nominalValue.getAmountAsDouble()));
		}
		else {
			setEBNominalValue(null);
		}
	}

	/**
	 * Get location of own bank account no.
	 * 
	 * @return String
	 */
	public String getOwnAccNoLocation() {
		return getBankAccNoLocation();
	}

	/**
	 * Set location of own bank account no.
	 * 
	 * @param ownAccNoLocation is of type String
	 */
	public void setOwnAccNoLocation(String ownAccNoLocation) {
		setBankAccNoLocation(ownAccNoLocation);
	}

	/**
	 * Get Insurance Info currency Code - not used in charge common
	 * 
	 * @return null
	 */
	public String getCurrentyCode() {
		return null;
	}

	/*
	 * Set insurance Info Currency Code - not used in charge common
	 * 
	 * @param currencyCode of type String
	 */
	public void setCurrencyCode(String currencyCode) {
	}

	/**
	 * Get Insurance Info ID - not used in charge common
	 * 
	 * @return null
	 */
	public long getInsuranceInfoID() {
		return ICMSConstant.LONG_MIN_VALUE;
	}

	/*
	 * Set insurance Info ID - not used in charge common
	 * 
	 * @param insuranceInfoID of type long
	 */
	public void setInsuranceInfoID(long insuranceInfoID) {
	}

	/**
	 * Get Insurance Info ref ID - not used in charge common
	 * 
	 * @return null
	 */
	public String getRefID() {
		return null;
	}

	/*
	 * Set insurance Info ref ID - not used in charge common
	 * 
	 * @param refID of type String
	 */
	public void setRefID(String refID) {
	}

	/**
	 * Get Insurance Info status - not used in charge common
	 * 
	 * @return null
	 */
	public String getStatus() {
		return null;
	}

	/*
	 * Set insurance Info status - not used in charge common
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status) {
	}

	/**
	 * Get Insurance Info category - not used in charge common
	 * 
	 * @return null
	 */
	public String getCategory() {
		return null;
	}

	/*
	 * Set insurance Info category - not used in charge common
	 * 
	 * @param category of type String
	 */
	public void setCategory(String category) {
	}

	public abstract Long getEBCollateralID();

	public abstract void setEBCollateralID(Long eBCollateralID);

	public abstract String getAgentName();

	public abstract void setAgentName(String agentName);

	public abstract String getAgentLocation();

	public abstract void setAgentLocation(String agentLocation);

	public abstract String getApprovedBuyer();

	public abstract void setApprovedBuyer(String approvedBuyer);

	public abstract String getApprovedBuyerLocation();

	public abstract void setApprovedBuyerLocation(String approvedBuyerLocation);

	public abstract String getOwnAccNo();

	public abstract void setOwnAccNo(String ownAccNo);

	public abstract String getEBIsOwnProceedsOfReceivables();

	public abstract void setEBIsOwnProceedsOfReceivables(String eBIsOwnProceedsOfReceivables);

	public abstract String getBankAccNoLocation();

	public abstract void setBankAccNoLocation(String bankAccNoLocation);

	public abstract String getAgentBankReceivables();

	public abstract void setAgentBankReceivables(String agentBankReceivables);

	public abstract String getAgentBankLocation();

	public abstract void setAgentBankLocation(String agentBankLocation);

	public abstract Double getEBNominalValue();

	public abstract void setEBNominalValue(Double nominalValue);

	public String getCoverageType() {
		return null;
	}

	public void setCoverageType(String coverageType) {
	}

	public String getOtherCoverageType() {
		return null;
	}

	public void setOtherCoverageType(String otherCoverageType) {
	}

	// FOR Spec agent or gen agent or no agent

	public abstract String getProjectName();

	public abstract void setProjectName(String projectName);

	public abstract String getBlanketAssignment();

	public abstract void setBlanketAssignment(String blanketAssignment);

	public abstract String getDateAwarded();

	public abstract void setDateAwarded(String dateAwarded);

	public abstract String getEBLetterInstructFlag();

	public abstract void setEBLetterInstructFlag(String eBLetterInstructFlag);

	public boolean getLetterInstructFlag() {
		String flag = getEBLetterInstructFlag();
		if ((flag != null) && flag.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	public void setLetterInstructFlag(boolean letterInstructFlag) {
		if (letterInstructFlag) {
			setEBLetterInstructFlag(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBLetterInstructFlag(ICMSConstant.FALSE_VALUE);
		}
	}

	public abstract String getEBLetterUndertakeFlag();

	public abstract void setEBLetterUndertakeFlag(String eBLetterUndertakeFlag);

	public boolean getLetterUndertakeFlag() {
		String flag = getEBLetterUndertakeFlag();
		if ((flag != null) && flag.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	public void setLetterUndertakeFlag(boolean letterUndertakeFlag) {
		if (letterUndertakeFlag) {
			setEBLetterUndertakeFlag(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBLetterUndertakeFlag(ICMSConstant.FALSE_VALUE);
		}

	}

    public abstract String getInvoiceType();

    public abstract void setInvoiceType(String invoiceType);

	public abstract String getRemarks();

	public abstract void setRemarks(String remarks);
}