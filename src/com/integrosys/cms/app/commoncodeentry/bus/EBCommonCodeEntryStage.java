/**
 * EBCommonCodeEntryStage.java
 *
 * Created on January 31, 2007, 5:37 PM
 *
 * Purpose: the remote interface
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

public interface EBCommonCodeEntryStage extends EJBObject {

	public void updateCommonCodeEntry(OBCommonCodeEntry ob) throws ConcurrentUpdateException, RemoteException;

	public OBCommonCodeEntry getOBCommonCodeEntryStage() throws RemoteException;

}
