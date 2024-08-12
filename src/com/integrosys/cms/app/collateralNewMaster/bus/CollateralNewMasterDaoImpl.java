package com.integrosys.cms.app.collateralNewMaster.bus;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import org.hibernate.Query;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
/**
 * @author  Abhijit R. 
 * Dao implication of interface CollateralNewMaster
 */

public class CollateralNewMasterDaoImpl extends HibernateDaoSupport implements ICollateralNewMasterDao{
	
	/**
	  * @return Particular CollateralNewMaster according 
	  * to the id passed as parameter.  
	  * 
	  */

	public ICollateralNewMaster getCollateralNewMaster(String entityName, Serializable key)throws CollateralNewMasterException {
		
		if(!(entityName==null|| key==null)){
		
		return (ICollateralNewMaster) getHibernateTemplate().get(entityName, key);
		}else{
			throw new CollateralNewMasterException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	/**
	 * @return CollateralNewMaster Object
	 * @param Entity Name
	 * @param CollateralNewMaster Object  
	 * This method Updates CollateralNewMaster Object
	 */
	
	public ICollateralNewMaster updateCollateralNewMaster(String entityName, ICollateralNewMaster item)throws CollateralNewMasterException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (ICollateralNewMaster) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new CollateralNewMasterException("ERROR-- Entity Name Or Key is null");
		}
	}
	/**
	 * @return CollateralNewMaster Object
	 * @param Entity Name
	 * @param CollateralNewMaster Object  
	 * This method delete CollateralNewMaster Object
	 */
	
	public ICollateralNewMaster deleteCollateralNewMaster(String entityName, ICollateralNewMaster item)throws CollateralNewMasterException{

		if(!(entityName==null|| item==null)){
			item.setDeprecated("Y");
			item.setStatus("INACTIVE");
			getHibernateTemplate().update(entityName, item);
			return (ICollateralNewMaster) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new CollateralNewMasterException("ERROR-- Entity Name Or Key is null");
		}
	}
	/**
	 * @return CollateralNewMaster Object
	 * @param Entity Name
	 * @param CollateralNewMaster Object  
	 * This method Creates CollateralNewMaster Object
	 */
	public ICollateralNewMaster createCollateralNewMaster(String entityName,
			ICollateralNewMaster collateralNewMaster)
			throws CollateralNewMasterException {
		if(!(entityName==null|| collateralNewMaster==null)){
			if( collateralNewMaster.getNewCollateralCode() == null || collateralNewMaster.getNewCollateralCode().equals("")){
				String collateralCode=getCollateralCode();
				collateralNewMaster.setNewCollateralCode(collateralCode);
			}
			
			Long key = (Long) getHibernateTemplate().save(entityName, collateralNewMaster);
			collateralNewMaster.setId(key.longValue());
			return collateralNewMaster;
			}else{
				throw new CollateralNewMasterException("ERROR- Entity name or key is null ");
			}
	}

	private String getCollateralCode() {
		Query query = currentSession().createSQLQuery("SELECT COLLATERAL_NEW_MASTER_SEQ.NEXTVAL FROM dual");
		String sequenceNumber = query.uniqueResult().toString();
		NumberFormat numberFormat = new DecimalFormat("0000000");
		String collateralCode = numberFormat.format(Long.parseLong(sequenceNumber));
		collateralCode = "COL" + collateralCode;		
		return collateralCode;
	}

	
	
	/**
	  * @return Particular CollateralNewMaster according 
	  * to the id passed as parameter.  
	  * 
	  */

	public ICollateralNewMaster load(String entityName, long id)throws CollateralNewMasterException
	{
		if(!(entityName==null|| id==0)){
		return (ICollateralNewMaster)getHibernateTemplate().get(entityName , new Long(id));	
		}else{
			throw new CollateralNewMasterException("ERROR- Entity name or key is null ");
		}
	}

	public boolean isCollateraNameUnique(String collateralName) {
		String stagingQuery = "SELECT FROM "+ICollateralNewMasterDao.STAGE_COLLATERAL_NEW_MASTER_NAME+" WHERE NEW_COLLATERAL_DESCRIPTION like '"+collateralName+"' ";
		ArrayList stagingResultList = (ArrayList) getHibernateTemplate().find(stagingQuery);
		if(stagingResultList.size()>0){
			String actualQuery = "SELECT FROM "+ICollateralNewMasterDao.ACTUAL_COLLATERAL_NEW_MASTER_NAME+" WHERE STATUS != 'INACTIVE' AND DEPRECATED != 'Y' AND NEW_COLLATERAL_DESCRIPTION like '"+collateralName+"' ";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if(actualResultList.size()>0)
				return true;
			else
				return false;
		}	
		else 
			return false;		 
	}
	public boolean isDuplicateRecord(String cpsId) {
		String stagingQuery = "SELECT FROM "+ICollateralNewMasterDao.STAGE_COLLATERAL_NEW_MASTER_NAME+" WHERE CPS_ID = '"+cpsId+"' ";
		ArrayList stagingResultList = (ArrayList) getHibernateTemplate().find(stagingQuery);
		if(stagingResultList.size()>0){
			String actualQuery = "SELECT FROM "+ICollateralNewMasterDao.ACTUAL_COLLATERAL_NEW_MASTER_NAME+" WHERE STATUS != 'INACTIVE' AND DEPRECATED != 'Y' AND CPS_ID = '"+cpsId+"' ";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if(actualResultList.size()>0)
				return true;
			else
				return false;
		}	
		else 
			return false;		 
	}
	
	public String getInsuranceByCode(String collateralCode)throws CollateralNewMasterException
	{
		String data="";
		String sql= "select INSURANCE from CMS_COLLATERAL_NEW_MASTER where NEW_COLLATERAL_CODE='"+collateralCode+"' and STATUS='ACTIVE' and DEPRECATED='N'";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();			
			while(rs.next())
			{
				data=rs.getString(1);	
				if(null==data)
					data="no";
			}
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (NoSQLStatementException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finalize(dbUtil,rs);
		return data;
	}
	
	private void finalize(DBUtil dbUtil, ResultSet rs) {
		try {
			if (null != rs) {
				rs.close();
			}
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
		

}
