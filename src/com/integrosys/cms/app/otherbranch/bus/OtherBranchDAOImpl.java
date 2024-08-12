package com.integrosys.cms.app.otherbranch.bus;

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

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.bus.ICityDAO;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.bus.ICountryDAO;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.bus.IStateDAO;
import com.integrosys.cms.app.otherbank.bus.IOtherBankDAO;
import com.integrosys.cms.app.otherbank.bus.OBOtherBank;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyException;
import com.integrosys.cms.batch.IncompleteBatchJobException;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

/**
 * Purpose : This OtherBranchDAOImpl implements the methods that will be available to the
 * operating on a other bank branch
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-02-18 17:27:01 +0800 (Fri, 18 Feb 2011) $
 */
public class OtherBranchDAOImpl extends HibernateDaoSupport implements IOtherBranchDAO {
	
	

	private ICountryDAO countryDAO;
	private IRegionDAO regionDAO;
	private IStateDAO stateDAO;
	private ICityDAO cityDAO;
	
	
	
	/**
	 * @return String Entity Name
	 */
	public String getEntityName(){
		return IOtherBranchDAO.ACTUAL_ENTITY_NAME; 
	}
	
	/**
	 * @return SearchResult of all branches
	 */
	public SearchResult getOtherBranch() throws OtherBranchException{
		ArrayList resultList = new ArrayList();
		try{
			resultList = (ArrayList) getHibernateTemplate().loadAll(OBOtherBranch.class);
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in getOtherBranch",e);
			e.printStackTrace();
			throw new OtherBranchException("Unable to get other bank branch");
		}
		return new SearchResult(0, resultList.size(), resultList.size(), resultList);
	}

	/**
	 * @return IOtherBranch present for input id
	 */
	public IOtherBranch getOtherBranchById(long id) throws OtherBranchException {
		IOtherBranch otherBranch = new OBOtherBranch();
		try{
			otherBranch = (IOtherBranch)getHibernateTemplate().load(getEntityName(), new Long(id));
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in getOtherBranchById ",e);
			e.printStackTrace();
			throw new OtherBranchException("Unable to get other bank branch with id ["+id+"]");
		}
		return otherBranch;
	}

	/**
	 * @return SearchResult of all branches
	 */
	public SearchResult getOtherBranchList(String searchType,String searchVal) {
		ArrayList resultList = new ArrayList();
		try{
			String query = "SELECT FROM "+getEntityName()+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y'";
			
			if((searchType!="" && searchType!=null) && (searchVal!="" && searchVal!=null)){
				int searchMode = Integer.parseInt(searchType); 
				switch(searchMode){
					case 1: query += " AND ( OTHER_BANK_CODE like '%"+searchVal+"%' )";
						break;
					case 2: query += " AND ( BRANCH_CODE like '%"+searchVal+"%' )";
						break;
					case 3: query += " AND ( BRANCH_NAME like '%"+searchVal+"%' )";
						break;
					case 4: query += " AND ( CITY_TOWN like '%"+searchVal+"%' )";
						break;
					case 5: query += " AND ( STATE like '%"+searchVal+"%' )";
						break;
					case 6: query += " AND ( REGION like '%"+searchVal+"%' )";
						break;
					case 7: query += " AND ( COUNTRY like '%"+searchVal+"%' )";
						break;
				}
			}	
			
			resultList = (ArrayList) getHibernateTemplate().find(query);
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in getOtherBranchList ",e);
			e.printStackTrace();
			throw new OtherBranchException("Unable to Load other bank branch List");
		}
		return new SearchResult(0, resultList.size(), resultList.size(), resultList);
	}
	
	
	public SearchResult getOtherBranchFromCode(String searchVal) {
		ArrayList resultList = new ArrayList();
		try{
			String query = "SELECT FROM "+getEntityName()+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y'";
			
					 query += " AND ( BRANCH_CODE = '"+searchVal+"' )";
				
				
			
			resultList = (ArrayList) getHibernateTemplate().find(query);
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in getOtherBranchList ",e);
			e.printStackTrace();
			throw new OtherBranchException("Unable to Load other bank branch List");
		}
		return new SearchResult(0, resultList.size(), resultList.size(), resultList);
	}

	/**
	 * Updates the Other Branch
	 */
	public IOtherBranch updateOtherBranch(IOtherBranch OtherBranch) throws OtherBranchException {
		IOtherBranch returnObj = new OBOtherBranch();
		try{
			getHibernateTemplate().update(getEntityName(), OtherBranch);
			returnObj = (IOtherBranch) getHibernateTemplate().load(getEntityName(),new Long(OtherBranch.getId()));
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in updateOtherBranch ",e);
			e.printStackTrace();
			throw new OtherBranchException("Unable to update other bank branch with id ["+OtherBranch.getId()+"]");
		}
		return returnObj;
	}

