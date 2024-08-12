/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/proxy/SBDDNProxyManagerBean.java,v 1.4 2005/06/08 06:35:38 htli Exp $
 */
package com.integrosys.cms.app.ddn.proxy;

//java
import java.rmi.RemoteException;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.forex.SBForexManager;
import com.integrosys.base.businfra.forex.SBForexManagerHome;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.ddn.bus.DDNException;
import com.integrosys.cms.app.ddn.bus.DDNSearchCriteria;
import com.integrosys.cms.app.ddn.bus.IDDN;
import com.integrosys.cms.app.ddn.bus.SBDDNBusManager;
import com.integrosys.cms.app.ddn.bus.SBDDNBusManagerHome;

/**
 * Session bean implementation of the services provided by the ddn proxy
 * manager. This will only contains the persistance logic.
 * 
 * @author $Author: htli $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/06/08 06:35:38 $ Tag: $Name: $
 */
public class SBDDNProxyManagerBean extends AbstractDDNProxyManager implements SessionBean {
	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBDDNProxyManagerBean() {
	}

	public void ejbCreate() {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void setSessionContext(javax.ejb.SessionContext sc) {
		_context = sc;
	}

	/**
	 * To rollback a transaction
	 */
	protected void rollback() {
		_context.setRollbackOnly();
	}

	/**
	 * To mark the DDN as invalid as SCC has been issued
	 * @param aLimitProfileID of long type
	 * @throws ConcurrentUpdateException , DDNException
	 */
	public void sccIssued(long aLimitProfileID) throws ConcurrentUpdateException, DDNException {
		try {
			getSBDDNBusManager().setSCCIssuedIndicator(aLimitProfileID, true);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in sccIssued for limit profile: " + aLimitProfileID);
		}
	}

	protected IDDN getDDNWithoutLimitInfo(long aLimitProfileID, String type) throws DDNException {
		try {
			return getSBDDNBusManager().getDDNByLimitProfileID(aLimitProfileID, type);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in getDDNWithoutLimitInfo limit profile : " + aLimitProfileID);
		}
	}

	protected long convertAmount(Amount anAmount, CurrencyCode aCurrencyCode) throws DDNException {
		if (anAmount == null) {
			return 0;
		}
		if (anAmount.getCurrencyCode() == null) {
			throw new DDNException("Currency Code is null !!");
		}

		if (anAmount.getCurrencyCode().equals(aCurrencyCode.getCode())) {
			return Math.round(anAmount.getAmount());
		}
		try {
			SBForexManager mgr = getSBForexManager();
			DefaultLogger.debug(this, "Amount: " + anAmount);
			DefaultLogger.debug(this, "Currency: " + aCurrencyCode);
			Amount amount = mgr.convert(anAmount, aCurrencyCode);
			return Math.round(amount.getAmount());
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in convertAmount: " + ex.toString());
		}
	}

	protected int getNoOfDDN(DDNSearchCriteria aCriteria) throws DDNException {
		try {
			return getSBDDNBusManager().getNoOfDDN(aCriteria);
		}
		catch (SearchDAOException ex) {
			throw new DDNException("Exception in getNoOfDDN", ex);
		}
		catch (RemoteException ex) {
			throw new DDNException("Exception in getNoOfDDN( for search key: " + aCriteria.toString());
		}
	}

	/**
	 * Helper method to return the ddn bus session bean
	 * 
	 * @return SBDDNBusManager - the remote handler for the ddn bus manager
	 *         session bean
	 * @throws DDNException for any errors encountered
	 */
	private SBDDNBusManager getSBDDNBusManager() throws DDNException {
		SBDDNBusManager busmgr = (SBDDNBusManager) BeanController.getEJB(ICMSJNDIConstant.SB_DDN_BUS_JNDI,
				SBDDNBusManagerHome.class.getName());
		if (busmgr == null) {
			throw new DDNException("SBDDNBusManager is null!");
		}
		return busmgr;
	}

	/**
	 * Helper method to return the staging ddn bus session bean
	 * 
	 * @return SBDDNBusManager - the remote handler for the ddn bus manager
	 *         session bean
	 * @throws DDNException for any errors encountered
	 */
	private SBDDNBusManager getSBStagingDDNBusManager() throws DDNException {
		SBDDNBusManager busmgr = (SBDDNBusManager) BeanController.getEJB(ICMSJNDIConstant.SB_STAGING_DDN_BUS_JNDI,
				SBDDNBusManagerHome.class.getName());
		if (busmgr == null) {
			throw new DDNException("SBDDNBusManager is null!");
		}
		return busmgr;
	}

	/**
	 * Helper method to return the forex session bean
	 * 
	 * @return SBForexManager - the remote handler for the forex manager session
	 *         bean
	 * @throws DDNException for any errors encountered
	 */
	private SBForexManager getSBForexManager() throws DDNException {
		SBForexManager mgr = (SBForexManager) BeanController.getEJB(ICMSJNDIConstant.SB_FOREX_MANAGER_JNDI,
				SBForexManagerHome.class.getName());
		if (mgr == null) {
			throw new DDNException("SBForexManager is null!");
		}
		return mgr;
	}

}
