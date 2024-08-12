/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/proxy/SBGenerateRequestProxyManagerBean.java,v 1.4 2003/09/22 02:23:34 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.proxy;

//java
import java.rmi.RemoteException;
import java.util.HashMap;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.forex.SBForexManager;
import com.integrosys.base.businfra.forex.SBForexManagerHome;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.SBCheckListBusManager;
import com.integrosys.cms.app.checklist.bus.SBCheckListBusManagerHome;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.generatereq.bus.DeferralRequestSearchCriteria;
import com.integrosys.cms.app.generatereq.bus.GenerateRequestException;
import com.integrosys.cms.app.generatereq.bus.SBGenerateRequestBusManager;
import com.integrosys.cms.app.generatereq.bus.SBGenerateRequestBusManagerHome;
import com.integrosys.cms.app.generatereq.bus.WaiverRequestSearchCriteria;

/**
 * Session bean implementation of the services provided by the certificate proxy
 * manager. This will only contains the persistance logic.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/09/22 02:23:34 $ Tag: $Name: $
 */
public class SBGenerateRequestProxyManagerBean extends AbstractGenerateRequestProxyManager implements SessionBean {
	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBGenerateRequestProxyManagerBean() {
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

	protected int getNoOfWaiverRequest(WaiverRequestSearchCriteria aCriteria) throws GenerateRequestException {
		try {
			return getSBGenerateRequestBusManager().getNoOfWaiverRequest(aCriteria);
		}
		catch (SearchDAOException ex) {
			throw new GenerateRequestException("Exception in getNoOfWaiverRequest", ex);
		}
		catch (RemoteException ex) {
			throw new GenerateRequestException("Exception in getNoOfWaiverRequest", ex);
		}
	}

	protected int getNoOfDeferralRequest(DeferralRequestSearchCriteria aCriteria) throws GenerateRequestException {
		try {
			return getSBGenerateRequestBusManager().getNoOfDeferralRequest(aCriteria);
		}
		catch (SearchDAOException ex) {
			throw new GenerateRequestException("Exception in getNoOfDeferralRequest", ex);
		}
		catch (RemoteException ex) {
			throw new GenerateRequestException("Exception in getNoOfDeferralRequest", ex);
		}
	}

	protected HashMap getCheckListItemListbyStatus(long aLimitProfileID, String anItemStatus)
			throws GenerateRequestException {
		try {
			return getSBCheckListBusManager().getCheckListItemListbyStatus(aLimitProfileID, anItemStatus);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new GenerateRequestException("SearchDAOException in getCheckListItemListbyStatus", ex);
		}
		catch (CheckListException ex) {
			rollback();
			throw new GenerateRequestException("CheckListException in getCheckListItemListbyStatus", ex);
		}
		catch (RemoteException ex) {
			rollback();
			throw new GenerateRequestException("RemoteException in getCheckListItemListbyStatus: " + ex.toString());
		}
	}

	protected HashMap getCheckListItemListbyStatusForNonBorrower(long aCustomerID, String anItemStatus)
			throws GenerateRequestException {
		try {
			return getSBCheckListBusManager().getCheckListItemListbyStatusForNonBorrower(aCustomerID, anItemStatus);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new GenerateRequestException("SearchDAOException in getCheckListItemListbyStatusForNonBorrower", ex);
		}
		catch (CheckListException ex) {
			rollback();
			throw new GenerateRequestException("CheckListException in getCheckListItemListbyStatusForNonBorrower", ex);
		}
		catch (RemoteException ex) {
			rollback();
			throw new GenerateRequestException("RemoteException in getCheckListItemListbyStatusForNonBorrower: "
					+ ex.toString());
		}
	}

	protected long convertAmount(Amount anAmount, CurrencyCode aCurrencyCode) throws GenerateRequestException {
		if (anAmount == null) {
			return 0;
		}
		if (anAmount.getCurrencyCode() == null) {
			throw new GenerateRequestException("Currency Code is null !!");
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
			throw new GenerateRequestException("Exception in convertAmount: " + ex.toString());
		}
	}

	/**
	 * Helper method to return the generate request bus session bean
	 * 
	 * @return SBGenerateRequestBusManager - the remote handler for the generate
	 *         request bus manager session bean
	 * @throws GenerateRequestException for any errors encountered
	 */
	private SBGenerateRequestBusManager getSBGenerateRequestBusManager() throws GenerateRequestException {
		SBGenerateRequestBusManager busmgr = (SBGenerateRequestBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_GENERATE_REQUEST_BUS_JNDI, SBGenerateRequestBusManagerHome.class.getName());
		if (busmgr == null) {
			throw new GenerateRequestException("SBGenerateRequestBusManager is null!");
		}
		return busmgr;
	}

	/**
	 * Helper method to return the staging generate request bus session bean
	 * 
	 * @return SBGenerateRequestBusManager - the remote handler for the generate
	 *         request bus manager session bean
	 * @throws GenerateRequestException for any errors encountered
	 */
	private SBGenerateRequestBusManager getSBStagingCertificateBusManager() throws GenerateRequestException {
		SBGenerateRequestBusManager busmgr = (SBGenerateRequestBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_GENERATE_REQUEST_BUS_JNDI, SBGenerateRequestBusManagerHome.class.getName());
		if (busmgr == null) {
			throw new GenerateRequestException("SBGenerateRequestBusManager is null!");
		}
		return busmgr;
	}

	/**
	 * Helper method to return the checklist bus session bean
	 * 
	 * @return SBCheckListBusManager - the remote handler for the checklist bus
	 *         manager session bean
	 * @throws CheckListException for any errors encountered
	 */
	private SBCheckListBusManager getSBCheckListBusManager() throws CheckListException {
		SBCheckListBusManager busmgr = (SBCheckListBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CHECKLIST_BUS_JNDI, SBCheckListBusManagerHome.class.getName());
		if (busmgr == null) {
			throw new CheckListException("SBCheckListBusManager is null!");
		}
		return busmgr;
	}

	/**
	 * Helper method to return the forex session bean
	 * 
	 * @return SBForexManager - the remote handler for the forex manager session
	 *         bean
	 * @throws GenerateRequestException for any errors encountered
	 */
	private SBForexManager getSBForexManager() throws GenerateRequestException {
		SBForexManager mgr = (SBForexManager) BeanController.getEJB(ICMSJNDIConstant.SB_FOREX_MANAGER_JNDI,
				SBForexManagerHome.class.getName());
		if (mgr == null) {
			throw new GenerateRequestException("SBForexManager is null!");
		}
		return mgr;
	}
}
