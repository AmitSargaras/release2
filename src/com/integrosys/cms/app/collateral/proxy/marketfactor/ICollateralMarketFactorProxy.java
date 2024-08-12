package com.integrosys.cms.app.collateral.proxy.marketfactor;

import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist;
import com.integrosys.cms.app.collateral.trx.marketfactor.IMFChecklistTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Provide service to Marketability Factory workflow as well as the domain
 * object.
 * 
 * @author Chong Jun Yong
 * 
 */
public interface ICollateralMarketFactorProxy {
	/**
	 * Gets the MF Checklist details by MF Checklist ID.
	 * 
	 * @param ctx transaction context
	 * @param mFChecklistID MF Checklist ID
	 * @return IMFChecklist
	 * @throws CollateralException on errors encountered
	 */
	public IMFChecklist getMFChecklist(long mFChecklistID) throws CollateralException;

	/**
	 * Gets the MF Checklist trx value by collateral ID.
	 * 
	 * @param ctx transaction context
	 * @param collateralID collateral ID
	 * @return MF Checklist transaction value for the collateral ID
	 * @throws CollateralException on errors encountered
	 */
	public IMFChecklistTrxValue getMFChecklistTrxValue(ITrxContext ctx, long collateralID) throws CollateralException;

	/**
	 * Gets the MF Checklist trx value by transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return MF Checklist transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IMFChecklistTrxValue getMFChecklistTrxValueByTrxID(ITrxContext ctx, String trxID) throws CollateralException;

	/**
	 * Maker creates a MF Checklist.
	 * 
	 * @param ctx transaction context
	 * @param trxVal MF Checklist transaction value
	 * @param value a MF Checklist object to use for updating.
	 * @return updated MF Checklist transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IMFChecklistTrxValue makerCreateMFChecklist(ITrxContext ctx, IMFChecklistTrxValue trxVal, IMFChecklist value)
			throws CollateralException;

	/**
	 * Maker updates a MF Checklist.
	 * 
	 * @param ctx transaction context
	 * @param trxVal MF Checklist transaction value
	 * @param value a MF Checklist object to use for updating.
	 * @return updated MF Checklist transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IMFChecklistTrxValue makerUpdateMFChecklist(ITrxContext ctx, IMFChecklistTrxValue trxVal, IMFChecklist value)
			throws CollateralException;

	/**
	 * Maker close MF Checklist created by him/her, and rejected by a checker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal MF Checklist transaction value
	 * @return the updated MF Checklist transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IMFChecklistTrxValue makerCloseCreateMFChecklist(ITrxContext ctx, IMFChecklistTrxValue trxVal)
			throws CollateralException;

	/**
	 * Maker close MF Checklist updated by him/her, and rejected by a checker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal MF Checklist transaction value
	 * @return the updated MF Checklist transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IMFChecklistTrxValue makerCloseUpdateMFChecklist(ITrxContext ctx, IMFChecklistTrxValue trxVal)
			throws CollateralException;

	/**
	 * Checker approve MF Checklist updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal MF Checklist transaction value
	 * @return the updated MF Checklist transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IMFChecklistTrxValue checkerApproveUpdateMFChecklist(ITrxContext ctx, IMFChecklistTrxValue trxVal)
			throws CollateralException;

	/**
	 * Checker reject MF Checklist updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal MF Checklist transaction value
	 * @return updated MF Checklist transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IMFChecklistTrxValue checkerRejectUpdateMFChecklist(ITrxContext ctx, IMFChecklistTrxValue trxVal)
			throws CollateralException;
}
