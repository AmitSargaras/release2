/*
* Copyright Integro Technologies Pte Ltd
* $Header$
*/
package com.integrosys.cms.app.creditriskparam.bus.exemptFacility;

import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * EBExemptFacilityHome
 * Purpose:
 * Description:
 *
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
public interface EBExemptFacilityHome extends EJBHome {

    EBExemptFacility create (IExemptFacility ExemptFacility) throws CreateException, RemoteException;

    EBExemptFacility findByPrimaryKey (Long pk) throws FinderException, RemoteException;

    Collection findByGroupID (long groupID) throws FinderException, RemoteException;

    Collection findAll () throws FinderException, RemoteException;

}
