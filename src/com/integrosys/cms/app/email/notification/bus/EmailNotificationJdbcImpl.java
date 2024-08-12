package com.integrosys.cms.app.email.notification.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class EmailNotificationJdbcImpl extends JdbcDaoSupport implements
		IEmailNotificationJdbc,INotificationQuery {

	public List getExpiredCAMParties(){
		DefaultLogger.error(this,"CAM_EXPIRY_SQL:"+CAM_EXPIRY_SQL);
		List queryForList = getJdbcTemplate().query(CAM_EXPIRY_SQL,new RowMapper() {
			
			public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
				ICustomerNotificationDetail notificationDetail=new OBCustomerNotificationDetail();
				notificationDetail.setPartyId(rs.getString("partyID"));
				notificationDetail.setPartyName(rs.getString("partyName"));
				notificationDetail.setCamExpiryDate(rs.getString("camExpiryDate"));
				return notificationDetail;
			}
		});
		return queryForList;
	}
	
	public List getPartiesWithoutCAM(){
		DefaultLogger.error(this,"CAM_NOT_CREATED_SQL:"+CAM_NOT_CREATED_SQL);
		List queryForList = getJdbcTemplate().query(CAM_NOT_CREATED_SQL,new RowMapper() {
			
			public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
				ICustomerNotificationDetail notificationDetail=new OBCustomerNotificationDetail();
				notificationDetail.setPartyId(rs.getString("partyID"));
				notificationDetail.setPartyName(rs.getString("partyName"));
				return notificationDetail;
			}
		});
		return queryForList;
	}
	public List getLADDueParties(){
		DefaultLogger.error(this,"LAD_DUE_SQL:"+LAD_DUE_SQL);
		List queryForList = getJdbcTemplate().query(LAD_DUE_SQL,new RowMapper() {
			
			public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
				ICustomerNotificationDetail notificationDetail=new OBCustomerNotificationDetail();
				notificationDetail.setPartyId(rs.getString("partyID"));
				notificationDetail.setPartyName(rs.getString("partyName"));
				notificationDetail.setLadDueDate(rs.getString("ladDueDate"));
				return notificationDetail;
			}
		});
		return queryForList;
	}
	public List getLADExpiredParties(){
		DefaultLogger.error(this,"LAD_EXPIRY_SQL:"+LAD_EXPIRY_SQL);
		List queryForList = getJdbcTemplate().query(LAD_EXPIRY_SQL,new RowMapper() {
			
			public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
				ICustomerNotificationDetail notificationDetail=new OBCustomerNotificationDetail();
				notificationDetail.setPartyId(rs.getString("partyID"));
				notificationDetail.setPartyName(rs.getString("partyName"));
				notificationDetail.setLadExpiryDate(rs.getString("ladExpiryDate"));
				return notificationDetail;
			}
		});
		return queryForList;
	}
	
	public List getAllPartiesWithoutTagImage(){
		DefaultLogger.error(this,"IMAGE_NOT_TAGGED_SQL:"+IMAGE_NOT_TAGGED_SQL);
		List queryForList = getJdbcTemplate().query(IMAGE_NOT_TAGGED_SQL,new RowMapper() {
			
			public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
				ICustomerNotificationDetail notificationDetail=new OBCustomerNotificationDetail();
				notificationDetail.setPartyId(rs.getString("partyID"));
				notificationDetail.setPartyName(rs.getString("partyName"));
				notificationDetail.setCamCreationDate(rs.getString("camCreationDate"));
				return notificationDetail;
			}
		});
		return queryForList;
	}
	public List getPartiesWithStaleCheques(){
		DefaultLogger.error(this,"GET_PARTY_WITH_STALE_CHEQUE_SQL:"+GET_PARTY_WITH_STALE_CHEQUE_SQL);
		List queryForList = getJdbcTemplate().query(GET_PARTY_WITH_STALE_CHEQUE_SQL,new RowMapper() {
			
			public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
				ICustomerNotificationDetail notificationDetail=new OBCustomerNotificationDetail();
				notificationDetail.setPartyId(rs.getString("partyID"));
				notificationDetail.setPartyName(rs.getString("partyName"));
				return notificationDetail;
			}
		});
		return queryForList;
	}
	public List getPartiesWithMatureCollaterals(){
		DefaultLogger.error(this,"GET_PARTY_WITH_MATURE_SECURITY_SQL:"+GET_PARTY_WITH_MATURE_SECURITY_SQL);
		List queryForList = getJdbcTemplate().query(GET_PARTY_WITH_MATURE_SECURITY_SQL,new RowMapper() {
			public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
				ICustomerNotificationDetail notificationDetail=new OBCustomerNotificationDetail();
				notificationDetail.setPartyId(rs.getString("partyID"));
				notificationDetail.setPartyName(rs.getString("partyName"));
				notificationDetail.setSecuritySubType(rs.getString("secSubType"));
				notificationDetail.setSecurityMaturityDate(rs.getString("maturityDate"));
				return notificationDetail;
			}
		});
		return queryForList;
	}
	
	
	
	
	public List getPartiesWithMatureCollateralInsurance(){
		DefaultLogger.error(this,"GET_PARTY_WITH_MATURE_SECURITY_INSURANCE_SQL:"+GET_PARTY_WITH_MATURE_SECURITY_INSURANCE_SQL);
		List queryForList = getJdbcTemplate().query(GET_PARTY_WITH_MATURE_SECURITY_INSURANCE_SQL,new RowMapper() {
			public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
				ICustomerNotificationDetail notificationDetail=new OBCustomerNotificationDetail();
				notificationDetail.setPartyId(rs.getString("partyID"));
				notificationDetail.setPartyName(rs.getString("partyName"));
				notificationDetail.setSecuritySubType(rs.getString("secSubType"));
				notificationDetail.setInsuranceMaturityDate(rs.getString("insuranceMaturityDate"));
				return notificationDetail;
			}
		});
		return queryForList;
	}

	public List getPartiesHavingDocumentDue(){
		DefaultLogger.error(this,"GET_PARTIES_HAVING_DOCUMENT_DUE_SQL:"+GET_PARTIES_HAVING_DOCUMENT_DUE_SQL);
		List queryForList = getJdbcTemplate().query(GET_PARTIES_HAVING_DOCUMENT_DUE_SQL,new RowMapper() {
			String status="";
			public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
				ICustomerNotificationDetail notificationDetail=new OBCustomerNotificationDetail();
				notificationDetail.setPartyId(rs.getString("partyID"));
				notificationDetail.setPartyName(rs.getString("partyName"));
				notificationDetail.setStatementType(rs.getString("statementType"));
				status=rs.getString("status");
					if(ICMSConstant.STATE_ITEM_AWAITING.equals(status)){
						notificationDetail.setDocDueDate(rs.getString("docExpiryDate"));
					}else if(ICMSConstant.STATE_ITEM_DEFERRED.equals(status)){
						notificationDetail.setDocDueDate(rs.getString("docDeferDate"));
					}
				return notificationDetail;
			}
		});
		return queryForList;
	}

	
	public String getRMAndRMHeadByCustomerId(String partyId) {
		String sql=new StringBuffer(" SELECT " )
						.append(" rm.RM_MGR_MAIL 				as rmEmailId,	")
						.append(" rm.REPORTING_HEAD_MAIL 		as rmHeadEmailId")
						.append(" FROM sci_le_sub_profile party,")
						.append("   CMS_RELATIONSHIP_MGR rm")
						.append(" WHERE party.relation_mgr=rm.id")
						.append(" AND party.cms_le_sub_profile_id= ?")
						.toString();
		DefaultLogger.error(this,"getRMAndRMHeadByCustomerId:"+sql);
//		DefaultLogger.error(this,"partyId:"+partyId);
		String rmEmailIds = (String)getJdbcTemplate().query(sql, new Object[] { partyId }, new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					String emailId="";
					String temp="";
						emailId=rs.getString("rmEmailId");
						temp	=rs.getString("rmHeadEmailId");
						if(temp!=null &&!"".equals(temp.trim())){
							emailId=emailId+","+temp;
						}	
						return emailId;
				}
				return null;
			}
		});
		/*String rmEmailIds = (String)getJdbcTemplate().queryForObject(GET_PARTIES_HAVING_DOCUMENT_DUE_SQL,new RowMapper() {
			String emailId="";
			String temp="";
			public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
				emailId=rs.getString("rmEmailId");
				temp	=rs.getString("rmHeadEmailId");
				if(temp!=null &&!"".equals(temp.trim())){
					emailId=emailId+";"+temp;
				}	
				return emailId;
			}
		});*/
		return rmEmailIds;
	}
	
	
	
	
	//Shiv 131212
	public List getAllPartiesWithMatureCollateralInsurance(){
		DefaultLogger.error(this,"GET_PARTY_WITH_MATURE_SECURITY_INSURANCE_SQL:"+GET_PARTY_WITH_MATURE_SECURITY_INSURANCE_SQL);
		List queryForList = getJdbcTemplate().query(GET_PARTY_WITH_MATURE_SECURITY_INSURANCE_SQL,new RowMapper() {
			public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
				ICustomerNotificationDetail notificationDetail=new OBCustomerNotificationDetail();
				notificationDetail.setPartyId(rs.getString("partyID"));
				notificationDetail.setPartyName(rs.getString("partyName"));
				notificationDetail.setSecuritySubType(rs.getString("secSubType"));
				notificationDetail.setInsuranceMaturityDate(rs.getString("insuranceMaturityDate"));
				return notificationDetail;
			}
		});
		return queryForList;
	}
	
	
	
	
	
	public List getCaseCreationDistinctBranch(){
		DefaultLogger.error(this,"GET_CASECREATION_DISTINCT_BRANCH_SQL:"+GET_CASECREATION_DISTINCT_BRANCH_SQL);
		List queryForList = getJdbcTemplate().query(GET_CASECREATION_DISTINCT_BRANCH_SQL,new RowMapper() {
			public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
				String notificationDetail=rs.getString("branch");
				return notificationDetail;
			}
		});
		return queryForList;
	}
	
	public List getCaseCreationDistinctSegment(){
		DefaultLogger.error(this,"GET_CASECREATION_DISTINCT_SEGMENT_SQL:"+GET_CASECREATION_DISTINCT_SEGMENT_SQL);
		List queryForList = getJdbcTemplate().query(GET_CASECREATION_DISTINCT_SEGMENT_SQL,new RowMapper() {
			public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
				String notificationDetail=rs.getString("segment");
				return notificationDetail;
			}
		});
		return queryForList;
	}
	
	
	public String getBranchCoordinatorEmailId(String branchCode) {
		
		String sql="select coordinator1email,coordinator2email from cms_casebranch where branchcode='"+branchCode+"'";
		
		String coordinatorEmailIds = (String)getJdbcTemplate().queryForObject(sql,new RowMapper() {
			String emailId1="";
			String emailId2="";
			String emailId="";
			public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
				emailId1=rs.getString("coordinator1email");
				emailId2=rs.getString("coordinator2email");
				if(emailId1!=null &&!"".equals(emailId1.trim())){
					emailId=emailId+","+emailId1;
				}
				if(emailId2!=null &&!"".equals(emailId2.trim())){
					emailId=emailId+","+emailId2;
				}
				return emailId;
			}
		});
		return coordinatorEmailIds;
	}
	
	
	public String getBranchName(String branchCode) {
		
		String sql="select system_bank_branch_name from cms_system_bank_branch where system_bank_branch_code='"+branchCode+"'";
		
		String coordinatorEmailIds = (String)getJdbcTemplate().queryForObject(sql,new RowMapper() {
			
			String branchName="";
			public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
				branchName=rs.getString("system_bank_branch_name");
				
				return branchName;
			}
		});
		return coordinatorEmailIds;
	}

	
	
	public List getCPUTRequestedCaseCreation(){
		DefaultLogger.error(this,"GET_CASECREATION_CPUT_REQUESTED_SQL:"+GET_CASECREATION_CPUT_REQUESTED_SQL);
		List queryForList = getJdbcTemplate().query(GET_CASECREATION_CPUT_REQUESTED_SQL,new RowMapper() {
			public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
				ICaseCreationNotificationDetail notificationDetail=new OBCaseCreationNotificationDetail();
				notificationDetail.setPartyId(rs.getString("partyID"));
				notificationDetail.setPartyName(rs.getString("partyName"));
				notificationDetail.setBranch(rs.getString("branch"));
				notificationDetail.setCaseCreationDate(rs.getString("creation_date"));
				notificationDetail.setCaseCreationId(rs.getString("caseCreationId"));
				notificationDetail.setDocNos(rs.getString("doc_nos"));
				notificationDetail.setPendingDays("");
				notificationDetail.setRemarks(rs.getString("remarks"));
				notificationDetail.setSegment(rs.getString("segment"));
				return notificationDetail;
			}
		});
		return queryForList;
	}
	
	
	public List getBranchNotSentCaseCreation() {
		DefaultLogger.error(this,"GET_CASECREATION_BRANCH_NOT_SENT_SQL:"+GET_CASECREATION_BRANCH_NOT_SENT_SQL);
		List queryForList = getJdbcTemplate().query(GET_CASECREATION_BRANCH_NOT_SENT_SQL,new RowMapper() {
			public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
				ICaseCreationNotificationDetail notificationDetail=new OBCaseCreationNotificationDetail();
				notificationDetail.setPartyId(rs.getString("partyID"));
				notificationDetail.setPartyName(rs.getString("partyName"));
				notificationDetail.setBranch(rs.getString("branch"));
				notificationDetail.setCaseCreationDate(rs.getString("creation_date"));
				notificationDetail.setCaseCreationId(rs.getString("caseCreationId"));
				notificationDetail.setDocNos(rs.getString("doc_nos"));
				notificationDetail.setPendingDays(rs.getString("pendingDays"));
				notificationDetail.setRemarks(rs.getString("remarks"));
				notificationDetail.setSegment(rs.getString("segment"));
				return notificationDetail;
			}
		});
		return queryForList;
	}

	
	public List getBranchSentCaseCreation() {
		DefaultLogger.error(this,"GET_CASECREATION_BRANCH_SENT_SQL:"+GET_CASECREATION_BRANCH_SENT_SQL);
		List queryForList = getJdbcTemplate().query(GET_CASECREATION_BRANCH_SENT_SQL,new RowMapper() {
			public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
				ICaseCreationNotificationDetail notificationDetail=new OBCaseCreationNotificationDetail();
				notificationDetail.setPartyId(rs.getString("partyID"));
				notificationDetail.setPartyName(rs.getString("partyName"));
				notificationDetail.setBranch(rs.getString("branch"));
				notificationDetail.setCaseCreationDate(rs.getString("creation_date"));
				notificationDetail.setCaseCreationId(rs.getString("caseCreationId"));
				notificationDetail.setDocNos(rs.getString("doc_nos"));
				notificationDetail.setPendingDays("");
				notificationDetail.setRemarks(rs.getString("remarks"));
				notificationDetail.setSegment(rs.getString("segment"));
				return notificationDetail;
			}
		});
		return queryForList;
	}

	
	public List getCPUTNotReceivedCaseCreation() {
		DefaultLogger.error(this,"GET_CASECREATION_CPUT_NOT_REC_SQL:"+GET_CASECREATION_CPUT_NOT_REC_SQL);
		List queryForList = getJdbcTemplate().query(GET_CASECREATION_CPUT_NOT_REC_SQL,new RowMapper() {
			public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
				ICaseCreationNotificationDetail notificationDetail=new OBCaseCreationNotificationDetail();
				notificationDetail.setPartyId(rs.getString("partyID"));
				notificationDetail.setPartyName(rs.getString("partyName"));
				notificationDetail.setBranch(rs.getString("branch"));
				notificationDetail.setCaseCreationDate(rs.getString("creation_date"));
				notificationDetail.setCaseCreationId(rs.getString("caseCreationId"));
				notificationDetail.setDocNos(rs.getString("doc_nos"));
				notificationDetail.setPendingDays(rs.getString("pendingDays"));
				notificationDetail.setRemarks(rs.getString("remarks"));
				notificationDetail.setSegment(rs.getString("segment"));
				return notificationDetail;
			}
		});
		return queryForList;
	}

	
	public List getCPUTReceivedCaseCreation() {
		DefaultLogger.error(this,"GET_CASECREATION_CPUT_RECEIVED_SQL:"+GET_CASECREATION_CPUT_RECEIVED_SQL);
		List queryForList = getJdbcTemplate().query(GET_CASECREATION_CPUT_RECEIVED_SQL,new RowMapper() {
			public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
				ICaseCreationNotificationDetail notificationDetail=new OBCaseCreationNotificationDetail();
				notificationDetail.setPartyId(rs.getString("partyID"));
				notificationDetail.setPartyName(rs.getString("partyName"));
				notificationDetail.setBranch(rs.getString("branch"));
				notificationDetail.setCaseCreationDate(rs.getString("creation_date"));
				notificationDetail.setCaseCreationId(rs.getString("caseCreationId"));
				notificationDetail.setDocNos(rs.getString("doc_nos"));
				notificationDetail.setPendingDays("");
				notificationDetail.setRemarks(rs.getString("remarks"));
				notificationDetail.setSegment(rs.getString("segment"));
				return notificationDetail;
			}
		});
		return queryForList;
	}

	
	public List getCPUTWrongRequestCaseCreation() {
		DefaultLogger.error(this,"GET_CASECREATION_CPUT_WRONG_REQUEST_SQL:"+GET_CASECREATION_CPUT_WRONG_REQUEST_SQL);
		List queryForList = getJdbcTemplate().query(GET_CASECREATION_CPUT_WRONG_REQUEST_SQL,new RowMapper() {
			public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
				ICaseCreationNotificationDetail notificationDetail=new OBCaseCreationNotificationDetail();
				notificationDetail.setPartyId(rs.getString("partyID"));
				notificationDetail.setPartyName(rs.getString("partyName"));
				notificationDetail.setBranch(rs.getString("branch"));
				notificationDetail.setCaseCreationDate(rs.getString("creation_date"));
				notificationDetail.setCaseCreationId(rs.getString("caseCreationId"));
				notificationDetail.setDocNos(rs.getString("doc_nos"));
				notificationDetail.setPendingDays("");
				notificationDetail.setRemarks(rs.getString("remarks"));
				notificationDetail.setSegment(rs.getString("segment"));
				return notificationDetail;
			}
		});
		return queryForList;
	}
	
	@Override
   public List getTodaysDiaryList() {
		String DIARY_EMAIL =  new StringBuffer	("SELECT item.customer_name,item.le_id,item.FACILITY_LINE_NO,item.FACILITY_SERIAL_NO,item.DESCRIPTION,item.CUSTOMER_SEGMENT ")
				.append(" FROM CMS_DIARY_ITEM item WHERE item.ITEM_DUE_DATE = to_date((select param_value from CMS_GENERAL_PARAM where param_code = 'APPLICATION_DATE'),'dd-mm-yyyy') ")
				.append(" UNION ALL ")
				.append(" SELECT item.customer_name, item.le_id,item.FACILITY_LINE_NO,item.FACILITY_SERIAL_NO,item.DESCRIPTION,item.CUSTOMER_SEGMENT FROM CMS_DIARY_ITEM item,cms_diary_schedule_data sch ") 
				.append(" WHERE sch.DIARY_NUMBER= item.DIARY_NUMBER AND sch.item_due_date = to_date((select param_value from CMS_GENERAL_PARAM where param_code = 'APPLICATION_DATE'),'dd-mm-yyyy') AND sch.IS_CLOSED= 'N' ").toString();
		
		List queryForList = getJdbcTemplate().query(DIARY_EMAIL,new RowMapper() {
			
			public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
				ICustomerNotificationDetail notificationDetail=new OBCustomerNotificationDetail();
				notificationDetail.setPartyId(rs.getString("customer_name"));
				notificationDetail.setPartyName(rs.getString("le_id"));
				notificationDetail.setFacilityLineNo(rs.getString("FACILITY_LINE_NO"));
				notificationDetail.setFacilitySerialNo(rs.getString("FACILITY_SERIAL_NO"));
				notificationDetail.setDescription(rs.getString("DESCRIPTION"));
				notificationDetail.setSegment(rs.getString("CUSTOMER_SEGMENT"));
				return notificationDetail;
			}
		});
		
		DefaultLogger.error(this,"getTodaysDiaryList:"+DIARY_EMAIL);
		
		return queryForList;
	}

@Override
public List getTodaysDiaryEmailIdList(String segment) {
	
	String DIARY_EMAIL_LIST = "Select email from CMS_DIARY_EMAIL_SEG_MAP where status = 'ACTIVE' and segments=(select entry_name  from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_code='"+segment+"')";
	
	List queryForList = getJdbcTemplate().query(DIARY_EMAIL_LIST,new RowMapper() {
		public Object mapRow(ResultSet rs, int rowCount) throws SQLException {
			String email=rs.getString("email");
			return email;
		}
	});
	DefaultLogger.error(this,"getTodaysDiaryEmailIdList:"+DIARY_EMAIL_LIST);
	return queryForList;
}

}
