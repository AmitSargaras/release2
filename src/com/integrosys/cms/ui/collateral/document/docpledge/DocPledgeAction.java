//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.document.docpledge;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
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
 * Created by IntelliJ IDEA. User: kienleong Date: 2007/02/28 To change this
 * template use Options | File Templates.
 */
public class DocPledgeAction extends DocumentAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[];
		if (EVENT_PREPARE_CREATE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareCollateralCreateCommand();
			objArray[1] = new PrepareDocPledgeCommand();
		}
		else if (EVENT_PREPARE_UPDATE.equals(event) || EVENT_PROCESS_UPDATE.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new ReadCollateralCommand();
			objArray[1] = new ReadDocumentCommand();
			objArray[2] = new PrepareDocPledgeCommand();
		}
		else if (EVENT_PREPARE_FORM.equals(event)) {
			DefaultLogger.debug("DocPledgeAction", "in prepare_form...");
			objArray = new ICommand[1];
			objArray[0] = new PrepareDocPledgeCommand();
		}
		else if (EVENT_UPDATE_RETURN.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareDocPledgeCommand();
			objArray[1] = new ReturnCollateralCommand();
		}
		else if (EVENT_DELETE_ITEM.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeleteItemsCommand();
			objArray[1] = new PrepareDocPledgeCommand();
		}
		else if (EVENT_DELETE_PLEDGOR.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeletePledgorCommand();
			objArray[1] = new PrepareDocPledgeCommand();
		}
		else if (EVENT_DELETE_PLEDGE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeletePledgeCommand();
			objArray[1] = new PrepareDocPledgeCommand();
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

	/**
	 * This method is called only for create and Update command to validate the
	 * form and return the ActionErrors object.
	 * 
	 * @param aForm is of type ActionForm
	 * @param locale of type Locale
	 * @return ActionErrors
	 */
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return DocPledgeValidator.validateInput((DocPledgeForm) aForm, locale);
	}
}
