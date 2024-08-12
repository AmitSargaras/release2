package com.integrosys.cms.app.json.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.json.line.dto.RetrieveScmLineRequest;

public class ScmLineDaoImpl extends JdbcTemplateAdapter implements ScmLineDao {
	
	public RetrieveScmLineRequest getLineDetails(String xrefId,String limitProfileId,String limitId,String custId) {
		//System.out.println("Inside RetrieveScmLineRequest getLineDetails");
		System.out.println("Inside RetrieveScmLineRequest getLineDetails=>xrefId=>"+xrefId+" limitProfileId=>"+limitProfileId+" limitId=>"+limitId+" custId=>"+custId);
		DefaultLogger.debug(this,"Inside RetrieveScmLineRequest getLineDetails=>xrefId=>"+xrefId+" limitProfileId=>"+limitProfileId+" limitId=>"+limitId+" custId=>"+custId);
		RetrieveScmLineRequest lineDetails=new RetrieveScmLineRequest();
		ResultSet rs = null;
		DBUtil dbUtil =null;
		String query_rls_dtls = "SELECT   nvl(XREF.scm_flag,'No') scm_flag,XREF.ACTION, XREF.status,  " + 
				" XREF.FACILITY_SYSTEM,XREF.CURRENCY,  " + 
				" XREF.RELEASED_AMOUNT,XREF.CMS_LSP_SYS_XREF_ID,     XREF.MAIN_LINE_CODE,   "+
				" XREF.FACILITY_SYSTEM_ID,  " + 
				" XREF.LINE_NO,     XREF.SERIAL_NO, "+
				" (select branchcode from CMS_FCCBRANCH_MASTER where id = XREF.LIAB_BRANCH and status ='ACTIVE') LIAB_BRANCH,"+
				" to_char(XREF.LIMIT_START_DATE,'DD/MON/YYYY') LIMIT_START_DATE,    " + 
				" nvl(XREF.AVAILABLE,'No') AVAILABLE,    nvl(XREF.REVOLVING_LINE,'No') REVOLVING_LINE,    nvl(XREF.FREEZE,'No') FREEZE ,    XREF.SEGMENT_1,   " + 
				" (select entry_name from common_code_category_entry where XREF.UNCONDI_CANCL = entry_code and ACTIVE_STATUS = '1'" + 
				" and category_code='UNCONDI_CANCL_COMMITMENT' ) UNCONDI_CANCL,     to_char(XREF.DATE_OF_RESET,'DD/MON/YYYY') DATE_OF_RESET , to_char(XREF.LAST_AVAILABLE_DATE,'DD/MON/YYYY')  LAST_AVAILABLE_DATE, " + 
				" XREF.INTERNAL_REMARKS, XREF.LIMIT_TENOR_DAYS,  " + 
				" XREF.product_allowed, nvl(XREF.is_priority_sector,'No') is_priority_sector, XREF.PRIORITY_SECTOR, XREF.INT_RATE_FIX  " + 
				" from CMS_STAGE_LSP_SYS_XREF XREF  where XREF.CMS_LSP_SYS_XREF_ID='"+xrefId+"'";
				
		try {
			DefaultLogger.debug(this,"Select query is for release  "+query_rls_dtls);
			System.out.println("RetrieveScmLineRequest Select query is for release => "+query_rls_dtls);
		    	 dbUtil = new DBUtil();
				 dbUtil.setSQL(query_rls_dtls);
				 rs = dbUtil.executeQuery();
				 while (rs.next()) 
				 {		
					    lineDetails.setScmFlag("Yes".equalsIgnoreCase(rs.getString("scm_flag"))?"Y":"N");
					    if("CLOSE".equalsIgnoreCase(rs.getString("action"))) {lineDetails.setAction("C");}
					    else if ("REOPEN".equalsIgnoreCase(rs.getString("action"))) {lineDetails.setAction("O");}
						else if ("MODIFY".equalsIgnoreCase(rs.getString("action"))) {lineDetails.setAction("U");}
						else if ("NEW".equalsIgnoreCase(rs.getString("action"))){lineDetails.setAction("A");} 
						else { lineDetails.setAction(lineDetails.getAction());}
					    lineDetails.setCurrency(rs.getString("CURRENCY"));
					    lineDetails.setReleasedAmount(rs.getString("RELEASED_AMOUNT"));
					    lineDetails.setMainLineNumber(rs.getString("MAIN_LINE_CODE"));
					    lineDetails.setLiabBranch(rs.getString("LIAB_BRANCH"));
					    lineDetails.setLimitExpiryDate(rs.getString("DATE_OF_RESET"));
					    lineDetails.setLimitStartDate(rs.getString("LIMIT_START_DATE"));
					    lineDetails.setLineNumber(rs.getString("LINE_NO"));
					    lineDetails.setSerialNumber(rs.getString("SERIAL_NO"));
					    lineDetails.setProductAllowed(rs.getString("product_allowed"));
					    lineDetails.setAvailableFlag("Yes".equalsIgnoreCase(rs.getString("AVAILABLE"))?"Y":"N");
					    lineDetails.setCommitment(rs.getString("UNCONDI_CANCL"));
					    lineDetails.setFreezeFlag("Yes".equalsIgnoreCase(rs.getString("FREEZE"))?"Y":"N");
					    lineDetails.setPslFlag("Yes".equalsIgnoreCase(rs.getString("is_priority_sector"))?"Y":"N");
					    lineDetails.setPslValue(rs.getString("PRIORITY_SECTOR"));
					    lineDetails.setRateValue(rs.getString("INT_RATE_FIX"));
					    lineDetails.setRevolvingLine("Yes".equalsIgnoreCase(rs.getString("REVOLVING_LINE"))?"Y":"N");
					    lineDetails.setRemarks(rs.getString("INTERNAL_REMARKS"));
					    lineDetails.setSegment(rs.getString("SEGMENT_1"));
					    lineDetails.setSystemId(rs.getString("FACILITY_SYSTEM_ID"));
					    lineDetails.setSystem(rs.getString("FACILITY_SYSTEM"));
					    lineDetails.setTenorDays(rs.getString("LIMIT_TENOR_DAYS"));
					    lineDetails.setMainLineSystemID(rs.getString("FACILITY_SYSTEM"));
						DefaultLogger.debug(this,"Party details list  "+lineDetails);
						System.out.println("Line details list  "+lineDetails);
				 }
				/* RetrieveScmLineRequest mainLineTmp = getMainLineDetails(lineDetails.getMainLineNumber());
				 lineDetails.setMainLinePartyId(mainLineTmp.getMainLinePartyId());
				 lineDetails.setMainLinePartyName(mainLineTmp.getMainLinePartyName());
				 lineDetails.setMainLineSystemID(mainLineTmp.getMainLineSystemID());*/
				 DefaultLogger.debug(this,"RetrieveScmLineRequest => Between set values .. ");
				 System.out.println("RetrieveScmLineRequest => Between set values .. ");
				 RetrieveScmLineRequest facilityTmp = getFacilityDetails(limitProfileId,limitId);
				 System.out.println("RetrieveScmLineRequest => After getFacilityDetails(limitProfileId,limitId) called .. " );
				 lineDetails.setSublimitFlag(facilityTmp.getSublimitFlag());
				 lineDetails.setGuarantee(facilityTmp.getGuarantee());
				 lineDetails.setGuaranteeliabilityId(facilityTmp.getGuaranteeliabilityId());
				 lineDetails.setGuaranteePartyName(facilityTmp.getGuaranteePartyName());
				 lineDetails.setReleaseableAmount(facilityTmp.getReleaseableAmount());
				 lineDetails.setReleaseFlag(facilityTmp.getReleaseFlag());
				 lineDetails.setAdhocFlag(facilityTmp.getAdhocFlag());
				 lineDetails.setAdhocLimitAmount(facilityTmp.getAdhocLimitAmount());
				 lineDetails.setFacilityName(facilityTmp.getFacilityName());
				 lineDetails.setSanctionAmount(facilityTmp.getSanctionAmount());
				 lineDetails.setSanctionAmountINR(facilityTmp.getSanctionAmountINR());
				 
				 RetrieveScmLineRequest custTmp = getCustDetails(custId);
				 lineDetails.setPartyId(custTmp.getPartyId());
				 lineDetails.setPartyName(custTmp.getPartyName());
				 
				 
				 RetrieveScmLineRequest getudfDetails = getudfDetails(xrefId);
				 lineDetails.setNpa(getudfDetails.getNpa());
				 System.out.println("getudfDetails.getNpa()=>"+getudfDetails.getNpa());
				 DefaultLogger.debug(this,"getudfDetails.getNpa()=>"+getudfDetails.getNpa());
				 if (facilityTmp.getGuarantee().equalsIgnoreCase("Y") && lineDetails.getMainLineNumber()!=null) {
					 lineDetails.setMainLinePartyId(facilityTmp.getMainLinePartyId());
					 lineDetails.setMainLinePartyName(facilityTmp.getMainLinePartyName());
				 }else if(facilityTmp.getGuarantee().equalsIgnoreCase("N") && lineDetails.getMainLineNumber()!=null) {
					 lineDetails.setMainLinePartyId(custTmp.getPartyId());
					 lineDetails.setMainLinePartyName(custTmp.getPartyName()); 
				 }else {
					 lineDetails.setMainLinePartyId(null);
					 lineDetails.setMainLinePartyName(null);
					 lineDetails.setMainLineSystemID(null);
				 }
				 System.out.println("RetrieveScmLineRequest => Line number 116 after set complete.. ");
		         
		     }
		     catch(Exception e){
		    	 System.out.println("Exception caught in RetrieveScmLineRequest getLineDetails => e=>"+e);
		    	 DefaultLogger.debug(this, "Exception caught in RetrieveScmLineRequest getLineDetails => e=>"+e.getMessage() );
					e.printStackTrace();
					return lineDetails;
		     }
		     finally{
		    	 try {
					dbUtil.close();
				} catch (SQLException e) {
					 System.out.println("Exception caught in RetrieveScmLineRequest getLineDetails inside finally => e=>"+e);
					 e.printStackTrace();
				}
		     }
		 
		return lineDetails;
		}	
	
