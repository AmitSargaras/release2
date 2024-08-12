package com.integrosys.cms.ui.discrepency;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.discrepency.bus.IDiscrepancyJdbc;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;
import com.integrosys.cms.app.discrepency.bus.OBDiscrepency;
import com.integrosys.cms.app.discrepency.proxy.IDiscrepencyProxyManager;
import com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue;
import com.integrosys.cms.app.discrepency.trx.OBDiscrepencyTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * This Class is used for showing list of Discrepency
 * 
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 01/01/2011 02:37:00 $ Tag: $Name: $
 */

public class ListDiscrepencyCommand extends AbstractCommand {

	private IDiscrepencyProxyManager discrepencyProxy;
	
	public IDiscrepencyProxyManager getDiscrepencyProxy() {
		return discrepencyProxy;
	}

	public void setDiscrepencyProxy(IDiscrepencyProxyManager discrepencyProxy) {
		this.discrepencyProxy = discrepencyProxy;
	}

	public ListDiscrepencyCommand() {
	}

	/**
	 * Defines 2-D array to be passed to doExecute Method by HashMap
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "legalCustomerId", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",GLOBAL_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{"searchstatus","java.lang.String",REQUEST_SCOPE},
				{"discType","java.lang.String",REQUEST_SCOPE},
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "stgActualMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "session.discrepencyList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
				{ "checkerEvent", "java.lang.String", REQUEST_SCOPE },
				{ "discrepancyCreateList", "java.util.List", SERVICE_SCOPE },
				{ "discrepencyList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
			});
	}
	
	/**
	 * Defines 2-D array with ResultList expected doExecute Method using HashMap
	 */

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "legalCustomerId", "java.lang.String", REQUEST_SCOPE },
				{ "discrepencyList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "session.discrepencyList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "searchstatus", "java.lang.String", REQUEST_SCOPE },
				{ "stgActualMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "discType", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "discrepancyCreateList", "java.util.List", SERVICE_SCOPE },
				{ "checkerEvent", "java.lang.String", REQUEST_SCOPE }
			});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap. Here Listing of City is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException,CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		SearchResult discrepencyList =null;
		SearchResult discrepencyStageList =null;
		IDiscrepancyJdbc discrepencyJdbc= (IDiscrepancyJdbc)BeanHouse.get("discrepencyJdbc");
		ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		String startIdx = (String) map.get("startIndex");
		String event = (String) map.get("event");
		String checkerEvent = (String) map.get("checkerEvent");
		HashMap selectedArrayMap = (HashMap) map.get("selectedArrayMap");
		DefaultLogger.debug(this, " doExecute : ListDiscrepencyCommand ");
		 // ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
	      //  long limitProfileID = limit.getLimitProfileID();
		
		 if(event.equals("search_discrepency")||event.equals("return_list_discrepency")){
					 try{
						 SearchResult discrepencyList1 =null;
						 
						 ArrayList discrepancyTrxList= new ArrayList();
						 
						
							ArrayList discrepancyObjList= new ArrayList();
							//discrepencyList = getDiscrepencyProxy().listDiscrepency(customer.getCustomerID());
							discrepancyTrxList = discrepencyJdbc.listDiscrepancy(customer.getCustomerID());
							
							for(int i=0;i<discrepancyTrxList.size();i++){
								
								
								String trxId=(String) discrepancyTrxList.get(i);
								IDiscrepencyTrxValue discrepencyTrxValue=getDiscrepencyProxy().getDiscrepencyByTrxID(trxId);
								discrepancyObjList.add(discrepencyTrxValue);
							}
							discrepencyList1 = new SearchResult(0, discrepancyObjList.size(), discrepancyObjList.size(),discrepancyObjList);
							
							
							//discrepencyList1=getDiscrepencyProxy().listDiscrepency(customer.getCustomerID());
						 
							//SearchResult returnList=getSortList(discrepencyList1);
							HashMap valuesMap= getSortListTrx(discrepencyList1);
							SearchResult returnList=(SearchResult)valuesMap.get("searchResult");
							ArrayList searchList = new ArrayList();
							searchList=(ArrayList)returnList.getResultList();
							ArrayList discrepancyCreateList= (ArrayList)map.get("discrepancyCreateList");

							if(event.equals("return_list_discrepency")&& discrepancyCreateList!=null){
								discrepancyCreateList.clear();
							}
						 
						 if((Integer.parseInt(startIdx)>=10)&& event.equals("search_discrepency")
								 ||searchList.size()<=10){
						 startIdx="0";
						 }
						 
						String searchstatus=(String)map.get("searchstatus");
						String discType=(String)map.get("discType");
						if(!searchstatus.equalsIgnoreCase("Please")|| !discType.equalsIgnoreCase("Please")){
						ArrayList sessiondiscrepencyList =new ArrayList();
						 
						for(int i=0;i<searchList.size();i++){
								OBDiscrepency searchDiscrepency=(OBDiscrepency)searchList.get(i);
								
								if(searchstatus.equalsIgnoreCase("PENDING_DEFER")
										||searchstatus.equalsIgnoreCase("PENDING_CLOSE")
										||searchstatus.equalsIgnoreCase("PENDING_WAIVE")
										||searchstatus.equalsIgnoreCase("PENDING_UPDATE")){
									if(!searchstatus.equals("")&& discType.equals("Please")){
										if(searchstatus.equalsIgnoreCase(searchDiscrepency.getTransactionStatus())){
											sessiondiscrepencyList.add(searchDiscrepency);
											
										}	
									}
									else if(!discType.equals("")&& searchstatus.equals("Please")){
										if(discType.equalsIgnoreCase(searchDiscrepency.getTransactionStatus())){
											sessiondiscrepencyList.add(searchDiscrepency);
										}
										
									}
									else if(!discType.equals("")&&!searchstatus.equals("")){
										if(discType.equalsIgnoreCase(searchDiscrepency.getDiscrepencyType())&& 
												searchstatus.equalsIgnoreCase(searchDiscrepency.getTransactionStatus())){
											sessiondiscrepencyList.add(searchDiscrepency);
										}
										
									}
								}
								
								else{
									if(!searchstatus.equals("")&& discType.equals("Please")){
										if(searchstatus.equalsIgnoreCase(searchDiscrepency.getStatus())&&
												searchDiscrepency.getTransactionStatus().equalsIgnoreCase("ACTIVE")){
											sessiondiscrepencyList.add(searchDiscrepency);
											
										}	
									}
									else if(!discType.equals("")&& searchstatus.equals("Please")){
										if(discType.equalsIgnoreCase(searchDiscrepency.getDiscrepencyType())){
											sessiondiscrepencyList.add(searchDiscrepency);
										}
										
									}
									else if(!discType.equals("")&&!searchstatus.equals("")){
										if(discType.equalsIgnoreCase(searchDiscrepency.getDiscrepencyType())&& 
												searchstatus.equalsIgnoreCase(searchDiscrepency.getStatus())&&
												searchDiscrepency.getTransactionStatus().equalsIgnoreCase("ACTIVE")){
											sessiondiscrepencyList.add(searchDiscrepency);
										}
										
									}
								}
						}
						if(sessiondiscrepencyList.size()<=10){
							 startIdx="0";
						}
						resultMap.put("discrepencyList", new SearchResult(0, 0, sessiondiscrepencyList.size(),sessiondiscrepencyList));
						}
						else{
							resultMap.put("discrepencyList",returnList);
						}
						
						resultMap.put("session.discrepencyList",map.get("session.discrepencyList"));
						resultMap.put("checkerEvent", event);
						resultMap.put("startIndex", startIdx);
						resultMap.put("stgActualMap", valuesMap.get("stgActualMap"));
						resultMap.put("event", event);
						resultMap.put("searchstatus", searchstatus);
						resultMap.put("discType", discType);
						resultMap.put("discrepancyCreateList", discrepancyCreateList);
						resultMap.put("legalCustomerId",String.valueOf(customer.getCustomerID()));
				 }
				 catch(Exception e){
					 CommandProcessingException cpe = new CommandProcessingException(
								"Internal Error While Processing '" + discrepencyList + "'");
						cpe.initCause(e);
						throw cpe;
					 
				 }
				
		}
		 else if(event.equals("next_list_discrepency")){
			 try {
					//discrepencyList = getDiscrepencyProxy().listDiscrepency(customer.getCustomerID());
					resultMap.put("discrepencyList", map.get("discrepencyList"));
					resultMap.put("session.discrepencyList", map.get("session.discrepencyList"));
					resultMap.put("startIndex", startIdx);
					resultMap.put("checkerEvent", event);
					resultMap.put("searchstatus", map.get("searchstatus"));
					resultMap.put("discType", map.get("discType"));
					resultMap.put("event", event);
					resultMap.put("legalCustomerId",String.valueOf(customer.getCustomerID()));
				}  catch (Exception e) {
					CommandProcessingException cpe = new CommandProcessingException(
							"Internal Error While Processing '" + discrepencyList + "'");
					cpe.initCause(e);
					throw cpe;
				}
				
				
			}
		 
		
		 else {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				ArrayList discrepancyTrxList= new ArrayList();
				ArrayList discrepancyObjList= new ArrayList();
				//discrepencyList = getDiscrepencyProxy().listDiscrepency(customer.getCustomerID());
				discrepancyTrxList = discrepencyJdbc.listDiscrepancy(customer.getCustomerID());
				
				for(int i=0;i<discrepancyTrxList.size();i++){
					
					
					String trxId=(String) discrepancyTrxList.get(i);
					IDiscrepencyTrxValue discrepencyTrxValue=getDiscrepencyProxy().getDiscrepencyByTrxID(trxId);
					discrepancyObjList.add(discrepencyTrxValue);
				}
				discrepencyList = new SearchResult(0, discrepancyObjList.size(), discrepancyObjList.size(),discrepancyObjList);
				HashMap valuesMap= getSortListTrx(discrepencyList);
				SearchResult returnList=(SearchResult)valuesMap.get("searchResult");
				/*ArrayList openList = new ArrayList();
				ArrayList closeList = new ArrayList();
				ArrayList deferList = new ArrayList();
				ArrayList waiveList = new ArrayList();
				ArrayList pendingUpdateList = new ArrayList();
				ArrayList pendingDeferList = new ArrayList();
				ArrayList pendingCloseList = new ArrayList();
				ArrayList pendingWaiveList = new ArrayList();
				
				
				ArrayList sortedList = new ArrayList();
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
					}else if(searchDiscrepency.getTransactionStatus().equalsIgnoreCase("PENDING_UPDATE")){
						pendingUpdateList.add(searchDiscrepency);
					}
					else if(searchDiscrepency.getTransactionStatus().equalsIgnoreCase("PENDING_DEFER")){
						pendingDeferList.add(searchDiscrepency);
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
				
				if(pendingUpdateList!=null||pendingUpdateList.size()>0||!pendingUpdateList.isEmpty()){
					sortedList.addAll(pendingUpdateList);
					}
				if(pendingDeferList!=null||pendingDeferList.size()>0||!pendingDeferList.isEmpty()){
					sortedList.addAll(pendingDeferList);
					}
				if(pendingCloseList!=null||pendingCloseList.size()>0||!pendingCloseList.isEmpty()){
					sortedList.addAll(pendingCloseList);
					}
				if(pendingWaiveList!=null||pendingWaiveList.size()>0||!pendingWaiveList.isEmpty()){
					sortedList.addAll(pendingWaiveList);
					}*/
				
				
				
				resultMap.put("discrepencyList", returnList);
				resultMap.put("session.discrepencyList",returnList);
				resultMap.put("stgActualMap", valuesMap.get("stgActualMap"));
				resultMap.put("startIndex", startIdx);
				resultMap.put("checkerEvent", event);
				resultMap.put("event", event);
				resultMap.put("legalCustomerId",String.valueOf(customer.getCustomerID()));
			}  catch (Exception e) {
				CommandProcessingException cpe = new CommandProcessingException(
						"Internal Error While Processing '" + discrepencyList + "'");
				cpe.initCause(e);
				throw cpe;
			}
			
			
		}
		 if(selectedArrayMap==null ){
			 selectedArrayMap = new HashMap();			 
		 }
		 else{
			 selectedArrayMap.clear();
		 }
		 resultMap.put("selectedArrayMap", selectedArrayMap);
		 returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
	}
	/** method for sorting discrepancy based on status */
	public SearchResult getSortList(SearchResult discrepencyList)
		{
		SearchResult returnList=null;
			ArrayList openList = new ArrayList();
			ArrayList closeList = new ArrayList();
			ArrayList deferList = new ArrayList();
			ArrayList waiveList = new ArrayList();
			ArrayList pendingUpdateList = new ArrayList();
			ArrayList pendingDeferList = new ArrayList();
			ArrayList pendingCloseList = new ArrayList();
			ArrayList pendingWaiveList = new ArrayList();
			
			
			ArrayList sortedList = new ArrayList();
			 ArrayList searchList = new ArrayList();
			searchList=(ArrayList)discrepencyList.getResultList();
			for(int i=0;i<searchList.size();i++){
				OBDiscrepency searchDiscrepency=(OBDiscrepency)searchList.get(i);
				if((searchDiscrepency.getStatus()!=null &&!searchDiscrepency.getStatus().equals("")
						&&!searchDiscrepency.getStatus().equalsIgnoreCase(null))&&(searchDiscrepency.getTransactionStatus()!=null &&!searchDiscrepency.getTransactionStatus().equals("")
						&&!searchDiscrepency.getTransactionStatus().equalsIgnoreCase(null))){
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
				}else if(searchDiscrepency.getTransactionStatus().equalsIgnoreCase("PENDING_UPDATE")){
					pendingUpdateList.add(searchDiscrepency);
				}
				else if(searchDiscrepency.getTransactionStatus().equalsIgnoreCase("PENDING_DEFER")){
					pendingDeferList.add(searchDiscrepency);
				}
				else if(searchDiscrepency.getTransactionStatus().equalsIgnoreCase("PENDING_CLOSE")){
					pendingCloseList.add(searchDiscrepency);
				}
				else if(searchDiscrepency.getTransactionStatus().equalsIgnoreCase("PENDING_WAIVE")){
					pendingWaiveList.add(searchDiscrepency);
				}
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
			
			if(pendingUpdateList!=null||pendingUpdateList.size()>0||!pendingUpdateList.isEmpty()){
				sortedList.addAll(pendingUpdateList);
				}
			if(pendingDeferList!=null||pendingDeferList.size()>0||!pendingDeferList.isEmpty()){
				sortedList.addAll(pendingDeferList);
				}
			if(pendingCloseList!=null||pendingCloseList.size()>0||!pendingCloseList.isEmpty()){
				sortedList.addAll(pendingCloseList);
				}
			if(pendingWaiveList!=null||pendingWaiveList.size()>0||!pendingWaiveList.isEmpty()){
				sortedList.addAll(pendingWaiveList);
				}
			
			return returnList=	new SearchResult(0, 0, sortedList.size(),sortedList);
	}
	
	
	
	
	
	public HashMap getSortListTrx(SearchResult discrepencyList)
	{
		
		HashMap returnActualMap= new HashMap();
		
	SearchResult returnList=null;
		ArrayList openList = new ArrayList();
		ArrayList closeList = new ArrayList();
		ArrayList deferList = new ArrayList();
		ArrayList waiveList = new ArrayList();
		ArrayList pendingUpdateList = new ArrayList();
		ArrayList pendingDeferList = new ArrayList();
		ArrayList pendingCloseList = new ArrayList();
		ArrayList pendingWaiveList = new ArrayList();
		HashMap actualStagingMap= new HashMap();
		
		ArrayList sortedList = new ArrayList();
		 ArrayList searchList = new ArrayList();
		searchList=(ArrayList)discrepencyList.getResultList();
		for(int i=0;i<searchList.size();i++){
			OBDiscrepencyTrxValue searchDiscrepencyTrx=(OBDiscrepencyTrxValue)searchList.get(i);
			IDiscrepency searchDiscrepency=null;
			if(searchDiscrepencyTrx.getStatus().equalsIgnoreCase("ACTIVE")){
				searchDiscrepency=searchDiscrepencyTrx.getActualDiscrepency();
				actualStagingMap.put(String.valueOf(searchDiscrepency.getId()), String.valueOf(searchDiscrepency.getId()));
			}else{
				searchDiscrepency=searchDiscrepencyTrx.getStagingDiscrepency();
				actualStagingMap.put(String.valueOf(searchDiscrepency.getId()), String.valueOf(searchDiscrepencyTrx.getActualDiscrepency().getId()));
			}
			if((searchDiscrepency.getStatus()!=null &&!searchDiscrepency.getStatus().equals("")
					&&!searchDiscrepency.getStatus().equalsIgnoreCase(null))&&(searchDiscrepency.getTransactionStatus()!=null &&!searchDiscrepency.getTransactionStatus().equals("")
							&&!searchDiscrepency.getTransactionStatus().equalsIgnoreCase(null))){
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
			}else 
			    if(searchDiscrepency.getTransactionStatus().equalsIgnoreCase("PENDING_UPDATE")){
				pendingUpdateList.add(searchDiscrepency);
			}
			else if(searchDiscrepency.getTransactionStatus().equalsIgnoreCase("PENDING_DEFER")){
				pendingDeferList.add(searchDiscrepency);
			}
			else if(searchDiscrepency.getTransactionStatus().equalsIgnoreCase("PENDING_CLOSE")){
				pendingCloseList.add(searchDiscrepency);
			}
			else if(searchDiscrepency.getTransactionStatus().equalsIgnoreCase("PENDING_WAIVE")){
				pendingWaiveList.add(searchDiscrepency);
			}
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
		
		if(pendingUpdateList!=null||pendingUpdateList.size()>0||!pendingUpdateList.isEmpty()){
			sortedList.addAll(pendingUpdateList);
			}
		if(pendingDeferList!=null||pendingDeferList.size()>0||!pendingDeferList.isEmpty()){
			sortedList.addAll(pendingDeferList);
			}
		if(pendingCloseList!=null||pendingCloseList.size()>0||!pendingCloseList.isEmpty()){
			sortedList.addAll(pendingCloseList);
			}
		if(pendingWaiveList!=null||pendingWaiveList.size()>0||!pendingWaiveList.isEmpty()){
			sortedList.addAll(pendingWaiveList);
			}
		
		
		
		returnList=	new SearchResult(0, 0, sortedList.size(),sortedList);
		returnActualMap.put("searchResult", returnList);
		returnActualMap.put("stgActualMap", actualStagingMap);
		return returnActualMap;
}
}
