package com.integrosys.cms.ui.facilitydetailsupload;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadDao;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBFileUpload;
import com.integrosys.cms.app.fileUpload.bus.OBFacilitydetailsFile;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.facilitydetailsupload.proxy.IFacilitydetailsUploadProxyManager;
import com.integrosys.component.user.app.bus.ICommonUser;

public class CheckerApproveFacilitydetailsFileUploadCmd extends AbstractCommand implements ICommonEventConstant{
	
	public CheckerApproveFacilitydetailsFileUploadCmd(){		
	}

	private IFacilitydetailsUploadProxyManager facilitydetailsuploadProxy;
	

	public IFacilitydetailsUploadProxyManager getFacilitydetailsuploadProxy() {
		return facilitydetailsuploadProxy;
	}

	public void setFacilitydetailsuploadProxy(IFacilitydetailsUploadProxyManager facilitydetailsuploadProxy) {
		this.facilitydetailsuploadProxy = facilitydetailsuploadProxy;
	} 

	public String[][] getParameterDescriptor() {
		return (new String[][]{
				{"IFileUploadTrxValue", "com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"facilitydetailsList", "java.util.List", SERVICE_SCOPE},
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{"remarks", "java.lang.String", REQUEST_SCOPE}
				
		}
		);
	}

	public String[][] getResultDescriptor() {
		return (new String[][]{
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
				{ "total", "java.lang.String", REQUEST_SCOPE },
				{ "correct", "java.lang.String", REQUEST_SCOPE },
				{ "fail", "java.lang.String", REQUEST_SCOPE },
				{ "preUpload", "java.lang.String", REQUEST_SCOPE },
				{"trxValueOut", "com.integrosys.cms.ui.fileUpload.FileUploadAction.IFileUploadTrxValue", SERVICE_SCOPE},
				{ "totalUploadedList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "fileType", "java.lang.String", REQUEST_SCOPE },
		}
		);
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap chkMap = new HashMap();
		
		try {
			
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ArrayList totalUploadedList=new ArrayList();
			
			// File  Trx value
			IFileUploadTrxValue trxValueIn = (OBFileUploadTrxValue) map.get("IFileUploadTrxValue");
			long countPass =0;
			long countFail =0;
			String facilitydetailsPending="Party doesn't not exist";
			
			ICommonUser user=(ICommonUser)map.get(IGlobalConstant.USER);
			ArrayList facilitydetailsList=(ArrayList)map.get("facilitydetailsList");
//			ArrayList facilitydetailsErrorList=(ArrayList)map.get("errorList");
			OBFileUpload actualClone = new OBFileUpload();
			OBFileUpload stagingClone = new OBFileUpload();
			OBFileUpload actual = (OBFileUpload)trxValueIn.getFileUpload();
			OBFileUpload staging = (OBFileUpload)trxValueIn.getStagingfileUpload();
			IFileUploadTrxValue trxValueOut=null;
			String remarks = (String) map.get("remarks");
			
			IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
			IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
			IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
			Date applicationDate=new Date();
			for(int i=0;i<generalParamEntries.length;i++){
				if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
					applicationDate=new Date(generalParamEntries[i].getParamValue());
				}
			}
			
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			String appDate=df.format(applicationDate);
			
			Date d = DateUtil.getDate();
			applicationDate.setHours(d.getHours());
			applicationDate.setMinutes(d.getMinutes());
			applicationDate.setSeconds(d.getSeconds());
			IFileUploadDao dao=(IFileUploadDao)BeanHouse.get("fileUploadDao");
			IFileUploadJdbc jdbc=(IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
			
	
		
				IFileUploadJdbc fileUploadJdbc = (IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
//				String refreshStatus = fileUploadJdbc.spUploadTransaction("FACILITYDETAILS");
				
				IFileUpload stgFile = trxValueIn.getStagingfileUpload();
				stgFile.setApproveBy(user.getEmployeeID());
				trxValueIn.setStagingfileUpload(stgFile);
				ctx.setCustomer(null); 
				ctx.setLimitProfile(null);
				trxValueOut = getFacilitydetailsuploadProxy().checkerApproveFileUpload(ctx, trxValueIn);
				long fileId = Long.parseLong(trxValueOut.getStagingReferenceID());
				if(null!=facilitydetailsList && null!=trxValueOut){
				for(int i=0; i<facilitydetailsList.size(); i++){
					OBFacilitydetailsFile oBFacilitydetailsFile = (OBFacilitydetailsFile)facilitydetailsList.get(i);
						oBFacilitydetailsFile.setFileId(fileId);
					totalUploadedList.add(oBFacilitydetailsFile);
				}
				}
			
				
				if(totalUploadedList.size()>0){
					for(int i=0;i<totalUploadedList.size();i++){
						OBFacilitydetailsFile oBFacilitydetailsFile=(OBFacilitydetailsFile)totalUploadedList.get(i);
						if(oBFacilitydetailsFile.getStatus().equals("PASS")){
							countPass++;
						}else if(oBFacilitydetailsFile.getStatus().equals("FAIL")){
							countFail++;
						}
					}
				}
				
				DefaultLogger.debug(this,"##################### totalUploadedList ############:: "+ totalUploadedList.size());
				int batchSize = 200;
				for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
					List<OBFacilitydetailsFile> batchList = totalUploadedList.subList(j,j + batchSize > totalUploadedList.size() ? totalUploadedList.size(): j + batchSize);
					jdbc.createEntireFacilitydetailsActualFile(batchList);
				}
				
				//Update Staging Records
				
				for (int j = 0; j < totalUploadedList.size(); j++) {
					OBFacilitydetailsFile obFacilitydetailsFile = (OBFacilitydetailsFile) totalUploadedList.get(j);
					if (obFacilitydetailsFile.getStatus().equalsIgnoreCase("PASS") && obFacilitydetailsFile.getUploadStatus().equalsIgnoreCase("Y")) {
						
						if(obFacilitydetailsFile.getSanctionAmt() != null && !"".equals(obFacilitydetailsFile.getSanctionAmt())) {
						jdbc.updateSanctionAmt(obFacilitydetailsFile);
						}
						if(obFacilitydetailsFile.getReleasableAmt() != null && !"".equals(obFacilitydetailsFile.getReleasableAmt())) {
						jdbc.updateReleasableAmt(obFacilitydetailsFile);
						}
					}
				}
				
				//Update Actual Records
				
				for (int j = 0; j < totalUploadedList.size(); j++) {
					OBFacilitydetailsFile obFacilitydetailsFile  = (OBFacilitydetailsFile) totalUploadedList.get(j);
					if(obFacilitydetailsFile.getStatus().equalsIgnoreCase("PASS") && obFacilitydetailsFile.getUploadStatus().equalsIgnoreCase("Y"))
					{	
						if(obFacilitydetailsFile.getSanctionAmt() != null && !"".equals(obFacilitydetailsFile.getSanctionAmt())) {
							jdbc.updateSanctionAmtActual(obFacilitydetailsFile);
							}
							if(obFacilitydetailsFile.getReleasableAmt() != null && !"".equals(obFacilitydetailsFile.getReleasableAmt())) {
							jdbc.updateReleasableAmtActual(obFacilitydetailsFile);
							}
				    }   
				}

				DefaultLogger.debug(this,"########## File Data is dumped into Actual Table for  Release line details Upload##################:: ");
				

				
				DefaultLogger.debug(this,"spUpdateFacilitydetailsUpload started:");
				DefaultLogger.debug(this,"spUpdateFacilitydetailsUpload finished:");
				
				countFail=totalUploadedList.size()-countPass;
				
				resultMap.put("fileType", "EXCEL");
				resultMap.put("totalUploadedList", totalUploadedList);
				resultMap.put("total", String.valueOf(totalUploadedList.size()));
				resultMap.put("correct", String.valueOf(countPass));
				resultMap.put("fail", String.valueOf(countFail));
				resultMap.put("trxValueOut", trxValueOut);
		}catch(FileUploadException ex){
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
		} catch (TrxParameterException e) {
			throw (new CommandProcessingException(e.getMessage()));
		} catch (Exception e) { 
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