	/*public RetrieveScmLineRequest getMainLineDetails(String mainLineCode) {
		
		String sql = "select "+
				" d.llp_le_id ,"+
				" e.LSP_SHORT_NAME  ,"+
				" c.facility_system_id "+
				 "  from "+
				 " sci_lsp_sys_xref a, sci_lsp_lmts_xref_map b,SCI_LSP_APPR_LMTS c,SCI_LSP_LMT_PROFILE d,SCI_LE_SUB_PROFILE e "+
				 " where a.CMS_LSP_SYS_XREF_ID = b.CMS_LSP_SYS_XREF_ID "+
				 " and b.CMS_LSP_APPR_LMTS_ID = c.CMS_LSP_APPR_LMTS_ID "+
				 " and c.CMS_LIMIT_PROFILE_ID = d.CMS_LSP_LMT_PROFILE_ID "+
				"  and concat(a.line_no,a.serial_no) ='"+mainLineCode+"'"+
				"  and d.LLP_LE_ID = e.LSP_LE_ID ";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		DefaultLogger.debug(this,"Select query is  "+sql);
		RetrieveScmLineRequest lineDetailsTmp = new RetrieveScmLineRequest();
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				lineDetailsTmp.setMainLinePartyId(rs.getString("llp_le_id"));
				lineDetailsTmp.setMainLinePartyName(rs.getString("LSP_SHORT_NAME"));
				lineDetailsTmp.setMainLineSystemID(rs.getString("facility_system_id"));

			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
			return lineDetailsTmp;
		}
		finally{
		finalize(dbUtil,rs);
		}
		return lineDetailsTmp;
	}*/
		
	
public RetrieveScmLineRequest getFacilityDetails(String limitProfileId, String limitId) {
	System.out.println("Inside RetrieveScmLineRequest getFacilityDetails(String limitProfileId, String limitId) .. ");
	String query_fac_dtls = "select SCI.CMS_LIMIT_PROFILE_ID,SCI.CMS_LSP_APPR_LMTS_ID, SCI.FACILITY_NAME,  "+
			 " nvl(SCI.guarantee,'No') guarantee,SCI.CMS_REQ_SEC_COVERAGE , "+
			 " SCI.sub_party_name,(select lsp_short_name from sci_le_sub_profile where to_char (cms_le_sub_profile_id) = SCI.sub_party_name) guaranteePartyName"+
			 ",(select lsp_le_id from sci_le_sub_profile where to_char (cms_le_sub_profile_id) = SCI.sub_party_name) guaranteePartyId,"+
			 "   REGEXP_SUBSTR(SCI.liability_id,'[^-]+',3,3)  AS liability_id ,     nvl(SCI.LMT_TYPE_VALUE,'No') LMT_TYPE_VALUE, " + 
			" SCI.LMT_TENOR,SCI.IS_RELEASED,SCI.IS_ADHOC,SCI.ADHOC_LMT_AMOUNT,SCI.RELEASABLE_AMOUNT " + 
			" FROM SCI_LSP_APPR_LMTS SCI WHERE SCI.CMS_LIMIT_PROFILE_ID ='"+limitProfileId+"'"+
			" AND SCI.CMS_LSP_APPR_LMTS_ID ='"+limitId+"'";
	String query_fac_dtls1 = "select SCI.CMS_LIMIT_PROFILE_ID,SCI.CMS_LSP_APPR_LMTS_ID, SCI.FACILITY_NAME,  "+
			 " nvl(SCI.guarantee,'No') guarantee, SCI.CMS_REQ_SEC_COVERAGE, "+
			 " SCI.sub_party_name,(select lsp_short_name from sci_le_sub_profile where to_char (cms_le_sub_profile_id) = SCI.sub_party_name) guaranteePartyName"+
			 " ,(select lsp_le_id from sci_le_sub_profile where to_char (cms_le_sub_profile_id) = SCI.sub_party_name) guaranteePartyId,"+
			 "   REGEXP_SUBSTR(SCI.liability_id,'[^-]+',3,3)  AS liability_id,     nvl(SCI.LMT_TYPE_VALUE,'No') LMT_TYPE_VALUE, " + 
			" SCI.LMT_TENOR,SCI.IS_RELEASED,SCI.IS_ADHOC,SCI.ADHOC_LMT_AMOUNT,SCI.RELEASABLE_AMOUNT " + 
			" FROM SCI_LSP_APPR_LMTS SCI WHERE SCI.CMS_LIMIT_PROFILE_ID ='"+limitProfileId+"'";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		DefaultLogger.debug(this,"RetrieveScmLineRequest getFacilityDetails Select query for facility is query_fac_dtls=> "+query_fac_dtls);
		System.out.println("1.RetrieveScmLineRequest getFacilityDetails =>Select query for facility is query_fac_dtls=>"+query_fac_dtls);
		System.out.println("2.RetrieveScmLineRequest getFacilityDetails =>Select query for facility is  query_fac_dtls1=> "+query_fac_dtls1);
		RetrieveScmLineRequest facDetailsTmp = new RetrieveScmLineRequest();
		try {
			dbUtil = new DBUtil();
			if(!"".equals(limitId)|| !limitId.equalsIgnoreCase(null)){
				System.out.println("Going to execute query_fac_dtls Query .." );
				    dbUtil.setSQL(query_fac_dtls);
				}else{
					System.out.println("Going to execute query_fac_dtls1 Query .." );
					dbUtil.setSQL(query_fac_dtls1);	
				}
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				
				facDetailsTmp.setSublimitFlag("Yes".equalsIgnoreCase(rs.getString("LMT_TYPE_VALUE"))?"Y":"N");
				facDetailsTmp.setGuarantee("Yes".equalsIgnoreCase(rs.getString("GUARANTEE"))?"Y":"N");
				facDetailsTmp.setGuaranteeliabilityId(rs.getString("liability_id"));
				facDetailsTmp.setGuaranteePartyName(rs.getString("guaranteePartyName"));
				facDetailsTmp.setReleaseableAmount(rs.getString("RELEASABLE_AMOUNT"));
				facDetailsTmp.setMainLinePartyId(rs.getString("guaranteePartyId"));
				facDetailsTmp.setMainLinePartyName(rs.getString("guaranteePartyName"));
				facDetailsTmp.setReleaseFlag(rs.getString("IS_RELEASED"));
				facDetailsTmp.setSanctionAmount(rs.getString("CMS_REQ_SEC_COVERAGE"));
				facDetailsTmp.setSanctionAmountINR(rs.getString("CMS_REQ_SEC_COVERAGE"));
				facDetailsTmp.setAdhocFlag(rs.getString("IS_ADHOC"));
				facDetailsTmp.setAdhocLimitAmount(rs.getString("ADHOC_LMT_AMOUNT"));
				facDetailsTmp.setFacilityName(rs.getString("FACILITY_NAME"));


			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			System.out.println("Exception caught in RetrieveScmLineRequest getFacilityDetails..e=>"+e );
			e.printStackTrace();
			return facDetailsTmp;
		}
		finally{
		finalize(dbUtil,rs);
		}
		return facDetailsTmp;
	}

public RetrieveScmLineRequest getCustDetails(String custId) {
	
	String query_cust_dtls = "Select SPRO.lsp_le_id,   SPRO.lsp_short_name "+
			" from SCI_LE_SUB_PROFILE SPRO ,SCI_LSP_LMT_PROFILE PF " + 
			" where SPRO.CMS_LE_SUB_PROFILE_ID ='"+custId+"'"+
			" AND PF.CMS_CUSTOMER_ID = Spro.CMS_LE_SUB_PROFILE_ID ";
	
	DBUtil dbUtil = null;
	ResultSet rs=null;
	DefaultLogger.debug(this,"Select query is  "+query_cust_dtls);
	System.out.println("RetrieveScmLineRequest getCustDetails..Select query is  "+query_cust_dtls );
	RetrieveScmLineRequest custDetailsTmp = new RetrieveScmLineRequest();
	try {
		dbUtil = new DBUtil();
		dbUtil.setSQL(query_cust_dtls);
		 rs = dbUtil.executeQuery();
		while(rs.next()){
			custDetailsTmp.setPartyId(rs.getString("LSP_LE_ID"));
			custDetailsTmp.setPartyName(rs.getString("lsp_short_name"));
		}
	}catch(Exception e){
		System.out.println("Exception caught in RetrieveScmLineRequest getCustDetails..e=>"+e );
		DefaultLogger.debug(this, e.getMessage());
		e.printStackTrace();
		return custDetailsTmp;
	}
	finally{
	finalize(dbUtil,rs);
	}
	return custDetailsTmp;
}

public RetrieveScmLineRequest getudfDetails(String xrefId) {
	
	String query_udf_dtls = "select UDF20_VALUE from STAGE_SCI_LSP_LMT_XREF_UDF where SCI_LSP_SYS_XREF_ID  ='"+xrefId+"'";
	
	DBUtil dbUtil = null;
	ResultSet rs=null;
	DefaultLogger.debug(this,"Select query is  "+query_udf_dtls);
	RetrieveScmLineRequest udfDetailsTmp = new RetrieveScmLineRequest();
	try {
		dbUtil = new DBUtil();
		dbUtil.setSQL(query_udf_dtls);
		 rs = dbUtil.executeQuery();
		while(rs.next()){
			udfDetailsTmp.setNpa(rs.getString("UDF20_VALUE"));
		}
	}catch(Exception e){
		System.out.println("Exception caught in RetrieveScmLineRequest getudfDetails ... e=>"+e );
		DefaultLogger.debug(this, e.getMessage());
		e.printStackTrace();
		return udfDetailsTmp;
	}
	finally{
	finalize(dbUtil,rs);
	}
	return udfDetailsTmp;
}
	 
		
	

