/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/ITemplateBusManager.java,v 1.3 2003/07/03 01:34:30 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//java
import java.io.Serializable;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;


/**
 * This interface defines the biz services that is available for template
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/07/03 01:34:30 $ Tag: $Name: $
 */
public interface ITemplateBusManager extends Serializable {
	/**
	 * Get the list of laws and customer types
	 * @return LawSearchResultItem[] - the list of law and customer types
	 * @throws SearchDAOException on errors in DAO
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException on errors
	 */
	// public LawSearchResultItem[] getLawCustomerTypes() throws
	// SearchDAOException, CheckListTemplateException;
	/**
	 * To get the list of templates based on the criteria specified
	 * @param aCriteria - TemplateSearchCriteria
	 * @return SearchResult - contain the list fo templates that satisfy the
	 *         criteria
	 * @throws SearchDAOException is errors in DAO
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException on errors
	 */
	// public SearchResult searchTemplateList(TemplateSearchCriteria aCriteria)
	// throws SearchDAOException, CheckListTemplateException;
	/**
	 * Create a template
	 * @param anITemplate - ITemplate
	 * @return ICheckList - the template being created
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException ;
	 */
	public ITemplate create(ITemplate anITemplate) throws CheckListTemplateException;

	/**
	 * Update a checklist
	 * @param anITemplate - ITemplate
	 * @return ITemplate - the template being updated
	 * @throws CheckListTemplateException
	 */
	public ITemplate update(ITemplate anITemplate) throws ConcurrentUpdateException, CheckListTemplateException;

	/**
	 * Get a template based on the value in the template type
	 * @param anITemplateType - ITemplateType
	 * @return ICheckList - the checklist
	 * @throws CheckListTemplateException
	 */
	public ITemplate getTemplateList(ITemplateType anITemplateType) throws CheckListTemplateException;

	/**
	 * Get a template based on the value in the template type
	 * @param aTemplateID - long
	 * @return ITemplate - the biz object containing the template info
	 * @throws CheckListTemplateException
	 */
	public ITemplate getTemplateByID(long aTemplateID) throws CheckListTemplateException;

}
