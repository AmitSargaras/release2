package com.integrosys.cms.app.collateral.bus.valuation;

import com.integrosys.cms.app.collateral.bus.ICollateral;

import java.util.List;

/**
 * <p>
 * Collateral valuator to perform system valuation, based on the valuation model
 * given. Able to check the completeness of collateral information, run the
 * actual system valuation.
 * <p>
 * This should be implemented in the way that one collateral type one valuator
 * class, or can be extended to one collateral subtype one valuator class. Check
 * out the subclass for complete view.
 * <p>
 * Prior to use this valuator, caller should prepare the valuation model
 * instance (use {@link #createValuationModelInstance()}) then populate relevant
 * collateral information into it, then use the validation check contract
 * provided here ({@link #checkCompleteForVal(IValuationModel, List)}
 * @author Hii Hui Sieng
 * @author Cynthia Zhou
 * @author Chong Jun Yong
 * 
 */
public interface IValuator {
	/**
	 * To run validation check before actual valuation, whether for a
	 * collateral, it's information is complete.
	 * @param model valuation model, containing relevant collateral and
	 *        valuation information.
	 * @param errorDesc a storage to keep the error description for
	 *        incompleteness collateral information.
	 * @return whether collateral information is complete for valuation run.
	 */
	public boolean checkCompleteForVal(IValuationModel model, List errorDesc);

	/**
	 * Perform system valuation using the valuation model containing collateral
	 * and valuation information provided.
	 * @param model valuation model containing collateral and valuation
	 *        information.
	 * @throws ValuationException if there is any error when running valuation.
	 */
	public void performValuation(IValuationModel model) throws ValuationException;

	/**
	 * To save online valuation information for a collateral, only applied for
	 * those collateral which can be valuated when get updated online by user.
	 * @param collateral a collateral instance
	 * @param model valuation model containing collateral and valution
	 *        information
	 */
	public void saveOnlineValuationInfoInCollateral(ICollateral collateral, IValuationModel model);

	/**
	 * To create a empty valuation model instance, must be same type with
	 * instance that other contracts are using.
	 * @return a new independent valuation model instance.
	 */
	public IValuationModel createValuationModelInstance();

}
