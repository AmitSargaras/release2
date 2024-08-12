package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.common.constant.IGlobalConstant.REFERRER_EVENT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.CollateralConstant;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.vo.ILeadBankStock;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class PrepareEditLeadBankStockCommand extends AbstractCommand implements CollateralConstant {

	public String[][] getParameterDescriptor() {
		return new String[][] { 
			{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
			{ DUE_DATE_AND_STOCK_DETAILS_KEY, ILeadBankStock.class.getName(), FORM_SCOPE },
			{ SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE },
			{ SELECTED_LNB_STOCK_INDEX, String.class.getName(), REQUEST_SCOPE },
			{ REFERRER_EVENT, String.class.getName(), REQUEST_SCOPE },
			{ EVENT, String.class.getName(), REQUEST_SCOPE },
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
			{ LEAD_BANK_STOCK_KEY, ILeadBankStock.class.getName(), FORM_SCOPE },
			{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE }, 
			{ REFERRER_EVENT, String.class.getName(), REQUEST_SCOPE },
			{ SELECTED_LNB_STOCK_INDEX, String.class.getName(), REQUEST_SCOPE },
			{ EVENT, String.class.getName(), REQUEST_SCOPE },
			{ LEAD_BANK_STOCK_ACTION, String.class.getName(), REQUEST_SCOPE },
			{ SESSION_LEAD_BANK_REFERRER_EVENT, String.class.getName(), SERVICE_SCOPE },
			{ SESSION_LEAD_BANK_SUB_EVENT, String.class.getName(), SERVICE_SCOPE },
			{ SESSION_SELECTED_LNB_STOCK_INDEX, String.class.getName(), SERVICE_SCOPE },
		};
	}

	public HashMap<String, Map<String, ?>> doExecute(HashMap inputMap)
			throws CommandProcessingException, CommandValidationException {
		
		String event = (String) inputMap.get(EVENT);
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		IGeneralChargeDetails sessionChargeDetail = (IGeneralChargeDetails) inputMap
				.get(SESSION_DUE_DATE_AND_STOCK_DETAILS);
		List<ILeadBankStock>  sessionLeadBankStockList = sessionChargeDetail.getLeadBankStock();
		String selectedIndex = (String) inputMap.get(SELECTED_LNB_STOCK_INDEX);
		if(StringUtils.isNotEmpty(selectedIndex)) {
			int idx = Integer.parseInt(selectedIndex)-1;
			ILeadBankStock leadBankStock = sessionLeadBankStockList.get(idx);
			resultMap.put(LEAD_BANK_STOCK_KEY, leadBankStock);
		}
		
		resultMap.put(SESSION_DUE_DATE_AND_STOCK_DETAILS, sessionChargeDetail);
		resultMap.put(SELECTED_LNB_STOCK_INDEX, selectedIndex);
		resultMap.put(REFERRER_EVENT, (String)inputMap.get(REFERRER_EVENT));
		resultMap.put(SESSION_LEAD_BANK_REFERRER_EVENT, (String)inputMap.get(REFERRER_EVENT));
		resultMap.put(SESSION_SELECTED_LNB_STOCK_INDEX, selectedIndex);
		resultMap.put(EVENT, event);
		resultMap.put(SESSION_LEAD_BANK_SUB_EVENT, event);
		resultMap.put(LEAD_BANK_STOCK_ACTION, ICMSUIConstant.ACTION_EDIT);
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
