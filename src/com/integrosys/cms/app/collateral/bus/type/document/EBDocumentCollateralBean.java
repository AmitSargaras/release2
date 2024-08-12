/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/document/EBDocumentCollateralBean.java,v 1.3 2005/09/27 07:22:48 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.document;

import java.math.BigDecimal;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.cms.app.collateral.bus.EBCollateralDetailBean;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for collateral of type document.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/09/27 07:22:48 $ Tag: $Name: $
 */
public abstract class EBDocumentCollateralBean extends EBCollateralDetailBean implements IDocumentCollateral {
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
	 * Get document minimun amount.
	 * 
	 * @return Amount
	 */
	public Amount getMinAmount() {
		return (getEBMinAmount() == null ? null : new Amount(getEBMinAmount(), new CurrencyCode(getMinAmountCcy())));
	}

	/**
	 * Set document minimum amount.
	 * 
	 * @param minAmount of type Amount
	 */
	public void setMinAmount(Amount minAmount) {
		setEBMinAmount(minAmount == null ? null : minAmount.getAmountAsBigDecimal());
		setMinAmountCcy(minAmount == null ? null : minAmount.getCurrencyCode());
	}

	/**
	 * Get document maximum amount.
	 * 
	 * @return Amount
	 */
	public Amount getMaxAmount() {
		return (getEBMaxAmount() == null ? null : new Amount(getEBMaxAmount(), new CurrencyCode(getMaxAmountCcy())));
	}

	/**
	 * Set document maximum amount.
	 * 
	 * @param maxAmount of type Amount
	 */
	public void setMaxAmount(Amount maxAmount) {
		setEBMaxAmount(maxAmount == null ? null : maxAmount.getAmountAsBigDecimal());
		setMaxAmountCcy(maxAmount == null ? null : maxAmount.getCurrencyCode());
	}

	/**
	 * Get document amount.
	 * 
	 * @return Amount
	 */
	public Amount getDocumentAmount() {
		return getEBDocumentAmount() == null ? null : new Amount(getEBDocumentAmount(), new CurrencyCode(
				getDocumentAmountCcy()));
	}

	/**
	 * Set document amount.
	 * 
	 * @param documentAmount of type Amount
	 */
	public void setDocumentAmount(Amount documentAmount) {
		setEBDocumentAmount(documentAmount == null ? null : documentAmount.getAmountAsBigDecimal());
		setDocumentAmountCcy(documentAmount == null ? null : documentAmount.getCurrencyCode());
	}

	/**
	 * Get buy back value
	 * 
	 * @return Amount
	 */
	public Amount getBuybackValue() {
		return getEBBuybackValue() == null ? null : new Amount(getEBBuybackValue(), new CurrencyCode(
				getBuybackValueCcy()));
	}

	/**
	 * Set buy back value
	 * 
	 * @param buybackValue of type Amount
	 */
	public void setBuybackValue(Amount buybackValue) {
		setEBBuybackValue(buybackValue == null ? null : buybackValue.getAmountAsBigDecimal());
		setBuybackValueCcy(buybackValue == null ? null : buybackValue.getCurrencyCode());
	}

	/**
	 * 
	 */
	public Amount getGuranteeAmount() {
		return getEBGuranteeAmount() == null ? null : new Amount(getEBGuranteeAmount(), new CurrencyCode(
				getBuybackValueCcy()));
	}

	public void setGuranteeAmount(Amount guranteeAmount) {
		setEBGuranteeAmount(guranteeAmount == null ? null : guranteeAmount.getAmountAsBigDecimal());
	}

