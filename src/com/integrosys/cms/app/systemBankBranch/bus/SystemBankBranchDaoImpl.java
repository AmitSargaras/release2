package com.integrosys.cms.app.systemBankBranch.bus;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.OBItem;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.bus.ICityDAO;
import com.integrosys.cms.app.geography.city.bus.OBCity;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.bus.ICountryDAO;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.bus.IStateDAO;
import com.integrosys.cms.app.geography.state.bus.OBState;
import com.integrosys.cms.app.otherbranch.bus.OtherBranchException;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListSubItem;
import com.integrosys.cms.app.recurrent.bus.OBRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.OBRecurrentCheckListSubItem;
import com.integrosys.cms.app.systemBank.bus.OBSystemBank;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.batch.IncompleteBatchJobException;
/**
 * @author  Abhijit R. 
 * Dao implication of interface SystemBankBranch
 */

public class SystemBankBranchDaoImpl extends HibernateDaoSupport implements ISystemBankBranchDao{
	private DBUtil dbUtil = null;
	/**
	  * @return Particular System Bank Branch according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */
	
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

	public ISystemBankBranch getSystemBankBranch(String entityName, Serializable key)throws SystemBankBranchException {
		
		if(!(entityName==null|| key==null)){
		
		return (ISystemBankBranch) getHibernateTemplate().get(entityName, key);
		}else{
			throw new SystemBankBranchException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	public IRecurrentCheckList getRecurrentCheckList(long key)throws Exception {
		
		if(key!=0L){
		
		return (IRecurrentCheckList) getHibernateTemplate().load("stageRecurrentDoc", new Long(key));
		}else{
			throw new SystemBankBranchException("ERROR-- Entity Name Or Key is null");
		}
	}
	
public IRecurrentCheckListItem[] getRecurrentCheckListItem(long key)throws Exception {
	
	
	String sql = "Select recurrent_item_id,recurrent_item_ref_id,frequency,frequency_unit,due_date,remarks,is_deleted,doc_end_date,grace_period,grace_period_unit,chase_remind_ind,one_off,last_doc_entry_date,end_date_changed_count,recurrent_item_desc,doc_type from STAGE_RECURRENT_DOC_ITEM where recurrent_doc_id="+key;


	try {
		dbUtil = new DBUtil();
		dbUtil.setSQL(sql);
		// DefaultLogger.debug(this, sql);
	
		ResultSet rs = dbUtil.executeQuery();
		ArrayList resultList = new ArrayList();
		IRecurrentCheckListItem recurrentCheckListItem = null;
		while (rs.next()) {
				
			recurrentCheckListItem = new OBRecurrentCheckListItem();
			recurrentCheckListItem.setChaseReminderInd(true);
			recurrentCheckListItem.setCheckListItemID(rs.getLong("recurrent_item_id"));
			recurrentCheckListItem.setCheckListItemRef(rs.getLong("recurrent_item_ref_id"));
			recurrentCheckListItem.setFrequency(rs.getInt("frequency"));
			recurrentCheckListItem.setFrequencyUnit(rs.getString("frequency_unit"));
			recurrentCheckListItem.setGracePeriod(rs.getInt("grace_period"));
			recurrentCheckListItem.setGracePeriodUnit(rs.getString("grace_period_unit")); 
			recurrentCheckListItem.setDocType(rs.getString("doc_type")); 
			
			 String pattern = "yyyy-MM-dd";
		        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		       
		     String dueDate = rs.getString("due_date");   
			if (dueDate != null) {
				Date date =sdf.parse(dueDate);
				recurrentCheckListItem.setInitialDueDate(date);
			}
			recurrentCheckListItem.setRemarks(rs.getString("remarks"));
			if(rs.getString("is_deleted").equals("N"))
			{
			recurrentCheckListItem.setIsDeletedInd(false);
			}
			else
			{
		    recurrentCheckListItem.setIsDeletedInd(true);	
			}
			
			String docEndDate = rs.getString("doc_end_date");
			if (docEndDate != null) {
				Date date =sdf.parse(docEndDate);
				recurrentCheckListItem.setInitialDocEndDate(date);
			}
			if(rs.getString("chase_remind_ind").equals("N"))
			{
			recurrentCheckListItem.setChaseReminderInd(false);
			}
			else
			{
			recurrentCheckListItem.setChaseReminderInd(true);	
			}
			if(rs.getString("one_off").equals("N"))
			{
			recurrentCheckListItem.setIsOneOffInd(false);
			}
			else
			{
			recurrentCheckListItem.setIsOneOffInd(true);	
			}
			
			String lastDocEntryDate = rs.getString("last_doc_entry_date");
			if (lastDocEntryDate != null) {
				Date date =sdf.parse(lastDocEntryDate);
				recurrentCheckListItem.setLastDocEntryDate(date);
			}
			recurrentCheckListItem.setEndDateChangedCount(rs.getInt("end_date_changed_count"));
			String desc = rs.getString("recurrent_item_desc");
			IItem item = null;
			item = recurrentCheckListItem.getItem();
			if (item == null) {
				item = new OBItem();
			}

			item.setItemDesc(desc);
			recurrentCheckListItem.setItem(item);
			
			resultList.add(recurrentCheckListItem);
		}	

		rs.close();
		return (IRecurrentCheckListItem[]) resultList.toArray(new IRecurrentCheckListItem[resultList.size()]);
	}
	catch (SQLException ex) {
		throw new SearchDAOException("SQLException in getCheckListItemListForAudit", ex);
	}
	catch (Exception ex) {
		throw new SearchDAOException("Exception in getCheckListItemListForAudit", ex);
	}
	finally {
		try {
			dbUtil.close();
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListItemListForAudit", ex);
		}
	}	
	
	
//	List resultList= null;
//		if(key!=0L){
//			String query = " FROM stageRecurrentDocItem stage WHERE stage.recurrentDocId IN ("+key+")";
//			try {
//				
//					resultList = getHibernateTemplate().find(query);
//					
//			} catch (Exception e) {
//				throw new CountryException("ERROR-- While retriving Country");
//			}
//		
//	}
//		return (IRecurrentCheckListItem[]) resultList.toArray(new IRecurrentCheckListItem[resultList.size()]);
}

public IRecurrentCheckListSubItem[]  getRecurrentCheckListSubItem(long key)throws Exception {
	String sql = "Select sub_item_id,sub_item_ref_id,frequency,frequency_unit,remarks,grace_period,grace_period_unit,status,due_date,is_deleted_ind,doc_end_date,rec_date,deferred_date,waived_date,deferred_cnt,action_party from stage_recurrent_doc_sub_item where recurrent_item_id="+key;

	try {
		dbUtil = new DBUtil();
		dbUtil.setSQL(sql);
		// DefaultLogger.debug(this, sql);
	
		ResultSet rs = dbUtil.executeQuery();
		ArrayList resultList = new ArrayList();
		IRecurrentCheckListSubItem recurrentCheckListItem = null;
		while (rs.next()) {
			
			recurrentCheckListItem = new OBRecurrentCheckListSubItem();
			recurrentCheckListItem.setSubItemID(rs.getLong("sub_item_id"));
			recurrentCheckListItem.setStatus(rs.getString("status"));
			recurrentCheckListItem.setSubItemRef(rs.getLong("sub_item_ref_id"));
			recurrentCheckListItem.setFrequency(rs.getInt("frequency"));
			recurrentCheckListItem.setFrequencyUnit(rs.getString("frequency_unit"));
			recurrentCheckListItem.setGracePeriod(rs.getInt("grace_period"));
			recurrentCheckListItem.setGracePeriodUnit(rs.getString("grace_period_unit"));
			recurrentCheckListItem.setRemarks(rs.getString("remarks"));

				String pattern = "yyyy-MM-dd";
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				
				
				String dateStr = rs.getString("due_date");
					if (dateStr != null) {
						Date date = sdf.parse(dateStr);
						recurrentCheckListItem.setDueDate(date);
					}
				
			if(rs.getString("is_deleted_ind").equals("N"))
			{
				recurrentCheckListItem.setIsDeletedInd(false);
			}
			else
			{
				recurrentCheckListItem.setIsDeletedInd(true);	
			}

		
			String docEndDate = rs.getString("doc_end_date");
			if (docEndDate != null) {
				Date date = sdf.parse(docEndDate);
				recurrentCheckListItem.setDocEndDate(date);
			}
			
			String recDate = rs.getString("rec_date");
			if (recDate != null) {
				Date date = sdf.parse(recDate);
				
				recurrentCheckListItem.setReceivedDate(date);
			}
		
			String deferredDate = rs.getString("deferred_date");
			if (deferredDate != null) {
				Date date = sdf.parse(deferredDate);
				recurrentCheckListItem.setDeferredDate(date);
			}
			
			String waivedDate = rs.getString("waived_date");
			if (waivedDate != null) {
				Date date = sdf.parse(waivedDate);
				recurrentCheckListItem.setWaivedDate(date);
			}
			recurrentCheckListItem.setDeferredCount(Long.parseLong(rs.getString("deferred_cnt")));
			
			recurrentCheckListItem.setActionParty(rs.getString("action_party"));
			
			//recurrentCheckListItem.setRecurrentItemDesc(rs.getString("recurrent_item_desc"));
			
			resultList.add(recurrentCheckListItem);
		}	

		rs.close();
		return (IRecurrentCheckListSubItem[]) resultList.toArray(new IRecurrentCheckListSubItem[resultList.size()]);
	}
	catch (SQLException ex) {
		throw new SearchDAOException("SQLException in getCheckListItemListForAudit", ex);
	}
	catch (Exception ex) {
		throw new SearchDAOException("Exception in getCheckListItemListForAudit", ex);
	}
	finally {
		try {
			dbUtil.close();
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCheckListItemListForAudit", ex);
		}
	}	
}
	
	/**
	 * @return SystemBankBranch Object
	 * @param Entity Name
	 * @param SystemBankBranch Object  
	 * This method Updates System Bank Branch Object
	 */
	
	public ISystemBankBranch updateSystemBankBranch(String entityName, ISystemBankBranch item)throws SystemBankBranchException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (ISystemBankBranch) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new SystemBankBranchException("ERROR-- Entity Name Or Key is null");
		}
	}
	/**
	 * @return SystemBankBranch Object
	 * @param Entity Name
	 * @param SystemBankBranch Object  
	 * This method delete System Bank Branch Object
	 */
	
