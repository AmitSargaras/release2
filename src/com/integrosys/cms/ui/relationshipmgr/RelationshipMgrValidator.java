package com.integrosys.cms.ui.relationshipmgr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Purpose : Used for Validating Relationship Manager 
 *
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-04-06 15:13:16 +0800 
 * Tag : $Name$
 */

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.common.util.SQLParameter;
import com.integrosys.cms.app.relationshipmgr.bus.IHRMSData;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.app.relationshipmgr.proxy.RelationshipMgrProxyManagerImpl;
import com.integrosys.cms.asst.validator.ASSTValidator;

public class RelationshipMgrValidator {


	public static ActionErrors validateRelationshipMgrForm(RelationshipMgrForm aForm, Locale locale) {
		//<validate remarks field
    	ActionErrors errors = new ActionErrors();
		String errorCode = null;
		String event = aForm.getEvent(); 
		
			RelationshipMgrForm form = (RelationshipMgrForm) aForm;
			if(event.contains(RelationshipManagerAction.ADD_CAD) || event.contains(RelationshipManagerAction.ADD_EDIT_CAD) || event.contains(RelationshipManagerAction.ADD_RESUBMIT_CAD) ) {
				
//				if(null == form.getCadBranchCode() || "".equals(form.getCadBranchCode())) {
//					errors.add("cadBranchCodeError", new ActionMessage("error.string.mandatory"));
//				}
				
				if(null == form.getCadEmployeeCode() || "".equals(form.getCadEmployeeCode())) {
					errors.add("cadEmployeeCodeError", new ActionMessage("error.string.mandatory"));
				}else {
//					IRelationshipMgrProxyManager iRelationshipMgrProxyManager = new RelationshipMgrProxyManagerImpl();
//					IHRMSData iHrmsData = iRelationshipMgrProxyManager.getLocalCAD(form.getCadEmployeeCode(),"");					
//					if(null == iHrmsData) {
//						errors.add("cadEmployeeCodeError", new ActionMessage("error.string.exist", "Local CAD"));	
//					}
					
					boolean isAlphaNum = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getCadEmployeeCode());
					
					if (isAlphaNum){
						errors.add("cadEmployeeCodeError", new ActionMessage("error.string.specialcharacter"));
					}
					
					int counter = fetchHRMSData(form.getCadEmployeeCode());
					if(counter < 1) {
						errors.add("cadEmployeeCodeError", new ActionMessage("error.string.no.details.found"));
					}
				}
			}
			else if("maker_save_update_relationship_mgr".equals(event)) {
				
				
				if(form.getRegion()==null || "".equals(form.getRegion().trim()))
				{
//					if(!event.equals(RelationshipManagerAction.MAKER_SAVE_CREATE_RELATIONSHIP_MANAGER)){
						errors.add("regionError", new ActionMessage("error.string.mandatory"));
						DefaultLogger.debug(RelationshipMgrValidator.class, "regionError");
//					}
				}
				
			}
			else if("populateFields".equals(event)) {
				
				if(form.getRelationshipMgrCode()==null || "".equals(form.getRelationshipMgrCode().trim()))
				{	
//					if(!event.equals(RelationshipManagerAction.MAKER_SAVE_CREATE_RELATIONSHIP_MANAGER)){
						errors.add("relationshipMgrCodeError", new ActionMessage("error.string.mandatory"));
						DefaultLogger.debug(RelationshipMgrValidator.class, "relationshipMgrCodeError");
//					}
				}else {
				boolean flag = false;
					int counts = fetchCountEmployeecode(form.getRelationshipMgrCode());
					if(counts == 0) {
						errors.add("relationshipMgrCodeExistError", new ActionMessage("error.string.notexist.emp.code"));
						flag =true;
					}
					
					/*if(flag == false) {
					int countsDisabled = fetchCountEmployeecodeDisabled(form.getRelationshipMgrCode());
					if(countsDisabled > 0) {
						errors.add("relationshipMgrCodeExistError", new ActionMessage("error.string.notexist.emp.code"));
					}
					}*/
					
					int countsPendingAuth = fetchCountEmpCodePendingAuth(form.getRelationshipMgrCode());
					if(countsPendingAuth > 0) {
						errors.add("relationshipMgrCodeError", new ActionMessage("error.string.pending.emp.code"));
					}
					
				}
			}
			else {
			if(form.getRelationshipMgrCode()==null || "".equals(form.getRelationshipMgrCode().trim()))
			{	
//				if(!event.equals(RelationshipManagerAction.MAKER_SAVE_CREATE_RELATIONSHIP_MANAGER)){
					errors.add("relationshipMgrCodeError", new ActionMessage("error.string.mandatory"));
					DefaultLogger.debug(RelationshipMgrValidator.class, "relationshipMgrCodeError");
//				}
			}else if(form.getRelationshipMgrCode().length()>10) {
				errors.add("relationshipMgrCodeError", new ActionMessage("error.string.length"));
			}
			
			boolean isAlphaNumeric = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getRelationshipMgrCode());
			
