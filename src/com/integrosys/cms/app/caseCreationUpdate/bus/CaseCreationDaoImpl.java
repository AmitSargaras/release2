package com.integrosys.cms.app.caseCreationUpdate.bus;

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

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.caseBranch.bus.ICaseBranchDao;
import com.integrosys.cms.app.caseCreationUpdate.trx.ICaseCreationTrxValue;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.batch.IncompleteBatchJobException;
/**
 * @author  Abhijit R. 
 * Dao implication of interface CaseCreation
 */

public class CaseCreationDaoImpl extends HibernateDaoSupport implements ICaseCreationDao{
	
	private ICaseCreationDao caseCreationUpdateDao;
	
	public ICaseCreationDao getCaseCreationDao() {
		return caseCreationUpdateDao;
	}
	public void setCaseCreationDao(ICaseCreationDao caseCreationUpdateDao) {
		this.caseCreationUpdateDao = caseCreationUpdateDao;
	}
	
	/**
	  * @return Particular CaseCreation according 
	  * to the id passed as parameter.  
	  * 
	  */

	public ICaseCreation getCaseCreation(String entityName, Serializable key)throws CaseCreationException {
		
		if(!(entityName==null|| key==null)){
		
		return (ICaseCreation) getHibernateTemplate().get(entityName, key);
		}else{
			throw new CaseCreationException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	/**
	 * @return CaseCreation Object
	 * @param Entity Name
	 * @param CaseCreation Object  
	 * This method Updates CaseCreation Object
	 */
	
	public ICaseCreation updateCaseCreation(String entityName, ICaseCreation item)throws CaseCreationException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (ICaseCreation) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new CaseCreationException("ERROR-- Entity Name Or Key is null");
		}
	}
	/**
	 * @return CaseCreation Object
	 * @param Entity Name
	 * @param CaseCreation Object  
	 * This method delete CaseCreation Object
	 */
	
	public ICaseCreation deleteCaseCreation(String entityName, ICaseCreation item)throws CaseCreationException{

		if(!(entityName==null|| item==null)){
			item.setDeprecated("Y");
			item.setStatus("INACTIVE");
			getHibernateTemplate().update(entityName, item);
			return (ICaseCreation) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new CaseCreationException("ERROR-- Entity Name Or Key is null");
		}
	}
	/**
	 * @return CaseCreation Object
	 * @param Entity Name
	 * @param CaseCreation Object  
	 * This method Creates CaseCreation Object
	 */
	public ICaseCreation createCaseCreation(String entityName,
			ICaseCreation caseCreationUpdate)
			throws CaseCreationException {
		if(!(entityName==null|| caseCreationUpdate==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, caseCreationUpdate);
			caseCreationUpdate.setId(key.longValue());
			return caseCreationUpdate;
			}else{
				throw new CaseCreationException("ERROR- Entity name or key is null ");
			}
	}


	
	
	/**
	  * @return Particular CaseCreation according 
	  * to the id passed as parameter.  
	  * 
	  */

	public ICaseCreation load(String entityName, long id)throws CaseCreationException
	{
		if(!(entityName==null|| id==0)){
		return (ICaseCreation)getHibernateTemplate().get(entityName , new Long(id));	
		}else{
			throw new CaseCreationException("ERROR- Entity name or key is null ");
		}
	}
	
	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId insertCaseCreation(String entityName,
			IFileMapperId fileId, ICaseCreationTrxValue trxValue)
			throws CaseCreationException {
		
		if(!(entityName==null|| fileId==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, fileId);
			fileId.setId(key.longValue());
			return fileId;
		}else{
			throw new CaseCreationException("ERROR- Entity name or key is null ");
		}	
	}
	
