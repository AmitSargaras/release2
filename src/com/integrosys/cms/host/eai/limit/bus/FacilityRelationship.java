package com.integrosys.cms.host.eai.limit.bus;

import java.io.Serializable;

import com.integrosys.cms.host.eai.StandardCode;

public class FacilityRelationship implements Serializable {

	private static final long serialVersionUID = -7631865118011165220L;

	private long id;

	private long facilityMasterId;

	private long CMSFacilityRelationshipID;

	private Long cmsReferenceId;

	private String CIFId;

	private StandardCode accountRelationship;

	private Double guaranteeAmt;

	private Double guaranteePercentage;

	private Long addressSeqNum;

	private String updateStatusIndicator;

	private String changeIndicator;

	private String customerName;

	private Long mainProfileID;

	private String currencyCode;

	private Double shareHolderPercentage;

	private String receiveMailCode;

	private StandardCode holdMailCode;

	private Integer nameAssociatedWFacOrder;

	private String nameConjunctionPosition;

	private String nameConjunction;

	private Double profitRatio;

	private Double dividendRatio;

	private String status;

	public StandardCode getAccountRelationship() {
		return accountRelationship;
	}

	public Long getAddressSeqNum() {
		return addressSeqNum;
	}

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public Long getCmsReferenceId() {
		return this.cmsReferenceId;
	}

	public String getCIFId() {
		return CIFId;
	}

	public long getCMSFacilityRelationshipID() {
		return CMSFacilityRelationshipID;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public Double getDividendRatio() {
		return dividendRatio;
	}

	public long getFacilityMasterId() {
		return facilityMasterId;
	}

	public Double getGuaranteeAmt() {
		return guaranteeAmt;
	}

	public Double getGuaranteePercentage() {
		return guaranteePercentage;
	}

	public StandardCode getHoldMailCode() {
		return holdMailCode;
	}

	public long getId() {
		return id;
	}

	public Long getMainProfileID() {
		return mainProfileID;
	}

	public Integer getNameAssociatedWFacOrder() {
		return nameAssociatedWFacOrder;
	}

	public String getNameConjunction() {
		return nameConjunction;
	}

	public String getNameConjunctionPosition() {
		return nameConjunctionPosition;
	}

	public Double getProfitRatio() {
		return profitRatio;
	}

	public String getReceiveMailCode() {
		return receiveMailCode;
	}

	public Double getShareHolderPercentage() {
		return shareHolderPercentage;
	}

	public String getStatus() {
		return status;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public void setAccountRelationship(StandardCode accountRelationship) {
		this.accountRelationship = accountRelationship;
	}

	public void setAddressSeqNum(Long addressSeqNum) {
		this.addressSeqNum = addressSeqNum;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public void setCmsReferenceId(Long cmsReferenceId) {
		this.cmsReferenceId = cmsReferenceId;
	}

	public void setCIFId(String id) {
		CIFId = id;
	}

	public void setCMSFacilityRelationshipID(long facilityRelationshipID) {
		CMSFacilityRelationshipID = facilityRelationshipID;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setDividendRatio(Double dividendRatio) {
		this.dividendRatio = dividendRatio;
	}

	public void setFacilityMasterId(long facilityMasterId) {
		this.facilityMasterId = facilityMasterId;
	}

	public void setGuaranteeAmt(Double guaranteeAmt) {
		this.guaranteeAmt = guaranteeAmt;
	}

	public void setGuaranteePercentage(Double guaranteePercentage) {
		this.guaranteePercentage = guaranteePercentage;
	}

	public void setHoldMailCode(StandardCode holdMailCode) {
		this.holdMailCode = holdMailCode;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setMainProfileID(Long mainProfileID) {
		this.mainProfileID = mainProfileID;
	}

	public void setNameAssociatedWFacOrder(Integer nameAssociatedWFacOrder) {
		this.nameAssociatedWFacOrder = nameAssociatedWFacOrder;
	}

	public void setNameConjunction(String nameConjunction) {
		this.nameConjunction = nameConjunction;
	}

	public void setNameConjunctionPosition(String nameConjunctionPosition) {
		this.nameConjunctionPosition = nameConjunctionPosition;
	}

	public void setProfitRatio(Double profitRatio) {
		this.profitRatio = profitRatio;
	}

	public void setReceiveMailCode(String receiveMailCode) {
		this.receiveMailCode = receiveMailCode;
	}

	public void setShareHolderPercentage(Double shareHolderPercentage) {
		this.shareHolderPercentage = shareHolderPercentage;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CIFId == null) ? 0 : CIFId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		FacilityRelationship other = (FacilityRelationship) obj;
		if (CIFId == null) {
			if (other.CIFId != null) {
				return false;
			}
		}
		else if (!CIFId.equals(other.CIFId)) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		}
		else if (!status.equals(other.status)) {
			return false;
		}
		return true;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("FacilityRelationship [");
		buf.append("CIFId=");
		buf.append(CIFId);
		buf.append(", facilityMasterId=");
		buf.append(facilityMasterId);
		buf.append(", customerName=");
		buf.append(customerName);
		buf.append(", accountRelationship=");
		buf.append(accountRelationship);
		buf.append(", addressSeqNum=");
		buf.append(addressSeqNum);
		buf.append(", guaranteeAmt=");
		buf.append(guaranteeAmt);
		buf.append(", currencyCode=");
		buf.append(currencyCode);
		buf.append(", dividendRatio=");
		buf.append(dividendRatio);
		buf.append(", guaranteePercentage=");
		buf.append(guaranteePercentage);
		buf.append(", holdMailCode=");
		buf.append(holdMailCode);
		buf.append(", nameAssociatedWFacOrder=");
		buf.append(nameAssociatedWFacOrder);
		buf.append(", nameConjunction=");
		buf.append(nameConjunction);
		buf.append(", nameConjunctionPosition=");
		buf.append(nameConjunctionPosition);
		buf.append(", profitRatio=");
		buf.append(profitRatio);
		buf.append(", receiveMailCode=");
		buf.append(receiveMailCode);
		buf.append(", shareHolderPercentage=");
		buf.append(shareHolderPercentage);
		buf.append(", status=");
		buf.append(status);
		buf.append("]");
		return buf.toString();
	}

}
