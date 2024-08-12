package com.aurionpro.clims.rest.interfaceLog;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.aurionpro.clims.rest.interfaceLog.OBUdfEnqRestInterfaceLog;

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


public class UdfEnqRestInterfaceLogDAOImpl  extends HibernateDaoSupport implements IUdfEnqRestInterfaceLogDAO
{
	public String getEntityName(){
		return IUdfEnqRestInterfaceLogDAO.UDF_ENQ_REST_SERVICE_INTERFACE_LOG;
	}
	
	
	
	@Override
	public void insertUdfEnuiryDetailsInterfaceLog(OBUdfEnqRestInterfaceLog restInterfaceLog) {
		try {
				System.out.println("Going for insertUdfEnuiryDetailsInterfaceLog");
				getHibernateTemplate().save(getEntityName(), restInterfaceLog);
			
		} catch (Exception e) {
			System.out.println("Exception in insertUdfEnuiryDetailsInterfaceLog."+e.getMessage());
			e.printStackTrace();
		}finally {
			getHibernateTemplate().flush();
		}
	}
	
}
