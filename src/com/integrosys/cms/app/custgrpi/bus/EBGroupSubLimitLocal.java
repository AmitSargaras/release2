package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

import javax.ejb.EJBLocalObject;

public interface EBGroupSubLimitLocal extends EJBLocalObject {


    public long getGroupSubLimitID();

    public long getGroupSubLimitIDRef();

    public IGroupSubLimit getValue() throws CustGrpIdentifierException;

    public void setValue(IGroupSubLimit obj) throws CustGrpIdentifierException, ConcurrentUpdateException;

    public void setStatus(String str) ;
}
