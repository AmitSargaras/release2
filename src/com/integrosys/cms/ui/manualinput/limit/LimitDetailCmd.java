package com.integrosys.cms.ui.manualinput.limit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.common.UIUtil;

public class LimitDetailCmd extends AbstractCommand {

	@Override
	public HashMap doExecute(HashMap map) throws CommandValidationException,
			CommandProcessingException, AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		
		List newLimitDashboardList1=new ArrayList();
		String totalInrValue="";
		String totalFdRequired="";
		// double totalInrValueDouble=0.00;
		double totalFdRequireDouble=0.00;
		try{
		ILimitTrxValue iLimitTrxValue = (ILimitTrxValue)map.get("lmtTrxObj");
		ILimit iLimit = (ILimit)map.get("lmtDetailForm");
		List limitDashboardList = (List)map.get("limitDashboardList");

		List newLimitDashboardList = (List)map.get("newLimitDashboardList");
		
		String fdMargin = (String)map.get("fdMargin");
		String fdRequired  = (String)map.get("fdRequired");
		String currency = (String)map.get("currency");
		String currencyRate = (String)map.get("currencyRate");
		String dashboardLineNo = (String)map.get("dashboardLineNo");
		String limitAmount = (String)map.get("limitAmount");
		String facilityName = (String)map.get("facilityName");
		String limitInInr = (String)map.get("limitInInr");
		
		LimitCalculationItem limitCalculationItem=new LimitCalculationItem();
		limitCalculationItem.setCurrency(currency);
		limitCalculationItem.setCurrencyRate(currencyRate);
		limitCalculationItem.setFacilityName(facilityName);
		limitCalculationItem.setFdMargin(fdMargin);
		limitCalculationItem.setFdRequired(fdRequired);
		limitCalculationItem.setLimitAmount(limitAmount);
		limitCalculationItem.setLimitInInr(limitInInr);
		limitCalculationItem.setLineNo(dashboardLineNo);
		
		if(null==newLimitDashboardList){
			newLimitDashboardList1.add(limitCalculationItem);
			result.put("newLimitDashboardList", newLimitDashboardList1);
		}else{
			newLimitDashboardList.add(limitCalculationItem);
			result.put("newLimitDashboardList", newLimitDashboardList);
		}
		newLimitDashboardList = (List)result.get("newLimitDashboardList");
		
		DefaultLogger.debug(this, "newLimitDashboardList:"+newLimitDashboardList);
		
		if(null!=newLimitDashboardList){
			for(int i=0;i<newLimitDashboardList.size(); i++){
				
				LimitCalculationItem lmtCalculationItem= (LimitCalculationItem)newLimitDashboardList.get(i);
//				String limitInInr2 = lmtCalculationItem.getLimitInInr();
//			
//				limitInInr2=limitInInr2.replaceAll(",", "");
//				double limitInInrDouble = Double.parseDouble(limitInInr2);
//				
//				totalInrValueDouble=totalInrValueDouble+limitInInrDouble;
//				
				String fdRequired2 = lmtCalculationItem.getFdRequired();
				fdRequired2=fdRequired2.replaceAll(",", "");
				double fdRequiredDouble = Double.parseDouble(fdRequired2);
				totalFdRequireDouble=totalFdRequireDouble+fdRequiredDouble;
			}
		}
	//	totalInrValue=BigDecimal.valueOf(totalInrValueDouble).toPlainString();
		totalFdRequired=BigDecimal.valueOf(totalFdRequireDouble).toPlainString();
		
	//	result.put("totalInrValue", totalInrValue);
		result.put("totalFdRequired", totalFdRequired);
		
		DefaultLogger.debug(this, "iLimitTrxValue:"+iLimitTrxValue);
		DefaultLogger.debug(this, " iLimit:"+iLimit);
		//result.put(arg0, arg1);
		
		}
		catch(Exception e){
			e.printStackTrace();
			DefaultLogger.debug(this,e.getMessage());
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	@Override
	public String[][] getParameterDescriptor() {
		return (new String[][] {{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE},
				{ "lmtDetailForm", "com.integrosys.cms.app.limit.bus.ILimit", FORM_SCOPE },
				{"newLimitDashboardList","java.util.List", SERVICE_SCOPE},
				{"fdMargin","java.lang.String",REQUEST_SCOPE},
				{"fdRequired","java.lang.String",REQUEST_SCOPE},
				{"limitInInr","java.lang.String",REQUEST_SCOPE},
				{"currency","java.lang.String",REQUEST_SCOPE},
				{"currencyRate","java.lang.String",REQUEST_SCOPE},
				{"dashboardLineNo","java.lang.String",REQUEST_SCOPE},
				{"facilityName","java.lang.String",REQUEST_SCOPE},
				{"limitAmount","java.lang.String",REQUEST_SCOPE},
				});
	}
	
	@Override

	public String[][] getResultDescriptor() {
		return (new String[][] {{"newLimitDashboardList","java.util.List", SERVICE_SCOPE},
				//{"totalInrValue","java.lang.String", REQUEST_SCOPE},
				{"totalFdRequired","java.lang.String", REQUEST_SCOPE},
			
		});
}
	
}	

