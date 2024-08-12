/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/forex/SBExemptFacilityProxyHome.java,v 1.2 2003/07/31 03:04:34 btchng Exp $
*/
package com.integrosys.cms.app.creditriskparam.proxy.exemptFacility;

import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/31 03:04:34 $
 * Tag: $Name:  $
 */
public interface SBExemptFacilityProxyHome
        extends EJBHome {

    public SBExemptFacilityProxy create()
            throws CreateException, RemoteException;
}
