package com.integrosys.cms.ui.discrepency;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 01/06/2011 01:49:00 $ Tag: $Name: $
 */

public class DiscrepencyForm extends TrxContextForm implements Serializable {

	static final long serialVersionUID = 0L;

	private String id;
	private String customerId;
	private String discrepencyType;
	private String facilityList;
	
	private String discrepencyId;
	private String selectedFacilityList;
//	private List selectedFacilityList;
	
	private String hiddenList;
	private String creationDate;
	private String acceptedDate;
	private String originalTargetDate;
	private String nextDueDate;
	private String critical;
	private String approvedBy;
	private String counter;
	private String discrepency;
	private String discrepencyRemark;
	private String docRemarks;
	private String recDate;
	private String deferDate;
	private String waiveDate;
	private String creditApprover;
	private String status;
	private String totalDeferedDays;
	private String originalDeferedDays;
	private String deferedCounter;
	private String selectedArray;
	private String unCheckArray;
	private String cpsDiscrepancyId;
	
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

	public String getDeferedCounter() {
		return deferedCounter;
	}

	public void setDeferedCounter(String deferedCounter) {
		this.deferedCounter = deferedCounter;
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

	public String getRecDate() {
		return recDate;
	}

	public void setRecDate(String recDate) {
		this.recDate = recDate;
	}

	public String getDeferDate() {
		return deferDate;
	}

	public void setDeferDate(String deferDate) {
		this.deferDate = deferDate;
	}

	public String getWaiveDate() {
		return waiveDate;
	}

	public void setWaiveDate(String waiveDate) {
		this.waiveDate = waiveDate;
	}

	public String getDocRemarks() {
		return docRemarks;
	}

	public void setDocRemarks(String docRemarks) {
		this.docRemarks = docRemarks;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

 	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getDiscrepencyType() {
		return discrepencyType;
	}

	public void setDiscrepencyType(String discrepencyType) {
		this.discrepencyType = discrepencyType;
	}

	public String getDiscrepencyId() {
		return discrepencyId;
	}

	public void setDiscrepencyId(String discrepencyId) {
		this.discrepencyId = discrepencyId;
	}

	public String getFacilityList() {
		return facilityList;
	}

	public void setFacilityList(String facilityList) {
		this.facilityList = facilityList;
	}

	public String getSelectedFacilityList() {
		return selectedFacilityList;
	}

	public void setSelectedFacilityList(String selectedFacilityList) {
		this.selectedFacilityList = selectedFacilityList;
	}
	
	

	public String getHiddenList() {
		return hiddenList;
	}

	/*public List getSelectedFacilityList() {
		return selectedFacilityList;
	}

	public void setSelectedFacilityList(List selectedFacilityList) {
		this.selectedFacilityList = selectedFacilityList;
	}*/

	public void setHiddenList(String hiddenList) {
		this.hiddenList = hiddenList;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getAcceptedDate() {
		return acceptedDate;
	}

	public void setAcceptedDate(String acceptedDate) {
		this.acceptedDate = acceptedDate;
	}

	public String getOriginalTargetDate() {
		return originalTargetDate;
	}

	public void setOriginalTargetDate(String originalTargetDate) {
		this.originalTargetDate = originalTargetDate;
	}

	public String getNextDueDate() {
		return nextDueDate;
	}

	public void setNextDueDate(String nextDueDate) {
		this.nextDueDate = nextDueDate;
	}

	public String getCounter() {
		return counter;
	}

	public void setCounter(String counter) {
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
	
	public String[][] getMapper() {

		String[][] input = { { "theOBTrxContext", TRX_MAPPER },
				{ "discrepencyObj", DISREPENCY_MAPPER } };
		return input;
	}

	public static final String DISREPENCY_MAPPER = "com.integrosys.cms.ui.discrepency.DiscrepencyMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";

	public String getCpsDiscrepancyId() {
		return cpsDiscrepancyId;
	}

	public void setCpsDiscrepancyId(String cpsDiscrepancyId) {
		this.cpsDiscrepancyId = cpsDiscrepancyId;
	}
}
