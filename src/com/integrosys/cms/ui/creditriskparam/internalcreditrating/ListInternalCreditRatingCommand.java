package com.integrosys.cms.ui.creditriskparam.internalcreditrating;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Comparator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.base.techinfra.util.SortUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.InternalCreditRatingException;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRatingParam;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.OBInternalCreditRatingParam;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRating;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.OBInternalCreditRating;
import com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue;
import com.integrosys.cms.app.creditriskparam.proxy.internalcreditrating.IInternalCreditRatingProxy;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
//public class ListInternalCreditRatingCommand extends AbstractCommand {
public class ListInternalCreditRatingCommand extends InternalCreditRatingCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][]{			
			{"trxID", "java.lang.String", REQUEST_SCOPE},
			{"event", "java.lang.String", REQUEST_SCOPE},
			{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
			{"internalCreditRatingTrxObj", "com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue", SERVICE_SCOPE},
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][]{           
			{"internalCreditRatingTrxObj", "com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue", SERVICE_SCOPE},
			{"InternalCreditRatingForm", "java.lang.Object", FORM_SCOPE},
			{"wip", "java.lang.String", REQUEST_SCOPE},			
			{"fromEvent", "java.lang.String", REQUEST_SCOPE},
		});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		   HashMap result = new HashMap();
           HashMap exceptionMap = new HashMap();
           HashMap temp = new HashMap();
        
            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			String event = (String)(map.get("event"));
        	String trxID = (String)(map.get("trxID"));
        
//        	IInternalCreditRatingProxy proxy = InternalCreditRatingProxyFactory.getProxy();
            IInternalCreditRatingProxy proxy = getInternalCreditRatingProxy();
		    IInternalCreditRatingParam itemList = new OBInternalCreditRatingParam();
		    IInternalCreditRatingTrxValue internalCreditRatingTrxObj = null;
			DefaultLogger.debug(this, "**********event IS: " + event);
			
//			System.out.println("trxID:::" + trxID);
//			System.out.println("coming event:::"+event);
		
		try {
            if ("maker_prepare_update".equals(event)
                    || "maker_prepare_close".equals(event)
                    || "to_track".equals(event)
                    || "checker_prepare_process".equals(event)
                    ) {
            	
	                if (trxID == null) {
	            		internalCreditRatingTrxObj = (IInternalCreditRatingTrxValue)map.get("internalCreditRatingTrxObj");
	            	}
	            	else {
	            		internalCreditRatingTrxObj = proxy.getInternalCreditRatingTrxValueByTrxID(ctx, trxID);
	            	}
	                
	                List stagingList = internalCreditRatingTrxObj.getStagingICRList();
	                
	                if(stagingList!= null&& stagingList.size()>0){
	                	String[] param = {"IntCredRatCode"};
	                    SortUtil.sortObject(stagingList, param);  
	                }
	                
	                itemList.setInternalCreditRatingList(stagingList);

//                System.out.println("stagingList:::"+ itemList.getInternalCreditRatingList());
                result.put("fromEvent", event);
            } else {
                internalCreditRatingTrxObj = proxy.getInternalCreditRatingTrxValue(ctx);
                
                List actualList = internalCreditRatingTrxObj.getActualICRList();
                
                if(actualList != null && actualList.size()>0){
                String[] param = {"IntCredRatCode"};
                SortUtil.sortObject(actualList, param);  
                }
                itemList.setInternalCreditRatingList(actualList);    
//                System.out.println("actualList:::"+ itemList.getInternalCreditRatingList());   
            }

            if ("credit_rating_list".equals(event))
                if ((ICMSConstant.STATE_ND.equals(internalCreditRatingTrxObj.getStatus())
                        && internalCreditRatingTrxObj.getStagingICRList() != null
                        && internalCreditRatingTrxObj.getStagingICRList().size() > 0)
                        || ICMSConstant.STATE_PENDING_UPDATE.equals(internalCreditRatingTrxObj.getStatus())
                        || ICMSConstant.STATE_REJECTED_UPDATE.equals(internalCreditRatingTrxObj.getStatus())
                        || ICMSConstant.STATE_REJECTED_CREATE.equals(internalCreditRatingTrxObj.getStatus())
                        || ICMSConstant.STATE_PENDING_CREATE.equals(internalCreditRatingTrxObj.getStatus())) {
	                       
                    result.put("wip", "wip");
                }

                DefaultLogger.error(this, "trxValue" + internalCreditRatingTrxObj);
                
            result.put("InternalCreditRatingForm", itemList);
            result.put("internalCreditRatingTrxObj", internalCreditRatingTrxObj);
        } catch (InternalCreditRatingException e) {
            DefaultLogger.error(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
		  temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
	    temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
	    return temp;
	}
	
}
