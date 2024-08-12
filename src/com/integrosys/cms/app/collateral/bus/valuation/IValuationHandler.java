package com.integrosys.cms.app.collateral.bus.valuation;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.ICollateral;

/**
 * A handler to perform online or system valuation for a collateral.
 * 
 * @author Chong Jun Yong
 * 
 */
public interface IValuationHandler {
	public IValuationModel performOnlineValuation(ICollateral collateral) throws ValuationException;

	public IValuationModel performSystemValuation(IValuationModel valuationModel) throws ValuationException;

	public IValuationModel calculateSecCmvFsv(ICollateral col, Amount omv, Amount fsv, String valuationType,
			Date valuationDate, String valuer) throws ValuationException;

	public IValuationModel calculateSecCmvFsv(IValuationModel currentValuation) throws ValuationException;
}
