/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/dataprotection/proxy/SBDataProtectionBean.java,v 1.3 2005/11/17 03:53:52 lyng Exp $
 */
package com.integrosys.cms.app.dataprotection.proxy;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.dataprotection.bus.EBCollateralMetaData;
import com.integrosys.cms.app.dataprotection.bus.EBCollateralMetaDataHome;
import com.integrosys.cms.app.dataprotection.bus.ICollateralMetaData;
import com.integrosys.cms.app.dataprotection.bus.IDataAccessProfile;

/**
 * Purpose: Session Bean implementation for data protection measures
 * 
 * @author $jtan$<br>
 * @version $revision$
 * @since $date$ Tag: $Name: $
 * 
 */

public class SBDataProtectionBean extends AbstractDataProtectionProxy implements SessionBean {

	private SessionContext ctx = null;

	public ICollateralMetaData[] getCollateralMetaDataBySubType(String type) {

		try {
			EBCollateralMetaDataHome home = getProxy();
			Collection c = home.findBySubType(type);
			ICollateralMetaData[] results = new ICollateralMetaData[c.size()];
			ArrayList list = new ArrayList(c.size());
			ICollateralMetaData d;

			for (Iterator i = c.iterator(); i.hasNext();) {
				d = ((EBCollateralMetaData) i.next()).getCollateralMetaData();
				list.add(d);
			}

			results = (ICollateralMetaData[]) list.toArray(results);
			return results;

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Error retrieving collateral data");
			return null;
			// @TODO: Exception Handling
		}
	}

	/**
	 * Get data access profile given its module type, subtype, and user role
	 * type.
	 * 
	 * @param type module type
	 * @param subType module subtype
	 * @param roleType user role type
	 * @return a list of IDataAccessProfile
	 */
	protected IDataAccessProfile[] getCachedDAPList(String type, String subType, long roleType) {
		return null; // no implementation at ejb level.
	}

	/**
	 * Called by the container to create a session bean instance. Its parameters
	 * typically contain the information the client uses to customize the bean
	 * instance for its use. It requires a matching pair in the bean class and
	 * its home interface.
	 */
	public void ejbCreate() {
		DefaultLogger.debug(this, "ejbCreate called");
	}

	/**
	 * Set the associated session context. The container calls this method after
	 * the instance creation. The enterprise bean instance should store the
	 * reference to the context object in an instance variable. This method is
	 * called with no transaction context.
	 */
	public void setSessionContext(SessionContext sessionContext) throws EJBException, RemoteException {
		DefaultLogger.debug(this, "setSessionContext called");
		this.ctx = sessionContext;
	}

	/**
	 * A container invokes this method before it ends the life of the session
	 * object. This happens as a result of a client's invoking a remove
	 * operation, or when a container decides to terminate the session object
	 * after a timeout. This method is called with no transaction context.
	 */
	public void ejbRemove() throws EJBException, RemoteException {
		DefaultLogger.debug(this, "ejbRemove called");
	}

	/**
	 * The activate method is called when the instance is activated from its
	 * 'passive' state. The instance should acquire any resource that it has
	 * released earlier in the ejbPassivate() method. This method is called with
	 * no transaction context.
	 */
	public void ejbActivate() throws EJBException, RemoteException {
		DefaultLogger.debug(this, "ejbActivate called");
	}

	/**
	 * The passivate method is called before the instance enters the 'passive'
	 * state. The instance should release any resources that it can re-acquire
	 * later in the ejbActivate() method. After the passivate method completes,
	 * the instance must be in a state that allows the container to use the Java
	 * Serialization protocol to externalize and store away the instance's
	 * state. This method is called with no transaction context.
	 */
	public void ejbPassivate() throws EJBException, RemoteException {
		DefaultLogger.debug(this, "ejbPassivate called");
	}

	/**
	 * Helper method to home interface of Collateral MetaData Entity bean.
	 * @return EJBHome for EBCollateralMetaDataBean
	 * @throws Exception
	 */
	private EBCollateralMetaDataHome getProxy() throws Exception {
		DefaultLogger.debug(this, "Entering getProxy");
		EBCollateralMetaDataHome proxy = (EBCollateralMetaDataHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_COLLATERAL_META_DATA_HOME, EBCollateralMetaDataHome.class.getName());

		if (proxy == null) {
			throw new Exception("EBCollateralMetaData is null!");
		}
		return proxy;
	}

}