			if (isAlphaNumeric){
				errors.add("relationshipMgrCodeError", new ActionMessage("error.string.specialcharacter"));
			}
			
			int count = fetchHRMSData(form.getRelationshipMgrCode());
			if(count < 1) {
				errors.add("relationshipMgrCodeError", new ActionMessage("error.string.invalid.emp.code"));
			}
			
		if ("maker_submit_create_relationship_mgr".equals(form.getEvent())) {
			int count1 = fetchRMData(form.getRelationshipMgrCode());
			if (count1 >= 1) {
				errors.add("relationshipMgrCodeError", new ActionMessage("error.string.exist", "Employee code "));
			}
			
			
			
			/*int count2 = fetchStageRMData(form.getRelationshipMgrCode());
			if (count2 > 1) {
				int count3 = fetchInactiveRMData(form.getRelationshipMgrCode());
				if(count3<1) {
				errors.add("relationshipMgrCodeError", new ActionMessage("error.string.pending.emp.code"));
				}
			}*/
			
		}
			
//			else if(form.getRelationshipMgrName().length()>50)
//			{
//				errors.add("relationshipMgrNameError", new ActionMessage("error.string.length"));
//				DefaultLogger.debug(RelationshipMgrValidator.class, "relationshipMgrNameError");
//			}
//			else{
//				boolean nameFlag = ASSTValidator.isValidRelationshipManagerName(form.getRelationshipMgrName());
//				if( nameFlag == true)
//					errors.add("specialCharacterNameError", new ActionMessage("error.string.invalidCharacter"));
//			}
			if(form.getRegion()==null || "".equals(form.getRegion().trim()))
			{
//				if(!event.equals(RelationshipManagerAction.MAKER_SAVE_CREATE_RELATIONSHIP_MANAGER)){
					errors.add("regionError", new ActionMessage("error.string.mandatory"));
					DefaultLogger.debug(RelationshipMgrValidator.class, "regionError");
//				}
			}
			
			if(form.getWboRegion()==null || "".equals(form.getWboRegion().trim()))
			{
					errors.add("wboregionError", new ActionMessage("error.string.mandatory"));
					DefaultLogger.debug(RelationshipMgrValidator.class, "wboregionError");
			}
			
//			if(form.getReportingHeadEmployeeCode()==null || "".equals(form.getReportingHeadEmployeeCode().trim()))
//			{
//					errors.add("employeeIdError", new ActionMessage("error.string.mandatory"));
//					DefaultLogger.debug(RelationshipMgrValidator.class, "employeeIdError");
//			}
			if(form.getReportingHeadRegion()==null || "".equals(form.getReportingHeadRegion().trim()))
			{
					errors.add("supervisorregionError", new ActionMessage("error.string.mandatory"));
					DefaultLogger.debug(RelationshipMgrValidator.class, "supervisorregionError");
			}
			
			
			
	}
