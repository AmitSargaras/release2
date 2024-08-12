package com.integrosys.cms.app.commoncode.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public interface SBCommonCodeTypeBusManagerHome extends EJBHome {
	public SBCommonCodeTypeBusManager create() throws CreateException, RemoteException;
}
