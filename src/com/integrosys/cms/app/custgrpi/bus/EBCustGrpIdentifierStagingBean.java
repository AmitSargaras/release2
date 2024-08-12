package com.integrosys.cms.app.custgrpi.bus;


import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

import javax.ejb.EJBException;


public abstract class EBCustGrpIdentifierStagingBean extends EBCustGrpIdentifierBean {

    protected String getSequenceName() {
        return ICMSConstant.SEQUENCE_CMS_CUST_GRP_SEQ_STAGING;
    }

    public ICustGrpIdentifier ejbHomeGetCustGrpIdentifierByGroupID(long groupID) throws SearchDAOException {

        ICustGrpIdentifier aICustGrpIdentifier = null;

        return aICustGrpIdentifier;
    }

    protected IGroupSubLimit[] retrieveGroupSubLimitListRaw() throws CustGrpIdentifierException {
        IGroupSubLimit[] obj;
        Long grpNo = getCMPGrpID();
        try {
            return getDAO().getGroupSubLimit(getCMPGrpID().longValue());
        } catch (SearchDAOException ex) {
            throw new CustGrpIdentifierException("Exception at retrieveGroupSubLimitListRaw: " + ex.toString());
        }
    }

    protected IGroupOtrLimit[] retrieveGroupOtrLimitListRaw() throws CustGrpIdentifierException {
        IGroupOtrLimit[] obj;
        Long grpNo = getCMPGrpID();
        try {
            return getDAO().getGroupOtrLimit(getCMPGrpID().longValue());
        } catch (SearchDAOException ex) {
            throw new CustGrpIdentifierException("Exception at retrieveGroupOtrLimitListRaw: " + ex.toString());
        }
    }
    /// all JNDI Names Here


    /**
     * Return default DAO
     *
     * @return ICCICustomerDAO
     */
    protected ICustGrpIdentifierDAO getDAO() {
        return new StagingCustGrpIdentifierDAO();
    }
    //end Here

    protected EBGroupSubLimitLocalHome getEBGroupSubLimitLocalHome() {
        EBGroupSubLimitLocalHome ejbHome = (EBGroupSubLimitLocalHome) BeanController.getEJBLocalHome(
                ICMSJNDIConstant.EB_GROUP_SUBLIMIT_LOCAL_STAGING_JNDI, EBGroupSubLimitLocalHome.class.getName());

        if (ejbHome == null) {
            throw new EJBException("EBGroupSubLimitLocalHome staging is Null!");
        }

        return ejbHome;
    }

    protected EBGroupOtrLimitLocalHome getEBGroupOtrLimitLocalHome() {
        EBGroupOtrLimitLocalHome ejbHome = (EBGroupOtrLimitLocalHome) BeanController.getEJBLocalHome(
                ICMSJNDIConstant.EB_GROUP_OTRLIMIT_LOCAL_STAGING_JNDI, EBGroupOtrLimitLocalHome.class.getName());

        if (ejbHome == null) {
            throw new EJBException("EBGroupOtrLimitLocalHome staging is Null!");
        }

        return ejbHome;
    }

    protected EBGroupMemberLocalHome getEBGroupMemberLocalHome() {
        EBGroupMemberLocalHome ejbHome = (EBGroupMemberLocalHome) BeanController.getEJBLocalHome(
                ICMSJNDIConstant.EB_GROUP_MEMBER_LOCAL_STAGING_JNDI, EBGroupMemberLocalHome.class.getName());

        if (ejbHome == null) {
            throw new EJBException("EBGroupMemberLocalHome staging is Null!");
        }

        return ejbHome;
    }

    protected EBGroupCreditGradeLocalHome getEBGroupCreditGradeLocalHome() {
        EBGroupCreditGradeLocalHome ejbHome = (EBGroupCreditGradeLocalHome) BeanController.getEJBLocalHome(
                ICMSJNDIConstant.EB_GROUP_CREDIT_GRADE_LOCAL_STAGING_JNDI, EBGroupCreditGradeLocalHome.class.getName());

        if (ejbHome == null) {
            throw new EJBException("EBGroupCreditGradeLocalHome Staging is Null!");
        }

        return ejbHome;
    }

    /* protected EBGroupCreditGradeHome getEBGroupCreditGradeHome() {
        EBGroupCreditGradeHome ejbHome = (EBGroupCreditGradeHome) BeanController.getEJBHome(
                ICMSJNDIConstant.EB_GROUP_CREDIT_GRADE_STAGING_JNDI, EBGroupCreditGradeHome.class.getName());

        if (ejbHome == null) {
            throw new EJBException("EBGroupCreditGradeHome staging is Null!");
        }

        return ejbHome;
    }*/
}
