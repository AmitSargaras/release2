package com.integrosys.cms.app.feed.bus.forex;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
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
import java.sql.SQLException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.batch.IncompleteBatchJobException;
import com.integrosys.cms.batch.forex.OBForex;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Implementation of {@link IForexDao}
 * 
 * @author Chong Jun Yong
 * @since 1.1
 */
public class ForexDaoImpl extends HibernateDaoSupport implements IForexDao {
	private DBUtil dbUtil = null;
	
	private static final String RETRIVE_CURRENCY="select BUY_RATE from cms_forex where BUY_CURRENCY=";

	public IForexFeedEntry createForexFeedEntry(String entityName, IForexFeedEntry forexFeedEntry) {
		Long key = (Long) getHibernateTemplate().save(entityName, forexFeedEntry);

		return (IForexFeedEntry) getHibernateTemplate().get(entityName, key);
	}

	public IForexFeedGroup createForexFeedGroup(String entityName, IForexFeedGroup forexFeedGroup) {
		Long key = (Long) getHibernateTemplate().save(entityName, forexFeedGroup);

		return (IForexFeedGroup) getHibernateTemplate().get(entityName, key);
	}

	public void deleteForexFeedEntry(String entityName, IForexFeedEntry forexFeedEntry) {

	}

	public void deleteForexFeedGroup(String entityName, IForexFeedGroup forexFeedGroup) {

	}

	public IForexFeedEntry getForexFeedEntryByPrimaryKey(String entityName, Serializable key) {
		return (IForexFeedEntry) getHibernateTemplate().get(entityName, key);
	}

	public IForexFeedGroup getForexFeedGroupByPrimaryKey(String entityName, Serializable key) {
		return (IForexFeedGroup) getHibernateTemplate().get(entityName, key);
	}

	public IForexFeedEntry updateForexFeedEntry(String entityName, IForexFeedEntry forexFeedEntry) {
		getHibernateTemplate().update(entityName, forexFeedEntry);

		return (IForexFeedEntry) getHibernateTemplate()
				.load(entityName, new Long(forexFeedEntry.getForexFeedEntryID()));
	}

	public IForexFeedGroup updateForexFeedGroup(String entityName, IForexFeedGroup forexFeedGroup) {
		getHibernateTemplate().update(entityName, forexFeedGroup);

		return (IForexFeedGroup) getHibernateTemplate().get(entityName, new Long(forexFeedGroup.getForexFeedGroupID()));
	}

	public IForexFeedGroup getForexFeedGroupByGroupType(String entityName, String groupType) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("type", groupType));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList.isEmpty()) {
			return null;
		}

		return (IForexFeedGroup) resultList.get(0);
	}

	public IForexFeedEntry getForexFeedByCurrencyCode(String entityName, String currencyCode) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(
				Restrictions.eq("sellCurrency", currencyCode)).addOrder(Order.desc("forexFeedEntryID"));

		List resultList = getHibernateTemplate().findByCriteria(criteria);
		if (resultList.isEmpty()) {
			return null;
		}

		return (IForexFeedEntry) resultList.get(0);
	}
	
	
