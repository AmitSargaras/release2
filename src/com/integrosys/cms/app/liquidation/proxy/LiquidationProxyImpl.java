/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.proxy;

import java.rmi.RemoteException;
import java.util.Collection;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.liquidation.bus.ILiquidation;
import com.integrosys.cms.app.liquidation.bus.LiquidationException;
import com.integrosys.cms.app.liquidation.trx.ILiquidationTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * This class facades the ILiquidationProxy implementation by delegating the
 * handling of requests to an ejb session bean.
 * 
 * @author $Author: lini$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class LiquidationProxyImpl extends AbstractLiquidationProxy {

     private VelocityEngine velocityEngine;

	private SBLiquidationProxy liquidationProxy;
    


    public Collection getNPLInfo(long collateralID) throws LiquidationException {
		try {
//			SBLiquidationProxy proxy = getProxy();
			return liquidationProxy.getNPLInfo(collateralID);
		}
		catch (RemoteException e) {
			throw new LiquidationException(
					"failed to work on liqudaition remote interface for retrieving npl info, throwing root cause", e
							.getCause());
		}
	}

	public ILiquidationTrxValue getLiquidationTrxValue(ITrxContext ctx, long collateralID) throws LiquidationException {
		try {
//			SBLiquidationProxy proxy = getProxy();

			return liquidationProxy.getLiquidationTrxValue(ctx, collateralID);
		}
		catch (RemoteException e) {
			throw new LiquidationException(
					"failed to work on liqudaition remote interface for getting trx value, throwing root cause", e
							.getCause());
		}
	}

	public ILiquidationTrxValue getLiquidationTrxValueByTrxID(ITrxContext ctx, String trxID)
			throws LiquidationException {
		try {
//			SBLiquidationProxy proxy = getProxy();
			return liquidationProxy.getLiquidationTrxValueByTrxID(ctx, trxID);
		}
		catch (RemoteException e) {
			throw new LiquidationException(
					"failed to work on liqudaition remote interface for getting trx value, throwing root cause", e
							.getCause());
		}
	}

	public ILiquidationTrxValue getLiquidationTrxValueByTrxRefID(ITrxContext ctx, String trxRefID)
			throws LiquidationException {
		try {
//			SBLiquidationProxy proxy = getProxy();
			return liquidationProxy.getLiquidationTrxValueByTrxRefID(ctx, trxRefID);
		}
		catch (RemoteException e) {
			throw new LiquidationException(
					"failed to work on liqudaition remote interface for getting trx value, throwing root cause", e
							.getCause());
		}
	}

	public ILiquidationTrxValue makerUpdateLiquidation(ITrxContext ctx, ILiquidationTrxValue trxVal,
			ILiquidation assetLifes) throws LiquidationException {
		try {
//			SBLiquidationProxy proxy = getProxy();
			return liquidationProxy.makerUpdateLiquidation(ctx, trxVal, assetLifes);
		}
		catch (Exception e) {
			throw new LiquidationException(
					"failed to work on liqudaition remote interface for maker update, throwing root cause", e
							.getCause());
		}
	}

	public ILiquidationTrxValue makerSaveLiquidation(ITrxContext ctx, ILiquidationTrxValue trxVal,
			ILiquidation assetLifes) throws LiquidationException {
		try {
//			SBLiquidationProxy proxy = getProxy();
			return liquidationProxy.makerSaveLiquidation(ctx, trxVal, assetLifes);
		}
		catch (RemoteException e) {
			throw new LiquidationException(
					"failed to work on liqudaition remote interface for maker save, throwing root cause", e.getCause());
		}
	}

	public ILiquidationTrxValue makerCancelUpdateLiquidation(ITrxContext ctx, ILiquidationTrxValue trxVal)
			throws LiquidationException {
		try {
//			SBLiquidationProxy proxy = getProxy();
			return liquidationProxy.makerCancelUpdateLiquidation(ctx, trxVal);
		}
		catch (RemoteException e) {
			throw new LiquidationException(
					"failed to work on liqudaition remote interface for maker cancel, throwing root cause", e
							.getCause());
		}
	}

	public ILiquidationTrxValue checkerApproveUpdateLiquidation(ITrxContext ctx, ILiquidationTrxValue trxVal)
			throws LiquidationException {
		try {
//			SBLiquidationProxy proxy = getProxy();
			return liquidationProxy.checkerApproveUpdateLiquidation(ctx, trxVal);
		}
		catch (RemoteException e) {
			throw new LiquidationException(
					"failed to work on liqudaition remote interface for checker approve, throwing root cause", e
							.getCause());
		}
	}

	public ILiquidationTrxValue checkerRejectUpdateLiquidation(ITrxContext ctx, ILiquidationTrxValue trxVal)
			throws LiquidationException {
		try {
//			SBLiquidationProxy proxy = getProxy();
			return liquidationProxy.checkerRejectUpdateLiquidation(ctx, trxVal);
		}
		catch (Exception e) {
			throw new LiquidationException(
					"failed to work on liqudaition remote interface for checker reject update, throwing root cause", e
							.getCause());
		}
	}

	/**
	 * Method to rollback a transaction. Not implemented at online proxy level.
	 * 
	 * @throws LiquidationException for any errors encountered
	 */
	protected void rollback() throws LiquidationException {
	}


    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }


    public SBLiquidationProxy getLiquidationProxy() {
        return liquidationProxy;
    }

    public void setLiquidationProxy(SBLiquidationProxy liquidationProxy) {
        this.liquidationProxy = liquidationProxy;
    }

    /**
	 * Helper method to get ejb object of liquidation proxy session bean.
	 * 
	 * @return liquidation proxy ejb object
	 */
	private SBLiquidationProxy getProxy() throws LiquidationException {
		SBLiquidationProxy proxy = (SBLiquidationProxy) BeanController.getEJB(
				ICMSJNDIConstant.SB_LIQUIDATION_PROXY_JNDI, SBLiquidationProxyHome.class.getName());

		if (proxy == null) {
			throw new LiquidationException("SBLiquidationProxy is null!");
		}
		return proxy;
	}
}