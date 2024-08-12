//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.ReadReturnCollateralCommand;

// this needs to extend from Collateral Action to fit the IPin interface. This is a workaround till IPin is removed from
// Marker interface.

public class ChequeAction extends AssetPostDatedChqsAction {
	
	private Map nameCommandMap;
	
	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public static final String RETURN_VIEW = "return_from_view";
	
	public static final String DELETE_DETAIL = "delete_cheque_detail";
	
	public static final String RETURN_VIEW_LIST = "return_view_list";
	
	public static final String EVENT_REFRESH_BRANCH_ID = "refresh_branch_id";

	public ICommand[] getCommandChain(String event) {
		ICommand objArray[];
		if (EVENT_CREATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("AddChequeCommand");
		}
		else if (EVENT_PREPARE.equals(event)) {                                                                         
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("PrepareChequeCommand");
		}
		else if (EVENT_PREPARE_UPDATE_SUB.equals(event)||MAKER_VIEW_LIST.equals(event)||VIEW_LIST.equals(event) || EVENT_READ.equals(event)||VIEW_CHEQUE_DETAIL.equals(event)||DELECT_CHEQUEDETAIL.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = (ICommand) getNameCommandMap().get("PrepareChequeCommand");
			objArray[1] = (ICommand) getNameCommandMap().get("ReadChequeCommand");
		}
		else if (EVENT_UPDATE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("UpdateChequeCommand");
		}
		else if (RETURN_VIEW.equals(event)||DELETE_DETAIL.equals(event)||RETURN_VIEW_LIST.equals(event)) {
			    objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("ViewchequeCmd");
		}
		else if (event.endsWith(CREATE_BANKDETAIL) ) {
		     objArray = new ICommand[1];
		     objArray[0] = (ICommand) getNameCommandMap().get("CreateBankDetails");
	    }
		
		else if (event.equals(EVENT_REFRESH_BRANCH_ID)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RefreshBranchCommand");
		}
		
		else if (EVENT_READ_RETURN.equals(event) || EVENT_CANCEL.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadReturnCollateralCommand();
		}else{
			objArray = new ICommand[0];
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
		DefaultLogger.debug(this, "VALIDATION REQUIRED...");
		return ChequeValidator.validateInput((ChequeForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (EVENT_CREATE.equals(event) || EVENT_UPDATE.equals(event)||EVENT_CREATE.equals(event)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if (EVENT_CREATE.equals(event)) {
			errorEvent = EVENT_PREPARE;
		}
		else if (EVENT_UPDATE.equals(event)) {
			errorEvent = EVENT_PREPARE;
		}

		return errorEvent;
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		if (EVENT_CREATE.equals(event) || EVENT_UPDATE.equals(event) || EVENT_READ_RETURN.equals(event)
				|| EVENT_CANCEL.equals(event)) {
			DefaultLogger.debug(this, "ResultMap is :" + resultMap);
			String subtype = (String) resultMap.get("subtype");
			if (subtype == null) {
				// todo forward to error page after populating the exceptionMap
				throw new RuntimeException("URL passed is wrong");
			}
			else if (EVENT_READ_RETURN.equals(event)) {
				aPage.setPageReference(subtype + "_" + (String) resultMap.get("from_event"));
			}
			else if (EVENT_CREATE.equals(event)) {
				aPage.setPageReference("return_from_cheque_detail");
			}
			else if (VIEW_CHEQUE_DETAIL.equals(event)) {
				aPage.setPageReference("view_cheque_detail");
			}
			else if (VIEW_LIST.equals(event)) {
				aPage.setPageReference("view_list");
			}
			else if (CREATE_BANKDETAIL.equals(event)) {
				aPage.setPageReference("create_bankDetail");
			}
			
			else if (EVENT_UPDATE.equals(event)) {
				aPage.setPageReference("update_pdc_detail");
			}
			else {
				aPage.setPageReference(subtype + "_update");
			}
		}
		
		else if (VIEW_CHEQUE_DETAIL.equals(event)||DELECT_CHEQUEDETAIL.equals(event)) {
			aPage.setPageReference("view_cheque_detail");
			}
		else if (RETURN_VIEW.equals(event)||DELETE_DETAIL.equals(event)) {
			aPage.setPageReference("return_from_view_page");
			}
			
			else if (RETURN_VIEW_LIST.equals(event)) {
				aPage.setPageReference("return_view_list");
				}
			else if (EVENT_REFRESH_BRANCH_ID.equals(event)) {
				aPage.setPageReference("refresh_branch_id");
				}
		
		
		else {
			aPage.setPageReference(event);
		}
		return aPage;
	}

}
