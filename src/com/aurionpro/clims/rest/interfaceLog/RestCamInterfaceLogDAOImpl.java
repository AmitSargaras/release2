package com.aurionpro.clims.rest.interfaceLog;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.aurionpro.clims.rest.interfaceLog.OBRestCamInterfaceLog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;


public class RestCamInterfaceLogDAOImpl  extends HibernateDaoSupport implements IRestCamInterfaceLogDAO
{
	public String getEntityName(){
		return IRestCamInterfaceLogDAO.CAM_REST_SERVICE_INTERFACE_LOG;
	}
	
	
	
	@Override
	public void insertCamDetailsInterfaceLog(OBRestCamInterfaceLog restInterfaceLog) {
		try {
				System.out.println("Going for insertCamDetailsInterfaceLog => save data into table => camRestServiceInterfaceLog");
				getHibernateTemplate().save(getEntityName(), restInterfaceLog);
			
		} catch (Exception e) {
			System.out.println("Exception in insertCamDetailsInterfaceLog."+e.getMessage());
			e.printStackTrace();
		}finally {
			getHibernateTemplate().flush();
		}
	}
	
	@Override
	public void updateCamDetailsInterfaceLog(OBRestCamInterfaceLog restInterfaceLog) {
		try {
				System.out.println("Going for updateCamDetailsInterfaceLog => save data into table => camRestServiceInterfaceLog");
				getHibernateTemplate().saveOrUpdate(getEntityName(), restInterfaceLog);
			
		} catch (Exception e) {
			System.out.println("Exception in updateCamDetailsInterfaceLog."+e.getMessage());
			e.printStackTrace();
		}finally {
			getHibernateTemplate().flush();
		}
	}
	
}
