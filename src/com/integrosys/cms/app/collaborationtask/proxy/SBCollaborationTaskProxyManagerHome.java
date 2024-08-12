/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/proxy/SBCollaborationTaskProxyManagerHome.java,v 1.1 2003/08/15 14:02:47 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.proxy;

//java
import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Session bean home interface for the services provided by the collaboration
 * task proxy manager
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/15 14:02:47 $ Tag: $Name: $
 */
public interface SBCollaborationTaskProxyManagerHome extends EJBHome {
	public SBCollaborationTaskProxyManager create() throws CreateException, RemoteException;
}
