package com.integrosys.cms.app.collateral.proxy;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.collateral.bus.ICollateralBusManager;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationHandler;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxDAO;
import com.integrosys.cms.app.custodian.proxy.ICustodianProxyManager;

/**
 * DAO, Proxy or Bus Manager to be accessed by Collateral Proxy to be injected
 * here directly.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class CollateralProxyAccessor {
	private ICollateralBusManager actualCollateralBusManager;

	private ICheckListProxyManager checkListProxyManager;

	private ICollateralDAO collateralDao;

	private ITrxController collateralReadController;

	private ITrxController collateralTrxController;

	private ICollateralTrxDAO collateralTrxDao;

	private ICustodianProxyManager custodianProxyManager;

	private IValuationHandler valuationHandler;

	public ICollateralBusManager getActualCollateralBusManager() {
		return actualCollateralBusManager;
	}

	public ICheckListProxyManager getCheckListProxyManager() {
		return checkListProxyManager;
	}

	public ICollateralDAO getCollateralDao() {
		return collateralDao;
	}

	/**
	 * @return the collateralReadController
	 */
	public ITrxController getCollateralReadController() {
		return collateralReadController;
	}

	/**
	 * @return the collateralTrxController
	 */
	public ITrxController getCollateralTrxController() {
		return collateralTrxController;
	}

	public ICollateralTrxDAO getCollateralTrxDao() {
		return collateralTrxDao;
	}

	public ICustodianProxyManager getCustodianProxyManager() {
		return custodianProxyManager;
	}

	public IValuationHandler getValuationHandler() {
		return valuationHandler;
	}

	public void setActualCollateralBusManager(ICollateralBusManager actualCollateralBusManager) {
		this.actualCollateralBusManager = actualCollateralBusManager;
	}

	public void setCheckListProxyManager(ICheckListProxyManager checkListProxyManager) {
		this.checkListProxyManager = checkListProxyManager;
	}

	public void setCollateralDao(ICollateralDAO collateralDao) {
		this.collateralDao = collateralDao;
	}

	/**
	 * @param collateralReadController the collateralReadController to set
	 */
	public void setCollateralReadController(ITrxController collateralReadController) {
		this.collateralReadController = collateralReadController;
	}

	/**
	 * @param collateralTrxController the collateralTrxController to set
	 */
	public void setCollateralTrxController(ITrxController collateralTrxController) {
		this.collateralTrxController = collateralTrxController;
	}

	public void setCollateralTrxDao(ICollateralTrxDAO collateralTrxDao) {
		this.collateralTrxDao = collateralTrxDao;
	}

	public void setCustodianProxyManager(ICustodianProxyManager custodianProxyManager) {
		this.custodianProxyManager = custodianProxyManager;
	}

	public void setValuationHandler(IValuationHandler valuationHandler) {
		this.valuationHandler = valuationHandler;
	}
}
