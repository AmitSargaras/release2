/**
 * EBCommonCodeEntryHome.java
 *
 * Created on January 30, 2007, 10:58 AM
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
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface EBCommonCodeEntryHome extends EJBHome {

	public EBCommonCodeEntry create(OBCommonCodeEntry ob) throws CreateException, RemoteException;

	public EBCommonCodeEntry findByPrimaryKey(Long entryId) throws FinderException, RemoteException;

	public Collection findByCategoryCodeId(Long categoryCodeId) throws FinderException, RemoteException;

	public EBCommonCodeEntry findByCategoryAndEntryCode(Long categoryCodeId, String entryCode, String country)
			throws FinderException, RemoteException;;

	public EBCommonCodeEntry findByCategoryAndEntryCodeAndSourceId(Long categoryCodeId, String entryCode,
			String country, String sourceId) throws FinderException, RemoteException;

	public Collection findByCategoryCode(String categoryCode) throws FinderException, RemoteException;

	public Collection findByCategoryCodeAndRefEntryCode(String categoryCode, String refEntryCode)
			throws FinderException, RemoteException;

}
