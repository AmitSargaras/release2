package com.integrosys.cms.app.leiDetailsUpload;

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
import com.integrosys.cms.app.fileUpload.bus.OBLeiDetailsFile;
import com.integrosys.cms.app.fileUpload.bus.OBLeiDetailsFile;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.leiDetailsUpload.proxy.ILeiDetailsUploadProxyManager;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

public class CheckerApproveLeiDetailsFileUploadCmd extends AbstractCommand implements ICommonEventConstant{
	
	public CheckerApproveLeiDetailsFileUploadCmd(){		
	}

	private ILeiDetailsUploadProxyManager leiDetailsUploadProxy;
	

	public ILeiDetailsUploadProxyManager getLeiDetailsUploadProxy() {
		return leiDetailsUploadProxy;
	}

	public void setLeiDetailsUploadProxy(ILeiDetailsUploadProxyManager leiDetailsUploadProxy) {
		this.leiDetailsUploadProxy = leiDetailsUploadProxy;
	} 

	public String[][] getParameterDescriptor() {
		return (new String[][]{
				{"IFileUploadTrxValue", "com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"leidetailsList", "java.util.List", SERVICE_SCOPE},
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
			String leiDetailsPending="Party doesn't not exist";
			
			ICommonUser user=(ICommonUser)map.get(IGlobalConstant.USER);
			ArrayList leiDetailsList=(ArrayList)map.get("leidetailsList");
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
			
			DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
			String appDate=df.format(applicationDate);
			
			Date d = DateUtil.getDate();
			applicationDate.setHours(d.getHours());
			applicationDate.setMinutes(d.getMinutes());
			applicationDate.setSeconds(d.getSeconds());
			IFileUploadDao dao=(IFileUploadDao)BeanHouse.get("fileUploadDao");
			IFileUploadJdbc jdbc=(IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
			
	
		
				IFileUploadJdbc fileUploadJdbc = (IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
//				String refreshStatus = fileUploadJdbc.spUploadTransaction("LEIDETAILS");
				
				IFileUpload stgFile = trxValueIn.getStagingfileUpload();
				stgFile.setApproveBy(user.getEmployeeID());
				trxValueIn.setStagingfileUpload(stgFile);
				ctx.setCustomer(null); 
				ctx.setLimitProfile(null);
				trxValueOut = getLeiDetailsUploadProxy().checkerApproveFileUpload(ctx, trxValueIn);
				long fileId = Long.parseLong(trxValueOut.getStagingReferenceID());
				if(null!=leiDetailsList && null!=trxValueOut){
				for(int i=0; i<leiDetailsList.size(); i++){
					OBLeiDetailsFile obLeiDetailsFile = (OBLeiDetailsFile)leiDetailsList.get(i);
					obLeiDetailsFile.setFileId(fileId);
					totalUploadedList.add(obLeiDetailsFile);
				}
				}
				
				if(totalUploadedList.size()>0){
					for(int i=0;i<totalUploadedList.size();i++){
						OBLeiDetailsFile obLeiDetailsFile=(OBLeiDetailsFile)totalUploadedList.get(i);
						if(obLeiDetailsFile.getStatus().equals("PASS")){
							countPass++;
						}else if(obLeiDetailsFile.getStatus().equals("FAIL")){
							countFail++;
						}
					}
				}
				
				DefaultLogger.debug(this,"##################### totalUploadedList ############:: "+ totalUploadedList.size());
				int batchSize = 200;
				for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
					List<OBLeiDetailsFile> batchList = totalUploadedList.subList(j,j + batchSize > totalUploadedList.size() ? totalUploadedList.size(): j + batchSize);
					jdbc.createEntireLeiDetailsActualFile(batchList);
				}
				for (int j = 0; j < totalUploadedList.size(); j++) {
					OBLeiDetailsFile obLeiDetailsFile  = (OBLeiDetailsFile) totalUploadedList.get(j);
					if(obLeiDetailsFile.getStatus().equalsIgnoreCase("PASS") && obLeiDetailsFile.getUploadStatus().equalsIgnoreCase("Y"))
					{	
						String isLeiValidated = jdbc.getIsLeiValidated(obLeiDetailsFile.getPartyId());
						String leiCode = jdbc.getLeiCode(obLeiDetailsFile.getPartyId());
						if(leiCode.equals(obLeiDetailsFile.getLeiCode().trim()) && isLeiValidated.equals("Y")) {
							jdbc.updateSubProfileforValidatedLei(obLeiDetailsFile);														
						}else {
							jdbc.updateSubProfile(obLeiDetailsFile);							
						}
						jdbc.updatePublicInputLEI(obLeiDetailsFile);
						jdbc.updateLeiValidationStatusforScheduler(obLeiDetailsFile.getPartyId(), obLeiDetailsFile.getLeiCode());
				    }   
				}

				DefaultLogger.debug(this,"########## File Data is dumped into Actual Table for LEI Details Upload##################:: ");
				

				
				DefaultLogger.debug(this,"spUpdateLeiDetailsUpload started:");
				DefaultLogger.debug(this,"spUpdateLeiDetailsUpload finished:");
				
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
