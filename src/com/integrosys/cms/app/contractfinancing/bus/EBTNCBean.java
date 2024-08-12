package com.integrosys.cms.app.contractfinancing.bus;

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
public abstract class EBTNCBean implements EntityBean, ITNC {

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD = new String[] { "getTncID", "getContractID", "getCommonRef" };

	// ====================================
	// Get and Set Value
	// ====================================
	/**
	 * Retrieve an instance of terms and conditions object.
	 * 
	 * @return terms and conditions business object
	 */
	public ITNC getValue() {
		ITNC value = new OBTNC();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	/**
	 * Sets the terms and conditions object.
	 * 
	 * @param value of type ITNC
	 * @throws ContractFinancingException on error
	 */
	public void setValue(ITNC value) throws ContractFinancingException {
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
	}

	// ========================
	// Getters
	// ========================
	public abstract Long getCMPTncID();

	public abstract Long getCMPContractID();

	public abstract Long getCMPCommonRef();

	public abstract String getCMPIsDeleted();

	public long getTncID() {
		return TypeConverter.convertToPrimitiveType(getCMPTncID());
	}

	public long getContractID() {
		return TypeConverter.convertToPrimitiveType(getCMPContractID());
	}

	public long getCommonRef() {
		return TypeConverter.convertToPrimitiveType(getCMPCommonRef());
	}

	public boolean getIsDeleted() {
		return TypeConverter.convertStringToBooleanEquivalent(getCMPIsDeleted());
	}

	// ========================
	// Setters
	// ========================
	public abstract void setCMPTncID(Long tncID);

	public abstract void setCMPContractID(Long contractID);

	public abstract void setCMPCommonRef(Long commonRef);

	public abstract void setCMPIsDeleted(String isDeleted);

	public void setTncID(long id) {
		setCMPTncID(new Long(id));
	}

	public void setContractID(long contractID) {
		setCMPContractID(new Long(contractID));
	}

	public void setCommonRef(long commonRef) {
		setCMPCommonRef(new Long(commonRef));
	}

	public void setIsDeleted(boolean isDeleted) {
		setCMPIsDeleted(TypeConverter.convertBooleanToStringEquivalent(isDeleted));
	}

	// ==============================
	// Standard Methods
	// ==============================
	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param object ITNC object to be created
	 * @return pk
	 * @throws javax.ejb.CreateException on error creating the entity object
	 */
	public Long ejbCreate(ITNC object) throws CreateException {
		if (object == null) {
			throw new CreateException("ITNC object to be created is null");
		}

		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_CF_TNC, true));
			setTncID(pk);
			if (object.getCommonRef() == ICMSConstant.LONG_INVALID_VALUE) {
				setCommonRef(pk);
			}
			else {
				setCommonRef(object.getCommonRef());
			}

			AccessorUtil.copyValue(object, this, EXCLUDE_METHOD);
			// need to copy the CMP Methods?
			return new Long(pk);
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
	 * @param value object of ITNC
	 * @throws javax.ejb.CreateException on error creating the references
	 */
	public void ejbPostCreate(ITNC value) throws CreateException {
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
