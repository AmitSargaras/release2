package com.integrosys.cms.app.chktemplate.proxy;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;


public interface SBCheckListTemplateProxyManagerHome extends EJBHome {
    public SBCheckListTemplateProxyManager create() throws CreateException, RemoteException;

}
