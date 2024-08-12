package com.integrosys.cms.batch.valuation;

import java.util.List;

/**
 * Batch Job for Collateral Valuation.
 * 
 * @author Chong Jun Yong
 * 
 */
public interface IValuationMain {
	/**
	 * Process a list valuation model which contains collateral valuation basic
	 * info.
	 * 
	 * @param valuationModelList list of collateral valuation model.
	 */
	public void processValuationModelList(List valuationModelList);
}
