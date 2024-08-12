package com.integrosys.cms.app.custexposure.proxy.group;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.custexposure.bus.CustExposureException;
import com.integrosys.cms.app.custexposure.bus.group.IGroupExposure;
import com.integrosys.cms.app.custexposure.bus.group.SBGroupExposureBusManager;
import com.integrosys.cms.app.custexposure.bus.group.SBGroupExposureBusManagerHome;
import com.integrosys.cms.app.custexposure.trx.group.IGroupExposureTrxValue;
import com.integrosys.cms.app.custexposure.trx.group.OBGroupExposureTrxValue;

/**
 * @author skchai
 */
public class AbstractGroupExposureProxy  implements IGroupExposureProxy {


	private static final long serialVersionUID = 1L;

	public IGroupExposureTrxValue getGroupExposure(long groupId) throws CustExposureException{
        try {
              SBGroupExposureBusManager mgr = getBusManager();
              IGroupExposure groupExposure = mgr.getGroupExposure(groupId);
              IGroupExposureTrxValue trxValue = new OBGroupExposureTrxValue();
              
              // no transaction involved, not setting any trxContext
              trxValue.setGroupExposure(groupExposure);
              
              return trxValue;
              
          } catch (Exception e) {
              e.printStackTrace();
              throw new CustExposureException("Caught Exception!", e);
          }
   }

    protected SBGroupExposureBusManager getBusManager() throws CustExposureException {
        SBGroupExposureBusManager theEjb = (SBGroupExposureBusManager) BeanController.getEJB(
                ICMSJNDIConstant.SB_GROUP_EXPOSURE_BUS_MANAGER_JNDI, SBGroupExposureBusManagerHome .class.getName());

        if (theEjb == null)
            throw new CustExposureException("AbstractGroupExposureProxy is null!");

        return theEjb;
    }

}
