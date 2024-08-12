package com.integrosys.cms.ui.manualinput.customer.bankingmethod;


import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.customer.bus.IBankingMethod;
import com.integrosys.cms.app.customer.bus.IIfscMethod;
import com.integrosys.cms.app.customer.bus.OBBankingMethod;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

/**
 * Purpose : This RelationshipMgrDAOImpl implements the methods that will be available to the
 * operating on a Relationship Manager 
 *  
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 */

public class BankingMethodDAOImpl extends HibernateDaoSupport implements IBankingMethodDAO {
	
	/*private IRegionDAO regionDAO;
	
	public IRegionDAO getRegionDAO() {
		return regionDAO;
	}
	public void setRegionDAO(IRegionDAO regionDAO) {
		this.regionDAO = regionDAO;
	}
	
	
	public String getEntityName(){
		return IRelationshipMgrDAO.ACTUAL_ENTITY_NAME; 
	}*/
	public String getEntityName(){
		return IBankingMethodDAO.ACTUAL_BANKING_METHOD_ENTITY_NAME; 
	}
	
	public String getStageEntityName(){
		return IBankingMethodDAO.STAGING_BANKING_METHOD_ENTITY_NAME; 
	}
	
	/**
	 * returns SearchResult List of Other Banks
	 */
	/*public SearchResult getRelationshipMgr() throws BankingMethodException{

		try{
			ArrayList resultList = (ArrayList) getHibernateTemplate().loadAll(OBRelationshipMgr.class);
			return new SearchResult(0, resultList.size(), resultList.size(), resultList);
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getRelationshipMgr",obe);
			obe.printStackTrace();
			throw new BankingMethodException("Unable to get other bank");
		}
	}

	*//**
	 * Returns other bank present for the  input bank id
	 *//*
	public IRelationshipMgr getRelationshipMgrById(long id) throws BankingMethodException {
		IRelationshipMgr RelationshipMgr = new OBRelationshipMgr();
		try{
			RelationshipMgr = (IRelationshipMgr)getHibernateTemplate().load(getEntityName(), new Long(id));
			return RelationshipMgr;
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getRelationshipMgrById ",obe);
			obe.printStackTrace();
			throw new BankingMethodException("Unable to find other bank with id ["+id+"]");
		}
	
	}

	*//**
	 * Return List of Relationship Manager.
	 *//*
	public SearchResult getRelationshipMgrList(String rmCode,String rmName) throws BankingMethodException{

		try{
			String query = "SELECT FROM "+getEntityName()+" WHERE DEPRECATED!='Y'";
			if(rmCode != null && !rmCode.trim().equals("")){
				query = query + " AND RM_MGR_CODE like '%"+rmCode.toUpperCase()+"%' ";
			}
			if(rmName!=null && !rmName.trim().equals("")){
				query = query + " AND upper(RM_MGR_NAME) like '%"+rmName.toUpperCase()+"%'";
			}
			ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			return new SearchResult(0, resultList.size(), resultList.size(), resultList);
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getRelationshipMgrList ",obe);
			obe.printStackTrace();
			throw new BankingMethodException("Unable to get Relationship Manager List");
		}
		
	}
	
	public SearchResult getRelationshipMgrList(String regionId) throws BankingMethodException{

		try{
			DefaultLogger.debug(this, "inside getRelationshipMgrList() method RMDAO------ 120------");
			String query = "SELECT FROM "+getEntityName()+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y'";
			if(regionId != null && !"".equals(regionId)){
				query = query + " AND REGION ='"+regionId+"' ";
			}
			DefaultLogger.debug(this, "---------------Query fired-------- 125------"+query);
			ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			return new SearchResult(0, resultList.size(), resultList.size(), resultList);
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getRelationshipMgrList ",obe);
			obe.printStackTrace();
			throw new BankingMethodException("Unable to get Relationship Manager List");
		}
		
	}

	*//**
	 * Updates the other bank
	 *//*
	public IRelationshipMgr updateRelationshipMgr(IRelationshipMgr RelationshipMgr) throws BankingMethodException {

		try{
			getHibernateTemplate().saveOrUpdate(getEntityName(), RelationshipMgr);
			IRelationshipMgr returnObj = (IRelationshipMgr) getHibernateTemplate().load(getEntityName(),new Long(RelationshipMgr.getId()));
			return returnObj;
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in updateRelationshipMgr ",obe);
			obe.printStackTrace();
			throw new BankingMethodException("Unable to update other bank with id ["+RelationshipMgr.getId()+"]");
		}
		
	}
	
	*//**
	 * soft delete the other bank
	 *//*
	
	public IRelationshipMgr deleteRelationshipMgr(IRelationshipMgr RelationshipMgr) throws BankingMethodException {
		IRelationshipMgr returnObj = new OBRelationshipMgr();
		try{
			RelationshipMgr.setStatus("INACTIVE");
			RelationshipMgr.setDeprecated("Y");
			getHibernateTemplate().saveOrUpdate(getEntityName(), RelationshipMgr);
			returnObj = (IRelationshipMgr) getHibernateTemplate().load(getEntityName(),new Long(RelationshipMgr.getId()));
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in deleteRelationshipMgr ",obe);
			obe.printStackTrace();
			throw new BankingMethodException("Unable to delete other bank with id ["+RelationshipMgr.getId()+"]");
		}
		return returnObj;
	}
	
	*//**
	 * Create the other bank
	 *//*
	public IRelationshipMgr createRelationshipMgr(IRelationshipMgr RelationshipMgr) throws BankingMethodException {
		IRelationshipMgr returnObj = new OBRelationshipMgr();
		try{
			System.out.println("getEntityName() createRelationshipMgr(IRelationshipMgr RelationshipMgr) ===========>"+getEntityName());
			getHibernateTemplate().save(getEntityName(), RelationshipMgr);
			returnObj = (IRelationshipMgr) getHibernateTemplate().load(getEntityName(),new Long(RelationshipMgr.getId()));
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in createRelationshipMgr ",obe);
			obe.printStackTrace();
			throw new BankingMethodException("Unable to create other bank ");
		}
		return returnObj;
	}

	*//**
	 * @return RelationshipMgr Object
	 * @param Entity Name
	 * @param Key  
	 * This method returns Other Bank Object
	 *//*
	public IRelationshipMgr getRelationshipMgr(String entityName, Serializable key)throws BankingMethodException {
		if(!(entityName==null|| key==null)){
		return (IRelationshipMgr) getHibernateTemplate().get(entityName, key);
		}else{
			throw new BankingMethodException("ERROR- Entity name or key is null ");
		}
	}

	*//**
	 * @return RelationshipMgr Object
	 * @param Entity Name
	 * @param RelationshipMgr Object  
	 * This method Updates Other Bank Object
	 *//*
	public IRelationshipMgr updateRelationshipMgr(String entityName, IRelationshipMgr item)throws BankingMethodException {
		if(!(entityName==null|| item==null)){
		getHibernateTemplate().update(entityName, item);

		return (IRelationshipMgr) getHibernateTemplate().load(entityName,
				new Long(item.getId()));
		}else{
			throw new BankingMethodException("ERROR- Entity name or key is null ");
		}

	}
	*//**
	 * @return RelationshipMgr Object
	 * @param Entity Name
	 * @param RelationshipMgr Object  
	 * This method Creates Other Bank Object
	 *//*

	public IRelationshipMgr createRelationshipMgr(String entityName,
			IRelationshipMgr relationshipMgr)throws BankingMethodException {
		if(!(entityName==null|| relationshipMgr==null)){
			if( relationshipMgr.getRelationshipMgrCode() == null || relationshipMgr.getRelationshipMgrCode().equals("")){
				String relationshipMgrCode=getRelationshipMgrCode();
				relationshipMgr.setRelationshipMgrCode(relationshipMgrCode);
			}
		Long key = (Long) getHibernateTemplate().save(entityName, relationshipMgr);
		relationshipMgr.setId(key.longValue());
		return relationshipMgr;
		}else{
			throw new BankingMethodException("ERROR- Entity name or key is null ");
		}
	}
	
	private String getRelationshipMgrCode() {
		Query query = getSession().createSQLQuery("SELECT RELATIONSHIP_MGR_SEQ.NEXTVAL FROM dual");
		String sequenceNumber = query.uniqueResult().toString();
		NumberFormat numberFormat = new DecimalFormat("0000000");
		String relationshipMgrCode = numberFormat.format(Long.parseLong(sequenceNumber));
		relationshipMgrCode = "RM" + relationshipMgrCode;		
		return relationshipMgrCode;
	}
	
	public boolean isRMCodeUnique(String rmCode){
		String query = "SELECT FROM "+IRelationshipMgrDAO.STAGING_ENTITY_NAME+" WHERE RM_MGR_CODE like '"+rmCode+"'";
		ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
		if(resultList.size()>0){
			String query1 = "SELECT FROM "+getEntityName()+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' AND RM_MGR_CODE like '"+rmCode+"'";
			ArrayList resultList1 = (ArrayList) getHibernateTemplate().find(query1);
			if(resultList1.size()>0)
				return true;
			else
				return false;
		}	
		else 
			return false;		 
	}
	
	public boolean isRelationshipMgrNameUnique(String relationshipMgrName) {
		String newRelationshipMgrName = "";
		int len =relationshipMgrName.length();
		for(int i=0;i<len;i++){
			if( String.valueOf(relationshipMgrName.charAt(i)).equals("'"))
				newRelationshipMgrName = newRelationshipMgrName.concat("'");
			
			newRelationshipMgrName = newRelationshipMgrName.concat(String.valueOf(relationshipMgrName.charAt(i)));
			}
		String stagingQuery = "SELECT FROM "+IRelationshipMgrDAO.STAGING_ENTITY_NAME+" WHERE UPPER(RM_MGR_NAME) like '"+newRelationshipMgrName.toUpperCase()+"'";
		ArrayList stagingResultList = (ArrayList) getHibernateTemplate().find(stagingQuery);
		if( stagingResultList.size() > 0 ){
			String actualQuery = "SELECT FROM "+getEntityName()+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' AND UPPER(RM_MGR_NAME) like '"+newRelationshipMgrName.toUpperCase()+"'";
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
	
	*//**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 *//*
	public IFileMapperId insertRelationshipMgr(String entityName,
			IFileMapperId fileId, IRelationshipMgrTrxValue trxValue)
			throws BankingMethodException {
		
		if(!(entityName==null|| fileId==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, fileId);
			fileId.setId(key.longValue());
			return fileId;
		}else{
			throw new BankingMethodException("ERROR- Entity name or key is null ");
		}	
	}
	
	*//**
	 * @return integer (record count of inserted record)
	 * @param FileMapperMaster
	 * @param userName  
	 * @param result of upload files 
	 * This method Creates FileMapperMaster Object
	 *//*
	public int insertRelationshipMgr(IFileMapperMaster fileMapperMaster, String userName, ArrayList result) {
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
				OBRelationshipMgr obRelationshipMgr = new OBRelationshipMgr();
				
				String rmCode = (String) eachDataMap.get("RM_MGR_CODE");				
				if( rmCode == null || rmCode.equals("")  )
					obRelationshipMgr.setRelationshipMgrCode(getRelationshipMgrCode());
				else
					obRelationshipMgr.setRelationshipMgrCode(rmCode);
				
				obRelationshipMgr.setRelationshipMgrName((String) eachDataMap.get("RM_MGR_NAME"));
				obRelationshipMgr.setRelationshipMgrMailId((String)eachDataMap.get("RM_MGR_MAIL"));
				obRelationshipMgr.setReportingHeadName((String)eachDataMap.get("REPORTING_HEAD"));
				obRelationshipMgr.setReportingHeadMailId((String)eachDataMap.get("REPORTING_HEAD_MAIL"));
				IRegion region = new OBRegion();
				region = getRegionByRegionCode((String)eachDataMap.get("REGION_CODE"));
				obRelationshipMgr.setRegion(region);
				obRelationshipMgr.setLastUpdateDate(new Date());
				obRelationshipMgr.setCreatedBy(userName);
				obRelationshipMgr.setCreationDate(new Date());
				obRelationshipMgr.setStatus("ACTIVE");
				obRelationshipMgr.setDeprecated("N");
				obRelationshipMgr.setLastUpdateBy(userName);
				key = (Long) getHibernateTemplate().save("stagingRelationshipMgr", obRelationshipMgr);				
				//create FileMapperMaster
				
				IFileMapperMaster fileMapper = new OBFileMapperMaster();
				fileMapper.setFileId(fileMapperMaster.getFileId());
				fileMapper.setSysId(key.longValue());
				fileMapper.setTransId(fileMapperMaster.getTransId());
				if(!(fileMapperMaster==null)){
					key = (Long) getHibernateTemplate().save("fileMapper", fileMapper);
					getHibernateTemplate().get("fileMapper", key);
					}else{
						throw new BankingMethodException("ERROR- Entity name or key is null ");
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

	*//**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 *//*
	public IFileMapperId createFileId(String entityName, IFileMapperId fileId)
	throws BankingMethodException {
			// TODO Auto-generated method stub
			if(!(entityName==null|| fileId==null)){
		
		Long key = (Long) getHibernateTemplate().save(entityName, fileId);
		fileId.setId(key.longValue());
		return fileId;
		}else{
			throw new BankingMethodException("ERROR- Entity name or key is null ");
		}
	}
	
	*//**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 *//*
	public IFileMapperId getInsertFileList(String entityName, Serializable key)
	throws BankingMethodException {
		if(!(entityName==null|| key==null)){
			
			return (IFileMapperId) getHibernateTemplate().get(entityName, key);
			}else{
				throw new BankingMethodException("ERROR-- Entity Name Or Key is null");
		}
	}
	

	*//**
	 * @return RelationshipMgr Object
	 * @param Entity Name
	 * @param sysId  
	 * This method Creates RelationshipMgr Object
	 *//*
	public IRelationshipMgr insertActualRelationshipMgr(String sysId)
	throws BankingMethodException {
		Map parametersMap = new HashMap();
		parametersMap.put("id", new Long(sysId));
		
		DetachedCriteria criteria = DetachedCriteria.forEntityName("stagingRelationshipMgr").add(Restrictions.allEq(parametersMap));
		List relationshipMgrList = getHibernateTemplate().findByCriteria(criteria);
			return (IRelationshipMgr)relationshipMgrList.get(0);
		}
	*//**
	 * @return RelationshipMgr Object
	 * @param Entity Name
	 * @param RelationshipMgr Object  
	 * This method Creates RelationshipMgr Object
	 *//*
	public IRelationshipMgr insertRelationshipMgr(String entityName,
			IRelationshipMgr relationshipMgr)
			throws BankingMethodException {
		if(!(entityName==null|| relationshipMgr==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, relationshipMgr);
			relationshipMgr.setId(key.longValue());
			return relationshipMgr;
			}else{
				throw new BankingMethodException("ERROR- Entity name or key is null ");
			}
	}
	
	//************************************************************************
	//********************* Methods for File Upload **************************
	//************************************************************************
	
	public List getAllStageRelationshipMgr(String searchBy, String login) {
		List resultList = null;
		String searchByValue = searchBy;
		String strId ="";
		List listId = getFileMasterList(searchBy);
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
			String query = "SELECT FROM "+IRelationshipMgrDAO.STAGING_ENTITY_NAME+" WHERE DEPRECATED = 'N' AND ID IN ("+strId+")";
			try {
				if (searchByValue.trim() != "") {
					resultList = getHibernateTemplate().find(query);
				}
	
			} catch (Exception e) {
				throw new BankingMethodException("ERROR-- While retriving RelationshipMgr");
			}
		}
		return resultList;
	}
	
	
	*//**
	 * @return Maker check if previous upload is pending.
	 *//*
	public boolean isPrevFileUploadPending() {

		String rowCount = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_RELATION_MGR'";
		List rowList = getHibernateTemplate().find(rowCount);
		if(rowList.size()>0){
			String sqlQuery = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_RELATION_MGR' AND status != 'ACTIVE'";
			List holidayList = getHibernateTemplate().find(sqlQuery);
			if(holidayList.size()>0){
				return true;
			}
		}
		return false;
	}
	
	*//**
	 * @return list of files uploaded in staging table of RelationshipMgr.
	 *//*
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
			ArrayList resultList;
			
			if( query != "" ){
				
				ArrayList countryList =(ArrayList) getHibernateTemplate().find(query);
				 
				return (IRegion) countryList.get(0); 
				
			}else{
				throw new NoSuchGeographyException("Unable to get List for "+getEntityName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoSuchGeographyException("ERROR-- While retriving getRegionByRegionCode ");
		}
		
	}
	
	public IRegion getRegionByRegionName(String regionName) {
			try{
				JndiTemplate jndiTemplate = new JndiTemplate();

				String dataSourceJndiName = PropertyManager.getValue("dbconfig.weblogic.datasource.jndiname");
				DataSource ofaDataSource = (DataSource) jndiTemplate
						.lookup(dataSourceJndiName, javax.sql.DataSource.class);
				
				JdbcTemplate jdbc = new JdbcTemplate(ofaDataSource);
				

				String query = "FROM "+IRegionDAO.ACTUAL_ENTITY_NAME_REGION+" WHERE UPPER(REGION_NAME) = UPPER('"+regionName+"')  AND "
//						+ "COUNTRY_ID IN (SELECT COUNTRYS.idCountry FROM actualCountry COUNTRYS WHERE COUNTRYS.countryCode = 'IN') ";
						+ " COUNTRY_ID IN (FROM actualCountry WHERE COUNTRY_CODE = 'IN') ";	
				System.out.println("IRegion getRegionByRegionName(String regionName)=>"+query);
//					String query = "select * FROM CMS_REGION where REGION_NAME ='"+regionName+"'";
//					List list = jdbc.query(query, new RowMapper() {
//
//						@Override
//						public Object mapRow(ResultSet rs, int rowNum) throws SQLException {	
//							IRegion region = new OBRegion();
//							region.setIdRegion(rs.getLong("ID"));
//							region.setRegionCode(rs.getString("REGION_CODE"));
//							region.setRegionName(rs.getString("REGION_NAME"));
//							return region;
//						}
//						});
					ArrayList list =(ArrayList) getHibernateTemplate().find(query);
					if(!list.isEmpty()){
					IRegion data = new OBRegion();
					data = (OBRegion) list.get(0);
					return data;
					}
//						throw new NoSuchGeographyException("Unable to get List for "+getEntityName());
									
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoSuchGeographyException("ERROR-- While retriving getRegionByRegionName ");
		}
//			return null;
			return null;
		
	}
	
	
	public IRelationshipMgr getRelationshipMgrByName(String relationshipMgrName, String rmMgrCode) {
		try{
			String query = "FROM actualRelationshipMgr WHERE RM_MGR_NAME ='"+relationshipMgrName+"' and RM_MGR_CODE = '"+rmMgrCode+"'";				

				ArrayList list =(ArrayList) getHibernateTemplate().find(query);
				if(!list.isEmpty()){
				IRelationshipMgr data = new OBRelationshipMgr();
				data = (OBRelationshipMgr) list.get(0);
				return data;
				}								
	} catch (Exception e) {
		e.printStackTrace();
	}
		return null;
	
}
	
	public IRelationshipMgr getRelationshipMgrByNameAndRMCode(String relationshipMgrName, String rmMgrCode) {
		try{
			String query = "FROM actualRelationshipMgr WHERE RM_MGR_NAME ='"+relationshipMgrName+"' and RM_MGR_CODE = '"+rmMgrCode+"'";				

				ArrayList list =(ArrayList) getHibernateTemplate().find(query);
				if(!list.isEmpty()){
				IRelationshipMgr data = new OBRelationshipMgr();
				data = (OBRelationshipMgr) list.get(0);
				return data;
				}								
	} catch (Exception e) {
		e.printStackTrace();
	}
		return null;
	
}
	
	public List getRegionList(String countryName){
		List regionList = new ArrayList();
		try{			
			DefaultLogger.debug(this, "inside RelatonshipDAO ----- 532-------");
			String query = "SELECT FROM "+IRegionDAO.ACTUAL_ENTITY_NAME_REGION+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' " +
					" AND COUNTRY_ID IN (FROM actualCountry WHERE Upper (COUNTRY_NAME) like Upper ('"+countryName+"%'))";
			DefaultLogger.debug(this, "-------query fired ----- 535-------"+query);
			regionList = (List) getHibernateTemplate().find(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
		DefaultLogger.debug(this, "-----after--query fired --regionList.size--- 540-------"+regionList.size());
		return regionList;
	}
	
	public boolean isValidRegionCode(String regionCode) {		
		try {
			String query = "FROM "+IRegionDAO.ACTUAL_ENTITY_NAME_REGION+" WHERE UPPER(REGION_CODE) ='"+ regionCode.toUpperCase()+"'";
			if( query != "" ){				
				ArrayList regionList =(ArrayList) getHibernateTemplate().find(query);				 
				if( regionList.size() > 0 )
					return false;
				else
					return true;
			}else{
				throw new NoSuchGeographyException("Unable to get List for "+getEntityName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoSuchGeographyException("ERROR-- While retriving getCountryByCountryCode ");
		}
		
	}
	
	public ICountry getCountryByCountryCode(String countryCode) {		
		try {
			String query = "FROM "+ICountryDAO.ACTUAL_ENTITY_NAME_COUNTRY+" WHERE COUNTRY_CODE ='"+ countryCode+"'";
			ArrayList resultList;			
			if( query != "" ){				
				ArrayList countryList =(ArrayList) getHibernateTemplate().find(query);				 
				return (ICountry) countryList.get(0);				
			}else{
				throw new NoSuchGeographyException("Unable to get List for "+getEntityName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoSuchGeographyException("ERROR-- While retriving getCountryByCountryCode ");
		}
		
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		OBCMSTrxValue obCMSTrxValue = new OBCMSTrxValue();
		String query = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_RELATION_MGR' AND obcms.transactionID='"+obFileMapperMaster.getTransId()+"'";
		List list = getHibernateTemplate().find(query);
		obCMSTrxValue = (OBCMSTrxValue)list.get(0);
		getHibernateTemplate().flush();
		getHibernateTemplate().delete(obCMSTrxValue);			
	}
	
	*//**
	 * Returns relationship mgr present for the  input relationship code
	 *//*
	public IRelationshipMgr getRelationshipMgrByCode(String id) throws BankingMethodException {
		IRelationshipMgr RelationshipMgr = new OBRelationshipMgr();
		System.out.println("Inside  IRelationshipMgr getRelationshipMgrByCode(String id). and id is=>"+id);
		try{
			String query = "FROM actualRelationshipMgr relMGr WHERE relMGr.relationshipMgrCode='"+id+"'";
			List list = getHibernateTemplate().find(query);
			if(!list.isEmpty()) {
				RelationshipMgr = (IRelationshipMgr)list.get(0);
			}
			getHibernateTemplate().flush();
			if(!list.isEmpty()) {
				return RelationshipMgr;
			}else {
				return null;
			}
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getRelationshipMgrById ",obe);
			obe.printStackTrace();
			throw new BankingMethodException("Unable to find relationship manager with code ["+id+"]");
		}
	
	}

	
	*//**
	 * Returns relationship mgr present for the  input relationship code
	 *//*
	@Override
	public IRelationshipMgr getRelationshipMgrByEmployeeCode(String id) throws BankingMethodException {
		IRelationshipMgr RelationshipMgr = new OBRelationshipMgr();
		try{
			String query = "FROM actualRelationshipMgr relMGr WHERE relMGr.employeeId='"+id+"'";
			List list = getHibernateTemplate().find(query);
			if(!list.isEmpty()){
			RelationshipMgr = (IRelationshipMgr)list.get(0);
			getHibernateTemplate().flush();
			}
			return RelationshipMgr;
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getRelationshipMgrById ",obe);
			obe.printStackTrace();
			throw new BankingMethodException("Unable to find relationship manager with code ["+id+"]");
		}
	
	}
	

	
	
	public boolean isEmployeeIdUnique(String employeeId) {
		
		String stagingQuery = "SELECT FROM "+IRelationshipMgrDAO.STAGING_ENTITY_NAME+" WHERE UPPER(EMPLOYEE_ID) like '"+employeeId.toUpperCase()+"'";
		ArrayList stagingResultList = (ArrayList) getHibernateTemplate().find(stagingQuery);
		if( stagingResultList.size() > 0 ){
			String actualQuery = "SELECT FROM "+getEntityName()+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' AND UPPER(EMPLOYEE_ID) like '"+employeeId.toUpperCase()+"'";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if( actualResultList.size() > 0 )
				return true;
			else
				return false;
		}	
		else 
			return false;		 
	}*/

