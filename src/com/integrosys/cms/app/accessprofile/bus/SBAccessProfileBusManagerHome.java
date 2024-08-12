/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/accessprofile/bus/SBAccessProfileBusManagerHome.java,v 1.1 2003/10/20 11:04:13 btchng Exp $
 */
package com.integrosys.cms.app.accessprofile.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * EJB session bean home interface.
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/10/20 11:04:13 $ Tag: $Name: $
 */
public interface SBAccessProfileBusManagerHome extends EJBHome {

	public SBAccessProfileBusManager create() throws CreateException, RemoteException;
}
