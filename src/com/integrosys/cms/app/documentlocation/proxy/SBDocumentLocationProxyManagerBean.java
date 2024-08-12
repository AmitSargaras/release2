/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/proxy/SBDocumentLocationProxyManagerBean.java,v 1.3 2004/04/06 09:22:38 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.proxy;

//java
import java.rmi.RemoteException;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.documentlocation.bus.CCDocumentLocationSearchCriteria;
import com.integrosys.cms.app.documentlocation.bus.CCDocumentLocationSearchResult;
import com.integrosys.cms.app.documentlocation.bus.DocumentLocationException;
import com.integrosys.cms.app.documentlocation.bus.ICCDocumentLocation;
import com.integrosys.cms.app.documentlocation.bus.SBDocumentLocationBusManager;
import com.integrosys.cms.app.documentlocation.bus.SBDocumentLocationBusManagerHome;

/**
 * Session bean implementation of the services provided by the document location
 * proxy manager. This will only contains the persistance logic.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/04/06 09:22:38 $ Tag: $Name: $
 */
public class SBDocumentLocationProxyManagerBean extends AbstractDocumentLocationProxyManager implements SessionBean {
	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBDocumentLocationProxyManagerBean() {
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

	protected int getNoOfCCDocumentLocation(CCDocumentLocationSearchCriteria aCriteria)
			throws DocumentLocationException {
		try {
			return getSBDocumentLocationBusManager().getNoOfCCDocumentLocation(aCriteria);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new DocumentLocationException("Exception in getNoOfCCDocumentLocation", ex);
		}
		catch (RemoteException ex) {
			rollback();
			throw new DocumentLocationException("Exception in getNoOfCCDocumentLocation for search key: "
					+ aCriteria.toString());
		}
	}

	public CCDocumentLocationSearchResult[] getCCDocumentLocation(CCDocumentLocationSearchCriteria aCriteria)
			throws DocumentLocationException {
		try {
			return getSBDocumentLocationBusManager().getCCDocumentLocation(aCriteria);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new DocumentLocationException("Exception in getCCDocumentLocation", ex);
		}
		catch (RemoteException ex) {
			rollback();
			throw new DocumentLocationException("Exception in getCCDocumentLocation for search key: "
					+ aCriteria.toString());
		}
	}

	/**
	 * Get the list of cc document location based on the criteria
	 * @param anOwnerType of String type
	 * @param aLimitProfileID of long type
	 * @param anOwnerID of long type
	 * @return ICCDocumentLocation[] - the list of cc document location
	 * @throws DocumentLocationException on error
	 */
	public ICCDocumentLocation[] getCCDocumentLocation(String anOwnerType, long aLimitProfileID, long anOwnerID)
			throws DocumentLocationException {
		try {
			return getSBDocumentLocationBusManager().getCCDocumentLocation(anOwnerType, aLimitProfileID, anOwnerID);
		}
		catch (RemoteException ex) {
			rollback();
			throw new DocumentLocationException("Exception in getCCDocumentLocation", ex);
		}
	}

	/**
	 * To rollback a transaction
	 */
	protected void rollback() {
		_context.setRollbackOnly();
	}

	/**
	 * Helper method to return the document location bus session bean
	 * 
	 * @return SBDDocumentLocationBusManager - the remote handler for the
	 *         document location bus manager session bean
	 * @throws DocumentLocationException for any errors encountered
	 */
	private SBDocumentLocationBusManager getSBDocumentLocationBusManager() throws DocumentLocationException {
		SBDocumentLocationBusManager busmgr = (SBDocumentLocationBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_DOC_LOCATION_BUS_JNDI, SBDocumentLocationBusManagerHome.class.getName());
		if (busmgr == null) {
			throw new DocumentLocationException("SBDocumentLocationBusManager is null!");
		}
		return busmgr;
	}

	/**
	 * Helper method to return the staging document location bus session bean
	 * 
	 * @return SBDocumentLocationBusManager - the remote handler for the
	 *         document location bus manager session bean
	 * @throws DocumentLocationException for any errors encountered
	 */
	private SBDocumentLocationBusManager getSBStagingDocumentLocationBusManager() throws DocumentLocationException {
		SBDocumentLocationBusManager busmgr = (SBDocumentLocationBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_DOC_LOCATION_BUS_JNDI, SBDocumentLocationBusManagerHome.class.getName());
		if (busmgr == null) {
			throw new DocumentLocationException("SBDocumentLocationBusManager is null!");
		}
		return busmgr;
	}
}
