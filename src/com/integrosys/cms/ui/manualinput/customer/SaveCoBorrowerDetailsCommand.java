package com.integrosys.cms.ui.manualinput.customer;

import static com.integrosys.cms.ui.common.constant.IGlobalConstant.SELECTED_INDEX;
import static com.integrosys.cms.ui.manualinput.IManualInputConstants.CO_BORROWER_DETAILS_KEY;

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
import static com.integrosys.cms.ui.manualinput.IManualInputConstants.SESSION_CUSTOMER;

public class SaveCoBorrowerDetailsCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] { 
			{ SESSION_CUSTOMER, ICMSCustomer.class.getName(), SERVICE_SCOPE },
			{ CO_BORROWER_DETAILS_KEY, ICoBorrowerDetails.class.getName(), FORM_SCOPE},
			{ SELECTED_INDEX, String.class.getName(), SERVICE_SCOPE },
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { SESSION_CUSTOMER, ICMSCustomer.class.getName(), FORM_SCOPE }, };
	}
	
	public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		ICMSCustomer customer = (ICMSCustomer) inputMap.get(SESSION_CUSTOMER);
		ICoBorrowerDetails coBorrower = (ICoBorrowerDetails) inputMap.get(CO_BORROWER_DETAILS_KEY);
		ICMSLegalEntity sessionLegalEntity = (ICMSLegalEntity) customer.getCMSLegalEntity();
		String index = (String) inputMap.get(SELECTED_INDEX);
		updateCoBorrower(sessionLegalEntity, coBorrower, index);
		 
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		resultMap.put(SESSION_CUSTOMER, customer);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);

		return returnMap;
	}
	
	private static void updateCoBorrower(ICMSLegalEntity sessionLegalEntity, ICoBorrowerDetails coBorrower,
			String index) {
		if (coBorrower != null) {
			List<ICoBorrowerDetails> coBorrowerDetailsList = sessionLegalEntity.getCoBorrowerDetails();
			int indexInt = Integer.parseInt(index)-1;
			coBorrowerDetailsList.set(indexInt, coBorrower);
			sessionLegalEntity.setCoBorrowerDetails(coBorrowerDetailsList);
		}
	}
}
