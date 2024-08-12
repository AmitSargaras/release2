/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/OBParticipant.java,v 1.8 2004/08/19 02:16:40 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * A class represents loan agency's participant.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2004/08/19 02:16:40 $ Tag: $Name: $
 */
public class OBParticipant implements IParticipant, java.io.Serializable {
	private long participantID = ICMSConstant.LONG_INVALID_VALUE;

	private String name;

	private Amount allocatedAmount;

	private String remarks;

	private String status = ICMSConstant.STATE_ACTIVE;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	/**
	 * Default constructor
	 */
	public OBParticipant() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IParticipant
	 */
	public OBParticipant(IParticipant obj) {
		this();
		if (obj != null) {
			AccessorUtil.copyValue(obj, this);
		}
	}

	/**
	 * Get participant id.
	 * 
	 * @return long
	 */
	public long getParticipantID() {
		return participantID;
	}

	/**
	 * Set participant id.
	 * 
	 * @param participantID of type long
	 */
	public void setParticipantID(long participantID) {
		this.participantID = participantID;
	}

	/**
	 * Get participant name.
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set participant name.
	 * 
	 * @param name of type String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get amount allocated to participant.
	 * 
	 * @return Amount
	 */
	public Amount getAllocatedAmount() {
		return allocatedAmount;
	}

	/**
	 * Set amount allocated to participant.
	 * 
	 * @param allocatedAmount of type Amount
	 */
	public void setAllocatedAmount(Amount allocatedAmount) {
		this.allocatedAmount = allocatedAmount;
	}

	/**
	 * Get remarks for participant.
	 * 
	 * @return String
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * Set remarks for participant.
	 * 
	 * @param remarks of type String
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * Get participant status, active or deleted.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set participant status, active or deleted.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Get common reference for actual and staging sub-limit.
	 * 
	 * @return long
	 */
	public long getCommonRef() {
		return commonRef;
	}

	/**
	 * Set common reference for actual and staging sub-limit.
	 * 
	 * @param commonRef of type long
	 */
	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Return the hash code
	 * 
	 * @return int
	 */
	public int hashCode() {
		String hash = String.valueOf(this.participantID);
		return hash.hashCode();
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof IParticipant)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}
