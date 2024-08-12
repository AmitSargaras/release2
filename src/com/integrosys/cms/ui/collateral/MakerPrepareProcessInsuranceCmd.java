package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGC;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGCJdbc;

public class MakerPrepareProcessInsuranceCmd extends AbstractCommand {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, 
				{ "calculatedDP", "java.lang.String", REQUEST_SCOPE },
				{"insuranceList", "java.util.ArrayList", SERVICE_SCOPE},
				//{"actualMap", " java.util.HashMap", SERVICE_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE},
				});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{"insuranceList", "java.util.ArrayList", SERVICE_SCOPE},
				//{"actualMap", " java.util.HashMap", SERVICE_SCOPE},
				{ "calculatedDP", "java.lang.String", REQUEST_SCOPE },
			
				});
	}
	
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap temp = new HashMap();
		//HashMap actualMap=(HashMap)map.get("actualMap");
		List insuranceList=null;
		SearchResult insuranceIdList = new SearchResult(); 
		IInsuranceGCJdbc insuranceGCJdbc = (IInsuranceGCJdbc) BeanHouse.get("insuranceGcJdbc");
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		String parentId=itrxValue.getReferenceID();
		
		try{
			     	
			SearchResult deleteStageInsuranceList= (SearchResult)  insuranceGCJdbc.getAllDeleteStageInsurance(parentId);
			SearchResult processStageInsuranceList= (SearchResult)  insuranceGCJdbc.getAllProcessStageInsurance(parentId);
			SearchResult rejectStageInsuranceList= (SearchResult)  insuranceGCJdbc.getAllRejectStageInsurance(parentId);
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
				/*for(int i=0;i<actualList.size();i++){
				IInsuranceGC actualObj=(IInsuranceGC)actualList.get(i);
				String actualId=String.valueOf(actualObj.getId());
				//long parentId=actualObj.getParentId();
				actualMap.put(actualId,actualObj);
				
				}*/
			}
			
			if(deleteStageInsuranceList!=null){
				deleteStageList=(List)deleteStageInsuranceList.getResultList();
			}
			if(draftStageInsuranceList!=null){
				draftStageList=(List)draftStageInsuranceList.getResultList();
			}
			if(processStageInsuranceList!=null){
				processStageList=(List)processStageInsuranceList.getResultList();
			}
			if(rejectStageInsuranceList!=null){
				rejectStageList=(List)rejectStageInsuranceList.getResultList();
			}
			
			List rejectStageListInsCode=new ArrayList<String>();
			Set rejectStageListInsCodeSet=new HashSet<String>();
			List rejectStageListNew=new ArrayList();
			
			for(int i=0;i<rejectStageList.size();i++){
				IInsuranceGC stageObj=(IInsuranceGC)rejectStageList.get(i);
				String insuranceCode = stageObj.getInsuranceCode();

				rejectStageListInsCodeSet.add(insuranceCode);
			}
			
			
			rejectStageListInsCode.addAll(rejectStageListInsCodeSet);
			for(int i=0; i<rejectStageListInsCode.size(); i++){
				
				rejectStageListNew.addAll(insuranceGCJdbc.getRecentInsuranceForCode((String)rejectStageListInsCode.get(i),parentId));
				
			}
			
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
			
			List rejectStageListNew2=new ArrayList();
			List rejectStageListNew3=new ArrayList();
			for(int j=0;j< rejectStageListNew.size();j++){
				IInsuranceGC stageObj=(IInsuranceGC)rejectStageListNew.get(j);
				if("Y".equals(stageObj.getIsProcessed()) && "Y".equals(stageObj.getDeprecated())){
					rejectStageListNew3.add(stageObj);
				}
			}
			for(int i=0;i<actualList.size();i++){
				for(int j=0;j<rejectStageListNew.size();j++){
						IInsuranceGC actualObj=(IInsuranceGC)actualList.get(i);
					//	IInsuranceGC stageObj=(IInsuranceGC)rejectStageList.get(j);
						IInsuranceGC stageObj=(IInsuranceGC)rejectStageListNew.get(j);
							if(actualObj.getInsuranceCode().equals(stageObj.getInsuranceCode())){
									if(!actualObj.getIsProcessed().equals(stageObj.getIsProcessed())){
										removeList.add(actualObj);
										//actualList.remove(actualObj);
									}else if (actualObj.getIsProcessed().equals(stageObj.getIsProcessed()) && actualObj.getDeprecated().equals(stageObj.getDeprecated())){
										rejectStageListNew2.add(stageObj);
									}
							}
					}
				}
			
			
			if(null!=rejectStageListNew && null!=rejectStageListNew2){
				rejectStageListNew.removeAll(rejectStageListNew2);
			}
			if(null!=rejectStageListNew && null!=rejectStageListNew3){
				rejectStageListNew.removeAll(rejectStageListNew3);
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
			
			if(rejectStageListNew!=null){			
				allInsurance.addAll(rejectStageListNew);	
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
			//result.put("actualMap", actualMap);
			
			}
		catch(Exception e){
			throw new InsuranceCGException("ERROR-- MakerPrepareProcessInsuranceCmd.doExecute");
			
		}
		//result.put("insuranceList", insuranceList);
		result.put("calculatedDP", map.get("calculatedDP"));
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return temp;
	}

	
	
	
}
