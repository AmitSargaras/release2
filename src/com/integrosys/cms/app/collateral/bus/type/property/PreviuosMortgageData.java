package com.integrosys.cms.app.collateral.bus.type.property;

import java.util.Date;

public class PreviuosMortgageData{
	
	private long id;
    private long collateralId;
    private String cersaiTrxRefNo;
    private Date dateCersaiRegi;
    private String cersaiId;
    private Date salePurchaseDate;
    private Date legalAuditDate;
    private Date interveingPeriSerachDate;
    private Date dateOfReceiptTitleDeed;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCollateralId() {
		return collateralId;
	}
	public void setCollateralId(long collateralId) {
		this.collateralId = collateralId;
	}
	public String getCersaiTrxRefNo() {
		return cersaiTrxRefNo;
	}
	public void setCersaiTrxRefNo(String cersaiTrxRefNo) {
		this.cersaiTrxRefNo = cersaiTrxRefNo;
	}
	public Date getDateCersaiRegi() {
		return dateCersaiRegi;
	}
	public void setDateCersaiRegi(Date dateCersaiRegi) {
		this.dateCersaiRegi = dateCersaiRegi;
	}
	public String getCersaiId() {
		return cersaiId;
	}
	public void setCersaiId(String cersaiId) {
		this.cersaiId = cersaiId;
	}
	public Date getSalePurchaseDate() {
		return salePurchaseDate;
	}
	public void setSalePurchaseDate(Date salePurchaseDate) {
		this.salePurchaseDate = salePurchaseDate;
	}
	public Date getLegalAuditDate() {
		return legalAuditDate;
	}
	public void setLegalAuditDate(Date legalAuditDate) {
		this.legalAuditDate = legalAuditDate;
	}
	public Date getInterveingPeriSerachDate() {
		return interveingPeriSerachDate;
	}
	public void setInterveingPeriSerachDate(Date interveingPeriSerachDate) {
		this.interveingPeriSerachDate = interveingPeriSerachDate;
	}
	public Date getDateOfReceiptTitleDeed() {
		return dateOfReceiptTitleDeed;
	}
	public void setDateOfReceiptTitleDeed(Date dateOfReceiptTitleDeed) {
		this.dateOfReceiptTitleDeed = dateOfReceiptTitleDeed;
	}
	
	
}
