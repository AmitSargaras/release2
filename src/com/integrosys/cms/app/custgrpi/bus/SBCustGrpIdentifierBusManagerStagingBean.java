package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

import javax.ejb.EJBException;
import javax.ejb.SessionContext;

public class SBCustGrpIdentifierBusManagerStagingBean extends SBCustGrpIdentifierBusManagerBean {



      protected EBGroupCreditGradeLocalHome getEBGroupCreditGradeLocalHome() {
        EBGroupCreditGradeLocalHome ejbHome = (EBGroupCreditGradeLocalHome) BeanController.getEJBLocalHome(
                ICMSJNDIConstant.EB_GROUP_CREDIT_GRADE_LOCAL_STAGING_JNDI, EBGroupCreditGradeLocalHome.class.getName());

        if (ejbHome == null) {
            throw new EJBException("EBGroupCreditGradeLocalHome STAGING is Null!");
        }

        return ejbHome;
    }



     protected EBGroupSubLimitLocalHome getEBGroupSubLimitLocalHome() {
        EBGroupSubLimitLocalHome ejbHome = (EBGroupSubLimitLocalHome) BeanController.getEJBLocalHome(
                ICMSJNDIConstant.EB_GROUP_SUBLIMIT_LOCAL_STAGING_JNDI, EBGroupSubLimitLocalHome.class.getName());

        if (ejbHome == null) {
            throw new EJBException("EBGroupSubLimitLocalHome STAGING is Null!");
        }

        return ejbHome;
    }


     protected EBGroupCreditGradeHome getEBGroupCreditGradeHome() {
        EBGroupCreditGradeHome ejbHome = (EBGroupCreditGradeHome) BeanController.getEJBHome(
                                        ICMSJNDIConstant.EB_GROUP_CREDIT_GRADE_STAGING_JNDI,
                                        EBGroupCreditGradeHome.class.getName());
        if (ejbHome == null) {
            throw new EJBException("EBGroupCreditGradeHome STAGING is Null!");
        }
        return ejbHome;
    }


    protected EBCustGrpIdentifierHome getEBCustGrpIdentifierHome() {
        return (EBCustGrpIdentifierHome) BeanController.getEJBHome(
                ICMSJNDIConstant.EB_CUST_GRP_IDENTIFIER_STAGING_JNDI,
                EBCustGrpIdentifierHome.class.getName());
    }


    public void ejbCreate() {
    }


    public void setSessionContext(SessionContext sessionContext)
            throws EJBException {
        context = sessionContext;
    }


    public void ejbRemove()
            throws EJBException {
    }


    public void ejbActivate()
            throws EJBException {
    }


    public void ejbPassivate()
            throws EJBException {
    }


    private SessionContext context;
}
