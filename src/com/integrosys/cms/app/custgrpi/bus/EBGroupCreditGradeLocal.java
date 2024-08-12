package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

import javax.ejb.EJBLocalObject;

public interface EBGroupCreditGradeLocal extends EJBLocalObject {



    public long getGroupCreditGradeID();

    public long getGroupCreditGradeIDRef();

    public IGroupCreditGrade getValue() ;

    public void setValue(IGroupCreditGrade obj) throws CustGrpIdentifierException;


    public void setStatus(String status);
}
