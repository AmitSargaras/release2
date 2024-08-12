package com.integrosys.cms.app.custexposure.proxy;

import java.rmi.RemoteException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.custexposure.bus.CustExposureException;
import com.integrosys.cms.app.custexposure.bus.ICustExposure;

/**
 * Customer exposure proxy implementation class
 * @author skchai
 * 
 */
public class CustExposureProxyImpl implements ICustExposureProxy {

	private static final long serialVersionUID = 1L;

	public ICustExposure getCustExposure(long subProfileId) throws CustExposureException {
    	 
    	 try {
    		 return getProxy().getCustExposure(subProfileId);
    	 } catch (RemoteException e) {
 			DefaultLogger.error(this, "", e);
			throw new CustExposureException("RemoteException", e);    		 
    	 }
     }

	private SBCustExposureProxy getProxy() throws CustExposureException {
		SBCustExposureProxy home = (SBCustExposureProxy) BeanController.getEJB(
				ICMSJNDIConstant.SB_CUST_EXPOSURE_PROXY_JNDI,
				SBCustExposureProxyHome.class.getName());

		if (null != home) {
			return home;
		} else {
			throw new CustExposureException("SBCustExposureProxy is null!");
		}
	}
}
