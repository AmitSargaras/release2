package com.integrosys.cms.ui.manualinput.customer;

import java.util.HashMap;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class ValidateLEICodeChangeCommand extends AbstractCommand{

	@Override
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
			{ "leiCode", "java.lang.String", REQUEST_SCOPE },
			{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", FORM_SCOPE },
			{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE }
		});
	}

	@Override
	public String[][] getResultDescriptor() {
		return (new String[][] {	
			{ "relationshipMgr", "java.lang.String", REQUEST_SCOPE },
			{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", SERVICE_SCOPE },
			{ "OBCMSCustomerNew","com.integrosys.cms.app.customer.bus.ICMSCustomer",SERVICE_SCOPE }
		});
	}

	
	
	@Override
	public HashMap doExecute(HashMap map)
			throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		String leiCode = (String) map.get("leiCode");
		ICMSCustomer obCustomer = (OBCMSCustomer)map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);				
		obCustomer.setIsLeiValidated('N');
		
		resultMap.put("OBCMSCustomer", obCustomer);
		resultMap.put("OBCMSCustomerNew", obCustomer);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}	

}
