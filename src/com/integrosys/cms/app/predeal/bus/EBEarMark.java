/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * EBEarMark
 *
 * Created on 10:02:25 AM
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
 * Created by IntelliJ IDEA. User: Eric Date: Mar 23, 2007 Time: 10:02:25 AM
 */
public interface EBEarMark extends EJBObject {
	public OBEarMark getOBEarMark() throws RemoteException;

	public void updateEBEarMark(OBEarMark ob) throws RemoteException;

}
