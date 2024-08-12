package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.DUE_DATE_AND_STOCK_DETAILS_KEY;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SERVICE_COLLATERAL_OBJ;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_DUE_DATE_AND_STOCK_DETAILS;

import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class UpdateSessionDueDateAndStockCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
			{ DUE_DATE_AND_STOCK_DETAILS_KEY, IGeneralChargeDetails.class.getName(), FORM_SCOPE},
			{ SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE },
			{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
		//	{ "totalLonable", "java.lang.String", REQUEST_SCOPE },
			{ "totalLoanable", "java.lang.String", REQUEST_SCOPE },
			{ IGlobalConstant.REFERRER_EVENT, String.class.getName(), REQUEST_SCOPE },
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] { 
			{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
	//		{ "totalLonable", "java.lang.String", SERVICE_SCOPE },
			{ "totalLoanable", "java.lang.String", SERVICE_SCOPE },
			{ IGlobalConstant.REFERRER_EVENT, String.class.getName(), REQUEST_SCOPE },

		};
	}

	public HashMap<String, Map<String, ?>> doExecute(HashMap inputMap)
			throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		IGeneralChargeDetails sessionChargeDetail = (IGeneralChargeDetails) inputMap.get(SESSION_DUE_DATE_AND_STOCK_DETAILS);
		IGeneralChargeDetails formChargeDetail = (IGeneralChargeDetails) inputMap.get(DUE_DATE_AND_STOCK_DETAILS_KEY);
		
		loadFormChargeToSession(formChargeDetail, sessionChargeDetail);
		
		String totalLoanable =(String) inputMap.get("totalLoanable");
		String sssss =(String) inputMap.get("totalLoanable");
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$$$$$$$$ sssss=="+sssss);
		if(null==totalLoanable ||  "".equals(totalLoanable)) {
			totalLoanable=sessionChargeDetail.getTotalLoanable();
			
		}
		System.out.println("totalLonable in UpdateSessionDueDateAndStockCommand==="+totalLoanable);
		
		resultMap.put("totalLoanable", totalLoanable);

		resultMap.put(SESSION_DUE_DATE_AND_STOCK_DETAILS, sessionChargeDetail);
		resultMap.put(IGlobalConstant.REFERRER_EVENT, (String)inputMap.get(IGlobalConstant.REFERRER_EVENT));
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	private static void loadFormChargeToSession(IGeneralChargeDetails formChargeDetail,
			IGeneralChargeDetails sessionChargeDetail) {
		if(formChargeDetail==null || sessionChargeDetail==null)
			return;
		sessionChargeDetail.setDocCode(formChargeDetail.getDocCode());
		sessionChargeDetail.setLocation(formChargeDetail.getLocation());
		sessionChargeDetail.setStockdocMonth(formChargeDetail.getStockdocMonth());
		sessionChargeDetail.setStockdocYear(formChargeDetail.getStockdocYear());
		sessionChargeDetail.setDpCalculateManually(formChargeDetail.getDpCalculateManually());
		sessionChargeDetail.setDpShare(formChargeDetail.getDpShare());
		sessionChargeDetail.setCalculatedDP(formChargeDetail.getCalculatedDP());
		sessionChargeDetail.setDpForCashFlowOrBudget(formChargeDetail.getDpForCashFlowOrBudget());
		sessionChargeDetail.setActualDp(formChargeDetail.getActualDp());
		
		sessionChargeDetail.setRemarkByMaker(formChargeDetail.getRemarkByMaker());
		sessionChargeDetail.setTotalLoanable(formChargeDetail.getTotalLoanable());

		
	}
}
