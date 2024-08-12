/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/IDiaryItemDAO.java,v 1.4 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.newTat.bus;

import java.util.ArrayList;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.facilityNewMaster.bus.FacilityNewMasterException;


/**
 * @author  Abhijit R. 
 */
public interface INewTatJdbc {
	
	ArrayList getFilteredActualTat(String cusName,String cusId,	String caseIni,	String lastUpdate,	String rgn,	String sts,	String sgmnt,String mdl)throws TatException;
	public ArrayList getAuditTrailDetail(String caseid);
	public INewTat getTatDetail(String caseid, String status);
	public long getNewTatReportCaseByCaseId(String caseid);
	public ArrayList getTatCase(String caseid);
	public ArrayList getVersionTime(String caseid);
	public INewTat getFirstSubmitTime(String caseid, String status);
	public String getDifferenceInMin(String processedDate,String processedTime, String receivedDate,String receivedTime,String module,long caseId);
	
	
	
	
	
}
