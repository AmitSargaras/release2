package com.integrosys.cms.app.npaTraqCodeMaster.bus;

import java.math.BigDecimal;
import java.util.Date;

public class OBNpaDataLog implements INpaDataLog {
	
	public OBNpaDataLog() {}
	
    private long id;
    private Date reportingDate;
	private String system;
	private String partyID;
	private String collateralType;
	private Date valuationDate;
	private BigDecimal valuationAmount;
	private Double originalValue;
	private String startDate;
	private String maturityDate;
	private String fileName;
	private String status;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getReportingDate() {
		return reportingDate;
	}
	public void setReportingDate(Date reportingDate) {
		this.reportingDate = reportingDate;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getPartyID() {
		return partyID;
	}
	public void setPartyID(String partyID) {
		this.partyID = partyID;
	}
	public String getCollateralType() {
		return collateralType;
	}
	public void setCollateralType(String collateralType) {
		this.collateralType = collateralType;
	}
	public Date getValuationDate() {
		return valuationDate;
	}
	public void setValuationDate(Date valuationDate) {
		this.valuationDate = valuationDate;
	}
	public BigDecimal getValuationAmount() {
		return valuationAmount;
	}
	public void setValuationAmount(BigDecimal valuationAmount) {
		this.valuationAmount = valuationAmount;
	}
	public Double getOriginalValue() {
		return originalValue;
	}
	public void setOriginalValue(Double originalValue) {
		this.originalValue = originalValue;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
