package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.FORM_COLLATERAL_OBJ;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SERVICE_COLLATERAL_OBJ;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_COLLATERAL_ID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.common.UIUtil;

public class RemoveDueDateAndStockCommand  extends AbstractCommand{
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE },
				{ "dueDateAndStockRemovableIndexes", String.class.getName(), REQUEST_SCOPE },
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE },
				{ SESSION_COLLATERAL_ID, String.class.getName(), SERVICE_SCOPE },
				
		});
	}
	
	public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		ICollateralTrxValue collateralTrx = (ICollateralTrxValue) inputMap.get(SERVICE_COLLATERAL_OBJ);
		String removableIndexes = (String) inputMap.get("dueDateAndStockRemovableIndexes");
		
		if(StringUtils.isNotBlank(removableIndexes)) {
			String[] indexArray = removableIndexes.split("-");
			
			IGeneralCharge stagingCollateral = (IGeneralCharge) collateralTrx.getStagingCollateral();
			IGeneralChargeDetails[] chargeDetails = stagingCollateral.getGeneralChargeDetails();
			List<IGeneralChargeDetails> chargeDetailsList = new ArrayList<IGeneralChargeDetails>(
					Arrays.asList(chargeDetails));

			UIUtil.removeElements(chargeDetailsList, indexArray);
			chargeDetails = chargeDetailsList.toArray(new IGeneralChargeDetails[0]);
			stagingCollateral.setGeneralChargeDetails(chargeDetails);
		}
	    resultMap.put(SERVICE_COLLATERAL_OBJ, collateralTrx);
	    resultMap.put(FORM_COLLATERAL_OBJ, collateralTrx.getStagingCollateral());
	    String collateralId = String.valueOf(collateralTrx.getCollateral().getCollateralID());
	    resultMap.put(SESSION_COLLATERAL_ID, collateralId);

		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);

		return returnMap;
	}

}
