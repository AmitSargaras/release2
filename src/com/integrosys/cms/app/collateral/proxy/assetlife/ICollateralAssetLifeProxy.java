package com.integrosys.cms.app.collateral.proxy.assetlife;

import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralAssetLife;
import com.integrosys.cms.app.collateral.trx.assetlife.ICollateralAssetLifeTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Service bean to be used for asset life setup. Mainly go to workflow engine to
 * retrieve or update transaction value. And also asset life domain objects.
 * 
 * @author Chong Jun Yong
 * 
 */
public interface ICollateralAssetLifeProxy {
	/**
	 * Gets the collateral assetlife trx value.
	 * 
	 * @param ctx transaction context
	 * @return collateral assetlife transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralAssetLifeTrxValue getCollateralAssetLifeTrxValue(ITrxContext ctx) throws CollateralException;

	/**
	 * Gets the collateral assetlife trx value by transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return collateral assetlife transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralAssetLifeTrxValue getCollateralAssetLifeTrxValueByTrxID(ITrxContext ctx, String trxID)
			throws CollateralException;

	/**
	 * Maker updates a list of collateral assetlife.
	 * 
	 * @param ctx transaction context
	 * @param assetLifes a list of collateral assetlife objects to use for
	 *        updating.
	 * @return updated collateral assetlife transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralAssetLifeTrxValue makerUpdateCollateralAssetLife(ITrxContext ctx,
			ICollateralAssetLifeTrxValue trxVal, ICollateralAssetLife[] assetLifes) throws CollateralException;

	/**
	 * Maker saves a list of collateral assetlife.
	 * 
	 * @param ctx transaction context
	 * @param assetLifes a list of collateral assetlife objects to use for
	 *        updating.
	 * @return updated collateral assetlife transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralAssetLifeTrxValue makerSaveCollateralAssetLife(ITrxContext ctx,
			ICollateralAssetLifeTrxValue trxVal, ICollateralAssetLife[] assetLifes) throws CollateralException;

	/**
	 * Maker cancel security assetlife updated by him/her, or rejected by a
	 * checker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal security assetlife transaction value
	 * @return the updated security assetlife transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralAssetLifeTrxValue makerCancelUpdateCollateralAssetLife(ITrxContext ctx,
			ICollateralAssetLifeTrxValue trxVal) throws CollateralException;

	/**
	 * Checker approve security assetlife updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal security assetlife transaction value
	 * @return updated transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralAssetLifeTrxValue checkerApproveUpdateCollateralAssetLife(ITrxContext ctx,
			ICollateralAssetLifeTrxValue trxVal) throws CollateralException;

	/**
	 * Checker reject security assetlife updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal security assetlife transaction value
	 * @return updated security assetlife transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralAssetLifeTrxValue checkerRejectUpdateCollateralAssetLife(ITrxContext ctx,
			ICollateralAssetLifeTrxValue trxVal) throws CollateralException;
}
