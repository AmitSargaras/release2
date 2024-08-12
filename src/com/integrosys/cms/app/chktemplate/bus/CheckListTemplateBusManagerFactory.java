package com.integrosys.cms.app.chktemplate.bus;

import com.integrosys.cms.app.checklist.bus.CheckListBusManagerImpl;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 18, 2008
 * Time: 10:40:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class CheckListTemplateBusManagerFactory {

    /**
     * Default Constructor
     */
    public CheckListTemplateBusManagerFactory() {
    }

    /**
	 * Get the document bus manager.
     * @return IDocumentBusManager - the document bus manager
     */
    public static IDocumentBusManager getDocumentBusManager() {
        return new CheckListTemplateBusManagerImpl();
    }

    /**
	 * Get the template bus manager.
     * @return ITemplateBusManager - the template bus manager
     */
    public static ITemplateBusManager getTemplateBusManager() {
        return new CheckListTemplateBusManagerImpl();
    }
}
