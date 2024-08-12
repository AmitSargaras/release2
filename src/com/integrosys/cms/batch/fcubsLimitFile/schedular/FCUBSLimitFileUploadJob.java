package com.integrosys.cms.batch.fcubsLimitFile.schedular;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ILineCovenant;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.customer.bus.OBLineCovenantReport;
import com.integrosys.cms.app.ftp.CMSFtpClient;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.ws.dto.FCUBSDataLogException;
import com.integrosys.cms.app.ws.dto.FCUBSDataLogHelper;
import com.integrosys.cms.app.ws.dto.FCUBSReportDataLogHelper;
import com.integrosys.cms.app.ws.dto.OBFCUBSDataLog;
import com.integrosys.cms.app.ws.dto.OBFCUBSReportDataLog;


public class FCUBSLimitFileUploadJob implements FCUBSFileConstants {

	public long totalSum = 0;
	boolean flagFcubs = false;

	private final static Logger logger = LoggerFactory.getLogger(FCUBSLimitFileUploadJob.class);

	public static void main(String[] args) {

		new FCUBSLimitFileUploadJob().execute();
	}

	public FCUBSLimitFileUploadJob() {


	}

	/**
	 * This job is run and executed by quartz schedular.
	 * For more details refer to schedular configuration in 
	 * config\spring\batch\fcubsLimitFile\AppContext_Master.xml
	 * 
	 * Schedular has been designed to carry out the following activities
	 * 1. Fetch the pending released line details
	 * 2. Generate the file and add all the records as per the format shared
	 * 3. Upload the file to remote location
	 * 4. Store the records in the log table.
	 * 5.Move the file in backup folder
	 * 6.Delete file after 7 days.
	 */

