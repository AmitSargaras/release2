package com.integrosys.cms.ui.geography.region;

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
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.bus.ICountryDAO;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.geography.region.trx.IRegionTrxValue;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyException;
import com.integrosys.cms.batch.IncompleteBatchJobException;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class RegionDAOImpl extends HibernateDaoSupport implements IRegionDAO {

	/**
	 * @return String entity name
	 */

	public String getEntityName() {
		return IRegionDAO.ACTUAL_ENTITY_NAME_REGION;
	}

	public IRegion getRegionById(long id) throws NoSuchGeographyException,TrxParameterException, TransactionException {
		IRegion country = new OBRegion();
		try {
			country = (IRegion) getHibernateTemplate().load(getEntityName(),new Long(id));
			return country;
		} catch (Exception obe) {
			DefaultLogger.error(this, "############# error in IRegion ", obe);
			obe.printStackTrace();
			throw new NoSuchGeographyException("Unable to find Region with id [" + id + "]");
		}
	}

	public IRegion getRegion(String entity, long id) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		IRegion region = new OBRegion();
		try {
			region = (IRegion) getHibernateTemplate().load(entity, new Long(id));
			return region;
		} catch (Exception obe) {
			DefaultLogger.error(this, "############# error in IRegion ", obe);
			obe.printStackTrace();
			throw new NoSuchGeographyException("Unable to find Region with id [" + id + "]");
		}
	}

	public SearchResult listRegion(String type, String text) throws NoSuchGeographyException {
		try {
			String query = "";
			String regionQuery = "";
			ArrayList resultList;
			ArrayList list1 = new ArrayList();
			if (text == null || text == "") {
				query = "FROM " + getEntityName() + " region ORDER BY UPPER(REGION_CODE)";
			} else {
				if (type.equalsIgnoreCase("Country")) {
					if (text != null && !(text.equals(""))) {
						regionQuery = "FROM actualCountry country WHERE UPPER(country.countryName) LIKE '" + text.toUpperCase() + "%'";
						resultList = (ArrayList) getHibernateTemplate().find(regionQuery);
						Iterator countryItor = resultList.iterator();
						while (countryItor.hasNext()) {
							ICountry country = (ICountry) countryItor.next();
							long id = country.getIdCountry();
							query = "FROM " + getEntityName() + " st WHERE st.countryId = " + new Long(id);
							ArrayList regionList = (ArrayList) getHibernateTemplate().find(query);
							list1.addAll(regionList);
						}
						return new SearchResult(0, list1.size(), list1.size(), list1);
					} else
						query = "FROM " + getEntityName() + " region ORDER BY UPPER(REGION_CODE)";
				}
			}
			if (query != "") {
				resultList = (ArrayList) getHibernateTemplate().find(query);
				return new SearchResult(0, resultList.size(),resultList.size(), resultList);
			}
			return null;
		} catch (Exception obe) {
			DefaultLogger.error(this, "############# error in listRegion", obe);
			obe.printStackTrace();
			throw new NoSuchGeographyException("Unable to get Region");
		}
	}

	public IRegion createRegion(IRegion anRegion)throws NoSuchGeographyException {
		try {
			anRegion.setStatus("ACTIVE");
			anRegion.setDeprecated("N");
			getHibernateTemplate().save(getEntityName(), anRegion);
		} catch (Exception obe) {
			DefaultLogger.error(this, "error in createRegion ", obe);
			obe.printStackTrace();
			throw new NoSuchGeographyException("Unable to create Region");
		}
		return anRegion;
	}

	public IRegion deleteRegion(IRegion region) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		try {
			region.setStatus("INACTIVE");
			region.setDeprecated("Y");
			getHibernateTemplate().saveOrUpdate(getEntityName(), region);
			return region;
		} catch (Exception obe) {
			DefaultLogger.error(this, "############# error in updateRegion ",obe);
			obe.printStackTrace();
			throw new NoSuchGeographyException("Unable to update region with id [" + region.getIdRegion() + "]");
		}
	}

	public IRegion updateRegion(IRegion region) throws NoSuchGeographyException, TrxParameterException,TransactionException, ConcurrentUpdateException {
		try {
			if (region.getStatus().equals("INACTIVE")) {
				region.setStatus("ACTIVE");
				region.setDeprecated("N");
			}
			getHibernateTemplate().saveOrUpdate(getEntityName(), region);
			return region;
		} catch (Exception obe) {
			DefaultLogger.error(this, "############# error in updateRegion ",obe);
			obe.printStackTrace();
			throw new NoSuchGeographyException("Unable to update region with id [" + region.getIdRegion() + "]");
		}
	}

	public IRegion createRegion(String entity, IRegion region) throws NoSuchGeographyException {
		try {
			region.setStatus("ACTIVE");
			region.setDeprecated("N");
			getHibernateTemplate().save(entity, region);
		} catch (Exception obe) {
			DefaultLogger.error(this, "############# error in createRegion ",obe);
			obe.printStackTrace();
			throw new NoSuchGeographyException("Unable to create Region ");
		}
		return region;
	}

	public List getCountryList(long countryId) throws NoSuchGeographyException {
		try {
			String entityName = "actualCountry";
			String query = "";
			if (countryId != 0)
				query = "FROM " + entityName + " country WHERE country.id =" + new Long(countryId);
			else
				query = "FROM " + entityName;
			ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			return resultList;
		} catch (Exception obe) {
			DefaultLogger.error(this, "############# error in listRegion", obe);
			obe.printStackTrace();
			throw new NoSuchGeographyException("Unable to get CountryList");
		}
	}

	public boolean checkActiveState(IRegion region) {
		long regionId = region.getIdRegion();
		DetachedCriteria criteria = DetachedCriteria.forEntityName("actualState");
		ArrayList resultState = (ArrayList) getHibernateTemplate().findByCriteria(criteria);
		Iterator stateItor = resultState.iterator();
		while (stateItor.hasNext()) {
			IState state = (IState) stateItor.next();
			if (state.getRegionId().getIdRegion() == regionId) {
				if (state.getStatus().equals("ACTIVE"))
					return true;
			}
		}
		return false;
	}

	public boolean checkInActiveCountries(IRegion region) {
		long countryId = region.getCountryId().getIdCountry();
		DetachedCriteria criteria = DetachedCriteria.forEntityName("actualCountry");
		ArrayList resultState = (ArrayList) getHibernateTemplate().findByCriteria(criteria);
		Iterator countryItor = resultState.iterator();
		while (countryItor.hasNext()) {
			ICountry country = (ICountry) countryItor.next();
			if (country.getIdCountry() == countryId) {
				if (country.getStatus().equals("INACTIVE"))
					return true;
			}
		}
		return false;
	}
	
	//################### File Upload ###########################
	
	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId insertRegion(String entityName,IFileMapperId fileId, IRegionTrxValue trxValue)throws NoSuchGeographyException {		
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
	public int insertRegion(IFileMapperMaster fileMapperMaster, String userName, ArrayList result) {
		int noOfRecInserted = 0;
		Long key=null;
		if (result == null || result.size() <= 0) {
			throw new IncompleteBatchJobException("Data to be instered to DB is null or empty");
		}
		try {
			for (int index = 0; index < result.size(); index++) {
				HashMap eachDataMap = (HashMap) result.get(index);
				OBRegion obRegion = new OBRegion();
				obRegion.setRegionCode((String) eachDataMap.get("REGION_CODE"));
				obRegion.setRegionName((String) eachDataMap.get("REGION_NAME"));
				ICountry country = new OBCountry();
				country = getCountryByCountryCode ((String)eachDataMap.get("COUNTRY_CODE"));
				obRegion.setCountryId(country);
				obRegion.setStatus("ACTIVE");
				obRegion.setDeprecated("N");
				key = (Long) getHibernateTemplate().save("stagingRegion", obRegion);				
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
	 * @return Region Object
	 * @param Entity Name
	 * @param sysId  
	 * This method Creates Region Object
	 */
	public IRegion insertActualRegion(String sysId)	throws NoSuchGeographyException {
		Map parametersMap = new HashMap();
		parametersMap.put("id", new Long(sysId));		
		DetachedCriteria criteria = DetachedCriteria.forEntityName("stagingRegion").add(Restrictions.allEq(parametersMap));
		List countryList = getHibernateTemplate().findByCriteria(criteria);
		return (IRegion)countryList.get(0);
	}
	/**
	 * @return Region Object
	 * @param Entity Name
	 * @param Region Object  
	 * This method Creates Region Object
	 */
	public IRegion insertRegion(String entityName,IRegion country) throws NoSuchGeographyException {
		if(!(entityName==null|| country==null)){			
			Long key = (Long) getHibernateTemplate().save(entityName, country);
			country.setIdRegion(key.longValue());
			return country;
		}else{
			throw new NoSuchGeographyException("ERROR- Entity name or key is null ");
		}
	}
	
	//************************************************************************
	//********************* Methods for File Upload **************************
	//************************************************************************
	
	public List getAllStageRegion(String searchBy, String login) {
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
			String query = "SELECT FROM "+IRegionDAO.STAGING_REGION_ENTITY_NAME+" WHERE DEPRECATED = 'N' AND ID IN ("+strId+")";
			try {
				if (searchByValue.trim() != "") {
					resultList = getHibernateTemplate().find(query);
				}
	
			} catch (Exception e) {
				throw new NoSuchGeographyException("ERROR-- While retriving Region");
			}
		}
		return resultList;
	}
	
	
	/**
	 * @return Maker check if previous upload is pending.
	 */
	public boolean isPrevFileUploadPending() {
		String rowCount = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_REGION'";
		List rowList = getHibernateTemplate().find(rowCount);
		if(rowList.size()>0){
			String sqlQuery = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_REGION' AND status != 'ACTIVE'";
			List holidayList = getHibernateTemplate().find(sqlQuery);
			if(holidayList.size()>0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return list of files uploaded in staging table of Region.
	 */
	public List getFileMasterList(String searchBy) {
		Map parametersList = new HashMap();
		parametersList.put("transId",searchBy);
		DetachedCriteria criteria = DetachedCriteria.forEntityName("fileMapper").add(Restrictions.allEq(parametersList));
		List listId = getHibernateTemplate().findByCriteria(criteria);
       return listId;
	}

	public boolean isRegionCodeUnique(String regionCode) {
		String stagingQuery = "SELECT FROM "+IRegionDAO.STAGING_REGION_ENTITY_NAME+" WHERE UPPER(region_code) like '"+regionCode.toUpperCase()+"' ";
		ArrayList stagingResultList = (ArrayList) getHibernateTemplate().find(stagingQuery);
		if(stagingResultList.size()>0){
			String actualQuery = "SELECT FROM "+getEntityName()+" WHERE UPPER(region_code) like '"+regionCode.toUpperCase()+"' ";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if(actualResultList.size()>0)
				return true;
			else
				return false;
		}	
		else 
			return false;	
	}
	
	public boolean isRegionNameUnique(String regionName,long countryId) {
		String stagingQuery = "SELECT FROM "+IRegionDAO.STAGING_REGION_ENTITY_NAME+" WHERE UPPER(region_name) like '"+regionName.toUpperCase()+"' ";
		ArrayList stagingResultList = (ArrayList) getHibernateTemplate().find(stagingQuery);
		if( stagingResultList.size() > 0 ){
			String actualQuery = "SELECT FROM "+getEntityName()+" WHERE UPPER(region_name) like '"+regionName.toUpperCase()+"' ";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if( actualResultList.size() > 0 ){
				Iterator itor = actualResultList.iterator();
				while( itor.hasNext() ){
					IRegion region =  (IRegion)itor.next();
						if( region.getCountryId().getIdCountry() == countryId )
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
	
	public ICountry getCountryByCountryCode(String countryCode) {		
		try {
			String query = "FROM "+ICountryDAO.ACTUAL_ENTITY_NAME_COUNTRY+" WHERE COUNTRY_CODE ='"+ countryCode+"'";
			if( query != "" ){				
				ArrayList countryList =(ArrayList) getHibernateTemplate().find(query);				 
				if( countryList.size() > 0 )
					return (ICountry) countryList.get(0);
				else
					return null;
			}else{
				throw new NoSuchGeographyException("Unable to get List for "+getEntityName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoSuchGeographyException("ERROR-- While retriving getCountryByCountryCode ");
		}		
	}
	
	public IRegion getRegionByRegionCode(String regionCode) {		
		try {
			String query = "FROM "+getEntityName()+" WHERE REGION_CODE ='"+ regionCode+"'";
			ArrayList resultList;			
			if( query != "" ){				
				ArrayList regionList =(ArrayList) getHibernateTemplate().find(query);				 
				return (IRegion) regionList.get(0); 				
			}else{
				throw new NoSuchGeographyException("Unable to get List for "+getEntityName());
			}
		} catch (Exception e) {
			throw new NoSuchGeographyException("ERROR-- While retriving getRegionByRegionCode");
		}		
	}

	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		OBCMSTrxValue obCMSTrxValue = new OBCMSTrxValue();
		String query = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_REGION' AND obcms.transactionID='"+obFileMapperMaster.getTransId()+"'";
		List list = getHibernateTemplate().find(query);
		obCMSTrxValue = (OBCMSTrxValue)list.get(0);
		getHibernateTemplate().flush();
		getHibernateTemplate().delete(obCMSTrxValue);
			
	}

	public long getRegionByRegionId(String regionCode)throws Exception {
		
		long id=0L;
		String sql = "SELECT ID FROM CMS_REGION WHERE REGION_CODE='"+regionCode+"'";
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