package com.integrosys.cms.ui.manualinput.customer;

import static com.integrosys.cms.ui.manualinput.IManualInputConstants.CO_BORROWER_LIAB_ID_LIST;
import static com.integrosys.cms.ui.manualinput.IManualInputConstants.SESSION_CUSTOMER;
import static com.integrosys.cms.app.common.constant.IUIConstants.FROM_PAGE;
import static com.integrosys.cms.ui.manualinput.IManualInputConstants.CO_BORROWER_DETAILS_KEY;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;
import com.integrosys.cms.app.customer.bus.ICoBorrowerDetails;
import com.integrosys.cms.app.customer.bus.OBCoBorrowerDetails;

public class PrepareAddCoBorrowerDetailsCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] { 
			{ SESSION_CUSTOMER, ICMSCustomer.class.getName(), SERVICE_SCOPE },
			{"cifId","java.lang.String", REQUEST_SCOPE},
			{ FROM_PAGE, String.class.getName(), REQUEST_SCOPE },			
			};
	}

	public String[][] getResultDescriptor() {
		return new String[][] { 
			{ SESSION_CUSTOMER, ICMSCustomer.class.getName(), SERVICE_SCOPE},
			{ CO_BORROWER_LIAB_ID_LIST, String.class.getName(), SERVICE_SCOPE },
			{ CO_BORROWER_DETAILS_KEY, OBCoBorrowerDetails.class.getName(), FORM_SCOPE },
			{"cifId","java.lang.String", SERVICE_SCOPE},
			{ FROM_PAGE, String.class.getName(), REQUEST_SCOPE },			
			};
	}

	public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		ICMSCustomer customer = (ICMSCustomer) inputMap.get(SESSION_CUSTOMER);
		ICMSLegalEntity sessionLegalEntity = (ICMSLegalEntity) customer.getCMSLegalEntity();
		String cifId=(String) inputMap.get("cifId");
		
		String coBorrowerLiabIdList = "";
		if(sessionLegalEntity!=null) {
			List<ICoBorrowerDetails> coBorrowerList = sessionLegalEntity.getCoBorrowerDetails();
			if(!CollectionUtils.isEmpty(coBorrowerList)) {
				for(ICoBorrowerDetails coBorrower : coBorrowerList) {
					coBorrowerLiabIdList = coBorrowerLiabIdList+coBorrower.getCoBorrowerLiabId()+",";
				}
				if(coBorrowerLiabIdList.length()>1)
					coBorrowerLiabIdList = coBorrowerLiabIdList.substring(0, coBorrowerLiabIdList.length()-1);
			}
		}
			
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		resultMap.put(SESSION_CUSTOMER, customer);
		resultMap.put(CO_BORROWER_LIAB_ID_LIST, coBorrowerLiabIdList);
		resultMap.put("cifId", cifId);
		resultMap.put(CO_BORROWER_DETAILS_KEY, new OBCoBorrowerDetails());
		resultMap.put(FROM_PAGE, (String)inputMap.get(FROM_PAGE));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);

		return returnMap;
	}

}
