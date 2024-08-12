package com.integrosys.cms.app.caseCreation.bus;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * @author  Abhijit R. 
 */
public interface ICaseCreation extends Serializable, IValueObject {



    public long getId();
    public void setId(long id);	


    public String getStatus();
    public void setStatus(String aStatus);	


    public long getVersionTime();
    public void setVersionTime(long versionTime);
    
    public long getChecklistitemid();
	public void setChecklistitemid(long checklistitemid);
	
	public long getCasecreationid();
	public void setCasecreationid(long casecreationid);
	
	public String getItemtype();
	public void setItemtype(String itemtype);
	
	public String getRemark();
	public void setRemark(String remark);
	
	public Date getCaseDate();
	public void setCaseDate(Date caseDate);
	
	public Date getRequestedDate();
	public void setRequestedDate(Date requestedDate);
	
	public Date getDispatchedDate();
	public void setDispatchedDate(Date dispatchedDate);
	
	public Date getReceivedDate();
	public void setReceivedDate(Date receivedDate);
	
	public long getLimitProfileId();
	public void setLimitProfileId(long limitProfileId);
	

    public Date getWrongReqDate() ;
	public void setWrongReqDate(Date wrongReqDate) ;
	
	public String getIsAutoCase();
	public void setIsAutoCase(String isAutoCase);
	
	
	//New Added For Case Cration
	public String getDocumentBarCode();
	public void setDocumentBarCode(String documentBarCode);
	
	public String getVaultNumber();
	public void setVaultNumber(String vaultNumber);
	
	public Date getRetrievaldate();
	public void setRetrievaldate(Date retrievaldate);
	
	public Date getVaultReceiptDate();
	public void setVaultReceiptDate(Date vaultReceiptDate);
	
	public String getUserName();
	public void setUserName(String userName);
	
	public String getSubmittedTo();
	public void setSubmittedTo(String submittedTo);
	
	public String getDocumentAmount();
	public void setDocumentAmount(String documentAmount);
	
	public String getVaultLocation();
	public void setVaultLocation(String vaultLocation);
	
	public String getStampDuty();
	public void setStampDuty(String stampDuty);
	
	public String getPlaceOfExecution();
	public void setPlaceOfExecution(String placeOfExecution);
	
	public String getFileBarCode();
	public void setFileBarCode(String fileBarCode);
	
	public String getBoxBarCode();
	public void setBoxBarCode(String boxBarCode);
	
	public String getRackNumber();
	public void setRackNumber(String rackNumber);
	
	public String getLotNumber();
	public void setLotNumber(String lotNumber);
	
	public String[] getUpdatedDocBarcodes();
	public void setUpdatedDocBarcodes(String[] updatedDocBarcodes);
	
	public String[] getUpdatedDocAmounts();
	public void setUpdatedDocAmounts(String[] updatedDocAmounts);
	
	public String[] getUpdatedVaultNumbers();
	public void setUpdatedVaultNumbers(String[] updatedVaultNumbers);
	
	public String[] getUpdatedRetrievaldates();
	public void setUpdatedRetrievaldates(String[] updatedRetrievaldates);
	
	public String[] getUpdatedVaultReceiptDates();
	public void setUpdatedVaultReceiptDates(String[] updatedVaultReceiptDates);
	
	public String[] getUpdatedUserNames();
	public void setUpdatedUserNames(String[] updatedUserNames);
	
	public String[] getUpdatedSubmittedTos();
	public void setUpdatedSubmittedTos(String[] updatedSubmittedTos);
	
	public String[] getUpdatedVaultLocations();
	public void setUpdatedVaultLocations(String[] updatedVaultLocations);
	
	public String[] getUpdatedStampDutys();
	public void setUpdatedStampDutys(String[] updatedStampDutys);
	
	public String[] getUpdatedPlaceOfExecutions();
	public void setUpdatedPlaceOfExecutions(String[] updatedPlaceOfExecutions);
	
	public String[] getUpdatedFileBarCodes();
	public void setUpdatedFileBarCodes(String[] updatedFileBarCodes);
	
	public String[] getUpdatedBoxBarCodes();
	public void setUpdatedBoxBarCodes(String[] updatedBoxBarCodes);
	
	public String[] getUpdatedRackNumbers();
	public void setUpdatedRackNumbers(String[] updatedRackNumbers) ;
	
	public String[] getUpdatedLotNumbers();
	public void setUpdatedLotNumbers(String[] updatedLotNumbers);
	
	public String[] getUpdatedReceivedDates();
	public void setUpdatedReceivedDates(String[] updatedReceivedDates);
	//End

}
