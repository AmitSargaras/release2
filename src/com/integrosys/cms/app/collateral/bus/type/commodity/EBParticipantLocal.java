/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBParticipantLocal.java,v 1.4 2004/08/18 08:12:07 wltan Exp $
 */

package com.integrosys.cms.app.collateral.bus.type.commodity;

/**
 * Local interface for participant entity bean.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/18 08:12:07 $ Tag: $Name: $
 */
public interface EBParticipantLocal extends javax.ejb.EJBLocalObject {

	/**
	 * Get loan agency participant business object.
	 * 
	 * @return IParticipant
	 */
	public IParticipant getValue();

	/**
	 * Persist newly updated loan agency participant.
	 * 
	 * @param participant of type IParticipant
	 */
	public void setValue(IParticipant participant);

	/**
	 * Set status of this loan agency participant.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}