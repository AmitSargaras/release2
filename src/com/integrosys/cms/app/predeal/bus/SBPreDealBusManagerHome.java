/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * SBPreDealBusManagerHome
 *
 * Created on 2:50:51 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 23, 2007 Time: 2:50:51 PM
 */
public interface SBPreDealBusManagerHome extends EJBHome {
	public SBPreDealBusManager create() throws CreateException, RemoteException;
}
