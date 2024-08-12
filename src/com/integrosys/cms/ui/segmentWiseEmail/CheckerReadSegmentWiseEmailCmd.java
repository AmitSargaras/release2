package com.integrosys.cms.ui.segmentWiseEmail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
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

public class CheckerReadSegmentWiseEmailCmd extends AbstractCommand implements ICommonEventConstant {

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
	public CheckerReadSegmentWiseEmailCmd() {
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
				{ "segmentWiseEmailObj", "com.integrosys.cms.app.segmentWiseEmail.bus.OBSegmentWiseEmail", FORM_SCOPE },
				{"ISegmentWiseEmailTrxValue", "com.integrosys.cms.app.segmentWiseEmail.trx.ISegmentWiseEmailTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "emailList", "java.util.List", SERVICE_SCOPE },
				{ "emailList", "java.util.List", REQUEST_SCOPE },
				{ "isDelete", "java.lang.String", REQUEST_SCOPE },
				{ "allEmailList", "java.util.List", REQUEST_SCOPE },
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
			List allEmailList=null;
			SearchResult emailList = new SearchResult();
			String aTrxID=(String) (map.get("TrxId"));
			String event = (String) map.get("event");
			
			trxValue = (OBSegmentWiseEmailTrxValue) getSegmentWiseEmailProxy().getSegmentWiseEmailByTrxID(aTrxID);
			segmentWiseEmail = (OBSegmentWiseEmail) trxValue.getStagingSegmentWiseEmail();
			
			emailList= (SearchResult) getSegmentWiseEmailProxy().getStageEmail(segmentWiseEmail.getID());
			List list=(List) emailList.getResultList();
			
			if("maker_prepare_resubmit_delete".equals(event)) {
				segmentWiseEmailList = (SearchResult)getSegmentWiseEmailProxy().getSegmentWiseEmail(segmentWiseEmail.getSegment());
				allEmailList=(List) segmentWiseEmailList.getResultList();
			
				List temp=new ArrayList<ISegmentWiseEmail>();
				for(int i=0;i<allEmailList.size();i++){
					ISegmentWiseEmail obj=(ISegmentWiseEmail)allEmailList.get(i);
					int counter = 0;
					for(int j=0;j<list.size();j++) {
						ISegmentWiseEmail obj2=(ISegmentWiseEmail)list.get(j);
						if(obj.getEmail().equals(obj2.getEmail())){
							counter++;
						}
					}
					if(counter == 0) {
						ISegmentWiseEmail obj3=(ISegmentWiseEmail)allEmailList.get(i);
						temp.add(obj3);
					}
				}
				
				resultMap.put("allEmailList", temp);
			}
			
			if(null!=segmentWiseEmail && "INACTIVE".equals(segmentWiseEmail.getStatus())) {
				resultMap.put("isDelete", "YES");
			}else {
				resultMap.put("isDelete", "NO");
			}
			
			resultMap.put("emailList", list);
			resultMap.put("ISegmentWiseEmailTrxValue", trxValue);
			resultMap.put("segmentWiseEmailObj", segmentWiseEmail);
			resultMap.put("event", event);
		} catch (SegmentWiseEmailException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (TransactionException e) {
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
