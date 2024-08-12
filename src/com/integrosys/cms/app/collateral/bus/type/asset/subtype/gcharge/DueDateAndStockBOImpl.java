package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.DUE_DATE_LIST;
import static com.integrosys.cms.ui.collateral.CollateralConstant.DUE_DATE_MAP;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SERVICE_COLLATERAL_OBJ;
import static com.integrosys.cms.ui.collateral.CollateralConstant.LOCATION_LIST;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_LOCATION_LIST;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.util.MapperUtil;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.businfra.LabelValue;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.DueDateAndStockHelper;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class DueDateAndStockBOImpl implements IDueDateAndStockBO {

	private ICollateralDAO collateralDao = (ICollateralDAO) BeanHouse.get("collateralDao");

	@Override
	public Map<String, String> getStatementNames(Long stageCollateralId) throws SearchDAOException {
		return collateralDao.getStatementNames(stageCollateralId);
	}

	public void loadDueDateList(HashMap<String, Object> inputMap, HashMap<String, Object> resultMap) {
		ICMSCustomer customer = (ICMSCustomer) inputMap.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		List<LabelValue> dueDateList = collateralDao.getDueDateList(customer.getCustomerID());
		resultMap.put(DUE_DATE_LIST, dueDateList);
		Map<String,String> dueDateMap = UIUtil.convertLabelValueListToMap(dueDateList);
		resultMap.put(DUE_DATE_MAP, dueDateMap);
	}

	public void loadLocationList(HashMap<String, Object> inputMap, HashMap<String, Object> resultMap, IGeneralChargeDetails chargeDetail) {
		ICollateralTrxValue collateralTrx = (ICollateralTrxValue) inputMap.get(SERVICE_COLLATERAL_OBJ);
		long collateralId = collateralTrx.getCollateral().getCollateralID();
		List<LabelValue> locationList = collateralDao.getStockDocLocations(chargeDetail.getDocCode(), collateralId);
		resultMap.put(LOCATION_LIST, locationList);
		resultMap.put(SESSION_LOCATION_LIST, locationList);
	}
	
	public void prepareDataForView(HashMap<String, Object> inputMap, HashMap<String, Object> resultMap,
			IGeneralChargeDetails chargeDetail) {
		
		loadDueDateList(inputMap, resultMap);
		loadLocationList(inputMap, resultMap, chargeDetail);
		String dueDate = MapperUtil.dateToString(chargeDetail.getDueDate(), null);
		DueDateAndStockHelper.loadStockDocMonthsandYears(dueDate, resultMap);

	}

	public void prepareDataForRefreshDueDate(HashMap<String, Object> inputMap, HashMap<String, Object> resultMap,
			IGeneralChargeDetails chargeDetail) {
		prepareDataForView(inputMap, resultMap, chargeDetail);
	}

	public void prepareDataForViewStock(HashMap<String, Object> inputMap, HashMap<String, Object> resultMap,
			IGeneralChargeDetails chargeDetail) {
		prepareDataForRefreshDueDate(inputMap, resultMap, chargeDetail);

		ICollateralTrxValue collateralTrx = (ICollateralTrxValue) inputMap.get(SERVICE_COLLATERAL_OBJ);
		IGeneralCharge stagingCol = (IGeneralCharge) collateralTrx.getStagingCollateral();
		DueDateAndStockHelper.setStockDetailsForSelectedLocation(chargeDetail, stagingCol);
	}
}