	public void execute() {	
		try {
						ResourceBundle bundle1 = ResourceBundle.getBundle("ofa");
			String fcubsServerName = bundle1.getString("fcubs.server.name");
			DefaultLogger.debug(this, "FCUBSLimitFileUploadJob started...." + Calendar.getInstance().getTime());
			
			if(null!= fcubsServerName && fcubsServerName.equalsIgnoreCase("app1")){
				DefaultLogger.debug(this, "FCUBS File Upload => flagFcubs flag is =>" + flagFcubs);
				if (isSFTPAvailable()) {
				System.out.println("FCUBS File Upload => flagFcubs flag is Line no 72..=>"+flagFcubs);
				
				if(flagFcubs == false) {
					
			DefaultLogger.debug(this,"Starting FCUBSLimitFileUploadJob......"+Calendar.getInstance().getTime());
			System.out.println("Starting FCUBSLimitFileUploadJob");
			// Fetch record from limit table
			ILimitDAO dao = LimitDAOFactory.getDAO();
			flagFcubs = true;
			OBCustomerSysXRef[] customerSysXRefList = dao.getLimitProfileforFCUBSFile();
			
			DefaultLogger.debug(this,"FCUBSLimitFileUploadJob fetched Line Details record......"+customerSysXRefList.length);
			System.out.println("FCUBSLimitFileUploadJob fetched Line Details record......"+customerSysXRefList.length);

			System.out.println("FCUBS File Upload=> Inside If => flagFcubs flag is Line no 83... =>"+flagFcubs);
			
			//NPA Classification FCUBS Start
			
			String colId = "";
			String stockdocMonth = "";
			String stockdocYear = "";
			int count = 0;
			
			//Co-Borrower
		//	OBCustomerSysXRef[] coBorrowerList = dao.getCoBorrowerList(customerSysXRefList);
			for(OBCustomerSysXRef obCustomerSysXRef : customerSysXRefList){
				long xrefId = (long)obCustomerSysXRef.getXRefID();
			//	ArrayList coBorrowerList = new ArrayList();
				 dao.getCoBorrowerList( obCustomerSysXRef ,xrefId);
				
			}
			
			/*for(OBCustomerSysXRef obCustomerSysXRef : customerSysXRefList){
				long xrefId = (long)obCustomerSysXRef.getXRefID();
				ArrayList collateralIdList = new ArrayList();
				collateralIdList = dao.getCollateralIdList(xrefId);
				if(!collateralIdList.isEmpty()) {
					for(int i=0;i<collateralIdList.size();i++) {
						colId = (String) collateralIdList.get(i);
						stockdocMonth = dao.getStockDocMonthByColId(colId);
						stockdocYear = dao.getStockDocYearByColId(colId);
						
						if(stockdocMonth != null && !"".equals(stockdocMonth) && stockdocYear != null && !"".equals(stockdocYear)) {
							obCustomerSysXRef.setStockDocMonth(stockdocMonth);
							obCustomerSysXRef.setStockDocYear(stockdocYear);
						}
					}
					
				}else {
					obCustomerSysXRef.setStockDocMonth(stockdocMonth);
					obCustomerSysXRef.setStockDocYear(stockdocYear);
				}
				
			}*/
			
			
			
			
			/*for(OBCustomerSysXRef obCustomerSysXRef : customerSysXRefList){
				long xrefId = (long)obCustomerSysXRef.getXRefID();
				ArrayList collateralIdList = new ArrayList();
				if(count == 1) {
					break;
				}
				collateralIdList = dao.getCollateralIdList(xrefId);
				count++;
				if(!collateralIdList.isEmpty()) {
					System.out.println("FCUBS File Upload =>collateralIdList.size()=>"+collateralIdList.size());
					for(int i=0;i<collateralIdList.size();i++) {
						colId = (String) collateralIdList.get(i);
						stockdocMonth = dao.getStockDocMonthByColId(colId);
						stockdocYear = dao.getStockDocYearByColId(colId);
						
						if(stockdocMonth != null && !"".equals(stockdocMonth) && stockdocYear != null && !"".equals(stockdocYear)) {
							obCustomerSysXRef.setStockDocMonth(stockdocMonth);
							obCustomerSysXRef.setStockDocYear(stockdocYear);
							break;
						}
					}
					
				}else {
					obCustomerSysXRef.setStockDocMonth(stockdocMonth);
					obCustomerSysXRef.setStockDocYear(stockdocYear);
				}
				
			}*/
			
					String facilityId = "";
//					for (OBCustomerSysXRef obCustomerSysXRef : customerSysXRefList) {
//						long xrefId = (long) obCustomerSysXRef.getXRefID();
//						ArrayList collateralIdList = new ArrayList();
//						System.out.println("FCUBS File Upload =>facilityId=>" + facilityId
//								+ " obCustomerSysXRef.getFacilityId()=>" + obCustomerSysXRef.getFacilityId());
//						
//						if (!(facilityId).equals(obCustomerSysXRef.getFacilityId())) {
//							
//							count++;
//							System.out.println("FCUBS File Upload =>Inside Condition true==>facilityId=>" + facilityId
//									+ " obCustomerSysXRef.getFacilityId()=>" + obCustomerSysXRef.getFacilityId()+"  count for loop=>"+count);
//
//							facilityId = obCustomerSysXRef.getFacilityId();
//
//							collateralIdList = dao.getCollateralIdList(xrefId);
//
//							if (!collateralIdList.isEmpty()) {
//								System.out.println(
//										"FCUBS File Upload =>collateralIdList.size()=>" + collateralIdList.size());
//								for (int i = 0; i < collateralIdList.size(); i++) {
//									colId = (String) collateralIdList.get(i);
//									stockdocMonth = dao.getStockDocMonthByColId(colId);
//									stockdocYear = dao.getStockDocYearByColId(colId);
//
//									if (stockdocMonth != null && !"".equals(stockdocMonth) && stockdocYear != null
//											&& !"".equals(stockdocYear)) {
//										obCustomerSysXRef.setStockDocMonth(stockdocMonth);
//										obCustomerSysXRef.setStockDocYear(stockdocYear);
//										break;
//									}
//								}
//
//							} else {
//								obCustomerSysXRef.setStockDocMonth("");
//								obCustomerSysXRef.setStockDocYear("");
//							}
//						}else {
//						obCustomerSysXRef.setStockDocMonth(stockdocMonth);
//						obCustomerSysXRef.setStockDocYear(stockdocYear);
//						}
//					}
			
			
			
			
			
			
			//NPA Classification FCUBS End
			
			//Duplicate entry sent to core issue
			for(OBCustomerSysXRef obCustomerSysXRef : customerSysXRefList){
				//log progress flag into table 
//				obCustomerSysXRef.setStockDocMonth(stockdocMonth);
//				obCustomerSysXRef.setStockDocYear(stockdocYear);
				dao.updateStatusSchedulerProgress(obCustomerSysXRef.getXRefID(),obCustomerSysXRef.getSourceRefNo());
			}
			

			if (null != customerSysXRefList && customerSysXRefList.length > 0) {
				List<OBFCUBSReportDataLog> covennantReportObj =null;
				List<OBFCUBSReportDataLog> FinalcovennantReportObj =null;
				FCUBSReportDataLogHelper  fcubsReportDataLogHelper = null;
				totalSum = 0;
				int recordNo = 1;
				File fcubsFile = null;
				BufferedWriter out = null;
				FileWriter fw = null;
				ResourceBundle bundle = ResourceBundle.getBundle("ofa");
				String serverFilePath = bundle.getString(FTP_FCUBS_UPLOAD_LOCAL_DIR);
				String fileName = FCUBS_FILENAME; 
				/** Below 4 lines to be uncomment if need to fetch application date **/
				 //IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
		        //IGeneralParamEntry generalParamEntry = generalParamDao.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
				//String date = generalParamEntry.getParamValue();
				//Date dateApplication=new Date(date);
				
				Date date=new Date();
				String fileDate = toDateFormat(date,FCUBS_FILEDATEFORMAT);
				String seqNo = dao.getSeqNoForFile();
				fileName = fileName + fileDate + FCUBS_FILESTATICNAME + seqNo + FCUBS_FILEEXTENSION;
				DefaultLogger.debug(this, "FCUBS File Upload => filename => " + fileName);
				fcubsFile = new File(serverFilePath + fileName);
				DefaultLogger.debug(this,"FCUBSLimitFileUploadJob FileName......" + serverFilePath + fileName);
				File dirFile = new File(serverFilePath);
				if (!dirFile.exists()) {
					dirFile.mkdirs();
				}


				try {
					boolean createNewFile = fcubsFile.createNewFile();
					if (createNewFile == false) {
						DefaultLogger.debug(this, "Error while creating new file:" + fcubsFile.getPath());
					}
					fw = new FileWriter(fcubsFile.getAbsoluteFile(), true);
					out = new BufferedWriter(fw);
					for (OBCustomerSysXRef obCustomerSysXRef : customerSysXRefList) {
						System.out.println("FCUBSLimitFileUploadJob Record "+recordNo+"ID is "+obCustomerSysXRef.getXRefID());
					if(null != obCustomerSysXRef.getInternalRemarks() && !obCustomerSysXRef.getInternalRemarks().isEmpty()) {
						if(obCustomerSysXRef.getInternalRemarks().length()>255){
						String remarks=obCustomerSysXRef.getInternalRemarks().substring(0, 255);
						obCustomerSysXRef.setInternalRemarks(remarks);
						}
						System.out.println("obCustomerSysXRef.getInternalRemarks()"+obCustomerSysXRef.getInternalRemarks());
						
					}
					try {
						System.out.println("FCUBSLimitFileUploadJob Report data logging started "+ recordNo+"ID is "+obCustomerSysXRef.getXRefID());

						covennantReportObj =  new ArrayList();
						covennantReportObj = generateFCUBSLogData(obCustomerSysXRef,1,covennantReportObj,fileName);
						//System.out.println("Inside sachin list2 => "+covennantReportObj);
						//add to list
						FinalcovennantReportObj= new ArrayList();
						FinalcovennantReportObj.addAll(covennantReportObj);
						System.out.println("FCUBSLimitFileUploadJob Report data logging ended "+ recordNo+"ID is "+obCustomerSysXRef.getXRefID());

					}
					catch(Exception e){
						System.out.println("Exception in Report data writing.  e=>"+e);
						e.printStackTrace();
					}
					out = generateFCUBSFile(obCustomerSysXRef,recordNo,out);
					out.newLine();
					recordNo++;
					//add to list
					}

					out.write(totalSum + ",^,");

					out.close();
					fw.close();
					flagFcubs = false;
					DefaultLogger.debug(this,"FCUBS File Upload => flagFcubs flag is Line no 122..=>" + flagFcubs);

					uploadFileToSFTP(fileName, fileDate);
					
				try {
					for(OBFCUBSReportDataLog OBLineCovenantReport : FinalcovennantReportObj)
					{
						fcubsReportDataLogHelper = new FCUBSReportDataLogHelper();
						System.out.println("Inside OBFCUBSReportDataLog loop => "+OBLineCovenantReport.getPartyId());
						fcubsReportDataLogHelper.fcubsReportDataLoggingActivity(OBLineCovenantReport,"",new Date());
					}
				}
				catch(Exception e){
					System.out.println("Exception in Logging data to report FCUBSLimitFileUploadJob.  e=>"+e);
					e.printStackTrace();
				}
					
					Date requestDate = new Date();
					FCUBSDataLogHelper fcubsDataLogHelper = new FCUBSDataLogHelper();
					DefaultLogger.debug(this,"FCUBSLimitFileUploadJob.java going for fcubsDataLoggingActivity...");
					for (OBCustomerSysXRef obCustomerSysXRef : customerSysXRefList) {
						fcubsDataLogHelper.fcubsDataLoggingActivity(obCustomerSysXRef, fileName,
								requestDate);
						dao.updateStatus(obCustomerSysXRef.getXRefID(), obCustomerSysXRef.getSourceRefNo());
					}

					moveFile(fcubsFile, fileName);
					deleteOldFiles();

					} catch (Exception e) {
						DefaultLogger.debug(this,"Exception in uploadFileToSFTP FCUBSLimitFileUploadJob.  e=>" + e);
						e.printStackTrace();
					
						moveFailedFile(fcubsFile, fileName);
					}

				}
				flagFcubs = false;
				DefaultLogger.debug(this, "FCUBS File Upload=> OutSide if => flagFcubs flag is: " + flagFcubs);
				}
				}
			}
			DefaultLogger.debug(this, "FCUBSLimitFileUploadJob End...." + Calendar.getInstance().getTime());

		}catch (LimitException e) {
			DefaultLogger.debug(this,"FCUBSLimitFileUploadJob in catch LimitException......"+e.getMessage());
			e.printStackTrace();
		} catch (FCUBSDataLogException e) {
			flagFcubs = false;
			DefaultLogger.debug(this, "FCUBSLimitFileUploadJob in catch FCUBSDataLogException......" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			flagFcubs = false;
			DefaultLogger.debug(this, "FCUBSLimitFileUploadJob in catch IOException......" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			flagFcubs = false;
			DefaultLogger.debug(this, "FCUBSLimitFileUploadJob in catch Exception......" + e.getMessage());
			e.printStackTrace();
		}

	}


	private BufferedWriter generateFCUBSFile(OBCustomerSysXRef obCustomerSysXRef,int recordNo,BufferedWriter out) throws Exception {

		ILimitDAO dao = LimitDAOFactory.getDAO();
		DefaultLogger.debug(this,"FCUBSLimitFileUploadJob generate File Record"+recordNo+"ID is "+obCustomerSysXRef.getXRefID());
		String branchCode = "";
		if(null!=obCustomerSysXRef.getLiabBranch() && !"".equalsIgnoreCase(obCustomerSysXRef.getLiabBranch()))
		branchCode = fetchBranchCode(obCustomerSysXRef.getLiabBranch());
		//String ruleId = fetchDesc(obCustomerSysXRef.getRuleId());
		String limitStartDate = toDateFormat(obCustomerSysXRef.getLimitStartDate(),FCUBS_DATEFORMAT);
		String limitExpiryDate = toDateFormat(obCustomerSysXRef.getDateOfReset(),FCUBS_DATEFORMAT);
		String lastAvailableDate = toDateFormat(obCustomerSysXRef.getLastavailableDate(),FCUBS_DATEFORMAT);
		String intradayLimitExpiryDate = toDateFormat(obCustomerSysXRef.getIntradayLimitExpiryDate(),FCUBS_DATEFORMAT);
		
		String coBorrowerId_1= null != obCustomerSysXRef.getCoBorrowerId_1() ? obCustomerSysXRef.getCoBorrowerId_1() : "";
		String coBorrowerName_1= null != obCustomerSysXRef.getCoBorrowerName_1() ? obCustomerSysXRef.getCoBorrowerName_1() : "";
		
		String coBorrowerId_2=  null != obCustomerSysXRef.getCoBorrowerId_2() ? obCustomerSysXRef.getCoBorrowerId_2() : "";
		String coBorrowerName_2=  null != obCustomerSysXRef.getCoBorrowerName_2() ? obCustomerSysXRef.getCoBorrowerName_2() : "";
		
		String coBorrowerId_3=  null != obCustomerSysXRef.getCoBorrowerId_3() ? obCustomerSysXRef.getCoBorrowerId_3() : "";
		String coBorrowerName_3=  null != obCustomerSysXRef.getCoBorrowerName_3() ? obCustomerSysXRef.getCoBorrowerName_3() : "";
		
		String coBorrowerId_4=  null != obCustomerSysXRef.getCoBorrowerId_4() ? obCustomerSysXRef.getCoBorrowerId_4() : "";
		String coBorrowerName_4=  null != obCustomerSysXRef.getCoBorrowerName_4() ? obCustomerSysXRef.getCoBorrowerName_4() : "";
		
		String coBorrowerId_5=  null != obCustomerSysXRef.getCoBorrowerId_5() ? obCustomerSysXRef.getCoBorrowerId_5() : "";
		String coBorrowerName_5=  null != obCustomerSysXRef.getCoBorrowerName_5() ? obCustomerSysXRef.getCoBorrowerName_5() : "";
		

//		DefaultLogger.debug(this,"FCUBSLimitFileUploadJob write 1st line"+recordNo+"ID is "+obCustomerSysXRef.getXRefID());
		ResourceBundle bundle2 = ResourceBundle.getBundle("ofa");
		String scodLineNoList=PropertyManager.getValue("scod.linenocode.name");
		boolean scodB = false;
		if(scodLineNoList != null && !scodLineNoList.equals("")) {
		String[] scodlinelist = scodLineNoList.split(",");
		for(int i=0; i< scodlinelist.length; i++ ) {
			if(obCustomerSysXRef.getLineNo()!=null && obCustomerSysXRef.getLineNo().equals(scodlinelist[i])) {
				scodB = true;
			}
		}
		}
//		System.out.println(".....daylightlimit.available.enable"+bundle2.getString("daylightlimit.available.enable"));
		System.out.println(".....scodB"+scodB);
		String stockLimitFlag = "";
		if("Y".equals(obCustomerSysXRef.getStockLimitFlag())) {
			stockLimitFlag = FCUBS_STOCK;
		}else {
			stockLimitFlag = FCUBS_CBOP;
		}
		System.out.println("obCustomerSysXRef.getStockLimitFlag()=>"+obCustomerSysXRef.getStockLimitFlag()+" ** stockLimitFlag=>"+stockLimitFlag);
		
		if("Yes".equalsIgnoreCase(bundle2.getString("daylightlimit.available.enable"))){
		if(scodB){
		if(obCustomerSysXRef.getDelay_level() == null || "".equals(obCustomerSysXRef.getDelay_level()) ) {
			if(null != obCustomerSysXRef.getRev_asset_class() && !"".equals(obCustomerSysXRef.getRev_asset_class())){
		out.write(FCUBS_FILEDESCKEY+","+obCustomerSysXRef.getAction()+","+recordNo+","+stockLimitFlag+","+obCustomerSysXRef.getSourceRefNo()
				+","+branchCode+","+obCustomerSysXRef.getFacilitySystemID()+","+obCustomerSysXRef.getLineNo()+","+obCustomerSysXRef.getSerialNo()
				+","+obCustomerSysXRef.getMainLineCode()+","+obCustomerSysXRef.getCurrency()+","+obCustomerSysXRef.getRuleId()+","
				+obCustomerSysXRef.getCurrencyRestriction()+","+obCustomerSysXRef.getRevolvingLine()
				+","+","+","+","+","+obCustomerSysXRef.getAvailable()+","+obCustomerSysXRef.getFreeze()
				+","+limitStartDate+","+limitExpiryDate
				+","+lastAvailableDate+","+obCustomerSysXRef.getReleasedAmount()
				+",,"+obCustomerSysXRef.getReleasedAmount()+","+obCustomerSysXRef.getInternalRemarks().replaceAll("[\\t\\n\\r]+"," ")+","+obCustomerSysXRef.getLimitTenorDays()+","+obCustomerSysXRef.getDayLightLimit()+",,,,,,"+obCustomerSysXRef.getIsDayLightLimitAvailable()+","+intradayLimitExpiryDate+","
				+obCustomerSysXRef.getProjectFinance()+","+obCustomerSysXRef.getProjectLoan()+","+obCustomerSysXRef.getInfaFlag()+","+obCustomerSysXRef.getScod()+",,,"+obCustomerSysXRef.getAcod()+",,"+obCustomerSysXRef.getRev_asset_class()+","+obCustomerSysXRef.getRev_asset_class_date()+","+obCustomerSysXRef.getStockDocMonth()+","
				+obCustomerSysXRef.getStockDocYear()+","
				
              
        /*         +coBorrowerId_1+"," +coBorrowerName_1+"," +coBorrowerId_2+"," +coBorrowerName_2+"," +coBorrowerId_3+"," +coBorrowerName_3+","
                 +coBorrowerId_4+"," +coBorrowerName_4+","+coBorrowerId_5+"," +coBorrowerName_5+","	*/
             
				);
		System.out.println(".....1");
			}else {
				out.write(FCUBS_FILEDESCKEY+","+obCustomerSysXRef.getAction()+","+recordNo+","+stockLimitFlag+","+obCustomerSysXRef.getSourceRefNo()
						+","+branchCode+","+obCustomerSysXRef.getFacilitySystemID()+","+obCustomerSysXRef.getLineNo()+","+obCustomerSysXRef.getSerialNo()
						+","+obCustomerSysXRef.getMainLineCode()+","+obCustomerSysXRef.getCurrency()+","+obCustomerSysXRef.getRuleId()+","
						+obCustomerSysXRef.getCurrencyRestriction()+","+obCustomerSysXRef.getRevolvingLine()
						+","+","+","+","+","+obCustomerSysXRef.getAvailable()+","+obCustomerSysXRef.getFreeze()
						+","+limitStartDate+","+limitExpiryDate
						+","+lastAvailableDate+","+obCustomerSysXRef.getReleasedAmount()
						+",,"+obCustomerSysXRef.getReleasedAmount()+","+obCustomerSysXRef.getInternalRemarks().replaceAll("[\\t\\n\\r]+"," ")+","+obCustomerSysXRef.getLimitTenorDays()+","+obCustomerSysXRef.getDayLightLimit()+",,,,,,"+obCustomerSysXRef.getIsDayLightLimitAvailable()+","+intradayLimitExpiryDate+","
						+obCustomerSysXRef.getProjectFinance()+","+obCustomerSysXRef.getProjectLoan()+","+obCustomerSysXRef.getInfaFlag()+","+obCustomerSysXRef.getScod()+",,,"+obCustomerSysXRef.getAcod()+",,"+obCustomerSysXRef.getExt_asset_class()+","+obCustomerSysXRef.getExt_asset_class_date()+","+obCustomerSysXRef.getStockDocMonth()+","
						+obCustomerSysXRef.getStockDocYear()+","
						
				/*		+coBorrowerId_1+"," +coBorrowerName_1+"," +coBorrowerId_2+"," +coBorrowerName_2+"," +coBorrowerId_3+"," +coBorrowerName_3+","
						+coBorrowerId_4+"," +coBorrowerName_4+","+coBorrowerId_5+"," +coBorrowerName_5+","	*/
						);
				System.out.println(".....2");
			}
		}else if("1".equals(obCustomerSysXRef.getDelay_level())) {
			out.write(FCUBS_FILEDESCKEY+","+obCustomerSysXRef.getAction()+","+recordNo+","+stockLimitFlag+","+obCustomerSysXRef.getSourceRefNo()
			+","+branchCode+","+obCustomerSysXRef.getFacilitySystemID()+","+obCustomerSysXRef.getLineNo()+","+obCustomerSysXRef.getSerialNo()
			+","+obCustomerSysXRef.getMainLineCode()+","+obCustomerSysXRef.getCurrency()+","+obCustomerSysXRef.getRuleId()+","
			+obCustomerSysXRef.getCurrencyRestriction()+","+obCustomerSysXRef.getRevolvingLine()
			+","+","+","+","+","+obCustomerSysXRef.getAvailable()+","+obCustomerSysXRef.getFreeze()
			+","+limitStartDate+","+limitExpiryDate
			+","+lastAvailableDate+","+obCustomerSysXRef.getReleasedAmount()
			+",,"+obCustomerSysXRef.getReleasedAmount()+","+obCustomerSysXRef.getInternalRemarks().replaceAll("[\\t\\n\\r]+"," ")+","+obCustomerSysXRef.getLimitTenorDays()+","+obCustomerSysXRef.getDayLightLimit()+",,,,,,"+obCustomerSysXRef.getIsDayLightLimitAvailable()+","+intradayLimitExpiryDate+","
			+obCustomerSysXRef.getProjectFinance()+","+obCustomerSysXRef.getProjectLoan()+","+obCustomerSysXRef.getInfaFlag()+","+obCustomerSysXRef.getScod()+","+obCustomerSysXRef.getEscod_l1()+",,,"+obCustomerSysXRef.getDelay_level()+","+obCustomerSysXRef.getRev_asset_class_L1()+","+obCustomerSysXRef.getRev_ext_asset_class_date_L1()+","+obCustomerSysXRef.getStockDocMonth()+","
			+obCustomerSysXRef.getStockDocYear()+","
			
          /*  +coBorrowerId_1+"," +coBorrowerName_1+"," +coBorrowerId_2+"," +coBorrowerName_2+"," +coBorrowerId_3+"," +coBorrowerName_3+","
            +coBorrowerId_4+"," +coBorrowerName_4+","+coBorrowerId_5+"," +coBorrowerName_5+","	*/
					);
			System.out.println(".....3");
		}else if("2".equals(obCustomerSysXRef.getDelay_level())) {
			out.write(FCUBS_FILEDESCKEY+","+obCustomerSysXRef.getAction()+","+recordNo+","+stockLimitFlag+","+obCustomerSysXRef.getSourceRefNo()
			+","+branchCode+","+obCustomerSysXRef.getFacilitySystemID()+","+obCustomerSysXRef.getLineNo()+","+obCustomerSysXRef.getSerialNo()
			+","+obCustomerSysXRef.getMainLineCode()+","+obCustomerSysXRef.getCurrency()+","+obCustomerSysXRef.getRuleId()+","
			+obCustomerSysXRef.getCurrencyRestriction()+","+obCustomerSysXRef.getRevolvingLine()
			+","+","+","+","+","+obCustomerSysXRef.getAvailable()+","+obCustomerSysXRef.getFreeze()
			+","+limitStartDate+","+limitExpiryDate
			+","+lastAvailableDate+","+obCustomerSysXRef.getReleasedAmount()
			+",,"+obCustomerSysXRef.getReleasedAmount()+","+obCustomerSysXRef.getInternalRemarks().replaceAll("[\\t\\n\\r]+"," ")+","+obCustomerSysXRef.getLimitTenorDays()+","+obCustomerSysXRef.getDayLightLimit()+",,,,,,"+obCustomerSysXRef.getIsDayLightLimitAvailable()+","+intradayLimitExpiryDate+","
			+obCustomerSysXRef.getProjectFinance()+","+obCustomerSysXRef.getProjectLoan()+","+obCustomerSysXRef.getInfaFlag()+","+obCustomerSysXRef.getScod()+","+obCustomerSysXRef.getEscod_l2()+","+obCustomerSysXRef.getRevised_escod_l2()+",,"+obCustomerSysXRef.getDelay_level()+","+obCustomerSysXRef.getRev_asset_class_L2()+","+obCustomerSysXRef.getRev_ext_asset_class_date_L2()+","+obCustomerSysXRef.getStockDocMonth()+","
			+obCustomerSysXRef.getStockDocYear()+","
		
			/*+coBorrowerId_1+"," +coBorrowerName_1+"," +coBorrowerId_2+"," +coBorrowerName_2+"," +coBorrowerId_3+"," +coBorrowerName_3+","
			+coBorrowerId_4+"," +coBorrowerName_4+","+coBorrowerId_5+"," +coBorrowerName_5+","	*/
		);
			System.out.println(".....4");
		}else if("3".equals(obCustomerSysXRef.getDelay_level())) {
			out.write(FCUBS_FILEDESCKEY+","+obCustomerSysXRef.getAction()+","+recordNo+","+stockLimitFlag+","+obCustomerSysXRef.getSourceRefNo()
			+","+branchCode+","+obCustomerSysXRef.getFacilitySystemID()+","+obCustomerSysXRef.getLineNo()+","+obCustomerSysXRef.getSerialNo()
			+","+obCustomerSysXRef.getMainLineCode()+","+obCustomerSysXRef.getCurrency()+","+obCustomerSysXRef.getRuleId()+","
			+obCustomerSysXRef.getCurrencyRestriction()+","+obCustomerSysXRef.getRevolvingLine()
			+","+","+","+","+","+obCustomerSysXRef.getAvailable()+","+obCustomerSysXRef.getFreeze()
			+","+limitStartDate+","+limitExpiryDate
			+","+lastAvailableDate+","+obCustomerSysXRef.getReleasedAmount()
			+",,"+obCustomerSysXRef.getReleasedAmount()+","+obCustomerSysXRef.getInternalRemarks().replaceAll("[\\t\\n\\r]+"," ")+","+obCustomerSysXRef.getLimitTenorDays()+","+obCustomerSysXRef.getDayLightLimit()+",,,,,,"+obCustomerSysXRef.getIsDayLightLimitAvailable()+","+intradayLimitExpiryDate+","
			+obCustomerSysXRef.getProjectFinance()+","+obCustomerSysXRef.getProjectLoan()+","+obCustomerSysXRef.getInfaFlag()+","+obCustomerSysXRef.getScod()+","+obCustomerSysXRef.getEscod_l3()+","+obCustomerSysXRef.getRevised_escod_l3()+",,"+obCustomerSysXRef.getDelay_level()+","+obCustomerSysXRef.getRev_asset_class_L3()+","+obCustomerSysXRef.getRev_ext_asset_class_date_L3()+","+obCustomerSysXRef.getStockDocMonth()+","
			+obCustomerSysXRef.getStockDocYear()+","

		/*	+coBorrowerId_1+"," +coBorrowerName_1+"," +coBorrowerId_2+"," +coBorrowerName_2+"," +coBorrowerId_3+"," +coBorrowerName_3+","
            +coBorrowerId_4+"," +coBorrowerName_4+","+coBorrowerId_5+"," +coBorrowerName_5+","	*/
			);
			System.out.println(".....5");
		}
		}else {
			out.write(FCUBS_FILEDESCKEY+","+obCustomerSysXRef.getAction()+","+recordNo+","+stockLimitFlag+","+obCustomerSysXRef.getSourceRefNo()
			+","+branchCode+","+obCustomerSysXRef.getFacilitySystemID()+","+obCustomerSysXRef.getLineNo()+","+obCustomerSysXRef.getSerialNo()
			+","+obCustomerSysXRef.getMainLineCode()+","+obCustomerSysXRef.getCurrency()+","+obCustomerSysXRef.getRuleId()+","
			+obCustomerSysXRef.getCurrencyRestriction()+","+obCustomerSysXRef.getRevolvingLine()
			+","+","+","+","+","+obCustomerSysXRef.getAvailable()+","+obCustomerSysXRef.getFreeze()
			+","+limitStartDate+","+limitExpiryDate
			+","+lastAvailableDate+","+obCustomerSysXRef.getReleasedAmount()
			+",,"+obCustomerSysXRef.getReleasedAmount()+","+obCustomerSysXRef.getInternalRemarks().replaceAll("[\\t\\n\\r]+"," ")+","+obCustomerSysXRef.getLimitTenorDays()+","+obCustomerSysXRef.getDayLightLimit()+",,,,,,"+obCustomerSysXRef.getIsDayLightLimitAvailable()+","+intradayLimitExpiryDate+","
			+ ",,,,,,,,,,"+obCustomerSysXRef.getStockDocMonth()+","
			+obCustomerSysXRef.getStockDocYear()+","
			
		/*	+coBorrowerId_1+"," +coBorrowerName_1+"," +coBorrowerId_2+"," +coBorrowerName_2+"," +coBorrowerId_3+"," +coBorrowerName_3+","
            +coBorrowerId_4+"," +coBorrowerName_4+","+coBorrowerId_5+"," +coBorrowerName_5+","	*/
            
			);
			System.out.println(".....6");
		}
		}else{
			
			out.write(FCUBS_FILEDESCKEY+","+obCustomerSysXRef.getAction()+","+recordNo+","+stockLimitFlag+","+obCustomerSysXRef.getSourceRefNo()
					+","+branchCode+","+obCustomerSysXRef.getFacilitySystemID()+","+obCustomerSysXRef.getLineNo()+","+obCustomerSysXRef.getSerialNo()
					+","+obCustomerSysXRef.getMainLineCode()+","+obCustomerSysXRef.getCurrency()+","+obCustomerSysXRef.getRuleId()+","
					+obCustomerSysXRef.getCurrencyRestriction()+","+obCustomerSysXRef.getRevolvingLine()
					+","+","+","+","+","+obCustomerSysXRef.getAvailable()+","+obCustomerSysXRef.getFreeze()
					+","+limitStartDate+","+limitExpiryDate
					+","+lastAvailableDate+","+obCustomerSysXRef.getReleasedAmount()
					+",,"+obCustomerSysXRef.getReleasedAmount()+","+obCustomerSysXRef.getInternalRemarks().replaceAll("[\\t\\n\\r]+"," ")+","+obCustomerSysXRef.getLimitTenorDays()+",,,,,,,"
					+ ",,,,,,,,,,"+obCustomerSysXRef.getStockDocMonth()+","
					+obCustomerSysXRef.getStockDocYear()+","
					 
				/*	+coBorrowerId_1+"," +coBorrowerName_1+"," +coBorrowerId_2+"," +coBorrowerName_2+"," +coBorrowerId_3+"," +coBorrowerName_3+","
					+coBorrowerId_4+"," +coBorrowerName_4+","+coBorrowerId_5+"," +coBorrowerName_5+","	*/
              );
			System.out.println(".....7");
		}
		String expiryDate = "";
		if(null!=obCustomerSysXRef.getDateOfReset() && !"".equals(obCustomerSysXRef.getDateOfReset())){
			expiryDate = obCustomerSysXRef.getDateOfReset().toString().replaceAll("-", "");
			expiryDate = expiryDate.substring(2,expiryDate.length());
		}

		DefaultLogger.debug(this,"FCUBSLimitFileUploadJob before checksum calc Record"+recordNo+"ID is "+obCustomerSysXRef.getXRefID());
		long sum = calChecksum(branchCode,obCustomerSysXRef.getFacilitySystemID(),obCustomerSysXRef.getLineNo(),expiryDate,obCustomerSysXRef.getSerialNo());
		out.write(sum+",^");
		out.newLine();
		DefaultLogger.debug(this,"FCUBSLimitFileUploadJob after checksum calc Record"+recordNo+"checksum is "+sum);
		DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+recordNo+"action  is "+obCustomerSysXRef.getAction());
		if(null!= obCustomerSysXRef.getAction() && !"".equalsIgnoreCase(obCustomerSysXRef.getAction()) && !obCustomerSysXRef.getAction().equalsIgnoreCase(ICMSConstant.FCUBSLIMIT_ACTION_REOPEN) && !obCustomerSysXRef.getAction().equalsIgnoreCase(ICMSConstant.FCUBSLIMIT_ACTION_CLOSE))
		{

			DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+recordNo+"within if loop action  is "+obCustomerSysXRef.getAction());

			if(null != obCustomerSysXRef.getBranchAllowedFlag() && !"".equalsIgnoreCase(obCustomerSysXRef.getBranchAllowedFlag())){
				ArrayList<String[]> branchAllowedList = getComboList(obCustomerSysXRef.getBranchAllowedFlag());

				for(String[] branchAllowed : branchAllowedList){

					String branchAllowedCode = fetchBranchCode(branchAllowed[0]);
					out.write(FCUBS_FILEBRANCHKEY+","+branchAllowedCode+","+branchAllowed[1]+",^,");
					out.newLine();

				}
			}

			DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+recordNo+"after branch allowed list "+FCUBS_FILEBRANCHKEY);


			if(null != obCustomerSysXRef.getLimitRestrictionFlag() && !"".equalsIgnoreCase(obCustomerSysXRef.getLimitRestrictionFlag())){
				ArrayList<String[]> customerRestrictionList = getComboList(obCustomerSysXRef.getLimitRestrictionFlag());

				for(String[] customerRestriction : customerRestrictionList){


					out.write(FCUBS_FILECUSTOMERKEY+","+customerRestriction[0]+","+customerRestriction[1]+",^,");
					out.newLine();

				}
			}
			DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+recordNo+"after customer restriction list "+FCUBS_FILECUSTOMERKEY);

