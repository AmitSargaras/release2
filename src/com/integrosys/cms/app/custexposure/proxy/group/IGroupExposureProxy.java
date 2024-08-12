package com.integrosys.cms.app.custexposure.proxy.group;

import com.integrosys.cms.app.custexposure.bus.CustExposureException;
import com.integrosys.cms.app.custexposure.trx.group.IGroupExposureTrxValue;

/**
 * Group ExposureProxy
 * @author skchai
 *
 */
public interface IGroupExposureProxy   extends java.io.Serializable {


     public IGroupExposureTrxValue getGroupExposure(long groupId)  throws CustExposureException;




}
