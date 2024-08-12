package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;
import java.util.List;

public interface IOtherCovenant  extends Serializable {

	
	public String getIsUpdate();
	
	public void setIsUpdate(String isUpdate);
	
	public String getPreviousStagingId();
	
	public void setPreviousStagingId(String previousStagingId);
	
	public String getDisplayId();
	
	public void setDisplayId(String displayId);
	
	public String getStatus();
	
	public void setStatus(String status);
	
	public String getStagingRefid();
	
	public void setStagingRefid(String stagingRefid);
	
	public String getCovenantCategory();
	
	public void setCovenantCategory(String covenantCategory);
	
	public long getOtherCovenantId();

	public void setOtherCovenantId(long OtherCovenantId);
	
	public String getCovenantType() ;
	
	public void setCovenantType(String covenantType);
	
	public String getTargetDate() ;
	
	public void setTargetDate(String targetDate);

	public String getCustRef();
	
	public void setCustRef(String CustID);
	
	public String getCovenantCondition();
	
	public void setCovenantCondition(String covenantCondition);
	
	public String getFaciltyName() ;
	
	public void setFaciltyName(String faciltyName);
	
	public String getCompiled();
	
	public void setCompiled(String compiled);
	
	public String getAdvised() ;
	public void setAdvised(String advised);
	public String getMonitoringResponsibilityList1();
	public String getMonitoringResponsibilityList2();
	public void setMonitoringResponsibilityList1(String monitoringResponsibilityList1);
	public void setMonitoringResponsibilityList2(String monitoringResponsibilityList2);
	public String getCovenantDescription();
	public void setCovenantDescription(String covenantDescription);
	public String getRemarks();
	public void setRemarks(String remarks);
	
	public String getMonitoringResponsibiltyValue();
	public void setMonitoringResponsibiltyValue(String monitoringResponsibiltyValue);
	public String getFacilityNameValue();
	public void setFacilityNameValue(String facilityNameValue);
	public String getFinalfaciltyName();
	public void setFinalfaciltyName(String finalfaciltyName);
}
