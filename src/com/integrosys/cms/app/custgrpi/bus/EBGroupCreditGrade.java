package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

import javax.ejb.EJBObject;

public interface EBGroupCreditGrade extends EJBObject {


    long getGroupCreditGradeID() throws java.rmi.RemoteException;

    IGroupCreditGrade getValue() throws java.rmi.RemoteException;


    void setValue(IGroupCreditGrade obj) throws java.rmi.RemoteException, CustGrpIdentifierException ;
}
