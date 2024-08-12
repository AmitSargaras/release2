package com.integrosys.cms.ui.creditriskparam.internallimit.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.creditriskparam.proxy.internallimit.InternalLimitProxyImpl;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.creditriskparam.proxy.internallimit.IInternalLimitProxy;
import com.integrosys.cms.app.creditriskparam.trx.internallimit.IInternalLimitParameterTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.internallimit.OBInternalLimitParameterTrxValue;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.OBInternalLimitParameter;
import java.util.Collection;
import java.util.ArrayList;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.InternalLimitException;
import com.integrosys.cms.ui.creditriskparam.internallimit.InternalLimitCommand;

public class InternalLmtMakerReadCommand extends InternalLimitCommand implements ICommonEventConstant {

				public String[][] getParameterDescriptor() {
				  return (new String[][]{
	                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	                {"obInternalLmtParam", "com.integrosys.cms.app.creditriskparam.bus.internallimit.OBInternalLimitParameter", FORM_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE}
          });
    	  }
    	  
    	  public String[][] getResultDescriptor(){
	    	  return (new String[][]{
	                {"internalLmtParamTrxValue", "com.integrosys.cms.app.creditriskparam.trx.internallimit.OBInternalLimitParameterTrxValue", SERVICE_SCOPE},
	                {"currencyCode" , "java.util.Collection" , ICommonEventConstant.REQUEST_SCOPE } ,  
	                {"obInternalLmtParam", "java.util.list", REQUEST_SCOPE}
	        });
    	  }
    	  
    	  
    	  
    	  public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
    	  
	    	HashMap returnMap = new HashMap();
	        HashMap resultMap = new HashMap();
	        OBTrxContext  trxContext = (OBTrxContext ) map.get("theOBTrxContext");
	        OBInternalLimitParameter obInternalLmtParam = (OBInternalLimitParameter) map.get("obInternalLmtParam");
	        String event = (String) map.get("event");
	        DefaultLogger.debug(this,"internal lmt param's event : " + event);

	        try {

	                IInternalLimitProxy proxy = getInternalLimitProxy();
	                IInternalLimitParameterTrxValue trxValue = proxy.getILParamTrxValue();

                    if (trxValue == null) {
                        trxValue = getEmptyTrxValue();
                    }
									
	                if ("internal_limit_list".equals(event)) {
	                    resultMap.put("internalLmtParamTrxValue", trxValue);
	                	resultMap.put("obInternalLmtParam", trxValue.getActualILPList());
	                } else {
		                // if current status is other than ACTIVE & REJECTED, then show workInProgress.
		                // i.e. allow edit only if status is either ACTIVE or REJECTED
		                DefaultLogger.debug(this,"internal lmt param's status b4: " + trxValue.getStatus());
		                if (!((trxValue.getStatus().equals(ICMSConstant.STATE_ND))
		                        || (trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE))
		                    )) {
//											System.out.println("************** wip *************");
											DefaultLogger.debug(this,"internal lmt param's status : " + trxValue.getStatus());
						                    resultMap.put("wip", "wip");
						                    resultMap.put("obInternalLmtParam", trxValue.getStagingILPList());
						                } else {
						                    resultMap.put("internalLmtParamTrxValue", trxValue);
						                }
						                resultMap.put("obInternalLmtParam", trxValue.getActualILPList());
					          }
		  
	            	
	            Collection list4 = CurrencyList.getInstance ().getCountryValues ();
							resultMap.put ( "currencyCode", list4 );
							
							DefaultLogger.debug(this, "Going out of doExecute()");
			        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			        return returnMap;

	        }catch (InternalLimitException e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }

	    }  
	    
	    
	  private IInternalLimitParameterTrxValue getEmptyTrxValue() {
				IInternalLimitParameterTrxValue trxValue = new OBInternalLimitParameterTrxValue();
				trxValue.setActualILPList(new ArrayList());
				trxValue.setStagingILPList(new ArrayList());
				return trxValue;
	  }
    	  
}