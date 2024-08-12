package com.integrosys.cms.ui.checklist.document;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface IViewCompleteCheckList extends  Serializable {

	public String getDocType();

	public void setDocType(String docType) ;

	public String getType() ;
	public void setType(String type);

	public String getDescription(); 

	public void setDescription(String description) ;

	public String getVersion() ;

	public void setVersion(String version) ;

	public String getStatus() ;

	public void setStatus(String status) ;

	public Date getDocDate() ;

	public void setDocDate(Date docDate) ;

	public Date getDocExpDate() ;

	public void setDocExpDate(Date docExpDate) ;


	public void setDocAmount(String docAmount);

	public String getActive();

	public void setActive(String active); 
	
	public long getDocItemId();

	public void setDocItemId(long docItemId) ;

}
