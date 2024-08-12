package com.integrosys.cms.ui.creditriskparam.sectorlimit.subsectorlimit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.OBSubSectorLimitParameter;
import com.integrosys.cms.ui.creditriskparam.sectorlimit.subsectorlimit.SubSectorLimitAction;

public class SubSectorLimitMapper extends AbstractCommonMapper {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
            {"subSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter", SERVICE_SCOPE},
        	{"mainSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter", SERVICE_SCOPE},
            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,"java.util.Locale", GLOBAL_SCOPE},
            {"ecoIndexId", "java.lang.String", REQUEST_SCOPE},
            {"event", "java.lang.String", REQUEST_SCOPE},
        });
    }
    
    public Object mapFormToOB(CommonForm commonForm, HashMap inputs) throws MapperException {

         SubSectorLimitForm aForm = (SubSectorLimitForm) commonForm;
        
        ISubSectorLimitParameter obToChange;
        
        String event = (String)inputs.get("event");
        int indexId = Integer.parseInt((String)inputs.get("ecoIndexId"));
        
        DefaultLogger.debug(this, "Sub Mapper, event= " + event);
        
        if("edit_item".equals(aForm.getEvent())||"delete_eco_item".equals(aForm.getEvent())){
        	
        	DefaultLogger.debug(this, "Sub Mapper here!!! edit item " + indexId);
	        obToChange = (ISubSectorLimitParameter)inputs.get("subSectorLimitObj");

        }
        else{
        
        	if (indexId == ICMSConstant.INT_INVALID_VALUE) {
        		 if("add_eco_items".equals(event) || "create".equals(event)){
        			 obToChange = (ISubSectorLimitParameter)inputs.get("subSectorLimitObj");
        	         DefaultLogger.debug(this, "Sub Mapper here!!! getEcoSectorList, event " + obToChange.getEcoSectorList() + event);
        		 }else{
		          obToChange = new OBSubSectorLimitParameter();
		          DefaultLogger.debug(this, "Sub Mapper here !!!, create new OB, event is " + event);
        		 }

	        } 
	        else {
	        	
	        	try {
		       		obToChange = (ISubSectorLimitParameter)inputs.get("subSectorLimitObj");
			        } catch (Exception e) {
		                e.printStackTrace();
		                throw new MapperException(e.getMessage());
		            }      
	        }
        }

        try {
        	obToChange.setLoanPurposeCode(aForm.getSubSectorCode());
        	
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
        	
        	if (SubSectorLimitAction.EVENT_DELETE_ECO_ITEM.equals(aForm.getEvent())) {
            	String[] deleteItemList = aForm.getDeleteEcoItems();
            	DefaultLogger.debug(this, "sector limit mapper_delete item list, eco= " + deleteItemList.length);
            	List ecoSectorList =  (List) obToChange.getEcoSectorList();
            	DefaultLogger.debug(this, "sector limit mapper_ecoSectorList from Object= " + ecoSectorList);
            	ArrayList newItemList = new ArrayList();
            	if (deleteItemList != null && ecoSectorList != null) {
            		for (int i = 0; i < ecoSectorList.size(); i++) {
            			boolean isDelete = false;
            			for (int j = 0; j < deleteItemList.length; j++) {
            				int deleteIdx = Integer.parseInt(deleteItemList[j]);
            				if (i == deleteIdx) {
            					isDelete = true;
            					break;
            				}
            			}
            			if (!isDelete)
            				newItemList.add(ecoSectorList.get(i));
            		}
            		obToChange.setEcoSectorList(newItemList);  
            		
            	}
            }
        	
        } catch (Exception e) {
        	throw new MapperException (e.getMessage());
        }
        DefaultLogger.debug(this, "sector limit getEcoSectorList= " + obToChange.getEcoSectorList());
        return obToChange;
    }
    
    public CommonForm mapOBToForm(CommonForm commonForm, Object o, HashMap inputs) throws MapperException {
    	
    	ISubSectorLimitParameter subSectorLmtObj = (ISubSectorLimitParameter)o;
    	SubSectorLimitForm aForm =(SubSectorLimitForm)commonForm;
    	
    	if (aForm.getEvent().equals(SubSectorLimitAction.EVENT_DELETE_ECO_ITEM)) {
    		aForm.setDeleteEcoItems(null);
        	return aForm;
        }
    	
        try {
        	if (subSectorLmtObj != null){
	           	aForm.setSubSectorCode(subSectorLmtObj.getLoanPurposeCode());
	           	
	           	DefaultLogger.debug(this, "Sub sector limit mapper= " + subSectorLmtObj.getLimitPercentage());
	           	
	            if (subSectorLmtObj.getLimitPercentage() == null){
	            	aForm.setLimitPercentage("");
	            }else{
	            	aForm.setLimitPercentage(String.valueOf(subSectorLmtObj.getLimitPercentage()));
	            }
	            
	            if (subSectorLmtObj.getConventionalBankPercentage() == null){
	            	aForm.setConventionalBankPercentage("");
	            }else{
	            	aForm.setConventionalBankPercentage(String.valueOf(subSectorLmtObj.getConventionalBankPercentage()));
		        }
	            	
	            if (subSectorLmtObj.getIslamicBankPercentage() == null){
	            	aForm.setIslamicBankPercentage("");
	            }else{
	            	aForm.setIslamicBankPercentage(String.valueOf(subSectorLmtObj.getIslamicBankPercentage()));
		        }
	            	
	            if (subSectorLmtObj.getInvestmentBankPercentage() == null){
	            	aForm.setInvestmentBankPercentage("");
	            }else{
	            	aForm.setInvestmentBankPercentage(String.valueOf(subSectorLmtObj.getInvestmentBankPercentage()));
		            
		        }
	            	
	            aForm.setSecCode(subSectorLmtObj.getSectorCode());
            
        	}
            
            return aForm;

        } catch (Exception e) {
            throw new MapperException(e.getMessage());
        }
    	
    	
    }    
}
