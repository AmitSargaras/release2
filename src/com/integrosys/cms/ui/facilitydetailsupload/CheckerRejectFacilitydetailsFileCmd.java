package com.integrosys.cms.ui.facilitydetailsupload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.fileUpload.bus.FileUploadException;
import com.integrosys.cms.app.fileUpload.bus.OBFacilitydetailsFile;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.facilitydetailsupload.proxy.IFacilitydetailsUploadProxyManager;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;

public class CheckerRejectFacilitydetailsFileCmd extends AbstractCommand implements	ICommonEventConstant{
	
	private IFacilitydetailsUploadProxyManager facilitydetailsuploadProxy;
	

	public IFacilitydetailsUploadProxyManager getFacilitydetailsuploadProxy() {
		return facilitydetailsuploadProxy;
	}

	public void setFacilitydetailsuploadProxy(IFacilitydetailsUploadProxyManager facilitydetailsuploadProxy) {
		this.facilitydetailsuploadProxy = facilitydetailsuploadProxy;
	} 

	/**
	 * Default Constructor
	 */
	public CheckerRejectFacilitydetailsFileCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][]{
				{"IFileUploadTrxValue", "com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"facilitydetailsList", "java.util.List", SERVICE_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE}
		}
		);
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][]{
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
				{"trxValueOut", "com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue", SERVICE_SCOPE},
				{"facilitydetailsList","java.util.List",SERVICE_SCOPE}
		}
		);
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();	
		
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			// File  Trx value
			IFileUploadTrxValue trxValueIn = (OBFileUploadTrxValue) map.get("IFileUploadTrxValue");
			List facilitydetailsList=(List)map.get("facilitydetailsList");
			List totalUploadedList=new ArrayList();
			IFileUploadJdbc jdbc=(IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
			if(null!=facilitydetailsList ){
				for(int i=0; i<facilitydetailsList.size(); i++){
					OBFacilitydetailsFile oBFacilitydetailsFile = (OBFacilitydetailsFile)facilitydetailsList.get(i);
					
					totalUploadedList.add(oBFacilitydetailsFile);
				}
			}
			
			DefaultLogger.debug(this,"##################### totalUploadedList ############:: "+ totalUploadedList.size());
			int batchSize = 200;
			for (int j = 0; j < totalUploadedList.size(); j += batchSize) {
				List<OBFacilitydetailsFile> batchList = totalUploadedList.subList(j,j + batchSize > totalUploadedList.size() ? totalUploadedList.size(): j + batchSize);
				jdbc.createEntireFacilitydetailsActualFile(batchList);
			}
			

			for (int j = 0; j < totalUploadedList.size(); j++) {
				OBFacilitydetailsFile obFacilitydetailsFile  = (OBFacilitydetailsFile) totalUploadedList.get(j);
					
				//  jdbc.createEntireFacilitydetailsActualFile(obFacilitydetailsFile);
			      
			}
			String remarks = (String) map.get("remarks");
			 
			if (null == remarks || remarks.isEmpty()) {
				exceptionMap.put("limitRemarksError", new ActionMessage("error.string.mandatory"));
				resultMap.put("trxValueOut", trxValueIn);
				resultMap.put("facilitydetailsList", facilitydetailsList);

				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,exceptionMap);
				return returnMap;
			}
			
			else{
			ctx.setRemarks(remarks);
			}
			
			IFileUploadTrxValue trxValueOut = getFacilitydetailsuploadProxy().checkerRejectFileUpload(ctx, trxValueIn);
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
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}


}
