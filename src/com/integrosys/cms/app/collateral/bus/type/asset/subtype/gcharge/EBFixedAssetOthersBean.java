/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/EBFixedAssetOthersBean.java,v 1.2 2005/03/30 14:03:22 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.Date;

/**
 * For EBFixedAssetOthersBean
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/03/30 14:03:22 $ Tag: $Name: $
 */
public abstract class EBFixedAssetOthersBean extends EBGeneralChargeSubTypeBean implements EntityBean,
		IFixedAssetOthers {

	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during create fixed asset/others. */
	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getAssetGCFixedAssetOthersID" };

	/** A list of methods to be excluded during update fixed asset/others. */
	private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getAssetGCFixedAssetOthersID" };

	public abstract Long getEBAssetGCFixedAssetOthersID();

	public abstract void setEBAssetGCFixedAssetOthersID(Long assetGCFixedAssetOthersID);

	public String getID() {
		return null;
	}

	public long getAssetGCFixedAssetOthersID() {
		return getEBAssetGCFixedAssetOthersID().longValue();
	}

	public void setAssetGCFixedAssetOthersID(long assetGCFixedAssetOthersID) {
		setEBAssetGCFixedAssetOthersID(new Long(assetGCFixedAssetOthersID));
	}

	/**
	 * Sets the fixed asset/others object.
	 * @param fao - IFixedAssetOthers
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(IFixedAssetOthers fao) throws ConcurrentUpdateException {
		AccessorUtil.copyValue(fao, this, EXCLUDE_METHOD_UPDATE);
	}

	/**
	 * Return a fixed asset/others object
	 * @return IFixedAssetOthers - the object containing the fixed asset/others
	 *         object
	 */
	public IFixedAssetOthers getValue() {
		OBFixedAssetOthers value = new OBFixedAssetOthers();
		AccessorUtil.copyValue(this, value, null);
		return value;
	}

	/**
	 * Soft delete of the fixed asset/others business object.
	 */
	public void deleteFixedAssetOthers(IFixedAssetOthers fao) throws ConcurrentUpdateException {
		setStatus(ICMSConstant.STATE_DELETED);
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param fao of type IFixedAssetOthers
	 * @throws javax.ejb.CreateException on error creating the entity object
	 */
	public Long ejbCreate(IFixedAssetOthers fao) throws CreateException {
		try {
			String assetGCFixedAssetOthersID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_ASSET_GC_FAO,
					true);
			AccessorUtil.copyValue(fao, this, EXCLUDE_METHOD_CREATE);
			setEBAssetGCFixedAssetOthersID(new Long(assetGCFixedAssetOthersID));
			setStatus(ICMSConstant.STATE_ACTIVE);
			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, e);
			throw new CreateException(e.toString());
		}
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param fao of type IFixedAssetOthers
	 * @throws CreateException on error creating the references
	 */
	public void ejbPostCreate(IFixedAssetOthers fao) throws CreateException {
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

    public abstract Date getValuationDate();

    public abstract Date getRevaluationDate();

    public abstract String getRevalFreqUnit();

    public abstract void setValuationDate(Date valuationDate);

    public abstract void setRevaluationDate(Date revaluationDate);

    public abstract void setRevalFreqUnit(String revalFreqUnit);

    public abstract String getAddress();

    public abstract void setAddress(String address);

    public abstract String getStatus();

    public abstract String getValuerName();

    public abstract void setValuerName(String valuerName);

    public abstract String getValuationCurrency();

    public abstract void setValuationCurrency(String valuationCurrency);

    public abstract String getFAOID();

    public abstract void setFAOID(String fxasstOthrID);

    public abstract String getDescription();

    public abstract void setDescription(String description);
}
