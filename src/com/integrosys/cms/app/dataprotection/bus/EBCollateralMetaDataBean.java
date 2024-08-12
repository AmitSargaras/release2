/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/dataprotection/bus/EBCollateralMetaDataBean.java,v 1.4 2006/03/17 07:20:10 hshii Exp $
 */
package com.integrosys.cms.app.dataprotection.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;

/**
 * Purpose: Entity bean implementation for collateral field meta data
 * 
 * @author $jtan$<br>
 * @version $revision$
 * @since $date$ Tag: $Name: $
 * 
 */
public abstract class EBCollateralMetaDataBean implements EntityBean {

	private EntityContext ctx;

	public static final String YES = "Y";

	// field name belonging to a subtype
	public abstract void setFieldName(String fname);

	public abstract String getFieldName();

	public abstract void setSecuritySubTypeId(String id);

	public abstract String getSecuritySubTypeId();

	// Is it SCC Updatable?
	public abstract void setIsUpdatable(String b);

	public abstract String getIsUpdatable();

	// the isUpdatable boolean only applicable country
	public abstract void setApplicableCountry(String applicableCountry);

	public abstract String getApplicableCountry();

	/**
	 * Constructs a value object that contains the subtype which the collateral
	 * belongs to, the fieldname and the update control
	 * @return CollateralMeta Data
	 */
	public ICollateralMetaData getCollateralMetaData() {
		OBCollateralMetaData OBData = new OBCollateralMetaData();
		OBCollateralSubType subtype = new OBCollateralSubType();
		subtype.setSubTypeCode(getSecuritySubTypeId());
		OBData.setSubtype(subtype);
		OBData.setSCCUpdatable(YES.equals(getIsUpdatable()));
		OBData.setFieldName(getFieldName());
		OBData.setApplicableCountry(getApplicableCountry());
		return OBData;
	}

	/**
	 * Create the meta data for a field found in subtype
	 * 
	 * @throws CreateException on error creating the limit charge
	 */
	public String ejbCreate() throws CreateException, EJBException {
		DefaultLogger.debug(this, "ejbCreate called");
		return null;
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @throws CreateException
	 */
	public void ejbPostCreate() throws CreateException {
		DefaultLogger.debug(this, "ejbPostCreate called");
	}

	/**
	 * EJB callback method to set the context of the bean.
	 * @param entityContext
	 * @throws EJBException
	 * @throws RemoteException
	 */
	public void setEntityContext(EntityContext entityContext) throws EJBException, RemoteException {
		DefaultLogger.debug(this, "EntityContext called");
		this.ctx = entityContext;
	}

	/**
	 * EJB callback method to clears the context of the bean.
	 * @throws EJBException
	 * @throws RemoteException
	 */
	public void unsetEntityContext() throws EJBException, RemoteException {
		DefaultLogger.debug(this, "UnsetEntityContext called");
		this.ctx = null;
	}

	/**
	 * The container invokes this method in response to a client-invoked remove
	 * request. No implementation is currently provided for taking actions
	 * before the bean is removed from the database.
	 */
	public void ejbRemove() throws RemoveException, EJBException, RemoteException {
		// DefaultLogger.debug(this, "ejbRemove called");
	}

	/**
	 * This method is called when the container picks this entity object and
	 * assigns it to a specific entity object. No implementation currently
	 * acquires any additional resources that it needs when it is in the ready
	 * state.
	 */
	public void ejbActivate() throws EJBException, RemoteException {
		// DefaultLogger.debug(this, "ejbActivate called");
	}

	/**
	 * This method is called when the container diassociates the bean from the
	 * entity object identity and puts the instance back into the pool of
	 * available instances. No implementation is currently provided to release
	 * resources that should not be held while the instance is in the pool.
	 */
	public void ejbPassivate() throws EJBException, RemoteException {
		// DefaultLogger.debug(this, "ejbPassivate called");
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the bean's state with the state in the database.
	 * This method is called after the container has loaded the bean's state
	 * from the database.
	 */
	public void ejbLoad() throws EJBException, RemoteException {
		// DefaultLogger.debug(this, "ejbLoad called");
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the state in the database with the state of the
	 * bean. This method is called before the container extracts the fields and
	 * writes them into the database.
	 */
	public void ejbStore() throws EJBException, RemoteException {
		// DefaultLogger.debug(this, "ejbStore called");
	}
}
