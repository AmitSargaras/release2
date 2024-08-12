package com.integrosys.cms.app.eod.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.eod.sync.bus.EODSyncRecordStatus;
import com.integrosys.cms.app.eod.sync.bus.EODSyncStatusException;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.poi.report.OBFilter;
import com.integrosys.cms.batch.common.syncmaster.climstocps.xml.schema.Param;
import com.integrosys.cms.batch.common.syncmaster.climstocps.xml.schema.SyncMasterTemplateOut;
import com.integrosys.cms.batch.common.syncmaster.climstocps.xml.schema.WhereClause;
import com.integrosys.cms.batch.eod.IEodSyncConstants;
import com.integrosys.cms.ui.commoncodeentry.item.MaintainCCEntryForm;
import com.integrosys.cms.ui.docglobal.DocumentationGlobalForm;
import com.integrosys.cms.ui.otherbankbranch.OtherBankForm;
import com.integrosys.cms.ui.otherbankbranch.OtherBranchForm;

public class EODSyncOutMastersDaoImpl extends JdbcTemplateAdapter implements IEODSyncOutMasterDao{

	private IGeneralParamDao generalParam;
	
	
	/**
	 * @return the generalParam
	 */
	public IGeneralParamDao getGeneralParam() {
		return generalParam;
	}


	/**
	 * @param generalParam the generalParam to set
	 */
	public void setGeneralParam(IGeneralParamDao generalParam) {
		this.generalParam = generalParam;
	}


	/**
	 * @param reportQueryResult
	 * @param reports
	 * @return
	 */
	public List<Object[]> getReportQueryResult( SyncMasterTemplateOut syncMasterTemplateOut,OBFilter filter) {
		List<Object[]> reportQueryResult= new LinkedList<Object[]>(); 
		/** genrating query from report object, object will be ob class containing filter values*/
		String sql=generateQuery(syncMasterTemplateOut, filter);
		
		try {
			reportQueryResult = getJdbcTemplate().query(sql,new RowMapper() {
				public Object mapRow(ResultSet rs, int rownum) throws SQLException {
//					System.out.println("============inside Row mapper=========");
					int columnCount = rs.getMetaData().getColumnCount();
	//				System.out.println("===============Column Count================"+columnCount);
					Object[] objArray= new Object[columnCount];
						for (int i = 0; i < columnCount; i++) {
							objArray[i]=rs.getObject(i+1); 
						}
					return objArray;
				}
			});
		} catch (Exception e) {
			throw new EODSyncStatusException("Error excecuting query.Please check the report template configuration.",e); 
		}
		return reportQueryResult;
	}
	
	
//	TODO :Logic of genrating query will change(for where claue)
	/**
	 * genrating query from report object
	 */
	private String generateQuery(SyncMasterTemplateOut syncMasterTemplateOut,OBFilter filter) {

		StringBuffer query = new StringBuffer();
		query.append(syncMasterTemplateOut.getQuery());
		
		if(syncMasterTemplateOut.getWhereClause()!=null){
			//creating Criteria
			String criteria = createCriteria(syncMasterTemplateOut,filter);
			if(criteria!=null && !("".equals(criteria.trim()))){
				query.append(" WHERE ");
				query.append(criteria);
			}
		}
		return query.toString();
	}


	/**
	 * @param syncMasterTemplateOut
	 */
	private String createCriteria(SyncMasterTemplateOut syncMasterTemplateOut,OBFilter filter) {
		StringBuffer criteria= new StringBuffer();
		
		WhereClause whereClause = syncMasterTemplateOut.getWhereClause();
		if(whereClause.getMandatoryClause()!=null && !"".equals(whereClause.getMandatoryClause().trim()))
			criteria.append(whereClause.getMandatoryClause());
		
		String applicationDate = getApplicationDate();
		Param[] params = whereClause.getParam();
		if(params!=null && params.length>0){
			for (int i = 0; i < params.length; i++) {
				Param param = params[i];
				DefaultLogger.debug(this,"processing param------------"+param.getName());
					if("ApplicationDate".equals(param.getName())){
							criteria.append(" "+param.getCondition());
							criteria.append("'");
							criteria.append(getApplicationDate());
							criteria.append("'");
					}
			}
		}
		if(whereClause.getOrderAndGroupByClause() !=null && !"".equals(whereClause.getOrderAndGroupByClause().trim()))
			criteria.append(whereClause.getOrderAndGroupByClause());
		
		return criteria.toString();
	}
 
	
	
	private String getApplicationDate() {
			IGeneralParamEntry generalParamEntry = getGeneralParam().getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
			String applicationDate = generalParamEntry.getParamValue();
		return applicationDate;
	}


