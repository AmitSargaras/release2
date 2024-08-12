/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBStgDocumentItemBean.java,v 1.1 2003/06/27 10:36:23 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.chktemplate.bus.IDocumentItem;

/**
 * This entity bean represents the persistence for document item Information
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/27 10:36:23 $ Tag: $Name: $
 */
public abstract class EBStgDocAppItemBean extends EBDocAppItemBean {
	/**
	 * Get the name of the sequence to be used
	 * @return String - the name of the sequence
	 */
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_STAGE_DOC_APP_ITEM;
	}
}