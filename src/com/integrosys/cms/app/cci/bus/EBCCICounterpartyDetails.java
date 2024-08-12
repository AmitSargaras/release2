package com.integrosys.cms.app.cci.bus;


import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

import javax.ejb.EJBObject;


public interface EBCCICounterpartyDetails extends EJBObject {


    /**
     * Returns the primary key
     *
     * @return String - the primary key ID
     */
    long getGroupCCINo() throws java.rmi.RemoteException;


    long getGroupCCINoRef() throws java.rmi.RemoteException;


    /**
     * Returns the value object representing this entity bean
     */
    ICCICounterparty getValue() throws java.rmi.RemoteException, CCICounterpartyDetailsException;


    /**
     * Sets the entity using its corresponding value object.
     */
    void setValue(ICCICounterparty aICCICounterpartyDetails)    throws java.rmi.RemoteException, CCICounterpartyDetailsException, ConcurrentUpdateException;
}
