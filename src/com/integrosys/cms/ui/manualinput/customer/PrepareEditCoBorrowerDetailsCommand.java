package com.integrosys.cms.ui.manualinput.customer;

import static com.integrosys.cms.ui.common.constant.IGlobalConstant.SELECTED_INDEX;
import static com.integrosys.cms.ui.manualinput.IManualInputConstants.CO_BORROWER_DETAILS_KEY;
import static com.integrosys.cms.ui.manualinput.IManualInputConstants.CO_BORROWER_LIAB_ID_LIST;
import static com.integrosys.cms.ui.manualinput.IManualInputConstants.SESSION_CUSTOMER;

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

public class PrepareEditCoBorrowerDetailsCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] { 
			{ SESSION_CUSTOMER, ICMSCustomer.class.getName(), SERVICE_SCOPE },
			{ CO_BORROWER_DETAILS_KEY, OBCoBorrowerDetails.class.getName(), FORM_SCOPE },
			{ SELECTED_INDEX, String.class.getName(), SERVICE_SCOPE },
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] { 
			{ SESSION_CUSTOMER, ICMSCustomer.class.getName(), SERVICE_SCOPE},
			{ CO_BORROWER_LIAB_ID_LIST, String.class.getName(), SERVICE_SCOPE },
			{ CO_BORROWER_DETAILS_KEY, OBCoBorrowerDetails.class.getName(), FORM_SCOPE },
			{ SELECTED_INDEX, String.class.getName(), SERVICE_SCOPE },
			};
	}

	public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		ICMSCustomer customer = (ICMSCustomer) inputMap.get(SESSION_CUSTOMER);
		ICMSLegalEntity sessionLegalEntity = (ICMSLegalEntity) customer.getCMSLegalEntity();
		
		String selectedIndex = (String)inputMap.get(SELECTED_INDEX);
		int selectedIndexInt = Integer.parseInt(selectedIndex);
		String coBorrowerLiabIdList = "";
		if(sessionLegalEntity!=null) {
			List<ICoBorrowerDetails> coBorrowerList = sessionLegalEntity.getCoBorrowerDetails();
			if(!CollectionUtils.isEmpty(coBorrowerList)) {
				for(int i=0; i<coBorrowerList.size(); i++) {
					if(i != selectedIndexInt)
						coBorrowerLiabIdList = coBorrowerLiabIdList+coBorrowerList.get(i).getCoBorrowerLiabId()+",";
				}
				if(coBorrowerLiabIdList.length()>1)
					coBorrowerLiabIdList = coBorrowerLiabIdList.substring(0, coBorrowerLiabIdList.length()-1);
			}
		}
		ICoBorrowerDetails coBorrower = (ICoBorrowerDetails) inputMap.get(CO_BORROWER_DETAILS_KEY);
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		resultMap.put(SESSION_CUSTOMER, customer);
		resultMap.put(CO_BORROWER_LIAB_ID_LIST, coBorrowerLiabIdList);
		resultMap.put(CO_BORROWER_DETAILS_KEY, coBorrower);
		resultMap.put(SELECTED_INDEX, selectedIndex);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);

		return returnMap;
	}

}