			if(null != obCustomerSysXRef.getCurrencyAllowedFlag() && !"".equalsIgnoreCase(obCustomerSysXRef.getCurrencyAllowedFlag())){
				ArrayList<String[]> currencyAllowedList = getComboList(obCustomerSysXRef.getCurrencyAllowedFlag());

				for(String[] currencyAllowed : currencyAllowedList){


					out.write(FCUBS_FILECURRENCYKEY+","+currencyAllowed[0]+","+currencyAllowed[1]+",^,");
					out.newLine();

				}
			}

			DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+recordNo+"after currency  allowed list "+FCUBS_FILECURRENCYKEY);

			if(null != obCustomerSysXRef.getProductAllowedFlag() && !"".equalsIgnoreCase(obCustomerSysXRef.getProductAllowedFlag())){
				ArrayList<String[]> productAllowedList = getComboList(obCustomerSysXRef.getProductAllowedFlag());

				for(String[] productAllowed : productAllowedList){
					
					//String productCode = dao.getProductCodeFromId(productAllowed[0]);
					String productCode = dao.getActiveProductCodeFromId(productAllowed[0]);
					if(!"0".equals(productCode)) {
					out.write(FCUBS_FILEPRODUCTKEY+","+productCode+","+productAllowed[1]+",^,");
					out.newLine();
					}

				}
			}

			DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+recordNo+"after product  allowed list "+FCUBS_FILEPRODUCTKEY);



			//adding udf fields

			if(null!=obCustomerSysXRef.getIsCapitalMarketExposer() && !"".equalsIgnoreCase(obCustomerSysXRef.getIsCapitalMarketExposer()) && null != obCustomerSysXRef.getIsCapitalMarketExposerFlag() && !"".equalsIgnoreCase(obCustomerSysXRef.getIsCapitalMarketExposerFlag())) {

				out.write(FCUBS_FILEUDFKEY+","+FCUBS_CAPITALMARKETUDF+","+obCustomerSysXRef.getIsCapitalMarketExposer()+","+obCustomerSysXRef.getIsCapitalMarketExposerFlag()+",^,");
				out.newLine();
			}

			DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+recordNo+FCUBS_CAPITALMARKETUDF);
			if(null!=obCustomerSysXRef.getSegment() && !"".equalsIgnoreCase(obCustomerSysXRef.getSegment()) && null != obCustomerSysXRef.getSegment1Flag() && !"".equalsIgnoreCase(obCustomerSysXRef.getSegment1Flag())) {


				out.write(FCUBS_FILEUDFKEY+","+FCUBS_SEGMENTUDF+","+fetchDesc(obCustomerSysXRef.getSegment())+","+obCustomerSysXRef.getSegment1Flag()+",^,");
				out.newLine();
			}

			DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+recordNo+FCUBS_SEGMENTUDF);

			if(null!=obCustomerSysXRef.getEstateType() && !"".equalsIgnoreCase(obCustomerSysXRef.getEstateType()) && null != obCustomerSysXRef.getEstateTypeFlag() && !"".equalsIgnoreCase(obCustomerSysXRef.getEstateTypeFlag())) {

				out.write(FCUBS_FILEUDFKEY+","+FCUBS_REALESTATEUDF+","+obCustomerSysXRef.getEstateType()+","+obCustomerSysXRef.getEstateTypeFlag()+",^,");
				out.newLine();
			}
			
			else if(null != obCustomerSysXRef.getEstateTypeFlag() && !"".equalsIgnoreCase(obCustomerSysXRef.getEstateTypeFlag())) {

				out.write(FCUBS_FILEUDFKEY+","+FCUBS_REALESTATEUDF+",,"+obCustomerSysXRef.getEstateTypeFlag()+",^,");
				out.newLine();
			}

			DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+recordNo+FCUBS_REALESTATEUDF);

			if(null!=obCustomerSysXRef.getPrioritySector() && !"".equalsIgnoreCase(obCustomerSysXRef.getPrioritySector()) && null != obCustomerSysXRef.getPrioritySectorFlag() && !"".equalsIgnoreCase(obCustomerSysXRef.getPrioritySectorFlag())) {

				out.write(FCUBS_FILEUDFKEY+","+FCUBS_PSLFLAGUDF+","+fetchDesc(obCustomerSysXRef.getPrioritySector())+","+obCustomerSysXRef.getPrioritySectorFlag()+",^,");
				out.newLine();
			}

			DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+recordNo+FCUBS_PSLFLAGUDF);


			if(null!=obCustomerSysXRef.getUncondiCancl() && !"".equalsIgnoreCase(obCustomerSysXRef.getUncondiCancl()) && null != obCustomerSysXRef.getUncondiCanclFlag() && !"".equalsIgnoreCase(obCustomerSysXRef.getUncondiCanclFlag())) {


				out.write(FCUBS_FILEUDFKEY+","+FCUBS_UNCONDUDF+","+fetchDesc(obCustomerSysXRef.getUncondiCancl())+","+obCustomerSysXRef.getUncondiCanclFlag()+",^,");
				out.newLine();
			}

			DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+recordNo+FCUBS_UNCONDUDF);

			
			
			if(null!=obCustomerSysXRef.getCommRealEstateType() && !"".equalsIgnoreCase(obCustomerSysXRef.getCommRealEstateType()) && null != obCustomerSysXRef.getCommRealEstateTypeFlag() && !"".equalsIgnoreCase(obCustomerSysXRef.getCommRealEstateTypeFlag())) {


				out.write(FCUBS_FILEUDFKEY+","+FCUBS_CREUDF+","+fetchDesc(obCustomerSysXRef.getCommRealEstateType())+","+obCustomerSysXRef.getCommRealEstateTypeFlag()+",^,");
				out.newLine();
			}
			else if(null != obCustomerSysXRef.getCommRealEstateTypeFlag() && !"".equalsIgnoreCase(obCustomerSysXRef.getCommRealEstateTypeFlag())) {


				out.write(FCUBS_FILEUDFKEY+","+FCUBS_CREUDF+",,"+obCustomerSysXRef.getCommRealEstateTypeFlag()+",^,");
				out.newLine();
			}

			DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+recordNo+FCUBS_CREUDF);
			


			ArrayList<String[]> udfFieldsList = getUDFList(obCustomerSysXRef);

			if(null!=udfFieldsList && udfFieldsList.size()>0){
			for(String[] udfFields : udfFieldsList){

				out.write(FCUBS_FILEUDFKEY+","+udfFields[0]+","+udfFields[1]+","+udfFields[2]+",^,");
				out.newLine();

			}
			
			}

			ArrayList<String[]> udfFieldsList2 = getUDFList2(obCustomerSysXRef);

			if(null!=udfFieldsList2 && udfFieldsList2.size()>0){
			for(String[] udfFields : udfFieldsList2){

				out.write(FCUBS_FILEUDFKEY+","+udfFields[0]+","+udfFields[1]+","+udfFields[2]+",^,");
				out.newLine();

			}
			
			}
			
			DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+recordNo+"after udf  fields list "+FCUBS_FILEUDFKEY);

			if(null !=obCustomerSysXRef.getCoBorrowerId_1() && !"".equals(obCustomerSysXRef.getCoBorrowerId_1()) ) {
			out.write(FCUBS_FILECOBORROWERKEY+","+obCustomerSysXRef.getCoBorrowerId_1()+",^,");
			out.newLine();
			}
			if(null !=obCustomerSysXRef.getCoBorrowerId_2() && !"".equals(obCustomerSysXRef.getCoBorrowerId_2()) ) {
				out.write(FCUBS_FILECOBORROWERKEY+","+obCustomerSysXRef.getCoBorrowerId_2()+",^,");
				out.newLine();
				}
			
			if(null !=obCustomerSysXRef.getCoBorrowerId_3() && !"".equals(obCustomerSysXRef.getCoBorrowerId_3()) ) {
				out.write(FCUBS_FILECOBORROWERKEY+","+obCustomerSysXRef.getCoBorrowerId_3()+",^,");
				out.newLine();
				}
			if(null !=obCustomerSysXRef.getCoBorrowerId_4() && !"".equals(obCustomerSysXRef.getCoBorrowerId_4()) ) {
				out.write(FCUBS_FILECOBORROWERKEY+","+obCustomerSysXRef.getCoBorrowerId_4()+",^,");
				out.newLine();
				}
			
			if(null !=obCustomerSysXRef.getCoBorrowerId_5() && !"".equals(obCustomerSysXRef.getCoBorrowerId_5()) ) {
				out.write(FCUBS_FILECOBORROWERKEY+","+obCustomerSysXRef.getCoBorrowerId_5()+",^,");
				out.newLine();
				}
			
