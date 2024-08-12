package com.integrosys.cms.app.fccBranch.bus;

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

import com.integrosys.cms.app.fccBranch.trx.IFCCBranchTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.state.bus.IStateDAO;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.batch.IncompleteBatchJobException;
/**
 * @author  Abhijit R. 
 * Dao implication of interface FCCBranch
 */

public class FCCBranchDaoImpl extends HibernateDaoSupport implements IFCCBranchDao{
	
	private IFCCBranchDao fccBranchDao;
	
	
	
	/**
	 * @return the fccBranchDao
	 */
	public IFCCBranchDao getFccBranchDao() {
		return fccBranchDao;
	}

	/**
	 * @param fccBranchDao the fccBranchDao to set
	 */
	public void setFccBranchDao(IFCCBranchDao fccBranchDao) {
		this.fccBranchDao = fccBranchDao;
	}

	/**
	  * @return Particular FCCBranch according 
	  * to the id passed as parameter.  
	  * 
	  */

	public IFCCBranch getFCCBranch(String entityName, Serializable key)throws FCCBranchException {
		
		if(!(entityName==null|| key==null)){
		
		return (IFCCBranch) getHibernateTemplate().get(entityName, key);
		}else{
			throw new FCCBranchException("ERROR-- Entity Name Or Key is null");
		}
	}
	
	/**
	 * @return FCCBranch Object
	 * @param Entity Name
	 * @param FCCBranch Object  
	 * This method Updates FCCBranch Object
	 */
	
