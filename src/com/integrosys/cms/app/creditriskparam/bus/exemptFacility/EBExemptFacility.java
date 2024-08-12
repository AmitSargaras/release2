/*
* Copyright Integro Technologies Pte Ltd
* $Header$
*/
package com.integrosys.cms.app.creditriskparam.bus.exemptFacility;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.businfra.common.exception.VersionMismatchException;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * EBExemptFacility
 * Purpose:
 * Description:
 *
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
public interface EBExemptFacility extends EJBObject{

    IExemptFacility getValue() throws RemoteException;

    void setValue(IExemptFacility exemptFacility) throws RemoteException, ConcurrentUpdateException;

    void setStatusDeleted (IExemptFacility exemptFacility) throws ConcurrentUpdateException, RemoteException;
}
