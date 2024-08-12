package com.integrosys.cms.host.eai.security.bus;

import java.util.Date;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * Domain Object represent a valuation for a collateral
 * 
 * @author Zhai Jian
 * @author Chong Jun Yong
 */
public class SecurityValuation implements java.io.Serializable {

	private static final long serialVersionUID = -8983131276498932098L;

	private String LOSSecurityId;

	private long CMSSecurityId;

	private long valuationId;

	private StandardCode valuer;

	private Date valuationDate;

	private String valuationCurrency;

	private Double cMV;

	private Double fSV;

	private Double reservePrice;

	private StandardCode valuationType;

	private String sourceType;

	private Date receivedDate;

	private Integer revaluationFreq;

	private String revaluationFreqUnit;

	private String changeIndicator;

	private String updateStatusIndicator;

	private Long LOSValuationId;

	private String sourceId;

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public long getCMSSecurityId() {
		return CMSSecurityId;
	}

	public Double getCMV() {
		return cMV;
	}

	public Double getFSV() {
		return fSV;
	}

	public Date getJDOValuationDate() {
		return valuationDate;
	}

	public String getLOSSecurityId() {
		return LOSSecurityId;
	}

	public Long getLOSValuationId() {
		return LOSValuationId;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public Double getReservePrice() {
		return reservePrice;
	}

	public Integer getRevaluationFreq() {
		return revaluationFreq;
	}

	public String getRevaluationFreqUnit() {
		return revaluationFreqUnit;
	}

	public String getSourceId() {
		return sourceId;
	}

	public String getSourceType() {
		return sourceType;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public String getValuationCurrency() {
		return valuationCurrency;
	}

	public String getValuationDate() {
		return MessageDate.getInstance().getString(valuationDate);
	}

	public long getValuationId() {
		return valuationId;
	}

	public StandardCode getValuationType() {
		return valuationType;
	}

	public StandardCode getValuer() {
		return valuer;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public void setCMSSecurityId(long securityId) {
		CMSSecurityId = securityId;
	}

	public void setCMV(Double cmv) {
		cMV = cmv;
	}

	public void setFSV(Double fsv) {
		fSV = fsv;
	}

	public void setJDOValuationDate(Date valuationDate) {
		this.valuationDate = valuationDate;
	}

	public void setLOSSecurityId(String securityId) {
		LOSSecurityId = securityId;
	}

	public void setLOSValuationId(Long valuationId) {
		LOSValuationId = valuationId;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public void setReservePrice(Double reservePrice) {
		this.reservePrice = reservePrice;
	}

	public void setRevaluationFreq(Integer revaluationFreq) {
		this.revaluationFreq = revaluationFreq;
	}

	public void setRevaluationFreqUnit(String revaluationFreqUnit) {
		this.revaluationFreqUnit = revaluationFreqUnit;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public void setValuationCurrency(String valuationCurrency) {
		this.valuationCurrency = valuationCurrency;
	}

	public void setValuationDate(String valuationDate) {
		this.valuationDate = MessageDate.getInstance().getDate(valuationDate);
	}

	public void setValuationId(long valuationId) {
		this.valuationId = valuationId;
	}

	public void setValuationType(StandardCode valuationType) {
		this.valuationType = valuationType;
	}

	public void setValuer(StandardCode valuer) {
		this.valuer = valuer;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("Security Valuation");
		buf.append("@").append(System.identityHashCode(this));
		buf.append(" CMS Collateral Id [").append(CMSSecurityId).append("], ");
		buf.append("Valuer [").append(valuer).append("], ");
		buf.append("Currency [").append(valuationCurrency).append("], ");
		buf.append("CMV [").append(cMV).append("], ");
		buf.append("FSV [").append(fSV).append("], ");
		buf.append("Valuation Type [").append(valuationType).append("], ");
		buf.append("Source Type [").append(sourceType).append("], ");
		buf.append("Source Id [").append(sourceId).append("]");

		return buf.toString();
	}
}
