/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/proxy/SBSCCertificateProxyManagerBean.java,v 1.8 2005/05/12 04:03:38 lyng Exp $
 */
package com.integrosys.cms.app.sccertificate.proxy;

//java
import java.rmi.RemoteException;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.forex.SBForexManager;
import com.integrosys.base.businfra.forex.SBForexManagerHome;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate;
import com.integrosys.cms.app.sccertificate.bus.ISCCertificate;
import com.integrosys.cms.app.sccertificate.bus.PartialSCCertificateDAO;
import com.integrosys.cms.app.sccertificate.bus.PartialSCCertificateSearchCriteria;
import com.integrosys.cms.app.sccertificate.bus.SBSCCertificateBusManager;
import com.integrosys.cms.app.sccertificate.bus.SBSCCertificateBusManagerHome;
import com.integrosys.cms.app.sccertificate.bus.SCCertificateDAO;
import com.integrosys.cms.app.sccertificate.bus.SCCertificateException;
import com.integrosys.cms.app.sccertificate.bus.SCCertificateSearchCriteria;

/**
 * Session bean implementation of the services provided by the certificate proxy
 * manager. This will only contains the persistance logic.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2005/05/12 04:03:38 $ Tag: $Name: $
 */
public class SBSCCertificateProxyManagerBean extends AbstractSCCertificateProxyManager implements SessionBean {
	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBSCCertificateProxyManagerBean() {
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
	 * Search SCCertificate based on the criteria given.
	 * 
	 * @param criteria sccertificate criteria
	 * @return SearchResult containing a list of SCCertificateSearchResult
	 * @throws SCCertificateException on any errors encountered
	 */
	public SearchResult searchSCCertificate(SCCertificateSearchCriteria criteria) throws SCCertificateException {
		super.searchSCCertificate(criteria);
		try {
			return new SCCertificateDAO().getSCCbyLimitProfile(criteria);
		}
		catch (Exception e) {
			throw new SCCertificateException("Exception in searchSCCertificate limit profile: "
					+ criteria.getLimitProfileID(), e);
		}
	}

	/**
	 * Search Partial SCCertificate based on the criteria given.
	 * 
	 * @param criteria partial sccertificate criteria
	 * @return SearchResult containing a list of
	 *         PartialSCCertificateSearchResult
	 * @throws SCCertificateException on any errors encountered
	 */
	public SearchResult searchPSCCertificate(PartialSCCertificateSearchCriteria criteria) throws SCCertificateException {
		super.searchPSCCertificate(criteria);
		try {
			return new PartialSCCertificateDAO().getPSCCbyLimitProfile(criteria);
		}
		catch (Exception e) {
			throw new SCCertificateException("Exception in searchPSCCertificate limit profile: "
					+ criteria.getLimitProfileID(), e);
		}
	}

	protected ISCCertificate getSCCertificateWithoutLimitInfo(long aLimitProfileID) throws SCCertificateException {
		try {
			return getSBSCCertificateBusManager().getSCCertificateByLimitProfileID(aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getSCCertificateWithoutLimitInfo limit profile : "
					+ aLimitProfileID, ex);
		}
	}

	protected IPartialSCCertificate getPartialSCCertificateWithoutLimitInfo(long aLimitProfileID)
			throws SCCertificateException {
		try {
			return getSBSCCertificateBusManager().getPartialSCCertificateByLimitProfileID(aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getPartialSCCertificateWithoutLimitInfo limit profile : "
					+ aLimitProfileID);
		}

	}

	protected long convertAmount(Amount anAmount, CurrencyCode aCurrencyCode) throws SCCertificateException {
		if (anAmount == null) {
			return 0;
		}
		if (anAmount.getCurrencyCode() == null) {
			throw new SCCertificateException("Currency Code is null !!");
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
		catch (Exception ex) {
			throw new SCCertificateException("Exception in convertAmount: " + ex.toString());
		}
	}

	protected int getNoOfSCCertificate(SCCertificateSearchCriteria aCriteria) throws SCCertificateException {
		try {
			return getSBSCCertificateBusManager().getNoOfSCCertificate(aCriteria);
		}
		catch (SearchDAOException ex) {
			throw new SCCertificateException("Exception in getNoOfSCCertificate", ex);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getNoOfSCCertificate( for search key: "
					+ aCriteria.toString());
		}
	}

	protected int getNoOfPartialSCCertificate(SCCertificateSearchCriteria aCriteria) throws SCCertificateException {
		try {
			return getSBSCCertificateBusManager().getNoOfPartialSCCertificate(aCriteria);
		}
		catch (SearchDAOException ex) {
			throw new SCCertificateException("Exception in getNoOfPartialSCCertificate", ex);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getNoOfPartialSCCertificate( for search key: "
					+ aCriteria.toString());
		}
	}

	protected String getSCCTrxIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException, SCCertificateException {
		try {
			return getSBSCCertificateBusManager().getSCCTrxIDbyLimitProfile(aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getSCCTrxIDbyLimitProfile( for search key: "
					+ aLimitProfileID);
		}
	}

	protected String getPSCCTrxIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException, SCCertificateException {
		try {
			return getSBSCCertificateBusManager().getPSCCTrxIDbyLimitProfile(aLimitProfileID);
		}
		catch (RemoteException ex) {
			throw new SCCertificateException("Exception in getPSCCTrxIDbyLimitProfile( for search key: "
					+ aLimitProfileID);
		}
	}

	/**
	 * Helper method to return the certificate bus session bean
	 * 
	 * @return SBSCCertificateBusManager - the remote handler for the
	 *         certificate bus manager session bean
	 * @throws SCCertificateException for any errors encountered
	 */
	private SBSCCertificateBusManager getSBSCCertificateBusManager() throws SCCertificateException {
		SBSCCertificateBusManager busmgr = (SBSCCertificateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_SCCERTIFICATE_BUS_JNDI, SBSCCertificateBusManagerHome.class.getName());
		if (busmgr == null) {
			throw new SCCertificateException("SBSCCertificateBusManager is null!");
		}
		return busmgr;
	}

	/**
	 * Helper method to return the staging certificate bus session bean
	 * 
	 * @return SBSCCertificateBusManager - the remote handler for the
	 *         certificate bus manager session bean
	 * @throws SCCertificateException for any errors encountered
	 */
	private SBSCCertificateBusManager getSBStagingCertificateBusManager() throws SCCertificateException {
		SBSCCertificateBusManager busmgr = (SBSCCertificateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_SCCERTIFICATE_BUS_JNDI, SBSCCertificateBusManagerHome.class.getName());
		if (busmgr == null) {
			throw new SCCertificateException("SBSCCertificateBusManager is null!");
		}
		return busmgr;
	}

	/**
	 * Helper method to return the forex session bean
	 * 
	 * @return SBForexManager - the remote handler for the forex manager session
	 *         bean
	 * @throws SCCertificateException for any errors encountered
	 */
	private SBForexManager getSBForexManager() throws SCCertificateException {
		SBForexManager mgr = (SBForexManager) BeanController.getEJB(ICMSJNDIConstant.SB_FOREX_MANAGER_JNDI,
				SBForexManagerHome.class.getName());
		if (mgr == null) {
			throw new SCCertificateException("SBForexManager is null!");
		}
		return mgr;
	}

}
