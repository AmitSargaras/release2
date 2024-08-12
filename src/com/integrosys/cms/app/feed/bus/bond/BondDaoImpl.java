package com.integrosys.cms.app.feed.bus.bond;

import java.io.Serializable;
import java.math.BigDecimal;
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

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.feed.bus.forex.ForexFeedGroupException;
import com.integrosys.cms.app.feed.bus.forex.IForexDao;
import com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.batch.IncompleteBatchJobException;

/**
 * Implementation of {@link IBondDao} using Hibernate
 * 
 * @author Chong Jun Yong
 * @since 1.1
 */
public class BondDaoImpl extends HibernateDaoSupport implements IBondDao {

	public IBondFeedEntry createBondFeedEntry(String entityName, IBondFeedEntry bondFeedEntry) {
		Long key = (Long) getHibernateTemplate().save(entityName, bondFeedEntry);

		return (IBondFeedEntry) getHibernateTemplate().get(entityName, key);
	}

	public IBondFeedGroup createBondFeedGroup(String entityName, IBondFeedGroup bondFeedGroup) {
		Long key = (Long) getHibernateTemplate().save(entityName, bondFeedGroup);

		return (IBondFeedGroup) getHibernateTemplate().get(entityName, key);
	}

	public void deleteBondFeedEntry(String entityName, IBondFeedEntry bondFeedEntry) {
		getHibernateTemplate().delete(entityName, bondFeedEntry);
	}

	public void deleteBondFeedGroup(String entityName, IBondFeedGroup bondFeedGroup) {
		getHibernateTemplate().delete(entityName, bondFeedGroup);
	}

	public IBondFeedEntry getBondFeedEntryByPrimaryKey(String entityName, Serializable key) {
		return (IBondFeedEntry) getHibernateTemplate().get(entityName, key);
	}

	public IBondFeedGroup getBondFeedGroupByGroupType(String entityName, String groupType) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("type", groupType));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList.isEmpty()) {
			return null;
		}

		return (IBondFeedGroup) resultList.get(0);
	}

	public IBondFeedEntry getBondFeedEntryByRic(String entityName, String ric) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("ric", ric));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList.isEmpty()) {
			return null;
		}

		return (IBondFeedEntry) resultList.get(0);
	}

	public IBondFeedGroup getBondFeedGroupByPrimaryKey(String entityName, Serializable key) {
		return (IBondFeedGroup) getHibernateTemplate().get(entityName, key);
	}

	public IBondFeedEntry updateBondFeedEntry(String entityName, IBondFeedEntry bondFeedEntry) {
		getHibernateTemplate().update(entityName, bondFeedEntry);

		return (IBondFeedEntry) getHibernateTemplate().get(entityName, new Long(bondFeedEntry.getBondFeedEntryID()));
	}

	public IBondFeedGroup updateBondFeedGroup(String entityName, IBondFeedGroup bondFeedGroup) {
		getHibernateTemplate().update(entityName, bondFeedGroup);

		return (IBondFeedGroup) getHibernateTemplate().get(entityName, new Long(bondFeedGroup.getBondFeedGroupID()));
	}
	
	public IBondFeedEntry getBondFeedEntry (String bondCode) throws BondFeedEntryException

	{
		IBondFeedEntry  accIBondFeedEntry = null;
		//currencyCode = "MYR";
		try {
			List resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+IBondDao.ACTUAL_BOND_FEED_ENTRY_ENTITY_NAME +" where BOND_CODE = '"+bondCode+"'");
			if(!resultList.isEmpty())
			{
			Iterator IBondFeedEntryIt = resultList.iterator();
			while(IBondFeedEntryIt.hasNext())
			{
				accIBondFeedEntry = (IBondFeedEntry)IBondFeedEntryIt.next();
				break;
			}
			}
		} catch (Exception e) {
			// TODO: handle exception
		//System.out.println(e);
	}
		if(accIBondFeedEntry != null)
		{
		return accIBondFeedEntry;
		}
		else
		{
			return null;			
		}
	}
	
