package com.integrosys.cms.app.cci.bus;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

import javax.ejb.EJBLocalObject;


public interface EBCCICounterpartyDetailsLocal extends EJBLocalObject {


    long getGroupCCINo();

    ICCICounterparty getValue() throws CCICounterpartyDetailsException;

    void setValue(ICCICounterparty aICCICounterparty) throws CCICounterpartyDetailsException, ConcurrentUpdateException;
}