	public List<String[]> getReportDataList(List<Object[]> reportQueryResult,
			Map parameters) {

		int noOfColumns = ((String[]) parameters.get(IEodSyncConstants.KEY_COL_LABEL)).length;

		List<String[]> dataList = new LinkedList<String[]>();

		if (reportQueryResult.size() > 0) {
			DefaultLogger.debug(this,"reportQueryResult.size()---------------->"+reportQueryResult.size());
			int no=1;
			for (Object[] objects : reportQueryResult) {
				String records[] = new String[noOfColumns];

				if (objects.length != noOfColumns) {
					throw new EODSyncStatusException("Query columns dosen't match with configured columns");
				}

				for (int i = 0; i < noOfColumns; i++) {
					if (objects[i] != null && !"".equals(objects[i])) {
						records[i] = convertToString(objects[i]);
					} else {
						records[i] = "";
					}
				}
				dataList.add(records);
				no++;
			}

		}

		return dataList;
	}
	public String convertToString(Object object) {
		if (object != null && !object.equals("")) {
			if (object instanceof Boolean) {
				if ((Boolean) object == true)
					return "Yes";
				else
					return "No";
			}
			String record = object.toString();
			//To replace Enter with space if any
			record = record.replaceAll("\r\n", " ");
			record = record.replaceAll("\n", " ");
			return record;
		} else
			return "";
	}
	static Map<String, String> climsToCpsMastersMap= new HashMap<String, String>();
	static{
		climsToCpsMastersMap.put("OTHERBANK_EOD","Cms_Other_Bank");
		climsToCpsMastersMap.put("OTHERBANK_BRANCH_EOD","Cms_Other_Bank_Branch");
		climsToCpsMastersMap.put("STATEMENT_TYPE_EOD","Common_Code_Category_Entry");
		climsToCpsMastersMap.put("DOC_CHECKLIST_GLOBAL_EOD","cms_document_globallist");
		climsToCpsMastersMap.put("DOC_CHECKLIST_FACILITY_GLOBAL_EOD","cms_document_globallist");
		climsToCpsMastersMap.put("DOC_CHECKLIST_SECURITY_GLOBAL_EOD","cms_document_globallist");
		climsToCpsMastersMap.put("DOC_CHECKLIST_CAM_EOD","cms_document_globallist");
		climsToCpsMastersMap.put("DOC_CHECKLIST_OTHER_EOD","cms_document_globallist");
	}
	public void updateEodSyncStatus(String masterName, List<String[]> reportDataList){
		if(climsToCpsMastersMap.containsKey(masterName)){
			String updateQuery = "update "+climsToCpsMastersMap.get(masterName)+" set EOD_SYNC_STATUS =? where ";
			updateQuery += getMastersIdClause(masterName);
			
			for (String[] record : reportDataList) {
				try {
					getJdbcTemplate().update(updateQuery,getSpecDataByMasterName(masterName,record));
				}
				catch (Exception e) {
					DefaultLogger.debug(this, "Error while updating sync staus for "+masterName);
					e.printStackTrace();
				}
			}
		}
		
	}


	private String getMastersIdClause(String masterName) {
		String idSpec="";
		if("STATEMENT_TYPE_EOD".equals(masterName)){
			idSpec =" Entry_Id = ? ";
		}else if("DOC_CHECKLIST_GLOBAL_EOD".equals(masterName)){
			idSpec =" DOCUMENT_ID = ? ";
		}else{
			idSpec =" id= ? ";
		}
		return idSpec;
	}


	private Object[] getSpecDataByMasterName(String masterName, String[] record) {
		
		Object[] specData = new Object[]{};
		String status="";
		if("OTHERBANK_EOD".equals(masterName)){
			status=getEodSyncStatus(record[13]);
			specData =new Object[]{status,record[0]};//Action,Other Bank Id
		}else if("OTHERBANK_BRANCH_EOD".equals(masterName)){
			status=getEodSyncStatus(record[15]);
			specData =new Object[]{status,record[0]};//Action,Other Bank Branch Id
		}else if("STATEMENT_TYPE_EOD".equals(masterName)){
			status=getEodSyncStatus(record[3]);
			specData =new Object[]{status,record[0]};//Action,entry Id
		}else if("DOC_CHECKLIST_GLOBAL_EOD".equals(masterName)){
			status=getEodSyncStatus(record[4]);
			specData =new Object[]{status,record[0]};//Action,Other Bank Id
		}
		return specData;
	}
	private Object[] getSpecDataByMstName(String masterName, Object record) {
		
		Object[] specData = new Object[]{};
		String status="";
		if("OTHERBANK_EOD".equals(masterName)){
			OtherBankForm  othBank=(OtherBankForm)record;
			specData =new Object[]{getEODSyncStatusbyFileStatus(othBank.getSyncStatus(),othBank.getSyncAction()),othBank.getId()};
		}else if("OTHERBANK_BRANCH_EOD".equals(masterName)){
			OtherBranchForm  othBranch=(OtherBranchForm)record;
			specData =new Object[]{getEODSyncStatusbyFileStatus(othBranch.getSyncStatus(),othBranch.getSyncAction()),othBranch.getId()};
		}else if("STATEMENT_TYPE_EOD".equals(masterName)){
			MaintainCCEntryForm codeEntries=(MaintainCCEntryForm)record;
			specData =new Object[]{getEODSyncStatusbyFileStatus(codeEntries.getSyncStatus(),codeEntries.getSyncAction()),codeEntries.getEntryId()};
		}else if("DOC_CHECKLIST_GLOBAL_EOD".equals(masterName)){
			DocumentationGlobalForm documentItem=(DocumentationGlobalForm)record;
			specData =new Object[]{getEODSyncStatusbyFileStatus(documentItem.getSyncStatus(),documentItem.getSyncAction()),documentItem.getItemID()};
		}
		return specData;
	}
	

