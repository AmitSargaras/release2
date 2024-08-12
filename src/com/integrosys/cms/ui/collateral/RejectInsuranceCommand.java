package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.OBInsuranceGC;

public class RejectInsuranceCommand extends AbstractCommand {
	
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
			if(remarks == null || remarks.equals("")){
					temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
					
	            }
			else{
				 if(insuranceList!=null){
					Iterator it = insuranceList.iterator();
					while(it.hasNext()){
						OBInsuranceGC s = (OBInsuranceGC) it.next();
						//Condition for new created 
						if((s.getIsProcessed().equals("N")||s.getIsProcessed().equals("NM"))&& (s.getDeprecated().equals("N")||s.getDeprecated().equals("Y"))){
						s.setIsProcessed("YR");
						insuranceGCDao.updateInsurance("stageInsurance", s);
						
						}
						
						
						}
				}
				 temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			}
		}
		catch(Exception ex){
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
			
		}
		
		
		
		return temp;
	}
	
	

}
