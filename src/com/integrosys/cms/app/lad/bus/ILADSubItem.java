package com.integrosys.cms.app.lad.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 @author $Author: Abhijit R $
 */
public interface ILADSubItem extends Serializable, IValueObject {

	public long getId();
	public void setId(long id);
	
	public long getDoc_sub_item_id();
	public void setDoc_sub_item_id(long docSubItemId);
	
	public String getDoc_description();
	public void setDoc_description(String docDescription);
	
	public Date getDoc_date();
	public void setDoc_date(Date docDate);
	
	public Date getExpiry_date();
	public void setExpiry_date(Date expiryDate);
	
	public long getDoc_item_id();
	public void setDoc_item_id(long docItemId);
	
	public String getStatus();
	public void setStatus(String status);
	
	public long getVersionTime();
	public void setVersionTime(long versionTime);
	
	public String getCategory();
	public void setCategory(String category);
	
	public String getDocChklistVersion();
	public void setDocChklistVersion(String docChklistVersion) ;
	
	public String getDocChklistAmt();
	public void setDocChklistAmt(String docChklistAmt);
	
	public long getChklistDocItemId();
	public void setChklistDocItemId(long chklistDocItemId);
	
	/*public String getIsDisplay();
	public void setIsDisplay(String isDisplay);*/
	
	public long getChklistDocId();
	public void setChklistDocId(long chklistDocId);
	
	public String getType();
	public void setType(String type);
	
	public String getDocStatus();
	public void setDocStatus(String docStatus);
    
    
}
