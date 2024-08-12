/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import java.io.Serializable;
import java.util.Collection;

/**
 * This interface defines the services of a Liquidation business manager.
 * 
 * @author $Author: lini$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface ILiquidationBusManager extends Serializable {

	public Collection getNPLInfo(long collateralID) throws LiquidationException;

	public ILiquidation getLiquidation(long liquidationID) throws LiquidationException;

	public ILiquidation createLiquidation(ILiquidation liq) throws LiquidationException;

	public ILiquidation updateLiquidation(ILiquidation liq) throws LiquidationException;

}
