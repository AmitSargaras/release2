package com.integrosys.cms.ui.newTat;

/**
 *@author abhijit.rudrakshawar
 *$ Command for Listing Customer
 */

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.newTat.bus.INewTat;
import com.integrosys.cms.app.newTat.bus.INewTatDAO;
import com.integrosys.cms.app.newTat.bus.INewTatJdbc;
import com.integrosys.cms.app.newTat.bus.NewTatDAOFactory;
import com.integrosys.cms.app.newTat.bus.OBNewTat;
import com.integrosys.cms.app.newTat.bus.OBNewTatReportCase;
import com.integrosys.cms.app.otherbranch.bus.IOtherBranchDAO;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;
import com.integrosys.component.bizstructure.app.bus.ITeam;

public class SubmitDocumentsCommand extends AbstractCommand {

	public SubmitDocumentsCommand() {

	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "newTatObj", "com.integrosys.cms.app.newTat.bus.OBNewTat", FORM_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "startIndexInner", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				{"cusNameSearch", "java.lang.String", SERVICE_SCOPE },
			    {"cusIdSearch", "java.lang.String", SERVICE_SCOPE },
				{"caseIniSearch", "java.lang.String", SERVICE_SCOPE },
				{"lastUpdateSearch", "java.lang.String", SERVICE_SCOPE },
				{"rgnSearch", "java.lang.String", SERVICE_SCOPE },
				{"stsSearch", "java.lang.String", SERVICE_SCOPE },
				{"sgmntSearch", "java.lang.String", SERVICE_SCOPE },
				{"mdlSearch", "java.lang.String", SERVICE_SCOPE },
		});
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{"regionMap", "java.util.Map",SERVICE_SCOPE},
				{ "newTatObj", "com.integrosys.cms.app.newTat.bus.OBNewTat", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "startIndexInner", "java.lang.String", REQUEST_SCOPE },
				{"rmMap", "java.util.Map",SERVICE_SCOPE},
				{"cusNameSearch", "java.lang.String", SERVICE_SCOPE },
			    {"cusIdSearch", "java.lang.String", SERVICE_SCOPE },
				{"caseIniSearch", "java.lang.String", SERVICE_SCOPE },
				{"lastUpdateSearch", "java.lang.String", SERVICE_SCOPE },
				{"rgnSearch", "java.lang.String", SERVICE_SCOPE },
				{"stsSearch", "java.lang.String", SERVICE_SCOPE },
				{"sgmntSearch", "java.lang.String", SERVICE_SCOPE },
				{"mdlSearch", "java.lang.String", SERVICE_SCOPE },

		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *             on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *             on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
	CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		INewTat tat = (INewTat) map.get("newTatObj");
		String event = (String) map.get("event");
		String startIndex = (String) map.get("startIndex");
		String startIndexInner = (String) map.get("startIndexInner");
		
		 String cusNameSearch = (String) map.get("cusNameSearch");
	        String cusIdSearch = (String) map.get("cusIdSearch");
	        String caseIniSearch = (String) map.get("caseIniSearch");
	        String lastUpdateSearch = (String) map.get("lastUpdateSearch");
	        String rgnSearch = (String) map.get("rgnSearch");
	        String stsSearch = (String) map.get("stsSearch");
	        String sgmntSearch = (String) map.get("sgmntSearch");
	        String mdlSearch = (String) map.get("mdlSearch");
		
		
		
		
		// DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		// System.out.println(dateFormat.format(cal.getTime()));
		IGeneralParamDao generalParam=(IGeneralParamDao) BeanHouse.get("generalParamDao");
		IGeneralParamEntry generalParamEntry2 = generalParam.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
		java.util.Date date = new java.util.Date(generalParamEntry2.getParamValue());
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, date.getYear()+Integer.parseInt("1900"));
		cal.set(Calendar.MONTH, date.getMonth());
		cal.set(Calendar.DAY_OF_MONTH, date.getDate());
