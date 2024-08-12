package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_DUE_DATE_AND_STOCK_DETAILS;

import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;

public class UpdateSessionStockDetailsCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
			{ "form.stockDetailsObject", "java.lang.Object", FORM_SCOPE },
			{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] { 
			{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
		};
	}

	public HashMap<String, Map<String, ?>> doExecute(HashMap inputMap)
			throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		IGeneralChargeDetails newChargeDetails = (IGeneralChargeDetails) inputMap.get("form.stockDetailsObject");
		IGeneralChargeDetails existingChargeDetails = (IGeneralChargeDetails) inputMap.get(SESSION_DUE_DATE_AND_STOCK_DETAILS);

		if(existingChargeDetails !=null) {
			existingChargeDetails.setCoverageAmount(newChargeDetails.getCoverageAmount());
			existingChargeDetails.setAdHocCoverageAmount(newChargeDetails.getAdHocCoverageAmount());
			existingChargeDetails.setCoveragePercentage(newChargeDetails.getCoveragePercentage());
			resultMap.put(SESSION_DUE_DATE_AND_STOCK_DETAILS, existingChargeDetails);
		}
		
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		returnMap.put(COMMAND_RESULT_MAP, resultMap);

		return returnMap;
	}
	
}
