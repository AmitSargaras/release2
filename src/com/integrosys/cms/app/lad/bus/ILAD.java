package com.integrosys.cms.app.lad.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 @author $Author: Abhijit R $
 */
public interface ILAD extends Serializable, IValueObject {

	 	public long getLad_id();
		public void setLad_id(long ladId);
		
		public String getLad_name();
		public void setLad_name(String ladName);
		
		public Date getLad_due_date();
		public void setLad_due_date(Date ladDueDate);
		
		public Date getGeneration_date();
		public void setGeneration_date(Date generationDate);
		
		public Date getReceive_date();
		public void setReceive_date(Date receiveDate);
		
		public Date getDocument_date();
		public void setDocument_date(Date documentDate);
		
		public Date getExpiry_date();
		public void setExpiry_date(Date expiryDate);
		
		public long getLad_counter();
		public void setLad_counter(long ladCounter);
		
		public String getStatus();
		public void setStatus(String status);
		
		public long getId();
		public void setId(long id);
		
		public long getVersionTime();
		public void setVersionTime(long versionTime);
		
		public ILADItem[] getIladItem();
		public void setIladItem(ILADItem[] iladItem);
		
		public long getLimit_profile_id();
		public void setLimit_profile_id(long limitProfileId);
		
		public String getIsOperationAllowed();
		public void setIsOperationAllowed(String isOperationAllowed) ;
		
		public long getChecklistId() ;
		public void setChecklistId(long checklistId);
}
