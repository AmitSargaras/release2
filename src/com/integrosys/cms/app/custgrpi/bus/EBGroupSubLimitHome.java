package com.integrosys.cms.app.custgrpi.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

public interface EBGroupSubLimitHome extends EJBHome {

   public EBGroupSubLimit create(IGroupSubLimit obj) throws CreateException, RemoteException;

   public EBGroupSubLimit findByPrimaryKey(Long pk) throws FinderException, RemoteException;

}
