/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/user/bus/SBUserTeamManager.java,v 1.1 2005/08/08 08:23:58 dli Exp $
 */
package com.integrosys.cms.app.user.bus;

/**
 * This session bean provides the support for multiple-role per user (CPC & CPC_Custodian),
 * according to CR33. -- 2005/08/04 10:29:00	davidli
 *
 * @author  $Author: dli $<br>
 * @version $Revision: 1.1 $
 * @since   $Date: 2005/08/08 08:23:58 $
 * Tag:     $Name:  $
 */

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;

public interface SBUserTeamManager extends EJBObject {

	// Get the member team list of a user by UserID.
	public abstract ITeam[] getTeamsByUserID(long l) throws EntityNotFoundException, RemoteException;

	// Get the team membership list of a user by UserID.
	public abstract ITeamMembership[] getTeamMembershipListByUserID(long l) throws EntityNotFoundException,
			RemoteException;

}
