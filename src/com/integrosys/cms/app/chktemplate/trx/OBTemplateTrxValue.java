/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/trx/OBTemplateTrxValue.java,v 1.2 2003/07/03 01:35:40 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.trx;

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;

/**
 * This class provides the implementation for ITemplateTrxValue
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/03 01:35:40 $ Tag: $Name: $
 */
public class OBTemplateTrxValue extends OBCMSTrxValue implements ITemplateTrxValue {
	private ITemplate template = null;

	private ITemplate stagingTemplate = null;

	/**
	 * Default Constructor
	 */
	public OBTemplateTrxValue() {
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSTrxValue - ICMSTrxValue
	 */
	public OBTemplateTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Get the template busines entity
	 * 
	 * @return ITemplate
	 */
	public ITemplate getTemplate() {
		return this.template;
	}

	/**
	 * Get the staging template business entity
	 * 
	 * @return ITemplate
	 */
	public ITemplate getStagingTemplate() {
		return this.stagingTemplate;
	}

	/**
	 * Set the template business entity
	 * 
	 * @param anITemplate is of type ITemplate
	 */
	public void setTemplate(ITemplate anITemplate) {
		this.template = anITemplate;
	}

	/**
	 * Set the staging template business entity
	 * 
	 * @param anITemplate is of type ITemplate
	 */
	public void setStagingTemplate(ITemplate anITemplate) {
		this.stagingTemplate = anITemplate;
	}
}