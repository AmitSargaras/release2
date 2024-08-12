package com.integrosys.cms.app.limit.bus;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;

public class OBFacilityRelationship implements IFacilityRelationship {
	private long id;

	private long facilityMasterId;

	private Long cmsRefId;

	private String currencyCode;

	private ICMSLegalEntity cmsLegalEntity;

	private String cifNumber;

	private String customerName;

	private String accountRelationshipCategoryCode;

	private String accountRelationshipEntryCode;

	private Character guaranteeTypeIndicator;

	private Amount guaranteeAmount;

	private Double guaranteePercentage;

	private Double shareHolderPercentage;

	private Long hostAddressSequenceNumber;

	private Boolean receiveMailCode;

	private String holdMailCodeCategoryCode;

	private String holdMailCodeEntryCode;

	private Integer nameAssociateWithFacilityOrder;

	private Character nameConjunctionPosition;

	private String nameConjunction;

	private Double profitRatio;

	private Double dividendRatio;

	private String status;

	/**
	 * @return the accountRelationshipCategoryCode
	 */
	public String getAccountRelationshipCategoryCode() {
		return accountRelationshipCategoryCode;
	}

	/**
	 * @return the accountRelationshipEntryCode
	 */
	public String getAccountRelationshipEntryCode() {
		return accountRelationshipEntryCode;
	}

	/**
	 * @return the cifNumber
	 */
	public String getCifNumber() {
		return cifNumber;
	}

