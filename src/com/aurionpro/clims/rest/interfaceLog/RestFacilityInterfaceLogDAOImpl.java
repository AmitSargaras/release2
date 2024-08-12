package com.aurionpro.clims.rest.interfaceLog;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;


public class RestFacilityInterfaceLogDAOImpl  extends HibernateDaoSupport implements IRestFacilityInterfaceLogDAO
{
	public String getEntityName(){
		return IRestFacilityInterfaceLogDAO.FACILITY_REST_SERVICE_INTERFACE_LOG;
	}
	
	public String getSecurityEntityName(){
		return IRestFacilityInterfaceLogDAO.SECURITY_REST_SERVICE_INTERFACE_LOG;
	}
	
	
	@Override
	public void insertFacilityDetailsInterfaceLog(OBRestFacilityInterfaceLog restInterfaceLog) {
		try {
				System.out.println("Going for insertFacilityDetailsInterfaceLog => save data into table => SECURITY_REST_INTERFACE_LOG");
				getHibernateTemplate().save(getEntityName(), restInterfaceLog);
			
		} catch (Exception e) {
			System.out.println("Exception in insertFacilityDetailsInterfaceLog."+e.getMessage());
			e.printStackTrace();
		}finally {
			getHibernateTemplate().flush();
		}
	}

	
	@Override
	public void insertSecurityDetailsInterfaceLog(OBRestFacilityInterfaceLog restInterfaceLog) {
		try {
				System.out.println("Going for insertSecurityDetailsInterfaceLog => save data into table => SECURITY_REST_INTERFACE_LOG");
				getHibernateTemplate().save(getSecurityEntityName(), restInterfaceLog);
			
		} catch (Exception e) {
			System.out.println("Exception in insertSecurityDetailsInterfaceLog."+e.getMessage());
			e.printStackTrace();
		}finally {
			getHibernateTemplate().flush();
		}
	}


	
	
/*	@Override
	public void updateCamDetailsInterfaceLog(OBRestFacilityInterfaceLog restInterfaceLog) {
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
