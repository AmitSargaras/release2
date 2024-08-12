package com.integrosys.cms.app.bridgingloan.bus;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.TypeConverter;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public abstract class EBPropertyTypeBean implements EntityBean, IPropertyType {

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD = new String[] { "getPropertyTypeID", "getProjectID", "getCommonRef" };

	/**
	 * Default Constructor
	 */
	public EBPropertyTypeBean() {
	}

	// ====================================
	// Get and Set Value
	// ====================================
	/**
	 * Retrieve an instance of IPropertyType object.
	 * @return IPropertyType business object
	 */
	public IPropertyType getValue() {
		IPropertyType value = new OBPropertyType();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	/**
	 * Sets the IPropertyType object.
	 * @param value of type IPropertyType
	 */
	public void setValue(IPropertyType value) {
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
	}

	// ========================
	// Getters
	// ========================
	public abstract Long getCMPProjectID();

	public abstract Long getCMPPropertyTypeID();

	public abstract Integer getCMPNoOfUnits();

	public abstract Long getCMPCommonRef();

	public abstract String getCMPIsDeleted();

	public long getProjectID() {
		return TypeConverter.convertToPrimitiveType(getCMPProjectID());
	}

	public long getPropertyTypeID() {
		return TypeConverter.convertToPrimitiveType(getCMPPropertyTypeID());
	}

	public int getNoOfUnits() {
		return TypeConverter.convertToPrimitiveType(getCMPNoOfUnits());
	}

	public long getCommonRef() {
		return TypeConverter.convertToPrimitiveType(getCMPCommonRef());
	}

	public boolean getIsDeletedInd() {
		return TypeConverter.convertStringToBooleanEquivalent(getCMPIsDeleted());
	}

	// ========================
	// Setters
	// ========================
	public abstract void setCMPProjectID(Long projectID);

	public abstract void setCMPPropertyTypeID(Long id);

	public abstract void setCMPNoOfUnits(Integer units);

	public abstract void setCMPCommonRef(Long commonRef);

	public abstract void setCMPIsDeleted(String isDeleted);

	public void setProjectID(long projectID) {
		setCMPProjectID(new Long(projectID));
	}

	public void setPropertyTypeID(long id) {
		setCMPPropertyTypeID(new Long(id));
	}

	public void setNoOfUnits(int units) {
		setCMPNoOfUnits(new Integer(units));
	}

	public void setCommonRef(long commonRef) {
		setCMPCommonRef(new Long(commonRef));
	}

	public void setIsDeletedInd(boolean isDeleted) {
		setCMPIsDeleted(TypeConverter.convertBooleanToStringEquivalent(isDeleted));
	}

	// ==============================
	// Standard Methods
	// ==============================
	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param object IFDR object to be created
	 * @throws javax.ejb.CreateException on error creating the entity object
	 */
	public Long ejbCreate(IPropertyType object) throws CreateException {
		if (object == null) {
			throw new CreateException("IPropertyType object to be created is null");
		}

		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_BL_PROPERTY_TYPE, true));
			setPropertyTypeID(pk);
			if (object.getCommonRef() == ICMSConstant.LONG_INVALID_VALUE) {
				setCommonRef(pk);
			}
			else {
				setCommonRef(object.getCommonRef());
			}

			AccessorUtil.copyValue(object, this, EXCLUDE_METHOD);
			return new Long(pk);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			e.printStackTrace();
			throw new CreateException(e.toString());
		}
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param value object of IPropertyType
	 * @throws javax.ejb.CreateException on error creating the references
	 */
	public void ejbPostCreate(IPropertyType value) throws CreateException {
	}

	/**
	 * EJB callback method to set the context of the bean.
	 * 
	 * @param context the entity context.
	 */
	public void setEntityContext(EntityContext context) {
		_context = context;
	}

	/**
	 * EJB callback method to clears the context of the bean.
	 */
	public void unsetEntityContext() {
		_context = null;
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

	// ==============================
	// DAO Methods
	// ==============================

}
