/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/user/bus/SBUserTeamManagerHome.java,v 1.1 2005/08/08 08:23:58 dli Exp $
 */
package com.integrosys.cms.app.user.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * This session bean provides the support for multiple-role per user (CPC &
 * CPC_Custodian), according to CR33. -- 2005/08/04 10:29:00 davidli
 * 
 * @author $Author: dli $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/08/08 08:23:58 $ Tag: $Name: $
 */
public interface SBUserTeamManagerHome extends EJBHome {
	/**
	 * Default Create method
	 * 
	 * @throws CreateException on error creating the ejb object
	 * @throws RemoteException on error during remote method call
	 */
	public SBUserTeamManager create() throws CreateException, RemoteException;
}
