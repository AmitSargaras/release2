package com.integrosys.cms.app.chktemplate.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 16, 2008
 * Time: 6:56:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SBCheckListTemplateBusManagerHome extends EJBHome {
    public SBCheckListTemplateBusManager create() throws CreateException, RemoteException;

}
