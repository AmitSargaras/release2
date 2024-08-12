/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBTemplateLocal.java,v 1.2 2003/07/02 01:18:22 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//javax
import javax.ejb.EJBLocalObject;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;


/**
 * Remote interface for the template entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/02 01:18:22 $ Tag: $Name: $
 */
public interface EBTemplateLocal extends EJBLocalObject {
	/**
	 * Retrieve an instance of a template
	 * @return ITemplate - the object encapsulating the template info
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
     */
	public ITemplate getValue() throws CheckListTemplateException;

	/**
	 * Set the template object
	 * @param anITemplate - ITemplate
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 * @throws CheckListTemplateException
	 */
	public void setValue(ITemplate anITemplate) throws CheckListTemplateException, ConcurrentUpdateException;

	/**
	 * Create the child items that are under this template
	 * @param anITemplate - ITemplate
	 * @throws CheckListTemplateException
	 */
	public void createTemplateItems(ITemplate anITemplate) throws CheckListTemplateException;
}