//--------------------File Upload
	
	/**
	 * @return Maker check if previous upload is pending.
	 */
	public boolean isPrevFileUploadPending() {

		String rowCount = "FROM com.integrosys.cms.app.transaction.OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_FOREX_FEED_GR'";
		List rowList = getHibernateTemplate().find(rowCount);
		if(rowList.size()>0){
			String sqlQuery = "FROM com.integrosys.cms.app.transaction.OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_FOREX_FEED_GR' AND status != 'ACTIVE'";
			List forexFeedEntryList = getHibernateTemplate().find(sqlQuery);
			if(forexFeedEntryList.size()>0){
				return true;
			}
		}
		return false;
	}
	
	
	public List getAllStageForexFeedEntry(String searchBy, String login) {
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
			String query = "SELECT FROM "+IForexDao.STAGE_FOREX_FEED_ENTRY_ENTITY_NAME+" WHERE FEED_GROUP_ID = ("+strId+")";
			try {
				if (searchByValue.trim() != "") {
					resultList = getHibernateTemplate().find(query);
				}
	
			} catch (Exception e) {
				throw new ForexFeedEntryException("ERROR-- While retriving RelationshipMgr");
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
	public IFileMapperId insertForexFeedEntry(String entityName,
			IFileMapperId fileId, IForexFeedGroupTrxValue trxValue)
			throws ForexFeedEntryException {
		
		if(!(entityName==null|| fileId==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, fileId);
			fileId.setId(key.longValue());
			return fileId;
		}else{
			throw new ForexFeedEntryException("ERROR- Entity name or key is null ");
		}	
	}
	
	/**
	 * @return integer (record count of inserted record)
	 * @param FileMapperMaster
	 * @param userName  
	 * @param result of upload files 
	 * This method Creates FileMapperMaster Object
	 */
	public int insertForexFeedEntry(IFileMapperMaster fileMapperMaster, String userName, ArrayList result) {
		// TODO Auto-generated method stub
		int noOfRecInserted = 0;
		DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
		Long key=null;
		if (result == null || result.size() <= 0) {
			throw new IncompleteBatchJobException(
					"Data to be instered to DB is null or empty");
		}
		try {
			Long stageForexFeedGroupkey= new Long(0);
			Set setChild = new HashSet();
			OBForexFeedGroup obForexFeedGroup = null;
			int index;
			for (index = 0; index < result.size(); index++) {
				HashMap eachDataMap = (HashMap) result.get(index);
				String entityName="actualForexFeedEntry";
				String currencyCode = (String)eachDataMap.get("BUY_CURRENCY");
				List resultList = null;
				OBForexFeedEntry obForexFeedEntry = new OBForexFeedEntry();
				OBForexFeedEntry forexFeedEntry = new OBForexFeedEntry();
				obForexFeedGroup =  new OBForexFeedGroup();
				if(!(entityName==null|| currencyCode==null)){
				resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+entityName +" where UPPER(CURRENCY_ISO_CODE) = '"+currencyCode.toUpperCase()+"'");
				if (!resultList.isEmpty()) {
				
				forexFeedEntry = (OBForexFeedEntry)resultList.get(0);
				obForexFeedEntry.setForexFeedEntryID(0);
				obForexFeedEntry.setBuyRate(new BigDecimal((String)eachDataMap.get("BUY_RATE")));
				obForexFeedEntry.setBuyCurrency((String)eachDataMap.get("BUY_CURRENCY"));
				obForexFeedEntry.setSellCurrency(forexFeedEntry.getSellCurrency());
				obForexFeedEntry.setBuyUnit(forexFeedEntry.getBuyUnit());
				obForexFeedEntry.setSellUnit(forexFeedEntry.getSellUnit());
				obForexFeedEntry.setEffectiveDate(new Date());
				obForexFeedEntry.setForexFeedEntryRef(stageForexFeedGroupkey.longValue());
				obForexFeedEntry.setCurrencyIsoCode((String)eachDataMap.get("BUY_CURRENCY"));
				obForexFeedEntry.setRestrictionType((String)eachDataMap.get("RESTRICTION_TYPE"));
				obForexFeedEntry.setSystemCode((String)eachDataMap.get("SYSTEM_CODE")==null?forexFeedEntry.getSystemCode():(String)eachDataMap.get("SYSTEM_CODE"));
				obForexFeedEntry.setCurrencyDescription((String)eachDataMap.get("CURRENCY_DESCRIPTION")==null?forexFeedEntry.getCurrencyDescription():(String)eachDataMap.get("CURRENCY_DESCRIPTION") );
				obForexFeedEntry.setStatus("ENABLE");
				
				//Update few more fields
				obForexFeedEntry.setSellRate(forexFeedEntry.getSellRate());
				obForexFeedEntry.setCpsId(forexFeedEntry.getCpsId());
				
				setChild.add(obForexFeedEntry);
			    
				//key = (Long) getHibernateTemplate().save("stageForexFeedEntry", obForexFeedEntry);				
				}//inner if
			 }//if
			}//for
			/***********************/
			//create FileMapperMaster
			if(index == result.size() && index!=0)
			{
			obForexFeedGroup.setType("FOREX");
			obForexFeedGroup.setFeedEntriesSet(setChild);
			key = (Long)getHibernateTemplate().save("stageForexFeedGroup", obForexFeedGroup);	
			
			IFileMapperMaster fileMapper = new OBFileMapperMaster();
			fileMapper.setFileId(fileMapperMaster.getFileId());
			fileMapper.setSysId(key.longValue());
			fileMapper.setTransId(fileMapperMaster.getTransId());
			if(!(fileMapperMaster==null)){
				key = (Long) getHibernateTemplate().save("fileMapper", fileMapper);
				getHibernateTemplate().get("fileMapper", key);
				}else{
					throw new ForexFeedEntryException("ERROR- Entity name or key is null ");
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
	 * @return HashMap
	 * @param FileMapperMaster
	 * @param userName  
	 * @param result of upload files 
	 * This method Creates FileMapperMaster Object in DB used for autoupload of exchange rate.
	 */
	public HashMap insertForexFeedEntryAuto(IFileMapperMaster fileMapperMaster, String userName, ArrayList result) {
		int noOfRecInserted = 0;
		Long key=null;
		if (result == null || result.size() <= 0) {
			throw new IncompleteBatchJobException(
					"Data to be instered to DB is null or empty");
		}
		HashMap map = new HashMap();
		try {
			
			Long stageForexFeedGroupkey= new Long(0);
			Set setChild = new HashSet();
			OBForexFeedGroup obForexFeedGroup = null;
			int index;
			for (index = 0; index < result.size(); index++) {
				HashMap eachDataMap = (HashMap) result.get(index);
				
				String entityName="actualForexFeedEntry";
				String currencyCode = (String)eachDataMap.get("BUY_CURRENCY");
				List resultList = null;
				OBForexFeedEntry obForexFeedEntry = new OBForexFeedEntry();
				OBForexFeedEntry forexFeedEntry = new OBForexFeedEntry();
				obForexFeedGroup =  new OBForexFeedGroup();
				if(!(entityName==null|| currencyCode==null)){
				resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+entityName +" where UPPER(CURRENCY_ISO_CODE) = '"+currencyCode.toUpperCase()+"'");
				if (!resultList.isEmpty()) {
				forexFeedEntry = (OBForexFeedEntry)resultList.get(0);
				map.put((String)eachDataMap.get("BUY_CURRENCY"), forexFeedEntry.getBuyRate());
				obForexFeedEntry.setForexFeedEntryID(0);
				obForexFeedEntry.setBuyRate(new BigDecimal((String)eachDataMap.get("BUY_RATE")));
				obForexFeedEntry.setBuyCurrency((String)eachDataMap.get("BUY_CURRENCY"));
				obForexFeedEntry.setSellCurrency(forexFeedEntry.getSellCurrency());
				obForexFeedEntry.setBuyUnit(forexFeedEntry.getBuyUnit());
				obForexFeedEntry.setSellUnit(forexFeedEntry.getSellUnit());
				String sDate1=(String)eachDataMap.get("EFFECTIVE_DATE");
				Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
				obForexFeedEntry.setEffectiveDate(date1);
				obForexFeedEntry.setForexFeedEntryRef(stageForexFeedGroupkey.longValue());
				obForexFeedEntry.setCurrencyIsoCode((String)eachDataMap.get("BUY_CURRENCY"));
				obForexFeedEntry.setRestrictionType((String)eachDataMap.get("RESTRICTION_TYPE"));
				obForexFeedEntry.setSystemCode((String)eachDataMap.get("SYSTEM_CODE")==null?forexFeedEntry.getSystemCode():(String)eachDataMap.get("SYSTEM_CODE"));
				obForexFeedEntry.setCurrencyDescription((String)eachDataMap.get("CURRENCY_DESCRIPTION")==null?forexFeedEntry.getCurrencyDescription():(String)eachDataMap.get("CURRENCY_DESCRIPTION") );
				obForexFeedEntry.setStatus("ENABLE");
				
				//Update few more fields
				obForexFeedEntry.setSellRate(forexFeedEntry.getSellRate());
				obForexFeedEntry.setCpsId(forexFeedEntry.getCpsId());
				setChild.add(obForexFeedEntry);
				}//inner if 
				else{
					map.put((String)eachDataMap.get("BUY_CURRENCY"), null);
				}
			 }//if
			}//for
			//create FileMapperMaster
			if(index == result.size() && index!=0)
			{
			obForexFeedGroup.setType("FOREX");
			obForexFeedGroup.setFeedEntriesSet(setChild);
			key = (Long)getHibernateTemplate().save("stageForexFeedGroup", obForexFeedGroup);	
			
			IFileMapperMaster fileMapper = new OBFileMapperMaster();
			fileMapper.setFileId(fileMapperMaster.getFileId());
			fileMapper.setSysId(key.longValue());
			fileMapper.setTransId(fileMapperMaster.getTransId());
			if(!(fileMapperMaster==null)){
				key = (Long) getHibernateTemplate().save("fileMapper", fileMapper);
				getHibernateTemplate().get("fileMapper", key);
				}else{
					throw new ForexFeedEntryException("ERROR- Entity name or key is null ");
				}
			noOfRecInserted++;
			return map;
			}
			/***********************/
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IncompleteBatchJobException(
					"Unable to update/insert dad retrived form CSV file");

		}
		return map;
	}
	
	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId createFileId(String entityName, IFileMapperId fileId)
	throws ForexFeedEntryException {
			// TODO Auto-generated method stub
			if(!(entityName==null|| fileId==null)){
		
		Long key = (Long) getHibernateTemplate().save(entityName, fileId);
		fileId.setId(key.longValue());
		return fileId;
		}else{
			throw new ForexFeedEntryException("ERROR- Entity name or key is null ");
		}
	}
	
	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId getInsertFileList(String entityName, Serializable key)
	throws ForexFeedEntryException {
		if(!(entityName==null|| key==null)){
			
			return (IFileMapperId) getHibernateTemplate().get(entityName, key);
			}else{
				throw new ForexFeedEntryException("ERROR-- Entity Name Or Key is null");
		}
	}
	

	/**
	 * @return ForexFeedEntry Object
	 * @param Entity Name
	 * @param sysId  
	 * This method Creates ForexFeedEntry Object
	 */
	public List insertActualForexFeedEntry(String forexFeedGroupID)
	throws ForexFeedEntryException {
		Map parametersMap = new HashMap();
		parametersMap.put("forexFeedGroupID", new Long(forexFeedGroupID));
		
		//DetachedCriteria criteria = DetachedCriteria.forEntityName("stageForexFeedGroup").add(Restrictions.allEq(parametersMap));
		//List forexFeedGroupList = getHibernateTemplate().findByCriteria(criteria);
		IForexFeedGroup forexFeedGroup = (IForexFeedGroup) getHibernateTemplate().get("stageForexFeedGroup", new Long(forexFeedGroupID));
		Set forexFeedEntrySet = forexFeedGroup.getFeedEntriesSet() ;
		
		List forexFeedEntryList = new ArrayList(forexFeedEntrySet);
		
		return forexFeedEntryList;
		}
	/**
	 * @return ForexFeedEntry Object
	 * @param Entity Name
	 * @param ForexFeedEntry Object  
	 * This method Creates ForexFeedEntry Object
	 */
	public IForexFeedGroup insertForexFeedEntry(String entityName,
			IForexFeedGroup forexFeedEntry)
			throws ForexFeedEntryException {
		if(!(entityName==null|| forexFeedEntry==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, forexFeedEntry);
			forexFeedEntry.setForexFeedGroupID(key.longValue());
			return forexFeedEntry;
			}else{
				throw new ForexFeedEntryException("ERROR- Entity name or key is null ");
			}
	}
	public void updateForexFeedEntryExchangeRate(String entityName ,List stgForexFeedEntryList) throws ForexFeedGroupException
	{
		IForexFeedEntry  stgForexFeedEntry = null;
		Iterator forexFeedEntryIt = stgForexFeedEntryList.iterator();
		while(forexFeedEntryIt.hasNext())
		{
			stgForexFeedEntry = (IForexFeedEntry)forexFeedEntryIt.next();
			LimitDAO dao = new LimitDAO();
			String restricatedType = "";
			
			List actForexFeedEntryList = getHibernateTemplate().find("SELECT FROM "+entityName +" where UPPER(CURRENCY_ISO_CODE) = '"+stgForexFeedEntry.getBuyCurrency().trim().toUpperCase()+"'");
			 IForexFeedEntry  actForexFeedEntry = (IForexFeedEntry)actForexFeedEntryList.get(0);
			 actForexFeedEntry.setCurrencyDescription(stgForexFeedEntry.getCurrencyDescription());
			 actForexFeedEntry.setCurrencyIsoCode(stgForexFeedEntry.getCurrencyIsoCode());

			 if(null == stgForexFeedEntry.getRestrictionType() || "".equals(stgForexFeedEntry.getRestrictionType())) {
				 System.out.println("Going for dao.getRestrictedTypeForScheduler updateForexFeedEntryExchangeRate");
				 restricatedType =  dao.getRestrictedTypeForScheduler(stgForexFeedEntry.getCurrencyIsoCode());
				 actForexFeedEntry.setRestrictionType(restricatedType);
			 }else {
				 System.out.println("Going for else updateForexFeedEntryExchangeRate=>stgForexFeedEntry.getRestrictionType()=>"+stgForexFeedEntry.getRestrictionType());
				 actForexFeedEntry.setRestrictionType(stgForexFeedEntry.getRestrictionType());
			 }
//			 actForexFeedEntry.setRestrictionType(stgForexFeedEntry.getRestrictionType());

			 actForexFeedEntry.setBuyRate(stgForexFeedEntry.getBuyRate());
			 actForexFeedEntry.setEffectiveDate(new Date());
			 actForexFeedEntry.setStatus(stgForexFeedEntry.getStatus());
			 
			 updateForexFeedEntry(entityName,  actForexFeedEntry);
		}		
	}
	
	public void updateForexFeedEntryExchangeRateAuto(String entityName ,List stgForexFeedEntryList) throws ForexFeedGroupException
	{
		System.out.println("ForexDaoImpl.java Inside updateForexFeedEntryExchangeRateAuto for ExchangeRateUploadJob.java");
		IForexFeedEntry  stgForexFeedEntry = null;
		Iterator forexFeedEntryIt = stgForexFeedEntryList.iterator();
		while(forexFeedEntryIt.hasNext())
		{
			stgForexFeedEntry = (IForexFeedEntry)forexFeedEntryIt.next();
			System.out.println("ForexDaoImpl.java SELECT FROM "+entityName +" where UPPER(CURRENCY_ISO_CODE) = "+stgForexFeedEntry.getBuyCurrency().trim().toUpperCase());
			List actForexFeedEntryList = getHibernateTemplate().find("SELECT FROM "+entityName +" where UPPER(CURRENCY_ISO_CODE) = '"+stgForexFeedEntry.getBuyCurrency().trim().toUpperCase()+"'");
			System.out.println("actForexFeedEntryList=>"+actForexFeedEntryList);
			if(!actForexFeedEntryList.isEmpty()){
				LimitDAO dao = new LimitDAO();
				String restricatedType = "";
				 IForexFeedEntry  actForexFeedEntry = (IForexFeedEntry)actForexFeedEntryList.get(0);
				 actForexFeedEntry.setCurrencyDescription(stgForexFeedEntry.getCurrencyDescription());
				 actForexFeedEntry.setCurrencyIsoCode(stgForexFeedEntry.getCurrencyIsoCode());

				 if(null == stgForexFeedEntry.getRestrictionType() || "".equals(stgForexFeedEntry.getRestrictionType())) {
					 System.out.println("Going for dao.getRestrictedTypeForScheduler");
					 restricatedType =  dao.getRestrictedTypeForScheduler(stgForexFeedEntry.getCurrencyIsoCode());
					 actForexFeedEntry.setRestrictionType(restricatedType);
				 }else {
					 System.out.println("Going for else updateForexFeedEntryExchangeRateAuto=>stgForexFeedEntry.getRestrictionType()=>"+stgForexFeedEntry.getRestrictionType());
					 actForexFeedEntry.setRestrictionType(stgForexFeedEntry.getRestrictionType());
				 }

				 actForexFeedEntry.setBuyRate(stgForexFeedEntry.getBuyRate());
				 actForexFeedEntry.setBuyUnit(stgForexFeedEntry.getBuyUnit());
				 actForexFeedEntry.setSellCurrency(stgForexFeedEntry.getSellCurrency());
				 actForexFeedEntry.setSellRate(stgForexFeedEntry.getSellRate());
				 actForexFeedEntry.setSellUnit(stgForexFeedEntry.getSellUnit());
				 actForexFeedEntry.setEffectiveDate(stgForexFeedEntry.getEffectiveDate());
				 actForexFeedEntry.setStatus(stgForexFeedEntry.getStatus());
				 actForexFeedEntry.setBuyCurrency(stgForexFeedEntry.getBuyCurrency());
				 System.out.println("Going for updateForexFeedEntry");
				 updateForexFeedEntry(entityName,  actForexFeedEntry);
				 System.out.println("After  updateForexFeedEntry");
			}
		}		
	}
	
	public boolean isCurrencyCodeExist(String entityName,List currencyCodeList) throws ForexFeedGroupException
	{
		boolean isCurrencyCodeExist = false;
		Iterator itCurrencyCodeList  = currencyCodeList.iterator();
		while(itCurrencyCodeList.hasNext()){
		List resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+entityName +" where UPPER(CURRENCY_ISO_CODE) = '"+itCurrencyCodeList.next().toString().trim().toUpperCase()+"'");
		if (resultList.isEmpty()) {
			isCurrencyCodeExist =  false;
		}
		else{
			isCurrencyCodeExist =  true;
			break;
		}
		}
		return isCurrencyCodeExist;	
	}
	//----------------------------------End file upload-------------------//
	/**
	 * This Method get ExchangeRate With INR
	 * Input parameter Currency Code
	 */
	public BigDecimal getExchangeRateWithINR (String entityName,String currencyCode) throws ForexFeedGroupException

	{
		IForexFeedEntry  accForexFeedEntry = null;
		//currencyCode = "MYR";
		try {
			List resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+entityName +" where BUY_CURRENCY = '"+currencyCode+"'");
			if(!resultList.isEmpty())
			{
			Iterator forexFeedEntryIt = resultList.iterator();
			while(forexFeedEntryIt.hasNext())
			{
				accForexFeedEntry = (IForexFeedEntry)forexFeedEntryIt.next();
				break;
			}
			}
		} catch (Exception e) {
		e.printStackTrace();
			// TODO: handle exception
		//System.out.println(e);
	}
		if(accForexFeedEntry != null)
		{
		return accForexFeedEntry.getBuyRate();
		}
		else
		{
			return new BigDecimal(0);			
		}
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws ForexFeedGroupException, TrxParameterException,TransactionException {
		OBCMSTrxValue obCMSTrxValue = new OBCMSTrxValue();
		String query = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_FOREX_FEED_GR' AND obcms.transactionID='"+obFileMapperMaster.getTransId()+"'";
		List list = getHibernateTemplate().find(query);
		obCMSTrxValue = (OBCMSTrxValue)list.get(0);
		getHibernateTemplate().flush();
		getHibernateTemplate().delete(obCMSTrxValue);			
	}
	
	public OBForex retriveCurrency(String currency)throws ForexFeedGroupException{
		HashMap map = new HashMap();
		OBForex ob=new OBForex();
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("'"+currency+"'");		
		//strBuf.append("order by(CMS_LSP_LMT_PROFILE_ID)desc ");
		ResultSet rs=null;
//		String sql = SELECT_CHECKLIST_BY_COLLATERAL_ID + strBuf.toString();
		String sql = RETRIVE_CURRENCY + strBuf.toString();
		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			//dbUtil.setString(1, camNo);
			rs = dbUtil.executeQuery();
			/*long checkListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			while (rs.next()) {
				checkListID = rs.getLong(CHKTBL_CHECKLISTID);
							
			}
			rs.close();
			return new Long(checkListID);*/
		
			while (rs.next()) {
				ob.setRate(rs.getDouble("BUY_RATE"));
				
				break;
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				finalize(dbUtil, rs);
			}
			catch (Exception e) {
				throw new SearchDAOException("Error in finalize" + e.getMessage());
			}
		}
		
		return ob;
		
	}
	private void finalize(DBUtil dbUtil, ResultSet rs) throws SearchDAOException {
		try {
			if (null != rs) {
				rs.close();
			}
		}
		catch (Exception e) {
		}
		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in cleaning up DB resources!", e);
		}
	}
	
}

