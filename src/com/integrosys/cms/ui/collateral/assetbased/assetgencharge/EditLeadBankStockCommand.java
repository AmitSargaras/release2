package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.common.constant.IGlobalConstant.REFERRER_EVENT;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.SUB_EVENT;

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

public class EditLeadBankStockCommand extends AbstractCommand implements CollateralConstant {

	public String[][] getParameterDescriptor() {
		return new String[][] { 
			{ LEAD_BANK_STOCK_KEY, ILeadBankStock.class.getName(), FORM_SCOPE },
			{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
			{ SESSION_LEAD_BANK_STOCK_LIST, List.class.getName(), SERVICE_SCOPE },
			{ SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE }, 
			{ EVENT, String.class.getName(), REQUEST_SCOPE },
			{ REFERRER_EVENT, String.class.getName(), REQUEST_SCOPE },
			{ SELECTED_LNB_STOCK_INDEX, String.class.getName(), REQUEST_SCOPE },
			{ SESSION_SELECTED_LNB_STOCK_INDEX, String.class.getName(), SERVICE_SCOPE },

		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
			{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
			{ SESSION_LEAD_BANK_STOCK_LIST, List.class.getName(), SERVICE_SCOPE },
			{ SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE },
			{ SUB_EVENT, String.class.getName(), REQUEST_SCOPE },
			{ REFERRER_EVENT, String.class.getName(), REQUEST_SCOPE },
		};
	}

	public HashMap<String, Map<String, ?>> doExecute(HashMap inputMap)
			throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		ILeadBankStock stock = (ILeadBankStock) inputMap.get(LEAD_BANK_STOCK_KEY);
		IGeneralChargeDetails sessionChargeDetails = (IGeneralChargeDetails) inputMap.get(SESSION_DUE_DATE_AND_STOCK_DETAILS);
		String index = (String) inputMap.get(SELECTED_LNB_STOCK_INDEX);
		
		if(StringUtils.isBlank(index))
			index = (String) inputMap.get(SESSION_SELECTED_LNB_STOCK_INDEX);
		
		editStock(sessionChargeDetails, stock, index);
			
		resultMap.put(SESSION_DUE_DATE_AND_STOCK_DETAILS, sessionChargeDetails);
		resultMap.put(SUB_EVENT, "return");
		resultMap.put(REFERRER_EVENT, (String)inputMap.get(REFERRER_EVENT));
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	private static void editStock(IGeneralChargeDetails sessionChargeDetails, ILeadBankStock updatedStock, String index) {
		if (updatedStock != null && StringUtils.isNotBlank(index)) {
			List<ILeadBankStock> sessionStockList = sessionChargeDetails.getLeadBankStock();
			if (sessionStockList != null) {
				int idx = Integer.parseInt(index)-1;
				sessionStockList.set(idx, updatedStock);
				sessionChargeDetails.setLeadBankStock(sessionStockList);
			}	
		}
	}
	
}
