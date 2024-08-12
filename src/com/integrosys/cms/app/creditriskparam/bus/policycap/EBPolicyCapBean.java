package com.integrosys.cms.app.creditriskparam.bus.policycap;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSErrorCodes;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public abstract class EBPolicyCapBean implements EntityBean, IPolicyCap {

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD = new String[] { "getPolicyCapID" };

	/**
	 * Default Constructor
	 */
	public EBPolicyCapBean() {
	}

	// ====================================
	// Get and Set Value
	// ====================================
	/**
	 * Retrieve an instance of policy cap object.
	 * @return policy cap business object
	 */
	public IPolicyCap getValue() throws PolicyCapException {
		try {
			OBPolicyCap value = new OBPolicyCap();
			AccessorUtil.copyValue(this, value);
			return value;
		}
		catch (Exception e) {
			throw new PolicyCapException(e);
		}
	}

	/**
	 * Set the policy cap object.
	 * @param value of type IPolicyCap
	 * @throws ConcurrentUpdateException if more than one client accessing this
	 *         policy cap object at the same time
	 */
	public void setValue(IPolicyCap value) throws ConcurrentUpdateException, PolicyCapException {
		try {
			checkVersionMismatch(value);
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			this.setVersionTime(VersionGenerator.getVersionNumber());
		}
		catch (ConcurrentUpdateException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new PolicyCapException("Exception in setValue: " + ex.toString());
		}
	}

	/**
	 * Helper method to check the version of this IPolicyCap object.
	 * @param value policy cap object
	 * @throws ConcurrentUpdateException if the entity version is invalid
	 */
	private void checkVersionMismatch(IPolicyCap value) throws ConcurrentUpdateException {
		if (getVersionTime() != value.getVersionTime()) {
			ConcurrentUpdateException exp = new ConcurrentUpdateException("Mismatch timestamp");
			exp.setErrorCode(ICMSErrorCodes.CONCURRENT_UPDATE);
			throw exp;
		}
	}

	// ========================
	// Getters
	// ========================
	public abstract Long getCMPPolicyCapID();

	public abstract Float getCMPMaxTradeCapNonFI();

	public abstract Float getCMPMaxCollateralCapNonFI();

	public abstract Float getCMPQuotaCollateralCapNonFI();

	public abstract Float getCMPMaxCollateralCapFI();

	public abstract Float getCMPQuotaCollateralCapFI();

	public abstract Float getCMPLiquidMOA();

	public abstract Float getCMPIlliquidMOA();

	public abstract Double getCMPMaxPriceCap();

	public abstract String getCurrency();

	public abstract Long getCMPGroupID();

	public abstract Long getCMPCommonRef();

	/**
	 * Helper method to get the policy cap ID
	 * @return long - the long value of the policy cap ID
	 */
	public long getPolicyCapID() {
		if (getCMPPolicyCapID() != null) {
			return getCMPPolicyCapID().longValue();
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Helper method to get the max trading cap for non financial institute
	 * @return float - the float value of the max trading cap for non financial
	 *         institute
	 */
	public float getMaxTradeCapNonFI() {
		if (getCMPMaxTradeCapNonFI() != null) {
			return getCMPMaxTradeCapNonFI().floatValue();
		}
		return ICMSConstant.FLOAT_INVALID_VALUE;
	}

	/**
	 * Helper method to get the max collateral cap for non financial institute
	 * @return float - the float value of the max collateral cap for non
	 *         financial institute
	 */
	public float getMaxCollateralCapNonFI() {
		if (getCMPMaxCollateralCapNonFI() != null) {
			return getCMPMaxCollateralCapNonFI().floatValue();
		}
		return ICMSConstant.FLOAT_INVALID_VALUE;
	}

	/**
	 * Helper method to get the collateral quota cap for non financial institute
	 * @return float - the float value of the collateral quota cap for non
	 *         financial institute
	 */
	public float getQuotaCollateralCapNonFI() {
		if (getCMPQuotaCollateralCapNonFI() != null) {
			return getCMPQuotaCollateralCapNonFI().floatValue();
		}
		return ICMSConstant.FLOAT_INVALID_VALUE;
	}

	/**
	 * Helper method to get the max collateral cap for financial institute
	 * @return float - the float value of the max collateral cap for financial
	 *         institute
	 */
	public float getMaxCollateralCapFI() {
		if (getCMPMaxCollateralCapFI() != null) {
			return getCMPMaxCollateralCapFI().floatValue();
		}
		return ICMSConstant.FLOAT_INVALID_VALUE;
	}

	/**
	 * Helper method to get the collateral quota cap for financial institute
	 * @return float - the float value of the collateral quota cap for financial
	 *         institute
	 */
	public float getQuotaCollateralCapFI() {
		if (getCMPQuotaCollateralCapFI() != null) {
			return getCMPQuotaCollateralCapFI().floatValue();
		}
		return ICMSConstant.FLOAT_INVALID_VALUE;
	}

	/**
	 * Helper method to get the liquid MOA
	 * @return float - the float value of the liquid MOA
	 */
	public float getLiquidMOA() {
		if (getCMPLiquidMOA() != null) {
			return getCMPLiquidMOA().floatValue();
		}
		return ICMSConstant.FLOAT_INVALID_VALUE;
	}

	/**
	 * Helper method to get the illiquid MOA
	 * @return float - the float value of the illiquid MOA
	 */
	public float getIlliquidMOA() {
		if (getCMPIlliquidMOA() != null) {
			return getCMPIlliquidMOA().floatValue();
		}
		return ICMSConstant.FLOAT_INVALID_VALUE;
	}

	/**
	 * Get PriceCap amount.
	 * 
	 * @return Amount
	 */
	public Amount getPriceCap() {
		if ((getCMPMaxPriceCap() != null) && (getCurrency() != null)) {
			return new Amount(getCMPMaxPriceCap().doubleValue(), new CurrencyCode(getCurrency()));
		}
		return null;
	}

	/**
	 * Helper method to get the group id
	 * @return long - the long value of the group id
	 */
	public long getGroupID() {
		if (getCMPGroupID() != null) {
			return getCMPGroupID().longValue();
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Helper method to get the common reference
	 * @return long - the long value of the common reference
	 */
	public long getCommonRef() {
		if (getCMPCommonRef() != null) {
			return getCMPCommonRef().longValue();
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	// ========================
	// Setters
	// ========================
	public abstract void setCMPPolicyCapID(Long policyCapID);

	public abstract void setCMPMaxTradeCapNonFI(Float cap);

	public abstract void setCMPMaxCollateralCapNonFI(Float cap);

	public abstract void setCMPQuotaCollateralCapNonFI(Float cap);

	public abstract void setCMPMaxCollateralCapFI(Float cap);

	public abstract void setCMPQuotaCollateralCapFI(Float cap);

	public abstract void setCMPLiquidMOA(Float moa);

	public abstract void setCMPIlliquidMOA(Float moa);

	public abstract void setCMPMaxPriceCap(Double cap);

	public abstract void setCurrency(String cap);

	public abstract void setCMPGroupID(Long groupID);

	public abstract void setCMPCommonRef(Long commonRef);

	/**
	 * Helper method to set the policy cap ID
	 * @param policyCapID - long
	 */
	public void setPolicyCapID(long policyCapID) {
		setCMPPolicyCapID(new Long(policyCapID));
	}

	/**
	 * Helper method to set the max trading cap for non financial institute
	 * @param cap - float
	 */
	public void setMaxTradeCapNonFI(float cap) {
		setCMPMaxTradeCapNonFI(new Float(cap));
	}

	/**
	 * Helper method to set the max collateral cap for non financial institute
	 * @param cap - float
	 */
	public void setMaxCollateralCapNonFI(float cap) {
		setCMPMaxCollateralCapNonFI(new Float(cap));
	}

	/**
	 * Helper method to set the collateral quota cap for non financial institute
	 * @param cap - float
	 */
	public void setQuotaCollateralCapNonFI(float cap) {
		setCMPQuotaCollateralCapNonFI(new Float(cap));
	}

	/**
	 * Helper method to set the max collateral cap for financial institute
	 * @param cap - float
	 */
	public void setMaxCollateralCapFI(float cap) {
		setCMPMaxCollateralCapFI(new Float(cap));
	}

	/**
	 * Helper method to set the collateral quota cap for financial institute
	 * @param cap - float
	 */
	public void setQuotaCollateralCapFI(float cap) {
		setCMPQuotaCollateralCapFI(new Float(cap));
	}

	/**
	 * Helper method to set the liquid MOA
	 * @param moa - float
	 */
	public void setLiquidMOA(float moa) {
		setCMPLiquidMOA(new Float(moa));
	}

	/**
	 * Helper method to set the illiquid MOA
	 * @param moa - float
	 */
	public void setIlliquidMOA(float moa) {
		setCMPIlliquidMOA(new Float(moa));
	}

	/**
	 * Helper method to set the group id
	 * @param groupID - long
	 */
	public void setGroupID(long groupID) {
		setCMPGroupID(new Long(groupID));
	}

	/**
	 * Helper method to set the common reference
	 * @param commonRef - long
	 */
	public void setCommonRef(long commonRef) {
		setCMPCommonRef(new Long(commonRef));
	}

	public void setPriceCap(Amount amt) {
		setCMPMaxPriceCap(new Double(amt.getAmount()));
		setCurrency(amt.getCurrencyCode());
	}

	// ==============================
	// Standard Methods
	// ==============================
	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param value IPolicyCap object
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IPolicyCap value) throws CreateException {
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_POLICY_CAP_SEQ, true));
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			setPolicyCapID(pk);

			if (value.getCommonRef() == ICMSConstant.LONG_MIN_VALUE) {
				setCommonRef(getPolicyCapID());
			}
			else { // else maintain this reference id.
				setCommonRef(value.getCommonRef());
			}
			this.setVersionTime(VersionGenerator.getVersionNumber());

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
	 * @param value object of IPolicyCap
	 * @throws javax.ejb.CreateException on error creating the references
	 */
	public void ejbPostCreate(IPolicyCap value) throws CreateException {
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

    public abstract String getBoard();

    public abstract void setBoard(String board);

    public abstract long getVersionTime();

    public abstract void setVersionTime(long l);
}
