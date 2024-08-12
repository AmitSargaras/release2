package com.integrosys.cms.app.feed.bus.stock;

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

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.forex.ForexFeedGroupException;
import com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.batch.IncompleteBatchJobException;

/**
 * Implementation of {@link IStockDao}
 * 
 * @author Chong Jun Yong
 * @since 1.1
 * 
 */
public class StockDaoImpl extends HibernateDaoSupport implements IStockDao {

	public IStockFeedEntry createStockFeedEntry(String entityName, IStockFeedEntry stockFeedEntry) {
		Long key = (Long) getHibernateTemplate().save(entityName, stockFeedEntry);

		return (IStockFeedEntry) getHibernateTemplate().get(entityName, key);
	}

	public IStockFeedGroup createStockFeedGroup(String entityName, IStockFeedGroup stockFeedGroup) {
		Long key = (Long) getHibernateTemplate().save(entityName, stockFeedGroup);

		return (IStockFeedGroup) getHibernateTemplate().get(entityName, key);
	}

	public void deleteStockFeedEntry(String entityName, IStockFeedEntry stockFeedEntry) {
		getHibernateTemplate().delete(entityName, stockFeedEntry);
	}

	public void deleteStockFeedGroup(String entityName, IStockFeedGroup stockFeedGroup) {
		getHibernateTemplate().delete(entityName, stockFeedGroup);
	}

