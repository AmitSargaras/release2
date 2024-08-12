package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_DUE_DATE_AND_STOCK_SUB_ACTION;
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
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;

public class RemoveLeadBankStockCommand extends AbstractCommand implements CollateralConstant {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
				{ SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE },
				{ SESSION_DUE_DATE_AND_STOCK_SUB_ACTION, String.class.getName(), SERVICE_SCOPE },
				{ "leadBankStockRemovableIndexes", String.class.getName(), REQUEST_SCOPE}
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
				{ REFERRER_EVENT, String.class.getName(), REQUEST_SCOPE },
		};
	}

	public HashMap<String, Map<String, ?>> doExecute(HashMap inputMap)
			throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String removableIndexes = (String) inputMap.get("leadBankStockRemovableIndexes");
		IGeneralChargeDetails sessionChargeDetails = (IGeneralChargeDetails) inputMap.get(SESSION_DUE_DATE_AND_STOCK_DETAILS);

		String dueDateStockAction = (String) inputMap.get(SESSION_DUE_DATE_AND_STOCK_SUB_ACTION);
		
		if(StringUtils.isNotBlank(removableIndexes)) {
			String[] indexArray = removableIndexes.split("-");
			List<ILeadBankStock>  sessionLeadBankStock = sessionChargeDetails.getLeadBankStock();

			UIUtil.removeElements(sessionLeadBankStock, indexArray);
			
			sessionChargeDetails.setLeadBankStock(sessionLeadBankStock);
		}
		resultMap.put(SESSION_DUE_DATE_AND_STOCK_DETAILS, sessionChargeDetails);
		
		resultMap.put(REFERRER_EVENT, ICMSUIConstant.ACTION_ADD.equals(dueDateStockAction)?
				AssetGenChargeAction.EVENT_ADD_DUE_DATE_AND_STOCK:AssetGenChargeAction.EVENT_EDIT_DUE_DATE_AND_STOCK);
		
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
