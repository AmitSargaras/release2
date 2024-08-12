/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBStgTemplateBean.java,v 1.3 2003/07/08 07:39:05 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//ofa
import java.rmi.RemoteException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.checklist.bus.*;

/**
 * Implementation for the staging template entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/07/08 07:39:05 $ Tag: $Name: $
 */

public abstract class EBStgTemplateBean extends EBTemplateBean {
	/**
	 * Get the name of the sequence to be used
	 * @return String - the name of the sequence
	 */
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_STAGING_TEMPLATE;
	}

	/**
	 * To get the list of law and the customer types
	 * @return LawSearchResultItem[] - the list of law and customer types
	 * @throws SearchDAOException if errors in DAO
	 * @throws RemoteException on remote errors
	 */
	public LawSearchResultItem[] ejbHomeGetLawCustomerTypes() throws SearchDAOException {
		throw new SearchDAOException("This method is not applicable to staging data !!!");
	}

	/**
	 * To get the list of templates based on the criteria specified
	 * @param aCriteria - TemplateSearchCriteria
	 * @return SearchResult - contain the list fo templates that satisfy the
	 *         criteria
	 * @throws SearchDAOException is errors in DAO
	 * @throws RemoteException on remote errors
	 */
	public SearchResult ejbHomeSearchTemplateList(TemplateSearchCriteria aCriteria) throws SearchDAOException {
		return CheckListTemplateDAOFactory.getStagingTemplateDAO().searchTemplateList(aCriteria);
	}

	/**
	 * This method is not implemented here as there is no need to generate the
	 * item code for staging data
	 * @param anITemplateItem - ITemplateItem
	 * @String aCountryCode - String
	 * @throws Exception on errors
	 */
	protected void preCreationProcess(ITemplateItem anITemplateItem, String aCountryCode) throws Exception {
		// do nothing
	}

	/**
	 * Method to get EB Local Home for template item
	 */
	protected EBTemplateItemLocalHome getEBTemplateItemLocalHome() throws CheckListTemplateException {
		EBTemplateItemLocalHome home = (EBTemplateItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STAGING_TEMPLATE_ITEM_LOCAL_JNDI, EBTemplateItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new CheckListTemplateException("EBTemplateItemLocal is null!");
	}
}