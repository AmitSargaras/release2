package com.integrosys.cms.app.ws.dto;

import java.io.Serializable;
import java.util.Date;

	public interface IFCUBSReportDataLog extends Serializable {
	public long getId();        
	public void setId(long id) ;
	
	public String getPartyId() ;
	public void setPartyId(String partyId);
	
	public String getPartyName();
	public void setPartyName(String partyName);


	public String getFacilityName() ;

	public void setFacilityName(String facilityName) ;

	public String getFacilityId();

	public void setFacilityId(String facilityId) ;

	public String getFacilityCategory();

	public void setFacilityCategory(String facilityCategory) ;

	public String getLineCode();

	public void setLineCode(String lineCode);
	public String getSerialNumber() ;

	public void setSerialNumber(String serialNumber) ;

	public String getTypeOfCovenant() ;

	public void setTypeOfCovenant(String typeOfCovenant) ;

	public String getCondition1() ;

	public void setCondition1(String condition1);

	public String getCondition2() ;

	public void setCondition2(String condition2) ;

	public String getCondition3();
	

	public void setCondition3(String condition3) ;

	public String getCondition4() ;

	public void setCondition4(String condition4) ;

	public String getCondition5();

	public void setCondition5(String condition5) ;
	
	
}
