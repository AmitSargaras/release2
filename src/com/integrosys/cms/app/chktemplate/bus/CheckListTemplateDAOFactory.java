package com.integrosys.cms.app.chktemplate.bus;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 18, 2008
 * Time: 11:11:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class CheckListTemplateDAOFactory {
    /**
	 * Get the document DAO
     * @return IDocumentDAO - the interface class for accessing the DAO
     * @throws com.integrosys.base.businfra.search.SearchDAOException
     */
    public static IDocumentDAO getDocumentDAO() throws SearchDAOException {
        return new DocumentDAO();
    }

    /**
	 * Get the template DAO
     * @return ITemplateDAO - the interface class for accessing the DAO
     * @throws com.integrosys.base.businfra.search.SearchDAOException
     */
    public static ITemplateDAO getTemplateDAO() throws SearchDAOException {
        return new TemplateDAO();
    }

    /**
	 * Get the staging template DAO
     * @return ITemplateDAO - the interface class for accessing the DAO
     * @throws com.integrosys.base.businfra.search.SearchDAOException
     */
    public static ITemplateDAO getStagingTemplateDAO() throws SearchDAOException {
        return new StagingTemplateDAO();
    }
}
