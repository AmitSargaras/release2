//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.marketablesec.marksecmainforeign;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.cms.ui.collateral.PrepareCollateralCreateCommand;
import com.integrosys.cms.ui.collateral.ReadCollateralCommand;
import com.integrosys.cms.ui.collateral.ReturnCollateralCommand;
import com.integrosys.cms.ui.collateral.marketablesec.DeletePortItemCommand;
import com.integrosys.cms.ui.collateral.marketablesec.MarketableSecAction;
import com.integrosys.cms.ui.collateral.pledge.DeletePledgeCommand;
import com.integrosys.cms.ui.collateral.pledgor.DeletePledgorCommand;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 2:49:22 PM
 * To change this template use Options | File Templates.
 */
public class MarksecMainForeignAction extends MarketableSecAction {

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
			objArray[1] = new PrepareMarksecMainForeignCommand();
		}
		else if (EVENT_PREPARE_UPDATE.equals(event) || EVENT_PROCESS_UPDATE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadCollateralCommand();
			objArray[1] = new PrepareMarksecMainForeignCommand();
		}
		else if (EVENT_PREPARE_FORM.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareMarksecMainForeignCommand();
		}
		else if (EVENT_UPDATE_RETURN.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareMarksecMainForeignCommand();
			objArray[1] = new ReturnCollateralCommand();
		}
		else if (EVENT_PROCESS_RETURN.equals(event) || EVENT_READ_RETURN.equals(event)
				|| EVENT_CLOSE_RETURN.equals(event) || EVENT_TRACK_RETURN.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReturnCollateralCommand();
		}
		else if (EVENT_DELETE_ITEM.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeletePortItemCommand();
			objArray[1] = new PrepareMarksecMainForeignCommand();
		}
		else if (EVENT_DELETE_PLEDGOR.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeletePledgorCommand();
			objArray[1] = new PrepareMarksecMainForeignCommand();
		}
		else if (EVENT_DELETE_PLEDGE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeletePledgeCommand();
			objArray[1] = new PrepareMarksecMainForeignCommand();
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
		return MarksecMainForeignValidator.validateInput((MarksecMainForeignForm) aForm, locale);
	}
	/*
	 * public IPage getNextPage(String event, HashMap resultMap, HashMap
	 * exceptionMap) { Page aPage = new Page(); if(resultMap.get("wip")!=null &&
	 * ((String)resultMap.get("wip")).equals("wip")){
	 * aPage.setPageReference("wip"); return aPage; }
	 * 
	 * if (EVENT_DELETE_ITEM.equals(event)) {
	 * DefaultLogger.debug(this,"ResultMap is :"+resultMap); String subtype =
	 * (String)resultMap.get("subtype"); if ( subtype == null ) { //todo forward
	 * to error page after populating the exceptionMap throw new
	 * RuntimeException("URL passed is wrong"); } else
	 * aPage.setPageReference("update_return"); } else if
	 * (event.endsWith(EVENT_PREPARE_UPDATE_SUB) ||
	 * event.endsWith(EVENT_PREPARE)) { String itemType =
	 * (String)resultMap.get("itemType"); if (itemType != null &&
	 * itemType.length() > 0) { aPage.setPageReference(event+"_"+itemType); }
	 * else { aPage.setPageReference(event); } } else
	 * if(EVENT_PREPARE_FORM.equals(event)){
	 * aPage.setPageReference(EVENT_PREPARE_UPDATE);
	 * 
	 * } else{ aPage.setPageReference(event); } return aPage; }
	 */
}
