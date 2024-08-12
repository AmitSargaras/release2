package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.DUE_DATE_AND_STOCK_DETAILS_KEY;
import static com.integrosys.cms.ui.collateral.CollateralConstant.DUE_DATE_LIST;
import static com.integrosys.cms.ui.collateral.CollateralConstant.DUE_DATE_MAP;
import static com.integrosys.cms.ui.collateral.CollateralConstant.DUE_DATE_VALUE;
import static com.integrosys.cms.ui.collateral.CollateralConstant.IS_LEAD_BANK_STOCK_BANKING;
import static com.integrosys.cms.ui.collateral.CollateralConstant.LOCATION_LIST;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SERVICE_COLLATERAL_OBJ;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_DUE_DATE_ACTION;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_DUE_DATE_AND_STOCK_DETAILS;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_DUE_DATE_AND_STOCK_FAC_LINE_PENDING_MSG;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_DUE_DATE_AND_STOCK_SUB_ACTION;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_DUE_DATE_AND_STOCK_TOTAL_RELEASED_AMT;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_LOCATION_LIST;
import static com.integrosys.cms.ui.collateral.CollateralConstant.STOCK_DOC_MONTH_LIST;
import static com.integrosys.cms.ui.collateral.CollateralConstant.STOCK_DOC_YEAR_LIST;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_HIGHLIGHTED_DUE_DATE_SET;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.GLOBAL_CUSTOMER_OBJ;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.SUB_EVENT;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IDueDateAndStockBO;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralChargeDetails;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.MapperUtil;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.DueDateAndStockHelper.LeadBankStockBankingArrangement;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;

public class PrepareAddDueDateAndStockCommand extends AbstractCommand {
	
	private IDueDateAndStockBO dueDateAndStockBO = (IDueDateAndStockBO) BeanHouse.get("dueDateAndStockBO");
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ DUE_DATE_AND_STOCK_DETAILS_KEY, IGeneralChargeDetails.class.getName(), FORM_SCOPE},
				{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE},
				{ GLOBAL_CUSTOMER_OBJ, ICMSCustomer.class.getName(), GLOBAL_SCOPE },
				{ EVENT, String.class.getName(), REQUEST_SCOPE },
				{ SUB_EVENT, String.class.getName(), REQUEST_SCOPE },
				{ SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE },
				{ DUE_DATE_VALUE, String.class.getName(), REQUEST_SCOPE },
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ DUE_DATE_AND_STOCK_DETAILS_KEY, IGeneralChargeDetails.class.getName(), FORM_SCOPE},
				{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE},
				{ DUE_DATE_LIST, List.class.getName(), REQUEST_SCOPE},
				{ DUE_DATE_MAP, Map.class.getName(), SERVICE_SCOPE},
				{ LOCATION_LIST,  List.class.getName(), REQUEST_SCOPE },
				{ SESSION_LOCATION_LIST,  List.class.getName(), SERVICE_SCOPE },
				{ STOCK_DOC_MONTH_LIST, List.class.getName(), REQUEST_SCOPE},
				{ STOCK_DOC_YEAR_LIST, List.class.getName(), REQUEST_SCOPE},
				{ EVENT, String.class.getName(), REQUEST_SCOPE },
				{ SESSION_DUE_DATE_ACTION, String.class.getName(), SERVICE_SCOPE},
				{ SESSION_DUE_DATE_AND_STOCK_SUB_ACTION, String.class.getName(), SERVICE_SCOPE },
				{ IS_LEAD_BANK_STOCK_BANKING, String.class.getName(), SERVICE_SCOPE },
				{ SESSION_DUE_DATE_AND_STOCK_FAC_LINE_PENDING_MSG, String.class.getName(), SERVICE_SCOPE },
				{ SESSION_DUE_DATE_AND_STOCK_TOTAL_RELEASED_AMT, String.class.getName(), SERVICE_SCOPE },
				{ SESSION_HIGHLIGHTED_DUE_DATE_SET, Set.class.getName(), SERVICE_SCOPE },
		});
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String event = (String) inputMap.get(EVENT);
		String subEvent = (String) inputMap.get(SUB_EVENT);
		IGeneralChargeDetails chargeDetail = (IGeneralChargeDetails)inputMap.get(DUE_DATE_AND_STOCK_DETAILS_KEY);
		dueDateAndStockBO.loadDueDateList(inputMap, resultMap);
		
		
		ICollateralTrxValue collateralTrx = (ICollateralTrxValue) inputMap.get(SERVICE_COLLATERAL_OBJ);
		IGeneralCharge sessionStageCollateral = (IGeneralCharge) collateralTrx.getStagingCollateral();
		IGeneralCharge actualCollateral = (IGeneralCharge) collateralTrx.getCollateral();
		String bankingArrangement = sessionStageCollateral.getBankingArrangement();
		resultMap.put(IS_LEAD_BANK_STOCK_BANKING, LeadBankStockBankingArrangement.contains(bankingArrangement)?ICMSConstant.YES:ICMSConstant.NO);
		
		if ("new".equals(subEvent)) {
			chargeDetail = new OBGeneralChargeDetails();
			resultMap.put(SESSION_DUE_DATE_AND_STOCK_SUB_ACTION, ICMSUIConstant.ACTION_ADD);
			 resultMap.put(SESSION_DUE_DATE_ACTION, event);
		} else if ("refreshDueDate".equals(subEvent)) {
			dueDateAndStockBO.prepareDataForRefreshDueDate(inputMap, resultMap, chargeDetail);
		} else if ("viewStocks".equals(subEvent)) {
			dueDateAndStockBO.prepareDataForViewStock(inputMap, resultMap, chargeDetail);
		} else if (subEvent==null || "return".equals(subEvent)) {
			chargeDetail = (IGeneralChargeDetails) inputMap.get(SESSION_DUE_DATE_AND_STOCK_DETAILS);
			dueDateAndStockBO.prepareDataForRefreshDueDate(inputMap, resultMap, chargeDetail);
		}
		
		if(actualCollateral !=null) {
			String isLinePendingError = DueDateAndStockHelper.getIsLinePendingErrorMsg(actualCollateral.getCollateralID());
			resultMap.put(SESSION_DUE_DATE_AND_STOCK_FAC_LINE_PENDING_MSG, isLinePendingError);
			
			BigDecimal totalReleasedAmount = CollateralDAOFactory.getDAO()
					.getTotalLimitReleasedAmtForLinkedFacilities(actualCollateral.getCollateralID());
			resultMap.put(SESSION_DUE_DATE_AND_STOCK_TOTAL_RELEASED_AMT, MapperUtil.bigDecimalToString(totalReleasedAmount));
		}
		
		Set<String> docCodesSet = Collections.emptySet();
		
		IGeneralChargeDetails[] chargeDetails = sessionStageCollateral.getGeneralChargeDetails();
		if(!ArrayUtils.isEmpty(chargeDetails)) {
			docCodesSet = new HashSet<String>();
			for(IGeneralChargeDetails genChargeDetail : chargeDetails) {
				docCodesSet.add(genChargeDetail.getDocCode());
			}
		}
		
	    resultMap.put(DUE_DATE_AND_STOCK_DETAILS_KEY, chargeDetail);
	    resultMap.put(SESSION_DUE_DATE_AND_STOCK_DETAILS, chargeDetail);
		resultMap.put(EVENT, event);
		resultMap.put(SESSION_HIGHLIGHTED_DUE_DATE_SET, docCodesSet);
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
}
