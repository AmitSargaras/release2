package com.integrosys.cms.ui.collateral.cash.cashcash;

import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.PrepareCollateralCreateCommand;
import com.integrosys.cms.ui.collateral.ReadCollateralCommand;
import com.integrosys.cms.ui.collateral.ReturnCollateralCommand;
import com.integrosys.cms.ui.collateral.cash.CashAction;
import com.integrosys.cms.ui.collateral.cash.DeleteDepositCommand;
import com.integrosys.cms.ui.collateral.pledge.DeletePledgeCommand;
import com.integrosys.cms.ui.collateral.pledgor.DeletePledgorCommand;

/**
 * Created by IntelliJ IDEA. User: Naveen Date: Feb 20, 2007 Time: 2:49:22 PM To
 * change this template use Options | File Templates.
 */
public class CashCashAction extends CashAction {

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
			objArray[1] = new PrepareCashCashCommand();
		}
		else if (EVENT_PREPARE_UPDATE.equals(event) || EVENT_PROCESS_UPDATE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadCollateralCommand();
			objArray[1] = new PrepareCashCashCommand();
		}
		else if (EVENT_PREPARE_FORM.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareCashCashCommand();
		}
		else if (EVENT_UPDATE_RETURN.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareCashCashCommand();
			objArray[1] = new ReturnCollateralCommand();
		}
		else if (EVENT_PROCESS_RETURN.equals(event) || EVENT_READ_RETURN.equals(event)
				|| EVENT_CLOSE_RETURN.equals(event) || EVENT_TRACK_RETURN.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReturnCollateralCommand();
		}
		else if (EVENT_DELETE_ITEM.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeleteDepositCommand();
			objArray[1] = new PrepareCashCashCommand();
		}
		else if (EVENT_DELETE_PLEDGOR.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeletePledgorCommand();
			objArray[1] = new PrepareCashCashCommand();
		}
		else if (EVENT_DELETE_PLEDGE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeletePledgeCommand();
			objArray[1] = new PrepareCashCashCommand();
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
		return CashCashValidator.validateInput((CashCashForm) aForm, locale);
	}
}
