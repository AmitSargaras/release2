/**
 * SBCommonCodeParamManagerHome.java
 *
 * Created on January 30, 2007, 10:46 AM
 *
 * Purpose: the home interface
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface SBCommonCodeEntryManagerHome extends EJBHome {
	public SBCommonCodeEntryManager create() throws CreateException, RemoteException;
}
