/*
 * Copyright Integro Technologies Pte Ltd
 * 
 */
package com.integrosys.cms.host.eai.security;

import java.util.Vector;

import com.integrosys.cms.host.eai.EAIBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.asset.AssetSecurity;
import com.integrosys.cms.host.eai.security.bus.cash.CashSecurity;
import com.integrosys.cms.host.eai.security.bus.clean.CleanSecurity;
import com.integrosys.cms.host.eai.security.bus.document.DocumentSecurity;
import com.integrosys.cms.host.eai.security.bus.guarantee.GuaranteeSecurity;
import com.integrosys.cms.host.eai.security.bus.insurance.InsuranceSecurity;
import com.integrosys.cms.host.eai.security.bus.marketable.MarketableSecurity;
import com.integrosys.cms.host.eai.security.bus.others.OthersSecurity;
import com.integrosys.cms.host.eai.security.bus.property.PropertySecurity;

/**
 * Message Body to hold all the information of a collateral, such as general
 * info, detailed info, valuations, pledgors, insurance policies.
 * 
 * @author lyng
 * @author Chong Jun Yong
 * @since 2003/10/23
 */
public class SecurityMessageBody extends EAIBody implements java.io.Serializable {

	private static final long serialVersionUID = -890719566485454433L;

	private ApprovedSecurity securityDetail;

	private OthersSecurity otherDetail;

	private AssetSecurity assetDetail;

	private PropertySecurity propertyDetail;

	private GuaranteeSecurity guaranteeDetail;

	private DocumentSecurity documentationDetail;

	private InsuranceSecurity insuranceDetail;

	private MarketableSecurity marketableSecDetail;

	private CashSecurity cashDetail;

	private CleanSecurity cleanDetail;

	private Vector valuationDetail;

	private Vector insurancePolicyDetail;

	private Vector depositDetail;

	private Vector chequeDetail;

	private Vector creditDefaultSwapsDetail;

	private Vector portfolioItems;

	private Vector pledgor;

	private Vector stagePledgor;

	private boolean isVariation = false;

	/**
	 * Default constructor.
	 */
	public SecurityMessageBody() {
		super();
	}

	public AssetSecurity getAssetDetail() {
		return assetDetail;
	}

	public CashSecurity getCashDetail() {
		return cashDetail;
	}

	public Vector getChequeDetail() {
		return chequeDetail;
	}

	public CleanSecurity getCleanDetail() {
		return cleanDetail;
	}

	public Vector getCreditDefaultSwapsDetail() {
		return creditDefaultSwapsDetail;
	}

	public Vector getDepositDetail() {
		return depositDetail;
	}

	public DocumentSecurity getDocumentationDetail() {
		return documentationDetail;
	}

	public GuaranteeSecurity getGuaranteeDetail() {
		return guaranteeDetail;
	}

	public InsuranceSecurity getInsuranceDetail() {
		return insuranceDetail;
	}

	public Vector getInsurancePolicyDetail() {
		return insurancePolicyDetail;
	}

	public MarketableSecurity getMarketableSecDetail() {
		return marketableSecDetail;
	}

	public OthersSecurity getOtherDetail() {
		return otherDetail;
	}

	public Vector getPledgor() {
		return pledgor;
	}

	public Vector getPortfolioItems() {
		return portfolioItems;
	}

	public PropertySecurity getPropertyDetail() {
		return propertyDetail;
	}

	/**
	 * Get approved security contained in this message.
	 * 
	 * @return a list of ApprovedSecurity objects, the value of field
	 *         'approvedSecurity'
	 */
	public ApprovedSecurity getSecurityDetail() {
		return this.securityDetail;
	}

	public Vector getStagePledgor() {
		return stagePledgor;
	}

	public Vector getValuationDetail() {
		return valuationDetail;
	}

	public boolean isVariation() {
		return isVariation;
	}

