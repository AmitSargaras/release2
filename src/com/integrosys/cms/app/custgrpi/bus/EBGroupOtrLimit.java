package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

public interface EBGroupOtrLimit extends EJBObject {


    /**
     * Returns the primary key
     *
     * @return String - the primary key ID
     */
    public long getGroupOtrLimitID()
            throws RemoteException;



    /**
     * Returns the value object representing this entity bean
     */
    public IGroupOtrLimit getValue()
            throws RemoteException, CustGrpIdentifierException;


    /**
     * Sets the entity using its corresponding value object.
     */
    public void setValue(IGroupOtrLimit obj)
            throws RemoteException, CustGrpIdentifierException, ConcurrentUpdateException;


}