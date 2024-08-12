package com.integrosys.cms.app.collateral.proxy.parameter;

import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.trx.parameter.ICollateralParameterTrxValue;
import com.integrosys.cms.app.collateral.trx.parameter.ICollateralSubTypeTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Provide service to interface with Collateral Recovery Parameter , eithere for
 * Country or Global
 * 
 * @author Chong Jun Yong
 * @since 11.09.2008
 */
public interface ICollateralParameterProxy {

	/**
	 * Gets the collateral parameter trx value by country code and security type
	 * code.
	 * 
	 * @param ctx transaction context
	 * @param countryCode country code
	 * @param typeCode security type code
	 * @return security parameter transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralParameterTrxValue getCollateralParameterTrxValue(ITrxContext ctx, String countryCode,
			String typeCode) throws CollateralException;

	/**
	 * Get the security parameter trx value by its transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return security parameter transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralParameterTrxValue getCollateralParameterTrxValue(ITrxContext ctx, String trxID)
			throws CollateralException;

	/**
	 * Maker updates a list of collateral parameters.
	 * 
	 * @param ctx transaction context
	 * @param trxValue security parameter transaction value
	 * @param colParams a list of security parameters
	 * @return the updated security parameter transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralParameterTrxValue makerUpdateCollateralParameter(ITrxContext ctx,
			ICollateralParameterTrxValue trxValue, ICollateralParameter[] colParams) throws CollateralException;

	/**
	 * Maker saves a list of collateral parameters.
	 * 
	 * @param ctx transaction context
	 * @param trxValue security parameter transaction value
	 * @param colParams a list of security parameters
	 * @return the updated security parameter transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralParameterTrxValue makerSaveCollateralParameter(ITrxContext ctx,
			ICollateralParameterTrxValue trxValue, ICollateralParameter[] colParams) throws CollateralException;

	/**
	 * Maker cancel security parameter updated by him/her, or rejected by a
	 * checker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal security parameter transaction value
	 * @return the updated security parameter transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralParameterTrxValue makerCancelUpdateCollateralParameter(ITrxContext ctx,
			ICollateralParameterTrxValue trxVal) throws CollateralException;

	/**
	 * Checker approve security parameter updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal security parameter transaction value
	 * @return updated transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralParameterTrxValue checkerApproveUpdateCollateralParameter(ITrxContext ctx,
			ICollateralParameterTrxValue trxVal) throws CollateralException;

	/**
	 * Checker reject security parameter updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal security parameter transaction value
	 * @return updated security parameter transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralParameterTrxValue checkerRejectUpdateCollateralParameter(ITrxContext ctx,
			ICollateralParameterTrxValue trxVal) throws CollateralException;

	/**
	 * Gets the collateral subtype trx value by collateral type code.
	 * 
	 * @param ctx transaction context
	 * @param colTypeCode collateral type code
	 * @return collateral subtype transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralSubTypeTrxValue getCollateralSubTypeTrxValue(ITrxContext ctx, String colTypeCode)
			throws CollateralException;

	/**
	 * Gets the collateral subtype trx value by transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return collateral subtype transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralSubTypeTrxValue getCollateralSubTypeTrxValueByTrxID(ITrxContext ctx, String trxID)
			throws CollateralException;

	/**
	 * Maker updates a list of collateral subtypes.
	 * 
	 * @param ctx transaction context
	 * @param subtypes a list of collateral subtype objects to use for updating.
	 * @return updated collateral subtype transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralSubTypeTrxValue makerUpdateCollateralSubType(ITrxContext ctx, ICollateralSubTypeTrxValue trxVal,
			ICollateralSubType[] subtypes) throws CollateralException;

	/**
	 * Maker saves a list of collateral subtypes.
	 * 
	 * @param ctx transaction context
	 * @param subtypes a list of collateral subtype objects to use for updating.
	 * @return updated collateral subtype transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralSubTypeTrxValue makerSaveCollateralSubType(ITrxContext ctx, ICollateralSubTypeTrxValue trxVal,
			ICollateralSubType[] subtypes) throws CollateralException;

	/**
	 * Maker cancel security subtypes updated by him/her, or rejected by a
	 * checker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal security subtype transaction value
	 * @return the updated security subtype transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralSubTypeTrxValue makerCancelUpdateCollateralSubType(ITrxContext ctx,
			ICollateralSubTypeTrxValue trxVal) throws CollateralException;

	/**
	 * Checker approve security subtypes updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal security subtype transaction value
	 * @return updated transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralSubTypeTrxValue checkerApproveUpdateCollateralSubType(ITrxContext ctx,
			ICollateralSubTypeTrxValue trxVal) throws CollateralException;

	/**
	 * Checker reject security subtypes updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxVal security subtype transaction value
	 * @return updated security subtype transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralSubTypeTrxValue checkerRejectUpdateCollateralSubTypes(ITrxContext ctx,
			ICollateralSubTypeTrxValue trxVal) throws CollateralException;
}