	/**
	 * @return the cmsLegalEntity
	 */
	public ICMSLegalEntity getCmsLegalEntity() {
		return cmsLegalEntity;
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @return the dividendRatio
	 */
	public Double getDividendRatio() {
		return dividendRatio;
	}

	/**
	 * @return the facilityMasterId
	 */
	public long getFacilityMasterId() {
		return facilityMasterId;
	}

	/**
	 * @return the guaranteeAmount
	 */
	public Amount getGuaranteeAmount() {
		return guaranteeAmount;
	}

	/**
	 * @return the guaranteePercentage
	 */
	public Double getGuaranteePercentage() {
		return guaranteePercentage;
	}

	/**
	 * @return the guaranteeTypeIndicator
	 */
	public Character getGuaranteeTypeIndicator() {
		return guaranteeTypeIndicator;
	}

	/**
	 * @return the holdMailCodeCategoryCode
	 */
	public String getHoldMailCodeCategoryCode() {
		return holdMailCodeCategoryCode;
	}

	/**
	 * @return the holdMailCodeEntryCode
	 */
	public String getHoldMailCodeEntryCode() {
		return holdMailCodeEntryCode;
	}

	/**
	 * @return the hostAddressSequenceNumber
	 */
	public Long getHostAddressSequenceNumber() {
		return hostAddressSequenceNumber;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the nameAssociateWithFacilityOrder
	 */
	public Integer getNameAssociateWithFacilityOrder() {
		return nameAssociateWithFacilityOrder;
	}

	/**
	 * @return the nameConjunction
	 */
	public String getNameConjunction() {
		return nameConjunction;
	}

	/**
	 * @return the nameConjunctionPosition
	 */
	public Character getNameConjunctionPosition() {
		return nameConjunctionPosition;
	}

	/**
	 * @return the profitRatio
	 */
	public Double getProfitRatio() {
		return profitRatio;
	}

	/**
	 * @return the shareHolderPercentage
	 */
	public Double getShareHolderPercentage() {
		return shareHolderPercentage;
	}

	/**
	 * @return the receiveMailCode
	 */
	public Boolean getReceiveMailCode() {
		return receiveMailCode;
	}

	/**
	 * @param accountRelationshipCategoryCode the
	 *        accountRelationshipCategoryCode to set
	 */
	public void setAccountRelationshipCategoryCode(String accountRelationshipCategoryCode) {
		this.accountRelationshipCategoryCode = accountRelationshipCategoryCode;
	}

	/**
	 * @param accountRelationshipEntryCode the accountRelationshipEntryCode to
	 *        set
	 */
	public void setAccountRelationshipEntryCode(String accountRelationshipEntryCode) {
		this.accountRelationshipEntryCode = accountRelationshipEntryCode;
	}

	/**
	 * @param cifNumber the cifNumber to set
	 */
	public void setCifNumber(String cifNumber) {
		this.cifNumber = cifNumber;
	}

	/**
	 * @param cmsLegalEntity the cmsLegalEntity to set
	 */
	public void setCmsLegalEntity(ICMSLegalEntity cmsLegalEntity) {
		this.cmsLegalEntity = cmsLegalEntity;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @param dividendRatio the dividendRatio to set
	 */
	public void setDividendRatio(Double dividendRatio) {
		this.dividendRatio = dividendRatio;
	}

	/**
	 * @param facilityMasterId the facilityMasterId to set
	 */
	public void setFacilityMasterId(long facilityMasterId) {
		this.facilityMasterId = facilityMasterId;
	}

	/**
	 * @param guaranteeAmount the guaranteeAmount to set
	 */
	public void setGuaranteeAmount(Amount guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}

	/**
	 * @param guaranteePercentage the guaranteePercentage to set
	 */
	public void setGuaranteePercentage(Double guaranteePercentage) {
		this.guaranteePercentage = guaranteePercentage;
	}

	/**
	 * @param guaranteeTypeIndicator the guaranteeTypeIndicator to set
	 */
	public void setGuaranteeTypeIndicator(Character guaranteeTypeIndicator) {
		this.guaranteeTypeIndicator = guaranteeTypeIndicator;
	}

	/**
	 * @param holdMailCodeCategoryCode the holdMailCodeCategoryCode to set
	 */
	public void setHoldMailCodeCategoryCode(String holdMailCodeCategoryCode) {
		this.holdMailCodeCategoryCode = holdMailCodeCategoryCode;
	}

	/**
	 * @param holdMailCodeEntryCode the holdMailCodeEntryCode to set
	 */
	public void setHoldMailCodeEntryCode(String holdMailCodeEntryCode) {
		this.holdMailCodeEntryCode = holdMailCodeEntryCode;
	}

	/**
	 * @param hostAddressSequenceNumber the hostAddressSequenceNumber to set
	 */
	public void setHostAddressSequenceNumber(Long hostAddressSequenceNumber) {
		this.hostAddressSequenceNumber = hostAddressSequenceNumber;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @param nameAssociateWithFacilityOrder the nameAssociateWithFacilityOrder
	 *        to set
	 */
	public void setNameAssociateWithFacilityOrder(Integer nameAssociateWithFacilityOrder) {
		this.nameAssociateWithFacilityOrder = nameAssociateWithFacilityOrder;
	}

	/**
	 * @param nameConjunction the nameConjunction to set
	 */
	public void setNameConjunction(String nameConjunction) {
		this.nameConjunction = nameConjunction;
	}

	/**
	 * @param nameConjunctionPosition the nameConjunctionPosition to set
	 */
	public void setNameConjunctionPosition(Character nameConjunctionPosition) {
		this.nameConjunctionPosition = nameConjunctionPosition;
	}

	/**
	 * @param profitRatio the profitRatio to set
	 */
	public void setProfitRatio(Double profitRatio) {
		this.profitRatio = profitRatio;
	}

	/**
	 * @param receiveMailCode the receiveMailCode to set
	 */
	public void setReceiveMailCode(Boolean receiveMailCode) {
		this.receiveMailCode = receiveMailCode;
	}

	/**
	 * @param shareHolderPercentage the shareHolderPercentage to set
	 */
	public void setShareHolderPercentage(Double shareHolderPercentage) {
		this.shareHolderPercentage = shareHolderPercentage;
	}

	public Long getCmsRefId() {
		return cmsRefId;
	}

	public void setCmsRefId(Long cmsRefId) {
		this.cmsRefId = cmsRefId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cifNumber == null) ? 0 : cifNumber.hashCode());
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
		OBFacilityRelationship other = (OBFacilityRelationship) obj;
		if (cifNumber == null) {
			if (other.cifNumber != null) {
				return false;
			}
		}
		else if (!cifNumber.equals(other.cifNumber)) {
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
		StringBuffer buf = new StringBuffer();

		buf.append("Facility Relationship").append("@");
		buf.append("accountRelationshipEntryCode [").append(accountRelationshipEntryCode).append("], ");
		buf.append("cifNumber [").append(cifNumber).append("], ");
		buf.append("customerName [").append(customerName).append("], ");
		buf.append("guaranteeAmount [").append(guaranteeAmount).append("], ");
		buf.append("guaranteePercentage [").append(guaranteePercentage).append("], ");
		buf.append("guaranteeTypeIndicator [").append(guaranteeTypeIndicator).append("]");

		return buf.toString();
	}

}
