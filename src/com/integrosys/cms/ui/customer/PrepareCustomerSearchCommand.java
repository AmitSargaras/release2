package com.integrosys.cms.ui.customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;

public class PrepareCustomerSearchCommand extends AbstractCommand {

	public HashMap doExecute(HashMap arg0) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		DefaultLogger.debug(this, " - BEGIN. ");
		HashMap resultMap = new HashMap();
		//27th Aug, country related changes, for HDFC
		/*CountryList list = CountryList.getInstance();
		ArrayList countryLabels = new ArrayList(list.getCountryLabels());
		ArrayList countryValues = new ArrayList(list.getCountryValues());


		resultMap.put("countryLabels", countryLabels);
		resultMap.put("countryValues", countryValues);
*/
		CommonCodeList commonCode = CommonCodeList.getInstance(null, null, ICMSUIConstant.COMMON_CODE_REF_LE_ID_TYPE,
				null);
		Collection leTypeValues = commonCode.getCommonCodeValues();
		Collection leTypeLabels = commonCode.getCommonCodeLabels();
		if (leTypeValues == null) {
			leTypeValues = new ArrayList();
		}
		if (leTypeLabels == null) {
			leTypeLabels = new ArrayList();
		}
		resultMap.put("leTypeValues", leTypeValues);
		resultMap.put("leTypeLabels", leTypeLabels);
		// reset search criteria for validation failure case.
		resultMap.put("aCustomerSearchCriteria", arg0.get("aCustomerSearchCriteria"));

