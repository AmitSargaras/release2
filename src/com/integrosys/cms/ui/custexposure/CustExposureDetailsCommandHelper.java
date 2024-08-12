package com.integrosys.cms.ui.custexposure;

import com.integrosys.cms.app.custexposure.bus.CustExposureException;
import com.integrosys.cms.app.custexposure.bus.ICustExposure;
import com.integrosys.cms.app.custexposure.proxy.CustExposureProxyFactory;
import com.integrosys.cms.app.custexposure.proxy.ICustExposureProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Build and return the Customer Exposure
 *
 */
public class CustExposureDetailsCommandHelper {

	/**
	 * Get customer exposure by subProfile Id
	 * @param trxContext
	 * @param subProfileId
	 * @return
	 */
	public ICustExposure getCustExposure(OBTrxContext trxContext, long subProfileId) {
		
		ICustExposure custExposure = null;
		ICustExposureProxy custExposureProxy = CustExposureProxyFactory.getProxy();
		
		try {
			custExposure = custExposureProxy.getCustExposure(subProfileId);
			
			return custExposure;
			
		} catch (CustExposureException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
