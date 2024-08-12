package com.integrosys.cms.ui.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.component.user.app.bus.CommonUserSearchCriteria;
import com.integrosys.component.user.app.bus.OBCommonUserSearchCriteria;

public class UserListMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public UserListMapper() {
		DefaultLogger.debug(this, "Inside constructor");
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ "userMap", "java.util.ArrayList", SERVICE_SCOPE }, });
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @throws com.integrosys.base.uiinfra.exception.MapperException on errors
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		DefaultLogger.debug(this, "Inside mapForm to ob ");
		NewUserSearchForm aForm = (NewUserSearchForm) cForm;
		ArrayList userList = (ArrayList) inputs.get("userMap");
		String event = (String) inputs.get("event");
		CommonUserSearchCriteria cBor = new CommonUserSearchCriteria();
		OBCommonUserSearchCriteria obSearch = new OBCommonUserSearchCriteria();
		obSearch.setAssignmentType("U");// todo get from some ui const file
		// DefaultLogger.debug(this, "............ " + userList);
		if ((userList != null)
				&& (event != null)
				&& (event.equals("list") || event.equals("list_rejected") || event.equals("add_users_for_add")
						|| event.equals("add_users_for_add_reject") || event.equals("add_users_for_edit") || event
						.equals("add_users_for_edit_reject"))) {
			DefaultLogger.debug(this, "Processing user map in the mapper ");
			List pageUsers = Arrays.asList((aForm.getPageUsers() != null) ? aForm.getPageUsers() : new Object[0]);
			List checkedUsers = Arrays.asList((aForm.getCheckedUsers() != null) ? aForm.getCheckedUsers()
					: new Object[0]);
			for (int i = 0; i < pageUsers.size(); i++) {
				if (checkedUsers.contains(pageUsers.get(i))) {
					if (!userList.contains(pageUsers.get(i))) {
						userList.add(pageUsers.get(i));
						DefaultLogger.debug(this, "Adding to User List " + pageUsers.get(i));
					}
				}
				else {
					if (userList.contains(pageUsers.get(i))) {
						userList.remove(pageUsers.get(i));
						DefaultLogger.debug(this, "Removing from User List " + pageUsers.get(i));
					}
				}
			}
		}

		if ((aForm.getUserName() != null) && (!aForm.getUserName().trim().equals(""))) {
			obSearch.setUserName(aForm.getUserName());
		}
		// todo filter for only unassigned user to add to team..
		cBor.setNItems(10); // TODO
		cBor.setStartIndex(aForm.getStartIndex());
		cBor.setCriteria(obSearch);
		DefaultLogger.debug(this, "Going out of mapForm to ob ");
		return cBor;
	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @throws MapperException on errors
	 * @return Object
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "inside mapOb to form ");
		NewUserSearchForm aForm = (NewUserSearchForm) cForm;
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}

}
