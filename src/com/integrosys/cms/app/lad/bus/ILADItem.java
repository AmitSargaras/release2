package com.integrosys.cms.app.lad.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 @author $Author: Abhijit R $
 */
public interface ILADItem extends Serializable, IValueObject {

	public long getId();
	public void setId(long id);
	
	public long getLad_id();
	public void setLad_id(long ladId);
	
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
	
	public ILADSubItem[] getIladSubItem();
	public void setIladSubItem(ILADSubItem[] iladSubItem);
    
    
}
