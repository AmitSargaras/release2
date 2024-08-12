package com.integrosys.cms.ui.creditriskparam.producttypelimit;

import java.util.*;

import com.integrosys.cms.ui.creditriskparam.productprogramlimit.ProductLimitCommand;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductTypeLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.ProductLimitException;

public class UpdateProductLmtCmd extends ProductLimitCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][]{
			{"indexID", "java.lang.String", REQUEST_SCOPE},
			{"productTypeLimitForm", "com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductTypeLimitParameter", FORM_SCOPE},
			{"productProgramLimitObj", "com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter", SERVICE_SCOPE},
			{"remarks","java.lang.String",SERVICE_SCOPE },
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][]{
			{"productProgramLimitObj", "com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter", SERVICE_SCOPE},
			{"remarks","java.lang.String",SERVICE_SCOPE},
		});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();
        
        try {
        	String indexID = (String)map.get("indexID");
        
        	IProductProgramLimitParameter productLimitObj = (IProductProgramLimitParameter)map.get("productProgramLimitObj");
        	IProductTypeLimitParameter prodTypeObj = (IProductTypeLimitParameter)map.get("productTypeLimitForm");
        
        	List originalProdTypeLimitList =  (List) productLimitObj.getProductTypeList();
        	
        	IProductTypeLimitParameter origProdType = (IProductTypeLimitParameter)originalProdTypeLimitList.get(Integer.parseInt(indexID));
        	
        	List productTypeList = getProductLimitProxy().listProductType();
        	
        	List editedProductTypeList = new ArrayList();
        	editedProductTypeList.addAll(originalProdTypeLimitList);
        	editedProductTypeList.remove(Integer.parseInt(indexID));
        	productTypeList.addAll(editedProductTypeList);
        	
        	boolean isDuplicate = false;
        	
        	if (productTypeList != null && !origProdType.getReferenceCode().equals(prodTypeObj.getReferenceCode())) {
        		isDuplicate = validateDuplicate(prodTypeObj, productTypeList, exceptionMap);
        	}
        	
        	if (!isDuplicate) {
        		originalProdTypeLimitList.set(Integer.parseInt(indexID),prodTypeObj);
        	}
    	
        	Collections.sort(originalProdTypeLimitList, new AlphabeticComparator());
        
        	productLimitObj.setProductTypeList((Collection)originalProdTypeLimitList);       
        
        	String remarks = (String)map.get("remarks");
        	resultMap.put("remarks", remarks);

        
        	resultMap.put("productProgramLimitObj", productLimitObj);
        	
        }
        catch (ProductLimitException e) {
        	DefaultLogger.debug(this,"ProductLimitException caught! " + e.toString());
        }
        
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;	        
        
	}	
	
	class AlphabeticComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            IProductTypeLimitParameter s1 = (IProductTypeLimitParameter) o1;
            IProductTypeLimitParameter s2 = (IProductTypeLimitParameter) o2;
            return s1.getProductTypeDesc().compareTo(s2.getProductTypeDesc());
        }
    }
	
	private boolean validateDuplicate(IProductTypeLimitParameter prodTypeObj, List productTypeList, HashMap exceptionMap) {
		
		boolean isDuplicate = false;
		
		for (int jj= 0; jj < productTypeList.size(); jj++) {       
            if (prodTypeObj.getReferenceCode().equals(((IProductTypeLimitParameter)productTypeList.get(jj)).getReferenceCode())){
                exceptionMap.put("duplicateEntryError", new ActionMessage("error.entries.duplicate"));
                isDuplicate = true;
                break;

            }
        }
		return isDuplicate;
    }
}
