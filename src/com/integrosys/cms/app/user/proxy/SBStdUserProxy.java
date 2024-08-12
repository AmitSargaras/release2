/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/user/proxy/SBStdUserProxy.java,v 1.1 2005/08/08 08:27:12 dli Exp $
 */
package com.integrosys.cms.app.user.proxy;

/**
 * This is the remote interface to the SBStdUserProxy
 * session bean.
 *
 * @author  $Author: dli $<br>
 * @version $Revision: 1.1 $
 * @since   $Date: 2005/08/08 08:27:12 $
 * Tag:     $Name:  $
 */

import java.rmi.RemoteException;
import java.util.Date;

import javax.ejb.EJBObject;

import com.integrosys.component.user.app.bus.UserException;
import com.integrosys.component.user.app.proxy.ICommonUserProxy;

// Referenced classes of package com.integrosys.component.user.app.proxy:
//            ICommonUserProxy

public interface SBStdUserProxy extends EJBObject, ICommonUserProxy {
	Date getLastLoginTime(long userID) throws UserException, RemoteException;
	
	String getUserNameByUserID(long userID) throws UserException, RemoteException;

}
