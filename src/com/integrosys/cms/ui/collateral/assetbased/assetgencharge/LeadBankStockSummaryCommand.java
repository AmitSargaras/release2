package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.CollateralConstant;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.vo.ILeadBankStock;

public class LeadBankStockSummaryCommand extends AbstractCommand implements CollateralConstant {

	public String[][] getParameterDescriptor() {
		return new String[][] { 
			{EVENT, String.class.getName(), REQUEST_SCOPE},
			{SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE},
			{SESSION_LEAD_BANK_STOCK_LIST, List.class.getName(), SERVICE_SCOPE},
			{DUE_DATE_AND_STOCK_DETAILS_KEY, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE},
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
			{DUE_DATE_AND_STOCK_DETAILS_KEY, IGeneralChargeDetails.class.getName(), FORM_SCOPE}
		};
	}

	public HashMap<String, Map<String, ?>> doExecute(HashMap inputMap)
			throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		IGeneralChargeDetails dueDateAndStockDetails = (IGeneralChargeDetails) inputMap
				.get(DUE_DATE_AND_STOCK_DETAILS_KEY);
		@SuppressWarnings("unchecked")
		List<ILeadBankStock> sessionLNBStockList = (List<ILeadBankStock>) inputMap
				.get(SESSION_LEAD_BANK_STOCK_LIST);
		if(dueDateAndStockDetails!=null && !CollectionUtils.isEmpty(sessionLNBStockList))
			dueDateAndStockDetails.setLeadBankStock(sessionLNBStockList);

		resultMap.put(DUE_DATE_AND_STOCK_DETAILS_KEY, dueDateAndStockDetails);
		resultMap.put(SESSION_LEAD_BANK_STOCK_LIST, null);
		
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