//			if(form.getRelationshipMgrMailId()!=null && !(form.getRelationshipMgrMailId().equals("")))
//			{
//				if( form.getRelationshipMgrMailId().length()>50 ) 	{
//					errors.add("relationshipMgrMailIdError", new ActionMessage("error.email.length"));
//					DefaultLogger.debug(RelationshipMgrValidator.class, "relationshipMgrMailIdError");
//				}
//				else if( ASSTValidator.isValidEmail(form.getRelationshipMgrMailId()) )
//				{
//						errors.add("relationshipMgrMailIdError", new ActionMessage("error.email.format"));
//						DefaultLogger.debug(RelationshipMgrValidator.class, "relationshipMgrMailIdError");
//				}
//			}else{
//				if(!event.equals(RelationshipManagerAction.MAKER_SAVE_CREATE_RELATIONSHIP_MANAGER)){
//					errors.add("relationshipMgrMailIdError", new ActionMessage("error.string.mandatory"));
//					DefaultLogger.debug(RelationshipMgrValidator.class, "relationshipMgrMailIdError");
//				}
//			}
//		
//			if(form.getReportingHeadMailId()!=null && !(form.getReportingHeadMailId().equals("")))
//			{
//				if( form.getReportingHeadMailId().length()>50 ) 	{
//					errors.add("reportingHeadMailIdError", new ActionMessage("error.email.length"));
//					DefaultLogger.debug(RelationshipMgrValidator.class, "reportingHeadMailIdError");
//				}
//				else if( ASSTValidator.isValidEmail(form.getReportingHeadMailId()) )
//				{
//						errors.add("reportingHeadMailIdError", new ActionMessage("error.email.format"));
//						DefaultLogger.debug(RelationshipMgrValidator.class, "reportingHeadMailIdError");
//				}
//			}		
			
			/*	Condition Added to check for Special Character by Sand*/
//			if((form.getReportingHeadName()!=null && !form.getReportingHeadName().equals("")) 
//					&& form.getReportingHeadName().length()>50)
//			{
//					errors.add("reportingHeadNameError", new ActionMessage("error.string.length"));
//					DefaultLogger.debug(RelationshipMgrValidator.class, "reportingHeadNameError");
//			}
//			else if( form.getReportingHeadName() !=null && !form.getReportingHeadName().equals("") )
//			{
//				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithSpacewAndUnderScore(form.getReportingHeadName());
//				if( nameFlag == true)
//					errors.add("specialCharacterReportingNameError", new ActionMessage("error.string.invalidCharacter"));			
//			}
			
