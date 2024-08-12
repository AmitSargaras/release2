/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralPledgorBean.java,v 1.8 2003/07/24 02:22:59 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for collateral pledgor entity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2003/07/24 02:22:59 $ Tag: $Name: $
 */
public abstract class EBCollateralPledgorBean extends OBPledgor implements ICollateralPledgor, EntityBean {

	private static final long serialVersionUID = 3526942129394408058L;

	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during update to the pledgor. */
	private static final String[] EXCLUDE_METHOD = new String[] { "getMapID" };

	/**
	 * Get security pledgor mapping id.
	 * 
	 * @return long
	 */
	public long getMapID() {
		return getEBMapID().longValue();
	}

	/**
	 * Set security pledgor mapping id.
	 * 
	 * @param mapID of type long
	 */
	public void setMapID(long mapID) {
		setEBMapID(new Long(mapID));
	}

	public abstract Long getEBMapID();

	public abstract void setEBMapID(Long eBMapID);

	public abstract EBPledgorLocal getPledgorCMR();

	public abstract void setPledgorCMR(EBPledgorLocal pledgorCMR);

	/**
	 * Get the collateral pledgor of this entity.
	 * 
	 * @return collateral pledgor
	 */
	public ICollateralPledgor getValue() {
		IPledgor pledgor = getPledgorCMR().getValue();
		OBCollateralPledgor colPledgor = new OBCollateralPledgor();
		AccessorUtil.copyValue(this, colPledgor);
		AccessorUtil.copyValue(pledgor, colPledgor);
		return colPledgor;
	}

	/**
	 * Set the collateral pledgor.
	 * 
	 * @param colPledgor a person who pledges the collateral
	 */
	public void setValue(ICollateralPledgor colPledgor) {
		AccessorUtil.copyValue(colPledgor, this, EXCLUDE_METHOD);
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param colPledgor of type ICollateralPledgor
	 * @return collateral pledgor primary key
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(ICollateralPledgor colPledgor) throws CreateException {
		try {
			String mapID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_PLEDGOR, true);
			AccessorUtil.copyValue(colPledgor, this);
			setEBMapID(new Long(mapID));

			if (getSCIPledgorID() <= 0) {
				setSCIPledgorID(Long.parseLong(mapID));
			}

            if (getSPMID() <= 0) {
                setSPMID(Long.parseLong(mapID));
            }

			return null;
		}
		catch (Exception e) {
			CreateException ce = new CreateException("failed to create collateral pledgor map, pledgor cif ["
					+ colPledgor.getLegalID() + "]");
			ce.initCause(e);
			throw ce;
		}
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param colPledgor of type ICollateralPledgor
	 */
	public void ejbPostCreate(ICollateralPledgor colPledgor) {
		try {
			setPledgorRef(colPledgor);
		}
		catch (Exception e) {
			throw new EJBException("failed to create pledgor, pledgor cif [" + colPledgor.getLegalID() + "]", e);
		}

	}

	/**
	 * Set pledgor.
	 *
	 * @param pledgor of type IPledgor
	 * @throws FinderException on error finding the pledgor information
	 */
	protected void setPledgorRef(IPledgor pledgor) throws CreateException {
		EBPledgorLocalHome ejbHome = getEBPledgorLocalHome();
		try {
            //Andy Wong, 14 July 2009: search existing pledgor by CIF, dont insert pledgor detail table, was PK previously
            EBPledgorLocal theEjb = ejbHome.findByLegalID(pledgor.getLegalID());
            setPledgorCMR(theEjb);
		} catch (FinderException e) {
			DefaultLogger.warn(this, "failed to find pledgor using pledgor id [" + pledgor.getPledgorID()
					+ "], attempt to create pledgor");
            EBPledgorLocal pledgorLocal = ejbHome.create(pledgor);
			setPledgorCMR(pledgorLocal);
		}
	}

	protected EBPledgorLocalHome getEBPledgorLocalHome() {
		EBPledgorLocalHome ejbHome = (EBPledgorLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_PLEDGOR_LOCAL_JNDI, EBPledgorLocalHome.class.getName());
		if (ejbHome == null) {
			throw new EJBException("failed to find pledgor local home using jndi name ["
					+ ICMSJNDIConstant.EB_PLEDGOR_LOCAL_JNDI + "]");
		}
		return ejbHome;
	}

	/**
	 * EJB callback method to set the context of the bean.
	 * 
	 * @param context the entity context.
	 */
	public void setEntityContext(EntityContext context) {
		this.context = context;
	}

	/**
	 * EJB callback method to clears the context of the bean.
	 */
	public void unsetEntityContext() {
		this.context = null;
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

	public abstract long getSCIPledgorID();

	public abstract void setSCIPledgorID(long sciPledgorID);

	public abstract String getSCISecID();

	public abstract void setSCISecID(String sciSecID);

	public abstract long getSCIMapSysGenID();

	public abstract void setSCIMapSysGenID(long sciMapSysGenID);

	public abstract String getSCIPledgorMapStatus();

	public abstract void setSCIPledgorMapStatus(String sciPledgorMapStatus);

	public abstract String getPledgorRelnshipCode();

	public abstract void setPledgorRelnshipCode(String value);

	public abstract String getPledgorRelnship();

	public abstract void setPledgorRelnship(String value);

	public abstract String getSourceID();

	public abstract void setSourceID(String value);

    public abstract long getSPMID();

    public abstract void setSPMID(long sPMID);

}