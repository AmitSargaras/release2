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
import com.integrosys.cms.app.segmentWiseEmail.bus.OBSegmentWiseEmail;
import com.integrosys.cms.app.segmentWiseEmail.bus.SegmentWiseEmailException;
import com.integrosys.cms.app.segmentWiseEmail.proxy.ISegmentWiseEmailProxyManager;
import com.integrosys.cms.app.segmentWiseEmail.trx.ISegmentWiseEmailTrxValue;
import com.integrosys.cms.app.segmentWiseEmail.trx.OBSegmentWiseEmailTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;

public class MakerReadSegmentWiseEmailCmd extends AbstractCommand implements ICommonEventConstant{

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
	public MakerReadSegmentWiseEmailCmd() {
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
			 {"segment", "java.lang.String", REQUEST_SCOPE},
			 { "startIndex", "java.lang.String", REQUEST_SCOPE },
			 { "TrxId", "java.lang.String", REQUEST_SCOPE },
			 {"event", "java.lang.String", REQUEST_SCOPE},
		});
	}
	
	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "emailList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "segmentWiseEmailObj", "com.integrosys.cms.app.segmentWiseEmail.bus.OBSegmentWiseEmail", FORM_SCOPE },
				{"ISegmentWiseEmailTrxValue", "com.integrosys.cms.app.segmentWiseEmail.trx.ISegmentWiseEmailTrxValue", SERVICE_SCOPE},
				
		});
	}
	
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,SystemBankException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			SearchResult segmentWiseEmailList = new SearchResult();
			ISegmentWiseEmail segmentWiseEmail;
			ISegmentWiseEmailTrxValue trxValue=null;
			String segment=(String) (map.get("segment"));
			String startIdx = (String) map.get("startIndex");
			String event = (String) map.get("event");
			
			long segmentId= getSegmentWiseEmailProxy().getSegmentId(segment);
			
			trxValue = (OBSegmentWiseEmailTrxValue) getSegmentWiseEmailProxy().getSegmentWiseEmailTrxValue(segmentId);
			segmentWiseEmail = (OBSegmentWiseEmail) trxValue.getStagingSegmentWiseEmail();
			
			if((trxValue.getStatus().equals("PENDING_CREATE"))||(trxValue.getStatus().equals("PENDING_UPDATE"))
					||(trxValue.getStatus().equals("PENDING_DELETE"))||trxValue.getStatus().equals("REJECTED"))
			{
				resultMap.put("wip", "wip");
			}
			segmentWiseEmailList = (SearchResult)getSegmentWiseEmailProxy().getSegmentWiseEmail(segment);
			List list=(List) segmentWiseEmailList.getResultList();
			
			resultMap.put("ISegmentWiseEmailTrxValue", trxValue);
			resultMap.put("segmentWiseEmailObj", segmentWiseEmail);
			resultMap.put("emailList", list);
			resultMap.put("event", event);
			resultMap.put("startIndex",startIdx);
		} catch (SegmentWiseEmailException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
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
