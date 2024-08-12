package com.aurionpro.clims.rest.interfaceLog;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;


public class RestInterfaceLogDAOImpl  extends HibernateDaoSupport implements IRestInterfaceLogDAO
{
	public String getEntityName(){
		return IRestInterfaceLogDAO.COMMON_CODE_INTERFACE_LOG;
	}
	
	
	
	@Override
	public void insertCommonCodeLog(OBRestInterfaceLog restInterfaceLog) {
		try {
				System.out.println("Going for insertCommonCodeLog => save data ino table => insertCommonCodeLog");
				getHibernateTemplate().save(getEntityName(), restInterfaceLog);
			
		} catch (Exception e) {
			System.out.println("Exception in insertCommonCodeLog."+e.getMessage());
			e.printStackTrace();
		}finally {
			getHibernateTemplate().flush();
		}
	}
	
	
	
	}
