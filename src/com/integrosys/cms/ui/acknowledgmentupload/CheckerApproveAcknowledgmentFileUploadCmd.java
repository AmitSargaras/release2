package com.integrosys.cms.ui.acknowledgmentupload;

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
import com.integrosys.cms.app.fileUpload.bus.OBAcknowledgmentFile;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.acknowledgmentupload.proxy.IAcknowledgmentUploadProxyManager;
import com.integrosys.component.user.app.bus.ICommonUser;

public class CheckerApproveAcknowledgmentFileUploadCmd extends AbstractCommand implements ICommonEventConstant{
	
	public CheckerApproveAcknowledgmentFileUploadCmd(){		
	}

	private IAcknowledgmentUploadProxyManager acknowledgmentuploadProxy;
	

	public IAcknowledgmentUploadProxyManager getAcknowledgmentuploadProxy() {
		return acknowledgmentuploadProxy;
	}

	public void setAcknowledgmentuploadProxy(IAcknowledgmentUploadProxyManager acknowledgmentuploadProxy) {
		this.acknowledgmentuploadProxy = acknowledgmentuploadProxy;
	} 

	public String[][] getParameterDescriptor() {
		return (new String[][]{
				{"IFileUploadTrxValue", "com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"acknowledgmentList", "java.util.List", SERVICE_SCOPE},
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
		//	ArrayList errorList=new ArrayList();
			
			// File  Trx value
			IFileUploadTrxValue trxValueIn = (OBFileUploadTrxValue) map.get("IFileUploadTrxValue");
			long countPass =0;
			long countFail =0;
			String acknowledgmentPending="Party doesn't not exist";
			
			ICommonUser user=(ICommonUser)map.get(IGlobalConstant.USER);
			ArrayList acknowledgmentList=(ArrayList)map.get("acknowledgmentList");
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
//				String refreshStatus = fileUploadJdbc.spUploadTransaction("ACKNOWLEDGMENT");
				
				IFileUpload stgFile = trxValueIn.getStagingfileUpload();
				stgFile.setApproveBy(user.getEmployeeID());
				trxValueIn.setStagingfileUpload(stgFile);
				ctx.setCustomer(null); 
				ctx.setLimitProfile(null);
				trxValueOut = getAcknowledgmentuploadProxy().checkerApproveFileUpload(ctx, trxValueIn);
				long fileId = Long.parseLong(trxValueOut.getStagingReferenceID());
				if(null!=acknowledgmentList && null!=trxValueOut){
				for(int i=0; i<acknowledgmentList.size(); i++){
					OBAcknowledgmentFile oBAcknowledgmentFile = (OBAcknowledgmentFile)acknowledgmentList.get(i);
						oBAcknowledgmentFile.setFileId(fileId);
					totalUploadedList.add(oBAcknowledgmentFile);
				}
				}
				
				if(totalUploadedList.size()>0){
					for(int i=0;i<totalUploadedList.size();i++){
						OBAcknowledgmentFile oBAcknowledgmentFile=(OBAcknowledgmentFile)totalUploadedList.get(i);
						if(oBAcknowledgmentFile.getStatus().equals("PASS")){
							countPass++;
						}else if(oBAcknowledgmentFile.getStatus().equals("FAIL")){
							countFail++;
						}
					}
				}
				
				DefaultLogger.debug(this,"##################### totalUploadedList ############:: "+ totalUploadedList.size());
				int batchSize = 200;
				for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
					List<OBAcknowledgmentFile> batchList = totalUploadedList.subList(j,j + batchSize > totalUploadedList.size() ? totalUploadedList.size(): j + batchSize);
					jdbc.createEntireAcknowledgmentActualFile(batchList);
				}
				for (int j = 0; j < totalUploadedList.size(); j++) {
					OBAcknowledgmentFile obAcknowledgmentFile  = (OBAcknowledgmentFile) totalUploadedList.get(j);
					if(obAcknowledgmentFile.getStatus().equalsIgnoreCase("PASS") && obAcknowledgmentFile.getUploadStatus().equalsIgnoreCase("Y"))
					{	
					  jdbc.updateAcknowledgmentSecurity(obAcknowledgmentFile);
				    }   
				}

				DefaultLogger.debug(this,"########## File Data is dumped into Actual Table for Cersai Acknowledgment Upload##################:: ");
				

				
				DefaultLogger.debug(this,"spUpdateAcknowledgmentUpload started:");
				DefaultLogger.debug(this,"spUpdateAcknowledgmentUpload finished:");
				
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