	public IStockFeedEntry getStockFeedEntryByIsinCode(String entityName, String isinCode) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(
				Restrictions.eq("isinCode", isinCode));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		return (resultList.isEmpty()) ? null : (IStockFeedEntry) resultList.get(0);
	}

	public IStockFeedEntry getStockFeedEntryByPrimaryKey(String entityName, Serializable key) {
		return (IStockFeedEntry) getHibernateTemplate().get(entityName, key);
	}

	public IStockFeedEntry getStockFeedEntryByRic(String entityName, String ric) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("ric", ric));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		return (resultList.isEmpty()) ? null : (IStockFeedEntry) resultList.get(0);
	}

	public IStockFeedEntry getStockFeedEntryByTicker(String entityName, String ticker) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.eq("ticker", ticker));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		return (resultList.isEmpty()) ? null : (IStockFeedEntry) resultList.get(0);
	}

	public IStockFeedGroup getStockFeedGroupByPrimaryKey(String entityName, Serializable key) {
		return (IStockFeedGroup) getHibernateTemplate().get(entityName, key);
	}

	public IStockFeedGroup getStockFeedGroupByTypeAndSubType(String entityName, String type, String subType) {
		Map parametersMap = new HashMap();
		parametersMap.put("type", type);
		parametersMap.put("subType", subType);

		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.allEq(parametersMap));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		return (resultList.isEmpty()) ? null : (IStockFeedGroup) resultList.get(0);
	}

	public IStockFeedGroup getStockFeedGroupByTypeAndSubTypeAndStockType(String entityName, String type,
			String subType, String stockType) {
		Map parametersMap = new HashMap();
		parametersMap.put("type", type);
		parametersMap.put("subType", subType);
		parametersMap.put("stockType", stockType);

		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.allEq(parametersMap));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		return (resultList.isEmpty()) ? null : (IStockFeedGroup) resultList.get(0);
	}

	public IStockFeedEntry updateStockFeedEntry(String entityName, IStockFeedEntry stockFeedEntry) {
		getHibernateTemplate().update(entityName, stockFeedEntry);

		return (IStockFeedEntry) getHibernateTemplate().get(entityName, new Long(stockFeedEntry.getStockFeedEntryID()));
	}

	public IStockFeedGroup updateStockFeedGroup(String entityName, IStockFeedGroup stockFeedGroup) {
		getHibernateTemplate().update(entityName, stockFeedGroup);

		return (IStockFeedGroup) getHibernateTemplate().get(entityName, new Long(stockFeedGroup.getStockFeedGroupID()));
	}
	
	//govind
	public IStockFeedEntry getStockFeedEntryByStockExcScri(String entityName, String stockExchange ,String scriptCode){
		Map parametersMap = new HashMap();
		boolean isStockExchangeOthers = "003".equals(stockExchange);
		  String stockExchange1 = stockExchange;
			if(stockExchange1.equals("001"))
			{
				stockExchange1 = "BSE";
				}
			else if(stockExchange1.equals("002"))
			{
				stockExchange1 = "NSE";
				}
			else if(stockExchange1.equals("003"))
			{
				stockExchange1 = "Others";
				}
			else 
			{
				stockExchange1 = "";
				}
		
		parametersMap.put("scriptCode", scriptCode);
		
		if(isStockExchangeOthers) {
			parametersMap.put("groupStockType", stockExchange);
		}else {
			parametersMap.put("stockExchangeName", stockExchange1);
		}
		
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.allEq(parametersMap));
		List stockFeedEntryList = getHibernateTemplate().findByCriteria(criteria);
		if(!stockFeedEntryList.isEmpty()){
			return (IStockFeedEntry)stockFeedEntryList.get(0);
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
	public boolean isPrevFileUploadPending(String stockType) {
		String trxType = "";
		if(stockType.equals("001")){
			trxType = ICMSConstant.INSTANCE_INSERT_STOCK_ITEM;
	        }
	        else if(stockType.equals("002")){
	        	trxType = ICMSConstant.INSTANCE_INSERT_STOCK_NSE_ITEM;
	        }
	        else if(stockType.equals("003")){
	        	trxType = ICMSConstant.INSTANCE_INSERT_STOCK_OTHERS_ITEM;
	        }
		
		String rowCount = "FROM com.integrosys.cms.app.transaction.OBCMSTrxValue obcms WHERE obcms.transactionType='"+trxType+"'";
		List rowList = getHibernateTemplate().find(rowCount);
		if(rowList.size()>0){
			String sqlQuery = "FROM com.integrosys.cms.app.transaction.OBCMSTrxValue obcms WHERE obcms.transactionType='"+trxType+"' AND status != 'ACTIVE'";
			List stockFeedEntryList = getHibernateTemplate().find(sqlQuery);
			if(stockFeedEntryList.size()>0){
				return true;
			}
		}
		return false;
	}
	
	
	public List getAllStageStockFeedEntry(String searchBy, String login) {
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
			String query = "SELECT FROM "+IStockDao.STAGE_STOCK_FEED_ENTRY_ENTITY_NAME+" WHERE FEED_GROUP_ID = ("+strId+")";
			try {
				if (searchByValue.trim() != "") {
					resultList = getHibernateTemplate().find(query);
				}
	
			} catch (Exception e) {
				throw new StockFeedEntryException("ERROR-- While retriving RelationshipMgr");
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
	public IFileMapperId insertStockFeedEntry(String entityName,
			IFileMapperId fileId, IStockFeedGroupTrxValue trxValue)
			throws StockFeedEntryException {
		
		if(!(entityName==null|| fileId==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, fileId);
			fileId.setId(key.longValue());
			return fileId;
		}else{
			throw new StockFeedEntryException("ERROR- Entity name or key is null ");
		}	
	}
	
	/**
	 * @return integer (record count of inserted record)
	 * @param FileMapperMaster
	 * @param userName  
	 * @param result of upload files 
	 * This method Creates FileMapperMaster Object
	 */
	public int insertStockFeedEntry(IFileMapperMaster fileMapperMaster, String userName, ArrayList result, String stockType) {
		// TODO Auto-generated method stub
		int noOfRecInserted = 0;
		DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
		Long key=null;
		if (result == null || result.size() <= 0) {
			throw new IncompleteBatchJobException(
					"Data to be instered to DB is null or empty");
		}
		try {
			Long stageStockFeedGroupkey= new Long(0);
			Set setChild = new HashSet();
			OBStockFeedGroup obStockFeedGroup = null;
			int index;
			for (index = 0; index < result.size(); index++) {
				HashMap eachDataMap = (HashMap) result.get(index);
				String entityName="actualStockFeedEntry";
				String scriptCode = (String)eachDataMap.get("ISIN_CODE");
				List resultList = null;
				OBStockFeedEntry obStockFeedEntry = new OBStockFeedEntry();
				OBStockFeedEntry StockFeedEntry = new OBStockFeedEntry();
				obStockFeedGroup =  new OBStockFeedGroup();
				if(!(entityName==null|| scriptCode==null)){
					String exchange = "";
					if(stockType.equals("001")){
						exchange = " AND EXCHANGE = 'BSE'";
					}else if(stockType.equals("002")){
						exchange = " AND EXCHANGE = 'NSE'";
					}
					
				resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+entityName +" where UPPER(ISIN_CODE) = '"+scriptCode.toUpperCase()+"'"+exchange);
				if (!resultList.isEmpty()) {
					String unitPrice = (String) eachDataMap.get("UNIT_PRICE");
					String parValue = (String) eachDataMap.get("PAR_VALUE");
				StockFeedEntry = (OBStockFeedEntry)resultList.get(0);
				obStockFeedEntry.setStockFeedEntryID(0);
				obStockFeedEntry.setScriptCode((String)eachDataMap.get("ISIN_CODE"));
				obStockFeedEntry.setScriptName((String)eachDataMap.get("NAME"));
				if(unitPrice!=null && !unitPrice.trim().equals("")){
				obStockFeedEntry.setScriptValue(Double.parseDouble((String) eachDataMap.get("UNIT_PRICE")));
				}
				if(parValue!=null && !parValue.trim().equals("")){
				obStockFeedEntry.setFaceValue(Double.parseDouble((String) eachDataMap.get("PAR_VALUE")));
				}
				obStockFeedEntry.setStockExchangeName((String)eachDataMap.get("EXCHANGE"));
				obStockFeedEntry.setLastUpdatedDate(new Date());
				setChild.add(obStockFeedEntry);
			    
				//key = (Long) getHibernateTemplate().save("stageStockFeedEntry", obStockFeedEntry);				
				}//inner if
			 }//if
			}//for
			/***********************/
			//create FileMapperMaster
			if(index == result.size() && index!=0)
			{
			obStockFeedGroup.setType("STOCK");
			obStockFeedGroup.setFeedEntriesSet(setChild);
			key = (Long)getHibernateTemplate().save("stageStockFeedGroup", obStockFeedGroup);	
			
			IFileMapperMaster fileMapper = new OBFileMapperMaster();
			fileMapper.setFileId(fileMapperMaster.getFileId());
			fileMapper.setSysId(key.longValue());
			fileMapper.setTransId(fileMapperMaster.getTransId());
			if(!(fileMapperMaster==null)){
				key = (Long) getHibernateTemplate().save("fileMapper", fileMapper);
				getHibernateTemplate().get("fileMapper", key);
				}else{
					throw new StockFeedEntryException("ERROR- Entity name or key is null ");
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
	throws StockFeedEntryException {
			// TODO Auto-generated method stub
			if(!(entityName==null|| fileId==null)){
		
		Long key = (Long) getHibernateTemplate().save(entityName, fileId);
		fileId.setId(key.longValue());
		return fileId;
		}else{
			throw new StockFeedEntryException("ERROR- Entity name or key is null ");
		}
	}
	
	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId getInsertFileList(String entityName, Serializable key)
	throws StockFeedEntryException {
		if(!(entityName==null|| key==null)){
			
			return (IFileMapperId) getHibernateTemplate().get(entityName, key);
			}else{
				throw new StockFeedEntryException("ERROR-- Entity Name Or Key is null");
		}
	}
	

	/**
	 * @return StockFeedEntry Object
	 * @param Entity Name
	 * @param sysId  
	 * This method Creates StockFeedEntry Object
	 */
	public List insertActualStockFeedEntry(String StockFeedGroupID)
	throws StockFeedEntryException {
		Map parametersMap = new HashMap();
		parametersMap.put("StockFeedGroupID", new Long(StockFeedGroupID));
		
		//DetachedCriteria criteria = DetachedCriteria.forEntityName("stageStockFeedGroup").add(Restrictions.allEq(parametersMap));
		//List StockFeedGroupList = getHibernateTemplate().findByCriteria(criteria);
		IStockFeedGroup stockFeedGroup = (IStockFeedGroup) getHibernateTemplate().get("stageStockFeedGroup", new Long(StockFeedGroupID));
		Set stockFeedEntrySet = stockFeedGroup.getFeedEntriesSet() ;
		
		List stockFeedEntryList = new ArrayList(stockFeedEntrySet);
		
		return stockFeedEntryList;
		}
	/**
	 * @return StockFeedEntry Object
	 * @param Entity Name
	 * @param StockFeedEntry Object  
	 * This method Creates StockFeedEntry Object
	 */
	public IStockFeedGroup insertStockFeedEntry(String entityName,
			IStockFeedGroup stockFeedEntry)
			throws StockFeedEntryException {
		if(!(entityName==null|| stockFeedEntry==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, stockFeedEntry);
			stockFeedEntry.setStockFeedGroupID(key.longValue());
			return stockFeedEntry;
			}else{
				throw new StockFeedEntryException("ERROR- Entity name or key is null ");
			}
	}
	public void updateStockFeedEntryItem(String entityName ,List stgStockFeedEntryList) throws StockFeedGroupException
	{
		IStockFeedEntry  stgStockFeedEntry = null;
		Iterator stockFeedEntryIt = stgStockFeedEntryList.iterator();
		while(stockFeedEntryIt.hasNext())
		{
			stgStockFeedEntry = (IStockFeedEntry)stockFeedEntryIt.next();
			//entityName is actual
			List actStockFeedEntryList = getHibernateTemplate().find("SELECT FROM "+entityName +" where UPPER(ISIN_CODE) = '"+stgStockFeedEntry.getScriptCode().toUpperCase()+"'");
			 IStockFeedEntry  actStockFeedEntry = (IStockFeedEntry)actStockFeedEntryList.get(0);
			 
			 	actStockFeedEntry.setScriptCode(stgStockFeedEntry.getScriptCode());
			 	actStockFeedEntry.setScriptName(stgStockFeedEntry.getScriptName());
			 	actStockFeedEntry.setScriptValue(stgStockFeedEntry.getScriptValue());
			 	actStockFeedEntry.setFaceValue(stgStockFeedEntry.getFaceValue());
				actStockFeedEntry.setStockExchangeName(stgStockFeedEntry.getStockExchangeName());
				actStockFeedEntry.setLastUpdatedDate(stgStockFeedEntry.getLastUpdatedDate());
			 updateStockFeedEntry(entityName,  actStockFeedEntry);
		}		
	}
	
	public boolean isStockCodeExist(String entityName,List scriptCodeList , String stockType) throws ForexFeedGroupException
	{
		boolean isStockCodeExist = false;
		String exchange = "";
		if(stockType.equals("001")){
			exchange = " AND EXCHANGE = 'BSE'";
		}else if(stockType.equals("002")){
			exchange = " AND EXCHANGE = 'NSE'";
		}
		Iterator itScriptCodeList  = scriptCodeList.iterator();
		while(itScriptCodeList.hasNext()){
		List resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+entityName +" where UPPER(ISIN_CODE) = '"+itScriptCodeList.next().toString().toUpperCase()+"'"+exchange);
		if (resultList.isEmpty()) {
			isStockCodeExist = false;
		}else{
			isStockCodeExist = true;
		break;
		}
	}
	return isStockCodeExist;	
	}
	/**
	 * @return Maker delete if error.  //Add by govind 270811
	 */
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) { 
		OBCMSTrxValue obCMSTrxValue = new OBCMSTrxValue();
		String query = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_STOCK' AND obcms.transactionID='"+obFileMapperMaster.getTransId()+"'";
		List list = getHibernateTemplate().find(query);
		obCMSTrxValue = (OBCMSTrxValue)list.get(0);
		getHibernateTemplate().flush();
		getHibernateTemplate().delete(obCMSTrxValue);
	}
	//----------------------------------End file upload-------------------//
	
	
	public boolean isExistScriptCode(String entityName, String stockCode) throws ForexFeedGroupException
	{
		boolean isStockCodeExist = false;
		List resultList = (ArrayList)getHibernateTemplate().find("SELECT FROM "+entityName +" where ISIN_CODE = '"+stockCode+"'");
		if(resultList.size() > 0)
			return true;
		else 
			return false;	
	}
}
