package com.integrosys.cms.app.baselmaster.bus;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.baselmaster.trx.IBaselMasterTrxValue;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.bus.IComponentDao;
import com.integrosys.cms.app.component.bus.OBComponent;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;
import com.integrosys.cms.app.facilityNewMaster.bus.FacilityNewMasterException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.batch.IncompleteBatchJobException;
import com.integrosys.cms.ui.common.CommonCodeList;

public class BaselDaoImpl extends HibernateDaoSupport implements IBaselDao{
	


	private IBaselDao baselMasterDao;		
		 
	

	public IBaselDao getBaselMasterDao() {
		return baselMasterDao;
	}

	public void setBaselMasterDao(IBaselDao baselMasterDao) {
		this.baselMasterDao = baselMasterDao;
	}

		/**
		  * @return Particular Component according 
		  * to the id passed as parameter.  
		  * 
		  */

		public IBaselMaster getBasel(String entityName, Serializable key)throws BaselMasterException {
			
			if(!(entityName==null|| key==null)){
			
			return (IBaselMaster) getHibernateTemplate().get(entityName, key);
			}else{
				throw new BaselMasterException("ERROR-- Entity Name Or Key is null");
			}
		}
		
		/**
		 * @return Component Object
		 * @param Entity Name
		 * @param Component Object  
		 * This method Updates Component Object
		 */
		
		public IBaselMaster updateBasel(String entityName, IBaselMaster item)throws BaselMasterException{
			if(!(entityName==null|| item==null)){
				getHibernateTemplate().update(entityName, item);
				return  (IBaselMaster) getHibernateTemplate().load(entityName, new Long(item.getId()));
			}else{
				throw new BaselMasterException("ERROR-- Entity Name Or Key is null");
			}
		}
		/**
		 * @return Component Object
		 * @param Entity Name
		 * @param Component Object  
		 * This method delete Component Object
		 */
		
		public IBaselMaster deleteBasel(String entityName, IBaselMaster item)throws BaselMasterException{

			if(!(entityName==null|| item==null)){
				item.setDeprecated("Y");
				item.setStatus("INACTIVE");
				getHibernateTemplate().update(entityName, item);
				return (IBaselMaster) getHibernateTemplate().load(entityName, new Long(item.getId()));
			}else{
				throw new BaselMasterException("ERROR-- Entity Name Or Key is null");
			}
		}
		/**
		 * @return Component Object
		 * @param Entity Name
		 * @param Component Object  
		 * This method Creates Component Object
		 */
		public IBaselMaster createBasel(String entityName,IBaselMaster basel)
				throws BaselMasterException {
			if(!(entityName==null|| basel==null)){
				
				/*if(component.getComponentCode()==null||component.getComponentCode().equals("")){
					String componentCode=getComponentCode();
					component.setComponentCode(componentCode);
				}*/
			
				
				Long key = (Long) getHibernateTemplate().save(entityName, basel);
				basel.setId(key.longValue());
				
				return basel;
				}else{
					throw new BaselMasterException("ERROR- Entity name or key is null ");
				}
		}


		
		
		/**
		  * @return Particular Component according 
		  * to the id passed as parameter.  
		  * 
		  */

		public IBaselMaster load(String entityName, long id)throws BaselMasterException
		{
			if(!(entityName==null|| id==0)){
			return (IBaselMaster)getHibernateTemplate().get(entityName , new Long(id));	
			}else{
				throw new BaselMasterException("ERROR- Entity name or key is null ");
			}
		}
		
		/**
		 * @return FileMapperId Object
		 * @param Entity Name
		 * @param FileMapperId Object  
		 * This method Creates FileMapperId Object
		 */
		public IFileMapperId insertBasel(String entityName,	IFileMapperId fileId, IBaselMasterTrxValue trxValue)
				throws BaselMasterException {
			
			if(!(entityName==null|| fileId==null)){
				
				Long key = (Long) getHibernateTemplate().save(entityName, fileId);
				fileId.setId(key.longValue());
				return fileId;
			}else{
				throw new BaselMasterException("ERROR- Entity name or key is null ");
			}	
		}
		
