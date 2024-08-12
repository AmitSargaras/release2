/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBParticipantBean.java,v 1.8 2004/08/19 02:19:23 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.math.BigDecimal;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for loan agency's participant.
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.8 $
 * @since $Date: 2004/08/19 02:19:23 $ Tag: $Name: $
 */
public abstract class EBParticipantBean implements EntityBean, IParticipant {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during create/update participant. */
	private static final String[] EXCLUDE_METHOD = new String[] { "getParticipantID", "getCommonRef" };

	public EBParticipantBean() {
	}

	/**
	 * Get participant id.
	 * 
	 * @return long
	 */
	public long getParticipantID() {
		return getEBParticipantID().longValue();
	}

	/**
	 * Set participant id.
	 * 
	 * @param participantID of type long
	 */
	public void setParticipantID(long participantID) {
		setEBParticipantID(new Long(participantID));
	}

	/**
	 * Get amount allocated to participant.
	 * 
	 * @return Amount
	 */
	public Amount getAllocatedAmount() {
		return (getEBAllocatedAmount() == null) ? null : new Amount(getEBAllocatedAmount(), new CurrencyCode(null));
	}

	/**
	 * Set amount allocated to participant.
	 * 
	 * @param allocatedAmount of type Amount
	 */
	public void setAllocatedAmount(Amount allocatedAmount) {
		setEBAllocatedAmount(allocatedAmount == null ? null : allocatedAmount.getAmountAsBigDecimal());
	}

	public abstract Long getEBParticipantID();

	public abstract void setEBParticipantID(Long participantID);

	public abstract BigDecimal getEBAllocatedAmount();

	public abstract void setEBAllocatedAmount(BigDecimal value);

	public abstract void setStatus(String status);

	/**
	 * Get loan agency participant business object.
	 * 
	 * @return IParticipant
	 */
	public IParticipant getValue() {
		OBParticipant participant = new OBParticipant();
		AccessorUtil.copyValue(this, participant);
		return participant;
	}

	/**
	 * Persist newly updated loan agency participant.
	 * 
	 * @param participant of type IParticipant
	 */
	public void setValue(IParticipant participant) {
		AccessorUtil.copyValue(participant, this, EXCLUDE_METHOD);
	}

	/**
	 * Create a record.
	 * 
	 * @param participant participant to be created.
	 * @return Long
	 * @throws javax.ejb.CreateException on error creating the record.
	 */
	public Long ejbCreate(IParticipant participant) throws CreateException {
		try {
			String newParticipantID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COMMODITY_PARTICIPANT,
					true);
			AccessorUtil.copyValue(participant, this, EXCLUDE_METHOD);
			setEBParticipantID(new Long(newParticipantID));

			if (participant.getCommonRef() == ICMSConstant.LONG_INVALID_VALUE) {
				setCommonRef(getParticipantID());
			}
			else {
				// else maintain this reference id.
				setCommonRef(participant.getCommonRef());
			}
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
	 * @param participant of type IParticipant
	 * @throws CreateException on error creating the references
	 */
	public void ejbPostCreate(IParticipant participant) throws CreateException {
	}

	/**
	 * EJB callback method to set the context of the bean.
	 * 
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
}