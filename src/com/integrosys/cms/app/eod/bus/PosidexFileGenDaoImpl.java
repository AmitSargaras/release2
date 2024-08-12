package com.integrosys.cms.app.eod.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.eod.sync.bus.EODSyncStatusException;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.poi.report.OBFilter;
import com.integrosys.cms.batch.common.posidex.templateparser.Param;
import com.integrosys.cms.batch.common.posidex.templateparser.PosidexTemplateOut;
import com.integrosys.cms.batch.common.posidex.templateparser.WhereClause;
import com.integrosys.cms.batch.eod.IEodSyncConstants;

public class PosidexFileGenDaoImpl extends JdbcTemplateAdapter implements IPosidexFileGenDao{

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
	public List<Object[]> getReportQueryResult( PosidexTemplateOut posidexTemplateOut,OBFilter filter) {
		List<Object[]> reportQueryResult= new LinkedList<Object[]>(); 
		/** genrating query from report object, object will be ob class containing filter values*/
		String sql=generateQuery(posidexTemplateOut, filter);
		
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
			//throw new Exception("Error excecuting query.Please check the report template configuration.",e); 
			e.printStackTrace();
		}
		return reportQueryResult;
	}
	
	
//	TODO :Logic of genrating query will change(for where claue)
	/**
	 * genrating query from report object
	 */
	private String generateQuery(PosidexTemplateOut posidexTemplateOut,OBFilter filter) {

		StringBuffer query = new StringBuffer();
		query.append(posidexTemplateOut.getQuery());
		
		if(posidexTemplateOut.getWhereClause()!=null){
			//creating Criteria
			String criteria = createCriteria(posidexTemplateOut,filter);
			if(criteria!=null && !("".equals(criteria.trim()))){
				query.append(" WHERE ");
				query.append(criteria);
			}
		}
		
		
		DefaultLogger.debug(this, "============================= Displaying Query ==========================");
		DefaultLogger.debug(this, query.toString());
		return query.toString();
	}


	/**
	 * @param posidexTemplateOut
	 */
	private String createCriteria(PosidexTemplateOut posidexTemplateOut,OBFilter filter) {
		StringBuffer criteria= new StringBuffer();
		
		WhereClause whereClause = posidexTemplateOut.getWhereClause();
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

}
