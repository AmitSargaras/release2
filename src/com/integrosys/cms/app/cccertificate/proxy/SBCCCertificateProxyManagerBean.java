/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/proxy/SBCCCertificateProxyManagerBean.java,v 1.9 2005/05/12 03:58:08 lyng Exp $
 */
package com.integrosys.cms.app.cccertificate.proxy;

//java
import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.forex.SBForexManager;
import com.integrosys.base.businfra.forex.SBForexManagerHome;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateDAO;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateException;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateSearchCriteria;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateSearchResult;
import com.integrosys.cms.app.cccertificate.bus.ICCCertificate;
import com.integrosys.cms.app.cccertificate.bus.SBCCCertificateBusManager;
import com.integrosys.cms.app.cccertificate.bus.SBCCCertificateBusManagerHome;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.EBCollateralPledgorLocalHome;
import com.integrosys.cms.app.collateral.bus.IPledgor;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Session bean implementation of the services provided by the certificate proxy
 * manager. This will only contains the persistance logic.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2005/05/12 03:58:08 $ Tag: $Name: $
 */
public class SBCCCertificateProxyManagerBean extends AbstractCCCertificateProxyManager implements SessionBean {
	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBCCCertificateProxyManagerBean() {
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
	 * Search CCCertificate based on the criteria given.
	 * 
	 * @param criteria cccertificate criteria
	 * @return SearchResult containing a list of CCCertificateSearchResult
	 * @throws CCCertificateException on any errors encountered
	 */
	public SearchResult searchCCCertificate(CCCertificateSearchCriteria criteria) throws CCCertificateException {
		super.searchCCCertificate(criteria);
		try {
			return new CCCertificateDAO().getCCCbyLimitProfile(criteria);
		}
		catch (Exception e) {
			throw new CCCertificateException("Exception in searchCCCertificate limit profile: "
					+ criteria.getLimitProfileID(), e);
		}
	}

	protected int getNoOfCCCertificate(CCCertificateSearchCriteria aCriteria) throws CCCertificateException {
		try {
			return getSBCCCertificateBusManager().getNoOfCCCertificate(aCriteria);
		}
		catch (SearchDAOException ex) {
			throw new CCCertificateException("Exception in getNoOfCCCertificate", ex);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getNoOfCCCertificate( for search key: "
					+ aCriteria.toString());
		}
	}

	protected ICCCertificate getMainBorrowerCCC(long aLimitProfileID, long aSubProfileID) throws CCCertificateException {
		try {
			return getSBCCCertificateBusManager().getMainBorrowerCCC(aLimitProfileID, aSubProfileID);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getMainBorrowerCCC for search key: " + aLimitProfileID
					+ ", " + aSubProfileID + ": " + ex.toString());
		}
	}

