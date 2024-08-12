/**
 * 
 */
package com.integrosys.cms.batch.exchangeRateUpload.scheduler;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.forex.OBForexFeedGroupTrxValue;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBExchangeRateAutoUpload;
import com.integrosys.cms.app.fileUpload.proxy.IFileUploadProxyManager;
import com.integrosys.cms.app.ftp.FileUploadFtpService;
import com.integrosys.cms.app.ftp.IFileUploadConstants;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;
import com.integrosys.cms.ui.common.CurrencyList;

/**
 * @author shrikant.kalyadapu
 *
 */
public class ExchangeRateUploadJob implements ICommonEventConstant,IFileUploadConstants,ICMSConstant{
	
	private IForexFeedProxy forexFeedProxy;
	public IForexFeedProxy getForexFeedProxy() {
		return forexFeedProxy;
	}
	public void setForexFeedProxy(IForexFeedProxy forexFeedProxy) {
		this.forexFeedProxy = forexFeedProxy;
	}
	
	private IFileUploadProxyManager fileUploadProxy;
	public IFileUploadProxyManager getFileUploadProxy() {
		return fileUploadProxy;
	}
	public void setFileUploadProxy(IFileUploadProxyManager fileUploadProxy) {
		this.fileUploadProxy = fileUploadProxy;
	}
	
	private static IFileUploadJdbc fileUploadJdbc = null;
	
	public static IFileUploadJdbc getFileUploadJdbc() {
		if (fileUploadJdbc == null) {
			fileUploadJdbc = (IFileUploadJdbc) BeanHouse.get("fileUploadJdbc");
        }
		return fileUploadJdbc;
	}
	
	private final static Logger logger = LoggerFactory.getLogger(ExchangeRateUploadJob.class);

	public static void main(String[] args) {

		new ExchangeRateUploadJob().execute();
	}
	
	public ExchangeRateUploadJob() {

	}
	
	/**
	 * This job is run and executed by quartz schedular.
	 * For more details refer to schedular configuration in 
	 * config/spring/batch/ubsFileUpload/AppContext_Master.xml
	 * Schedular has been designed to carry out the following activities
	 * 1. Download the file from remote location to local 
	 * 2. Read the file.
	 * 3. Store the records in the database.
	 */
	
	public void execute() {	
		forexFeedProxy = (IForexFeedProxy)BeanHouse.get("forexFeedProxy");
		System.out.println("Starting ExchangeRateUploadJob...........");
		logger.info("Starting ExchangeRateUploadJob...........");
		
		ResourceBundle bundle1 = ResourceBundle.getBundle("ofa");
		String autoUploadServerName = bundle1.getString("auto.upload.server.name");
		
		if(null!= autoUploadServerName && autoUploadServerName.equalsIgnoreCase("app1")){
			FileUploadFtpService fileUploadFtpService = new FileUploadFtpService();
			try {
				fileUploadFtpService.downlodUploadedFiles("EXCH");
			} catch (Exception e) {
				logger.info("Error occurred while file Download::"+e.getMessage());
			}
			try {
				processFile();
			} catch (Exception e) {
			logger.info("Error occurred while file parsing or data insert::"+e.getMessage());
			}
			logger.info("ENDING ExchangeRateUploadJob...........");
			System.out.println("ENDING ExchangeRateUploadJob...........");
		}
	}
	
	public void processFile(){
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
		IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
		IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
		Date applicationDate=new Date();
		for(int i=0;i<generalParamEntries.length;i++){
			if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
				applicationDate=new Date(generalParamEntries[i].getParamValue());
			}
		}
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		DateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
		String checkDate=df.format(applicationDate);
		
		Set<String> alreadyDownloadedFiles = getFileUploadJdbc().getAlreadyDownloadedExchangeReportFileNamesByBatch(applicationDate);
		