	/**
	 * Soft Delete Other Branch
	 */
	public IOtherBranch deleteOtherBranch(IOtherBranch OtherBranch) throws OtherBranchException {
		IOtherBranch returnObj = new OBOtherBranch();
		try{
			OtherBranch.setStatus("INACTIVE");
			OtherBranch.setDeprecated("Y");
			getHibernateTemplate().update(getEntityName(), OtherBranch);
			returnObj = (IOtherBranch) getHibernateTemplate().load(getEntityName(),new Long(OtherBranch.getId()));
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in updateOtherBranch ",e);
			e.printStackTrace();
			throw new OtherBranchException("Unable to delete other bank branch with id ["+OtherBranch.getId()+"]");
		}
		return returnObj;
	}
	
	/**
	 * Updates the Other Branch
	 */
	public IOtherBranch createOtherBranch(IOtherBranch OtherBranch) throws OtherBranchException {
		IOtherBranch returnObj = new OBOtherBranch();
		try{
			getHibernateTemplate().save(getEntityName(), OtherBranch);
			returnObj = (IOtherBranch) getHibernateTemplate().load(getEntityName(),new Long(OtherBranch.getId()));
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in createOtherBranch ",e);
			e.printStackTrace();
			throw new OtherBranchException("Unable to create other bank branch ");
		}
		return returnObj;
	}
	

	/**
	 * @return OtherBank Object
	 * @param Entity Name
	 * @param Key  
	 * This method returns Other Bank Object
	 */
	public IOtherBranch getOtherBranch(String entityName, Serializable key)throws OtherBankException {
		if(!(entityName==null|| key==null)){
		return (IOtherBranch) getHibernateTemplate().get(entityName, key);
		}else{
			throw new OtherBankException("ERROR- Entity name or key is null ");
		}
	}

	/**
	 * @return OtherBank Object
	 * @param Entity Name
	 * @param OtherBank Object  
	 * This method Updates Other Bank Object
	 */
	public IOtherBranch updateOtherBranch(String entityName, IOtherBranch item)throws OtherBankException {
		if(!(entityName==null|| item==null)){
		getHibernateTemplate().update(entityName, item);

		return (IOtherBranch) getHibernateTemplate().load(entityName,
				new Long(item.getId()));
		}else{
			throw new OtherBankException("ERROR- Entity name or key is null ");
		}

	}
	/**
	 * @return OtherBank Object
	 * @param Entity Name
	 * @param OtherBank Object  
	 * This method Creates Other Bank Object
	 */

	public IOtherBranch createOtherBranch(String entityName,
			IOtherBranch systemBank)throws OtherBankException {
		if(!(entityName==null|| systemBank==null)){
			if( systemBank.getOtherBranchCode() == null || systemBank.getOtherBranchCode().equals(""))
				systemBank.setOtherBranchCode(getOtherBranchCode());
			
		Long key = (Long) getHibernateTemplate().save(entityName, systemBank);
		systemBank.setId(key.longValue());
		return systemBank;
		}else{
			throw new OtherBankException("ERROR- Entity name or key is null ");
		}
	}
	
	private String getOtherBranchCode() {
		Query query = currentSession().createSQLQuery("SELECT OTHER_BANK_BRANCH_SEQ.NEXTVAL FROM dual");
		String sequenceNumber = query.uniqueResult().toString();
		NumberFormat numberFormat = new DecimalFormat("0000000");
		String branchCode = numberFormat.format(Long.parseLong(sequenceNumber));
		branchCode = "OBR" + branchCode;		
		return branchCode;
	}
	
	public boolean isOBCodeUnique(String obCode){
		String query = "SELECT FROM "+IOtherBranchDAO.STAGING_ENTITY_NAME+" WHERE BRANCH_CODE like '"+obCode+"'";
		ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
		if(resultList.size()>0){
			String query1 = "SELECT FROM "+getEntityName()+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' AND BRANCH_CODE like '"+obCode+"'";
			ArrayList resultList1 = (ArrayList) getHibernateTemplate().find(query1);
			if(resultList1.size()>0)
				return true;
			else
				return false;
		}	
		else 
			return false;
		 
	}
	
	
	
	//*************************************UPLOAD************************************************
	
	
	public ICountryDAO getCountryDAO() {
		return countryDAO;
	}