	/*public IHRMSData getHRMSEmpDetails(String rmEmpID) {
		try{
//		JndiTemplate jndiTemplate = new JndiTemplate();
//
//		String dataSourceJndiName = PropertyManager.getValue("dbconfig.weblogic.datasource.jndiname");
//		DataSource ofaDataSource = (DataSource) jndiTemplate
//				.lookup(dataSourceJndiName, javax.sql.DataSource.class);
//		
//		JdbcTemplate jdbc = new JdbcTemplate(ofaDataSource);
//		
		IHRMSData data = new OBHRMSData();
		
			String query = "FROM hrmsData where EMPLOYEE_CODE ='"+rmEmpID+"'";
//		String query = "FROM actualRelationshipMgr where RM_MGR_CODE = '"+rmEmpID+"'";
			List<IHRMSData> list = getHibernateTemplate().find(query);
//			, new RowMapper() {
//
//				@Override
//				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
//					List <IHRMSData> ihrmsDataList = new ArrayList();
//					while (rs.next()) {
//					long id = rs.getLong("ID");
//					String employeeCode = rs.getString("EMPLOYEE_CODE");
//					String name = rs.getString("NAME");
//					String emailId = rs.getString("EMAIL_ID");
//					String mobileNo = rs.getString("MOBILE_NO");
//					String region = rs.getString("REGION");
//					String city = rs.getString("CITY");
//					String state = rs.getString("STATE");
//					String wboRegion = rs.getString("WBO_REGION");
//					String supervisorEmployeeCode = rs.getString("SUPERVISOR_EMPLOYEE_CODE");
//					String branchCode = rs.getString("BRANCH_CODE");
//				
//					IHRMSData ihrmsData = new OBHRMSData();
//					ihrmsData.setEmployeeCode(employeeCode);
//					ihrmsData.setName(name);
//					ihrmsData.setEmailId(emailId);
//					
//					ihrmsData.setMobileNo(mobileNo);
//					ihrmsData.setRegion(region);
//					ihrmsData.setCity(city);
//					ihrmsData.setState(state);
//					ihrmsData.setWboRegion(wboRegion);
//					ihrmsData.setSupervisorEmployeeCode(supervisorEmployeeCode);
//					ihrmsData.setBranchCode(branchCode);
//					
//					ihrmsDataList.add(ihrmsData);
//					}
//					return ihrmsDataList;
//				}
//				});
			if(!list.isEmpty()){
			data = (OBHRMSData)list.get(0);
			return data;
			}
//			List list = getHibernateTemplate().find(query);
//			List list = getHibernateTemplate().find(query);
//			if (!list.isEmpty()) {
//				data = (IHRMSData) list.get(0);
//				getHibernateTemplate().flush();
//			}
//			return data;
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getHRMSEmpDetails ",obe);
			obe.printStackTrace();
		    return null;
		    }
		return null;
	
	}
	
	public IRelationshipMgr getRMDetails(String rmID) {
		IRelationshipMgr data = new OBRelationshipMgr();
		try{
			String query = "FROM actualRelationshipMgr where RM_MGR_CODE = '"+rmID+"'";
			List list = getHibernateTemplate().find(query);
			if(!list.isEmpty()){
			data = (IRelationshipMgr)list.get(0);
			getHibernateTemplate().flush();
			}
			return data;
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getRelationshipMgrById ",obe);
			obe.printStackTrace();
		    return null;
		    }	
	}
	
	public IHRMSData getLocalCAD(String cadEmployeeCode, String cadBranchCode) {
		IHRMSData data = new OBHRMSData();
		try{
			String query = "FROM hrmsData where EMPLOYEE_CODE  = '"+cadEmployeeCode+"'";
			List list = getHibernateTemplate().find(query);
			if(!list.isEmpty()){
			data = (IHRMSData)list.get(0);
			getHibernateTemplate().flush();
			return data;
			}
			return null;
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in Local CAD ",obe);
			obe.printStackTrace();
            return null;
		}		
	}
	
	public void insertHRMSData(String[] data) {
		try {
//			JndiTemplate jndiTemplate = new JndiTemplate();
//
//			String dataSourceJndiName = PropertyManager.getValue("dbconfig.weblogic.datasource.jndiname");
//			DataSource ofaDataSource = (DataSource) jndiTemplate
//					.lookup(dataSourceJndiName, javax.sql.DataSource.class);
//			JdbcTemplate jdbc = new JdbcTemplate(ofaDataSource);
			System.out.println("Inside insertHRMSData.");
			long nextSequenceSql = getSequence("CMS_HRMS_DATA_SEQ");
			String title = data[1] + " " + data[2] + " " + data[3];
			String status = "ACTIVE";
			String depricated = "N";
			Date date = new Date();


//			String query = "INSERT INTO CMS_HRMS_DATA (ID,VERSION_TIME,EMPLOYEE_CODE,NAME,EMAIL_ID,MOBILE_NO,REGION,CITY,STATE,WBO_REGION,SUPERVISOR_EMPLOYEE_CODE,BRANCH_CODE," + 
//					"DEPRECATED,STATUS,CREATE_BY,CREATION_DATE,LAST_UPDATE_BY,LAST_UPDATE_DATE,BAND) " + 
//					"VALUES ("+nextSequenceSql+",1,'"+ data[0]+"','"+title+"','"+data[4]+"','"+data[5] + " / " + data[6]+"','"+data[7]+"','"+data[8]+"','"+data[9]+"','"+data[10]+"',"  +
//					"'"+data[11]+"','"+data[12]+"','"+depricated+"','"+status+"','a','"+date+"','a','"+date+"','"+data[13]+"')";
//
//				jdbc.execute(query);
			
//			if(!(data==null|| data==null)){
				IHRMSData ihrmsData = new OBHRMSData();
				
				ihrmsData.setId(nextSequenceSql);
				ihrmsData.setVersionTime(1);
				ihrmsData.setEmployeeCode(data[0]);
				ihrmsData.setName(title);
				ihrmsData.setEmailId(data[4]);
				System.out.println("Insert hrms data[5]="+data[5]+"**");
				System.out.println("Insert hrms data[6]="+data[6]+"**");
				if((data[5] != null && !"".equals(data[5])) && (data[6] != null && !"".equals(data[6]))) {
					ihrmsData.setMobileNo(data[5] + " / " + data[6]);
				}
				else if((data[5] != null && !"".equals(data[5])) && (data[6] == null || "".equals(data[6]))) {
					ihrmsData.setMobileNo(data[5]);
				}
				else if((data[5] == null || "".equals(data[5])) && (data[6] != null && !"".equals(data[6]))) {
					ihrmsData.setMobileNo(data[6]);
				}
				else {
					ihrmsData.setMobileNo("");
				}
				ihrmsData.setRegion(data[10]);
				ihrmsData.setCity(data[7]);
				ihrmsData.setState(data[9]);
				ihrmsData.setWboRegion(data[8]);
				ihrmsData.setSupervisorEmployeeCode(data[11]);
				ihrmsData.setBranchCode(data[12]);
				ihrmsData.setDeprecated(depricated);
				ihrmsData.setStatus(status);
				ihrmsData.setCreatedBy("A");
				ihrmsData.setCreationDate(date);
				ihrmsData.setLastUpdateBy("A");
				ihrmsData.setLastUpdateDate(date);
				ihrmsData.setBand(data[13]);
				
				System.out.println("Going for insertHRMSData => save data ino table => hrmsData");
				getHibernateTemplate().save("hrmsData", ihrmsData);
//				fileId.setId(key.longValue());
//				return fileId;
			
			

		} catch (Exception e) {
			System.out.println("Exception in insertHRMSData.");
			e.printStackTrace();
		}finally {
			getHibernateTemplate().flush();
		}

	}

	public void updateHRMSData(IHRMSData  ihrmsData,String[] data) {
		try {
//			JndiTemplate jndiTemplate = new JndiTemplate();
//
//			String dataSourceJndiName = PropertyManager.getValue("dbconfig.weblogic.datasource.jndiname");
//			DataSource ofaDataSource = (DataSource) jndiTemplate
//					.lookup(dataSourceJndiName, javax.sql.DataSource.class);
//			JdbcTemplate jdbc = new JdbcTemplate(ofaDataSource);
			System.out.println("Inside updateHRMSData.");
			String title = data[1] + " " + data[2] + " " + data[3];
			Date date = new Date();
			
//			String query = "update CMS_HRMS_DATA set NAME = '"+title+"',EMAIL_ID = '"+data[4]+"',MOBILE_NO='"+data[5] + " / " + data[6]+"',REGION='"+data[8]+"',CITY='"+data[7]+"',"
//					+ "STATE='"+data[9]+"',WBO_REGION='"+data[10]+"',SUPERVISOR_EMPLOYEE_CODE='"+data[11]+"',BRANCH_CODE='"+data[12]+"'," + 
//					"LAST_UPDATE_BY='"+date+"',BAND = '"+data[13]+"'"
//					+ " where EMPLOYEE_CODE = '"+data[0]+"'";
//			
//			jdbc.execute(query);
			
//			IHRMSData ihrmsData = new OBHRMSData();
			
//			ihrmsData.setId(nextSequenceSql);
//			ihrmsData.setVersionTime(1);
			ihrmsData.setEmployeeCode(data[0]);
			ihrmsData.setName(title);
			ihrmsData.setName(title);
			ihrmsData.setEmailId(data[4]);
			System.out.println("Update hrms data[5]="+data[5]+"**");
			System.out.println("Update hrms data[6]="+data[6]+"**");
			if((data[5] != null && !"".equals(data[5])) && (data[6] != null && !"".equals(data[6]))) {
				ihrmsData.setMobileNo(data[5] + " / " + data[6]);
			}
			else if((data[5] != null && !"".equals(data[5])) && (data[6] == null || "".equals(data[6]))) {
				ihrmsData.setMobileNo(data[5]);
			}
			else if((data[5] == null || "".equals(data[5])) && (data[6] != null && !"".equals(data[6]))) {
				ihrmsData.setMobileNo(data[6]);
			}
			else {
				ihrmsData.setMobileNo("");
			}
			
//			ihrmsData.setMobileNo(data[5] + " / " + data[6]);
			ihrmsData.setRegion(data[10]);
			ihrmsData.setCity(data[7]);
			ihrmsData.setState(data[9]);
			ihrmsData.setWboRegion(data[8]);
			ihrmsData.setSupervisorEmployeeCode(data[11]);
			ihrmsData.setBranchCode(data[12]);
//			ihrmsData.setDeprecated(depricated);
//			ihrmsData.setStatus(status);
//			ihrmsData.setCreatedBy("A");
			ihrmsData.setCreationDate(date);
//			ihrmsData.setLastUpdateBy("A");
			ihrmsData.setLastUpdateDate(date);
			ihrmsData.setBand(data[13]);
			
			System.out.println("Going for updateHRMSData =>update => hrmsData");
			getHibernateTemplate().update("hrmsData", ihrmsData);
			
		} catch (Exception e) {
			System.out.println("Exception in updateHRMSData.");
			e.printStackTrace();
		}finally {
			getHibernateTemplate().flush();
		}

	}

	public void updateRMData(IRelationshipMgr  iRelationshipMgr,String[] data) {
		try {
			String title = data[1] + " " + data[2] + " " + data[3];
			Date date = new Date();
			System.out.println("Inside updateRMData(IRelationshipMgr  iRelationshipMgr,String[] data).");
			iRelationshipMgr.setRelationshipMgrName(title);
			iRelationshipMgr.setRelationshipMgrMailId(data[4]);
			System.out.println("updateRMData data[5]="+data[5]+"**");
			System.out.println("updateRMData data[6]="+data[6]+"**");
			if((data[5] != null && !"".equals(data[5])) && (data[6] != null && !"".equals(data[6]))) {
				iRelationshipMgr.setRelationshipMgrMobileNo(data[5] + " / " + data[6]);
			}
			else if((data[5] != null && !"".equals(data[5])) && (data[6] == null || "".equals(data[6]))) {
				iRelationshipMgr.setRelationshipMgrMobileNo(data[5]);
			}
			else if((data[5] == null || "".equals(data[5])) && (data[6] != null && !"".equals(data[6]))) {
				iRelationshipMgr.setRelationshipMgrMobileNo(data[6]);
			}
			else {
				iRelationshipMgr.setRelationshipMgrMobileNo("");
			}
//			iRelationshipMgr.getRegion().setRegionName((data[7]));
			IRegion region = getRegionByRegionName(data[10]);
			if(null != region) {
				System.out.println("iRelationshipMgr.getRegion() is =>"+iRelationshipMgr.getRegion());
				if(iRelationshipMgr.getRegion() != null) {
			iRelationshipMgr.getRegion().setIdRegion(region.getIdRegion());
			System.out.println("region.getIdRegion() is =>"+region.getIdRegion());
			System.out.println("iRelationshipMgr.getRegion().setIdRegion(region.getIdRegion() is =>"+iRelationshipMgr.getRegion().getIdRegion()+"**");
				}
			}
			
			iRelationshipMgr.setRelationshipMgrCity(data[7]);
			iRelationshipMgr.setRelationshipMgrState(data[9]);
			iRelationshipMgr.setWboRegion(data[8]);
			
			IRelationshipMgr supervisorData1= getRelationshipMgrByCode(data[0]);
			IHRMSData hrmsSupervisorData= getHRMSEmpDetails(data[11]);
			//getHRMSEmpDetails
			System.out.println("hrmsSupervisorData=> "+hrmsSupervisorData);
			System.out.println("supervisorData1=> "+supervisorData1);
			iRelationshipMgr.setReportingHeadEmployeeCode(data[11]);
			if (null != hrmsSupervisorData) {
				iRelationshipMgr.setReportingHeadMobileNo(supervisorData.getRelationshipMgrMobileNo());
				iRelationshipMgr.setReportingHeadName(supervisorData.getRelationshipMgrName());
				iRelationshipMgr.setReportingHeadMailId(supervisorData.getRelationshipMgrMailId());
				
				iRelationshipMgr.setReportingHeadMobileNo(hrmsSupervisorData.getMobileNo());
				iRelationshipMgr.setReportingHeadName(hrmsSupervisorData.getName());
				iRelationshipMgr.setReportingHeadMailId(hrmsSupervisorData.getEmailId());
				
				if (null != supervisorData1) {
				if(null !=supervisorData1.getRegion()) {
//				iRelationshipMgr.setReportingHeadRegion(supervisorData1.getRegion().getRegionCode());
				iRelationshipMgr.setReportingHeadRegion(supervisorData1.getRegion().getRegionName());
				}
				}
			}
			iRelationshipMgr.setLastUpdateDate(date);
			
			System.out.println("Going for update => update(*actualRelationshipMgrUpdate*, iRelationshipMgr)");
//			getHibernateTemplate().update("stagingRelationshipMgr", iRelationshipMgr);actualRelationshipMgrUpdate
//			getHibernateTemplate().update("actualRelationshipMgr", iRelationshipMgr);
			getHibernateTemplate().update("actualRelationshipMgrUpdate", iRelationshipMgr);
			System.out.println("After  update => Completed");
			
		} catch (Exception e) {
			System.out.println("Exception in updateRMData(IRelationshipMgr  iRelationshipMgr,String[] data).");
			e.printStackTrace();
		}finally {
			getHibernateTemplate().flush();
		}

	}

	
	
	private long getSequence(String seqName){
		long seqId=0l;
		try
		{
			seqId = Long.parseLong((new SequenceManager()).getSeqNum(seqName, true));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return seqId;
	}
	
	public ILocalCAD createLocalCAD(String entityName,
			ILocalCAD localCAD){
		try {
		if(!(entityName==null|| localCAD==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, localCAD);
			localCAD.setId(key.longValue());
			return localCAD;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ILocalCAD updateLocalCAD(String entityName,
			ILocalCAD localCAD){
		try {
		if(!(entityName==null|| localCAD==null)){
			
//			Long key = (Long) getHibernateTemplate().save(entityName, localCAD);
			getHibernateTemplate().update(entityName, localCAD);
//			localCAD.setId(key.longValue());
			return localCAD;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ILocalCAD updateStagingLocalCAD(String entityName,
			ILocalCAD localCAD){
		try {
		if(!(entityName==null|| localCAD==null)){
			
//			Long key = (Long) getHibernateTemplate().save(entityName, localCAD);
			getHibernateTemplate().update(entityName, localCAD);
//			localCAD.setId(key.longValue());
			return localCAD;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List getLocalCADs(String rmCode){
		List localCADs  = new ArrayList();
		try {
			String query = "SELECT FROM stagingLocalCAD WHERE RM_MGR_ID = '"+rmCode+"' AND STATUS = 'ACTIVE'"; //actualLocalCAD
			String query = "SELECT FROM actualLocalCAD WHERE RM_MGR_ID = '"+rmCode+"' AND STATUS = 'ACTIVE' AND DEPRECATED = 'N'";
			System.out.println("RM Master getLocalCADs => sql=>"+query);
			if(!(rmCode==null)){
				localCADs = (List) getHibernateTemplate().find(query);
				return localCADs;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return null;
	}
	
	public List getStagingLocalCADs(String rmCode){
		List localCADs  = new ArrayList();
		try {
			String query = "SELECT FROM stagingLocalCAD WHERE RM_MGR_ID = '"+rmCode+"' AND STATUS IN ('PENDING_CREATE')";//actualLocalCAD
			System.out.println("RM Master getStagingLocalCADs => sql=>"+query);
//			String query = "SELECT FROM actualLocalCAD WHERE RM_MGR_ID = '"+rmCode+"' AND STATUS = 'ACTIVE'";
			if(!(rmCode==null)){
				localCADs = (List) getHibernateTemplate().find(query);
				return localCADs;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return null;
	}
	
	public List getStagingDeletedLocalCADs(String rmCode){
		List localCADs  = new ArrayList();
		try {
			String query = "SELECT FROM stagingLocalCAD WHERE RM_MGR_ID = '"+rmCode+"' AND STATUS = 'PENDING_DELETE'";//actualLocalCAD
			System.out.println("RM Master getStagingDeletedLocalCADs => sql=>"+query);
//			String query = "SELECT FROM actualLocalCAD WHERE RM_MGR_ID = '"+rmCode+"' AND STATUS = 'ACTIVE'";
			if(!(rmCode==null)){
				localCADs = (List) getHibernateTemplate().find(query);
				return localCADs;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return null;
	}
	
	public List getStagingCreatedAndDeletedLocalCADs(String rmCode){
		List localCADs  = new ArrayList();
		try {
			String query = "SELECT FROM stagingLocalCAD WHERE RM_MGR_ID = '"+rmCode+"' AND STATUS IN ('PENDING_CREATE','PENDING_DELETE')";//actualLocalCAD
			System.out.println("RM Master getStagingCreatedAndDeletedLocalCADs => sql=>"+query);
//			String query = "SELECT FROM actualLocalCAD WHERE RM_MGR_ID = '"+rmCode+"' AND STATUS = 'ACTIVE'";
			if(!(rmCode==null)){
				localCADs = (List) getHibernateTemplate().find(query);
				return localCADs;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return null;
	}
	
	public void insertData(IFileUpload fileUpload) {
//		getHibernateTemplate().save("stagefileUpload", fileUpload);
		getHibernateTemplate().save("actualfileUpload", fileUpload);
	}
	*/
	
	
	
	
	public void insertBankingMethodCustStage(OBBankingMethod obj) {
		try {

//			long nextSequenceSql = getSequence("CHK_STG_BANKING_METHOD_SEQ");
			//obj.setBankId(nextSequenceSql);
				System.out.println("Going for insertBankingMethodCustStage => save data ino table => stagingBankingMethodCust=> getStageEntityName() => "+getStageEntityName());
				getHibernateTemplate().save(getStageEntityName(), obj);
			
		} catch (Exception e) {
			System.out.println("Exception in insertBankingMethodCustStage.");
			e.printStackTrace();
		}finally {
			getHibernateTemplate().flush();
		}

	}
	
