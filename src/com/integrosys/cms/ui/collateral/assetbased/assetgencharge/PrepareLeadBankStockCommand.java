package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.collateral.CollateralConstant;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class PrepareLeadBankStockCommand  extends AbstractCommand implements CollateralConstant,ICMSUIConstant {

	@SuppressWarnings("rawtypes")
	@Override
	public HashMap<String, Map<String, ?>> doExecute(HashMap inputMap)
			throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		String leadBankSubEvent = (String) inputMap.get(SESSION_LEAD_BANK_SUB_EVENT);

		resultMap.put(IGlobalConstant.REFERRER_EVENT,(String) inputMap.get(SESSION_LEAD_BANK_REFERRER_EVENT));
		resultMap.put(COL_ASSET_ID, (String) inputMap.get(SESSION_LEAD_BANK_COL_ASSET_ID));
		resultMap.put(SELECTED_LNB_STOCK_INDEX, (String) inputMap.get(SESSION_SELECTED_LNB_STOCK_INDEX));
		
		resultMap.put(EVENT,  leadBankSubEvent);
		
		resultMap.put(LEAD_BANK_STOCK_ACTION, AssetGenChargeAction.EVENT_PREPARE_ADD_LEAD_BANK_STOCK.equals(leadBankSubEvent) ? ACTION_ADD: ACTION_EDIT);
		
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		returnMap.put(COMMAND_RESULT_MAP, resultMap);

		return returnMap;
	}

	@Override
	public String[][] getParameterDescriptor() {
		return new String[][] {
			{ SESSION_LEAD_BANK_COL_ASSET_ID, String.class.getName(), SERVICE_SCOPE },
			{ SESSION_SELECTED_LNB_STOCK_INDEX, String.class.getName(), SERVICE_SCOPE },
			{ SESSION_LEAD_BANK_REFERRER_EVENT, String.class.getName(), SERVICE_SCOPE },
			{ SESSION_LEAD_BANK_SUB_EVENT, String.class.getName(), SERVICE_SCOPE },
		};
	}

	@Override
	public String[][] getResultDescriptor() {
		return new String[][] {
			{ IGlobalConstant.REFERRER_EVENT, String.class.getName(), REQUEST_SCOPE },
			{ SELECTED_LNB_STOCK_INDEX, String.class.getName(), REQUEST_SCOPE },
			{ COL_ASSET_ID, String.class.getName(), REQUEST_SCOPE },
			{ EVENT, String.class.getName(), REQUEST_SCOPE },
			{ LEAD_BANK_STOCK_ACTION, String.class.getName(), REQUEST_SCOPE },
		};
	}

}
