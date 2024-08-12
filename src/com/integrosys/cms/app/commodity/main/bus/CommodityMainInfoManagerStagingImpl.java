/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/CommodityMainInfoManagerStagingImpl.java,v 1.4 2004/08/04 11:58:26 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus;

import java.rmi.RemoteException;
import java.sql.SQLException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.JNDIConstants;
import com.integrosys.cms.app.commodity.main.CommodityException;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: Mar 24, 2004 Time:
 * 3:45:56 PM To change this template use File | Settings | File Templates.
 */
public class CommodityMainInfoManagerStagingImpl extends CommodityMainInfoManagerImpl {
	public SearchResult searchCommodityMainInfos(CommodityMainInfoSearchCriteria criteria) throws CommodityException {
		try {
			return getManager().searchCommodityMainInfos(criteria);
		}
		catch (RemoteException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, e.toString());
			return null;
		}
	}

	protected SBCommodityMainInfoManager getManager() throws CommodityException {
		SBCommodityMainInfoManager theEjb = (SBCommodityMainInfoManager) BeanController.getEJB(
				JNDIConstants.SB_COMMODITY_MAIN_INFO_STAGING_MGR_HOME,
				JNDIConstants.SB_COMMODITY_MAIN_INFO_MGR_HOME_PATH);

		if (theEjb == null) {
			throw new CommodityException("SBCommodityMainInfoManager is not deployed. Unable to find name in JNDI!");
		}

		return theEjb;
	}

	public long getStagingWarehouseGroupIDByCountryCode(String countryCode) throws CommodityException {
		try {
			long groupID;
			DefaultLogger.debug(this, "getStagingWarehouseGroupIDByCountryCode");
			groupID = (new CommodityMainInfoDAO()).getGroupIDForStagingWarehouseByCountry(countryCode);
			DefaultLogger.error(this, "" + groupID);
			return groupID;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new CommodityException(e);
		}
	}
}
