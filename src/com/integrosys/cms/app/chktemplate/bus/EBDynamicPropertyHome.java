package com.integrosys.cms.app.chktemplate.bus;

import com.integrosys.cms.app.checklist.bus.EBDocumentshare;
import com.integrosys.cms.app.checklist.bus.IShareDoc;

import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Nov 15, 2005 Time: 5:48:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EBDynamicPropertyHome extends EJBHome {

	public EBDynamicProperty create(Long docItemID, IDynamicProperty object) throws CreateException, RemoteException;

	public EBDynamicProperty findByPrimaryKey(Long dynPropId) throws FinderException, RemoteException;

}
