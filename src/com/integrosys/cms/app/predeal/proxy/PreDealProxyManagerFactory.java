/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealProxyFactory
 *
 * Created on 5:31:51 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal.proxy;

import com.integrosys.cms.app.notification.proxy.ICMSNotificationProxy;
import com.integrosys.base.techinfra.context.BeanHouse;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 22, 2007 Time: 5:31:51 PM
 */
public class PreDealProxyManagerFactory {

	public static IPreDealProxy getIPreDealProxy() {
//		return new PreDealProxyManagerImpl();
        return (IPreDealProxy) BeanHouse.get("predealproxy");
    }

}
