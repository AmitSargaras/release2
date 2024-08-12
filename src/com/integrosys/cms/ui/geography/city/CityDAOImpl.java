package com.integrosys.cms.ui.geography.city;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.bus.ICityDAO;
import com.integrosys.cms.app.geography.city.bus.OBCity;
import com.integrosys.cms.app.geography.city.trx.ICityTrxValue;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.bus.IStateDAO;
import com.integrosys.cms.app.geography.state.bus.OBState;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyException;
import com.integrosys.cms.batch.IncompleteBatchJobException;
/** 
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class CityDAOImpl extends HibernateDaoSupport  implements ICityDAO{

	/**
	 * @return String entity name
	 */
	
	public String getEntityName(){
		return ICityDAO.ACTUAL_ENTITY_NAME_CITY; 
	}
	
	public ICity getCityById(long id) throws NoSuchGeographyException,TrxParameterException, TransactionException {
		ICity city = new OBCity();
		try{
			city = (ICity)getHibernateTemplate().load(getEntityName(), new Long(id));
			return city;
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in ICity ",e);
			e.printStackTrace();
			throw new NoSuchGeographyException("Unable to find City with id ["+id+"]");
		}		
	}

	public ICity getCity(String entity,long id) throws NoSuchGeographyException,TrxParameterException, TransactionException {
		ICity city = new OBCity();
		try{
			city = (ICity)getHibernateTemplate().load(entity, new Long(id));
			return city;
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in ICity ",e);
			e.printStackTrace();
			throw new NoSuchGeographyException("Unable to find City with id ["+id+"]");
		}		
	}
	
	public SearchResult listCity(String type,String text)throws NoSuchGeographyException {
		try{
			String query = "";
			ArrayList resultList;
			ArrayList list = new ArrayList();
			ArrayList list1 = new ArrayList();
			ArrayList list2 = new ArrayList();
			if( type == null || type == "" )
				query = "FROM "+getEntityName()+" city ORDER BY UPPER(CITY_CODE)";
			else if( type.equalsIgnoreCase("Country") ){						
				if( text != null || !(text.trim().equals("")) ){
					 String countryQuery = "FROM actualCountry country WHERE UPPER(country.countryName) LIKE '" + text.toUpperCase() + "%'";
					  ArrayList countryList = (ArrayList) getHibernateTemplate().find(countryQuery);
					  Iterator countryItor = countryList.iterator();
					  while( countryItor.hasNext() ){
						ICountry country  = (ICountry) countryItor.next();
						long countryId = country.getIdCountry();
						String regionQuery = "FROM actualRegion region WHERE region.countryId = " + new Long(countryId);
						ArrayList regionList = (ArrayList) getHibernateTemplate().find(regionQuery);
						list1.addAll(regionList);
					  }
						Iterator regionItor = list1.iterator();
						while( regionItor.hasNext() ){
							IRegion region  = (IRegion) regionItor.next();
							long regionId = region.getIdRegion();
							String stateQuery = "FROM actualState state WHERE state.regionId = " + new Long(regionId);
							ArrayList stateList = (ArrayList) getHibernateTemplate().find(stateQuery);							
							list.addAll(stateList);							
						}
						Iterator stateItor = list.iterator();
						while( stateItor.hasNext() ){
							IState state  = (IState) stateItor.next();
							long stateId = state.getIdState();
							String cityQuery = "FROM "+getEntityName()+" city WHERE  city.stateId = " + new Long(stateId);
							ArrayList stateList = (ArrayList) getHibernateTemplate().find(cityQuery);
							list2.addAll(stateList);
						}
						return new SearchResult(0, list2.size(), list2.size(), list2);
					  }
					else
						query = "FROM "+getEntityName() + " city ORDER BY UPPER(CITY_CODE)";				
			}
			else if( type.equalsIgnoreCase("Region") ){
				if( text != null || !( text.trim().equals("")) ){
					String regionQuery = "FROM actualRegion region WHERE UPPER(region.regionName) LIKE '" + text.toUpperCase() + "%'";
					ArrayList regionList = (ArrayList) getHibernateTemplate().find(regionQuery);
					Iterator regionItor = regionList.iterator();
					while( regionItor.hasNext() ){
						IRegion region  = (IRegion) regionItor.next();
						long regionId = region.getIdRegion();
						
						String stateQuery = "FROM actualState state WHERE state.regionId = " + new Long(regionId);
						ArrayList stateList = (ArrayList) getHibernateTemplate().find(stateQuery);
						list.addAll(stateList);							
					}
						Iterator stateItor = list.iterator();
						while( stateItor.hasNext() ){
							IState state  = (IState) stateItor.next();
							long stateId = state.getIdState();
							query = "FROM "+getEntityName()+" city WHERE city.stateId = " + new Long(stateId);
							ArrayList cityList = (ArrayList) getHibernateTemplate().find(query);
							list2.addAll(cityList);
						}
						return new SearchResult(0, list2.size(), list2.size(), list2);
				}
				else
					query = "FROM "+getEntityName() + " city ORDER BY UPPER(CITY_CODE)";
			}
			else if( type.equalsIgnoreCase("State") ){
				if( text != null || !(text.trim().equals("")) ){
					String stateQuery = "FROM actualState state WHERE UPPER(state.stateName) LIKE '" + text.toUpperCase() + "%'";
					ArrayList stateList = (ArrayList) getHibernateTemplate().find(stateQuery);
					Iterator stateItor = stateList.iterator();
					while( stateItor.hasNext() ){
						IState state = (IState) stateItor.next();
						long stateId = state.getIdState();
						query = "FROM "+getEntityName()+" city WHERE city.stateId = " + new Long(stateId);
						ArrayList cityList = (ArrayList) getHibernateTemplate().find(query);
						list.addAll(cityList);
					}
					return new SearchResult(0, list.size(), list.size(), list);
				}
				else
					query = "FROM "+getEntityName() + " city ORDER BY UPPER(CITY_CODE)";
			}					
			if( query != "" ){
				resultList = (ArrayList) getHibernateTemplate().find(query);
				return new SearchResult(0, resultList.size(), resultList.size(), resultList);
			}
			return null;
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in listCity",e);
			e.printStackTrace();
			throw new NoSuchGeographyException("Unable to get City");
		}		
	}
	
	public ICity createCity(ICity anCity)throws NoSuchGeographyException {		// Approve
		try{
			getHibernateTemplate().save(getEntityName(), anCity);
		}catch (Exception e) {
			DefaultLogger.error(this, "error in createCity ",e);
			e.printStackTrace();
			throw new NoSuchGeographyException("Unable to create City");
		}
		return anCity;	
	}

	public ICity deleteCity(ICity city)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		try{
			city.setStatus("INACTIVE");
			city.setDeprecated("Y");
			getHibernateTemplate().saveOrUpdate(getEntityName(), city);
		return city;
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in updateCity ",e);
			e.printStackTrace();
			throw new NoSuchGeographyException("Unable to update city with id ["+city.getIdCity()+"]");
		}		
	}

	public ICity updateCity(ICity city)throws NoSuchGeographyException, TrxParameterException,TransactionException, ConcurrentUpdateException {
		try{
			if (city.getStatus().equals("INACTIVE")) {
				city.setStatus("ACTIVE");
				city.setDeprecated("N");
			}
			getHibernateTemplate().saveOrUpdate(getEntityName(), city);
			return city;
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in updateCity ",e);
			e.printStackTrace();
			throw new NoSuchGeographyException("Unable to update city with id ["+city.getIdCity()+"]");
		}
	}
	
	public ICity createCity(String entity, ICity city)throws NoSuchGeographyException {		//	Create/Edit
		try{
			city.setStatus("ACTIVE");
			city.setDeprecated("N");							
			getHibernateTemplate().save(entity, city);
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in createCity ",e);
			e.printStackTrace();
			throw new NoSuchGeographyException("Unable to create City ");
		}
		return city;		
	}
	
	public List getCountryList(long countryId)throws NoSuchGeographyException {		
		 String entityName = "actualCountry";
		 try{		
			 String query="";
			 if( countryId != 0 )
				 query = "SELECT FROM " + entityName + " country WHERE  country.id = "+new Long(countryId) + " AND STATUS = 'ACTIVE' ";
			 else
				 query = "SELECT FROM " + entityName + " country" + " where STATUS  = 'ACTIVE' ";
			 ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			return resultList;
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in listing "+entityName,e);
			e.printStackTrace();
			throw new NoSuchGeographyException("Unable to get List for "+entityName);
		}
	}
	
	public List getRegionList(long stateId)throws NoSuchGeographyException {		
		 String entityName = "actualRegion";
		 try{		
			 String query="";
			 if( stateId != 0 )
				 query = "SELECT FROM " + entityName + " region WHERE region.countryId = "+new Long(stateId) + " AND STATUS  = 'ACTIVE' ";
			 else
				 query = "SELECT FROM " + entityName + " where STATUS  = 'ACTIVE' ";
			 ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			return resultList;
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in listing "+entityName,e);
			e.printStackTrace();
			throw new NoSuchGeographyException("Unable to get List for "+entityName);
		}
	}
	
	public List getStateList(long stateId)throws NoSuchGeographyException {		
		String entityName = "actualState";
		try{
			String query = "";
			if( stateId != 0 )
				query = "SELECT FROM "+ entityName +" state WHERE state.regionId = "+new Long(stateId) + " AND STATUS  = 'ACTIVE' ";
			else
				query = "SELECT FROM "+ entityName + " where STATUS  = 'ACTIVE' ";
			 ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			return resultList;
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in listing "+entityName,e);
			e.printStackTrace();
			throw new NoSuchGeographyException("Unable to get List for "+entityName);
		}	
	}

	public List getCityList(long stateId)throws NoSuchGeographyException {		
		 String entityName = "actualCity";
			try{
				String query = "";
				if( stateId != 0 )
					query = "SELECT FROM "+ entityName +" city WHERE city.stateId = "+new Long(stateId) + " AND STATUS  = 'ACTIVE' ";
				else
					query = "SELECT FROM "+ entityName + " where STATUS  = 'ACTIVE' ";
				 ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
				return resultList;
			}catch (Exception e) {
				DefaultLogger.error(this, "############# error in listing "+entityName,e);
				e.printStackTrace();
				throw new NoSuchGeographyException("Unable to get List for "+entityName);
			}	
	}
	
	public boolean checkInActiveStates(ICity city) {
		long stateId =  city.getStateId().getIdState();
		DetachedCriteria criteria = DetachedCriteria.forEntityName("actualState");
		ArrayList resultCity = (ArrayList) getHibernateTemplate().findByCriteria(criteria);		
		Iterator cityItor = resultCity.iterator();
		while( cityItor.hasNext() )
		{
			IState state  = (IState) cityItor.next();
			if( state.getIdState() == stateId )
			{
				if( state.getStatus().equals("INACTIVE") )
					return true;
			}
		}
		return false;
	}

	public boolean isCityCodeUnique(String cityCode) {
		String stagingQuery = "SELECT FROM "+ICityDAO.STAGING_CITY_ENTITY_NAME+" WHERE UPPER(city_code) like '"+cityCode.toUpperCase()+"' ";
		ArrayList stagingResultList = (ArrayList) getHibernateTemplate().find(stagingQuery);
		if(stagingResultList.size()>0){
			String actualQuery = "SELECT FROM "+getEntityName()+" WHERE UPPER(city_code) like '"+cityCode.toUpperCase()+"' ";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if(actualResultList.size()>0)
				return true;
			else
				return false;
		}	
		else 
			return false;	
	}
	
	public boolean isCityNameUnique(String cityName,long stateId) {
		String stagingQuery = "SELECT FROM "+ICityDAO.STAGING_CITY_ENTITY_NAME+" WHERE UPPER(city_name) like '"+cityName.toUpperCase()+"' AND state_id = "+new Long(stateId);
		ArrayList stagingResultList = (ArrayList) getHibernateTemplate().find(stagingQuery);
		if( stagingResultList.size() > 0 ){
			String actualQuery = "SELECT FROM "+getEntityName()+" WHERE UPPER(city_name) like '"+cityName.toUpperCase()+"' AND state_id = "+new Long(stateId);
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if( actualResultList.size() > 0 )
				return true;
			else
				return false;
		}	
		else 
			return false;	
	}
	
