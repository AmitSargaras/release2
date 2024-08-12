package com.integrosys.cms.ui.poi.report;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.time.DateFormatUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.ftp.IFileUploadConstants;
import com.integrosys.cms.app.poi.report.IReportService;
import com.integrosys.cms.app.poi.report.OBFilter;
import com.integrosys.cms.app.poi.report.ReportDaoImpl;
import com.integrosys.cms.app.poi.report.xml.schema.IReportConstants;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author cyliew
 * @author Chong Jun Yong
 * @since 2006/10/27
 */
public class GenerateReportCmd extends AbstractCommand implements ICommonEventConstant {

	private IReportService reportService;
	
	public IReportService getReportService() {
		return reportService;
	}

	public void setReportService(IReportService reportService) {
		this.reportService = reportService;
	}

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
				{ "reportFormObj","com.integrosys.cms.app.poi.report.OBFilter",FORM_SCOPE },
				});
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
//				{ "output", "java.io.ByteArrayOutputStream", SERVICE_SCOPE },
//				{ "output", "java.io.ByteArrayOutputStream", REQUEST_SCOPE },
				{ "fileName", "java.lang.String", SERVICE_SCOPE },
				{ "reportfile", "java.lang.String", SERVICE_SCOPE },
				{ "reportFormObj","com.integrosys.cms.app.poi.report.OBFilter",SERVICE_SCOPE }, 
				{ "eventOrCriterias", "java.lang.String", SERVICE_SCOPE },
