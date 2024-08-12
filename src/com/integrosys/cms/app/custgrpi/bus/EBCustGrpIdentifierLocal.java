package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

import javax.ejb.EJBLocalObject;


public interface EBCustGrpIdentifierLocal extends EJBLocalObject {


    public long getGrpID();

    public ICustGrpIdentifier getValue() throws CustGrpIdentifierException;

    public void setValue(ICustGrpIdentifier aICustGrpIdentifier) throws CustGrpIdentifierException, ConcurrentUpdateException;

    public void setStatus(String status);
}