//--------------------File Upload
	
	/**
	 * @return Maker check if previous upload is pending.
	 */
	public boolean isPrevFileUploadPending() {

		String rowCount = "FROM com.integrosys.cms.app.transaction.OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_BOND'";
		List rowList = getHibernateTemplate().find(rowCount);
		if(rowList.size()>0){
			String sqlQuery = "FROM com.integrosys.cms.app.transaction.OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_BOND' AND status != 'ACTIVE'";
			List bondFeedEntryList = getHibernateTemplate().find(sqlQuery);
			if(bondFeedEntryList.size()>0){
				return true;
			}
		}
		return false;
	}
	
	
	public List getAllStageBondFeedEntry(String searchBy, String login) {
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
			String query = "SELECT FROM "+IBondDao.STAGE_BOND_FEED_ENTRY_ENTITY_NAME+" WHERE FEED_GROUP_ID = ("+strId+")";
			try {
				if (searchByValue.trim() != "") {
					resultList = getHibernateTemplate().find(query);
				}
	
			} catch (Exception e) {
				throw new BondFeedEntryException("ERROR-- While retriving RelationshipMgr");
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
	public IFileMapperId insertBondFeedEntry(String entityName,
			IFileMapperId fileId, IBondFeedGroupTrxValue trxValue)
			throws BondFeedEntryException {
		
		if(!(entityName==null|| fileId==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, fileId);
			fileId.setId(key.longValue());
			return fileId;
		}else{
			throw new BondFeedEntryException("ERROR- Entity name or key is null ");
		}	
	}
	
	/**
	 * @return integer (record count of inserted record)
	 * @param FileMapperMaster
	 * @param userName  
	 * @param result of upload files 
	 * This method Creates FileMapperMaster Object
	 */
	public int insertBondFeedEntry(IFileMapperMaster fileMapperMaster, String userName, ArrayList result) {
		// TODO Auto-generated method stub
		int noOfRecInserted = 0;
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		Long key=null;
		if (result == null || result.size() <= 0) {
			throw new IncompleteBatchJobException(
					"Data to be instered to DB is null or empty");
		}
		try {
			Long stageBondFeedGroupkey= new Long(0);
			Set setChild = new HashSet();
			OBBondFeedGroup obBondFeedGroup = null;
			int index;
			for (index = 0; index < result.size(); index++) {
				HashMap eachDataMap = (HashMap) result.get(index);
				String entityName="actualBondFeedEntry";
				String bondCode = (String)eachDataMap.get("BOND_CODE");
				List resultList = null;
				OBBondFeedEntry obBondFeedEntry = new OBBondFeedEntry();
				OBBondFeedEntry BondFeedEntry = new OBBondFeedEntry();
				obBondFeedGroup =  new OBBondFeedGroup();
				if(!(entityName==null|| bondCode==null)){
				resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+entityName +" where UPPER(BOND_CODE) = '"+bondCode.toUpperCase()+"'");
				if (!resultList.isEmpty()) {
				
				BondFeedEntry = (OBBondFeedEntry)resultList.get(0);
				obBondFeedEntry.setBondFeedEntryID(0);
				obBondFeedEntry.setBondCode((String)eachDataMap.get("BOND_CODE"));
				obBondFeedEntry.setName((String)eachDataMap.get("NAME"));
				obBondFeedEntry.setIsinCode((String)eachDataMap.get("ISIN_CODE"));
				obBondFeedEntry.setIssueDate(df.parse((String) eachDataMap.get("DATE_LAUNCHED").toString()));
				obBondFeedEntry.setMaturityDate(df.parse(eachDataMap.get("MATURITY_DATE").toString()));
				obBondFeedEntry.setUnitPrice(Double.parseDouble((String) eachDataMap.get("UNIT_PRICE")));
				obBondFeedEntry.setRating((String) eachDataMap.get("RATING"));
				obBondFeedEntry.setCouponRate(Double.parseDouble((String) eachDataMap.get("COUPON_RATE")));
				obBondFeedEntry.setLastUpdateDate(new Date());
				
				setChild.add(obBondFeedEntry);
			    
				//key = (Long) getHibernateTemplate().save("stageBondFeedEntry", obBondFeedEntry);				
				}//inner if
			 }//if
			}//for
			/***********************/
			//create FileMapperMaster
			if(index == result.size() && index!=0)
			{
			obBondFeedGroup.setType("BOND");
			obBondFeedGroup.setFeedEntriesSet(setChild);
			key = (Long)getHibernateTemplate().save("stageBondFeedGroup", obBondFeedGroup);	
			
			IFileMapperMaster fileMapper = new OBFileMapperMaster();
			fileMapper.setFileId(fileMapperMaster.getFileId());
			fileMapper.setSysId(key.longValue());
			fileMapper.setTransId(fileMapperMaster.getTransId());
			if(!(fileMapperMaster==null)){
				key = (Long) getHibernateTemplate().save("fileMapper", fileMapper);
				getHibernateTemplate().get("fileMapper", key);
				}else{
					throw new BondFeedEntryException("ERROR- Entity name or key is null ");
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
	throws BondFeedEntryException {
			// TODO Auto-generated method stub
			if(!(entityName==null|| fileId==null)){
		
		Long key = (Long) getHibernateTemplate().save(entityName, fileId);
		fileId.setId(key.longValue());
		return fileId;
		}else{
			throw new BondFeedEntryException("ERROR- Entity name or key is null ");
		}
	}
	
	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId getInsertFileList(String entityName, Serializable key)
	throws BondFeedEntryException {
		if(!(entityName==null|| key==null)){
			
			return (IFileMapperId) getHibernateTemplate().get(entityName, key);
			}else{
				throw new BondFeedEntryException("ERROR-- Entity Name Or Key is null");
		}
	}
	

	/**
	 * @return BondFeedEntry Object
	 * @param Entity Name
	 * @param sysId  
	 * This method Creates BondFeedEntry Object
	 */
	public List insertActualBondFeedEntry(String BondFeedGroupID)
	throws BondFeedEntryException {
		Map parametersMap = new HashMap();
		parametersMap.put("BondFeedGroupID", new Long(BondFeedGroupID));
		
		//DetachedCriteria criteria = DetachedCriteria.forEntityName("stageBondFeedGroup").add(Restrictions.allEq(parametersMap));
		//List BondFeedGroupList = getHibernateTemplate().findByCriteria(criteria);
		IBondFeedGroup bondFeedGroup = (IBondFeedGroup) getHibernateTemplate().get("stageBondFeedGroup", new Long(BondFeedGroupID));
		Set bondFeedEntrySet = bondFeedGroup.getFeedEntriesSet() ;
		
		List bondFeedEntryList = new ArrayList(bondFeedEntrySet);
		
		return bondFeedEntryList;
		}
	/**
	 * @return BondFeedEntry Object
	 * @param Entity Name
	 * @param BondFeedEntry Object  
	 * This method Creates BondFeedEntry Object
	 */
	public IBondFeedGroup insertBondFeedEntry(String entityName,
			IBondFeedGroup bondFeedEntry)
			throws BondFeedEntryException {
		if(!(entityName==null|| bondFeedEntry==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, bondFeedEntry);
			bondFeedEntry.setBondFeedGroupID(key.longValue());
			return bondFeedEntry;
			}else{
				throw new BondFeedEntryException("ERROR- Entity name or key is null ");
			}
	}
	public void updateBondFeedEntryItem(String entityName ,List stgBondFeedEntryList) throws BondFeedGroupException
	{
		IBondFeedEntry  stgBondFeedEntry = null;
		Iterator bondFeedEntryIt = stgBondFeedEntryList.iterator();
		while(bondFeedEntryIt.hasNext())
		{
			stgBondFeedEntry = (IBondFeedEntry)bondFeedEntryIt.next();
			//entityName is actual
			List actBondFeedEntryList = getHibernateTemplate().find("SELECT FROM "+entityName +" where UPPER(BOND_CODE) = '"+stgBondFeedEntry.getBondCode().toUpperCase()+"'");
			 IBondFeedEntry  actBondFeedEntry = (IBondFeedEntry)actBondFeedEntryList.get(0);
			 
			 actBondFeedEntry.setBondCode(stgBondFeedEntry.getBondCode());
			 actBondFeedEntry.setUnitPrice(stgBondFeedEntry.getUnitPrice());
			 actBondFeedEntry.setName(stgBondFeedEntry.getName());
			 actBondFeedEntry.setIsinCode(stgBondFeedEntry.getIsinCode());
			 actBondFeedEntry.setIssueDate(stgBondFeedEntry.getIssueDate());
			 actBondFeedEntry.setMaturityDate(stgBondFeedEntry.getMaturityDate());
			 actBondFeedEntry.setRating(stgBondFeedEntry.getRating());
			 actBondFeedEntry.setCouponRate(stgBondFeedEntry.getCouponRate());
			 actBondFeedEntry.setLastUpdateDate(stgBondFeedEntry.getLastUpdateDate());
			 updateBondFeedEntry(entityName,  actBondFeedEntry);
		}		
	}
	
	public boolean isBondCodeExist(String entityName,List bondCodeList) throws ForexFeedGroupException
	{
		boolean isBondCodeExist = false;
		Iterator itbondCodeList  = bondCodeList.iterator();
		while(itbondCodeList.hasNext()){
			List resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+entityName +" where UPPER(BOND_CODE) = '"+itbondCodeList.next().toString().toUpperCase()+"'");
			if (resultList.isEmpty()) {
				isBondCodeExist = false;
			}else{
			isBondCodeExist = true;
			break;
			}
		}
		return isBondCodeExist;	
	}
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws ForexFeedGroupException, TrxParameterException,TransactionException {
		OBCMSTrxValue obCMSTrxValue = new OBCMSTrxValue();
		String query = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_BOND' AND obcms.transactionID='"+obFileMapperMaster.getTransId()+"'";
		List list = getHibernateTemplate().find(query);
		obCMSTrxValue = (OBCMSTrxValue)list.get(0);
		getHibernateTemplate().flush();
		getHibernateTemplate().delete(obCMSTrxValue);			
	}
	
	//----------------------------------End file upload-------------------//
	
	
	public boolean isExistBondCode(String entityName, String bondCode) throws ForexFeedGroupException
	{
		boolean isStockCodeExist = false;
		List resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+entityName +" where BOND_CODE = '"+bondCode+"'");
		if(resultList.size() > 0)
			return true;
		else 
			return false;	
	}
}
