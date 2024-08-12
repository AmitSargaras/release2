package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

public interface EBGroupSubLimit extends EJBObject {


    /**
     * Returns the primary key
     *
     * @return String - the primary key ID
     */
    public long getGroupSubLimitID()
            throws RemoteException;



    /**
     * Returns the value object representing this entity bean
     */
    public IGroupSubLimit getValue()
            throws RemoteException, CustGrpIdentifierException;


    /**
     * Sets the entity using its corresponding value object.
     */
    public void setValue(IGroupSubLimit obj)
            throws RemoteException, CustGrpIdentifierException, ConcurrentUpdateException;


}
