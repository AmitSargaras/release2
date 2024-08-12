package com.integrosys.cms.app.geography.state.bus;

import java.io.Serializable;
import java.util.Set;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.geography.region.bus.IRegion;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public interface IState extends IValueObject,Serializable{

	public IRegion getRegionId();
	public void setRegionId(IRegion regionId);
	 
	public String getStateName();
	public void setStateName(String regionCode);
    
	public String getStateCode();
	public void setStateCode(String regionCode);
	
	public String getStatus();
	public void setStatus(String status);
	
	public String getDeprecated();
	public void setDeprecated(String deprecated);
	
	public long getIdState();
	public void setIdState(long idState);
	
	public Set getCityList();
	public void setCityList(Set cityList);
	
	public void setCpsId(String cpsId);
	public String getCpsId();
	public String getOperationName();
	public void setOperationName(String operationName);
	
	
	
	
	/**
	 * @return the fileUpload
	 */
	public FormFile getFileUpload();

	/**
	 * @param fileUpload the fileUpload to set
	 */
	public void setFileUpload(FormFile fileUpload);
}
