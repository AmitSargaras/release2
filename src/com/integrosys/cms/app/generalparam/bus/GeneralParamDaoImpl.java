package com.integrosys.cms.app.generalparam.bus;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Implementation of {@link IGeneralParamDao} using Hibernate
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/06/23 10:45:20 $ Tag: $Name%
 */
public class GeneralParamDaoImpl extends HibernateDaoSupport implements IGeneralParamDao {

	public IGeneralParamEntry createGeneralParamEntry(String entityName, IGeneralParamEntry mutualFundsFeedEntry) {
		Long key = (Long) getHibernateTemplate().save(entityName, mutualFundsFeedEntry);

		return (IGeneralParamEntry) getHibernateTemplate().get(entityName, key);
	}

	public IGeneralParamGroup createGeneralParamGroup(String entityName, IGeneralParamGroup generalParamGroup) {
		Long key = (Long) getHibernateTemplate().save(entityName, generalParamGroup);

		return (IGeneralParamGroup) getHibernateTemplate().get(entityName, key);
	}

	public void deleteGeneralParamEntry(String entityName, IGeneralParamEntry generalParamEntry) {
		getHibernateTemplate().delete(entityName, generalParamEntry);
	}

	public void deleteGeneralParamGroup(String entityName, IGeneralParamGroup generalParamGroup) {
		getHibernateTemplate().delete(entityName, generalParamGroup);
	}

	public IGeneralParamEntry getGeneralParamEntryByPrimaryKey(String entityName, Serializable key) {
		return (IGeneralParamEntry) getHibernateTemplate().get(entityName, key);
	}

	public IGeneralParamGroup getGeneralParamGroupByGroupType(String entityName, String groupType) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("type", groupType));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList.isEmpty()) {
			return null;
		}

		return (IGeneralParamGroup) resultList.get(0);
	}

	public IGeneralParamEntry getGeneralParamEntryByRic(String entityName, String ric) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("ric", ric));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList.isEmpty()) {
			return null;
		}

		return (IGeneralParamEntry) resultList.get(0);
	}

	public IGeneralParamGroup getGeneralParamGroupByPrimaryKey(String entityName, Serializable key) {
		return (IGeneralParamGroup) getHibernateTemplate().get(entityName, key);
	}

	public IGeneralParamEntry updateGeneralParamEntry(String entityName, IGeneralParamEntry generalParamEntry) {
		getHibernateTemplate().update(entityName, generalParamEntry);

		return (IGeneralParamEntry) getHibernateTemplate().get(entityName, new Long(generalParamEntry.getParamID()));
	}

	public IGeneralParamGroup updateGeneralParamGroup(String entityName, IGeneralParamGroup generalParamGroup) {
		getHibernateTemplate().update(entityName, generalParamGroup);

		return (IGeneralParamGroup) getHibernateTemplate().get(entityName, new Long(generalParamGroup.getGeneralParamGroupID()));
	}
	
	public IGeneralParamEntry getGeneralParamEntryByParamCodeActual(String paramCode) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(ACTUAL_GENERAL_PARAM_ENTRY_ENTITY_NAME).add(Restrictions.eq("paramCode", paramCode));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList.isEmpty()) {
			return null;
		}

		return (IGeneralParamEntry) resultList.get(0);
	}
public void updateGeneralParamAppDate(String appDate){
		
		//String server = PropertyManager.getValue("integrosys.server.identification");
		DBUtil dbUtil = null;
		
		String sql = "update CMS_GENERAL_PARAM set param_value='"+appDate+"' where param_code='APPLICATION_DATE'";


		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);
		
			int  rs = dbUtil.executeUpdate();
			//this commented by janki on 17/apr/2012 . this was giving error related to global commit
			//dbUtil.commit();
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in init login manager", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in init login manager", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in init login manager", ex);
			}
		}
		
	}


public IGeneralParamEntry[] getGeneralParamEntries(String tableName,String roleId){


	DBUtil dbUtil = null;
	String sql = "Select PARAM_ID,PARAM_CODE,PARAM_NAME,PARAM_VALUE,UI_VIEW,LAST_UPDATE_DATE,VERSION_TIME,FEED_REF,ROLE_ALLOCATION from "+tableName+"   where UI_VIEW='Y' and ROLE_ALLOCATION like '%"+roleId+"%'";
	

	try {
		dbUtil = new DBUtil();
		dbUtil.setSQL(sql);
		// DefaultLogger.debug(this, sql);
		ResultSet rs = null;
		rs = dbUtil.executeQuery();
		IGeneralParamEntry paramEntries= null;  
		ArrayList resultList = new ArrayList();
		while (rs.next()) {
			paramEntries = new OBGeneralParamEntry();
			paramEntries.setGeneralParamEntryRef(rs.getLong("FEED_REF"));
			paramEntries.setLastUpdatedDate(rs.getDate("LAST_UPDATE_DATE"));
			paramEntries.setParamCode(rs.getString("PARAM_CODE"));
			paramEntries.setParamID(rs.getLong("PARAM_ID"));
			paramEntries.setParamName(rs.getString("PARAM_NAME"));
			paramEntries.setParamValue(rs.getString("PARAM_VALUE"));
			paramEntries.setUiView(rs.getString("UI_VIEW"));
			paramEntries.setVersionTime(rs.getLong("VERSION_TIME"));

			resultList.add(paramEntries);
		}
		rs.close();
		return (IGeneralParamEntry[]) resultList.toArray(new IGeneralParamEntry[resultList.size()]);
		//this commented by janki on 17/apr/2012 . this was giving error related to global commit
		//dbUtil.commit();
	}
	catch (SQLException ex) {
		throw new SearchDAOException("SQLException getGeneralParamEntry ", ex);
	}
	catch (Exception ex) {
		throw new SearchDAOException("Exception getGeneralParamEntry ", ex);
	}
	finally {
		try {
			dbUtil.close();
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException getGeneralParamEntry ", ex);
		}
	}

}
//Start:Added by Uma Khot for EOY activity.
	public String getActivityPerformedForParamCode(String paramCode){
		String sql="select activity_performed from cms_general_param where param_code='"+paramCode+"'";
		String activityPerformed="";
		DBUtil dbUtil = null;
		try{
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = null;
			rs = dbUtil.executeQuery();
			if(rs.next()){
				activityPerformed=rs.getString(1);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception while getting Activity performed for parameter "+paramCode+" error:"+e.getMessage());
		}
		return activityPerformed;
	}
	//End:Added by Uma Khot for EOY activity.	
	
	public String getGenralParamValues(String paramCode) {
		String sql="select param_value from cms_general_param where param_code='"+paramCode+"'";
		String paramValue="";
		DBUtil dbUtil = null;
		try{
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = null;
			rs = dbUtil.executeQuery();
			if(rs.next()){
				paramValue=rs.getString(1);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception while getting Activity performed for parameter "+paramCode+" error:"+e.getMessage());
		}
		return paramValue;
	}
}
