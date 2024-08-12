package com.integrosys.cms.app.chktemplate.bus;

import com.integrosys.cms.app.checklist.bus.IShareDoc;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 8, 2008
 * Time: 5:44:20 PM
 * To change this template use File | Settings | File Templates.
 */

public interface EBDynamicProperty extends EJBObject {

	public IDynamicProperty getValue() throws RemoteException;

	public void setValue(IDynamicProperty object) throws RemoteException;

}
