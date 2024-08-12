package com.integrosys.cms.app.discrepency.bus;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface IDiscrepency extends IValueObject,Serializable{
	
	
	
	public long getId();
	public void setId(long id);
	
	public long getCustomerId();
	public void setCustomerId(long customerId);
	
	public String getDiscrepencyType();
	public void setDiscrepencyType(String disrepencyType);
    
//	public String getSelectedFacilityList();
//	public void setSelectedFacilityList(String selectedFacilityList);
	
	public String getApprovedBy();
	public void setApprovedBy(String approvedBy);
	
	public String getCritical();
	public void setCritical(String critical);
	
	public Date getCreationDate();
	public void setCreationDate(Date creationDate);
	
	public Date getAcceptedDate();
	public void setAcceptedDate(Date acceptedDate);
	
	public Date getOriginalTargetDate();
	public void setOriginalTargetDate(Date originalTargetDate);
	
	public Date getNextDueDate();
	public void setNextDueDate(Date nextDueDate);
	
	public long getCounter();
	public void setCounter(long counter);
	
	public String getDiscrepency();
	public void setDiscrepency(String disrepency);
	
	public String getDiscrepencyRemark();
	public void setDiscrepencyRemark(String disrepencyRemark);
	
	public IDiscrepencyFacilityList[] getFacilityList();
	public void setFacilityList(IDiscrepencyFacilityList[] facilityList);
	
	public String getDocRemarks();
	public void setDocRemarks(String docRemarks);
	
	public Date getRecDate();
	public void setRecDate(Date recDate);
	
	public Date getDeferDate();
	public void setDeferDate(Date deferDate);
	
	public Date getWaiveDate();
	public void setWaiveDate(Date waiveDate);
	
	public String getCreditApprover();
	public void setCreditApprover(String creditApprover) ;
	
	public String getStatus();
	public void setStatus(String status);
//	public List getFacilityList();
//	public void setFacilityList(List facilityList);

	public String getTransactionStatus() ;
	public void setTransactionStatus(String transactionStatus);
	
	public String getTotalDeferedDays();
	public void setTotalDeferedDays(String totalDeferedDays);
	
	public String getOriginalDeferedDays();
	public void setOriginalDeferedDays(String originalDeferedDays);
	
	public long getDeferedCounter();
	public void setDeferedCounter(long deferedCounter);
	
	public String getSelectedArray();
	public void setSelectedArray(String selectedArray);
	public String getUnCheckArray() ;
	public void setUnCheckArray(String unCheckArray) ;
}