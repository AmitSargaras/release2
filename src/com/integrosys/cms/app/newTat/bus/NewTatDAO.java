package com.integrosys.cms.app.newTat.bus;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.holiday.bus.IHoliday;

/**
 * DAO for Checklist
 * @author $Author: Abhijit Rudrakshawar $
 * @version $Revision: 1.0 $
 * @since $Date: 19-sep-2013 $
 */

public class NewTatDAO extends HibernateDaoSupport implements INewTatDAO {
	private DBUtil dbUtil = null;

	private INewTatDAO newTatDao;

	public INewTatDAO getNewTatDao() {
		return newTatDao;
	}
	public void setNewTatDao(INewTatDAO newTatDao) {
		this.newTatDao = newTatDao;
	}

	private static final String QUERY_LIST_NEW_TAT = "SELECT * FROM CMS_NEW_TAT  where status != 'CLOSED' "+
         " order by decode    "+
         "(status, 'DOCUMENT_SUBMITTED',1,"+
         " 'DOCUMENT_SCANNED',2,"+
         " 'DOCUMENT_RECEIVED',3,"+
         " 'DEFERRAL_RAISED',4,"+
		 " 'DEFERRAL_APPROVED',5,"+
		 " 'DOCUMENT_RESCANNED',6,"+
		 " 'DOCUMENT_RERECEIVED',7,"+
		 " 'CLIMS_UPDATED',8,"+
		 " 'LIMIT_RELEASED',9)";

	public INewTat createTAT(String entityName,
			INewTat tat,long id)
	throws TatException {
		if(!(entityName==null|| tat==null)){
			if(id == 0){
				Long key = (Long) getHibernateTemplate().save(entityName, tat);
				tat.setId(key.longValue());
				tat.setCaseId(key.longValue());
			}
			else{
				Long key = (Long) getHibernateTemplate().save(entityName, tat);
				tat.setId(key.longValue());
				tat.setCaseId(id);
			}
			return tat;
		}else{
			throw new TatException("ERROR- Entity name or key is null ");
		}
	}

	public void updateTAT(String entityName,
			INewTat tat,long id)
	throws TatException {
		if((entityName==null || tat==null || entityName.equals("actualNewTAT"))){
			getHibernateTemplate().update(entityName, tat);
			getHibernateTemplate().flush();
		}

		else if((entityName==null || tat==null || entityName.equals("stageNewTAT"))){

			try {
				INewTat newTat = (INewTat)tat.clone();
				Long key = (Long) getHibernateTemplate().save(entityName, newTat);
				tat.setId(key.longValue());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		else{
			throw new TatException("ERROR- Entity name or key is null ");
		}
	}


	public INewTat getTat(String entityName, Serializable key)throws TatException {

		if(!(entityName==null|| key==null)){

			return (INewTat) getHibernateTemplate().get(entityName, key);
		}else{
			throw new TatException("ERROR-- Entity Name Or Key is null");
		}
	}
	public INewTat load(String entityName, long id)throws TatException
	{
		if(!(entityName==null|| id==0)){
			return (INewTat)getHibernateTemplate().get(entityName , new Long(id));	
		}else{
			throw new HolidayException("ERROR- Entity name or key is null ");
		}
	}

	public ArrayList getListNewTat() throws SearchDAOException {
		DefaultLogger.info(this, "IN method getListNewTat()");

		String sql = QUERY_LIST_NEW_TAT;

		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList newTatList = new ArrayList();
			while(rs.next()){
				INewTat iNewTat= new OBNewTat();

				iNewTat.setId(                  rs.getLong("ID"));                     
				iNewTat.setVersionTime(        	rs.getLong("VERSION_TIME"));           
				iNewTat.setCreateBy(           	rs.getString("CREATE_BY"));              
				iNewTat.setCreationDate(       	rs.getTimestamp("CREATION_DATE"));          
				iNewTat.setLastUpdateBy(      	rs.getString("LAST_UPDATE_BY"));         
				iNewTat.setLastUpdateDate(     	rs.getTimestamp("LAST_UPDATE_DATE"));       
				iNewTat.setDeprecated(          rs.getString("DEPRECATED"));             
				iNewTat.setStatus(              rs.getString("STATUS"));                 
				iNewTat.setCmsLeMainProfileId( 	rs.getLong("CMS_LE_MAIN_PROFILE_ID")); 
				iNewTat.setLspLeId(            	rs.getString("LSP_LE_ID"));              
				iNewTat.setLspShortName(        rs.getString("LSP_SHORT_NAME"));         
				iNewTat.setCaseId(              rs.getLong("CASE_ID"));                
				iNewTat.setModule(              rs.getString("MODULE"));                 
				iNewTat.setCaseInitiator(       rs.getString("CASE_INITIATOR"));         
				iNewTat.setRelationshipManager(	rs.getString("RELATIONSHIP_MANAGER"));   
				iNewTat.setDocStatus(		    rs.getString("DOC_STATUS"));             
				iNewTat.setRemarks(             rs.getString("REMARKS"));                
				iNewTat.setFacilityCategory(   	rs.getString("FACILITY_CATEGORY"));      
				iNewTat.setFacilityName(       	rs.getString("FACILITY_NAME"));          
				iNewTat.setCamType             (rs.getString("CAM_TYPE"));               
				iNewTat.setDeferralType(       	rs.getString("DEFERRAL_TYPE"));          
				iNewTat.setLssCoordinatorBranch(rs.getString("LSS_COORDINATOR_BRANCH")); 
				iNewTat.setType(                rs.getString("TYPE"));                   
				iNewTat.setActivityTime        (rs.getTimestamp("ACTIVITY_TIME"));          
				iNewTat.setActualActivityTime( 	rs.getTimestamp("ACTUAL_ACTIVITY_TIME"));   
				iNewTat.setFacilitySystem(            rs.getString("FACILITY_SYSTEM"));   
				iNewTat.setFacilityManual(            rs.getString("FACILITY_MANUAL"));   
				iNewTat.setAmount(             	rs.getString("AMOUNT"));                 
				iNewTat.setCurrency(          	rs.getString("CURRENCY"));               
				iNewTat.setLineNumber(         	rs.getString("LINE_NUMBER"));            
				iNewTat.setSerialNumber(       	rs.getString("SERIAL_NUMBER"));  
				iNewTat.setRegion(       	    rs.getString("REGION"));   
				iNewTat.setSegment(       	    rs.getString("SEGMENT"));   
				iNewTat.setDelayReason(       	    rs.getString("DELAY_REASON")); 
				iNewTat.setDelayReasonText(rs.getString("DELAY_REASON_TEXT"));   
				iNewTat.setRmRegion(       	    rs.getString("RM_REGION"));   
				newTatList.add(iNewTat);
			}



			rs.close();
			return newTatList;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getLimitProfileCollateralCount", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getLimitProfileCollateralCount", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getLimitProfileCollateralCount", ex);
			}
		}
	}
	
	public void createTATReportCaseBase(String entityName,OBNewTatReportCase tatReportCase,long id)	throws TatException {
		if(!(entityName==null|| tatReportCase==null)){
			if(id == 0){
				Long key = (Long) getHibernateTemplate().save(entityName, tatReportCase);
				tatReportCase.setId(key.longValue());
				
			}
			else{
				 getHibernateTemplate().update(entityName, tatReportCase);
				 getHibernateTemplate().flush();
				
				
			}
			
		}else{
			throw new TatException("ERROR- Entity name or key is null ");
		}
	}

}