	public ISystemBankBranch deleteSystemBankBranch(String entityName, ISystemBankBranch item)throws SystemBankBranchException{

		if(!(entityName==null|| item==null)){
			item.setDeprecated("Y");
			item.setStatus("INACTIVE");
			getHibernateTemplate().update(entityName, item);
			return (ISystemBankBranch) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new SystemBankBranchException("ERROR-- Entity Name Or Key is null");
		}
	}
	/**
	 * @return SystemBankBranch Object
	 * @param Entity Name
	 * @param SystemBankBranch Object  
	 * This method Creates System Bank Branch Object
	 */
	public ISystemBankBranch createSystemBankBranch(String entityName,
			ISystemBankBranch systemBankBranch)
			throws SystemBankBranchException {
		if(!(entityName==null|| systemBankBranch==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, systemBankBranch);
			systemBankBranch.setId(key.longValue());
			return systemBankBranch;
			}else{
				throw new SystemBankException("ERROR- Entity name or key is null ");
			}
	}


	
	
	/**
	  * @return Particular System Bank Branch according 
	  * to the id passed as parameter.  
	  * @param Branch Code 
	  */

	public ISystemBankBranch load(String entityName, long id)throws SystemBankBranchException
	{
		if(!(entityName==null|| id==0)){
		return (ISystemBankBranch)getHibernateTemplate().get(entityName , new Long(id));	
		}else{
			throw new SystemBankException("ERROR- Entity name or key is null ");
		}
	}
	
