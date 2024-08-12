package com.integrosys.cms.app.geography.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface IGeography extends IValueObject,Serializable{
	
//public static final String TRX_GROUP = "CONTINENT";
	
	public long getId();
	public void setId(long id);

	public String getCodeName();
	public void setCodeName(String codeName);
    
	public String getCodeDesc();
	public void setCodeDesc(String codeDesc);
	
	public String getCodeType();
	public void setCodeType(String codeType);
    
	public String getParentId();
	public void setParentId(String parentId);
    
	public String getGeographyId();
	public void setGeographyId(String geographyId);
    
	public String getRegionId();
	public void setRegionId(String regionId);

	public String getCreateBy();
	public void setCreateBy(String createBy);
	
	public Date getCreationDate();
	public void setCreationDate(Date creationDate);
	
	public String getUpdateBy();
	public void setUpdateBy(String updateBy);
	
	public Date getUpdateDate();	
	public void setUpdateDate(Date updateDate);
	
	public String getStatus();
	public void setStatus(String status);
	
	public String getDeprecated();
	public void setDeprecated(String deprecated);

	public long getVersionTime();
	public void setVersionTime(long versionTime);
	
	public long getMasterId();
	public void setMasterId(long MasterId);
}
