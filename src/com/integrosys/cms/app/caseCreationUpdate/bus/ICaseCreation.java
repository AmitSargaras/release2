package com.integrosys.cms.app.caseCreationUpdate.bus;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * @author  Abhijit R. 
 */
public interface ICaseCreation extends Serializable, IValueObject {


	public String getDescription();
	public void setDescription(String description);
	
	
	 
    public String getDeprecated();
    public void setDeprecated(String deprecated);

    public long getId();
    public void setId(long id);	


    public String getStatus();
    public void setStatus(String aStatus);	


    public long getVersionTime();
    public void setVersionTime(long versionTime);
    
    public Date getCreationDate();
    public void setCreationDate(Date creationDate);
   
    public String getCreateBy();
    public void setCreateBy(String createBy);
    
    public Date getLastUpdateDate();
    public void setLastUpdateDate(Date lastUpdateDate);
   
    public String getLastUpdateBy();
    public void setLastUpdateBy(String lastUpdateBy);
    
   
    
    public long getLimitProfileId() ;
	public void setLimitProfileId(long limitProfileId);
	

	public String getBranchCode();
	public void setBranchCode(String branchCode);
	
	public String getPrevRemarks();
	public void setPrevRemarks(String prevRemarks) ;
	
	
	public String getCurrRemarks();
	public void setCurrRemarks(String currRemarks) ;
	
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
	
	public String[] getCheckBoxValues();
	public void setCheckBoxValues(String[] checkBoxValues);

}
