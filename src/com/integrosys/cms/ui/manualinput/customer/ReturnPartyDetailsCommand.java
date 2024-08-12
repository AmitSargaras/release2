package com.integrosys.cms.ui.manualinput.customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;

import static com.integrosys.cms.app.common.constant.IUIConstants.FROM_PAGE;
import static com.integrosys.cms.ui.manualinput.IManualInputConstants.SESSION_CO_BORROWER_DETAILS_KEY;
import static com.integrosys.cms.ui.manualinput.IManualInputConstants.SESSION_CUSTOMER;

public class ReturnPartyDetailsCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] { 
			{ "OBCMSCustomer", ICMSCustomer.class.getName(), SERVICE_SCOPE },
			{ SESSION_CUSTOMER, ICMSCustomer.class.getName(), SERVICE_SCOPE },
			{ SESSION_CO_BORROWER_DETAILS_KEY, List.class.getName(), SERVICE_SCOPE },
			{ FROM_PAGE, String.class.getName(), REQUEST_SCOPE },
			{ "event", "java.lang.String", REQUEST_SCOPE },

		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] { 
			{ "OBCMSCustomer", ICMSCustomer.class.getName(), FORM_SCOPE },
			{ SESSION_CUSTOMER, ICMSCustomer.class.getName(), SERVICE_SCOPE },
			{ FROM_PAGE, String.class.getName(), REQUEST_SCOPE },
			{ "event", "java.lang.String", SERVICE_SCOPE },

		};
	}

	public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		ICMSCustomer customer = (ICMSCustomer) inputMap.get(SESSION_CUSTOMER);

		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		resultMap.put("OBCMSCustomer", customer);
		String event = (String) inputMap.get("event");

		resultMap.put(SESSION_CUSTOMER, customer);
		resultMap.put(FROM_PAGE, (String)inputMap.get(FROM_PAGE));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		resultMap.put("event", event);


		return returnMap;
	}

}
