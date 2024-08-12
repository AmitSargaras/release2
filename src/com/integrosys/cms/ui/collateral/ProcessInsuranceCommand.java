package com.integrosys.cms.ui.collateral;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGC;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGCJdbc;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class ProcessInsuranceCommand extends AbstractCommand {
	
	private IInsuranceGCJdbc insuranceGcJdbc;
		
	public IInsuranceGCJdbc getInsuranceGcJdbc() {
		return insuranceGcJdbc;
	}

	public void setInsuranceGcJdbc(IInsuranceGCJdbc insuranceGcJdbc) {
		this.insuranceGcJdbc = insuranceGcJdbc;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "parentPageFrom", "java.lang.String", SERVICE_SCOPE },
				{ "displayList",  "java.util.List", SERVICE_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{"actualMap", "java.util.HashMap", SERVICE_SCOPE},
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "exchangeRate", "java.lang.String", SERVICE_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE }, });
	}
	
	public String[][] getResultDescriptor() {
        return (new String[][]{
                {"insuranceList", "java.util.ArrayList", SERVICE_SCOPE},
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{"actualMap", " java.util.HashMap", SERVICE_SCOPE},
				{ "exchangeRate", "java.lang.String", SERVICE_SCOPE },
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}
        });
    }
	

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap temp = new HashMap();
		List insuranceList=null;
		HashMap actualMap=(HashMap)map.get("actualMap");
		SearchResult insuranceIdList = new SearchResult(); 
		IInsuranceGCJdbc insuranceGCJdbc = (IInsuranceGCJdbc) BeanHouse.get("insuranceGcJdbc");
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		String parentId=itrxValue.getReferenceID();
		
		try{
			SearchResult deleteStageInsuranceList= (SearchResult)  insuranceGCJdbc.getAllDeleteStageInsurance(parentId);
			SearchResult processStageInsuranceList= (SearchResult)  insuranceGCJdbc.getAllProcessStageInsurance(parentId);
			//SearchResult rejectStageInsuranceList= (SearchResult)  insuranceGCJdbc.getAllRejectStageInsurance(parentId);
			SearchResult draftStageInsuranceList= (SearchResult)  insuranceGCJdbc.getAllDraftStageInsurance(parentId);
			SearchResult allActualInsuranceList= (SearchResult)  insuranceGCJdbc.getAllActualInsurance(parentId);
			List allInsurance=new ArrayList();
			List actualList=new ArrayList();
			List deleteStageList=new ArrayList();
			List processStageList=new ArrayList();
			List rejectStageList=new ArrayList();
			List removeList=new ArrayList();
			List draftStageList=new ArrayList();
			if(allActualInsuranceList!=null){
				actualList=(List) allActualInsuranceList.getResultList();
				if(actualMap==null||actualMap.isEmpty()){
					for(int i=0;i<actualList.size();i++){
						IInsuranceGC actualObj=(IInsuranceGC)actualList.get(i);
						//long parentId=actualObj.getParentId();
						actualMap.put(String.valueOf(actualObj.getId()),actualObj);
						
						}
					}
			}
			
			if(deleteStageInsuranceList!=null){
				deleteStageList=(List)deleteStageInsuranceList.getResultList();
				
			}
			if(processStageInsuranceList!=null){
				processStageList=(List)processStageInsuranceList.getResultList();
			}
			if(draftStageInsuranceList!=null){
				draftStageList=(List)draftStageInsuranceList.getResultList();
			}
			/*if(rejectStageInsuranceList!=null){
				rejectStageList=(List)rejectStageInsuranceList.getResultList();
			}*/
			
			for(int i=0;i<actualList.size();i++){
				for(int j=0;j<processStageList.size();j++){
						IInsuranceGC actualObj=(IInsuranceGC)actualList.get(i);
						IInsuranceGC stageObj=(IInsuranceGC)processStageList.get(j);
							if(actualObj.getInsuranceCode().equals(stageObj.getInsuranceCode())){
									if(!actualObj.getIsProcessed().equals(stageObj.getIsProcessed())&&
											actualObj.getDeprecated().equals(stageObj.getDeprecated())){
											removeList.add(actualObj);
											//actualList.remove(actualObj);
										}
							}
					}
				}
			for(int i=0;i<actualList.size();i++){
				for(int j=0;j<draftStageList.size();j++){
						IInsuranceGC actualObj=(IInsuranceGC)actualList.get(i);
						IInsuranceGC stageObj=(IInsuranceGC)draftStageList.get(j);
							if(actualObj.getInsuranceCode().equals(stageObj.getInsuranceCode())){
									if(!actualObj.getIsProcessed().equals(stageObj.getIsProcessed())){
										removeList.add(actualObj);
										//actualList.remove(actualObj);
									}
							}
					}
				}
			
			/*for(int i=0;i<actualList.size();i++){
				for(int j=0;j<rejectStageList.size();j++){
						IInsuranceGC actualObj=(IInsuranceGC)actualList.get(i);
						IInsuranceGC stageObj=(IInsuranceGC)rejectStageList.get(j);
							if(actualObj.getInsuranceCode().equals(stageObj.getInsuranceCode())){
									if(!actualObj.getIsProcessed().equals(stageObj.getIsProcessed())){
										removeList.add(actualObj);
											
										//actualList.remove(actualObj);
									}
							}
					}
				}*/
			
			
			for(int i=0;i<actualList.size();i++){
				for(int j=0;j<deleteStageList.size();j++){
						IInsuranceGC actualObj=(IInsuranceGC)actualList.get(i);
						IInsuranceGC stageObj=(IInsuranceGC)deleteStageList.get(j);
							if(actualObj.getInsuranceCode().equals(stageObj.getInsuranceCode())){
									if(!actualObj.getIsProcessed().equals(stageObj.getIsProcessed())&&
											!actualObj.getDeprecated().equals(stageObj.getDeprecated())){
											removeList.add(actualObj);
					
											//actualList.remove(actualObj);
										}
							}
					}
				}
			
			
			if(actualList!=null){	
				actualList.removeAll(removeList);
				allInsurance.addAll(actualList);			
			}
			
			if(processStageList!=null){			
				allInsurance.addAll(processStageList);			
			}
			
		/*	if(rejectStageList!=null){			
				allInsurance.addAll(rejectStageList);			
			}*/
			
			if(deleteStageList!=null){			
				allInsurance.addAll(deleteStageList);			
			}
			
			if(draftStageList!=null){			
				allInsurance.addAll(draftStageList);			
			}
			
			
			
			
			/*if(stageList!=null){	
				for(int k=0;k<stageList.size();k++){
					IInsuranceGC stageObj=(IInsuranceGC)stageList.get(k);
					if(stageObj.getIsProcessed().equals("YR")&&(stageObj.getDeprecated().equals("N")|| stageObj.getDeprecated().equals("Y")) ){
						allInsurance.add(stageObj);
					}
					if(stageObj.getIsProcessed().equals("N")&& (stageObj.getDeprecated().equals("N")|| stageObj.getDeprecated().equals("Y"))){
						allInsurance.add(stageObj);
					}
					
					
				}
				//allInsurance.addAll(stageList);			
			}
			*/
			result.put("theOBTrxContext", map.get("theOBTrxContext"));
			result.put("trxID", map.get("trxID"));
			if(allInsurance!=null){
			result.put("insuranceList", allInsurance);
			}
			result.put("event", map.get("event"));
				
			
			}
		catch(Exception e){
			throw new InsuranceCGException("ERROR-- ProcessInsuranceCommand.doExecute");
			
		}
		//result.put("insuranceList", insuranceList);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return temp;
	}

}
