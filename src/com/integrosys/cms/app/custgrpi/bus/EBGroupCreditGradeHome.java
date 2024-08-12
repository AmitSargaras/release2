package com.integrosys.cms.app.custgrpi.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

public interface EBGroupCreditGradeHome extends EJBHome {

   public EBGroupCreditGrade create(IGroupCreditGrade obj) throws CreateException, RemoteException;

   public EBGroupCreditGrade findByPrimaryKey(Long pk) throws FinderException, RemoteException;


}
