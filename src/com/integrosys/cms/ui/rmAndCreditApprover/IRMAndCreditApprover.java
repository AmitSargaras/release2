package com.integrosys.cms.ui.rmAndCreditApprover;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import org.apache.struts.upload.FormFile;
/**
 * @author   
 */
public interface IRMAndCreditApprover extends Serializable, IValueObject {

	public String getCpsId();
	public void setCpsId(String cpsId) ;
	
	public String getUserName() ;
	public void setUserName(String userName) ;
	
	public String getRegion() ;
	public void setRegion(String region) ;
	
	public String getDeprecated() ;
	public void setDeprecated(String deprecated);
	
	public String getUserEmail() ;
	public void setUserEmail(String userEmail) ;
	
	public String getLoginId() ;
	public void setLoginId(String loginId) ;
	
	public String getSupervisorId() ;
	public void setSupervisorId(String supervisorId) ;
	
	public String getSeniorApproval() ;
	public void setSeniorApproval(String seniorApproval) ;
	
	public String getDpValue();
	public void setDpValue(String dpValue) ;
	
	public String getStatus() ;
	public void setStatus(String status) ;
	
	public String getEvent() ;
	public void setEvent(String event);
	
	public String getCreateBy();
	public void setCreateBy(String createBy) ;
	
	public String getLastUpdateBy() ;
	public void setLastUpdateBy(String lastUpdateBy) ;
	
	public String getOperationName();
	public void setOperationName(String operationName);
	
	public String getUserUnitType();
	public void setUserUnitType(String userUnitType);
	
	public String getUserRole();
	public void setUserRole(String userRole);
	
}
