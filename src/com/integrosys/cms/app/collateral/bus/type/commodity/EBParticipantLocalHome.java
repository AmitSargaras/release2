/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBParticipantLocalHome.java,v 1.4 2004/08/18 08:12:07 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

/**
 * Defines participant create and finder methods for local clients.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/18 08:12:07 $ Tag: $Name: $
 */
public interface EBParticipantLocalHome extends javax.ejb.EJBLocalHome {
	/**
	 * Create loan agency borrower.
	 * 
	 * @param participant of type IParticipant
	 * @return local participant ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBParticipantLocal create(IParticipant participant) throws CreateException;

	/**
	 * Find participant by its primary key, the participant id.
	 * 
	 * @param primaryKey participant id
	 * @return local borrower ejb object
	 * @throws FinderException on error finding the participant
	 */
	public EBParticipantLocal findByPrimaryKey(Long primaryKey) throws FinderException;

	/**
	 * Find all participants.
	 * 
	 * @return a Collection of participants local ejb object
	 * @throws FinderException on error finding the participants
	 */
	public Collection findAll() throws FinderException;
}