package com.integrosys.cms.ui.manualinput.customer;

import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import static com.integrosys.cms.ui.manualinput.customer.ManualInputCustomerAction.EVENT_VIEW_CO_BORROWER_DETAILS;
import static com.integrosys.cms.ui.manualinput.customer.ManualInputCustomerAction.EVENT_EDIT_CO_BORROWER_DETAILS;
import static com.integrosys.cms.ui.manualinput.customer.ManualInputCustomerAction.EVENT_ADD_CO_BORROWER_DETAILS;
import static com.integrosys.cms.ui.manualinput.customer.ManualInputCustomerAction.EVENT_REMOVE_CO_BORROWER_DETAILS;

public class PrepareCoBorrowerDetailsCommand extends AbstractCommand {

	public String[][] getResultDescriptor() {
		return new String[][] {};
	}

	public String[][] getParameterDescriptor() {
		return new String[][] { };
	}
	
	public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		String event = (String) inputMap.get(EVENT);
		
		if(EVENT_VIEW_CO_BORROWER_DETAILS.equals(event)) {
			viewCoBorrower();
		}else if (EVENT_EDIT_CO_BORROWER_DETAILS.equals(event)) {
			editCoBorrower();
		}else if (EVENT_EDIT_CO_BORROWER_DETAILS.equals(event)) {
			addCoBorrower();
		}else if (EVENT_EDIT_CO_BORROWER_DETAILS.equals(event)) {
			removeCoBorrower();
		}
		
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);

		return returnMap;
	}

	private void viewCoBorrower() {
		
	}
	
	private void editCoBorrower() {
		
	}
	
	private boolean addCoBorrower() {
		return false;
	}

	private boolean removeCoBorrower() {
		return false;
	}
	
	
}
