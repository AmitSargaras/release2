package com.integrosys.cms.app.segmentWiseEmail.bus;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface ISegmentWiseEmail extends Serializable, IValueObject{
	
	public long getID();
	public void setID(long iD);
	
	public String getSegment();
	public void setSegment(String segment);
	
	public String getEmail() ;
	public void setEmail(String email);
	
	public String getStatus();
	public void setStatus(String status);
	
	public Date getLastUpdateDate();
	public void setLastUpdateDate(Date lastUpdateDate);
	
	public Date getCreationDate();
	public void setCreationDate(Date creationDate);
	
	public String getCreatedBy();
	public void setCreatedBy(String createdBy);
	
	public String getLastUpdatedBy();
	public void setLastUpdatedBy(String lastUpdatedBy);
	
/*	public List<ISegmentWiseEmail> getObList();
	public void setObList(List<ISegmentWiseEmail> obList);*/
	
}
