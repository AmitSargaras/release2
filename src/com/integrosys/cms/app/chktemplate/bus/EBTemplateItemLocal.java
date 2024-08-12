/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBTemplateItemLocal.java,v 1.3 2003/07/03 08:14:38 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;


import javax.ejb.EJBLocalObject;

/**
 * This is the local interface to the EBTemplateItem entity bean.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/07/03 08:14:38 $ Tag: $Name: $
 */
public interface EBTemplateItemLocal extends EJBLocalObject {
	/**
	 * Return the template item ID of the template item
	 * @return long - the template item ID
	 */
	public long getTemplateItemID();

	/**
	 * Return an object representation of the template item information.
	 * @return ITemplateItem
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException on errors
	 */
	public ITemplateItem getValue() throws CheckListTemplateException;

	/**
	 * Persist a template item information
	 * 
	 * @param value is of type ITemplateItem
	 */
	public void setValue(ITemplateItem value) throws CheckListTemplateException;

    public void createPropertyList(ITemplateItem templateItem) throws CheckListTemplateException;

}