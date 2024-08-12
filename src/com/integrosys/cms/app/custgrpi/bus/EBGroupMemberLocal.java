package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

import javax.ejb.EJBLocalObject;

public interface EBGroupMemberLocal extends EJBLocalObject {


     long getGroupMemberID() ;
     long getGroupMemberIDRef() ;

    IGroupMember getValue() throws CustGrpIdentifierException;

    void setValue(IGroupMember obj) throws CustGrpIdentifierException, ConcurrentUpdateException;

    void setStatus(String str) ;
}
