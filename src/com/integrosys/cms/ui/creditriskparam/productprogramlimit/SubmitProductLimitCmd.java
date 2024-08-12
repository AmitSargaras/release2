package com.integrosys.cms.ui.creditriskparam.productprogramlimit;

import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter;
import com.integrosys.cms.app.creditriskparam.trx.productlimit.IProductLimitParameterTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.productlimit.OBProductLimitParameterTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Author: Priya
 * Date: Oct 5, 2009
 */
public class SubmitProductLimitCmd extends ProductLimitCommand {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
            {"productLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.productlimit.IProductLimitParameterTrxValue", SERVICE_SCOPE},
            {"productProgramLimitForm", "com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter", FORM_SCOPE},
        });

    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
            {"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();
        IProductLimitParameterTrxValue trxValue = null;

        try {
        	 IProductProgramLimitParameter obj = (IProductProgramLimitParameter)map.get("productProgramLimitForm");
        	
	         ITrxContext ctx = (ITrxContext)map.get("theOBTrxContext");
	         IProductLimitParameterTrxValue productLimitTrxObj = (IProductLimitParameterTrxValue) map.get("productLimitTrxObj");
	         if (productLimitTrxObj == null)
	        	 productLimitTrxObj = new OBProductLimitParameterTrxValue();
	            
	         IProductProgramLimitParameter productLimitParameter = (IProductProgramLimitParameter)productLimitTrxObj.getActualProductProgramLimitParameter();
	         
	         List productProgramList = getProductLimitProxy().listProductLimit();
	         
	         boolean isDuplicate = false;
	        
	         if (productProgramList != null) {
	        	 
	        	 if (productLimitParameter != null){
	        		 for (int i = 0; i < productProgramList.size(); i++) {
		        		if (((IProductProgramLimitParameter)(productProgramList.get(i))).getReferenceCode().equals(obj.getReferenceCode())) {
		        			productProgramList.remove(i);
		        			break;
		        		}
		        	}
	        	 }
	        	 
	        	 isDuplicate = validateDuplicate(productProgramList, obj, exceptionMap);
	         }
	         
	         if (!isDuplicate) {
	        	 if (null == productLimitParameter){
	        	 //create
	        		 productLimitTrxObj.setStagingProductProgramLimitParameter(obj);
	        		 trxValue = getProductLimitProxy().makerUpdateList(ctx, productLimitTrxObj);
	        	 } else {
		         //update
	        		 productLimitTrxObj.setStagingProductProgramLimitParameter (obj);
	        		 trxValue = getProductLimitProxy().makerUpdateList(ctx, productLimitTrxObj);
	        	 }
	        	 resultMap.put("request.ITrxValue", trxValue);
	         }
	         returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	         returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        }catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        return returnMap;
    }
    
    private boolean validateDuplicate(List prodProgramList, IProductProgramLimitParameter productProgram, HashMap exceptionMap) {
		
		boolean isDuplicate = false;
        
		for (int i = 0; i < prodProgramList.size(); i++) {

            if (((IProductProgramLimitParameter)prodProgramList.get(i)).getReferenceCode().equals(productProgram.getReferenceCode())){
            	exceptionMap.put("duplicateEntryError", new ActionMessage("error.entries.duplicate"));
                isDuplicate = true;
                break;                     
            }
        }
       return isDuplicate;
    }
	
}
