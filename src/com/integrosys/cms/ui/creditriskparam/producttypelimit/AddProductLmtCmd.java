package com.integrosys.cms.ui.creditriskparam.producttypelimit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.integrosys.cms.ui.creditriskparam.productprogramlimit.ProductLimitCommand;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductTypeLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.ProductLimitException;

public class AddProductLmtCmd extends ProductLimitCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][]{
		{"productTypeLimitForm", "com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductTypeLimitParameter", FORM_SCOPE},				
		{"productProgramLimitObj", "com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter", SERVICE_SCOPE},
		{"remarks","java.lang.String",SERVICE_SCOPE },
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][]{
		{"productProgramLimitObj", "com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter", SERVICE_SCOPE},
		{"remarks","java.lang.String",SERVICE_SCOPE },
			});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();
        
        try {
        
        	IProductProgramLimitParameter productProgramLimitObj = (IProductProgramLimitParameter)map.get("productProgramLimitObj");
        	IProductTypeLimitParameter prodTypeObj = (IProductTypeLimitParameter)map.get("productTypeLimitForm");
        
        	Collection originalProdTypeLimitList = productProgramLimitObj.getProductTypeList();
       
        	int prodTypeLimitSize = (originalProdTypeLimitList!=null)?originalProdTypeLimitList.size() : 0;
        
        	List prodTypeList = new ArrayList();
        
        	if (prodTypeLimitSize > 0){
        		prodTypeList.addAll(originalProdTypeLimitList);
        	}
        	
        	List productTypeList = getProductLimitProxy().listProductType();
        	productTypeList.addAll(prodTypeList);

        	boolean isDuplicate = false;
        	
        	if (productTypeList != null) {
        		isDuplicate = validateDuplicate(prodTypeObj, productTypeList, exceptionMap);
        	}
        	
        	if (!isDuplicate) {
        		prodTypeList.add(prodTypeObj);
        	}
        
        	Collections.sort(prodTypeList, new AlphabeticComparator());

        	productProgramLimitObj.setProductTypeList(prodTypeList);
        	
        	resultMap.put("productProgramLimitObj", productProgramLimitObj);        	
        
        }
        
        catch (ProductLimitException e) {
        	DefaultLogger.debug(this,"ProductLimitException caught! " + e.toString());
        }
 
        
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;	        
        
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
	
	class AlphabeticComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            IProductTypeLimitParameter s1 = (IProductTypeLimitParameter) o1;
            IProductTypeLimitParameter s2 = (IProductTypeLimitParameter) o2;
            return s1.getProductTypeDesc().compareTo(s2.getProductTypeDesc());
        }
    }

}
