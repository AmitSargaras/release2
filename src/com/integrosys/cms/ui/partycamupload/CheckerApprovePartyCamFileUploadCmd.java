package com.integrosys.cms.ui.partycamupload;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.chktemplate.bus.OBDocumentItem;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.fileUpload.bus.IFileUpload;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadDao;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.bus.OBFileUpload;
import com.integrosys.cms.app.fileUpload.bus.OBPartyCamFile;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.partycamupload.proxy.IPartyCamUploadProxyManager;
import com.integrosys.component.user.app.bus.ICommonUser;

public class CheckerApprovePartyCamFileUploadCmd extends AbstractCommand implements ICommonEventConstant{
	
	public CheckerApprovePartyCamFileUploadCmd(){		
	}

	private IPartyCamUploadProxyManager partyCamuploadProxy;
	

	public IPartyCamUploadProxyManager getPartyCamuploadProxy() {
		return partyCamuploadProxy;
	}

	public void setPartyCamuploadProxy(IPartyCamUploadProxyManager partyCamuploadProxy) {
		this.partyCamuploadProxy = partyCamuploadProxy;
	} 

	public String[][] getParameterDescriptor() {
		return (new String[][]{
				{"IFileUploadTrxValue", "com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"partyCamList", "java.util.List", SERVICE_SCOPE},
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{"remarks", "java.lang.String", REQUEST_SCOPE}
				
		}		);
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
				//{ "errorList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "fileType", "java.lang.String", REQUEST_SCOPE },
		}
		);
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap chkMap = new HashMap();
		Set<String> dataFromPartyCamUploadMv=new HashSet<String>();
		String nonfundAmt = null;
		
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ArrayList totalUploadedList=new ArrayList();
		//	ArrayList errorList=new ArrayList();
			
			// File  Trx value
			IFileUploadTrxValue trxValueIn = (OBFileUploadTrxValue) map.get("IFileUploadTrxValue");
			long countPass =0;
			long countFail =0;
			String partyCamPending="Party doesn't not exist OR Party or CAM are pending for authorization.";
			
			ICommonUser user=(ICommonUser)map.get(IGlobalConstant.USER);
			ArrayList partyCamList=(ArrayList)map.get("partyCamList");
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
			
			DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
			String appDate=df.format(applicationDate);
			
