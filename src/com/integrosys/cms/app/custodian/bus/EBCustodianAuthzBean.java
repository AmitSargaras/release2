/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.custodian.bus;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Implementation for the custodian doc entity bean
 * 
 * @author $Author: sathish $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */

public abstract class EBCustodianAuthzBean implements EntityBean, ICustAuthorize {
	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_CUSTODIAN_AUTHZ;

	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getCustAuthorizeId" };

	private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getCustAuthorizeId" };

	/**
	 * Default Constructor
	 */
	public EBCustodianAuthzBean() {
	}

	public abstract Long getCMPCustAuthorizeId();

	public abstract Long getCMPCustodianId();

	/**
	 * Get the custodian document ID
	 * @return long - the long value of the custodian document ID
	 */
	public long getCustAuthorizeId() {
		if (getCMPCustAuthorizeId() != null) {
			return getCMPCustAuthorizeId().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Get the custodian document ID
	 * @return long - the long value of the custodian document ID
	 */
	public long getCustodianId() {
		if (getCMPCustodianId() != null) {
			return getCMPCustodianId().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	public abstract void setCMPCustAuthorizeId(Long aCustAuthzID);

	public abstract void setCMPCustodianId(Long aCustodianID);

	/**
	 * Helper method to set the custodian doc ID
	 * @param id - long
	 */
	public void setCustAuthorizeId(long id) {
		setCMPCustAuthorizeId(new Long(id));
	}

	/**
	 * Helper method to set the custodian doc ID
	 * @param id - long
	 */
	public void setCustodianId(long id) {
		setCMPCustodianId(new Long(id));
	}

	/**
	 * Return a custodian authz object
	 * @return ICustodianDoc - the object containing the custodian document
	 *         object
	 */
	public ICustAuthorize getValue() {
		OBCustAuthorize value = new OBCustAuthorize();
		AccessorUtil.copyValue(this, value, null);
		return value;
	}

	/**
	 * Sets the custodian authz object.
	 * @param anICustAuthorize - ICustAuthorize
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(ICustAuthorize anICustAuthorize) throws ConcurrentUpdateException {
		AccessorUtil.copyValue(anICustAuthorize, this, EXCLUDE_METHOD_UPDATE);
	}

	/**
	 * Create a custodian authz record
	 * @param anICustAuthorize - ICustAuthorize
	 * @return Long - the custodian doc ID
	 * @throws CreateException on error
	 */
	public Long ejbCreate(ICustAuthorize anICustAuthorize) throws CreateException {
		try {
			String custAuthorizeId = (new SequenceManager()).getSeqNum(SEQUENCE_NAME, true);
			AccessorUtil.copyValue(anICustAuthorize, this, EXCLUDE_METHOD_CREATE);
			setCMPCustAuthorizeId(new Long(custAuthorizeId));
			return new Long(custAuthorizeId);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new CreateException("Caught unknown exception: " + ex.toString());
		}
	}

	/**
	 * EJB Post Create Method
	 * @param anICustAuthorize - ICustodianDoc
	 */
	public void ejbPostCreate(ICustAuthorize anICustAuthorize) {
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

	/**
	 * A container invokes this method when the instance is taken out of the
	 * pool of available instances to become associated with a specific EJB
	 * object. This method transitions the instance to the ready state. This
	 * method executes in an unspecified transaction context.
	 */
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

	public abstract String getAuthorizeName();

	public abstract String getAuthorizeRole();

	public abstract Date getAuthzDate();

	public abstract long getCheckListItemRef();

	public abstract String getOperation();

	public abstract String getSignNum();

	public abstract void setAuthorizeName(String authorizeName);

	public abstract void setAuthorizeRole(String authorizeRole);

	public abstract void setAuthzDate(Date authzDate);

	public abstract void setCheckListItemRef(long checkListItemRef);

	public abstract void setOperation(String operation);

	public abstract void setSignNum(String signNum);

}