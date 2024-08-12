package com.integrosys.cms.ui.collateral;

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
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGC;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGCJdbc;

public class MakerPrepareInsuranceCmd extends AbstractCommand {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, 
				{ "calculatedDP", "java.lang.String", REQUEST_SCOPE },
				{"insuranceList", "java.util.ArrayList", SERVICE_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE},
				{ "event", "java.lang.String", REQUEST_SCOPE },
				//{"actualMap", " java.util.HashMap", SERVICE_SCOPE},
				});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{"insuranceList", "java.util.ArrayList", SERVICE_SCOPE},
				{ "event", "java.lang.String", REQUEST_SCOPE },
				//{"actualMap", " java.util.HashMap", SERVICE_SCOPE},
				});
	}
	
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap temp = new HashMap();
		//HashMap actualMap=(HashMap)map.get("actualMap");
		List insuranceList=null;
		String event=(String)map.get("event");
		SearchResult insuranceIdList = new SearchResult(); 
		IInsuranceGCJdbc insuranceGCJdbc = (IInsuranceGCJdbc) BeanHouse.get("insuranceGcJdbc");
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		String parentId=itrxValue.getReferenceID();
		SearchResult deleteStageInsuranceList= null;
		SearchResult processStageInsuranceList=  null;
		SearchResult rejectStageInsuranceList=  null;
		SearchResult allActualInsuranceList=  null;
		try{
			
					deleteStageInsuranceList= (SearchResult)  insuranceGCJdbc.getAllDeleteStageInsurance(parentId);
					 processStageInsuranceList= (SearchResult)  insuranceGCJdbc.getAllProcessStageInsurance(parentId);
				//	rejectStageInsuranceList= (SearchResult)  insuranceGCJdbc.getAllRejectStageInsurance(parentId);
					SearchResult draftStageInsuranceList= (SearchResult)  insuranceGCJdbc.getAllDraftStageInsurance(parentId);
					 allActualInsuranceList= (SearchResult)  insuranceGCJdbc.getAllActualInsurance(parentId);
			
					List allInsurance=new ArrayList();
					List actualList=new ArrayList();
					List deleteStageList=new ArrayList();
					List processStageList=new ArrayList();
					List rejectStageList=new ArrayList();
					List removeList=new ArrayList();
					List draftStageList=new ArrayList();
					if(allActualInsuranceList!=null){
						actualList=(List) allActualInsuranceList.getResultList();
						/*if(actualMap==null||actualMap.isEmpty()){
						for(int i=0;i<actualList.size();i++){
							IInsuranceGC actualObj=(IInsuranceGC)actualList.get(i);
							//long parentId=actualObj.getParentId();
							actualMap.put(String.valueOf(actualObj.getId()),actualObj);
							
							}
						}*/
					}
					
					if(deleteStageInsuranceList!=null){
						deleteStageList=(List)deleteStageInsuranceList.getResultList();
					}
					if(processStageInsuranceList!=null){
						processStageList=(List)processStageInsuranceList.getResultList();
					}
					if(rejectStageInsuranceList!=null){
						rejectStageList=(List)rejectStageInsuranceList.getResultList();
					}
					if(draftStageInsuranceList!=null){
						draftStageList=(List)draftStageInsuranceList.getResultList();
					}
					if(actualList.size()>0 && processStageList.size()>0){
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
					}
					
					if(actualList.size()>0 && rejectStageList.size()>0){
					for(int i=0;i<actualList.size();i++){
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
					
					if(actualList.size()>0 && deleteStageList.size()>0){
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
					}
					
					
					if(actualList!=null){		
						actualList.removeAll(removeList);
						allInsurance.addAll(actualList);			
					}
					
					if(processStageList!=null){			
						allInsurance.addAll(processStageList);			
					}
					
					if(rejectStageList!=null){			
						allInsurance.addAll(rejectStageList);			
					}
					
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
					
					result.put("insuranceList", allInsurance);
					result.put("event", map.get("event"));
					//result.put("actualMap", actualMap);
						
			     	
        		/*if(event.equalsIgnoreCase("prepare_close")){
        			insuranceIdList= (SearchResult)  insuranceGCJdbc.getAllCloseInsurance(parentId); 
        			insuranceList=new ArrayList(insuranceIdList.getResultList());
        			result.put("insuranceList", insuranceList);
        		}*/
			
			}
		catch(Exception e){
			throw new InsuranceCGException("ERROR-- MakerPrepareInsuranceCmd");
			
		}
		
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return temp;
	}

}
