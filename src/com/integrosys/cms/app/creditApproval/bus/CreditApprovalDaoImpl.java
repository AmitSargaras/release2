package com.integrosys.cms.app.creditApproval.bus;

import java.io.Serializable;
import java.math.BigDecimal;
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

import com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.bus.ICountryDAO;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.batch.IncompleteBatchJobException;

/**
 * Implementation of {@link ICreditApprovalDao} using Hibernate
 * 
 * @author Govind.Sahu
 * @since 2011
 */
public class CreditApprovalDaoImpl extends HibernateDaoSupport implements ICreditApprovalDao {
	
	IRegion regionDAO;
	
	/**
	 * @return the regionDAO
	 */
	public IRegion getRegionDAO() {
		return regionDAO;
	}

	/**
	 * @param regionDAO the regionDAO to set
	 */
	public void setRegionDAO(IRegion regionDAO) {
		this.regionDAO = regionDAO;
	}

	/**
	 * @return String entity name
	 */
	public String getEntityName(){
		return ICreditApprovalDao.ACTUAL_CREDIT_APPROVAL_ENTITY_NAME; 
	}
	
	/*
	 *  
	 *  This method get credit approval result list
	 */
	public List getCreditApprovalList() {
		List resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM actualCreditApproval where DEPRECATED = \'N\' ORDER BY APPROVAL_CODE");
			return resultList;
	}
	
	/*
	 *This method check Credit Approval Unique Code
	 *Return true if approval code found else false
	*/
	public boolean getCheckCreditApprovalUniquecode(String entityName,String appCode) {
		boolean bCheck = false;
		List resultList = null;
		if(!(entityName==null|| appCode==null)){
		resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+entityName +" where APPROVAL_CODE = '"+appCode+"'");
		if (resultList.isEmpty()) {
			bCheck = false;
		}
		else
		{
			resultList = getHibernateTemplate().find("SELECT FROM actualCreditApproval where APPROVAL_CODE = '"+appCode+"' AND DEPRECATED = 'N'");
			if (resultList.isEmpty()) {
				bCheck = false;
			}
			else
			{
			bCheck = true;
			}
		}
		}else{
		throw new CreditApprovalException("ERROR-- Entity Name Or Approval Code is null");
		}
		
		
		return bCheck;
	}
	
	/*
	 * @param etity name
	 * @param ICreditApproval
	 * Return CreditApproval
	 */
	public ICreditApproval createCreditApproval(String entityName, ICreditApproval creditApprovalEntry) {
		Long key;
		if(!(entityName==null|| creditApprovalEntry==null)){
			if( creditApprovalEntry.getApprovalCode() == null || creditApprovalEntry.getApprovalCode().equals("")){
				String creditApprovalCode=getCreditApprovalCode();
				creditApprovalEntry.setApprovalCode(creditApprovalCode);
			}
			key = (Long) getHibernateTemplate().save(entityName, creditApprovalEntry);
		}else{
		throw new CreditApprovalException("ERROR-- Entity Name Or CreditApproval Ob is null");
		}
		
		return (ICreditApproval) getHibernateTemplate().get(entityName, key);
	}
	
	/*
	 * @param etity name
	 * @param ICreditApproval
	 * Return CreditApproval
	 */
	public ICreditApproval getCreditApprovalEntryByPrimaryKey(String entityName, Long key) {
		ICreditApproval creditApproval = null;
		if(!(entityName==null|| key==null)){
			creditApproval =  (ICreditApproval) getHibernateTemplate().get(entityName, key);
		}else{
		throw new CreditApprovalException("ERROR-- Entity Name Or Key is null");
		}
		return creditApproval;
	}

	
	/*
	 * @param etity name
	 * @param key
	 * Return CreditApproval
	 */
	public ICreditApproval getCreditApprovalEntryByEntryType(String entityName, String key) {
		ICreditApproval creditApproval = null;
		if(!(entityName==null|| key==null)){
			creditApproval =  (ICreditApproval) getHibernateTemplate().get(entityName, new Long(key));
		}else{
		throw new CreditApprovalException("ERROR-- Entity Name Or Key is null");
		}
		return creditApproval;	
	}
	
	/*
	 * This method update Credit Approval
	 * @param etity name
	 * @param ICreditApproval
	 * Return CreditApproval
	 */
	public ICreditApproval updateCreditApproval(String entityName, ICreditApproval creditApprovalEntry) {
		getHibernateTemplate().update(entityName, creditApprovalEntry);
		return (ICreditApproval) getHibernateTemplate().get(entityName, new Long(creditApprovalEntry.getId()));
	}



	/*
	 * Add this method for updating status of actual table as  INACTIVE
	 * This method update Credit Approval
	 * @param etity name
	 * @param ICreditApproval
	 * Return CreditApproval
	 */
	public ICreditApproval updateStatusCreditApproval(String entityName, ICreditApproval creditApproval) {
		if(!(entityName==null|| creditApproval==null)){
			creditApproval = (ICreditApproval)getHibernateTemplate().load(entityName, new Long(creditApproval.getId()));
		getHibernateTemplate().update(entityName, creditApproval);
		}else{
			throw new CreditApprovalException("ERROR-- Entity Name Or CreditApproval Ob is null");
			}
		return (ICreditApproval) getHibernateTemplate().get(entityName, new Long(creditApproval.getId()));
	}
	
	/**
	 * @return List of all authorized Credit Approval according to Search Criteria provided.
	 * 
	 */
	public List getAllCreditApproval(String EntityName, String searchTxtApprovalCode, String searchTxtApprovalName){

		List resultList = null;
//		System.out.println("IN GET ALL  Credit Approval");
//		System.out.println("Search Code:"+searchTxtApprovalCode);
//		System.out.println("Search Approval Name:"+searchTxtApprovalName);
		
		String GET_BRANCH_QUERY_STRING = "SELECT FROM "+EntityName+" where DEPRECATED = \'N\' ";
		
		if(searchTxtApprovalCode!=null && !searchTxtApprovalCode.equals(""))
		{
			GET_BRANCH_QUERY_STRING+="AND LOWER(APPROVAL_CODE) like \'"+searchTxtApprovalCode.toLowerCase()+"%\' ";
		}
		if(searchTxtApprovalName!=null && !searchTxtApprovalName.equals(""))
		{
			GET_BRANCH_QUERY_STRING+=" AND LOWER(APPROVAL_NAME) like \'"+searchTxtApprovalName.toLowerCase()+"%\'";
		}
		 
//		System.out.println("SQL:"+GET_BRANCH_QUERY_STRING);
		
			try {
				resultList = (ArrayList)getHibernateTemplate().find(GET_BRANCH_QUERY_STRING);

			} catch (Exception e) {
				e.printStackTrace();
				throw new CreditApprovalException("ERROR-- While retriving Credit Approval");
			}
		
		
		return resultList;
	}
	
