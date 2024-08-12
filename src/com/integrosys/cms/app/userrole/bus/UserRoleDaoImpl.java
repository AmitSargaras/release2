package com.integrosys.cms.app.userrole.bus;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public class UserRoleDaoImpl extends HibernateDaoSupport implements IUserRoleDao{

	public List getUserModuleList(String membershipId){
		List resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+IUserRoleDao.ACTUAL_ENTITY_NAME_USER_ROLE+" WHERE ROLE ="+membershipId);
		return resultList;
	}
}
