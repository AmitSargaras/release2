package com.integrosys.cms.app.discrepency.bus;

import java.util.Date;
import java.util.Set;

/**
 * 
 * @author sandiip.shinde
 * @since 01-06-2011
 */

public class OBDiscrepency implements IDiscrepency{
	
	private long id;	
	private long customerId;
	private String discrepencyType;
//	private String selectedFacilityList;
	private Date creationDate;
	private Date acceptedDate;
	private Date originalTargetDate;
	private Date nextDueDate;
	private String critical;
	private String approvedBy;
	private long counter;
	private String discrepency;
	private String discrepencyRemark;
	private String creditApprover;
	private String docRemarks;
	private String status;
	private Date recDate;
	private Date deferDate;
	private Date waiveDate;
	private IDiscrepencyFacilityList[] facilityList;
	private String totalDeferedDays;
	private String originalDeferedDays;
	private String transactionStatus;
	private long deferedCounter;
	private String selectedArray;
	private String unCheckArray;
	
	
	
	
	
	public String getSelectedArray() {
		return selectedArray;
	}
	public void setSelectedArray(String selectedArray) {
		this.selectedArray = selectedArray;
	}
	public String getUnCheckArray() {
		return unCheckArray;
	}
	public void setUnCheckArray(String unCheckArray) {
		this.unCheckArray = unCheckArray;
	}
	public String getTotalDeferedDays() {
		return totalDeferedDays;
	}
	public void setTotalDeferedDays(String totalDeferedDays) {
		this.totalDeferedDays = totalDeferedDays;
	}
	public String getOriginalDeferedDays() {
		return originalDeferedDays;
	}
	public void setOriginalDeferedDays(String originalDeferedDays) {
		this.originalDeferedDays = originalDeferedDays;
	}
	public long getDeferedCounter() {
		return deferedCounter;
	}
	public void setDeferedCounter(long deferedCounter) {
		this.deferedCounter = deferedCounter;
	}
	
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
		
	
//	private List facilityList;
	 
	public long getId() {
		return id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreditApprover() {
		return creditApprover;
	}
	public void setCreditApprover(String creditApprover) {
		this.creditApprover = creditApprover;
	}
	public String getDocRemarks() {
		return docRemarks;
	}
	public void setDocRemarks(String docRemarks) {
		this.docRemarks = docRemarks;
	}
	public Date getRecDate() {
		return recDate;
	}
	public void setRecDate(Date recDate) {
		this.recDate = recDate;
	}
	public Date getDeferDate() {
		return deferDate;
	}
	public void setDeferDate(Date deferDate) {
		this.deferDate = deferDate;
	}
	public Date getWaiveDate() {
		return waiveDate;
	}
	public void setWaiveDate(Date waiveDate) {
		this.waiveDate = waiveDate;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public String getDiscrepencyType() {
		return discrepencyType;
	}
	public void setDiscrepencyType(String discrepencyType) {
		this.discrepencyType = discrepencyType;
	}
	public String getDiscrepency() {
		return discrepency;
	}
	public void setDiscrepency(String discrepency) {
		this.discrepency = discrepency;
	}
	public String getDiscrepencyRemark() {
		return discrepencyRemark;
	}
	public void setDiscrepencyRemark(String discrepencyRemark) {
		this.discrepencyRemark = discrepencyRemark;
	}
/*	public String getSelectedFacilityList() {
		return selectedFacilityList;
	}
	public void setSelectedFacilityList(String selectedFacilityList) {
		this.selectedFacilityList = selectedFacilityList;
	}*/
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getAcceptedDate() {
		return acceptedDate;
	}
	public void setAcceptedDate(Date acceptedDate) {
		this.acceptedDate = acceptedDate;
	}
	public Date getOriginalTargetDate() {
		return originalTargetDate;
	}
	public void setOriginalTargetDate(Date originalTargetDate) {
		this.originalTargetDate = originalTargetDate;
	}	
	public Date getNextDueDate() {
		return nextDueDate;
	}
	public void setNextDueDate(Date nextDueDate) {
		this.nextDueDate = nextDueDate;
	}
	public long getCounter() {
		return counter;
	}
	public void setCounter(long counter) {
		this.counter = counter;
	}
	public String getCritical() {
		return critical;
	}
	public void setCritical(String critical) {
		this.critical = critical;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public IDiscrepencyFacilityList[] getFacilityList() {
		return facilityList;
	}
	public void setFacilityList(IDiscrepencyFacilityList[] facilityList) {
		this.facilityList = facilityList;
	}

	public long getVersionTime() {
		return 0;
	}
/*	public List getFacilityList() {
		return facilityList;
	}
	public void setFacilityList(List facilityList) {
		this.facilityList = facilityList;
	}
*/	public void setVersionTime(long arg0) {		
	}

}