	private String getEodSyncStatus(String action) {
		if("I".equals(action)){
			return EODSyncRecordStatus.INSERT_SENT.name();	
		}else if("U".equals(action)){
			return EODSyncRecordStatus.UPDATE_SENT.name();	
		}else if("D".equals(action)){
			return EODSyncRecordStatus.DELETE_SENT.name();	
		}
		return null;
	}
	public void updateEodSyncStatus(String masterName, EODSyncRecordStatus syncStatus){
		if(climsToCpsMastersMap.containsKey(masterName)){
			String updateQuery =new StringBuffer ("update ")
								.append(climsToCpsMastersMap.get(masterName))
								.append(" set EOD_SYNC_STATUS =? where EOD_SYNC_STATUS IN(") 
								.append("'").append(EODSyncRecordStatus.INSERT_SENT.name())
								.append("','").append(EODSyncRecordStatus.UPDATE_SENT.name())
								.append("','").append(EODSyncRecordStatus.DELETE_SENT.name())
								.append("')").toString();
				
			try {
				getJdbcTemplate().update(updateQuery,new Object[]{syncStatus.name()});
			}
			catch (Exception e) {
				DefaultLogger.debug(this, "Error while updating sync staus for "+masterName);
				e.printStackTrace();
			}
	
		}else{
			DefaultLogger.debug(this, masterName+" is not configured.");
		}
	}
	/*public void updateEodSyncStatus(String masterName, String opStatus,String id){
		if(climsToCpsMastersMap.containsKey(masterName)){
			String syncStatus = getEODSyncStatusbyFileStatus(opStatus);
			String updateQuery ="update "+climsToCpsMastersMap.get(masterName)+" set EOD_SYNC_STATUS =? where";
			updateQuery+=getMastersIdClause(masterName);
				
			try {
				getJdbcTemplate().update(updateQuery,new Object[]{syncStatus,id});
			}
			catch (Exception e) {
				DefaultLogger.debug(this, "Error while updating sync staus for "+masterName);
				e.printStackTrace();
			}
	
		}else{
			DefaultLogger.debug(this, masterName+" is not configured.");
		}
	}*/


	private String getEODSyncStatusbyFileStatus(String opStatus, String syncAction) {
		String syncStatus="";
		if("SUCCESS".equalsIgnoreCase(opStatus)){
			syncStatus=EODSyncRecordStatus.SUCCESSFUL.name();
		}else if("FAIL".equalsIgnoreCase(opStatus)){
			if("I".equals(syncAction)){
				syncStatus= EODSyncRecordStatus.INSERT_FAILED.name();	
			}else if("U".equals(syncAction)){
				syncStatus= EODSyncRecordStatus.UPDATE_FAILED.name();	
			}else if("D".equals(syncAction)){
				syncStatus= EODSyncRecordStatus.DELETE_FAILED.name();	
			}
		}
		return syncStatus;
	}
	public void updateRecordsEodSyncStatus(String masterName, Object obToStore){
		if(climsToCpsMastersMap.containsKey(masterName)){
			String updateQuery = "update "+climsToCpsMastersMap.get(masterName)+" set EOD_SYNC_STATUS =? where ";
			updateQuery += getMastersIdClause(masterName);
			try {
				Object[] specDataByMstName = getSpecDataByMstName(masterName,obToStore);
				if(specDataByMstName!=null && ! specDataByMstName[0].toString().equals("FAILED")){
					//Ignore Failed record status update so they can be sent again on next eod
					getJdbcTemplate().update(updateQuery,specDataByMstName);
				}
			}
			catch (Exception e) {
				DefaultLogger.debug(this, "Error while updating sync staus for "+masterName);
				e.printStackTrace();
			}
		}
		
	}
	
	public long getCountryId(Long cpsId) {
		String sql = "SELECT id FROM cms_country WHERE cps_id=?";
		
		Long id = (Long)getJdbcTemplate().query(sql, new Object[] {cpsId}, new ResultSetExtractor() {
			@Override
			public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next())
					return rs.getLong("id");
				return Long.valueOf(0);
			}
		});
		return id.longValue();
	}
	
	public long getRegionId(Long cpsId) {
		String sql = "SELECT id FROM cms_region WHERE cps_id=?";
		
		Long id = (Long)getJdbcTemplate().query(sql, new Object[] {cpsId}, new ResultSetExtractor() {
			@Override
			public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next())
					return rs.getLong("id");
				return Long.valueOf(0);
			}
		});
		return id.longValue();
	}
	
	public long getStateId(Long cpsId) {
		String sql = "SELECT id FROM cms_state WHERE cps_id=?";
		
		Long id = (Long)getJdbcTemplate().query(sql, new Object[] {cpsId}, new ResultSetExtractor() {
			@Override
			public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next())
					return rs.getLong("id");
				return Long.valueOf(0);
			}
		});
		return id.longValue();
	}
}
