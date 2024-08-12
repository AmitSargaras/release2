package com.integrosys.cms.app.directorMaster.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * Description:
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-04 15:13:16 +0800 (Tue, 03 May 2011) $
 */

public interface IDirectorMaster extends Serializable, IValueObject {

	
	 public String getDinNo();
	 public void setDinNo(String dinNo);
	
    
	 public String getName();
	 public void setName(String name);
	 
	 
	 public String getDirectorCode();
	 public void setDirectorCode(String directorCode);
	 
	 public String getAction();
	 public void setAction(String action);
	 	 			 
	 
    public String getDeprecated();
    public void setDeprecated(String deprecated);

    public long getId();
    public void setId(long id);	


    public String getStatus();
    public void setStatus(String Status);	
    
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

}
