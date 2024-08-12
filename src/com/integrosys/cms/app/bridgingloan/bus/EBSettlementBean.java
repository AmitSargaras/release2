package com.integrosys.cms.app.bridgingloan.bus;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.currency.Amount;
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
public abstract class EBSettlementBean implements EntityBean, ISettlement {

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD = new String[] { "getSettlementID", "getProjectID", "getCommonRef" };

	/**
	 * Default Constructor
	 */
	public EBSettlementBean() {
	}

	// ====================================
	// Get and Set Value
	// ====================================
	/**
	 * Retrieve an instance of ISettlement object.
	 * @return ISettlement business object
	 */
	public ISettlement getValue() {
		ISettlement value = new OBSettlement();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	/**
	 * Sets the ISettlement object.
	 * @param value of type ISettlement
	 */
	public void setValue(ISettlement value) {
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
	}

	// ========================
	// Getters
	// ========================
	public abstract Long getCMPSettlementID();

	public abstract Long getCMPProjectID();

	public abstract Double getCMPSettledValue();

	public abstract String getCMPSettledCurrency();

	public abstract Double getCMPOutstandingValue();

	public abstract String getCMPOutstandingCurrency();

	public abstract Long getCMPCommonRef();

	public abstract String getCMPIsDeleted();

	public long getSettlementID() {
		return TypeConverter.convertToPrimitiveType(getCMPSettlementID());
	}

	public long getProjectID() {
		return TypeConverter.convertToPrimitiveType(getCMPProjectID());
	}

	public Amount getSettledAmount() {
		return TypeConverter.convertToAmount(getCMPSettledValue(), getCMPSettledCurrency());
	}

	public Amount getOutstandingAmount() {
		return TypeConverter.convertToAmount(getCMPOutstandingValue(), getCMPOutstandingCurrency());
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
	public abstract void setCMPSettlementID(Long id);

	public abstract void setCMPProjectID(Long id);

	public abstract void setCMPSettledValue(Double value);

	public abstract void setCMPSettledCurrency(String currency);

	public abstract void setCMPOutstandingValue(Double value);

	public abstract void setCMPOutstandingCurrency(String currency);

	public abstract void setCMPCommonRef(Long commonRef);

	public abstract void setCMPIsDeleted(String isDeleted);

	public void setSettlementID(long id) {
		setCMPSettlementID(new Long(id));
	}

	public void setProjectID(long id) {
		setCMPProjectID(new Long(id));
	}

	public void setSettledAmount(Amount amt) {
		if (amt != null) {
			setCMPSettledValue(new Double(amt.getAmount()));
			setCMPSettledCurrency(amt.getCurrencyCode());
		}
		else {
			setCMPSettledValue(null);
			setCMPSettledCurrency(null);
		}
	}

	public void setOutstandingAmount(Amount amt) {
		if (amt != null) {
			setCMPOutstandingValue(new Double(amt.getAmount()));
			setCMPOutstandingCurrency(amt.getCurrencyCode());
		}
		else {
			setCMPOutstandingValue(null);
			setCMPOutstandingCurrency(null);
		}
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
	 * @param object ISettlement object to be created
	 * @throws javax.ejb.CreateException on error creating the entity object
	 */
	public Long ejbCreate(ISettlement object) throws CreateException {
		if (object == null) {
			throw new CreateException("ISettlement object to be created is null");
		}

		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_BL_SETTLEMENT, true));
			setSettlementID(pk);
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
			throw new CreateException(e.toString());
		}
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param value object of ISettlement
	 * @throws javax.ejb.CreateException on error creating the references
	 */
	public void ejbPostCreate(ISettlement value) throws CreateException {
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
