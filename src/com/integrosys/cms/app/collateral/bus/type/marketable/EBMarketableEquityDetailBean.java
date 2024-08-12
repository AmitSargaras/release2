/**
 * 
 */
package com.integrosys.cms.app.collateral.bus.type.marketable;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for marketable equity detail.
 * 
 * @author $Author: Siew Kheat$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public abstract class EBMarketableEquityDetailBean implements IMarketableEquityDetail, EntityBean {

	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during update to the equity. */
	private static final String[] EXCLUDE_METHOD = new String[] { "getEquityDetailID", "getEquity" };

	/**
	 * Get the equity detail id value in long type
	 */
	public long getEquityDetailID() {
		return getEBEquityDetailID().longValue();
	}

	/**
	 * Set equity detail id value
	 * @param equity detail id
	 */
	public void setEquityDetailID(long equityDetailId) {
		setEBEquityDetailID(new Long(equityDetailId));
	}

	/**
	 * Get the number of units value in long
	 */
	public long getNoOfUnits() {
		if (getEBNoOfUnits() != null) {
			return getEBNoOfUnits().longValue();
		}
		else {
			return 0;
		}
	}

	public void setNoOfUnits(long noOfUnits) {
		setEBNoOfUnits(new Long(noOfUnits));
	}

	public abstract Long getEBEquityDetailID();

	public abstract Long getEBNoOfUnits();

	public abstract String getShareType();

	public abstract String getStatus();

	public abstract Date getTransactionDate();

	public abstract String getUnitSign();

	public abstract void setEBEquityDetailID(Long equityDetailId);

	public abstract void setEBNoOfUnits(Long noOfUnits);

	public abstract void setShareType(String shareType);

	public abstract void setStatus(String status);

	public abstract void setTransactionDate(Date date);

	public abstract void setUnitSign(String unitSign);

	// public abstract void setEquityCMR(EBMarketableEquityLocal equity);
	// public abstract EBMarketableEquityLocal getEquityCMR();

	/**
	 * Get the marketable equity.
	 * 
	 * @return IMarketableEquityDetail
	 */
	public IMarketableEquityDetail getValue() {

		OBMarketableEquityDetail ob = new OBMarketableEquityDetail();
		AccessorUtil.copyValue(this, ob);
		return ob;
	}

	/**
	 * Set the marketable equity.
	 * 
	 * @param equity is of type IMarketableEquityDetail
	 */
	public void setValue(IMarketableEquityDetail equity) {

		AccessorUtil.copyValue(equity, this, EXCLUDE_METHOD);
	}

	// /**
	// * Get equity that owns the marketable equity
	// */
	// public IMarketableEquity getEquity() {
	// return getEquityCMR().getValue();
	// }
	//
	// public void setEquity(IMarketableEquity equity) {
	//
	// }

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param equity of type IMarketableEquityDetail
	 * @return primary key
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IMarketableEquityDetail equityDetail) throws CreateException {

		try {
			String equityID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_MARKETABLE_EQUITY_DETAIL, true);
			AccessorUtil.copyValue(equityDetail, this, EXCLUDE_METHOD);
			this.setEBEquityDetailID(new Long(equityID));

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
	 * @param equity of type IMarketableEquityDetail
	 */
	public void ejbPostCreate(IMarketableEquityDetail equity) {
	}

	/**
	 * EJB callback method to set the context of the bean.
	 * @param context the entity context.
	 */
	public void setEntityContext(EntityContext context) {
		this.context = context;
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
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the bean's state with the state in the database.
	 * This method is called after the container has loaded the bean's state
	 * from the database.
	 */
	public void ejbLoad() {
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

	/**
	 * EJB callback method to clears the context of the bean.
	 */
	public void unsetEntityContext() {
		this.context = null;
	}

}
