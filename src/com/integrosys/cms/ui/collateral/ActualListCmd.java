package com.integrosys.cms.ui.collateral;

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
import com.integrosys.cms.app.component.bus.IComponentDao;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeHelper;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGC;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGCJdbc;

public class ActualListCmd extends AbstractCommand {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, 
				{"insuranceCode","java.lang.String", REQUEST_SCOPE },
				//{"actualMap", " java.util.HashMap", SERVICE_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE},
				{"itemType","java.lang.String", REQUEST_SCOPE },
			//	{ "totalLonable", "java.lang.String", REQUEST_SCOPE },
			//	{ "remarkByMaker", "java.lang.String", REQUEST_SCOPE },
			//	{ "dpCalculateManually", "java.lang.String", REQUEST_SCOPE },
				{ "migrationFlag", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE},
				{ "event", "java.lang.String", SERVICE_SCOPE},
				});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{"actualMap", " java.util.HashMap", REQUEST_SCOPE},
				{"actualObj", " com.integrosys.cms.ui.collateral.assetbased.assetgencharge.OBInsuranceGC", REQUEST_SCOPE},
				//{"actualObj", " com.integrosys.cms.ui.collateral.assetbased.assetgencharge.OBInsuranceGC", SERVICE_SCOPE},
				{"stageMap", " java.util.HashMap", REQUEST_SCOPE},
				//Santosh
				{"applicableForDpList", " java.util.HashMap", REQUEST_SCOPE},
				{"compoList", " java.util.HashMap", REQUEST_SCOPE},
				{"itemType","java.lang.String", REQUEST_SCOPE },
			//	{ "totalLonable", "java.lang.String", SERVICE_SCOPE },
			//	{ "remarkByMaker", "java.lang.String", SERVICE_SCOPE },
			//	{ "dpCalculateManually", "java.lang.String", SERVICE_SCOPE },
				{ "migrationFlag", "java.lang.String", SERVICE_SCOPE },
				
				
				});
	}
	
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap temp = new HashMap();
		HashMap actualMap=new HashMap();
		HashMap stageMap=new HashMap();
		
		
		IInsuranceGCJdbc insuranceGCJdbc = (IInsuranceGCJdbc) BeanHouse.get("insuranceGcJdbc");
		DefaultLogger.debug(this, "insuranceGCJdbc: " + insuranceGCJdbc);
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		DefaultLogger.debug(this, "itrxValue: " + itrxValue);
		String parentId=itrxValue.getReferenceID();
		DefaultLogger.debug(this, "parentId: " + parentId);
		String insuranceCode=(String)map.get("insuranceCode");
		String itemType=(String)map.get("itemType");
	
		/*String migrationFlag =(String) map.get("migrationFlag");
		String totalLonable =(String) map.get("totalLonable");
		if(null==totalLonable) {
			totalLonable="0";
		}
		System.out.println("totalLonable==="+totalLonable);
		*/
		
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
				if(null!=insuranceCode /*&& !"".equals(insuranceCode)*/){
				if(insuranceCode.equals(String.valueOf(actualObj.getInsuranceCode()))){
					result.put("actualObj", actualObj);
				}
				}
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
			
			//Start santosh
			IComponentDao componentDAO=(IComponentDao)BeanHouse.get("componentDao");
			List<String> applicableForDpList=new ArrayList<String>();
			applicableForDpList=componentDAO.getApplicableForDpList();
			result.put("applicableForDpList", applicableForDpList);
			//end santosh
			System.out.println("itemType===================="+itemType);
			String category="";
			if(null != itemType && !"".equals(itemType)) {
				
				if("CURRENTASSET".equals(itemType)) {
					 category="Stock";
				}else if("CURRENTLIABILITIES".equals(itemType)) {
					 category="Creditors";
				}else if("VALUEDEBTORS".equals(itemType)) {
					 category="Debtors";
				}else if("LESSVALUEADVANCES".equals(itemType)) {
					 category="Advances";
				}
				
				
				
			}
			
			List<String> compoList=new ArrayList<String>();
			AssetGenChargeHelper helper= new AssetGenChargeHelper();
			compoList=helper.getCompoList(category);
			result.put("compoList", compoList);
			
			//result.put("insuranceList", allInsurance);
			result.put("actualMap", actualMap);
			result.put("stageMap", stageMap);
			/*result.put("totalLonable", totalLonable);
			result.put("remarkByMaker", map.get("remarkByMaker"));
			result.put("dpCalculateManually", map.get("dpCalculateManually")); */
		//	result.put("migrationFlag", map.get("migrationFlag")); 

			
			}
		catch(Exception e){
			throw new InsuranceCGException("ERROR-- ActualListCmd.doExecute");
			
		}
		//result.put("insuranceList", insuranceList);
		
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return temp;
	}

	

}
