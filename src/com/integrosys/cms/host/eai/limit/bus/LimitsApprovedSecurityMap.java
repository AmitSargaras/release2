package com.integrosys.cms.host.eai.limit.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * An entity represent a linkage between a limit/facility and collateral, also
 * contain pledge/draw amount/percentage information.
 * @author marvin
 * @author Chong Jun Yong
 */
public class LimitsApprovedSecurityMap implements java.io.Serializable {

	private static final long serialVersionUID = 6786061192280880145L;

	private long cmsId;

	private long cmsLimitId;

	private long limitProfileId;

	private long cmsSecurityId;

	private long limitsApprovedSecurityMapId;

	private Long cmsRefId;

	private String CIFId;

	private Long subProfileId;

	private String AANumber;

	private String limitId;

	private String securityId;

	private String updateStatusIndicator;

	private String changeIndicator;

	private String customerCategory = ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER;

	private String amtPledgeCcy;

	private Double amtPledge;

	private Double percentPledge;

	private String amtDrawCcy;

	private Double amtDraw;

	private Double percentDraw;

	private String source;

	public String getAANumber() {
		return AANumber;
	}

	public Double getAmtDraw() {
		return amtDraw;
	}

	public String getAmtDrawCcy() {
		return amtDrawCcy;
	}

	public Double getAmtPledge() {
		return amtPledge;
	}

	public String getAmtPledgeCcy() {
		return amtPledgeCcy;
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

	public long getCmsLimitId() {
		return cmsLimitId;
	}

	public Long getCmsRefId() {
		return cmsRefId;
	}

	public long getCmsSecurityId() {
		return cmsSecurityId;
	}

	public String getCustomerCategory() {
		return customerCategory;
	}

	public String getLimitId() {
		return limitId;
	}

	public long getLimitProfileId() {
		return limitProfileId;
	}

	public long getLimitsApprovedSecurityMapId() {
		return limitsApprovedSecurityMapId;
	}

	public Double getPercentDraw() {
		return percentDraw;
	}

	public Double getPercentPledge() {
		return percentPledge;
	}

	public String getSecurityId() {
		return securityId;
	}

	public String getSource() {
		return source;
	}

	public Long getSubProfileId() {
		return subProfileId;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public void setAANumber(String AANumber) {
		this.AANumber = AANumber;
	}

	public void setAmtDraw(Double amtDraw) {
		this.amtDraw = amtDraw;
	}

	public void setAmtDrawCcy(String amtDrawCcy) {
		this.amtDrawCcy = amtDrawCcy;
	}

	public void setAmtPledge(Double amtPledge) {
		this.amtPledge = amtPledge;
	}

	public void setAmtPledgeCcy(String amtPledgeCcy) {
		this.amtPledgeCcy = amtPledgeCcy;
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

	public void setCmsLimitId(long cmsLimitId) {
		this.cmsLimitId = cmsLimitId;
	}

	public void setCmsRefId(Long cmsRefId) {
		this.cmsRefId = cmsRefId;
	}

	public void setCmsSecurityId(long cmsSecurityId) {
		this.cmsSecurityId = cmsSecurityId;
	}

	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
	}

	public void setLimitId(String limitId) {
		this.limitId = limitId;
	}

	public void setLimitProfileId(long limitProfileId) {
		this.limitProfileId = limitProfileId;
	}

	public void setLimitsApprovedSecurityMapId(long limitsApprovedSecurityMapId) {
		this.limitsApprovedSecurityMapId = limitsApprovedSecurityMapId;
	}

	public void setPercentDraw(Double percentDraw) {
		this.percentDraw = percentDraw;
	}

	public void setPercentPledge(Double percentPledge) {
		this.percentPledge = percentPledge;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setSubProfileId(Long subProfileId) {
		this.subProfileId = subProfileId;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("LimitsApprovedSecurityMap [");
		buf.append("CIFId=");
		buf.append(CIFId);
		buf.append(", AANumber=");
		buf.append(AANumber);
		buf.append(", limitProfileId=");
		buf.append(limitProfileId);
		buf.append(", cmsLimitId=");
		buf.append(cmsLimitId);
		buf.append(", cmsSecurityId=");
		buf.append(cmsSecurityId);
		buf.append(", amtDraw=");
		buf.append(amtDraw);
		buf.append(", amtDrawCcy=");
		buf.append(amtDrawCcy);
		buf.append(", percentDraw=");
		buf.append(percentDraw);
		buf.append(", amtPledge=");
		buf.append(amtPledge);
		buf.append(", amtPledgeCcy=");
		buf.append(amtPledgeCcy);
		buf.append(", percentPledge=");
		buf.append(percentPledge);
		buf.append(", source=");
		buf.append(source);
		buf.append("]");
		return buf.toString();
	}

}
