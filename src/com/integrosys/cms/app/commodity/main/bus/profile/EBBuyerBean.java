/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/profile/EBBuyerBean.java,v 1.3 2004/08/12 03:14:49 wltan Exp $
 */

package com.integrosys.cms.app.commodity.main.bus.profile;

import javax.ejb.CreateException;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.CommodityConstant;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author dayanand $
 * @version $
 * @since $Date: 2004/08/12 03:14:49 $ Tag: $Name: $
 */

public abstract class EBBuyerBean implements javax.ejb.EntityBean, IBuyer {

	private javax.ejb.EntityContext context;

	private static final String[] EXCLUDE_METHOD = new String[] { "getBuyerID", "getCommonReferenceID" };

	public EBBuyerBean() {
	}

	public OBBuyer getValue() {
		OBBuyer contents = new OBBuyer();
		AccessorUtil.copyValue(this, contents);
		return contents;
	}

	public void setValue(OBBuyer contents) {
		AccessorUtil.copyValue(contents, this, EXCLUDE_METHOD);
	}

	public long getBuyerID() {
		return getBuyerPK().longValue();
	}

	public void setBuyerID(long theID) {
		setBuyerPK(new Long(theID));
	}

	public long getCommonReferenceID() {
		Long commonRefID = getEBCommonReferenceID();
		return (commonRefID == null) ? ICMSConstant.LONG_INVALID_VALUE : commonRefID.longValue();
	}

	public void setCommonReferenceID(long commonReferenceID) {
		setEBCommonReferenceID((commonReferenceID == ICMSConstant.LONG_INVALID_VALUE) ? null : new Long(
				commonReferenceID));
	}

	public abstract Long getBuyerPK();

	public abstract void setBuyerPK(Long pk);

	public abstract String getName();

	public abstract void setName(String val);

	public abstract Long getEBCommonReferenceID();

	public abstract void setEBCommonReferenceID(Long commonReferenceID);

	public abstract String getStatus();

	public abstract void setStatus(String status);

	/**
	 * Create a record.
	 * 
	 * @param buyer to be created.
	 * @return Long
	 * @throws javax.ejb.CreateException on error creating the record.
	 */
	public Long ejbCreate(OBBuyer buyer) throws CreateException {
		try {
			long newBuyerPK = generateBuyerPK();
			AccessorUtil.copyValue(buyer, this, EXCLUDE_METHOD);
			setBuyerPK(new Long(newBuyerPK));
			long commonRefID = buyer.getCommonReferenceID();
			DefaultLogger.debug(this, "########## ########## EBBuyerBean.create -- buyer : " + buyer);
			setCommonReferenceID((commonRefID == ICMSConstant.LONG_INVALID_VALUE) ? newBuyerPK : commonRefID);
			setStatus(ICMSConstant.STATE_ACTIVE);
			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	/**
	 * Matching method of ejbCreate. The container invokes the matching
	 * ejbPostCreate method on an instance after it invokes the ejbCreate method
	 * with the same arguments. It executes in the same transaction context as
	 * that of the matching ejbCreate method.
	 * @param value is of type IBuyer
	 * @throws javax.ejb.CreateException on error creating references for this
	 *         borrower.
	 */
	public void ejbPostCreate(OBBuyer value) throws CreateException {
	}

	public void ejbActivate() {
	}

	/**
	 * A container invokes this method on an instance before the instance
	 * becomes disassociated with a specific EJB object. After this method
	 * completes, the container will place the instance into the pool of
	 * available instances. This method executes in an unspecified transaction
	 * context.
	 */
	public void ejbPassivate() {
	}

	/**
	 * A container invokes this method to instruct the instance to synchronize
	 * its state by loading it from the underlying database. This method always
	 * executes in the transaction context determined by the value of the
	 * transaction attribute in the deployment descriptor.
	 */
	public void ejbLoad() {
	}

	/**
	 * A container invokes this method to instruct the instance to synchronize
	 * its state by storing it to the underlying database. This method always
	 * executes in the transaction context determined by the value of the
	 * transaction attribute in the deployment descriptor.
	 */
	public void ejbStore() {
	}

	/**
	 * A container invokes this method before it removes the EJB object that is
	 * currently associated with the instance. It is invoked when a client
	 * invokes a remove operation on the enterprise Bean's home or remote
	 * interface. It transitions the instance from the ready state to the pool
	 * of available instances. It is called in the transaction context of the
	 * remove operation.
	 */
	public void ejbRemove() throws RemoveException {
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

	protected long generateBuyerPK() throws CommodityException {
		return generatePK(CommodityConstant.SEQUENCE_PROFILE_BUYER_SEQ);
	}

	protected long generatePK(String sequenceName) throws CommodityException {
		try {
			String seq = new SequenceManager().getSeqNum(sequenceName, true);
			return Long.parseLong(seq);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommodityException("Exception in generating Sequence '" + sequenceName
					+ "' \n The exception is : " + e);
		}
	}
}