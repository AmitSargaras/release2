package com.integrosys.cms.ui.segmentWiseEmail;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.segmentWiseEmail.bus.ISegmentWiseEmail;
import com.integrosys.cms.app.segmentWiseEmail.bus.SegmentWiseEmailException;
import com.integrosys.cms.app.segmentWiseEmail.proxy.ISegmentWiseEmailProxyManager;
import com.integrosys.cms.app.segmentWiseEmail.trx.ISegmentWiseEmailTrxValue;
import com.integrosys.cms.app.segmentWiseEmail.trx.OBSegmentWiseEmailTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class CheckerApproveEditSegmentWiseEmailCmd extends AbstractCommand implements ICommonEventConstant {
	
	private ISegmentWiseEmailProxyManager segmentWiseEmailProxy;

	public ISegmentWiseEmailProxyManager getSegmentWiseEmailProxy() {
		return segmentWiseEmailProxy;
	}

	public void setSegmentWiseEmailProxy(ISegmentWiseEmailProxyManager segmentWiseEmailProxy) {
		this.segmentWiseEmailProxy = segmentWiseEmailProxy;
	}
	/**
	 * Default Constructor
	 */
	public CheckerApproveEditSegmentWiseEmailCmd() {
	}
	
	/**
	 * Defines an two dimensional array with the result list to be
	 * expected as a result from the doExecute method using a HashMap
	 * syntax for the array is (HashMapkey,classname,scope)
	 * The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][]{
				{"ISegmentWiseEmailTrxValue", "com.integrosys.cms.app.segmentWiseEmail.trx.ISegmentWiseEmailTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE}
		}
		);
	}
	
	/**
	 * Defines an two dimensional array with the result list to be
	 * expected as a result from the doExecute method using a HashMap
	 * syntax for the array is (HashMapkey,classname,scope)
	 * The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][]{
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
		}
		);
	}
	
	/**
	 * This method does the Business operations  with the HashMap and put the results back into
	 * the HashMap.
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
			ISegmentWiseEmailTrxValue trxValueIn = (OBSegmentWiseEmailTrxValue) map.get("ISegmentWiseEmailTrxValue");
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			SearchResult emailList = new SearchResult();

			ISegmentWiseEmailTrxValue trxValueOut = getSegmentWiseEmailProxy().checkerApproveSegmentWiseEmail(ctx, trxValueIn);
			
			if(null!=trxValueOut && null!=trxValueOut.getReferenceID() && null!=trxValueOut.getStagingReferenceID()) {
				long stageRefId=Long.parseLong(trxValueOut.getStagingReferenceID());
				
				emailList= (SearchResult) getSegmentWiseEmailProxy().getStageEmail(stageRefId);
				List list=(List) emailList.getResultList();
				
				if("INACTIVE".equals(trxValueOut.getSegmentWiseEmail().getStatus())) {
					getSegmentWiseEmailProxy().deleteActualEmailIDs(trxValueOut.getReferenceID(), list);
				}else {
					getSegmentWiseEmailProxy().insertDataToActualEmailTable(trxValueOut.getReferenceID(), list);
				}
			}
			
			resultMap.put("request.ITrxValue", trxValueOut);
			
		}catch (SegmentWiseEmailException ex) {
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

