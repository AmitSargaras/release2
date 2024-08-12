package com.integrosys.cms.ui.manualinput.limit.othercovenantsdetails;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.customer.bus.OBBankingMethod;
import com.integrosys.cms.app.limit.bus.OBOtherCovenant;
import com.integrosys.cms.app.udf.bus.IUdfDao;
import com.integrosys.cms.app.udf.bus.UdfException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;


public class OtherCovenantDetailsDAOImpl  extends HibernateDaoSupport implements IOtherCovenantDetailsDAO
{
	public String getEntityName(){
		return IOtherCovenantDetailsDAO.ACTUAL_OTHER_COVENANT_DETAILS;
	}
	
	public String getStageEntityName(){
		return IOtherCovenantDetailsDAO.STAGING_OTHER_COVENANT_DETAILS; 
	}
	
	public String getStageEntityValuesName(){
		return IOtherCovenantDetailsDAO.STAGING_OTHER_COVENANT_DETAILS_VALUES ; 
	}
	
	public String getEntityValuesName(){
		return IOtherCovenantDetailsDAO.ACTUAL_OTHER_COVENANT_DETAILS_VALUES;
	}
	@Override
	public void insertOtherCovenantDetailsStage(OBOtherCovenant obothercovenant) {
		try {
				System.out.println("Going for insertOtherCovenantDetailsStage => save data ino table => stagingotherCovenantDetails=> getStageEntityName() => "+getStageEntityName());
				getHibernateTemplate().save(getStageEntityName(), obothercovenant);
			
		} catch (Exception e) {
			System.out.println("Exception in insertOtherCovenantDetailsStage.");
			e.printStackTrace();
		}finally {
			getHibernateTemplate().flush();
		}
	
	}
	
	@Override
	public void insertOtherCovenantDetailsActual(OBOtherCovenant obothercovenant) {
		try {

//			long nextSequenceSql = getSequence("CMS_BANKING_METHOD_SEQ");
			//obj.setBankId(nextSequenceSql);
				System.out.println("Going for insertOtherCovenantDetailsActual => save data ino table => actualOtherCovenantDetails");
				getHibernateTemplate().save("actualOtherCovenantDetails", obothercovenant);
			
		} catch (Exception e) {
			System.out.println("Exception in insertOtherCovenantDetailsActual.");
			e.printStackTrace();
		}finally {
			getHibernateTemplate().flush();
		}
	}
	
	
	
	public void updateOtherCovenantDetailsActual(OBOtherCovenant obothercovenant) {
		try {

//			long nextSequenceSql = getSequence("CMS_BANKING_METHOD_SEQ");
			//obj.setBankId(nextSequenceSql);
				System.out.println("Going for updateOtherCovenantDetailsActual => update data into table => actualOtherCovenantDetails");
				getHibernateTemplate().saveOrUpdate("actualOtherCovenantDetails", obothercovenant);
			
		} catch (Exception e) {
			System.out.println("Exception in insertOtherCovenantDetailsActual.");
			e.printStackTrace();
		}finally {
			getHibernateTemplate().flush();
		}
	}
	public void updateOtherCovenantDetailsActualValues(OBOtherCovenant obothercovenant) {

		try {
				System.out.println("Going for updateOtherCovenantDetailsActualValues => save/update data into table => getEntityValuesName() => "+getEntityValuesName());
				getHibernateTemplate().saveOrUpdate(getEntityValuesName(), obothercovenant);
			
		} catch (Exception e) {
			System.out.println("Exception in updateOtherCovenantDetailsActualValues.");
			e.printStackTrace();
		}finally {
			getHibernateTemplate().flush();
		}
	
	
	}
		
	@Override
	public List getOtherCovenantDetailsStaging(String CAMrefNO) {
		try {
			String query="FROM stagingotherCovenantDetails WHERE CMS_LE_LMT_PROFILE_ID ='"+CAMrefNO+"'" ;
			ArrayList list =(ArrayList) getHibernateTemplate().find(query);
			if(!list.isEmpty()) {
			return list;
			}
		}
		catch (Exception e) {
	        e.printStackTrace();
	    }
		
return null;
	}
	
