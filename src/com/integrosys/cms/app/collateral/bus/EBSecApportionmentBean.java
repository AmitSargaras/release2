/*
 * Created on Jun 19, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class EBSecApportionmentBean implements ISecApportionment, EntityBean {
	private EntityContext context;

	private static final String[] EXCLUDE_METHOD = new String[] { "getSecApportionmentID", "getRefID", "getStatusInd" };

	public abstract Long getEBSecApportionmentID();

	public abstract void setEBSecApportionmentID(Long eBSecApportionmentID);

	public abstract Long getEBLimitID();

	public abstract void setEBLimitID(Long limitID);

	public Long ejbCreate(ISecApportionment secApportionment) throws CreateException {
		try {
			String secApportionmentId = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_SEC_APPORTION, true);
			AccessorUtil.copyValue(secApportionment, this, EXCLUDE_METHOD);
			setEBSecApportionmentID(new Long(secApportionmentId));
			if (secApportionment.getRefID() == ICMSConstant.LONG_MIN_VALUE) {
				setRefID(getSecApportionmentID());
			}
			else {
				setRefID(secApportionment.getRefID());
			}
			return null;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new CreateException(ex.toString());
		}
	}

	public void ejbPostCreate(ISecApportionment secApportionment) {
	}

	public String getCurrencyCode() {
		return null;
	}

	public void setCurrencyCode(String code) {
	}

	public ISecApportionment getValue() {
		OBSecApportionment apportionment = new OBSecApportionment();
		AccessorUtil.copyValue(this, apportionment);
		return apportionment;
	}

	public void setValue(ISecApportionment apportionment) {
		try {
			AccessorUtil.copyValue(apportionment, this, EXCLUDE_METHOD);
		}
		catch (Exception e) {
			throw new EJBException(e);
		}
	}

	public long getSecApportionmentID() {
		return getEBSecApportionmentID().longValue();
	}

	public void setSecApportionmentID(long id) {
		setEBSecApportionmentID(new Long(id));
	}

	public long getLimitID() {
		return getEBLimitID().longValue();
	}

	public void setLimitID(long limitId) {
		setEBLimitID(new Long(limitId));
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


    public abstract int getPriorityRanking();

    public abstract void setPriorityRanking(int rank);

    public abstract double getPriorityRankingAmount();

    public abstract void setPriorityRankingAmount(double amt);

    public abstract double getApportionAmount();

    public abstract void setApportionAmount(double amt);

    public abstract long getRefID();

    public abstract void setRefID(long refId);

    public abstract String getPercAmtInd();

    public abstract void setPercAmtInd(String ind);

    public abstract double getByAbsoluteAmt();

    public abstract void setByAbsoluteAmt(double amt);

    public abstract double getByPercentage();

    public abstract void setByPercentage(double perc);

    public abstract String getMinPercAmtInd();

    public abstract void setMinPercAmtInd(String ind);

    public abstract double getMinAbsoluteAmt();

    public abstract void setMinAbsoluteAmt(double amt);

    public abstract double getMinPercentage();

    public abstract void setMinPercentage(double perc);

    public abstract String getMaxPercAmtInd();

    public abstract void setMaxPercAmtInd(String ind);

    public abstract double getMaxAbsoluteAmt();

    public abstract void setMaxAbsoluteAmt(double amt);

    public abstract double getMaxPercentage();

    public abstract void setMaxPercentage(double perc);

    public abstract long getChargeDetailId();

    public abstract void setChargeDetailId(long chargeDetailId);
}