	/**
	 * @return integer (record count of inserted record)
	 * @param FileMapperMaster
	 * @param userName  
	 * @param result of upload files 
	 * This method Creates FileMapperMaster Object
	 */
	public int insertCaseCreation(IFileMapperMaster fileMapperMaster, String userName, ArrayList result) {
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
				OBCaseCreation obCaseCreation = new OBCaseCreation();
				obCaseCreation.setDescription((String) eachDataMap.get("DESCRIPTION"));
				/*obCaseCreation.setStartDate(df.parse((String) eachDataMap.get("START_DATE").toString()));
				obCaseCreation.setEndDate(df.parse(eachDataMap.get("END_DATE").toString()));
				obCaseCreation.setIsRecurrent((String)eachDataMap.get("IS_RECURRENT").toString());*/
				obCaseCreation.setLastUpdateDate(new Date());
				obCaseCreation.setCreateBy(userName);
				obCaseCreation.setCreationDate(new Date());
				obCaseCreation.setStatus("ACTIVE");
				obCaseCreation.setDeprecated("N");
				obCaseCreation.setLastUpdateBy(userName);
				key = (Long) getHibernateTemplate().save("stageCaseCreation", obCaseCreation);				
				//create FileMapperMaster
				
				IFileMapperMaster fileMapper = new OBFileMapperMaster();
				fileMapper.setFileId(fileMapperMaster.getFileId());
				fileMapper.setSysId(key.longValue());
				fileMapper.setTransId(fileMapperMaster.getTransId());
				if(!(fileMapperMaster==null)){
					key = (Long) getHibernateTemplate().save("fileMapper", fileMapper);
					getHibernateTemplate().get("fileMapper", key);
					}else{
						throw new CaseCreationException("ERROR- Entity name or key is null ");
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
	throws CaseCreationException {
			// TODO Auto-generated method stub
			if(!(entityName==null|| fileId==null)){
		
		Long key = (Long) getHibernateTemplate().save(entityName, fileId);
		fileId.setId(key.longValue());
		return fileId;
		}else{
			throw new CaseCreationException("ERROR- Entity name or key is null ");
		}
	}
	
	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId getInsertFileList(String entityName, Serializable key)
	throws CaseCreationException {
		if(!(entityName==null|| key==null)){
			
			return (IFileMapperId) getHibernateTemplate().get(entityName, key);
			}else{
				throw new CaseCreationException("ERROR-- Entity Name Or Key is null");
		}
	}
	

	/**
	 * @return CaseCreation Object
	 * @param Entity Name
	 * @param sysId  
	 * This method Creates CaseCreation Object
	 */
	public ICaseCreation insertActualCaseCreation(String sysId)
	throws CaseCreationException {
		Map parametersMap = new HashMap();
		parametersMap.put("id", new Long(sysId));
		
		DetachedCriteria criteria = DetachedCriteria.forEntityName("stageCaseCreation").add(Restrictions.allEq(parametersMap));
		List caseCreationUpdateList = getHibernateTemplate().findByCriteria(criteria);
			return (ICaseCreation)caseCreationUpdateList.get(0);
		}
	/**
	 * @return CaseCreation Object
	 * @param Entity Name
	 * @param CaseCreation Object  
	 * This method Creates CaseCreation Object
	 */
	public ICaseCreation insertCaseCreation(String entityName,
			ICaseCreation caseCreationUpdate)
			throws CaseCreationException {
		if(!(entityName==null|| caseCreationUpdate==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, caseCreationUpdate);
			caseCreationUpdate.setId(key.longValue());
			return caseCreationUpdate;
			}else{
				throw new CaseCreationException("ERROR- Entity name or key is null ");
			}
	}
	
	/**
	 * @return Maker check if previous upload is pending.
	 */
	public boolean isPrevFileUploadPending() {

		String rowCount = "FROM OBCMSTrxValue c WHERE c.transactionType='INSERT_CASECREATION'";
		List rowList = getHibernateTemplate().find(rowCount);
		if(rowList.size()>0){
			String sqlQuery = "FROM OBCMSTrxValue trx WHERE trx.transactionType='INSERT_CASECREATION' AND trx.status != 'ACTIVE'";
			List caseCreationUpdateList = getHibernateTemplate().find(sqlQuery);
			if(caseCreationUpdateList.size()>0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return list of files uploaded in staging table of CaseCreation.
	 */
	public List getFileMasterList(String searchBy) {
		Map parametersList = new HashMap();
		parametersList.put("transId",searchBy);
		DetachedCriteria criteria = DetachedCriteria.forEntityName("fileMapper").add(Restrictions.allEq(parametersList));
		List listId = getHibernateTemplate().findByCriteria(criteria);
       return listId;
	}
	
	/**
	 * @return Pagination for uploaded files in CaseCreation.
	 */

	public List getAllStageCaseCreation(String searchBy, String login) {
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
			String query = "SELECT FROM "+ICaseCreationDao.STAGE_CASECREATION_NAME+" WHERE DEPRECATED = 'N' AND ID IN ("+strId+")";
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
		String query = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_CASECREATION' AND obcms.transactionID='"+obFileMapperMaster.getTransId()+"'";
		List list = getHibernateTemplate().find(query);
		obCMSTrxValue = (OBCMSTrxValue)list.get(0);
		getHibernateTemplate().flush();
		getHibernateTemplate().delete(obCMSTrxValue);
	}
	
	
	public List getCaseCreationByBranchCode(String branchCode) {
		List resultList = null;
		DefaultLogger.debug(this, " ===================349===========in  getCaseCreationByBranchCode=================== ");
		
		if(!branchCode.equals("")){
			Map parameters = new HashMap();
			
			String query = "SELECT FROM "+ICaseBranchDao.ACTUAL_CASEBRANCH_NAME+" WHERE DEPRECATED = 'N' AND branchCode IN ('"+branchCode+"')";
			try {
					resultList = getHibernateTemplate().find(query);
				//	if(re)
	
			} catch (Exception e) {
				throw new RelationshipMgrException("ERROR-- While retriving RelationshipMgr");
			}
		}
		return resultList;
	}
}
