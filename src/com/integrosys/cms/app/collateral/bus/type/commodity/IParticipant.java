/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/IParticipant.java,v 1.7 2004/08/19 02:21:28 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface represents loan agency participant.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2004/08/19 02:21:28 $ Tag: $Name: $
 */
public interface IParticipant extends java.io.Serializable {
	/**
	 * Get loan's participant id.
	 * 
	 * @return long
	 */
	public long getParticipantID();

	/**
	 * Set participant id.
	 * 
	 * @param participantID of type long
	 */
	public void setParticipantID(long participantID);

	/**
	 * Get participant name.
	 * 
	 * @return String
	 */
	public String getName();

	/**
	 * Set participant name.
	 * 
	 * @param name of type String
	 */
	public void setName(String name);

	/**
	 * Get amount allocated to the participant.
	 * 
	 * @return Amount
	 */
	public Amount getAllocatedAmount();

	/**
	 * Set amount allocated to participant.
	 * 
	 * @param amt of type Amount
	 */
	public void setAllocatedAmount(Amount amt);

	/**
	 * Get remarks of this participant.
	 * 
	 * @return String
	 */
	public String getRemarks();

	/**
	 * Set remarks for participant.
	 * 
	 * @param remarks of type String
	 */
	public void setRemarks(String remarks);

	/**
	 * Get cms business status for this participant.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set participant status, active or deleted.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);

	/**
	 * Get common reference of actual and staging participant.
	 * 
	 * @return long
	 */
	public long getCommonRef();

	/**
	 * Set common reference of actual and staging participant.
	 * 
	 * @param commonRef of type long
	 */
	public void setCommonRef(long commonRef);
}
