package com.integrosys.cms.ui.discrepency;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.discrepency.bus.OBDiscrepency;

/**
 * Class created by
 * 
 * @author sandiip.shinde
 * @since 11-04-2011
 * 
 */
public class MakerRemoveDiscrepencyCommand extends AbstractCommand {

		/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "selectId", "java.lang.String", REQUEST_SCOPE },
				{ "unCheckId", "java.lang.String", REQUEST_SCOPE },
				{ "legalCustomerId", "java.lang.String", REQUEST_SCOPE },
				{"searchstatus","java.lang.String",REQUEST_SCOPE},
				{"discType","java.lang.String",REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "startIndexInn", "java.lang.String", REQUEST_SCOPE },
				{ "discrepancyCreateList", "java.util.List", SERVICE_SCOPE },	
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
				{ "legalCustomerId", "java.lang.String", REQUEST_SCOPE },
				{"searchstatus","java.lang.String",REQUEST_SCOPE},
				{"discType","java.lang.String",REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "startIndexInn", "java.lang.String", REQUEST_SCOPE },
				{ "discrepancyCreateList", "java.util.List", SERVICE_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE }
				
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get data from database for Interest
	 * Rate is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		DefaultLogger.debug(this,
				"Inside doExecute() MakerRemoveDiscrepencyCommand");
		String id = (String) map.get("legalCustomerId");
		
		ArrayList discrepencyIdList = new ArrayList();
		String selectId = (String) map.get("selectId");
		String unCheckId =(String) map.get("unCheckId");
		String[] selectArray= selectId.split("-");
		String[] unCheckArray=unCheckId.split("-");
		resultMap.put("startIndexInn", map.get("startIndexInn"));
		ArrayList discrepancyCreateList= (ArrayList)map.get("discrepancyCreateList");
		HashMap selectedArrayMap = (HashMap) map.get("selectedArrayMap");
		ArrayList removeList=new ArrayList();
		
		for(int k=0;k<selectArray.length;k++){
			if(!selectArray[k].equals("")){
				if(!selectedArrayMap.containsKey(selectArray[k])){
					selectedArrayMap.put(selectArray[k], selectArray[k]);
					
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
		if(discrepencyIdList!=null){			
			
			for(int i=0;i<discrepencyIdList.size();i++){
				if(!discrepencyIdList.get(i).equals("")){
					String k=(String)discrepencyIdList.get(i);
					OBDiscrepency remove=(OBDiscrepency)discrepancyCreateList.get(Integer.parseInt(k));
					//int index= Integer.parseInt(removeArray[i]);
					removeList.add(remove);
				}
			}
		}
		discrepancyCreateList.removeAll(removeList);
		selectedArrayMap.clear();
		resultMap.put("discrepancyCreateList", discrepancyCreateList);
		resultMap.put("searchstatus", map.get("searchstatus"));
		resultMap.put("discType", map.get("discType"));
		resultMap.put("startIndex", map.get("startIndex"));
		resultMap.put("selectedArrayMap", selectedArrayMap);
		long customerId =0;		
		if( id != null && !(id.equals("")))			
			customerId = Long.parseLong(id);
		resultMap.put("legalCustomerId", String.valueOf(customerId));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
