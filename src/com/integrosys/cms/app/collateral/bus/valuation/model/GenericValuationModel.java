package com.integrosys.cms.app.collateral.bus.valuation.model;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.support.ValuationFrequency;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class GenericValuationModel implements Serializable, IValuationModel {

	private static final long serialVersionUID = 7213175350674466046L;

	private long collateralId = ICMSConstant.LONG_INVALID_VALUE;

	private String secId; // use for debugging purpose only

	private String secType;

	private String secSubtype;

	private String securityCountry;

	private String securityOrganisation;

	private String secCurrency;

	private Date prevValuationDate;

	private Date valuationDate;

	private Date nextValuationDate;

	private ValuationFrequency valuationFrequency;

	private Amount valOMV;

	private Amount valFSV;

	private double valuationMargin = 1;

	private String valuationType;

	private String valuer;

	public GenericValuationModel() {
	}

	/**
	 * Constructor to accept valuation object, and populate basic information of
	 * the object into this.
	 */
	public GenericValuationModel(IValuation valuation) {
		this.collateralId = valuation.getCollateralID();
		this.valuationDate = valuation.getValuationDate();
		this.prevValuationDate = valuation.getLastEvaluationDate();
		this.secCurrency = valuation.getCurrencyCode();
		this.valOMV = valuation.getCMV();
		this.valFSV = valuation.getFSV();
		this.valuationFrequency = new ValuationFrequency(valuation.getRevaluationFreqUnit(), valuation
				.getRevaluationFreq());
		this.nextValuationDate = valuation.getNextRevaluationDate();
		this.valuer = valuation.getValuerName();
		this.valuationType = valuation.getValuationType();
	}

	/**
	 * @return Returns the collateralId.
	 */
	public long getCollateralId() {
		return collateralId;
	}

	public Date getNextValuationDate() {
		return nextValuationDate;
	}

	public Date getPrevValuationDate() {
		return prevValuationDate;
	}

	/**
	 * @return Returns the secCurrency.
	 */
	public String getSecCurrency() {
		return secCurrency;
	}

	public String getSecId() {
		return secId;
	}

	/**
	 * @return Returns the secSubtype.
	 */
	public String getSecSubtype() {
		return secSubtype;
	}

	/**
	 * @return Returns the secType.
	 */
	public String getSecType() {
		return secType;
	}

	/**
	 * @return Returns the securityCountry.
	 */
	public String getSecurityCountry() {
		return securityCountry;
	}

	/**
	 * @return Returns the security organisation
	 */
	public String getSecurityOrganisation() {
		return securityOrganisation;
	}

	public Amount getValFSV() {

		if (valFSV != null) { // this will only be set for prev valuations
			return valFSV;
		}

		Amount omvAmount = getValOMV();
		if (omvAmount != null) {
			double omv = omvAmount.getAmount();
			return (omv <= 0) ? new Amount(0, omvAmount.getCurrencyCode()) : new Amount(
					(omv * valuationMargin / 100.0), omvAmount.getCurrencyCode());
		}

		return null;
	}

	/**
	 * @return Returns the valOMV.
	 */
	public Amount getValOMV() {
		return valOMV;
	}

	/**
	 * @return Returns the valuationDate.
	 */
	public Date getValuationDate() {
		return valuationDate;
	}

	/**
	 * @return Returns the valuationFrequency.
	 */
	public ValuationFrequency getValuationFrequency() {
		return valuationFrequency;
	}

	/**
	 * @return Returns the valuationMargin.
	 */
	public double getValuationMargin() {
		return valuationMargin;
	}

	public String getValuationType() {
		return valuationType;
	}

	public String getValuer() {
		return valuer;
	}

	/**
	 * @param collateralId The collateralId to set.
	 */
	public void setCollateralId(long collateralId) {
		this.collateralId = collateralId;
	}

	public void setDetailFromCollateral(ICollateral col) {
		this.setCollateralId(col.getCollateralID());
		this.setSecType(col.getCollateralSubType().getTypeCode());
		this.setSecSubtype(col.getCollateralSubType().getSubTypeCode());
		this.setSecurityCountry(col.getCollateralLocation());
		this.setSecCurrency(col.getCurrencyCode());
	}

	public void setNextValuationDate(Date nextValuationDate) {
		this.nextValuationDate = nextValuationDate;
	}

	public void setPrevValuationDate(Date prevValuationDate) {
		this.prevValuationDate = prevValuationDate;
	}

	/**
	 * @param secCurrency The secCurrency to set.
	 */
	public void setSecCurrency(String secCurrency) {
		this.secCurrency = secCurrency;
	}

	public void setSecId(String secId) {
		this.secId = secId;
	}

	/**
	 * @param secSubtype The secSubtype to set.
	 */
	public void setSecSubtype(String secSubtype) {
		this.secSubtype = secSubtype;
	}

	/**
	 * @param secType The secType to set.
	 */
	public void setSecType(String secType) {
		this.secType = secType;
	}

	/**
	 * @param securityCountry The securityCountry to set.
	 */
	public void setSecurityCountry(String securityCountry) {
		this.securityCountry = securityCountry;
	}

	/**
	 * @param securityOrganisation The security organisation to set.
	 */
	public void setSecurityOrganisation(String securityOrganisation) {
		this.securityOrganisation = securityOrganisation;
	}

	public void setValFSV(Amount valFSV) {
		this.valFSV = valFSV;
	}

	/**
	 * @param valOMV The valOMV to set.
	 */
	public void setValOMV(Amount valOMV) {
		this.valOMV = valOMV;
	}

	/**
	 * @param valuationDate The valuationDate to set.
	 */
	public void setValuationDate(Date valuationDate) {
		this.valuationDate = valuationDate;
	}

	/**
	 * @param valuationFrequency The valuationFrequency to set.
	 */
	public void setValuationFrequency(ValuationFrequency valuationFrequency) {
		this.valuationFrequency = valuationFrequency;
	}

	/**
	 * @param valuationMargin The valuationMargin to set.
	 */
	public void setValuationMargin(double valuationMargin) {
		this.valuationMargin = valuationMargin;
	}

	public void setValuationType(String valuationType) {
		this.valuationType = valuationType;
	}

	public void setValuer(String valuer) {
		this.valuer = valuer;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("Valuation Model: [");
		buf.append("CMS Collateral Id=").append(collateralId).append(" ");
		buf.append("LOS Security Id=").append(secId).append(" ");
		buf.append("Security Type=").append(secType).append(" ");
		buf.append("Security Sub Type=").append(secSubtype).append(" ");
		buf.append("Security Country=").append(securityCountry).append(" ");
		buf.append("Security Organisation=").append(securityOrganisation).append(" ");
		buf.append("Security Currency=").append(secCurrency).append(" ");
		buf.append("Previous Valuation Date=").append(prevValuationDate).append(" ");
		buf.append("Valuation Date=").append(valuationDate).append(" ");
		buf.append("Next Valuation Date=").append(nextValuationDate).append(" ");
		buf.append("Valuation Frequency=").append(valuationFrequency).append(" ");
		buf.append("Valuation OMV=").append(valOMV).append(" ");
		buf.append("Valuation FSV=").append(valFSV).append(" ");
		buf.append("Calculated Valuation FSV=").append(getValFSV()).append(" ");
		buf.append("Valuation Margin=").append(valuationMargin).append(" ");
		buf.append("Valuation Type=").append(valuationType).append(" ");
		buf.append("Valuer=").append(valuer);
		buf.append("]");
		return buf.toString();
	}
}
