package com.integrosys.cms.app.custgrpi.bus;

import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

public interface EBGroupMemberHome extends EJBHome {

   public EBGroupMember create(IGroupMember obj) throws CreateException, RemoteException;

   public EBGroupMember findByPrimaryKey(Long pk) throws FinderException, RemoteException;

}
