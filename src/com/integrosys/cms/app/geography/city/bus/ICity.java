package com.integrosys.cms.app.geography.city.bus;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.geography.state.bus.IState;

/**
 * 
 * @author sandiip.shinde
 * @since 15-04-2011
 */

public interface ICity extends IValueObject,Serializable{
	
	public IState getStateId();
	public void setStateId(IState stateId);
	 
	public String getCityName();
	public void setCityName(String regionCode);
    
	public String getCityCode();
	public void setCityCode(String regionCode);
	
	public String getStatus();
	public void setStatus(String status);
	
	public String getDeprecated();
	public void setDeprecated(String deprecated);	
	
	public long getIdCity();
	public void setIdCity(long idCity);
	
	public void setOperationName(String operationName);
	public String getOperationName();
	public void setCpsId(String cpsId);
	public String getCpsId();
	
	/**
	 * @return the fileUpload
	 */
	public FormFile getFileUpload();
	/**
	 * @param fileUpload the fileUpload to set
	 */
	public void setFileUpload(FormFile fileUpload);
	
	public Long getEcbfCityId();
	public void setEcbfCityId(Long ecbfCityId);
}