	public void setCountryDAO(ICountryDAO countryDAO) {
		this.countryDAO = countryDAO;
	}

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
	
	public int insertOtherBankBranch(IFileMapperMaster fileMapperMaster, String userName, ArrayList result) {
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
				IOtherBranch obOtherBankBranch = new OBOtherBranch();
				IOtherBank objOtherBank=new OBOtherBank();
				objOtherBank = getOtherBankByOtherBankCode((String)eachDataMap.get("OTHER_BANK_CODE"));
				obOtherBankBranch.setOtherBankCode(objOtherBank);
				
				String countryCode = (String) eachDataMap.get("COUNTRY");
				String regionCode = (String) eachDataMap.get("REGION");
				String stateCode = (String) eachDataMap.get("STATE");
				String cityCode = (String) eachDataMap.get("CITY");
				String contactNumber = (String) (String)eachDataMap.get("CONTACT_NUMBER");
				String faxNumber = (String) eachDataMap.get("FAX_NO");
				String rbiCode = (String) eachDataMap.get("RBI_CODE");								
				String branchCode = (String) eachDataMap.get("BRANCH_CODE");
				
				if( branchCode == null || branchCode.equals("")  )
					obOtherBankBranch.setOtherBranchCode(getOtherBranchCode());
				else
					obOtherBankBranch.setOtherBranchCode(branchCode);
				
				obOtherBankBranch.setAddress(eachDataMap.get("ADDRESS").toString());
				
				if ( countryCode != null && !countryCode.trim().equals("") ){
					ICountry country = getCountryDAO().getCountryByCountryCode(countryCode);
					obOtherBankBranch.setCountry(country);
				}
				
				if ( regionCode != null && !regionCode.trim().equals("") ){
					IRegion region = getRegionDAO().getRegionByRegionCode(regionCode);
					obOtherBankBranch.setRegion(region);
				}
				
				if ( stateCode != null && !stateCode.trim().equals("") ){
					IState state = getStateDAO().getStateByStateCode(stateCode);
					obOtherBankBranch.setState(state);
				}
				
				if ( cityCode != null && !cityCode.trim().equals("") ){
					ICity city = getCityDAO().getCityByCityCode(cityCode);
					obOtherBankBranch.setCity(city);
				}
				
				if ( contactNumber != null && !contactNumber.trim().equals("") )
					obOtherBankBranch.setContactNo(Long.parseLong(contactNumber));
				else
					obOtherBankBranch.setContactNo(0);
				
				if ( faxNumber != null && !faxNumber.trim().equals("") )
					obOtherBankBranch.setFaxNo(Long.parseLong(faxNumber));
				else
					obOtherBankBranch.setFaxNo(0);
				
				if ( rbiCode != null && !rbiCode.trim().equals("") )
					obOtherBankBranch.setRbiCode(Long.parseLong(faxNumber));
				else
					obOtherBankBranch.setRbiCode(0);
				
				obOtherBankBranch.setOtherBranchName((String) eachDataMap.get("BRANCH_NAME"));
				obOtherBankBranch.setContactMailId((String) eachDataMap.get("CONTACT_MAIL"));				
				obOtherBankBranch.setLastUpdateDate(new Date());
				obOtherBankBranch.setCreatedBy(userName);
				obOtherBankBranch.setCreationDate(new Date());
				
				obOtherBankBranch.setStatus("ACTIVE");
				obOtherBankBranch.setDeprecated("N");
				obOtherBankBranch.setLastUpdateBy(userName);
				key = (Long) getHibernateTemplate().save("stagingOtherBranch", obOtherBankBranch);				
				//create FileMapperMaster
				
				IFileMapperMaster fileMapper = new OBFileMapperMaster();
				fileMapper.setFileId(fileMapperMaster.getFileId());
				fileMapper.setSysId(key.longValue());
				fileMapper.setTransId(fileMapperMaster.getTransId());
				if(!(fileMapperMaster==null)){
					key = (Long) getHibernateTemplate().save("fileMapper", fileMapper);
//					getHibernateTemplate().get("fileMapper", key);
					}else{
						throw new OtherBranchException("ERROR- Entity name or key is null ");
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
		
	public IFileMapperId createFileIdBankBranch(String entityName, IFileMapperId fileId)
	throws OtherBankException {
			// TODO Auto-generated method stub
			if(!(entityName==null|| fileId==null)){
		
		Long key = (Long) getHibernateTemplate().save(entityName, fileId);
		fileId.setId(key.longValue());
		return fileId;
		}else{
			throw new OtherBankException("ERROR- Entity name or key is null ");
		}
	}
	
	public IOtherBranch insertOtherBankBranch(String entityName,
			IOtherBranch otherBanKBranch)
			throws OtherBankException {
		if(!(entityName==null|| otherBanKBranch==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, otherBanKBranch);
			otherBanKBranch.setId(key.longValue());
			return otherBanKBranch;
			}else{
				throw new OtherBankException("ERROR- Entity name or key is null ");
			}
	}
	
	public IFileMapperId insertOtherBankBranch(String entityName,
			IFileMapperId fileId, IOtherBankBranchTrxValue trxValue)
			throws OtherBankException {
		
		if(!(entityName==null|| fileId==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, fileId);
			fileId.setId(key.longValue());
			return fileId;
		}else{
			throw new OtherBranchException("ERROR- Entity name or key is null ");
		}	
	}
	
	
	public IFileMapperId getInsertFileListBankBranch(String entityName, Serializable key)
	throws OtherBranchException {
		if(!(entityName==null|| key==null)){
			
			return (IFileMapperId) getHibernateTemplate().get(entityName, key);
			}else{
				throw new OtherBankException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	
	
	public List getAllStageOtherBankBranch(String searchBy, String login) {
		List resultList = null;
//		String searchByValue = searchBy;
		String query = "SELECT FROM "+IOtherBranchDAO.STAGING_ENTITY_NAME+" as othbr WHERE othbr.deprecated = 'N' " +
				" AND othbr.id IN (SELECT mapper.sysId FROM "+IOtherBranchDAO.CMS_FILE_MAPPER_ID+" as mapper WHERE mapper.transId = "+searchBy+")";
	
		try {
			if (searchBy!=null && !"".equalsIgnoreCase(searchBy.trim())) {
				resultList = getHibernateTemplate().find(query);
				DefaultLogger.debug(this, "resultList.size():::"+resultList.size());
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "ERROR-- While retriving getAllStageOtherBankBranch:::"+searchBy);
			throw new OtherBankException("ERROR-- While retriving getAllStageOtherBankBranch");
		}
		return resultList;
	}
	
	/**
	 * @return list of files uploaded in staging table of RelationshipMgr.
	 */
	public List getFileMasterListBankBranch(String searchBy) {
		Map parametersList = new HashMap();
		parametersList.put("transId",searchBy);
		DetachedCriteria criteria = DetachedCriteria.forEntityName("fileMapper").add(Restrictions.allEq(parametersList));
		List listId = getHibernateTemplate().findByCriteria(criteria);
       return listId;
	}
	
	public IOtherBranch insertActualOtherBankBranch(String sysId)
	throws OtherBranchException {
		Map parametersMap = new HashMap();
		parametersMap.put("id", new Long(sysId));
		
		DetachedCriteria criteria = DetachedCriteria.forEntityName(IOtherBranchDAO.STAGING_ENTITY_NAME).add(Restrictions.allEq(parametersMap));
		List branchList = getHibernateTemplate().findByCriteria(criteria);
			return (IOtherBranch)branchList.get(0);
		}
	
	public boolean isUniqueCodeBankBranch(String branchCode)throws OtherBankException
	{   ArrayList resultList =null;
		
		if(!(branchCode==null && branchCode.trim()=="")){
			String query = "SELECT FROM "+IOtherBranchDAO.ACTUAL_ENTITY_NAME+" WHERE  BRANCH_CODE='"+branchCode.toUpperCase()+"'";
			 resultList = (ArrayList) getHibernateTemplate().find(query);
			 
			 if(resultList.size()>0){
					String query1 = "SELECT FROM "+IOtherBranchDAO.STAGING_ENTITY_NAME+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' AND BRANCH_CODE like '"+branchCode.toUpperCase()+"'";
					ArrayList resultList1 = (ArrayList) getHibernateTemplate().find(query1);
					if(resultList1.size()>0)
						return true;
					else
						return false;
				}	
				else 
					return false;
		
		}else{
			throw new OtherBankException("ERROR- Entity name or key is null ");
		}
	}
	
	public boolean isUniqueBranchName(String branchName,String bankCode)throws OtherBankException
	{   
		ArrayList resultList =null;		
		if(!(branchName==null && branchName.trim()=="")){
			String query = "SELECT FROM "+IOtherBranchDAO.STAGING_ENTITY_NAME+" WHERE  UPPER(BRANCH_NAME) ='"+branchName.toUpperCase()+"' AND UPPER(OTHER_BANK_CODE) ='"+bankCode.toUpperCase()+"'" ;
			 resultList = (ArrayList) getHibernateTemplate().find(query);			 
			 if(resultList.size()>0){
				String query1 = "SELECT FROM "+IOtherBranchDAO.ACTUAL_ENTITY_NAME+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' AND UPPER(BRANCH_NAME) like '"+branchName.toUpperCase()+"' AND UPPER(OTHER_BANK_CODE) ='"+bankCode.toUpperCase()+"'" ;
				ArrayList resultList1 = (ArrayList) getHibernateTemplate().find(query1);
				if(resultList1.size()>0)
					return true;
				else
					return false;
			}	
				else 
					return false;		
		}else{
			throw new OtherBankException("ERROR- Entity name or key is null ");
		}
	}
	
	
	public boolean isUniqueRbiCode(String rbiCode)throws OtherBankException
	{   ArrayList resultList =null;
		
		if(!(rbiCode==null && rbiCode.trim()=="")){
			String query = "SELECT FROM "+IOtherBranchDAO.ACTUAL_ENTITY_NAME+" WHERE  RBI_CODE='"+rbiCode.toUpperCase()+"'";
			 resultList = (ArrayList) getHibernateTemplate().find(query);			 
			 if(resultList.size()>0){
					String query1 = "SELECT FROM "+IOtherBranchDAO.STAGING_ENTITY_NAME+" WHERE RBI_CODE like '"+rbiCode.toUpperCase()+"'";
					ArrayList resultList1 = (ArrayList) getHibernateTemplate().find(query1);
					if(resultList1.size()>0)
						return true;
					else
						return false;
				}	
				else 
					return false;
		
		}else{
			throw new OtherBankException("ERROR- Entity name or key is null ");
		}
	}
	
	public boolean isPrevFileUploadPendingBankBranch() {

		String rowCount = "FROM com.integrosys.cms.app.transaction.OBCMSTrxValue obcms WHERE obcms.transactionType='ADD_OTHER_BANKBRANCH'";
		List rowList = getHibernateTemplate().find(rowCount);
		if(rowList.size()>0){
			String sqlQuery = "FROM com.integrosys.cms.app.transaction.OBCMSTrxValue obcms WHERE obcms.transactionType='ADD_OTHER_BANKBRANCH' AND status != 'ACTIVE'";
			List branchList = getHibernateTemplate().find(sqlQuery);
			if(branchList.size()>0){
				return true;
			}
		}
		return false;
	}

	public IOtherBank getOtherBankByOtherBankCode(String otherBankCode) {
		
		try {
			String query = "FROM "+IOtherBankDAO.ACTUAL_ENTITY_NAME+" WHERE BANK_CODE ='"+ otherBankCode+"'";
			ArrayList resultList;
			
			if( query != "" ){
				
				ArrayList countryList =(ArrayList) getHibernateTemplate().find(query);
				 
				return (IOtherBank) countryList.get(0); 
				
			}else{
				throw new ValuationAgencyException("Unable to get List for "+getEntityName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ValuationAgencyException("ERROR-- While retriving getOtherBankByOtherBankCode ");
		}
		
	}

//*****************************UPLOAD***********************************
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		OBCMSTrxValue obCMSTrxValue = new OBCMSTrxValue();
		String query = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='ADD_OTHER_BANKBRANCH' AND obcms.transactionID='"+obFileMapperMaster.getTransId()+"'";
		List list = getHibernateTemplate().find(query);
		obCMSTrxValue = (OBCMSTrxValue)list.get(0);
		getHibernateTemplate().flush();
		getHibernateTemplate().delete(obCMSTrxValue);			
	}

	public String isCodeExisting(String countryCode, String regionCode,String stateCode, String cityCode) throws OtherBranchException,TrxParameterException, TransactionException {

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

	
	
	public IOtherBranch getOtherBranchListForPDC(String bankCode) throws OtherBankException {
		IOtherBranch otherBank = new OBOtherBranch();
		ArrayList resultList = new ArrayList();
		try{
			
			String query = "SELECT FROM "+getEntityName()+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' ";
			if(bankCode != null && !bankCode.trim().equals("")){
				query = query + "AND ( BRANCH_CODE like'"+bankCode+"') ";
			}
				 
			query = query + " ORDER BY BRANCH_CODE";	
			resultList = (ArrayList) getHibernateTemplate().find(query);
			
			
			
			
			
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getOtherBankById ",obe);
			obe.printStackTrace();
			throw new OtherBankException("Unable to find other bank with id ["+bankCode+"]");
		}
		return (IOtherBranch)resultList.get(0);
	}

	
	
}
