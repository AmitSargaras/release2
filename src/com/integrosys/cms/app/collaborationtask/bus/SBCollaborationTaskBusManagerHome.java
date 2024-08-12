/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/SBCollaborationTaskBusManagerHome.java,v 1.1 2003/08/14 13:25:11 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Session bean home interface for the services provided by the Collaboration
 * task bus manager
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/14 13:25:11 $ Tag: $Name: $
 */
public interface SBCollaborationTaskBusManagerHome extends EJBHome {
	public SBCollaborationTaskBusManager create() throws CreateException, RemoteException;
}
