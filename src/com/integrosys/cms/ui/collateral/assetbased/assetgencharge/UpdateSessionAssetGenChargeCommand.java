package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.FORM_COLLATERAL_OBJ;
import static com.integrosys.cms.ui.collateral.CollateralConstant.PARENT_PAGE;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SERVICE_COLLATERAL_OBJ;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_PARENT_PAGE;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

import static com.integrosys.cms.ui.common.constant.IGlobalConstant.SELECTED_INDEX;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.SUB_EVENT;

public class UpdateSessionAssetGenChargeCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
			{ SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE },
			{ FORM_COLLATERAL_OBJ, IGeneralCharge.class.getName(), FORM_SCOPE},
			{ SUB_EVENT, String.class.getName(), REQUEST_SCOPE},
			{ SELECTED_INDEX, String.class.getName(), REQUEST_SCOPE },
			{ PARENT_PAGE, String.class.getName(), REQUEST_SCOPE },
			{ SESSION_PARENT_PAGE, String.class.getName(), SERVICE_SCOPE },
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { 
			{ SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE }, 
			{ SUB_EVENT, String.class.getName(), SERVICE_SCOPE},
			{ SELECTED_INDEX, String.class.getName(), SERVICE_SCOPE },
			{ PARENT_PAGE, String.class.getName(), REQUEST_SCOPE },
			{ SESSION_PARENT_PAGE, String.class.getName(), SERVICE_SCOPE },
		});
	}
	
	public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		ICollateralTrxValue collateralTrx = (ICollateralTrxValue) inputMap.get(SERVICE_COLLATERAL_OBJ);
		IGeneralCharge formCollateral = (IGeneralCharge) inputMap.get(FORM_COLLATERAL_OBJ);
		IGeneralCharge sessionStagingCol = (IGeneralCharge) collateralTrx.getStagingCollateral();
		
		sessionStagingCol.setBankingArrangement(formCollateral.getBankingArrangement());
		resultMap.put(SUB_EVENT, inputMap.get(SUB_EVENT));
		resultMap.put(SERVICE_COLLATERAL_OBJ, collateralTrx);
		
		String parentPageFrom = (String) inputMap.get(PARENT_PAGE);
		String sessionParentPageFrom = (String) inputMap.get(SESSION_PARENT_PAGE);
		String parentPage = StringUtils.isNotBlank(parentPageFrom) ? parentPageFrom : sessionParentPageFrom ;
		resultMap.put(PARENT_PAGE, parentPage);
		resultMap.put(SESSION_PARENT_PAGE, parentPage);
		
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);

		return returnMap;
	}
}
