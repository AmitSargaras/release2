/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/EBHedgePriceExtensionBean.java,v 1.3 2004/06/18 02:31:55 wltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/06/18 02:31:55 $ Tag: $Name: $
 */
public abstract class EBHedgePriceExtensionBean implements IHedgePriceExtension, EntityBean {

	private EntityContext context;

	private static final String[] EXCLUDE_METHOD = new String[] { "getExtensionID", "getCommonReferenceID" };

	public EBHedgePriceExtensionBean() {
	}

	public void setEntityContext(EntityContext context) throws EJBException {
		this.context = context;
	}

	public void unsetEntityContext() throws EJBException {
		this.context = null;
	}

	public Long ejbCreate(IHedgePriceExtension extension) throws CreateException {
		try {
			String newExtensionPK = (new SequenceManager())
					.getSeqNum(ICMSConstant.SEQUENCE_HEDGE_PRICE_EXTENSION, true);
			AccessorUtil.copyValue(extension, this, EXCLUDE_METHOD);
			setEBExtensionID(new Long(newExtensionPK));

			if (extension.getCommonReferenceID() == ICMSConstant.LONG_INVALID_VALUE) {
				setCommonReferenceID(getExtensionID());
			}
			else {
				// else maintain this reference id.
				setCommonReferenceID(extension.getCommonReferenceID());
			}
			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	public void ejbPostCreate(IHedgePriceExtension extension) throws CreateException {
	}

	public void ejbRemove() throws RemoveException, EJBException {
	}

	public void ejbActivate() throws EJBException {
	}

	public void ejbPassivate() throws EJBException {
	}

	public void ejbLoad() throws EJBException {
	}

	public void ejbStore() throws EJBException {
	}

	/**
	 * Gets the extension ID.
	 * 
	 * @return long - ID of this extension
	 */
	public long getExtensionID() {
		return getEBExtensionID().longValue();
	}

	/**
	 * Sets the extension ID.
	 * 
	 * @param extensionID - long repersenting the extension ID
	 */
	public void setExtensionID(long extensionID) {
		// TODO : consider if validation is required.
		setEBExtensionID((extensionID != ICMSConstant.LONG_INVALID_VALUE) ? new Long(extensionID) : null);
	}

	/**
	 * Gets the period unit used to specify the end date for this extension.
	 * 
	 * @return PeriodUnit
	 */
	public PeriodUnit getPeriodUnit() {
		return (getEBPeriodUnit() == null) ? null : PeriodUnit.valueOf(getEBPeriodUnit());
	}

	/**
	 * Sets the period unit used to specify the end date for this extension.
	 * 
	 * @param unit - PeriodUnit
	 */
	public void setPeriodUnit(PeriodUnit unit) {
		setEBPeriodUnit((unit == null) ? null : unit.getName());
	}

	/**
	 * Gets the count that indicates the number of period unit used to specify
	 * the end date for this extension.
	 * 
	 * @return long
	 */
	public long getPeriodUnitCount() {
		return (getEBPeriodUnitCount() == null) ? ICMSConstant.LONG_INVALID_VALUE : getEBPeriodUnitCount().longValue();
	}

	/**
	 * Sets the count that indicates the number of period unit used to specify
	 * the end date for this extension.
	 * 
	 * @param count - long
	 */
	public void setPeriodUnitCount(long count) {
		setEBPeriodUnitCount((count == ICMSConstant.LONG_INVALID_VALUE) ? null : new Long(count));
	}

	/**
	 * Gets the common reference ID. This ID will be used to identify which
	 * staging record corresponds to which actual records.
	 * 
	 * @return long representing the common reference ID
	 */
	public long getCommonReferenceID() {
		return getEBCommonReferenceID().longValue();
	}

	/**
	 * Sets the common reference ID. This ID will be used to identify which
	 * staging record correspods to which actual records.
	 * 
	 * @param commonRefID
	 */
	public void setCommonReferenceID(long commonRefID) {
		setEBCommonReferenceID((commonRefID != ICMSConstant.LONG_INVALID_VALUE) ? new Long(commonRefID) : null);
	}

	public abstract Long getEBExtensionID();

	public abstract void setEBExtensionID(Long extensionID);

	public abstract Long getEBCommonReferenceID();

	public abstract void setEBCommonReferenceID(Long commonReferenceID);

	public abstract Long getEBPeriodUnitCount();

	public abstract void setEBPeriodUnitCount(Long unitCount);

	public abstract String getEBPeriodUnit();

	public abstract void setEBPeriodUnit(String unitCode);

	public abstract void setStatus(String status);

	/**
	 * Get the hedged price extension business object.
	 * 
	 * @return hedged price extension
	 */
	public IHedgePriceExtension getValue() {
		OBHedgePriceExtension extension = new OBHedgePriceExtension();
		AccessorUtil.copyValue(this, extension);
		return extension;
	}

	/**
	 * Set the hedged price extension to this entity.
	 * 
	 * @param extension is of type IHedgePriceExtension
	 */
	public void setValue(IHedgePriceExtension extension) {
		AccessorUtil.copyValue(extension, this, EXCLUDE_METHOD);
	}

	/**
	 * Get hedge price extension local home.
	 * 
	 * @return EBHedgePriceExtensionLocalHome
	 */
	protected EBHedgePriceExtensionLocalHome getLocalHome() {
		EBHedgePriceExtensionLocalHome ejbHome = (EBHedgePriceExtensionLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_HEDGE_PRICE_EXTENSION_LOCAL_JNDI, EBHedgePriceExtensionLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBHedgePriceExtensionLocalHome is Null!");
		}

		return ejbHome;
	}

}