		HashMap returnMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		//boolean update =getUpdateDocument();
		/*if(update){
			updateDuplicateCAMError();
		}*/
		/*if(update){
		DefaultLogger.debug(this, " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> UPDATE SUCESSFULLY  ");
		}*/
		DefaultLogger.debug(this, " - END. ");
		return returnMap;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "aCustomerSearchCriteria",
				"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", FORM_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "countryLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "countryValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "leTypeLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "leTypeValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "aCustomerSearchCriteria", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", FORM_SCOPE } };
	}
	
	
	/*private void updateDuplicateCAMError(){
		
		DBUtil dbUtil = null;
		
		String sql = "SELECT MIN(a.cms_lsp_lmt_profile_id) as oldId,  a.llp_le_id as custId,  MAX(a.cms_lsp_lmt_profile_id) as newId  "+
		" FROM SCI_LSP_LMT_PROFILE a WHERE a.llp_le_id IN  ( SELECT DISTINCT (o.llp_le_id)  FROM SCI_LSP_LMT_PROFILE o  INNER JOIN    "+
		"  (SELECT llp_le_id,      COUNT(*) AS dupeCount FROM SCI_LSP_LMT_PROFILE    GROUP BY llp_le_id    HAVING COUNT(*) > 1    ) oc  " +
		" ON o.llp_le_id = oc.llp_le_id  ) GROUP BY a.llp_le_id ";

		
		try {
			ResultSet rsOldIdNewId=null;
			HashMap oldIdNewIdMap = new HashMap();
			ArrayList oldIdList= new ArrayList();
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
//			int  rs = dbUtil.executeUpdate();
//			dbUtil.commit();
			
			rsOldIdNewId = dbUtil.executeQuery();
			  while(rsOldIdNewId.next()){
				  oldIdList.add(String.valueOf(rsOldIdNewId.getLong("oldId")));
				  oldIdNewIdMap.put(String.valueOf(rsOldIdNewId.getLong("oldId")),String.valueOf( rsOldIdNewId.getLong("newId")));
			  }
			if(oldIdList!=null && oldIdList.size()>0){
				for (int i = 0; i < oldIdList.size(); i++) {
					String oldId=(String)oldIdList.get(i);
					String newId=(String)oldIdNewIdMap.get(oldId);
					String oldTransactionId="";
					String sqlTransaction = "select reference_id, transaction_id,staging_reference_id from transaction where reference_id= "+oldId+" and transaction_type='LIMITPROFILE' ";
					ResultSet rsOldIdTransId=null;
					dbUtil.setSQL(sqlTransaction);
					rsOldIdTransId= dbUtil.executeQuery();
					 while(rsOldIdTransId.next()){
						 oldTransactionId=rsOldIdTransId.getString("transaction_id");
					  }
					String deleteQueryOldId=" delete from sci_lsp_lmt_profile where cms_lsp_lmt_profile_id= "+oldId;
					String updateQueryOldId=" update sci_lsp_lmt_profile set cms_lsp_lmt_profile_id= "+oldId+" where cms_lsp_lmt_profile_id= "+newId;
					String deleteTransactionQueryOldId=" delete from transaction where reference_id= "+oldId+" and transaction_type='LIMITPROFILE' ";
					String updateTransactionQueryNewId=" update transaction set transaction_id= "+oldTransactionId+" , reference_id= "+oldId+" where reference_id= "+newId+" and transaction_type='LIMITPROFILE' ";
					String updateTrancHistoryNewId=" update trans_history set transaction_id= "+oldTransactionId+" , reference_id= "+oldId+" where reference_id= "+newId+" and transaction_type='LIMITPROFILE' ";
					dbUtil.setSQL(deleteQueryOldId);
					dbUtil.executeUpdate();
					dbUtil.commit();
					dbUtil.setSQL(updateQueryOldId);
					dbUtil.executeUpdate();
					dbUtil.commit();
					dbUtil.setSQL(deleteTransactionQueryOldId);
					dbUtil.executeUpdate();
					dbUtil.commit();
					dbUtil.setSQL(updateTransactionQueryNewId);
					dbUtil.executeUpdate();
					dbUtil.commit();
					dbUtil.setSQL(updateTrancHistoryNewId);
					dbUtil.executeUpdate();
					dbUtil.commit();
				}
				
				
			}
			
			
		}
		catch (SQLException ex) {
			// do nothing
		}
		catch (Exception ex) {
			// do nothing
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				// do nothing
			}
		}
		
	}
	
	private boolean getUpdateTableCAM(){
		
		DBUtil dbUtil = null;
		
		String sql = "SELECT MIN(a.cms_lsp_lmt_profile_id),  a.llp_le_id,  MAX(a.cms_lsp_lmt_profile_id)FROM SCI_LSP_LMT_PROFILE a "+
		"WHERE a.llp_le_id IN  ( SELECT DISTINCT (o.llp_le_id)  FROM SCI_LSP_LMT_PROFILE o  INNER JOIN    (SELECT llp_le_id,      COUNT(*) AS dupeCount "+
		"FROM SCI_LSP_LMT_PROFILE    GROUP BY llp_le_id    HAVING COUNT(*) > 1    ) oc  ON o.llp_le_id = oc.llp_le_id  ) GROUP BY a.llp_le_id ";
		
		DefaultLogger.debug(this, "--------1---------"+sql);
		ResultSet rs=null;

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
		
			  rs = dbUtil.executeQuery();
			  if(rs.next()){
				  return true;
			  }else{
				  return false;
			  }
		}
		catch (SQLException ex) {
			// do nothing
		}
		catch (Exception ex) {
			// do nothing
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				// do nothing
			}
		}
		return false;
		
	}*/
	
/*private boolean getUpdateDocument(){
		
		DBUtil dbUtil = null;
		
		String sql = " update cms_checklist_item set status='RECEIVED' where status='PENDING_RECEIVED' and "+
		" checklist_id in (select checklist_id from cms_checklist where category in ('REC','LAD')) ";
		
		String sql2=" update cms_checklist_item set status='RECEIVED' where status='DEFERRED' and "+
		" received_date is not null and checklist_id in (select checklist_id from cms_checklist where category in ('REC','LAD')) ";
		
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.executeUpdate();
			dbUtil.commit();
			dbUtil.setSQL(sql2);
			dbUtil.executeUpdate();
			dbUtil.commit();
		}
		catch (SQLException ex) {
			// do nothing
		}
		catch (Exception ex) {
			// do nothing
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				// do nothing
			}
		}
		return true;
		
	}*/
}
