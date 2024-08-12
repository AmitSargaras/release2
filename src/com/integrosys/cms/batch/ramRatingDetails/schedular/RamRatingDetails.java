package com.integrosys.cms.batch.ramRatingDetails.schedular;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBRamRatingFileUpload;
import com.integrosys.cms.app.fileUpload.bus.RamRating;
import com.integrosys.cms.app.fileUpload.proxy.IFileUploadProxyManager;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.ftp.FileUploadFtpService;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;
import com.integrosys.cms.batch.fcubsLimitFile.schedular.FCUBSLimitFileDownloadJob;

public class RamRatingDetails {
	
	public static void main(String[] args) {
		new RamRatingDetails().execute();
	}

	public RamRatingDetails() {
	}
	
	private IFileUploadProxyManager fileUploadProxy;
	public static final String UBS_UPLOAD = "UbsUpload";

	public IFileUploadProxyManager getFileUploadProxy() {
		return fileUploadProxy;
	}
	public void setFileUploadProxy(IFileUploadProxyManager fileUploadProxy) {
		this.fileUploadProxy = fileUploadProxy;
	}
	
	
	ArrayList resultList=new ArrayList();
	ArrayList totalUploadedList=new ArrayList();
	int countPass =0;
	int countFail =0;
	
	
	public void execute() {	
		ResourceBundle bundle1 = ResourceBundle.getBundle("ofa");
		String ramRatingServerName = bundle1.getString("ram.rating.server.name");
		System.out.println("<<<<RamRatingDetailsJob Strating....>>>>"+ramRatingServerName);
		if(null!= ramRatingServerName && ramRatingServerName.equalsIgnoreCase("app1")){
			try {
			OBTrxContext ctx = new OBTrxContext();
			IFileUploadTrxValue trxValueOut = new OBFileUploadTrxValue();
			trxValueOut.setTransactionSubType("RAM_RATING_UPLOAD");
			IFileUploadJdbc jdbc=(IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
			HashMap mp = new HashMap();
			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			FileUploadFtpService fileUploadFtpService = new FileUploadFtpService();
			fileUploadFtpService.downlodUploadedFiles("RAM_RATING");
			System.out.println("<<<<In execute() of ramRatingDetails.....after downlodUploadedFiles()");
			List finalList = new ArrayList();
			HashMap eachDataMap = new HashMap();
			IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
			IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
			IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
			Date applicationDate=new Date();
			for(int i=0;i<generalParamEntries.length;i++){
				if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
					applicationDate=new Date(generalParamEntries[i].getParamValue());
				}
			}
			DateFormat df = new SimpleDateFormat("ddMMyyyy");
			String checkDate=df.format(applicationDate);
			
			File path=new File(bundle.getString("ftp.download.ramrating.local.dir"));
			path.getName();
			File list[]=path.listFiles();
			String fileName=null;
			for(int i=list.length-1;i>=0;i--){ 
				File sortFile=list[i];
				if((sortFile.getName().contains("RAM_Rating_"+checkDate) 
						&& (sortFile.getName().endsWith(".xlsx") ||  sortFile.getName().endsWith(".XLSX")))){
					System.out.println("<<<<<RAM_RATING uploadRamRatingDetails()>>>>>>"+sortFile.getName());//fileInfo.add(list[i]);
					ProcessDataFile dataFile = new ProcessDataFile();
					resultList = dataFile.processFileUpload(sortFile, "RAMRATING");
					System.out.println("<<<<<RAM_RATING uploadRamRatingDetails()>>>>>>"+resultList.size());
					fileName = sortFile.getName();
					
					if(resultList.size()>0){
						mp = (HashMap)jdbc.insertRAMRatingfile(resultList, this,fileName,"System",applicationDate);
					}
				}
			}
			System.out.println("<<<<RamRatingDetailsJob Ending....>>>>");
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