		/**
		 * @return integer (record count of inserted record)
		 * @param FileMapperMaster
		 * @param userName  
		 * @param result of upload files 
		 * This method Creates FileMapperMaster Object
		 */
		public int insertBasel(IFileMapperMaster fileMapperMaster, String userName, ArrayList result) {
			
			int noOfRecInserted = 0;
			
			Long key=null;
			if (result == null || result.size() <= 0) {
				throw new IncompleteBatchJobException(
						"Data to be instered to DB is null or empty");
			}
			try {
				for (int index = 0; index < result.size(); index++) {
					HashMap eachDataMap = (HashMap) result.get(index);
					OBBaselMaster obBasel = new OBBaselMaster();
					
					obBasel.setStatus("ACTIVE");
					obBasel.setDeprecated("N");
					
					key = (Long) getHibernateTemplate().save("stageBasel", obBasel);				
					//create FileMapperMaster
					
					IFileMapperMaster fileMapper = new OBFileMapperMaster();
					fileMapper.setFileId(fileMapperMaster.getFileId());
					fileMapper.setSysId(key.longValue());
					fileMapper.setTransId(fileMapperMaster.getTransId());
					if(!(fileMapperMaster==null)){
						key = (Long) getHibernateTemplate().save("fileMapper", fileMapper);
						getHibernateTemplate().get("fileMapper", key);
						}else{
							throw new BaselMasterException("ERROR- Entity name or key is null ");
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
		throws BaselMasterException {
				
				if(!(entityName==null|| fileId==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, fileId);
			fileId.setId(key.longValue());
			return fileId;
			}else{
				throw new BaselMasterException("ERROR- Entity name or key is null ");
			}
		}
		
		/**
		 * @return FileMapperId Object
		 * @param Entity Name
		 * @param FileMapperId Object  
		 * This method Creates FileMapperId Object
		 */
		public IFileMapperId getInsertFileList(String entityName, Serializable key)
		throws BaselMasterException {
			if(!(entityName==null|| key==null)){
				
				return (IFileMapperId) getHibernateTemplate().get(entityName, key);
				}else{
					throw new BaselMasterException("ERROR-- Entity Name Or Key is null");
			}
		}
		

		/**
		 * @return Component Object
		 * @param Entity Name
		 * @param sysId  
		 * This method Creates Component Object
		 */
		public IBaselMaster insertActualBasel(String sysId)
		throws BaselMasterException {
			Map parametersMap = new HashMap();
			parametersMap.put("id", new Long(sysId));
			
			DetachedCriteria criteria = DetachedCriteria.forEntityName("stageBasel").add(Restrictions.allEq(parametersMap));
			List baselList = getHibernateTemplate().findByCriteria(criteria);
				return (IBaselMaster)baselList.get(0);
			}
		/**
		 * @return Component Object
		 * @param Entity Name
		 * @param Component Object  
		 * This method Creates Component Object
		 */
		public IBaselMaster insertBasel(String entityName,IBaselMaster basel)
				throws BaselMasterException {
			if(!(entityName==null|| basel==null)){
				
				Long key = (Long) getHibernateTemplate().save(entityName, basel);
				basel.setId(key.longValue());
				return basel;
				}else{
					throw new BaselMasterException("ERROR- Entity name or key is null ");
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
				List componentList = getHibernateTemplate().find(sqlQuery);
				if(componentList.size()>0){
					return true;
				}
			}
			return false;
		}
		
		public void insertComponentinCommonCode (OBComponent obj) {
			
		
			String componentType = "";
			String componentCode = obj.getComponentCode();
			String componentName = obj.getComponentName();
			if(obj.getComponentType().equals("Current_Asset"))
			{
				 componentType = "CURRENT_ASSET";
			}
			else{
				componentType = "CURRENT_LIABILITIES";
			}
			String sqlstmt = "Insert into COMMON_CODE_CATEGORY_ENTRY (ENTRY_ID,ENTRY_CODE,ENTRY_NAME,ACTIVE_STATUS,CATEGORY_CODE,CATEGORY_CODE_ID,ENTRY_SOURCE,COUNTRY,GROUP_ID,REF_ENTRY_CODE,VERSION_TIME,STATUS) values (? ,? ,? ,? ,? ,? ,? ,?, ?, ?, ?, ?)";
			
			
			DBUtil myDBUtil = null;
			ResultSet rs=null;
			try {
				myDBUtil = new DBUtil();
				myDBUtil.setSQL(sqlstmt);
				String componentCodeEntryId1 = "";
				String componentCodeEntryId = "";
				try {
					componentCodeEntryId = new SequenceManager().getSeqNum("COMMON_CODE_CATEGORY_ENTRY_SEQ", true);
					NumberFormat numberFormat = new DecimalFormat("000000000");
					DateFormat df = new SimpleDateFormat("yyyyMMdd");
			        String date = df.format(new Date());
				    componentCodeEntryId1 = numberFormat.format(Long.parseLong(componentCodeEntryId));
					//componentCodeEntryId = date + componentCodeEntryId;	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				myDBUtil.setString(1, componentCodeEntryId1);
				myDBUtil.setString(2, componentCode);
				myDBUtil.setString(3, componentName);
				myDBUtil.setString(4, "1");
				myDBUtil.setString(5, componentType);
				myDBUtil.setString(6, "310");
				myDBUtil.setString(7, null);
				myDBUtil.setString(8, "IN");
				myDBUtil.setString(9, null);
				myDBUtil.setString(10, null);
				myDBUtil.setString(11, "0");
				myDBUtil.setString(12, null);
				myDBUtil.executeUpdate();
			} catch (DBConnectionException e) {
				e.printStackTrace();
			} catch (NoSQLStatementException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finalize(myDBUtil,rs);
			CommonCodeList.refresh(componentType);
			
		}
		
		public void updateComponentinCommonCode (OBComponent actual,OBComponent staging,boolean isNameChanged, boolean isDeleted, boolean isTypeChanged) {
			
			String stagingCompType = staging.getComponentType();
			String stagingComponentType = "";
			if(stagingCompType.equals("Current_Asset"))
			{
				stagingComponentType = "CURRENT_ASSET";
			}
			else{
				stagingComponentType = "CURRENT_LIABILITIES";
			}
			String actualCompType = actual.getComponentType();
			String actualComponentType = "";
			if(actualCompType.equals("Current_Asset"))
			{
				actualComponentType = "CURRENT_ASSET";
			}
			else{
				actualComponentType = "CURRENT_LIABILITIES";
			}
			String stagingComponentName = staging.getComponentName().trim();
			String actualComponentName = actual.getComponentName().trim();
			String sqlstmt = "";
			String newEntryName = "";
			String oldEntryName = "";
			String activeStatus = "";
			String newCategoryCode = "";
			String oldCategoryCode = "";
			if(isNameChanged == true){
				newEntryName = stagingComponentName;
				oldEntryName = actualComponentName;
			}
			else{
				newEntryName = stagingComponentName;
				oldEntryName = stagingComponentName;
			}
		    if (isDeleted == true){
				activeStatus = "0";
			}
		    else{
		    	activeStatus = "1";
		    }
		    if (isTypeChanged == true){
		    	newCategoryCode = stagingComponentType;
				oldCategoryCode = actualComponentType;
			}
		    else{
		    	newCategoryCode = stagingComponentType;
				oldCategoryCode = stagingComponentType;
		    }
		
			sqlstmt = "update common_code_category_entry set entry_name = ? , active_status = ? , category_code = ? where ENTRY_NAME = ? and CATEGORY_CODE = ?";
			
			
			DBUtil myDBUtil = null;
			ResultSet rs=null;
			try {
				myDBUtil = new DBUtil();
				myDBUtil.setSQL(sqlstmt);
		
				myDBUtil.setString(1, newEntryName);
				myDBUtil.setString(2, activeStatus);
				myDBUtil.setString(3, newCategoryCode);
				myDBUtil.setString(4, oldEntryName);
				myDBUtil.setString(5, oldCategoryCode);
				myDBUtil.executeUpdate();
				
			} catch (DBConnectionException e) {
				e.printStackTrace();
			} catch (NoSQLStatementException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finalize(myDBUtil,rs);
		}
		
		
		
		
		private void finalize(DBUtil dbUtil, ResultSet rs) {
			try {
				if (null != rs) {
					rs.close();
				}
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		/**
		 * @return list of files uploaded in staging table of Component.
		 */
		public List getFileMasterList(String searchBy) {
			Map parametersList = new HashMap();
			parametersList.put("transId",searchBy);
			DetachedCriteria criteria = DetachedCriteria.forEntityName("fileMapper").add(Restrictions.allEq(parametersList));
			List listId = getHibernateTemplate().findByCriteria(criteria);
	       return listId;
		}
		
		/**
		 * @return Pagination for uploaded files in Component.
		 */

		public List getAllStageComponent(String searchBy, String login) {
			List resultList = null;
			String searchByValue = searchBy;
			String strId ="";
			List listId = getFileMasterList(searchBy);
			for (int i = 0; i < listId.size(); i++) {
				OBFileMapperMaster map = (OBFileMapperMaster) listId.get(i);
//					System.out.println("val = " + map.getSysId());
					if(!strId.equals("")){
						strId +=",";
					}
					strId += map.getSysId();
			}
			
			if(!strId.equals("")){
				Map parameters = new HashMap();
				parameters.put("deprecated","N");
				parameters.put("id", strId);
				String query = "SELECT FROM "+IComponentDao.STAGE_COMPONENT_NAME+" WHERE DEPRECATED = 'N' AND ID IN ("+strId+")";
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
		
		private String getComponentCode() {
			Query query = currentSession().createSQLQuery("SELECT COMPONENT_SEQ.NEXTVAL FROM dual");
			String sequenceNumber = query.uniqueResult().toString();
			NumberFormat numberFormat = new DecimalFormat("0000000");
			String componentCode = numberFormat.format(Long.parseLong(sequenceNumber));
			componentCode = "COM" + componentCode;		
			return componentCode;
		}
		
		
		
		
		public SearchResult getSearchComponentList(String componentName)throws BaselMasterException {
			ArrayList resultList = new ArrayList();
			try{
				String query = "SELECT FROM "+IComponentDao.ACTUAL_COMPONENT_NAME+" WHERE DEPRECATED='N'";
				if(componentName!=null && !componentName.trim().equals("")){
					query = query + " AND UPPER(COMPONENT_NAME) like '"+ componentName.toUpperCase()+"%' ";
				}
				resultList = (ArrayList) getHibernateTemplate().find(query);
			}
			catch(Exception ex){
				DefaultLogger.error(this, "############# error in getOtherBankList ",ex);
			}
			return new SearchResult(0, resultList.size(), resultList.size(), resultList);
		}
		
		public List getActualComponentList()throws BaselMasterException {
			ArrayList resultList = new ArrayList();
			try{
				String query = "SELECT FROM "+IComponentDao.ACTUAL_COMPONENT_NAME+" WHERE DEPRECATED='N'";
				
				resultList = (ArrayList) getHibernateTemplate().find(query);
			}
			catch(Exception ex){
				DefaultLogger.error(this, "############# error in getOtherBankList ",ex);
			}
			return resultList;
		}
		
		public boolean isUniqueCode(String systemName)
		{
			
			if(!(systemName==null && systemName.trim()=="")){
				String query = "SELECT FROM "+IBaselDao.ACTUAL_BASEL_NAME+" WHERE DEPRECATED='N' AND  SYSTEM='"+systemName+"'";
				ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
				if(resultList.size()>0){
					return true;
			}else 
				return false;
		
			}else{
				throw new BaselMasterException("ERROR- Entity name or key is null ");
			}
		}
		
		
		
		public SearchResult getSearchBaselList(String baselName)throws BaselMasterException {
			ArrayList resultList = new ArrayList();
			try{
				String query = "SELECT FROM "+IBaselDao.ACTUAL_BASEL_NAME+" WHERE DEPRECATED='N'";
				if(baselName!=null && !baselName.trim().equals("")){
					query = query + " AND UPPER(SYSTEM) like '"+ baselName.toUpperCase()+"%' ";
				}
				resultList = (ArrayList) getHibernateTemplate().find(query);
			}
			catch(Exception ex){
				DefaultLogger.error(this, "############# error in getOtherBankList ",ex);
			}
			return new SearchResult(0, resultList.size(), resultList.size(), resultList);
		}

	
	

}