//################### File Upload ###########################
	
	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId insertCity(String entityName,IFileMapperId fileId, ICityTrxValue trxValue)throws NoSuchGeographyException {		
		if(!(entityName==null|| fileId==null)){			
			Long key = (Long) getHibernateTemplate().save(entityName, fileId);
			fileId.setId(key.longValue());
			return fileId;
		}else{
			throw new NoSuchGeographyException("ERROR- Entity name or key is null ");
		}	
	}
	
	/**
	 * @return integer (record count of inserted record)
	 * @param FileMapperMaster
	 * @param userName  
	 * @param result of upload files 
	 * This method Creates FileMapperMaster Object
	 */
	public int insertCity(IFileMapperMaster fileMapperMaster, String userName, ArrayList result) {
		int noOfRecInserted = 0;
		Long key=null;
		if (result == null || result.size() <= 0) {
			throw new IncompleteBatchJobException("Data to be instered to DB is null or empty");
		}
		try {
			for (int index = 0; index < result.size(); index++) {
				HashMap eachDataMap = (HashMap) result.get(index);
				OBCity obCity = new OBCity();
				obCity.setCityCode((String) eachDataMap.get("CITY_CODE"));
				obCity.setCityName((String) eachDataMap.get("CITY_NAME"));
				IState state = new OBState();
				state = getStateByStateCode((String)eachDataMap.get("STATE_CODE"));
				obCity.setStateId(state);
				obCity.setStatus("ACTIVE");
				obCity.setDeprecated("N");
				key = (Long) getHibernateTemplate().save("stagingCity", obCity);				
				//create FileMapperMaster
				
				IFileMapperMaster fileMapper = new OBFileMapperMaster();
				fileMapper.setFileId(fileMapperMaster.getFileId());
				fileMapper.setSysId(key.longValue());
				fileMapper.setTransId(fileMapperMaster.getTransId());
				if(!(fileMapperMaster==null)){
					key = (Long) getHibernateTemplate().save("fileMapper", fileMapper);
					getHibernateTemplate().get("fileMapper", key);
				}else{
					throw new NoSuchGeographyException("ERROR- Entity name or key is null ");
				}
				noOfRecInserted++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IncompleteBatchJobException("Unable to update/insert dad retrived form CSV file");
		}
		return noOfRecInserted;
	}

	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId createFileId(String entityName, IFileMapperId fileId) throws NoSuchGeographyException {
		if(!(entityName==null|| fileId==null)){		
			Long key = (Long) getHibernateTemplate().save(entityName, fileId);
			fileId.setId(key.longValue());
			return fileId;
		}else{
			throw new NoSuchGeographyException("ERROR- Entity name or key is null ");
		}
	}
	
	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId getInsertFileList(String entityName, Serializable key)	throws NoSuchGeographyException {
		if(!(entityName==null|| key==null)){			
			return (IFileMapperId) getHibernateTemplate().get(entityName, key);
		}else{
			throw new NoSuchGeographyException("ERROR-- Entity Name Or Key is null");
		}
	}
	

	/**
	 * @return City Object
	 * @param Entity Name
	 * @param sysId  
	 * This method Creates City Object
	 */
	public ICity insertActualCity(String sysId)	throws NoSuchGeographyException {
		Map parametersMap = new HashMap();
		parametersMap.put("id", new Long(sysId));		
		DetachedCriteria criteria = DetachedCriteria.forEntityName("stagingCity").add(Restrictions.allEq(parametersMap));
		List countryList = getHibernateTemplate().findByCriteria(criteria);
		return (ICity)countryList.get(0);
	}
	/**
	 * @return City Object
	 * @param Entity Name
	 * @param City Object  
	 * This method Creates City Object
	 */
	public ICity insertCity(String entityName,ICity country) throws NoSuchGeographyException {
		if(!(entityName==null|| country==null)){			
			Long key = (Long) getHibernateTemplate().save(entityName, country);
			country.setIdCity(key.longValue());
			return country;
		}else{
			throw new NoSuchGeographyException("ERROR- Entity name or key is null ");
		}
	}
	
	//************************************************************************
	//********************* Methods for File Upload **************************
	//************************************************************************
	
	public List getAllStageCity(String searchBy, String login) {
		List resultList = null;
		String searchByValue = searchBy;
		String strId ="";
		List listId = getFileMasterList(searchBy);
		System.out.println("**inside cityDaoImpl.java*****478*****getAllStageCity()*****searchBy : "+searchBy+", login : "+login);
		for (int i = 0; i < listId.size(); i++) {
			OBFileMapperMaster map = (OBFileMapperMaster) listId.get(i);
//			System.out.println("val = " + map.getSysId());
			if(!strId.equals("")){
				strId +=",";
			}
			strId += map.getSysId();
		}
		System.out.println("*inside cityDaoImpl.java*****487*****getAllStageCity()**** strId : "+strId+"***");
		if(!strId.equals("")){
			Map parameters = new HashMap();
			parameters.put("deprecated","N");
			parameters.put("id", strId);
			String query = "SELECT FROM "+ICityDAO.STAGING_CITY_ENTITY_NAME+" WHERE DEPRECATED = 'N' AND ID IN ("+strId+")";
			System.out.println("*inside cityDaoImpl.java*****493*****getAllStageCity()**** query : "+query);
			try {
				if (searchByValue.trim() != "") {
					resultList = getHibernateTemplate().find(query);
				}
	
			} catch (Exception e) {
				System.out.println("Exception in cityDaoImpl.java*****500*****catch()**** query : "+query+" Exception is :"+e);
				e.printStackTrace();
				throw new NoSuchGeographyException("ERROR-- While retriving City");
			}
		}
		return resultList;
	}
	
	
	/**
	 * @return Maker check if previous upload is pending.
	 */
	public boolean isPrevFileUploadPending() {
		String rowCount = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_CITY'";
		List rowList = getHibernateTemplate().find(rowCount);
		if(rowList.size()>0){
			String sqlQuery = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_CITY' AND status != 'ACTIVE'";
			List holidayList = getHibernateTemplate().find(sqlQuery);
			if(holidayList.size()>0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return list of files uploaded in staging table of City.
	 */
	public List getFileMasterList(String searchBy) {
		Map parametersList = new HashMap();
		parametersList.put("transId",searchBy);
		DetachedCriteria criteria = DetachedCriteria.forEntityName("fileMapper").add(Restrictions.allEq(parametersList));
		List listId = getHibernateTemplate().findByCriteria(criteria);
       return listId;
	}
	
	public IState getStateByStateCode(String stateCode) {		
		try {
			String query = "FROM "+IStateDAO.ACTUAL_ENTITY_NAME_STATE+" WHERE STATE_CODE ='"+ stateCode+"'";
			ArrayList resultList;			
			if( query != "" ){				
				ArrayList countryList =(ArrayList) getHibernateTemplate().find(query);				 
				return (IState) countryList.get(0); 				
			}else{
				throw new NoSuchGeographyException("Unable to get List for "+getEntityName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoSuchGeographyException("ERROR-- While retriving getStateByStateCode ");
		}		
	}

	public ICity getCityByCityCode(String cityCode) {		
		try {
			String query = "FROM "+getEntityName()+" WHERE CITY_CODE ='"+ cityCode+"'";
			if( query != "" ){				
				ArrayList cityList =(ArrayList) getHibernateTemplate().find(query);				 
				return (ICity) cityList.get(0); 		
			}else{
				throw new NoSuchGeographyException("Unable to get List for "+getEntityName());
			}
		} catch (Exception e) {
			throw new NoSuchGeographyException("ERROR-- While retriving getCityByCityCode");
		}
	}
	
	public long getCityByCityId(String cityCode)throws Exception {
		
		long id=0L;
		String sql = "SELECT ID FROM CMS_CITY WHERE CITY_CODE='"+cityCode+"'";
		DBUtil dbUtil=null;

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			// DefaultLogger.debug(this, sql);
		
			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				id= rs.getLong("ID");
			}
			return id;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("ERROR-- While retriving getCityByCityCode", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("ERROR-- While retriving getCityByCityCode", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("ERROR-- While retriving getCityByCityCode", ex);
			}
		}

		}
	
	
	public List getCityByCountryCode(String countryCode)throws NoSuchGeographyException {
		 try{		
			 String query="";
			 if(countryCode!=null && !"".equals(countryCode.trim())){
				 query ="select from actualCity city where city.stateId in " +
				 		"(select st.id from actualState st where st.regionId in" +
				 		"(select re.id from actualRegion re where re.countryId in " +
				 		"(select co.id from actualCountry co where co.countryName='" +countryCode +"' " +
				 		"and co.deprecated='N' and co.status='ACTIVE') " +
				 		"and re.deprecated='N' and re.status='ACTIVE') " +
				 		"and st.deprecated='N' and st.status='ACTIVE') ";
			 }
			 DefaultLogger.error(this, "query"+query);
			 ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			return resultList;
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in getCityByCountryCode() ",e);
			e.printStackTrace();
			throw new NoSuchGeographyException("Unable to get city list");
		}
	}
	
	/**
	 * @return Maker delete if error.  //A shiv 170811
	 */
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) { 
		OBCMSTrxValue obCMSTrxValue = new OBCMSTrxValue();
		String query = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_CITY' AND obcms.transactionID='"+obFileMapperMaster.getTransId()+"'";
		List list = getHibernateTemplate().find(query);
		obCMSTrxValue = (OBCMSTrxValue)list.get(0);
		getHibernateTemplate().flush();
		getHibernateTemplate().delete(obCMSTrxValue);
	}
	
	
}