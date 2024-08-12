package com.integrosys.cms.app.leiDetailsUpload;

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
import com.integrosys.cms.app.fileUpload.bus.OBLeiDetailsFile;
import com.integrosys.cms.app.fileUpload.trx.IFileUploadTrxValue;
import com.integrosys.cms.app.fileUpload.trx.OBFileUploadTrxValue;
import com.integrosys.cms.app.leiDetailsUpload.proxy.ILeiDetailsUploadProxyManager;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;

public class CheckerRejectLeiDetailsFileCmd extends AbstractCommand implements	ICommonEventConstant{
	
	private ILeiDetailsUploadProxyManager leidetailsuploadProxy;
	

	public ILeiDetailsUploadProxyManager getLeiDetailsuploadProxy() {
		return leidetailsuploadProxy;
	}

	public void setLeiDetailsuploadProxy(ILeiDetailsUploadProxyManager leidetailsuploadProxy) {
		this.leidetailsuploadProxy = leidetailsuploadProxy;
	} 

	/**
	 * Default Constructor
	 */
	public CheckerRejectLeiDetailsFileCmd() {
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
				{"leidetailsList", "java.util.List", SERVICE_SCOPE},
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
				{"leidetailsList","java.util.List",SERVICE_SCOPE}
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
			List leidetailsList=(List)map.get("leidetailsList");
			List totalUploadedList=new ArrayList();
			IFileUploadJdbc jdbc=(IFileUploadJdbc)BeanHouse.get("fileUploadJdbc");
			if(null!=leidetailsList ){
				for(int i=0; i<leidetailsList.size(); i++){
					OBLeiDetailsFile oBLeiDetailsFile = (OBLeiDetailsFile)leidetailsList.get(i);
					
					totalUploadedList.add(oBLeiDetailsFile);
				}
			}
			
			for (int j = 0; j < totalUploadedList.size(); j++) {
				OBLeiDetailsFile obLeiDetailsFile  = (OBLeiDetailsFile) totalUploadedList.get(j);
					
				  jdbc.createRejectLeiDetailsActualFile(obLeiDetailsFile);
			      
			}
			String remarks = (String) map.get("remarks");
			
			if (null == remarks || remarks.isEmpty()) {
				exceptionMap.put("limitRemarksError", new ActionMessage("error.string.mandatory"));
				resultMap.put("trxValueOut", trxValueIn);
				resultMap.put("leidetailsList", leidetailsList);

				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,exceptionMap);
				return returnMap;
			}else{
			ctx.setRemarks(remarks);			
			}			
			IFileUploadTrxValue trxValueOut = getLeiDetailsuploadProxy().checkerRejectFileUpload(ctx, trxValueIn);
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
