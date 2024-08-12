package com.integrosys.cms.app.userrole.bus;

import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;

public class UserRoleBusImpl implements IUserRoleBus{

	public List getUserModuleList(String membershipId){
		IUserRoleDao userRoleDao = (IUserRoleDao)BeanHouse.get("userRoleDao");
		
		return userRoleDao.getUserModuleList(membershipId);
	}
}
