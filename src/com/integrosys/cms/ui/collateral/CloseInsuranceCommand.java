package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.OBInsuranceGC;

public class CloseInsuranceCommand extends AbstractCommand {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, 
				{ "calculatedDP", "java.lang.String", REQUEST_SCOPE },
				{"insuranceList", "java.util.ArrayList", SERVICE_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE},
				});
	}
	
	
	
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		String remarks = (String) map.get("remarks");

		IInsuranceGCDao  insuranceGCDao = (IInsuranceGCDao) BeanHouse.get("insuranceGcDao");
		List insuranceList=(ArrayList)map.get("insuranceList");
		try{
			
				 if(insuranceList!=null){
					Iterator it = insuranceList.iterator();
					while(it.hasNext()){
						OBInsuranceGC s = (OBInsuranceGC) it.next();
						
							//OBInsuranceGC newIns=new OBInsuranceGC();
							if(!(s.getIsProcessed().equals("Y")&& s.getDeprecated().equals("N"))){
							OBInsuranceGC newIns =replicateInsurance(s);
						/*	//newIns=replicateInsurance(s,newIns);
							newIns.setCreationDate(s.getCreationDate());
							newIns.setDeprecated(s.getDeprecated());
							newIns.setId(s.getId());
							newIns.setInsuranceCode(s.getInsuranceCode());							
							newIns.setIsProcessed("C");
							newIns.setParentId(s.getParentId());
							newIns.setVersionTime(s.getVersionTime());
							newIns.setCoverNoteNo(s.getCoverNoteNo());
							newIns.setEffectiveDate(s.getEffectiveDate());
							newIns.setExpiryDate(s.getExpiryDate());
							newIns.setReceivedDate(s.getReceivedDate());
							newIns.setRemark(s.getRemark());
							newIns.setInsuranceCompany(s.getInsuranceCompany());
							newIns.setInsuranceCoverge(s.getInsuranceCoverge());
							newIns.setInsuranceCurrency(s.getInsuranceCurrency());
							newIns.setInsuranceDefaulted(s.getInsuranceDefaulted());
							newIns.setInsurancePolicyAmt(s.getInsurancePolicyAmt());
							newIns.setInsurancePolicyNo(s.getInsurancePolicyNo());
							newIns.setInsurancePremium(s.getInsurancePremium());
							newIns.setInsuranceRequired(s.getInsuranceRequired());
							newIns.setInsuredAmount(s.getInsuredAmount());
							if(s.getSelectComponent()!=null){
								newIns.setSelectComponent(s.getSelectComponent());
							}
							if(s.getAllComponent()!=null){
								newIns.setAllComponent(s.getAllComponent());
							}
							newIns.setLastApproveBy(s.getLastApproveBy());
							newIns.setLastApproveOn(s.getLastApproveOn());
							newIns.setLastUpdatedBy(s.getLastUpdatedBy());
							newIns.setLastUpdatedOn(s.getLastUpdatedOn());*/
						
							insuranceGCDao.updateInsurance(IInsuranceGCDao.STAGE_INSURANCE_NAME, newIns);
							}
						}
				}
				 temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			
		
		}
		catch(Exception ex){
			DefaultLogger.debug(this, "got exception in CloseInsuranceCommand doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
			
		}
		
		
		
		return temp;
	}
	public OBInsuranceGC replicateInsurance(OBInsuranceGC s){
		
		OBInsuranceGC newIns=new OBInsuranceGC();
		
		newIns.setInsuranceCode(s.getInsuranceCode());
		newIns.setCreationDate(s.getCreationDate());
		newIns.setParentId(s.getParentId());
		newIns.setVersionTime(s.getVersionTime());
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



