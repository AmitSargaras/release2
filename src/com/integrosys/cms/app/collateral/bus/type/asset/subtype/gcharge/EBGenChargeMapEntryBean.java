/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/EBGenChargeMapEntryBean.java,v 1.3 2005/05/19 12:15:50 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.math.BigDecimal;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for asset of type charge.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/05/19 12:15:50 $ Tag: $Name: $
 */
public abstract class EBGenChargeMapEntryBean implements IGenChargeMapEntry, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during update to the equity. */
	protected static final String[] EXCLUDE_METHOD = new String[] { "getMapEntryID", "getRefID" };

	// todo : to populate the CCY in EBGenerageCharge
	protected static CurrencyCode cmsSecurityCCYCode = null;

	/**
	 * Get map entry id.
	 * 
	 * @return long
	 */
	public long getMapEntryID() {
		return getEBMapEntryID().longValue();
	}

	/**
	 * Set map entry id.
	 * 
	 * @param mapEntryID of type long
	 */
	public void setMapEntryID(long mapEntryID) {
		setEBMapEntryID(new Long(mapEntryID));
	}

	// weiling : to be phased out. replaced by recoverable amt in stock.
	/**
	 * Get coverage amount of this insurance against the general charge subtype
	 * in CMS Security Currency.
	 * 
	 * @return Amount - Coverage Amount
	 */
	/*
	 * public Amount getInsrCoverageAmount() { BigDecimal insrCoverageAmt =
	 * getEBInsrCoverageAmount(); return (insrCoverageAmt == null) ? null : new
	 * Amount(insrCoverageAmt, cmsSecurityCCYCode); }
	 */

	// weiling : to be phased out. replaced by recoverable amt in stock.
	/**
	 * Set coverage amount of this insurance against the general charge subtype
	 * in CMS Security Currency.
	 * 
	 * @param insrCoverageAmt - Amount
	 */
	/*
	 * public void setInsrCoverageAmount(Amount insrCoverageAmt) { if
	 * (insrCoverageAmt != null && insrCoverageAmt.getAmountAsBigDecimal() !=
	 * null) { setEBInsrCoverageAmount(insrCoverageAmt.getAmountAsBigDecimal());
	 * } }
	 */

	public abstract Long getEBMapEntryID();

	public abstract void setEBMapEntryID(Long eBMapEntryID);

	public abstract BigDecimal getEBInsrCoverageAmount();

	public abstract void setEBInsrCoverageAmount(BigDecimal insrCoverageAmt);

	public abstract void setStatus(String status);

	/*
	 * Get sequence name
	 * 
	 * @return String
	 */
	protected abstract String getSequenceName();

	/**
	 * Get map entry.
	 * 
	 * @return IGenChargeMapEntry
	 */
	public IGenChargeMapEntry getValue() {
		OBGenChargeMapEntry ob = new OBGenChargeMapEntry();
		AccessorUtil.copyValue(this, ob);
		return ob;
	}

	/**
	 * Set map entry
	 * 
	 * @param mapEntry of type IGenChargeMapEntry
	 */
	public void setValue(IGenChargeMapEntry mapEntry) {
		AccessorUtil.copyValue(mapEntry, this, EXCLUDE_METHOD);
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param mapEntry of type IGenChargeMapEntry
	 * @return primary key
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IGenChargeMapEntry mapEntry) throws CreateException {
		try {
			String mapEntryID = (new SequenceManager()).getSeqNum(getSequenceName(), true);
			AccessorUtil.copyValue(mapEntry, this, EXCLUDE_METHOD);
			setEBMapEntryID(new Long(mapEntryID));
			if (mapEntry.getRefID() == ICMSConstant.LONG_MIN_VALUE) {
				setRefID(getMapEntryID());
			}
			else {
				// else maintain this reference id.
				setRefID(mapEntry.getRefID());
			}
			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param mapEntry of type IGenChargeMapEntry
	 */
	public void ejbPostCreate(IGenChargeMapEntry mapEntry) {
	}

	/**
	 * EJB callback method to set the context of the bean.
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
}