package com.integrosys.cms.app.udf.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface IUdf extends Serializable, IValueObject{
			
	// Getters 
	public String getFieldName();
	public long getFieldTypeId();
	public long getId();
	public long getModuleId();
	public String getOptions();
	public long getVersionTime();
	public String getModuleName();
	public String getFieldType();
	
	// Setters
	public void setFieldName(String fieldName);
	public void setFieldTypeId(long fieldTypeId);
	public void setId(long id);
	public void setModuleId(long moduleId);
	public void setOptions(String options);
	public void setVersionTime(long versionTime);
	public void setModuleName(String moduleName);
	public void setFieldType(String fieldType);
	
	public int getSequence();
	public void setSequence(int sequence);
	public String getStatus();
	public void setStatus(String status);
	
	public String getMandatory();
	public void setMandatory(String mandatory);
	
	
	public String getNumericLength();
	public void setNumericLength(String numericLength);
	
	public Date getCreationDate();
    public void setCreationDate(Date creationDate);
   
    public String getCreateBy();
    public void setCreateBy(String createBy);
    
    public Date getLastUpdateDate();
    public void setLastUpdateDate(Date lastUpdateDate);
   
    public String getLastUpdateBy();
    public void setLastUpdateBy(String lastUpdateBy);
    public String getOperationName();
	public void setOperationName(String operationName);
	
	public String getDeprecated();
	public void setDeprecated(String deprecated);
}
