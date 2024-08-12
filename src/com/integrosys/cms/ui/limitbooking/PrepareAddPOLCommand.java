/**
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.ui.limitbooking;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.ProductLimitException;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.SectorLimitException;
import com.integrosys.cms.app.limitbooking.bus.OBLimitBooking;
import com.integrosys.cms.app.limitbooking.proxy.ILimitBookingProxy;
import com.integrosys.cms.app.limitbooking.proxy.LimitBookingProxyFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author priya
 *
 */

public class PrepareAddPOLCommand extends AbstractCommand implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public PrepareAddPOLCommand() {
        DefaultLogger.debug(this,"Inside PrepareAddPOLCommand >>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    /**
     * Defines an two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() {
        return (new String[][]{
            {LimitBookingAction.LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking", FORM_SCOPE},
            {LimitBookingAction.SESSION_LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking", SERVICE_SCOPE},
            {"event", "java.lang.String", REQUEST_SCOPE},
        });
    }


    /**
     * Defines an two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return (new String[][]{
            {LimitBookingAction.LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking", FORM_SCOPE},
            {LimitBookingAction.SESSION_LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking", SERVICE_SCOPE},
            {"sectorCode", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
            {"sectorDesc", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
            {"prodTypeCode", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
            {"prodTypeDesc", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
        });
    }

    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.Here creation for Company Borrower is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        DefaultLogger.debug(this, "Inside doExecute()");

        OBLimitBooking limitBooking = (OBLimitBooking)map.get(LimitBookingAction.LIMIT_BOOKING);
        OBLimitBooking sesslimitBooking = (OBLimitBooking)map.get(LimitBookingAction.SESSION_LIMIT_BOOKING);
        
        String event = (String) map.get("event");
        
        limitBooking.setAllBkgs(sesslimitBooking.getAllBkgs());
        limitBooking.setLoanSectorList(sesslimitBooking.getLoanSectorList());
        limitBooking.setBankGroupList(sesslimitBooking.getBankGroupList());
        
        getEcoSectorCodeMap("sectorCode", "sectorDesc", resultMap);
        
        getProductTypeCodeMap("prodTypeCode", "prodTypeDesc", resultMap);
        
        resultMap.put(LimitBookingAction.LIMIT_BOOKING,limitBooking);
        
        if (event.equals(LimitBookingAction.EVENT_ADD_POL_ERROR)) {
        	resultMap.put(LimitBookingAction.SESSION_LIMIT_BOOKING, sesslimitBooking);
        }
        else {
        	resultMap.put(LimitBookingAction.SESSION_LIMIT_BOOKING, limitBooking);
        }
        
        DefaultLogger.debug(this,"limitBooking = " + limitBooking);
        
        DefaultLogger.debug(this, "Going out of doExecute()");
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
        
    }
    
    private void getEcoSectorCodeMap(String nameValue, String namelabels, HashMap result) {
    	
    	try{
    	ILimitBookingProxy proxy = LimitBookingProxyFactory.getProxy();
    	Map ecoSectorLimit = proxy.getEcoSectorCodeMap();
    	
    	List values = new ArrayList();
    	List labels = new ArrayList();
        
    	ArrayList as = new ArrayList( ecoSectorLimit.entrySet() );   
        
        Collections.sort( as , new Comparator() {   
            public int compare( Object o1 , Object o2 )   
            {   
                Map.Entry e1 = (Map.Entry)o1 ;   
                Map.Entry e2 = (Map.Entry)o2 ;   
                String first = (String)e1.getValue();   
                String second = (String)e2.getValue();   
                return first.compareTo( second );   
            }   
        });   
           
        Iterator i = as.iterator();   
        while ( i.hasNext() )   
        {   
        	Map.Entry temp = (Map.Entry)i.next();
        	values.add(temp.getKey());
        	labels.add(temp.getValue());
        }  
    	
        result.put(nameValue, values);
        result.put(namelabels, labels);
    	}
    	catch (SectorLimitException e) {
    		 DefaultLogger.debug(this, "got exception in getEcoSectorCodeMap" + e);
             e.printStackTrace();
    	}
    }
    
    private void getProductTypeCodeMap(String nameValue, String namelabels, HashMap result) {
    	
    	try{
    	ILimitBookingProxy proxy = LimitBookingProxyFactory.getProxy();
    	Map prodTypeLimit = proxy.getProductTypeCodeMap();
    	
    	List values = new ArrayList();
    	List labels = new ArrayList();
        
    	ArrayList as = new ArrayList( prodTypeLimit.entrySet() );   
        
        Collections.sort( as , new Comparator() {   
            public int compare( Object o1 , Object o2 )   
            {   
                Map.Entry e1 = (Map.Entry)o1 ;   
                Map.Entry e2 = (Map.Entry)o2 ;   
                String first = (String)e1.getValue();   
                String second = (String)e2.getValue();   
                return first.compareTo( second );   
            }   
        });   
           
        Iterator i = as.iterator();   
        while ( i.hasNext() )   
        {   
        	Map.Entry temp = (Map.Entry)i.next();
        	values.add(temp.getKey());
        	labels.add(temp.getValue());
        }  
        
        result.put(nameValue, values);
        result.put(namelabels, labels);
    	}
    	catch (ProductLimitException e) {
    		 DefaultLogger.debug(this, "got exception in getProductTypeCodeMap" + e);
             e.printStackTrace();
    	}
    }
  
}



