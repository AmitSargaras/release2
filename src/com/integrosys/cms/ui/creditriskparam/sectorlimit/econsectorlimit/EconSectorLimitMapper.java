package com.integrosys.cms.ui.creditriskparam.sectorlimit.econsectorlimit;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IEcoSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.OBEcoSectorLimitParameter;

public class EconSectorLimitMapper extends AbstractCommonMapper {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
           {"subSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter", SERVICE_SCOPE},
            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,"java.util.Locale", GLOBAL_SCOPE},
            {"ecoIndexId", "java.lang.String", REQUEST_SCOPE},
        });
    }
    
    public Object mapFormToOB(CommonForm commonForm, HashMap inputs) throws MapperException {

        EconSectorLimitForm aForm = (EconSectorLimitForm) commonForm;
        
        IEcoSectorLimitParameter obToChange;
        
        int indexId = Integer.parseInt((String)inputs.get("ecoIndexId"));
        if (indexId == ICMSConstant.INT_INVALID_VALUE) {
        	obToChange = new OBEcoSectorLimitParameter();
        } else {
        	ISubSectorLimitParameter sectorLmtObj = (ISubSectorLimitParameter)inputs.get("subSectorLimitObj");
        	try {
        		
        		DefaultLogger.debug(this, "Eco Mapper, getEcoSectorList= " + sectorLmtObj.getEcoSectorList());
        		obToChange = (IEcoSectorLimitParameter)AccessorUtil.deepClone(((List) sectorLmtObj.getEcoSectorList()).get(indexId));
        		DefaultLogger.debug(this, "Eco Mapper, obToChange= " + obToChange);
        
            } catch (Exception e) {
                e.printStackTrace();
                throw new MapperException(e.getMessage());
            }        		
        }

        try {
        	obToChange.setLoanPurposeCode(aForm.getEconSectorCode());
        	
        	if(aForm.getLimitPercentage().equals("")){
        		obToChange.setLimitPercentage(null);
        	}else{
        		obToChange.setLimitPercentage(new Double(aForm.getLimitPercentage()));
        	}

        	if(aForm.getConventionalBankPercentage().equals("")){
        		obToChange.setConventionalBankPercentage(null);
        	}else{
        		obToChange.setConventionalBankPercentage(new Double(aForm.getConventionalBankPercentage()));
        	}
        
        	if(aForm.getIslamicBankPercentage().equals("")){
        	    obToChange.setIslamicBankPercentage(null);
        	}else{
        		obToChange.setIslamicBankPercentage(new Double(aForm.getIslamicBankPercentage()));
        	}

        	if(aForm.getInvestmentBankPercentage().equals("")){
        	    obToChange.setInvestmentBankPercentage(null);
        	}else{
        		obToChange.setInvestmentBankPercentage(new Double(aForm.getInvestmentBankPercentage()));
        	}
        	
        	obToChange.setSectorCode(aForm.getSecCode());
        	
        } catch (Exception e) {
        	throw new MapperException (e.getMessage());
        }
        
        return obToChange;
    }
    
    public CommonForm mapOBToForm(CommonForm commonForm, Object o, HashMap inputs) throws MapperException {
    	
    	IEcoSectorLimitParameter econSectorLmtObj = (IEcoSectorLimitParameter)o;
    	EconSectorLimitForm aForm =(EconSectorLimitForm)commonForm;
    	
        try {
        	if (econSectorLmtObj != null){
	           	aForm.setEconSectorCode(econSectorLmtObj.getLoanPurposeCode());
	
	           	DefaultLogger.debug(this, "Eco sector limit mapper= " + econSectorLmtObj.getLimitPercentage());
	            if (econSectorLmtObj.getLimitPercentage() == null){
	            	aForm.setLimitPercentage("");
	            }else{
	            	aForm.setLimitPercentage(String.valueOf(econSectorLmtObj.getLimitPercentage()));
	            }
	            
	            if (econSectorLmtObj.getConventionalBankPercentage() == null){
	            	aForm.setConventionalBankPercentage("");
	            }else{
	            	aForm.setConventionalBankPercentage(String.valueOf(econSectorLmtObj.getConventionalBankPercentage()));
	            }
	            
	            if (econSectorLmtObj.getIslamicBankPercentage() == null){
	            	aForm.setIslamicBankPercentage("");
	            }else{
	            	aForm.setIslamicBankPercentage(String.valueOf(econSectorLmtObj.getIslamicBankPercentage()));
	            }
	            
	            if (econSectorLmtObj.getInvestmentBankPercentage() == null){
	            	aForm.setInvestmentBankPercentage("");
	            }else{
	            	aForm.setInvestmentBankPercentage(String.valueOf(econSectorLmtObj.getInvestmentBankPercentage()));
	            }
	            
	            aForm.setSecCode(econSectorLmtObj.getSectorCode());
            
        	}
            
            return aForm;

        } catch (Exception e) {
            throw new MapperException(e.getMessage());
        }
    	
    	
    }    
}
