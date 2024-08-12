package com.integrosys.cms.ui.creditriskparam.sectorlimit;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.OBMainSectorLimitParameter;
import com.integrosys.cms.ui.creditriskparam.sectorlimit.SectorLimitForm;

/**
 * Author: Syukri
 * Date: Jun 5, 2008
 */
public class SectorLimitMapper extends AbstractCommonMapper {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
            {"mainSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter", SERVICE_SCOPE},
            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,"java.util.Locale", GLOBAL_SCOPE},          
        });
    }

    public CommonForm mapOBToForm(CommonForm commonForm, Object obj, HashMap inputs) throws MapperException {
        
    	SectorLimitForm sectorLimitForm = (SectorLimitForm) commonForm;
    	DefaultLogger.debug(this, "in setorlimit form= " + sectorLimitForm.getEvent());
    	DefaultLogger.debug(this, "in setorlimit mapOBtoForm= " + obj);
    	IMainSectorLimitParameter mainSectorObj = (IMainSectorLimitParameter) obj;
    	
        

        if (sectorLimitForm.getEvent().equals(SectorLimitAction.EVENT_DELETE_ITEM)) {
        	sectorLimitForm.setDeleteItems(null);
        	return sectorLimitForm;
        }

        try {
 
        	sectorLimitForm.setSectorCode(mainSectorObj.getLoanPurposeCode());

            if (mainSectorObj.getLimitPercentage() == null){
            	sectorLimitForm.setLimitPercentage("");
            }else{
                sectorLimitForm.setLimitPercentage(String.valueOf(mainSectorObj.getLimitPercentage()));
            }
            
            if (mainSectorObj.getConventionalBankPercentage() == null){
            	sectorLimitForm.setConventionalBankPercentage("");
            }else{
                sectorLimitForm.setConventionalBankPercentage(String.valueOf(mainSectorObj.getConventionalBankPercentage()));
            }
            
            if (mainSectorObj.getIslamicBankPercentage() == null){
            	 sectorLimitForm.setIslamicBankPercentage("");
            }else{
                sectorLimitForm.setIslamicBankPercentage(String.valueOf(mainSectorObj.getIslamicBankPercentage()));
            }
            
            if (mainSectorObj.getInvestmentBankPercentage() == null){
            	 sectorLimitForm.setInvestmentBankPercentage("");
            }else{
                sectorLimitForm.setInvestmentBankPercentage(String.valueOf(mainSectorObj.getInvestmentBankPercentage()));
            }
            
            sectorLimitForm.setSecCode(mainSectorObj.getSectorCode());
        	
            
            return sectorLimitForm;

        } catch (Exception e) {
            throw new MapperException(e.getMessage());
        }
    	
    }

    public Object mapFormToOB(CommonForm commonForm, HashMap inputs) throws MapperException {
                
        IMainSectorLimitParameter mainSectorObj = (IMainSectorLimitParameter)inputs.get("mainSectorLimitObj");        

        if (mainSectorObj == null) {
        	mainSectorObj = new OBMainSectorLimitParameter();
        }
        
        SectorLimitForm sectorLimitForm = (SectorLimitForm) commonForm;
        
        DefaultLogger.debug(this, "Main Mapper, event= " + sectorLimitForm.getEvent());

        try {
        	
        	DefaultLogger.debug(this, "Main Mapper, mainSectorObj=" + mainSectorObj);
        	DefaultLogger.debug(this, "Main Mapper,getLimitPercentage =" + sectorLimitForm.getLimitPercentage());
        	DefaultLogger.debug(this, "Main Mapper,getLimitPercentage =" + sectorLimitForm.getConventionalBankPercentage());
        	DefaultLogger.debug(this, "Main Mapper,getLimitPercentage =" + sectorLimitForm.getInvestmentBankPercentage());
        	DefaultLogger.debug(this, "Main Mapper,getLimitPercentage =" + sectorLimitForm.getIslamicBankPercentage());

        	
        	mainSectorObj.setLoanPurposeCode(sectorLimitForm.getSectorCode());
            
            if (SectorLimitAction.EVENT_DELETE_ITEM.equals(sectorLimitForm.getEvent())) {
            	String[] deleteItemList = sectorLimitForm.getDeleteItems();
            	DefaultLogger.debug(this, "sector limit mapper_delete item list= " + deleteItemList.length);
            	List subSectorList =  (List) mainSectorObj.getSubSectorList();
            	DefaultLogger.debug(this, "sector limit mapper_subSectorList= " + subSectorList);
            	ArrayList newItemList = new ArrayList();
            	if (deleteItemList != null && subSectorList.size() != 0) {
            		for (int i = 0; i < subSectorList.size(); i++) {
            			boolean isDelete = false;
            			for (int j = 0; j < deleteItemList.length; j++) {
            				int deleteIdx = Integer.parseInt(deleteItemList[j]);
            				if (i == deleteIdx) {
            					isDelete = true;
            					break;
            				}
            			}
            			if (!isDelete)
            				newItemList.add(subSectorList.get(i));
            		}
            		mainSectorObj.setSubSectorList(newItemList);            		
            	}
            }
            
            
            if(sectorLimitForm.getLimitPercentage().equals("")){
            	mainSectorObj.setLimitPercentage(null);
            }else{
            	mainSectorObj.setLimitPercentage(new Double(sectorLimitForm.getLimitPercentage()));
            }

            if(sectorLimitForm.getConventionalBankPercentage().equals("")){
            	mainSectorObj.setConventionalBankPercentage(null);
            }else{
            	mainSectorObj.setConventionalBankPercentage(new Double(sectorLimitForm.getConventionalBankPercentage()));
            }
        
            if(sectorLimitForm.getIslamicBankPercentage().equals("") ){
            	mainSectorObj.setIslamicBankPercentage(null);      	
            }else{
            	mainSectorObj.setIslamicBankPercentage(new Double(sectorLimitForm.getIslamicBankPercentage()));
            }
            
            if(sectorLimitForm.getInvestmentBankPercentage().equals("")){
            	mainSectorObj.setInvestmentBankPercentage(null);
            }else{
            	mainSectorObj.setInvestmentBankPercentage(new Double(sectorLimitForm.getInvestmentBankPercentage()));
            }
            
            mainSectorObj.setSectorCode(sectorLimitForm.getSecCode());
            
            return mainSectorObj;

        } catch (Exception e) {
            throw new MapperException(e.getMessage());
        }
    }

}