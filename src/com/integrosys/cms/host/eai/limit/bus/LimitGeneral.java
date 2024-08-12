package com.integrosys.cms.host.eai.limit.bus;

import java.io.Serializable;

import com.integrosys.cms.host.eai.OriginatingBookingLocation;
import com.integrosys.cms.host.eai.StandardCode;

/**
 * An entity represents the basic information of a <tt>Limits</tt>, contain the
 * most important value, such as approved amount, booking location, limit id.
 * 
 * @author Thurein
 * @author Chong Jun Yong
 * @since 20-Nov-2008
 * 
 */
public class LimitGeneral implements Serializable {

	private static final long serialVersionUID = -6888775405724185021L;

	private long cmsId;

	private long cmsLimitProfileId;

	private String CIFId;

	private long subProfileId;

	private String LOSAANumber;

	private String hostAANumber;

	private String losLimitId;

	private String hostLimitId;

	private long CMSLimitId;
	
	private String acfNo;

	private Integer innerLimitOrdering;

	private String LOSouterLimitId;

	private Long cmsOuterLimitId;
	
	private Long omnibusEnvelopeId;
	
	private String omnibusEnvelopeName;

	private OriginatingBookingLocation originatingBookingLocation;

	private StandardCode productType;

	private StandardCode facilityType;

	private String accountType;

	private String limitCurrency;

	private Double approvedLimit;

	private Double drawingLimit;

	private Long limitTenor;

	private StandardCode limitTenorBasis;

	private String updateStatusIndicator;

	private String changeIndicator;

	private String status;

	private String isExist;

	private String sourceId;

	private StandardCode limitType;

	private Double interestRate;

	public String getAccountType() {
		return accountType;
	}

	public String getAcfNo() {
		return acfNo;
	}

	public Double getApprovedLimit() {
		return approvedLimit;
	}

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public String getCIFId() {
		return CIFId;
	}

	public long getCmsId() {
		return cmsId;
	}

	public long getCMSLimitId() {
		return CMSLimitId;
	}

	public long getCmsLimitProfileId() {
		return cmsLimitProfileId;
	}

	public Long getCMSOuterLimitId() {
		return cmsOuterLimitId;
	}

	public Double getDrawingLimit() {
		return drawingLimit;
	}

	public StandardCode getFacilityType() {
		return facilityType;
	}

	public String getHostAANumber() {
		return hostAANumber;
	}

	public Integer getInnerLimitOrdering() {
		return innerLimitOrdering;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public String getIsExist() {
		return isExist;
	}

	public String getLimitCurrency() {
		return limitCurrency;
	}

	public String getLimitId() {
		return this.hostLimitId;
	}

	public Long getLimitTenor() {
		return limitTenor;
	}

	public StandardCode getLimitTenorBasis() {
		return limitTenorBasis;
	}

	public StandardCode getLimitType() {
		return limitType;
	}

	public String getLOSAANumber() {
		return LOSAANumber;
	}

	public String getLOSLimitId() {
		return losLimitId;
	}

	public String getLOSOuterLimitId() {
		return LOSouterLimitId;
	}

	public Long getOmnibusEnvelopeId() {
		return omnibusEnvelopeId;
	}

	public String getOmnibusEnvelopeName() {
		return omnibusEnvelopeName;
	}

	public OriginatingBookingLocation getOriginatingBookingLocation() {
		return originatingBookingLocation;
	}

	public StandardCode getProductType() {
		return productType;
	}

	public String getSourceId() {
		return sourceId;
	}

	public String getStatus() {
		return status;
	}

	public long getSubProfileId() {
		return subProfileId;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public void setAcfNo(String acfNo) {
		this.acfNo = acfNo;
	}

	public void setApprovedLimit(Double approvedLimit) {
		this.approvedLimit = approvedLimit;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public void setCIFId(String CIFId) {
		this.CIFId = CIFId;
	}

	public void setCmsId(long cmsId) {
		this.cmsId = cmsId;
	}

	public void setCMSLimitId(long limitId) {
		CMSLimitId = limitId;
	}

	public void setCmsLimitProfileId(long cmsLimitProfileId) {
		this.cmsLimitProfileId = cmsLimitProfileId;
	}

	public void setCMSOuterLimitId(Long CMSouterLimitId) {
		this.cmsOuterLimitId = CMSouterLimitId;
	}

	public void setDrawingLimit(Double drawingLimit) {
		this.drawingLimit = drawingLimit;
	}

	public void setFacilityType(StandardCode facilityType) {
		this.facilityType = facilityType;
	}

	public void setHostAANumber(String hostAANumber) {
		this.hostAANumber = hostAANumber;
	}

	public void setInnerLimitOrdering(Integer innerLimitOrdering) {
		this.innerLimitOrdering = innerLimitOrdering;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public void setIsExist(String isExist) {
		this.isExist = isExist;
	}

	public void setLimitCurrency(String limitCurrency) {
		this.limitCurrency = limitCurrency;
	}

	public void setLimitId(String hostLimitId) {
		this.hostLimitId = hostLimitId;
	}

	public void setLimitTenor(Long limitTenor) {
		this.limitTenor = limitTenor;
	}

	public void setLimitTenorBasis(StandardCode limitTenorBasis) {
		this.limitTenorBasis = limitTenorBasis;
	}

	public void setLimitType(StandardCode limitType) {
		this.limitType = limitType;
	}

	public void setLOSAANumber(String LOSAANumber) {
		this.LOSAANumber = LOSAANumber;
	}

	public void setLOSLimitId(String losLimitId) {
		this.losLimitId = losLimitId;
	}

	public void setLOSOuterLimitId(String LOSouterLimitId) {
		this.LOSouterLimitId = LOSouterLimitId;
	}

	public void setOmnibusEnvelopeId(Long omnibusEnvelopeId) {
		this.omnibusEnvelopeId = omnibusEnvelopeId;
	}

	public void setOmnibusEnvelopeName(String omnibusEnvelopeName) {
		this.omnibusEnvelopeName = omnibusEnvelopeName;
	}

	public void setOriginatingBookingLocation(OriginatingBookingLocation originatingBookingLocation) {
		this.originatingBookingLocation = originatingBookingLocation;
	}

	public void setProductType(StandardCode productType) {
		this.productType = productType;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSubProfileId(long subProfileId) {
		this.subProfileId = subProfileId;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("LimitGeneral [");
		buf.append("losLimitId=");
		buf.append(losLimitId);
		buf.append(", CIFId=");
		buf.append(CIFId);
		buf.append(", LOSAANumber=");
		buf.append(LOSAANumber);
		buf.append(", hostAANumber=");
		buf.append(hostAANumber);
		buf.append(", approvedLimit=");
		buf.append(approvedLimit);
		buf.append(", facilityType=");
		buf.append(facilityType);
		buf.append(", interestRate=");
		buf.append(interestRate);
		buf.append(", limitTenor=");
		buf.append(limitTenor);
		buf.append(", limitTenorBasis=");
		buf.append(limitTenorBasis);
		buf.append(", productType=");
		buf.append(productType);
		buf.append(", originatingBookingLocation=");
		buf.append(originatingBookingLocation);
		buf.append("]");
		return buf.toString();
	}
}