	public String generateSourceSeqNo() {
		String generateSourceString = null;
		String sequence = null;
		 generateSourceString="select concat (to_char(sysdate,'DDMMYYYY'), LPAD(CMS_JS_INTERFACE_SEQ.nextval, 9,'0')) sequence from dual";
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(generateSourceString);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				 sequence = rs.getString("sequence");
			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
			return sequence;
		}
		finally{
		finalize(dbUtil,rs);
		}
		return sequence;
	}
	
	public String getScmFlagfromMain(String limitProfileId,String LineNo,String SerialNo ) {
		System.out.println(" Inside getScmFlagfromMain method ");
		String query ="select nvl(scmFlag,'N'),requestDateTime from cms_js_interface_log where  requestdatetime in "+
				 "    (select max(requestDateTime) from cms_js_interface_log" + 
				"    where limitProfileId ='"+limitProfileId+"' and line_no ='"+LineNo+"' and serial_no='"+SerialNo+"')";
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		String mainScmFlag = "No";
		String ScmFlag = "N";
		DefaultLogger.debug(this,"Select query is  "+query);

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(query);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				ScmFlag = rs.getString(1);
			}
			mainScmFlag = ScmFlag.equalsIgnoreCase("N")?"No":"Yes";
			System.out.println("getScmFlagfromMain method => mainScmFlag=>"+mainScmFlag+" ScmFlag=>"+ScmFlag);
		}catch(Exception e){
			System.out.println("Exception in  getScmFlagfromMain method e=> "+e);
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
			return mainScmFlag;
		}
		finally{
		finalize(dbUtil,rs);
		}
		return mainScmFlag;
	}
	
	
	public String getScmFlagfromStg(String xrefId) {
		System.out.println(" Inside getScmFlagfromStg method ");
		String stgScmFlagqry ="  select nvl(scm_flag,'No') scm_flag from CMS_STAGE_LSP_SYS_XREF where CMS_LSP_SYS_XREF_ID='"+xrefId+"'";
			
		DBUtil dbUtil = null;
		ResultSet rs=null;
		//String tmpScmFlag = "N";
		String stgScmFlag = "No";
		DefaultLogger.debug(this,"Select query is  "+stgScmFlagqry);

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(stgScmFlagqry);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				stgScmFlag = rs.getString(1);
			}
			//stgScmFlag = tmpScmFlag.equalsIgnoreCase("N")?"No":"Yes";
			System.out.println("getScmFlagfromStg method stgScmFlag =>"+stgScmFlag);
		}catch(Exception e){
			System.out.println("Exception in getScmFlagfromStg method e =>"+e);
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
			return stgScmFlag;
		}
		finally{
		finalize(dbUtil,rs);
		}
		return stgScmFlag;
		
	}
	
	
