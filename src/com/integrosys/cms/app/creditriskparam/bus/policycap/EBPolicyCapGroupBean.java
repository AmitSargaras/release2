/**
 * 
 */
package com.integrosys.cms.app.creditriskparam.bus.policycap;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity Class of PolicyCapGroup Bean
 * 
 * @author $Author: Siew Kheat $<br>
 * @version $Revision: $
 * @since $Date: 28/Aug/2007 $ Tag: $Name: $
 */
public abstract class EBPolicyCapGroupBean implements IPolicyCapGroup, EntityBean {

	private EntityContext entityContext;

	private static final String[] EXCLUDE_METHOD = new String[] { "getPolicyCapGroupID" };

	/**
	 * Set the PolicyCapGroup object to the EB, which in turn persistent to
	 * database
	 * @param policyCapGroup policyCapGroup object, which contains the array of
	 *        IPolicyCap
	 * @throws ConcurrentUpdateException
	 * @throws PolicyCapException
	 */
	public void setValue(IPolicyCapGroup policyCapGroup) throws ConcurrentUpdateException, PolicyCapException {
		checkVersionMismatch(policyCapGroup);
		AccessorUtil.copyValue(policyCapGroup, this, EXCLUDE_METHOD);
		setVersionTime(VersionGenerator.getVersionNumber());

		// setValue for EBPolicyCap
		this.setPolicyCaps(policyCapGroup);
	}

	/**
	 * Get the PolicyCapGroup object from EB, refer as a interface
	 * @return policyCapGroup object, which contains the array of IPolicyCap
	 * @throws PolicyCapException
	 */
	public IPolicyCapGroup getValue() throws PolicyCapException {

		try {
			OBPolicyCapGroup value = new OBPolicyCapGroup();
			AccessorUtil.copyValue(this, value);
			return value;
		}
		catch (Exception e) {
			throw new PolicyCapException(e);
		}
	}

