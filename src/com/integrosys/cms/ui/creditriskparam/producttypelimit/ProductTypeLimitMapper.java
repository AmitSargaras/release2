package com.integrosys.cms.ui.creditriskparam.producttypelimit;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductTypeLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.OBProductTypeLimitParameter;

public class ProductTypeLimitMapper extends AbstractCommonMapper {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
           {"productProgramLimitObj", "com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter", SERVICE_SCOPE},
           {"indexID", "java.lang.String", REQUEST_SCOPE},
        });
    }
    
    public Object mapFormToOB(CommonForm commonForm, HashMap inputs) throws MapperException {

        ProductTypeLimitForm aForm = (ProductTypeLimitForm) commonForm;
        
        IProductTypeLimitParameter obToChange = (IProductTypeLimitParameter)inputs.get("productTypeLimitObj"); ;
        
        int indexID = Integer.parseInt((String)inputs.get("indexID"));
        
        if (indexID == ICMSConstant.INT_INVALID_VALUE) {
        	obToChange = new OBProductTypeLimitParameter();
        } else {
        	IProductProgramLimitParameter productLmtObj = (IProductProgramLimitParameter)inputs.get("productProgramLimitObj");
        	try {
        		
        		obToChange = (IProductTypeLimitParameter)AccessorUtil.deepClone(((List) productLmtObj.getProductTypeList()).get(indexID));
        		
            } catch (Exception e) {
                e.printStackTrace();
                throw new MapperException(e.getMessage());
            }    
        }

        try {
        	obToChange.setProductTypeDesc(aForm.getProdTypeDesc());
        	obToChange.setReferenceCode(aForm.getProdTypeRefCode());
        	
        } catch (Exception e) {
        	throw new MapperException (e.getMessage());
        }
        
        return obToChange;
    }
    
    public CommonForm mapOBToForm(CommonForm commonForm, Object o, HashMap inputs) throws MapperException {
    	
    	IProductTypeLimitParameter prodTypeLmtObj = (IProductTypeLimitParameter)o;
    	ProductTypeLimitForm aForm =(ProductTypeLimitForm)commonForm;
    	
        try {
        	if (prodTypeLmtObj != null){
        		
        		aForm.setProdTypeDesc(prodTypeLmtObj.getProductTypeDesc());
        		aForm.setProdTypeRefCode(prodTypeLmtObj.getReferenceCode());
            
        	}
            
            return aForm;

        } catch (Exception e) {
            throw new MapperException(e.getMessage());
        }
    	
    	
    }    
}
