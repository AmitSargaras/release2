package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.CollateralHelper;
import com.integrosys.cms.ui.collateral.IInsuranceGCDao;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class DeleteListInsuranceCommand extends AbstractCommand {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				//{"actualMap", " java.util.HashMap", SERVICE_SCOPE},
				 { "calculatedDP", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "insuranceList",  "java.util.List", SERVICE_SCOPE },
				{ "insuranceCode",  "java.lang.String", REQUEST_SCOPE },
				{ "select",  "java.lang.String", REQUEST_SCOPE },
				{ "serviceColObj", ICollateralTrxValue.class.getName() , SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, Locale.class.getName(), GLOBAL_SCOPE },
				});
		
	}
	
	
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "calculatedDP", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "insuranceList",  "java.util.List", SERVICE_SCOPE },
				{ "serviceColObj", ICollateralTrxValue.class.getName() , SERVICE_SCOPE },
				//{"actualMap", " java.util.HashMap", SERVICE_SCOPE},
				});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap result = new HashMap();
		
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		IGeneralCharge stagingCollateral = (IGeneralCharge) itrxValue.getStagingCollateral();
		
		List insuranceList=(List)map.get("insuranceList");
		HashMap actualMap= new HashMap();
		String insuranceCode=(String)map.get("select");
		IInsuranceGCDao  insuranceGCDao = (IInsuranceGCDao) BeanHouse.get("insuranceGcDao");
		IInsuranceGCJdbc insuranceGCJdbc = (IInsuranceGCJdbc) BeanHouse.get("insuranceGcJdbc");
		String[] abc = insuranceCode.split("-");
		List deleteList = Arrays.asList(abc);
		List deleteInsList =new ArrayList();
		List actualList=new ArrayList();
		try{
			
			if(insuranceList!=null && deleteList!=null){
				for(int i=0;i<deleteList.size();i++){
					String deleteCode=(String)deleteList.get(i);
					for(int j=0;j<insuranceList.size();j++){
						IInsuranceGC s = (IInsuranceGC)insuranceList.get(j);
						if(s.getInsuranceCode().equals(deleteCode)){
							if(s.getIsProcessed().equals("N")||s.getIsProcessed().equals("DA")){
								//OBInsuranceGC newIns =replicateInsurance(s);
								if(s.getIsProcessed().equals("DA")){
									s.setIsProcessed("C");
									IInsuranceGC ins=insuranceGCDao.updateInsurance(IInsuranceGCDao.STAGE_INSURANCE_NAME, s);
								}
								insuranceList.remove(s);
							}
							else if(s.getIsProcessed().equals("Y")||s.getIsProcessed().equals("DE")||s.getIsProcessed().equals("NM")){
								insuranceList.remove(s);
								s.setDeprecated("Y");
								if(s.getIsProcessed().equals("DE")){
									s.setIsProcessed("DE");	
								}else{
								s.setIsProcessed("N");	
								}
								deleteInsList.add(s);
							}
							else if(s.getIsProcessed().equals("YR")){
								SearchResult allActualInsuranceList= (SearchResult)  insuranceGCJdbc.getAllActualInsurance(String.valueOf(s.getParentId()));
								
								if(allActualInsuranceList!=null){
									actualList=(List) allActualInsuranceList.getResultList();
									for(int cur=0;cur<actualList.size();cur++){
									IInsuranceGC actualObj=(IInsuranceGC)actualList.get(cur);
									String actualId=String.valueOf(actualObj.getId());
									//long parentId=actualObj.getParentId();
									actualMap.put(actualId,actualObj);
									
									}
								}
								if((actualMap.containsKey(String.valueOf(s.getId())))==true){
									insuranceList.remove(s);
									s.setDeprecated("Y");
									s.setIsProcessed("N");						
									deleteInsList.add(s);
									
								}
								
								else
									insuranceList.remove(s);
							}
							
						}
					}
				}
			}
			if(deleteInsList!=null){
			insuranceList.addAll(deleteInsList);
			}
			
			CollateralHelper.updateSecurityCoverageDetails(stagingCollateral,insuranceList);
			itrxValue.setStagingCollateral(stagingCollateral);
			result.put("serviceColObj", itrxValue);
			
			result.put("calculatedDP", map.get("calculatedDP"));
			result.put("insuranceList", insuranceList);
			//result.put("actualMap", actualMap);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return returnMap;
	}
	
public OBInsuranceGC replicateInsurance(OBInsuranceGC s){
		
		OBInsuranceGC newIns=new OBInsuranceGC();
		
		newIns.setInsuranceCode(s.getInsuranceCode());
		newIns.setCreationDate(s.getCreationDate());
		newIns.setParentId(s.getParentId());
		newIns.setId(s.getId());
		newIns.setIsProcessed("C");
		newIns.setDeprecated(s.getDeprecated());
		newIns.setCoverNoteNo(s.getCoverNoteNo());
		newIns.setEffectiveDate(s.getEffectiveDate());
		newIns.setExpiryDate(s.getExpiryDate());
		newIns.setReceivedDate(s.getReceivedDate());
		newIns.setInsuranceCompany(s.getInsuranceCompany());
		newIns.setInsuranceCoverge(s.getInsuranceCoverge());
		newIns.setInsuranceCurrency(s.getInsuranceCurrency());
		newIns.setInsuranceDefaulted(s.getInsuranceDefaulted());
		newIns.setInsurancePolicyAmt(s.getInsurancePolicyAmt());
		newIns.setInsurancePolicyNo(s.getInsurancePolicyNo());
		newIns.setInsurancePremium(s.getInsurancePremium());
		newIns.setInsuranceRequired(s.getInsuranceRequired());
		newIns.setInsuredAmount(s.getInsuredAmount());
		newIns.setRemark(s.getRemark());
		if(s.getSelectComponent()!=null){
			newIns.setSelectComponent(s.getSelectComponent());
		}
		/*if(s.getAllComponent()!=null){
			newIns.setAllComponent(s.getAllComponent());
		}*/
		newIns.setLastApproveBy(s.getLastApproveBy());
		newIns.setLastApproveOn(s.getLastApproveOn());
		newIns.setLastUpdatedBy(s.getLastUpdatedBy());
		newIns.setLastUpdatedOn(s.getLastUpdatedOn());
		
		return newIns;
	}

}

	
