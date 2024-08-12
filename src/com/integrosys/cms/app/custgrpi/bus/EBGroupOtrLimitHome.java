package com.integrosys.cms.app.custgrpi.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

public interface EBGroupOtrLimitHome extends EJBHome {

   public EBGroupOtrLimit create(IGroupOtrLimit obj) throws CreateException, RemoteException;

   public EBGroupOtrLimit findByPrimaryKey(Long pk) throws FinderException, RemoteException;

}