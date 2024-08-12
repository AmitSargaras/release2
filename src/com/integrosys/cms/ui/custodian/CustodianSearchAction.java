/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/CustomerSearchAction.java,v 1.7 2006/09/30 09:14:27 jzhai Exp $
 */

package com.integrosys.cms.ui.custodian;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.customer.CustomerSearchAction;

/**
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/09/30 09:14:27 $ Tag: $Name: $
 */
public class CustodianSearchAction extends CustomerSearchAction {
	
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "Inside validate Input child class");
		return CustodianSearchFormValidator.validateInput((CustodianSearchForm) aForm, locale);
	}

	
}
