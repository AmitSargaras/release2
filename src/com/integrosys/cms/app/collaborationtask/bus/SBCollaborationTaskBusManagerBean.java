/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/SBCollaborationTaskBusManagerBean.java,v 1.15 2006/08/08 10:04:10 jzhai Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

//java
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.IPledgor;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;

/**
 * Session bean implementation of the services provided by the certificate bus
 * manager. This will only contains the persistance logic.
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2006/08/08 10:04:10 $ Tag: $Name: $
 */
public class SBCollaborationTaskBusManagerBean extends AbstractCollaborationTaskBusManager implements SessionBean {
	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBCollaborationTaskBusManagerBean() {
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
	 * To get the number of collateral task that satisfy the criteria
	 * @param aCriteria of CollaborationTaskSearchCriteria type
	 * @return int - the number of Collateral Task that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfCollateralTask(CollateralTaskSearchCriteria aCriteria) throws SearchDAOException,
			CollaborationTaskException {
		try {
			EBCollateralTaskHome home = getEBCollateralTaskHome();
			return home.getNoOfCollateralTask(aCriteria);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in getNoOfCollateralTask: " + ex.toString());
		}
	}

	/**
	 * To get the number of cc task that satisfy the criteria
	 * @param aCriteria of CollaborationTaskSearchCriteria type
	 * @return int - the number of CC Task that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfCCTask(CCTaskSearchCriteria aCriteria) throws SearchDAOException, CollaborationTaskException {
		try {
			EBCCTaskHome home = getEBCCTaskHome();
			return home.getNoOfCCTask(aCriteria);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in getNoOfCCTask: " + ex.toString());
		}
	}

	/**
	 * To get the list of collateral task that satisfy the criteria
	 * @param aCritieria of CollateralTaskSearchCritieria type
	 * @return CollateralTaskSearchResult[] - the list of collateral task
	 * @throws SearchDAOException
	 */
	public CollateralTaskSearchResult[] getCollateralTask(CollateralTaskSearchCriteria aCriteria)
			throws SearchDAOException, CollaborationTaskException {
		try {
			EBCollateralTaskHome home = getEBCollateralTaskHome();
			return home.getCollateralTask(aCriteria);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in getCollateralTask: " + ex.toString());
		}
	}

	/**
	 * To get the list of cc task that satisfy the criteria
	 * @param aCritieria of CCTaskSearchCritieria type
	 * @return CCTaskSearchResult[] - the list of CC task
	 * @throws SearchDAOException
	 */
	public CCTaskSearchResult[] getCCTask(CCTaskSearchCriteria aCriteria) throws SearchDAOException,
			CollaborationTaskException {
		try {
			EBCCTaskHome home = getEBCCTaskHome();
			return home.getCCTask(aCriteria);
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in getCCTask: " + ex.toString());
		}
	}

	/**
	 * Get the collateral task based on the task ID
	 * @param aTaskID of long type
	 * @return ICollateralTask - the collateral task with the task ID
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTask getCollateralTask(long aTaskID) throws CollaborationTaskException {
		try {
			EBCollateralTaskHome home = getEBCollateralTaskHome();
			EBCollateralTask remote = home.findByPrimaryKey(new Long(aTaskID));
			ICollateralTask colTask = remote.getValue();
			populateLimitCollateralInfo(colTask);
			return colTask;
		}
		catch (FinderException ex) {
			throw new CollaborationTaskException("Exception in getCollateralTask: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in getCollateralTask: " + ex.toString());
		}
	}

	/**
	 * Get the CC task by limit profile ID, cust category and customer ID
	 * @param aLimitProfileID of long type
	 * @param aCustCategory of String type
	 * @param aCustomerID of long type
	 * @return ICCTask - the CC Task
	 * @throws CollaborationTaskException
	 */
	public ICCTask getCCTask(long aLimitProfileID, String aCustCategory, long aCustomerID)
			throws CollaborationTaskException {
		try {
			EBCCTaskHome home = getEBCCTaskHome();
			Collection remoteList = null;
			if (ICMSConstant.CHECKLIST_PLEDGER.equals(aCustCategory)) {
				remoteList = home.findByLimitProfileAndPledgorID(new Long(aLimitProfileID), new Long(aCustomerID));
			}
			else {
				remoteList = home.findByLimitProfileAndSubProfile(new Long(aLimitProfileID), new Long(aCustomerID));
			}
			if ((remoteList == null) || (remoteList.size() == 0)) {
				return null;
			}
			if (remoteList.size() > 1) {
				throw new CollaborationTaskException("More than 1 records found with " + aLimitProfileID + ", "
						+ aCustCategory + ", " + aCustomerID);
			}
			Iterator iter = remoteList.iterator();
			EBCCTask remote = (EBCCTask) iter.next();
			ICCTask ccTask = remote.getValue();
			populateCustomerInfo(ccTask);
			return ccTask;
		}
		catch (FinderException ex) {
			throw new CollaborationTaskException("Exception in getCollateralTask: " + ex.toString());

		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in getCollateralTask: " + ex.toString());
		}
	}

	/**
	 * Get the CC task based on the task ID
	 * @param aTaskID of long type
	 * @return ICCTask - the CC task with the task ID
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTask getCCTask(long aTaskID) throws CollaborationTaskException {
		try {
			EBCCTaskHome home = getEBCCTaskHome();
			EBCCTask remote = home.findByPrimaryKey(new Long(aTaskID));
			ICCTask colTask = remote.getValue();
			populateCustomerInfo(colTask);
			return colTask;
		}
		catch (FinderException ex) {
			throw new CollaborationTaskException("Exception in getCCTask: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in getCCTask: " + ex.toString());
		}
	}

	/**
	 * Create a collateral task
	 * @param anICollateralTask of ICollateralTask type
	 * @return ICollateralTask - the collateral task created
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTask createCollateralTask(ICollateralTask anICollateralTask) throws CollaborationTaskException {
		try {
			if (anICollateralTask == null) {
				throw new CollaborationTaskException("The ICollateralTask to be created is null !!!");
			}
			EBCollateralTaskHome home = getEBCollateralTaskHome();
			EBCollateralTask remote = home.create(anICollateralTask);
			ICollateralTask colTask = remote.getValue();
			populateLimitCollateralInfo(colTask);
			return colTask;
		}
		catch (CreateException ex) {
			throw new CollaborationTaskException("Exception in createCollateralTask: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in createCollateralTask: " + ex.toString());
		}
	}

	/**
	 * Create a CC task
	 * @param anICCTask of ICCTask type
	 * @return ICCTask - the CC task created
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTask createCCTask(ICCTask anICCTask) throws CollaborationTaskException {
		try {
			if (anICCTask == null) {
				throw new CollaborationTaskException("The ICCTask to be created is null !!!");
			}
			EBCCTaskHome home = getEBCCTaskHome();
			EBCCTask remote = home.create(anICCTask);
			ICCTask colTask = remote.getValue();
			populateCustomerInfo(colTask);
			return colTask;
		}
		catch (CreateException ex) {
			throw new CollaborationTaskException("Exception in createCCTask: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in createCCTask: " + ex.toString());
		}
	}

	/**
	 * Update a collateral task
	 * @param anICollateralTask of ICollateralTask
	 * @return ICollateralTask - the collateral task updated
	 * @throws CollaborationTaskException on errors
	 */
	public ICollateralTask updateCollateralTask(ICollateralTask anICollateralTask) throws ConcurrentUpdateException,
			CollaborationTaskException {
		try {
			if (anICollateralTask == null) {
				throw new CollaborationTaskException("The ICollateralTask to be updated is null !!!");
			}
			if (anICollateralTask.getTaskID() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				throw new CollaborationTaskException("The taskID of the collateral task to be updated is invalid !!!");
			}
			EBCollateralTaskHome home = getEBCollateralTaskHome();
			EBCollateralTask remote = home.findByPrimaryKey(new Long(anICollateralTask.getTaskID()));
			remote.setValue(anICollateralTask);
			ICollateralTask colTask = remote.getValue();
			populateLimitCollateralInfo(colTask);
			return colTask;
		}
		catch (FinderException ex) {
			throw new CollaborationTaskException("Exception in updateCollateralTask: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in updateCollateralTask: " + ex.toString());
		}
	}

	/**
	 * Update a CC task
	 * @param anICCTask of ICCTask
	 * @return ICCTask - the CC task updated
	 * @throws CollaborationTaskException on errors
	 */
	public ICCTask updateCCTask(ICCTask anICCTask) throws ConcurrentUpdateException, CollaborationTaskException {
		try {
			if (anICCTask == null) {
				throw new CollaborationTaskException("The ICCTask to be updated is null !!!");
			}
			if (anICCTask.getTaskID() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				throw new CollaborationTaskException("The taskID of the CC task to be updated is invalid !!!");
			}
			EBCCTaskHome home = getEBCCTaskHome();
			EBCCTask remote = home.findByPrimaryKey(new Long(anICCTask.getTaskID()));
			remote.setValue(anICCTask);
			ICCTask colTask = remote.getValue();
			populateCustomerInfo(colTask);
			return colTask;
		}
		catch (FinderException ex) {
			throw new CollaborationTaskException("Exception in updateCCTask: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new CollaborationTaskException("Exception in updateCCTask: " + ex.toString());
		}
	}

	/**
	 * Populate the limit reference and the collateral type and sub type which
	 * are not being persisted
	 * @param anICollateralTask of ICollateralTask type
	 * @throws CollaborationTaskException on errors
	 */
	private void populateLimitCollateralInfo(ICollateralTask anICollateralTask) throws CollaborationTaskException {
		try {
			if (anICollateralTask == null) {
				throw new CollaborationTaskException("ICollateralTask is null !!!");
			}
			long limitProfileID = anICollateralTask.getLimitProfileID();
			long collateralID = anICollateralTask.getCollateralID();
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			ILimitProfile profile = proxy.getLimitProfile(limitProfileID);

			ICheckListProxyManager ProxyChecklist = CheckListProxyManagerFactory.getCheckListProxyManager();

			HashMap collateralLimitMap = ProxyChecklist.getCollateralLimitMap(profile);
			HashMap hmap = (HashMap) collateralLimitMap.get(ICMSConstant.CHECKLIST_MAIN_BORROWER);
			HashMap cbMap = (HashMap) collateralLimitMap.get(ICMSConstant.CHECKLIST_CO_BORROWER);
			HashMap colMap = (HashMap) collateralLimitMap.get("COLLATERAL");

			for (Iterator i = colMap.keySet().iterator(); i.hasNext();) {
				ICollateral col = (ICollateral) i.next();
				if (collateralID == col.getCollateralID()) {
					anICollateralTask.setCollateralRef(col.getSCISecurityID());
					anICollateralTask.setCollateralType(col.getCollateralType());
					anICollateralTask.setCollateralSubType(col.getCollateralSubType());

					ArrayList limitList = (ArrayList) hmap.get(col);
					ArrayList cbLimitList = (ArrayList) cbMap.get(col);

					if (limitList != null) {
						ILimit[] limits = (ILimit[]) limitList.toArray(new ILimit[limitList.size()]);
						anICollateralTask.setLimitList(limits);

					}
					else {

						if (cbLimitList != null) {
							ICoBorrowerLimit[] cbLimits = (ICoBorrowerLimit[]) cbLimitList
									.toArray(new ICoBorrowerLimit[cbLimitList.size()]);
							anICollateralTask.setCoBorrowerLimitList(cbLimits);
						}
					}
				}

			}

		}
		catch (CheckListException ex) {
			throw new CollaborationTaskException("Exception in populateLimitCollateralInfo", ex);
		}
		catch (LimitException ex) {
			throw new CollaborationTaskException("Exception in populateLimitCollateralInfo", ex);
		}

	}

	private void populateCustomerInfo(ICCTask anICCTask) throws CollaborationTaskException {
		try {
			String custCategory = anICCTask.getCustomerCategory();
			DefaultLogger.debug(this, "Customer Type: " + custCategory);
			DefaultLogger.debug(this, "CustomerID: " + anICCTask.getCustomerID());
			if (ICMSConstant.CHECKLIST_PLEDGER.equals(custCategory)) {
				IPledgor pledgor = getPledgor(anICCTask.getCustomerID());
				// DefaultLogger.debug(this, "Pledgor: " + pledgor);
				anICCTask.setLegalRef(String.valueOf(pledgor.getLegalID()));
				anICCTask.setLegalName(pledgor.getPledgorName());
				anICCTask.setCustomerType(pledgor.getLegalType());
				return;
			}
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			ICMSCustomer customer = custProxy.getCustomer(anICCTask.getCustomerID());
			// DefaultLogger.debug(this, "Customer: " + customer);
			anICCTask.setLegalRef(customer.getCMSLegalEntity().getLEReference());
			anICCTask.setLegalName(customer.getCMSLegalEntity().getLegalName());
			anICCTask.setCustomerType(customer.getCMSLegalEntity().getLegalConstitution());
		}
		catch (CustomerException ex) {
			throw new CollaborationTaskException("Exception in populateCustomerInfo", ex);
		}
	}

	protected IPledgor getPledgor(long aPledgorID) throws CollaborationTaskException {
		try {
			ICollateralProxy proxy = CollateralProxyFactory.getProxy();
			return proxy.getPledgor(aPledgorID);
		}
		catch (CollateralException ex) {
			throw new CollaborationTaskException("Exception in getPledgor: " + ex.toString());
		}
	}

	/**
	 * To get the home handler for the SCCertificate Entity Bean
	 * @return EBCollateralTaskHome - the home handler for the SC Certificate
	 *         entity bean
	 */
	protected EBCollateralTaskHome getEBCollateralTaskHome() {
		EBCollateralTaskHome ejbHome = (EBCollateralTaskHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_COLLATERAL_TASK_JNDI, EBCollateralTaskHome.class.getName());
		return ejbHome;
	}

	/**
	 * To get the home handler for the SCCertificate Entity Bean
	 * @return EBCCTaskHome - the home handler for the SC Certificate entity
	 *         bean
	 */
	protected EBCCTaskHome getEBCCTaskHome() {
		EBCCTaskHome ejbHome = (EBCCTaskHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_CC_TASK_JNDI,
				EBCCTaskHome.class.getName());
		return ejbHome;
	}
}