	/**
	 * Get if the lease rental agreement.
	 * 
	 * @return boolean
	 */
	public boolean getLeaseRentalAgreement() {
		String isAgreement = getEBLeaseRentalAgreement();
		if ((isAgreement != null) && isAgreement.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Set the lease rental agreement.
	 * 
	 * @param isLEByChargeRanking is of type boolean
	 */
	public void setLeaseRentalAgreement(boolean leaseRentalAgreement) {
		if (leaseRentalAgreement) {
			setEBLeaseRentalAgreement(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBLeaseRentalAgreement(ICMSConstant.FALSE_VALUE);
		}
	}

	public Amount getContractAmt() {
		if (getEBContractAmt() != null)
			return new Amount(getEBContractAmt().doubleValue(), new CurrencyCode(getDocumentAmountCcy()));
		return null;
	}

	public void setContractAmt(Amount contractAmt) {
		if (contractAmt != null)
			setEBContractAmt(new Double(contractAmt.getAmountAsDouble()));
		else
			setEBContractAmt(null);
	}

	public abstract Long getEBCollateralID();

	public abstract void setEBCollateralID(Long eBCollateralID);

	public abstract Date getISDADate();

	public abstract void setISDADate(Date iSDADate);

	public abstract String getISDAProductDesc();

	public abstract void setISDAProductDesc(String iSDAProductDesc);

	public abstract Date getIFEMADate();

	public abstract void setIFEMADate(Date iFEMADate);

	public abstract String getIFEMAProductDesc();

	public abstract void setIFEMAProductDesc(String iFEMAProductDesc);

	public abstract Date getICOMDate();

	public abstract void setICOMDate(Date iCOMDate);

	public abstract String getICOMProductDesc();

	public abstract void setICOMProductDesc(String iCOMProductDesc);

	public abstract BigDecimal getEBMinAmount();

	public abstract void setEBMinAmount(BigDecimal eBMinAmount);

	public abstract BigDecimal getEBMaxAmount();

	public abstract void setEBMaxAmount(BigDecimal eBMaxAmount);

	public abstract String getMinAmountCcy();

	public abstract void setMinAmountCcy(String minAmountCcy);

	public abstract String getMaxAmountCcy();

	public abstract void setMaxAmountCcy(String maxAmountCcy);

	public abstract BigDecimal getEBDocumentAmount();

	public abstract void setEBDocumentAmount(BigDecimal eBDocumentAmount);

	public abstract String getDocumentAmountCcy();

	public abstract void setDocumentAmountCcy(String documentAmountCcy);

	public abstract String getDeedAssignmtTypeCode();

	public abstract void setDeedAssignmtTypeCode(String deedAssignmtTypeCode);

	public abstract String getProjectName();

	public abstract void setProjectName(String projectName);

	public abstract Date getAwardedDate();

	public abstract void setAwardedDate(Date awardedDate);

	public abstract String getLetterInstructFlag();

	public abstract void setLetterInstructFlag(String letterInstructFlag);

	public abstract String getLetterUndertakeFlag();

	public abstract void setLetterUndertakeFlag(String letterUndertakeFlag);

	public abstract String getBlanketAssignment();

	public abstract void setBlanketAssignment(String blanketAssignment);

	public abstract Date getDocumentDate();

	public abstract void setDocumentDate(Date documentDate);

	public abstract String getDocumentDesc();

	public abstract void setDocumentDesc(String documentDesc);

	public abstract String getReferenceNo();

	public abstract void setReferenceNo(String referenceNo);

	public abstract BigDecimal getEBBuybackValue();

	public abstract void setEBBuybackValue(BigDecimal buybackValue);

	public abstract String getBuybackValueCcy();

	public abstract void setBuybackValueCcy(String buybackValueCcy);

	public abstract String getIssuer();

	public abstract void setIssuer(String issuer);

	public abstract String getEBLeaseRentalAgreement();

	public abstract void setEBLeaseRentalAgreement(String leaseRentalAgreement);

	public abstract String getLeaseLimitation();

	public abstract void setLeaseLimitation(String leaseLimitation);

	public abstract String getPropertyType();

	public abstract void setPropertyType(String propertyType);

	public abstract String getLotsLocation();

	public abstract void setLotsLocation(String lotsLocation);

	public abstract String getTitleNumberType();

	public abstract void setTitleNumberType(String titleNumberType);

	public abstract String getTitleNumberValue();

	public abstract void setTitleNumberValue(String titleNumberValue);

	public abstract String getLeaseType();

	public abstract void setLeaseType(String leaseType);

	public abstract Date getDateOfLeaseAgreement();

	public abstract void setDateOfLeaseAgreement(Date dateOfLeaseAgreement);

	public abstract BigDecimal getEBGuranteeAmount();

	public abstract void setEBGuranteeAmount(BigDecimal guranteeAmount);

	public abstract Double getEBContractAmt();

	public abstract void setEBContractAmt(Double contractAmt);

	public abstract Date getContractDate();

	public abstract String getContractName();

	public abstract String getContractNumber();

	public abstract void setContractDate(Date contractDate);

	public abstract void setContractName(String contractName);

	public abstract void setContractNumber(String contractNumber);

}
