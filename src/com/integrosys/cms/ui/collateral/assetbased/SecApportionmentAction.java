/*
 * Created on Jul 24, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.collateral.assetbased;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.cms.ui.collateral.secapportion.SecApportionmentActionTemplate;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SecApportionmentAction extends AssetBasedAction {
	public ICommand[] getCommandChain(String event) {
		return SecApportionmentActionTemplate.getCommandChain(event);
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return SecApportionmentActionTemplate.validateInput(aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		return SecApportionmentActionTemplate.isValidationRequired(event);
	}

	protected String getErrorEvent(String event) {
		return SecApportionmentActionTemplate.getErrorEvent(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.CommonAction#getNextPage(java.lang
	 * .String, java.util.HashMap, java.util.HashMap)
	 */
	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		return SecApportionmentActionTemplate.getNextPage(event, resultMap, exceptionMap);
	}

}
