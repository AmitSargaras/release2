package com.integrosys.cms.app.otherbank.bus;

import java.io.Serializable;
import java.text.DateFormat;
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
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.bus.ICityDAO;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.bus.ICountryDAO;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.bus.IStateDAO;
import com.integrosys.cms.app.insurancecoverage.bus.InsuranceCoverageException;
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.app.otherbranch.bus.OtherBranchException;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.batch.IncompleteBatchJobException;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;

/**
 * Purpose : This OtherBankDAOImpl implements the methods that will be available to the
 * operating on a other bank 
 *  
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-02-18 17:27:01 +0800 (Fri, 18 Feb 2011) $
 */
public class OtherBankDAOImpl extends HibernateDaoSupport implements IOtherBankDAO {
	
	
	private ICountryDAO countryDAO;
	private IRegionDAO regionDAO;
	private IStateDAO stateDAO;
	private ICityDAO cityDAO;
	
	
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
	
	/**
	 * @return String entity name
	 */
	public String getEntityName(){
		return IOtherBankDAO.ACTUAL_ENTITY_NAME; 
	}
	
	
	public String getEntityNameForupload(){
		return IOtherBankDAO.CMS_FILE_MAPPER_ID; 
	}
	
	
	/**
	 * returns SearchResult List of Other Banks
	 */
	public SearchResult getOtherBank() throws OtherBankException{
		ArrayList resultList = new ArrayList();
		try{
			resultList = (ArrayList) getHibernateTemplate().loadAll(OBOtherBank.class);
			
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getOtherBank",obe);
			obe.printStackTrace();
			throw new OtherBankException("Unable to get other bank");
		}
		return new SearchResult(0, resultList.size(), resultList.size(), resultList);
	}

