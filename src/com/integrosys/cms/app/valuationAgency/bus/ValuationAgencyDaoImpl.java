package com.integrosys.cms.app.valuationAgency.bus;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.bus.ICityDAO;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.bus.ICountryDAO;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.bus.IStateDAO;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.app.valuationAgency.trx.IValuationAgencyTrxValue;
import com.integrosys.cms.batch.IncompleteBatchJobException;

public class ValuationAgencyDaoImpl extends HibernateDaoSupport implements
		IValuationAgencyDao {

	private ICountryDAO countryDAO;
	private IRegionDAO regionDAO;
	private IStateDAO stateDAO;
	private ICityDAO cityDAO;
	
	public IRegionDAO getRegionDAO() {
		return regionDAO;
	}
	public void setRegionDAO(IRegionDAO regionDAO) {
		this.regionDAO = regionDAO;
	}
	public IStateDAO getStateDAO() {
		return stateDAO;
	}
	public void setStateDAO(IStateDAO stateDAO) {
		this.stateDAO = stateDAO;
	}
	public ICityDAO getCityDAO() {
		return cityDAO;
	}
	public void setCityDAO(ICityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}

	
	public ICountryDAO getCountryDAO() {
		return countryDAO;
	}
	public void setCountryDAO(ICountryDAO countryDAO) {
		this.countryDAO = countryDAO;
	}
	/**
	 * @return String entity name
	 */
	public String getEntityName(){
		return IValuationAgencyDao.ACTUAL_VALUATION_AGENCY_NAME; 
	}
	public String getEntityNameForupload(){
		return IValuationAgencyDao.CMS_FILE_MAPPER_ID; 
	}
	
	
	
	
	/**
	 * @return ValuationAgency Object
	 * @param Entity Name
	 * @param Key  
	 * This method returns ValuationAgency Object
	 */
	public IValuationAgency getValuationAgency(String entityName, Serializable key)throws ValuationAgencyException {
		if(!(entityName==null|| key==null)){
		return (IValuationAgency) getHibernateTemplate().get(entityName, key);
		}else{
			throw new ValuationAgencyException("ERROR- Entity name or key is null ");
		}
	}

	/**
	 * @return ValuationAgency Object
	 * @param Entity Name
	 * @param ValuationAgency Object  
	 * This method Updates ValuationAgency Object
	 */
	public IValuationAgency updateValuationAgency(String entityName, IValuationAgency item)throws ValuationAgencyException {
		
		DefaultLogger.debug(this, "---------------ValuationAgencyDaoImpl------A--------");
		if(!(entityName==null|| item==null)){
			
			
		getHibernateTemplate().update(entityName, item);

		return (IValuationAgency) getHibernateTemplate().load(entityName,
				new Long(item.getId()));
		}else{
			throw new ValuationAgencyException("ERROR- Entity name or key is null ");
		}

	}
	/**
	 * @return ValuationAgency Object
	 * @param Entity Name
	 * @param ValuationAgency Object  
	 * This method Creates ValuationAgency Object
	 */

	public IValuationAgency createValuationAgency(String entityName,
			IValuationAgency valuationAgency)throws ValuationAgencyException {
		DefaultLogger.debug(this, "---------------ValuationAgencyDaoImpl------B--------");
		
		if(!(entityName==null|| valuationAgency==null)){
			if( valuationAgency.getValuationAgencyCode() == null || valuationAgency.getValuationAgencyCode().equals("")){
				String valuationAgencyCode=getValuationAgencyCode();
				valuationAgency.setValuationAgencyCode(valuationAgencyCode);
			}
			Long key = (Long) getHibernateTemplate().save(entityName, valuationAgency);
			valuationAgency.setId(key.longValue());
			return valuationAgency;
		}else{
			throw new ValuationAgencyException("ERROR- Entity name or key is null ");
		}
	}

	public IValuationAgency disableValuationAgency(String entityName, IValuationAgency item)throws ValuationAgencyException{

		if(!(entityName==null|| item==null)){
				item.setStatus("INACTIVE");
				Long key = (Long) getHibernateTemplate().save(entityName, item);
				item.setId(key.longValue());
				return item;
			}else{
				throw new ValuationAgencyException("ERROR-- Entity Name Or Key is null in disable");
			}
	}
	/**
	 * @return ValuationAgency Object
	 * @param Entity Name
	 * @param ValuationAgency Object  
	 * This method enable ValuationAgency Object
	 */
	
	public IValuationAgency enableValuationAgency(String entityName, IValuationAgency item)throws ValuationAgencyException{

		if(!(entityName==null|| item==null)){
		
			item.setStatus("ACTIVE");
			Long key = (Long) getHibernateTemplate().save(entityName, item);
			item.setId(key.longValue());
				
		return item;
		}else{
			throw new ValuationAgencyException("ERROR-- Entity Name Or Key is null in enable");
		}
	}
	
	public boolean isVACodeUnique(String vaCode){
		String query = "SELECT FROM "+IValuationAgencyDao.STAGE_VALUATION_AGENCY_NAME+" WHERE VALUATION_AGENCY_CODE like '"+vaCode+"'";
		ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
		if(resultList.size()>0){
			String query1 = "SELECT FROM "+getEntityName()+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' AND VALUATION_AGENCY_CODE like '"+vaCode+"'";
			ArrayList resultList1 = (ArrayList) getHibernateTemplate().find(query1);
			if(resultList1.size()>0)
				return true;
			else
				return false;
		}	
		else 
			return false;
		 
	}
	
	public List getCountryList(long countryId)throws ValuationAgencyException {		
		 String entityName = "actualCountry";
		 try{		
			 String query="";
			 if( countryId != 0 )
				 query = "SELECT FROM " + entityName + " country WHERE  country.id = "+new Long(countryId);
			 else
				 query = "SELECT FROM " + entityName + " country";
			 ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			return resultList;
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in listing "+entityName,e);
			e.printStackTrace();
			throw new ValuationAgencyException("Unable to get List for  "+entityName);
		}
	}
	
	public List getCityList(long stateId)throws ValuationAgencyException {		
		 String entityName = "actualCityTown";
			try{
				String query = "";
				if( stateId != 0 )
					query = "SELECT FROM "+ entityName +" city WHERE city.stateId = "+new Long(stateId);
				else
					query = "SELECT FROM "+ entityName;
				 ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
				return resultList;
			}catch (Exception e) {
				DefaultLogger.error(this, "############# error in listing "+entityName,e);
				e.printStackTrace();
				throw new ValuationAgencyException("Unable to get List for "+entityName);
			}	
	}
	
	public List getAllValuationAgency(){
		String query = "FROM "+getEntityName() +" valuationAgency ORDER BY valuationAgency.valuationAgencyCode  ";
		ArrayList resultList;
		
		if( query != "" ){
			return resultList = (ArrayList) getHibernateTemplate().find(query);
			
		}else{
			throw new ValuationAgencyException("Unable to get List for "+getEntityName());
		}
	}
	
	public List getFilteredValuationAgency(String code,String name){
		String order=" ORDER BY valuationAgency.valuationAgencyCode";
		String query = "FROM "+getEntityName() +" valuationAgency ";
		ArrayList resultList;
		if("".equals(code))
			code=null;
		if("".equals(name))
			name=null;
		if(null!=code && null!=name)
			query=query+"where lower(valuationAgency.valuationAgencyCode) = '"+code.toLowerCase()+"' and lower(valuationAgency.valuationAgencyName) like '%"+name.toLowerCase()+"%'";
		if(null!=code && null==name)
			query=query+"where lower(valuationAgency.valuationAgencyCode) = '"+code.toLowerCase()+"'";
		if(null!=name && null==code)
			query=query+"where lower(valuationAgency.valuationAgencyName) like '%"+name.toLowerCase()+"%'";
		query=query+order;
		if( query != "" ){
			return resultList = (ArrayList) getHibernateTemplate().find(query);
			
		}else{
			throw new ValuationAgencyException("Unable to get List for "+getEntityName());
		}
	}
	
	
	
	//*****************************UPLOAD***********************************
	
	public int insertValuationAgency(IFileMapperMaster fileMapperMaster, String userName, ArrayList result) {
		// TODO Auto-generated method stub
		int noOfRecInserted = 0;
		DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
		Long key=null;
		if (result == null || result.size() <= 0) {
			throw new IncompleteBatchJobException(
					"Data to be instered to DB is null or empty");
		}
		try {
			for (int index = 0; index < result.size(); index++) {
				HashMap eachDataMap = (HashMap) result.get(index);
				OBValuationAgency obValuationAgency = new OBValuationAgency();
				
//				obValuationAgency.setValuationAgencyCode((String) eachDataMap.get("VALUATION_AGENCY_CODE"));
				
				String valuationCode = (String) eachDataMap.get("VALUATION_AGENCY_CODE");
				String countryCode = (String) eachDataMap.get("COUNTRY_CODE");
				String regionCode = (String) eachDataMap.get("REGION_CODE");
				String stateCode = (String) eachDataMap.get("STATE_CODE");
				String cityCode = (String) eachDataMap.get("CITY_CODE");
				if( valuationCode != null && ! valuationCode.equals("") )
					obValuationAgency.setValuationAgencyCode(valuationCode);
				else
					obValuationAgency.setValuationAgencyCode(getValuationAgencyCode());
				
				obValuationAgency.setValuationAgencyName((String) eachDataMap.get("VALUATION_AGENCY_NAME"));
				obValuationAgency.setAddress(eachDataMap.get("ADDRESS").toString());
				
				if(countryCode!=null && !countryCode.trim().equals("")){
				ICountry country = getCountryDAO().getCountryByCountryCode((String)eachDataMap.get("COUNTRY_CODE"));
				obValuationAgency.setCountry(country);
				}
				if(regionCode!=null && !regionCode.trim().equals("")){
				IRegion region = getRegionDAO().getRegionByRegionCode((String)eachDataMap.get("REGION_CODE"));
				obValuationAgency.setRegion(region);
				}	
				if(stateCode!=null && !stateCode.trim().equals("")){
				IState state = getStateDAO().getStateByStateCode((String)eachDataMap.get("STATE_CODE"));
				obValuationAgency.setState(state);
				}
				if(cityCode!=null && !cityCode.trim().equals("")){
				ICity city = getCityDAO().getCityByCityCode((String)eachDataMap.get("CITY_CODE"));
				obValuationAgency.setCityTown(city);
				}
				
				obValuationAgency.setLastUpdateDate(new Date());
				obValuationAgency.setCreateBy(userName);
				obValuationAgency.setCreationDate(new Date());
				
				obValuationAgency.setStatus("ACTIVE");
				obValuationAgency.setDeprecated("N");
				obValuationAgency.setLastUpdateBy(userName);
				key = (Long) getHibernateTemplate().save("stageOBValuationAgency", obValuationAgency);				
				//create FileMapperMaster
				
				IFileMapperMaster fileMapper = new OBFileMapperMaster();
				fileMapper.setFileId(fileMapperMaster.getFileId());
				fileMapper.setSysId(key.longValue());
				fileMapper.setTransId(fileMapperMaster.getTransId());
				if(!(fileMapperMaster==null)){
					key = (Long) getHibernateTemplate().save("fileMapper", fileMapper);
					getHibernateTemplate().get("fileMapper", key);
					}else{
						throw new ValuationAgencyException("ERROR- Entity name or key is null ");
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
	
	public IFileMapperId createFileId(String entityName, IFileMapperId fileId) throws ValuationAgencyException {
		if(!(entityName==null|| fileId==null)){		
		Long key = (Long) getHibernateTemplate().save(entityName, fileId);
		fileId.setId(key.longValue());
		return fileId;
		}else{
			throw new ValuationAgencyException("ERROR- Entity name or key is null ");
		}
	}
	
	
	public IValuationAgency insertValuationAgency(String entityName,
			IValuationAgency valuationAgency)
			throws ValuationAgencyException {
		if(!(entityName==null|| valuationAgency==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, valuationAgency);
			valuationAgency.setId(key.longValue());
			return valuationAgency;
			}else{
				throw new ValuationAgencyException("ERROR- Entity name or key is null ");
			}
	}
	
	public IFileMapperId insertValuationAgency(String entityName,
			IFileMapperId fileId, IValuationAgencyTrxValue trxValue)
			throws ValuationAgencyException {
		
		if(!(entityName==null|| fileId==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, fileId);
			fileId.setId(key.longValue());
			return fileId;
		}else{
			throw new ValuationAgencyException("ERROR- Entity name or key is null ");
		}	
	}
	
	
	public IFileMapperId getInsertFileList(String entityName, Serializable key)
	throws ValuationAgencyException {
		if(!(entityName==null|| key==null)){
			
			return (IFileMapperId) getHibernateTemplate().get(entityName, key);
			}else{
				throw new ValuationAgencyException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	
	
	public List getAllStageValuationAgency(String searchBy, String login) {
		List resultList = null;
		String searchByValue = searchBy;
		String strId ="";
		Map parametersList = new HashMap();
		parametersList.put("transId",searchBy);
		DetachedCriteria criteria = DetachedCriteria.forEntityName("fileMapper").add(Restrictions.allEq(parametersList));
		List listId = getHibernateTemplate().findByCriteria(criteria);
			for (int i = 0; i < listId.size(); i++) {
			OBFileMapperMaster map = (OBFileMapperMaster) listId.get(i);
//				System.out.println("val = " + map.getSysId());
				if(!strId.equals("")){
					strId +=",";
				}
				strId += map.getSysId();
		}
		
		if(!strId.equals("")){
			Map parameters = new HashMap();
			parameters.put("deprecated","N");
			parameters.put("id", strId);
			//DetachedCriteria crit = DetachedCriteria.forEntityName("stagingRelationshipMgr").add((Restrictions.in("id", sysId)));
			String query = "SELECT FROM "+IValuationAgencyDao.STAGE_VALUATION_AGENCY_NAME+" WHERE DEPRECATED = 'N' AND ID IN ("+strId+")";
			try {
				if (searchByValue.trim() != "") {
					resultList = getHibernateTemplate().find(query);
				}
	
			} catch (Exception e) {
				throw new ValuationAgencyException("ERROR-- While retriving RelationshipMgr");
			}
		}
		return resultList;
	}
	
	/**
	 * @return list of files uploaded in staging table of RelationshipMgr.
	 */
	public List getFileMasterList(String searchBy) {
		Map parametersList = new HashMap();
		parametersList.put("transId",searchBy);
		DetachedCriteria criteria = DetachedCriteria.forEntityName("fileMapper").add(Restrictions.allEq(parametersList));
		List listId = getHibernateTemplate().findByCriteria(criteria);
       return listId;
	}
	
	public IValuationAgency insertActualValuationAgency(String sysId)
	throws ValuationAgencyException {
		Map parametersMap = new HashMap();
		parametersMap.put("id", new Long(sysId));
		
		DetachedCriteria criteria = DetachedCriteria.forEntityName(IValuationAgencyDao.STAGE_VALUATION_AGENCY_NAME).add(Restrictions.allEq(parametersMap));
		List holidayList = getHibernateTemplate().findByCriteria(criteria);
			return (IValuationAgency)holidayList.get(0);
		}
	
	public boolean isUniqueCode(String branchCode)throws ValuationAgencyException
	{   ArrayList resultList =null;
		
		if(!(branchCode==null && branchCode.trim()=="")){
			String query = "SELECT FROM "+IValuationAgencyDao.ACTUAL_VALUATION_AGENCY_NAME+" WHERE  VALUATION_AGENCY_CODE='"+branchCode+"'";
			 resultList = (ArrayList) getHibernateTemplate().find(query);
		/*	if(resultList.size()==0){
				String queryval = "SELECT FROM "+IValuationAgencyDao.STAGE_VALUATION_AGENCY_NAME+" WHERE  VALUATION_AGENCY_CODE='"+branchCode+"'";
				 resultList = (ArrayList) getHibernateTemplate().find(queryval);
			
			}*/
			
			if(resultList.size()>0){
					return true;
			}else 
				return false;
		
		}else{
			throw new SystemBankException("ERROR- Entity name or key is null ");
		}
	}
	
	public boolean isPrevFileUploadPending() {

		String rowCount = "FROM com.integrosys.cms.app.transaction.OBCMSTrxValue obcms WHERE obcms.transactionType='ADD_VALUATION_AGENCY'";
		List rowList = getHibernateTemplate().find(rowCount);
		if(rowList.size()>0){
			String sqlQuery = "FROM com.integrosys.cms.app.transaction.OBCMSTrxValue obcms WHERE obcms.transactionType='ADD_VALUATION_AGENCY' AND status != 'ACTIVE'";
			List holidayList = getHibernateTemplate().find(sqlQuery);
			if(holidayList.size()>0){
				return true;
			}
		}
		return false;
	}
	
	private String getValuationAgencyCode() {
		Query query = currentSession().createSQLQuery("SELECT VALUATION_AGENCY_SEQ.NEXTVAL FROM dual");
		String sequenceNumber = query.uniqueResult().toString();
		NumberFormat numberFormat = new DecimalFormat("0000000");
		String valuationCode = numberFormat.format(Long.parseLong(sequenceNumber));
		valuationCode = "VAL" + valuationCode;		
		return valuationCode;
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws ValuationAgencyException, TrxParameterException,TransactionException {
		OBCMSTrxValue obCMSTrxValue = new OBCMSTrxValue();
		String query = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='ADD_VALUATION_AGENCY' AND obcms.transactionID='"+obFileMapperMaster.getTransId()+"'";
		List list = getHibernateTemplate().find(query);
		obCMSTrxValue = (OBCMSTrxValue)list.get(0);
		getHibernateTemplate().flush();
		getHibernateTemplate().delete(obCMSTrxValue);			
	}

	public boolean isValuationNameUnique(String valuationName) throws ValuationAgencyException, TrxParameterException,TransactionException {
		String stagingQuery = "SELECT FROM "+IValuationAgencyDao.STAGE_VALUATION_AGENCY_NAME+" WHERE UPPER(VALUATION_AGENCY_NAME) like '"+valuationName.toUpperCase()+"' ";
		ArrayList stagingResultList = (ArrayList) getHibernateTemplate().find(stagingQuery);
		if( stagingResultList.size() > 0 ){
			String actualQuery = "SELECT FROM "+IValuationAgencyDao.ACTUAL_VALUATION_AGENCY_NAME+" WHERE STATUS != 'INACTIVE' AND DEPRECATED != 'Y' AND UPPER(VALUATION_AGENCY_NAME) like '"+valuationName.toUpperCase()+"' ";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if( actualResultList.size() > 0 )
				return true;
			else
				return false;
		}	
		else 
			return false;		 
	}

	public String isCodeExisting(String countryCode, String regionCode,String stateCode, String cityCode) throws ValuationAgencyException,TrxParameterException, TransactionException {
		String entityCountryName = "actualCountry";
		String entityRegionName = "actualRegion";
		String entityStateName = "actualState";
		String entityCityName = "actualCity";
		String code = "";
		
		if( countryCode != null &&  ! countryCode.equals("") ){
			String countryQuery = "SELECT FROM " + entityCountryName + " c WHERE UPPER(country_code) like '"+countryCode.toUpperCase()+"' ";
			List countryList = (ArrayList) getHibernateTemplate().find(countryQuery);
			if( countryList.size() > 0 ){
				if( regionCode != null && ! regionCode.equals("") ){ 
					Iterator countryIterator = countryList.iterator();
					if( countryIterator.hasNext() ){
						ICountry country = (ICountry)countryIterator.next();
						long countryId = country.getIdCountry();
						String regionQuery = "SELECT FROM " + entityRegionName + " reg WHERE UPPER(region_code) like '"+regionCode.toUpperCase()+"' AND reg.countryId = "+new Long(countryId);
						List regionList = (ArrayList) getHibernateTemplate().find(regionQuery);
						if( regionList.size() > 0 ){
							if( stateCode != null && ! stateCode.equals("") ){ 
								Iterator regionIterator = regionList.iterator();
								if( regionIterator.hasNext() ){
									IRegion region = (IRegion)regionIterator.next();
									long regionId = region.getIdRegion();
									String stateQuery = "SELECT FROM " + entityStateName + " st WHERE UPPER(state_code) like '"+stateCode.toUpperCase()+"' AND st.regionId = "+new Long(regionId);
									List stateList = (ArrayList) getHibernateTemplate().find(stateQuery);
									if( stateList.size() > 0 ){
										if( cityCode != null && ! cityCode.equals("") ){
											Iterator stateIterator = stateList.iterator();
											if( stateIterator.hasNext() ){
												IState state = (IState)stateIterator.next();
												long stateId = state.getIdState();
												String cityQuery = "SELECT FROM " + entityCityName + " ct WHERE UPPER(city_code) like '"+cityCode.toUpperCase()+"' AND ct.stateId = "+new Long(stateId);
												List cityList = (ArrayList) getHibernateTemplate().find(cityQuery);
												if( cityList.size() == 0 ){
													code = "cityCode";	// City Code Not Exist;
												}
											}											
										}	// End If City Code
									}	// End If stateList
									else{
										code = "stateCode";	// State Code Not Exist;
									}
								}		
							}	// End If State Code
						}	// End If regionList
						else{
							code = "regionCode";	// Region Code Not Exist;
						}							
					}
				}	// End If Region Code
			}	// End If countryList
			else{
				code = "countryCode";	// Country Code Not Exist;
			}			
		}
		return code;
	}
	
	public String getValuationAgencyName(String companyId){
		String agencyName = null;
		String query = "FROM "+getEntityName() +" WHERE ID = "+companyId;
		ArrayList resultList;
		
		if( query != "" ){
			resultList = (ArrayList) getHibernateTemplate().find(query);
			if( resultList.size() > 0 ){
				Iterator itr = resultList.iterator();
				if(itr.hasNext()){
					IValuationAgency valuationAgency = (IValuationAgency) itr.next();
					agencyName = valuationAgency.getValuationAgencyName();
				}
			}
			
		}else{
			throw new ValuationAgencyException("Unable to get List for "+getEntityName());
		}
		return agencyName;
	}
}