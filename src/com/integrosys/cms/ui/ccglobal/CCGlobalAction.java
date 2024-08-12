/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/ccglobal/CCGlobalAction.java,v 1.3 2003/08/23 07:30:45 elango Exp $
 */
package com.integrosys.cms.ui.ccglobal;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.docglobal.DocumentationGlobalAction;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/23 07:30:45 $ Tag: $Name: $
 */
public class CCGlobalAction extends DocumentationGlobalAction {

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "CCGlobalAction GO TO CCGlobalFormValidator");
		return CCGlobalFormValidator.validateInput((CCGlobalForm) aForm, locale);
	}
}