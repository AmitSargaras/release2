package com.integrosys.cms.app.collateral.proxy.valuation;

import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.trx.valuation.IValuationTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

public interface ICollateralValuationProxy {
	/**
	 * Get the valuation transaction value given its valuation id.
	 * 
	 * @param ctx transaction context
	 * @param valuationID valuation id
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue getValuationTrxValue(ITrxContext ctx, long valuationID) throws CollateralException;

	/**
	 * Get valuation transaction value given its transaction reference id.
	 * 
	 * @param ctx transaction context
	 * @param parentTrxID transaction ref id of valuation, i.e collateral trx id
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue getValuationTrxValueByTrxRefID(ITrxContext ctx, String parentTrxID)
			throws CollateralException;

	/**
	 * Revaluate a collateral manually by using feeds for price, eg. stocks
	 * 
	 * @param ctx transaction context object
	 * @param trxVal of type IValuationTrxValue
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue manualFeedsCreateRevaluation(ITrxContext ctx, IValuationTrxValue trxVal)
			throws CollateralException;

	/**
	 * Revaluate a collateral manually by using feeds for price, eg. stocks
	 * 
	 * @param ctx transaction context object
	 * @param trxVal of type IValuationTrxValue
	 * @param val new valuation value
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue manualFeedsUpdateRevaluation(ITrxContext ctx, IValuationTrxValue trxVal, IValuation val)
			throws CollateralException;

	/**
	 * Revaluate a collateral manually. It requires a valuer to provide
	 * valuation, e.g. property.
	 * 
	 * @param ctx transaction context object
	 * @param trxVal of type IValuationTrxValue
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue manualCreateRevaluation(ITrxContext ctx, IValuationTrxValue trxVal)
			throws CollateralException;

	/**
	 * Revaluate a collateral manually. The update will create new valuation,
	 * the existing valuation values will become valuation history.
	 * 
	 * @param ctx transaction context object
	 * @param trxVal of type IValuationTrxValue
	 * @param val new valuation value
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue manualUpdateRevaluation(ITrxContext ctx, IValuationTrxValue trxVal, IValuation val)
			throws CollateralException;

	/**
	 * Resubmit revaluation rejected by a checker.
	 * 
	 * @param ctx transaction context object
	 * @param trxVal of type IValuationTrxValue
	 * @param val new valuation value
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue manualResubmitRevaluation(ITrxContext ctx, IValuationTrxValue trxVal, IValuation val)
			throws CollateralException;

	/**
	 * Cancel the revaluation rejected by a checker.
	 * 
	 * @param ctx transaction context object
	 * @param trxVal of type IValuationTrxValue
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue manualCancelRevaluation(ITrxContext ctx, IValuationTrxValue trxVal)
			throws CollateralException;

	/**
	 * Checker approve collateral revaluated by maker.
	 * 
	 * @param ctx transaction context object
	 * @param trxVal valuation transaction value
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue checkerApproveRevaluation(ITrxContext ctx, IValuationTrxValue trxVal)
			throws CollateralException;

	/**
	 * Checker rejects collateral revaluated by maker.
	 * 
	 * @param ctx transaction context object
	 * @param trxVal valuation transaction value
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue checkerRejectRevaluation(ITrxContext ctx, IValuationTrxValue trxVal)
			throws CollateralException;

	/**
	 * System Close Revaluation as the valuable entity has been removed.
	 * 
	 * @param ctx transaction context object
	 * @param trxVal valuation transaction value
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue systemCloseRevaluation(ITrxContext ctx, IValuationTrxValue trxVal)
			throws CollateralException;

	/**
	 * System creates revaluation, e.g. stocks
	 * 
	 * @param ctx transaction context
	 * @param trxVal valuation transaction value
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue systemCreateRevaluation(ITrxContext ctx, IValuationTrxValue trxVal)
			throws CollateralException;

	/**
	 * System updates revaluation e.g. stocks
	 * 
	 * @param ctx transaction context
	 * @param trxVal valuation transaction value
	 * @param val new valuation value
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue systemUpdateRevaluation(ITrxContext ctx, IValuationTrxValue trxVal, IValuation val)
			throws CollateralException;
}
