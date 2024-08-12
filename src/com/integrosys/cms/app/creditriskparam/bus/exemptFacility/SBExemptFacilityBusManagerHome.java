/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/forex/SBExemptFacilityBusManagerHome.java,v 1.1 2003/08/11 04:08:19 btchng Exp $
*/
package com.integrosys.cms.app.creditriskparam.bus.exemptFacility;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;

/**
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
public interface SBExemptFacilityBusManagerHome
        extends EJBHome {

    public SBExemptFacilityBusManager create() throws CreateException, RemoteException;

}
