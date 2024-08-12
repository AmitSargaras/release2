package com.integrosys.cms.ui.discrepency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

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

public class MakerBulkResubmitDiscrepencyCommmand extends AbstractCommand {
	
private IDiscrepencyProxyManager discrepencyProxy;
	
	public IDiscrepencyProxyManager getDiscrepencyProxy() {
		return discrepencyProxy;
	}

	public void setDiscrepencyProxy(IDiscrepencyProxyManager discrepencyProxy) {
		this.discrepencyProxy = discrepencyProxy;
	}

	public MakerBulkResubmitDiscrepencyCommmand(){
		
	}
	
	
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
				{"searchstatus","java.lang.String",REQUEST_SCOPE},
				{"discType","java.lang.String",REQUEST_SCOPE},
				{"event","java.lang.String",REQUEST_SCOPE},
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "session.searchResult", "java.util.HashMap", SERVICE_SCOPE },
				{ "selectId", "java.lang.String", REQUEST_SCOPE },
				{ "unCheckId", "java.lang.String", REQUEST_SCOPE },
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
		return (new String[][] { 
				{"searchstatus","java.lang.String",REQUEST_SCOPE},
				{"discType","java.lang.String",REQUEST_SCOPE},
				{"event","java.lang.String",REQUEST_SCOPE},
				{ "session.searchResult", "java.util.HashMap", SERVICE_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
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
			CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		IDiscrepencyTrxValue trxValueOut = new OBDiscrepencyTrxValue();
		try {
			DefaultLogger.debug(this,
					" doExecute : CheckerApproveDiscrepencyCommmand ");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IDiscrepencyDAO discrepencyDAO=(IDiscrepencyDAO)BeanHouse.get("discrepencyDAO");
			String remarks = (String) map.get("remarks");
			ArrayList discrepencyIdList = new ArrayList();
			HashMap searchResult=(HashMap)map.get("session.searchResult");
			ArrayList resultList1 = (ArrayList)searchResult.get("list");
			String selectId = (String) map.get("selectId");
			String unCheckId =(String) map.get("unCheckId");
			HashMap selectedArrayMap = (HashMap) map.get("selectedArrayMap");
			String startIndex = (String) map.get("startIndex");
			String[] selectIdArray = selectId.split("-");
			String[] unCheckArray=unCheckId.split("-");
			ctx.setRemarks(remarks);
			
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
				IDiscrepencyTrxValue trxValue = (OBDiscrepencyTrxValue) getDiscrepencyProxy().getDiscrepencyByTrxID(k);
				IDiscrepency discrepency = null;
				String id=trxValue.getStagingReferenceID();
				ArrayList removeList=new ArrayList();
				for(int i=0;i<resultList1.size();i++){
					OBDiscrepency  objDisc=(OBDiscrepency)resultList1.get(i);
					String stageId=String.valueOf(objDisc.getId());
					if(stageId.equals(id)){
						discrepency=objDisc;
						removeList.add(objDisc);
					}
					
				} 
				resultList1.removeAll(removeList);
				searchResult.remove("list");
				searchResult.put("list", resultList1);
				
				if(resultList1.size()<=10){
					startIndex="0";
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
				trxValueOut = getDiscrepencyProxy().makerEditRejectedDiscrepency(ctx,trxValue, discrepency);
				//OBDiscrepency updateActual=replaceActual((OBDiscrepency)trxValueOut.getActualDiscrepency(),trxValueOut);				
				//discrepencyDAO.updateDiscrepency(updateActual);
				/*if(trxValueOut.getActualDiscrepency().getFacilityList()!=null){
					IDiscrepencyFacilityList[] discrepencyFacilityList=(IDiscrepencyFacilityList[])trxValueOut.getActualDiscrepency().getFacilityList();
					for(int i=0;i<discrepencyFacilityList.length;i++){
						discrepencyFacilityList[i].setDiscrepencyId(trxValueOut.getActualDiscrepency().getId());
						discrepencyDAO.createDiscrepencyFacilityList("actualDiscrepencyFacilityList", discrepencyFacilityList[i]);
					}
				}*/
			}
			}
		}
			
			resultMap.put("session.searchResult",searchResult);
			resultMap.put("searchstatus", map.get("searchstatus"));
			resultMap.put("discType",map.get("discType"));
			resultMap.put("startIndex", startIndex);
			resultMap.put("event", map.get("event"));
			resultMap.put("customerID", map.get("customerID"));
			resultMap.put("request.ITrxValue", trxValueOut);
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
	
	public OBDiscrepency replaceActual(OBDiscrepency discrepency,IDiscrepencyTrxValue trxValueOut)
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

}