	public ISystemBankBranch getSystemBankBranch(String branchCode) {
		ArrayList resultList = new ArrayList();
		ISystemBankBranch systemBankBranch = new OBSystemBankBranch();
		try{
			String query = "SELECT FROM "+ISystemBankBranchDao.ACTUAL_SYSTEM_BANK_BRANCH_NAME +" where DEPRECATED = 'N' "  ;
			
			if(branchCode != null && !branchCode.trim().equals("")){
				query = query + " and Upper(SYSTEM_BANK_BRANCH_CODE) like Upper ('"+branchCode+"') ";
			
			}
			
			List sysBranchList = getHibernateTemplate().find(query);
			if(sysBranchList.size()>0){
				systemBankBranch = (ISystemBankBranch) sysBranchList.get(0);
			}else{
				systemBankBranch = null;
			}
			return systemBankBranch;
			
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in getBranchList ",e);
			e.printStackTrace();
			throw new SystemBankException("Unable to Load  bank branch List");
		}
		
	}
	
	public SearchResult getSystemBankBranchList(String branchcode)throws SystemBankBranchException{
		
		ArrayList resultList = new ArrayList();
		try{
			String query = "SELECT FROM "+ISystemBankBranchDao.ACTUAL_SYSTEM_BANK_BRANCH_NAME +" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y'";
			
			query += " AND Upper( SYSTEM_BANK_BRANCH_CODE)like Upper ('"+branchcode+"') ";
		
				
			
			resultList = (ArrayList) getHibernateTemplate().find(query);
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in getOtherBranchList ",e);
			e.printStackTrace();
			throw new OtherBranchException("Unable to Load other bank branch List");
		}
		return new SearchResult(0, resultList.size(), resultList.size(), resultList);
	}
	
	
	/**
	  * Validate Branch Code
	  */