	/**
	 * Store each policyCap from the policyCapGroup into database
	 * @param policyCapGroup
	 * @throws PolicyCapException
	 */
	protected void setPolicyCaps(IPolicyCapGroup policyCapGroup) throws PolicyCapException {
		try {
			EBPolicyCapLocalHome home = getEBPolicyCapLocalHome();
			IPolicyCap[] policyCaps = policyCapGroup.getPolicyCapArray();

			Collection oldPolicyCaps = getPolicyCapsCMR();
			HashMap oldPolicyCapsMap = new HashMap();
			EBPolicyCapLocal pcLocal;
			for (Iterator iter = oldPolicyCaps.iterator(); iter.hasNext();) {
				pcLocal = (EBPolicyCapLocal) iter.next();
				IPolicyCap pc = pcLocal.getValue();
				oldPolicyCapsMap.put(new Long(pc.getPolicyCapID()), pcLocal);
			}

			if (policyCaps != null) {

				for (int i = 0; i < policyCaps.length; i++) {

					DefaultLogger.debug(this, ">>>>>>>>>> Policy ID : " + policyCaps[i].getPolicyCapID());
					pcLocal = (EBPolicyCapLocal) oldPolicyCapsMap.get(new Long(policyCaps[i].getPolicyCapID()));

					if (pcLocal != null) {

						DefaultLogger.debug(this, ">>>>>>>>>> Update the existing Policy Cap");
						pcLocal.setValue(policyCaps[i]);

					}
					else {

						DefaultLogger.debug(this, ">>>>>>>>>> ++++ Create new existing Policy Cap");

						EBPolicyCapLocal newPcLocal = home.create(policyCaps[i]);
						this.getPolicyCapsCMR().add(newPcLocal);
					}
				}
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PolicyCapException(e);
		}
	}

	public abstract Long getCMPPolicyCapGroupID();

	public abstract void setCMPPolicyCapGroupID(Long policyGroupId);

	public abstract String getCMPStockExchange();

	public abstract void setCMPStockExchange(String stockExchange);

	public abstract String getCMPBankEntity();

	public abstract void setCMPBankEntity(String bankEntity);

	public abstract Collection getPolicyCapsCMR();

	public abstract void setPolicyCapsCMR(Collection policyCaps);

	public String getBankEntity() {
		return getCMPBankEntity();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup#
	 * getPolicyCapGroupID()
	 */
	public long getPolicyCapGroupID() {
		if (getCMPPolicyCapGroupID() != null) {
			return getCMPPolicyCapGroupID().longValue();
		}

		return ICMSConstant.LONG_INVALID_VALUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup#
	 * getPolicyCapList()
	 */
	public IPolicyCap[] getPolicyCapArray() {
		try {
			Iterator i = getPolicyCapsCMR().iterator();
			ArrayList list = new ArrayList();

			while (i.hasNext()) {
				try {
					list.add(((EBPolicyCapLocal) i.next()).getValue());
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			return (IPolicyCap[]) list.toArray(new IPolicyCap[0]);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
		}
		return null;
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param value IPolicyCapGroup object
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IPolicyCapGroup value) throws CreateException {
		try {
			long pk = Long.parseLong((new SequenceManager())
					.getSeqNum(ICMSConstant.SEQUENCE_POLICY_CAP_GROUP_SEQ, true));
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			setPolicyCapGroupID(pk);

			this.setVersionTime(VersionGenerator.getVersionNumber());

			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup#
	 * getStockExchange()
	 */
	public String getStockExchange() {
		return getCMPStockExchange();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup#
	 * setBankEntity(java.lang.String)
	 */
	public void setBankEntity(String bankEntity) {
		setCMPBankEntity(bankEntity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup#
	 * setPolicyCapGroupID(long)
	 */
	public void setPolicyCapGroupID(long groupID) {
		setCMPPolicyCapGroupID(new Long(groupID));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup#
	 * setPolicyCapList(java.util.Collection)
	 */
	public void setPolicyCapArray(IPolicyCap[] policyCaps) {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup#
	 * setStockExchange(java.lang.String)
	 */
	public void setStockExchange(String stockExchange) {
		setCMPStockExchange(stockExchange);

	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param value object of IPolicyCapGroup
	 * @throws javax.ejb.CreateException on error creating the references
	 */
	public void ejbPostCreate(IPolicyCapGroup value) throws CreateException {
		try {
			IPolicyCap[] policyCaps = value.getPolicyCapArray();
			if (policyCaps != null) {
				for (int i = 0; i < policyCaps.length; i++) {
					EBPolicyCapLocal local = getEBPolicyCapLocalHome().create(policyCaps[i]);
					this.getPolicyCapsCMR().add(local);
				}
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CreateException("Caught unknown exception: " + e.toString());
		}

	}

	/**
	 * EJB callback method to clears the context of the bean.
	 */
	public void unsetEntityContext() {
		entityContext = null;
	}

	/**
	 * This method is called when the container picks this entity object and
	 * assigns it to a specific entity object. No implementation currently
	 * acquires any additional resources that it needs when it is in the ready
	 * state.
	 */
	public void ejbActivate() {
	}

	/**
	 * This method is called when the container diassociates the bean from the
	 * entity object identity and puts the instance back into the pool of
	 * available instances. No implementation is currently provided to release
	 * resources that should not be held while the instance is in the pool.
	 */
	public void ejbPassivate() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the bean's state with the state in the database.
	 * This method is called after the container has loaded the bean's state
	 * from the database.
	 */
	public void ejbLoad() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the state in the database with the state of the
	 * bean. This method is called before the container extracts the fields and
	 * writes them into the database.
	 */
	public void ejbStore() {
	}

	/**
	 * The container invokes this method in response to a client-invoked remove
	 * request. No implementation is currently provided for taking actions
	 * before the bean is removed from the database.
	 */
	public void ejbRemove() {
	}

	/**
	 * EJB callback method to set the context of the bean.
	 * 
	 * @param context the entity context.
	 */
	public void setEntityContext(EntityContext entityContext) throws EJBException, RemoteException {
		this.entityContext = entityContext;

	}

	/**
	 * Get the EJB Local Home interface of PolicyCap
	 * @return
	 * @throws PolicyCapException
	 */
	private EBPolicyCapLocalHome getEBPolicyCapLocalHome() throws PolicyCapException {

		EBPolicyCapLocalHome home = (EBPolicyCapLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_POLICY_CAP_LOCAL_JNDI, EBPolicyCapLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		else {
			throw new PolicyCapException("EBPolicyCapLocalHome is null");
		}
	}

	/**
	 * Check the version of this persistEntity with database version.
	 * @param policyCapGroup - IPolicyCapGroup
	 * @throws ConcurrentUpdateException if the entity version is different from
	 *         database version
	 */
	protected void checkVersionMismatch(IPolicyCapGroup policyCapGroup) throws ConcurrentUpdateException {

		if (getVersionTime() != policyCapGroup.getVersionTime()) {
			throw new ConcurrentUpdateException("Mismatch timestamp");
		}
	}

    public abstract void setVersionTime(long versionTime);

    public abstract long getVersionTime();
}
