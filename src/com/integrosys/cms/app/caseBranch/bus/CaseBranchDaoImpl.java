package com.integrosys.cms.app.caseBranch.bus;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.cms.app.caseBranch.trx.ICaseBranchTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.batch.IncompleteBatchJobException;
/**
 * @author  Abhijit R. 
 * Dao implication of interface CaseBranch
 */

public class CaseBranchDaoImpl extends HibernateDaoSupport implements ICaseBranchDao{
	
	private ICaseBranchDao caseBranchDao;
	
	public ICaseBranchDao getCaseBranchDao() {
		return caseBranchDao;
	}
	public void setCaseBranchDao(ICaseBranchDao caseBranchDao) {
		this.caseBranchDao = caseBranchDao;
	}
	
	/**
	  * @return Particular CaseBranch according 
	  * to the id passed as parameter.  
	  * 
	  */

	public ICaseBranch getCaseBranch(String entityName, Serializable key)throws CaseBranchException {
		
		if(!(entityName==null|| key==null)){
		
		return (ICaseBranch) getHibernateTemplate().get(entityName, key);
		}else{
			throw new CaseBranchException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	/**
	 * @return CaseBranch Object
	 * @param Entity Name
	 * @param CaseBranch Object  
	 * This method Updates CaseBranch Object
	 */
	
	public ICaseBranch updateCaseBranch(String entityName, ICaseBranch item)throws CaseBranchException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (ICaseBranch) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new CaseBranchException("ERROR-- Entity Name Or Key is null");
		}
	}
	/**
	 * @return CaseBranch Object
	 * @param Entity Name
	 * @param CaseBranch Object  
	 * This method delete CaseBranch Object
	 */
	
	public ICaseBranch deleteCaseBranch(String entityName, ICaseBranch item)throws CaseBranchException{

		if(!(entityName==null|| item==null)){
			item.setDeprecated("Y");
			item.setStatus("INACTIVE");
			getHibernateTemplate().update(entityName, item);
			return (ICaseBranch) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new CaseBranchException("ERROR-- Entity Name Or Key is null");
		}
	}
	/**
	 * @return CaseBranch Object
	 * @param Entity Name
	 * @param CaseBranch Object  
	 * This method Creates CaseBranch Object
	 */
	public ICaseBranch createCaseBranch(String entityName,
			ICaseBranch caseBranch)
			throws CaseBranchException {
		if(!(entityName==null|| caseBranch==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, caseBranch);
			caseBranch.setId(key.longValue());
			return caseBranch;
			}else{
				throw new CaseBranchException("ERROR- Entity name or key is null ");
			}
	}


	
	
	/**
	  * @return Particular CaseBranch according 
	  * to the id passed as parameter.  
	  * 
	  */

	public ICaseBranch load(String entityName, long id)throws CaseBranchException
	{
		if(!(entityName==null|| id==0)){
		return (ICaseBranch)getHibernateTemplate().get(entityName , new Long(id));	
		}else{
			throw new CaseBranchException("ERROR- Entity name or key is null ");
		}
	}
	
	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId insertCaseBranch(String entityName,
			IFileMapperId fileId, ICaseBranchTrxValue trxValue)
			throws CaseBranchException {
		
		if(!(entityName==null|| fileId==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, fileId);
			fileId.setId(key.longValue());
			return fileId;
		}else{
			throw new CaseBranchException("ERROR- Entity name or key is null ");
		}	
	}
	
