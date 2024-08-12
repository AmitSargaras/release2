package com.integrosys.cms.app.holiday.bus;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.batch.IncompleteBatchJobException;
/**
 * @author  Abhijit R. 
 * Dao implication of interface Holiday
 */

public class HolidayDaoImpl extends HibernateDaoSupport implements IHolidayDao{
	
	private IHolidayDao holidayDao;
	
	public IHolidayDao getHolidayDao() {
		return holidayDao;
	}
	public void setHolidayDao(IHolidayDao holidayDao) {
		this.holidayDao = holidayDao;
	}
	
	/**
	  * @return Particular Holiday according 
	  * to the id passed as parameter.  
	  * 
	  */

	public IHoliday getHoliday(String entityName, Serializable key)throws HolidayException {
		
		if(!(entityName==null|| key==null)){
		
		return (IHoliday) getHibernateTemplate().get(entityName, key);
		}else{
			throw new HolidayException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	/**
	 * @return Holiday Object
	 * @param Entity Name
	 * @param Holiday Object  
	 * This method Updates Holiday Object
	 */
	
	public IHoliday updateHoliday(String entityName, IHoliday item)throws HolidayException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (IHoliday) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new HolidayException("ERROR-- Entity Name Or Key is null");
		}
	}
	/**
	 * @return Holiday Object
	 * @param Entity Name
	 * @param Holiday Object  
	 * This method delete Holiday Object
	 */
	
	public IHoliday deleteHoliday(String entityName, IHoliday item)throws HolidayException{

		if(!(entityName==null|| item==null)){
			item.setDeprecated("Y");
			item.setStatus("INACTIVE");
			getHibernateTemplate().update(entityName, item);
			return (IHoliday) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new HolidayException("ERROR-- Entity Name Or Key is null");
		}
	}
	/**
	 * @return Holiday Object
	 * @param Entity Name
	 * @param Holiday Object  
	 * This method Creates Holiday Object
	 */
	public IHoliday createHoliday(String entityName,
			IHoliday holiday)
			throws HolidayException {
		if(!(entityName==null|| holiday==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, holiday);
			holiday.setId(key.longValue());
			return holiday;
			}else{
				throw new HolidayException("ERROR- Entity name or key is null ");
			}
	}


	
	
	/**
	  * @return Particular Holiday according 
	  * to the id passed as parameter.  
	  * 
	  */

	public IHoliday load(String entityName, long id)throws HolidayException
	{
		if(!(entityName==null|| id==0)){
		return (IHoliday)getHibernateTemplate().get(entityName , new Long(id));	
		}else{
			throw new HolidayException("ERROR- Entity name or key is null ");
		}
	}
	
	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId insertHoliday(String entityName,
			IFileMapperId fileId, IHolidayTrxValue trxValue)
			throws HolidayException {
		
		if(!(entityName==null|| fileId==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, fileId);
			fileId.setId(key.longValue());
			return fileId;
		}else{
			throw new HolidayException("ERROR- Entity name or key is null ");
		}	
	}
	
