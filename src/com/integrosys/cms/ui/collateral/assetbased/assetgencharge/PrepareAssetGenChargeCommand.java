//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.REQUEST_DUE_DATE_AND_STOCK_FORM_LIST;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SERVICE_COLLATERAL_OBJ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.util.MapperUtil;
import com.integrosys.cms.ui.collateral.CollateralAction;
import com.integrosys.cms.ui.collateral.assetbased.PrepareAssetBasedCommand;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 3:05:13 PM
 * To change this template use Options | File Templates.
 */
public class PrepareAssetGenChargeCommand extends PrepareAssetBasedCommand {
	
	public String[][] getParameterDescriptor() {
		String[][] thisDesc =new String[][] {
				{ "calculatedDP", "java.lang.String", ICommonEventConstant.SERVICE_SCOPE },
				{ "dueDate", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "dpShare", "java.lang.String", ICommonEventConstant.SERVICE_SCOPE },
				{ "dpCalculateManually", "java.lang.String", ICommonEventConstant.SERVICE_SCOPE },
				{ "stockdocMonth", "java.lang.String", ICommonEventConstant.SERVICE_SCOPE },
				{ "stockdocYear", "java.lang.String", ICommonEventConstant.SERVICE_SCOPE },
				{ "remarkByMaker", "java.lang.String", ICommonEventConstant.SERVICE_SCOPE },
				{ "totalLonable", "java.lang.String", ICommonEventConstant.SERVICE_SCOPE },
				{ "migrationFlag", "java.lang.String", ICommonEventConstant.SERVICE_SCOPE },
				
				{ "event", String.class.getName() , REQUEST_SCOPE },
				{  SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName() , SERVICE_SCOPE },
				}; 
		String[][] fromSuper = super.getParameterDescriptor();
		return mergeDescriptor(thisDesc, fromSuper);
	}

	public String[][] getResultDescriptor() {
		String[][] thisDesc = PrepareAssetGenChargeCommandHelper.getResultDescriptor();
		String[][] fromSuper = super.getResultDescriptor();
		return super.mergeResultDescriptor(thisDesc, fromSuper);
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		PrepareAssetGenChargeCommandHelper.fillPrepare(map, result, exceptionMap);

		String event = (String) map.get("event");
		ICollateralTrxValue collateralTrx = (ICollateralTrxValue) map.get(SERVICE_COLLATERAL_OBJ);
		if(CollateralAction.EVENT_PREPARE_FORM.equals(event)) {
			
			IGeneralCharge sessionStageCollateral = (IGeneralCharge) collateralTrx.getStagingCollateral();
			
			List<DueDateAndStockSummaryForm> dueDateAndStockFormList = new ArrayList<DueDateAndStockSummaryForm>();
			
			Map<String, String> statements = sessionStageCollateral.getDueDateAndStockStatements();
			if(statements==null)
				statements = Collections.emptyMap();
			
			
			if(!ArrayUtils.isEmpty(sessionStageCollateral.getGeneralChargeDetails())) {
				for(IGeneralChargeDetails chargeDetail : sessionStageCollateral.getGeneralChargeDetails()) {
					DueDateAndStockSummaryForm itemForm = new DueDateAndStockSummaryForm();
					
					String dueDate = MapperUtil.dateToString(chargeDetail.getDueDate(), null);
					itemForm.setDueDate(dueDate);
					
					String statement = statements.get(chargeDetail.getDocCode());
					itemForm.setStatementName(MapperUtil.emptyIfNull(statement));
					
					String dpToBeCalcManually = chargeDetail.getDpCalculateManually();
					dpToBeCalcManually = dpToBeCalcManually==null ? "NO" : dpToBeCalcManually;
					itemForm.setDpCalcManually(dpToBeCalcManually);
					
					String dpShare = MapperUtil.emptyIfNull(chargeDetail.getDpShare());
					itemForm.setDpShare(dpShare);
					
					String dpAsPerStockStatement = MapperUtil.emptyIfNull(chargeDetail.getCalculatedDP());
					itemForm.setDpAsPerStockStatement(dpAsPerStockStatement);
					
					String dpForCashFlow = MapperUtil.bigDecimalToString(chargeDetail.getDpForCashFlowOrBudget());
					itemForm.setDpForCashFlowOrBudget(dpForCashFlow);
					
					String chargeDetailId = String.valueOf(chargeDetail.getGeneralChargeDetailsID());
					itemForm.setGcDetailId(chargeDetailId);
					dueDateAndStockFormList.add(itemForm);
				}
				result.put(REQUEST_DUE_DATE_AND_STOCK_FORM_LIST, dueDateAndStockFormList);
			}
		}
		
		HashMap fromSuper = super.doExecute(map);
		result.put("calculatedDP", map.get("calculatedDP"));
		result.put("dueDate", map.get("dueDate"));
		
		result.put("dpShare", map.get("dpShare"));
		result.put("remarkByMaker", map.get("remarkByMaker"));
		//start santosh 
		result.put("dpCalculateManually", map.get("dpCalculateManually"));
		//End santosh
		result.put("stockdocMonth", map.get("stockdocMonth"));
		result.put("stockdocYear", map.get("stockdocYear"));
		
		String migrationFlag =(String) map.get("migrationFlag");
		String totalLonable =(String) map.get("totalLonable");
		if(null==totalLonable ||  "".equals(totalLonable)) {
			totalLonable="0";
		}
		System.out.println("totalLonable==="+totalLonable);

		result.put("totalLonable", totalLonable);
		
		result.put("migrationFlag", migrationFlag);
		result.put(SERVICE_COLLATERAL_OBJ, collateralTrx);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, super.mergeResultMap(result, fromSuper));
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, super.mergeExceptionMap(exceptionMap, fromSuper));
		return temp;
	}
	protected String[][] mergeDescriptor(String[][] other, String[][] original) {

		if ((original.length == 0) && (other.length == 0)) {
			return new String[0][];
		}
		if (original.length == 0) {
			return other;
		}
		if (other.length == 0) {
			return original;
		}
		String[][] returnString = new String[other.length + original.length][];
		System.arraycopy(original, 0, returnString, 0, original.length);
		System.arraycopy(other, 0, returnString, original.length, other.length);
		return returnString;
	}
}
