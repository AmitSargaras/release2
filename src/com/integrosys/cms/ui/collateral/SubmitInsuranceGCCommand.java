package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import java.util.List;

import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGCJdbc;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.OBInsuranceGC;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGC;

public class SubmitInsuranceGCCommand extends AbstractCommand{
	
	private IInsuranceGCDao insuranceGcDao;
	
	
	
	public IInsuranceGCDao getInsuranceGcDao() {
		return insuranceGcDao;
	}

	public void setInsuranceGcDao(IInsuranceGCDao insuranceGcDao) {
		this.insuranceGcDao = insuranceGcDao;
	}
	
	

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", FORM_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },	
				{ "calculatedDP", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "insuranceList",  "java.util.List", SERVICE_SCOPE },
				 { "event", "java.lang.String", REQUEST_SCOPE }
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
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue",REQUEST_SCOPE }, 
				{ "calculatedDP", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
        HashMap temp = new HashMap();
        ICollateralTrxValue trxValue = (ICollateralTrxValue) map.get("serviceColObj");
        String event=(String)map.get("event");
		List insuranceList=(List)map.get("insuranceList");		
		IInsuranceGCJdbc insuranceGCJdbc = (IInsuranceGCJdbc) BeanHouse.get("insuranceGcJdbc");
		if(insuranceList!=null){
		IInsuranceGCDao insuranceGCDao = (IInsuranceGCDao) BeanHouse.get("insuranceGcDao");
		if("submit".equals(event)){
		for(int i=0;i<insuranceList.size();i++){
			OBInsuranceGC insurance= (OBInsuranceGC)insuranceList.get(i);
			long parent=insurance.getParentId();
			Date toDay=new Date();
			if(insurance!=null){
				if(insurance.getIsProcessed().equals("YR")&& insurance.getDeprecated().equals("Y")){
					//Rejected & Deleted
					insurance.setIsProcessed("N");
					insurance.setLastUpdatedBy(trxValue.getLoginId());
					insurance.setLastUpdatedOn(toDay);
					IInsuranceGC ins=insuranceGCDao.updateInsurance(IInsuranceGCDao.STAGE_INSURANCE_NAME, insurance);
					}
				else if(insurance.getIsProcessed().equals("YR")&& insurance.getDeprecated().equals("N")){
						boolean check=allActual(parent,insurance);
						if(check==true)
							insurance.setIsProcessed("NM");
						else if(check==false)
							insurance.setIsProcessed("N");
						insurance.setLastUpdatedBy(trxValue.getLoginId());
						insurance.setLastUpdatedOn(toDay);
						IInsuranceGC ins=insuranceGCDao.updateInsurance(IInsuranceGCDao.STAGE_INSURANCE_NAME, insurance);
						}
				else if(insurance.getIsProcessed().equals("NM")&& insurance.getDeprecated().equals("N")){
					// Maker submit Edit
					IInsuranceGC gc = (IInsuranceGC)ReplicateUtils.replicateObject(insurance,new String[] { "id"});
					gc.setLastUpdatedBy(trxValue.getLoginId());
					gc.setLastUpdatedOn(toDay);
					insuranceGCDao.createInsurance(IInsuranceGCDao.STAGE_INSURANCE_NAME, gc);
					}
				else if(insurance.getIsProcessed().equals("N")&& insurance.getDeprecated().equals("N")){
					//New Add
					insurance.setLastUpdatedBy(trxValue.getLoginId());
					insurance.setLastUpdatedOn(toDay);
					IInsuranceGC ins=insuranceGCDao.createInsurance(IInsuranceGCDao.STAGE_INSURANCE_NAME, insurance);
					}
				else if((insurance.getIsProcessed().equals("N")&& insurance.getDeprecated().equals("Y"))
						||(insurance.getIsProcessed().equals("DE")&& insurance.getDeprecated().equals("Y"))){
					//Maker submit delete
					if(insurance.getIsProcessed().equals("DE")){
						insurance.setIsProcessed("N");
						insurance.setLastUpdatedBy(trxValue.getLoginId());
						insurance.setLastUpdatedOn(toDay);
						IInsuranceGC ins=insuranceGCDao.updateInsurance(IInsuranceGCDao.STAGE_INSURANCE_NAME, insurance);
						//insurance.setId(0);
					}
					else{
					IInsuranceGC gc = (IInsuranceGC)ReplicateUtils.replicateObject(insurance,new String[] { "id"});
					gc.setLastUpdatedBy(trxValue.getLoginId());
					gc.setLastUpdatedOn(toDay);
					insuranceGCDao.createInsurance(IInsuranceGCDao.STAGE_INSURANCE_NAME, gc);
					}
					}
				
		}
		}
		}
		if("update".equals(event)){
			for(int i=0;i<insuranceList.size();i++){
				OBInsuranceGC insurance= (OBInsuranceGC)insuranceList.get(i);
				long parent=insurance.getParentId();
				Date toDay=new Date();
				if(insurance!=null){
					String collateralID=insurance.getInsuranceCode();
						//Draft
						int count=insuranceGCJdbc.getActualCount(collateralID);
						if(count>0 && insurance.getDeprecated().equals("N")&& insurance.getIsProcessed().equals("NM")){
							insurance.setIsProcessed("DE");
							insurance.setLastUpdatedBy(trxValue.getLoginId());
							insurance.setLastUpdatedOn(toDay);
							IInsuranceGC ins=insuranceGCDao.createInsurance(IInsuranceGCDao.STAGE_INSURANCE_NAME, insurance);
						}else if(count>0 && insurance.getDeprecated().equals("Y")){
							insurance.setIsProcessed("DD");
							insurance.setLastUpdatedBy(trxValue.getLoginId());
							insurance.setLastUpdatedOn(toDay);
							IInsuranceGC ins=insuranceGCDao.createInsurance(IInsuranceGCDao.STAGE_INSURANCE_NAME, insurance);
						}else if(count==0){
							insurance.setIsProcessed("DA");
							insurance.setLastUpdatedBy(trxValue.getLoginId());
							insurance.setLastUpdatedOn(toDay);
							IInsuranceGC ins=insuranceGCDao.createInsurance(IInsuranceGCDao.STAGE_INSURANCE_NAME, insurance);
						}
					
			}
			}
		}
		}
		result.put("calculatedDP", map.get("calculatedDP"));
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return temp;
		
	}
	
	public boolean allActual(long parent,IInsuranceGC insurance){
		List actualList=new ArrayList();
		boolean value=false;
		IInsuranceGCJdbc insuranceGCJdbc = (IInsuranceGCJdbc) BeanHouse.get("insuranceGcJdbc");
		String parentID=String.valueOf(parent);
		SearchResult allActualInsuranceList= (SearchResult)  insuranceGCJdbc.getAllActualInsurance(parentID);
		actualList=(List) allActualInsuranceList.getResultList();
		for(int i=0;i<actualList.size();i++){
			IInsuranceGC actualObj=(IInsuranceGC)actualList.get(i);
			if(actualObj.getInsuranceCode().equals(insurance.getInsuranceCode())){
				value= true;
				break;
				
			}
			else
				value=false;
		}
		
		return value;
	}
}
