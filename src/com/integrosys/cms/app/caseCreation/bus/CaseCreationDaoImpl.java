package com.integrosys.cms.app.caseCreation.bus;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.component.notification.bus.OBNotificationRecipient;
/**
 * @author  Abhijit R. 
 * Dao implication of interface Holiday
 */

public class CaseCreationDaoImpl extends HibernateDaoSupport implements ICaseCreationDao{
	
	private ICaseCreationDao caseCreationDao;
	
	public ICaseCreationDao getCaseCreationDao() {
		return caseCreationDao;
	}
	public void setCaseCreationDao(ICaseCreationDao caseCreationDao) {
		this.caseCreationDao = caseCreationDao;
	}
	
	/**
	  * @return Particular CaseCreation according 
	  * to the id passed as parameter.  
	  * 
	  */

	
	
	/**
	 * @return CaseCreation Object
	 * @param Entity Name
	 * @param CaseCreation Object  
	 * This method Updates CaseCreation Object
	 */
	
	public ICaseCreation updateCaseCreation(String entityName, ICaseCreation item)throws Exception{
		if(!(entityName==null|| item==null)){
			getHibernateTemplate().update(entityName, item);
			return  (ICaseCreation) getHibernateTemplate().load(entityName, new Long(item.getId()));
		}else{
			throw new Exception("ERROR-- Entity Name Or Key is null");
		}
	}
	/**
	 * @return CaseCreation Object
	 * @param Entity Name
	 * @param CaseCreation Object  
	 * This method delete CaseCreation Object
	 */
	
		public ICaseCreation createCaseCreation(String entityName,
			ICaseCreation caseCreation)
			throws Exception {
		if(!(entityName==null|| caseCreation==null)){
			
			Long key = (Long) getHibernateTemplate().save(entityName, caseCreation);
			caseCreation.setId(key.longValue());
			return caseCreation;
			}else{
				throw new Exception("ERROR- Entity name or key is null ");
			}
	}

		
		public void createCaseCreationRemark(String entityName,
				ICaseCreationRemark caseCreationRemark)
				throws Exception {
			if(!(entityName==null|| caseCreationRemark==null)){
				
				 getHibernateTemplate().save(entityName, caseCreationRemark);
				
				}else{
					throw new Exception("ERROR- Entity name or key is null ");
				}
		}


	
		public ICaseCreation load(String entityName, long id)throws Exception
	{
		if(!(entityName==null|| id==0)){
		return (ICaseCreation)getHibernateTemplate().get(entityName , new Long(id));	
		}else{
			throw new Exception("ERROR- Entity name or key is null ");
		}
	}
		
		public SearchResult listCaseCreation( String entityName,long caseCreationId)throws Exception {
			try{
				String query = "FROM " + entityName + " caseCreation where caseCreationId="+caseCreationId;
				ArrayList caseCreationList = (ArrayList) getHibernateTemplate().find(query);
				return new SearchResult(0, caseCreationList.size(), caseCreationList.size(),caseCreationList);
				
			}catch (Exception e) {
				DefaultLogger.error(this, "############# error in listcaseCreation",e);
				e.printStackTrace();
				throw new Exception("Unable to List the caseCreation");
			}		
		}
		
		public SearchResult listCaseCreationByLimitProfileId( String entityName,long limitProfileId)throws Exception {
			try{
				String query = "FROM " + entityName + " caseCreation where LIMIT_PROFILE_ID="+limitProfileId;
				ArrayList caseCreationList = (ArrayList) getHibernateTemplate().find(query);
				return new SearchResult(0, caseCreationList.size(), caseCreationList.size(),caseCreationList);
				
			}catch (Exception e) {
				DefaultLogger.error(this, "############# error in listcaseCreation",e);
				e.printStackTrace();
				throw new Exception("Unable to List the caseCreation");
			}		
		}
		
