/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/IDiaryItemDAO.java,v 1.4 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.facilityNewMaster.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;

/**
 * @author  Abhijit R. 
 */
public interface IFacilityNewMasterJdbc {
	SearchResult getAllFacilityNewMaster (String searchBy,String searchText)throws FacilityNewMasterException;
	SearchResult getAllFacilityNewMaster()throws FacilityNewMasterException;
	SearchResult getFilteredActualFacilityNewMaster(String code, String name,String category, String type, String system, String line)throws FacilityNewMasterException;
	List getAllFacilityNewMasterSearch(String login)throws FacilityNewMasterException;
	IFacilityNewMaster listFacilityNewMaster(long branchCode)throws FacilityNewMasterException;
//	boolean isUniqueCode(String lineNumber) throws FacilityNewMasterException;
	
	IFacilityNewMaster getFacilityMasterByFacCode(String facilityCode) throws FacilityNewMasterException;
	String getLineExcludeFromLoaMasterByFacilityCode(String facilityCode);
	List<String> getAllActiveLineNumbers();

}
