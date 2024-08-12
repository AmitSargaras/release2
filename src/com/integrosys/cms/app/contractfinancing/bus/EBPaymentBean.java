package com.integrosys.cms.app.contractfinancing.bus;

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
public abstract class EBPaymentBean implements EntityBean, IPayment {

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD = new String[] { "getPaymentID", "getContractID", "getCommonRef" };

	// /**
	// * Default Constructor
	// */
	// protected EBPaymentBean() {
	// }

	// ====================================
	// Get and Set Value
	// ====================================
	/**
	 * Retrieve an instance of payment object.
	 * @return payment business object
	 */
	public IPayment getValue() {
		IPayment value = new OBPayment();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	/**
	 * Sets the payment object.
	 * @param value of type IPayment
	 * @throws ContractFinancingException
	 */
	public void setValue(IPayment value) throws ContractFinancingException {
		DefaultLogger.debug(this, ">>>>>>> 5. In EBPaymentBean.setValue");
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
	}

	// ========================
	// Getters
	// ========================
	public abstract Long getCMPPaymentID();

	public abstract Long getCMPAdvanceID();

	public abstract Double getCMPReceiveValue();

	public abstract String getCMPReceiveCurrency();

	public abstract Double getCMPDistributeValue();

	public abstract String getCMPDistributeCurrency();

	public abstract String getCMPIsToFDR();

	public abstract Double getCMPFDRValue();

	public abstract String getCMPFDRCurrency();

	public abstract String getCMPIsToRepo();

	public abstract Double getCMPRepoValue();

	public abstract String getCMPRepoCurrency();

	public abstract String getCMPIsToAPG();

	public abstract Double getCMPAPGValue();

	public abstract String getCMPAPGCurrency();

	public abstract String getCMPIsToTL1();

	public abstract Double getCMPTL1Value();

	public abstract String getCMPTL1Currency();

	public abstract String getCMPIsToTL2();

	public abstract Double getCMPTL2Value();

	public abstract String getCMPTL2Currency();

	public abstract String getCMPIsToTL3();

	public abstract Double getCMPTL3Value();

	public abstract String getCMPTL3Currency();

	public abstract String getCMPIsToBC();

	public abstract Double getCMPBCValue();

	public abstract String getCMPBCCurrency();

	public abstract String getCMPIsToCollectionAccount();

	public abstract Double getCMPCollectionAccountValue();

	public abstract String getCMPCollectionAccountCurrency();

	public abstract String getCMPIsToOthers();

	public abstract Double getCMPOthersValue();

	public abstract String getCMPOthersCurrency();

	public abstract Long getCMPCommonRef();

	public abstract String getCMPIsDeleted();

	public long getPaymentID() {
		return TypeConverter.convertToPrimitiveType(getCMPPaymentID());
	}

	public long getAdvanceID() {
		return TypeConverter.convertToPrimitiveType(getCMPAdvanceID());
	}

	public Amount getReceiveAmount() {
		return TypeConverter.convertToAmount(getCMPReceiveValue(), getCMPReceiveCurrency());
	}

	public Amount getDistributeAmount() {
		return TypeConverter.convertToAmount(getCMPDistributeValue(), getCMPDistributeCurrency());
	}

	public boolean getIsToFDR() {
		return TypeConverter.convertStringToBooleanEquivalent(getCMPIsToFDR());
	}

	public Amount getFDRAmount() {
		return TypeConverter.convertToAmount(getCMPFDRValue(), getCMPFDRCurrency());
	}

	public boolean getIsToRepo() {
		return TypeConverter.convertStringToBooleanEquivalent(getCMPIsToRepo());
	}

	public Amount getRepoAmount() {
		return TypeConverter.convertToAmount(getCMPRepoValue(), getCMPRepoCurrency());
	}

	public boolean getIsToAPG() {
		return TypeConverter.convertStringToBooleanEquivalent(getCMPIsToAPG());
	}

	public Amount getAPGAmount() {
		return TypeConverter.convertToAmount(getCMPAPGValue(), getCMPAPGCurrency());
	}

	public boolean getIsToTL1() {
		return TypeConverter.convertStringToBooleanEquivalent(getCMPIsToTL1());
	}

	public Amount getTL1Amount() {
		return TypeConverter.convertToAmount(getCMPTL1Value(), getCMPTL1Currency());
	}

	public boolean getIsToTL2() {
		return TypeConverter.convertStringToBooleanEquivalent(getCMPIsToTL2());
	}

	public Amount getTL2Amount() {
		return TypeConverter.convertToAmount(getCMPTL2Value(), getCMPTL2Currency());
	}

	public boolean getIsToTL3() {
		return TypeConverter.convertStringToBooleanEquivalent(getCMPIsToTL3());
	}

	public Amount getTL3Amount() {
		return TypeConverter.convertToAmount(getCMPTL3Value(), getCMPTL3Currency());
	}

	public boolean getIsToBC() {
		return TypeConverter.convertStringToBooleanEquivalent(getCMPIsToBC());
	}

	public Amount getBCAmount() {
		return TypeConverter.convertToAmount(getCMPBCValue(), getCMPBCCurrency());
	}

	public boolean getIsToCollectionAccount() {
		return TypeConverter.convertStringToBooleanEquivalent(getCMPIsToCollectionAccount());
	}

	public Amount getCollectionAccountAmount() {
		return TypeConverter.convertToAmount(getCMPCollectionAccountValue(), getCMPCollectionAccountCurrency());
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

	public boolean getIsDeleted() {
		return TypeConverter.convertStringToBooleanEquivalent(getCMPIsDeleted());
	}

	// ========================
	// Setters
	// ========================
	public abstract void setCMPPaymentID(Long id);

	public abstract void setCMPAdvanceID(Long id);

	public abstract void setCMPReceiveValue(Double value);

	public abstract void setCMPReceiveCurrency(String currency);

	public abstract void setCMPDistributeValue(Double value);

	public abstract void setCMPDistributeCurrency(String currency);

	public abstract void setCMPIsToFDR(String flag);

	public abstract void setCMPFDRValue(Double value);

	public abstract void setCMPFDRCurrency(String currency);

	public abstract void setCMPIsToRepo(String flag);

	public abstract void setCMPRepoValue(Double value);

	public abstract void setCMPRepoCurrency(String currency);

	public abstract void setCMPIsToAPG(String flag);

	public abstract void setCMPAPGValue(Double value);

	public abstract void setCMPAPGCurrency(String currency);

	public abstract void setCMPIsToTL1(String flag);

	public abstract void setCMPTL1Value(Double value);

	public abstract void setCMPTL1Currency(String currency);

	public abstract void setCMPIsToTL2(String flag);

	public abstract void setCMPTL2Value(Double value);

	public abstract void setCMPTL2Currency(String currency);

	public abstract void setCMPIsToTL3(String flag);

	public abstract void setCMPTL3Value(Double value);

	public abstract void setCMPTL3Currency(String currency);

	public abstract void setCMPIsToBC(String flag);

	public abstract void setCMPBCValue(Double value);

	public abstract void setCMPBCCurrency(String currency);

	public abstract void setCMPIsToCollectionAccount(String flag);

	public abstract void setCMPCollectionAccountValue(Double value);

	public abstract void setCMPCollectionAccountCurrency(String currency);

	public abstract void setCMPIsToOthers(String flag);

	public abstract void setCMPOthersValue(Double value);

	public abstract void setCMPOthersCurrency(String currency);

	public abstract void setCMPCommonRef(Long commonRef);

	public abstract void setCMPIsDeleted(String isDeleted);

	public void setPaymentID(long id) {
		setCMPPaymentID(new Long(id));
	}

	public void setAdvanceID(long id) {
		setCMPAdvanceID(new Long(id));
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

	public void setIsToFDR(boolean flag) {
		setCMPIsToFDR(TypeConverter.convertBooleanToStringEquivalent(flag));
	}

	public void setFDRAmount(Amount amt) {
		if (amt != null) {
			setCMPFDRValue(new Double(amt.getAmount()));
			setCMPFDRCurrency(amt.getCurrencyCode());
		}
		else {
			setCMPFDRValue(null);
			setCMPFDRCurrency(null);
		}
	}

	public void setIsToRepo(boolean flag) {
		setCMPIsToRepo(TypeConverter.convertBooleanToStringEquivalent(flag));
	}

	public void setRepoAmount(Amount amt) {
		if (amt != null) {
			setCMPRepoValue(new Double(amt.getAmount()));
			setCMPRepoCurrency(amt.getCurrencyCode());
		}
		else {
			setCMPRepoValue(null);
			setCMPRepoCurrency(null);
		}
	}

	public void setIsToAPG(boolean flag) {
		setCMPIsToAPG(TypeConverter.convertBooleanToStringEquivalent(flag));
	}

	public void setAPGAmount(Amount amt) {
		if (amt != null) {
			setCMPAPGValue(new Double(amt.getAmount()));
			setCMPAPGCurrency(amt.getCurrencyCode());
		}
		else {
			setCMPAPGValue(null);
			setCMPAPGCurrency(null);
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

	public void setIsToTL2(boolean flag) {
		setCMPIsToTL2(TypeConverter.convertBooleanToStringEquivalent(flag));
	}

	public void setTL2Amount(Amount amt) {
		if (amt != null) {
			setCMPTL2Value(new Double(amt.getAmount()));
			setCMPTL2Currency(amt.getCurrencyCode());
		}
		else {
			setCMPTL2Value(null);
			setCMPTL2Currency(null);
		}
	}

	public void setIsToTL3(boolean flag) {
		setCMPIsToTL3(TypeConverter.convertBooleanToStringEquivalent(flag));
	}

	public void setTL3Amount(Amount amt) {
		if (amt != null) {
			setCMPTL3Value(new Double(amt.getAmount()));
			setCMPTL3Currency(amt.getCurrencyCode());
		}
		else {
			setCMPTL3Value(null);
			setCMPTL3Currency(null);
		}
	}

	public void setIsToBC(boolean flag) {
		setCMPIsToBC(TypeConverter.convertBooleanToStringEquivalent(flag));
	}

	public void setBCAmount(Amount amt) {
		if (amt != null) {
			setCMPBCValue(new Double(amt.getAmount()));
			setCMPBCCurrency(amt.getCurrencyCode());
		}
		else {
			setCMPBCValue(null);
			setCMPBCCurrency(null);
		}
	}

	public void setIsToCollectionAccount(boolean flag) {
		setCMPIsToCollectionAccount(TypeConverter.convertBooleanToStringEquivalent(flag));
	}

	public void setCollectionAccountAmount(Amount amt) {
		if (amt != null) {
			setCMPCollectionAccountValue(new Double(amt.getAmount()));
			setCMPCollectionAccountCurrency(amt.getCurrencyCode());
		}
		else {
			setCMPCollectionAccountValue(null);
			setCMPCollectionAccountCurrency(null);
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
	 * @param object IContractFacilityType object to be created
	 * @throws javax.ejb.CreateException on error creating the entity object
	 */
	public Long ejbCreate(IPayment object) throws CreateException {
		DefaultLogger.debug(this, ">>>>>>> 6. in Payment bean -> ejbCreate");
		if (object == null) {
			throw new CreateException("IPayment object to be created is null");
		}

		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_CF_PAYMENT, true));
			setPaymentID(pk);
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
	 * @param value object of IPayment
	 * @throws javax.ejb.CreateException on error creating the references
	 */
	public void ejbPostCreate(IPayment value) throws CreateException {
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
