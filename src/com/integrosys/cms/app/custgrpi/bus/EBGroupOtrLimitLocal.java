package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

import javax.ejb.EJBLocalObject;

public interface EBGroupOtrLimitLocal extends EJBLocalObject {


    public long getGroupOtrLimitID();

    public long getGroupOtrLimitIDRef();

    public IGroupOtrLimit getValue() throws CustGrpIdentifierException;

    public void setValue(IGroupOtrLimit obj) throws CustGrpIdentifierException, ConcurrentUpdateException;

    public void setStatus(String str) ;
}