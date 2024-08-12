package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.CollateralConstant;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.vo.ILeadBankStock;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.vo.OBLeadBankStock;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class PrepareAddLeadBankStockCommand extends AbstractCommand implements CollateralConstant {

	public String[][] getParameterDescriptor() {
		return new String[][] {
			{ SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE },
			{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
			{ COL_ASSET_ID, String.class.getName(), REQUEST_SCOPE },
			{ IGlobalConstant.REFERRER_EVENT, String.class.getName(), REQUEST_SCOPE },
			{ EVENT, String.class.getName(), REQUEST_SCOPE },
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] { 
			{ LEAD_BANK_STOCK_KEY, ILeadBankStock.class.getName(), FORM_SCOPE },
			{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
			{ COL_ASSET_ID, String.class.getName(), REQUEST_SCOPE },
			{ IGlobalConstant.REFERRER_EVENT, String.class.getName(), REQUEST_SCOPE },
			{ SESSION_LEAD_BANK_COL_ASSET_ID, String.class.getName(), SERVICE_SCOPE },
			{ EVENT, String.class.getName(), REQUEST_SCOPE },
			{ LEAD_BANK_STOCK_ACTION, String.class.getName(), REQUEST_SCOPE },
			{ SESSION_LEAD_BANK_REFERRER_EVENT, String.class.getName(), SERVICE_SCOPE },
			{ SESSION_LEAD_BANK_SUB_EVENT, String.class.getName(), SERVICE_SCOPE },
		};
	}

	public HashMap<String, Map<String, ?>> doExecute(HashMap inputMap)
			throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		IGeneralChargeDetails sessionChargeDetail = (IGeneralChargeDetails) inputMap.get(SESSION_DUE_DATE_AND_STOCK_DETAILS);
		
		resultMap.put(SESSION_DUE_DATE_AND_STOCK_DETAILS, sessionChargeDetail);
		resultMap.put(LEAD_BANK_STOCK_KEY, new OBLeadBankStock());
		resultMap.put(COL_ASSET_ID, (String)inputMap.get(COL_ASSET_ID));
		resultMap.put(IGlobalConstant.REFERRER_EVENT, (String)inputMap.get(IGlobalConstant.REFERRER_EVENT));
		
		resultMap.put(SESSION_LEAD_BANK_REFERRER_EVENT, (String)inputMap.get(IGlobalConstant.REFERRER_EVENT));
		
		resultMap.put(SESSION_LEAD_BANK_COL_ASSET_ID, (String)inputMap.get(COL_ASSET_ID));
		resultMap.put(EVENT, (String)inputMap.get(EVENT));
		resultMap.put(SESSION_LEAD_BANK_SUB_EVENT, (String)inputMap.get(EVENT));
		
		resultMap.put(LEAD_BANK_STOCK_ACTION, ICMSUIConstant.ACTION_ADD);
		
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		returnMap.put(COMMAND_RESULT_MAP, resultMap);

		return returnMap;
	}

}
