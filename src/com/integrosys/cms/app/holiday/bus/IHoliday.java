package com.integrosys.cms.app.holiday.bus;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * @author  Abhijit R. 
 */
public interface IHoliday extends Serializable, IValueObject {


	public String getDescription();
	public void setDescription(String description);
	
	public Date getStartDate();
	public void setStartDate(Date startDate);
	
	public Date getEndDate();
	public void setEndDate(Date endDate);
	
	public String getIsRecurrent();
    public void setIsRecurrent(String isRecurrent);
	 
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
    
    public FormFile getFileUpload();
    public void setFileUpload(FormFile fileUpload);

}
