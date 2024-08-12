package com.integrosys.cms.app.custexposure.proxy.group;

import java.rmi.RemoteException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.custexposure.bus.CustExposureException;
import com.integrosys.cms.app.custexposure.trx.group.IGroupExposureTrxValue;

/**
 * Created by IntelliJ IDEA. User: JITENDRA Date: May 30, 2008 Time: 11:15:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class GroupExposureProxyImpl implements IGroupExposureProxy {

	public IGroupExposureTrxValue getGroupExposure(long groupId) throws CustExposureException {
    	 
    	 try {
    		 return getProxy().getGroupExposure(groupId);
    	 } catch (RemoteException e) {
 			DefaultLogger.error(this, "", e);
			throw new CustExposureException("RemoteException", e);    		 
    	 }
     }

	private SBGroupExposureProxy getProxy() throws CustExposureException {
		SBGroupExposureProxy home = (SBGroupExposureProxy) BeanController.getEJB(
				ICMSJNDIConstant.SB_GROUP_EXPOSURE_PROXY_JNDI,
				SBGroupExposureProxyHome.class.getName());

		if (null != home) {
			return home;
		} else {
			throw new CustExposureException("SBGroupExposureProxy is null!");
		}
	}
}