	public IFCCBranch updateFCCBranch(String entityName, IFCCBranch item)throws FCCBranchException{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (IFCCBranch) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new FCCBranchException("ERROR-- Entity Name Or Key is null");
		}
	}
	/**
	 * @return FCCBranch Object
	 * @param Entity Name
	 * @param FCCBranch Object  
	 * This method delete FCCBranch Object
	 */
	
	public IFCCBranch deleteFCCBranch(String entityName, IFCCBranch item)throws FCCBranchException{

		if(!(entityName==null|| item==null)){
			item.setDeprecated("Y");
			item.setStatus("INACTIVE");
			getHibernateTemplate().update(entityName, item);
			return (IFCCBranch) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new FCCBranchException("ERROR-- Entity Name Or Key is null");
		}
	}
	/**
	 * @return FCCBranch Object
	 * @param Entity Name
	 * @param FCCBranch Object  
	 * This method Creates FCCBranch Object
	 */
	public IFCCBranch createFCCBranch(String entityName,
			IFCCBranch fccBranch)
			throws FCCBranchException {
		if(!(entityName==null|| fccBranch==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, fccBranch);
			fccBranch.setId(key.longValue());
			return fccBranch;
			}else{
				throw new FCCBranchException("ERROR- Entity name or key is null ");
			}
	}


	
	
	/**
	  * @return Particular FCCBranch according 
	  * to the id passed as parameter.  
	  * 
	  */

	public IFCCBranch load(String entityName, long id)throws FCCBranchException
	{
		if(!(entityName==null|| id==0)){
		return (IFCCBranch)getHibernateTemplate().get(entityName , new Long(id));	
		}else{
			throw new FCCBranchException("ERROR- Entity name or key is null ");
		}
	}
	
	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId insertFCCBranch(String entityName,
			IFileMapperId fileId, IFCCBranchTrxValue trxValue)
			throws FCCBranchException {
		
		if(!(entityName==null|| fileId==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, fileId);
			fileId.setId(key.longValue());
			return fileId;
		}else{
			throw new FCCBranchException("ERROR- Entity name or key is null ");
		}	
	}
	
	/**
	 * @return integer (record count of inserted record)
	 * @param FileMapperMaster
	 * @param userName  
	 * @param result of upload files 
	 * This method Creates FileMapperMaster Object
	 */
	public int insertFCCBranch(IFileMapperMaster fileMapperMaster, String userName, ArrayList result) {
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
				OBFCCBranch obFCCBranch = new OBFCCBranch();
				/*obFCCBranch.setDescription((String) eachDataMap.get("DESCRIPTION"));
				obFCCBranch.setStartDate(df.parse((String) eachDataMap.get("START_DATE").toString()));
				obFCCBranch.setEndDate(df.parse(eachDataMap.get("END_DATE").toString()));
				obFCCBranch.setIsRecurrent((String)eachDataMap.get("IS_RECURRENT").toString());
				obFCCBranch.setLastUpdateDate(new Date());
				obFCCBranch.setCreateBy(userName);
				obFCCBranch.setCreationDate(new Date());
				obFCCBranch.setStatus("ACTIVE");
				obFCCBranch.setDeprecated("N");
				obFCCBranch.setLastUpdateBy(userName);*/
				key = (Long) getHibernateTemplate().save("stageFCCBranch", obFCCBranch);				
				//create FileMapperMaster
				
				IFileMapperMaster fileMapper = new OBFileMapperMaster();
				fileMapper.setFileId(fileMapperMaster.getFileId());
				fileMapper.setSysId(key.longValue());
				fileMapper.setTransId(fileMapperMaster.getTransId());
				if(!(fileMapperMaster==null)){
					key = (Long) getHibernateTemplate().save("fileMapper", fileMapper);
					getHibernateTemplate().get("fileMapper", key);
					}else{
						throw new FCCBranchException("ERROR- Entity name or key is null ");
					}
				noOfRecInserted++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IncompleteBatchJobException(
					"Unable to update/insert data retrived form CSV file");

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
	throws FCCBranchException {
			// TODO Auto-generated method stub
			if(!(entityName==null|| fileId==null)){
		
		Long key = (Long) getHibernateTemplate().save(entityName, fileId);
		fileId.setId(key.longValue());
		return fileId;
		}else{
			throw new FCCBranchException("ERROR- Entity name or key is null ");
		}
	}
	
	/**
	 * @return FileMapperId Object
	 * @param Entity Name
	 * @param FileMapperId Object  
	 * This method Creates FileMapperId Object
	 */
	public IFileMapperId getInsertFileList(String entityName, Serializable key)
	throws FCCBranchException {
		if(!(entityName==null|| key==null)){
			
			return (IFileMapperId) getHibernateTemplate().get(entityName, key);
			}else{
				throw new FCCBranchException("ERROR-- Entity Name Or Key is null");
		}
	}
	

	/**
	 * @return FCCBranch Object
	 * @param Entity Name
	 * @param sysId  
	 * This method Creates FCCBranch Object
	 */
	public IFCCBranch insertActualFCCBranch(String sysId)
	throws FCCBranchException {
		Map parametersMap = new HashMap();
		parametersMap.put("id", new Long(sysId));
		
		DetachedCriteria criteria = DetachedCriteria.forEntityName("stageFCCBranch").add(Restrictions.allEq(parametersMap));
		List caseBranchList = getHibernateTemplate().findByCriteria(criteria);
			return (IFCCBranch)caseBranchList.get(0);
		}
	/**
	 * @return FCCBranch Object
	 * @param Entity Name
	 * @param FCCBranch Object  
	 * This method Creates FCCBranch Object
	 */
	public IFCCBranch insertFCCBranch(String entityName,
			IFCCBranch caseBranch)
			throws FCCBranchException {
		if(!(entityName==null|| caseBranch==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, caseBranch);
			caseBranch.setId(key.longValue());
			return caseBranch;
			}else{
				throw new FCCBranchException("ERROR- Entity name or key is null ");
			}
	}
	
	/**
	 * @return Maker check if previous upload is pending.
	 */
	public boolean isPrevFileUploadPending() {

		String rowCount = "FROM OBCMSTrxValue c WHERE c.transactionType='INSERT_FCCBRANCH'";
		List rowList = getHibernateTemplate().find(rowCount);
		if(rowList.size()>0){
			String sqlQuery = "FROM OBCMSTrxValue trx WHERE trx.transactionType='INSERT_FCCBRANCH' AND trx.status != 'ACTIVE'";
			List caseBranchList = getHibernateTemplate().find(sqlQuery);
			if(caseBranchList.size()>0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return list of files uploaded in staging table of FCCBranch.
	 */
	public List getFileMasterList(String searchBy) {
		Map parametersList = new HashMap();
		parametersList.put("transId",searchBy);
		DetachedCriteria criteria = DetachedCriteria.forEntityName("fileMapper").add(Restrictions.allEq(parametersList));
		List listId = getHibernateTemplate().findByCriteria(criteria);
       return listId;
	}
	
	/**
	 * @return Pagination for uploaded files in FCCBranch.
	 */

	public List getAllStageFCCBranch(String searchBy, String login) {
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
			String query = "SELECT FROM "+IFCCBranchDao.ACTUAL_FCCBRANCH_NAME+" WHERE DEPRECATED = 'N' AND ID IN ("+strId+")";
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
		String query = "FROM OBCMSTrxValue obcms WHERE obcms.transactionType='INSERT_FCCBRANCH' AND obcms.transactionID='"+obFileMapperMaster.getTransId()+"'";
		List list = getHibernateTemplate().find(query);
		obCMSTrxValue = (OBCMSTrxValue)list.get(0);
		getHibernateTemplate().flush();
		getHibernateTemplate().delete(obCMSTrxValue);
	}
	
	
	
	
	
public String fccBranchUniqueCombination(String branchCode, String aliasBranchCode,long id) throws FCCBranchException {
		
		String fccBranchUniqueCheck = "false";
		String aliasBranchUniqueCheck = "false";
		
		String stagingQuery = "SELECT FROM "+IFCCBranchDao.STAGE_FCCBRANCH_NAME+" WHERE UPPER(BRANCHCODE) = '"+branchCode.toUpperCase()+"'";
		ArrayList stagingResultList = (ArrayList) getHibernateTemplate().find(stagingQuery);
		if( stagingResultList.size() > 0 ){
			String actualQuery = null;
			if(id!=0 && !"".equals(id))
			actualQuery = "SELECT FROM "+IFCCBranchDao.ACTUAL_FCCBRANCH_NAME+" WHERE UPPER(BRANCHCODE) = '"+branchCode.toUpperCase()+"' and id != "+id;
			else
				actualQuery = "SELECT FROM "+IFCCBranchDao.ACTUAL_FCCBRANCH_NAME+" WHERE UPPER(BRANCHCODE) = '"+branchCode.toUpperCase()+"'";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if( actualResultList.size() > 0 )
				fccBranchUniqueCheck = "false";
			else
				fccBranchUniqueCheck = "true";
		}	
		else 
			fccBranchUniqueCheck = "true";
		
		String stagingAliasBranchQuery = "SELECT FROM "+IFCCBranchDao.STAGE_FCCBRANCH_NAME+" WHERE UPPER(ALIASBRANCHCODE) = '"+aliasBranchCode.toUpperCase()+"'";
		ArrayList stagingAliasResultList = (ArrayList) getHibernateTemplate().find(stagingAliasBranchQuery);
		if( stagingAliasResultList.size() > 0 ){
			String actualQuery = null;
			if(id!=0 && !"".equals(id))
			actualQuery = "SELECT FROM "+IFCCBranchDao.ACTUAL_FCCBRANCH_NAME+" WHERE UPPER(ALIASBRANCHCODE) = '"+aliasBranchCode.toUpperCase()+"' and id != "+id;
			else
				actualQuery = "SELECT FROM "+IFCCBranchDao.ACTUAL_FCCBRANCH_NAME+" WHERE UPPER(ALIASBRANCHCODE) = '"+aliasBranchCode.toUpperCase()+"'";
			ArrayList actualResultList = (ArrayList) getHibernateTemplate().find(actualQuery);
			if( actualResultList.size() > 0 )
				aliasBranchUniqueCheck = "false";
			else
				aliasBranchUniqueCheck = "true";
		}	
		else 
			aliasBranchUniqueCheck = "true"; 	
		
		if(fccBranchUniqueCheck.equals("false"))
			return "fccBranch";
		
		else if(aliasBranchUniqueCheck.equals("false"))
			return "aliasBranch";
		
		return "true";
	}

public List getFccBranchList(){
	List fccBranchList = new ArrayList();
	try{
		
		String query = "SELECT FROM "+IFCCBranchDao.ACTUAL_FCCBRANCH_NAME+" WHERE STATUS!='INACTIVE' AND DEPRECATED!='Y' ";
		fccBranchList = (List) getHibernateTemplate().find(query);
	}catch (Exception e) {
		e.printStackTrace();
	}
	return fccBranchList;
}
}
