/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/proxy/SBCollaborationTaskProxyManagerBean.java,v 1.4 2003/09/08 02:08:20 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.proxy;

//java
import java.rmi.RemoteException;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.collaborationtask.bus.CCTaskSearchCriteria;
import com.integrosys.cms.app.collaborationtask.bus.CCTaskSearchResult;
import com.integrosys.cms.app.collaborationtask.bus.CollaborationTaskException;
import com.integrosys.cms.app.collaborationtask.bus.CollateralTaskSearchCriteria;
import com.integrosys.cms.app.collaborationtask.bus.CollateralTaskSearchResult;
import com.integrosys.cms.app.collaborationtask.bus.SBCollaborationTaskBusManager;
import com.integrosys.cms.app.collaborationtask.bus.SBCollaborationTaskBusManagerHome;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Session bean implementation of the services provided by the collaboration
 * task proxy manager. This will only contains the persistance logic.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/09/08 02:08:20 $ Tag: $Name: $
 */
public class SBCollaborationTaskProxyManagerBean extends AbstractCollaborationTaskProxyManager implements SessionBean {
	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBCollaborationTaskProxyManagerBean() {
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

	protected int getNoOfCollateralTask(CollateralTaskSearchCriteria aCriteria) throws CollaborationTaskException {
		try {
			return getSBCollaborationTaskBusManager().getNoOfCollateralTask(aCriteria);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CollaborationTaskException("Exception in getNoOfCollateralTask", ex);
		}
		catch (RemoteException ex) {
			rollback();
			throw new CollaborationTaskException("Exception in getNoOfCollateralTask( for search key: "
					+ aCriteria.toString());
		}
	}

	protected CollateralTaskSearchResult[] getCollateralTask(CollateralTaskSearchCriteria aCriteria)
			throws CollaborationTaskException {
		try {
			return getSBCollaborationTaskBusManager().getCollateralTask(aCriteria);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CollaborationTaskException("Exception in getCollateralTask", ex);
		}
		catch (RemoteException ex) {
			rollback();
			throw new CollaborationTaskException("Exception in getCollateralTask( for search key: "
					+ aCriteria.toString());
		}
	}

	protected int getNoOfCCTask(CCTaskSearchCriteria aCriteria) throws CollaborationTaskException {
		try {
			return getSBCollaborationTaskBusManager().getNoOfCCTask(aCriteria);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CollaborationTaskException("Exception in getNoOfCCTask", ex);
		}
		catch (RemoteException ex) {
			rollback();
			throw new CollaborationTaskException("Exception in getNoOfCCTask( for search key: " + aCriteria.toString());
		}
	}

	public CCTaskSearchResult[] getCCTask(CCTaskSearchCriteria aCriteria) throws CollaborationTaskException {
		try {
			return getSBCollaborationTaskBusManager().getCCTask(aCriteria);
		}
		catch (SearchDAOException ex) {
			rollback();
			throw new CollaborationTaskException("Exception in getCCTask", ex);
		}
		catch (RemoteException ex) {
			rollback();
			throw new CollaborationTaskException("Exception in getCCTask( for search key: " + aCriteria.toString());
		}
	}

	/**
	 * To rollback a transaction
	 */
	protected void rollback() {
		_context.setRollbackOnly();
	}

	/**
	 * Helper method to return the collaboration task bus session bean
	 * 
	 * @return SBCollaborationTaskBusManager - the remote handler for the
	 *         collaboration task bus manager session bean
	 * @throws CollaborationTaskException for any errors encountered
	 */
	private SBCollaborationTaskBusManager getSBCollaborationTaskBusManager() throws CollaborationTaskException {
		SBCollaborationTaskBusManager busmgr = (SBCollaborationTaskBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_COLLABORATION_BUS_JNDI, SBCollaborationTaskBusManagerHome.class.getName());
		if (busmgr == null) {
			throw new CollaborationTaskException("SBCollaborationTaskBusManager is null!");
		}
		return busmgr;
	}

	/**
	 * Helper method to return the staging collaboration task bus session bean
	 * 
	 * @return SBCollaborationTaskBusManager - the remote handler for the
	 *         collaboration task bus manager session bean
	 * @throws CollaborationTaskException for any errors encountered
	 */
	private SBCollaborationTaskBusManager getSBStagingCollaborationTaskBusManager() throws CollaborationTaskException {
		SBCollaborationTaskBusManager busmgr = (SBCollaborationTaskBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_COLLABORATION_BUS_JNDI, SBCollaborationTaskBusManagerHome.class.getName());
		if (busmgr == null) {
			throw new CollaborationTaskException("SBCollaborationTaskBusManager is null!");
		}
		return busmgr;
	}
}