//			if (!(errorCode = Validator.checkString(form.getEmployeeId(), true, 1, 10)).equals(Validator.ERROR_NONE)) {
//				errors.add("employeeIdError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
//						"10"));
//			}
//				else{
//					boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getEmployeeId());
//					if( codeFlag == true)
//						errors.add("employeeIdError", new ActionMessage("error.string.invalidCharacter"));
//					}
			
			/*if (event!= null && event.equals("EODSyncMasters")) {
				
				if(form.getRelationshipMgrName()==null || "".equals(form.getRelationshipMgrName().trim()))
				{	
						errors.add("relationshipMgrNameError", new ActionMessage("USER_NAME is mandatory"));
				}
				else if(form.getRelationshipMgrName().length()>50)
				{
					errors.add("relationshipMgrNameError", new ActionMessage("For USER_NAME valid range is between 0 and 50 character(s)"));
				}
				else{
					boolean nameFlag = ASSTValidator.isValidRelationshipManagerName(form.getRelationshipMgrName());
					if( nameFlag == true)
						errors.add("specialCharacterNameError", new ActionMessage("USER_NAME has Invalid characters"));
				}
				if(form.getRegionId()==null || "".equals(form.getRegionId().trim()))
				{
						errors.add("regionError", new ActionMessage("USER_LOCCODE is mandatory"));
				}
				
				if(form.getRelationshipMgrMailId()!=null && !(form.getRelationshipMgrMailId().equals("")))
				{
					if( form.getRelationshipMgrMailId().length()>50 ) 	{
						errors.add("relationshipMgrMailIdError", new ActionMessage("For USER_EMAIL valid range is between 0 and 50 character(s)"));
					}
					else if( ASSTValidator.isValidEmail(form.getRelationshipMgrMailId()) )
					{
							errors.add("relationshipMgrMailIdError", new ActionMessage("USER_EMAIL format is invalid"));
					}
				}else{
						errors.add("relationshipMgrMailIdError", new ActionMessage("USER_EMAIL is mandatory"));
				}
			
				if((form.getReportingHeadName()!=null && !form.getReportingHeadName().equals("")) 
						&& form.getReportingHeadName().length()>50)
				{
						errors.add("reportingHeadNameError", new ActionMessage("error.string.length"));
						DefaultLogger.debug(RelationshipMgrValidator.class, "reportingHeadNameError");
				}
				else if( form.getReportingHeadName() !=null && !form.getReportingHeadName().equals("") )
				{
					boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getReportingHeadName());
					if( nameFlag == true)
						errors.add("specialCharacterReportingNameError", new ActionMessage("error.string.invalidCharacter"));			
				}
	}*/
			
			
		return errors;
    }
	
	private static int fetchHRMSData(String rmEmpCode){
//		List data = new ArrayList();	
		int count = 0;
		
		String sql="select count(*) as count from CMS_HRMS_DATA where EMPLOYEE_CODE = '"+rmEmpCode+"'";
		
	        SQLParameter params = SQLParameter.getInstance();
	        DBUtil dbUtil = null;
	        ResultSet rs = null;

	        try {
	            dbUtil = new DBUtil();
	            dbUtil.setSQL(sql);
	            rs = dbUtil.executeQuery();
	           if(null != rs) {
	            while (rs.next()) {	 
	            	count = Integer.parseInt(rs.getString("count")); 
	            }
	           }
	           return count; 
	        } catch (SQLException ex) {
	            throw new SearchDAOException("SQLException in StagingCustGrpIdentifierDAO", ex);
	        } catch (Exception ex) {
	            throw new SearchDAOException("Exception in StagingCustGrpIdentifierDAO", ex);
	        }finally{
	        	try {
					dbUtil.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	}

	private static int fetchRMData(String rmEmpCode){
//		List data = new ArrayList();	
		int count = 0;
		
//		String sql="select count(*) as count from CMS_RELATIONSHIP_MGR where RM_MGR_CODE = '"+rmEmpCode+"' AND STATUS ='ACTIVE'";
		String sql="select count(*) as count from CMS_RELATIONSHIP_MGR where RM_MGR_CODE = '"+rmEmpCode+"'";		
	        SQLParameter params = SQLParameter.getInstance();
	        DBUtil dbUtil = null;
	        ResultSet rs = null;

	        try {
	            dbUtil = new DBUtil();
	            dbUtil.setSQL(sql);
	            rs = dbUtil.executeQuery();
	           if(null != rs) {
	            while (rs.next()) {	 
	            	count = Integer.parseInt(rs.getString("count")); 
	            }
	           }
	           return count; 
	        } catch (SQLException ex) {
	            throw new SearchDAOException("SQLException in StagingCustGrpIdentifierDAO", ex);
	        } catch (Exception ex) {
	            throw new SearchDAOException("Exception in StagingCustGrpIdentifierDAO", ex);
	        }finally{
	        	try {
					dbUtil.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	}
	
	private static int fetchStageRMData(String rmEmpCode){
//		List data = new ArrayList();	
		int count = 0;
		
//		String sql="select count(*) as count from CMS_STAGE_RELATIONSHIP_MGR where RM_MGR_CODE = '"+rmEmpCode+"' AND STATUS ='ACTIVE'";
		String sql="select count(*) as count from CMS_STAGE_RELATIONSHIP_MGR where RM_MGR_CODE = '"+rmEmpCode+"'";
	        SQLParameter params = SQLParameter.getInstance();
	        DBUtil dbUtil = null;
	        ResultSet rs = null;

	        try {
	            dbUtil = new DBUtil();
	            dbUtil.setSQL(sql);
	            rs = dbUtil.executeQuery();
	           if(null != rs) {
	            while (rs.next()) {	 
	            	count = Integer.parseInt(rs.getString("count")); 
	            }
	           }
	           return count; 
	        } catch (SQLException ex) {
	            throw new SearchDAOException("SQLException in StagingCustGrpIdentifierDAO", ex);
	        } catch (Exception ex) {
	            throw new SearchDAOException("Exception in StagingCustGrpIdentifierDAO", ex);
	        }finally{
	        	try {
					dbUtil.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	}
	
	private static int fetchInactiveRMData(String rmEmpCode){
//		List data = new ArrayList();	
		int count = 0;
		
		String sql="select count(*) as count from CMS_RELATIONSHIP_MGR where RM_MGR_CODE = '"+rmEmpCode+"' AND STATUS ='INACTIVE'";
		
	        SQLParameter params = SQLParameter.getInstance();
	        DBUtil dbUtil = null;
	        ResultSet rs = null;

	        try {
	            dbUtil = new DBUtil();
	            dbUtil.setSQL(sql);
	            rs = dbUtil.executeQuery();
	           if(null != rs) {
	            while (rs.next()) {	 
	            	count = Integer.parseInt(rs.getString("count")); 
	            }
	           }
	           return count; 
	        } catch (SQLException ex) {
	            throw new SearchDAOException("SQLException in StagingCustGrpIdentifierDAO", ex);
	        } catch (Exception ex) {
	            throw new SearchDAOException("Exception in StagingCustGrpIdentifierDAO", ex);
	        }finally{
	        	try {
					dbUtil.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	}
	
	private static int fetchCountEmployeecode(String rmEmpCode){
//		List data = new ArrayList();	
		int count = 0;
		
//		String sql="select count(*) as count from CMS_RELATIONSHIP_MGR where RM_MGR_CODE = '"+rmEmpCode+"' AND STATUS ='ACTIVE'";
		String sql="SELECT COUNT(*) AS EMPLOYEE_CODE_COUNT FROM CMS_HRMS_DATA WHERE EMPLOYEE_CODE = '"+rmEmpCode+"'";		
	        SQLParameter params = SQLParameter.getInstance();
	        DBUtil dbUtil = null;
	        ResultSet rs = null;

	        try {
	            dbUtil = new DBUtil();
	            dbUtil.setSQL(sql);
	            rs = dbUtil.executeQuery();
	           if(null != rs) {
	            while (rs.next()) {	 
	            	count = Integer.parseInt(rs.getString("EMPLOYEE_CODE_COUNT")); 
	            }
	           }
	           return count; 
	        } catch (SQLException ex) {
	            throw new SearchDAOException("SQLException in fetchCountEmployeecode", ex);
	        } catch (Exception ex) {
	            throw new SearchDAOException("Exception in fetchCountEmployeecode", ex);
	        }finally{
	        	try {
					dbUtil.close();
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	}
	
	
	private static int fetchCountEmployeecodeDisabled(String rmEmpCode){
//		List data = new ArrayList();	
		int count = 0;
		
//		String sql="select count(*) as count from CMS_RELATIONSHIP_MGR where RM_MGR_CODE = '"+rmEmpCode+"' AND STATUS ='ACTIVE'";
		String sql="SELECT COUNT(*) AS EMPLOYEE_CODE_COUNT FROM CMS_RELATIONSHIP_MGR WHERE RM_MGR_CODE = '"+rmEmpCode+"' AND STATUS = 'INACTIVE'";		
	        SQLParameter params = SQLParameter.getInstance();
	        DBUtil dbUtil = null;
	        ResultSet rs = null;

	        try {
	            dbUtil = new DBUtil();
	            dbUtil.setSQL(sql);
	            rs = dbUtil.executeQuery();
	           if(null != rs) {
	            while (rs.next()) {	 
	            	count = Integer.parseInt(rs.getString("EMPLOYEE_CODE_COUNT")); 
	            }
	           }
	           return count; 
	        } catch (SQLException ex) {
	            throw new SearchDAOException("SQLException in fetchCountEmployeecodeDisabled", ex);
	        } catch (Exception ex) {
	            throw new SearchDAOException("Exception in fetchCountEmployeecodeDisabled", ex);
	        }finally{
	        	try {
					dbUtil.close();
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	}
	
	private static int fetchCountEmpCodePendingAuth(String rmEmpCode){
//		List data = new ArrayList();	
		int count = 0;
		
//		String sql="select count(*) as count from CMS_RELATIONSHIP_MGR where RM_MGR_CODE = '"+rmEmpCode+"' AND STATUS ='ACTIVE'";
		String sql="SELECT COUNT(*) AS EMPLOYEE_CODE_COUNT FROM TRANSACTION WHERE STAGING_REFERENCE_ID = (SELECT MAX(ID) FROM CMS_STAGE_RELATIONSHIP_MGR WHERE RM_MGR_CODE = '"+rmEmpCode+"') AND STATUS IN ('PENDING_CREATE','REJECTED')";		
	        SQLParameter params = SQLParameter.getInstance();
	        DBUtil dbUtil = null;
	        ResultSet rs = null;

	        try {
	            dbUtil = new DBUtil();
	            dbUtil.setSQL(sql);
	            rs = dbUtil.executeQuery();
	           if(null != rs) {
	            while (rs.next()) {	 
	            	count = Integer.parseInt(rs.getString("EMPLOYEE_CODE_COUNT")); 
	            }
	           }
	           return count; 
	        } catch (SQLException ex) {
	            throw new SearchDAOException("SQLException in fetchCountEmpCodePendingAuth", ex);
	        } catch (Exception ex) {
	            throw new SearchDAOException("Exception in fetchCountEmpCodePendingAuth", ex);
	        }finally{
	        	try {
					dbUtil.close();
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	}
	
}
