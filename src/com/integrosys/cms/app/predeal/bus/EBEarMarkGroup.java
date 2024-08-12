/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * EBEarkMarkGroup
 *
 * Created on 5:45:54 PM
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

import javax.ejb.EJBObject;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Apr 2, 2007 Time: 5:45:54 PM
 */
public interface EBEarMarkGroup extends EJBObject {
	public OBEarMarkGroup getOBEarMarkGroup() throws RemoteException;

	public void updateEBEarMarkGroup(OBEarMarkGroup ob) throws RemoteException;

}
