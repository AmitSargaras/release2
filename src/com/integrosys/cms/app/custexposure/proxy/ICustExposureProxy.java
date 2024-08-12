package com.integrosys.cms.app.custexposure.proxy;

import com.integrosys.cms.app.custexposure.bus.CustExposureException;
import com.integrosys.cms.app.custexposure.bus.ICustExposure;

/**
 * Created by IntelliJ IDEA.
 * User: JITENDRA
 * Date: May 30, 2008
 * Time: 11:13:47 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ICustExposureProxy   extends java.io.Serializable {

     public ICustExposure getCustExposure(long subProfileId)  throws CustExposureException;

}
