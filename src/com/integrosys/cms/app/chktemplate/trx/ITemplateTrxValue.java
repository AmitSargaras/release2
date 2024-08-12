/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/ITemplateTrxValue.java,v 1.1 2003/06/27 10:44:38 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.trx;

import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a template trx value.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/27 10:44:38 $ Tag: $Name: $
 */
public interface ITemplateTrxValue extends ICMSTrxValue {
	/**
	 * Get the template busines entity
	 * 
	 * @return ITemplate
	 */
	public ITemplate getTemplate();

	/**
	 * Get the staging template business entity
	 * 
	 * @return ITemplate
	 */
	public ITemplate getStagingTemplate();

	/**
	 * Set the template business entity
	 * 
	 * @param anITemplate is of type ITemplate
	 */
	public void setTemplate(ITemplate anITemplate);

	/**
	 * Set the staging template business entity
	 * 
	 * @param anITemplate is of type ITemplate
	 */
	public void setStagingTemplate(ITemplate anITemplate);
}