//--------------------File Upload
	
	/**
	 * @return Maker check if previous upload is pending.
	 */
	public boolean isPrevFileUploadPending() {

		String rowCount = "FROM com.integrosys.cms.app.transaction.OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_CREDIT_APP'";
		List rowList = getHibernateTemplate().find(rowCount);
		if(rowList.size()>0){
			String sqlQuery = "FROM com.integrosys.cms.app.transaction.OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_CREDIT_APP' AND status != 'ACTIVE'";
			List creditApprovalList = getHibernateTemplate().find(sqlQuery);
			if(creditApprovalList.size()>0){
				return true;
			}
		}
		return false;
	}
	
	
	public List getAllStageCreditApproval(String searchBy, String login) {
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
			String query = "SELECT FROM "+ICreditApprovalDao.STAGE_CREDIT_APPROVAL_ENTITY_NAME+" WHERE DEPRECATED = 'N' AND ID IN ("+strId+")";
			try {
				if (searchByValue.trim() != "") {
					resultList = getHibernateTemplate().find(query);
				}
	
			} catch (Exception e) {
				throw new CreditApprovalException("ERROR-- While retriving CreditApproval");
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
	

	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId insertCreditApproval(String entityName,
			IFileMapperId fileId, ICreditApprovalTrxValue trxValue)
			throws CreditApprovalException {
		
		if(!(entityName==null|| fileId==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, fileId);
			fileId.setId(key.longValue());
			return fileId;
		}else{
			throw new CreditApprovalException("ERROR- Entity name or key is null ");
		}	
	}
	
	/**
	 * @return integer (record count of inserted record)
	 * @param FileMapperMaster
	 * @param userName  
	 * @param result of upload files 
	 * This method Creates FileMapperMaster Object
	 */
	public int insertCreditApproval(IFileMapperMaster fileMapperMaster, String userName, ArrayList result, long countryId) {
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
				OBCreditApproval obCreditApproval = new OBCreditApproval();
				
//				if (form.getId() != null) {
//					obCreditApproval.setId(Long.parseLong(form.getId()));
//				}	
				if (((String) eachDataMap.get("APPROVAL_CODE") != null) && ! eachDataMap.get("APPROVAL_CODE").toString().trim().equals("")) 
					obCreditApproval.setApprovalCode((String) eachDataMap.get("APPROVAL_CODE"));
				else
					obCreditApproval.setApprovalCode(getCreditApprovalCode());
				
				if (((String) eachDataMap.get("APPROVAL_NAME") != null) && ! eachDataMap.get("APPROVAL_NAME").toString().trim().equals("")) {
					obCreditApproval.setApprovalName((String) eachDataMap.get("APPROVAL_NAME"));
				}
				else
				{
					obCreditApproval.setApprovalName("");
				}
				
				if (((String) eachDataMap.get("MAXIMUM_LIMIT") != null) && ! eachDataMap.get("MAXIMUM_LIMIT").toString().trim().equals("")) {
				obCreditApproval.setMaximumLimit(new BigDecimal(eachDataMap.get("MAXIMUM_LIMIT").toString()));
				}
				else
				{
					 obCreditApproval.setMaximumLimit(new BigDecimal(0));
			    }
				if (((String) eachDataMap.get("MINIMUM_LIMIT") != null) && ! eachDataMap.get("MINIMUM_LIMIT").toString().trim().equals("")) {
				obCreditApproval.setMinimumLimit(new BigDecimal(eachDataMap.get("MINIMUM_LIMIT").toString()));
				}
				else
				{
					 obCreditApproval.setMinimumLimit(new BigDecimal(0));
			    }
				obCreditApproval.setSegmentId((String) eachDataMap.get("SEGMENT"));
				obCreditApproval.setEmail((String) eachDataMap.get("EMAIL"));
				
				if((String) eachDataMap.get("SENIOR")!=null){
				   if(eachDataMap.get("SENIOR").toString().trim().equalsIgnoreCase("Y")){
					 obCreditApproval.setSenior("Y");
				   }else if(eachDataMap.get("SENIOR").toString().trim().equalsIgnoreCase("N")){
					 obCreditApproval.setSenior("N");
				    }
				 }
				
				if((String) eachDataMap.get("RISK")!=null){
					if(eachDataMap.get("RISK").toString().trim().equalsIgnoreCase("Y")){
					obCreditApproval.setRisk("Y");
					}else if(eachDataMap.get("RISK").toString().trim().equalsIgnoreCase("N")){
					obCreditApproval.setRisk("N");
				    }
				}
				    obCreditApproval.setCreationDate(new Date());
					obCreditApproval.setCreateBy(userName);
					obCreditApproval.setLastUpdateDate(new Date());
					obCreditApproval.setLastUpdateBy(userName);
					obCreditApproval.setDeprecated("N");
					obCreditApproval.setStatus("ACTIVE");

			obCreditApproval.setDeferralPowers((String) eachDataMap.get("DEFERRAL_POWERS"));
			obCreditApproval.setWaivingPowers((String) eachDataMap.get("WAIVING_POWERS"));
			long regionId  = -1;
			String regionCode = (String)eachDataMap.get("REGION_CODE");
			if(regionCode!=null && !regionCode.equals("")){
		    regionId = getRegionByRegionCode((String)eachDataMap.get("REGION_CODE"),countryId );	
			}
			obCreditApproval.setRegionId(regionId);
			
				key = (Long) getHibernateTemplate().save("stageCreditApproval", obCreditApproval);				
				//create FileMapperMaster
				
				IFileMapperMaster fileMapper = new OBFileMapperMaster();
				fileMapper.setFileId(fileMapperMaster.getFileId());
				fileMapper.setSysId(key.longValue());
				fileMapper.setTransId(fileMapperMaster.getTransId());
				if(!(fileMapperMaster==null)){
					key = (Long) getHibernateTemplate().save("fileMapper", fileMapper);
					getHibernateTemplate().get("fileMapper", key);
					}else{
						throw new CreditApprovalException("ERROR- Entity name or key is null ");
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
	public IFileMapperId createFileId(String entityName, IFileMapperId fileId)
	throws CreditApprovalException {
			// TODO Auto-generated method stub
			if(!(entityName==null|| fileId==null)){
		
		Long key = (Long) getHibernateTemplate().save(entityName, fileId);
		fileId.setId(key.longValue());
		return fileId;
		}else{
			throw new CreditApprovalException("ERROR- Entity name or key is null ");
		}
	}
	
	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId getInsertFileList(String entityName, Serializable key)
	throws CreditApprovalException {
		if(!(entityName==null|| key==null)){
			
			return (IFileMapperId) getHibernateTemplate().get(entityName, key);
			}else{
				throw new CreditApprovalException("ERROR-- Entity Name Or Key is null");
		}
	}
	

	/**
	 * @return CreditApproval Object
	 * @param Entity Name
	 * @param sysId  
	 * This method Creates CreditApproval Object
	 */
	public ICreditApproval insertActualCreditApproval(String sysId)
	throws CreditApprovalException {
		Map parametersMap = new HashMap();
		parametersMap.put("id", new Long(sysId));
		
		DetachedCriteria criteria = DetachedCriteria.forEntityName("stageCreditApproval").add(Restrictions.allEq(parametersMap));
		List creditApprovalList = getHibernateTemplate().findByCriteria(criteria);
			return (ICreditApproval)creditApprovalList.get(0);
		}
	/**
	 * @return CreditApproval Object
	 * @param Entity Name
	 * @param CreditApproval Object  
	 * This method Creates CreditApproval Object
	 */
	public ICreditApproval insertCreditApproval(String entityName,
			ICreditApproval creditApproval)
			throws CreditApprovalException {
		if(!(entityName==null|| creditApproval==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, creditApproval);
			creditApproval.setId(key.longValue());
			return creditApproval;
			}else{
				throw new CreditApprovalException("ERROR- Entity name or key is null ");
			}
	}
	
public long getRegionByRegionCode(String regionCode, long countryId){
		Long regionId = null;
		try {
			String query = "SELECT reg.idRegion FROM actualRegion reg WHERE reg.status!='INACTIVE' AND reg.deprecated!='Y' AND reg.regionCode ='"+ regionCode+"' AND reg.countryId = "+countryId;

			//			ArrayList resultList;			
			if( query != "" ){			
				List regionList =(ArrayList) getHibernateTemplate().find(query);
				Iterator itor =regionList.iterator();
				if( itor.hasNext() ){
					regionId = (Long) itor.next();
				}
				return regionId.longValue();	
			
			}else{
				throw new NoSuchGeographyException("Unable to get List for "+IRegionDAO.ACTUAL_ENTITY_NAME_REGION);
			}
		} catch (Exception e) {
			throw new NoSuchGeographyException("ERROR-- While retriving getRegionByRegionCode ");
		}
		
	}
	

	
	/*
	 *  
	 *  This method get credit approval result list
	 */
	public List getCreditApprover() {
		
		List resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM actualCreditApproval where STATUS = \'ACTIVE\'");
			return resultList;
	}
	private String getCreditApprovalCode() {
		/* updated the hibernate jar from 3 to 4
		 * Query query = getSession().createSQLQuery("SELECT CREDIT_APPROVAL_SEQ.NEXTVAL FROM dual");*/
		Query query = currentSession().createSQLQuery("SELECT CREDIT_APPROVAL_SEQ.NEXTVAL FROM dual");
		String sequenceNumber = query.uniqueResult().toString();
		NumberFormat numberFormat = new DecimalFormat("0000000");
		String creditApprovalCode = numberFormat.format(Long.parseLong(sequenceNumber));
		creditApprovalCode = "CAP" + creditApprovalCode;		
		return creditApprovalCode;
	}
	
	public long getCountryIdForCountry(String countryName) {
		try {
			String query = "FROM "+ICountryDAO.ACTUAL_ENTITY_NAME_COUNTRY+" WHERE COUNTRY_NAME ='"+ countryName+"'";							
			ArrayList countryList =(ArrayList) getHibernateTemplate().find(query);				 
			if( countryList.size() > 0 ){
				ICountry country = (ICountry)countryList.get(0);
				return country.getIdCountry() ;
			}
			else
				return 0;			
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoSuchGeographyException("ERROR-- While retriving CountryName "+countryName);
		}		
	}

	public boolean isCreditApprovalNameUnique(String creditApprovalName) {
		creditApprovalName = creditApprovalName.replaceAll("'", "''");
		String stagingQuery = "SELECT FROM "+ICreditApprovalDao.STAGE_CREDIT_APPROVAL_ENTITY_NAME+" WHERE UPPER(APPROVAL_NAME) like '"+creditApprovalName.toUpperCase()+"' ";
		ArrayList stagingResultList = (ArrayList) getHibernateTemplate().find(stagingQuery);
		if( stagingResultList.size() > 0 ){
			String actualQuery = "SELECT FROM "+ICreditApprovalDao.ACTUAL_CREDIT_APPROVAL_ENTITY_NAME+" WHERE STATUS != 'INACTIVE' AND DEPRECATED != 'Y' AND UPPER(APPROVAL_NAME) like '"+creditApprovalName.toUpperCase()+"' ";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if( actualResultList.size() > 0 )
				return true;
			else
				return false;
		}	
		else 
			return false;		 
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) { 
		OBCMSTrxValue obCMSTrxValue = new OBCMSTrxValue();
		String query = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_CREDIT_APP' AND obcms.transactionID='"+obFileMapperMaster.getTransId()+"'";
		List list = getHibernateTemplate().find(query);
		obCMSTrxValue = (OBCMSTrxValue)list.get(0);
		getHibernateTemplate().flush();
		getHibernateTemplate().delete(obCMSTrxValue);
	}

	public boolean isRegionCodeVaild(String regionCode, long countryId) {
		String stagingQuery = "SELECT FROM "+IRegionDAO.STAGING_REGION_ENTITY_NAME+" WHERE UPPER(REGION_CODE) = '"+regionCode.toUpperCase()+"' AND COUNTRY_ID = "+ new Long(countryId);
		ArrayList stagingResultList = (ArrayList) getHibernateTemplate().find(stagingQuery);
		if( stagingResultList.size() > 0 ){
			String actualQuery = "SELECT FROM "+IRegionDAO.ACTUAL_ENTITY_NAME_REGION+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' AND UPPER(REGION_CODE) = '"+regionCode.toUpperCase()+"' AND COUNTRY_ID ="+ new Long(countryId);
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if( actualResultList.size() > 0 ){
				Iterator itor = actualResultList.iterator();
				while( itor.hasNext() ){
					IRegion region =  (IRegion)itor.next();
						if( region.getCountryId().getIdCountry() == countryId )
							return false;
				}
				return true;
			}
			else
				return true;
		}	
		else 
			return true;	
	}
	
	public List getRegionList(String countryName){
		List regionList = new ArrayList();
		try{			
			String query = "SELECT FROM "+IRegionDAO.ACTUAL_ENTITY_NAME_REGION+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' " +
					" AND COUNTRY_ID IN (FROM actualCountry WHERE Upper (COUNTRY_NAME) like Upper ('"+countryName+"%'))";
			regionList = (List) getHibernateTemplate().find(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return regionList;
	}
	
	public String getCPSIdByApprovalCode(String entityName,String appCode) {
		String cpsId = null;
		String query = "SELECT FROM " + entityName + " where APPROVAL_CODE = ? ";
		try{
			Object obj = getHibernateTemplate().find(query,appCode).get(0);
			if(obj!=null){
				cpsId = ((OBCreditApproval)obj).getCpsId();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return cpsId;
	}
	
	
	public boolean isCreditEmployeeIdUnique(String employeeId) {
		employeeId = employeeId.replaceAll("'", "''");
		String stagingQuery = "SELECT FROM "+ICreditApprovalDao.STAGE_CREDIT_APPROVAL_ENTITY_NAME+" WHERE UPPER(EMPLOYEE_ID) like '"+employeeId.toUpperCase()+"' ";
		ArrayList stagingResultList = (ArrayList) getHibernateTemplate().find(stagingQuery);
		if( stagingResultList.size() > 0 ){
			String actualQuery = "SELECT FROM "+ICreditApprovalDao.ACTUAL_CREDIT_APPROVAL_ENTITY_NAME+" WHERE STATUS != 'INACTIVE' AND DEPRECATED != 'Y' AND UPPER(EMPLOYEE_ID) like '"+employeeId.toUpperCase()+"' ";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if( actualResultList.size() > 0 )
				return true;
			else
				return false;
		}	
		else 
			return false;		 
	}
}
