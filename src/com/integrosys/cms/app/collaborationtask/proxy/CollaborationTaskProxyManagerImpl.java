/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/proxy/CollaborationTaskProxyManagerImpl.java,v 1.11 2006/04/13 05:14:36 jzhai Exp $
 */
package com.integrosys.cms.app.collaborationtask.proxy;

//java
import java.rmi.RemoteException;
import java.util.HashMap;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.collaborationtask.bus.CCTaskSearchCriteria;
import com.integrosys.cms.app.collaborationtask.bus.CCTaskSearchResult;
import com.integrosys.cms.app.collaborationtask.bus.CollaborationTaskException;
import com.integrosys.cms.app.collaborationtask.bus.CollaborationTaskNotAllowedException;
import com.integrosys.cms.app.collaborationtask.bus.ICCTask;
import com.integrosys.cms.app.collaborationtask.bus.ICollateralTask;
import com.integrosys.cms.app.collaborationtask.trx.ICCTaskTrxValue;
import com.integrosys.cms.app.collaborationtask.trx.ICollateralTaskTrxValue;
import com.integrosys.cms.app.common.IContext;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This class act as a facade to the services offered by the collaboration task
 * modules
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2006/04/13 05:14:36 $ Tag: $Name: $
 */
public class CollaborationTaskProxyManagerImpl implements ICollaborationTaskProxyManager {
	/**
	 * Check if there is any pending collateral task trx
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @param aCollateralLocation of String type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws CollaborationTaskException on errors
	 */
	public boolean hasPendingCollateralTaskTrx(long aLimitProfileID, long aCollateralID, String aCollateralLocation)
			throws CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().hasPendingCollateralTaskTrx(aLimitProfileID, aCollateralID,
					aCollateralLocation);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in hasPendingCollateralTaskTrx: " + ex.toString());
		}
	}

	/**
	 * Check if there is any pending CC task trx
	 * @param aLimitProfileID of long type
	 * @param aCustomerCategory of String type
	 * @param aCustomerID of long type
	 * @param aDomicileCtry of String type
	 * @return boolean - true if there is pending trx and false otherwise
	 * @throws CollaborationTaskException on errors
	 */
	public boolean hasPendingCCTaskTrx(long aLimitProfileID, String aCustomerCategory, long aCustomerID,
			String aDomicileCtry) throws CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().hasPendingCCTaskTrx(aLimitProfileID, aCustomerCategory,
					aCustomerID, aDomicileCtry);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in hasPendingCCTaskTrx: " + ex.toString());
		}
	}

	/**
	 * Get the collateral summary list
	 * @param anIContext of IContext type
	 * @param anILimitProfile of ILimitProfile type
	 * @return HashMap - the will contain the collateral summary list and the
	 *         whether collaboration is allowed or not
	 * @throws CollaborationTaskNotAllowedException, CollaborationTaskException
	 */
	public HashMap getCollateralSummaryList(IContext anIContext, ILimitProfile anILimitProfile)
			throws CollaborationTaskNotAllowedException, CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().getCollateralSummaryList(anIContext, anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in getCollateralSummaryList: " + ex.toString());
		}
	}

	/**
	 * Get the CC summary list for non borrower
	 * @param anIContext of IContext type
	 * @param anICMSCustomer of ICMSCustomer type
	 * @return HashMap - the will contain the CC summary list and the whether
	 *         collaboration is allowed or not
	 * @throws CollaborationTaskNotAllowedException, CollaborationTaskException
	 */
	public HashMap getCCSummaryList(IContext anIContext, ICMSCustomer anICMSCustomer)
			throws CollaborationTaskNotAllowedException, CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().getCCSummaryList(anIContext, anICMSCustomer);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in getCCSummaryList: " + ex.toString());
		}
	}

	/**
	 * Get the CC summary list
	 * @param anIContext of IContext type
	 * @param anILimitProfile of ILimitProfile type
	 * @return HashMap - the will contain the CC summary list and the whether
	 *         collaboration is allowed or not
	 * @throws CollaborationTaskNotAllowedException, CollaborationTaskException
	 */
	public HashMap getCCSummaryList(IContext anIContext, ILimitProfile anILimitProfile)
			throws CollaborationTaskNotAllowedException, CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().getCCSummaryList(anIContext, anILimitProfile);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in getCCSummaryList: " + ex.toString());
		}
	}

	/**
	 * Get the collateral task trx value using the limitprofile id, collateral
	 * ID and collateral location
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @param aCollateralLocation of String type
	 * @return ICollateralTaskTrxValue - the trx value of the collateral task
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTaskTrxValue getCollateralTaskTrxValue(long aLimitProfileID, long aCollateralID,
			String aCollateralLocation) throws CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().getCollateralTaskTrxValue(aLimitProfileID, aCollateralID,
					aCollateralLocation);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in getCollateralTaskTrxValue: " + ex.toString());
		}
	}

	/**
	 * Get the cc task trx value using the limitprofile id, customer type,
	 * customer id and the country
	 * @param aLimitProfileID of long type
	 * @param aCustomerCategory of String type
	 * @param aCustomerID of long type
	 * @param aDomicileCtry of String type
	 * @return ICCTaskTrxValue - the trx value of the CC task
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTaskTrxValue getCCTaskTrxValue(long aLimitProfileID, String aCustomerCategory, long aCustomerID,
			String aDomicileCtry) throws CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().getCCTaskTrxValue(aLimitProfileID, aCustomerCategory,
					aCustomerID, aDomicileCtry);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in getCCTaskTrxValue: " + ex.toString());
		}
	}

	/**
	 * Get the collateral task trx value using the trx ID
	 * @param aTrxID of String type
	 * @return ICollateralTaskTrxValue - the collateral task trx value
	 * @throws CollaborationTaskException
	 */
	public ICollateralTaskTrxValue getCollateralTaskByTrxID(String aTrxID) throws CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().getCollateralTaskByTrxID(aTrxID);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in getCollateralTaskByTrxID: " + ex.toString());
		}
	}

	/**
	 * Get the CC task trx value using the trx ID
	 * @param aTrxID of String type
	 * @return ICCTaskTrxValue - the CC task trx value
	 * @throws CollaborationTaskException
	 */
	public ICCTaskTrxValue getCCTaskByTrxID(String aTrxID) throws CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().getCCTaskByTrxID(aTrxID);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in getCCTaskByTrxID: " + ex.toString());
		}
	}

	/**
	 * Maker create the collaboration task
	 * @param anITrxContext of ITrxContext type
	 * @param anICollateralTask of ICollateralTask type
	 * @return ICollateralTaskTrxValue - the created collateral task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTaskTrxValue makerCreateCollaborationTask(ITrxContext anITrxContext,
			ICollateralTask anICollateralTask) throws CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().makerCreateCollaborationTask(anITrxContext, anICollateralTask);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in makerCreateCollaborationTask: " + ex.toString());
		}
	}

	/**
	 * Maker update the collaboration task
	 * @param anITrxContext of ITrxContext type
	 * @param anICollateralTask of ICollateralTask type
	 * @return ICollateralTaskTrxValue - the updated collateral task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTaskTrxValue makerUpdateCollaborationTask(ITrxContext anITrxContext,
			ICollateralTaskTrxValue anICollateralTaskTrxValue, ICollateralTask anICollateralTask)
			throws CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().makerUpdateCollaborationTask(anITrxContext,
					anICollateralTaskTrxValue, anICollateralTask);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in makerUpdateCollaborationTask: " + ex.toString());
		}
	}

	/**
	 * Maker create the collaboration task
	 * @param anITrxContext of ITrxContext type
	 * @param anICCTask of ICCTask type
	 * @return ICCTaskTrxValue - the created CC task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTaskTrxValue makerCreateCollaborationTask(ITrxContext anITrxContext, ICCTask anICCTask)
			throws CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().makerCreateCollaborationTask(anITrxContext, anICCTask);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in makerCreateCollaborationTask: " + ex.toString());
		}
	}

	/**
	 * Maker update the collaboration task
	 * @param anITrxContext of ITrxContext type
	 * @param anICCTask of ICCTask type
	 * @return ICCTaskTrxValue - the updated CC task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTaskTrxValue makerUpdateCollaborationTask(ITrxContext anITrxContext, ICCTaskTrxValue anICCTaskTrxValue,
			ICCTask anICCTask) throws CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().makerUpdateCollaborationTask(anITrxContext, anICCTaskTrxValue,
					anICCTask);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in makerUpdateCollaborationTask: " + ex.toString());
		}
	}

	public ICCTaskTrxValue makerRejectCollaborationTask(ITrxContext anITrxContext, ICCTaskTrxValue anICCTaskTrxValue,
			ICCTask anICCTask) throws CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().makerRejectCollaborationTask(anITrxContext, anICCTaskTrxValue,
					anICCTask);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in makerrejectCollaborationTask: " + ex.toString());
		}
	}

	public ICollateralTaskTrxValue makerRejectCollaborationTask(ITrxContext anITrxContext,
			ICollateralTaskTrxValue anICollateralTaskTrxValue, ICollateralTask anICollateralTask)
			throws CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().makerRejectCollaborationTask(anITrxContext,
					anICollateralTaskTrxValue, anICollateralTask);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in makerUpdateCollaborationTask: " + ex.toString());
		}
	}

	/**
	 * Checker approve the collaboration task
	 * @param anITrxContext of ITrxContext type
	 * @param ICollateralTaskTrxValue of ICollateralTaskTrxValue type
	 * @return ICollateralTaskTrxValue - the created collateral task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTaskTrxValue checkerApproveCollaborationTask(ITrxContext anITrxContext,
			ICollateralTaskTrxValue anICollateralTaskTrxValue) throws CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().checkerApproveCollaborationTask(anITrxContext,
					anICollateralTaskTrxValue);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in checkerApproveCollaborationTask: " + ex.toString());
		}
	}

	/**
	 * Checker approve the collaboration task
	 * @param anITrxContext of ITrxContext type
	 * @param ICCTaskTrxValue of ICCTaskTrxValue type
	 * @return ICCTaskTrxValue - the created CC task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTaskTrxValue checkerApproveCollaborationTask(ITrxContext anITrxContext, ICCTaskTrxValue anICCTaskTrxValue)
			throws CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().checkerApproveCollaborationTask(anITrxContext, anICCTaskTrxValue);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in checkerApproveCollaborationTask: " + ex.toString());
		}
	}

	/**
	 * Checker reject the collaboration task
	 * @param anITrxContext of ITrxContext type
	 * @param anICollateralTaskTrxValue of ICollateralTaskTrxValue type
	 * @return ICollateralTaskTrxValue - the collateral task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTaskTrxValue checkerRejectCollaborationTask(ITrxContext anITrxContext,
			ICollateralTaskTrxValue anICollateralTaskTrxValue) throws CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().checkerRejectCollaborationTask(anITrxContext,
					anICollateralTaskTrxValue);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in checkerRejectCollaborationTask: " + ex.toString());
		}
	}

	/**
	 * Checker reject the collaboration task
	 * @param anITrxContext of ITrxContext type
	 * @param anICCTaskTrxValue of ICCTaskTrxValue type
	 * @return ICCTaskTrxValue - the collateral task trx value
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTaskTrxValue checkerRejectCollaborationTask(ITrxContext anITrxContext, ICCTaskTrxValue anICCTaskTrxValue)
			throws CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().checkerRejectCollaborationTask(anITrxContext, anICCTaskTrxValue);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in checkerRejectCollaborationTask: " + ex.toString());
		}
	}

	/**
	 * Maker edit the rejected collaboration task
	 * @param anITrxContext of ITrxContext type
	 * @param anICollateralTaskTrxValue of ICollateralTaskTrxValue
	 * @param anICollateralTask of ICollateralTask
	 * @return ICollateralTaskTrxValue - the Collateral task trx
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTaskTrxValue makerEditRejectedCollaborationTask(ITrxContext anITrxContext,
			ICollateralTaskTrxValue anICollateralTaskTrxValue, ICollateralTask anICollateralTask)
			throws CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().makerEditRejectedCollaborationTask(anITrxContext,
					anICollateralTaskTrxValue, anICollateralTask);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in makerEditRejectedCollaborationTask: " + ex.toString());
		}
	}

	/**
	 * Maker edit the rejected collaboration task
	 * @param anITrxContext of ITrxContext type
	 * @param anICCTaskTrxValue of ICCTaskTrxValue
	 * @param anICCTask of ICCTask
	 * @return ICCTaskTrxValue - the CC task trx
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTaskTrxValue makerEditRejectedCollaborationTask(ITrxContext anITrxContext,
			ICCTaskTrxValue anICCTaskTrxValue, ICCTask anICCTask) throws CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().makerEditRejectedCollaborationTask(anITrxContext,
					anICCTaskTrxValue, anICCTask);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in makerEditRejectedCollaborationTask: " + ex.toString());
		}
	}

	/**
	 * Make close the rejected collaboration task
	 * @param anITrxContext of ITrxContext type
	 * @param anICollateralTaskTrxValue of ICollateralTaskTrxValue type
	 * @return ICollateralTaskTrxValue - the Collateral Task trx
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTaskTrxValue makerCloseRejectedCollaborationTask(ITrxContext anITrxContext,
			ICollateralTaskTrxValue anICollateralTaskTrxValue) throws CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().makerCloseRejectedCollaborationTask(anITrxContext,
					anICollateralTaskTrxValue);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in makerCloseRejectedCollaborationTask: " + ex.toString());
		}
	}

	/**
	 * Make close the rejected collaboration task
	 * @param anITrxContext of ITrxContext type
	 * @param anICCTaskTrxValue of ICCTaskTrxValue type
	 * @return ICCTaskTrxValue - the CC Task trx
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTaskTrxValue makerCloseRejectedCollaborationTask(ITrxContext anITrxContext,
			ICCTaskTrxValue anICCTaskTrxValue) throws CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().makerCloseRejectedCollaborationTask(anITrxContext,
					anICCTaskTrxValue);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in makerCloseRejectedCollaborationTask: " + ex.toString());
		}
	}

	/**
	 * System close the collateral task for a collateral
	 * @param anITrxContext of ITrxContext type
	 * @param aCollateralID of long type
	 * @throws CollaborationTaskException
	 */
	public void systemCloseCollateralCollaborationTaskTrx(ITrxContext anITrxContext, long aCollateralID)
			throws CollaborationTaskException {
		try {
			getCollaborationTaskProxyManager().systemCloseCollateralCollaborationTaskTrx(anITrxContext, aCollateralID);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in systemCloseCollateralCollaborationTaskTrx: "
					+ ex.toString());
		}
	}

	/**
	 * Get the list of CC Task that satisfy the search criteria
	 * @param aCriteria of CCTaskSearchCriteria type
	 * @return CCTaskSearchResult[] - the list of cc task result that satisfy
	 *         the criteria
	 * @throws CollaborationTaskException
	 */
	public CCTaskSearchResult[] getCCTask(CCTaskSearchCriteria aCriteria) throws CollaborationTaskException {
		try {
			return getCollaborationTaskProxyManager().getCCTask(aCriteria);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in getCCTask: " + ex.toString());
		}
	}

	/**
	 * System update the CC Collaboration task transaction
	 * @param aLimitProfileID of long type
	 * @param aCustomerCategory of String type
	 * @param aCustomerID of long type
	 * @throws CollaborationTaskException on errors
	 */
	public void systemUpdateCCCollaborationTask(long aLimitProfileID, String aCustomerCategory, long aCustomerID)
			throws CollaborationTaskException {
		try {
			getCollaborationTaskProxyManager().systemUpdateCCCollaborationTask(aLimitProfileID, aCustomerCategory,
					aCustomerID);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in systemUpdateCCCollaborationTask: " + ex.toString());
		}
	}

	/**
	 * System update the Collateral Collaboration Task transaction
	 * @param aLimitProfileID of long type
	 * @param aCollateralID of long type
	 * @throws CollaborationTaskException on errors
	 */
	public void systemUpdateCollateralCollaborationTask(long aLimitProfileID, long aCollateralID)
			throws CollaborationTaskException {
		try {
			getCollaborationTaskProxyManager().systemUpdateCollateralCollaborationTask(aLimitProfileID, aCollateralID);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in systemUpdateCollateralCollaborationTask: "
					+ ex.toString());
		}
	}

	/**
	 * System update the Collateral Collaboration Task transaction
	 * @param aCollateralID of long type
	 * @throws CollaborationTaskException on errors
	 */
	public void systemUpdateCollateralCollaborationTask(long aCollateralID) throws CollaborationTaskException {
		try {
			getCollaborationTaskProxyManager().systemUpdateCollateralCollaborationTask(aCollateralID);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in systemUpdateCollateralCollaborationTask: "
					+ ex.toString());
		}
	}

	/**
	 * To get the remote handler for the collaboration task proxy manager
	 * @return SBCollaborationTaskProxyManager - the remote handler for the
	 *         Collaboration task proxy manager
	 */
	private SBCollaborationTaskProxyManager getCollaborationTaskProxyManager() {
		SBCollaborationTaskProxyManager proxymgr = (SBCollaborationTaskProxyManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_COLLABORATION_PROXY_JNDI, SBCollaborationTaskProxyManagerHome.class.getName());
		return proxymgr;
	}
}
