package com.integrosys.cms.app.creditriskparam.bus.policycap;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public interface SBPolicyCapBusManagerHome extends EJBHome {

	public SBPolicyCapBusManager create() throws CreateException, RemoteException;

}
