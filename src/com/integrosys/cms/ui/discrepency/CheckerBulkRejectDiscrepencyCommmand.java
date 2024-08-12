package com.integrosys.cms.ui.discrepency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;
import com.integrosys.cms.app.discrepency.bus.IDiscrepencyDAO;
import com.integrosys.cms.app.discrepency.bus.IDiscrepencyFacilityList;
import com.integrosys.cms.app.discrepency.bus.NoSuchDiscrepencyException;
import com.integrosys.cms.app.discrepency.bus.OBDiscrepency;
import com.integrosys.cms.app.discrepency.proxy.IDiscrepencyProxyManager;
import com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue;
import com.integrosys.cms.app.discrepency.trx.OBDiscrepencyTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 01/06/2011 02:37:00 $ Tag: $Name: $
 */

public class CheckerBulkRejectDiscrepencyCommmand extends AbstractCommand implements
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
	public CheckerBulkRejectDiscrepencyCommmand() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{
						"IDiscrepencyTrxValue",
						"com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "selectId", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "unCheckId", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{"searchstatus","java.lang.String",REQUEST_SCOPE},
				{"discType","java.lang.String",REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE }

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
				{ "selectId", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{"searchstatus","java.lang.String",REQUEST_SCOPE},
				{"discType","java.lang.String",REQUEST_SCOPE},
				{ "unCheckId", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE }
				
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
		HashMap exceptionMap = new HashMap();
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IDiscrepencyDAO discrepencyDAO=(IDiscrepencyDAO)BeanHouse.get("discrepencyDAO");
			String selectId = (String) map.get("selectId");
			ArrayList discrepencyIdList = new ArrayList();
			String remarks = (String) map.get("remarks");
			String unCheckId =(String) map.get("unCheckId");
			HashMap selectedArrayMap = (HashMap) map.get("selectedArrayMap");
			String[] selectIdArray = selectId.split("-");
			String[] unCheckArray=unCheckId.split("-");
			resultMap.put("selectId", selectId);
			if(selectedArrayMap==null){ 
				selectedArrayMap = new HashMap();
			}
			for(int k=0;k<selectIdArray.length;k++){
				if(!selectIdArray[k].equals("")){
					if(!selectedArrayMap.containsKey(selectIdArray[k])){
						selectedArrayMap.put(selectIdArray[k], selectIdArray[k]);
						
					}
				}
			}
			for(int ak=0;ak<unCheckArray.length;ak++){
				if(!unCheckArray[ak].equals("")){
					if(selectedArrayMap.containsKey(unCheckArray[ak])){
						selectedArrayMap.remove(unCheckArray[ak]);
						
					}
				}
			}
			discrepencyIdList.addAll(selectedArrayMap.keySet());
			
			if(remarks.equals("")){
				exceptionMap.put("remarkError", new ActionMessage("error.string.mandatory"));				
				//resultMap.put("request.ITrxValue", trxValueIn);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
				
			}
 			else{
 				ctx.setRemarks(remarks);
			}
			if(discrepencyIdList!=null){
			
				for(int counter=0;counter<discrepencyIdList.size();counter++){
					//long trxId= Long.parseLong(selectIdArray[counter]);
					if(!discrepencyIdList.get(counter).equals("")){
						String k=(String)discrepencyIdList.get(counter);
					IDiscrepencyTrxValue trxValue = (OBDiscrepencyTrxValue) getDiscrepencyProxy().getDiscrepencyByTrxID(k);
					IDiscrepencyTrxValue trxValueOut = getDiscrepencyProxy().checkerRejectDiscrepency(ctx,trxValue);
					}
				}
			}
			resultMap.put("searchstatus",map.get("searchstatus"));
			resultMap.put("discType",map.get("discType"));
			resultMap.put("event",map.get("event"));
			resultMap.put("startIndex", map.get("startIndex"));
			//resultMap.put("request.ITrxValue", trxValueOut);
		} catch (NoSuchDiscrepencyException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
