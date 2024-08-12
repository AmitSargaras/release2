package com.integrosys.cms.app.custexposure.proxy;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.custexposure.bus.CustExposureException;
import com.integrosys.cms.app.custexposure.bus.ICustExposure;
import com.integrosys.cms.app.custexposure.bus.SBCustExposureBusManager;
import com.integrosys.cms.app.custexposure.bus.SBCustExposureBusManagerHome;

/**
 * Abstract Customer Exposure Proxy
 * should be extended by Session Bean
 * @author skchai
 */
public class AbstractCustExposureProxy  implements ICustExposureProxy {


	private static final long serialVersionUID = 6023245644753519245L;

	/**
	 * Get Customer exposure
	 * @param customer Id 
	 * @return Customer Exposure object interface
	 */
	public ICustExposure getCustExposure(long subProfileId) throws CustExposureException{
        try {
              SBCustExposureBusManager mgr = getBusManager();

              return mgr.getCustExposure(subProfileId);
              
          } catch (Exception e) {
              e.printStackTrace();
              throw new CustExposureException("Caught Exception!", e);
          }
   }

    protected SBCustExposureBusManager getBusManager() throws CustExposureException {
        SBCustExposureBusManager theEjb = (SBCustExposureBusManager) BeanController.getEJB(
                ICMSJNDIConstant.SB_CUST_EXPOSURE_BUS_MANAGER_JNDI, SBCustExposureBusManagerHome .class.getName());

        if (theEjb == null)
            throw new CustExposureException("AbstractCustExposureProxy is null!");

        return theEjb;
    }
}