	public void setAssetDetail(AssetSecurity assetDetail) {
		this.assetDetail = assetDetail;
	}

	public void setCashDetail(CashSecurity cashDetail) {
		this.cashDetail = cashDetail;
	}

	public void setChequeDetail(Vector chequeDetail) {
		this.chequeDetail = chequeDetail;
	}

	public void setCleanDetail(CleanSecurity cleanDetail) {
		this.cleanDetail = cleanDetail;
	}

	public void setCreditDefaultSwapsDetail(Vector creditDefaultSwapsDetail) {
		this.creditDefaultSwapsDetail = creditDefaultSwapsDetail;
	}

	public void setDepositDetail(Vector depositDetail) {
		this.depositDetail = depositDetail;
	}

	public void setDocumentationDetail(DocumentSecurity documentationDetail) {
		this.documentationDetail = documentationDetail;
	}

	public void setGuaranteeDetail(GuaranteeSecurity guaranteeDetail) {
		this.guaranteeDetail = guaranteeDetail;
	}

	public void setInsuranceDetail(InsuranceSecurity insuranceDetail) {
		this.insuranceDetail = insuranceDetail;
	}

	public void setInsurancePolicyDetail(Vector insurancePolicyDetail) {
		this.insurancePolicyDetail = insurancePolicyDetail;
	}

	public void setMarketableSecDetail(MarketableSecurity marketableSecDetail) {
		this.marketableSecDetail = marketableSecDetail;
	}

	public void setOtherDetail(OthersSecurity otherDetail) {
		this.otherDetail = otherDetail;
	}

	public void setPledgor(Vector pledgor) {
		this.pledgor = pledgor;
	}

	public void setPortfolioItems(Vector portfolioItems) {
		this.portfolioItems = portfolioItems;
	}

	public void setPropertyDetail(PropertySecurity propertyDetail) {
		this.propertyDetail = propertyDetail;
	}

	/**
	 * Set the value of field 'approvedSecurity' to this message.
	 * 
	 * @param approvedSecurity a list of ApprovedSecurity, the value of field
	 *        'approvedSecurity'
	 */
	public void setSecurityDetail(ApprovedSecurity approvedSecurity) {
		this.securityDetail = approvedSecurity;
	}

	public void setStagePledgor(Vector stagePledgor) {
		this.stagePledgor = stagePledgor;
	}

	public void setValuationDetail(Vector valuationDetail) {
		this.valuationDetail = valuationDetail;
	}

	public void setVariation(boolean isVariation) {
		this.isVariation = isVariation;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("SecurityMessageBody [");
		buf.append("securityDetail=");
		buf.append(securityDetail);
		buf.append(", assetDetail=");
		buf.append(assetDetail);
		buf.append(", chequeDetail=");
		buf.append(chequeDetail);
		buf.append(", cashDetail=");
		buf.append(cashDetail);
		buf.append(", depositDetail=");
		buf.append(depositDetail);
		buf.append(", cleanDetail=");
		buf.append(cleanDetail);
		buf.append(", insuranceDetail=");
		buf.append(insuranceDetail);
		buf.append(", creditDefaultSwapsDetail=");
		buf.append(creditDefaultSwapsDetail);
		buf.append(", documentationDetail=");
		buf.append(documentationDetail);
		buf.append(", guaranteeDetail=");
		buf.append(guaranteeDetail);
		buf.append(", marketableSecDetail=");
		buf.append(marketableSecDetail);
		buf.append(", portfolioItems=");
		buf.append(portfolioItems);
		buf.append(", otherDetail=");
		buf.append(otherDetail);
		buf.append(", propertyDetail=");
		buf.append(propertyDetail);
		buf.append(", pledgor=");
		buf.append(pledgor);
		buf.append(", insurancePolicyDetail=");
		buf.append(insurancePolicyDetail);
		buf.append(", valuationDetail=");
		buf.append(valuationDetail);
		buf.append("]");
		return buf.toString();
	}

}
