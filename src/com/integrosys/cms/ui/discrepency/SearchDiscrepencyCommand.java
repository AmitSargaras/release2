package com.integrosys.cms.ui.discrepency;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.discrepency.bus.OBDiscrepency;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class SearchDiscrepencyCommand extends AbstractCommand {

	public SearchDiscrepencyCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{"status","java.lang.String",REQUEST_SCOPE},
				{"discType","java.lang.String",REQUEST_SCOPE},
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "session.searchResult", "java.util.HashMap", SERVICE_SCOPE },
				{ "session.discrepencyList", "java.util.List", SERVICE_SCOPE}
			
			});
	}

	/**
	 * Defines 2-D array with ResultList expected doExecute Method using HashMap
	 */

	public String[][] getResultDescriptor() {
		return (new String[][] {

				{ "legalCustomerId", "java.lang.String", REQUEST_SCOPE },
								{ "discrepencyList", "java.util.List", REQUEST_SCOPE },
								{ "session.discrepencyList", "java.util.List", SERVICE_SCOPE },
								{ "startIndex", "java.lang.String", REQUEST_SCOPE },
								{ "searchResult", "java.util.HashMap", SERVICE_SCOPE },
								{ "event", "java.lang.String", REQUEST_SCOPE }
				
			});
	}
	
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException,CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		SearchResult discrepencyList =null;
		ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		
		String startIdx = (String) map.get("startIndex");
		String event = (String) map.get("event");
		String status=(String)map.get("status");
		String discType=(String)map.get("discType");
		//String checkerEvent = (String) map.get("checkerEvent");
		DefaultLogger.debug(this, " doExecute : ListDiscrepencyCommand ");
		 // ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
	      //  long limitProfileID = limit.getLimitProfileID();
		
		 
				 try{
					 ArrayList openList = new ArrayList();
						ArrayList closeList = new ArrayList();
						ArrayList deferList = new ArrayList();
						ArrayList waiveList = new ArrayList();
						ArrayList pendinfDeferList = new ArrayList();
						ArrayList pendingCloseList = new ArrayList();
						ArrayList pendingWaiveList = new ArrayList();
						ArrayList sortedList = new ArrayList();
						 startIdx="0";
						
					 if(event.equals("search_discrepency")){
						 discrepencyList=(SearchResult)map.get("session.discrepencyList");
					 }
					 else if(event.equals("checker_search_discrepency")){
						 discrepencyList=(SearchResult)map.get("session.searchResult");
					 }
					ArrayList sessiondiscrepencyList =new ArrayList();
					 ArrayList searchList = new ArrayList();
					searchList=(ArrayList)discrepencyList.getResultList();
					for(int i=0;i<searchList.size();i++){
						OBDiscrepency searchDiscrepency=(OBDiscrepency)searchList.get(i);
						if(searchDiscrepency.getStatus().equalsIgnoreCase("ACTIVE")&& searchDiscrepency.getTransactionStatus().equalsIgnoreCase("ACTIVE")){
							openList.add(searchDiscrepency);
						}
						else if(searchDiscrepency.getStatus().equalsIgnoreCase("CLOSED")&& searchDiscrepency.getTransactionStatus().equalsIgnoreCase("ACTIVE")){
							closeList.add(searchDiscrepency);
						}
						else if(searchDiscrepency.getStatus().equalsIgnoreCase("DEFERED")&& searchDiscrepency.getTransactionStatus().equalsIgnoreCase("ACTIVE")){
							deferList.add(searchDiscrepency);
						}
						else if(searchDiscrepency.getStatus().equalsIgnoreCase("WAIVED")&& searchDiscrepency.getTransactionStatus().equalsIgnoreCase("ACTIVE")){
							waiveList.add(searchDiscrepency);
						}
						else if(searchDiscrepency.getTransactionStatus().equalsIgnoreCase("PENDING_DEFER")){
							pendinfDeferList.add(searchDiscrepency);
						}
						else if(searchDiscrepency.getTransactionStatus().equalsIgnoreCase("PENDING_CLOSE")){
							pendingCloseList.add(searchDiscrepency);
						}
						else if(searchDiscrepency.getTransactionStatus().equalsIgnoreCase("PENDING_WAIVE")){
							pendingWaiveList.add(searchDiscrepency);
						}
							
						}	
					if(openList!=null||openList.size()>0||!openList.isEmpty()){						
						sortedList.addAll(openList);
					}
					if(deferList!=null||deferList.size()>0||!deferList.isEmpty()){
					sortedList.addAll(deferList);
					}
					if(closeList!=null||closeList.size()>0||!closeList.isEmpty()){
					sortedList.addAll(closeList);
					}
					if(waiveList!=null||waiveList.size()>0||!waiveList.isEmpty()){
					sortedList.addAll(waiveList);
					}
					if(pendinfDeferList!=null||pendinfDeferList.size()>0||!pendinfDeferList.isEmpty()){
						sortedList.addAll(pendinfDeferList);
						}
					if(pendingCloseList!=null||pendingCloseList.size()>0||!pendingCloseList.isEmpty()){
						sortedList.addAll(pendingCloseList);
						}
					if(pendingWaiveList!=null||pendingWaiveList.size()>0||!pendingWaiveList.isEmpty()){
						sortedList.addAll(pendingWaiveList);
						}

					if(event.equals("search_discrepency")){
					resultMap.put("discrepencyList", new SearchResult(0, 0, sortedList.size(),sortedList));
					resultMap.put("session.discrepencyList",  new SearchResult(0, 0, sortedList.size(),sortedList));
					resultMap.put("checkerEvent", event);
					resultMap.put("startIndex", startIdx);
					resultMap.put("event", event);
					resultMap.put("legalCustomerId",String.valueOf(customer.getCustomerID()));
					}
					else if(event.equals("checker_search_discrepency")){
						resultMap.put("searchResult",  new SearchResult(0, 0, sortedList.size(),sortedList));
					 }
				 }
				 catch(Exception e){
					 CommandProcessingException cpe = new CommandProcessingException(
								"Internal Error While Processing '" + discrepencyList + "'");
						cpe.initCause(e);
						throw cpe;
					 
				 }
				
				 returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					return returnMap;
	}
	
	
}
