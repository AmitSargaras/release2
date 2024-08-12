package com.integrosys.cms.app.feed.bus.mutualfunds;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.forex.ForexFeedGroupException;
import com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.batch.IncompleteBatchJobException;

/**
 * Implementation of {@link IMutualFundsDao} using Hibernate
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class MutualFundsDaoImpl extends HibernateDaoSupport implements IMutualFundsDao {

	public IMutualFundsFeedEntry createMutualFundsFeedEntry(String entityName, IMutualFundsFeedEntry mutualFundsFeedEntry) {
		Long key = (Long) getHibernateTemplate().save(entityName, mutualFundsFeedEntry);

		return (IMutualFundsFeedEntry) getHibernateTemplate().get(entityName, key);
	}

	public IMutualFundsFeedGroup createMutualFundsFeedGroup(String entityName, IMutualFundsFeedGroup mutualFundsFeedGroup) {
		Long key = (Long) getHibernateTemplate().save(entityName, mutualFundsFeedGroup);

		return (IMutualFundsFeedGroup) getHibernateTemplate().get(entityName, key);
	}

	public void deleteMutualFundsFeedEntry(String entityName, IMutualFundsFeedEntry mutualFundsFeedEntry) {
		getHibernateTemplate().delete(entityName, mutualFundsFeedEntry);
	}

	public void deleteMutualFundsFeedGroup(String entityName, IMutualFundsFeedGroup mutualFundsFeedGroup) {
		getHibernateTemplate().delete(entityName, mutualFundsFeedGroup);
	}

	public IMutualFundsFeedEntry getMutualFundsFeedEntryByPrimaryKey(String entityName, Serializable key) {
		return (IMutualFundsFeedEntry) getHibernateTemplate().get(entityName, key);
	}

	public IMutualFundsFeedGroup getMutualFundsFeedGroupByGroupType(String entityName, String groupType) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("type", groupType));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList.isEmpty()) {
			return null;
		}

		return (IMutualFundsFeedGroup) resultList.get(0);
	}

	public IMutualFundsFeedEntry getMutualFundsFeedEntryByRic(String entityName, String ric) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("ric", ric));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList.isEmpty()) {
			return null;
		}

		return (IMutualFundsFeedEntry) resultList.get(0);
	}

	public IMutualFundsFeedGroup getMutualFundsFeedGroupByPrimaryKey(String entityName, Serializable key) {
		return (IMutualFundsFeedGroup) getHibernateTemplate().get(entityName, key);
	}

	public IMutualFundsFeedEntry updateMutualFundsFeedEntry(String entityName, IMutualFundsFeedEntry mutualFundsFeedEntry) {
		getHibernateTemplate().update(entityName, mutualFundsFeedEntry);

		return (IMutualFundsFeedEntry) getHibernateTemplate().get(entityName, new Long(mutualFundsFeedEntry.getMutualFundsFeedEntryID()));
	}

	public IMutualFundsFeedGroup updateMutualFundsFeedGroup(String entityName, IMutualFundsFeedGroup mutualFundsFeedGroup) {
		getHibernateTemplate().update(entityName, mutualFundsFeedGroup);

		return (IMutualFundsFeedGroup) getHibernateTemplate().get(entityName, new Long(mutualFundsFeedGroup.getMutualFundsFeedGroupID()));
	}

	
	public IMutualFundsFeedEntry getIMutualFundsFeed (String entityName,String schemeCode) throws MutualFundsFeedEntryException

	{
		IMutualFundsFeedEntry  accIMutualFundsFeedEntry = null;
		//currencyCode = "MYR";
		try {
			List resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+entityName +" where SCHEME_CODE = '"+schemeCode+"'");
			if(!resultList.isEmpty())
			{
			Iterator IMutualFundsFeedEntryIt = resultList.iterator();
			while(IMutualFundsFeedEntryIt.hasNext())
			{
				accIMutualFundsFeedEntry = (IMutualFundsFeedEntry)IMutualFundsFeedEntryIt.next();
				break;
			}
			}
		} catch (Exception e) {
			// TODO: handle exception
		//System.out.println(e);
	}
		if(accIMutualFundsFeedEntry != null)
		{
		return accIMutualFundsFeedEntry;
		}
		else
		{
			return null;			
		}
	}

	

	
	public boolean isValidSchemeCode(String schemeCode){
		boolean chkIsExists = false;
		ArrayList resultList =null;
		
		if(!(schemeCode==null && schemeCode.trim()=="")){
			String query = "SELECT FROM "+IMutualFundsDao.ACTUAL_MUTUAL_FUNDS_FEED_ENTRY_ENTITY_NAME+" WHERE  SCHEME_CODE like '"+schemeCode.toUpperCase()+"'";
			 resultList = (ArrayList) getHibernateTemplate().find(query);
			 
			 if(resultList.size()>0){
					String query1 = "SELECT FROM "+IMutualFundsDao.STAGE_MUTUAL_FUNDS_FEED_ENTRY_ENTITY_NAME+" WHERE SCHEME_CODE like '"+schemeCode.toUpperCase()+"'";
					ArrayList resultList1 = (ArrayList) getHibernateTemplate().find(query1);
					if(resultList1.size()>0)
						chkIsExists = true;
					else
						chkIsExists = false;
				}	
				else 
					chkIsExists = false;
		}	 
		return chkIsExists;
	}
//--------------------File Upload
	
	/**
	 * @return Maker check if previous upload is pending.
	 */
	public boolean isPrevFileUploadPending() {

		String rowCount = "FROM com.integrosys.cms.app.transaction.OBCMSTrxValue obcms WHERE obcms.transactionType= '"+ICMSConstant.INSTANCE_INSERT_MUTUAL_FUNDS_FEED_GROUP+"'";
		List rowList = getHibernateTemplate().find(rowCount);
		if(rowList.size()>0){
			String sqlQuery = "FROM com.integrosys.cms.app.transaction.OBCMSTrxValue obcms WHERE obcms.transactionType= '"+ICMSConstant.INSTANCE_INSERT_MUTUAL_FUNDS_FEED_GROUP+"' AND status != 'ACTIVE'";
			List mutualfundsFeedEntryList = getHibernateTemplate().find(sqlQuery);
			if(mutualfundsFeedEntryList.size()>0){
				return true;
			}
		}
		return false;
	}
	
	
	public List getAllStageMutualFundsFeedEntry(String searchBy, String login) {
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
			String query = "SELECT FROM "+IMutualFundsDao.STAGE_MUTUAL_FUNDS_FEED_ENTRY_ENTITY_NAME+" WHERE FEED_GROUP_ID = ("+strId+")";
			try {
				if (searchByValue.trim() != "") {
					resultList = getHibernateTemplate().find(query);
				}
	
			} catch (Exception e) {
				throw new MutualFundsFeedEntryException("ERROR-- While retriving RelationshipMgr");
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
	public IFileMapperId insertMutualFundsFeedEntry(String entityName,
			IFileMapperId fileId, IMutualFundsFeedGroupTrxValue trxValue)
			throws MutualFundsFeedEntryException {
		
		if(!(entityName==null|| fileId==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, fileId);
			fileId.setId(key.longValue());
			return fileId;
		}else{
			throw new MutualFundsFeedEntryException("ERROR- Entity name or key is null ");
		}	
	}
	
	/**
	 * @return integer (record count of inserted record)
	 * @param FileMapperMaster
	 * @param userName  
	 * @param result of upload files 
	 * This method Creates FileMapperMaster Object
	 */
	public int insertMutualFundsFeedEntry(IFileMapperMaster fileMapperMaster, String userName, ArrayList result) {
		// TODO Auto-generated method stub
		int noOfRecInserted = 0;
		//DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		Long key=null;
		if (result == null || result.size() <= 0) {
			throw new IncompleteBatchJobException(
					"Data to be instered to DB is null or empty");
		}
		try {
			Long stageMutualFundsFeedGroupkey= new Long(0);
			Set setChild = new HashSet();
			OBMutualFundsFeedGroup obMutualFundsFeedGroup = null;
			int index;
			for (index = 0; index < result.size(); index++) {
				HashMap eachDataMap = (HashMap) result.get(index);
				String entityName="actualMutualFundsFeedEntry";
				String schemeCode = (String)eachDataMap.get("SCHEME_CODE");
				List resultList = null;
				OBMutualFundsFeedEntry obMutualFundsFeedEntry = new OBMutualFundsFeedEntry();
				OBMutualFundsFeedEntry MutualFundsFeedEntry = new OBMutualFundsFeedEntry();
				obMutualFundsFeedGroup =  new OBMutualFundsFeedGroup();
				if(!(entityName==null|| schemeCode==null)){
				resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+entityName +" where UPPER(SCHEME_CODE) = '"+schemeCode.toUpperCase()+"'");
				if (!resultList.isEmpty()) {
				
				MutualFundsFeedEntry = (OBMutualFundsFeedEntry)resultList.get(0);
				obMutualFundsFeedEntry.setMutualFundsFeedEntryID(0);
				obMutualFundsFeedEntry.setSchemeCode((String)eachDataMap.get("SCHEME_CODE"));
				obMutualFundsFeedEntry.setSchemeName((String)eachDataMap.get("SCHEME_NAME"));
				obMutualFundsFeedEntry.setSchemeType((String)eachDataMap.get("SCHEME_TYPE"));
				
				if ((eachDataMap.get("START_DATE") != null) && !eachDataMap.get("START_DATE").equals("")) {
					obMutualFundsFeedEntry.setStartDate(df.parse((String) eachDataMap.get("START_DATE")));
				}
				if ((eachDataMap.get("EXPIRY_DATE") != null) && !eachDataMap.get("EXPIRY_DATE").equals("")) {
					obMutualFundsFeedEntry.setExpiryDate(df.parse((String)eachDataMap.get("EXPIRY_DATE")));
				}
				obMutualFundsFeedEntry.setLastUpdatedDate(new Date());
				obMutualFundsFeedEntry.setCurrentNAV(Double.parseDouble((String) eachDataMap.get("CURRENT_NAV")));
				
				setChild.add(obMutualFundsFeedEntry);
			    
				//key = (Long) getHibernateTemplate().save("stageMutualFundsFeedEntry", obMutualFundsFeedEntry);				
				}//inner if
			 }//if
			}//for
			/***********************/
			//create FileMapperMaster
			if(index == result.size() && index!=0)
			{
			obMutualFundsFeedGroup.setType("MUTUAL_FUND");
			obMutualFundsFeedGroup.setFeedEntriesSet(setChild);
			key = (Long)getHibernateTemplate().save("stageMutualFundsFeedGroup", obMutualFundsFeedGroup);	
			
			IFileMapperMaster fileMapper = new OBFileMapperMaster();
			fileMapper.setFileId(fileMapperMaster.getFileId());
			fileMapper.setSysId(key.longValue());
			fileMapper.setTransId(fileMapperMaster.getTransId());
			if(!(fileMapperMaster==null)){
				key = (Long) getHibernateTemplate().save("fileMapper", fileMapper);
				getHibernateTemplate().get("fileMapper", key);
				}else{
					throw new MutualFundsFeedEntryException("ERROR- Entity name or key is null ");
				}
			noOfRecInserted++;
			}
			/***********************/
			
			
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
	throws MutualFundsFeedEntryException {
			// TODO Auto-generated method stub
			if(!(entityName==null|| fileId==null)){
		
		Long key = (Long) getHibernateTemplate().save(entityName, fileId);
		fileId.setId(key.longValue());
		return fileId;
		}else{
			throw new MutualFundsFeedEntryException("ERROR- Entity name or key is null ");
		}
	}
	
	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId getInsertFileList(String entityName, Serializable key)
	throws MutualFundsFeedEntryException {
		if(!(entityName==null|| key==null)){
			
			return (IFileMapperId) getHibernateTemplate().get(entityName, key);
			}else{
				throw new MutualFundsFeedEntryException("ERROR-- Entity Name Or Key is null");
		}
	}
	

	/**
	 * @return MutualFundsFeedEntry Object
	 * @param Entity Name
	 * @param sysId  
	 * This method Creates MutualFundsFeedEntry Object
	 */
	public List insertActualMutualFundsFeedEntry(String MutualFundsFeedGroupID)
	throws MutualFundsFeedEntryException {
		Map parametersMap = new HashMap();
		parametersMap.put("MutualFundsFeedGroupID", new Long(MutualFundsFeedGroupID));
		
		//DetachedCriteria criteria = DetachedCriteria.forEntityName("stageMutualFundsFeedGroup").add(Restrictions.allEq(parametersMap));
		//List MutualFundsFeedGroupList = getHibernateTemplate().findByCriteria(criteria);
		IMutualFundsFeedGroup mutualfundsFeedGroup = (IMutualFundsFeedGroup) getHibernateTemplate().get("stageMutualFundsFeedGroup", new Long(MutualFundsFeedGroupID));
		Set mutualfundsFeedEntrySet = mutualfundsFeedGroup.getFeedEntriesSet() ;
		
		List mutualfundsFeedEntryList = new ArrayList(mutualfundsFeedEntrySet);
		
		return mutualfundsFeedEntryList;
		}
	/**
	 * @return MutualFundsFeedEntry Object
	 * @param Entity Name
	 * @param MutualFundsFeedEntry Object  
	 * This method Creates MutualFundsFeedEntry Object
	 */
	public IMutualFundsFeedGroup insertMutualFundsFeedEntry(String entityName,
			IMutualFundsFeedGroup mutualfundsFeedEntry)
			throws MutualFundsFeedEntryException {
		if(!(entityName==null|| mutualfundsFeedEntry==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, mutualfundsFeedEntry);
			mutualfundsFeedEntry.setMutualFundsFeedGroupID(key.longValue());
			return mutualfundsFeedEntry;
			}else{
				throw new MutualFundsFeedEntryException("ERROR- Entity name or key is null ");
			}
	}
	public void updateMutualFundsFeedEntryItem(String entityName ,List stgMutualFundsFeedEntryList) throws MutualFundsFeedGroupException
	{
		IMutualFundsFeedEntry  stgMutualFundsFeedEntry = null;
		Iterator mutualfundsFeedEntryIt = stgMutualFundsFeedEntryList.iterator();
		while(mutualfundsFeedEntryIt.hasNext())
		{
			stgMutualFundsFeedEntry = (IMutualFundsFeedEntry)mutualfundsFeedEntryIt.next();
			//entityName is actual
			List actMutualFundsFeedEntryList = getHibernateTemplate().find("SELECT FROM "+entityName +" where UPPER(SCHEME_CODE) = '"+stgMutualFundsFeedEntry.getSchemeCode().toUpperCase()+"'");
			 IMutualFundsFeedEntry  actMutualFundsFeedEntry = (IMutualFundsFeedEntry)actMutualFundsFeedEntryList.get(0);
			 
			 actMutualFundsFeedEntry.setSchemeCode(stgMutualFundsFeedEntry.getSchemeCode());
			 actMutualFundsFeedEntry.setSchemeName(stgMutualFundsFeedEntry.getSchemeName());
			 actMutualFundsFeedEntry.setSchemeType(stgMutualFundsFeedEntry.getSchemeType());
			 actMutualFundsFeedEntry.setStartDate(stgMutualFundsFeedEntry.getStartDate());
			 actMutualFundsFeedEntry.setExpiryDate(stgMutualFundsFeedEntry.getExpiryDate());
			 actMutualFundsFeedEntry.setLastUpdatedDate(stgMutualFundsFeedEntry.getLastUpdatedDate());
			 actMutualFundsFeedEntry.setCurrentNAV(stgMutualFundsFeedEntry.getCurrentNAV());
			 
			 updateMutualFundsFeedEntry(entityName,  actMutualFundsFeedEntry);
		}		
	}
	
	public boolean isMutualFundsCodeExist(String entityName,List schemeCodeList) throws ForexFeedGroupException
	{
		boolean isMutualFundsCodeExist = false;
		Iterator itschemeCodeList  = schemeCodeList.iterator();
		while(itschemeCodeList.hasNext()){
		List resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+entityName +" where UPPER(SCHEME_CODE) = '"+itschemeCodeList.next().toString().toUpperCase()+"'");
		if (resultList.isEmpty()) {
			isMutualFundsCodeExist = false;
		}else{
			isMutualFundsCodeExist = true;
		break;
		}
	}
	return isMutualFundsCodeExist;	
	}
	
	/**
	 * @return Maker delete if error.  //A govind 300811
	 */
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) { 
		OBCMSTrxValue obCMSTrxValue = new OBCMSTrxValue();
		String query = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType= '"+ICMSConstant.INSTANCE_INSERT_MUTUAL_FUNDS_FEED_GROUP+"' AND obcms.transactionID='"+obFileMapperMaster.getTransId()+"'";
		List list = getHibernateTemplate().find(query);
		obCMSTrxValue = (OBCMSTrxValue)list.get(0);
		getHibernateTemplate().flush();
		getHibernateTemplate().delete(obCMSTrxValue);
	}
	//----------------------------------End file upload-------------------//

}