	public boolean isUniqueCode(String coloumn ,String value)throws SystemBankBranchException
	{
		
		
			String query = "SELECT FROM "+ISystemBankBranchDao.STAGE_SYSTEM_BANK_BRANCH_NAME+" WHERE Upper("+coloumn+") = '"+value.toUpperCase()+"'";
			ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			if(resultList.size()>0){
				//String query1 = "SELECT FROM "+ISystemBankBranchDao.ACTUAL_SYSTEM_BANK_BRANCH_NAME+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' AND Upper("+coloumn+") = '"+value.toUpperCase()+"'";
				
				//changes for do not create duplicate branch
				String query1 = "SELECT FROM "+ISystemBankBranchDao.ACTUAL_SYSTEM_BANK_BRANCH_NAME+" WHERE STATUS!='INACTIVE'  AND Upper("+coloumn+") = '"+value.toUpperCase()+"'";
				ArrayList resultList1 = (ArrayList) getHibernateTemplate().find(query1);
				if(resultList1.size()>0)
					return true;
				else 
					return false;
			}else
		return false;
			
		
	}
	
	/**
	 * @return List of all authorized System Bank Branch
	 */
	public SearchResult getAllSystemBankBranch() {
		String entityName = ISystemBankBranchDao.ACTUAL_SYSTEM_BANK_BRANCH_NAME;
		try{
	//	String query =  " SELECT FROM "+ entityName+" where STATUS!='INACTIVE' order by system_bank_branch_code ";
			//Changes to remove deleted branch from dropdown
		String query =  " SELECT FROM "+ entityName+" where STATUS!='INACTIVE' and deprecated='N' order by system_bank_branch_code ";
		
//		System.out.println("Query for listing Branch"+query);
		
		
		 ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			
		
			SearchResult searchresult = new SearchResult(0, 10, resultList.size(), resultList);
			return searchresult;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemBankBranchException("ERROR-- While retriving System Bank Branch");
		}
		
		}
	public SearchResult getAllSystemBankBranchForHUB()throws SystemBankBranchException {
		String entityName = ISystemBankBranchDao.ACTUAL_SYSTEM_BANK_BRANCH_NAME;
		try{
	//	String query =  " SELECT FROM "+ entityName +" where is_hub='Y' AND STATUS!='INACTIVE' ";
		//Changes to remove deleted branch from dropdown
		String query =  " SELECT FROM "+ entityName +" where is_hub='Y' AND STATUS!='INACTIVE' AND  deprecated='N' ";
		
		// System.out.println("Query for listing Branch"+query);
		
		
		 ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			
		
			SearchResult searchresult = new SearchResult(0, 10, resultList.size(), resultList);
			return searchresult;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SystemBankBranchException("ERROR-- While retriving System Bank Branch");
		}
		
		}
	
	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId createFileId(String entityName, IFileMapperId fileId)
			throws SystemBankBranchException {
		// TODO Auto-generated method stub
		if (!(entityName == null || fileId == null)) {

			Long key = (Long) getHibernateTemplate().save(entityName, fileId);
			fileId.setId(key.longValue());
			return fileId;
		} else {
			throw new SystemBankException("ERROR- Entity name or key is null ");
		}
	}