//				{ "fileName", "java.lang.String", REQUEST_SCOPE }
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		DefaultLogger.debug(this, "Inside doExecute()");
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String event= (String) map.get("event");
		//Timestamp st  = new Timestamp(System.currentTimeMillis());
		//Date date =new Date();
		String pattern = "yyyyMMdd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		if("generate_report_sms_upload".equalsIgnoreCase(event)){
			OBFilter filter = null;
			HashMap exceptionMap = new HashMap();
			
			
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] fileData;
			try {
				
				//--------------------------------------------------------
				String reportId= (String) map.get("reportId");
				if(reportId==null)
					reportId="RPT0046";
				
				String fileName = PropertyManager.getValue(IReportConstants.REPORT_MAPPING_PREFIX + reportId
						+ IReportConstants.REPORT_MAPPING_FILENAME_SUFFIX)
						+date+".xls";
				DefaultLogger.debug(this,"========Before generateReport()  =========="+reportId+" "+fileName);
				getReportService().generateReport(reportId, fileName,filter);
				DefaultLogger.debug(this,"========After generateReport()  ==========");
				//--------------------------------------------------------
				
				String reportFile=getBasePath()+ System.getProperty("file.separator") + fileName;
				/*File file = new File(reportFile);
				fileData = IOUtils.toByteArray(new FileInputStream(file));
				output.write(fileData)*/;
				resultMap.put("fileName", fileName);
//				file.delete();
				resultMap.put("reportfile", reportFile);
				DefaultLogger.debug(this,"Report genration for report '" + reportId+ "' is done.");
			}
			catch (Exception e) {
				throw new UnhandledException(e);
				// todo
			}
			finally {
				if (!resultMap.containsKey("fileName")) {
					resultMap.put("fileName", "");
				}
			}
			
			
			//resultMap.put("output", output);

			resultMap.put("reportFormObj", filter);
			DefaultLogger.debug(this, "Going out of doExecute()");
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			return returnMap;
		}else{
			OBFilter filter = (OBFilter) map.get("reportFormObj");
			HashMap exceptionMap = ReportValidator.validateReport(map, filter);
			
			String reportId= (String) map.get("reportId");
			if(reportId==null)
				reportId="RPT0037";
			
			if (!((exceptionMap != null) && (exceptionMap.size() > 0))) {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] fileData;
			try {
				
				if(filter.getReportId()!=null && filter.getReportId().equals("RPT0025")){
				if(filter.getUploadSystem()!=null && filter.getUploadSystem().equals("UBS")){
					reportId = "RPT0025_1";
				}
				else if(filter.getUploadSystem()!=null && filter.getUploadSystem().equals("FINWARE")){
					reportId ="RPT0025_2";
				}
                else if(filter.getUploadSystem()!=null && filter.getUploadSystem().equals("BAHRAIN")){
                	reportId ="RPT0025_3";
				}
                else if(filter.getUploadSystem()!=null && filter.getUploadSystem().equals("HONGKONG")){
                	reportId ="RPT0025_4";
                }
				}
				

				if(filter.getReportId()!=null && filter.getReportId().equals("RPT0059")){
					if(filter.getQuarter()!=null && filter.getQuarter().equals("Q-1")){
						reportId = "RPT0059_1";
					}
					else if(filter.getQuarter()!=null && filter.getQuarter().equals("Q-2")){
						reportId ="RPT0059_2";
					}
	               
					}

				//Added by Uma Khot: Start:For Monthly Basel Report 08/09/2015
				if(null!=filter.getReportId() && "RPT0054".equals(filter.getReportId())){
					if(null!=filter.getSecurityType() && "AssetBasedGeneralCharge".equals(filter.getSecurityType())){
						reportId = "RPT0054_A";
					}
					else if(null!=filter.getSecurityType() && "AssetBasedGold".equals(filter.getSecurityType())){
						reportId ="RPT0054_B";
					}
	                else if(null!=filter.getSecurityType() && "AssetBasedPlantEquipment".equals(filter.getSecurityType())){
	                	reportId ="RPT0054_C";
					}
	                else if(null!=filter.getSecurityType() && "AssetBasedPostDatedCheques".equals(filter.getSecurityType())){
	                	reportId ="RPT0054_D";
	                }
	            	else if(null!=filter.getSecurityType() && "AssetBasedSpecificAsset".equals(filter.getSecurityType())){
						reportId ="RPT0054_E";
					}
	                else if(null!=filter.getSecurityType() && "AssetBasedVehicles".equals(filter.getSecurityType())){
	                	reportId ="RPT0054_F";
					}
	                else if(null!=filter.getSecurityType() && "CashFixedDeposit".equals(filter.getSecurityType())){
	                	reportId ="RPT0054_G";
	                }
	            	else if(null!=filter.getSecurityType() && "GuaranteesBank".equals(filter.getSecurityType())){
						reportId ="RPT0054_H";
					}
	                else if(null!=filter.getSecurityType() && "GuaranteesCorporate".equals(filter.getSecurityType())){
	                	reportId ="RPT0054_I";
					}
	                else if(null!=filter.getSecurityType() && "GuaranteesGovernment".equals(filter.getSecurityType())){
	                	reportId ="RPT0054_J";
	                }
	            	else if(null!=filter.getSecurityType() && "GuaranteesIndividual".equals(filter.getSecurityType())){
						reportId ="RPT0054_K";
					}
	                else if(null!=filter.getSecurityType() && "GuaranteesStandbyLC".equals(filter.getSecurityType())){
	                	reportId ="RPT0054_L";
					}
	                else if(null!=filter.getSecurityType() && "InsuranceLifeInsurance".equals(filter.getSecurityType())){
	                	reportId ="RPT0054_M";
	                }
	                else if(null!=filter.getSecurityType() && "PropertyProperty".equals(filter.getSecurityType())){
	                	reportId ="RPT0054_N";
	                }
					}
				//Added by Uma Khot: End:For Monthly Basel Report 08/09/2015

				
//			getReportService().generateReport(reportId, fileName,filter);
				
				String fileName = PropertyManager.getValue(IReportConstants.REPORT_MAPPING_PREFIX + reportId
						+ IReportConstants.REPORT_MAPPING_FILENAME_SUFFIX)
						+date+".xls";
				String fileName2="";
				String fileName3="";
				int srNo=1;
				
				DefaultLogger.debug(this, "date<<<<<<<<<<<>>>>>>>>>>>>"+date);
				
				if(null!=filter && "RPT0062".equals(filter.getReportId())){
				if(IFileUploadConstants.FILEUPLOAD_UBS.equals(filter.getUploadSystem())){
					reportId = "RPT0062_UBS";
				}
				else if(IFileUploadConstants.FILEUPLOAD_HONGKONG.equals(filter.getUploadSystem())){
					reportId ="RPT0062_HONGKONG";
				}
				else if(IFileUploadConstants.FILEUPLOAD_FINWARE.equals(filter.getUploadSystem())){
                	reportId ="RPT0062_FINWARE";
				}
				else if(IFileUploadConstants.FILEUPLOAD_BAHRAIN.equals(filter.getUploadSystem())){
                	reportId ="RPT0062_BAHRAIN";
                }
				else if(IFileUploadConstants.FILEUPLOAD_FD.equals(filter.getUploadSystem())){
					reportId ="RPT0062_FD";
				}
			}
				if(null!=filter.getReportId() && "RPT0063".equals(filter.getReportId()))
				{
					fileName = PropertyManager.getValue(IReportConstants.REPORT_MAPPING_PREFIX + reportId
							+ IReportConstants.REPORT_MAPPING_FILENAME_SUFFIX)
							+DateFormatUtils.format(new Date(), "_yyyyMMdd_");
				}
				else if("RPT0062_FD".equals(reportId) ) {
				fileName = PropertyManager.getValue(IReportConstants.REPORT_MAPPING_PREFIX + reportId
						+ IReportConstants.REPORT_MAPPING_FILENAME_SUFFIX)
						+DateFormatUtils.format(new Date(), "_yyyyMMdd_");
			}else if("RPT0062_UBS".equals(reportId)) {
				fileName = PropertyManager.getValue(IReportConstants.REPORT_MAPPING_PREFIX + reportId
						+ IReportConstants.REPORT_MAPPING_FILENAME_SUFFIX)
						+DateFormatUtils.format(new Date(), "_yyyyMMdd_");
			}
			else if(filter.getReportId().equals("RPT0067")) {
					if(filter.getReportFormat().equals("CersaiCompatible")) {
					
					if(filter.getTypeOfSecurity().equals("IMMOVABLE")) {
						fileName="cerupload_adsi_"+srNo+DateFormatUtils.format(new Date(),"_dd-MM-yyyy")+".txt";
						srNo++;
						fileName2="cerupload_adsi_"+srNo+DateFormatUtils.format(new Date(),"_dd-MM-yyyy")+".txt";
						fileName3 = "cerupload_adsi" 
								+DateFormatUtils.format(new Date(), "_dd-MM-yyyy")+".zip";
					}
					else if(filter.getTypeOfSecurity().equals("MOVABLE")) {
						fileName="cerupload_mvin_"+srNo+DateFormatUtils.format(new Date(),"_dd-MM-yyyy")+".txt";
						srNo++;
						fileName2="cerupload_mvin_"+srNo+DateFormatUtils.format(new Date(),"_dd-MM-yyyy")+".txt";
						fileName3 = "cerupload_mvin" 
								+DateFormatUtils.format(new Date(), "_dd-MM-yyyy")+".zip";
					}else {
						fileName = "cerupload_satn_" + srNo
								+DateFormatUtils.format(new Date(), "_dd-MM-yyyy")+".txt";
						srNo++;
						fileName2="cerupload_satn_"+srNo+DateFormatUtils.format(new Date(),"_dd-MM-yyyy")+".txt";
						fileName3 = "cerupload_satn" 
								+DateFormatUtils.format(new Date(), "_dd-MM-yyyy")+".zip";
					}
					}else {
						if(filter.getTypeOfSecurity().equals("IMMOVABLE")) {
							fileName="cerupload_adsi_"+srNo+DateFormatUtils.format(new Date(),"_dd-MM-yyyy")+".xls";
							srNo++;
							fileName2="cerupload_adsi_"+srNo+DateFormatUtils.format(new Date(),"_dd-MM-yyyy")+".xls";
							fileName3 = "cerupload_adsi" 
									+DateFormatUtils.format(new Date(), "_dd-MM-yyyy")+".zip";
						}
						else if(filter.getTypeOfSecurity().equals("MOVABLE")) {
							fileName="cerupload_mvin_"+srNo+DateFormatUtils.format(new Date(),"_dd-MM-yyyy")+".xls";
							srNo++;
							fileName2="cerupload_mvin_"+srNo+DateFormatUtils.format(new Date(),"_dd-MM-yyyy")+".xls";
							fileName3 = "cerupload_mvin" 
									+DateFormatUtils.format(new Date(), "_dd-MM-yyyy")+".zip";
						}else {
							fileName = "cerupload_satn_" + srNo
									+DateFormatUtils.format(new Date(), "_dd-MM-yyyy")+".xls";
							srNo++;
							fileName2="cerupload_satn_"+srNo+DateFormatUtils.format(new Date(),"_dd-MM-yyyy")+".xls";
							fileName3 = "cerupload_satn" 
									+DateFormatUtils.format(new Date(), "_dd-MM-yyyy")+".zip";
						}
					}
					
				}else if(filter.getReportId().equals("RPT0066")) {
					if(filter.getTypeOfSecurity().equals("MOVABLE")) {
						fileName = "cerupload_mvin_" + srNo
								+DateFormatUtils.format(new Date(), "_dd-MM-yyyy")+".txt";
						srNo++;
						fileName2 = "cerupload_mvin_" + srNo
								+DateFormatUtils.format(new Date(), "_dd-MM-yyyy")+".txt";
						fileName3 = "cerupload_mvin" 
								+DateFormatUtils.format(new Date(), "_dd-MM-yyyy")+".zip";
					}
					if(filter.getTypeOfSecurity().equals("IMMOVABLE")) {
						fileName = "cerupload_adsi_" + srNo
								+DateFormatUtils.format(new Date(), "_dd-MM-yyyy")+".txt";
						srNo++;
						fileName2="cerupload_adsi_" + srNo
								+DateFormatUtils.format(new Date(), "_dd-MM-yyyy")+".txt";
						fileName3 = "cerupload_adsi" 
								+DateFormatUtils.format(new Date(), "_dd-MM-yyyy")+".zip";
						
					}
				}else if(filter.getReportId().equals("RPT0073")) {
					fileName = PropertyManager.getValue(IReportConstants.REPORT_MAPPING_PREFIX + reportId
							+ IReportConstants.REPORT_MAPPING_FILENAME_SUFFIX)
							+DateFormatUtils.format(new Date(), "_yyyyMMdd_");
				}
				else {
					fileName = PropertyManager.getValue(IReportConstants.REPORT_MAPPING_PREFIX + reportId
							+ IReportConstants.REPORT_MAPPING_FILENAME_SUFFIX)
							//+DateFormatUtils.format(new Date(), "_yyyyMMdd_")+getReportService().getTotalNoOfRowsCount(reportId, filter)+".xls";
					+DateFormatUtils.format(new Date(), "_yyyyMMdd_");
				}
//				else  {
//				fileName = PropertyManager.getValue(IReportConstants.REPORT_MAPPING_PREFIX + reportId
//						+ IReportConstants.REPORT_MAPPING_FILENAME_SUFFIX)
//						+DateFormatUtils.format(new Date(), "_yyyyMMdd_")+getReportService().getTotalNoOfRowsCount(reportId, filter)+".xls";
//			}getTotalNoOfRowsCountMortgage
			
			DefaultLogger.debug(this, "DateFormatUtils.format(new Date())"+DateFormatUtils.format(new Date(), "_yyyyMMddHHmmss"));
				
				
				boolean rpFlag=false;
				if(filter.getReportId().equals("RPT0066")) {
					String bankMethod=filter.getBankingMethod();
					String fromDate = filter.getFromDate();
					String toDate = filter.getToDate();
					String typeOfsecurity = filter.getTypeOfSecurity();
					
					ReportDaoImpl reportImpl = new ReportDaoImpl();
					List<String[]> reportBorrower = new LinkedList<String[]>();
					List<String[]> reportThirdParty = new LinkedList<String[]>();
					List<String[]> reportSecurityInterest = new LinkedList<String[]>();
					List<String[]> reportProperty = new LinkedList<String[]>();
					List<String[]> reportDocuments = new LinkedList<String[]>();
					List<String[]> reportLoan = new LinkedList<String[]>();
					List<String[]> reportAsset = new LinkedList<String[]>();
					
					System.out.println("Get reportBorrower record list.GenerateReportCmd.java");
					reportBorrower = reportImpl.getBorrowerRecords(bankMethod,fromDate,toDate,typeOfsecurity);
					System.out.println("Get reportBorrower record list with size GenerateReportCmd.java == "+reportBorrower.size());
					
					System.out.println("Get reportThirdParty record list.GenerateReportCmd.java");
					reportThirdParty = reportImpl.getThirdPartyRecords(bankMethod,fromDate,toDate,typeOfsecurity);
					System.out.println("Get reportThirdParty record list with size GenerateReportCmd.java == "+reportThirdParty.size());
					
					System.out.println("Get reportSecurityInterest record list.GenerateReportCmd.java");
					reportSecurityInterest = reportImpl.getSecurityThirdInterestRecords(bankMethod,fromDate,toDate,typeOfsecurity);
					System.out.println("Get reportSecurityInterest record list with size GenerateReportCmd.java == "+reportSecurityInterest.size());
					
					if(filter.getTypeOfSecurity().equals("IMMOVABLE")) {
						System.out.println("Get reportProperty record list.GenerateReportCmd.java");
						reportProperty = reportImpl.getPropertyRecords(bankMethod,fromDate,toDate,typeOfsecurity);
						System.out.println("Get reportProperty record list with size GenerateReportCmd.java == "+reportProperty.size());
					}
					
					System.out.println("Get reportDocuments record list.GenerateReportCmd.java");
					reportDocuments = reportImpl.getDocumentRecords(bankMethod,fromDate,toDate,typeOfsecurity);
					System.out.println("Get reportDocuments record list with size GenerateReportCmd.java == "+reportDocuments.size());
					
					System.out.println("Get reportLoan record list.GenerateReportCmd.java");
					reportLoan = reportImpl.getLoanRecords(bankMethod,fromDate,toDate,typeOfsecurity);
					System.out.println("Get reportLoan record list with size GenerateReportCmd.java == "+reportLoan.size());
					
					if(filter.getTypeOfSecurity().equals("MOVABLE")) {
						System.out.println("Get reportAsset record list.GenerateReportCmd.java");
						reportAsset = reportImpl.getAssetRecords(bankMethod,fromDate,toDate,typeOfsecurity);
						System.out.println("Get reportAsset record list with size GenerateReportCmd.java == "+reportAsset.size());
					}


//				String fileName = PropertyManager.getValue(IReportConstants.REPORT_MAPPING_PREFIX + reportId
//						+ IReportConstants.REPORT_MAPPING_FILENAME_SUFFIX)
//						+date+".xls";
				

					List<String> borrowList = new ArrayList<String>();
					List<String> thirdPartyList = new ArrayList<String>();
					List<String> securityInterestList = new ArrayList<String>();
					List<String> propertyList = new ArrayList<String>();
					List<String> documentsList = new ArrayList<String>();
					List<String> loanList = new ArrayList<String>();
					List<String> assetList = new ArrayList<String>();

					String st = "";
					// String[] str = new String[reportBorrower.size()];
					int count = 0;

					// All Records security id added in list
					if (reportBorrower != null && !reportBorrower.isEmpty()) {
						for (int i = 0; i < reportBorrower.size(); i++) {
							String[] str = (String[]) reportBorrower.get(i);
							st = str[1];
							borrowList.add(st);
						}
					}

					if (reportThirdParty != null && !reportThirdParty.isEmpty()) {
						for (int i = 0; i < reportThirdParty.size(); i++) {
							String[] str = (String[]) reportThirdParty.get(i);
							st = str[1];
							thirdPartyList.add(st);
						}
					}

					if (reportSecurityInterest != null && !reportSecurityInterest.isEmpty()) {
						for (int i = 0; i < reportSecurityInterest.size(); i++) {
							String[] str = (String[]) reportSecurityInterest.get(i);
							st = str[1];
							securityInterestList.add(st);
						}
					}

					if(filter.getTypeOfSecurity().equals("IMMOVABLE")) {
					if (reportProperty != null && !reportProperty.isEmpty()) {
						for (int i = 0; i < reportProperty.size(); i++) {
							String[] str = (String[]) reportProperty.get(i);
							st = str[1];
							propertyList.add(st);
						}
					}
					}
					if (reportDocuments != null && !reportDocuments.isEmpty()) {
						for (int i = 0; i < reportDocuments.size(); i++) {
							String[] str = (String[]) reportDocuments.get(i);
							st = str[1];
							documentsList.add(st);
						}
					}

					if (reportLoan != null && !reportLoan.isEmpty()) {
						for (int i = 0; i < reportLoan.size(); i++) {
							String[] str = (String[]) reportLoan.get(i);
							st = str[1];
							loanList.add(st);
						}
					}
					if(filter.getTypeOfSecurity().equals("MOVABLE")) {
					if (reportAsset != null && !reportAsset.isEmpty()) {
						for (int i = 0; i < reportAsset.size(); i++) {
							String[] str = (String[]) reportAsset.get(i);
							st = str[1];
							assetList.add(st);
						}
					}
					}

					// Unique Security id in Set.

					Set<String> borrowListSpecific = new HashSet<String>(borrowList);
					Set<String> thirdPartyListSpecific = new HashSet<String>(thirdPartyList);
					Set<String> securityInterestListSpecific = new HashSet<String>(securityInterestList);
					Set<String> propertyListSpecific = new HashSet<String>();
					if(filter.getTypeOfSecurity().equals("IMMOVABLE")) {
						propertyListSpecific = new HashSet<String>(propertyList);
					}
					Set<String> documentsListSpecific = new HashSet<String>(documentsList);
					Set<String> loanListSpecific = new HashSet<String>(loanList);
					Set<String> assetListSpecific = new HashSet<String>();
					if(filter.getTypeOfSecurity().equals("MOVABLE")) {
						assetListSpecific =new HashSet<String>(assetList);
					}
					
					int numberOfTotalRecords = 0;
					if(filter.getTypeOfSecurity().equals("IMMOVABLE")) {
						numberOfTotalRecords = borrowListSpecific.size() + thirdPartyListSpecific.size() + securityInterestListSpecific.size()
								+ propertyListSpecific.size() + documentsListSpecific.size() + loanListSpecific.size();
						
						System.out.println("IMMOVABLE = numberOfTotalRecords = "+numberOfTotalRecords);
						//System.out.println("MOVABLE = numberOfTotalRecords = "+numberOfTotalRecords);
						System.out.println("borrowListSpecific.size() = "+borrowListSpecific.size()+" // thirdPartyListSpecific.size() = "+thirdPartyListSpecific.size()+" // securityInterestListSpecific.size() = "+securityInterestListSpecific.size()
						+" // propertyListSpecific.size() = "+propertyListSpecific.size()+" // documentsListSpecific.size() = "+documentsListSpecific.size()+" // loanListSpecific.size() = "+loanListSpecific.size());
						
					} else {
						numberOfTotalRecords = borrowListSpecific.size() + thirdPartyListSpecific.size() + securityInterestListSpecific.size()
						+ assetListSpecific.size() + documentsListSpecific.size() + loanListSpecific.size();
						
						System.out.println("MOVABLE = numberOfTotalRecords = "+numberOfTotalRecords);
						System.out.println("borrowListSpecific.size() = "+borrowListSpecific.size()+" // thirdPartyListSpecific.size() = "+thirdPartyListSpecific.size()+" // securityInterestListSpecific.size() = "+securityInterestListSpecific.size()
						+" // assetListSpecific.size() = "+assetListSpecific.size()+" // documentsListSpecific.size() = "+documentsListSpecific.size()+" // loanListSpecific.size() = "+loanListSpecific.size());
					}
					
					if(numberOfTotalRecords <= 1000) {
						rpFlag=false;
						System.out.println("GenerateReportCmd.java => Cersai batch report => going inside getReportService().generateReport with one file");
						fileName = getReportService().generateReport(reportId, fileName,filter);
						System.out.println("GenerateReportCmd.java => Cersai batch report =>  out from getReportService().generateReport with one file");
					}else {
						rpFlag=true;
						System.out.println("GenerateReportCmd.java => Cersai batch report =>  going inside getReportService().generateReport with two files and one zip");
						getReportService().generateReport2(reportId, fileName,fileName2,fileName3,filter);
						System.out.println("GenerateReportCmd.java => Cersai batch report =>  out from getReportService().generateReport with two files and one zip");
					}
					
				}else if(filter.getReportId().equals("RPT0067")) {
					System.out.println("GenerateReportCmd.java => Cersai charge release report => going inside getReportService().generateReport with two files and zip file");
					getReportService().generateReport2(reportId, fileName,fileName2,fileName3,filter);
					System.out.println("GenerateReportCmd.java => Cersai charge release report => out from getReportService().generateReport with two files and zip file");
				}
				else {
					System.out.println("GenerateReportCmd.java => going inside getReportService().generateReport fileName=>"+fileName);
					
					fileName =	getReportService().generateReport(reportId, fileName,filter);
					System.out.println("GenerateReportCmd.java => out from getReportService().generateReport fileName=>"+fileName);
				}

				//--------------------------------------------------------
				
				String reportFile=getBasePath()+ System.getProperty("file.separator") + fileName;
				System.out.println("GenerateReport.java => reportFile == "+reportFile);
				/*File file = new File(reportFile);
				fileData = IOUtils.toByteArray(new FileInputStream(file));
				output.write(fileData);*/
				
				// ADDING TEXT FILES IN ZIP FILE.
				if(rpFlag ==true || (("true").equals(filter.getIndustry()))) {
					System.out.println("Creating zip file inside GenerateReportCmd.java");
				filter.setIndustry(null);
				String zipFile = "";
				if(filter.getTypeOfSecurity().equals("IMMOVABLE")) {
				zipFile ="cerupload_adsi" 
						+DateFormatUtils.format(new Date(), "_dd-MM-yyyy")+".zip";
//					zipFile ="cerupload"+DateFormatUtils.format(new Date(), "_dd-MM-yyyy")+".zip";
				}else {
					zipFile ="cerupload_mvin" 
							+DateFormatUtils.format(new Date(), "_dd-MM-yyyy")+".zip";
				}
		         
		        String[] srcFiles = { getBasePath()+fileName, getBasePath()+fileName2};
		         
		        try {
		             
		            // create byte buffer
		            byte[] buffer = new byte[1024];
		 
		            FileOutputStream fos = new FileOutputStream(getBasePath()+zipFile);
		 
		            ZipOutputStream zos = new ZipOutputStream(fos);
		             
		            for (int i=0; i < srcFiles.length; i++) {
		                 
		                File srcFile = new File(srcFiles[i]);
		 
		                FileInputStream fis = new FileInputStream(srcFile);
		 
		                // begin writing a new ZIP entry, positions the stream to the start of the entry data
		                zos.putNextEntry(new ZipEntry(srcFile.getName()));
		                 
		                int length=0;
		 
		                while ((length = fis.read(buffer)) > 0) {
		                    zos.write(buffer, 0, length);
		                }
		 
		                zos.closeEntry();
		 
		                // close the InputStream
		                fis.close();
		                 
		            }
		 
		            // close the ZipOutputStream
		            zos.close();
		            fileName=zipFile;
		            reportFile=getBasePath()+ System.getProperty("file.separator") + fileName;
		        }
		        catch (IOException ioe) {
		            System.out.println("Error creating zip file: " + ioe);
		        }
				
				}
				
				resultMap.put("fileName", fileName);
//				file.delete();
				resultMap.put("reportfile", reportFile);
				DefaultLogger.debug(this,"Report genration for report '" + reportId+ "' is done.");
			}
			catch (Exception e) {
				System.out.println("Inside GenerateReportCmd.java UnhandledException == "+e);
				throw new UnhandledException(e);
				// todo
			}
			finally {
				System.out.println("Inside GenerateReportCmd.java inside finally ");
				if (!resultMap.containsKey("fileName")) {
					System.out.println("Inside GenerateReportCmd.java inside finally => inside !resultMap.containsKey ");
					resultMap.put("fileName", "");
				}
			}
			}
			
			//resultMap.put("output", output);
			resultMap.put("dueDates", filter.getEventOrCriteria());
			resultMap.put("reportFormObj", filter);
			DefaultLogger.debug(this, "Going out of doExecute()");
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			return returnMap;
		}
		
	}
	/**
	 * This method will return the basepath
	 * 
	 * @return String
	 */
	public String getBasePath() {
		return PropertyManager.getValue(IReportConstants.BASE_PATH);
	}
}
