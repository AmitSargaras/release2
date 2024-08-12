/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBStgDocumentItemBean.java,v 1.1 2003/06/27 10:36:23 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.chktemplate.bus.IDocumentItem;

/**
 * This entity bean represents the persistence for document item Information
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/27 10:36:23 $ Tag: $Name: $
 */
public abstract class EBStgDocumentItemBean extends EBDocumentItemBean {
	/**
	 * Get the name of the sequence to be used
	 * @return String - the name of the sequence
	 */
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_STAGING_DOCUMENT_ITEM;
	}

	/**
	 * This method is not implemented here as there is no need to generate the
	 * item code for staging data
	 * @param anIDocumentItem - IDocumentItem
	 * @throws Exception on errors
	 */
	protected void preCreationProcess(IDocumentItem anIDocumentItem) throws Exception {
		DefaultLogger.debug(this, "In staging PreCreationProcess !!!");
		// do nothing
	}
	
	protected EBDocAppItemLocalHome getEBDocAppItemLocalHome() throws CheckListTemplateException {
		EBDocAppItemLocalHome home = (EBDocAppItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_DOC_APP_ITEM_LOCAL_JNDI, EBDocAppItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new CheckListTemplateException("EBStagingDocAppItemLocal is null!");
	}
}