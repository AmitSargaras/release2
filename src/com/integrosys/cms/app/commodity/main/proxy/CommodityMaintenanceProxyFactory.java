/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/proxy/CommodityMaintenanceProxyFactory.java,v 1.2 2004/06/04 04:53:42 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.proxy;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: Mar 30, 2004 Time:
 * 11:37:34 AM To change this template use File | Settings | File Templates.
 */
public class CommodityMaintenanceProxyFactory {
	public static ICommodityMaintenanceProxy getProxy() {
		return new CommodityMaintenanceProxyImpl();
	}
}
