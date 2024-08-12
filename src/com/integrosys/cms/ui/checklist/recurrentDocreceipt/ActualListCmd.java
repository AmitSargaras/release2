package com.integrosys.cms.ui.checklist.recurrentDocreceipt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.InsuranceCGException;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGC;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGCJdbc;

public class ActualListCmd extends AbstractCommand {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, 
				 { "no_template_gc", "java.lang.String", SERVICE_SCOPE },
				//{"actualMap", " java.util.HashMap", SERVICE_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE},
				});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{"actualMap", " java.util.HashMap", REQUEST_SCOPE},
				{"stageMap", " java.util.HashMap", REQUEST_SCOPE},
			
				});
	}
	
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap temp = new HashMap();
		HashMap actualMap=new HashMap();
		HashMap stageMap=new HashMap();
		if (map.get("no_template_gc")!=null &&((String) map.get("no_template_gc")).equals("true")) {
			result.put("calculatedDP", map.get("calculatedDP"));
			result.put("actualMap", actualMap);
			result.put("stageMap", stageMap);
			temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			return temp;
		}else{
		
		IInsuranceGCJdbc insuranceGCJdbc = (IInsuranceGCJdbc) BeanHouse.get("insuranceGcJdbc");
		DefaultLogger.debug(this, "insuranceGCJdbc: " + insuranceGCJdbc);
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		DefaultLogger.debug(this, "itrxValue: " + itrxValue);
		String parentId=itrxValue.getReferenceID();
		DefaultLogger.debug(this, "parentId: " + parentId);
		
		try{
			SearchResult allActualInsuranceList = null;   
			SearchResult allStageInsuranceList = null;  
			if(insuranceGCJdbc!=null){
			allActualInsuranceList= (SearchResult)  insuranceGCJdbc.getAllActualInsurance(parentId);
			DefaultLogger.debug(this, "allActualInsuranceList: " + allActualInsuranceList);
			allStageInsuranceList= (SearchResult)  insuranceGCJdbc.getAllStageInsurance(parentId);
			DefaultLogger.debug(this, "allStageInsuranceList: " + allStageInsuranceList);
			}
			
			List actualList=new ArrayList();
			List stageList=new ArrayList();
			if(allActualInsuranceList!=null){
				actualList=(List) allActualInsuranceList.getResultList();
				for(int i=0;i<actualList.size();i++){
				IInsuranceGC actualObj=(IInsuranceGC)actualList.get(i);
				String actualId=String.valueOf(actualObj.getInsuranceCode());
				actualMap.put(actualId, actualObj);		
				
				}
			}
			if(allStageInsuranceList!=null){
				stageList=(List) allStageInsuranceList.getResultList();
				for(int i=0;i<stageList.size();i++){
				IInsuranceGC stageObj=(IInsuranceGC)stageList.get(i);
				String stageId=String.valueOf(stageObj.getId());
				stageMap.put(stageId, stageObj);		
				
				}
			}
			
			
			
			//result.put("insuranceList", allInsurance);
			result.put("actualMap", actualMap);
			result.put("stageMap", stageMap);
			
			}
		catch(Exception e){
			throw new InsuranceCGException("ERROR-- ActualListCmd.doExecute");
			
		}
		//result.put("insuranceList", insuranceList);
		result.put("calculatedDP", map.get("calculatedDP"));
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return temp;
		}
	}

	

}