	/**
	 * @return integer (record count of inserted record)
	 * @param FileMapperMaster
	 * @param userName  
	 * @param result of upload files 
	 * This method Creates FileMapperMaster Object
	 */
	public int insertCaseBranch(IFileMapperMaster fileMapperMaster, String userName, ArrayList result) {
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
				OBCaseBranch obCaseBranch = new OBCaseBranch();
				/*obCaseBranch.setDescription((String) eachDataMap.get("DESCRIPTION"));
				obCaseBranch.setStartDate(df.parse((String) eachDataMap.get("START_DATE").toString()));
				obCaseBranch.setEndDate(df.parse(eachDataMap.get("END_DATE").toString()));
				obCaseBranch.setIsRecurrent((String)eachDataMap.get("IS_RECURRENT").toString());
				obCaseBranch.setLastUpdateDate(new Date());
				obCaseBranch.setCreateBy(userName);
				obCaseBranch.setCreationDate(new Date());
				obCaseBranch.setStatus("ACTIVE");
				obCaseBranch.setDeprecated("N");
				obCaseBranch.setLastUpdateBy(userName);*/
				key = (Long) getHibernateTemplate().save("stageCaseBranch", obCaseBranch);				
				//create FileMapperMaster
				
				IFileMapperMaster fileMapper = new OBFileMapperMaster();
				fileMapper.setFileId(fileMapperMaster.getFileId());
				fileMapper.setSysId(key.longValue());
				fileMapper.setTransId(fileMapperMaster.getTransId());
				if(!(fileMapperMaster==null)){
					key = (Long) getHibernateTemplate().save("fileMapper", fileMapper);
					getHibernateTemplate().get("fileMapper", key);
					}else{
						throw new CaseBranchException("ERROR- Entity name or key is null ");
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
	throws CaseBranchException {
			// TODO Auto-generated method stub
			if(!(entityName==null|| fileId==null)){
		
		Long key = (Long) getHibernateTemplate().save(entityName, fileId);
		fileId.setId(key.longValue());
		return fileId;
		}else{
			throw new CaseBranchException("ERROR- Entity name or key is null ");
		}
	}
	
	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId getInsertFileList(String entityName, Serializable key)
	throws CaseBranchException {
		if(!(entityName==null|| key==null)){
			
			return (IFileMapperId) getHibernateTemplate().get(entityName, key);
			}else{
				throw new CaseBranchException("ERROR-- Entity Name Or Key is null");
		}
	}
	

	/**
	 * @return CaseBranch Object
	 * @param Entity Name
	 * @param sysId  
	 * This method Creates CaseBranch Object
	 */
	public ICaseBranch insertActualCaseBranch(String sysId)
	throws CaseBranchException {
		Map parametersMap = new HashMap();
		parametersMap.put("id", new Long(sysId));
		
		DetachedCriteria criteria = DetachedCriteria.forEntityName("stageCaseBranch").add(Restrictions.allEq(parametersMap));
		List caseBranchList = getHibernateTemplate().findByCriteria(criteria);
			return (ICaseBranch)caseBranchList.get(0);
		}
	/**
	 * @return CaseBranch Object
	 * @param Entity Name
	 * @param CaseBranch Object  
	 * This method Creates CaseBranch Object
	 */
	public ICaseBranch insertCaseBranch(String entityName,
			ICaseBranch caseBranch)
			throws CaseBranchException {
		if(!(entityName==null|| caseBranch==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, caseBranch);
			caseBranch.setId(key.longValue());
			return caseBranch;
			}else{
				throw new CaseBranchException("ERROR- Entity name or key is null ");
			}
	}
	
	/**
	 * @return Maker check if previous upload is pending.
	 */
	public boolean isPrevFileUploadPending() {

		String rowCount = "FROM OBCMSTrxValue c WHERE c.transactionType='INSERT_CASEBRANCH'";
		List rowList = getHibernateTemplate().find(rowCount);
		if(rowList.size()>0){
			String sqlQuery = "FROM OBCMSTrxValue trx WHERE trx.transactionType='INSERT_CASEBRANCH' AND trx.status != 'ACTIVE'";
			List caseBranchList = getHibernateTemplate().find(sqlQuery);
			if(caseBranchList.size()>0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return list of files uploaded in staging table of CaseBranch.
	 */
	public List getFileMasterList(String searchBy) {
		Map parametersList = new HashMap();
		parametersList.put("transId",searchBy);
		DetachedCriteria criteria = DetachedCriteria.forEntityName("fileMapper").add(Restrictions.allEq(parametersList));
		List listId = getHibernateTemplate().findByCriteria(criteria);
       return listId;
	}
	
	/**
	 * @return Pagination for uploaded files in CaseBranch.
	 */

	public List getAllStageCaseBranch(String searchBy, String login) {
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
			String query = "SELECT FROM "+ICaseBranchDao.ACTUAL_CASEBRANCH_NAME+" WHERE DEPRECATED = 'N' AND ID IN ("+strId+")";
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
		String query = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_CASEBRANCH' AND obcms.transactionID='"+obFileMapperMaster.getTransId()+"'";
		List list = getHibernateTemplate().find(query);
		obCMSTrxValue = (OBCMSTrxValue)list.get(0);
		getHibernateTemplate().flush();
		getHibernateTemplate().delete(obCMSTrxValue);
	}
	
	public boolean isUniqueCode(String coloumn ,String value)throws Exception
	{
		
		
			String query = "SELECT FROM "+ICaseBranchDao.ACTUAL_CASEBRANCH_NAME+" WHERE Upper("+coloumn+") = '"+value.toUpperCase()+"'";
			ArrayList resultList = (ArrayList) getHibernateTemplate().find(query);
			if(resultList.size()>0){
					return true;
							}else
		return false;
			
		
	}
}
