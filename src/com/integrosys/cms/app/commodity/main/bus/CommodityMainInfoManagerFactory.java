/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/CommodityMainInfoManagerFactory.java,v 1.3 2004/07/15 11:35:23 cchen Exp $
 */
package com.integrosys.cms.app.commodity.main.bus;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: Mar 30, 2004 Time:
 * 11:36:16 AM To change this template use File | Settings | File Templates.
 */
public class CommodityMainInfoManagerFactory {
	public static ICommodityMainInfoManager getManager() {
		DefaultLogger.debug("IMPL -- ", "&&&&& Mgr = Original");
		return new CommodityMainInfoManagerImpl();
	}

	public static ICommodityMainInfoManager getStagingManager() {
		DefaultLogger.debug("IMPL -- ", "&&&&& Mgr = Staging");

		CommodityMainInfoManagerStagingImpl x = new CommodityMainInfoManagerStagingImpl();
		return x;
	}
}
