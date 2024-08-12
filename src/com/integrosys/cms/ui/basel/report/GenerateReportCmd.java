package com.integrosys.cms.ui.basel.report;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.time.DateFormatUtils;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.poi.report.OBFilter;
import com.integrosys.cms.app.poi.report.xml.schema.IReportConstants;

/**
 * @author cyliew
 * @author Chong Jun Yong
 * @since 2006/10/27
 */
public class GenerateReportCmd extends AbstractCommand implements
		ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	public GenerateReportCmd() {

	}

	

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "reportId", "java.lang.String", REQUEST_SCOPE },
				{ "filterParty", "java.lang.String", REQUEST_SCOPE },
				{ "filterUser", "java.lang.String", REQUEST_SCOPE },
				{ "reportFormObj",
						"com.integrosys.cms.app.poi.report.OBFilter",
						FORM_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "reportfile", "java.lang.String", SERVICE_SCOPE },
				{ "reportFormObj",
						"com.integrosys.cms.app.poi.report.OBFilter",
						SERVICE_SCOPE },
				{ "output", "java.io.ByteArrayOutputStream", REQUEST_SCOPE },
				{ "fileName", "java.lang.String", REQUEST_SCOPE },
				{ "count", "java.lang.String", REQUEST_SCOPE } 
			});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		DefaultLogger.debug(this, "Inside doExecute()");
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		OBFilter filter = (OBFilter) map.get("reportFormObj");
		HashMap exceptionMap = new HashMap();// ReportValidator.validateReport(map,
		int count=0;										// filter);
		String event = (String) map.get("event");
		resultMap.put("event", event);
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		if (!((exceptionMap != null) && (exceptionMap.size() > 0))) {
			
			try {
				if (event.equals("generate_daily_basel_open_report")) {
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					byte[] fileData;
					
					String fileName = PropertyManager.getValue(IReportConstants.BASE_PATH)
					+"Daily_Basel_open_Report"+DateFormatUtils.format(new Date(), "_yyyyMMddHHmmss")+".csv";
					
					String reportFileName = "Daily_Basel_open_Report"+DateFormatUtils.format(new Date(), "_yyyyMMddHHmmss")+".DAT";

					DBUtil dbUtil = null;
					ResultSet rs = null;
					FileWriter out = null;
					out = new FileWriter(fileName);
					try {						
						dbUtil = new DBUtil();
						DateFormat df1 = new SimpleDateFormat("dd-MMM-yy");
						String sql = "SELECT * FROM DWH_OPENEXTRACT WHERE TO_CHAR(UPPER(SYSTEMDATE)) = TO_CHAR(UPPER('"+df1.format(new Date())+"'))";
						dbUtil.setSQL(sql);
						rs = dbUtil.executeQuery();
						out.flush();
						out.write("HDR");
						out.write("|");
						out.write(df.format(new Date()));
						out.write("|");
						out.write(reportFileName);
						out.write("|");
						out.write(" 6");
						out.write("\r\n");
						
						int ncols = rs.getMetaData().getColumnCount();
						BigDecimal grossValue = new BigDecimal("0.00") ;
						
						while (rs.next()) {
							count++;
							if(rs.getString(6) != null){
								BigDecimal bd = new BigDecimal(rs.getString(6));
								grossValue = grossValue.add(bd);
							}	
							for (int i = 1; i < (ncols + 1); i++) {
								
								if(i==17 || i==19 || i==22 || i==23 || i==28){
									if (rs.getString(i) != null)
										out.write(df.format(rs.getDate(i)));
								}else{
									if (rs.getString(i) != null)
										out.write(rs.getString(i).trim());
								}
								
								if (i < ncols)
									out.write("|");
								else
									out.write("\r\n");
							}
						}
						
						out.write("TRL");
						out.write("|");
						out.write(df.format(new Date()));
						out.write("|");
						out.write(Integer.toString(count));
						out.write("|");
						out.write(grossValue.toString());

					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							dbUtil.close();
							if (out != null) {
								out.close();
							}
							if (!resultMap.containsKey("fileName")) {
								resultMap.put("fileName", "");
							}
						} catch (Exception e) {
							e.printStackTrace();
							throw new SearchDAOException("Error in finalize"
									+ e.getMessage());
						}
					}

					// --------------------------------------------------------

					File file = new File(fileName);
					fileData = IOUtils.toByteArray(new FileInputStream(file));
					output.write(fileData);
					output.close();
					resultMap.put("fileName", fileName);
					// file.delete();
					resultMap.put("reportfile", fileName);
				}else if (event.equals("generate_daily_basel_delete_report")) {
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					byte[] fileData;
					// --------------------------------------------------------

					
					String fileName = PropertyManager.getValue(IReportConstants.BASE_PATH)
					+"Daily_Basel_Delete_Report"+DateFormatUtils.format(new Date(), "_yyyyMMddHHmmss")+".csv";
					
					String reportFileName = "Daily_Basel_Delete_Report"+DateFormatUtils.format(new Date(), "_yyyyMMddHHmmss")+".DAT";
					
					DBUtil dbUtil = null;
					ResultSet rs1 = null;
					FileWriter out = null;
					out = new FileWriter(fileName);
					try {
						dbUtil = new DBUtil();
						DateFormat df1 = new SimpleDateFormat("dd-MMM-yy");
						String sql = "SELECT * FROM DWH_CLOSEDDELETEEXTRACT WHERE TO_CHAR(UPPER(SYSTEMDATE)) = TO_CHAR(UPPER('"+df1.format(new Date())+"'))";
						dbUtil.setSQL(sql);
						rs1 = dbUtil.executeQuery();
						
						out.write("HDR");
						out.write("|");
						out.write(df.format(new Date()));
						out.write("|");
						out.write(reportFileName);
						out.write("|");
						out.write(" 6");
						out.write("\r\n");

						int ncols = rs1.getMetaData().getColumnCount();
						BigDecimal grossValue = new BigDecimal("0.00") ; ;
						
						while (rs1.next()) {
							count++;
							if(rs1.getString(6) != null){
								BigDecimal bd = new BigDecimal(rs1.getString(6));
								grossValue = grossValue.add(bd);
							} 
							for (int i = 1; i < (ncols + 1); i++) {
								if(i==17 || i==19 || i==22 || i==23 || i==28){
									if (rs1.getString(i) != null)
										out.write(df.format(rs1.getDate(i)));
								}else{
									if (rs1.getString(i) != null)
										out.write(rs1.getString(i).trim());
								}
								
								if (i < ncols)
									out.write("|");
								else
									out.write("\r\n");
							}
						}
						
						out.write("TRL");
						out.write("|");
						out.write(df.format(new Date()));
						out.write("|");
						out.write(Integer.toString(count));
						out.write("|");
						out.write(grossValue.toString());

					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							dbUtil.close();
							if (out != null) {
								out.close();
							}
							if (!resultMap.containsKey("fileName")) {
								resultMap.put("fileName", "");
							}
						} catch (Exception e) {
							e.printStackTrace();
							throw new SearchDAOException("Error in finalize"
									+ e.getMessage());
						}
					}

					// --------------------------------------------------------

					File file = new File(fileName);
					fileData = IOUtils.toByteArray(new FileInputStream(file));
					output.write(fileData);
					output.close();
					resultMap.put("fileName", fileName);
					// file.delete();
					resultMap.put("reportfile", fileName);
				} else {
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					byte[] fileData;
					// --------------------------------------------------------
					//String fileName = PropertyManager.getValue(IReportConstants.BASE_PATH)+"Monthly_Basel_Report"+DateFormatUtils.format(new Date(), "_yyyyMMddHHmmss")+".csv";
					
					String fileName = "LSS_BASEL.DAT";
					
					//String reportFileName = "Monthly_Basel_Report"+DateFormatUtils.format(new Date(), "_yyyyMMddHHmmss")+".DAT";
					String reportFileName = "LSS_BASEL.DAT";
					
					FileWriter out = new FileWriter(fileName);
					DBUtil dbUtil = null;
					ResultSet rs = null;
					try {
						 IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
						IGeneralParamEntry generalParamEntry2 = generalParamDao.getGeneralParamEntryByParamCodeActual("BASEL_MONTHLY_PHASE_2");
						String Phase = generalParamEntry2.getParamValue();
						String sql ="";
						dbUtil = new DBUtil();
						if(Phase.equals("N"))
						{
						 sql = "SELECT D_F_MATURITY_DATE, D_F_VALUE_DATE, F_F_AGREEMENT_FLAG, FIC_MIS_DATE, N_F_MKT_MITIGANT_VALUE, " +
								" GROSS_VALUE, N_F_ORIGINAL_MATURITY, N_F_REMARGIN_FREQUENCY, N_F_RESIDUAL_MATURITY, N_F_REVAL_FREQUENCY, " +
								" V_F_CCY_CODE, V_F_COUNTRY_ID, V_F_CREDIT_RATING, V_F_ISSUER_CODE, V_F_SECURITY_ID, V_F_EXP_SOURCE_SYS, " +
								" V_F_MITIGANT_SOURCE_SYS, V_F_SYSTEM_EXP_IND, V_F_LINE_CODE, N_F_LINE_SERIAL, V_F_MITIGANT_CODE, V_F_MAP_ID, " +
								" V_F_MITIGANT_TYPE_CODE, V_F_RATING_ID, D_F_START_DATE, V_F_TPFD_CUST_ID, F_F_PROPERTY_TYPE, V_F_RATING_AGENCY, " +
								" V_F_GURANTOR_RAM_ID, F_F_RATING_TYPE, F_F_RECOURSE_AVAILABLE, V_F_LSSEXTAG_PARTY_GROUP_ID, " +
								" V_F_LSSEXTAG_LOAN_PURPOSE, F_F_LSSEXTAG_PRIMSEC_COLL_STAT FROM CMS_BASEL_MONTHLY ";
						}
						else{
							sql = "SELECT D_F_MATURITY_DATE, D_F_VALUE_DATE, F_F_AGREEMENT_FLAG, FIC_MIS_DATE, N_F_MKT_MITIGANT_VALUE, " +
							" GROSS_VALUE, N_F_ORIGINAL_MATURITY, N_F_REMARGIN_FREQUENCY, N_F_RESIDUAL_MATURITY, N_F_REVAL_FREQUENCY, " +
							" V_F_CCY_CODE, V_F_COUNTRY_ID, V_F_CREDIT_RATING, V_F_ISSUER_CODE, V_F_SECURITY_ID, V_F_EXP_SOURCE_SYS, " +
							" V_F_MITIGANT_SOURCE_SYS, V_F_SYSTEM_EXP_IND, V_F_LINE_CODE, N_F_LINE_SERIAL, V_F_MITIGANT_CODE, V_F_MAP_ID, " +
							" V_F_MITIGANT_TYPE_CODE, V_F_RATING_ID, D_F_START_DATE, V_F_TPFD_CUST_ID, F_F_PROPERTY_TYPE, V_F_RATING_AGENCY, " +
							" V_F_GURANTOR_RAM_ID, F_F_RATING_TYPE, F_F_RECOURSE_AVAILABLE, V_F_LSSEXTAG_PARTY_GROUP_ID, " +
							" V_F_LSSEXTAG_LOAN_PURPOSE, F_F_LSSEXTAG_PRIMSEC_COLL_STAT, RAM_ID, FIRST_YEAR, FIRST_YEAR_TURNOVER, " +
							" SECOND_YEAR, SECOND_YEAR_TURNOVER, THIRD_YEAR, THIRD_YEAR_TURNOVER ,CLAIM ,ROC,ROC_STATUS,INSURANCE,INSURANCE_STATUS,"+
							 "PROPERTY_REVAL_APPLICABLE, PROPERTY_REVAL_REQUIRED, PLANT_AND_MACHINERY_INSP_APPLI, PLANT_AND_MACHINERY_INSP_STAT  FROM CMS_BASEL_MONTHLY_P2 ";
							
							
						}
						dbUtil.setSQL(sql);
						rs = dbUtil.executeQuery();
						
						out.write("HDR");
						out.write("|");
						out.write(df.format(new Date()));
						out.write("|");
						out.write(reportFileName);
						out.write("|");
						out.write(" 6");
						out.write("\r\n");
						
						int ncols = rs.getMetaData().getColumnCount();
						BigDecimal grossValue = new BigDecimal("0.000") ;
						
						while (rs.next()) {
							count++;
							if(rs.getString(6) != null){
								BigDecimal bd = new BigDecimal(rs.getString(6).trim());
								grossValue = grossValue.add(bd);
							} 
							for (int i = 1; i < (ncols + 1); i++) {
								if(i==1 || i==2 || i==4 || i==25){
									if (rs.getString(i) != null)
										out.write(df.format(rs.getDate(i)));
								}
								
								else{
									if(i== 49 ){ ////for PLANT_AND_MACHINERY_INSP_APPLI
										if ("0".equals(rs.getString(i)) ) 
										{
											out.write("Not Applicable");
										}else {
											out.write("Applicable");
										}
									}
									else if (rs.getString(i) != null)
										out.write(rs.getString(i).trim());
								}
								
								if (i < ncols)
									out.write("|");
								else
									out.write("\r\n");
							}
						}
						
						//this is temp solution, value was comint double, need to analyze in detail for final solution
						//grossValue= grossValue.divide(new BigDecimal(2));
						
						out.write("TRL ");
						out.write("|");
						out.write(df.format(new Date()));
						out.write("|");
						out.write(Integer.toString(count));
						out.write("|");
						out.write(grossValue.toString());

					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							dbUtil.close();
							if (out != null) {
								out.close();
							}
							if (!resultMap.containsKey("fileName")) {
								resultMap.put("fileName", "");
							}
						} catch (Exception e) {
							e.printStackTrace();
							throw new SearchDAOException("Error in finalize"
									+ e.getMessage());
						}
					}
					// --------------------------------------------------------
					File file = new File(fileName);
					fileData = IOUtils.toByteArray(new FileInputStream(file));
					output.write(fileData);
					resultMap.put("fileName", fileName);
					// file.delete();
					resultMap.put("reportfile", fileName);
					output.close();
				}
			} catch (Exception e) {
				throw new UnhandledException(e);
			}
		}
		resultMap.put("count", Integer.toString(count));
		resultMap.put("reportFormObj", filter);
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

	/**
	 * This method will return the basepath
	 * 
	 * @return String
	 */
	public String getBasePath() {
		return PropertyManager.getValue(IReportConstants.BASE_PATH);
	}
	
	public void generateBaselOnEOD(){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		int count=0;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] fileData;
		// --------------------------------------------------------
		String fileName = PropertyManager.getValue(IReportConstants.BASEL_BASE_PATH)+"LSS_BASEL.DAT";
		System.out.println("- Basel File name- "+fileName);
		//String fileName = "LSS_BASEL.DAT";
		
		//String reportFileName = "Monthly_Basel_Report"+DateFormatUtils.format(new Date(), "_yyyyMMddHHmmss")+".DAT";
		String reportFileName = "LSS_BASEL.DAT";
		
		
		DBUtil dbUtil = null;
		ResultSet rs = null;
		FileWriter out = null;
		try {
			 IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
			IGeneralParamEntry generalParamEntry2 = generalParamDao.getGeneralParamEntryByParamCodeActual("BASEL_MONTHLY_PHASE_2");
			String Phase = generalParamEntry2.getParamValue();
			String sql ="";
			out = new FileWriter(fileName);
			dbUtil = new DBUtil();
			if(Phase.equals("N"))
			{
			 sql = "SELECT D_F_MATURITY_DATE, D_F_VALUE_DATE, F_F_AGREEMENT_FLAG, FIC_MIS_DATE, N_F_MKT_MITIGANT_VALUE, " +
					" GROSS_VALUE, N_F_ORIGINAL_MATURITY, N_F_REMARGIN_FREQUENCY, N_F_RESIDUAL_MATURITY, N_F_REVAL_FREQUENCY, " +
					" V_F_CCY_CODE, V_F_COUNTRY_ID, V_F_CREDIT_RATING, V_F_ISSUER_CODE, V_F_SECURITY_ID, V_F_EXP_SOURCE_SYS, " +
					" V_F_MITIGANT_SOURCE_SYS, V_F_SYSTEM_EXP_IND, V_F_LINE_CODE, N_F_LINE_SERIAL, V_F_MITIGANT_CODE, V_F_MAP_ID, " +
					" V_F_MITIGANT_TYPE_CODE, V_F_RATING_ID, D_F_START_DATE, V_F_TPFD_CUST_ID, F_F_PROPERTY_TYPE, V_F_RATING_AGENCY, " +
					" V_F_GURANTOR_RAM_ID, F_F_RATING_TYPE, F_F_RECOURSE_AVAILABLE, V_F_LSSEXTAG_PARTY_GROUP_ID, " +
					" V_F_LSSEXTAG_LOAN_PURPOSE, F_F_LSSEXTAG_PRIMSEC_COLL_STAT FROM CMS_BASEL_MONTHLY ";
			}
			else{
				sql = "SELECT D_F_MATURITY_DATE, D_F_VALUE_DATE, F_F_AGREEMENT_FLAG, FIC_MIS_DATE, N_F_MKT_MITIGANT_VALUE, " +
				" GROSS_VALUE, N_F_ORIGINAL_MATURITY, N_F_REMARGIN_FREQUENCY, N_F_RESIDUAL_MATURITY, N_F_REVAL_FREQUENCY, " +
				" V_F_CCY_CODE, V_F_COUNTRY_ID, V_F_CREDIT_RATING, V_F_ISSUER_CODE, V_F_SECURITY_ID, V_F_EXP_SOURCE_SYS, " +
				" V_F_MITIGANT_SOURCE_SYS, V_F_SYSTEM_EXP_IND, V_F_LINE_CODE, N_F_LINE_SERIAL, V_F_MITIGANT_CODE, V_F_MAP_ID, " +
				" V_F_MITIGANT_TYPE_CODE, V_F_RATING_ID, D_F_START_DATE, V_F_TPFD_CUST_ID, F_F_PROPERTY_TYPE, V_F_RATING_AGENCY, " +
				" V_F_GURANTOR_RAM_ID, F_F_RATING_TYPE, F_F_RECOURSE_AVAILABLE, V_F_LSSEXTAG_PARTY_GROUP_ID, " +
				" V_F_LSSEXTAG_LOAN_PURPOSE, F_F_LSSEXTAG_PRIMSEC_COLL_STAT, RAM_ID, FIRST_YEAR, FIRST_YEAR_TURNOVER, " +
				" SECOND_YEAR, SECOND_YEAR_TURNOVER, THIRD_YEAR, THIRD_YEAR_TURNOVER ,CLAIM ,ROC,ROC_STATUS,INSURANCE,INSURANCE_STATUS ,"+
			    "PROPERTY_REVAL_APPLICABLE, PROPERTY_REVAL_REQUIRED, PLANT_AND_MACHINERY_INSP_APPLI, PLANT_AND_MACHINERY_INSP_STAT FROM CMS_BASEL_MONTHLY_P2 ";
				
				
			}
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();
			
			out.write("HDR");
			out.write("|");
			out.write(df.format(new Date()));
			out.write("|");
			out.write(reportFileName);
			out.write("|");
			out.write(" 6");
			out.write("\r\n");
			
			int ncols = rs.getMetaData().getColumnCount();
			BigDecimal grossValue = new BigDecimal("0.000") ;
			
			while (rs.next()) {
				count++;
				if(rs.getString(6) != null){
					BigDecimal bd = new BigDecimal(rs.getString(6).trim());
					grossValue = grossValue.add(bd);
				} 
				for (int i = 1; i < (ncols + 1); i++) {
					if(i==1 || i==2 || i==4 || i==25){
						if (rs.getString(i) != null)
							out.write(df.format(rs.getDate(i)));
					}
					
					
					else{
						if(i== 49 ){ ////for PLANT_AND_MACHINERY_INSP_APPLI
							if ("0".equals(rs.getString(i)) ) 
							{
								out.write("Not Applicable");
							}else {
								out.write("Applicable");
							}
						}
						else if (rs.getString(i) != null)
							out.write(rs.getString(i).trim());
					}
					
					if (i < ncols)
						out.write("|");
					else
						out.write("\r\n");
				}
			}
			//this is temp solution, value was comint double, need to analyze in detail for final solution
			//grossValue= grossValue.divide(new BigDecimal(2));
			
			out.write("TRL ");
			out.write("|");
			out.write(df.format(new Date()));
			out.write("|");
			out.write(Integer.toString(count));
			out.write("|");
			out.write(grossValue.toString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.close();
				if (out != null) {
					out.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new SearchDAOException("Error in finalize"
						+ e.getMessage());
			}
		}
		// --------------------------------------------------------
		/*File file = new File(fileName);
		fileData = IOUtils.toByteArray(new FileInputStream(file));
		output.write(fileData);
		resultMap.put("fileName", fileName);
		// file.delete();
		resultMap.put("reportfile", fileName);
		output.close();*/
	
	}
}
