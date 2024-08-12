/**
 * EBCommonCodeEntry.java
 *
 * Created on January 30, 2007, 10:46 AM
 *
 * Purpose: Remote inteface for EBCommonCodeEntryBean
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public interface EBCommonCodeEntry extends EJBObject {

	public void updateCommonCodeEntry(OBCommonCodeEntry ob) throws ConcurrentUpdateException, RemoteException;

	public OBCommonCodeEntry getOBCommonCodeEntry() throws RemoteException;
}
