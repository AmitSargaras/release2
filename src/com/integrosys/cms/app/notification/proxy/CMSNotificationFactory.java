/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/notification/proxy/CMSNotificationFactory.java,v 1.1 2003/08/30 14:29:19 phtan Exp $
 */

package com.integrosys.cms.app.notification.proxy;

import com.integrosys.base.techinfra.context.BeanHouse;

public class CMSNotificationFactory {
	private CMSNotificationFactory() {
	}

	public static ICMSNotificationProxy getCMSNotificationProxy() {
		return (ICMSNotificationProxy) BeanHouse.get("cmsNotificationProxy");
	}
}
