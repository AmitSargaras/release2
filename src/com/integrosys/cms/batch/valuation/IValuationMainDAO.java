package com.integrosys.cms.batch.valuation;

import java.util.List;
import java.util.Map;

/**
 * Jdbc Dao to retrieve collaterals that required to be valuated.
 * 
 * @author Chong Jun Yong
 * 
 */
public interface IValuationMainDAO {
	/**
	 * Get number of collaterals to be valuated, based on the parameters passed
	 * in.
	 * 
	 * @param paramMap parameters info to be used as criteria to filter query
	 *        condition.
	 * @return number of collaterals to be valuated.
	 */
	public long getNoOfSecurities(Map paramMap);

	/**
	 * Return list of valuation model, which collaterals required to be
	 * valuated.
	 * 
	 * @param paramMap parameters info to be used as criteria to filter query
	 *        condition.
	 * @param startIndex start index of row number
	 * @param batchSize total number of collaterals required to be valuated for
	 *        this batch.
	 * @return list of valuation model.
	 */
	public List getNextBatchSecurities(Map paramMap, long startIndex, int batchSize);

	/**
	 * To update the flag to indicate the collateral has been valuated for the
	 * collateral id supplied.
	 * @param collateralId cms collateral id
	 */
	public void updateValuatedFlagForCollateral(long collateralId);

}