//		Calendar currDate = Calendar.getInstance();
//		currDate.setTime(date);
		java.util.Date d = DateUtil.getDate();
		int finalHrs=d.getHours();
		int finalMins=d.getMinutes();
		HashMap inOutMap = NewTatUtil.getTatTimings(tat.getModule());
		String startTime=(String) inOutMap.get("in");
		String endTime=(String) inOutMap.get("out");
		String[]inTime =startTime.split(":");
		String[]outTime =endTime.split(":");
		
		if(finalHrs<Integer.parseInt(inTime[0])){
			finalHrs=Integer.parseInt(inTime[0]);
			finalMins=Integer.parseInt(inTime[1]);
		}else if(finalHrs==Integer.parseInt(inTime[0])){
			if(finalMins<=Integer.parseInt(inTime[1])){
			finalHrs=Integer.parseInt(inTime[0]);
			finalMins=Integer.parseInt(inTime[1]);
			}
		}else if(finalHrs>Integer.parseInt(outTime[0])){
			finalHrs=Integer.parseInt(outTime[0]);
			finalMins=Integer.parseInt(outTime[1]);
		}else if(finalHrs==Integer.parseInt(outTime[0])){
			if(finalMins>=Integer.parseInt(outTime[1])){
			finalHrs=Integer.parseInt(outTime[0]);
			finalMins=Integer.parseInt(outTime[1]);
			}
		}
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%intime :  hrs : "+finalHrs +"  mins : "+finalMins);
		
		date.setHours(finalHrs);
		date.setMinutes(finalMins);
		//date.setSeconds(d.getSeconds());
		
		tat.setActivityTime(date);
		tat.setActualActivityTime(cal.getTime());
		tat.setLastUpdateDate(date);
		tat.setDeprecated("N");
		INewTatDAO newTatDao = (INewTatDAO)BeanHouse.get("newTatDao");
		if(event!=null && !"".equals(event) && event.equals("confirm_submit_documents")){
			tat.setStatus("DOCUMENT_SUBMITTED");
			newTatDao.createTAT("actualNewTAT", tat,tat.getId());
			newTatDao.createTAT("stageNewTAT", tat,tat.getId());
			if(tat.getType()!=null && "FTNR".equals(tat.getType())){
				INewTatJdbc newTat = (INewTatJdbc)BeanHouse.get("newTatJdbc");
		    	ArrayList newTatListArray= new ArrayList(); 
		    	if(tat != null && tat.getCaseId() != 0){
		    		newTatListArray= (ArrayList)  newTat.getVersionTime(String.valueOf(tat.getCaseId()));
		    	}
				if(newTatListArray != null && newTatListArray.size()>0){
		    		OBNewTatReportCase obj = (OBNewTatReportCase) newTatListArray.get(0);
		    		tat.setVersionTime(obj.getVersionTime());
		    	}
				OBNewTatReportCase tatReportCase = updateValuesInReportObject(tat);
				newTatDao.createTATReportCaseBase("newTATReportCase", tatReportCase, tatReportCase.getId());
			}
		}
		else if(event!=null && !"".equals(event) && event.equals("deferral_approve")){
			tat.setStatus("DEFERRAL_APPROVED");
			newTatDao.updateTAT("actualNewTAT", tat,tat.getId());
			newTatDao.updateTAT("stageNewTAT", tat,tat.getId());
			if(tat.getType()!=null && "FTNR".equals(tat.getType())){
				INewTatJdbc newTat = (INewTatJdbc)BeanHouse.get("newTatJdbc");
		    	ArrayList newTatListArray= new ArrayList(); 
		    	if(tat != null && tat.getCaseId() != 0){
		    		newTatListArray= (ArrayList)  newTat.getVersionTime(String.valueOf(tat.getCaseId()));
		    	}
				if(newTatListArray != null && newTatListArray.size()>0){
		    		OBNewTatReportCase obj = (OBNewTatReportCase) newTatListArray.get(0);
		    		tat.setVersionTime(obj.getVersionTime());
		    	}
				OBNewTatReportCase tatReportCase = updateValuesInReportObject(tat);
				newTatDao.createTATReportCaseBase("newTATReportCase", tatReportCase, tatReportCase.getId());
			}
		}else if(event!=null && !"".equals(event) && event.equals("close_case")){
			tat.setStatus("CLOSED");
			newTatDao.updateTAT("actualNewTAT", tat,tat.getId());
			newTatDao.updateTAT("stageNewTAT", tat,tat.getId());
			
		}
		else if(event!=null && !"".equals(event) && event.equals("deferral_clearance")){
			tat.setStatus("DOCUMENT_SUBMITTED");
			newTatDao.updateTAT("actualNewTAT", tat,tat.getId());
			newTatDao.updateTAT("stageNewTAT", tat,tat.getId());
			if(tat.getType()!=null && "FTNR".equals(tat.getType())){
				INewTatJdbc newTat = (INewTatJdbc)BeanHouse.get("newTatJdbc");
		    	ArrayList newTatListArray= new ArrayList(); 
		    	if(tat != null && tat.getCaseId() != 0){
		    		newTatListArray= (ArrayList)  newTat.getVersionTime(String.valueOf(tat.getCaseId()));
		    	}
				if(newTatListArray != null && newTatListArray.size()>0){
		    		OBNewTatReportCase obj = (OBNewTatReportCase) newTatListArray.get(0);
		    		tat.setVersionTime(obj.getVersionTime());
		    	}
				OBNewTatReportCase tatReportCase = updateValuesInReportObject(tat);
				newTatDao.createTATReportCaseBase("newTATReportCase", tatReportCase, tatReportCase.getId());
			}
			
		}
		else if(event!=null && !"".equals(event) && event.equals("confirm_document_receive")){
			if("CLOSED".equals(tat.getStatus())){
				tat.setType("FTNR");
				newTatDao.updateTAT("actualNewTAT", tat,tat.getId());
				newTatDao.updateTAT("stageNewTAT", tat,tat.getId());
//				OBNewTatReportCase tatReportCase = updateValuesInReportObject(tat);
//				newTatDao.createTATReportCaseBase("newTATReportCase", tatReportCase, tatReportCase.getId());
			}else{
			newTatDao.updateTAT("actualNewTAT", tat,tat.getId());
			newTatDao.updateTAT("stageNewTAT", tat,tat.getId());
			INewTatJdbc newTat = (INewTatJdbc)BeanHouse.get("newTatJdbc");
			if(tat.getType()!=null && "FTNR".equals(tat.getType())){
		    	ArrayList newTatListArray= new ArrayList(); 
		    	if(tat != null && tat.getCaseId() != 0){
		    		newTatListArray= (ArrayList)  newTat.getVersionTime(String.valueOf(tat.getCaseId()));
		    	}
				if(newTatListArray != null && newTatListArray.size()>0){
		    		OBNewTatReportCase obj = (OBNewTatReportCase) newTatListArray.get(0);
		    		tat.setVersionTime(obj.getVersionTime());
		    	}
				OBNewTatReportCase tatReportCase = updateValuesInReportObject(tat);
				newTatDao.createTATReportCaseBase("newTATReportCase", tatReportCase, tatReportCase.getId());
			}
		}
		}
		else if(event!=null && !"".equals(event) && event.equals("confirm_document_scan")){
			tat.setStatus("DOCUMENT_SCANNED");
			newTatDao.updateTAT("actualNewTAT", tat,tat.getId());
			newTatDao.updateTAT("stageNewTAT", tat,tat.getId());
			INewTatJdbc newTat = (INewTatJdbc)BeanHouse.get("newTatJdbc");
			if(tat.getType()!=null && "FTNR".equals(tat.getType())){
		    	ArrayList newTatListArray= new ArrayList(); 
		    	if(tat != null && tat.getCaseId() != 0){
		    		newTatListArray= (ArrayList)  newTat.getVersionTime(String.valueOf(tat.getCaseId()));
		    	}
				if(newTatListArray != null && newTatListArray.size()>0){
		    		OBNewTatReportCase obj = (OBNewTatReportCase) newTatListArray.get(0);
		    		tat.setVersionTime(obj.getVersionTime());
		    	}
				OBNewTatReportCase tatReportCase = updateValuesInReportObject(tat);
				newTatDao.createTATReportCaseBase("newTATReportCase", tatReportCase, tatReportCase.getId());
			}
		}else if(event!=null && !"".equals(event) && event.equals("confirm_limit_release")){
			tat.setStatus("LIMIT_RELEASED");
			if(tat.getType()==null || !"FTNR".equals(tat.getType())){
				tat.setType("FTR");
			}
			newTatDao.updateTAT("actualNewTAT", tat,tat.getId());
			newTatDao.updateTAT("stageNewTAT", tat,tat.getId());
			INewTatJdbc newTat = (INewTatJdbc)BeanHouse.get("newTatJdbc");
	    	ArrayList newTatListArray= new ArrayList(); 
	    	if(tat != null && tat.getCaseId() != 0){
	    		newTatListArray= (ArrayList)  newTat.getVersionTime(String.valueOf(tat.getCaseId()));
	    	}
			if(newTatListArray != null && newTatListArray.size()>0){
	    		OBNewTatReportCase obj = (OBNewTatReportCase) newTatListArray.get(0);
	    		tat.setVersionTime(obj.getVersionTime());
	    	}
			OBNewTatReportCase tatReportCase = updateValuesInReportObject(tat);
			newTatDao.createTATReportCaseBase("newTATReportCase", tatReportCase, tatReportCase.getId());
		}else if(event!=null && !"".equals(event) && event.equals("confirm_deferral_raised")){
			tat.setStatus("DEFERRAL_RAISED");
			tat.setType("FTNR");
			newTatDao.updateTAT("actualNewTAT", tat,tat.getId());
			newTatDao.updateTAT("stageNewTAT", tat,tat.getId());
			INewTatJdbc newTat = (INewTatJdbc)BeanHouse.get("newTatJdbc");
	    	ArrayList newTatListArray= new ArrayList(); 
	    	if(tat != null && tat.getCaseId() != 0){
	    		newTatListArray= (ArrayList)  newTat.getVersionTime(String.valueOf(tat.getCaseId()));
	    	}
			if(newTatListArray != null && newTatListArray.size()>0){
	    		OBNewTatReportCase obj = (OBNewTatReportCase) newTatListArray.get(0);
	    		tat.setVersionTime(obj.getVersionTime());
	    	}
			OBNewTatReportCase tatReportCase = updateValuesInReportObject(tat);
			newTatDao.createTATReportCaseBase("newTATReportCase", tatReportCase, tatReportCase.getId());
		}else if(event!=null && !"".equals(event) && event.equals("confirm_clims_updated")){
			tat.setStatus("CLIMS_UPDATED");
			if(tat.getType()==null || !"FTNR".equals(tat.getType())){
				tat.setType("FTR");
			}
			newTatDao.updateTAT("actualNewTAT", tat,tat.getId());
			newTatDao.updateTAT("stageNewTAT", tat,tat.getId());
			INewTatJdbc newTat = (INewTatJdbc)BeanHouse.get("newTatJdbc");
	    	ArrayList newTatListArray= new ArrayList(); 
	    	if(tat != null && tat.getCaseId() != 0){
	    		newTatListArray= (ArrayList)  newTat.getVersionTime(String.valueOf(tat.getCaseId()));
	    	}
			if(newTatListArray != null && newTatListArray.size()>0){
	    		OBNewTatReportCase obj = (OBNewTatReportCase) newTatListArray.get(0);
	    		tat.setVersionTime(obj.getVersionTime());
	    	}
			OBNewTatReportCase tatReportCase = updateValuesInReportObject(tat);
			newTatDao.createTATReportCaseBase("newTATReportCase", tatReportCase, tatReportCase.getId());
		}
		//INewTat newTat = (INewTat) NewTatDAOFactory.getNewTatDAO().createTAT("actualNewTAT", tat);
		result.put("regionMap", getRegionMap());
		if(event!=null && !"".equals(event))
		{
			result.put("event", event);
		}
		result.put("rmMap", getRelationshipManagerMap());
		result.put("newTatObj", tat);
		result.put("startIndex", startIndex);
		result.put("startIndexInner", startIndexInner);
		
		result.put("cusNameSearch",cusNameSearch);
		result.put("cusIdSearch",cusIdSearch);
		result.put("caseIniSearch",caseIniSearch);
		result.put("lastUpdateSearch",lastUpdateSearch);
		result.put("rgnSearch", rgnSearch);
		result.put("stsSearch", stsSearch);
		result.put("sgmntSearch",sgmntSearch);
		result.put("mdlSearch", mdlSearch);
		
		
	temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
	
	private HashMap getRegionMap() {
		HashMap lbValmap = new HashMap();
		try {

			IRegionDAO regionDAO = (IRegionDAO) BeanHouse.get("regionDAO");
			Collection idList = (regionDAO.listRegion("", "")).getResultList();			
			Iterator itr =idList.iterator();
			while(itr.hasNext()){
				IRegion region = (IRegion)itr.next();
				String id = Long.toString(region.getIdRegion());
				String val = region.getRegionName();
				lbValmap.put(id, val);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lbValmap;
	}


	private HashMap getRelationshipManagerMap() {
		HashMap lbValmap = new HashMap();
		try {

			IRelationshipMgrDAO relationshipMgrDAO = (IRelationshipMgrDAO) BeanHouse.get("relationshipMgrDAO");
			Collection idList = relationshipMgrDAO.getRelationshipMgrList("").getResultList();			
			Iterator itr =idList.iterator();
			while(itr.hasNext()){
				IRelationshipMgr relationshipMgr = (IRelationshipMgr)itr.next();
				String id = Long.toString(relationshipMgr.getId());
				String val = relationshipMgr.getRelationshipMgrName();
				lbValmap.put(id, val);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lbValmap;
	}
	
	private OBNewTatReportCase updateValuesInReportObject(INewTat tat) {
		OBNewTatReportCase tatReportCase= new OBNewTatReportCase();
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy"); 
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
		SimpleDateFormat sdfTime1 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		java.util.Date finalDate =null;
		java.util.Date startDate =null;
		tatReportCase.setCase_No(String.valueOf(tat.getCaseId()));
		tatReportCase.setCase_RM(tat.getCaseInitiator());
		tatReportCase.setCategory(tat.getType());
		tatReportCase.setDelay_reason(tat.getDelayReason());
		INewTatJdbc newTatJdbcImpl = (INewTatJdbc)BeanHouse.get("newTatJdbc");
		if("CLIMS_UPDATED".equals(tat.getStatus())|| "LIMIT_RELEASED".equals(tat.getStatus())){
			finalDate =tat.getActivityTime();
		tatReportCase.setcPU_limit_set_CLIMS_date(sdfDate.format(tat.getActivityTime()));
		tatReportCase.setcPU_Limit_set_CLIMS_Time(sdfTime.format(tat.getActivityTime()));
		tatReportCase.setActual_cPU_Limit_set_CLIMS_dateime(sdfTime1.format(tat.getActualActivityTime()).toString());
		}
		if("DEFERRAL_APPROVED".equals(tat.getStatus())){
		tatReportCase.setDeferral_Approved_Date(sdfDate.format(tat.getActivityTime()));
		tatReportCase.setDeferral_Approved_Time(sdfTime.format(tat.getActivityTime()));
		//tatReportCase.setActual_deferral_Approved_datetime(tat.getActualActivityTime().toString());
		tatReportCase.setActual_deferral_Approved_datetime(sdfTime1.format(tat.getActualActivityTime()).toString());
		}else{
			INewTat iNewTatDA= newTatJdbcImpl.getTatDetail(String.valueOf(tat.getCaseId()),"DEFERRAL_APPROVED");
	    	if(iNewTatDA!=null){
			tatReportCase.setDeferral_Approved_Date(sdfDate.format(iNewTatDA.getActivityTime()));
			tatReportCase.setDeferral_Approved_Time(sdfTime.format(iNewTatDA.getActivityTime()));
			tatReportCase.setActual_deferral_Approved_datetime(sdfTime1.format(iNewTatDA.getActualActivityTime()).toString());
	    	}
		}
		if("DEFERRAL_RAISED".equals(tat.getStatus())){
			
		tatReportCase.setDeferral_Raised_Date(sdfDate.format(tat.getActivityTime()));
		tatReportCase.setDeferral_Raised_Time(sdfTime.format(tat.getActivityTime()));
		tatReportCase.setActual_deferral_Raised_datetime(sdfTime1.format(tat.getActualActivityTime()).toString());
		}else{
			INewTat iNewTatDR= newTatJdbcImpl.getTatDetail(String.valueOf(tat.getCaseId()),"DEFERRAL_RAISED");
	    	if(iNewTatDR!=null){
			tatReportCase.setDeferral_Raised_Date(sdfDate.format(iNewTatDR.getActivityTime()));
			tatReportCase.setDeferral_Raised_Time(sdfTime.format(iNewTatDR.getActivityTime()));
			tatReportCase.setActual_deferral_Raised_datetime(sdfTime1.format(iNewTatDR.getActualActivityTime()).toString());
	    	}
		}
		if("DOCUMENT_RECEIVED".equals(tat.getStatus())){
			INewTat iNewTatFirst= newTatJdbcImpl.getFirstSubmitTime(String.valueOf(tat.getCaseId()),"DOCUMENT_RECEIVED");
			if(iNewTatFirst !=null && iNewTatFirst.getActivityTime()!=null){
				startDate =iNewTatFirst.getActivityTime();
			}
			else{
				startDate =tat.getActivityTime();
			}
		tatReportCase.setLimit_document_received_date(sdfDate.format(tat.getActivityTime()));
		tatReportCase.setLimit_Document_received_time(sdfTime.format(tat.getActivityTime()));
		tatReportCase.setActual_limit_document_received_datetime(sdfTime1.format(tat.getActualActivityTime()).toString());
		}else{
			INewTat iNewTatFirst= newTatJdbcImpl.getFirstSubmitTime(String.valueOf(tat.getCaseId()),"DOCUMENT_RECEIVED");
			INewTat iNewTatR= newTatJdbcImpl.getTatDetail(String.valueOf(tat.getCaseId()),"DOCUMENT_RECEIVED");
	    	if(iNewTatR!=null){
	    	startDate =iNewTatFirst.getActivityTime();	
			tatReportCase.setLimit_document_received_date(sdfDate.format(iNewTatR.getActivityTime()));
			tatReportCase.setLimit_Document_received_time(sdfTime.format(iNewTatR.getActivityTime()));
			tatReportCase.setActual_limit_document_received_datetime(sdfTime1.format(iNewTatR.getActualActivityTime()).toString());
	    	}	
		}
		if("DOCUMENT_SCANNED".equals(tat.getStatus())){
		tatReportCase.setLimit_document_Scanned_Date(sdfDate.format(tat.getActivityTime()));
		tatReportCase.setLimit_Document_Scanned_Time(sdfTime.format(tat.getActivityTime()));
		tatReportCase.setActual_limit_Document_Scanned_datetime(sdfTime1.format(tat.getActualActivityTime()).toString());
		}else{
			INewTat iNewTatDS= newTatJdbcImpl.getTatDetail(String.valueOf(tat.getCaseId()),"DOCUMENT_SCANNED");
	    	if(iNewTatDS!=null){
			tatReportCase.setLimit_document_Scanned_Date(sdfDate.format(iNewTatDS.getActivityTime()));
			tatReportCase.setLimit_Document_Scanned_Time(sdfTime.format(iNewTatDS.getActivityTime()));
			tatReportCase.setActual_limit_Document_Scanned_datetime(sdfTime1.format(iNewTatDS.getActualActivityTime()).toString());
	    	}
		}
		if("DOCUMENT_SUBMITTED".equals(tat.getStatus())){
		tatReportCase.setrM_Document_submitted_date(sdfDate.format(tat.getActivityTime()));
		tatReportCase.setrM_Document_Submitted_Time(sdfTime.format(tat.getActivityTime()));
		tatReportCase.setrM_Actual_Document_Submitted_Datetime(sdfTime1.format(tat.getActualActivityTime()).toString());
		}else{
			if("FTNR".equals(tat.getType())){
				INewTat iNewTatS= newTatJdbcImpl.getTatDetail(String.valueOf(tat.getCaseId()),"DOCUMENT_SUBMITTED");
		    	if(iNewTatS!=null){
				tatReportCase.setrM_Document_submitted_date(sdfDate.format(iNewTatS.getActivityTime()));
				tatReportCase.setrM_Document_Submitted_Time(sdfTime.format(iNewTatS.getActivityTime()));
				tatReportCase.setrM_Actual_Document_Submitted_Datetime(sdfTime1.format(iNewTatS.getActualActivityTime()).toString());
		    	}
			}else{
			INewTat iNewTatS= newTatJdbcImpl.getTatDetail(String.valueOf(tat.getCaseId()),"DOCUMENT_SUBMITTED");
	    	if(iNewTatS!=null){
			tatReportCase.setrM_Document_submitted_date(sdfDate.format(iNewTatS.getActivityTime()));
			tatReportCase.setrM_Document_Submitted_Time(sdfTime.format(iNewTatS.getActivityTime()));
			tatReportCase.setrM_Actual_Document_Submitted_Datetime(sdfTime1.format(iNewTatS.getActualActivityTime()).toString());
	    	}
			}
		}
		
		tatReportCase.setCustomer_Segment(tat.getSegment());
		
		long id =newTatJdbcImpl.getNewTatReportCaseByCaseId(String.valueOf(tat.getCaseId()));
		tatReportCase.setId(id);
    	ArrayList newTatListArray= new ArrayList(); 
    	
    	
		tatReportCase.setEvent(tat.getModule());
//		tatReportCase.setId(tat);
		
    	
    	
		tatReportCase.setLimit_released_amount(tat.getAmount());
		tatReportCase.setLine_no(tat.getLineNumber());
		tatReportCase.setParty_ID(tat.getLspLeId());
		tatReportCase.setVersionTime(tat.getVersionTime());
		tatReportCase.setParty_Name(tat.getLspShortName());
		tatReportCase.setParty_Region(tat.getRegion());
		tatReportCase.setParty_RM(tat.getRelationshipManager());
		
		tatReportCase.setrM_Region(tat.getRmRegion());
		tatReportCase.setSerial_No(tat.getSerialNumber());
		String tatBurst = NewTatUtil.getTatBurst(tat.getModule());
		long lTatBurst = 0l;
		 SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		
		if(tatBurst!= null){
		 lTatBurst= Long.parseLong(tatBurst);
		}
		if(finalDate != null && startDate!= null){
			
			 String str =  newTatJdbcImpl.getDifferenceInMin(df.format(finalDate).toString(),finalDate.getHours()+":"+finalDate.getMinutes(),df.format(startDate).toString(),startDate.getHours()+":"+startDate.getMinutes(), tat.getModule(),tat.getCaseId());
				int hour = 0;
				int minutes = 0;
					 String segments[] = str.split(":");
					 if(segments.length>0)
						 hour = Integer.parseInt(segments[0])*60;
					 if(segments.length>0)
						 minutes = Integer.parseInt(segments[1]);
					 
					 long time = hour + minutes;	
			
			
		// long datediff = com.integrosys.cms.app.common.util.CommonUtil.dateDiff(finalDate,startDate, 12);
		 
		 if(time >lTatBurst){
			 tatReportCase.setTurn_Around_Time("OUTSIDE");
		 }else{
			 tatReportCase.setTurn_Around_Time("WITHIN");
		 }
		 
		 int hr=0;
		 int min=0;
		 if(time>=60){
		  hr =(int) (time/60);
		  min = (int) (time%60);
		 }else{
			 min=(int) time;
		 }
		tatReportCase.setTotal_Time(hr+" : "+min +" HH:MM");
		 
		}
		
		
		return tatReportCase;
	}

}
