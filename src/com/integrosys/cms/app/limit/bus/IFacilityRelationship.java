package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;

public interface IFacilityRelationship extends Serializable {

	/**
	 * @return the id
	 */
	public long getId();

	/**
	 * @return the facilityMasterId
	 */
	public long getFacilityMasterId();

	public Long getCmsRefId();

	/**
	 * @return the cifNumber
	 */
	public String getCifNumber();

	/**
	 * @return the customerName
	 */
	public String getCustomerName();

	/**
	 * @return the accountRelationshipCategoryCode
	 */
	public String getAccountRelationshipCategoryCode();

	/**
	 * @return the accountRelationshipEntryCode
	 */
	public String getAccountRelationshipEntryCode();

	/**
	 * @return the guaranteeTypeIndicator
	 */
	public Character getGuaranteeTypeIndicator();

	/**
	 * @return the guaranteeAmount
	 */
	public Amount getGuaranteeAmount();

	/**
	 * @return the guaranteePercentage
	 */
	public Double getGuaranteePercentage();

	/**
	 * @return the shareHolderPercentage
	 */
	public Double getShareHolderPercentage();

	/**
	 * @return the hostAddressSequenceNumber
	 */
	public Long getHostAddressSequenceNumber();

	/**
	 * @return the receiveMailCode
	 */
	public Boolean getReceiveMailCode();

	/**
	 * @return the holdMailCodeCategoryCode
	 */
	public String getHoldMailCodeCategoryCode();

	/**
	 * @return the holdMailCodeEntryCode
	 */
	public String getHoldMailCodeEntryCode();

	/**
	 * @return the nameAssociateWithFacilityOrder
	 */
	public Integer getNameAssociateWithFacilityOrder();

	/**
	 * @return the nameConjunctionPosition
	 */
	public Character getNameConjunctionPosition();

	/**
	 * @return the nameConjunction
	 */
	public String getNameConjunction();

	/**
	 * @return the profitRatio
	 */
	public Double getProfitRatio();

	/**
	 * @return the dividendRatio
	 */
	public Double getDividendRatio();

	/**
	 * @param id the id to set
	 */
	public void setId(long id);

	/**
	 * @param facilityMasterId the facilityMasterId to set
	 */
	public void setFacilityMasterId(long facilityMasterId);

	public void setCmsRefId(Long cmsRefId);

	/**
	 * @param cifNumber the cifNumber to set
	 */
	public void setCifNumber(String cifNumber);

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName);

	/**
	 * @param accountRelationshipCategoryCode the
	 *        accountRelationshipCategoryCode to set
	 */
	public void setAccountRelationshipCategoryCode(String accountRelationshipCategoryCode);

	/**
	 * @param accountRelationshipEntryCode the accountRelationshipEntryCode to
	 *        set
	 */
	public void setAccountRelationshipEntryCode(String accountRelationshipEntryCode);

	/**
	 * To set whether guarantee used is in Amount or Percentage
	 * 
	 * @param guaranteeTypeIndicator the guaranteeTypeIndicator to set
	 */
	public void setGuaranteeTypeIndicator(Character guaranteeTypeIndicator);

	/**
	 * @param guaranteeAmount the guaranteeAmount to set
	 */
	public void setGuaranteeAmount(Amount guaranteeAmount);

	/**
	 * @param guaranteePercentage the guaranteePercentage to set
	 */
	public void setGuaranteePercentage(Double guaranteeAmountPercentage);

	/**
	 * @param shareHolderPercentage the shareHolderPercentage to set
	 */
	public void setShareHolderPercentage(Double shareHolderPercentage);

	/**
	 * @param hostAddressSequenceNumber the hostAddressSequenceNumber to set
	 */
	public void setHostAddressSequenceNumber(Long hostAddressSequenceNumber);

	/**
	 * @param receiveMailCode the receiveMailCode to set
	 */
	public void setReceiveMailCode(Boolean receiveMailCode);

	/**
	 * @param holdMailCodeCategoryCode the holdMailCodeCategoryCode to set
	 */
	public void setHoldMailCodeCategoryCode(String holdMailCodeCategoryCode);

	/**
	 * @param holdMailCodeEntryCode the holdMailCodeEntryCode to set
	 */
	public void setHoldMailCodeEntryCode(String holdMailCodeEntryCode);

	/**
	 * @param nameAssociateWithFacilityOrder the nameAssociateWithFacilityOrder
	 *        to set
	 */
	public void setNameAssociateWithFacilityOrder(Integer nameAssociateWithFacilityOrder);

	/**
	 * @param nameConjunctionPosition the nameConjunctionPosition to set
	 */
	public void setNameConjunctionPosition(Character nameConjunctionPosition);

	/**
	 * @param nameConjunction the nameConjunction to set
	 */
	public void setNameConjunction(String nameConjunction);

	/**
	 * @param profitRatio the profitRatio to set
	 */
	public void setProfitRatio(Double profitRatio);

	/**
	 * @param dividendRatio the dividendRatio to set
	 */
	public void setDividendRatio(Double dividendRatio);

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode();

	/**
	 * @return the cmsLegalEntity
	 */
	public ICMSLegalEntity getCmsLegalEntity();

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode);

	/**
	 * @param cmsLegalEntity the cmsLegalEntity to set
	 */
	public void setCmsLegalEntity(ICMSLegalEntity cmsLegalEntity);

	/**
	 * @return the status
	 */
	public String getStatus();

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status);
}
