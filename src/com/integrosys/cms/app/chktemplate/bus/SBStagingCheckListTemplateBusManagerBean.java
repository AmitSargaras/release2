package com.integrosys.cms.app.chktemplate.bus;

import com.integrosys.cms.app.checklist.bus.SBCheckListBusManagerBean;

import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;

import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 16, 2008
 * Time: 6:58:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class SBStagingCheckListTemplateBusManagerBean extends SBCheckListTemplateBusManagerBean {
    /**
	 * Get the list of document items This methods applied to the actual
     * business entity only and is not implemented here
     * @param aCriteria - DocumentSearchCriteria
     * @return SearchResult - the result containing the list of document items
     *         that satisfy the criteria
     * @throws com.integrosys.base.businfra.search.SearchDAOException is error at the DAO
     * @throws java.rmi.RemoteException on errors
     */
    public SearchResult getDocumentItemList(DocumentSearchCriteria aCriteria) throws SearchDAOException,
            CheckListTemplateException {
        throw new CheckListTemplateException("This method is not applicable for staging data !!!");
    }

    /**
	 * Get a template based on the value in the template type This methods
     * applied to the actual business entity only and is not implemented here
     * @param anITemplateType - ITemplateType
     * @return ICheckList - the checklist
     * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
     */
    public ITemplate getTemplateList(ITemplateType anITemplateType) throws CheckListTemplateException {
        throw new CheckListTemplateException("This method is not applicable to staging data !!!");
    }

    /**
	 * To get the list of templates based on the criteria specified
     * @param aCriteria - TemplateSearchCriteria
     * @return SearchResult - contain the list fo templates that satisfy the
     *         criteria
     * @throws com.integrosys.base.businfra.search.SearchDAOException is errors in DAO
     */
    public SearchResult searchTemplateList(TemplateSearchCriteria aCriteria) throws SearchDAOException,
            CheckListTemplateException {
        try {
            return getEBTemplateHome().searchTemplateList(aCriteria);
        }
        catch (RemoteException ex) {
            throw new CheckListTemplateException("RemoteException enctr at searchTemplateList: " + ex.toString());
        }
    }

    /**
	 * To get the home handler for the staging document item Entity Bean
     * @return EBDocumentItemHome - the home handler for the document item
     *         entity bean
     */
    protected EBDocumentItemHome getEBDocumentItemHome() {
        EBDocumentItemHome ejbHome = (EBDocumentItemHome) BeanController.getEJBHome(
                ICMSJNDIConstant.EB_STAGING_DOCUMENT_ITEM_JNDI, EBDocumentItemHome.class.getName());
        return ejbHome;
    }

    /**
	 * To get the home handler for the staging template Entity Bean
     * @return EBTemplateHome - the home handler for the template entity bean
     */
    protected EBTemplateHome getEBTemplateHome() {
        EBTemplateHome ejbHome = (EBTemplateHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_STAGING_TEMPLATE_JNDI,
                EBTemplateHome.class.getName());
        return ejbHome;
    }
}
