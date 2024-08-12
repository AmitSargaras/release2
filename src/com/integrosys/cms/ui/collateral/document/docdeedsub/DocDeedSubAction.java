/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/document/docgencredit/PrepareDocGenCreditCommandHelper.java,v 1.2 2003/07/18 11:11:36 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.document.docdeedsub;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.cms.ui.collateral.DeleteItemsCommand;
import com.integrosys.cms.ui.collateral.PrepareCollateralCreateCommand;
import com.integrosys.cms.ui.collateral.ReadCollateralCommand;
import com.integrosys.cms.ui.collateral.ReturnCollateralCommand;
import com.integrosys.cms.ui.collateral.document.DocumentAction;
import com.integrosys.cms.ui.collateral.document.ReadDocumentCommand;
import com.integrosys.cms.ui.collateral.pledge.DeletePledgeCommand;
import com.integrosys.cms.ui.collateral.pledgor.DeletePledgorCommand;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/18 11:11:36 $ Tag: $Name: $
 */
public class DocDeedSubAction extends DocumentAction {
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[];
		if (EVENT_PREPARE_CREATE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareCollateralCreateCommand();
			objArray[1] = new PrepareDocDeedSubCommand();
		}
		else if (EVENT_PREPARE_UPDATE.equals(event) || EVENT_PROCESS_UPDATE.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new ReadCollateralCommand();
			objArray[1] = new ReadDocumentCommand();
			objArray[2] = new PrepareDocDeedSubCommand();
		}
		else if (EVENT_PREPARE_FORM.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareDocDeedSubCommand();
		}
		else if (EVENT_UPDATE_RETURN.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareDocDeedSubCommand();
			objArray[1] = new ReturnCollateralCommand();
		}
		else if (EVENT_DELETE_ITEM.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeleteItemsCommand();
			objArray[1] = new PrepareDocDeedSubCommand();
		}
		else if (EVENT_DELETE_PLEDGOR.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeletePledgorCommand();
			objArray[1] = new PrepareDocDeedSubCommand();
		}
		else if (EVENT_DELETE_PLEDGE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeletePledgeCommand();
			objArray[1] = new PrepareDocDeedSubCommand();
		}
		else if (EVENT_PROCESS_RETURN.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReturnCollateralCommand();
		}
		else {
			objArray = super.getCommandChain(event);
		}

		return objArray;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return DocDeedSubValidator.validateInput((DocDeedSubForm) aForm, locale);
	}
}
