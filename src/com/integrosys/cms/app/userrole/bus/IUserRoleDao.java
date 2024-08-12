package com.integrosys.cms.app.userrole.bus;

import java.util.List;

public interface IUserRoleDao {
	
	public final static String ACTUAL_ENTITY_NAME_USER_MODULE = "actualUserModule";
	
	public final static String ACTUAL_ENTITY_NAME_USER_ROLE = "actualUserRole";
	
	public List getUserModuleList(String userID);

}
