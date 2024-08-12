package com.integrosys.cms.ui.manualinput.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICoBorrowerDetails;
import com.integrosys.cms.ui.manualinput.customer.bankingmethod.IBankingMethodDAO;

import static com.integrosys.cms.ui.manualinput.IManualInputConstants.SESSION_CO_BORROWER_DETAILS_KEY;
import static com.integrosys.cms.ui.manualinput.IManualInputConstants.SESSION_CUSTOMER;

public class UpdateSessionPartyDetailsCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] { 
			{ "OBCMSCustomer", ICMSCustomer.class.getName(), FORM_SCOPE },
			{ SESSION_CO_BORROWER_DETAILS_KEY, List.class.getName(), SERVICE_SCOPE },
			{ "coBorrowerList", List.class.getName(), SERVICE_SCOPE},
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] { 
			{ SESSION_CUSTOMER, ICMSCustomer.class.getName(), SERVICE_SCOPE },
			{ SESSION_CO_BORROWER_DETAILS_KEY, List.class.getName(), SERVICE_SCOPE },
			{ "bankingMethodVals", "java.lang.String", SERVICE_SCOPE },	
			{ "OBCMSCustomer", ICMSCustomer.class.getName(), FORM_SCOPE },
			};
	}

	public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		ICMSCustomer customer = (ICMSCustomer) inputMap.get("OBCMSCustomer");
		List<ICoBorrowerDetails> coBorrowerList = (List<ICoBorrowerDetails>) inputMap.get(SESSION_CO_BORROWER_DETAILS_KEY);
		String bankingMethodVals = null;
		
		if(customer != null && customer.getCMSLegalEntity()!=null)
			customer.getCMSLegalEntity().setCoBorrowerDetails(coBorrowerList);
		
		
		if(null == resultMap.get(SESSION_CO_BORROWER_DETAILS_KEY)) {
			if(null != customer.getCMSLegalEntity() &&  null != customer.getCMSLegalEntity().getCoBorrowerDetails()) {
				resultMap.put(SESSION_CO_BORROWER_DETAILS_KEY, customer.getCMSLegalEntity().getCoBorrowerDetails());
			}else {
				resultMap.put(SESSION_CO_BORROWER_DETAILS_KEY, new ArrayList<ICoBorrowerDetails>());
			}
		}
		
		if(customer != null)
		{
			IBankingMethodDAO bankingMethodDAOImpl = (IBankingMethodDAO)BeanHouse.get("bankingMethodDAO");
			String customerPartyId = customer.getCifId();
			bankingMethodVals = bankingMethodDAOImpl.getBankingMethodByCustId(customerPartyId);
			
			if(customer.getFinalBankMethodList() == null || "".equals(customer.getFinalBankMethodList())) {
				customer.setFinalBankMethodList(bankingMethodVals);
			}
		}
		
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		resultMap.put("OBCMSCustomer", customer);
		resultMap.put("bankingMethodVals", bankingMethodVals);
		resultMap.put(SESSION_CUSTOMER, customer);
	//	resultMap.put(SESSION_CO_BORROWER_DETAILS_KEY, coBorrowerList);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);

		return returnMap;
	}
}
