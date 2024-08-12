package com.integrosys.cms.ui.manualinput.customer;

import static com.integrosys.cms.app.common.constant.IUIConstants.FROM_PAGE;
import static com.integrosys.cms.ui.manualinput.IManualInputConstants.CO_BORROWER_DETAILS_KEY;
import static com.integrosys.cms.ui.manualinput.IManualInputConstants.SESSION_CO_BORROWER_DETAILS_KEY;
import static com.integrosys.cms.ui.manualinput.IManualInputConstants.SESSION_CUSTOMER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;
import com.integrosys.cms.app.customer.bus.ICoBorrowerDetails;

public class AddCoBorrowerDetailsCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] { 
			{ SESSION_CUSTOMER, ICMSCustomer.class.getName(), SERVICE_SCOPE },
			{CO_BORROWER_DETAILS_KEY, ICoBorrowerDetails.class.getName(), FORM_SCOPE},
			{"cifId","java.lang.String", REQUEST_SCOPE},
			{ FROM_PAGE, String.class.getName(), REQUEST_SCOPE },
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] { 
			{ SESSION_CUSTOMER, ICMSCustomer.class.getName(), FORM_SCOPE },
			{ SESSION_CO_BORROWER_DETAILS_KEY, List.class.getName(), SERVICE_SCOPE },
			{"cifId","java.lang.String", SERVICE_SCOPE},
			{ FROM_PAGE, String.class.getName(), REQUEST_SCOPE },
		};
	}

	public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		ICMSCustomer customer = (ICMSCustomer) inputMap.get(SESSION_CUSTOMER);
		ICoBorrowerDetails coBorrower = (ICoBorrowerDetails) inputMap.get(CO_BORROWER_DETAILS_KEY);
		ICMSLegalEntity sessionLegalEntity = (ICMSLegalEntity) customer.getCMSLegalEntity();
		String cifId=(String) inputMap.get("cifId");

		addCoBorrower(sessionLegalEntity, coBorrower);
		 
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		resultMap.put(SESSION_CUSTOMER, customer);
		resultMap.put("cifId", cifId);
		resultMap.put(SESSION_CO_BORROWER_DETAILS_KEY, sessionLegalEntity.getCoBorrowerDetails());
		resultMap.put(FROM_PAGE, (String)inputMap.get(FROM_PAGE));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);

		return returnMap;
	}

	private static void addCoBorrower(ICMSLegalEntity sessionLegalEntity, ICoBorrowerDetails coBorrower) {
		if (coBorrower != null) {
			List<ICoBorrowerDetails> coBorrowerDetailsList = sessionLegalEntity.getCoBorrowerDetails();
			if (coBorrowerDetailsList == null)
				coBorrowerDetailsList = new ArrayList<ICoBorrowerDetails>();
			coBorrowerDetailsList.add(coBorrower);
			sessionLegalEntity.setCoBorrowerDetails(coBorrowerDetailsList);
		}
	}
	
}