	protected ICCCertificate getCoBorrowerCCC(long aLimitProfileID, long aSubProfileID) throws CCCertificateException {
		try {
			return getSBCCCertificateBusManager().getCoBorrowerCCC(aLimitProfileID, aSubProfileID);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getCoBorrowerCCC for search key: " + aLimitProfileID + ", "
					+ aSubProfileID + ": " + ex.toString());
		}
	}

	protected ICCCertificate getPledgorCCC(long aLimitProfileID, long aPledgorID) throws CCCertificateException {
		try {
			return getSBCCCertificateBusManager().getPledgorCCC(aLimitProfileID, aPledgorID);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getPledgorCCC for search key: " + aLimitProfileID + ", "
					+ aPledgorID + ": " + ex.toString());
		}
	}

	protected ICCCertificate getNonBorrowerCCC(long aSubProfileID) throws CCCertificateException {
		try {
			return getSBCCCertificateBusManager().getNonBorrowerCCC(aSubProfileID);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getNonBorrowerCCC for search key: " + aSubProfileID + ": "
					+ ex.toString());
		}
	}

	protected long convertAmount(Amount anAmount, CurrencyCode aCurrencyCode) throws CCCertificateException {
		if (anAmount == null) {
			return 0;
		}
		if (anAmount.getCurrencyCode() == null) {
			throw new CCCertificateException("Currency Code is null !!");
		}

		if (anAmount.getCurrencyCode().equals(aCurrencyCode.getCode())) {
			return Math.round(anAmount.getAmount());
		}
		try {
			SBForexManager mgr = getSBForexManager();
			Amount amount = mgr.convert(anAmount, aCurrencyCode);
			return Math.round(amount.getAmount());
		}
		catch (Exception ex) {
			throw new CCCertificateException("Exception in convertAmount: " + ex.toString());
		}
	}

	protected IPledgor getPledgor(long aPledgorID) throws CCCertificateException {
		try {
			ICollateralProxy proxy = CollateralProxyFactory.getProxy();
			return proxy.getPledgor(aPledgorID);
		}
		catch (CollateralException ex) {
			throw new CCCertificateException("Exception in getCollateralPledgor: " + ex.toString());
		}
	}

	protected CCCertificateSearchResult[] getCCCertificateGenerated(long aLimitProfileID) throws SearchDAOException,
			CCCertificateException {
		try {
			return getSBCCCertificateBusManager().getCCCertificateGenerated(aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getCCCertificateGenerated: " + ex.toString());
		}
	}

	/**
	 * Get the cc certificate trx ID
	 * @param aCriteria of CCCertificateSearchCriteria type
	 * @return String - the CCC TRX ID
	 * @throws SearchDAOException on errors
	 * @throws CCCertificateException on errors
	 */
	protected String getCCCTrxID(CCCertificateSearchCriteria aCriteria) throws SearchDAOException,
			CCCertificateException {
		try {
			return getSBCCCertificateBusManager().getCCCTrxID(aCriteria);
		}
		catch (RemoteException ex) {
			throw new CCCertificateException("Exception in getCCCTrxID: " + ex.toString());
		}
	}

	/**
	 * Helper method to return the certificate bus session bean
	 * 
	 * @return SBCCCertificateBusManager - the remote handler for the
	 *         certificate bus manager session bean
	 * @throws CCCertificateException for any errors encountered
	 */
	private SBCCCertificateBusManager getSBCCCertificateBusManager() throws CCCertificateException {
		SBCCCertificateBusManager busmgr = (SBCCCertificateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CCCERTIFICATE_BUS_JNDI, SBCCCertificateBusManagerHome.class.getName());
		if (busmgr == null) {
			throw new CCCertificateException("SBCCCertificateBusManager is null!");
		}
		return busmgr;
	}

	/**
	 * Helper method to return the staging certificate bus session bean
	 * 
	 * @return SBCCCertificateBusManager - the remote handler for the
	 *         certificate bus manager session bean
	 * @throws CCCertificateException for any errors encountered
	 */
	private SBCCCertificateBusManager getSBStagingCertificateBusManager() throws CCCertificateException {
		SBCCCertificateBusManager busmgr = (SBCCCertificateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_CCCERTIFICATE_BUS_JNDI, SBCCCertificateBusManagerHome.class.getName());
		if (busmgr == null) {
			throw new CCCertificateException("SBCCCertificateBusManager is null!");
		}
		return busmgr;
	}

	/**
	 * Helper method to return the forex session bean
	 * 
	 * @return SBForexManager - the remote handler for the forex manager session
	 *         bean
	 * @throws CCCertificateException for any errors encountered
	 */
	private SBForexManager getSBForexManager() throws CCCertificateException {
		SBForexManager mgr = (SBForexManager) BeanController.getEJB(ICMSJNDIConstant.SB_FOREX_MANAGER_JNDI,
				SBForexManagerHome.class.getName());
		if (mgr == null) {
			throw new CCCertificateException("SBForexManager is null!");
		}
		return mgr;
	}

	/**
	 * Get collateral pledgor local home.
	 * 
	 * @return EBCollateralPledgorLocalHome
	 */
	protected EBCollateralPledgorLocalHome getEBCollateralPledgorLocalHome() {
		EBCollateralPledgorLocalHome ejbHome = (EBCollateralPledgorLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COL_PLEDGOR_LOCAL_JNDI, EBCollateralPledgorLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBCollateralPledgorLocalHome is Null!");
		}

		return ejbHome;
	}
}
