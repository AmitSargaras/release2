package com.aurionpro.clims.rest.interfaceLog;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;


public class RestPartyInterfaceLogDAOImpl  extends HibernateDaoSupport implements IRestPartyInterfaceLogDAO
{
	public String getEntityName(){
		return IRestPartyInterfaceLogDAO.PARTY_REST_SERVICE_INTERFACE_LOG;
	}
	
	
	
	@Override
	public void insertPartyDetailsInterfaceLog(OBRestPartyInterfaceLog restInterfaceLog) {
		try {
				System.out.println("Going for insertCamDetailsInterfaceLog => save data into table => partyRestServiceInterfaceLog");
				getHibernateTemplate().save(getEntityName(), restInterfaceLog);
			
		} catch (Exception e) {
			System.out.println("Exception in insertCamDetailsInterfaceLog."+e.getMessage());
			e.printStackTrace();
		}finally {
			getHibernateTemplate().flush();
		}
	}



	
	
/*	@Override
	public void updateCamDetailsInterfaceLog(OBRestPartyInterfaceLog restInterfaceLog) {
		try {
				System.out.println("Going for updateCamDetailsInterfaceLog => save data into table => camRestServiceInterfaceLog");
				getHibernateTemplate().saveOrUpdate(getEntityName(), restInterfaceLog);
			
		} catch (Exception e) {
			System.out.println("Exception in updateCamDetailsInterfaceLog."+e.getMessage());
			e.printStackTrace();
		}finally {
			getHibernateTemplate().flush();
		}
	}*/
	
}
