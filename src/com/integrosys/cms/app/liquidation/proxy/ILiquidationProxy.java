/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.proxy;

import java.util.Collection;

import com.integrosys.cms.app.liquidation.bus.ILiquidation;
import com.integrosys.cms.app.liquidation.bus.LiquidationException;
import com.integrosys.cms.app.liquidation.trx.ILiquidationTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This interface defines the services that are available in CMS with respect to
 * the liquidation life cycle.
 * 
 * @author $Author: lini$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface ILiquidationProxy {

	public Collection getNPLInfo(long collateralID) throws LiquidationException;

	/**
	 * Gets the liquidation trx value by liquidation type and month.
	 * 
	 * @param ctx transaction context
	 * @param collateralID
	 * @return liquidation transaction value for the type and month
	 * @throws com.integrosys.cms.app.liquidation.bus.LiquidationException on
	 *         errors encountered
	 */
	public ILiquidationTrxValue getLiquidationTrxValue(ITrxContext ctx, long collateralID) throws LiquidationException;

	public ILiquidationTrxValue getLiquidationTrxValueByTrxRefID(ITrxContext ctx, String trxRefID)
			throws LiquidationException;

	/**
	 * Gets the liquidation trx value by transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return liquidation transaction value
	 * @throws LiquidationException on errors encountered
	 */
	public ILiquidationTrxValue getLiquidationTrxValueByTrxID(ITrxContext ctx, String trxID)
			throws LiquidationException;

	/**
	 * Maker updates a list of liquidation.
	 * 
	 * @param ctx transaction context
	 * @param trxVal liquidation transaction value
	 * @param liqs a list of liquidation objects to use for updating.
	 * @return updated liquidation transaction value
	 * @throws LiquidationException on errors encountered
	 */
	public ILiquidationTrxValue makerUpdateLiquidation(ITrxContext ctx, ILiquidationTrxValue trxVal, ILiquidation liqs)
			throws LiquidationException;

	/**
	 * Maker saves a list of liquidation.
	 * 
	 * @param ctx transaction context
	 * @param trxVal liquidation transaction value
	 * @param liqs a list of liquidation objects to use for updating.
	 * @return updated liquidation transaction value
	 * @throws LiquidationException on errors encountered
	 */
	public ILiquidationTrxValue makerSaveLiquidation(ITrxContext ctx, ILiquidationTrxValue trxVal, ILiquidation liqs)
			throws LiquidationException;

	/**
	 * Maker cancel liquidation updated by him/her, or rejected by a checker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal liquidation transaction value
	 * @return the updated liquidation transaction value
	 * @throws LiquidationException on errors encountered
	 */
	public ILiquidationTrxValue makerCancelUpdateLiquidation(ITrxContext ctx, ILiquidationTrxValue trxVal)
			throws LiquidationException;

	/**
	 * Maker close liquidation updated by him/her, or rejected by a checker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal liquidation transaction value
	 * @return the updated liquidation transaction value
	 * @throws LiquidationException on errors encountered
	 */
	public ILiquidationTrxValue makerCloseUpdateLiquidation(ITrxContext ctx, ILiquidationTrxValue trxVal)
			throws LiquidationException;

	/**
	 * Checker approve liquidation updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal liquidation transaction value
	 * @return updated transaction value
	 * @throws LiquidationException on errors encountered
	 */
	public ILiquidationTrxValue checkerApproveUpdateLiquidation(ITrxContext ctx, ILiquidationTrxValue trxVal)
			throws LiquidationException;

	/**
	 * Checker reject liquidation updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal liquidation transaction value
	 * @return updated liquidation transaction value
	 * @throws LiquidationException on errors encountered
	 */
	public ILiquidationTrxValue checkerRejectUpdateLiquidation(ITrxContext ctx, ILiquidationTrxValue trxVal)
			throws LiquidationException;

}
