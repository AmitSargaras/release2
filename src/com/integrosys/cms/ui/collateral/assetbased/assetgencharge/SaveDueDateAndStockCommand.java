package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.DUE_DATE_AND_STOCK_DETAILS_KEY;
import static com.integrosys.cms.ui.collateral.CollateralConstant.FORM_COLLATERAL_OBJ;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SERVICE_COLLATERAL_OBJ;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_COLLATERAL_ID;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_DUE_DATE_ACTION;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_DUE_DATE_AND_STOCK_DETAILS;
import static com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeAction.EVENT_ADD_DUE_DATE_AND_STOCK;
import static com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeAction.EVENT_EDIT_DUE_DATE_AND_STOCK;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.REFERRER_EVENT;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.SELECTED_INDEX;

import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

public class SaveDueDateAndStockCommand extends AbstractCommand {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ DUE_DATE_AND_STOCK_DETAILS_KEY, IGeneralChargeDetails.class.getName(), FORM_SCOPE},
				{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
				{ SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE },
				{ REFERRER_EVENT, String.class.getName(), REQUEST_SCOPE },
				{ SESSION_DUE_DATE_ACTION, String.class.getName(), SERVICE_SCOPE},
				{ SELECTED_INDEX, String.class.getName(), SERVICE_SCOPE },
				{ "dueDate", "java.lang.String", SERVICE_SCOPE },
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE },
				{ SESSION_COLLATERAL_ID, String.class.getName(), SERVICE_SCOPE },
				{ "dueDate", "java.lang.String", SERVICE_SCOPE },
		});
	}
	
	public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> exceptionMap = new HashMap<String, Object>();
		
		IGeneralChargeDetails chargeDetails = (IGeneralChargeDetails) inputMap.get(DUE_DATE_AND_STOCK_DETAILS_KEY);
		IGeneralChargeDetails sessionChargeDetails = (IGeneralChargeDetails) inputMap.get(SESSION_DUE_DATE_AND_STOCK_DETAILS);
		mergeSessionChargeDetails(chargeDetails, sessionChargeDetails);
		
		ICollateralTrxValue collateralTrx = (ICollateralTrxValue) inputMap.get(SERVICE_COLLATERAL_OBJ);
		IGeneralCharge stagingCol = (IGeneralCharge) collateralTrx.getStagingCollateral();
		String sessionAction = (String)inputMap.get(SESSION_DUE_DATE_ACTION);
		String selectedIndex = (String)inputMap.get(SELECTED_INDEX);
		String dueDateStr=(String)inputMap.get("dueDate");
		System.out.println("***********************inside saveduedatecmd dueDate : "+dueDateStr);
		
		if(stagingCol!=null) {
			boolean isSavedInSession = false;
			if(EVENT_ADD_DUE_DATE_AND_STOCK.equals(sessionAction)) {
				isSavedInSession = stagingCol.addGeneralChargeDetails(chargeDetails);
			}else if(EVENT_EDIT_DUE_DATE_AND_STOCK.equals(sessionAction)) {
				try {
					isSavedInSession = stagingCol.replaceGeneralChargeDetails(Integer.parseInt(selectedIndex), chargeDetails);
				} catch (NumberFormatException e) {
					DefaultLogger.error(this, "Indvalid index: "+selectedIndex, e);
					exceptionMap.put("saveDueDateAndStockError", "Error while updating Due Date And Stock");
				} catch (Exception e) {
					DefaultLogger.error(this, e.getMessage(), e);
					exceptionMap.put("saveDueDateAndStockError", "Error while updating Due Date And Stock");
				}
			}
				
			DefaultLogger.info(this, "Is session save successful: "+isSavedInSession);
		}
		
	    resultMap.put(SERVICE_COLLATERAL_OBJ, collateralTrx);
	    resultMap.put(FORM_COLLATERAL_OBJ, stagingCol);
	    String collateralId = String.valueOf(collateralTrx.getCollateral().getCollateralID());
	    resultMap.put(SESSION_COLLATERAL_ID, collateralId);
	    resultMap.put("dueDate", dueDateStr);
	    
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	private static void mergeSessionChargeDetails(IGeneralChargeDetails chargeDetails, IGeneralChargeDetails sessionChargeDetails) {
		if(chargeDetails==null || sessionChargeDetails==null)
			return;
		chargeDetails.setGeneralChargeStockDetails(sessionChargeDetails.getGeneralChargeStockDetails());
		chargeDetails.setLeadBankStock(sessionChargeDetails.getLeadBankStock());
	}
	
}
