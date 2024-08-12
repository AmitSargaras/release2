/**
 * CommonCodeEntryStageHome.java
 *
 * Created on January 31, 2007, 5:38 PM
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

public interface EBCommonCodeEntryStageHome extends EJBHome {

	public EBCommonCodeEntryStage create(OBCommonCodeEntry ob) throws CreateException, RemoteException;

	public EBCommonCodeEntryStage findByPrimaryKey(Long stageId) throws FinderException, RemoteException;

	public EBCommonCodeEntryStage findByEntryId(Long entryId) throws FinderException, RemoteException;
	
	public EBCommonCodeEntryStage findByEntryId(Long entryId,boolean active) throws FinderException, RemoteException;

	public Collection findByStagingRefId(Long stageRefId) throws FinderException, RemoteException;

}