//			CAM Cov changes
			ILineCovenant[] lineCovArr = (ILineCovenant[]) dao.getLineCovenantDataForStp(obCustomerSysXRef.getXRefID());
			//ILineCovenant[] lineCovArr = obCustomerSysXRef.getLineCovenant();
			if(!ArrayUtils.isEmpty(lineCovArr)) {
				ILineCovenant singleCov = null;
				for(ILineCovenant lineCov : lineCovArr) {
					if(ICMSConstant.YES.equals(lineCov.getSingleCovenantInd()))
						singleCov = lineCov;
				}
				
				if(singleCov != null) {
					if(StringUtils.isNotBlank(singleCov.getCountryRestrictionFlag())) {
						DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+recordNo+" Flag Value"+singleCov.getCountryRestrictionFlag());

						ArrayList<String[]> covenantList = getComboListForCamCovenant(singleCov.getCountryRestrictionFlag());
						for(String[] cov : covenantList){
							
							String FcubsCode = dao.getFCUBSCountryCode(cov[0]);
							if(!"0".equals(FcubsCode)) {
								out.write(FCUBS_FILE_COVENANT_COUNTRY_RESTRICTION+","+FcubsCode+","+cov[2]+",DL,"+cov[1]+",^,");
								out.newLine();
								}						
						}
					}
					
					if(StringUtils.isNotBlank(singleCov.getDraweeFlag() )) {
						DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+recordNo+" Flag Value"+singleCov.getDraweeFlag());
						ArrayList<String[]> covenantList = getComboListForCamCovenant(singleCov.getDraweeFlag());
						for(String[] cov : covenantList){
							//As per new requirement date:: 23112022 DL need to pass in every scenario
							/*if("A".equals(cov[2])) {
								out.write(FCUBS_FILE_COVENANT_FACILITY_OTHER_PARTY+","+cov[0]+","+cov[2]+","+cov[1]+",DL,^,");
							}else {
								out.write(FCUBS_FILE_COVENANT_FACILITY_OTHER_PARTY+","+cov[0]+","+cov[2]+","+cov[1]+",AL,^,");
							}*/
							out.write(FCUBS_FILE_COVENANT_FACILITY_OTHER_PARTY+","+cov[0]+","+cov[2]+","+cov[1]+",DL,^,");
							out.newLine();
						}
					}
					
					if(StringUtils.isNotBlank(singleCov.getDrawerFlag())) {
						DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+recordNo+" Flag Value"+singleCov.getDrawerFlag());
						ArrayList<String[]> covenantList = getComboListForCamCovenant(singleCov.getDrawerFlag());
						for(String[] cov : covenantList){
							/*if("A".equals(cov[2])) {
								out.write(FCUBS_FILE_COVENANT_FACILITY_OTHER_PARTY+","+cov[0]+","+cov[2]+","+cov[1]+",DL,^,");
							}else {
								out.write(FCUBS_FILE_COVENANT_FACILITY_OTHER_PARTY+","+cov[0]+","+cov[2]+","+cov[1]+",AL,^,");
							}*/
							out.write(FCUBS_FILE_COVENANT_FACILITY_OTHER_PARTY+","+cov[0]+","+cov[2]+","+cov[1]+",DL,^,");
							out.newLine();
						}
					}
					
					if(StringUtils.isNotBlank(singleCov.getBeneficiaryFlag() )) {
						DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+recordNo+" Flag Value"+singleCov.getBeneficiaryFlag());
						ArrayList<String[]> covenantList = getComboListForCamCovenant(singleCov.getBeneficiaryFlag());
						for(String[] cov : covenantList){
							/*if("A".equals(cov[2])) {
								out.write(FCUBS_FILE_COVENANT_FACILITY_OTHER_PARTY+","+cov[0]+","+cov[2]+","+cov[1]+",DL,^,");
							}else {
								out.write(FCUBS_FILE_COVENANT_FACILITY_OTHER_PARTY+","+cov[0]+","+cov[2]+","+cov[1]+",AL,^,");
							}*/
							out.write(FCUBS_FILE_COVENANT_FACILITY_OTHER_PARTY+","+cov[0]+","+cov[2]+","+cov[1]+",DL,^,");

							out.newLine();
						}
					}
					
					String combinedTenorReq="";
					if(singleCov.getMaxCombinedTenor()!=null && StringUtils.isNotBlank(singleCov.getMaxCombinedTenor())) {
						long maxTenor=Long.parseLong(singleCov.getMaxCombinedTenor());
						if(maxTenor>0) {
							combinedTenorReq="Y";
						}else {
							combinedTenorReq="N";
						}
					}
					
					StringBuffer singleLineCovenant = new StringBuffer()  
							.append(FCUBS_FILE_COVENANT_SINGLE+",")
							.append(getDefaultEmpty(singleCov.getModuleCode())+",")
							.append(getDefaultEmpty(singleCov.getCommitmentTenor() )+",")
							.append(getDefaultEmpty(singleCov.getOrderBackedbylc())+",")
							.append(getDefaultEmpty(singleCov.getRunningAccount())+",")
							.append(getDefaultEmpty(singleCov.getMoratoriumPeriod() )+",")
							.append(StringUtils.isNotBlank(singleCov.getEmiFrequency())?singleCov.getEmiFrequency().substring(0,1)+",": StringUtils.EMPTY +",")
							.append(getDefaultEmpty(singleCov.getNoOfInstallments())+",")
							.append(getDefaultEmpty(singleCov.getEcgcCover() )+",")
							.append(getDefaultEmpty(singleCov.getBuyersRating())+",")
							.append(getDefaultEmpty(singleCov.getPostShipmentLinkage())+",")
							.append(getDefaultEmpty(combinedTenorReq)+",")
							.append(getDefaultEmpty(singleCov.getMaxCombinedTenor())+",^,");
						out.write(String.valueOf(singleLineCovenant));
						out.newLine();
					
					if(StringUtils.isNotBlank(singleCov.getRunningAccountFlag()) && ((StringUtils.isNotBlank(singleCov.getRunningAccount()) && singleCov.getRunningAccount().equals("Y"))
							||(StringUtils.isNotBlank(singleCov.getOrderBackedbylc()) && singleCov.getOrderBackedbylc().equals("Y"))
							)) {
						DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+recordNo+" Flag Value"+singleCov.getRunningAccountFlag());

						ArrayList<String[]> covenantList = getComboListForCamCovenant(singleCov.getRunningAccountFlag().trim());
						for(String[] cov : covenantList){
							out.write(FCUBS_FILE_COVENANT_FACILITY_INCOTERM+","+cov[0]+","+cov[1]+","+cov[2]+",^,");
							out.newLine();
						}
					}
						
					if(StringUtils.isNotBlank(singleCov.getGoodsRestrictionFlag())) {
						DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+recordNo+" Flag Value"+singleCov.getGoodsRestrictionFlag());
						ArrayList<String[]> covenantList = getComboListForGoods(singleCov.getGoodsRestrictionFlag().trim());
						for(String[] cov : covenantList){
							out.write(FCUBS_FILE_COVENANT_GOODS_RESTRICTION+","+cov[0]+","+cov[1]+",DL,^,");
							out.newLine();
						}
					}
					
					if(StringUtils.isNotBlank(singleCov.getCurrencyRestrictionFlag())) {
						DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+recordNo+" Flag Value"+singleCov.getCurrencyRestrictionFlag());
						ArrayList<String[]> covenantList = getComboListForCamCovenant(singleCov.getCurrencyRestrictionFlag());
						for(String[] cov : covenantList){
							out.write(FCUBS_FILE_COVENANT_CURRENCY_RESTRICTION+","+cov[0]+","+cov[2]+","+cov[1]+",^,");
							out.newLine();
						}
					}
					
					if(StringUtils.isNotBlank(singleCov.getBankRestrictionFlag())) {
						DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record"+recordNo+" Flag Value"+singleCov.getBankRestrictionFlag());
						ArrayList<String[]> covenantList = getComboListForCamCovenant(singleCov.getBankRestrictionFlag());
						for(String[] cov : covenantList){
							out.write(FCUBS_FILE_COVENANT_BANK_RESTRICTION+","+cov[0]+","+cov[2]+","+cov[1]+",DL,^,");
							out.newLine();
						}
					}
				}
			}
		}
		
		out.write("#,");

		return out;

	}



	private ArrayList<String[]> getUDFList(OBCustomerSysXRef obCustomerSysXRef) {

		ILimitDAO dao = LimitDAOFactory.getDAO();
		DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record "+obCustomerSysXRef.getXRefID()+" in UDF list ");
		ArrayList<String[]> udfList = new ArrayList<String[]>();

		String[] udfSelectList = getUDFSelectAndDeleteList(obCustomerSysXRef.getUdfAllowed() ,obCustomerSysXRef.getUdfDelete());
		String[] UDFField = null;
		if(null!=udfSelectList){
		for(String udfField : udfSelectList){

			UDFField = dao.getUDFDetails(udfField,obCustomerSysXRef.getXRefID());
			if(null!= UDFField){
				if(null != UDFField[0] && null != UDFField[1] && null != UDFField[2] && "null" != UDFField[0] && "null" != UDFField[1] && "null" != UDFField[2]){
					DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  UDF Record "+UDFField[0]+","+UDFField[1]+","+UDFField[2]+" in UDF list ");
				udfList.add(UDFField);
				}
			}
		}
		
	}
		return udfList;
	}

	private ArrayList<String[]> getUDFList2(OBCustomerSysXRef obCustomerSysXRef) {

		ILimitDAO dao = LimitDAOFactory.getDAO();
		DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  Record "+obCustomerSysXRef.getXRefID()+" in UDF list2 ");
		ArrayList<String[]> udfList = new ArrayList<String[]>();

		String[] udfSelectList = getUDFSelectAndDeleteList(obCustomerSysXRef.getUdfAllowed_2() ,obCustomerSysXRef.getUdfDelete_2());
		String[] UDFField = null;
		if(null!=udfSelectList){
		for(String udfField : udfSelectList){
		
			int newUdfField= Integer.parseInt(udfField);
			newUdfField=newUdfField+115;
			udfField= String.valueOf(newUdfField);
			
			UDFField = dao.getUDFDetails2(udfField,obCustomerSysXRef.getXRefID());
			if(null!= UDFField){
				if(null != UDFField[0] && null != UDFField[1] && null != UDFField[2] && "null" != UDFField[0] && "null" != UDFField[1] && "null" != UDFField[2]){
					DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  UDF Record "+UDFField[0]+","+UDFField[1]+","+UDFField[2]+" in UDF list ");
				udfList.add(UDFField);
				}
			}
		}
		
	}
		return udfList;
	}

	private String[] getUDFSelectAndDeleteList(String udfAllowed,
			String udfDelete) {

		String[] udfListAdded = null;
		String[] udfListDeleted = null;
		String[] udfList = null;
		DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  UDF Allowed "+udfAllowed+" in UDF list ");
		DefaultLogger.debug(this,"FCUBSLimitFileUploadJob  UDF Delete "+udfDelete+" in UDF list ");
		if(null!=udfAllowed && udfAllowed.contains(",")){
			udfListAdded = udfAllowed.split(",");
		}
		else if(null != udfAllowed && !"".equalsIgnoreCase(udfAllowed)){
			udfListAdded = new String[1];
			udfListAdded[0] = udfAllowed;
		}

		if(null!=udfDelete && udfDelete.contains(",")){
			udfListDeleted = udfDelete.split(",");
		}
		else if(null != udfDelete && !"".equalsIgnoreCase(udfDelete)){
			udfListDeleted = new String[1];
			udfListDeleted[0] = udfDelete;
		}

		udfList = (String[])ArrayUtils.addAll(udfListAdded, udfListDeleted);

		return udfList;
	}

	private ArrayList<String[]> getComboList(String dataList) {

		DefaultLogger.debug(this,"FCUBSLimitFileUploadJob get ComboList");
		ArrayList<String[]> splitList = new ArrayList<String[]>();
		String[] comboList = null;
		if(null!=dataList && dataList.contains(",")){
			comboList = dataList.split(",");
		}
		else if(null != dataList && !"".equalsIgnoreCase(dataList)){
			comboList = new String[1];
			comboList[0] = dataList;
		}
		for(String allowedList : comboList){

			String[] splitArray = allowedList.split("#");
			DefaultLogger.debug(this,"FCUBSLimitFileUploadJob get splitArray"+splitArray);
			splitList.add(splitArray);

		}
		return splitList;
	}

	private String fetchBranchCode(String liabBranch) throws Exception {

		DefaultLogger.debug(this,"FCUBSLimitFileUploadJob get BranchCode");
		ILimitDAO dao = LimitDAOFactory.getDAO();
		String fccBranchCode =dao.getBranchCodeFromId(liabBranch);
		DefaultLogger.debug(this,"FCUBSLimitFileUploadJob after BranchCode"+fccBranchCode);
		return fccBranchCode;
	}


	private String fetchDesc(String code) throws Exception {
		DefaultLogger.debug(this,"FCUBSLimitFileUploadJob get BranchCode");
		ILimitDAO dao = LimitDAOFactory.getDAO();
		String desc = dao.getDescFromCode(code);
		DefaultLogger.debug(this,"FCUBSLimitFileUploadJob after BranchCode"+desc);
		return desc;
	}


	public long calChecksum(String branchCode,String liabilityId, String lineCode, String expiryDate, String serialNo) throws Exception{

		DefaultLogger.debug(this,"FCUBSLimitFileUploadJob in checksum calc");
		String appendValue = branchCode+liabilityId+lineCode;
		long sum = 0;
		for ( int i = 0; i < appendValue.length(); i++ ) {
			char c = appendValue.charAt( i );

			int j = (int) c;
			sum = sum + j;
		}
		if(null!= serialNo && !"".equals(serialNo))
			sum = sum + Long.parseLong(serialNo) ;
		if(null!= expiryDate && !"".equals(expiryDate))
			sum = sum + Long.parseLong(expiryDate);

		DefaultLogger.debug(this,"FCUBSLimitFileUploadJob in checksum calc sum is"+sum);
		totalSum = totalSum + sum;
		DefaultLogger.debug(this,"FCUBSLimitFileUploadJob in checksum calc total sum is"+totalSum);

		return sum;


	}


	public static String toDateFormat(Date date, String formatter) throws Exception {

		try{
			if(date == null )
				return "";
			SimpleDateFormat dateFormat = new SimpleDateFormat(formatter);
			return dateFormat.format(date);
		}
		catch(Exception e){
			DefaultLogger.debug("","FCUBSLimitFileUploadJob toDateFormat"+e.getMessage());
			e.printStackTrace();
			throw e;

		}



	}


	public void uploadFileToSFTP(String fcubsFile,String fileDate) throws Exception{
		DefaultLogger.debug(this,"FCUBSLimitFileUploadJob uploadFCUBSFileToSFTP() ");
		ResourceBundle resbundle = ResourceBundle.getBundle("ofa");
		CMSFtpClient sftpClient = CMSFtpClient.getInstance("ofa",ICMSConstant.FCUBSLIMIT_FILE_UPLOAD);
		String remoteDataDir = resbundle.getString(FTP_FCUBS_UPLOAD_REMOTE_DIR)+fileDate+"/";

		String localDataDir = resbundle.getString(FTP_FCUBS_UPLOAD_LOCAL_DIR);
		sftpClient.openConnection();
		DefaultLogger.debug(this, "FCUBSLimitFileUploadJob uploadFileToSFTP():SFTP connection opened() ");
		sftpClient.uploadFCUBSFile(localDataDir+ fcubsFile, remoteDataDir, fcubsFile);
		sftpClient.closeConnection();
		DefaultLogger.debug(this, "FCUBSLimitFileUploadJob uploadFileToSFTP():FTP connection closed() ");


	}


	



	private void deleteOldFiles() {

		try{
			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			String serverFilePath = bundle.getString(FCUBSFileConstants.FTP_FCUBS_BACKUP_LOCAL_DIR);
			String days = bundle.getString(FCUBSFileConstants.FTP_FCUBS_UPLOAD_NOOFDAYS);
			long noofDays = 1;
			if(null!= days && !"".equalsIgnoreCase(days)){

				noofDays = Long.parseLong(days);
			}
			File folder = new File(serverFilePath);

			if (folder.exists()) {

				File[] listFiles = folder.listFiles();

				long eligibleForDeletion = System.currentTimeMillis() -
						(noofDays * 24 * 60 * 60 * 1000);

				for (File listFile: listFiles) {

					if (listFile.lastModified() < eligibleForDeletion) {

						boolean delete = listFile.delete();
						if(delete==false) {
							DefaultLogger.debug(this, "file  deletion failed for file:"+listFile.getPath());
							System.out.println("file  deletion failed for file:"+listFile.getPath());
						}
					}
				}
			}
		}
		catch(Exception e){

			e.printStackTrace();
		}

	}


	public void moveFile(File sourceFile,
			String fileName) {

		FileInputStream inputStream = null;
		FileOutputStream outstream = null;
		try {
			inputStream = new FileInputStream(sourceFile);
			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			String destPath = bundle.getString(FTP_FCUBS_BACKUP_LOCAL_DIR);
			File destFolderPath = new File(destPath);
			if (!destFolderPath.exists())
			{
				destFolderPath.mkdirs();
			}

			outstream = new FileOutputStream(destPath+fileName);
			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = inputStream.read(buffer)) > 0) {

				outstream.write(buffer, 0, length);

			}


		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (inputStream != null)
					inputStream.close();

				if (outstream != null)
					outstream.close();

				boolean delete = sourceFile.delete();
				if(delete==false) {
					DefaultLogger.debug(this, "moveFile(File sourceFile,String fileName) file  deletion failed for file:"+sourceFile.getPath());
					System.out.println("moveFile(File sourceFile,String fileName) file  deletion failed for file:"+sourceFile.getPath());
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}

	
	private String getDefaultEmpty(String str) {
		return StringUtils.isBlank(str)?StringUtils.EMPTY:str;
	}
	
	private ArrayList<String[]> getComboListForCamCovenant(String dataList) {

		DefaultLogger.debug(this,"FCUBSLimitFileUploadJob get getComboListForCamCovenant");
		ArrayList<String[]> splitList = new ArrayList<String[]>();
		String[] comboList = null;
		if(null!=dataList && dataList.contains(",")){
			comboList = dataList.split(",");
		}
		else if(null != dataList && !"".equalsIgnoreCase(dataList)){
			comboList = new String[1];
			comboList[0] = dataList;
		}
		for(String allowedList : comboList){
			String[] splitArray = allowedList.split("#|\\|");
			for (int i = 0; i < splitArray.length; i++)
				splitArray[i] = splitArray[i].trim();
			splitList.add(splitArray);
		}
		return splitList;
	}
	
	private ArrayList<String[]> getComboListForGoods(String dataList) {

		DefaultLogger.debug(this,"FCUBSLimitFileUploadJob get getComboListForGoods");
		ArrayList<String[]> splitList = new ArrayList<String[]>();
		String[] comboList = null;
		if(null!=dataList && dataList.contains(",")){
			comboList = dataList.split(",");
		}
		else if(null != dataList && !"".equalsIgnoreCase(dataList)){
			comboList = new String[1];
			comboList[0] = dataList;
		}
		for(String allowedList : comboList){

			String[] splitArray = allowedList.split("#");
			String[] temp=null;
			
			if(splitArray[0].endsWith("|0|0")) {
				temp=splitArray[0].split("\\|");
				splitArray[0]=temp[0];
			}else if(splitArray[0].endsWith("|0")) {
				temp=splitArray[0].split("\\|");
				splitArray[0]=temp[1];
			}else {
				temp=splitArray[0].split("\\|");
				splitArray[0]=temp[2];
			}
			
			DefaultLogger.debug(this,"FCUBSLimitFileUploadJob getComboListForGoods get splitArray"+splitArray);
			splitList.add(splitArray);

		}
		return splitList;
	}
	
	private List<OBFCUBSReportDataLog> generateFCUBSLogData(OBCustomerSysXRef obCustomerSysXRef,int recordNo,List<OBFCUBSReportDataLog> obj, String fileName) throws Exception {

		ILimitDAO dao = LimitDAOFactory.getDAO();
		System.out.println("Inside generateFCUBSLogData list2 => ");

		OBFCUBSDataLog fcubsObj = new OBFCUBSDataLog();
		 ILimitDAO dao1 = LimitDAOFactory.getDAO();
		fcubsObj = dao1.fetchPartyDetails(obCustomerSysXRef,fcubsObj);
		String facilityId="";
		if(null!= fileName && !"".equalsIgnoreCase(fileName))
		{
			fcubsObj.setFileName(fileName);
		}
		
		if(null!= obCustomerSysXRef.getSourceRefNo() && !"".equalsIgnoreCase(obCustomerSysXRef.getSourceRefNo()))
		{
			fcubsObj.setSourceRefNo(obCustomerSysXRef.getSourceRefNo());
		}
		
		if(null!= obCustomerSysXRef.getFacilitySystemID() && !"".equalsIgnoreCase(obCustomerSysXRef.getFacilitySystemID()))
		{
			fcubsObj.setLiabilityId(obCustomerSysXRef.getFacilitySystemID());
		}
		
		
		if(null!= obCustomerSysXRef.getLineNo() && !"".equalsIgnoreCase(obCustomerSysXRef.getLineNo()))
		{
			fcubsObj.setLineNo(obCustomerSysXRef.getLineNo());
		}
		
		if(null!= obCustomerSysXRef.getSerialNo() && !"".equalsIgnoreCase(obCustomerSysXRef.getSerialNo()))
		{
			fcubsObj.setSerialNo(obCustomerSysXRef.getSerialNo());
		}
		
		if(null!= obCustomerSysXRef.getFacilityId() && !"".equalsIgnoreCase(obCustomerSysXRef.getFacilityId()))
		{
			 facilityId=obCustomerSysXRef.getFacilityId();
		}
//			CAM Cov changes
			ILineCovenant[] lineCovArr = (ILineCovenant[]) dao.getLineCovenantDataForStp(obCustomerSysXRef.getXRefID());
			//ILineCovenant[] lineCovArr = obCustomerSysXRef.getLineCovenant();
			if(!ArrayUtils.isEmpty(lineCovArr)) {
				ILineCovenant singleCov = null;
				for(ILineCovenant lineCov : lineCovArr) {
					if(ICMSConstant.YES.equals(lineCov.getSingleCovenantInd()))
						singleCov = lineCov;
				}
				
				
				
				if(singleCov != null) {
					if(StringUtils.isNotBlank(singleCov.getCountryRestrictionFlag())) {
						ArrayList<String[]> covenantList = getComboListForCamCovenant(singleCov.getCountryRestrictionFlag());
						for(String[] cov : covenantList)
						{
							String FcubsCode = dao.getFCUBSCountryCode(cov[0]);
								
							OBFCUBSReportDataLog obj1 = new OBFCUBSReportDataLog();
							obj1.setTypeOfCovenant("Country");
							if(!"0".equals(FcubsCode)) {
								obj1.setCondition1(FcubsCode);
							}						
							obj1.setCondition2(cov[1]);
							obj1.setPartyId(fcubsObj.getPartyId());
							obj1.setPartyName(fcubsObj.getPartyName());
							obj1.setXrefId(String.valueOf(obCustomerSysXRef.getXRefID()));
							obj1.setFileName(fcubsObj.getFileName());
							obj1.setLiabilityId(fcubsObj.getLiabilityId());
							obj1.setSourceRefNo(fcubsObj.getSourceRefNo());
							obj1.setLineNo(fcubsObj.getLineNo());
							obj1.setSerialNumber(fcubsObj.getSerialNo());
							obj1.setFacilityId(String.valueOf(facilityId));
							obj.add(obj1);
							
						}
					}
					
					if(StringUtils.isNotBlank(singleCov.getDraweeFlag() )) {
						ArrayList<String[]> covenantList = getComboListForCamCovenant(singleCov.getDraweeFlag());
						for(String[] cov : covenantList)
						{
							OBFCUBSReportDataLog obj1 = new OBFCUBSReportDataLog();
							obj1.setTypeOfCovenant("Drawee");
							obj1.setCondition1(cov[0]);
							obj1.setCondition2(cov[1]);
							obj1.setPartyId(fcubsObj.getPartyId());
							obj1.setPartyName(fcubsObj.getPartyName());
							obj1.setXrefId(String.valueOf(obCustomerSysXRef.getXRefID()));
							obj1.setFileName(fcubsObj.getFileName());
							obj1.setLiabilityId(fcubsObj.getLiabilityId());
							obj1.setSourceRefNo(fcubsObj.getSourceRefNo());
							obj1.setLineNo(fcubsObj.getLineNo());
							obj1.setSerialNumber(fcubsObj.getSerialNo());
							obj1.setFacilityId(String.valueOf(facilityId));

							obj.add(obj1);
						}
					}
					
					if(StringUtils.isNotBlank(singleCov.getDrawerFlag())) {
						ArrayList<String[]> covenantList = getComboListForCamCovenant(singleCov.getDrawerFlag());
						for(String[] cov : covenantList){
							OBFCUBSReportDataLog obj1 = new OBFCUBSReportDataLog();
							obj1.setTypeOfCovenant("Drawer");
							obj1.setCondition1(cov[0]);
							obj1.setCondition2(cov[1]);
							obj1.setPartyId(fcubsObj.getPartyId());
							obj1.setPartyName(fcubsObj.getPartyName());
							obj1.setXrefId(String.valueOf(obCustomerSysXRef.getXRefID()));
							obj1.setFileName(fcubsObj.getFileName());
							obj1.setLiabilityId(fcubsObj.getLiabilityId());
							obj1.setSourceRefNo(fcubsObj.getSourceRefNo());
							obj1.setLineNo(fcubsObj.getLineNo());
							obj1.setSerialNumber(fcubsObj.getSerialNo());
							obj1.setFacilityId(String.valueOf(facilityId));
							
							
							obj.add(obj1);
						}
					}
					
					if(StringUtils.isNotBlank(singleCov.getBeneficiaryFlag() )) {
						ArrayList<String[]> covenantList = getComboListForCamCovenant(singleCov.getBeneficiaryFlag());
						for(String[] cov : covenantList){
							OBFCUBSReportDataLog obj1 = new OBFCUBSReportDataLog();
							obj1.setTypeOfCovenant("BeneFiciary");
							obj1.setCondition1(cov[0]);
							obj1.setCondition2(cov[1]);
							obj1.setPartyId(fcubsObj.getPartyId());
							obj1.setPartyName(fcubsObj.getPartyName());
							obj1.setXrefId(String.valueOf(obCustomerSysXRef.getXRefID()));
							obj1.setFileName(fcubsObj.getFileName());
							obj1.setLiabilityId(fcubsObj.getLiabilityId());
							obj1.setSourceRefNo(fcubsObj.getSourceRefNo());
							obj1.setLineNo(fcubsObj.getLineNo());
							obj1.setSerialNumber(fcubsObj.getSerialNo());
							obj1.setFacilityId(String.valueOf(facilityId));

							obj.add(obj1);
						}
					}
					
					String combinedTenorReq="";
					if(singleCov.getMaxCombinedTenor()!=null && StringUtils.isNotBlank(singleCov.getMaxCombinedTenor())) {
						long maxTenor=Long.parseLong(singleCov.getMaxCombinedTenor());
						if(maxTenor>0) {
							combinedTenorReq="Y";
						}else {
							combinedTenorReq="N";
						}
						
						OBFCUBSReportDataLog obj1 = new OBFCUBSReportDataLog();
						obj1.setTypeOfCovenant("Combined tenor");
						obj1.setCondition1(combinedTenorReq);
						obj1.setPartyId(fcubsObj.getPartyId());
						obj1.setPartyName(fcubsObj.getPartyName());
						obj1.setXrefId(String.valueOf(obCustomerSysXRef.getXRefID()));
						obj1.setFileName(fcubsObj.getFileName());
						obj1.setLiabilityId(fcubsObj.getLiabilityId());
						obj1.setSourceRefNo(fcubsObj.getSourceRefNo());
						obj1.setLineNo(fcubsObj.getLineNo());
						obj1.setSerialNumber(fcubsObj.getSerialNo());
						obj1.setFacilityId(String.valueOf(facilityId));
						
						obj.add(obj1);
					}
					
					
					if(singleCov.getModuleCode()!=null && StringUtils.isNotBlank(singleCov.getModuleCode())) {
						
						
						OBFCUBSReportDataLog obj1 = new OBFCUBSReportDataLog();
						obj1.setTypeOfCovenant("Mudule Code");
						obj1.setCondition1(singleCov.getModuleCode());
						obj1.setPartyId(fcubsObj.getPartyId());
						obj1.setPartyName(fcubsObj.getPartyName());
						obj1.setXrefId(String.valueOf(obCustomerSysXRef.getXRefID()));
						obj1.setFileName(fcubsObj.getFileName());
						obj1.setLiabilityId(fcubsObj.getLiabilityId());
						obj1.setSourceRefNo(fcubsObj.getSourceRefNo());
						obj1.setLineNo(fcubsObj.getLineNo());
						obj1.setSerialNumber(fcubsObj.getSerialNo());
						obj1.setFacilityId(String.valueOf(facilityId));
						
						obj.add(obj1);
					}
					
					if(singleCov.getCommitmentTenor()!=null && StringUtils.isNotBlank(singleCov.getCommitmentTenor())) {
					
						
						OBFCUBSReportDataLog obj1 = new OBFCUBSReportDataLog();
						obj1.setTypeOfCovenant("Commitment tenor");
						obj1.setCondition1(singleCov.getCommitmentTenor());
						obj1.setPartyId(fcubsObj.getPartyId());
						obj1.setPartyName(fcubsObj.getPartyName());
						obj1.setXrefId(String.valueOf(obCustomerSysXRef.getXRefID()));
						obj1.setFileName(fcubsObj.getFileName());
						obj1.setLiabilityId(fcubsObj.getLiabilityId());
						obj1.setSourceRefNo(fcubsObj.getSourceRefNo());
						obj1.setLineNo(fcubsObj.getLineNo());
						obj1.setSerialNumber(fcubsObj.getSerialNo());
						obj1.setFacilityId(String.valueOf(facilityId));
						
						obj.add(obj1);
					}
					
					if(singleCov.getOrderBackedbylc()!=null && StringUtils.isNotBlank(singleCov.getOrderBackedbylc())) {
						
						
						OBFCUBSReportDataLog obj1 = new OBFCUBSReportDataLog();
						obj1.setTypeOfCovenant("Order Backed");
						obj1.setCondition1(singleCov.getOrderBackedbylc());
						obj1.setPartyId(fcubsObj.getPartyId());
						obj1.setPartyName(fcubsObj.getPartyName());
						obj1.setXrefId(String.valueOf(obCustomerSysXRef.getXRefID()));
						obj1.setFileName(fcubsObj.getFileName());
						obj1.setLiabilityId(fcubsObj.getLiabilityId());
						obj1.setSourceRefNo(fcubsObj.getSourceRefNo());
						obj1.setLineNo(fcubsObj.getLineNo());
						obj1.setSerialNumber(fcubsObj.getSerialNo());
						obj1.setFacilityId(String.valueOf(facilityId));
						
						obj.add(obj1);
					}
					
					if(singleCov.getRunningAccount()!=null && StringUtils.isNotBlank(singleCov.getRunningAccount())) {
					
						
						OBFCUBSReportDataLog obj1 = new OBFCUBSReportDataLog();
						obj1.setTypeOfCovenant("Running Account");
						obj1.setCondition1(singleCov.getRunningAccount());
						obj1.setPartyId(fcubsObj.getPartyId());
						obj1.setPartyName(fcubsObj.getPartyName());
						obj1.setXrefId(String.valueOf(obCustomerSysXRef.getXRefID()));
						obj1.setFileName(fcubsObj.getFileName());
						obj1.setLiabilityId(fcubsObj.getLiabilityId());
						obj1.setSourceRefNo(fcubsObj.getSourceRefNo());
						obj1.setLineNo(fcubsObj.getLineNo());
						obj1.setSerialNumber(fcubsObj.getSerialNo());
						obj1.setFacilityId(String.valueOf(facilityId));
						
						obj.add(obj1);
					}
					
					if(singleCov.getMoratoriumPeriod()!=null && StringUtils.isNotBlank(singleCov.getMoratoriumPeriod())) {
												
						OBFCUBSReportDataLog obj1 = new OBFCUBSReportDataLog();
						obj1.setTypeOfCovenant("Moratorium Period");
						obj1.setCondition1(combinedTenorReq);
						obj1.setPartyId(fcubsObj.getPartyId());
						obj1.setPartyName(fcubsObj.getPartyName());
						obj1.setXrefId(String.valueOf(obCustomerSysXRef.getXRefID()));
						obj1.setFileName(fcubsObj.getFileName());
						obj1.setLiabilityId(fcubsObj.getLiabilityId());
						obj1.setSourceRefNo(fcubsObj.getSourceRefNo());
						obj1.setLineNo(fcubsObj.getLineNo());
						obj1.setSerialNumber(fcubsObj.getSerialNo());
						obj1.setFacilityId(String.valueOf(facilityId));
						
						obj.add(obj1);
					}
					
					if(singleCov.getEmiFrequency()!=null && StringUtils.isNotBlank(singleCov.getEmiFrequency())) {
						
						OBFCUBSReportDataLog obj1 = new OBFCUBSReportDataLog();
						obj1.setTypeOfCovenant("EMI Frequency");
						obj1.setCondition1(singleCov.getEmiFrequency().substring(0,1));
						obj1.setPartyId(fcubsObj.getPartyId());
						obj1.setPartyName(fcubsObj.getPartyName());
						obj1.setXrefId(String.valueOf(obCustomerSysXRef.getXRefID()));
						obj1.setFileName(fcubsObj.getFileName());
						obj1.setLiabilityId(fcubsObj.getLiabilityId());
						obj1.setSourceRefNo(fcubsObj.getSourceRefNo());
						obj1.setLineNo(fcubsObj.getLineNo());
						obj1.setSerialNumber(fcubsObj.getSerialNo());
						obj1.setFacilityId(String.valueOf(facilityId));
						
						obj.add(obj1);
					}
					
					if(singleCov.getNoOfInstallments()!=null && StringUtils.isNotBlank(singleCov.getNoOfInstallments())) {
						
						
						OBFCUBSReportDataLog obj1 = new OBFCUBSReportDataLog();
						obj1.setTypeOfCovenant("No. Of InstallMents");
						obj1.setCondition1(singleCov.getNoOfInstallments());
						obj1.setPartyId(fcubsObj.getPartyId());
						obj1.setPartyName(fcubsObj.getPartyName());
						obj1.setXrefId(String.valueOf(obCustomerSysXRef.getXRefID()));
						obj1.setFileName(fcubsObj.getFileName());
						obj1.setLiabilityId(fcubsObj.getLiabilityId());
						obj1.setSourceRefNo(fcubsObj.getSourceRefNo());
						obj1.setLineNo(fcubsObj.getLineNo());
						obj1.setSerialNumber(fcubsObj.getSerialNo());
						obj1.setFacilityId(String.valueOf(facilityId));
						
						obj.add(obj1);
					}
					
					if(singleCov.getEcgcCover()!=null && StringUtils.isNotBlank(singleCov.getEcgcCover())) {
						
						OBFCUBSReportDataLog obj1 = new OBFCUBSReportDataLog();
						obj1.setTypeOfCovenant("ECGC Cover");
						obj1.setCondition1(singleCov.getEcgcCover());
						obj1.setPartyId(fcubsObj.getPartyId());
						obj1.setPartyName(fcubsObj.getPartyName());
						obj1.setXrefId(String.valueOf(obCustomerSysXRef.getXRefID()));
						obj1.setFileName(fcubsObj.getFileName());
						obj1.setLiabilityId(fcubsObj.getLiabilityId());
						obj1.setSourceRefNo(fcubsObj.getSourceRefNo());
						obj1.setLineNo(fcubsObj.getLineNo());
						obj1.setSerialNumber(fcubsObj.getSerialNo());
						obj1.setFacilityId(String.valueOf(facilityId));
						
						obj.add(obj1);
					}
					
					if(singleCov.getBuyersRating()!=null && StringUtils.isNotBlank(singleCov.getBuyersRating())) {
						
						OBFCUBSReportDataLog obj1 = new OBFCUBSReportDataLog();
						obj1.setTypeOfCovenant("Buyers Rating");
						obj1.setCondition1(singleCov.getBuyersRating());
						obj1.setPartyId(fcubsObj.getPartyId());
						obj1.setPartyName(fcubsObj.getPartyName());
						obj1.setXrefId(String.valueOf(obCustomerSysXRef.getXRefID()));
						obj1.setFileName(fcubsObj.getFileName());
						obj1.setLiabilityId(fcubsObj.getLiabilityId());
						obj1.setSourceRefNo(fcubsObj.getSourceRefNo());
						obj1.setLineNo(fcubsObj.getLineNo());
						obj1.setSerialNumber(fcubsObj.getSerialNo());
						obj1.setFacilityId(String.valueOf(facilityId));
						
						obj.add(obj1);
					}
					
					if(singleCov.getPostShipmentLinkage()!=null && StringUtils.isNotBlank(singleCov.getPostShipmentLinkage())) {
						
						OBFCUBSReportDataLog obj1 = new OBFCUBSReportDataLog();
						obj1.setTypeOfCovenant("Post Shipment Linkage");
						obj1.setCondition1(singleCov.getPostShipmentLinkage());
						obj1.setPartyId(fcubsObj.getPartyId());
						obj1.setPartyName(fcubsObj.getPartyName());
						obj1.setXrefId(String.valueOf(obCustomerSysXRef.getXRefID()));
						obj1.setFileName(fcubsObj.getFileName());
						obj1.setLiabilityId(fcubsObj.getLiabilityId());
						obj1.setSourceRefNo(fcubsObj.getSourceRefNo());
						obj1.setLineNo(fcubsObj.getLineNo());
						obj1.setSerialNumber(fcubsObj.getSerialNo());
						obj1.setFacilityId(String.valueOf(facilityId));
						
						obj.add(obj1);
					}
					
				/*StringBuffer singleLineCovenant = new StringBuffer()  
							.append(FCUBS_FILE_COVENANT_SINGLE+",")
							.append(getDefaultEmpty(singleCov.getModuleCode())+",")
							.append(getDefaultEmpty(singleCov.getCommitmentTenor() )+",")
							.append(getDefaultEmpty(singleCov.getOrderBackedbylc())+",")
							.append(getDefaultEmpty(singleCov.getRunningAccount())+",")
							.append(getDefaultEmpty(singleCov.getMoratoriumPeriod() )+",")
							.append(StringUtils.isNotBlank(singleCov.getEmiFrequency())?singleCov.getEmiFrequency().substring(0,1)+",": StringUtils.EMPTY +",")
							.append(getDefaultEmpty(singleCov.getNoOfInstallments())+",")
							.append(getDefaultEmpty(singleCov.getEcgcCover() )+",")
							.append(getDefaultEmpty(singleCov.getBuyersRating())+",")
							.append(getDefaultEmpty(singleCov.getPostShipmentLinkage())+",")
							.append(getDefaultEmpty(combinedTenorReq)+",")
							.append(getDefaultEmpty(singleCov.getMaxCombinedTenor())+",^,");
						out.write(String.valueOf(singleLineCovenant));
						out.newLine();*/
					
					if(StringUtils.isNotBlank(singleCov.getRunningAccountFlag()) && ((StringUtils.isNotBlank(singleCov.getRunningAccount()) && singleCov.getRunningAccount().equals("Y"))
							||(StringUtils.isNotBlank(singleCov.getOrderBackedbylc()) && singleCov.getOrderBackedbylc().equals("Y"))
							)) {
						ArrayList<String[]> covenantList = getComboListForCamCovenant(singleCov.getRunningAccountFlag().trim());
						for(String[] cov : covenantList){
							OBFCUBSReportDataLog obj1 = new OBFCUBSReportDataLog();
							obj1.setTypeOfCovenant("Running Account");
							obj1.setCondition1(cov[0]);
							obj1.setCondition2(cov[1]);

							obj1.setPartyId(fcubsObj.getPartyId());
							obj1.setPartyName(fcubsObj.getPartyName());
							obj1.setXrefId(String.valueOf(obCustomerSysXRef.getXRefID()));
							obj1.setFileName(fcubsObj.getFileName());
							obj1.setLiabilityId(fcubsObj.getLiabilityId());
							obj1.setSourceRefNo(fcubsObj.getSourceRefNo());
							obj1.setLineNo(fcubsObj.getLineNo());
							obj1.setSerialNumber(fcubsObj.getSerialNo());
							obj1.setFacilityId(String.valueOf(facilityId));
							
							obj.add(obj1);
							
						}
					}
						
					if(StringUtils.isNotBlank(singleCov.getGoodsRestrictionFlag())) {
						ArrayList<String[]> covenantList = getComboListForGoods(singleCov.getGoodsRestrictionFlag().trim());
						for(String[] cov : covenantList){
							OBFCUBSReportDataLog obj1 = new OBFCUBSReportDataLog();
							obj1.setTypeOfCovenant("Goods Restrictions");
							obj1.setCondition1(cov[0]);
							obj1.setCondition2(cov[1]);
							obj1.setPartyId(fcubsObj.getPartyId());
							obj1.setPartyName(fcubsObj.getPartyName());
							obj1.setXrefId(String.valueOf(obCustomerSysXRef.getXRefID()));
							obj1.setFileName(fcubsObj.getFileName());
							obj1.setLiabilityId(fcubsObj.getLiabilityId());
							obj1.setSourceRefNo(fcubsObj.getSourceRefNo());
							obj1.setLineNo(fcubsObj.getLineNo());
							obj1.setSerialNumber(fcubsObj.getSerialNo());
							obj1.setFacilityId(String.valueOf(facilityId));

							obj.add(obj1);
						}
					}
					
					if(StringUtils.isNotBlank(singleCov.getCurrencyRestrictionFlag())) {
						ArrayList<String[]> covenantList = getComboListForCamCovenant(singleCov.getCurrencyRestrictionFlag());
						for(String[] cov : covenantList){
							OBFCUBSReportDataLog obj1 = new OBFCUBSReportDataLog();
							obj1.setTypeOfCovenant("Currency Restrictions");
							obj1.setCondition1(cov[0]);
							obj1.setCondition2(cov[1]);
							obj1.setPartyId(fcubsObj.getPartyId());
							obj1.setPartyName(fcubsObj.getPartyName());
							obj1.setXrefId(String.valueOf(obCustomerSysXRef.getXRefID()));
							obj1.setFileName(fcubsObj.getFileName());
							obj1.setLiabilityId(fcubsObj.getLiabilityId());
							obj1.setSourceRefNo(fcubsObj.getSourceRefNo());
							obj1.setLineNo(fcubsObj.getLineNo());
							obj1.setSerialNumber(fcubsObj.getSerialNo());
							obj1.setFacilityId(String.valueOf(facilityId));

							obj.add(obj1);
						}
					}
					
					if(StringUtils.isNotBlank(singleCov.getBankRestrictionFlag())) {
						ArrayList<String[]> covenantList = getComboListForCamCovenant(singleCov.getBankRestrictionFlag());
						for(String[] cov : covenantList){
							OBFCUBSReportDataLog obj1 = new OBFCUBSReportDataLog();
							obj1.setTypeOfCovenant("Bank Restrictions");
							obj1.setCondition1(cov[0]);
							obj1.setCondition2(cov[1]);

							obj1.setPartyId(fcubsObj.getPartyId());
							obj1.setPartyName(fcubsObj.getPartyName());
							obj1.setXrefId(String.valueOf(obCustomerSysXRef.getXRefID()));
							obj1.setFileName(fcubsObj.getFileName());
							obj1.setLiabilityId(fcubsObj.getLiabilityId());
							obj1.setSourceRefNo(fcubsObj.getSourceRefNo());
							obj1.setLineNo(fcubsObj.getLineNo());
							obj1.setSerialNumber(fcubsObj.getSerialNo());
							obj1.setFacilityId(String.valueOf(facilityId));
							
							obj.add(obj1);
						}
					}
				}
			}
		
		
		

		return obj;

	}
	

	public boolean isSFTPAvailable() throws Exception{
		DefaultLogger.debug(this, "FCUBSLimitFileUploadJob isSFTPAvailable()....");
		CMSFtpClient sftpClient = CMSFtpClient.getInstance("ofa",ICMSConstant.FCUBSLIMIT_FILE_UPLOAD);
		boolean isSFTPAvailable=sftpClient.openConnection();
		sftpClient.closeConnection();
		DefaultLogger.debug(this, "FCUBSLimitFileUploadJob isSFTPAvailable():SFTP connection opened(): "+isSFTPAvailable);
		System.out.println("FCUBSLimitFileUploadJob isSFTPAvailable():SFTP connection opened(): "+isSFTPAvailable);
		return isSFTPAvailable;
	}
	
	public void moveFailedFile(File sourceFile, String fileName) {
		DefaultLogger.debug(this, "FCUBSLimitFileUploadJob moveFailedFile()....");
		FileInputStream inputStream = null;
		FileOutputStream outstream = null;
		try {
			inputStream = new FileInputStream(sourceFile);
			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			String destPath = bundle.getString(FTP_FCUBS_BACKUP_LOCAL_DIR);
			File destFolderPath = new File(destPath);
			if (!destFolderPath.exists()) {
				destFolderPath.mkdirs();
			}
			fileName="FAILED_"+fileName;
			outstream = new FileOutputStream(destPath + fileName);
			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = inputStream.read(buffer)) > 0) {
				outstream.write(buffer, 0, length);
			}

		} catch (Exception e) {
			System.out.println("Exception in moveFailedFile FCUBSLimitFileUploadJob 1444 line.=>e=>"+e);
			e.printStackTrace();

		} finally {
			try {
				if (inputStream != null)
					inputStream.close();

				if (outstream != null)
					outstream.close();

				boolean delete = sourceFile.delete();
				if (delete == false) {
					DefaultLogger.debug(this, "In the moveFailedFile()...file  deletion failed for file:" + sourceFile.getPath());
					System.out.println("In the moveFailedFile()...file  deletion failed for file:" + sourceFile.getPath());
				}
			} catch (Exception e) {
				System.out.println("Exception in moveFailedFile FCUBSLimitFileUploadJob.=>e=>"+e);
				e.printStackTrace();
			}
		}

	}
}
