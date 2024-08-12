package com.integrosys.cms.ui.limit.facility.relationship;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainForm;

public class RelationshipForm extends FacilityMainForm {

	private String LEReference;// CIF No.

	private String legalName;// Customer Name

	private String accountRelationshipEntryCode;// Account Relationship

	private String shareHolderPercentage; // Share Holder Percentage

	private String hostAddressSequenceNumber; // Address Sequence Number

	private String receiveMailCode = "Y"; // Receive Mail Code

	private String holdMailCodeEntryCode; // Hold Mail Code

	private String nameAssociateWithFacilityOrder;// Name Associated With

	// Facility Order

	private String nameConjunctionPosition;// Name Conjunction Position

	private String nameConjunction;// Name Conjunction

	private String profitRatio;// Profit Ratio

	private String dividendRatio;// Dividend Ratio

	private String guaranteeAmount;// Guarantee Amount

	private String guaranteePercentage;// Guarantee Percentage

	private String guaranteeFlag; // Radio Button to select between Guarantee

	private String issuedCountry;

	private String searchButton;

	private String idType;

	private String idNo;

	private String selectedId;

    //Added By KLYong: Search Facility Relationship
    private String[] cifNo;

    private String[] pledgorName;

    // Amount or Guarantee Percentage

	public static final String MAPPER = "com.integrosys.cms.ui.limit.facility.relationship.RelationshipMapper";

	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER },
				{ "customerSearchCriteria", "com.integrosys.cms.ui.limit.facility.relationship.CustomerSearchMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}

    public String[] getCifNo() {
        return cifNo;
    }

    public void setCifNo(String[] cifNo) {
        this.cifNo = cifNo;
    }

    public String[] getPledgorName() {
        return pledgorName;
    }

    public void setPledgorName(String[] pledgorName) {
        this.pledgorName = pledgorName;
    }

    /**
	 * @return the lEReference
	 */
	public String getLEReference() {
		return LEReference;
	}

	/**
	 * @param reference the lEReference to set
	 */
	public void setLEReference(String reference) {
		LEReference = reference;
	}

	/**
	 * @return the legalName
	 */
	public String getLegalName() {
		return legalName;
	}

	/**
	 * @param legalName the legalName to set
	 */
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	/**
	 * @return the accountRelationshipEntryCode
	 */
	public String getAccountRelationshipEntryCode() {
		return accountRelationshipEntryCode;
	}

	/**
	 * @param accountRelationshipEntryCode the accountRelationshipEntryCode to
	 *        set
	 */
	public void setAccountRelationshipEntryCode(String accountRelationshipEntryCode) {
		this.accountRelationshipEntryCode = accountRelationshipEntryCode;
	}

	/**
	 * @return the shareHolderPercentage
	 */
	public String getShareHolderPercentage() {
		return shareHolderPercentage;
	}

	/**
	 * @param shareHolderPercentage the shareHolderPercentage to set
	 */
	public void setShareHolderPercentage(String shareHolderPercentage) {
		this.shareHolderPercentage = shareHolderPercentage;
	}

	/**
	 * @return the hostAddressSequenceNumber
	 */
	public String getHostAddressSequenceNumber() {
		return hostAddressSequenceNumber;
	}

	/**
	 * @param hostAddressSequenceNumber the hostAddressSequenceNumber to set
	 */
	public void setHostAddressSequenceNumber(String hostAddressSequenceNumber) {
		this.hostAddressSequenceNumber = hostAddressSequenceNumber;
	}

	/**
	 * @return the receiveMailCode
	 */
	public String getReceiveMailCode() {
		return receiveMailCode;
	}

	/**
	 * @param receiveMailCode the receiveMailCode to set
	 */
	public void setReceiveMailCode(String receiveMailCode) {
		this.receiveMailCode = receiveMailCode;
	}

	/**
	 * @return the holdMailCodeEntryCode
	 */
	public String getHoldMailCodeEntryCode() {
		return holdMailCodeEntryCode;
	}

	/**
	 * @param holdMailCodeEntryCode the holdMailCodeEntryCode to set
	 */
	public void setHoldMailCodeEntryCode(String holdMailCodeEntryCode) {
		this.holdMailCodeEntryCode = holdMailCodeEntryCode;
	}

	/**
	 * @return the nameAssociateWithFacilityOrder
	 */
	public String getNameAssociateWithFacilityOrder() {
		return nameAssociateWithFacilityOrder;
	}

	/**
	 * @param nameAssociateWithFacilityOrder the nameAssociateWithFacilityOrder
	 *        to set
	 */
	public void setNameAssociateWithFacilityOrder(String nameAssociateWithFacilityOrder) {
		this.nameAssociateWithFacilityOrder = nameAssociateWithFacilityOrder;
	}

	/**
	 * @return the nameConjunctionPosition
	 */
	public String getNameConjunctionPosition() {
		return nameConjunctionPosition;
	}

	/**
	 * @param nameConjunctionPosition the nameConjunctionPosition to set
	 */
	public void setNameConjunctionPosition(String nameConjunctionPosition) {
		this.nameConjunctionPosition = nameConjunctionPosition;
	}

	/**
	 * @return the nameConjunction
	 */
	public String getNameConjunction() {
		return nameConjunction;
	}

	/**
	 * @param nameConjunction the nameConjunction to set
	 */
	public void setNameConjunction(String nameConjunction) {
		this.nameConjunction = nameConjunction;
	}

	/**
	 * @return the profitRatio
	 */
	public String getProfitRatio() {
		return profitRatio;
	}

	/**
	 * @param profitRatio the profitRatio to set
	 */
	public void setProfitRatio(String profitRatio) {
		this.profitRatio = profitRatio;
	}

	/**
	 * @return the dividendRatio
	 */
	public String getDividendRatio() {
		return dividendRatio;
	}

	/**
	 * @param dividendRatio the dividendRatio to set
	 */
	public void setDividendRatio(String dividendRatio) {
		this.dividendRatio = dividendRatio;
	}

	/**
	 * @return the guaranteeAmount
	 */
	public String getGuaranteeAmount() {
		return guaranteeAmount;
	}

	/**
	 * @param guaranteeAmount the guaranteeAmount to set
	 */
	public void setGuaranteeAmount(String guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}

	/**
	 * @return the guaranteePercentage
	 */
	public String getGuaranteePercentage() {
		return guaranteePercentage;
	}

	/**
	 * @param guaranteePercentage the guaranteePercentage to set
	 */
	public void setGuaranteePercentage(String guaranteePercentage) {
		this.guaranteePercentage = guaranteePercentage;
	}

	/**
	 * @return the guaranteeFlag
	 */
	public String getGuaranteeFlag() {
		return guaranteeFlag;
	}

	/**
	 * @param guaranteeFlag the guaranteeFlag to set
	 */
	public void setGuaranteeFlag(String guaranteeFlag) {
		this.guaranteeFlag = guaranteeFlag;
	}

	/**
	 * @return the issuedCountry
	 */
	public String getIssuedCountry() {
		return issuedCountry;
	}

	/**
	 * @param issuedCountry the issuedCountry to set
	 */
	public void setIssuedCountry(String issuedCountry) {
		this.issuedCountry = issuedCountry;
	}

	/**
	 * @return the searchButton
	 */
	public String getSearchButton() {
		return searchButton;
	}

	/**
	 * @param searchButton the searchButton to set
	 */
	public void setSearchButton(String searchButton) {
		this.searchButton = searchButton;
	}

	/**
	 * @return the idType
	 */
	public String getIdType() {
		return idType;
	}

	/**
	 * @param idType the idType to set
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}

	/**
	 * @return the idNo
	 */
	public String getIdNo() {
		return idNo;
	}

	/**
	 * @param idNo the idNo to set
	 */
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	/**
	 * @return the selectedId
	 */
	public String getSelectedId() {
		return selectedId;
	}

	/**
	 * @param selected the selectedId to set
	 */
	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

}
