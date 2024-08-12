package com.integrosys.cms.ui.bulkudfupdateupload;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.customer.trx.OBCMSCustomerTrxValue;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadDao;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;

import com.integrosys.cms.app.fileUpload.bus.OBFileUpload;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.json.command.PrepareSendReceivePartyCommand;
import com.integrosys.cms.app.json.dao.ScmPartyDao;
import com.integrosys.cms.app.json.dto.IJsInterfaceLog;
import com.integrosys.cms.app.json.dto.OBJsInterfaceLog;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.bulkudfupdateupload.proxy.IBulkUDFUploadProxyManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.ecbf.counterparty.ClimesToECBFHelper;
import com.integrosys.component.user.app.bus.ICommonUser;

public class CheckerApproveBulkUDFFileUploadCmd extends AbstractCommand implements ICommonEventConstant{
	
  public static final String BULKUDF_UPLOAD = "BulkUDFUpload";
	
	private IBulkUDFUploadProxyManager bulkudfuploadProxy;
		
	
	/*IBulkUDFUploadProxyManager bulkudfuploadProxy = (IBulkUDFUploadProxyManager) BeanHouse.get("bulkudfuploadProxy");*/

	public CheckerApproveBulkUDFFileUploadCmd(){
		
	}
	
	public IBulkUDFUploadProxyManager getBulkudfuploadProxy() {
		return bulkudfuploadProxy;
	}

