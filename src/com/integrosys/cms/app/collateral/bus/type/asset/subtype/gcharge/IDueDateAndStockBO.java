package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.businfra.search.SearchDAOException;

public interface IDueDateAndStockBO {
	
	public Map<String, String> getStatementNames(Long stageCollateralId) throws SearchDAOException ;
	
	public void loadDueDateList(HashMap<String, Object> inputMap, HashMap<String, Object> resultMap);

	public void prepareDataForView(HashMap<String, Object> inputMap, HashMap<String, Object> resultMap,
			IGeneralChargeDetails chargeDetail);
	
	public void prepareDataForRefreshDueDate(HashMap<String, Object> inputMap, HashMap<String, Object> resultMap,
			IGeneralChargeDetails chargeDetail);

	public void prepareDataForViewStock(HashMap<String, Object> inputMap, HashMap<String, Object> resultMap,
			IGeneralChargeDetails chargeDetail);

}
