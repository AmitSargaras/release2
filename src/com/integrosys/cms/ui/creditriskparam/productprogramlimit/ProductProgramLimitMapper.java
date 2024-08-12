package com.integrosys.cms.ui.creditriskparam.productprogramlimit;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.OBProductProgramLimitParameter;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Author: Priya
 * Date: Oct 5, 2009
 */
public class ProductProgramLimitMapper extends AbstractCommonMapper {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
            {"productProgramLimitObj", "com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter", SERVICE_SCOPE},
            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,"java.util.Locale", GLOBAL_SCOPE},            
        });
    }

    public CommonForm mapOBToForm(CommonForm commonForm, Object o, HashMap inputs) throws MapperException {
 
        IProductProgramLimitParameter prodProgramObj = (IProductProgramLimitParameter) o;
        ProductProgramLimitForm productLimitForm = (ProductProgramLimitForm) commonForm;
        
        NumberFormat formatter = new DecimalFormat("#############.##");
        
        if (productLimitForm.getEvent().equals(ProductProgramLimitAction.EVENT_DELETE_ITEM)) {
        	productLimitForm.setDeleteItems(null);
        	return productLimitForm;
        }

        try {
        	productLimitForm.setProdProgramDesc(prodProgramObj.getProductProgramDesc());
            productLimitForm.setProdProgramRefCode(prodProgramObj.getReferenceCode());
            if (prodProgramObj.getLimitAmount()!= null) {
            	productLimitForm.setLimitAmt(formatter.format(prodProgramObj.getLimitAmount().getAmount()));
            }
            else {
            	productLimitForm.setLimitAmt("");
            }
            	
            return productLimitForm;

        } catch (Exception e) {
            throw new MapperException(e.getMessage());
        }
    }

    public Object mapFormToOB(CommonForm commonForm, HashMap inputs) throws MapperException {
                
        IProductProgramLimitParameter prodProgramObj = (IProductProgramLimitParameter)inputs.get("productProgramLimitObj"); 
        Locale locale = (Locale)inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

        if (prodProgramObj == null) {
        	prodProgramObj = new OBProductProgramLimitParameter();
        }
        
        ProductProgramLimitForm productLimitForm = (ProductProgramLimitForm) commonForm;
        
        try {
        	
        	prodProgramObj.setProductProgramDesc(productLimitForm.getProdProgramDesc());
            
            if (ProductProgramLimitAction.EVENT_DELETE_ITEM.equals(productLimitForm.getEvent())) {
            	String[] deleteItemList = productLimitForm.getDeleteItems();
            	List productTypeList =  (List) prodProgramObj.getProductTypeList();
            	ArrayList newItemList = new ArrayList();
            	if (deleteItemList != null && productTypeList.size() != 0) {
            		for (int i = 0; i < productTypeList.size(); i++) {
            			boolean isDelete = false;
            			for (int j = 0; j < deleteItemList.length; j++) {
            				int deleteIdx = Integer.parseInt(deleteItemList[j]);
            				if (i == deleteIdx) {
            					isDelete = true;
            					break;
            				}
            			}
            			if (!isDelete)
            				newItemList.add(productTypeList.get(i));
            		}
            		prodProgramObj.setProductTypeList(newItemList);            		
            	}
            }
            
            prodProgramObj.setReferenceCode(productLimitForm.getProdProgramRefCode());
            
            if (productLimitForm.getLimitAmt() != null && !productLimitForm.getLimitAmt().equals("")) {
            	prodProgramObj.setLimitAmount(UIUtil.convertToAmount(locale, "MYR", productLimitForm.getLimitAmt()));
            }
            else {
            	prodProgramObj.setLimitAmount(null);
            }
            
            return prodProgramObj;

        } catch (Exception e) {
            throw new MapperException(e.getMessage());
        }
    }

}