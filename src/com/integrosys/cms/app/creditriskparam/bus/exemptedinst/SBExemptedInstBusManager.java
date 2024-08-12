/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.bus.exemptedinst;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * Remote interface to the Exempted Institution business manager session bean.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface SBExemptedInstBusManager extends EJBObject
{    
   /**
	     * Gets all the Exempted Institution in actual table.
	     *
	     * @return array of IExemptedInst
	     * @throws ExemptedInstException on errors encountered
	     */
    public IExemptedInst[] getAllExemptedInst ()
        throws ExemptedInstException, RemoteException;
	
	/**
	     * Gets list of Exempted Institution by group ID.
	     *
	     * @param groupID group ID
	     * @return array of IExemptedInst
	     * @throws ExemptedInstException on errors encountered
	     */
    public IExemptedInst[] getExemptedInstByGroupID ( long groupID )
        throws ExemptedInstException, RemoteException;

	/**
	     * Gets list of Exempted Institution by sub profile ID.
	     *
	     * @param subProfileID sub profile ID
	     * @return IExemptedInst
	     * @throws ExemptedInstException on errors encountered
	     */
    public IExemptedInst getExemptedInstBySubProfileID (long subProfileID)
        throws ExemptedInstException, RemoteException;

    /**
	     * Creates the input list of Exempted Institution.
	     *
	     * @param value array of IExemptedInst to be created
	     * @return array of IExemptedInst
	     * @throws ExemptedInstException on errors encountered
	     */
    public IExemptedInst[] createExemptedInst (IExemptedInst[] value)
        throws ExemptedInstException, RemoteException;

   /**
	     * Updates the input list of Exempted Institution.
	     *
	     * @param value array of IExemptedInst to be updated
	     * @return array of IExemptedInst
	     * @throws ExemptedInstException on errors encountered
	     */
    public IExemptedInst[] updateExemptedInst (IExemptedInst[] value)
        throws ExemptedInstException, RemoteException;

	
		
}
