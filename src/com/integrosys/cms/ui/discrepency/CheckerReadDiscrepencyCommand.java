package com.integrosys.cms.ui.discrepency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;
import com.integrosys.cms.app.discrepency.bus.IDiscrepencyDAO;
import com.integrosys.cms.app.discrepency.bus.IDiscrepencyFacilityList;
import com.integrosys.cms.app.discrepency.bus.NoSuchDiscrepencyException;
import com.integrosys.cms.app.discrepency.bus.OBDiscrepency;
import com.integrosys.cms.app.discrepency.bus.OBDiscrepencyFacilityList;
import com.integrosys.cms.app.discrepency.proxy.IDiscrepencyProxyManager;
import com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue;
import com.integrosys.cms.app.discrepency.trx.OBDiscrepencyTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/02/2011 02:37:00 $ Tag: $Name: $
 */

public class CheckerReadDiscrepencyCommand extends AbstractCommand implements
		ICommonEventConstant {

	private IDiscrepencyProxyManager discrepencyProxy;
	
	public IDiscrepencyProxyManager getDiscrepencyProxy() {
		return discrepencyProxy;
	}

	public void setDiscrepencyProxy(IDiscrepencyProxyManager discrepencyProxy) {
		this.discrepencyProxy = discrepencyProxy;
	}

	public CheckerReadDiscrepencyCommand() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,class name,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ "ID", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
				{ "loginId", "java.lang.String", REQUEST_SCOPE },
				{ "transactionDate", "java.lang.String", REQUEST_SCOPE },
				{ "checkId", "java.lang.String", REQUEST_SCOPE },
				{ "unCheckId", "java.lang.String", REQUEST_SCOPE },
				{"searchstatus","java.lang.String",REQUEST_SCOPE},
				{ "session.Id", "java.lang.String", SERVICE_SCOPE },
				{"discType","java.lang.String",REQUEST_SCOPE},
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "session.searchResult", "java.util.HashMap", SERVICE_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{"IDiscrepencyTrxValue",
					"com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue",
					SERVICE_SCOPE }});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,class name,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "discrepencyObj",
						"com.integrosys.cms.app.discrepency.bus.IDiscrepency",
						FORM_SCOPE },
				{"discrepencyObj","com.integrosys.cms.app.discrepency.bus.OBDiscrepency",
							REQUEST_SCOPE },
				{"discrepencyObj","com.integrosys.cms.app.discrepency.bus.OBDiscrepency",
								SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
				{"searchstatus","java.lang.String",REQUEST_SCOPE},
				{ "transactionDate", "java.lang.String", REQUEST_SCOPE },
				{"discType","java.lang.String",REQUEST_SCOPE},
				{ "loginId", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "selectedList", "java.util.List", REQUEST_SCOPE },
				{ "session.searchResult", "java.util.HashMap", SERVICE_SCOPE },
				{ "session.Id", "java.lang.String", SERVICE_SCOPE },
//				{ "facilityList", "java.util.List", REQUEST_SCOPE },
				{"IDiscrepencyTrxValue",
					"com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue",
					SERVICE_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException, NoSuchDiscrepencyException{
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String event=(String)map.get("event");
		String transactionDate=(String)map.get("transactionDate");
		String discrepencysessionID = (String) (map.get("ID"));
		String customerID=(String)map.get("customerID");
		String searchstatus=(String)map.get("searchstatus");
		String discType=(String)map.get("discType");
		String checkId =(String) map.get("checkId");
		String unCheckId =(String) map.get("unCheckId");
		ICMSCustomer custOB = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		String loginId=(String)map.get("loginId");
		HashMap selectedArrayMap = (HashMap) map.get("selectedArrayMap");
		resultMap.put("transactionDate", transactionDate);
		resultMap.put("loginId",loginId);
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
		
		if("prepare_maker_edit_reject_discrepency".equals(event)
				||"maker_view_reject_discrepency".equals(event)
				||"prepare_maker_edit_process_discrepency_waive".equals(event)
				||"prepare_maker_edit_process_discrepency_defer".equals(event)
				||"prepare_maker_edit_process_discrepency_close".equals(event)){
			try{
				if(discrepencysessionID==null){
					discrepencysessionID=(String)map.get("session.Id");
				}
				String custOB1=String.valueOf(custOB.getCustomerID());
				HashMap searchResult=(HashMap)map.get("session.searchResult");
				ArrayList searchList=(ArrayList)searchResult.get("list");
				if("maker_view_reject_discrepency".equals(event)||"prepare_maker_edit_reject_discrepency".equals(event)
						||"prepare_maker_edit_process_discrepency_waive".equals(event)
						||"prepare_maker_edit_process_discrepency_defer".equals(event)
						||"prepare_maker_edit_process_discrepency_close".equals(event)){
					String discrepencyTrx = (String) (map.get("TrxId"));				
					IDiscrepencyTrxValue trxValue = (OBDiscrepencyTrxValue) getDiscrepencyProxy().getDiscrepencyByTrxID(discrepencyTrx);
					resultMap.put("IDiscrepencyTrxValue",trxValue);
				}
				
				for(int i=0;i<searchList.size();i++){
					OBDiscrepency objDisc=(OBDiscrepency)searchList.get(i);
					String objID=String.valueOf(objDisc.getId());
					
					if(objID.equals(discrepencysessionID)){
						if(objDisc.getDiscrepencyType().equalsIgnoreCase("facility")){
							IDiscrepencyDAO discrepencyDAO=(IDiscrepencyDAO)BeanHouse.get("discrepencyDAO");
							SearchResult result= discrepencyDAO.listDiscrepencyFacility("stagingDiscrepencyFacilityList", objDisc.getId());
							Collection resultList = null;
							IDiscrepencyFacilityList[] discrepencyFacilityLists=new IDiscrepencyFacilityList[result.getResultList().size()];
							if (result != null) {
								resultList = result.getResultList();
							}
							if(resultList !=null){
								Iterator k= resultList.iterator();
									while(k.hasNext()){
										
										for(int j=0;j<resultList.size();j++){
											discrepencyFacilityLists[j]=(IDiscrepencyFacilityList)k.next();
										}
										
								}
							}
							objDisc.setFacilityList(discrepencyFacilityLists);
						}
						resultMap.put("discrepencyObj", objDisc);
						
					}
				}
				resultMap.put("event",event);
				resultMap.put("session.Id",discrepencysessionID);
				resultMap.put("searchstatus", searchstatus);
				resultMap.put("discType",discType);
				resultMap.put("customerID",custOB1);
				resultMap.put("session.searchResult", searchResult);
				resultMap.put("startIndex", map.get("startIndex"));
			}
			catch(NoSuchDiscrepencyException e){
				DefaultLogger.debug(this, "got exception in doExecute" + e);
				e.printStackTrace();
				throw (new CommandProcessingException(e.getMessage()));
			}
			 catch (TransactionException e) {
					
					e.printStackTrace();
				}
		}
		else{
					try {
						
						String discrepencyCode = (String) (map.get("TrxId"));
						IDiscrepencyTrxValue trxValue = (OBDiscrepencyTrxValue) getDiscrepencyProxy().getDiscrepencyByTrxID(discrepencyCode);
						IDiscrepency discrepency = (OBDiscrepency) trxValue.getStagingDiscrepency();
						IDiscrepency actualDiscrepency =null;
						if(trxValue.getActualDiscrepency()!=null){
							actualDiscrepency = (OBDiscrepency) trxValue.getActualDiscrepency();
						}
						
						IDiscrepencyDAO discrepencyDAO=(IDiscrepencyDAO)BeanHouse.get("discrepencyDAO");
						if(actualDiscrepency!=null){
							SearchResult resultActual= discrepencyDAO.listDiscrepencyFacility("actualDiscrepencyFacilityList", actualDiscrepency.getId());
							Collection resultListActual = null;
							IDiscrepencyFacilityList[] discrepencyFacilityListsActual=new IDiscrepencyFacilityList[resultActual.getResultList().size()];
							if (resultActual != null) {
								resultListActual = resultActual.getResultList();
							}
							if(resultListActual !=null){
								Iterator iA= resultListActual.iterator();
									while(iA.hasNext()){
										
										for(int j=0;j<resultListActual.size();j++){
											discrepencyFacilityListsActual[j]=(IDiscrepencyFacilityList)iA.next();
										}
										
								}
							}
							actualDiscrepency.setFacilityList(discrepencyFacilityListsActual);
							trxValue.setActualDiscrepency(actualDiscrepency);
						}
						SearchResult result= discrepencyDAO.listDiscrepencyFacility("stagingDiscrepencyFacilityList", discrepency.getId());
						Collection resultList = null;
						IDiscrepencyFacilityList[] discrepencyFacilityLists=new IDiscrepencyFacilityList[result.getResultList().size()];
						if (result != null) {
							resultList = result.getResultList();
						}
						if(resultList !=null){
							Iterator i= resultList.iterator();
								while(i.hasNext()){
									
									for(int j=0;j<resultList.size();j++){
										discrepencyFacilityLists[j]=(IDiscrepencyFacilityList)i.next();
									}
									
							}
						}
						discrepency.setFacilityList(discrepencyFacilityLists);
						trxValue.setStagingDiscrepency(discrepency);
						//String event = (String) (map.get("event"));
			//			Set list = (Set)discrepency.getFacilityList();
						
						/*Set facilityList = new HashSet();
						if( !list.contains("OverDraft") )
							facilityList.add("OverDraft");
						if( !list.contains("Bankers Guarantee") )
							facilityList.add("Bankers Guarantee");
						if( !list.contains("Hypothetical") )
							facilityList.add("Hypothetical");
						if( !list.contains("TermLoan") )
							facilityList.add("TermLoan");
						
						if(event.equals("maker_resubmit_create_discrepency"))
						{					
							resultMap.put("facilityList",facilityList.toArray());
						}*/
						
						if("prepare_maker_edit_reject_discrepency".equals(event)){
							resultMap.put("IDiscrepencyTrxValue", trxValue);
							resultMap.put("customerID",customerID);
						}
						else{
							resultMap.put("IDiscrepencyTrxValue", trxValue);
							resultMap.put("customerID",custOB);
							}
						resultMap.put("discrepencyObj", discrepency);
						
						resultMap.put("event",event);
						resultMap.put("searchstatus", searchstatus);
						resultMap.put("discType",discType);
						resultMap.put("startIndex", map.get("startIndex"));
			//			resultMap.put("selectedList", getSelectedList(list));
						
					} catch (NoSuchDiscrepencyException e) {
			
						DefaultLogger.debug(this, "got exception in doExecute" + e);
						e.printStackTrace();
						throw (new CommandProcessingException(e.getMessage()));
					} catch (TransactionException e) {
						e.printStackTrace();
						throw (new CommandProcessingException(e.getMessage()));
					}
				}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	/*private List getFacilityList(Set idList) {
		List lbValList = new ArrayList();
		try {
			Iterator itor = idList.iterator();
			List selectedList = new ArrayList();
			while( itor.hasNext() ){
				IDiscrepencyFacilityList discrepencyList = (OBDiscrepencyFacilityList)itor.next();
				String id = Long.toString(discrepencyList.getFacilityId());
				String val = discrepencyList.getFacilityName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}*/
	
	private List getSelectedList(Set idList) {
		List lbValList = new ArrayList();
		try {
			Iterator itor = idList.iterator();
			List selectedList = new ArrayList();
			while( itor.hasNext() ){
				IDiscrepencyFacilityList discrepencyList = (OBDiscrepencyFacilityList)itor.next();
				String id = Long.toString(discrepencyList.getFacilityId());
				String val = discrepencyList.getFacilityName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
}