	/**
	 * @return integer (record count of inserted record)
	 * @param FileMapperMaster
	 * @param userName  
	 * @param result of upload files 
	 * This method Creates FileMapperMaster Object
	 */
	public int insertHoliday(IFileMapperMaster fileMapperMaster, String userName, ArrayList result) {
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
			for (int index = 0; index < result.size(); index++) {
				HashMap eachDataMap = (HashMap) result.get(index);
				OBHoliday obHoliday = new OBHoliday();
				obHoliday.setDescription((String) eachDataMap.get("DESCRIPTION"));
				obHoliday.setStartDate(df.parse((String) eachDataMap.get("START_DATE").toString()));
				obHoliday.setEndDate(df.parse(eachDataMap.get("END_DATE").toString()));
				obHoliday.setIsRecurrent((String)eachDataMap.get("IS_RECURRENT").toString());
				obHoliday.setLastUpdateDate(new Date());
				obHoliday.setCreateBy(userName);
				obHoliday.setCreationDate(new Date());
				obHoliday.setStatus("ACTIVE");
				obHoliday.setDeprecated("N");
				obHoliday.setLastUpdateBy(userName);
				key = (Long) getHibernateTemplate().save("stageHoliday", obHoliday);				
				//create FileMapperMaster
				
				IFileMapperMaster fileMapper = new OBFileMapperMaster();
				fileMapper.setFileId(fileMapperMaster.getFileId());
				fileMapper.setSysId(key.longValue());
				fileMapper.setTransId(fileMapperMaster.getTransId());
				if(!(fileMapperMaster==null)){
					key = (Long) getHibernateTemplate().save("fileMapper", fileMapper);
					getHibernateTemplate().get("fileMapper", key);
					}else{
						throw new HolidayException("ERROR- Entity name or key is null ");
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
	throws HolidayException {
			// TODO Auto-generated method stub
			if(!(entityName==null|| fileId==null)){
		
		Long key = (Long) getHibernateTemplate().save(entityName, fileId);
		fileId.setId(key.longValue());
		return fileId;
		}else{
			throw new HolidayException("ERROR- Entity name or key is null ");
		}
	}
	
	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId getInsertFileList(String entityName, Serializable key)
	throws HolidayException {
		if(!(entityName==null|| key==null)){
			
			return (IFileMapperId) getHibernateTemplate().get(entityName, key);
			}else{
				throw new HolidayException("ERROR-- Entity Name Or Key is null");
		}
	}
	

	/**
	 * @return Holiday Object
	 * @param Entity Name
	 * @param sysId  
	 * This method Creates Holiday Object
	 */
	public IHoliday insertActualHoliday(String sysId)
	throws HolidayException {
		Map parametersMap = new HashMap();
		parametersMap.put("id", new Long(sysId));
		
		DetachedCriteria criteria = DetachedCriteria.forEntityName("stageHoliday").add(Restrictions.allEq(parametersMap));
		List holidayList = getHibernateTemplate().findByCriteria(criteria);
			return (IHoliday)holidayList.get(0);
		}
	/**
	 * @return Holiday Object
	 * @param Entity Name
	 * @param Holiday Object  
	 * This method Creates Holiday Object
	 */
	public IHoliday insertHoliday(String entityName,
			IHoliday holiday)
			throws HolidayException {
		if(!(entityName==null|| holiday==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, holiday);
			holiday.setId(key.longValue());
			return holiday;
			}else{
				throw new HolidayException("ERROR- Entity name or key is null ");
			}
	}
	
	/**
	 * @return Maker check if previous upload is pending.
	 */
	public boolean isPrevFileUploadPending() {

		String rowCount = "FROM OBCMSTrxValue c WHERE c.transactionType='INSERT_HOLIDAY'";
		List rowList = getHibernateTemplate().find(rowCount);
		if(rowList.size()>0){
			String sqlQuery = "FROM OBCMSTrxValue trx WHERE trx.transactionType='INSERT_HOLIDAY' AND trx.status != 'ACTIVE'";
			List holidayList = getHibernateTemplate().find(sqlQuery);
			if(holidayList.size()>0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return list of files uploaded in staging table of Holiday.
	 */
	public List getFileMasterList(String searchBy) {
		Map parametersList = new HashMap();
		parametersList.put("transId",searchBy);
		DetachedCriteria criteria = DetachedCriteria.forEntityName("fileMapper").add(Restrictions.allEq(parametersList));
		List listId = getHibernateTemplate().findByCriteria(criteria);
       return listId;
	}
	
	/**
	 * @return Pagination for uploaded files in Holiday.
	 */

	public List getAllStageHoliday(String searchBy, String login) {
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
			String query = "SELECT FROM "+IHolidayDao.STAGE_HOLIDAY_NAME+" WHERE DEPRECATED = 'N' AND ID IN ("+strId+")";
			try {
				if (searchByValue.trim() != "") {
					resultList = getHibernateTemplate().find(query);
				}
	
			} catch (Exception e) {
				throw new RelationshipMgrException("ERROR-- While retriving RelationshipMgr");
			}
		}
		return resultList;
	}

	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) { 
		OBCMSTrxValue obCMSTrxValue = new OBCMSTrxValue();
		String query = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_HOLIDAY' AND obcms.transactionID='"+obFileMapperMaster.getTransId()+"'";
		List list = getHibernateTemplate().find(query);
		obCMSTrxValue = (OBCMSTrxValue)list.get(0);
		getHibernateTemplate().flush();
		getHibernateTemplate().delete(obCMSTrxValue);
	}
}