	/**
	 * checks branches are present for input bank id & returns boolean 
	 */
	public boolean checkOtherBranchById(long id) throws OtherBankException{
		boolean flag = false;
		try{
			List resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+IOtherBranchDAO.ACTUAL_ENTITY_NAME+" WHERE STATUS ='ACTIVE' AND OTHER_BANK_CODE ="+id);
			if(resultList.size()>0){
				flag = true;
			}
		}catch (Exception obe) {
			DefaultLogger.error(this, "error in checkOtherBankBYid", obe);
			obe.printStackTrace();
			throw new OtherBankException("Unable to find other bank with id ["+id+"]");
		}	
		return flag;
	}
	
	/**
	 * Returns other bank present for the  input bank id
	 */
	public IOtherBank getOtherBankById(long id) throws OtherBankException {
		IOtherBank otherBank = new OBOtherBank();
		try{
			otherBank = (IOtherBank)getHibernateTemplate().load(getEntityName(), new Long(id));
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getOtherBankById ",obe);
			obe.printStackTrace();
			throw new OtherBankException("Unable to find other bank with id ["+id+"]");
		}
		return otherBank;
	}
	
	
	public IOtherBank getOtherBankByCode(String bankCode) throws OtherBankException {
		IOtherBank otherBank = new OBOtherBank();
		ArrayList resultList = new ArrayList();
		try{
			
//			String query = "SELECT FROM "+getEntityName()+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' ";
			
			String query = "SELECT FROM "+getEntityName(); 		/* By Sandeep Shinde*/
			if(bankCode != null && !bankCode.trim().equals("")){
//				query = query + "AND ( BANK_CODE like '"+bankCode+"%') ";
				query = query + " WHERE UPPER(BANK_CODE) like '"+ bankCode.toUpperCase() +"' ";		/* By Sandeep Shinde*/
			}
				 
			query = query + " ORDER BY BANK_CODE";	
			resultList = (ArrayList) getHibernateTemplate().find(query);
			
			
			
			
			
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getOtherBankById ",obe);
			obe.printStackTrace();
			throw new OtherBankException("Unable to find other bank with id ["+bankCode+"]");
		}
		return (IOtherBank)resultList.get(0);
	}
	
	
	/**
	 * Return List of other bank. Also used for searching other bank 
	 */
		public IOtherBank getOtherBankListForPDC(String bankCode) throws OtherBankException {
		IOtherBank otherBank = new OBOtherBank();
		ArrayList resultList = new ArrayList();
		try{
			
			String query = "SELECT FROM "+getEntityName()+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' ";
			if(bankCode != null && !bankCode.trim().equals("")){
				query = query + "AND ( BANK_CODE like'"+bankCode+"%') ";
			}
				 
			query = query + " ORDER BY BANK_CODE";	
			resultList = (ArrayList) getHibernateTemplate().find(query);
			
			
			
			
			
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getOtherBankById ",obe);
			obe.printStackTrace();
			throw new OtherBankException("Unable to find other bank with id ["+bankCode+"]");
		}
		if(resultList.size() > 0)
			return (IOtherBank)resultList.get(0);
		else
			return null;
	}
	
	/**
	 * Return List of other bank. Also used for searching other bank
	 */
	public SearchResult getOtherBankList(String bankCode,String bankName) throws OtherBankException{
		ArrayList resultList = new ArrayList();
		try{
			String query = "SELECT FROM "+getEntityName() + " WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y'";
			if(bankCode != null && !bankCode.trim().equals("")){
				query = query + " AND  UPPER(BANK_CODE) like '"+ bankCode.toUpperCase() +"%' ";
			}
			if(bankName!=null && !bankName.trim().equals("")){
				query = query + " AND UPPER(BANK_NAME) like '"+ bankName.toUpperCase() +"%' ";
			}	 
			query = query + " ORDER BY BANK_CODE";	
			resultList = (ArrayList) getHibernateTemplate().find(query);
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getOtherBankList ",obe);
			obe.printStackTrace();
			throw new OtherBankException("Unable to get Other Bank List");
		}
		return new SearchResult(0, resultList.size(), resultList.size(), resultList);
	}
	
	public List<OBOtherBank> getOtherBankList(String bankCode,String bankName,String branchName,String branchCode ) throws OtherBankException{
		List<OBOtherBank> resultList = new ArrayList<OBOtherBank>();
		try{
			
			String query ="SELECT bank.id,branch.id,bank.otherBankName ,branch.otherBranchName,branch.address,branch.otherBranchCode,branch.contactMailId,bank.otherBankCode "
					+ " FROM actualOtherBank as bank,actualOtherBranch as  branch where bank.id=branch.otherBankCode  and bank.status!='INACTIVE' AND bank.deprecated!='Y'";
			
			//String query9 = "SELECT FROM "+getEntityName() + " banktable,actualOtherBranch branchtable WHERE banktable.STATUS!='INACTIVE' AND banktable.DEPRECATED!='Y' other_bank_code=CMS_OTHER_BANK.id";
			if(bankCode != null && !bankCode.trim().equals("")){
				query = query + " AND  UPPER(bank.otherBankCode) like '"+ bankCode.toUpperCase() +"%' ";
			}
			if(bankName!=null && !bankName.trim().equals("")){
				query = query + " AND UPPER(bank.otherBankName) like '"+ bankName.toUpperCase() +"%' ";
			}
			if(branchName!=null && !branchName.trim().equals("")){
				query = query + " AND UPPER(branch.otherBranchName) like '"+ branchName.toUpperCase() +"%' ";
			}	 
			/*if(branchCode!=null && !branchCode.trim().equals("")){
				query = query + " AND UPPER(branch.otherBranchCode) like '"+ branchCode.toUpperCase() +"%' ";
			}*/	 
			query = query + " ORDER BY branch.otherBankCode";	
			System.out.println("OtherBankDAOImpl.java => getOtherBankList => SQL =>"+query);
			List<Object> queryData = (ArrayList) getHibernateTemplate().find(query);
			
			DefaultLogger.info("############# getOtherBankList Query "+query,null);
			
			if(null != queryData ){
				
				OBOtherBank ob=null;
				for(Object otherBranch : queryData){
					Object[] arrayBoString = (Object[])otherBranch;
					ob=new OBOtherBank();
					
					if(null !=arrayBoString[0]){
						ob.setId(Long.parseLong(arrayBoString[0].toString()));
					}
					if(null !=arrayBoString[1]){
						ob.setOtherBranchId(arrayBoString[1].toString());
					}
					if(null !=arrayBoString[2]){
						ob.setOtherBankName(arrayBoString[2].toString());
					}
					if(null !=arrayBoString[3]){
						ob.setBranchName(arrayBoString[3].toString());
					}
					if(null !=arrayBoString[4]){
						ob.setBranchNameAddress(arrayBoString[4].toString());
					}
					if(null !=arrayBoString[5]){
						ob.setiFSCCode(arrayBoString[5].toString());
					}
					if(null !=arrayBoString[6]){
						ob.setContactMailId(arrayBoString[6].toString());
					}
					if(null !=arrayBoString[7]){
						ob.setOtherBankCode(arrayBoString[7].toString());
					}
					 
					resultList.add(ob);
				}
			}
		
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getOtherBankList ",obe);
			obe.printStackTrace();
			throw new OtherBankException("Unable to get Other Bank List");
		}
		return resultList;
	}
	
	
	
	
	
	
	public SearchResult getInsurerList() throws OtherBankException{
		
		
	try{
		String query = "SELECT FROM actualInsuranceCoverage WHERE DEPRECATED!='Y'";
		
		query = query + " ORDER BY INSURANCE_COVERAGE_CODE";
		ArrayList resultList = (ArrayList) getHibernateTemplate().find(query); 
		return new SearchResult(0, resultList.size(), resultList.size(), resultList);
	}catch (Exception obe) {
		DefaultLogger.error(this, "############# error in getInsuranceCoverageList ",obe);
		obe.printStackTrace();
		throw new InsuranceCoverageException("Unable to get Insurance Coverage List");
	}
	
}
	
	public List getCountryList(){
		List countryList = new ArrayList();
		try{
			
			String query = "SELECT FROM "+ICountryDAO.ACTUAL_ENTITY_NAME_COUNTRY+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' ";
			countryList = (List) getHibernateTemplate().find(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return countryList;
	}

	public List getRegionList(String countryId){
		List regionList = new ArrayList();
		try{
			
			String query = "SELECT FROM "+IRegionDAO.ACTUAL_ENTITY_NAME_REGION+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' " ;
			if(countryId!=null && !countryId.equals("")){
				query += " AND COUNTRY_ID = "+countryId;
			}	
			regionList = (List) getHibernateTemplate().find(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return regionList;
	}
	
	public SearchResult getInsurerNameFromCode(String insurerName) throws OtherBankException{
		
		
		try{
			String query = "SELECT FROM actualInsuranceCoverage WHERE DEPRECATED!='Y'and INSURANCE_COVERAGE_CODE='"+insurerName.trim()+"'";
			
			query = query + " ORDER BY INSURANCE_COVERAGE_CODE";
			ArrayList resultList = (ArrayList) getHibernateTemplate().find(query); 
			
			return new SearchResult(0, resultList.size(), resultList.size(), resultList);
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getInsuranceCoverageList ",obe);
			obe.printStackTrace();
			throw new InsuranceCoverageException("Unable to get Insurance Coverage List");
		}
		
		
		
		
	
	}
	
	public List getStateList(String regionId){
		List stateList = new ArrayList();
		try{
			
			String query = "SELECT FROM "+IStateDAO.ACTUAL_ENTITY_NAME_STATE+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y'";
			if(regionId!=null && !regionId.equals("")){
				query += " AND REGION_ID="+regionId;
			}	
			stateList = (List) getHibernateTemplate().find(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return stateList;
	}
	
	public List getCityList(String stateId){
		List cityList = new ArrayList();
		try{
			
			String query = "SELECT FROM "+ICityDAO.ACTUAL_ENTITY_NAME_CITY+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y'";
			if(stateId!=null && !stateId.equals("")){
				query += " AND STATE_ID="+stateId;
			}
			cityList = (List) getHibernateTemplate().find(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return cityList;
	}
	
	/**
	 * Updates the other bank
	 */
	public IOtherBank updateOtherBank(IOtherBank otherBank) throws OtherBankException {
		IOtherBank returnObj = new OBOtherBank();
		try{
			getHibernateTemplate().saveOrUpdate(getEntityName(), otherBank);
			returnObj = (IOtherBank) getHibernateTemplate().load(getEntityName(),new Long(otherBank.getId()));
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in updateOtherBank ",obe);
			obe.printStackTrace();
			throw new OtherBankException("Unable to update other bank with id ["+otherBank.getId()+"]");
		}
		return returnObj;
	}
	
	/**
	 * soft delete the other bank
	 */
	
	public IOtherBank deleteOtherBank(IOtherBank OtherBank) throws OtherBankException {
		IOtherBank returnObj = new OBOtherBank();
		try{
			OtherBank.setStatus("INACTIVE");
			OtherBank.setDeprecated("Y");
			getHibernateTemplate().saveOrUpdate(getEntityName(), OtherBank);
			returnObj = (IOtherBank) getHibernateTemplate().load(getEntityName(),new Long(OtherBank.getId()));
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in deleteOtherBank ",obe);
			obe.printStackTrace();
			throw new OtherBankException("Unable to delete other bank with id ["+OtherBank.getId()+"]");
		}
		return returnObj;
	}
	
	/**
	 * Create the other bank
	 */
	public IOtherBank createOtherBank(IOtherBank otherBank) throws OtherBankException {
		IOtherBank returnObj = new OBOtherBank();
		try{
			getHibernateTemplate().save(getEntityName(), otherBank);
			returnObj = (IOtherBank) getHibernateTemplate().load(getEntityName(),new Long(otherBank.getId()));
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in createOtherBank ",obe);
			obe.printStackTrace();
			throw new OtherBankException("Unable to create other bank ");
		}
		return returnObj;
	}

	/**
	 * @return OtherBank Object
	 * @param Entity Name
	 * @param Key  
	 * This method returns Other Bank Object
	 */
	public IOtherBank getOtherBank(String entityName, Serializable key)throws OtherBankException {
		if(!(entityName==null|| key==null)){
		return (IOtherBank) getHibernateTemplate().get(entityName, key);
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
	public IOtherBank updateOtherBank(String entityName, IOtherBank item)throws OtherBankException {
		if(!(entityName==null|| item==null)){
		getHibernateTemplate().update(entityName, item);

		return (IOtherBank) getHibernateTemplate().load(entityName,
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

	public IOtherBank createOtherBank(String entityName,
			IOtherBank systemBank)throws OtherBankException {
		if(!(entityName==null|| systemBank==null)){
		
		Long key = (Long) getHibernateTemplate().save(entityName, systemBank);
		systemBank.setId(key.longValue());
		return systemBank;
		}else{
			throw new OtherBankException("ERROR- Entity name or key is null ");
		}
	}
	
	/**
	 * @return SearchResult of all branches
	 */
	public SearchResult getOtherBranchList(String branchCode,String branchName,String state,String city,long id) {
		ArrayList resultList = new ArrayList();
		try{
//			String query = "SELECT FROM "+IOtherBranchDAO.ACTUAL_ENTITY_NAME+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' AND OTHER_BANK_CODE = "+id;
			
					/* By Sandeep Shinde*/
			String query = "SELECT FROM "+IOtherBranchDAO.ACTUAL_ENTITY_NAME+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' AND OTHER_BANK_CODE = "+id;
			
			if(branchCode != null && !branchCode.trim().equals("")){
				query = query + " AND Upper(BRANCH_CODE) like Upper('"+branchCode+"%') ";
			}
			if(branchName!=null && !branchName.trim().equals("")){
				query = query + " AND Upper(BRANCH_NAME) like Upper('"+branchName+"%') ";
			}
			if(state != null && !state.trim().equals("")){
				query = query + " AND STATE IN (FROM actualState WHERE Upper (state_name) like Upper ('"+state+"%')) ";
			}
			if(city!=null && !city.trim().equals("")){
				query = query + " AND CITY_TOWN IN (FROM actualCity WHERE Upper (city_name) LIKE Upper('"+city+"%')) ";
			}
			
			resultList = (ArrayList) getHibernateTemplate().find(query);
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in getOtherBranchList ",e);
			e.printStackTrace();
			throw new OtherBranchException("Unable to Load other bank branch List");
		}
		return new SearchResult(0, resultList.size(), resultList.size(), resultList);
	}
	
	//*************************************UPLOAD************************************************
	
	
	public int insertOtherBank(IFileMapperMaster fileMapperMaster, String userName, ArrayList result) {
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
				IOtherBank obOtherBank = new OBOtherBank();
				obOtherBank.setOtherBankCode((String) eachDataMap.get("BANK_CODE"));
				obOtherBank.setOtherBankName((String) eachDataMap.get("BANK_NAME"));
				obOtherBank.setAddress(eachDataMap.get("ADDRESS").toString());
				
				String countryCode = (String) eachDataMap.get("COUNTRY");
				String regionCode = (String) eachDataMap.get("REGION");
				String stateCode = (String) eachDataMap.get("STATE");
				String cityCode = (String) eachDataMap.get("CITY");
				String contactNumber = (String) (String)eachDataMap.get("CONTACT_NO");
				String faxNumber = (String) eachDataMap.get("FAX_NO");
				
				if ( countryCode != null && !countryCode.trim().equals("") ){
					ICountry country = getCountryDAO().getCountryByCountryCode(countryCode);
					obOtherBank.setCountry(country);
				}
				
				if ( regionCode != null && !regionCode.trim().equals("") ){
					IRegion region = getRegionDAO().getRegionByRegionCode(regionCode);
					obOtherBank.setRegion(region);
				}
				
				if ( stateCode != null && !stateCode.trim().equals("") ){
					IState state = getStateDAO().getStateByStateCode(stateCode);
					obOtherBank.setState(state);
				}
				
				if ( cityCode != null && !cityCode.trim().equals("") ){
					ICity city = getCityDAO().getCityByCityCode(cityCode);
					obOtherBank.setCity(city);
				}
				
				if ( contactNumber != null && !contactNumber.trim().equals("") )
					obOtherBank.setContactNo(Long.parseLong(contactNumber));
				else
					obOtherBank.setContactNo(0);
				
				if ( faxNumber != null && !faxNumber.trim().equals("") )
					obOtherBank.setFaxNo(Long.parseLong(faxNumber));
				else
					obOtherBank.setFaxNo(0);

				obOtherBank.setContactMailId((String) eachDataMap.get("CONTACT_MAIL_ID"));
				obOtherBank.setLastUpdateDate(new Date());
				obOtherBank.setCreatedBy(userName);
				obOtherBank.setCreationDate(new Date());
				
				obOtherBank.setStatus("ACTIVE");
				obOtherBank.setDeprecated("N");
				obOtherBank.setLastUpdateBy(userName);
				key = (Long) getHibernateTemplate().save("stagingOtherBank", obOtherBank);				
				//create FileMapperMaster
				
				IFileMapperMaster fileMapper = new OBFileMapperMaster();
				fileMapper.setFileId(fileMapperMaster.getFileId());
				fileMapper.setSysId(key.longValue());
				fileMapper.setTransId(fileMapperMaster.getTransId());
				if(!(fileMapperMaster==null)){
					key = (Long) getHibernateTemplate().save("fileMapper", fileMapper);
					getHibernateTemplate().get("fileMapper", key);
					}else{
						throw new OtherBankException("ERROR- Entity name or key is null ");
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
	
	public IFileMapperId createFileId(String entityName, IFileMapperId fileId)
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
	
	public IOtherBank insertOtherBank(String entityName,
			IOtherBank otherBank)
			throws OtherBankException {
		if(!(entityName==null|| otherBank==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, otherBank);
			otherBank.setId(key.longValue());
			return otherBank;
			}else{
				throw new OtherBankException("ERROR- Entity name or key is null ");
			}
	}
	
	public IFileMapperId insertOtherBank(String entityName,
			IFileMapperId fileId, IOtherBankTrxValue trxValue)
			throws OtherBankException {
		
		if(!(entityName==null|| fileId==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, fileId);
			fileId.setId(key.longValue());
			return fileId;
		}else{
			throw new OtherBankException("ERROR- Entity name or key is null ");
		}	
	}
	
	
	public IFileMapperId getInsertFileList(String entityName, Serializable key)
	throws OtherBankException {
		if(!(entityName==null|| key==null)){
			
			return (IFileMapperId) getHibernateTemplate().get(entityName, key);
			}else{
				throw new OtherBankException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	
	public List getAllStageOtherBank(String searchBy, String login) {
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
			String query = "SELECT FROM "+IOtherBankDAO.STAGING_ENTITY_NAME+" WHERE DEPRECATED = 'N' AND ID IN ("+strId+")";
			try {
				if (searchByValue.trim() != "") {
					resultList = getHibernateTemplate().find(query);
				}
	
			} catch (Exception e) {
				throw new OtherBankException("ERROR-- While retriving RelationshipMgr");
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
	
	public IOtherBank insertActualOtherBank(String sysId)
	throws OtherBankException {
		Map parametersMap = new HashMap();
		parametersMap.put("id", new Long(sysId));
		
		DetachedCriteria criteria = DetachedCriteria.forEntityName(IOtherBankDAO.STAGING_ENTITY_NAME).add(Restrictions.allEq(parametersMap));
		List holidayList = getHibernateTemplate().findByCriteria(criteria);
			return (IOtherBank)holidayList.get(0);
		}
	
	public boolean isUniqueCode(String bankCode)throws OtherBankException {
		ArrayList resultList =null;		
		if(!(bankCode==null && bankCode.trim()=="")){
			String query = "SELECT FROM "+IOtherBankDAO.ACTUAL_ENTITY_NAME+" WHERE  UPPER(BANK_CODE)='"+bankCode.toUpperCase()+"'";
			 resultList = (ArrayList) getHibernateTemplate().find(query);
			 
			 if(resultList.size()>0){
					String query1 = "SELECT FROM "+IOtherBankDAO.STAGING_ENTITY_NAME+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' AND UPPER(BANK_CODE) like '"+bankCode.toUpperCase()+"'";
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
	
	
	public boolean isUniqueName(String bankName)throws OtherBankException
	{   ArrayList resultList =null;
		String query = "";
		String newBankName = "";
		if( !(bankName==null && bankName.trim()=="") ){						
			int len =bankName.length();
			for(int i=0;i<len;i++){
				if( String.valueOf(bankName.charAt(i)).equals("'"))
					newBankName = newBankName.concat("'");
				
				newBankName = newBankName.concat(String.valueOf(bankName.charAt(i)));
				}
			query = "SELECT FROM "+IOtherBankDAO.ACTUAL_ENTITY_NAME+" WHERE  UPPER(BANK_NAME) ='"+newBankName.toUpperCase()+"'";			
			resultList = (ArrayList) getHibernateTemplate().find(query);
			 
			 if(resultList.size()>0){
					String query1 = "SELECT FROM "+IOtherBankDAO.STAGING_ENTITY_NAME+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' AND UPPER(BANK_NAME) like '"+bankName.toUpperCase()+"'";
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
	
	public boolean isPrevFileUploadPending() {

		String rowCount = "FROM com.integrosys.cms.app.transaction.OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_OTHER_BANK'";
		List rowList = getHibernateTemplate().find(rowCount);
		if(rowList.size()>0){
			String sqlQuery = "FROM com.integrosys.cms.app.transaction.OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_OTHER_BANK' AND status != 'ACTIVE'";
			List holidayList = getHibernateTemplate().find(sqlQuery);
			if(holidayList.size()>0){
				return true;
			}
		}
		return false;
	}

//*****************************UPLOAD***********************************

	public String getCityName(String cityId)
	{   
		ArrayList resultList =null;
		String cityName = null;
		
		String query = "SELECT FROM "+ICityDAO.ACTUAL_ENTITY_NAME_CITY+" WHERE  ID='"+cityId+"'";
		resultList = (ArrayList) getHibernateTemplate().find(query);
		
		Iterator itr = resultList.iterator();
		
		while(itr.hasNext()){
			ICity city = (ICity)itr.next();
			cityName = city.getCityName();
		}
		return cityName;
	}	
	
	
	public String getStateName(String stateId)
	{   
		ArrayList resultList =null;
		String cityName = null;
		
		String query = "SELECT FROM "+IStateDAO.ACTUAL_ENTITY_NAME_STATE+" WHERE  ID='"+stateId+"'";
		resultList = (ArrayList) getHibernateTemplate().find(query);
		
		Iterator itr = resultList.iterator();
		
		while(itr.hasNext()){
			IState state = (IState)itr.next();
			cityName = state.getStateName();
		}
		return cityName;
	}	
	
	
	public String getRegionName(String regionId)
	{   
		ArrayList resultList =null;
		String cityName = null;
		
		String query = "SELECT FROM "+IRegionDAO.ACTUAL_ENTITY_NAME_REGION+" WHERE  ID='"+regionId+"'";
		resultList = (ArrayList) getHibernateTemplate().find(query);
		
		Iterator itr = resultList.iterator();
		
		while(itr.hasNext()){
			IRegion region = (IRegion)itr.next();
			cityName = region.getRegionName();
		}
		return cityName;
	}	
	
	public String getCountryName(String countryId)
	{   
		ArrayList resultList =null;
		String cityName = null;
		
		String query = "SELECT FROM "+ICountryDAO.ACTUAL_ENTITY_NAME_COUNTRY+" WHERE  ID='"+countryId+"'";
		resultList = (ArrayList) getHibernateTemplate().find(query);
		
		Iterator itr = resultList.iterator();
		
		while(itr.hasNext()){
			ICountry country = (ICountry)itr.next();
			cityName = country.getCountryName();
		}
		return cityName;
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws OtherBankException, TrxParameterException,TransactionException {
		OBCMSTrxValue obCMSTrxValue = new OBCMSTrxValue();
		String query = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_OTHER_BANK' AND obcms.transactionID='"+obFileMapperMaster.getTransId()+"'";
		List list = getHibernateTemplate().find(query);
		obCMSTrxValue = (OBCMSTrxValue)list.get(0);
		getHibernateTemplate().flush();
		getHibernateTemplate().delete(obCMSTrxValue);			
	}
	
	public String isCodeExisting(String countryCode, String regionCode,String stateCode, String cityCode) throws OtherBankException,TrxParameterException, TransactionException {

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
	
	public SearchResult getOtherBankList() throws OtherBankException{
		ArrayList resultList = new ArrayList();
		try{
			String query = "SELECT FROM "+getEntityName() + " WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' ORDER BY BANK_CODE";
			resultList = (ArrayList) getHibernateTemplate().find(query);
		}catch (Exception obe) {
			DefaultLogger.error(this, "############# error in getOtherBankList ",obe);
			obe.printStackTrace();
			throw new OtherBankException("Unable to get Other Bank List");
		}
		return new SearchResult(0, resultList.size(), resultList.size(), resultList);
	}
}