	public List getFaciltyNameForOtherCovenant(String cmsLmtProID){
		List faciltyNameList = new ArrayList();
		String query = "FROM actualLimit where CMS_LIMIT_PROFILE_ID='"+cmsLmtProID+"'" ;
		faciltyNameList = (ArrayList) getHibernateTemplate().find(query);
	    return faciltyNameList;
	}
	
	@Override
	public List getOtherCovenantDetailsActual(String refid) {
		try {
			String query="FROM actualOtherCovenantDetails WHERE CMS_LE_STATUS='ACTIVE' AND CMS_LE_LMT_PROFILE_ID ='"+refid+"'";
			ArrayList list =(ArrayList) getHibernateTemplate().find(query);
			if(!list.isEmpty()) {
			return list;
			}
		}
		catch (Exception e) {
	        e.printStackTrace();
	    }
		return null;
	}
	@Override
	public void disableOtherCovenantDetails(String referenceId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getOtherCovenantDetailsStagingIdFromSeq() {
		String othercovenantId = null;
		try {
		othercovenantId= (new SequenceManager()).getSeqNum("STAGE_SCI_LSP_OTHER_COVENANT_SEQ", true);
			System.out.println("------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.OtherCovenantDetailsStagingId from DB SEQ"+othercovenantId);
		} catch (Exception e1) {
			System.out.println("Exception while fetching Seq");
			e1.printStackTrace();
		}
	return othercovenantId;
	}
	
	@Override
	public String getOtherCovenantDetailsActualIdFromSeq() {
		String othercovenantId = null;
		try {
		othercovenantId= (new SequenceManager()).getSeqNum("SCI_LSP_OTHER_COVENANT_SEQ", true);
			System.out.println("------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.CovenantDetailsActualId from DB SEQ"+othercovenantId);
		} catch (Exception e1) {
			System.out.println("Exception while fetching Seq");
			e1.printStackTrace();
		}
	return othercovenantId;
	}
	private long getSequence(String seqName){
		long seqId=0l;
		try
		{
			seqId = Long.parseLong((new SequenceManager()).getSeqNum(seqName, true));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return seqId;
	}
	
	@Override
	public void insertStageOtherCovenantDetailsValues(OBOtherCovenant obothercovenant) {
		try {
				System.out.println("Going for insertStageOtherCovenantDetailsValues => save data into table => stagingotherCovenantDetailsValues=> getStageEntityName() => "+getStageEntityName());
				getHibernateTemplate().save(getStageEntityValuesName(), obothercovenant);
			
		} catch (Exception e) {
			System.out.println("Exception in insertStageOtherCovenantDetailsValues.");
			e.printStackTrace();
		}finally {
			getHibernateTemplate().flush();
		}
	
	}
	
	@Override
	public List getOtherCovenantDetailsValuesStaging(String StagingOCid) {
		try {
			String query="FROM stagingotherCovenantDetailsValues WHERE CMS_LE_STAGE_OC_ID ='"+StagingOCid+"' AND STATUS='ACTIVE'";
			ArrayList list =(ArrayList) getHibernateTemplate().find(query);
			if(!list.isEmpty()) {
			return list;
			}
		} 
		catch (Exception e) {
			System.out.println("Exception in getOtherCovenantDetailsValuesStaging."+e.getMessage());
	        e.printStackTrace();
	    }
		
return null;
	}
	@Override
	public List getOtherCovenantDetailsValuesActualList(String UniqueSeqNumFromOC)
	{

		try {
			String query="FROM "+getEntityValuesName()+" where UNIQUE_SEQ_FROM_OC ='"+UniqueSeqNumFromOC+"'" ;
			ArrayList list =(ArrayList) getHibernateTemplate().find(query);
			if(!list.isEmpty()) {
			return list;
			}
		}
		catch (Exception e) {
			System.out.println("Exception in getOtherCovenantDetailsValuesActualList."+e.getMessage());
	        e.printStackTrace();
	    }
		
return null;
	
	}
	@Override
	public String getOtherCovenantDetailsValuesActual(String ActualOCid) {
		String othercovenantdetailsvalues = "";
		ResultSet rs=null;
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			String query="SELECT DISTINCT CCCE.ENTRY_CODE AS ENTRY_CODE,\r\n" + 
					"  CCCE.ENTRY_NAME      AS ENTRY_NAME\r\n" + 
					"FROM SCI_LSP_OTHER_COVENANT_VALUES SLOCV,\r\n" + 
					"  COMMON_CODE_CATEGORY_ENTRY CCCE\r\n" + 
					"WHERE SLOCV.COVENANT_MONITORING_RESP_VALUE = CCCE.ENTRY_CODE\r\n" + 
					"AND CCCE.CATEGORY_CODE             = 'MONITORING_RESPONSIBILITY'\r\n" + 
					"AND CCCE.ACTIVE_STATUS             = 1\r\n" +
					"AND SLOCV.STATUS             = 'ACTIVE'\r\n" +
					"AND SLOCV.CMS_LE_STAGE_OC_ID       =  ? " ;
			dbUtil.setSQL(query);
			dbUtil.setString(1, ActualOCid);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				othercovenantdetailsvalues = othercovenantdetailsvalues + rs.getString("ENTRY_CODE") + "-" +  rs.getString("ENTRY_NAME") + ",";
			}
		}
		catch (Exception e) {
			System.out.println("Exception in getOtherCovenantDetailsValuesActual."+e.getMessage());
	        e.printStackTrace();
	    }
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
			}
			catch (SQLException e) {
				DefaultLogger.warn(this, "EXCEPTION when closing DB UTIL!", e);
			}
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException e) {
				DefaultLogger.warn(this, "EXCEPTION when closing DB UTIL!", e);
			}
		}
		return othercovenantdetailsvalues;
	}
	
	
	@Override
	public String getOtherCovenantDetailsFacilityValuesActual(String ActualOCid) {
		String othercovenantdetailsvalues = "";
		ResultSet rs=null;
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			
			String query1="SELECT Distinct SLAL.FACILITY_NAME AS FACILITY_NAME,\r\n" + 
					"  SLAL.FACILITY_NAME      AS FACILITY_NAME2\r\n" + 
					"FROM SCI_LSP_OTHER_COVENANT_VALUES SLOCV,\r\n" + 
					"   SCI_LSP_APPR_LMTS SLAL\r\n" + 
					"WHERE SLOCV.COVENANT_FACILITY_NAME_VALUE = SLAL.FACILITY_NAME\r\n" +
					"AND SLAL.CMS_LIMIT_STATUS             = 'ACTIVE'\r\n" +
					"AND SLOCV.STATUS             = 'ACTIVE'\r\n" +
					"AND SLOCV.CMS_LE_STAGE_OC_ID               =  ? ";
			dbUtil.setSQL(query1);
			dbUtil.setString(1, ActualOCid);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				othercovenantdetailsvalues = othercovenantdetailsvalues  +rs.getString("FACILITY_NAME") + ",";
			}
		}
		catch (Exception e) {
			System.out.println("Exception in getOtherCovenantDetailsValuesActual."+e.getMessage());
	        e.printStackTrace();
	    }
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
			}
			catch (SQLException e) {
				DefaultLogger.warn(this, "EXCEPTION when closing DB UTIL!", e);
			}
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException e) {
				DefaultLogger.warn(this, "EXCEPTION when closing DB UTIL!", e);
			}
		}
		return othercovenantdetailsvalues;
	}
	
	@Override
	public String getOtherCovenantDetailsFacilityValuesStaging(String StagingOCid) {
		String othercovenantdetailsvalues = "";
		ResultSet rs=null;
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			
			String query1="SELECT Distinct SLAL.FACILITY_NAME AS FACILITY_NAME,\r\n" + 
					"  SLAL.FACILITY_NAME      AS FACILITY_NAME2\r\n" + 
					"FROM STAGE_SCI_LSP_OTHER_COVENANT_VALUES SLOCV,\r\n" + 
					"   SCI_LSP_APPR_LMTS SLAL\r\n" + 
					"WHERE SLOCV.COVENANT_FACILITY_NAME_VALUE = SLAL.FACILITY_NAME\r\n" +
					"AND SLAL.CMS_LIMIT_STATUS             = 'ACTIVE'\r\n" +
					"AND SLOCV.STATUS             = 'ACTIVE'\r\n" +
					"AND SLOCV.CMS_LE_STAGE_OC_ID               =  ? ";
			dbUtil.setSQL(query1);
			dbUtil.setString(1, StagingOCid);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				othercovenantdetailsvalues = othercovenantdetailsvalues  +rs.getString("FACILITY_NAME") + ",";
			}
		}
		catch (Exception e) {
			System.out.println("Exception in getOtherCovenantDetailsValuesActual."+e.getMessage());
	        e.printStackTrace();
	    }
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
			}
			catch (SQLException e) {
				DefaultLogger.warn(this, "EXCEPTION when closing DB UTIL!", e);
			}
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException e) {
				DefaultLogger.warn(this, "EXCEPTION when closing DB UTIL!", e);
			}
		}
		return othercovenantdetailsvalues;
	}
	
	@Override
	public void insertActualsOtherCovenantDetailsValues(OBOtherCovenant obothercovenant) {
		try {
				System.out.println("Going for insertActualsOtherCovenantDetailsValues => save data ino table => stagingotherCovenantDetails=> getStageEntityName() => "+getStageEntityName());
				getHibernateTemplate().save(getEntityValuesName(), obothercovenant);
			
		} catch (Exception e) {
			System.out.println("Exception in insertActualsOtherCovenantDetailsValues.");
			e.printStackTrace();
		}finally {
			getHibernateTemplate().flush();
		}
	
	}
	
	@Override
	public String getOtherCovenantDetailsValuesStagingInString(String StagingOCid)
	{

		String othercovenantdetailsvalues = "";
		ResultSet rs=null;
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			String query="SELECT DISTINCT CCCE.ENTRY_CODE AS ENTRY_CODE,\r\n" + 
					"  CCCE.ENTRY_NAME      AS ENTRY_NAME\r\n" + 
					"FROM STAGE_SCI_LSP_OTHER_COVENANT_VALUES SLOCV,\r\n" + 
					"  COMMON_CODE_CATEGORY_ENTRY CCCE\r\n" + 
					"WHERE SLOCV.COVENANT_MONITORING_RESP_VALUE = CCCE.ENTRY_CODE\r\n" + 
					"AND CCCE.CATEGORY_CODE             = 'MONITORING_RESPONSIBILITY'\r\n" + 
					"AND CCCE.ACTIVE_STATUS             = 1\r\n" +
					"AND SLOCV.STATUS             = 'ACTIVE'\r\n" +
					"AND SLOCV.CMS_LE_STAGE_OC_ID       =  ? " ;
			dbUtil.setSQL(query);
			dbUtil.setString(1, StagingOCid);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				othercovenantdetailsvalues = othercovenantdetailsvalues + rs.getString("ENTRY_CODE") + "-" +  rs.getString("ENTRY_NAME") + ",";
			}
		}
		catch (Exception e) {
			System.out.println("Exception in getOtherCovenantDetailsValuesStagingInString."+e.getMessage());
	        e.printStackTrace();
	    }
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
			}
			catch (SQLException e) {
				DefaultLogger.warn(this, "EXCEPTION when closing DB UTIL!", e);
			}
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException e) {
				DefaultLogger.warn(this, "EXCEPTION when closing DB UTIL!", e);
			}
		}
		return othercovenantdetailsvalues;
	
	}

	public long getCMSLimitProfileIdActual(String partyId) 
	{		
			long cmsLimitProfileId=0l;
			ResultSet rs=null;
			DBUtil dbUtil = null;
			try {
				dbUtil = new DBUtil();
				String query="SELECT * FROM SCI_LSP_OTHER_COVENANT WHERE CMS_LE_STATUS='ACTIVE' AND CMS_LE_ID='"+partyId+"'" ;
				dbUtil.setSQL(query);
				rs = dbUtil.executeQuery();
				while (rs.next()) {
					cmsLimitProfileId =rs.getLong("CMS_LE_LMT_PROFILE_ID");
				}
			}
			catch (Exception e) {
				System.out.println("Exception in getCMSLimitProfileIdActual."+e.getMessage());
		        e.printStackTrace();
		    }
			finally {
				try {
					if (rs != null) {
						rs.close();
					}
				}
				catch (SQLException e) {
					DefaultLogger.warn(this, "EXCEPTION when closing DB UTIL!", e);
				}
				try {
					if (dbUtil != null) {
						dbUtil.close();
					}
				}
				catch (SQLException e) {
					DefaultLogger.warn(this, "EXCEPTION when closing DB UTIL!", e);
				}
			}
			return cmsLimitProfileId;		
	}

	public long getOtherCovenantIdActual(String partyId)
	{
		long otherCovenantId=0l;
		ResultSet rs=null;
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			String query="SELECT * FROM SCI_LSP_OTHER_COVENANT WHERE CMS_LE_STATUS='ACTIVE' AND CMS_LE_ID='"+partyId+"'" ;
			dbUtil.setSQL(query);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				otherCovenantId =rs.getLong("CMS_LE_OTHER_COVENANT_ID");
			}
		}
		catch (Exception e) {
			System.out.println("Exception in getOtherCovenantIdActual."+e.getMessage());
	        e.printStackTrace();
	    }
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
			}
			catch (SQLException e) {
				DefaultLogger.warn(this, "EXCEPTION when closing DB UTIL!", e);
			}
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}
			catch (SQLException e) {
				DefaultLogger.warn(this, "EXCEPTION when closing DB UTIL!", e);
			}
		}
		return otherCovenantId;
	
	}
	
	@Override
	public void deleteOtherCovenantValues(String UniqueSeqNumFromOC) {
		String query="FROM "+getEntityValuesName()+" where UNIQUE_SEQ_FROM_OC ='"+UniqueSeqNumFromOC+"'" ;
		ArrayList list =(ArrayList) getHibernateTemplate().find(query);
		   if (list != null && list.size() > 0) {
		     getHibernateTemplate().deleteAll(list);
		   }
	}	
	@Override
	public void deleteOtherCovenantStagingValues(String UniqueSeqNumFromOC) {
		String query="FROM "+getStageEntityValuesName()+" where UNIQUE_SEQ_FROM_OC ='"+UniqueSeqNumFromOC+"'" ;
		ArrayList list =(ArrayList) getHibernateTemplate().find(query);
		   if (list != null && list.size() > 0) {
		     getHibernateTemplate().deleteAll(list);
		   }
	}
	@Override
	public void deleteOtherCovenantValues1(String partyId) {
		String query="FROM "+getEntityValuesName()+" where CMS_LE_ID ='"+partyId+"'" ;
		ArrayList list =(ArrayList) getHibernateTemplate().find(query);
		   if (list != null && list.size() > 0) {
		     getHibernateTemplate().deleteAll(list);
		   }
	}
	@Override
	public void deleteOtherCovenantValues2(String partyId) {
		String query="FROM "+getStageEntityValuesName()+" where CMS_LE_ID ='"+partyId+"'" ;
		ArrayList list =(ArrayList) getHibernateTemplate().find(query);
		   if (list != null && list.size() > 0) {
		     getHibernateTemplate().deleteAll(list);
		   }
	}
	
	@Override
	public void deleteOtherCovenantDetailsStage(String partyId) {
		String query="FROM "+getStageEntityName()+" where CMS_LE_ID ='"+partyId+"'" ;
		ArrayList list =(ArrayList) getHibernateTemplate().find(query);
		   if (list != null && list.size() > 0) {
		     getHibernateTemplate().deleteAll(list);
		   }
	}
	
	@Override
	public void deleteOtherCovenantDetailsActual(String partyId) {
		String query="FROM "+getEntityName()+" where CMS_LE_ID ='"+partyId+"'" ;
		ArrayList list =(ArrayList) getHibernateTemplate().find(query);
		   if (list != null && list.size() > 0) {
		     getHibernateTemplate().deleteAll(list);
		   }
	}
	
	public void updateOtherCovenantDetailsStaging(OBOtherCovenant obothercovenant) {
		try {

				System.out.println("Going for updateOtherCovenantDetailsStaging => update data into table => stagingotherCovenantDetailsValues");
				getHibernateTemplate().saveOrUpdate("stagingotherCovenantDetailsValues", obothercovenant);
			
		} catch (Exception e) {
			System.out.println("Exception in updateOtherCovenantDetailsStaging.");
			e.printStackTrace();
		}finally {
			getHibernateTemplate().flush();
		}
	}
}
