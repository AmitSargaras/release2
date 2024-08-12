package com.integrosys.cms.ui.discrepency;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;
import com.integrosys.cms.app.discrepency.bus.IDiscrepencyDAO;
import com.integrosys.cms.app.discrepency.bus.IDiscrepencyFacilityList;
import com.integrosys.cms.app.discrepency.bus.NoSuchDiscrepencyException;
import com.integrosys.cms.app.discrepency.bus.OBDiscrepency;
import com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue;
import com.integrosys.cms.app.limit.bus.LimitDAO;

public class MakerEditTempDiscrepencyCommmand extends AbstractCommand {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "discrepencyId", "java.lang.String", REQUEST_SCOPE },
				{ "discrepancyCreateList", "java.util.List", SERVICE_SCOPE },
				{ "discrepencyObj", "com.integrosys.cms.app.discrepency.bus.IDiscrepency",FORM_SCOPE },
				{ "discId", "java.lang.String", REQUEST_SCOPE },
				{ "checkId", "java.lang.String", REQUEST_SCOPE },
				{ "unCheckId", "java.lang.String", REQUEST_SCOPE },
				{ "searchstatus", "java.lang.String", REQUEST_SCOPE },
				{ "discType", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "startIndexInn", "java.lang.String", REQUEST_SCOPE },
				 { "session.discId", "java.lang.String", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }
		});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "discrepencyObj",	"com.integrosys.cms.app.discrepency.bus.IDiscrepency",FORM_SCOPE },
				{ "discrepencyObj",	"com.integrosys.cms.app.discrepency.bus.IDiscrepency",REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "discrepancyCreateList", "java.util.List", SERVICE_SCOPE },
						{ "transactionHistoryList", "java.util.List", SERVICE_SCOPE },
						{ "searchstatus", "java.lang.String", REQUEST_SCOPE },
						{ "discType", "java.lang.String", REQUEST_SCOPE },
						{ "startIndex", "java.lang.String", REQUEST_SCOPE },
						{ "startIndexInn", "java.lang.String", REQUEST_SCOPE },
						 { "migratedFlag", "java.lang.String", SERVICE_SCOPE },
						 { "event", "java.lang.String", REQUEST_SCOPE },
						 { "discId", "java.lang.String", REQUEST_SCOPE },
				{
						"IDiscrepencyTrxValue",
						"com.integrosys.cms.app.discrepency.discrepency.trx.IDiscrepencyTrxValue",
						SERVICE_SCOPE }
			});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
	CommandValidationException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		 List transactionHistoryList= new ArrayList();
		 String event = (String) map.get("event");
		 resultMap.put("startIndexInn", map.get("startIndexInn"));
		
			 try{
				 IDiscrepency discrepency = (IDiscrepency) map.get("discrepencyObj");
				 String checkId =(String) map.get("checkId");
				 String unCheckId =(String) map.get("unCheckId");
				 HashMap selectedArrayMap = (HashMap) map.get("selectedArrayMap");
					if(selectedArrayMap==null){ 
						selectedArrayMap = new HashMap();
					}
					if(checkId!=null && !checkId.equals("") ){
						
						String[] selected=checkId.split("-");
						if(selected!=null){
								for(int k=0;k<selected.length;k++){
									if(!selected[k].equals("")){
											selectedArrayMap.put(selected[k], selected[k]);
									}
								}
						}
					}
						
					if(unCheckId!=null && !unCheckId.equals("")){
						String[] unchecked=unCheckId.split("-");
						if(unchecked!=null){
								for(int ak=0;ak<unchecked.length;ak++){
									if(!unchecked[ak].equals("")){
											selectedArrayMap.remove(unchecked[ak]);
									}
								}
							}
					}
					
					resultMap.put("selectedArrayMap", selectedArrayMap);	
					
				 String discId=(String)map.get("discId");
				 if(discId==null||discId.equalsIgnoreCase("null")){
					 discId=(String)map.get("session.discId");
				 }
				 OBDiscrepency viewObj=new OBDiscrepency();
				 ArrayList discrepancyCreateList= (ArrayList)map.get("discrepancyCreateList");
				 for(int i=0;i<discrepancyCreateList.size();i++){
					 if(String.valueOf(i).equals(discId)){
						  viewObj=(OBDiscrepency)discrepancyCreateList.get(i);
						  viewObj.setAcceptedDate(discrepency.getAcceptedDate());						
						  viewObj.setCreationDate(discrepency.getCreationDate());
						  viewObj.setCreditApprover(discrepency.getCreditApprover());
						  viewObj.setCritical(discrepency.getCritical());
						  viewObj.setOriginalTargetDate(discrepency.getOriginalTargetDate());
						  viewObj.setNextDueDate(discrepency.getNextDueDate());
						  viewObj.setDiscrepency(discrepency.getDiscrepency());
						  viewObj.setDiscrepencyRemark(discrepency.getDiscrepencyRemark());
						  viewObj.setApprovedBy(discrepency.getApprovedBy());
						  
						  Date creationDate=discrepency.getCreationDate();
							Date originalDate=discrepency.getOriginalTargetDate();
							Date nextDate=discrepency.getNextDueDate();
							Date deferDate = discrepency.getDeferDate();
							if(nextDate!=null){
							String originalDays=calculateDays(nextDate,originalDate);
							viewObj.setOriginalDeferedDays(originalDays);
							}
							if(nextDate!=null){
								String totalDays=calculateDays(nextDate,creationDate);
								viewObj.setTotalDeferedDays(totalDays);
								}
						
					 }
				 }
				 resultMap.put("event", event);
				 resultMap.put("discrepencyObj", viewObj);
				 resultMap.put("discrepancyCreateList", discrepancyCreateList);
				resultMap.put("searchstatus", map.get("searchstatus"));
				resultMap.put("discType",map.get("discType"));
				resultMap.put("startIndex", map.get("startIndex"));
				resultMap.put("discId",discId);
				resultMap.put("startIndex", map.get("startIndex"));
				resultMap.put("startIndexInn", map.get("startIndexInn"));
				resultMap.put("searchstatus", map.get("searchstatus"));
				resultMap.put("discType",map.get("discType"));
			 	}
			 catch (NoSuchDiscrepencyException ex) {
					DefaultLogger.debug(this, "got exception in doExecute" + ex);
					ex.printStackTrace();
					throw (new CommandProcessingException(ex.getMessage()));
				}
		 
		 
		 
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	
	
	}
	
public String calculateDays(Date nextDate,Date calculateDate){
		
		
		Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(calculateDate.getYear(),calculateDate.getMonth(),calculateDate.getDate());
		calendar2.set(nextDate.getYear(), nextDate.getMonth(), nextDate.getDate());
		  long milliseconds1 = calendar1.getTimeInMillis();
		  long milliseconds2 = calendar2.getTimeInMillis();
		  long diff =  milliseconds2-milliseconds1;
		  long diffSeconds = diff / 1000;
		  long diffMinutes = diff / (60 * 1000);
		  long diffHours = diff / (60 * 60 * 1000);
		  long diffDays = diff / (24 * 60 * 60 * 1000);
		  String days=String.valueOf(diffDays);
		  return days;
        
	}

}