/*	public String getScmFlagStgforStp (String srcRefNo) {
		String stgScmFlagqry ="  select nvl(scm_flag,'No') scm_flag from CMS_STAGE_LSP_SYS_XREF where SOURCE_REF_NO  = '"+srcRefNo+"'";
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		String stgScmFlag = "No";
		DefaultLogger.debug(this,"Select query is  "+stgScmFlagqry);

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(stgScmFlagqry);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				stgScmFlag = rs.getString(1);
			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
			return stgScmFlag;
		}
		finally{
		finalize(dbUtil,rs);
		}
		return stgScmFlag;
		
	}
	
	public String getScmFlagMainforStp (String srcRefNo) {
		String mainScmFlagqry ="  select nvl(scm_flag,'No') scm_flag from SCI_LSP_SYS_XREF where SOURCE_REF_NO  = '"+srcRefNo+"'";
		
		DBUtil dbUtil = null;
		ResultSet rs=null;
		String mainScmFlag = "No";
		DefaultLogger.debug(this,"Select query is  "+mainScmFlagqry);

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(mainScmFlagqry);
			 rs = dbUtil.executeQuery();
			while(rs.next()){
				mainScmFlag = rs.getString(1);
			}
		}catch(Exception e){
			DefaultLogger.debug(this, e.getMessage());
			e.printStackTrace();
			return mainScmFlag;
		}
		finally{
		finalize(dbUtil,rs);
		}
		return mainScmFlag;
		
	}*/
	
	/*public RetrieveScmLineRequest getLineDetailsforStp(String srcRefId) {
		RetrieveScmLineRequest lineDetails=new RetrieveScmLineRequest();
		ResultSet rs = null;
		DBUtil dbUtil =null;
		String query ="SELECT   "+
				"SCI.CMS_LIMIT_PROFILE_ID,"+
				"SCI.CMS_LSP_APPR_LMTS_ID,"+
				" nvl(XREF.scm_flag,'No') scm_flag ,   "+
				" MAPSS.CMS_STATUS,   "+
				" XREF.ACTION,"+
				" XREF.status,"+
				"SPRO.lsp_le_id,   "+
				"SPRO.lsp_short_name,   "+
				" XREF.FACILITY_SYSTEM,  "+
				"SCI.FACILITY_NAME,   "+
				" XREF.CURRENCY,  "+
				"SCI.CMS_REQ_SEC_COVERAGE ,  "+ 
				"nvl(SCI.guarantee,'No') guarantee,  "+
				"(select lsp_short_name from sci_le_sub_profile where to_char (cms_le_sub_profile_id) = SCI.sub_party_name) guaranteePartyName," + 
				" REGEXP_SUBSTR(SCI.liability_id,'[^-]+',3,3)  AS liability_id,XREF.RELEASED_AMOUNT,SCI.RELEASABLE_AMOUNT,XREF.MAIN_LINE_CODE," + 
				" (select(CASE when(SCI.guarantee = 'No' and  XREF.MAIN_LINE_CODE is not null) then SPRO.lsp_le_id " + 
				" when(SCI.guarantee = 'Yes' and  XREF.MAIN_LINE_CODE is not null) then " + 
				" (select lsp_le_id  from sci_le_sub_profile where to_char (cms_le_sub_profile_id) = SCI.sub_party_name)" + 
				" else null END) from dual) mainline_partyId," + 
				" (select(CASE when(SCI.guarantee = 'No' and  XREF.MAIN_LINE_CODE is not null) then SPRO.lsp_le_id " + 
				" when(SCI.guarantee = 'Yes' and  XREF.MAIN_LINE_CODE is not null) then " + 
				" (select lsp_short_name from sci_le_sub_profile where to_char (cms_le_sub_profile_id) = SCI.sub_party_name)" + 
				" else null END) from dual) mainline_party_name,"+
				" (select lsp_short_name from sci_le_sub_profile where to_char (cms_le_sub_profile_id) = SCI.sub_party_name) guaranteePartyName,   "+
				"  nvl(SCI.LMT_TYPE_VALUE,'No') LMT_TYPE_VALUE,"+
				" XREF.RELEASED_AMOUNT,   "+
				" XREF.CMS_LSP_SYS_XREF_ID,   "+
				" XREF.ACTION,   "+
				" XREF.MAIN_LINE_CODE,   "+
				" XREF.FACILITY_SYSTEM_ID,   "+
				" XREF.LINE_NO,    "+
				" XREF.SERIAL_NO,    "+
				" (select branchcode from CMS_FCCBRANCH_MASTER where id = XREF.LIAB_BRANCH and status ='ACTIVE') LIAB_BRANCH ,   "+
				" to_char(XREF.LIMIT_START_DATE,''DD/MON/YYYY'') LIMIT_START_DATE,    "+
				" nvl(XREF.AVAILABLE,'No') AVAILABLE ,   "+
				" nvl(XREF.REVOLVING_LINE,'No') REVOLVING_LINE,   "+
				" nvl(XREF.FREEZE,'No') FREEZE,   "+
				" XREF.SEGMENT_1,   "+
				" (select entry_name from common_code_category_entry where XREF.UNCONDI_CANCL = entry_code and ACTIVE_STATUS = '1'" + 
				"and category_code='UNCONDI_CANCL_COMMITMENT' ) UNCONDI_CANCL,    "+
				" to_char(XREF.DATE_OF_RESET,'DD/MON/YYYY') DATE_OF_RESET,   "+
				" to_char(XREF.LAST_AVAILABLE_DATE,'DD/MON/YYYY') LAST_AVAILABLE_DATE ,   "+
				" XREF.INTERNAL_REMARKS,   "+
				" XREF.LIMIT_TENOR_DAYS,"+
				"SCI.IS_RELEASED,"+
				"SCI.IS_ADHOC,"+
				"SCI.ADHOC_LMT_AMOUNT,"+
				"SCI.RELEASABLE_AMOUNT,"+
				" XREF.product_allowed,"+
				" nvl(XREF.is_priority_sector,'No') is_priority_sector,"+
				" XREF.PRIORITY_SECTOR,"+
				" XREF.INT_RATE_FIX,"+
				"  UDF.UDF20_VALUE   "+
				" FROM "+
				"  SCI_LSP_APPR_LMTS SCI,"+
				 " SCI_LSP_SYS_XREF XREF,"+
				"  SCI_LSP_LMTS_XREF_MAP MAPSS,"+
				 " SCI_LSP_LMT_PROFILE PF,"+
				 " SCI_LE_SUB_PROFILE SPRO, "+
				 " SCI_LSP_LMT_XREF_UDF UDF"+
				 " WHERE SCI.CMS_LIMIT_PROFILE_ID    = PF.CMS_LSP_LMT_PROFILE_ID"+
				 " AND PF.CMS_CUSTOMER_ID          = Spro.CMS_LE_SUB_PROFILE_ID"+
				  " AND SCI.CMS_LSP_APPR_LMTS_ID  = MAPSS.CMS_LSP_APPR_LMTS_ID"+
				 "  AND MAPSS.CMS_LSP_SYS_XREF_ID = XREF.CMS_LSP_SYS_XREF_ID"+
				  " AND XREF.CMS_LSP_SYS_XREF_ID = UDF.SCI_LSP_SYS_XREF_ID"+
				  " AND XREF.SOURCE_REF_NO = '"+srcRefId+"'";
				
		StringBuffer strBuffer = new StringBuffer(query.trim());
		try {
			DefaultLogger.debug(this,"Select query is  "+strBuffer);
		    	 dbUtil = new DBUtil();
				 dbUtil.setSQL(strBuffer.toString());
				 rs = dbUtil.executeQuery();
				 String latestAction = null;
				 while (rs.next()) 
				 {		
					  latestAction = getLatestOperationStatus(rs.getString("CMS_LIMIT_PROFILE_ID"),rs.getString("LINE_NO"),rs.getString("SERIAL_NO"));

					    lineDetails.setScmFlag(rs.getString("scm_flag").equalsIgnoreCase("Yes")?"Y":"N");
					    if(rs.getString("action").equalsIgnoreCase("CLOSE")) {lineDetails.setAction("C");}
					    else if (rs.getString("action").equalsIgnoreCase("REOPEN")) {lineDetails.setAction("O");}
						else if (rs.getString("action").equalsIgnoreCase("MODIFY")) {lineDetails.setAction("U");}
						else if (rs.getString("action").equalsIgnoreCase("NEW")){lineDetails.setAction("A");} 
						else { lineDetails.setAction(lineDetails.getAction());}
					    lineDetails.setPartyId(rs.getString("LSP_LE_ID"));
					    lineDetails.setPartyName(rs.getString("lsp_short_name"));
					    lineDetails.setAdhocFlag(rs.getString("IS_ADHOC"));
					    lineDetails.setAdhocLimitAmount(rs.getString("ADHOC_LMT_AMOUNT"));
					    lineDetails.setAvailableFlag(rs.getString("AVAILABLE").equalsIgnoreCase("Yes")?"Y":"N");
					    lineDetails.setCommitment(rs.getString("UNCONDI_CANCL"));
					    lineDetails.setCurrency(rs.getString("CURRENCY"));
					    lineDetails.setFacilityName(rs.getString("FACILITY_NAME"));
					    lineDetails.setSublimitFlag(rs.getString("LMT_TYPE_VALUE").equalsIgnoreCase("Yes")?"Y":"N");
					    lineDetails.setFreezeFlag(rs.getString("FREEZE").equalsIgnoreCase("Yes")?"Y":"N");
					    lineDetails.setGuarantee(rs.getString("GUARANTEE").equalsIgnoreCase("Yes")?"Y":"N");
					    lineDetails.setGuaranteeliabilityId(rs.getString("liability_id"));
					    lineDetails.setGuaranteePartyName(rs.getString("guaranteePartyName"));
					    lineDetails.setLiabBranch(rs.getString("LIAB_BRANCH"));
					    lineDetails.setLimitExpiryDate(rs.getString("DATE_OF_RESET"));
					    lineDetails.setLimitStartDate(rs.getString("LIMIT_START_DATE"));
					    lineDetails.setLineNumber(rs.getString("LINE_NO"));
					    lineDetails.setMainLineNumber(rs.getString("MAIN_LINE_CODE"));
					    lineDetails.setMainLinePartyId(rs.getString("mainline_partyId"));
						lineDetails.setMainLinePartyName(rs.getString("mainline_party_name"));
					    lineDetails.setMainLineSystemID(rs.getString("FACILITY_SYSTEM"));
					    lineDetails.setNpa(rs.getString("UDF20_VALUE"));
					    lineDetails.setProductAllowed(rs.getString("product_allowed"));
					    lineDetails.setPslFlag(rs.getString("is_priority_sector").equalsIgnoreCase("Yes")?"Y":"N");
					    lineDetails.setPslValue(rs.getString("PRIORITY_SECTOR"));
					    lineDetails.setRateValue(rs.getString("INT_RATE_FIX"));
					    lineDetails.setReleaseableAmount(rs.getString("RELEASABLE_AMOUNT"));
					    lineDetails.setReleasedAmount(rs.getString("RELEASED_AMOUNT"));
					    lineDetails.setReleaseFlag(rs.getString("IS_RELEASED"));
					    lineDetails.setRevolvingLine(rs.getString("REVOLVING_LINE").equalsIgnoreCase("Yes")?"Y":"N");
					    lineDetails.setRemarks(rs.getString("INTERNAL_REMARKS"));
					    lineDetails.setSanctionAmountINR(rs.getString("CMS_REQ_SEC_COVERAGE"));
					    lineDetails.setSanctionAmount(rs.getString("CMS_REQ_SEC_COVERAGE"));
					    lineDetails.setTenorDays(rs.getString("LIMIT_TENOR_DAYS"));
						DefaultLogger.debug(this,"Party details list  "+lineDetails);
				 }
				 if(!lineDetails.getAction().equals("C")) {
						if(latestAction!=null && latestAction.equalsIgnoreCase("A")) {
							lineDetails.setAction("A");
						}
						}
				 RetrieveScmLineRequest lineDetailsTmp = getMainLineDetails(lineDetails.getMainLineNumber());
				 lineDetails.setMainLinePartyId(lineDetailsTmp.getMainLinePartyId());
				 lineDetails.setMainLinePartyName(lineDetailsTmp.getMainLinePartyName());
				 lineDetails.setMainLineSystemID(lineDetailsTmp.getMainLineSystemID());
		         
		     }
		     catch(Exception e){
		    	 DefaultLogger.debug(this,e.getMessage() );
					e.printStackTrace();
		     }
		     finally{
		    	 try {
					dbUtil.close();
				} catch (SQLException e) {
					
				}
		     }
		 
		return lineDetails;
		}	*/
	
	
	public String getLatestOperationStatus(String limitProfileId,String LineNo,String SerialNo )  {
		
		
		
		String operationSuccessCount = "select count (1) AS CNT from cms_js_interface_log "+ 
		" where limitprofileId = '"+limitProfileId+"'  and line_no = '"+LineNo+"' and serial_no = '"+SerialNo+"'   AND STATUS='Success' "+
		 " order by requestdatetime desc";
		
	DefaultLogger.debug(this,"Select query for operationSuccessCount is ==  "+operationSuccessCount);
	System.out.println("Select query for operationSuccessCount is in scmLineDaoImpl ==  "+operationSuccessCount);

	String counts = "";
	try {
		List queryForList = getJdbcTemplate().queryForList(operationSuccessCount);
		for (int i = 0; i < queryForList.size(); i++) {
			Map  map = (Map) queryForList.get(i);
			if(null!=map.get("CNT") && !"".equals(map.get("CNT"))) {
				counts= map.get("CNT").toString();
			}else{
				counts = "";
			}
		}
		System.out.println("scmLineDaoImpl.java=> getLatestOperationStatus=>counts=>"+counts);
	}
	catch (Exception ex) {
		System.out.println("Exception in getLatestOperationStatus scmLineDaoImpl=>Line 628=>counts=>"+counts);

		ex.printStackTrace();
		
	}


	System.out.println(" getLatestOperationStatus scmLineDaoImpl=>Line 635=>counts=>"+counts);

	if ("".equals(counts) || "0".equals(counts) || null==counts) {
		
		String operationStatusQuery = "select OPERATION , status,ERRORMESSAGE from (select OPERATION,SCMFLAG,partyId,requestdatetime,status,ERRORMESSAGE from cms_js_interface_log " + 
				" where limitprofileId = '"+limitProfileId+"'  and line_no = '"+LineNo+"' and serial_no = '"+SerialNo+"'  " + 
				" order by requestdatetime desc) where rownum=1";
		
	DefaultLogger.debug(this,"Select query for operationStatusQuery scmLineDaoImpl is  "+operationStatusQuery);

	System.out.println(" ********Inside scmLineDaoImpl ********645**********Select query for operationStatusQuery is =>"+operationStatusQuery);

	List resultList = getJdbcTemplate().query(operationStatusQuery, new RowMapper() {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			System.out.println(" status is : "+rs.getString("status")+", Error message is : "+rs.getString("ERRORMESSAGE")+", operation is : "+rs.getString("OPERATION"));
			if(null==rs.getString("status") || rs.getString("status").isEmpty()) {
				System.out.println(" status is null or empty so setting operation as A");
				return "A";
			}else if(rs.getString("status").equalsIgnoreCase("Error")||rs.getString("status").equalsIgnoreCase("Fail")) {
				System.out.println(" status is  "+rs.getString("status"));
	/*			if( null!=rs.getString("ERRORMESSAGE") && !rs.getString("ERRORMESSAGE").isEmpty() && rs.getString("ERRORMESSAGE").contains("Line does not exist to update")) {
					System.out.println(" status is fail/error and error message is  'Line does not exist to update' so setting operation as A");
					return "A";
				}else if( null!=rs.getString("ERRORMESSAGE") && !rs.getString("ERRORMESSAGE").isEmpty() && rs.getString("ERRORMESSAGE").contains("Duplicate Line exists "))  {
					return "U";
				}else {
					System.out.println(" at line 660 inside else of errorMessage so setting operation from previous operation");
					return rs.getString("OPERATION");
				}*/
				return rs.getString("OPERATION");
				}else {
					System.out.println(" at line 664 inside else of Status fail/Error so setting operation as U ");
					return "U";
				}
		}
	});

	if(null!= resultList && resultList.size()==0){
		resultList.add("A");
		System.out.println(" *******672******no data present means adding  1st time so setting operation as A");
		
	}else if(null==resultList){
		resultList = new ArrayList();
		resultList.add("A");
		System.out.println(" ********677*********no data present means adding 1st time so setting operation as A in a null resultList");
	}
	System.out.println("as no previous data found operation : "+resultList.get(0).toString());
	return resultList.get(0).toString();
	}else {
		System.out.println(" at line 682 inside else of count=0,empty,null so setting operation as U ");
		return "U";
	}
	}
	
	public static void finalize(DBUtil dbUtil, ResultSet rs) {
		try {
			if (null != rs) {
				rs.close();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

}
