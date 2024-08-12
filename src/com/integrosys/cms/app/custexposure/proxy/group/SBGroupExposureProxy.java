package com.integrosys.cms.app.custexposure.proxy.group;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.cms.app.custexposure.bus.CustExposureException;
import com.integrosys.cms.app.custexposure.trx.group.IGroupExposureTrxValue;

/**
 * Created by IntelliJ IDEA.
 * User: JITENDRA
 * Date: May 30, 2008
 * Time: 11:14:09 AM
 * To change this template use File | Settings | File Templates.
 */
public interface SBGroupExposureProxy extends EJBObject {


    public IGroupExposureTrxValue getGroupExposure(long groupId)  throws CustExposureException, RemoteException;
}
