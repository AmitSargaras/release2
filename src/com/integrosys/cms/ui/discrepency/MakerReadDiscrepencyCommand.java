package com.integrosys.cms.ui.discrepency;

import java.sql.Array;
import java.util.ArrayList;
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
import com.integrosys.cms.app.discrepency.proxy.IDiscrepencyProxyManager;
import com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue;
import com.integrosys.cms.app.discrepency.trx.OBDiscrepencyTrxValue;
import com.integrosys.cms.app.limit.bus.LimitDAO;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class MakerReadDiscrepencyCommand extends AbstractCommand implements
		ICommonEventConstant {

	private IDiscrepencyProxyManager discrepencyProxy;
	
	public IDiscrepencyProxyManager getDiscrepencyProxy() {
		return discrepencyProxy;
	}

	public void setDiscrepencyProxy(IDiscrepencyProxyManager discrepencyProxy) {
		this.discrepencyProxy = discrepencyProxy;
	}

	/**
	 * Default Constructor
	 */
	public MakerReadDiscrepencyCommand() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "discrepencyId", "java.lang.String", REQUEST_SCOPE },
				{ "discrepancyCreateList", "java.util.List", SERVICE_SCOPE },
				{ "discId", "java.lang.String", REQUEST_SCOPE },
				{ "checkId", "java.lang.String", REQUEST_SCOPE },
				{ "unCheckId", "java.lang.String", REQUEST_SCOPE },
				{ "searchstatus", "java.lang.String", REQUEST_SCOPE },
				{ "discType", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "startIndexInn", "java.lang.String", REQUEST_SCOPE },
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
						{ "transactionHistoryList", "java.util.List", SERVICE_SCOPE },
						{ "searchstatus", "java.lang.String", REQUEST_SCOPE },
						{ "discType", "java.lang.String", REQUEST_SCOPE },
						{ "startIndex", "java.lang.String", REQUEST_SCOPE },
						{ "startIndexInn", "java.lang.String", REQUEST_SCOPE },
						 { "migratedFlag", "java.lang.String", SERVICE_SCOPE },
						 { "event", "java.lang.String", REQUEST_SCOPE },
						 { "discId", "java.lang.String", REQUEST_SCOPE },
						 { "session.discId", "java.lang.String", SERVICE_SCOPE },
				{
						"IDiscrepencyTrxValue",
						"com.integrosys.cms.app.discrepency.discrepency.trx.IDiscrepencyTrxValue",
						SERVICE_SCOPE }
			});
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
			CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		 List transactionHistoryList= new ArrayList();
		 String event = (String) map.get("event");
		 String sessionId="";
		 resultMap.put("startIndexInn", map.get("startIndexInn"));
		 if("maker_view_discrepancy".equals(event)||"maker_update_temp_discrepancy".equals(event)
				 ||"maker_edit_temp_discrepency_error".equals(event)){
			 try{
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
				 sessionId=(String)map.get("discId");
				 OBDiscrepency viewObj=new OBDiscrepency();
				 ArrayList discrepancyCreateList= (ArrayList)map.get("discrepancyCreateList");
				 for(int i=0;i<discrepancyCreateList.size();i++){
					 if(String.valueOf(i).equals(discId)){
						  viewObj=(OBDiscrepency)discrepancyCreateList.get(i);				 
					 }
				 }
				 resultMap.put("event", event);
				resultMap.put("discrepencyObj", viewObj);
				resultMap.put("startIndex", map.get("startIndex"));
				resultMap.put("startIndexInn", map.get("startIndexInn"));
				resultMap.put("searchstatus", map.get("searchstatus"));
				resultMap.put("discType",map.get("discType"));
				resultMap.put("discId",discId);
				resultMap.put("session.discId",sessionId);
			 	}
			 catch (NoSuchDiscrepencyException ex) {
					DefaultLogger.debug(this, "got exception in doExecute" + ex);
					ex.printStackTrace();
					throw (new CommandProcessingException(ex.getMessage()));
				}
		 }
		 
		 else{
				try {
					String id = (String) (map.get("discrepencyId"));
		//			IDiscrepencyTrxValue trxValue = (OBDiscrepencyTrxValue) getDiscrepencyProxy()
		//					.getDiscrepencyTrxValue((Long.parseLong(id)));
					IDiscrepencyTrxValue discrepency = (IDiscrepencyTrxValue) getDiscrepencyProxy().getDiscrepencyById(Long.parseLong(id));
					if(!event.equals("view_discrepency")){
						if(!discrepency.getStatus().trim().equals("ACTIVE")){
							resultMap.put("wip", "wip");
						}
					}
					ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
					 transactionHistoryList = customerDAO.getTransactionHistoryList(discrepency.getTransactionID());
					
					IDiscrepencyDAO discrepencyDAO=(IDiscrepencyDAO)BeanHouse.get("discrepencyDAO");
					SearchResult result= discrepencyDAO.listDiscrepencyFacility("actualDiscrepencyFacilityList", discrepency.getActualDiscrepency().getId());
					 List disFacList = new ArrayList(result.getResultList());
					 if(disFacList !=null){
					IDiscrepencyFacilityList discrepencyFacilityList[]= new IDiscrepencyFacilityList[disFacList.size()];
					
					for(int i=0;i< disFacList.size();i++){
						discrepencyFacilityList[i]=(IDiscrepencyFacilityList) disFacList.get(i);
						
						}
					
					 discrepency.getActualDiscrepency().setFacilityList(discrepencyFacilityList);
					 }
					 
					 LimitDAO limitDao = new LimitDAO();
						try {
						String migratedFlag = "N";	
						boolean status = false;	
						 status = limitDao.getCAMMigreted("CMS_DISCREPENCY",Long.parseLong(id),"ID");
						
						if(status)
						{
							migratedFlag= "Y";
						}
						resultMap.put("migratedFlag", migratedFlag);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					  resultMap.put("transactionHistoryList", transactionHistoryList);
					resultMap.put("IDiscrepencyTrxValue", discrepency);
					resultMap.put("event", event);
					resultMap.put("startIndex", map.get("startIndex"));
					resultMap.put("searchstatus", map.get("searchstatus"));
					resultMap.put("discType",map.get("discType"));
					resultMap.put("discrepencyObj", discrepency.getActualDiscrepency());
					
				} catch (NoSuchDiscrepencyException ex) {
					DefaultLogger.debug(this, "got exception in doExecute" + ex);
					ex.printStackTrace();
					throw (new CommandProcessingException(ex.getMessage()));
				} catch (TransactionException e) {
					DefaultLogger.debug(this, "got exception in doExecute" + e);
					e.printStackTrace();
					throw (new CommandProcessingException(e.getMessage()));
				}
		 }
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
