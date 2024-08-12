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
public abstract class EBSalesProceedsBean implements EntityBean, ISalesProceeds {

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD = new String[] { "getProceedsID", "getProjectID", "getCommonRef" };

	/**
	 * Default Constructor
	 */
	public EBSalesProceedsBean() {
	}

	// ====================================
	// Get and Set Value
	// ====================================
	/**
	 * Retrieve an instance of ISalesProceeds object.
	 * @return ISalesProceeds business object
	 */
	public ISalesProceeds getValue() {
		ISalesProceeds value = new OBSalesProceeds();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	/**
	 * Sets the ISalesProceeds object.
	 * @param value of type ISalesProceeds
	 */
	public void setValue(ISalesProceeds value) {
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
	}

	// ========================
	// Getters
	// ========================
	public abstract Long getCMPProceedsID();

	public abstract Long getCMPBuildUpID();

	public abstract Float getCMPPurposePercent();

	public abstract Double getCMPReceiveValue();

	public abstract String getCMPReceiveCurrency();

	public abstract Double getCMPDistributeValue();

	public abstract String getCMPDistributeCurrency();

	public abstract String getCMPIsToTL1();

	public abstract Double getCMPTL1Value();

	public abstract String getCMPTL1Currency();

	public abstract String getCMPIsToOD();

	public abstract Double getCMPODValue();

	public abstract String getCMPODCurrency();

	public abstract String getCMPIsToFDR();

	public abstract Double getCMPFDRValue();

	public abstract String getCMPFDRCurrency();

	public abstract String getCMPIsToHDA();

	public abstract Double getCMPHDAValue();

	public abstract String getCMPHDACurrency();

	public abstract String getCMPIsToOthers();

	public abstract Double getCMPOthersValue();

	public abstract String getCMPOthersCurrency();

	public abstract Long getCMPCommonRef();

	public abstract String getCMPIsDeleted();

	public long getProceedsID() {
		return TypeConverter.convertToPrimitiveType(getCMPProceedsID());
	}

	public long getBuildUpID() {
		return TypeConverter.convertToPrimitiveType(getCMPBuildUpID());
	}

	public float getPurposePercent() {
		return TypeConverter.convertToPrimitiveType(getCMPPurposePercent());
	}

	public Amount getReceiveAmount() {
		return TypeConverter.convertToAmount(getCMPReceiveValue(), getCMPReceiveCurrency());
	}

	public Amount getDistributeAmount() {
		return TypeConverter.convertToAmount(getCMPDistributeValue(), getCMPDistributeCurrency());
	}

	public boolean getIsToTL1() {
		return TypeConverter.convertStringToBooleanEquivalent(getCMPIsToTL1());
	}

	public Amount getTL1Amount() {
		return TypeConverter.convertToAmount(getCMPTL1Value(), getCMPTL1Currency());
	}

	public boolean getIsToOD() {
		return TypeConverter.convertStringToBooleanEquivalent(getCMPIsToOD());
	}

	public Amount getOdAmount() {
		return TypeConverter.convertToAmount(getCMPODValue(), getCMPODCurrency());
	}

	public boolean getIsToFDR() {
		return TypeConverter.convertStringToBooleanEquivalent(getCMPIsToFDR());
	}

	public Amount getFdrAmount() {
		return TypeConverter.convertToAmount(getCMPFDRValue(), getCMPFDRCurrency());
	}

	public boolean getIsToHDA() {
		return TypeConverter.convertStringToBooleanEquivalent(getCMPIsToHDA());
	}

	public Amount getHdaAmount() {
		return TypeConverter.convertToAmount(getCMPHDAValue(), getCMPHDACurrency());
	}

	public boolean getIsToOthers() {
		return TypeConverter.convertStringToBooleanEquivalent(getCMPIsToOthers());
	}

	public Amount getOthersAmount() {
		return TypeConverter.convertToAmount(getCMPOthersValue(), getCMPOthersCurrency());
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
	public abstract void setCMPProceedsID(Long id);

	public abstract void setCMPBuildUpID(Long id);

	public abstract void setCMPPurposePercent(Float percent);

	public abstract void setCMPReceiveValue(Double value);

	public abstract void setCMPReceiveCurrency(String currency);

	public abstract void setCMPDistributeValue(Double value);

	public abstract void setCMPDistributeCurrency(String currency);

	public abstract void setCMPIsToTL1(String flag);

	public abstract void setCMPTL1Value(Double value);

	public abstract void setCMPTL1Currency(String currency);

	public abstract void setCMPIsToOD(String flag);

	public abstract void setCMPODValue(Double value);

	public abstract void setCMPODCurrency(String currency);

	public abstract void setCMPIsToFDR(String flag);

	public abstract void setCMPFDRValue(Double value);

	public abstract void setCMPFDRCurrency(String currency);

	public abstract void setCMPIsToHDA(String flag);

	public abstract void setCMPHDAValue(Double value);

	public abstract void setCMPHDACurrency(String currency);

	public abstract void setCMPIsToOthers(String flag);

	public abstract void setCMPOthersValue(Double value);

	public abstract void setCMPOthersCurrency(String currency);

	public abstract void setCMPCommonRef(Long commonRef);

	public abstract void setCMPIsDeleted(String isDeleted);

	public void setProceedsID(long id) {
		setCMPProceedsID(new Long(id));
	}

	public void setBuildUpID(long id) {
		setCMPBuildUpID(new Long(id));
	}

	public void setPurposePercent(float percent) {
		setCMPPurposePercent(new Float(percent));
	}

	public void setReceiveAmount(Amount amt) {
		if (amt != null) {
			setCMPReceiveValue(new Double(amt.getAmount()));
			setCMPReceiveCurrency(amt.getCurrencyCode());
		}
		else {
			setCMPReceiveValue(null);
			setCMPReceiveCurrency(null);
		}
	}

	public void setDistributeAmount(Amount amt) {
		if (amt != null) {
			setCMPDistributeValue(new Double(amt.getAmount()));
			setCMPDistributeCurrency(amt.getCurrencyCode());
		}
		else {
			setCMPDistributeValue(null);
			setCMPDistributeCurrency(null);
		}
	}

	public void setIsToTL1(boolean flag) {
		setCMPIsToTL1(TypeConverter.convertBooleanToStringEquivalent(flag));
	}

	public void setTL1Amount(Amount amt) {
		if (amt != null) {
			setCMPTL1Value(new Double(amt.getAmount()));
			setCMPTL1Currency(amt.getCurrencyCode());
		}
		else {
			setCMPTL1Value(null);
			setCMPTL1Currency(null);
		}
	}

	public void setIsToOD(boolean flag) {
		setCMPIsToOD(TypeConverter.convertBooleanToStringEquivalent(flag));
	}

	public void setOdAmount(Amount amt) {
		if (amt != null) {
			setCMPODValue(new Double(amt.getAmount()));
			setCMPODCurrency(amt.getCurrencyCode());
		}
		else {
			setCMPODValue(null);
			setCMPODCurrency(null);
		}
	}

	public void setIsToFDR(boolean flag) {
		setCMPIsToFDR(TypeConverter.convertBooleanToStringEquivalent(flag));
	}

	public void setFdrAmount(Amount amt) {
		if (amt != null) {
			setCMPFDRValue(new Double(amt.getAmount()));
			setCMPFDRCurrency(amt.getCurrencyCode());
		}
		else {
			setCMPFDRValue(null);
			setCMPFDRCurrency(null);
		}
	}

	public void setIsToHDA(boolean flag) {
		setCMPIsToHDA(TypeConverter.convertBooleanToStringEquivalent(flag));
	}

	public void setHdaAmount(Amount amt) {
		if (amt != null) {
			setCMPHDAValue(new Double(amt.getAmount()));
			setCMPHDACurrency(amt.getCurrencyCode());
		}
		else {
			setCMPHDAValue(null);
			setCMPHDACurrency(null);
		}
	}

	public void setIsToOthers(boolean flag) {
		setCMPIsToOthers(TypeConverter.convertBooleanToStringEquivalent(flag));
	}

	public void setOthersAmount(Amount amt) {
		if (amt != null) {
			setCMPOthersValue(new Double(amt.getAmount()));
			setCMPOthersCurrency(amt.getCurrencyCode());
		}
		else {
			setCMPOthersValue(null);
			setCMPOthersCurrency(null);
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
	 * @param object IFDR object to be created
	 * @throws javax.ejb.CreateException on error creating the entity object
	 */
	public Long ejbCreate(ISalesProceeds object) throws CreateException {
		if (object == null) {
			throw new CreateException("ISalesProceeds object to be created is null");
		}

		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_BL_SALES_PROCEEDS, true));
			setProceedsID(pk);
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
	 * @param value object of ISalesProceeds
	 * @throws javax.ejb.CreateException on error creating the references
	 */
	public void ejbPostCreate(ISalesProceeds value) throws CreateException {
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
