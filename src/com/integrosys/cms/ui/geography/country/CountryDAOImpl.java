package com.integrosys.cms.ui.geography.country;

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
import com.integrosys.cms.app.geography.country.bus.CountryException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.bus.ICountryDAO;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.geography.country.trx.ICountryTrxValue;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyException;
import com.integrosys.cms.batch.IncompleteBatchJobException;

public class CountryDAOImpl extends HibernateDaoSupport implements ICountryDAO {

	/**
	 * @return String entity name
	 */
	public String getEntityName() {
		return ICountryDAO.ACTUAL_ENTITY_NAME_COUNTRY;
	}

	public ICountry getCountryById(long id) throws NoSuchGeographyException,TrxParameterException, TransactionException {
		ICountry country = new OBCountry();
		try {
			country = (ICountry) getHibernateTemplate().load(getEntityName(),new Long(id));
			return country;
		} catch (Exception obe) {
			DefaultLogger.error(this, "############# error in ICountry ", obe);
			obe.printStackTrace();
			throw new NoSuchGeographyException("Unable to find Country with id [" + id + "]");
		}
	}

	public ICountry getCountry(String entity, long id) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		ICountry country = new OBCountry();
		try {
			country = (ICountry) getHibernateTemplate().load(entity,new Long(id));
			return country;
		} catch (Exception obe) {
			DefaultLogger.error(this, "############# error in ICountry ", obe);
			obe.printStackTrace();
			throw new NoSuchGeographyException("Unable to find Country with id [" + id + "]");
		}
	}

	public SearchResult listCountry(String type, String text) throws NoSuchGeographyException {
		try {
			String query = "";
			if (text == null || text == "") {
				query = "FROM " + getEntityName() + " country ORDER BY UPPER(COUNTRY_CODE)";
			} else {
				if (type.equalsIgnoreCase("Country")) {
					if (text != null || !(text.trim().equals(""))) {
						query = "FROM actualCountry ac WHERE UPPER(ac.countryName) LIKE '" + text.toUpperCase() + "%' order by country_code";
					} else
						query = "FROM " + getEntityName() + " country ORDER BY UPPER(COUNTRY_CODE)";
				}
			}
			ArrayList countryList = (ArrayList) getHibernateTemplate().find(query);
			return new SearchResult(0, countryList.size(), countryList.size(),countryList);
		} catch (Exception obe) {
			DefaultLogger.error(this, "############# error in listCountry", obe);
			obe.printStackTrace();
			throw new NoSuchGeographyException("Unable to get Country");
		}
	}

	public ICountry createCountry(ICountry anCountry) throws NoSuchGeographyException {
		try {
			getHibernateTemplate().save(getEntityName(), anCountry);
		} catch (Exception obe) {
			DefaultLogger.error(this, "############# error in createCountry ",obe);
			obe.printStackTrace();
			throw new NoSuchGeographyException("Unable to create Country");
		}
		return anCountry;
	}

	public ICountry deleteCountry(ICountry country) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		try {
			country.setStatus("INACTIVE");
			country.setDeprecated("Y");
			getHibernateTemplate().saveOrUpdate(getEntityName(), country);
			return country;
		} catch (Exception obe) {
			DefaultLogger.error(this, "############# error in updateCountry ",obe);
			obe.printStackTrace();
			throw new NoSuchGeographyException("Unable to update country with id [" + country.getIdCountry() + "]");
		}
	}

	public ICountry updateCountry(ICountry country) throws NoSuchGeographyException, TrxParameterException,TransactionException, ConcurrentUpdateException {
		try {
			if (country.getStatus().equals("INACTIVE")) {
				country.setStatus("ACTIVE");
				country.setDeprecated("N");
			}
			getHibernateTemplate().saveOrUpdate(getEntityName(), country);
			return country;
		} catch (Exception obe) {
			DefaultLogger.error(this, "############# error in updateCountry ",obe);
			obe.printStackTrace();
			throw new NoSuchGeographyException("Unable to update country with id [" + country.getIdCountry() + "]");
		}
	}

	public ICountry createCountry(String entity, ICountry country)throws NoSuchGeographyException {
		try {
			country.setStatus("ACTIVE");
			country.setDeprecated("N");
			getHibernateTemplate().save(entity, country);
		} catch (Exception obe) {
			DefaultLogger.error(this, "############# error in createCountry ",obe);
			obe.printStackTrace();
			throw new NoSuchGeographyException("Unable to create Country ");
		}
		return country;
	}

	public boolean checkActiveRegion(ICountry country) {
		long countryId = country.getIdCountry();
		DetachedCriteria criteria = DetachedCriteria.forEntityName("actualRegion");
		ArrayList resultRegion = (ArrayList) getHibernateTemplate().findByCriteria(criteria);
		Iterator regionItor = resultRegion.iterator();
		while (regionItor.hasNext()) {
			IRegion region = (IRegion) regionItor.next();
			if (region.getCountryId().getIdCountry() == countryId) {
				if (region.getStatus().equals("ACTIVE"))
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
	public IFileMapperId insertCountry(String entityName,IFileMapperId fileId, ICountryTrxValue trxValue) throws CountryException {		
		if(!(entityName==null|| fileId==null)){			
			Long key = (Long) getHibernateTemplate().save(entityName, fileId);
			fileId.setId(key.longValue());
			return fileId;
		}else{
			throw new CountryException("ERROR- Entity name or key is null ");
		}	
	}
	
	/**
	 * @return integer (record count of inserted record)
	 * @param FileMapperMaster
	 * @param userName  
	 * @param result of upload files 
	 * This method Creates FileMapperMaster Object
	 */
	public int insertCountry(IFileMapperMaster fileMapperMaster, String userName, ArrayList result) {
		int noOfRecInserted = 0;
		Long key=null;
		if (result == null || result.size() <= 0) {
			throw new IncompleteBatchJobException("Data to be instered to DB is null or empty");
		}
		try {
			for (int index = 0; index < result.size(); index++) {
				HashMap eachDataMap = (HashMap) result.get(index);
				OBCountry obCountry = new OBCountry();
				obCountry.setCountryCode((String) eachDataMap.get("COUNTRY_CODE"));
				obCountry.setCountryName((String) eachDataMap.get("COUNTRY_NAME"));
				obCountry.setStatus("ACTIVE");
				obCountry.setDeprecated("N");
				key = (Long) getHibernateTemplate().save("stagingCountry", obCountry);				
				//create FileMapperMaster
				
				IFileMapperMaster fileMapper = new OBFileMapperMaster();
				fileMapper.setFileId(fileMapperMaster.getFileId());
				fileMapper.setSysId(key.longValue());
				fileMapper.setTransId(fileMapperMaster.getTransId());
				if(!(fileMapperMaster==null)){
					key = (Long) getHibernateTemplate().save("fileMapper", fileMapper);
					getHibernateTemplate().get("fileMapper", key);
				}else{
					throw new CountryException("ERROR- Entity name or key is null ");
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
	public IFileMapperId createFileId(String entityName, IFileMapperId fileId) throws CountryException {
		if(!(entityName==null|| fileId==null)){		
			Long key = (Long) getHibernateTemplate().save(entityName, fileId);
			fileId.setId(key.longValue());
			return fileId;
		}else{
			throw new CountryException("ERROR- Entity name or key is null ");
		}
	}
	
	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId getInsertFileList(String entityName, Serializable key)	throws CountryException {
		if(!(entityName==null|| key==null)){			
			return (IFileMapperId) getHibernateTemplate().get(entityName, key);
		}else{
			throw new CountryException("ERROR-- Entity Name Or Key is null");
		}
	}	

	/**
	 * @return Country Object
	 * @param Entity Name
	 * @param sysId  
	 * This method Creates Country Object
	 */
	public ICountry insertActualCountry(String sysId) throws CountryException {
		Map parametersMap = new HashMap();
		parametersMap.put("id", new Long(sysId));		
		DetachedCriteria criteria = DetachedCriteria.forEntityName("stagingCountry").add(Restrictions.allEq(parametersMap));
		List countryList = getHibernateTemplate().findByCriteria(criteria);
		return (ICountry)countryList.get(0);
	}
	/**
	 * @return Country Object
	 * @param Entity Name
	 * @param Country Object  
	 * This method Creates Country Object
	 */
	public ICountry insertCountry(String entityName,ICountry country) throws CountryException {
		if(!(entityName==null|| country==null)){			
			Long key = (Long) getHibernateTemplate().save(entityName, country);
			country.setIdCountry(key.longValue());
			return country;
		}else{
			throw new CountryException("ERROR- Entity name or key is null ");
		}
	}
	
	//************************************************************************
	//********************* Methods for File Upload **************************
	//************************************************************************
	
	public List getAllStageCountry(String searchBy, String login) {
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
			String query = "SELECT FROM "+ICountryDAO.STAGING_COUNTRY_ENTITY_NAME+" WHERE DEPRECATED = 'N' AND ID IN ("+strId+")";
			try {
				if (searchByValue.trim() != "") {
					resultList = getHibernateTemplate().find(query);
				}
	
			} catch (Exception e) {
				throw new CountryException("ERROR-- While retriving Country");
			}
		}
		return resultList;
	}
		
	/**
	 * @return Maker check if previous upload is pending.
	 */
	public boolean isPrevFileUploadPending() {
		String rowCount = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_COUNTRY'";
		List rowList = getHibernateTemplate().find(rowCount);
		if(rowList.size()>0){
			String sqlQuery = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_COUNTRY' AND status != 'ACTIVE'";
			List holidayList = getHibernateTemplate().find(sqlQuery);
			if(holidayList.size()>0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return list of files uploaded in staging table of Country.
	 */
	public List getFileMasterList(String searchBy) {
		Map parametersList = new HashMap();
		parametersList.put("transId",searchBy);
		DetachedCriteria criteria = DetachedCriteria.forEntityName("fileMapper").add(Restrictions.allEq(parametersList));
		List listId = getHibernateTemplate().findByCriteria(criteria);
       return listId;
	}

	public boolean isCountryCodeUnique(String countryCode) {
		String stagingQuery = "SELECT FROM "+ICountryDAO.STAGING_COUNTRY_ENTITY_NAME+" WHERE UPPER(country_code) like '"+countryCode.toUpperCase()+"' ";
		ArrayList stagingResultList = (ArrayList) getHibernateTemplate().find(stagingQuery);
		if(stagingResultList.size()>0){
			String actualQuery = "SELECT FROM "+ICountryDAO.ACTUAL_ENTITY_NAME_COUNTRY+" WHERE UPPER(country_code) like '"+countryCode.toUpperCase()+"' ";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if(actualResultList.size()>0)
				return true;
			else
				return false;
		}	
		else 
			return false;		 
	}
	
	public boolean isCountryNameUnique(String countryName) {
		String stagingQuery = "SELECT FROM "+ICountryDAO.STAGING_COUNTRY_ENTITY_NAME+" WHERE UPPER(country_name) like '"+countryName.toUpperCase()+"' ";
		ArrayList stagingResultList = (ArrayList) getHibernateTemplate().find(stagingQuery);
		if( stagingResultList.size() > 0 ){
			String actualQuery = "SELECT FROM "+ICountryDAO.ACTUAL_ENTITY_NAME_COUNTRY+" WHERE UPPER(country_name) like '"+countryName.toUpperCase()+"' ";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if( actualResultList.size() > 0 )
				return true;
			else
				return false;
		}	
		else 
			return false;		 
	}
	
	public ICountry getCountryByCountryCode(String countryCode) {
		try {
			String query = "FROM "+getEntityName()+" WHERE COUNTRY_CODE ='"+ countryCode+"'";
			ArrayList resultList;
			if( query != "" ){
				ArrayList countryList =(ArrayList) getHibernateTemplate().find(query);
				return (ICountry) countryList.get(0); 
			}else{
				throw new ValuationAgencyException("Unable to get List for "+getEntityName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ValuationAgencyException("ERROR-- While retriving getCountryByCountryCode ");
		}
	}

	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		OBCMSTrxValue obCMSTrxValue = new OBCMSTrxValue();
		String query = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_COUNTRY' AND obcms.transactionID='"+obFileMapperMaster.getTransId()+"'";
		List list = getHibernateTemplate().find(query);
		obCMSTrxValue = (OBCMSTrxValue)list.get(0);
		getHibernateTemplate().flush();
		getHibernateTemplate().delete(obCMSTrxValue);			
	}
	
	
	
	
	
	public long getCountryByCountryId(String countryCode)throws Exception {
		
		long id=0L;
		String sql = "SELECT ID FROM CMS_COUNTRY WHERE COUNTRY_CODE='"+countryCode+"'";
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