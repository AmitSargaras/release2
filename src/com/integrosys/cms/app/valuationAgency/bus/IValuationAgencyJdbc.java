package com.integrosys.cms.app.valuationAgency.bus;

import java.util.List;

import com.integrosys.cms.app.facilityNewMaster.bus.FacilityNewMasterException;
import com.integrosys.cms.app.holiday.bus.HolidayException;

public interface IValuationAgencyJdbc {

	
	
	
	
	List getAllStageValuationAgency (String searchBy, String login)throws ValuationAgencyException;
	List getFileMasterList(String searchBy)throws ValuationAgencyException;
	

	

}