	public void insertBankingMethodCustActual(OBBankingMethod obj) {
		try {

//			long nextSequenceSql = getSequence("CMS_BANKING_METHOD_SEQ");
			//obj.setBankId(nextSequenceSql);
				System.out.println("Going for insertBankingMethodCustActual => save data ino table => actualBankingMethodCust");
				getHibernateTemplate().save("actualBankingMethodCust", obj);
			
		} catch (Exception e) {
			System.out.println("Exception in insertBankingMethodCustActual.");
			e.printStackTrace();
		}finally {
			getHibernateTemplate().flush();
		}

	}

	
	/*public ArrayList getBankingMethodByCustId(String custPartyId) {
		try{
			String query = "FROM actualBankingMethodCust WHERE CUSTOMER_ID ='"+custPartyId+"' ";				

				ArrayList list =(ArrayList) getHibernateTemplate().find(query);
				if(!list.isEmpty()){
					IBankingMethod data = new OBBankingMethod();
				data = (OBBankingMethod) list.get(0);
				return data;
				}			
				return list;
	} catch (Exception e) {
		e.printStackTrace();
	}
		return null;
	
}*/
	
	
	public String getBankingMethodByCustId(String custPartyId) throws SearchDAOException {
		String bankingMethods = "";
		ResultSet rs=null;
		DBUtil dbUtil = null;
		try{
			 
			dbUtil = new DBUtil();
			//String sql = "SELECT COUNTRY_CODE, COUNTRY_NAME FROM CMS_COUNTRY WHERE COUNTRY_CODE = '"+partyCode+"'";
			String partyNm = null;
			String sql = "SELECT CCCE.ENTRY_CODE AS ENTRY_CODE,CCCE.ENTRY_NAME AS ENTRY_NAME FROM CMS_BANKING_METHOD_CUST CBEC, COMMON_CODE_CATEGORY_ENTRY CCCE WHERE CBEC.CMS_BANKING_METHOD_NAME = CCCE.ENTRY_CODE \r\n" + 
					"AND CCCE.CATEGORY_CODE = 'BANKING_METHOD' AND CCCE.ACTIVE_STATUS = 1 AND CBEC.STATUS = 'ACTIVE' AND CBEC.CUSTOMER_ID = ? ";
			dbUtil.setSQL(sql);
			dbUtil.setString(1, custPartyId);
			rs = dbUtil.executeQuery(); 
			while (rs.next()) {
				bankingMethods = bankingMethods + rs.getString("ENTRY_CODE") + "-" +  rs.getString("ENTRY_NAME") + ",";
			}
		 
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
			}
			catch (SQLException e) {
				DefaultLogger.warn(this, "EXCEPTION when closing DB UTIL!", e);
			}
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException e) {
				DefaultLogger.warn(this, "EXCEPTION when closing DB UTIL!", e);
			}
		}
		return bankingMethods;
	}
	
	
	public void disableActualBankingMethod(String referenceId) {
		ArrayList resultList = new ArrayList();
		try{
			String query = "SELECT FROM actualBankingMethodCust WHERE STATUS='ACTIVE' AND CMS_LE_SUB_PROFILE_ID='"+referenceId+"'";
			resultList = (ArrayList) getHibernateTemplate().find(query);
			
			if(null!=resultList) {
				for(int i=0;i<resultList.size();i++) {
					IBankingMethod obj=(IBankingMethod)resultList.get(i);
					obj.setStatus("INACTIVE");
					getHibernateTemplate().saveOrUpdate("actualBankingMethodCust", obj);
				}
			}
			
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in disableActualBankingMethod ",obe);
			obe.printStackTrace();
		}
	}
	
	
	
	
	private long getSequence(String seqName){
		long seqId=0l;
		try
		{
			seqId = Long.parseLong((new SequenceManager()).getSeqNum(seqName, true));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return seqId;
	}
	
	
}
