package com.integrosys.cms.app.creditriskparam.bus.policycap;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.creditriskparam.trx.policycap.IPolicyCapTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class SBPolicyCapBusManagerBean extends AbstractPolicyCapBusManager implements SessionBean {

	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBPolicyCapBusManagerBean() {
	}

	// ==========================================
	// Start of Standard Session Bean Methods
	// ==========================================

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

	// ==========================================
	// Implementation Methods
	// ==========================================
	/**
	 * Create a list of policy cap
	 * @param policyCapList - IPolicyCap[]
	 * @return IPolicyCap - the policy cap being created
	 * @throws PolicyCapException on errors
	 */
	public IPolicyCap[] create(IPolicyCap[] policyCapList) throws PolicyCapException {
		try {
			if (policyCapList == null) {
				throw new PolicyCapException("The IPolicyCap[] to be created is null !!!");
			}

			EBPolicyCapHome home = getEBPolicyCapHome();
			ArrayList list = new ArrayList();
			for (int i = 0; i < policyCapList.length; i++) {
				EBPolicyCap remote = home.create(policyCapList[i]);
				list.add(remote.getValue());
			}
			return (IPolicyCap[]) list.toArray(new IPolicyCap[0]);
		}
		catch (CreateException ex) {
			throw new PolicyCapException("Exception in createPolicyCap: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new PolicyCapException("Exception in createPolicyCap: " + ex.toString());
		}
	}

	/**
	 * Update a list of policy cap
	 * @param policyCapList - IPolicyCap[]
	 * @return ICheckList - the policy cap being updated
	 * @throws PolicyCapException on errors
	 * @throws com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException
	 *         on concurrent updates
	 */
	public IPolicyCap[] update(IPolicyCap[] policyCapList) throws ConcurrentUpdateException, PolicyCapException {
		try {
			if (policyCapList == null) {
				throw new PolicyCapException("The IPolicyCap[] to be update is null !!!");
			}

			EBPolicyCapHome home = getEBPolicyCapHome();
			ArrayList list = new ArrayList();
			for (int i = 0; i < policyCapList.length; i++) {
				EBPolicyCap remote = home.findByPrimaryKey(new Long(policyCapList[i].getPolicyCapID()));
				DefaultLogger.debug(this, ">>>>>>>> policy cap id: " + policyCapList[i].getPolicyCapID()
						+ " | policy cap ref: " + policyCapList[i].getCommonRef() + " | policy cap version time: "
						+ policyCapList[i].getVersionTime());
				DefaultLogger.debug(this, ">>>>>>>> db policy cap id: " + remote.getValue().getPolicyCapID()
						+ " | db policy cap ref: " + remote.getValue().getCommonRef()
						+ " | db policy cap version time: " + remote.getValue().getVersionTime());
				remote.setValue(policyCapList[i]);
				list.add(remote.getValue());
			}
			return (IPolicyCap[]) list.toArray(new IPolicyCap[0]);
		}
		catch (FinderException ex) {
			throw new PolicyCapException("Exception in update policy cap: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new PolicyCapException("Exception in create policy cap: " + ex.toString());
		}
	}

	/**
	 * Get the Policy Cap by the Exchange Code
	 * @param exchangeCode of String type
	 * @return IPolicyCap[] - the Policy Cap List for the given exchange
	 * @throws com.integrosys.cms.app.creditriskparam.bus.policycap.PolicyCapException
	 *         on errors
	 */
	public IPolicyCap[] getPolicyCapByExchange(String exchangeCode) throws PolicyCapException {
		// try {
		// EBPolicyCapHome home = getEBPolicyCapHome();
		// Collection resultList = home.findPolicyCapByExchange(exchangeCode);
		// return convertCollectionToPolicyCapArray(resultList);
		// }
		// catch(FinderException ex)
		// {
		// _context.setRollbackOnly();
		// throw new
		// PolicyCapException("FinderException at getPolicyCapByExchange: " +
		// ex.toString());
		// }
		// catch(RemoteException ex)
		// {
		// _context.setRollbackOnly();
		// throw new
		// PolicyCapException("RemoteException in getPolicyCapByExchange: " +
		// ex.toString());
		// }
		return null;
	}

	/**
	 * Get the Policy Cap by the Group ID
	 * @param groupID of Long type
	 * @return IPolicyCap[] - the Policy Cap List for the given exchange
	 * @throws com.integrosys.cms.app.creditriskparam.bus.policycap.PolicyCapException
	 *         on errors
	 */
	public IPolicyCap[] getPolicyCapByGroupID(Long groupID) throws PolicyCapException {
		try {
			EBPolicyCapHome home = getEBPolicyCapHome();
			Collection resultList = home.findPolicyCapByGroupID(groupID);
			return convertCollectionToPolicyCapArray(resultList);
		}
		catch (FinderException ex) {
			_context.setRollbackOnly();
			throw new PolicyCapException("FinderException at getPolicyCapByExchange: " + ex.toString());
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new PolicyCapException("RemoteException in getPolicyCapByExchange: " + ex.toString());
		}
	}

	private IPolicyCap[] convertCollectionToPolicyCapArray(Collection resultList) throws PolicyCapException {
		DefaultLogger.debug(this, ">>>>>>>> Size of Collection: " + resultList.size());
		DefaultLogger.debug(this, ">>>>>>>> Collection: \n" + resultList);

		try {

			if ((resultList == null) || (resultList.size() <= 0)) {
				DefaultLogger.debug(this, ">>>>>>>> Returning null");
				return null;
			}

			DefaultLogger.debug(this, ">>>>>>>> Conversion");
			Iterator it = resultList.iterator();
			ArrayList policyCapList = new ArrayList();
			while (it.hasNext()) {
				EBPolicyCap remote = (EBPolicyCap) it.next();
				IPolicyCap obj = remote.getValue();
				policyCapList.add(obj);
			}
			return (IPolicyCap[]) policyCapList.toArray(new IPolicyCap[0]);

		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new PolicyCapException("RemoteException in convertCollectionToPolicyCapArray: " + ex.toString());
		}

	}

	/**
	 * Get the Policy Cap by the Transaction ID
	 * @param ctx
	 * @param trxID of String type
	 * @return IPolicyCapTrxValue - the Policy Cap List
	 * @throws com.integrosys.cms.app.creditriskparam.bus.policycap.PolicyCapException
	 *         on errors
	 */
	public IPolicyCapTrxValue getPolicyCapTrxValueByTrxID(ITrxContext ctx, String trxID) throws PolicyCapException {
		return null;
	}

	/**
	 * To get the home handler for the PolicyCap Entity Bean
	 * @return EBPolicyCapHome - the home handler for the PolicyCap entity bean
	 */
	protected EBPolicyCapHome getEBPolicyCapHome() {
		EBPolicyCapHome ejbHome = (EBPolicyCapHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_POLICY_CAP_JNDI,
				EBPolicyCapHome.class.getName());
		return ejbHome;
	}

	/**
	 * To get the home handler for the PolicyCapGroup Entity Bean
	 * @return EBPolicyCapHomeGroup - the home handler for the PolicyCapGroup
	 *         entity bean
	 */
	protected EBPolicyCapGroupHome getEBPolicyCapGroupHome() {
		EBPolicyCapGroupHome ejbHome = (EBPolicyCapGroupHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_POLICY_CAP_GROUP_JNDI, EBPolicyCapGroupHome.class.getName());
		return ejbHome;
	}

	/**
	 * Crate a new Policy Cap Group
	 * @param policyCapGroup
	 * @return
	 * @throws PolicyCapException
	 */
	public IPolicyCapGroup createPolicyCapGroup(IPolicyCapGroup policyCapGroup) throws PolicyCapException {
		try {
			if (policyCapGroup == null) {
				throw new PolicyCapException("The IPolicyCapGroup to be created is null !!!");
			}

			EBPolicyCapGroupHome home = getEBPolicyCapGroupHome();
			EBPolicyCapGroup remote = home.create(policyCapGroup);

			return remote.getValue();
		}
		catch (CreateException ex) {
			throw new PolicyCapException("Exception in create PolicyCapGroup: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new PolicyCapException("Exception in create PolicyCapGroup: " + ex.toString());
		}
	}

	/**
	 * Update an existing Policy Cap Group
	 * @param policyCapGroup
	 * @return
	 * @throws ConcurrentUpdateException
	 * @throws PolicyCapException
	 */
	public IPolicyCapGroup updatePolicyCapGroup(IPolicyCapGroup policyCapGroup) throws ConcurrentUpdateException,
			PolicyCapException {
		try {

			if (policyCapGroup == null) {
				throw new PolicyCapException("The IPolicyCapGroup to be update is null !!!");
			}

			EBPolicyCapGroupHome home = getEBPolicyCapGroupHome();

			EBPolicyCapGroup remote = home.findByPrimaryKey(new Long(policyCapGroup.getPolicyCapGroupID()));
			DefaultLogger.debug(this, ">>>>>>>> policy cap group id: " + policyCapGroup.getPolicyCapGroupID()
					+ " | policy cap group version time: " + policyCapGroup.getVersionTime());
			DefaultLogger.debug(this, ">>>>>>>> db policy cap group id: " + remote.getValue().getPolicyCapGroupID()
					+ " | db policy cap group version time: " + remote.getValue().getVersionTime());
			remote.setValue(policyCapGroup);

			return remote.getValue();
		}
		catch (FinderException ex) {
			throw new PolicyCapException("Exception in update policy cap group: " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new PolicyCapException("Exception in update PolicyCapGroup: " + ex.toString());
		}
	}

	/**
	 * Get Policy Cap Group by given stock exchange code and bank entity
	 * @param exchangeCode Exchange code
	 * @param bankEntity Bank Entity
	 * @return IPolicyCapGroup
	 * @throws PolicyCapException
	 */
	public IPolicyCapGroup getPolicyCapGroupByExchangeBank(String exchangeCode, String bankEntity)
			throws PolicyCapException {

		try {
			EBPolicyCapGroupHome home = getEBPolicyCapGroupHome();
			Collection resultList = home.findPolicyCapGroupByExchangeBank(exchangeCode, bankEntity);

			if ((resultList == null) || (resultList.size() <= 0)) {
				DefaultLogger.debug(this, ">>>>>>>> Returning null");
				return null;
			}

			return ((EBPolicyCapGroup) resultList.iterator().next()).getValue();
		}
		catch (FinderException ex) {
			_context.setRollbackOnly();
			throw new PolicyCapException("FinderException at getPolicyCapGroupByExchangeBank: " + ex.toString());
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new PolicyCapException("FinderException at getPolicyCapGroupByExchangeBank: " + ex.toString());
		}

	}

	/**
	 * Get policy cap group with the primary key
	 * @param policyCapGroupId
	 * @return
	 * @throws PolicyCapException
	 */
	public IPolicyCapGroup getPolicyCapGroup(Long policyCapGroupId) throws PolicyCapException {

		try {
			EBPolicyCapGroupHome home = getEBPolicyCapGroupHome();
			EBPolicyCapGroup remote = home.findByPrimaryKey(policyCapGroupId);

			return remote.getValue();
		}
		catch (FinderException ex) {
			_context.setRollbackOnly();
			throw new PolicyCapException("FinderException at getPolicyCapGroupByExchangeBank: " + ex.toString());
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new PolicyCapException("FinderException at getPolicyCapGroupByExchangeBank: " + ex.toString());
		}
	}

}