	/**
	 * @return int (insert record count)
	 * @param userName
	 * @param result list of upload files
	 * @param FileMapperMaster Object  
	 * This method Creates FileMapperId Object
	 */
	public int insertSystemBankBranch(IFileMapperMaster fileMapperMaster,
			String userName, ArrayList result) {
		// TODO Auto-generated method stub
		int noOfRecInserted = 0;
		Long key = null;
		if (result == null || result.size() <= 0) {
			throw new IncompleteBatchJobException(
					"Data to be instered to DB is null or empty");
		}
		try {
			for (int index = 0; index < result.size(); index++) {
				HashMap eachDataMap = (HashMap) result.get(index);
				OBSystemBankBranch obSystemBankBranch = new OBSystemBankBranch();
				
				obSystemBankBranch.setSystemBankCode(new OBSystemBank());
				obSystemBankBranch.getSystemBankCode().setId(getSystemBankByCode((String) eachDataMap.get("SYSTEM_BANK_CODE")));
				
				obSystemBankBranch.setSystemBankBranchCode((String) eachDataMap.get("SYSTEM_BANK_BRANCH_CODE"));
				obSystemBankBranch.setSystemBankBranchName((String) eachDataMap.get("SYSTEM_BANK_BRANCH_NAME"));
				obSystemBankBranch.setContactNumber((String) eachDataMap.get("CONTACT_NUMBER"));
				obSystemBankBranch.setContactMail((String) eachDataMap.get("CONTACT_MAIL"));
				obSystemBankBranch.setAddress((String) eachDataMap.get("ADDRESS"));
				//ICity city = getCityDAO().getCityByCityCode((String)eachDataMap.get("CITY_TOWN"));
				long cityId = getCityDAO().getCityByCityId((String)eachDataMap.get("CITY_TOWN"));
				obSystemBankBranch.setCityTown(new OBCity());
				obSystemBankBranch.getCityTown().setIdCity(cityId);
				
				//IState state = getStateDAO().getStateByStateCode((String)eachDataMap.get("STATE"));
				long stateId = getStateDAO().getStateByStateId((String)eachDataMap.get("STATE"));
				obSystemBankBranch.setState(new OBState());
				obSystemBankBranch.getState().setIdState(stateId);
				
				//IRegion region = getRegionDAO().getRegionByRegionCode((String) eachDataMap.get("REGION"));
				long regionId = getRegionDAO().getRegionByRegionId((String) eachDataMap.get("REGION"));
				obSystemBankBranch.setRegion(new OBRegion());
				obSystemBankBranch.getRegion().setIdRegion(regionId);
				
				//ICountry country = getCountryDAO().getCountryByCountryCode((String)eachDataMap.get("COUNTRY"));
				long countryId = getCountryDAO().getCountryByCountryId((String)eachDataMap.get("COUNTRY"));
				obSystemBankBranch.setCountry(new OBCountry());
				obSystemBankBranch.getCountry().setIdCountry(countryId);
				obSystemBankBranch.setFaxNumber((String) eachDataMap.get("FAX_NUMBER"));
				obSystemBankBranch.setRbiCode((String) eachDataMap
						.get("RBI_CODE"));
				obSystemBankBranch.setIsHub((String) eachDataMap.get("IS_HUB"));
				obSystemBankBranch.setLinkedHub((String) eachDataMap
						.get("LINKED_HUB"));
				obSystemBankBranch.setIsVault((String) eachDataMap
						.get("IS_VAULT"));
				obSystemBankBranch.setCustodian1((String) eachDataMap
						.get("CUSTODIAN1"));
				obSystemBankBranch.setCustodian2((String) eachDataMap
						.get("CUSTODIAN2"));
				obSystemBankBranch
						.setStatus("ACTIVE");
				obSystemBankBranch.setDeprecated("N");
				obSystemBankBranch.setCreationDate(new Date());
				obSystemBankBranch.setCreateBy(userName);
				obSystemBankBranch.setLastUpdateDate(new Date());
				obSystemBankBranch.setLastUpdateBy(userName);

				// getHibernateTemplate().saveOrUpdate("actualSystemBankBranch",obSystemBankBranch);

				key = (Long) getHibernateTemplate().save(
						"stageSystemBankBranch", obSystemBankBranch);
				//getHibernateTemplate().setFlushMode(getHibernateTemplate().FLUSH_COMMIT);
				// create FileMapperMaster

				IFileMapperMaster fileMapper = new OBFileMapperMaster();
				fileMapper.setFileId(fileMapperMaster.getFileId());
				fileMapper.setSysId(key.longValue());
				fileMapper.setTransId(fileMapperMaster.getTransId());
				if (!(fileMapperMaster == null)) {
					key = (Long) getHibernateTemplate().save("fileMapper",
							fileMapper);
					getHibernateTemplate().get("fileMapper", key);
				} else {
					throw new SystemBankException(
							"ERROR- Entity name or key is null ");
				}
				noOfRecInserted++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IncompleteBatchJobException(
					"Unable to update/insert that retrived form CSV file");

		}
		return noOfRecInserted;
	}

	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId getInsertFileList(String entityName, Serializable key)
			throws SystemBankBranchException {
		if (!(entityName == null || key == null)) {

			return (IFileMapperId) getHibernateTemplate().get(entityName, key);
		} else {
			throw new SystemBankBranchException(
					"ERROR-- Entity Name Or Key is null");
		}
	}
	
	/**
	 * @return SystemBankBranch Object
	 * @param Entity Name
	 * @param sysId  
	 * This method Creates System Bank Branch Object
	 */
	public ISystemBankBranch insertActualSystemBankBranch(String sysId)
			throws SystemBankBranchException {
		Map parametersMap = new HashMap();
		parametersMap.put("id", new Long(sysId));

		DetachedCriteria criteria = DetachedCriteria.forEntityName(
				"stageSystemBankBranch").add(Restrictions.allEq(parametersMap));
		List sysBranchList = getHibernateTemplate().findByCriteria(criteria);
		return (ISystemBankBranch) sysBranchList.get(0);
	}

	/**
	 * @return SystemBankBranch Object
	 * @param Entity
	 *            Name
	 * @param SystemBankBranch
	 *            Object This method Creates System Bank Branch Object
	 */
	public ISystemBankBranch insertSystemBankBranch(String entityName,
			ISystemBankBranch systemBankBranch)
			throws SystemBankBranchException {
		if (!(entityName == null || systemBankBranch == null)) {

			Long key = (Long) getHibernateTemplate().save(entityName,
					systemBankBranch);
			systemBankBranch.setId(key.longValue());
			return systemBankBranch;
		} else {
			throw new SystemBankException("ERROR- Entity name or key is null ");
		}
	}

	/**
	 * @return FileMapperId Object
	 * @param Entity
	 *            Name
	 * @param FileMapperId
	 *            Object This method Creates FileMapperId Object
	 */
	public IFileMapperId insertSystemBankBranch(String entityName,
			IFileMapperId fileId, ISystemBankBranchTrxValue trxValue)
			throws SystemBankBranchException {

		if (!(entityName == null || fileId == null)) {

			Long key = (Long) getHibernateTemplate().save(entityName, fileId);
			fileId.setId(key.longValue());
			return fileId;
		} else {
			throw new SystemBankException("ERROR- Entity name or key is null ");
		}
	}
	
	/**
	 * @return Maker check if previous upload is pending.
	 */
	public boolean isPrevFileUploadPending() {

		String rowCount = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_SYS_BRANCH'";
		List rowList = getHibernateTemplate().find(rowCount);
		if(rowList.size()>0){
			String sqlQuery = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_SYS_BRANCH' AND status != 'ACTIVE'";
			List holidayList = getHibernateTemplate().find(sqlQuery);
			if(holidayList.size()>0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return list of files uploaded in staging table of SystemBankBranch.
	 */
	public List getFileMasterList(String searchBy) {
		Map parametersList = new HashMap();
		parametersList.put("transId",searchBy);
		DetachedCriteria criteria = DetachedCriteria.forEntityName("fileMapper").add(Restrictions.allEq(parametersList));
		List listId = getHibernateTemplate().findByCriteria(criteria);
       return listId;
	}
	

	public List getAllStageSystemBankBranch(String searchBy, String login) {
		List resultList = null;
		String searchByValue = searchBy;
		String strId ="";
		List listId = getFileMasterList(searchBy);
		for (int i = 0; i < listId.size(); i++) {
			OBFileMapperMaster map = (OBFileMapperMaster) listId.get(i);
				// System.out.println("val = " + map.getSysId());
				if(!strId.equals("")){
					strId +=",";
				}
				strId += map.getSysId();
		}
		
		if(!strId.equals("")){
			Map parameters = new HashMap();
			parameters.put("deprecated","N");
			parameters.put("id", strId);
			String query = "SELECT FROM "+ISystemBankBranchDao.STAGE_SYSTEM_BANK_BRANCH_NAME+" WHERE DEPRECATED = 'N' AND ID IN ("+strId+")";
			try {
				if (searchByValue.trim() != "") {
					resultList = getHibernateTemplate().find(query);
				}
	
			} catch (Exception e) {
				throw new SystemBankException("ERROR-- While retriving SystemBankBranch");
			}
		}
		return resultList;
	}

	public SearchResult getSystemBranchList(String branchCode,String branchName,String state,String city) {
		ArrayList resultList = new ArrayList();
		try{
			String query = "SELECT FROM "+ISystemBankBranchDao.ACTUAL_SYSTEM_BANK_BRANCH_NAME +" where DEPRECATED = 'N' "  ;
			
			if(branchCode != null && !branchCode.trim().equals("")){
				query = query + " and Upper(SYSTEM_BANK_BRANCH_CODE) like Upper ('"+branchCode+"%') ";
			}
			if(branchName!=null && !branchName.trim().equals("")){
				query = query + " and Upper (SYSTEM_BANK_BRANCH_NAME) like Upper ('"+branchName+"%') ";
			}
			if(state != null && !state.trim().equals("")){
				query = query + " and STATE  IN (FROM actualState WHERE Upper (state_name) like Upper ('"+state+"%')) ";
			}
			if(city!=null && !city.trim().equals("")){
				query = query + " and CITY_TOWN IN (FROM actualCity WHERE Upper (city_name) LIKE Upper('"+city+"%')) ";
			}
			
			resultList = (ArrayList) getHibernateTemplate().find(query);
		}catch (Exception e) {
			DefaultLogger.error(this, "############# error in getBranchList ",e);
			e.printStackTrace();
			throw new SystemBankException("Unable to Load  bank branch List");
		}
		return new SearchResult(0, resultList.size(), resultList.size(), resultList);
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) { 
		OBCMSTrxValue obCMSTrxValue = new OBCMSTrxValue();
		String query = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_SYS_BRANCH' AND obcms.transactionID='"+obFileMapperMaster.getTransId()+"'";
		List list = getHibernateTemplate().find(query);
		obCMSTrxValue = (OBCMSTrxValue)list.get(0);
		getHibernateTemplate().flush();
		getHibernateTemplate().delete(obCMSTrxValue);
	}

	public boolean isHubValid(String linkedHub) {
		String query =  " SELECT FROM "+ ISystemBankBranchDao.ACTUAL_SYSTEM_BANK_BRANCH_NAME +" where is_hub='Y' AND STATUS!='INACTIVE' AND SYSTEM_BANK_BRANCH_CODE = '"+ linkedHub +"'";
		List list = (ArrayList) getHibernateTemplate().find(query);
		if( list.size() > 0 )
			return false;
		
		return true;
	}
	
	public String isCodeExisting(String countryCode, String regionCode,String stateCode, String cityCode)throws SystemBankBranchException, TrxParameterException,TransactionException {
		
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
	
	
	
	
	
	
public long getSystemBankByCode(String bankCode)throws Exception {
		
		long id=0L;
		String sql = "SELECT ID FROM CMS_SYSTEM_BANK WHERE SYSTEM_BANK_CODE='"+bankCode+"'";
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