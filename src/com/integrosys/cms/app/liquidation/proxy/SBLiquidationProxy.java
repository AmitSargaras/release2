/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.proxy;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.EJBObject;

import com.integrosys.cms.app.liquidation.bus.ILiquidation;
import com.integrosys.cms.app.liquidation.bus.LiquidationException;
import com.integrosys.cms.app.liquidation.trx.ILiquidationTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This is the remote interface to the SBLiquidationProxy session bean.
 * 
 * @author $Author: lini$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface SBLiquidationProxy extends EJBObject {

	public Collection getNPLInfo(long collateralID) throws LiquidationException, RemoteException;

	/**
	 * Gets the liquidation trx value by liquidation type and month.
	 * 
	 * @param ctx transaction context
	 * @param collateralID the type of liquidation
	 * @return liquidation transaction value for the type and month
	 * @throws LiquidationException on errors encountered
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public ILiquidationTrxValue getLiquidationTrxValue(ITrxContext ctx, long collateralID) throws LiquidationException,
			RemoteException;

	public ILiquidationTrxValue getLiquidationTrxValueByTrxRefID(ITrxContext ctx, String trxRefID)
			throws LiquidationException, RemoteException;

	/**
	 * Gets the liquidation trx value by transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return liquidation transaction value
	 * @throws LiquidationException on errors encountered
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public ILiquidationTrxValue getLiquidationTrxValueByTrxID(ITrxContext ctx, String trxID)
			throws LiquidationException, RemoteException;

	/**
	 * Maker updates a list of liquidation.
	 * 
	 * @param ctx transaction context
	 * @param trxVal liquidation transaction value
	 * @param liqs a list of liquidation objects to use for updating.
	 * @return updated liquidation transaction value
	 * @throws LiquidationException on errors encountered
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public ILiquidationTrxValue makerUpdateLiquidation(ITrxContext ctx, ILiquidationTrxValue trxVal, ILiquidation liqs)
			throws LiquidationException, RemoteException;

	/**
	 * Maker saves a list of liquidation.
	 * 
	 * @param ctx transaction context
	 * @param trxVal liquidation transaction value
	 * @param liqs a list of liquidation objects to use for updating.
	 * @return updated liquidation transaction value
	 * @throws LiquidationException on errors encountered
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public ILiquidationTrxValue makerSaveLiquidation(ITrxContext ctx, ILiquidationTrxValue trxVal, ILiquidation liqs)
			throws LiquidationException, RemoteException;

	/**
	 * Maker cancel liquidation updated by him/her, or rejected by a checker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal liquidation transaction value
	 * @return the updated liquidation transaction value
	 * @throws LiquidationException on errors encountered
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public ILiquidationTrxValue makerCancelUpdateLiquidation(ITrxContext ctx, ILiquidationTrxValue trxVal)
			throws LiquidationException, RemoteException;

	/**
	 * Checker approve liquidation updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal liquidation transaction value
	 * @return updated transaction value
	 * @throws LiquidationException on errors encountered
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public ILiquidationTrxValue checkerApproveUpdateLiquidation(ITrxContext ctx, ILiquidationTrxValue trxVal)
			throws LiquidationException, RemoteException;

	/**
	 * Checker reject liquidation updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal liquidation transaction value
	 * @return updated liquidation transaction value
	 * @throws LiquidationException on errors encountered
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public ILiquidationTrxValue checkerRejectUpdateLiquidation(ITrxContext ctx, ILiquidationTrxValue trxVal)
			throws LiquidationException, RemoteException;

}