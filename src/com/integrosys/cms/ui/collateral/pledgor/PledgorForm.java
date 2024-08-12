package com.integrosys.cms.ui.collateral.pledgor;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

public class PledgorForm extends TrxContextForm implements Serializable {

	private String customerName;

	private String legalID;

	private String IDType;

	private String idNO;

	private String issuedCountry;

	private String searchButton;

	private String[] relationship;

    private String[] idTypeCode;

    private String[] idType;

    private String[] idNo;

    private String[] cifNo;

	private String[] pledgorName;

	private String[] selected;

	private String[] removed;

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the legalID
	 */
	public String getLegalID() {
		return legalID;
	}

	/**
	 * @param legalID the legalID to set
	 */
	public void setLegalID(String legalID) {
		this.legalID = legalID;
	}

	/**
	 * @return the iDType
	 */
	public String getIDType() {
		return IDType;
	}

	/**
	 * @param type the iDType to set
	 */
	public void setIDType(String type) {
		IDType = type;
	}

	/**
	 * @return the idNO
	 */
	public String getIdNO() {
		return idNO;
	}

	/**
	 * @param idNO the idNO to set
	 */
	public void setIdNO(String idNO) {
		this.idNO = idNO;
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
	 * @return the relationship
	 */
	public String[] getRelationship() {
		return relationship;
	}

	/**
	 * @param relationship the relationship to set
	 */
	public void setRelationship(String[] relationship) {
		this.relationship = relationship;
	}

    public String[] getIdTypeCode() {
        return idTypeCode;
    }

    public void setIdTypeCode(String[] idTypeCode) {
        this.idTypeCode = idTypeCode;
    }

    public String[] getIdType() {
        return idType;
    }

    public void setIdType(String[] idType) {
        this.idType = idType;
    }

    public String[] getIdNo() {
        return idNo;
    }

    public void setIdNo(String[] idNo) {
        this.idNo = idNo;
    }

	/**
	 * @return the cifNo
	 */
	public String[] getCifNo() {
		return cifNo;
	}

	/**
	 * @param cifNo the cifNo to set
	 */
	public void setCifNo(String[] cifNo) {
		this.cifNo = cifNo;
	}

	/**
	 * @return the pledgorName
	 */
	public String[] getPledgorName() {
		return pledgorName;
	}

	/**
	 * @param pledgorName the pledgorName to set
	 */
	public void setPledgorName(String[] pledgorName) {
		this.pledgorName = pledgorName;
	}

	/**
	 * @return the selected
	 */
	public String[] getSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(String[] selected) {
		this.selected = selected;
	}

	/**
	 * @return the removed
	 */
	public String[] getRemoved() {
		return removed;
	}

	/**
	 * @param removed the removed to set
	 */
	public void setRemoved(String[] removed) {
		this.removed = removed;
	}

	public String[][] getMapper() {
		String[][] input = {
				{ "pledgorSearchCriteria", "com.integrosys.cms.ui.collateral.pledgor.PledgorSearchMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "pledgorData", "com.integrosys.cms.ui.collateral.pledgor.PledgorMapper" },
		// { "form.collateralObject",
		// "com.integrosys.cms.ui.collateral.pledgor.PledgorCollateralMapper" },
		};
		return input;
	}
}
