/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/IMergeTemplateUtil.java,v 1.1 2003/07/11 01:45:26 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;



/**
 * This interface is for the purpose of externalising the way of template
 * merging. The merging is required to simulate the effect of a template
 * inheriting a template.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/11 01:45:26 $ Tag: $Name: $
 */
public interface IMergeTemplateUtil {
	/**
	 * To prepare the template for retrieval by merge the parent and child
	 * templates based on the implementation provided by the implementing class
	 * @param aParentTemplate - ITemplate
	 * @param aChildTemplate - ITemplate
	 * @return ITemplate - the template for retrieval
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException on errors
	 */
	public ITemplate prepareTemplateForRetrieval(ITemplate aParentTemplate, ITemplate aChildTemplate)
			throws CheckListTemplateException;

	/**
	 * To prepare the template for persistence based on merging the parent and
	 * child templates based on the implementation provided by the implementing
	 * class
	 * @param aParentTemplate - ITemplate
	 * @param aChildTemplate - ITemplate
	 * @return ITemplate - the template for persistence
	 * @throws CheckListTemplateException on errors
	 */
	public ITemplate prepareTemplateForPersistence(ITemplate aParentTemplate, ITemplate aChildTemplate)
			throws CheckListTemplateException;
}
