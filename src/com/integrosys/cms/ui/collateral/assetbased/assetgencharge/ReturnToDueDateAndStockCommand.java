package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.DUE_DATE_AND_STOCK_DETAILS_KEY;
import static com.integrosys.cms.ui.collateral.CollateralConstant.DUE_DATE_LIST;
import static com.integrosys.cms.ui.collateral.CollateralConstant.DUE_DATE_VALUE;
import static com.integrosys.cms.ui.collateral.CollateralConstant.GC_DETAIL_ID;
import static com.integrosys.cms.ui.collateral.CollateralConstant.LOCATION_LIST;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SERVICE_COLLATERAL_OBJ;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_DUE_DATE_AND_STOCK_DETAILS;
import static com.integrosys.cms.ui.collateral.CollateralConstant.STOCK_DOC_MONTH_LIST;
import static com.integrosys.cms.ui.collateral.CollateralConstant.STOCK_DOC_YEAR_LIST;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.SELECTED_INDEX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.businfra.LabelValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class ReturnToDueDateAndStockCommand extends AbstractCommand {
	
	private ICollateralDAO collateralDao;
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE},
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, ICMSCustomer.class.getName(), GLOBAL_SCOPE },
				{ EVENT, String.class.getName(), REQUEST_SCOPE },
				{ GC_DETAIL_ID, String.class.getName(), REQUEST_SCOPE },
				{ SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE },
				{ DUE_DATE_VALUE, String.class.getName(), REQUEST_SCOPE },
				{ SELECTED_INDEX, String.class.getName(), REQUEST_SCOPE },
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ DUE_DATE_AND_STOCK_DETAILS_KEY, IGeneralChargeDetails.class.getName(), FORM_SCOPE},
				{ LOCATION_LIST,  List.class.getName(), REQUEST_SCOPE },
				{ DUE_DATE_LIST, List.class.getName(), REQUEST_SCOPE },
				{ STOCK_DOC_MONTH_LIST, List.class.getName(), REQUEST_SCOPE},
				{ STOCK_DOC_YEAR_LIST, List.class.getName(), REQUEST_SCOPE},				
				{ EVENT, String.class.getName(), REQUEST_SCOPE },
				{ DUE_DATE_VALUE, String.class.getName(), REQUEST_SCOPE },
				{ SELECTED_INDEX, String.class.getName(), REQUEST_SCOPE },
		});
	}
	
	public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String event = (String) inputMap.get(EVENT);
		IGeneralChargeDetails sessionChargeDetails = (IGeneralChargeDetails) inputMap.get(SESSION_DUE_DATE_AND_STOCK_DETAILS);
		
		setCollateralDao((ICollateralDAO) BeanHouse.get("collateralDao"));		
		ICMSCustomer customer = (ICMSCustomer) inputMap.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
	    List<LabelValue> dueDateList= collateralDao.getDueDateList(customer.getCustomerID());
		resultMap.put(DUE_DATE_LIST, dueDateList);
		
	    ICollateralTrxValue collateralTrx = (ICollateralTrxValue) inputMap.get(SERVICE_COLLATERAL_OBJ);
		long collateralId = collateralTrx.getCollateral().getCollateralID();
		List<LabelValue> locationList = collateralDao.getStockDocLocations(sessionChargeDetails.getDocCode(), collateralId);
		resultMap.put(LOCATION_LIST, locationList);
		
	    List<LabelValue> stockDocMonthList = new ArrayList<LabelValue>();
	    resultMap.put(STOCK_DOC_MONTH_LIST, stockDocMonthList);
	    
	    List<LabelValue> stockDocYearList = new ArrayList<LabelValue>();
	    resultMap.put(STOCK_DOC_YEAR_LIST, stockDocYearList);

	    resultMap.put(DUE_DATE_AND_STOCK_DETAILS_KEY, sessionChargeDetails);
	    resultMap.put(SERVICE_COLLATERAL_OBJ, collateralTrx);
		resultMap.put(EVENT, event);
		
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	public void setCollateralDao(ICollateralDAO collateralDao) {
		this.collateralDao = collateralDao;
	}

}
