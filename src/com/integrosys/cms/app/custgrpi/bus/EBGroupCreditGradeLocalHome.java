package com.integrosys.cms.app.custgrpi.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface EBGroupCreditGradeLocalHome extends EJBLocalHome {

   public EBGroupCreditGradeLocal create(IGroupCreditGrade obj) throws CreateException;

   public  EBGroupCreditGradeLocal findByPrimaryKey(Long pk) throws FinderException ;

}