		public ArrayList listStageCaseCreationByLimitProfileId( long limitProfileId)throws Exception {
			
			 DBUtil dbUtil;
			 dbUtil = new DBUtil();
			try{
				
					
				String query = "select stgcaseitem.checklistitemid as checklistitemid from cms_stage_casecreation_item stgcaseitem where stgcaseitem.casecreationid in (select stgcase.id from cms_stage_casecreationupdate stgcase where stgcase.id in (select tr.staging_reference_id from transaction tr where tr.transaction_type='CASECREATION' and tr.from_state in ('ND','PENDING_CREATE','REJECTED') and tr.status in ('PENDING_CREATE','REJECTED'))) and stgcaseitem.limit_profile_id ="+limitProfileId;
				dbUtil.setSQL(query);
				ResultSet rs = dbUtil.executeQuery();
				ArrayList caseCreationList = new ArrayList();
				while (rs.next()) {
					
					caseCreationList.add(rs.getString("checklistitemid"));
				}
			
				
				return caseCreationList;
				
			}
			catch (DBConnectionException dbe) {
				throw new SearchDAOException(dbe);
			}
			catch (NoSQLStatementException ne) {
				throw new SearchDAOException(ne);
			}
			catch (SQLException se) {
				throw new SearchDAOException(se);
			}
			catch (Exception e) {
				DefaultLogger.error(this, "############# error in listcaseCreation",e);
				e.printStackTrace();
				throw new Exception("Unable to List the caseCreation");
			}	
			
			finally {
				try {
					dbUtil.close();
				}
				catch (SQLException e) {
					throw new SearchDAOException(e);
				}
			}
		}
		
		public SearchResult listCaseCreationRemark( String entityName,long limitProfileId)throws Exception {
			try{
				String query = "FROM " + entityName + " caseCreationRemark where caseCreationRemark.limitProfileId="+limitProfileId;
				ArrayList caseCreationList = (ArrayList) getHibernateTemplate().find(query);
				return new SearchResult(0, caseCreationList.size(), caseCreationList.size(),caseCreationList);
				
			}catch (Exception e) {
				DefaultLogger.error(this, "############# error in listcaseCreation",e);
				e.printStackTrace();
				throw new Exception("Unable to List the caseCreation");
			}		
		}
		public ICaseCreation getCaseCreation(String entityName, long caseId, long checklistitemid)
				throws Exception {
			try{
				String query = "FROM " + entityName + " caseCreation where casecreationid ="+caseId+" and checklistitemid="+checklistitemid;
				ArrayList caseCreationList = (ArrayList) getHibernateTemplate().find(query);
				for(int k=0;k<caseCreationList.size();k++){
					ICaseCreation caseCreation2=(ICaseCreation)caseCreationList.get(k);
					return caseCreation2;
				}
				
				
			}catch (Exception e) {
				DefaultLogger.error(this, "############# error in getCaseCreation",e);
				e.printStackTrace();
				throw new Exception("Unable to getCaseCreation  ");
			}
			return null;		
		}
		
		public ArrayList getStageCaseCreation(String entityName, long key)
		throws Exception {
				try{
					String query = "FROM " + entityName + " caseCreation where checklistitemid="+key;
					ArrayList caseCreationList = (ArrayList) getHibernateTemplate().find(query);
					/*for(int k=0;k<caseCreationList.size();k++){
						ICaseCreation caseCreation2=(ICaseCreation)caseCreationList.get(k);
						return caseCreation2;
					}*/
					if(caseCreationList!=null){
					return caseCreationList;
					}
				}catch (Exception e) {
					DefaultLogger.error(this, "############# error in getCaseCreation",e);
					e.printStackTrace();
					throw new Exception("Unable to getCaseCreation  ");
				}
				return null;		
			}
		
		
		public void updateImageTagUntagStatus(String imageId,String checklistId,String status){
			DBUtil dbUtil = null;
			ResultSet rs=null;
			String sql = "UPDATE cms_image_tag_map SET UNTAGGED_STATUS = '"+status+"' " + 
					" WHERE image_id='"+imageId+"' and tag_id IN  " + 
					"      (SELECT id  " + 
					"        FROM cms_image_tag_details  " + 
					"				WHERE DOC_DESC =  " + 
					"			(SELECT TO_CHAR(doc_item_id) " + 
					"		FROM cms_checklist_item  " + 
					"		WHERE DOC_ITEM_ID='"+checklistId+"' " + 
					"  )  " + 
					") ";
			System.out.println("Query in CaseCreationdaoImpl for updating imgae tag untag status = "+sql);
			try{
						dbUtil=new DBUtil();
						dbUtil.setSQL(sql);
						int noOfRecords=dbUtil.executeUpdate();
						DefaultLogger.debug(this, "Updated "+noOfRecords +" Records UNTAGGED_STATUS to "+status);		
						
				}  catch (SQLException e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in updateImageTagUntagStatus in CaseCreationDaoImpl:"+e.getMessage());
				}catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.debug(this, "Exception in updateImageTagUntagStatus in CaseCreationDaoImpl:"+e.getMessage());
				}
			finally{ 
				try {
					if(rs != null) {
						rs.close();
					}
					dbUtil.close();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
//				finalize(dbUtil,rs);
				
			}
		
	
	}
