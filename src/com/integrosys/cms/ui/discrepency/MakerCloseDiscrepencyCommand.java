package com.integrosys.cms.ui.discrepency;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;
import com.integrosys.cms.app.discrepency.bus.IDiscrepencyDAO;
import com.integrosys.cms.app.discrepency.bus.NoSuchDiscrepencyException;
import com.integrosys.cms.app.discrepency.bus.OBDiscrepency;
import com.integrosys.cms.app.discrepency.proxy.IDiscrepencyProxyManager;
import com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue;
import com.integrosys.cms.app.discrepency.trx.OBDiscrepencyTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/02/2011 02:37:00 $ Tag: $Name: $
 */

public class MakerCloseDiscrepencyCommand extends AbstractCommand implements
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
	public MakerCloseDiscrepencyCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,class name,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{
						"IDiscrepencyTrxValue","com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue",SERVICE_SCOPE },
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{"searchstatus","java.lang.String",REQUEST_SCOPE},
				{"discType","java.lang.String",REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "unCheckId", "java.lang.String", REQUEST_SCOPE },
				{ "selectId", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE }
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
		return (new String[][] { { "request.ITrxValue",
				"com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{"searchstatus","java.lang.String",REQUEST_SCOPE},
				{"discType","java.lang.String",REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE }
				});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException{
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			String event = (String) map.get("event");
			String searchstatus=(String)map.get("searchstatus");
			String discType=(String)map.get("discType");
			ArrayList discrepencyIdList = new ArrayList();
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IDiscrepencyDAO discrepencyDAO=(IDiscrepencyDAO)BeanHouse.get("discrepencyDAO");
			String selectId = (String) map.get("selectId");
			String[] selectIdArray = selectId.split("-");
			String remarks = (String) map.get("remarks");
			String unCheckId =(String) map.get("unCheckId");
			HashMap selectedArrayMap = (HashMap) map.get("selectedArrayMap");
			
 				ctx.setRemarks(remarks);
 				String[] unCheckArray=unCheckId.split("-");
 				
 				
 				for(int k=0;k<selectIdArray.length;k++){
 					if(!selectIdArray[k].equals("") && selectedArrayMap!=null){
 						if(!selectedArrayMap.containsKey(selectIdArray[k])){
 							selectedArrayMap.put(selectIdArray[k], selectIdArray[k]);
 							
 						}
 					}
 				}
 				for(int ak=0;ak<unCheckArray.length;ak++){
 					if(!unCheckArray[ak].equals("") && selectedArrayMap!=null){
 						if(selectedArrayMap.containsKey(unCheckArray[ak])){
 							selectedArrayMap.remove(unCheckArray[ak]);
 							
 						}
 					}
 				}
 				if(selectedArrayMap!=null){
 				discrepencyIdList.addAll(selectedArrayMap.keySet());
 				}
 				
 				if(discrepencyIdList!=null){		
 				
 					for(int counter=0;counter<discrepencyIdList.size();counter++){
 						if(!discrepencyIdList.get(counter).equals("")){
 						//long trxId= Long.parseLong(selectIdArray[counter]);
 						String k=(String)discrepencyIdList.get(counter);
				//long trxId= Long.parseLong(selectIdArray[counter]);
				IDiscrepencyTrxValue trxValue = (OBDiscrepencyTrxValue) getDiscrepencyProxy().getDiscrepencyByTrxID(k);
				IDiscrepencyTrxValue trxValueOut = getDiscrepencyProxy().makerCloseRejectedDiscrepency(ctx,trxValue);
				//OBDiscrepency updateStage=replaceStage(discrepency,trxValueOut);
				if(trxValueOut.getActualDiscrepency()!=null){
					OBDiscrepency updateActual=replaceActual(trxValueOut.getActualDiscrepency(),trxValueOut);
					discrepencyDAO.updateDiscrepency(updateActual);
				}
				if(trxValueOut.getStagingDiscrepency()!=null && trxValueOut.getStagingDiscrepency().getStatus().equals("PENDING_CREATE")){
					OBDiscrepency updateStage=replaceStage(trxValueOut.getStagingDiscrepency(),trxValueOut);
					discrepencyDAO.updateStageDiscrepency(updateStage);
				}
			}
			
 					}
		}
			
			resultMap.put("searchstatus", searchstatus);
			resultMap.put("discType",discType);
			resultMap.put("event",event);
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
	public OBDiscrepency replaceActual(IDiscrepency discrepency,IDiscrepencyTrxValue trxValueOut)
	{
		OBDiscrepency replace=new OBDiscrepency();
		replace.setAcceptedDate(discrepency.getAcceptedDate());
		replace.setApprovedBy(discrepency.getApprovedBy());
		replace.setCounter(discrepency.getCounter());
		replace.setCreationDate(discrepency.getCreationDate());
		replace.setCreditApprover(discrepency.getCreditApprover());
		replace.setCustomerId(discrepency.getCustomerId());
		replace.setDeferDate(discrepency.getDeferDate());
		replace.setTotalDeferedDays(discrepency.getTotalDeferedDays());
		replace.setOriginalDeferedDays(discrepency.getOriginalDeferedDays());
		replace.setDeferedCounter(discrepency.getDeferedCounter());
		replace.setDiscrepency(discrepency.getDiscrepency());
		replace.setDiscrepencyRemark(discrepency.getDiscrepencyRemark());
		replace.setDiscrepencyType(discrepency.getDiscrepencyType());
		replace.setDocRemarks(discrepency.getDocRemarks());
		replace.setFacilityList(discrepency.getFacilityList());
		replace.setId(discrepency.getId());
		replace.setNextDueDate(discrepency.getNextDueDate());
		replace.setOriginalTargetDate(discrepency.getOriginalTargetDate());
		replace.setRecDate(discrepency.getRecDate());
		replace.setStatus(discrepency.getStatus());
		replace.setTransactionStatus(trxValueOut.getStatus());
		replace.setCritical(discrepency.getCritical());
		//replace.setVersionTime(0);
		replace.setWaiveDate(discrepency.getWaiveDate());
		
		return replace;
		
	}
	
	public OBDiscrepency replaceStage(IDiscrepency discrepency,IDiscrepencyTrxValue trxValueOut)
	{
		OBDiscrepency replace=new OBDiscrepency();
		replace.setAcceptedDate(discrepency.getAcceptedDate());
		replace.setApprovedBy(discrepency.getApprovedBy());
		replace.setCounter(discrepency.getCounter());
		replace.setCreationDate(discrepency.getCreationDate());
		replace.setCreditApprover(discrepency.getCreditApprover());
		replace.setCustomerId(discrepency.getCustomerId());
		replace.setDeferDate(discrepency.getDeferDate());
		replace.setTotalDeferedDays(discrepency.getTotalDeferedDays());
		replace.setOriginalDeferedDays(discrepency.getOriginalDeferedDays());
		replace.setDeferedCounter(discrepency.getDeferedCounter());
		replace.setDiscrepency(discrepency.getDiscrepency());
		replace.setDiscrepencyRemark(discrepency.getDiscrepencyRemark());
		replace.setDiscrepencyType(discrepency.getDiscrepencyType());
		replace.setDocRemarks(discrepency.getDocRemarks());
		replace.setFacilityList(discrepency.getFacilityList());
		replace.setId(Long.parseLong(trxValueOut.getStagingReferenceID()));
		replace.setNextDueDate(discrepency.getNextDueDate());
		replace.setOriginalTargetDate(discrepency.getOriginalTargetDate());
		replace.setRecDate(discrepency.getRecDate());
		replace.setStatus("REJECTED_CLOSED");
		replace.setTransactionStatus("REJECTED_CLOSED");
		replace.setCritical(discrepency.getCritical());
		//replace.setVersionTime(0);
		replace.setWaiveDate(discrepency.getWaiveDate());
		
		return replace;
		
	}
}
