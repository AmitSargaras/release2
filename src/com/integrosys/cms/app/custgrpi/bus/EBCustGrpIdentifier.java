package com.integrosys.cms.app.custgrpi.bus;


import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;


public interface EBCustGrpIdentifier extends EJBObject {


    /**
     * Returns the primary key
     *
     * @return String - the primary key ID
     */
    public long getGrpID()
            throws java.rmi.RemoteException;



    /**
     * Returns the value object representing this entity bean
     */
    public ICustGrpIdentifier getValue()
            throws java.rmi.RemoteException, CustGrpIdentifierException;


    /**
     * Sets the entity using its corresponding value object.
     */
    public void setValue(ICustGrpIdentifier obj)
            throws java.rmi.RemoteException, CustGrpIdentifierException, ConcurrentUpdateException;

	/**
	     * Set the BGEL limit amount for Group.
	     *
	     * @param aICustGrpIdentifier of type ICustGrpIdentifier
	     * @throws ConcurrentUpdateException if the Group is invalid
	     * @throws RemoteException on error during remote method call
	     */
	public void updateGroupLimitAmount (ICustGrpIdentifier aICustGrpIdentifier)
            throws java.rmi.RemoteException, ConcurrentUpdateException;

    public void createDependants (ICustGrpIdentifier obj, long versionTime)
            throws VersionMismatchException, RemoteException;

   // public void createDependants (List list)   throws VersionMismatchException, RemoteException;

   public long getVersionTime() throws RemoteException;


     ////  for GroupSubLimit//
     //public void createDependants (long grpID, ICustGrpIdentifier obj)   throws RemoteException,CustGrpIdentifierException;





}