	public void setBulkudfuploadProxy(IBulkUDFUploadProxyManager bulkudfuploadProxy) {
		this.bulkudfuploadProxy = bulkudfuploadProxy;
	}

	
	public String[][] getParameterDescriptor() {
		return (new String[][]{
				{"IFileUploadTrxValue", "com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"bulkudflist", "java.util.List", SERVICE_SCOPE},
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
			String bulkUDFPending="Record doesn't not exist";
			
			ICommonUser user=(ICommonUser)map.get(IGlobalConstant.USER);
			ArrayList blkUDFList=(ArrayList)map.get("bulkudflist");
			OBFileUpload actualClone = new OBFileUpload();
			OBFileUpload actual = (OBFileUpload)trxValueIn.getFileUpload();
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
			
			//SCM Interface & ECBF
			
			ICMSCustomerTrxValue trxValueInSCM = (OBCMSCustomerTrxValue) map.get("ICMSCustomerTrxValue");
			
	//		ICMSCustomer stageCustomer = (OBCMSCustomer) trxValueInSCM.getStagingCustomer();
			IJsInterfaceLog log = new OBJsInterfaceLog();
			ScmPartyDao scmPartyDao = (ScmPartyDao)BeanHouse.get("scmPartyDao");	
			
		
				IFileUploadJdbc fileUploadJdbc = (IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
			//	String refreshStatus = fileUploadJdbc.spUploadTransaction("ACKNOWLEDGMENT");
				
			/*	IFileUpload stgFile = trxValueIn.getFileUpload();
				stgFile.setApproveBy(user.getEmployeeID());
				trxValueIn.setStagingfileUpload(stgFile);
				ctx.setCustomer(null); 
				ctx.setLimitProfile(null);*/
				trxValueOut = getBulkudfuploadProxy().checkerApproveFileUpload(ctx, trxValueIn);
				long fileId = Long.parseLong(trxValueOut.getStagingReferenceID());
				if(null!=blkUDFList && null!=trxValueOut){
				for(int i=0; i<blkUDFList.size(); i++){
					OBTempBulkUDFFileUpload obj = (OBTempBulkUDFFileUpload)blkUDFList.get(i);
					obj.setFileId(fileId);
					totalUploadedList.add(obj);
				}
				}
				
				if(totalUploadedList.size()>0){
					for(int i=0;i<totalUploadedList.size();i++){
						OBTempBulkUDFFileUpload obj = (OBTempBulkUDFFileUpload)totalUploadedList.get(i);
						if(obj.getStatus().equals("PASS")){
							countPass++;
						}else if(obj.getStatus().equals("FAIL")){
							countFail++;
						}
					}
				}
				
				DefaultLogger.debug(this,"##################### totalUploadedList ############:: "+ totalUploadedList.size());
				int batchSize = 200;
				for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
					List<OBTempBulkUDFFileUpload> batchList = totalUploadedList.subList(j,j + batchSize > totalUploadedList.size() ? totalUploadedList.size(): j + batchSize);
					System.out.println(batchList);
					
					PrepareSendReceivePartyCommand scmWsCall = new PrepareSendReceivePartyCommand();
				List<OBTempBulkUDFFileUpload> nw=	jdbc.updateStagingCompBulkUDFTemp();
				//Party
				for (int k = 0; k < totalUploadedList.size(); k++) {
				if("PASS".equals(nw.get(k).getStatus()) && "Y".equals(nw.get(k).getUploadStatus())) {
				if(nw.get(k).getTypeOfUDF().toString().equalsIgnoreCase("PARTY") 
						&& nw.get(k).getValid().equals("TRUE") && nw.get(k).getRemarks().equals("APPROVED"))
				{
					jdbc.updateTempToStageBulkUdfParty(nw.get(k), nw.get(k).getUdfFieldSequence().toString());
					
					//For SCF
					
					if((nw.get(k).getUdfFieldSequence().toString()).equals(97))
					{
					String custId=nw.get(k).getCMS_LE_MAIN_PROFILE_ID().toString();
					String mainScmFlag = scmPartyDao.getBorrowerScmFlag(custId);
					String stgScmFlag = scmPartyDao.getStgBorrowerScmFlag(trxValueInSCM.getStagingReferenceID());
						log.setIs_udf_upload("Bulk_Udf_Upload");
						
						if(mainScmFlag.equalsIgnoreCase("Yes")&&stgScmFlag.equalsIgnoreCase("Yes")) {
							scmWsCall.scmWebServiceCall(custId, "U", "Y",log);
						}else if(mainScmFlag.equalsIgnoreCase("No")&&stgScmFlag.equalsIgnoreCase("Yes")) {
							scmWsCall.scmWebServiceCall(custId, "U","Y", log);
						}else if(mainScmFlag.equalsIgnoreCase("Yes")&&stgScmFlag.equalsIgnoreCase("No")) {
							scmWsCall.scmWebServiceCall(custId, "U","N", log);
					}
					}
						
					//For ECBF
					if((nw.get(k).getUdfFieldSequence().toString()).equals(98))
					{
						try {
							ClimesToECBFHelper.sendRequest(nw.get(k).getPartyID());
						} catch (Exception e) {
							e.printStackTrace();
							DefaultLogger.error(this, "Exception caught inside sendRequest while sending data to ecbf party webservice with error: " + e.getMessage(), e);
						}
					}
				}
				//CAM
				else if(nw.get(k).getTypeOfUDF().toString().equalsIgnoreCase("CAM")
						&& nw.get(k).getValid().equals("TRUE") && nw.get(k).getRemarks().equals("APPROVED"))
				{
					jdbc.updateTempToStageBulkUdfCam(nw.get(k), nw.get(k).getUdfFieldSequence().toString());
				}
				//Limit
				else if(nw.get(k).getTypeOfUDF().toString().equalsIgnoreCase("LIMIT")
						&& nw.get(k).getValid().equals("TRUE") && nw.get(k).getRemarks().equals("APPROVED"))
				{
					jdbc.updateTempToStageBulkUdfLimits(nw.get(k), nw.get(k).getUdfFieldSequence().toString());
					//updateLineAsPendingIfSystemUBS
					jdbc.updateLineAsPendingIfSystemUbs(nw);
					//For SCM
					if((nw.get(k).getUdfFieldSequence().toString()).equals(97))
					{
					String custId=nw.get(k).getCMS_LE_MAIN_PROFILE_ID().toString();
					String mainScmFlag = scmPartyDao.getBorrowerScmFlag(custId);
					String stgScmFlag = scmPartyDao.getStgBorrowerScmFlag(trxValueInSCM.getStagingReferenceID());
					log.setIs_udf_upload("Bulk_Udf_Upload");
					
					if(mainScmFlag.equalsIgnoreCase("Yes")&&stgScmFlag.equalsIgnoreCase("Yes")) {
							scmWsCall.scmWebServiceCall(custId, "U", "Y",log);
						}else if(mainScmFlag.equalsIgnoreCase("No")&&stgScmFlag.equalsIgnoreCase("Yes")) {
							scmWsCall.scmWebServiceCall(custId, "U","Y", log);
						}else if(mainScmFlag.equalsIgnoreCase("Yes")&&stgScmFlag.equalsIgnoreCase("No")) {
							scmWsCall.scmWebServiceCall(custId, "U","N", log);
					}
					}
					
				}
				}}
			}

				DefaultLogger.debug(this,"########## File Data is dumped into Actual Table for Bulk UDF UPLOAD Upload##################:: ");
				
				
				countFail=totalUploadedList.size()-countPass;
				
				resultMap.put("fileType", "CSV");
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
