package com.integrosys.cms.app.chktemplate.bus;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: Cynthia Date: Aug 8, 2008 Time: 4:13:11 PM To
 * change this template use File | Settings | File Templates.
 */
public abstract class EBDynamicPropertyBean implements EntityBean, IDynamicProperty {

	private static final long serialVersionUID = -8543052166543933769L;

	private static final String[] EXCLUDE_METHOD = new String[] { "getPropertyID", "getReferenceID" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */

	public EBDynamicPropertyBean() {
	}

	public abstract String getPropertyCategory();

	public abstract void setPropertyCategory(String category);

	public abstract String getPropertyValue();

	public abstract void setPropertyValue(String propertyValue);

	public abstract String getStatus();

	public abstract void setStatus(String status);

	// ************** Abstract methods ************
	public abstract Long getCMPPropertyID();

	public abstract Long getCMPDocumentItemID();

	public abstract Long getCMPPropertySetupID();

	public abstract Long getCMPReferenceID();

	public abstract void setCMPPropertyID(Long id);

	public abstract void setCMPDocumentItemID(Long id);

	public abstract void setCMPPropertySetupID(Long id);

	public abstract void setCMPReferenceID(Long id);

	// ************* Non-persistent methods ***********

	public long getPropertyID() {
		if (getCMPPropertyID() != null) {
			return getCMPPropertyID().longValue();
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	public long getPropertySetupID() {
		if (getCMPPropertySetupID() != null) {
			return getCMPPropertySetupID().longValue();
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	public long getReferenceID() {
		if (getCMPReferenceID() != null) {
			return getCMPReferenceID().longValue();
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	public void setPropertyID(long propertyID) {
		setCMPPropertyID(new Long(propertyID));
	}

	public void setPropertySetupID(long propertySetupID) {
		setCMPPropertySetupID(new Long(propertySetupID));
	}

	public void setReferenceID(long referenceID) {
		setCMPReferenceID(new Long(referenceID));
	}

	// / *****************************************************
	/**
	 * Create the captured dynamic property value
	 * @param templateItemID - ID of the document item that this dynamic
	 *        property belongs to (only used in local)
	 * @param dynamicProperty - the dynamic property value
	 * @return Long - the dynamic property id (primary key)
	 * @throws javax.ejb.CreateException on error
	 */
	public Long ejbCreate(Long templateItemID, IDynamicProperty dynamicProperty) throws CreateException {
		if (dynamicProperty == null) {
			throw new CreateException("IDynamicProperty is null!");
		}
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			DefaultLogger.debug(this, "PK: " + pk);
			AccessorUtil.copyValue(dynamicProperty, this, EXCLUDE_METHOD);
			setPropertyID(pk);
			setReferenceID(ICMSConstant.LONG_INVALID_VALUE == dynamicProperty.getReferenceID() ? pk : dynamicProperty
					.getReferenceID());
			return new Long(pk);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new CreateException("Exception at ejbCreate: " + ex.toString());
		}

	}

	/**
	 * Post-Create a record
	 * 
	 * @param dynamicProperty - the dynamic property
	 */
	public void ejbPostCreate(Long docItemID, IDynamicProperty dynamicProperty) {
	}

	// ************************************************************************
	/**
	 * EJB callback method
	 */
	public void ejbActivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbPassivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbLoad() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbStore() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbRemove() {
	}

	/**
	 * EJB Callback Method
	 */
	public void setEntityContext(EntityContext ctx) {
		_context = ctx;
	}

	/**
	 * EJB Callback Method
	 */
	public void unsetEntityContext() {
		_context = null;
	}

	// *****************************************************
	/**
	 * Return the Interface representation of this object
	 * @return IDynamicProperty
	 */
	public IDynamicProperty getValue() {
		OBDynamicProperty value = new OBDynamicProperty();
		value.setPropertyCategory(getPropertyCategory());
		value.setPropertyID(getPropertyID());
		value.setPropertySetupID(getPropertySetupID());
		value.setPropertyValue(getPropertyValue());
		value.setReferenceID(getReferenceID());
		value.setStatus(getStatus());

		return value;
	}

	/**
	 * Persist a dynamic property information
	 * @param item - IDynamicProperty
	 */
	public void setValue(IDynamicProperty item) {
		AccessorUtil.copyValue(item, this, EXCLUDE_METHOD);
	}

	/**
	 * Get the name of the sequence to be used for the item id
	 * @return String - the name of the sequence
	 */
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_DOC_DYNAMIC_PROP;
	}

}
