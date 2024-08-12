package com.integrosys.cms.ui.udf;

import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.udf.bus.OBUdf;
import com.integrosys.cms.app.udf.bus.UDFConstants;
import com.integrosys.cms.app.udf.bus.UdfException;
import com.integrosys.cms.app.udf.proxy.IUdfProxyManager;
import com.integrosys.cms.app.udf.trx.IUdfTrxValue;
import com.integrosys.cms.app.udf.trx.OBUdfTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerEditUdfCmd extends AbstractCommand implements ICommonEventConstant{

	private IUdfProxyManager udfProxyManager;
	
	public IUdfProxyManager getUdfProxyManager() {
		return udfProxyManager;
	}

	public void setUdfProxyManager(IUdfProxyManager udfProxyManager) {
		this.udfProxyManager = udfProxyManager;
	}
	
	/**
	 * Default Constructor
	 */
	public MakerEditUdfCmd() {
	}
	
	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "IUdfTrxValue", "com.integrosys.cms.app.udf.trx.IUdfTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "UdfObj", "com.integrosys.cms.app.udf.bus.OBUdf", FORM_SCOPE }

		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 *
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		
			String event = (String) map.get("event");
			OBUdf udf = (OBUdf) map.get("UdfObj");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IUdfTrxValue trxValueIn = (OBUdfTrxValue) map.get("IUdfTrxValue");
			IUdfTrxValue trxValueOut = new OBUdfTrxValue();
			boolean isFieldNameUnique = false;
			if(trxValueIn.getFromState().equals("PENDING_PERFECTION")){
				/*int sequence=udf.getSequence();
				int SequenceActual=trxValueIn.getStagingUdf().getSequence();
				if(! productCode.equals(productCodeActual)) {
					isFieldNameUnique = false;  //getUdfProxy().isFieldNameUnique(productCode);				
					if(isFieldNameUnique != false){
						exceptionMap.put("productCodeError", new ActionMessage("error.string.exist","This Product Code "));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
				}
				trxValueOut = getUdfProxyManager().makerUpdateSaveCreateUdf(ctx, trxValueIn,udf);*/
			}
			else{
				if( event.equals("maker_edit_udf") ||event.equals("maker_save_update") || "maker_disable_udf".equals(event) ){

					/*List seqList = getUdfProxyManager().getUdfSequencesByModuleId(Long.toString((udf.getModuleId())));
					if (seqList != null && seqList.size() == UDFConstants.UDF_MAXLIMIT) {
						exceptionMap.put("udfError", new ActionMessage("udf.maxLimitExceeded"));
					}
					else if (seqList != null && seqList.contains(new Integer(udf.getSequence()))) {
						exceptionMap.put("udfError", new ActionMessage("udf.duplicateUdfNo"));
					}
					else {*/
						try {
					trxValueOut = getUdfProxyManager().makerUpdateUdf(ctx, trxValueIn,udf);
					resultMap.put("request.ITrxValue", trxValueOut);
						} catch (UdfException ex) {
					DefaultLogger.debug(this, "got exception in doExecute" + ex);
					ex.printStackTrace();
					throw (new CommandProcessingException(ex.getMessage()));
					} catch (TransactionException e) {
					DefaultLogger.debug(this, "got exception in doExecute" + e);
					throw (new CommandProcessingException(e.getMessage()));
					} catch (Exception e) {
					DefaultLogger.debug(this, "got exception in doExecute" + e);
					e.printStackTrace();
					throw (new CommandProcessingException(e.getMessage()));
						}
				
				//	}
					
				// event is  maker_confirm_resubmit_edit
			}
			}
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
		}

}