		File path=new File(bundle.getString(FTP_FILEUPLOAD_DOWNLOAD_EXCH_LOCAL_DIR));
		File list[]=path.listFiles();
		for(int i=list.length-1;i>=0;i--){ 
			File sortFile=list[i];
			if((sortFile.getName().contains(FTP_EXCH_FILE_NAME+checkDate)
					&& (sortFile.getName().endsWith(".csv") ||  sortFile.getName().endsWith(".CSV")))
					&& alreadyDownloadedFiles != null && !alreadyDownloadedFiles.contains(sortFile.getName())
				){
				HashMap map = new HashMap();
				List<String> currCodeList = new ArrayList<String>() ;
				String strError = "";
				ProcessDataFile dataFile = new ProcessDataFile();
				ArrayList resultList = dataFile.processFileUpload(list[i],EXCHANGE_RATE_UPLOAD);
				if(resultList == null || resultList.size() <= 0 || !dataFile.getErrorList().isEmpty() || dataFile.isValid()==false)
				{
					strError = "Invalid File Data or Empty File.";
				}
				OBTrxContext ctx = new OBTrxContext();
				boolean checkerInserted = false;
				IForexFeedGroupTrxValue trxValueOut = new OBForexFeedGroupTrxValue();
				if(dataFile.isCheckSumFooter()){
					strError ="Checksum Footer not matched";
				}else{
					//Check is Records available in System for Updates.
					
					if(resultList != null && resultList.size() > 0)
					for (int index = 0; index < resultList.size(); index++) {
						HashMap eachDataMap = (HashMap) resultList.get(index);
						String currCode = (String) eachDataMap.get("BUY_CURRENCY");
						currCodeList.add(currCode);
					}
					if(!currCodeList.isEmpty())
					{
						boolean validCode = false;
						try {
							validCode = getForexFeedProxy().isCurrencyCodeExist(currCodeList);
						} catch (Exception e) {
							e.printStackTrace();
							strError = "Exception occurred while checking valid currency code in DB";
						}
						if(!validCode){
							dataFile.setValid(false);
							strError ="CURRENCY CODES NOT FOUND IN DB";
						}
					}
				  //Insert a dummy fieldId (a random number) into a table in order to generate Transaction ID.
			    long fileId = (long)(Math.random()*1000000);
				OBFileMapperID obFileMapperID = new OBFileMapperID();
				obFileMapperID.setFileId(fileId);
				String userName = "";
			    if (ctx != null) {
					userName = "SYSTEM";
			    }
			    boolean makerInserted = false;
				if(dataFile.isValid() && strError.equals("") && resultList != null && resultList.size() >0 ){
						//create a record to get Transaction id
							OBFileMapperMaster obFileMapperMaster = new OBFileMapperMaster();
				    		try {
								//trxValueOut = getForexFeedProxy().makerInsertMapperForexFeedEntry(ctx, obFileMapperID);
								trxValueOut = getForexFeedProxy().makerInsertMapperForexFeedEntryAuto(ctx, obFileMapperID);
							} catch (Exception e1) {
								e1.printStackTrace();
								strError = "Exception occurred while inserting Transaction in DB";
							}
				    		//Set Transaction ID into Mapper Table
				    		obFileMapperMaster.setFileId(fileId);
				    		obFileMapperMaster.setTransId(trxValueOut.getTransactionID());
				    		//insert records in ForexFeedEntry staging table and store it's ID and transactionId in a master table 
				    		try {
							map = getForexFeedProxy().insertForexFeedEntryAuto(obFileMapperMaster, userName, resultList);
							if(map != null && map.size() > 0){
								makerInserted = true;
							}else{
								strError = "No Record Found in DB";
							}
							
						} catch (Exception e) {
							strError = "Exception occurred while inserting Feed in staging in DB";
							try {
								getForexFeedProxy().deleteTransaction(obFileMapperMaster);
							} catch (Exception e1) {
								e1.printStackTrace();
								strError = strError+ " - Exception occurred while deleting transaction from DB";
							} 
							
						}
						
						try{
							if(makerInserted)
							{
								ctx.setRemarks("System Approved");
								IForexFeedGroupTrxValue trxValueOut1 = getForexFeedProxy().checkerApproveInsertForexFeedEntry(ctx, trxValueOut);
								List listId = getForexFeedProxy().getFileMasterList(trxValueOut1.getTransactionID());
								OBFileMapperMaster obFileMapperMaster1 = new OBFileMapperMaster();
								List refForexFeedEntryList = null;
								for (int k = 0; k < listId.size(); k++) {
									obFileMapperMaster1 = (OBFileMapperMaster) listId.get(k);
									 String regStage = String.valueOf(obFileMapperMaster1.getSysId());
									 refForexFeedEntryList = getForexFeedProxy().insertActualForexFeedEntry(regStage);			
					    		}
								getForexFeedProxy().updateForexFeedEntryExchangeRateAuto(refForexFeedEntryList);
								strError = "Successfully Inserted";
								checkerInserted = true;
							}
						}catch(Exception e)
						{
							e.printStackTrace();
							strError = "Exception occurred while inserting Feed or transaction in main table in DB"; 
							try {
								getForexFeedProxy().deleteTransaction(obFileMapperMaster);
							} catch (Exception e1) {
								e1.printStackTrace();
								strError = strError+ " - Exception occurred while deleting transaction from DB";
							} 
						}
					}
				}
				DateFormat df1 = new SimpleDateFormat("HH:mm:ss");
				Calendar calobj = Calendar.getInstance();
				String time = df1.format(calobj.getTime());
				ArrayList<OBExchangeRateAutoUpload> repostList = new ArrayList<OBExchangeRateAutoUpload>();
				if(resultList!= null && !resultList.isEmpty())
				{
						for (int index = 0; index < resultList.size(); index++) {
							HashMap eachDataMap = (HashMap) resultList.get(index);
							if(eachDataMap != null && eachDataMap.size() > 0){
								OBExchangeRateAutoUpload auto = new OBExchangeRateAutoUpload();
								auto.setCurrency_code((String) eachDataMap.get("BUY_CURRENCY"));
								String sDate1=(String)eachDataMap.get("EFFECTIVE_DATE");
								try {
									Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
									java.sql.Date sqlStartDate = new java.sql.Date(date1.getTime());
									auto.setExchange_date(sqlStartDate);
								} catch (Exception e) {
									e.printStackTrace();
								}
								auto.setExchange_rate_new(new BigDecimal((String)eachDataMap.get("BUY_RATE")));
								auto.setFile_name(sortFile.getName());
								if(checkerInserted && map!= null && !map.isEmpty() && null != map.get((String) eachDataMap.get("BUY_CURRENCY")))
								{
									auto.setExchange_rate_old((BigDecimal) map.get((String) eachDataMap.get("BUY_CURRENCY")));
									auto.setUpload_status("SUCCESS");
									auto.setUpload_status_message(strError);
								}else if("".equals(strError) || "Successfully Inserted".equals(strError))
								{
									auto.setExchange_rate_old(new BigDecimal(0));
									auto.setUpload_status("FAIL");
									auto.setUpload_status_message("CURRENCY CODE NOT FOUND IN DB");
								}else
								{
									auto.setExchange_rate_old(new BigDecimal(0));
									auto.setUpload_status("FAIL");
									auto.setUpload_status_message(strError);
								}
								auto.setUpload_time(time);
								auto.setUpload_date(new java.sql.Date(applicationDate.getTime()));
								repostList.add(auto);
							}
						}
				}else
				{
							OBExchangeRateAutoUpload auto = new OBExchangeRateAutoUpload();
							auto.setFile_name(sortFile.getName());
							auto.setUpload_status("FAIL");
							auto.setUpload_status_message(strError);
							auto.setUpload_time(time);
							auto.setExchange_date(new java.sql.Date(applicationDate.getTime()));
							auto.setUpload_date(new java.sql.Date(applicationDate.getTime()));
							auto.setExchange_rate_old(new BigDecimal(0));
							auto.setExchange_rate_new(new BigDecimal(0));
							repostList.add(auto);
				}
				IFileUploadJdbc jdbc=(IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
				
				try {
					jdbc.insertReport(repostList);
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("Error occurred while report data insert::"+e.getMessage());
				} 
			}
			
			
		}
	}
	
	
}
