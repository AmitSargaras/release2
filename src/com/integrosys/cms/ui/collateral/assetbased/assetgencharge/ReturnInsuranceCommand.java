package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.collateral.IInsuranceGCDao;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class ReturnInsuranceCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "displayList",  "java.util.List", SERVICE_SCOPE },
				 {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				 { "calculatedDP", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				//{ "insuranceList",  "java.util.List", SERVICE_SCOPE },
				
				});
		
	}
	
	
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "tab", "java.lang.String", SERVICE_SCOPE },
//				{ "fundedShare", "java.lang.String", SERVICE_SCOPE },
				
				{ "dpShare", "java.lang.String", SERVICE_SCOPE },
				{ "isEditable", "java.lang.String", SERVICE_SCOPE },
				{ "releasableAmount", "java.math.BigDecimal", SERVICE_SCOPE },
				{ "calculatedDP", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "dueDate", "java.lang.String", SERVICE_SCOPE },
				{ "displayList",  "java.util.List", SERVICE_SCOPE },
				{ "dueDateList",  "java.util.List", SERVICE_SCOPE},
				{ "filterLocationList",  "java.util.List", SERVICE_SCOPE },
				//{ "insuranceList",  "java.util.List", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "alertRequired",  "java.util.List", SERVICE_SCOPE },
				{ "isStockDetailsAdded",  "java.util.List", SERVICE_SCOPE },
				});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap result = new HashMap();
		List insuranceList=(List)map.get("insuranceList");
		
		try{
			
			result.put("calculatedDP", map.get("calculatedDP"));
			//result.put("insuranceList", insuranceList);
			result.put("event", map.get("event"));
		}
		catch(Exception ex){
			
		}
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return returnMap;
	}

}
