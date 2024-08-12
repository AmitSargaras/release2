/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/forex/ExemptFacilityProxyFactory.java,v 1.2 2003/08/06 05:42:09 btchng Exp $
*/
package com.integrosys.cms.app.creditriskparam.proxy.exemptFacility;


/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/06 05:42:09 $
 * Tag: $Name:  $
 */
public class ExemptFacilityProxyFactory {

    public static IExemptFacilityProxy getProxy() {
        return new ExemptFacilityProxyImpl();
    }
}