			Date d = DateUtil.getDate();
			applicationDate.setHours(d.getHours());
			applicationDate.setMinutes(d.getMinutes());
			applicationDate.setSeconds(d.getSeconds());
			IFileUploadDao dao=(IFileUploadDao)BeanHouse.get("fileUploadDao");
			IFileUploadJdbc jdbc=(IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
			
	
			//	ArrayList totalUploadedFileList=new ArrayList();
				IFileUploadJdbc fileUploadJdbc = (IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
				String refreshStatus = fileUploadJdbc.spUploadTransaction("PARTYCAM");
				if(null!= refreshStatus && refreshStatus.equalsIgnoreCase("1")){
					dataFromPartyCamUploadMv = fileUploadJdbc.cacheDataFromPartyCamUploadMV("PARTY_CAM_UPLOAD_MV");
					
				}
				
				IFileUpload stgFile = trxValueIn.getStagingfileUpload();
				stgFile.setApproveBy(user.getEmployeeID());
				trxValueIn.setStagingfileUpload(stgFile);
				ctx.setCustomer(null); 
				ctx.setLimitProfile(null);
				trxValueOut = getPartyCamuploadProxy().checkerApproveFileUpload(ctx, trxValueIn);
				long fileId = Long.parseLong(trxValueOut.getReferenceID());
				if(null!=partyCamList && null!=trxValueOut){
				for(int i=0; i<partyCamList.size(); i++){
					OBPartyCamFile oBPartyCamFile = (OBPartyCamFile)partyCamList.get(i);
						oBPartyCamFile.setFileId(fileId);
						nonfundAmt = jdbc.updatePartyfundStgtoMain(fileId,oBPartyCamFile.getPartyId(),oBPartyCamFile.getId());
						if(nonfundAmt!=null) {
						DefaultLogger.debug(this,"########## Non funded Data is updated for file id for main ##################:: "+oBPartyCamFile.getFileId());
						DefaultLogger.debug(this,"########## Non funded Data is updated for file id for stg ##################:: "+fileId);
						DefaultLogger.debug(this,"########## Non funded Data is updated for party id##################:: "+oBPartyCamFile.getPartyId());
						DefaultLogger.debug(this,"########## Non funded Data is updated for Id ##################:: "+oBPartyCamFile.getId());
						DefaultLogger.debug(this,"########## Non funded Data is updated for nonfundAmt##################:: "+nonfundAmt);
						oBPartyCamFile.setNonfundedAmount(nonfundAmt);
						}
						if("PASS".equals(oBPartyCamFile.getStatus()) && "Y".equals(oBPartyCamFile.getUploadStatus())){
						if(!dataFromPartyCamUploadMv.contains(oBPartyCamFile.getPartyId())){
							oBPartyCamFile.setUploadStatus("N");
							oBPartyCamFile.setReason(partyCamPending);
							oBPartyCamFile.setStatus("FAIL");	
						}
					}
					totalUploadedList.add(oBPartyCamFile);
				}
				}
				
				if(totalUploadedList.size()>0){
					for(int i=0;i<totalUploadedList.size();i++){
						OBPartyCamFile oBPartyCamFile=(OBPartyCamFile)totalUploadedList.get(i);
						if(oBPartyCamFile.getStatus().equals("PASS")){
							countPass++;
						}else if(oBPartyCamFile.getStatus().equals("FAIL")){
							countFail++;
						}
					}
				}
				
				DefaultLogger.debug(this,"##################### totalUploadedList ############:: "+ totalUploadedList.size());
				int batchSize = 200;
				for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
					List<OBPartyCamFile> batchList = totalUploadedList.subList(j,j + batchSize > totalUploadedList.size() ? totalUploadedList.size(): j + batchSize);
					jdbc.createEntirePartyCamActualFile(batchList);
				}

				DefaultLogger.debug(this,"########## File Data is dumped into Actual Table for PARTY CAM Upload##################:: ");
				
				jdbc.updateUploadStatusToNull("SCI_LE_CRI");
				jdbc.updateUploadStatusToNull("SCI_LSP_LMT_PROFILE");
				
				DefaultLogger.debug(this,"spUpdatePartyCamUpload started:");
				
				//update ram rating statement after changing ram rating year.
				ILimitDAO limitDao = LimitDAOFactory.getDAO();
				Date dateApplication=new Date();
				for(int j=0;j<generalParamEntries.length;j++){
					if(generalParamEntries[j].getParamCode().equals("APPLICATION_DATE")){
						dateApplication=new Date(generalParamEntries[j].getParamValue());
					}
				}
				DateFormat formatter = new SimpleDateFormat("yyyy");
				if(null!=partyCamList && null!=trxValueOut){
					for(int i=0; i<partyCamList.size(); i++){
						OBPartyCamFile oBPartyCamFile = (OBPartyCamFile)partyCamList.get(i);
						if("PASS".equals(oBPartyCamFile.getStatus()) && "Y".equals(oBPartyCamFile.getUploadStatus())){
							if(null!=oBPartyCamFile.getRamRatingYear()) {
								String ramRatingYear=oBPartyCamFile.getRamRatingYear().toString();
								String limitProfileID=limitDao.getLimitProfileID(oBPartyCamFile.getPartyId());//Long.parseLong
								if(null!=limitProfileID && !"".equals(limitProfileID)) {
									String checkListId=limitDao.getChecklistId(Long.parseLong(limitProfileID));
									//Start Insert RAM statement for current year and update for previous year
									ILimitDAO limtDao = LimitDAOFactory.getDAO();
									String oldRamRatingYear=limitDao.getOldRAMYear(Long.parseLong(limitProfileID));
									String customerFyClosure=limtDao.getCustomerFyClosure(Long.parseLong(limitProfileID));
									Calendar cal = Calendar.getInstance();
									cal.setTime(dateApplication);
									cal.add(Calendar.MONTH, 8);
									Date newDate=cal.getTime();
							        String year= formatter.format(newDate);
							        if(ramRatingYear!=null && oldRamRatingYear!=null && !ramRatingYear.equals(oldRamRatingYear)) {
							        	int oldRAMYear=Integer.valueOf(oldRamRatingYear);
							        	int newRAMYear=Integer.valueOf(ramRatingYear);
							        	OBDocumentItem ramRatingChecklist = new OBDocumentItem();
							        	if(newRAMYear>oldRAMYear) {
							        		try {
							        			ramRatingChecklist=limtDao.getAllRamratingDocument();
							        			String docRefId=new SimpleDateFormat("yyyyMMdd").format(new Date())+limtDao.getDocSeqId();
							        			String docId=new SimpleDateFormat("yyyyMMdd").format(new Date())+limtDao.getDocSeqId();
							        			//Disable old RAM statement where status=Received
							        			limtDao.disableRAMChecklistDetails(checkListId);
							        			//update Ram statement status as Received
							        			limtDao.updateRAMChecklistDetails(checkListId);
							        			//create new Ram statement with pending status
							        			limtDao.insertRAMStatement(customerFyClosure,docId,ramRatingChecklist,checkListId,docRefId,ramRatingYear);
							        		} catch (Exception e) {
							        			e.printStackTrace();
							        		}
							        	}
							        }
							        //End Insert RAM statement for current year and update for previous year
							        //limitDao.updateChecklistDetails(checkListId,todayYear,ramRatingYear);
								}
							}
						}
					}
				}
				//End update ram rating statement after changing ram rating year.
				jdbc.spUpdatePartyCamUpload(String.valueOf(fileId),"ACTUAL");
				
				DefaultLogger.debug(this,"spUpdatePartyCamUpload finished:");
				
				countFail=totalUploadedList.size()-countPass;
				
				resultMap.put("fileType", "CSV");
				resultMap.put("totalUploadedList", totalUploadedList);
				resultMap.put("total", String.valueOf(totalUploadedList.size()));
				resultMap.put("correct", String.valueOf(countPass));
				resultMap.put("fail", String.valueOf(countFail));
				//	resultMap.put("errorList", errorList);
				resultMap.put("trxValueOut", trxValueOut);
		}catch (ComponentException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
