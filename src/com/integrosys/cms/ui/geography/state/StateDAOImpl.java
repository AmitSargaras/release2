package com.integrosys.cms.ui.geography.state;

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
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.bus.IStateDAO;
import com.integrosys.cms.app.geography.state.bus.OBState;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyException;
import com.integrosys.cms.app.geography.state.trx.IStateTrxValue;
import com.integrosys.cms.batch.IncompleteBatchJobException;

/** 
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class StateDAOImpl extends HibernateDaoSupport  implements IStateDAO{

	/**
	 * @return String entity name
	 */
	
	public String getEntityName(){
		return IStateDAO.ACTUAL_ENTITY_NAME_STATE; 
	}
	
	public IState getStateById(long id) throws NoSuchGeographyException,TrxParameterException, TransactionException {
		IState state = new OBState();
		try{
			state = (IState)getHibernateTemplate().load(getEntityName(), new Long(id));
			return state;
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in IState ",e);
			e.printStackTrace();
			throw new NoSuchGeographyException("Unable to find State with id ["+id+"]");
		}		
	}

	public IState getState(String entity,long id) throws NoSuchGeographyException,TrxParameterException, TransactionException {
		IState state = new OBState();
		try{
			state = (IState)getHibernateTemplate().load(entity, new Long(id));
			return state;
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in IState ",e);
			e.printStackTrace();
			throw new NoSuchGeographyException("Unable to find State with id ["+id+"]");
		}		
	}
	
	public SearchResult listState(String type, String text)throws NoSuchGeographyException {
		try{
			String query ="";
			ArrayList resultList = new ArrayList();
			ArrayList list = new ArrayList();
			ArrayList list1 = new ArrayList();
			long id =0;
			if( text == null || text.trim().equals("")){
				query = "FROM "+ getEntityName() + " state ORDER BY UPPER(STATE_CODE)";
			}
			else{
				if( type.equalsIgnoreCase("Country") ){
					if( text != null || !( text.trim().equals("")) ){
						String countryQuery = "FROM actualCountry ac WHERE UPPER(ac.countryName) LIKE '" + text.toUpperCase() + "%'";
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
								query = "FROM "+getEntityName()+" st WHERE st.regionId = " + new Long(regionId);
								resultList = (ArrayList) getHibernateTemplate().find(query);
								list.addAll(resultList);
							}
							return new SearchResult(0, list.size(), list.size(), list);
					}
					else
						query = "FROM "+getEntityName() + " state ORDER BY UPPER(STATE_CODE)";
				}
				else if( type.equalsIgnoreCase("Region") ){
					if( text != null || !( text.trim().equals("")) ){
						String regionQuery = "FROM actualRegion region WHERE UPPER(region.regionName) LIKE '" + text.toUpperCase() + "%'";
						ArrayList regionList = (ArrayList) getHibernateTemplate().find(regionQuery);
						Iterator regionItor = regionList.iterator();
						while( regionItor.hasNext() ){
							IRegion region  = (IRegion) regionItor.next();
							id = region.getIdRegion();
							query = "FROM "+getEntityName()+" st WHERE st.regionId = " + new Long(id);
							resultList = (ArrayList) getHibernateTemplate().find(query);
							list.addAll(resultList);
						}
						return new SearchResult(0, list.size(), list.size(), list);
					}
					else
						query = "FROM "+getEntityName() + " state ORDER BY UPPER(STATE_CODE)";
				}
				else
					query = "FROM "+getEntityName() + " state ORDER BY UPPER(STATE_CODE)";
				}		
			if( query != "" ){
				resultList = (ArrayList) getHibernateTemplate().find(query);
				return new SearchResult(0, resultList.size(), resultList.size(), resultList);
			}
			return null;
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in listState",e);
			e.printStackTrace();
			throw new NoSuchGeographyException("Unable to get State");
		}		
	}
	
	public IState createState(IState anState)throws NoSuchGeographyException {	// Approve
		try{
			getHibernateTemplate().save(getEntityName(), anState);
		}catch (Exception e) {
			DefaultLogger.error(this, "error in createState ",e);
			e.printStackTrace();
			throw new NoSuchGeographyException("Unable to create State");
		}
		return anState;	
	}

	public IState deleteState(IState state)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		try{	
			state.setStatus("INACTIVE");
			state.setDeprecated("Y");
			getHibernateTemplate().saveOrUpdate(getEntityName(), state);
		return state;
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in updateState ",e);
			e.printStackTrace();
			throw new NoSuchGeographyException("Unable to update state with id ["+state.getIdState()+"]");
		}		
	}
	public IState updateState(IState state)throws NoSuchGeographyException, TrxParameterException,TransactionException, ConcurrentUpdateException {
		try{			
			if( state.getStatus().equals("INACTIVE")){
				state.setStatus("ACTIVE");
				state.setDeprecated("N");			
			}
			getHibernateTemplate().update("actualState", state);
			return state;
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in updateState ",e);
			e.printStackTrace();
			throw new NoSuchGeographyException("Unable to update state with id ["+state.getIdState()+"]");
		}
	}
	
	public IState createState(String entity, IState state)throws NoSuchGeographyException {	//	Create/Edit
		try{
			state.setStatus("ACTIVE");
			state.setDeprecated("N");							
			getHibernateTemplate().save(entity, state);
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in createState ",e);
			e.printStackTrace();
			throw new NoSuchGeographyException("Unable to create State ");
		}
		return state;		
	}

	public List getCountryList(long countryId)throws NoSuchGeographyException {		
		String entityName = "actualCountry";
		try{
			 String query = "";
			 if( countryId != 0 )
				 query = "SELECT FROM " + entityName + " country WHERE country.id ="+new Long(countryId);
			 else
				 query = "SELECT FROM " + entityName;
			 ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			return resultList;
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in Listing "+entityName,e);
			e.printStackTrace();
			throw new NoSuchGeographyException("Unable to get List for "+entityName);
		}				
	}
	
	public List getStateList(long stateId)throws NoSuchGeographyException {		
		String entityName = "actualState";
		try{
			 String query ="";
			 if( stateId != 0 )
				 query = "SELECT FROM "+ entityName +" state WHERE state.regionId ="+new Long(stateId);
			 else
				 query = "SELECT FROM "+ entityName;
			 ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			return resultList;
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in Listing "+entityName,e);
			e.printStackTrace();
			throw new NoSuchGeographyException("Unable to get List for "+entityName);
		}				
	}

	public List getRegionList(long countryId) throws NoSuchGeographyException {
		 try{
			 String entityName = "actualRegion";
			 String query ="";
			 if( countryId != 0 )
				 query = "SELECT FROM "+ entityName +" as region WHERE region.countryId = " +countryId ;
			 else
				 query = "SELECT FROM "+ entityName;
			 ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			return resultList;
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in Listing actualRegion");
			e.printStackTrace();
			throw new NoSuchGeographyException("Unable to get List for actualRegion");
		}	
			
	}
	
	public boolean checkActiveCities(IState state) {
		long stateId =  state.getIdState();
		DetachedCriteria criteria = DetachedCriteria.forEntityName("actualCity");
		ArrayList resultCity = (ArrayList) getHibernateTemplate().findByCriteria(criteria);		
		Iterator cityItor = resultCity.iterator();
		while( cityItor.hasNext() )
		{
			ICity city  = (ICity) cityItor.next();
			if( city.getStateId().getIdState() == stateId )
			{
				if( city.getStatus().equals("ACTIVE") )
					return true;
			}
		}
		return false;
	}

	public boolean checkInActiveRegions(IState state) {
		long regionId =  state.getRegionId().getIdRegion();
		DetachedCriteria criteria = DetachedCriteria.forEntityName("actualRegion");
		ArrayList resultCity = (ArrayList) getHibernateTemplate().findByCriteria(criteria);		
		Iterator cityItor = resultCity.iterator();
		while( cityItor.hasNext() )
		{
			IRegion region  = (IRegion) cityItor.next();
			if( region.getIdRegion() == regionId )
			{
				if( region.getStatus().equals("INACTIVE") )
					return true;
			}
		}
		return false;
	}
	
	public boolean isStateCodeUnique(String stateCode) {
		String stagingQuery = "SELECT FROM "+IStateDAO.STAGING_STATE_ENTITY_NAME+" WHERE UPPER(state_code) like '"+stateCode.toUpperCase()+"' ";
		ArrayList stagingResultList = (ArrayList) getHibernateTemplate().find(stagingQuery);
		if(stagingResultList.size()>0){
			String actualQuery = "SELECT FROM "+getEntityName()+" WHERE UPPER(state_code) like '"+stateCode.toUpperCase()+"' ";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if(actualResultList.size()>0)
				return true;
			else
				return false;
		}	
		else 
			return false;	
	}
	
	
	public boolean isStateNameUnique(String stateName,long countryId) {
		String stagingQuery = "SELECT FROM "+IStateDAO.STAGING_STATE_ENTITY_NAME+" WHERE UPPER(state_name) like '"+stateName.toUpperCase()+"' ";
		ArrayList stagingResultList = (ArrayList) getHibernateTemplate().find(stagingQuery);
		if( stagingResultList.size() > 0 ){
			String actualQuery = "SELECT FROM "+getEntityName()+" WHERE UPPER(state_name) like '"+stateName.toUpperCase()+"' ";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if( actualResultList.size() > 0 ){
				Iterator itor = actualResultList.iterator();
				while( itor.hasNext() ){
					IState state =  (IState)itor.next();
						if( state.getRegionId().getCountryId().getIdCountry() == countryId )
							return true;
				}
				return false;			
			}
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
	public IFileMapperId insertState(String entityName,IFileMapperId fileId, IStateTrxValue trxValue)throws NoSuchGeographyException {		
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
	public int insertState(IFileMapperMaster fileMapperMaster, String userName, ArrayList result) {
		int noOfRecInserted = 0;
		Long key=null;
		if (result == null || result.size() <= 0) {
			throw new IncompleteBatchJobException("Data to be instered to DB is null or empty");
		}
		try {
			for (int index = 0; index < result.size(); index++) {
				HashMap eachDataMap = (HashMap) result.get(index);
				OBState obState = new OBState();
				obState.setStateCode((String) eachDataMap.get("STATE_CODE"));
				obState.setStateName((String) eachDataMap.get("STATE_NAME"));
				IRegion region = new OBRegion();
				region = getRegionByRegionCode((String)eachDataMap.get("REGION_CODE"));
				obState.setRegionId(region);
				obState.setStatus("ACTIVE");
				obState.setDeprecated("N");
				key = (Long) getHibernateTemplate().save("stagingState", obState);				
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
			throw new IncompleteBatchJobException(
					"Unable to update/insert dad retrived form CSV file");

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
	 * @return State Object
	 * @param Entity Name
	 * @param sysId  
	 * This method Creates State Object
	 */
	public IState insertActualState(String sysId) throws NoSuchGeographyException {
		Map parametersMap = new HashMap();
		parametersMap.put("id", new Long(sysId));		
		DetachedCriteria criteria = DetachedCriteria.forEntityName("stagingState").add(Restrictions.allEq(parametersMap));
		List countryList = getHibernateTemplate().findByCriteria(criteria);
		return (IState)countryList.get(0);
	}
	/**
	 * @return State Object
	 * @param Entity Name
	 * @param State Object  
	 * This method Creates State Object
	 */
	public IState insertState(String entityName,IState country) throws NoSuchGeographyException {
		if(!(entityName==null|| country==null)){			
			Long key = (Long) getHibernateTemplate().save(entityName, country);
			country.setIdState(key.longValue());
			return country;
		}else{
			throw new NoSuchGeographyException("ERROR- Entity name or key is null ");
		}
	}
	
	//************************************************************************
	//********************* Methods for File Upload **************************
	//************************************************************************
	
	public List getAllStageState(String searchBy, String login) {
		List resultList = null;
		String searchByValue = searchBy;
		String strId ="";
		List listId = getFileMasterList(searchBy);
		for (int i = 0; i < listId.size(); i++) {
			OBFileMapperMaster map = (OBFileMapperMaster) listId.get(i);
			DefaultLogger.debug(this,"val = " + map.getSysId());
				if(!strId.equals("")){
					strId +=",";
				}
				strId += map.getSysId();
		}
		
		if(!strId.equals("")){
			Map parameters = new HashMap();
			parameters.put("deprecated","N");
			parameters.put("id", strId);
			String query = "SELECT FROM "+IStateDAO.STAGING_STATE_ENTITY_NAME+" WHERE DEPRECATED = 'N' AND ID IN ("+strId+")";
			try {
				if (searchByValue.trim() != "") {
					resultList = getHibernateTemplate().find(query);
				}
	
			} catch (Exception e) {
				throw new NoSuchGeographyException("ERROR-- While retriving State");
			}
		}
		return resultList;
	}
	
	
	/**
	 * @return Maker check if previous upload is pending.
	 */
	public boolean isPrevFileUploadPending() {
		String rowCount = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_STATE'";
		List rowList = getHibernateTemplate().find(rowCount);
		if(rowList.size()>0){
			String sqlQuery = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_STATE' AND status != 'ACTIVE'";
			List holidayList = getHibernateTemplate().find(sqlQuery);
			if(holidayList.size()>0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return list of files uploaded in staging table of State.
	 */
	public List getFileMasterList(String searchBy) {
		Map parametersList = new HashMap();
		parametersList.put("transId",searchBy);
		DetachedCriteria criteria = DetachedCriteria.forEntityName("fileMapper").add(Restrictions.allEq(parametersList));
		List listId = getHibernateTemplate().findByCriteria(criteria);
       return listId;
	}
	
	public IRegion getRegionByRegionCode(String regionCode) {		
		try {
			String query = "FROM "+IRegionDAO.ACTUAL_ENTITY_NAME_REGION+" WHERE REGION_CODE ='"+ regionCode+"'";
			if( query != "" ){
				ArrayList regionList =(ArrayList) getHibernateTemplate().find(query);
				if( regionList.size() > 0 )
					return (IRegion) regionList.get(0);
				else
					return null;
			}else{
				throw new NoSuchGeographyException("Unable to get List for "+getEntityName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoSuchGeographyException("ERROR-- While retriving getRegionByRegionCode ");
		}
	}
	
	public IState getStateByStateCode(String stateCode) {		
		try {
			String query = "FROM "+getEntityName()+" WHERE STATE_CODE ='"+ stateCode+"'";
			ArrayList resultList;			
			if( query != "" ){
				ArrayList stateList =(ArrayList) getHibernateTemplate().find(query);
				return (IState) stateList.get(0); 
			}else{
				throw new NoSuchGeographyException("Unable to get List for "+getEntityName());
			}
		} catch (Exception e) {
			throw new NoSuchGeographyException("ERROR-- While retriving getStateByStateCode");
		}
		
	}

	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		OBCMSTrxValue obCMSTrxValue = new OBCMSTrxValue();
		String query = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_STATE' AND obcms.transactionID='"+obFileMapperMaster.getTransId()+"'";
		List list = getHibernateTemplate().find(query);
		obCMSTrxValue = (OBCMSTrxValue)list.get(0);
		getHibernateTemplate().flush();
		getHibernateTemplate().delete(obCMSTrxValue);			
	}
	
	public long getStateByStateId(String stateCode)throws Exception {
		
		long id=0L;
		String sql = "SELECT ID FROM CMS_STATE WHERE STATE_CODE='"+stateCode+"'";
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

}