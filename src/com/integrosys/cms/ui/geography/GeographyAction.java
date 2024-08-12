package com.integrosys.cms.ui.geography;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;

/**
 * This Action class is used for Geography master which include all commands
 * like, delete view and list.
 * 
 * @author sandiip.shinde
 * 
 */
public class GeographyAction extends CommonAction {

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public static final String EVENT_VIEW_GEOGRAPHY = "view_geography";
	public static final String EVENT_CHECKER_VIEW_GEOGRAPHY = "checker_view_geography";
	public static final String EVENT_LIST_CONTINENT = "view_list_continent";

	/*public static final String EVENT_PREPARE_LIST_COUNTRY = "prepare_list_country";

	// Country
	public static final String EVENT_LIST_COUNTRY = "view_list_country";
	public static final String EVENT_CHECKER_LIST_COUNTRY = "checker_view_list_country";
	public static final String EVENT_VIEW_COUNTRY_BY_INDEX = "view_country_by_index";
	public static final String EVENT_MAKER_PREPARE_CREATE_COUNTRY = "maker_prepare_create_country";
	public static final String EVENT_PREPARE_CREATE_COUNTRY = "prepare_create_country";
	public static final String EVENT_TOTRACK_MAKER_COUNTRY = "totrack_maker_country";
	public static final String EVENY_CHECKER_APPROVE_COUNTRY = "checker_approve_country";
	public static final String EVENY_CHECKER_REJECT_COUNTRY = "checker_reject_country";
	public static final String MAKER_CONFIRM_CLOSE = "maker_confirm_close";
	public static final String EVENT_CHECKER_PROCESS_CREATE_COUNTRY = "checker_process_create_country";
	public static final String MAKER_EDIT_COUNTRY_READ = "maker_edit_country_read";
	public static final String EVENT_PREPARE_MAKER_EDIT_COUNTRY = "prepare_maker_edit_country";
	public static final String EVENT_MAKER_EDIT_COUNTRY = "maker_edit_country";
	public static final String MAKER_DELETE_COUNTRY_READ = "maker_delete_country_read";
	public static final String MAKER_DELETE_COUNTRY = "maker_delete_country";
	public static final String EVENT_MAKER_PREPARE_ACTIVATE_COUNTRY = "maker_prepare_activate_country";
	public static final String EVENT_MAKER_ACTIVATE_COUNTRY = "maker_activate_country";
	public static final String MAKER_CONFIRM_RESUBMIT_EDIT = "maker_confirm_resubmit_edit";
	public static final String EVENT_MAKER_CREATE_COUNTRY = "maker_create_country";
	public static final String EVENT_REMOVE_COUNTRY = "prepare_remove_country";
	public static final String COMMON_SUBMIT_PAGE = "common_submit_page";
	public static final String EVENT_MAKER_RESUBMIT_CREATE_COUNTRY = "maker_resubmit_create_country";
	public static final String MAKER_PREPARE_RESUBMIT = "maker_prepare_resubmit";
	public static final String EVENT_CHECKER_PROCESS_EDIT = "checker_process_edit";
	public static final String EVENT_MAKER_PREPARE_CLOSE = "maker_prepare_close";

	public static final String EVENT_MAKER_SAVE_COUNTRY = "maker_save_country";
	public static final String EVENT_TODO_MAKER_SAVE_CREATE_COUNTRY = "todo_maker_save_create_country";
	public static final String EVENT_MAKER_CREATE_SAVED_COUNTRY = "maker_create_saved_country";
	public static final String EVENT_MAKER_SAVE_EDIT_COUNTRY = "maker_save_edit_country";

	// Region
	public static final String EVENT_REMOVE_REGION = "prepare_remove_region";
	
	public static final String EVENT_LIST_REGION = "view_list_region";
	public static final String EVENT_CHECKER_LIST_REGION = "checker_view_list_region";
	public static final String EVENT_VIEW_REGION_BY_INDEX = "view_region_by_index";
	public static final String EVENT_PREPARE_MAKER_EDIT_REGION = "prepare_maker_edit_region";
	public static final String EVENT_PREPARE_CREATE_REGION = "prepare_create_region";
	public static final String EVENT_TOTRACK_MAKER_REGION = "totrack_maker_region";
	public static final String EVENY_CHECKER_APPROVE_REGION = "checker_approve_region";
	public static final String EVENY_CHECKER_REJECT_REGION = "checker_reject_region";
	public static final String MAKER_CONFIRM_CLOSE_REGION = "maker_confirm_close_region";
	public static final String EVENT_CHECKER_PROCESS_CREATE_REGION = "checker_process_create_region";
	public static final String MAKER_EDIT_REGION_READ = "maker_edit_region_read";
	public static final String EVENT_MAKER_EDIT_REGION = "maker_edit_region";
	public static final String MAKER_DELETE_REGION_READ = "maker_delete_region_read";
	public static final String MAKER_DELETE_REGION = "maker_delete_region";
	public static final String EVENT_MAKER_PREPARE_ACTIVATE_REGION = "maker_prepare_activate_region";
	public static final String EVENT_MAKER_ACTIVATE_REGION = "maker_activate_region";
	public static final String MAKER_CONFIRM_RESUBMIT_EDIT_REGION = "maker_confirm_resubmit_edit_region";
	public static final String EVENT_MAKER_CREATE_REGION = "maker_create_region";
	public static final String EVENT_MAKER_RESUBMIT_CREATE_REGION = "maker_resubmit_create_region";
	public static final String EVENT_MAKER_PREPARE_CLOSE_REGION = "maker_prepare_close_region";
	public static final String EVENT_MAKER_SAVE_REGION = "maker_save_region";
	public static final String EVENT_TODO_MAKER_SAVE_CREATE_REGION = "todo_maker_save_create_region";
	public static final String EVENT_MAKER_CREATE_SAVED_REGION = "maker_create_saved_region";
	public static final String EVENT_MAKER_SAVE_EDIT_REGION = "maker_save_edit_region";

	// State
	public static final String EVENT_PREPARE_LIST_STATE = "prepare_list_state";
	public static final String EVENT_REMOVE_STATE = "prepare_remove_state";

	public static final String EVENT_LIST_STATE = "view_list_state";
	public static final String EVENT_CHECKER_LIST_STATE = "checker_view_list_state";
	public static final String EVENT_VIEW_STATE_BY_INDEX = "view_state_by_index";
	public static final String EVENT_PREPARE_MAKER_EDIT_STATE = "prepare_maker_edit_state";
	public static final String EVENT_PREPARE_CREATE_STATE = "prepare_create_state";
	public static final String EVENT_TOTRACK_MAKER_STATE = "totrack_maker_state";
	public static final String EVENY_CHECKER_APPROVE_STATE = "checker_approve_state";
	public static final String EVENY_CHECKER_REJECT_STATE = "checker_reject_state";
	public static final String MAKER_CONFIRM_CLOSE_STATE = "maker_confirm_close_state";
	public static final String EVENT_CHECKER_PROCESS_CREATE_STATE = "checker_process_create_state";
	public static final String MAKER_EDIT_STATE_READ = "maker_edit_state_read";
	public static final String EVENT_MAKER_EDIT_STATE = "maker_edit_state";
	public static final String MAKER_DELETE_STATE_READ = "maker_delete_state_read";
	public static final String MAKER_DELETE_STATE = "maker_delete_state";
	public static final String EVENT_MAKER_PREPARE_ACTIVATE_STATE = "maker_prepare_activate_state";
	public static final String EVENT_MAKER_ACTIVATE_STATE = "maker_activate_state";
	public static final String MAKER_CONFIRM_RESUBMIT_EDIT_STATE = "maker_confirm_resubmit_edit_state";
	public static final String EVENT_MAKER_CREATE_STATE = "maker_create_state";
	public static final String EVENT_MAKER_RESUBMIT_CREATE_STATE = "maker_resubmit_create_state";
	public static final String EVENT_MAKER_PREPARE_CLOSE_STATE = "maker_prepare_close_state";
	public static final String EVENT_REFRESH_REGION_ID = "refresh_region_id";
	public static final String EVENT_REFRESH_STATE_ID = "refresh_state_id";
	public static final String EVENT_REFRESH_CITY_LIST = "refresh_city_list";
	public static final String EVENT_MAKER_SAVE_STATE = "maker_save_state";
	public static final String EVENT_TODO_MAKER_SAVE_CREATE_STATE = "todo_maker_save_create_state";
	public static final String EVENT_MAKER_CREATE_SAVED_STATE = "maker_create_saved_state";
	public static final String EVENT_MAKER_SAVE_EDIT_STATE = "maker_save_edit_state";

	// City
	public static final String EVENT_PREPARE_LIST_CITY = "prepare_list_city";
	public static final String EVENT_REMOVE_CITY = "prepare_remove_city";

	public static final String EVENT_LIST_CITY = "view_list_city";
	public static final String EVENT_CHECKER_LIST_CITY = "checker_view_list_city";
	public static final String EVENT_VIEW_CITY_BY_INDEX = "view_city_by_index";
	public static final String EVENT_PREPARE_MAKER_EDIT_CITY = "prepare_maker_edit_city";
	public static final String EVENT_PREPARE_CREATE_CITY = "prepare_create_city";
	public static final String EVENT_TOTRACK_MAKER_CITY = "totrack_maker_city";
	public static final String EVENY_CHECKER_APPROVE_CITY = "checker_approve_city";
	public static final String EVENY_CHECKER_REJECT_CITY = "checker_reject_city";
	public static final String MAKER_CONFIRM_CLOSE_CITY = "maker_confirm_close_city";
	public static final String EVENT_CHECKER_PROCESS_CREATE_CITY = "checker_process_create_city";
	public static final String MAKER_EDIT_CITY_READ = "maker_edit_city_read";
	public static final String EVENT_MAKER_EDIT_CITY = "maker_edit_city";
	public static final String MAKER_DELETE_CITY_READ = "maker_delete_city_read";
	public static final String MAKER_DELETE_CITY = "maker_delete_city";
	public static final String EVENT_MAKER_PREPARE_ACTIVATE_CITY = "maker_prepare_activate_city";
	public static final String EVENT_MAKER_ACTIVATE_CITY = "maker_activate_city";
	public static final String MAKER_CONFIRM_RESUBMIT_EDIT_CITY = "maker_confirm_resubmit_edit_city";
	public static final String EVENT_MAKER_CREATE_CITY = "maker_create_city";
	public static final String EVENT_MAKER_RESUBMIT_CREATE_CITY = "maker_resubmit_create_city";
	public static final String EVENT_MAKER_PREPARE_CLOSE_CITY = "maker_prepare_close_city";
	public static final String EVENT_MAKER_SAVE_CITY = "maker_save_city";
	public static final String EVENT_TODO_MAKER_SAVE_CREATE_CITY = "todo_maker_save_create_city";
	public static final String EVENT_MAKER_CREATE_SAVED_CITY = "maker_create_saved_city";
	public static final String EVENT_MAKER_SAVE_EDIT_CITY = "maker_save_edit_city";
	public static final String EVENT_TODO_MAKER_SAVE_EDITED_CITY = "todo_maker_save_edited_city";
*/
	protected ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, " Event : -----> " + event);
		ICommand objArray[] = null;
		if (event.equals(EVENT_VIEW_GEOGRAPHY)
				|| event.equals(EVENT_CHECKER_VIEW_GEOGRAPHY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ViewGeographyCommand");
		} else if (event.equals(EVENT_LIST_CONTINENT)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ListContinentCommand");
		} /*else if (event.equals(EVENT_PREPARE_CREATE_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareCreateCountryCommand");
		}else if (event.equals(EVENT_PREPARE_LIST_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"PrepareListCountryCommand");
		} else if (event.equals(EVENT_LIST_COUNTRY) || event.equals(EVENT_CHECKER_LIST_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ListCountryCommand");
		} else if (event.equals(EVENT_VIEW_COUNTRY_BY_INDEX)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ViewCountryByIndexCommand");
		}
		else if (event.equals(MAKER_CONFIRM_CLOSE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCloseCountryCommand");
		}

		else if (event.equals(EVENT_PREPARE_MAKER_EDIT_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareEditCountryCommand");
		} else if (event.equals(EVENT_MAKER_EDIT_COUNTRY)
				|| (event.equals(MAKER_CONFIRM_RESUBMIT_EDIT) || (event
						.equals(EVENT_MAKER_SAVE_EDIT_COUNTRY)))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditCountryCommand");
		}

		else if (event.equals(MAKER_EDIT_COUNTRY_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadCountryCommand");
		} else if (event.equals(MAKER_DELETE_COUNTRY_READ)
				|| event.equals(EVENT_MAKER_PREPARE_ACTIVATE_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareDeleteCountryCommand");
		} else if (event.equals(MAKER_DELETE_COUNTRY)
				|| event.equals(EVENT_MAKER_ACTIVATE_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerDeleteCountryCommand");
		}

		else if (event.equals(EVENT_TOTRACK_MAKER_COUNTRY)
				|| event.equals(EVENT_CHECKER_PROCESS_CREATE_COUNTRY)
				|| event.equals(EVENT_MAKER_PREPARE_CLOSE)
				|| event.equals(EVENT_MAKER_RESUBMIT_CREATE_COUNTRY)
				|| event.equals(EVENT_TODO_MAKER_SAVE_CREATE_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadCountryCommand");
		}

		else if (event.equals(EVENT_MAKER_PREPARE_CREATE_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareCreateCountryCommand");
		} else if (event.equals(EVENT_MAKER_CREATE_COUNTRY)
				|| event.equals(EVENT_MAKER_CREATE_SAVED_COUNTRY)
				|| event.equals(EVENT_MAKER_SAVE_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCreateCountryCommand");
		}

		else if ((event != null) && event.equals(EVENY_CHECKER_APPROVE_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveCountryCommmand");
		} else if ((event != null)
				&& event.equals(EVENY_CHECKER_REJECT_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectCountryCommmand");
		}

		else if (event.equals(EVENT_REMOVE_COUNTRY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RemoveCountryCommand");
		} else if (event.equals(EVENT_LIST_REGION) || event.equals(EVENT_CHECKER_LIST_REGION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ListRegionCommand");
		} else if (event.equals(EVENT_REMOVE_REGION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RemoveRegionCommand");
		} else if (event.equals(EVENT_PREPARE_LIST_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"PrepareListStateCommand");
		} else if (event.equals(EVENT_LIST_STATE) || event.equals(EVENT_CHECKER_LIST_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap()
					.get("ListStateCommand");
		} else if (event.equals(EVENT_REMOVE_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RemoveStateCommand");
		} else if (event.equals(EVENT_PREPARE_LIST_CITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"PrepareListCityCommand");
		} else if (event.equals(EVENT_LIST_CITY) || event.equals(EVENT_CHECKER_LIST_CITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("ListCityCommand");
		} else if (event.equals(EVENT_REMOVE_CITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RemoveCityCommand");
		}

		// Region

		else if (event.equals(EVENT_VIEW_REGION_BY_INDEX)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ViewRegionByIndexCommand");
		} else if (event.equals(MAKER_CONFIRM_CLOSE_REGION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCloseRegionCommand");
		}

		else if (event.equals(EVENT_PREPARE_MAKER_EDIT_REGION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareEditRegionCommand");
		} else if (event.equals(EVENT_MAKER_EDIT_REGION)
				|| (event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_REGION) || (event
						.equals(EVENT_MAKER_SAVE_EDIT_REGION)))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditRegionCommand");
		}

		else if (event.equals(MAKER_EDIT_REGION_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadRegionCommand");
		} else if (event.equals(MAKER_DELETE_REGION_READ)
				|| event.equals(EVENT_MAKER_PREPARE_ACTIVATE_REGION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareDeleteRegionCommand");
		} else if (event.equals(MAKER_DELETE_REGION)
				|| event.equals(EVENT_MAKER_ACTIVATE_REGION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerDeleteRegionCommand");
		} else if (event.equals(EVENT_TOTRACK_MAKER_REGION)
				|| event.equals(EVENT_CHECKER_PROCESS_CREATE_REGION)
				|| event.equals(EVENT_MAKER_PREPARE_CLOSE_REGION)
				|| event.equals(EVENT_MAKER_RESUBMIT_CREATE_REGION)
				|| event.equals(EVENT_TODO_MAKER_SAVE_CREATE_REGION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadRegionCommand");
		}

		else if (event.equals(EVENT_PREPARE_CREATE_REGION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareCreateRegionCommand");
		} else if (event.equals(EVENT_MAKER_CREATE_REGION)
				|| event.equals(EVENT_MAKER_CREATE_SAVED_REGION)
				|| event.equals(EVENT_MAKER_SAVE_REGION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCreateRegionCommand");
		}

		else if ((event != null) && event.equals(EVENY_CHECKER_APPROVE_REGION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveRegionCommmand");
		} else if ((event != null) && event.equals(EVENY_CHECKER_REJECT_REGION)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectRegionCommmand");
		}

		// State

		else if (event.equals(EVENT_VIEW_STATE_BY_INDEX)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ViewStateByIndexCommand");
		} else if (event.equals(MAKER_CONFIRM_CLOSE_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCloseStateCommand");
		}

		else if (event.equals(EVENT_PREPARE_MAKER_EDIT_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareEditStateCommand");
		} else if (event.equals(EVENT_MAKER_EDIT_STATE)
				|| (event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_STATE) || (event
						.equals(EVENT_MAKER_SAVE_EDIT_STATE)))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditStateCommand");
		}

		else if (event.equals(MAKER_EDIT_STATE_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadStateCommand");
		} else if (event.equals(MAKER_DELETE_STATE_READ)
				|| event.equals(EVENT_MAKER_PREPARE_ACTIVATE_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareDeleteStateCommand");
		} else if (event.equals(MAKER_DELETE_STATE)
				|| event.equals(EVENT_MAKER_ACTIVATE_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerDeleteStateCommand");
		} else if (event.equals(EVENT_TOTRACK_MAKER_STATE)
				|| event.equals(EVENT_CHECKER_PROCESS_CREATE_STATE)
				|| event.equals(EVENT_MAKER_PREPARE_CLOSE_STATE)
				|| event.equals(EVENT_MAKER_RESUBMIT_CREATE_STATE)
				|| event.equals(EVENT_TODO_MAKER_SAVE_CREATE_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadStateCommand");
		}

		else if (event.equals(EVENT_PREPARE_CREATE_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareCreateStateCommand");
		} else if (event.equals(EVENT_MAKER_CREATE_STATE)
				|| event.equals(EVENT_MAKER_CREATE_SAVED_STATE)
				|| event.equals(EVENT_MAKER_SAVE_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCreateStateCommand");
		}
		else if ((event != null) && event.equals(EVENY_CHECKER_APPROVE_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveStateCommmand");
		} else if ((event != null) && event.equals(EVENY_CHECKER_REJECT_STATE)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectStateCommmand");
		}
		else if (event.equals(EVENT_REFRESH_CITY_LIST)
				|| event.equals(EVENT_REFRESH_REGION_ID)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RefreshStateCommmand");
		}

		// City

		else if (event.equals(EVENT_VIEW_CITY_BY_INDEX)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"ViewCityByIndexCommand");
		} else if (event.equals(MAKER_CONFIRM_CLOSE_CITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCloseCityCommand");
		}

		else if (event.equals(EVENT_PREPARE_MAKER_EDIT_CITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareEditCityCommand");
		} else if (event.equals(EVENT_MAKER_EDIT_CITY)
				|| (event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_CITY) || event
						.equals(EVENT_MAKER_SAVE_EDIT_CITY))) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerEditCityCommand");
		}

		else if (event.equals(MAKER_EDIT_CITY_READ)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerReadCityCommand");
		} else if (event.equals(MAKER_DELETE_CITY_READ)
				|| event.equals(EVENT_MAKER_PREPARE_ACTIVATE_CITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareDeleteCityCommand");
		} else if (event.equals(MAKER_DELETE_CITY)
				|| event.equals(EVENT_MAKER_ACTIVATE_CITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerDeleteCityCommand");
		} else if (event.equals(EVENT_TOTRACK_MAKER_CITY)
				|| event.equals(EVENT_CHECKER_PROCESS_CREATE_CITY)
				|| event.equals(EVENT_MAKER_PREPARE_CLOSE_CITY)
				|| event.equals(EVENT_MAKER_RESUBMIT_CREATE_CITY)
				|| event.equals(EVENT_TODO_MAKER_SAVE_CREATE_CITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerReadCityCommand");
		}

		else if (event.equals(EVENT_PREPARE_CREATE_CITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerPrepareCreateCityCommand");
		} else if (event.equals(EVENT_MAKER_CREATE_CITY)
				|| event.equals(EVENT_MAKER_CREATE_SAVED_CITY)
				|| event.equals(EVENT_MAKER_SAVE_CITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"MakerCreateCityCommand");
		}

		else if ((event != null) && event.equals(EVENY_CHECKER_APPROVE_CITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerApproveCityCommmand");
		} else if ((event != null) && event.equals(EVENY_CHECKER_REJECT_CITY)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"CheckerRejectCityCommmand");
		}

		else if (event.equals(EVENT_REFRESH_STATE_ID)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get(
					"RefreshCityCommmand");
		}*/
		return objArray;
	}
	
	
	protected IPage getNextPage(String event, HashMap resultMap,
			HashMap exceptionMap) {
		Page aPage = new Page();
		if ((resultMap.get("wip") != null)
				&& ((String) resultMap.get("wip")).equals("wip"))
			aPage.setPageReference("work_in_process");
		else
			aPage.setPageReference(getReference(event));
		return aPage;
	}

	private String getReference(String event) {
		String forwardName = null;
		
		if (event.equals(EVENT_VIEW_GEOGRAPHY)) {
			forwardName = EVENT_VIEW_GEOGRAPHY;
		}else if (event.equals(EVENT_CHECKER_VIEW_GEOGRAPHY)) {
			forwardName = EVENT_CHECKER_VIEW_GEOGRAPHY;
		} else if (event.equals(EVENT_LIST_CONTINENT)) {
			forwardName = EVENT_LIST_CONTINENT;
		} /*else if (event.equals(EVENT_PREPARE_LIST_COUNTRY)) {
			forwardName = EVENT_PREPARE_LIST_COUNTRY;
		} else if (event.equals(EVENT_LIST_COUNTRY)) {
			forwardName = EVENT_LIST_COUNTRY;
		} else if (event.equals(EVENT_CHECKER_LIST_COUNTRY)) {
			forwardName = EVENT_CHECKER_LIST_COUNTRY;
		} else if (event.equals(EVENT_REMOVE_COUNTRY)) {
			forwardName = EVENT_REMOVE_COUNTRY;
		} else if (event.equals(EVENT_LIST_REGION)) {
			forwardName = EVENT_LIST_REGION;
		} else if (event.equals(EVENT_CHECKER_LIST_REGION)) {
			forwardName = EVENT_CHECKER_LIST_REGION;
		}else if (event.equals(EVENT_REMOVE_REGION)) {
			forwardName = EVENT_REMOVE_REGION;
		} else if (event.equals(EVENT_PREPARE_LIST_STATE)) {
			forwardName = EVENT_PREPARE_LIST_STATE;
		} else if (event.equals(EVENT_LIST_STATE)) {
			forwardName = EVENT_LIST_STATE;
		} else if (event.equals(EVENT_CHECKER_LIST_STATE)) {
			forwardName = EVENT_CHECKER_LIST_STATE;
		} else if (event.equals(EVENT_REMOVE_STATE)) {
			forwardName = EVENT_REMOVE_STATE;
		} else if (event.equals(EVENT_PREPARE_LIST_CITY)) {
			forwardName = EVENT_PREPARE_LIST_CITY;
		} else if (event.equals(EVENT_LIST_CITY)) {
			forwardName = EVENT_LIST_CITY;
		} else if (event.equals(EVENT_CHECKER_LIST_CITY)) {
			forwardName = EVENT_CHECKER_LIST_CITY;
		}else if (event.equals(EVENT_REMOVE_CITY)) {
			forwardName = EVENT_REMOVE_CITY;
		} else if (event.equals(EVENT_VIEW_COUNTRY_BY_INDEX)) {
			forwardName = EVENT_VIEW_COUNTRY_BY_INDEX;
		} else if (event.equals(MAKER_EDIT_COUNTRY_READ)) {
			forwardName = MAKER_EDIT_COUNTRY_READ;
		} else if (event.equals(EVENT_VIEW_REGION_BY_INDEX)) {
			forwardName = EVENT_VIEW_REGION_BY_INDEX;
		} else if (event.equals(EVENT_VIEW_STATE_BY_INDEX)) {
			forwardName = EVENT_VIEW_STATE_BY_INDEX;
		} else if (event.equals(EVENT_VIEW_CITY_BY_INDEX)) {
			forwardName = EVENT_VIEW_CITY_BY_INDEX;
		} else if (event.equals(EVENT_MAKER_PREPARE_CREATE_COUNTRY)) {
			forwardName = EVENT_MAKER_PREPARE_CREATE_COUNTRY;
		} else if (event.equals(EVENT_MAKER_CREATE_COUNTRY)) {
			forwardName = EVENT_MAKER_CREATE_COUNTRY;
		}

		else if (event.equals(EVENT_TOTRACK_MAKER_COUNTRY)) {
			forwardName = EVENT_TOTRACK_MAKER_COUNTRY;
		} else if (event.equals(EVENT_PREPARE_CREATE_COUNTRY)) {
			forwardName = EVENT_PREPARE_CREATE_COUNTRY;
		} else if (event.equals(EVENY_CHECKER_APPROVE_COUNTRY)) {
			forwardName = EVENY_CHECKER_APPROVE_COUNTRY;
		} else if (event.equals(EVENY_CHECKER_REJECT_COUNTRY)) {
			forwardName = EVENY_CHECKER_REJECT_COUNTRY;
		} else if (event.equals(EVENY_CHECKER_REJECT_COUNTRY)) {
			forwardName = EVENY_CHECKER_REJECT_COUNTRY;
		} else if (event.equals(EVENT_CHECKER_PROCESS_CREATE_COUNTRY)) {
			forwardName = EVENT_CHECKER_PROCESS_CREATE_COUNTRY;
		} else if (event.equals(EVENT_TODO_MAKER_SAVE_CREATE_COUNTRY)) {
			forwardName = EVENT_TODO_MAKER_SAVE_CREATE_COUNTRY;
		} else if (event.equals(EVENT_PREPARE_MAKER_EDIT_COUNTRY)) {
			forwardName = EVENT_PREPARE_MAKER_EDIT_COUNTRY;
		} else if (event.equals(EVENT_MAKER_EDIT_COUNTRY)) {
			forwardName = EVENT_MAKER_EDIT_COUNTRY;
		} else if (EVENT_MAKER_PREPARE_CLOSE.equals(event)) {
			forwardName = EVENT_MAKER_PREPARE_CLOSE;
		} else if (MAKER_DELETE_COUNTRY_READ.equals(event)) {
			forwardName = MAKER_DELETE_COUNTRY_READ;
		} else if (MAKER_DELETE_COUNTRY.equals(event)) {
			forwardName = MAKER_DELETE_COUNTRY;
		}

		else if (EVENT_MAKER_PREPARE_ACTIVATE_COUNTRY.equals(event)) {
			forwardName = EVENT_MAKER_PREPARE_ACTIVATE_COUNTRY;
		} else if (EVENT_MAKER_ACTIVATE_COUNTRY.equals(event)) {
			forwardName = EVENT_MAKER_ACTIVATE_COUNTRY;
		}

		else if (event.equals(EVENT_MAKER_RESUBMIT_CREATE_COUNTRY)) {
			forwardName = MAKER_PREPARE_RESUBMIT;
		} else if (event.equals(MAKER_CONFIRM_CLOSE)) {
			forwardName = MAKER_CONFIRM_CLOSE;
		}

		// Region

		else if (event.equals(EVENT_MAKER_CREATE_REGION)) {
			forwardName = EVENT_MAKER_CREATE_REGION;
		} else if (event.equals(EVENT_TOTRACK_MAKER_REGION)) {
			forwardName = EVENT_TOTRACK_MAKER_REGION;
		} else if (event.equals(EVENT_PREPARE_CREATE_REGION)) {
			forwardName = EVENT_PREPARE_CREATE_REGION;
		} else if (event.equals(EVENY_CHECKER_APPROVE_REGION)) {
			forwardName = EVENY_CHECKER_APPROVE_REGION;
		} else if (event.equals(EVENY_CHECKER_REJECT_REGION)) {
			forwardName = EVENY_CHECKER_REJECT_REGION;
		} else if (event.equals(EVENY_CHECKER_REJECT_REGION)) {
			forwardName = EVENY_CHECKER_REJECT_REGION;
		} else if (event.equals(EVENT_CHECKER_PROCESS_CREATE_REGION)) {
			forwardName = EVENT_CHECKER_PROCESS_CREATE_REGION;
		} else if (event.equals(EVENT_TODO_MAKER_SAVE_CREATE_REGION)) {
			forwardName = EVENT_TODO_MAKER_SAVE_CREATE_REGION;
		} else if (event.equals(EVENT_PREPARE_MAKER_EDIT_REGION)) {
			forwardName = EVENT_PREPARE_MAKER_EDIT_REGION;
		} else if (event.equals(EVENT_MAKER_EDIT_REGION)) {
			forwardName = EVENT_MAKER_EDIT_REGION;
		} else if (event.equals(EVENT_MAKER_PREPARE_CLOSE_REGION)) {
			forwardName = EVENT_MAKER_PREPARE_CLOSE_REGION;
		} else if (MAKER_DELETE_REGION_READ.equals(event)) {
			forwardName = MAKER_DELETE_REGION_READ;
		} else if (MAKER_DELETE_REGION.equals(event)) {
			forwardName = MAKER_DELETE_REGION;
		} else if (EVENT_MAKER_PREPARE_ACTIVATE_REGION.equals(event)) {
			forwardName = EVENT_MAKER_PREPARE_ACTIVATE_REGION;
		} else if (EVENT_MAKER_ACTIVATE_REGION.equals(event)) {
			forwardName = EVENT_MAKER_ACTIVATE_REGION;
		} else if (event.equals(EVENT_MAKER_RESUBMIT_CREATE_REGION)) {
			forwardName = MAKER_PREPARE_RESUBMIT;
		} else if (event.equals(MAKER_CONFIRM_CLOSE_REGION)) {
			forwardName = MAKER_CONFIRM_CLOSE_REGION;
		}

		// State

		else if (event.equals(EVENT_MAKER_CREATE_STATE)) {
			forwardName = EVENT_MAKER_CREATE_STATE;
		} else if (event.equals(EVENT_TOTRACK_MAKER_STATE)) {
			forwardName = EVENT_TOTRACK_MAKER_STATE;
		} else if (event.equals(EVENT_PREPARE_CREATE_STATE)) {
			forwardName = EVENT_PREPARE_CREATE_STATE;
		} else if (event.equals(EVENY_CHECKER_APPROVE_STATE)) {
			forwardName = EVENY_CHECKER_APPROVE_STATE;
		} else if (event.equals(EVENY_CHECKER_REJECT_STATE)) {
			forwardName = EVENY_CHECKER_REJECT_STATE;
		} else if (event.equals(EVENY_CHECKER_REJECT_STATE)) {
			forwardName = EVENY_CHECKER_REJECT_STATE;
		} else if (event.equals(EVENT_CHECKER_PROCESS_CREATE_STATE)) {
			forwardName = EVENT_CHECKER_PROCESS_CREATE_STATE;
		} else if (event.equals(EVENT_TODO_MAKER_SAVE_CREATE_STATE)) {
			forwardName = EVENT_TODO_MAKER_SAVE_CREATE_STATE;
		} else if (event.equals(EVENT_PREPARE_MAKER_EDIT_STATE)) {
			forwardName = EVENT_PREPARE_MAKER_EDIT_STATE;
		} else if (event.equals(EVENT_MAKER_EDIT_STATE)) {
			forwardName = EVENT_MAKER_EDIT_STATE;
		} else if (event.equals(EVENT_MAKER_PREPARE_CLOSE_STATE)) {
			forwardName = EVENT_MAKER_PREPARE_CLOSE_STATE;
		} else if (MAKER_DELETE_STATE_READ.equals(event)) {
			forwardName = MAKER_DELETE_STATE_READ;
		} else if (MAKER_DELETE_STATE.equals(event)) {
			forwardName = MAKER_DELETE_STATE;
		} else if (EVENT_MAKER_PREPARE_ACTIVATE_STATE.equals(event)) {
			forwardName = EVENT_MAKER_PREPARE_ACTIVATE_STATE;
		} else if (EVENT_MAKER_ACTIVATE_STATE.equals(event)) {
			forwardName = EVENT_MAKER_ACTIVATE_STATE;
		} else if (event.equals(EVENT_MAKER_RESUBMIT_CREATE_STATE)) {
			forwardName = MAKER_PREPARE_RESUBMIT;
		} else if (event.equals(MAKER_CONFIRM_CLOSE_STATE)) {
			forwardName = MAKER_CONFIRM_CLOSE_STATE;
		} else if (event.equals(EVENT_REFRESH_REGION_ID)) {
			forwardName = EVENT_REFRESH_REGION_ID;
		} else if (event.equals(EVENT_REFRESH_STATE_ID)) {
			forwardName = EVENT_REFRESH_STATE_ID;
		} else if (event.equals(EVENT_REFRESH_CITY_LIST)) {
			forwardName = EVENT_REFRESH_CITY_LIST;
		}

		// City

		else if (event.equals(EVENT_MAKER_CREATE_CITY)) {
			forwardName = EVENT_MAKER_CREATE_CITY;
		} else if (event.equals(EVENT_MAKER_CREATE_SAVED_CITY)
				|| event.equals(EVENT_MAKER_CREATE_SAVED_REGION)
				|| event.equals(EVENT_MAKER_CREATE_SAVED_STATE)
				|| event.equals(EVENT_MAKER_CREATE_SAVED_COUNTRY)) {
			forwardName = COMMON_SUBMIT_PAGE;
		}

		else if (event.equals(EVENT_TOTRACK_MAKER_CITY)) {
			forwardName = EVENT_TOTRACK_MAKER_CITY;
		} else if (event.equals(EVENT_PREPARE_CREATE_CITY)) {
			forwardName = EVENT_PREPARE_CREATE_CITY;
		} else if (event.equals(EVENY_CHECKER_APPROVE_CITY)) {
			forwardName = EVENY_CHECKER_APPROVE_CITY;
		} else if (event.equals(EVENY_CHECKER_REJECT_CITY)) {
			forwardName = EVENY_CHECKER_REJECT_CITY;
		} else if (event.equals(EVENY_CHECKER_REJECT_CITY)) {
			forwardName = EVENY_CHECKER_REJECT_CITY;
		} else if (event.equals(EVENT_CHECKER_PROCESS_CREATE_CITY)) {
			forwardName = EVENT_CHECKER_PROCESS_CREATE_CITY;
		} else if (event.equals(EVENT_TODO_MAKER_SAVE_CREATE_CITY)) {
			forwardName = EVENT_TODO_MAKER_SAVE_CREATE_CITY;
		} else if (event.equals(EVENT_PREPARE_MAKER_EDIT_CITY)) {
			forwardName = EVENT_PREPARE_MAKER_EDIT_CITY;
		} else if (event.equals(EVENT_MAKER_EDIT_CITY)) {
			forwardName = EVENT_MAKER_EDIT_CITY;
		} else if (event.equals(EVENT_MAKER_PREPARE_CLOSE_CITY)) {
			forwardName = EVENT_MAKER_PREPARE_CLOSE_CITY;
		} else if (MAKER_DELETE_CITY_READ.equals(event)) {
			forwardName = MAKER_DELETE_CITY_READ;
		} else if (MAKER_DELETE_CITY.equals(event)) {
			forwardName = MAKER_DELETE_CITY;
		}

		else if (EVENT_MAKER_PREPARE_ACTIVATE_CITY.equals(event)) {
			forwardName = EVENT_MAKER_PREPARE_ACTIVATE_CITY;
		}

		else if (EVENT_MAKER_ACTIVATE_CITY.equals(event)) {
			forwardName = EVENT_MAKER_ACTIVATE_CITY;
		}

		else if (event.equals(EVENT_MAKER_RESUBMIT_CREATE_CITY)) {
			forwardName = MAKER_PREPARE_RESUBMIT;
		} else if (event.equals(MAKER_CONFIRM_CLOSE_CITY)) {
			forwardName = MAKER_CONFIRM_CLOSE_CITY;
		}

		else if (event.equals(MAKER_CONFIRM_RESUBMIT_EDIT)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_REGION)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_STATE)
				|| event.equals(MAKER_CONFIRM_RESUBMIT_EDIT_CITY)) {
			forwardName = COMMON_SUBMIT_PAGE;
		}

		else if (event.equals(EVENT_MAKER_SAVE_CITY)
				|| event.equals(EVENT_MAKER_SAVE_EDIT_CITY)) {
			forwardName = EVENT_MAKER_SAVE_CITY;
		} else if (event.equals(EVENT_MAKER_SAVE_REGION)
				|| event.equals(EVENT_MAKER_SAVE_EDIT_REGION)) {
			forwardName = EVENT_MAKER_SAVE_REGION;
		} else if (event.equals(EVENT_MAKER_SAVE_STATE)
				|| event.equals(EVENT_MAKER_SAVE_EDIT_STATE)) {
			forwardName = EVENT_MAKER_SAVE_STATE;
		} else if (event.equals(EVENT_MAKER_SAVE_COUNTRY)
				|| event.equals(EVENT_MAKER_SAVE_EDIT_COUNTRY)) {
			forwardName = EVENT_MAKER_SAVE_COUNTRY;
		}
*/
		DefaultLogger.debug(this, "Forward name --> " + forwardName);
		return forwardName;
	}
}
